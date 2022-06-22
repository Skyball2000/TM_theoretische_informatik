import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TM {
    public String[] Q;
    public String[] Alphabet;
    public ArrayList<Transition> T;
    public ArrayList<String> tape;

    private int currentPointer = 0;
    private String currentState;

    public TM() {
        this.T = new ArrayList<>();
    }

    public void setQ(String[] q) throws IOException {
        Q = q;

        if (q.length == 0) {
            throw new IOException("Q must not be empty!");
        }
        this.currentState = q[0];
    }

    public void setAlphabet(String[] alphabet) {
        ArrayList<String> alp = new ArrayList<>(Arrays.asList(alphabet));
        alp.add("#");
        String[] arr = new String[alp.size()];
        this.Alphabet = alp.toArray(arr);
    }

    public void setTape(String tape) throws IOException {
        String[] symbol = tape.split("");
        for (String s : symbol) {
            if (Arrays.stream(Alphabet).noneMatch(s::equals)) {
                throw new IOException("Symbol in the tape must be in Alphabet or '#' (as Blank)");
            }
        }

        this.tape = new ArrayList<>(Arrays.asList(tape.split("")));
    }


    public void addTransition(String encodedTransition) throws IOException {
        Transition t = Transition.fromInput(encodedTransition);
        if (Arrays.stream(Q).noneMatch(t.state::equals) || Arrays.stream(Q).noneMatch(t.newState::equals)) {
            throw new IOException("STATE and NEW STATE must be in Q");
        }
        if (Arrays.stream(Alphabet).noneMatch(t.symbol::equals) || Arrays.stream(Alphabet).noneMatch(t.newSymbol::equals)) {
            throw new IOException("SYMBOL and NEW SYMBOL must be in Alphabet or '#' (as Blank)");
        }
        if (t.direction == Direction.NONE) {
            throw new IOException("Direction must be L or R");
        }

        this.T.add(t);
    }

    public String processTape() {
        printState();

        while (true) {
            Transition t;
            try {
                t = getTransition(currentState, tape.get(currentPointer));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            tape.set(currentPointer, t.newSymbol);
            currentState = t.newState;
            movePointer(t.direction);

            printState();

            if (t.newState.equals("f")) {
                break;
            }
        }

        trimTape();

        return String.join("", tape);
    }

    private void trimTape() {
        for (int i = tape.size() - 1; i >= 0; i--) {
            if (tape.get(i).equals("#")) {
                tape.remove(i);
                if (currentPointer > i) {
                    currentPointer--;
                }
            } else {
                break;
            }
        }
        for (int i = 0; i < tape.size(); i++) {
            if (tape.get(i).equals("#")) {
                tape.remove(i);
                if (currentPointer > i) {
                    currentPointer--;
                }
            }
        }
    }

    private Transition getTransition(String state, String symbol) {
        for (Transition t : T) {
            if (t.state.equals(state) && t.symbol.equals(symbol)) {
                return t;
            }
        }

        throw new IllegalArgumentException("No transition found for state " + state + " and symbol " + symbol);
    }

    private void printTM() {
        System.out.println("         states = " + Arrays.toString(Q));
        System.out.println("       alphabet = " + Arrays.toString(Alphabet));
        System.out.println("    transitions = " + T);
        System.out.println("           tape = " + tape);
        System.out.println("current pointer = " + currentPointer);
        System.out.println("  current state = " + currentState);
    }

    private void printState() {
        System.out.println("state " + currentState + " at " + currentPointer + " on [" + String.join(" ", tape) + "]");
    }

    private void movePointer(Direction dir) {
        currentPointer += dir.value;

        if (currentPointer < 0) {
            tape.add(0, "#");
        } else if (currentPointer >= tape.size()) {
            tape.add("#");
        }
    }
}
