import java.io.IOException;

enum Direction {
    LEFT,
    RIGHT,
    NONE
}

public class Transition {
    public String state;
    public String symbol;

    public Direction direction;


    public String newState;
    public String newSymbol;

    static public Transition parseTransition(String encodedState) throws IOException {
        String[] encodedStateList = encodedState.split(", ?");
        if (encodedStateList.length != 5) {
            throw new IOException("Encoded Transition pattern 'STATE, SYMBOL, DIRECTION [L|R], NEW STATE, NEW SYMBOL'");
        }

        Transition transition = new Transition();
        transition.state = encodedStateList[0];
        transition.symbol = encodedStateList[1];
        transition.direction = encodedStateList[2].equals("L") ? Direction.LEFT : encodedStateList[2].equals("R") ? Direction.RIGHT : Direction.NONE;
        transition.newState = encodedStateList[3];
        transition.newSymbol = encodedStateList[4];

        return transition;
    }
}
