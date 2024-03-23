package project_java_group_5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateValidator {
    private DateTimeFormatter dateFormatter;

    public DateValidator(String dateFormat) {
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
    }

    public boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
