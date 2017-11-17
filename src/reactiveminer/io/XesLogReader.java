package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.in.*;
import org.deckfour.xes.model.*;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

/**
 * Class to read a XES log through LogReader Interface
 */
public class XesLogReader implements LogReader {

    private XLog log;
    private int index;
    private List<XEventClassifier> logClassifiers;

    /**
     * @param log XES log object (i.e. list of XES traces)
     */
    public XesLogReader(XLog log) {
        this.log = log;
        index = 0;
        this.logClassifiers = log.getClassifiers();
    }

    /**
     * @return if there are more trace in the log
     */
    @Override
    public boolean hasNext() {
        return index < (log.size() );
    }

    /**
     * @return Next Trace of the log
     */
    @Override
    public XTrace next() {
        return log.get(index++);
    }

    /**
     * @return List of traces in the log
     */
    @Override
    public List<XTrace> getTracesList() {
        return log;
    }

    /**
     * @param traceIndex Index of the desired trace
     * @return TraceReader of the specified trace
     */
    @Override
    public TraceReader getTraceReader(int traceIndex) {
        XesTraceReader res = new XesTraceReader(log.get(traceIndex), logClassifiers);
        return res;
    }

    /**
     * @return TraceReader of the current trace
     */
    @Override
    public TraceReader getCurrentTraceReader() {
        return (new XesTraceReader(log.get(index), logClassifiers));
    }

    /**
     * @return List of XES attributes for event classification of this log
     */
    public List<XEventClassifier> getLogClassifiers() {
        return logClassifiers;
    }
}
