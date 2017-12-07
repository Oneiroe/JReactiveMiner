package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to read a XES trace through TraceReader Interface
 */
public class XesTraceReader implements TraceReader {
    private final XTrace trace;
    private List<XEventClassifier> logClassifiers;

    public XesTraceReader(XTrace trace, List<XEventClassifier> logClassifiers) {
        this.trace = trace;
        this.logClassifiers = logClassifiers;
    }


    /**
     * @return the list of traces of the Log
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
     * @return Iterator of XesEventReaders for the events present in the trace
     */
    @Override
    public Iterator<EventReader> iterator() {
        ArrayList<EventReader> res = new ArrayList<>();
        for (XEvent event : trace) {
            res.add(new XesEventReader(event, logClassifiers));
        }
        return res.iterator();
    }
}
