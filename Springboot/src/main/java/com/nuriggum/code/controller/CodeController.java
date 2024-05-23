package com.nuriggum.code.controller;

import com.nuriggum.code.model.Code;
import com.nuriggum.code.model.CodeGroup;
import com.nuriggum.code.service.CodeGroupService;
import com.nuriggum.code.service.CodeService;
import com.nuriggum.nuriframe.common.response.entity.CommonResult;
import com.nuriggum.nuriframe.common.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/codes")
public class CodeController {
    private final CodeService codeService;
    private final CodeGroupService codeGroupService;
    private final ResponseService responseService;

    /*
     * 코드 그룹 목록 조회
     */
    @GetMapping("/group")
    public CommonResult codeGroupList(CodeGroup codeGroup
            ,@PageableDefault(size = 10, sort = {"REGIST_DATE"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return responseService.getSingleResult(codeGroupService.getCodeGroupList(codeGroup, pageable));
    }

    /*
     * 신규 코드 그룹 생성
     */
    @PostMapping("/group")
    public CommonResult codeGroupRegister(@RequestBody CodeGroup codeGroup) {
        return responseService.getSingleResult(codeGroupService.codeGroupRegister(codeGroup));
    }

    /*
     * 코드 그룹 중복 체크
     */
    @GetMapping("/group/duplicate")
    public CommonResult codeGroupDuplicate(CodeGroup codeGroup) {
        return responseService.getSingleResult(codeGroupService.codeGroupDuplicate(codeGroup));
    }

    /*
     * 신규 코드 생성
     */
    @PostMapping
    public CommonResult codeRegister(@RequestBody Code code) {
        return responseService.getSingleResult(codeService.codeRegister(code));
    }

    /*
     * 코드 중복 체크
     */
    @GetMapping("/duplicate")
    public CommonResult codeDuplicate(Code code) {
        return responseService.getSingleResult(codeService.codeDuplicate(code));
    }

    /*
     * 코드 목록 조회
     */
    @GetMapping
    public CommonResult codeList(Code code
            ,@PageableDefault(size = 10, sort = {"SORT_NUMBER"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return responseService.getSingleResult(codeService.getCodeList(code, pageable));
    }

}
