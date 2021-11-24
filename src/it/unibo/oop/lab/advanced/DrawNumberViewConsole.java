package it.unibo.oop.lab.advanced;

public class DrawNumberViewConsole implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts!";
    
    private DrawNumberViewObserver observer;
    
    public DrawNumberViewConsole() {
        
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
        System.out.println("Incorrect Number.. try again");
    }

    @Override
    public void result(DrawResult res) {
        System.out.println(res.getDescription());
    }

    @Override
    public void limitsReached() {
        System.out.println("You lost" + NEW_GAME);
    }

    @Override
    public void displayError(String message) {
        System.err.println(message);
    }

}
