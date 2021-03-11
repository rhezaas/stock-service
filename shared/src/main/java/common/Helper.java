package common;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Helper {
    public static <T> int findLatestIndex(List<T> datas, java.util.function.Function<T, Boolean> data) {
        for (int i = datas.size() - 1; i >= 0; i--) {
            if(data.apply(datas.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String timeToString(LocalTime time, String format) {
        return DateTimeFormatter.ofPattern(format).format(time);
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
