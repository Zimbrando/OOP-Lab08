package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final static String CONF_FILENAME = "./config.yml";
    private final static String CONF_MIN = "minimum:";
    private final static String CONF_MAX = "maximum:";
    private final static String CONF_ATTEMPTS = "attempts:";
    private final static int DEFAULT_MIN = 0;
    private final static int DEFAULT_MAX = 100;
    private final static int DEFAULT_ATTEMPTS = 10;
    
    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @throws FileNotFoundException 
     * 
     */
    public DrawNumberApp() throws FileNotFoundException {
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(CONF_FILENAME);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        int min = DEFAULT_MIN, max = DEFAULT_MAX, attempts = DEFAULT_ATTEMPTS;
        
        try {
            String string;
            while((string = reader.readLine()) != null) {
                StringTokenizer s = new StringTokenizer(string);   
                switch(s.nextToken()) {
                case CONF_MIN:
                    min = Integer.parseInt(s.nextToken());
                    break;
                case CONF_MAX:
                    max = Integer.parseInt(s.nextToken());
                    break;
                case CONF_ATTEMPTS:
                    attempts = Integer.parseInt(s.nextToken());
                    break;
                }
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.model = new DrawNumberImpl(min, max, attempts);
        
        final DrawNumberView guiView = new DrawNumberViewImpl();
        guiView.setObserver(this);
        guiView.start();
        
        final DrawNumberView consoleView = new DrawNumberViewConsole();
        consoleView.setObserver(this);
        consoleView.start();
        
        final DrawNumberView fileView = new DrawNumberViewFile("history.txt");
        fileView.setObserver(this);
        fileView.start();
        
        this.views = new ArrayList();
        this.views.add(guiView);
        this.views.add(consoleView);
        this.views.add(fileView);
    }

    @Override
    public void newAttempt(final int n) {
        for (DrawNumberView view : this.views) {
           try {
               final DrawResult result = model.attempt(n);
               view.result(result);            
           } catch (IllegalArgumentException e) {
                view.numberIncorrect();
           } catch (AttemptsLimitReachedException e) {
                view.limitsReached();
                this.resetGame();
           }
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
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp();
    }
}
