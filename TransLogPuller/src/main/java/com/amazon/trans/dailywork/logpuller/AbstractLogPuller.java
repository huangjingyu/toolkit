package com.amazon.trans.dailywork.logpuller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractLogPuller implements LogPuller {

    @Override
    public void pull(ProgramArgs args) throws Exception {
        String envName = args.getEnvName();
        String realm = args.getRealm();
        String domain = args.getDomain();
        String logTime = args.getLogTime();
        boolean current = args.isCurrentHourAsLogTime();
        Integer lastHours = args.getLastHours();
        String since = args.getSince();
        String until = args.getUntil();
        String[] logFiles = args.getLogFiles();
        String destDirPath = args.getDestDirPath();

        if (StringUtils.isEmpty(realm)) {
            realm = Configuration.getInstance().getDefaultRealm();
        }

        if (StringUtils.isEmpty(domain)) {
            domain = Configuration.getInstance().getDefaultDomain();
        }

        String[] hosts = Configuration.getInstance().getHosts(envName, realm, domain);
        if (ArrayUtils.isEmpty(hosts)) {
            throw new Exception("no hosts specified");
        }

        String logSuffixFormat = Configuration.getInstance().getLogsSuffixFormat(envName);

        if (current) {
            logTime = Util.getCurrentLogTime(hosts[0], logSuffixFormat);
        }
        String[] logTimes = null;
        if (StringUtils.isEmpty(logTime)) {
            if (StringUtils.isEmpty(since)) {
                throw new Exception("can't calculate logTime");
            }
            if (StringUtils.isEmpty(until)) {
                until = Util.getCurrentLogTime(hosts[0], logSuffixFormat);
            }
            logTimes = Util.getLogTimes(since, until, logSuffixFormat);
        } else if (lastHours == null) {
            logTimes = new String[] { logTime };
        } else {
            logTimes = Util.getLogTimes(logTime, lastHours, logSuffixFormat);
        }

        String[] logs = null;
        if (logFiles == null || logFiles.length == 0) {
            logs = Configuration.getInstance().getLogs(envName);
        } else {
            String logsDir = Configuration.getInstance().getLogsDir(envName);
            for (int i = 0; i < logFiles.length; i++) {
                if (!logFiles[i].startsWith("/")) {
                    logFiles[i] = logsDir + logFiles[i];
                }
            }
            logs = logFiles;
        }

        for (String host : hosts) {
            for (String logPrefix : logs) {
                for (String time : logTimes) {
                    String logFullName = logPrefix + "." + time;
                    pull(host, logFullName, destDirPath);
                }
            }
        }
    }

    public abstract void pull(String hostName, String remoteFilePath, String localDestDirPath) throws Exception;

}
