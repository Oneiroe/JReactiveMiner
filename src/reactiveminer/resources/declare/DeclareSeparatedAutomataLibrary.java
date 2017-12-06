package reactiveminer.resources.declare;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import reactiveminer.automata.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Class to retrieve parametric separated automata for Declare constraints
 */
public class DeclareSeparatedAutomataLibrary {

    /**
     * Returns an automaton accepting only if the transition is equal to a specific activator character
     *
     * @param activator parametric character representing the activator in the parametric automaton
     * @param others    all the parametric characters of the alphabet but the activator
     * @return activator automaton
     */
    public static Automaton getSingleCharActivatorAutomaton(char activator, char[] others) {
        State accepting = new State();
        accepting.setAccept(true);

        State notAccepting = new State();

        notAccepting.addTransition(new Transition(activator, accepting));
        accepting.addTransition(new Transition(activator, accepting));

        for (char o : others) {
            notAccepting.addTransition(new Transition(o, notAccepting));
            accepting.addTransition(new Transition(o, notAccepting));
        }

        Automaton res = new Automaton();

        res.setInitialState(notAccepting);
        return res;
    }

    /**
     * @return separated automaton for response constraint
     */
    public static SeparatedAutomaton getResponseSeparatedAutomaton() {
        char[] alphabet = {'a', 'b', 'z'};
        Automaton activator = getSingleCharActivatorAutomaton(alphabet[0], Arrays.copyOfRange(alphabet, 1, 3));

        List<ConjunctAutomata> disjunctAutomata = new ArrayList<ConjunctAutomata>();

//        Future automaton
        State futureNonAcceptingState = new State();
        State futureAcceptingState = new State();
        futureAcceptingState.setAccept(true);

        futureNonAcceptingState.addTransition(new Transition(alphabet[0], futureNonAcceptingState));
        futureNonAcceptingState.addTransition(new Transition(alphabet[1], futureAcceptingState));
        futureNonAcceptingState.addTransition(new Transition(alphabet[2], futureNonAcceptingState));
        futureAcceptingState.addTransition(new Transition(alphabet[0], futureAcceptingState));
        futureAcceptingState.addTransition(new Transition(alphabet[1], futureAcceptingState));
        futureAcceptingState.addTransition(new Transition(alphabet[2], futureAcceptingState));

        Automaton futureAutomaton = new Automaton();
        futureAutomaton.setInitialState(futureNonAcceptingState);

        ConjunctAutomata conjunctAutomaton = new ConjunctAutomata(null, null, futureAutomaton);

        disjunctAutomata.add(conjunctAutomaton);
        SeparatedAutomaton res = new SeparatedAutomaton(activator, disjunctAutomata, alphabet);
        return res;
    }
}
