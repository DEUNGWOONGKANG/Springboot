package com.nuriggum.menu.mapper;

import com.nuriggum.menu.model.Menu;
import com.nuriggum.nuriframe.common.response.data.RequestData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    int insertMenu(Menu menu);

    int selectMenuListCount(Menu menu);

    List<Menu> selectMenuList(RequestData<?> requestData);
}
