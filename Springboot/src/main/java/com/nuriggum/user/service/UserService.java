package com.nuriggum.user.service;

import com.nuriggum.nuriframe.common.message.model.MessageKey;
import com.nuriggum.nuriframe.common.message.util.MessageUtil;
import com.nuriggum.nuriframe.common.response.data.RequestData;
import com.nuriggum.nuriframe.common.util.NuriFormBasedUUID;
import com.nuriggum.nuriframe.common.util.NuriPassValidationUtil;
import com.nuriggum.nuriframe.common.util.NuriSecurityUtil;
import com.nuriggum.user.mapper.UserMapper;
import com.nuriggum.user.model.User;
import com.nuriggum.user.model.UserManage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    public Page<User> getUserList(User user, Pageable pageable) {
        if(user == null) user = new User();
        //검색 객체 생성
        RequestData<?> requestData = RequestData.builder().data(user).pageable(pageable).build();
        //사용자 목록 조회
        List<User> users = userMapper.selectUserList(requestData);
        //사용자 카운트 조회
        int count = userMapper.selectUserListCount(user);
        return new PageImpl<>(users, pageable, count);
    }

    public User getUserDetail(String userId) {
        return userMapper.selectUserDetail(userId);
    }

    public int userRegister(User user){
        //중복ID 체크
        if(userDuplicate(user)) throw new DuplicateKeyException(messageUtil.getMessage(MessageKey.DUPLICATE_ID, null));
        //userId UUID 설정
        user.setUserId(NuriFormBasedUUID.createUUID());
        //신규 비밀번호 유효성 검사
        Map<String, Object> validMap = NuriPassValidationUtil.isValidPassword(user.getPassword(), user.getLoginId());
        //유효성 검사 flag가 false 인경우 exception 처리
        if(!(boolean)validMap.get("flag")){
            //메세지 파라미터 체크
            String[] args = null;
            if(validMap.get("args") != null) args = (String[]) validMap.get("args");
            throw new IllegalArgumentException(messageUtil.getMessage(validMap.get("msg").toString(), args));
        }
        //패스워드 암호화 설정
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistUserId(NuriSecurityUtil.getLoginUserId());
        user.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        return userMapper.insertUser(user);
    }

    public boolean userDuplicate(User user) {
        // 중복된 loginId 존재하는지 조회
        return userMapper.selectUserDuplicate(user);
    }

    public Map<String, Object> changePassword(UserManage userManage) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = userMapper.selectUserDetail(userManage.getUserId());
        //비밀번호 일치 하지 않는 경우
        if(!passwordEncoder.matches(userManage.getPassword(), user.getPassword())) throw new IllegalArgumentException(MessageKey.INVALID_PASSWORD);
        //신규 비밀번호 일치 여부 검사
        if(!userManage.getNewPassword1().equals(userManage.getNewPassword2())) throw new IllegalArgumentException(MessageKey.NOT_MATCH_PASSWORD);
        //신규 비밀번호 유효성 검사
        resultMap = NuriPassValidationUtil.isValidPassword(userManage.getNewPassword1(), userManage.getLoginId());
        //유효성 검사 flag가 false 인경우 return 처리
        if(!(boolean)resultMap.get("flag")) return resultMap;
        //신규 비밀번호 설정 및 update처리
        user.setPassword(passwordEncoder.encode(userManage.getNewPassword1()));
        userMapper.updateUserPassword(user);
        return resultMap;
    }

    public User changeUser(String userId, User user) {
        user.setUserId(userId);
        user.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        userMapper.updateUser(user);
        return user;
    }

    public List<String> getLoginIds(User user) {
        List<String> loginIds = userMapper.selectLoginIds(user);
        if(loginIds.isEmpty()) throw new IllegalArgumentException(MessageKey.USER_NOT_EXIST);
        return loginIds;
    }
}
