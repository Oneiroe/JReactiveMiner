package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;

import java.util.List;

/**
 * Class to read a XES trace through TraceReader Interface
 */
public class XesTraceReader implements TraceReader {
    private final XTrace trace;
    private int index;
    private List<XEventClassifier> logClassifiers;

    public XesTraceReader(XTrace trace, List<XEventClassifier> logClassifiers) {
        this.trace = trace;
        this.logClassifiers = logClassifiers;
        index = 0;
    }


    /**
     * @return if there are more event in the trace
     */
    @Override
    public boolean hasNext() {
        return index < (trace.size());
    }

    /**
     * @return next event in the trace
     */
    @Override
    public XEvent next() {
        XEvent res = trace.get(index);
        index += 1;
        return res;
    }

    /**
     * @return the list of traces of the RMLog
     */
    @Override
    public List<XEvent> getEventsList() {
        return trace;
    }

    /**
     * @param traceIndex Index of the desired trace
     * @return Object to read the selected event
     */
    @Override
    public EventReader getEventReader(int traceIndex) {
        return (new XesEventReader(trace.get(traceIndex), logClassifiers));
    }

    /**
     * @return Object to read the current event
     */
    @Override
    public EventReader getCurrentEventReader() {
        return (new XesEventReader(trace.get(index), logClassifiers));
    }
}
