package com.snk.fundamentus.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ReportUtil {

    private static final String DD_MM_YYYY = "dd/MM/yyyy";

    public static double somaTodosValores(double... valores) {
        double soma = 0;

        for (double d : valores) {
            soma += d;
        }

        return soma;

    }

    public static Calendar getCalendarFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        return sdf.format(date.getTime());
    }

    public static Date formatString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        return sdf.parse(date);
    }

    public static Calendar getUltimoTrimestre(Calendar date) {

        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        float quarter = month / 3F;

        Calendar trimestre;
        if (quarter <= 1.0) {
            trimestre = Calendar.getInstance();
            trimestre.set(year, 11, 31);
            trimestre.add(Calendar.YEAR, -1);
        }
        else if (quarter <= 2.0) {
            trimestre = Calendar.getInstance();
            trimestre.set(year, 2, 31);
        }
        else if (quarter <= 3.0) {
            trimestre = Calendar.getInstance();
            trimestre.set(year, 5, 30);
        }
        else {
            trimestre = Calendar.getInstance();
            trimestre.set(year, 8, 30);
        }

        return trimestre;
    }

}
