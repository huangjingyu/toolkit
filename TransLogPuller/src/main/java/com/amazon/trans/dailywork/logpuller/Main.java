package com.amazon.trans.dailywork.logpuller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new PosixParser();
        Options options = ProgramArgs.supportOptions();
        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse(options, args);
        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("process_update.sh", options);
            return;
        }
        ProgramArgs argsObj = ProgramArgs.parse(cmdLine);
    }
}
