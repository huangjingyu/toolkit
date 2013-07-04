package com.amazon.geo.importer.tigerline;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AnsiMapping {
    private static final Pattern COMMA = Pattern.compile(",");
    private static AnsiMapping INSTANCE = new AnsiMapping();
    private Map<String, String> ansiStateMap = new HashMap<String, String>();
    private Map<String, String> ansiCountyMap = new HashMap<String, String>();

    private AnsiMapping() {
        loadMapping("ansi_state_mapping.csv", ansiStateMap);
        loadMapping("ansi_county_mapping.csv", ansiCountyMap);
    }

    private void loadMapping(String resource, Map<String, String> map) {
        BufferedReader br = null;
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = COMMA.split(line);
                if (arr.length != 2) {
                    throw new RuntimeException("invalid data: " + line);
                }
                map.put(arr[0], arr[1]);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Throwable t) {
            }
        }
    }

    public String getState(String ansiCode) {
        return ansiStateMap.get(ansiCode);
    }

    public String getCounty(String ansiCode) {
        return ansiCountyMap.get(ansiCode);
    }

    public static AnsiMapping getInstance() {
        return INSTANCE;
    }
}
