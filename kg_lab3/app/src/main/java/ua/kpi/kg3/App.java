package ua.kpi.kg3;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}