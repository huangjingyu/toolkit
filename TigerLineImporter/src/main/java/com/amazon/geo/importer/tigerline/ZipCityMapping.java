package com.amazon.geo.importer.tigerline;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ZipCityMapping {
    private static final Pattern COMMA = Pattern.compile(",");
    private static ZipCityMapping INSTANCE = new ZipCityMapping();
    private Map map = new HashMap();

    private ZipCityMapping() {
        BufferedReader br = null;
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("zip_city_mapping.csv");
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = COMMA.split(line);
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

    public String getCity(String zip) {
        return (String) map.get(zip);
    }

    public static ZipCityMapping getInstance() {
        return INSTANCE;
    }
}
