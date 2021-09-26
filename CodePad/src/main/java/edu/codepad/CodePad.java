package edu.codepad;

import javax.swing.SwingUtilities;

import edu.codepad.controller.ContentManager;
import edu.codepad.view.Principal;

public class CodePad {
    public static Principal window;
    public static ContentManager manager;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                window = new Principal();
                manager = new ContentManager(window);
                window.setContentManager(manager);
            }
            
        });
    }
}
