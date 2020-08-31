package ru.graduate.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static ru.graduate.web.UserProfileController.REST_URL;
import static ru.graduate.TestUtil.userHttpBasic;
import static ru.graduate.UserTestData.*;

class UserProfileControllerTest extends AbstractControllerTest{

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() throws Exception{
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER));
    }
}