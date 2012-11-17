package com.amazon.trans.dailywork.logpuller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;

public class Util {
    private static final String LINUX_GET_REMOTE_DATE_CMD = "ssh -o StrictHostKeyChecking=no <hostName> \"date -u \"+%Y-%m-%d-%H\"\"";
    private static final String WIN_GET_REMOTE_DATE_CMD = "cmd.exe /c echo y | <sshPath> <hostName> \"date -u \"+%Y-%m-%d-%H\"\"";
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    private static final DateFormat LOG_TIME_DF = new SimpleDateFormat("yyyy-MM-dd-HH");
    static {
        LOG_TIME_DF.setTimeZone(UTC);
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("win") > -1;
    }

    public static String getCurrentLogTime(String hostName) throws Exception {
        ST st = new ST(LINUX_GET_REMOTE_DATE_CMD);
        if (Util.isWindows()) {
            st = new ST(WIN_GET_REMOTE_DATE_CMD);
            String sshPath = Configuration.getInstance().getWindowsSSHPath();
            if (!sshPath.startsWith("\"")) {
                sshPath = "\"" + sshPath + "\"";
            }
            st.add("sshPath", sshPath);
        }
        st.add("hostName", hostName);
        String cmd = st.render();
        Executor executor = new DefaultExecutor();
        GetCurrentLogTimeStreamHandler streamHandler = new GetCurrentLogTimeStreamHandler();
        executor.setStreamHandler(streamHandler);
        executor.execute(CommandLine.parse(cmd));
        String logTime = streamHandler.getLogTime();
        if (StringUtils.isEmpty(logTime)) {
            throw new Exception("fail to get current log time from host " + hostName);
        }
        return logTime;
    }

    public static String[] getLogTimes(String since, String until) throws ParseException {
        Date sd = LOG_TIME_DF.parse(since);
        Calendar sc = Calendar.getInstance(UTC);
        sc.setTime(sd);

        Date ud = LOG_TIME_DF.parse(until);
        Calendar uc = Calendar.getInstance(UTC);
        uc.setTime(ud);

        int diffHours = (int) ((uc.getTimeInMillis() - sc.getTimeInMillis()) / (60 * 60 * 1000));
        if (diffHours < 0) {
            throw new RuntimeException("since greater than until");
        }
        String[] logTimes = new String[diffHours + 1];
        for (int i = 0; i <= diffHours; i++) {
            logTimes[i] = cal2logTime(sc);
            sc.add(Calendar.HOUR, 1);
        }
        return logTimes;
    }

    public static String[] getLogTimes(String logTime, Integer lastHours) throws ParseException {
        Date date = LOG_TIME_DF.parse(logTime);
        Calendar cal = Calendar.getInstance(UTC);
        cal.setTime(date);
        String[] logTimes = new String[lastHours.intValue() + 1];
        for (int i = 0; i <= lastHours; i++) {
            logTimes[i] = cal2logTime(cal);
            cal.add(Calendar.HOUR, -1);
        }
        return logTimes;
    }

    private static String cal2logTime(Calendar cal) {
        StringBuilder sb = new StringBuilder();
        sb.append(cal.get(Calendar.YEAR));
        sb.append("-");
        int month = cal.get(Calendar.MONTH) + 1;
        if (month < 10) {
            sb.append("0");
        }
        sb.append(month);
        sb.append("-");
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            sb.append("0");
        }
        sb.append(day);
        sb.append("-");
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        return sb.toString();
    }

    private static class GetCurrentLogTimeStreamHandler implements ExecuteStreamHandler {
        private String logTime = "";
        private InputStream out;
        private InputStream err;
        private static final Pattern PAT = Pattern.compile("\\d+-\\d+-\\d+-\\d+");

        @Override
        public void setProcessInputStream(OutputStream os) throws IOException {
        }

        @Override
        public void setProcessErrorStream(InputStream is) throws IOException {
            this.err = is;
        }

        @Override
        public void setProcessOutputStream(InputStream is) throws IOException {
            this.out = is;
        }

        @Override
        public void start() throws IOException {
            List<String> list = IOUtils.readLines(out);
            if (list != null && list.size() > 0) {
                for (String s : list) {
                    if (PAT.matcher(s).matches()) {
                        logTime = s;
                        break;
                    }
                }
            }
        }

        @Override
        public void stop() {
        }

        public String getLogTime() {
            return logTime;
        }
    }
}
