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
    private static Automaton getSingleCharActivatorAutomaton(char activator, char[] others) {
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
     * Get the automaton representing the <>A eventuality constraint for a desired letter of an alphabet
     *
     * @param desired desired character
     * @param others  alphabet without the desired character
     * @return automaton for <>desired
     */
    private static Automaton getEventualityAutomaton(char desired, char[] others) {
        State NonAcceptingState = new State();
        State AcceptingState = new State();
        AcceptingState.setAccept(true);

        NonAcceptingState.addTransition(new Transition(desired, AcceptingState));
        for (char other : others) {
            NonAcceptingState.addTransition(new Transition(other, NonAcceptingState));
        }
        AcceptingState.addTransition(new Transition(desired, AcceptingState));
        for (char other : others) {
            AcceptingState.addTransition(new Transition(other, AcceptingState));
        }

        Automaton resAutomaton = new Automaton();
        resAutomaton.setInitialState(NonAcceptingState);

        return resAutomaton;
    }

    /**
     * @return separated automaton for response constraint
     */
//    @SuppressWarnings("Duplicates")
    public static SeparatedAutomaton getResponseSeparatedAutomaton() {
        char[] alphabet = {'a', 'b', 'z'};
        Automaton activator = getSingleCharActivatorAutomaton(alphabet[0], Arrays.copyOfRange(alphabet, 1, 3));

        List<ConjunctAutomata> disjunctAutomata = new ArrayList<ConjunctAutomata>();

        char[] others = {alphabet[0], alphabet[2]};
        Automaton futureAutomaton = getEventualityAutomaton(alphabet[1], others);
        ConjunctAutomata conjunctAutomaton = new ConjunctAutomata(null, null, futureAutomaton);

        disjunctAutomata.add(conjunctAutomaton);
        SeparatedAutomaton res = new SeparatedAutomaton(activator, disjunctAutomata, alphabet);
        res.setNominalID("Response");
        return res;
    }

    /**
     * @return separated automaton for precedence constraint
     */
//    @SuppressWarnings("Duplicates")
    public static SeparatedAutomaton getPrecedenceSeparatedAutomaton() {
        char[] alphabet = {'a', 'b', 'z'};
        Automaton activator = getSingleCharActivatorAutomaton(alphabet[1], alphabet);

        List<ConjunctAutomata> disjunctAutomata = new ArrayList<ConjunctAutomata>();

        char[] others = {alphabet[1], alphabet[2]};
        Automaton pastAutomaton = getEventualityAutomaton(alphabet[0], others);

        ConjunctAutomata conjunctAutomaton = new ConjunctAutomata(pastAutomaton, null, null);

        disjunctAutomata.add(conjunctAutomaton);
        SeparatedAutomaton res = new SeparatedAutomaton(activator, disjunctAutomata, alphabet);
        res.setNominalID("Precedence");
        return res;
    }
}
