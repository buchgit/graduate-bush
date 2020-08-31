package ru.graduate;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import ru.graduate.model.User;
import ru.graduate.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;

public class TestUtil {
    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }
}
