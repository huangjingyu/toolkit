package com.amazon.trans.dailywork.logpuller;

import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;

public class ProgramArgs {

    private String envName;
	private String domain;
	private String realm;
	private String logTime;
	private boolean currentHourAsLogTime;
	private Integer lastHours;
	private String since;
	private String until;
	private String[] logFiles;
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

	public Integer getLastHours() {
		return lastHours;
	}

	public void setLastHours(Integer lastHours) {
		this.lastHours = lastHours;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

	public boolean isCurrentHourAsLogTime() {
		return currentHourAsLogTime;
	}

	public void setCurrentHourAsLogTime(boolean current) {
		this.currentHourAsLogTime = current;
	}

	public String[] getLogFiles() {
		return logFiles;
	}

	public void setLogFiles(String[] logFiles) {
		this.logFiles = logFiles;
	}

	public String getDestDirPath() {
		return destDirPath;
	}

	public void setDestDirPath(String destDirPath) {
		this.destDirPath = destDirPath;
	}

	private static final Pattern COMMA = Pattern.compile(",");

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
		if (cmdLine.hasOption("current")) {
			t.setCurrentHourAsLogTime(true);
		}
		if (cmdLine.hasOption("last")) {
			t.setLastHours(Integer.valueOf(cmdLine.getOptionValue("last")));
		}
		if (cmdLine.hasOption("since")) {
			t.setSince(cmdLine.getOptionValue("since"));
		}
		if (cmdLine.hasOption("until")) {
			t.setUntil(cmdLine.getOptionValue("until"));
		}
		if (cmdLine.hasOption("logFiles")) {
			t.setLogFiles(COMMA.split(cmdLine.getOptionValue("logFiles")));
		}
		if (cmdLine.hasOption("destDir")) {
			t.setDestDirPath(cmdLine.getOptionValue("destDir"));
		}
		if (StringUtils.isEmpty(t.getLogTime())
				&& StringUtils.isEmpty(t.getSince())
				&& StringUtils.isEmpty(t.getUntil())) {
			t.setCurrentHourAsLogTime(true);
		}
		return t;
	}

	@SuppressWarnings("static-access")
	public static Options supportOptions() {
		Options options = new Options();
		Option envName = OptionBuilder.withArgName("envName").hasArg()
				.withDescription("the environment name to pull log from")
				.withLongOpt("envName").create("e");
		Option domain = OptionBuilder.withArgName("domain").hasArg()
				.withDescription("the domain, e.g. prod,gamma,beta")
				.withLongOpt("domain").create();
		Option realm = OptionBuilder.withArgName("realm").hasArg()
				.withDescription("the realm, e.g. CNAmazon, GBAmazon")
				.withLongOpt("realm").create();
		Option logTime = OptionBuilder.withArgName("logTime").hasArg()
				.withDescription("the time at which you want to pull the log")
				.withLongOpt("logTime").create("t");
		Option currentLogTime = OptionBuilder.withArgName("currentLogTime")
				.hasArg(false).withDescription("get log in current hour")
				.withLongOpt("current").create("c");
		Option last = OptionBuilder.withArgName("lastHours").hasArg()
				.withArgName("pull logs of last n hours").withLongOpt("last")
				.create();
		Option logFiles = OptionBuilder.withArgName("logFiles").hasArg()
				.withDescription("log files that you want to pull")
				.withLongOpt("logFiles").create("f");
		Option since = OptionBuilder.withArgName("since").hasArg()
				.withDescription("pull log files since this time, inclusive")
				.withLongOpt("since").create();
		Option until = OptionBuilder.withArgName("until").hasArg()
				.withDescription("pull log files until this time, inclusive")
				.withLongOpt("until").create();
		Option destDir = OptionBuilder.withArgName("destDir").hasArg()
				.withArgName("the dest dir").withLongOpt("destDir").create("d");

		options.addOption(envName);
		options.addOption(domain);
		options.addOption(realm);
		options.addOption(logTime);
		options.addOption(currentLogTime);
		options.addOption(last);
		options.addOption(since);
		options.addOption(until);
		options.addOption(logFiles);
		options.addOption(destDir);
		return options;
	}
}
