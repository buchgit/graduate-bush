package ru.graduate;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.graduate.model.Role;
import ru.graduate.model.User;
import ru.graduate.web.json.JsonUtil;

import java.util.List;

import static ru.graduate.TestUtil.readFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduate.model.AbstractBaseEntity.START_SEQ;
import static ru.graduate.TestUtil.readListFromJsonMvcResult;

public class UserTestData {

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "user", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User UPDATED = new User(ADMIN_ID, "Admin", "admin@gmail.com", "newPass", Role.ADMIN, Role.USER);
    public static User NEW_USER = new User(null, "NEW", "new@gmail.com", "newPass", Role.USER);

    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class, "enabled", "registered", "votes", "password", "roles");

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
