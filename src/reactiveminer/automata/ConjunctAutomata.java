package reactiveminer.automata;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.Transition;


/**
 * Parametric conjunct Automata
 */
class ConjunctAutomata {
    private Automaton pastAutomaton = null;
    private Automaton presentAutomaton = null;
    private Automaton futureAutomaton = null;

    /**
     * Separation theorem result may be a disjunction of separated automata.
     * Put null if one of the automaton is not present
     */
    public ConjunctAutomata(Automaton pastAutomaton, Automaton presentAutomaton, Automaton futureAutomaton) {
        this.pastAutomaton = pastAutomaton;
        this.presentAutomaton = presentAutomaton;
        this.futureAutomaton = futureAutomaton;
//        TODO optimization of automata (e.g. minimization, completion, ...)

    }

    public Automaton getPastAutomaton() {
        return pastAutomaton;
    }

    public Automaton getPresentAutomaton() {
        return presentAutomaton;
    }

    public Automaton getFutureAutomaton() {
        return futureAutomaton;
    }

    /**
     * @return true if the past automaton is present, false otherwise
     */
    public boolean hasPast() {
        return pastAutomaton != null;
    }

    /**
     * @return true if the present automaton is present, false otherwise
     */
    public boolean hasPresent() {
        return presentAutomaton != null;
    }

    /**
     * @return true if the future automaton is present, false otherwise
     */
    public boolean hasFuture() {
        return futureAutomaton != null;
    }
}
