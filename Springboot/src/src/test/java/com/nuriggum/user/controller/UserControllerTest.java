package com.nuriggum.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuriggum.nuriframe.common.util.AbstractRestDocs;
import com.nuriggum.user.model.User;
import com.nuriggum.user.model.UserManage;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest extends AbstractRestDocs {

    private String url = "/users";

    User getTestData(){
        User user = new User();
        user.setUserId("02DF6EDB21904330B9F826E02EEB5DD7");
        user.setLoginId("nuri");
        user.setPassword("1");
        user.setUserName("누리꿈");
        user.setEmployeeNumber("1234");
        user.setMobileNumber("010-0000-0000");
        user.setEmailAddress("dw.kang@nuriggum.com");
        user.setCompanyId(1);
        user.setDeptName("개발팀");
        user.setPositionName("프로");
        user.setUseYn(true);

        return user;
    }

    @Test
    @DisplayName("로그인 테스트")
    void loginTest() throws Exception {
        User user = getTestData();
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("loginId", user.getLoginId());
        loginMap.put("password", user.getPassword());
        loginMap.put("loginType", "admin"); //로그인 TYPE 설정 (admin, user)

        //api 테스트
        MvcResult result = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginMap)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        String loginId = (String) response.get("loginId");
        Assertions.assertThat(loginId).isEqualTo(user.getLoginId());
    }

    @Test
    @DisplayName("사용자 신규 등록 테스트")
    void userRegisterTest() throws Exception {
        User user = getTestData();
        user.setLoginId("admin");
        user.setPassword("nuriggum2024!");

        //api 테스트
        MvcResult result = this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("사용자 중복 체크 테스트")
    void userDuplicateTest() throws Exception {
        User user = getTestData();
        MvcResult result = this.mockMvc.perform(get(url + "/duplicate")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("loginId", user.getLoginId()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("사용자 목록 조회 테스트")
    void userListTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userName", "누리")
                        .param("page", "0")
                        .param("sort", "regist_date,DESC"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("사용자 정보 조회 테스트")
    void userDetailTest() throws Exception {
        String userId = "02DF6EDB21904330B9F826E02EEB5DD7";

        //api 테스트
        MvcResult result = this.mockMvc.perform(get(url + "/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePasswordTest() throws Exception {
        User user = getTestData();

        UserManage userManage = new UserManage();
        userManage.setUserId(user.getUserId());
        userManage.setLoginId(user.getLoginId());
        userManage.setPassword(user.getPassword());
        userManage.setNewPassword1("asdf3215!");
        userManage.setNewPassword2("asdf3215!");

        //api 테스트
        MvcResult result = this.mockMvc.perform(put(url + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManage)))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("사용자 정보 변경 테스트")
    void changeUserTest() throws Exception {
        User user = getTestData();
        user.setDeptName("지능개발팀");
        user.setMobileNumber("010-1234-5678");

        //api 테스트
        MvcResult result = this.mockMvc.perform(put(url + "/" + user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }

    @Test
    @DisplayName("아이디 찾기 테스트")
    void userFindIdTest() throws Exception {
        User user = new User();
        user.setUserName("누리꿈");
        user.setEmailAddress("dw.kang@nuriggum.com");


        MvcResult result = this.mockMvc.perform(get(url + "/find-id")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userName", user.getUserName())
                        .param("emailAddress", user.getEmailAddress()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }
}
