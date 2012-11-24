package com.amazon.trans.dailywork.logpuller;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang.StringUtils;

public class Configuration {
    private static final Configuration INSTANCE = new Configuration();
    private CompositeConfiguration conf;

    private Configuration() {
        try {
            conf = new CompositeConfiguration();
            conf.addConfiguration(new SystemConfiguration());
            conf.addConfiguration(new PropertiesConfiguration("TransLogPuller.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public String getWindowsScpPath() {
        return conf.getString("windows.scp.path");
    }

    public String getWindowsSSHPath() {
        return conf.getString("windows.ssh.path");
    }

    public String[] getHosts(String envName, String realm, String domain) {
        String[] arr = new String[] { envName, realm, domain, "hosts" };
        String key = join(arr);
        return conf.getStringArray(key);
    }

    public String[] getLogs(String envName) {
        String dir = getLogsDir(envName);
        String key = join(new String[] { envName, "logs" });
        String[] arr = conf.getStringArray(key);
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].startsWith("/")) {
                arr[i] = dir + arr[i];
            }
        }
        return arr;
    }

    public String getLogsDir(String envName) {
        String dirKey = join(new String[] { envName, "logs.dir" });
        String dir = conf.getString(dirKey);
        dir = dir.endsWith("/") ? dir : (dir + "/");
        return dir;
    }

    public String getDefaultRealm() {
        return conf.getString("realm.default");
    }

    public String getDefaultDomain() {
        return conf.getString("domain.default");
    }

    public String get(String key) {
        return conf.getString(key);
    }

    private String join(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].toLowerCase();
        }
        return StringUtils.join(arr, ".");
    }
}
