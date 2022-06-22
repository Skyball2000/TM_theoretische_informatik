import java.io.IOException;
import java.util.Arrays;

public class Main {
    /**
     * Simulation of a Turing Machine.
     * <p>
     * Q is a set of all states
     * with: First q in Q is start Q
     * Alphabet is the set of input symbols
     * Transitions a set of transition functions in form of
     * (q,w,d) -> (q1,w1) with q,q1 in Q and w,w1 in Alphabet and d in {L,R}
     * <p>
     * If you run this Program with no args you set the TM from the command line.
     * <p>
     * If you run this program without args, set the TM parameters from the command line.
     * If you start the TM with an additional tag -yaml the tm.yaml in the current
     * working directory is used to set the parameters.
     *
     * @param args cmd args
     * @throws IOException Parameter Input Errors
     */
    public static void main(String[] args) throws IOException {

        // to use a different yaml file, use -yaml=<filename>

        TM tm = new TM();

        System.out.println("TM Starting ...");
        if (Arrays.stream(args).anyMatch(arg -> arg.startsWith("-yaml"))) {
            String yamlParameter = Arrays.stream(args).filter(arg -> arg.startsWith("-yaml")).findFirst().map(arg -> arg.substring(6)).orElse("tm.yaml");
            ArgsManager.fromYAML(tm, yamlParameter);
        } else {
            ArgsManager.fromCMD(tm);
        }

        tm.processTape();

        System.out.print("Result: ");
        System.out.println(tm.tape);
        System.out.println("TM DONE");
    }
}