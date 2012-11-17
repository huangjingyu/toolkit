package com.amazon.trans.dailywork.logpuller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class ProgramArgs {

    private String envName;
    private String domain;
    private String realm;
    private String logTime;
    private String destDirPath;

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getDestDirPath() {
        return destDirPath;
    }

    public void setDestDirPath(String destDirPath) {
        this.destDirPath = destDirPath;
    }

    public static ProgramArgs parse(CommandLine cmdLine) {
        ProgramArgs t = new ProgramArgs();
        if (cmdLine.hasOption("envName")) {
            t.setEnvName(cmdLine.getOptionValue("envName"));
        }
        if (cmdLine.hasOption("domain")) {
            t.setDomain(cmdLine.getOptionValue("domain"));
        }
        if (cmdLine.hasOption("realm")) {
            t.setRealm(cmdLine.getOptionValue("realm"));
        }
        if (cmdLine.hasOption("logTime")) {
            t.setLogTime(cmdLine.getOptionValue("logTime"));
        }
        return t;
    }

    @SuppressWarnings("static-access")
    public static Options supportOptions() {
        Options options = new Options();
        Option envName = OptionBuilder.withArgName("envName").hasArg()
                .withDescription("the environment name to pull log from").withLongOpt("envName").create("e");
        Option domain = OptionBuilder.withArgName("domain").hasArg()
                .withDescription("the domain, e.g. prod,gamma,beta").withLongOpt("domain").create();
        Option realm = OptionBuilder.withArgName("realm").hasArg()
                .withDescription("the realm, e.g. CNAmazon, GBAmazon").withLongOpt("realm").create();
        Option logTime = OptionBuilder.withArgName("logTime").hasArg()
                .withDescription("the time at which you want to pull the log").withLongOpt("logTime").create("t");
        Option destDir = OptionBuilder.withArgName("destDir").hasArg().withArgName("the dest dir")
                .withLongOpt("destDir").create();

        options.addOption(envName);
        options.addOption(domain);
        options.addOption(realm);
        options.addOption(logTime);
        options.addOption(destDir);
        return options;
    }
}
