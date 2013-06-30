package com.amazon.transportation.shipment.analyze;

import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    private static Map<String, String> MONTH_MAP = new HashMap<String, String>();
    static {
        MONTH_MAP.put("JAN", "01");
        MONTH_MAP.put("FEB", "02");
        MONTH_MAP.put("MAR", "03");
        MONTH_MAP.put("APR", "04");
        MONTH_MAP.put("MAY", "05");
        MONTH_MAP.put("JUN", "06");
        MONTH_MAP.put("JUL", "07");
        MONTH_MAP.put("AUG", "08");
        MONTH_MAP.put("SEP", "09");
        MONTH_MAP.put("OCT", "10");
        MONTH_MAP.put("NOV", "11");
        MONTH_MAP.put("DEC", "12");
    }

    public static String normalizeToMonth(String dateStr) {
        return new StringBuilder().append("20").append(dateStr.substring(7)).append(MONTH_MAP.get(dateStr.substring(3, 6))).toString();
    }
}
