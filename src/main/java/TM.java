import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TM {
    public String[] Q;
    public String[] Alphabet;
    public ArrayList<Transition> T;
    public ArrayList<String> tape;

    private int currentPointer = 0;
    private String currentState;
    private String expectedOutput;
    private int maxIterations;

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

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void addTransition(String encodedTransition) throws IOException {
        Transition t = Transition.fromInput(encodedTransition);
        if (Arrays.stream(Q).noneMatch(t.state::equals)) {
            throw new IOException("STATE [" + t.state + "] must be in Q");
        } else if (Arrays.stream(Q).noneMatch(t.newState::equals)) {
            throw new IOException("NEW STATE [" + t.newState + "] must be in Q");
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
        if (this.maxIterations == 0) {
            while (true) {
                if (stepTape()) break;
            }
        } else {
            for (int i = 0; i < this.maxIterations; i++) {
                if (stepTape()) break;
                if (i == this.maxIterations - 1) {
                    System.err.println("Max Iterations reached: " + this.maxIterations);
                }
            }
        }

        trimTape();
        printState(null);

        if (expectedOutput != null && !expectedOutput.equals("null")) {
            if (expectedOutput.equals(String.join("", tape))) {
                System.out.println("Output matches expected output: " + expectedOutput);
            } else {
                System.err.println("Output is incorrect, expected: " + expectedOutput + ", actual: " + String.join("", tape));
            }
        }

        return String.join("", tape);
    }

    private boolean stepTape() {
        Transition t;
        try {
            t = getTransition(currentState, tape.get(currentPointer));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return true;
        }

        printState(t);

        tape.set(currentPointer, t.newSymbol);
        currentState = t.newState;
        movePointer(t.direction);

        return t.newState.equals("f");
    }

    private void trimTape() {
        for (int i = tape.size() - 1; i >= 0; i--) {
            if (tape.get(i).equals("#")) {
                tape.remove(i);
                if (currentPointer >= i) {
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

    private void printState(Transition transition) {
        StringBuilder sb = new StringBuilder();

        sb.append(tape.stream().collect(Collectors.joining(" ", "[", "]")));
        sb.append("\n ");

        for (int i = 0; i < currentPointer; i++) {
            sb.append("  ");
        }

        sb.append("^ ");

        if (transition != null) {
            sb.append(transition);
        } else {
            sb.append(currentState);
        }

        System.out.println(sb);
    }

    private void movePointer(Direction dir) {
        currentPointer += dir.value;

        if (currentPointer < 0) {
            tape.add(0, "#");
            currentPointer++;
        } else if (currentPointer >= tape.size()) {
            tape.add("#");
        }
    }
}
