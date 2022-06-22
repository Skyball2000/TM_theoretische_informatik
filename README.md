# Simulation of a Turing Machine.
### Exercise THI

Implementation by **[Yan Wittmann](https://github.com/Skyball2000)**.

Add the `-yaml=<file>` option to the command line to load a YAML file.

## How to run
 - clone Repo  into your Project Directory (Or simply download source code from github.com):
 ```
git clone https://github.com/StarmanMartin/TM_theoretische_informatik.git
```
 - install mvn (see [install mvn](https://maven.apache.org/install.html))
 - Install dependencies:
  ```
  mvn dependency:resolve
```
## Setup

Q is a set of all states where the first q in Q is initial state

Alphabet is the set of input symbols

Transitions is a set of transition functions in form of
(q,w,d) -> (q1,w1) with q,q1 in Q and w,w1 in Alphabet and d in {L,R}

Tape is the tape the TM works on (the initial input).

If you run this Program with no args you set the TM from the command line.
 
If you run this program without args, you need to set the TM parameters from the command line.
If you start the TM with an additional tag -yaml the tm.yaml in the current
working directory is used to set the parameters.

There is an example tm.yaml in this Repo

## To Do

Implement the *ToDos* in the src/main/java/TM.java. 
**You only need to edit the processTape() and movePointer() function**. 
However, your free to edit the source code as you like. 
In the end it has to be able to parse a tm.yaml in the given
format and pass some not published jUnit tests.

```java
class TM {
//...
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
            case NONE:
                break;
        }
    }
//...
}
```