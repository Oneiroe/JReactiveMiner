package reactiveminer.automata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dk.brics.automaton.*;

/**
 * Parametric Separated Automaton (i.e. disjunction of conjunction of past/present/future automata
 */
public class SeparatedAutomaton {

    private Set<ConjunctAutomata> disjunctAutomata; //memo. Separation theorem result is a disjunction of separated automata
    private Automaton activator;
    /* TODO for Version > 0.1 @Alessio
    * The activator is represented as automaton for future extensions.
    * In this version should be way better to directly check if
    * the current trace character is equal to the activation one.
    * */


    /**
     * Initialize an empty separated automaton
     */
    public SeparatedAutomaton() {
        this.disjunctAutomata = new HashSet<ConjunctAutomata>();
    }

    /**
     * Initialize a separated automaton with an activator automaton
     *
     * @param activator automaton
     */
    public SeparatedAutomaton(Automaton activator) {
        this.activator = activator;
        this.disjunctAutomata = new HashSet<ConjunctAutomata>();
    }

    /**
     * Initialize a separated automaton with a lis of conjunct automata and an activator
     *
     * @param disjunctionOf collection of conjunct automata, in disjunction within this separated automaton
     * @param activator automaton
     */
    public SeparatedAutomaton(Automaton activator, List<ConjunctAutomata> disjunctionOf) {
        this.activator = activator;
        this.disjunctAutomata = new HashSet<ConjunctAutomata>();
        this.disjunctAutomata.addAll(disjunctionOf);
    }

    /**
     * @return Set of disjunct automata
     */
    public Set<ConjunctAutomata> getDisjunctAutomata() {
        return disjunctAutomata;
    }

    /**
     * Ad a new conjunct automata to the disjunction set
     *
     * @param newConjunction
     */
    public void addDisjunctionAutomata(ConjunctAutomata newConjunction) {
        this.disjunctAutomata.add(newConjunction);
    }

    /**
     * @return activator automaton
     */
    public Automaton getActivator() {
        return activator;
    }

    /**
     * @param activator new activator automaton
     */
    public void setActivator(Automaton activator) {
        this.activator = activator;
    }
}