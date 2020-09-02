package ru.graduate.utils;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.graduate.utils.exceptions.NotFoundException;

import java.time.LocalTime;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static ResponseEntity<String> getStringResponseEntity(BindingResult result, Logger logger) {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().stream().map(e -> sb.append(e.getField()).append(" ").append(e.getDefaultMessage()).append("<br>"));
        logger.info("binding error: {} ", sb.toString());
        return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static boolean timeIsOver(LocalTime localTime) {
        return localTime.isAfter(LocalTime.of(11, 00, 00));
    }

}
