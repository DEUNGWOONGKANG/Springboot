package com.nuriggum.menu.service;

import com.nuriggum.menu.mapper.MenuMapper;
import com.nuriggum.menu.model.Menu;
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
public class MenuService {

    private final MenuMapper menuMapper;
    private final MessageUtil messageUtil;

    public int menuRegister(Menu menu) {
        //중복 코드 체크
        if(menuDuplicate(menu)) throw new DuplicateKeyException(messageUtil.getMessage(MessageKey.DUPLICATE_ID,null));

        menu.setRegistUserId(NuriSecurityUtil.getLoginUserId());
        menu.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        return menuMapper.insertMenu(menu);
    }

    public boolean menuDuplicate(Menu menu) {
        return menuMapper.selectMenuDuplicate(menu);
    }

    public Page<Menu> getMenuList(Menu menu, Pageable pageable) {
        if(menu == null) menu = new Menu();
        //검색 객체 생성
        RequestData<?> requestData = RequestData.builder().data(menu).pageable(pageable).build();
        //코드 그룹 목록 조회
        List<Menu> menuList = menuMapper.selectMenuList(requestData);
        //코드 그룹 카운트 조회
        int count = menuMapper.selectMenuListCount(menu);

        return new PageImpl<>(menuList, pageable, count);
    }
}
