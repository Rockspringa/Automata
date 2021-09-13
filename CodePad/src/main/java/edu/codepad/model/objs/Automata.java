package edu.codepad.model.objs;

public interface Automata {
    int getNextState(char ch, int state);

    boolean isAcceptState(int state);
}
