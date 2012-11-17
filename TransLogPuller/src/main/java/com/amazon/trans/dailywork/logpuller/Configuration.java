package com.amazon.trans.dailywork.logpuller;

import java.util.regex.Pattern;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

public class Configuration {
    private static final Configuration INSTANCE = new Configuration();
    private static final Pattern COMMA = Pattern.compile(",");
    private PropertiesConfiguration conf;

    private Configuration() {
        try {
            conf = new PropertiesConfiguration();
            conf.load("TransLogPuller.properties");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public String[] getHosts(String envName, String realm, String domain) {
        String[] arr = new String[] { envName, realm, domain, "hosts" };
        String key = join(arr);
        String hostsStr = conf.getString(key);
        return COMMA.split(hostsStr);
    }

    public String[] getLogs(String envName) {
        String key = join(new String[] { envName, "logs" });
        String logsStr = conf.getString(key);
        return COMMA.split(logsStr);
    }

    private String join(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].toLowerCase();
        }
        return StringUtils.join(arr, ".");
    }
}
