package ru.graduate;

import ru.graduate.model.Role;
import ru.graduate.model.User;

import static ru.graduate.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "user", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class, "enabled","registered", "votes", "password","roles");

}
