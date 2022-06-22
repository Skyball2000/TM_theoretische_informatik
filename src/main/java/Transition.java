import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final static Pattern CSV_TRANSITION_PATTERN = Pattern.compile("^([^,]+),([^,]+),([LRN]|LEFT|RIGHT|NONE),([^,]+),([^,]+)$");
    private final static Pattern BRACKET_ARROW_TRANSITION_PATTERN = Pattern.compile("^\\(([^,]+), *([^,]+)\\) *-> *\\(([LRN]|LEFT|RIGHT|NONE), *([^,]+), *([^,]+)\\)$");

    static public Transition fromInput(String encodedState) {
        for (Pattern pattern : Arrays.asList(CSV_TRANSITION_PATTERN, BRACKET_ARROW_TRANSITION_PATTERN)) {
            Matcher matcher = pattern.matcher(encodedState);
            if (matcher.matches()) {
                return new Transition(
                        matcher.group(1),
                        matcher.group(2),
                        Direction.fromString(matcher.group(3)),
                        matcher.group(4),
                        matcher.group(5)
                );
            }
        }
        throw new IllegalArgumentException("Invalid transition: " + encodedState);
    }

    @Override
    public String toString() {
        return "(" + state + ", " + symbol + ") -> (" + newState + ", " + newSymbol + ", " + direction + ")";
    }
}
