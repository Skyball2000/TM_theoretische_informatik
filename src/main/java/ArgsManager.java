import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class ArgsManager {
    private static class TMArgs {
        private String[] q;
        private String[] alphabet;
        private String[] transition;
        private String tape;
        private String expected;
        private int maxIterations;

        public void setTransition(String[] transition) {
            this.transition = transition;
        }

        public void setAlphabet(String[] alphabet) {
            this.alphabet = alphabet;
        }

        public void setQ(String[] q) {
            this.q = q;
        }

        public void setTape(String tape) {
            this.tape = tape;
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }

        public void setMaxIterations(int maxIterations) {
            this.maxIterations = maxIterations;
        }
    }

    static void fromCMD(TM tm) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter States Q:");
        tm.setQ(in.next().split("[, ]"));
        System.out.print("Enter Alphabet:");
        tm.setAlphabet(in.next().split("[, ]"));

        String input;
        System.out.println("Add Transition as encoded String:");
        System.out.println("STATE, SYMBOL, DIRECTION [L|R], NEW STATE, NEW SYMBOL");
        System.out.println("STATE and NEW STATE must be in Q");
        System.out.println("SYMBOL and NEW SYMBOL must be in Alphabet or '#' (as Blank)");
        System.out.println("DIRECTION must be L or R");
        while (true) {
            System.out.print("Add Transition: (Q to quit)");
            input = in.next();
            if (input.equals("Q")) {
                break;
            }
            try {
                tm.addTransition(input);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter tabe:");
        try {
            tm.setTape(in.next());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static void fromYAML(TM tm, String path) throws IOException {
        Path filePath = Path.of(path);
        String yamlString;
        try {
            yamlString = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TMArgs args = mapper.readValue(yamlString, ArgsManager.TMArgs.class);
        tm.setQ(args.q);
        tm.setAlphabet(args.alphabet);
        tm.setTape(args.tape);
        tm.setExpectedOutput(args.expected);
        tm.setMaxIterations(args.maxIterations);
        for (int i = 0; i < args.transition.length; ++i) {
            try {
                tm.addTransition(args.transition[i]);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
