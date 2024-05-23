package com.nuriggum.user.mapper;

import com.nuriggum.nuriframe.common.response.data.RequestData;
import com.nuriggum.nuriframe.security.model.UserDetail;
import com.nuriggum.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserList(RequestData<?> requestData);

    UserDetail selectLoginUser(String userId);

    int insertUser(User user);

    int selectUserListCount(User user);

    User selectUserDetail(String userId);

    void updateUserPassword(User user);

    void updateUser(User user);

    List<String> selectLoginIds(User user);

    void updateLoginFailCount(User user);

    void updateUserLock(User user);

    boolean selectUserDuplicate(User user);
}
