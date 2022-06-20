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
        Transition t = Transition.parseTransition(encodedTransition);
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
        // ToDo implement the processing!
        return "";
    }

    private void movePointer(Direction dir) {
        switch (dir) {
            case LEFT:
                //ToDo
                break;
            case RIGHT:
                //ToDo
                break;
            default:
                break;
        }
    }
}
