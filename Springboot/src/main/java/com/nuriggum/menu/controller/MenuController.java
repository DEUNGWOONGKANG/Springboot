package com.nuriggum.menu.controller;

import com.nuriggum.menu.model.Menu;
import com.nuriggum.menu.service.MenuService;
import com.nuriggum.nuriframe.common.response.entity.CommonResult;
import com.nuriggum.nuriframe.common.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;
    private final ResponseService responseService;

    /*
     * 신규 메뉴 생성
     */
    @PostMapping
    public CommonResult register(@RequestBody Menu menu) {
        return responseService.getSingleResult(menuService.register(menu));
    }

    /*
     * 메뉴 ID 중복 체크
     */
    @GetMapping("/duplicate")
    public CommonResult duplicate(Menu menu) {
        return responseService.getSingleResult(menuService.duplicate(menu));
    }

    /*
     * 메뉴 목록 조회
     */
    @GetMapping
    public CommonResult list(Menu menu
            ,@PageableDefault(size = 10, sort = {"REGIST_DATE"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return responseService.getSingleResult(menuService.getMenuList(menu, pageable));
    }

}
