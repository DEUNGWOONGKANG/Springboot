package com.nuriggum.user.controller;

import com.nuriggum.nuriframe.common.response.entity.CommonResult;
import com.nuriggum.nuriframe.common.response.service.ResponseService;
import com.nuriggum.user.model.User;
import com.nuriggum.user.model.UserManage;
import com.nuriggum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final ResponseService responseService;

	/*
	 * 신규 사용자 등록
	 */
	@PostMapping
	public CommonResult userRegister(@RequestBody User user) {
		return responseService.getSingleResult(userService.userRegister(user));
	}

	/*
	 * 사용자 ID 중복 체크
	 */
	@GetMapping("/duplicate")
	public CommonResult userDuplicate(User user) {
		return responseService.getSingleResult(userService.userDuplicate(user));
	}

	/*
	 * 사용자 목록 조회
	 */
	@GetMapping
	public CommonResult userList(User user
			,@PageableDefault(size = 10, sort = {"REGIST_DATE"}, direction = Sort.Direction.DESC) Pageable pageable) {
		return responseService.getSingleResult(userService.getUserList(user, pageable));
	}

	/*
	 * 사용자 정보 조회
	 */
	@GetMapping("/{userId}")
	public CommonResult userDetail(@PathVariable String userId) {
		return responseService.getSingleResult(userService.getUserDetail(userId));
	}

	/*
	 * 사용자 이름, 이메일로 로그인ID 조회
	 */
	@GetMapping("/find-id")
	public CommonResult userFindId(User user) {
		return responseService.getListResult(userService.getLoginIds(user));
	}

	/*
	 * 사용자 정보 변경
	 */
	@PutMapping("/{userId}")
	public CommonResult changeUser(@PathVariable String userId, @RequestBody User user) {
		return responseService.getSingleResult(userService.changeUser(userId, user));
	}

	/*
	 * 패스워드 변경
	 */
	@PutMapping("/password")
	public CommonResult changePassword(@RequestBody UserManage userManage) {
		return responseService.getSingleResult(userService.changePassword(userManage));
	}



}
