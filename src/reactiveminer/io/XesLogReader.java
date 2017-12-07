package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to read a XES log through LogReader Interface
 */
public class XesLogReader implements LogReader {

    private XLog log;
    private List<XEventClassifier> logClassifiers;

    /**
     * @param log XES log object (i.e. list of XES traces)
     */
    public XesLogReader(XLog log) {
        this.log = log;
        this.logClassifiers = log.getClassifiers();
    }

    /**
     * @return List of traces in the log
     */
    @Override
    public List<XTrace> getTracesList() {
        return log;
    }

    /**
     * Get a reader for a specific trace present in the log
     *
     * @param traceIndex Index of the desired trace
     * @return TraceReader of the specified trace
     */
    @Override
    public TraceReader getTraceReader(int traceIndex) {
        return new XesTraceReader(log.get(traceIndex), logClassifiers);
    }

    /**
     * @return List of XES attributes for event classification of this log
     */
    public List<XEventClassifier> getLogClassifiers() {
        return logClassifiers;
    }

    /**
     * @return Iterator of XesTraceReaders for the traces present in the log
     */
    @Override
    public Iterator<TraceReader> iterator() {
        ArrayList<TraceReader> res = new ArrayList<>();
        for (XTrace trace : log) {
            res.add(new XesTraceReader(trace, logClassifiers));
        }
        return res.iterator();
    }


}
