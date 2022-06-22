import java.io.IOException;

enum Direction {
    LEFT(-1),
    RIGHT(1),
    NONE(0);

    public final int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction fromString(String s) {
        if (s.equals("L")) {
            return LEFT;
        } else if (s.equals("R")) {
            return RIGHT;
        } else {
            return NONE;
        }
    }

    public int getValue() {
        return 0;
    }
}

public class Transition {
    public final String state;
    public final String symbol;

    public final Direction direction;


    public final String newState;
    public final String newSymbol;

    private Transition() {
        this("", "", Direction.NONE, "", "");
    }

    public Transition(String state, String symbol, Direction direction, String newState, String newSymbol) {
        this.state = state;
        this.symbol = symbol;
        this.direction = direction;
        this.newState = newState;
        this.newSymbol = newSymbol;
    }

    static public Transition fromInput(String encodedState) throws IOException {
        String[] encodedStateList = encodedState.split(", ?");
        if (encodedStateList.length != 5) {
            throw new IOException("Encoded Transition pattern 'STATE, SYMBOL, DIRECTION [L|R], NEW STATE, NEW SYMBOL'");
        }

        return new Transition(
                encodedStateList[0],
                encodedStateList[1],
                Direction.fromString(encodedStateList[2]),
                encodedStateList[3],
                encodedStateList[4]
        );
    }

    @Override
    public String toString() {
        return "(" + state + ", " + symbol + ") -> (" + newState + ", " + newSymbol + ", " + direction + ")";
    }
}
