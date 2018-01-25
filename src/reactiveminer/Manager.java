package reactiveminer;

import reactiveminer.automata.*;
import reactiveminer.io.*;

/**
 * Class to manage and organize the run of automata over a Log/Trace
 */
public class Manager {
    /**
     * Run a set of separatedAutomata over a single trace
     *
     * @param tr       reader for a trace
     * @param automata set of separatedAutomata to test over the trace
     * @return ordered Array of supports for the trace for each automaton
     */
    public static double[] runTrace(TraceReader tr, SeparatedAutomatonRunner[] automata) {
        double[] results = new double[automata.length];
//        reset automata for a clean rin
        for (SeparatedAutomatonRunner automatonRunner : automata) {
            automatonRunner.reset();
        }

//        Step by step run of the automata
        for (EventReader eventReader : tr) {
            char transition = ((String) eventReader.getId()).charAt(0);
            for (SeparatedAutomatonRunner automatonRunner : automata) {
                automatonRunner.step(transition);
            }
        }

//        Retrieve result
        int i = 0;
        for (SeparatedAutomatonRunner automatonRunner : automata) {
            results[i] = automatonRunner.getSupport();
            i++;
        }

        return results;
    }

    /**
     * Run a set of separatedAutomata over a full Log
     *
     * @param lr       log reader
     * @param automata set of separatedAutomata to test over the log
     * @return ordered Array of supports for the full log for each automaton
     */
    public static double[] runLog(LogReader lr, SeparatedAutomatonRunner[] automata) {
        double[] finalResults = new double[automata.length];
        int numberOfTraces = 0;
        for (TraceReader tr : lr) {
            numberOfTraces++;
            double[] partialResults = runTrace(tr, automata);
            for (int i = 0; i < finalResults.length; i++) {
                finalResults[i] += partialResults[i];
            }
        }

        // Support of each constraint which respect to te log
        for (int i = 0; i < finalResults.length; i++) {
            finalResults[i] = finalResults[i] / numberOfTraces;
        }
        return finalResults;
    }
}
