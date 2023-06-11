package com.one.alura.foro.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static String formatDateTime(LocalDateTime fechaCreacion) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaCreacion.format(formato);
    }
}
