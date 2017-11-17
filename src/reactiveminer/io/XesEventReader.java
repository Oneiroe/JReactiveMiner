package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XEvent;

import java.util.List;

/**
 * Class to read a XES event through EventReader Interface
 */
public class XesEventReader implements EventReader {
    private XEvent event;
    private List<XEventClassifier> logClassifiers;
    private String id;

    /**
     * @param event          XES event
     * @param logClassifiers attributes list required to identify uniquely the event
     */
    public XesEventReader(XEvent event, List<XEventClassifier> logClassifiers) {
        this.event = event;
        this.logClassifiers = logClassifiers;
        id = "";
        for (XEventClassifier singleClassifier : logClassifiers) {
            id += singleClassifier.getClassIdentity(event) + ';';
        }
    }

    /**
     * @return String ID
     */
    @Override
    public Object getId() {
        return id;
    }
}
