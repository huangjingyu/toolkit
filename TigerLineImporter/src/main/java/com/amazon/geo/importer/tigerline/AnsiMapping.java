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
    private Map ansiStateMap = new HashMap();

    private AnsiMapping() {
        loadAnsiStateMapping();
    }

    private void loadAnsiStateMapping() {
        BufferedReader br = null;
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("ansi_state_mapping.csv");
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = COMMA.split(line);
                ansiStateMap.put(arr[0], arr[1]);
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
        return (String) ansiStateMap.get(ansiCode);
    }

    public static AnsiMapping getInstance() {
        return INSTANCE;
    }
}
