package ca.group1.coviz;

import ca.group1.coviz.login.LoginGUI;

/**
 * Coviz!
 */
public final class App {
    private App() {
    }

    /**
     * Runs the program
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        LoginGUI frame = new LoginGUI();
        frame.setVisible(true);
    }
}
