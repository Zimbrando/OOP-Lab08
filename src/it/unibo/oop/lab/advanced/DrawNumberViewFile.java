package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class DrawNumberViewFile implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts!";

    private DrawNumberViewObserver observer;
    private PrintStream fileStream;
    
    public DrawNumberViewFile(String filename) throws FileNotFoundException {
        this.fileStream = new PrintStream(new File(filename));
    }
     
    @Override
    public void setObserver(DrawNumberViewObserver observer) {
        this.observer = observer;
    }

    @Override
    public void start() {

    }

    @Override
    public void numberIncorrect() {
        this.fileStream.println("Incorrect Number.. try again");
    }

    @Override
    public void result(DrawResult res) {
        this.fileStream.println(res.getDescription());
    }

    @Override
    public void limitsReached() {
        this.fileStream.println("You lost" + NEW_GAME);
    }

    @Override
    public void displayError(String message) {
        this.fileStream.println(message);
    }

}
