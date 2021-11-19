package it.unibo.oop.lab.mvc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {
    
    private final PrintStream stream = System.out;
    private final List<String> history;
    private String nextString;
    
    
    public ControllerImpl(String nextString) {
        this();
        this.nextString = nextString;
    }
    
    public ControllerImpl() {
        this.history = new ArrayList<String>();
    }

    @Override
    public void setNextString(final String s) {
        this.nextString = s;
    }

    @Override
    public String getNextString() {
        return this.nextString;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<String>(this.history);
    }

    @Override
    public void sendCurrentString() throws IllegalStateException{
        if (this.nextString == null) {
            throw new IllegalStateException("Current string is null");
        }
        this.history.add(this.nextString);
        stream.println(this.nextString);        
    }

}
