package io.github.ppark;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Metal, Nimbus, CDE/Motif, Windows, Windows Classic
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame window = new JFrame();
        window.setTitle("NoteSE");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        MenuPanel mp = new MenuPanel();
        MenuPanel.window = window;
        NotePanel.window = window;
        window.add(mp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
