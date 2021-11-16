package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;
import it.unibo.oop.lab.mvcio.SimpleGUI;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    
    private final static String TITLE = "MVCIO2";
    
    private final JFrame frame = new JFrame(TITLE);
    private final Controller controller;

    public SimpleGUIWithFileChooser(final Controller controller) {
        this.controller = controller;

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        final JTextArea text = new JTextArea();
        final JButton write = new JButton("Save");
        
        JPanel commandsPanel = new JPanel();
        commandsPanel.setLayout(new BorderLayout());
        
        JTextField currentFile = new JTextField();
        currentFile.setEditable(false);
        
        JButton browse = new JButton("Browse");
        
        commandsPanel.add(currentFile, BorderLayout.CENTER);
        commandsPanel.add(browse, BorderLayout.LINE_END);
        
        panel.add(commandsPanel, BorderLayout.NORTH);
        panel.add(text);
        panel.add(write, BorderLayout.SOUTH);
        
        frame.setContentPane(panel);
        
        write.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.printString(text.getText());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, e1, "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
            
        });
        
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int value = chooser.showSaveDialog(browse);
                
                switch(value) {
                case JFileChooser.APPROVE_OPTION: 
                    controller.setFile(chooser.getSelectedFile());
                    currentFile.setText(controller.getFile().getName());
                    break;
                
                case JFileChooser.CANCEL_OPTION: 
                    break;
              
                default: 
                    JOptionPane.showMessageDialog(browse, "Error while selecting the file");
                      
                }
            }
            
        });
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
    }
    
    private void display() {
        frame.setVisible(true);
    }
    
    public static void main(final String... args) {
        new SimpleGUIWithFileChooser(new Controller()).display();
     }


}
