package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final static String FILENAME = "./config.yml";
    private final static String CONF_MIN = "minimum:";
    private final static String CONF_MAX = "maximum:";
    private final static String CONF_ATTEMPTS = "attempts:";
    private final static int DEFAULT_MIN = 10;
    private final static int DEFAULT_MAX = 100;
    private final static int DEFAULT_ATTEMPTS = 10;
    
    
    private int min = DEFAULT_MIN;
    private int max = DEFAULT_MAX;
    private int attempts = DEFAULT_ATTEMPTS;
    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */
    public DrawNumberApp() {
        this.loadConfiguration();
        this.model = new DrawNumberImpl(min, max, attempts);
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp();
    }

    @Override
    public void loadConfiguration() {
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        
        try {
            String string;
            while((string = reader.readLine()) != null) {
                StringTokenizer s = new StringTokenizer(string);   
                switch(s.nextToken()) {
                case CONF_MIN:
                    this.min = Integer.parseInt(s.nextToken());
                    break;
                case CONF_MAX:
                    this.max = Integer.parseInt(s.nextToken());
                    break;
                case CONF_ATTEMPTS:
                    this.attempts = Integer.parseInt(s.nextToken());
                    break;
                }
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
