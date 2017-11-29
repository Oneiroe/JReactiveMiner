package reactiveminer.automata;

import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

/**
 * Class to run traces over a set of tokens (AToken!) related to the same activation
 */
public class ATokenRunner {
    private AToken aToken;

    /**
     * Initialize a runner for a specific AToken
     *
     * @param aToken
     */
    public ATokenRunner(AToken aToken) {
        this.aToken = aToken;
    }

    /**
     * @return the AToken associated to the runner
     */
    public AToken getaToken() {
        return aToken;
    }

    /**
     * move the tokens of one step according to the given transition
     *
     * @param t transition to perform
     */
    public void step(Transition t) {
        AToken newAToken = new AToken();
        for (State token : aToken.getTokensCollection()) {
            newAToken.addTokenToCollection(token.step(t.getMin()));
        }
        aToken = newAToken;
    }


    /**
     * Retrieve the result of AToken set in the current state
     *
     * @return true is at least one token is in accepting state
     */
    public boolean getCurrentResult() {
        boolean result = false;
        for (State token : aToken.getTokensCollection()) {
            result = result || token.isAccept();
            if (result == true) return true;
        }
        return result;
    }

}
