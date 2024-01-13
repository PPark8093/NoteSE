package io.github.ppark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class NotePanel extends JPanel implements ActionListener {

    static JFrame window;

    public static final int WIDTH = MenuPanel.WIDTH;
    public static final int HEIGHT = MenuPanel.HEIGHT;

    File file;

    JTextArea jTextArea;
    JPanel jPanel;
    JButton saveButton;
    JButton exitButton;

    public NotePanel(File file) {
        this.file = file;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        setup();
    }

    public void setup() {
        jTextArea = new JTextArea();
        jPanel = new JPanel();
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                jTextArea.append(str + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 30));
        jTextArea.setBackground(Color.BLACK);
        jTextArea.setForeground(Color.WHITE);
        add(jTextArea, BorderLayout.CENTER);

        jPanel.setBackground(Color.BLACK);
        jPanel.setLayout(new GridLayout());
        add(jPanel, BorderLayout.SOUTH);

        saveButton.setBackground(new Color(30, 31, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("HY견고딕", Font.PLAIN, 15));;
        saveButton.addActionListener(this);
        jPanel.add(saveButton);

        exitButton.setBackground(new Color(30, 31, 34));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("HY견고딕", Font.PLAIN, 15));
        exitButton.addActionListener(this);
        jPanel.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveButton)) {
            try {
                FileWriter fileWriter = new FileWriter(file.getPath());
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(jTextArea.getText());
                printWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(exitButton)) {
            window.setContentPane(new MenuPanel());
            window.revalidate();
        }
    }
}
