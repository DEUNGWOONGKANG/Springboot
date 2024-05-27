package com.nuriggum.code.service;

import com.nuriggum.code.mapper.CodeMapper;
import com.nuriggum.code.model.CodeGroup;
import com.nuriggum.nuriframe.common.message.model.MessageKey;
import com.nuriggum.nuriframe.common.message.util.MessageUtil;
import com.nuriggum.nuriframe.common.response.data.RequestData;
import com.nuriggum.nuriframe.common.util.NuriSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CodeGroupService {

    private final CodeMapper codeMapper;
    private final MessageUtil messageUtil;

    public Page<CodeGroup> getCodeGroupList(CodeGroup codeGroup, Pageable pageable) {
        if(codeGroup == null) codeGroup = new CodeGroup();
        //검색 객체 생성
        RequestData<?> requestData = RequestData.builder().data(codeGroup).pageable(pageable).build();
        //코드 그룹 목록 조회
        List<CodeGroup> codeGroupList = codeMapper.selectCodeGroupList(requestData);
        //코드 그룹 총 카운트 조회
        int count = codeMapper.selectCodeGroupListCount(codeGroup);

        return new PageImpl<>(codeGroupList, pageable, count);
    }

    public int codeGroupRegister(CodeGroup codeGroup) {
        String groupName = codeGroup.getGroupName();
        //중복ID 체크를 위한 groupname 빈 String 처리
        codeGroup.setGroupName("");
        if(codeGroupDuplicate(codeGroup)) throw new DuplicateKeyException(messageUtil.getMessage(MessageKey.DUPLICATE_ID, null));

        //중복체크 통화 후 GroupName 다시 setting 후 insert
        codeGroup.setGroupName(groupName);
        codeGroup.setRegistUserId(NuriSecurityUtil.getLoginUserId());
        codeGroup.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        return codeMapper.insertCodeGroup(codeGroup);
    }

    public boolean codeGroupDuplicate(CodeGroup codeGroup) {
        return codeMapper.selectCodeGroupDuplicate(codeGroup);
    }

    public CodeGroup codeGroupUpdate(String groupId, CodeGroup codeGroup) {
        codeGroup.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        //url로 넘어온 groupId와 수정요청 파라미터로 넘어온 groupId가 다른경우 exception 처리
        if(!groupId.equals(codeGroup.getGroupId())) throw new IllegalStateException(messageUtil.getMessage(MessageKey.IS_NOT_SUPPORT, null));

        int result = codeMapper.updateCodeGroup(codeGroup);
        return codeGroup;
    }
}
