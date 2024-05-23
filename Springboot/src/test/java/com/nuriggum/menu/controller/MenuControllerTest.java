package com.nuriggum.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuriggum.code.model.Code;
import com.nuriggum.code.model.CodeGroup;
import com.nuriggum.menu.model.Menu;
import com.nuriggum.nuriframe.common.util.AbstractRestDocs;
import com.nuriggum.nuriframe.security.model.WithMockUser;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MenuControllerTest extends AbstractRestDocs {

    private String url = "/menus";

    Menu get1stMenuData(){

        Menu menu = new Menu();
        menu.setMenuId("0001");
        menu.setMenuName("시스템");
        menu.setSortNumber(1);
        menu.setDescription("시스템");
        menu.setCompanyId(1);
        menu.setUseYn(true);
        menu.setSystemType("ADMIN");
        menu.setLanguage("ko");

//        menu = new Menu();
//        menu.setMenuId("0002");
//        menu.setMenuName("사용자");
//        menu.setSortNumber(2);
//        menu.setDescription("사용자");
//        menu.setCompanyId(1);
//        menu.setUseYn(true);
//        menu.setSystemType("ADMIN");
//        menu.setLanguage("ko");
//        menuList.add(menu);
//
//        menu = new Menu();
//        menu.setMenuId("0003");
//        menu.setMenuName("사용자지원");
//        menu.setSortNumber(3);
//        menu.setDescription("사용자지원");
//        menu.setCompanyId(1);
//        menu.setUseYn(true);
//        menu.setSystemType("ADMIN");
//        menu.setLanguage("ko");
//        menuList.add(menu);
        return menu;
    }

    @Test
    @DisplayName("메뉴 등록 테스트")
    @WithMockUser
    void registerTest() throws Exception {
        Menu menu = get1stMenuData();

        //api 테스트
        MvcResult result = this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(menu)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());

        boolean success = (boolean) response.get("success");
        int code = (int) response.get("code");
        String msg = (String) response.get("msg");

        Assertions.assertThat(success).isEqualTo(true);
        Assertions.assertThat(code).isEqualTo(200);
    }
//
//    @Test
//    @DisplayName("코드 그룹 중복체크 테스트")
//    void codeGroupDuplicateTest() throws Exception {
//        CodeGroup codeGroup = getTestGroupData();
//        MvcResult result = this.mockMvc.perform(get(groupUrl + "/duplicate")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("groupId", codeGroup.getGroupName()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//
//        boolean success = (boolean) response.get("success");
//        int code = (int) response.get("code");
//        String msg = (String) response.get("msg");
//
//        Assertions.assertThat(success).isEqualTo(true);
//        Assertions.assertThat(code).isEqualTo(200);
//    }
//
//    @Test
//    @DisplayName("코드 그룹 목록 조회 테스트")
//    void codeGroupListTest() throws Exception {
//        MvcResult result = this.mockMvc.perform(get(groupUrl)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("page", "0")
//                        .param("sort", "regist_date,DESC"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//
//        boolean success = (boolean) response.get("success");
//        int code = (int) response.get("code");
//        String msg = (String) response.get("msg");
//
//        Assertions.assertThat(success).isEqualTo(true);
//        Assertions.assertThat(code).isEqualTo(200);
//    }
//
//    @Test
//    @DisplayName("코드 등록 테스트")
//    void codeRegisterTest() throws Exception {
//        Code code = getTestCodeData();
//
//        //api 테스트
//        MvcResult result = this.mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(code)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//
//        boolean success = (boolean) response.get("success");
//        int responseCode = (int) response.get("code");
//
//        Assertions.assertThat(success).isEqualTo(true);
//        Assertions.assertThat(responseCode).isEqualTo(200);
//    }
//
//    @Test
//    @DisplayName("코드 중복체크 테스트")
//    void codeDuplicateTest() throws Exception {
//        Code code = getTestCodeData();
//        MvcResult result = this.mockMvc.perform(get(url + "/duplicate")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("groupId", code.getGroupId())
//                        .param("code", code.getCode()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//
//        boolean success = (boolean) response.get("success");
//        int responseCode = (int) response.get("code");
//
//        Assertions.assertThat(success).isEqualTo(true);
//        Assertions.assertThat(responseCode).isEqualTo(200);
//    }
//
//    @Test
//    @DisplayName("코드 목록 조회 테스트")
//    void codeListTest() throws Exception {
//        Code code = getTestCodeData();
//
//        MvcResult result = this.mockMvc.perform(get(url)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("groupId", code.getGroupId())
//                        .param("page", "0")
//                        .param("sort", "sort_number,ASC"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//
//        boolean success = (boolean) response.get("success");
//        int responseCode = (int) response.get("code");
//
//        Assertions.assertThat(success).isEqualTo(true);
//        Assertions.assertThat(responseCode).isEqualTo(200);
//    }
}
