package reactiveminer.io;

/**
 *  class reading the single event of a string: a single character
 */
public class StringEventReader implements EventReader {
    private char event;

    /**
     * @param event character representing the event
     */
    public StringEventReader(char event) {
        this.event = event;
    }

    /**
     * @return character of the event
     */
    @Override
    public Object getId() {
        return event;
    }
}
