package ru.javaops.topjava2.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.javaops.topjava2.HasId;
import ru.javaops.topjava2.error.IllegalRequestDataException;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {
    final LocalTime MAX_TIME_TO_VOTE_PREVIOUS_DAY = LocalTime.of(11, 0);
    final String MAX_TIME_TO_VOTE_PREVIOUS_DAY_AS_STRING = "11:00";

    public static void checkIsValidVoteTime() {
        LocalTime time = LocalTime.now();
        if (time.isAfter(MAX_TIME_TO_VOTE_PREVIOUS_DAY)) {
            throw new IllegalRequestDataException("Vote for current day has exist. Change is possible before " + MAX_TIME_TO_VOTE_PREVIOUS_DAY_AS_STRING);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}