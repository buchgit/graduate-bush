package ru.graduate.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import ru.graduate.repository.DishRepository;
import ru.graduate.repository.MenuRepository;
import ru.graduate.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static ru.graduate.web.UserProfileController.REST_URL;
import static ru.graduate.TestUtil.userHttpBasic;
import static ru.graduate.UserTestData.*;

class UserProfileControllerTest extends AbstractControllerTest{

    @Autowired
    private UserService userService;

//    @Test
//    void testGetUnAuth() throws Exception {
//        mockMvc.perform(get("http://localhost:8080"+REST_URL))
//                .andDo(print());
//                //.andExpect(status().isUnauthorized());
//    }

//    @Test
//    void create() {
//    }
//
//    @Test
//    void update() {
//    }
//
//        @Test
//        void delete() throws Exception {
//            perform(MockMvcRequestBuilders.delete(REST_URL)
//                    .with(userHttpBasic(USER)))
//                    .andExpect(status().isNoContent());
//            //USER_MATCHER.assertMatch(userService.getAll(), ADMIN);
//        }
//
//    @Test
//    void get() throws Exception{
//        perform(MockMvcRequestBuilders.get(REST_URL)
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isOk());
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(USER_MATCHER.contentJson(USER));
 //   }
}