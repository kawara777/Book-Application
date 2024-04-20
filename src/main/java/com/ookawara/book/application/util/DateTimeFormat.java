package com.ookawara.book.application.util;

import com.ookawara.book.application.exception.DateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeFormat {

    private String format;

    public DateTimeFormat(String format) {
        this.format = format;
    }

    public LocalDate getFormat() {
        if (format != null) {
            try {
                return LocalDate.parse(format, DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException e) {
                throw new DateFormatException("yyyy/MM/dd の形式（例：2000/01/01）で存在する日付を入力してください。");
            }
        } else {
            return null;
        }
    }
}
