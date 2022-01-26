package com.github.tzzzzzb;

import org.apache.commons.cli.*;

import java.io.IOException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args){
        Options options = new Options();
        options.addOption("i", true, "input DFS description file");
        options.addOption("r", true, "number of tests to run, default 100");
        options.addOption("t", false, "only show failed tests");
        options.addOption("q", false, "only show the result");
        try {
            String inputFile;
            int numOfTests;
            boolean onlyShowFailedTests;
            boolean onlyShowResult;
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            inputFile = cmd.getOptionValue('i');
            numOfTests = Integer.parseInt(cmd.getOptionValue('r', "100"));
            onlyShowFailedTests = cmd.hasOption('t');
            onlyShowResult = cmd.hasOption('q');
            if (inputFile == null) {
                throw new ParseException("");
            } else {
                DFA dfa = new DFA(inputFile);
                TestResult result = dfa.verify(numOfTests);
                if (!onlyShowResult) {
                    for (String testString : result.getTests().keySet()) {
                        boolean pass = result.getTests().get(testString);
                        if (!onlyShowFailedTests || !pass)
                        System.out.println(testString + "   " + (pass ? "pass" : "fail"));
                    }
                }
                System.out.println(inputFile + ": " + (result.getPass() ? "pass" : "fail"));
            }
        } catch (DFAStructureException | IOException e) {
            System.err.println(e.getMessage());
            exit(1);
        } catch (ParseException e) {
            new HelpFormatter().printHelp(
                    "java -jar DFA-Verfier.jar -i <input-file> [-q|-r|-t]"
                    , options);
            exit(1);
        }

    }
}
