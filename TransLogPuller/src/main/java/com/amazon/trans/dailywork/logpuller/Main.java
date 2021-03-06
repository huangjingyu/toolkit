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
        if (args == null || args.length == 0) {
            printHelp(options);
            return;
        }
        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse(options, args);
        } catch (Exception e) {
            printHelp(options);
            return;
        }
        ProgramArgs argsObj = ProgramArgs.parse(cmdLine);
        LogPuller puller = new DefaultLogPuller();
        puller.pull(argsObj);
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("trans_log_puller.sh", options);
    }
}
