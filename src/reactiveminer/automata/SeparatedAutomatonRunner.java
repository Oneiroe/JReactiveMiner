package reactiveminer.automata;

import dk.brics.automaton.Transition;
import reactiveminer.io.TraceReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to run a trace over a separated automata
 */
public class SeparatedAutomatonRunner {
    private SeparatedAutomaton automaton;

    //    REMEMBER that separated automaton is a disjunction of conjunction!!!
    private List<ConjunctAutomataRunner> disjunctAutomataRunners; //ATokens!!!
    private List<ATokenRunner> aTokensRunners;

    private int activationCounter;
    private int fulfilledActivationCounter;

    /**
     * Initialize a runner object to run trace on a given separated automaton.
     * For each disjunct automata of the spared automaton is initialized a specific runner
     *
     * @param automaton
     */
    public SeparatedAutomatonRunner(SeparatedAutomaton automaton) {
        this.automaton = automaton;
        this.disjunctAutomataRunners = new ArrayList<ConjunctAutomataRunner>();
        this.aTokensRunners = new ArrayList<ATokenRunner>();

//        it is better to put the present automaton as first of the list for performance speedup
//        BUT pasts must be carried on any way
        for (ConjunctAutomata ca : automaton.getDisjunctAutomata()) {
            this.disjunctAutomataRunners.add(new ConjunctAutomataRunner(ca));
        }

        this.activationCounter = 0;
        this.fulfilledActivationCounter = 0;
    }


    /**
     * @return Number of total activation of the constraint
     */
    public int getActivationCounter() {
        return activationCounter;
    }

    /**
     * @return Number of fulfilled activation
     */
    public int getFulfilledActivationCounter() {
        return fulfilledActivationCounter;
    }

    /**
     * The support of the constraint represented by the separated automaton and the trace run on it.
     * It is the ration between the fulfilled activation and the total number of activations.
     *
     * @return support of the constraint represented by the separated automaton wrt the given trace.
     */
    public double getSupport() {
//        TODO full support formula
        if (activationCounter == 0) {
            return 0.0;
        }
        return fulfilledActivationCounter / activationCounter;
    }

    /**
     * Run a full trace over the separated automaton.
     * WARNING the automaton will be reset before the operation
     */
    public void run(TraceReader tr) {
        this.reset();
//        TODO
    }

    /**
     * Perform a single step in the separated automata using the given transition
     */
    public void step(Transition t) {
//        Activation step
        if (this.activated(t)) {
            activationCounter++;
            AToken standReadyAToken = new AToken();
            boolean solved = false;
            boolean unclear = false;
            for (ConjunctAutomataRunner car : disjunctAutomataRunners) {
                //    step in the past (anyway)
                car.step(t);
                // if we can retrieve a clear positive result no need for AToken to be launched
                if (!solved) {
                    /* TODO for Version > 0.1 @Alessio
                    * Not suitable for parallel computation, force to a semaphore check at each step and
                    * */
                    if (car.hasClearResult()) {
                        if (car.getCurrentResult(t)) {
                            solved = true;
                            fulfilledActivationCounter++;
                        }

                    } else {
                        unclear = true; //if at least one solution need to be checked in the future (if no positive certain answer)
                        standReadyAToken.addTokenToCollection(car.getAToken());
                    }
                }

            }
            // If no positive certain result and at least one result need to be checked in the future, launch AToken!
            if (!solved && unclear) {
                aTokensRunners.add(new ATokenRunner(standReadyAToken));
            }

            // ATokens Step in the future
            for (ATokenRunner a : aTokensRunners) {
                a.step(t);
            }
        } else {
            // step in the past
            for (ConjunctAutomataRunner car : disjunctAutomataRunners) {
                car.step(t);
            }
            // ATokens Step in the future
            for (ATokenRunner a : aTokensRunners) {
                a.step(t);
                /* TODO for Version > 0.1 @Alessio
                * Check if a token ends up in a permanent violation/satisfaction state.
                * - satisfaction: stop all tokens and return positive result
                * - violation: remove token. if no token remaining return negative result
                * */
            }


        }

    }

    private boolean activated(Transition t) {
        return automaton.getActivator().getInitialState().step(t.getMin()).isAccept();
    }


    /**
     * Reset the automaton state to make it ready for a new trace
     */
    public void reset() {
        activationCounter = 0;
        fulfilledActivationCounter = 0;
        aTokensRunners = new ArrayList<ATokenRunner>();
        for (ConjunctAutomataRunner car : disjunctAutomataRunners) {
            car.reset();
        }
    }


}
