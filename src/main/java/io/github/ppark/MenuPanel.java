package io.github.ppark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuPanel extends JPanel implements ActionListener {

    static JFrame window;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    String path = System.getProperty("user.home") + File.separator + "NoteSE";

    GridBagConstraints gbc;
    JPanel topPanel;
    JPanel downPanel;

    // topPanel elements
    JPanel blankPanel1;
    JLabel titleLabel;
    JPanel buttonPanel;

    // buttonPanel elements
    JButton helpButton;
    JButton writeButton;
    JButton settingButton;

    // downPanel elements
    JList listList;
    DefaultListModel model;

    // HelpWindow elements
    JFrame helpWindow;
    JButton githubButton = new JButton("Github");
    JButton licenseButton = new JButton("License");

    public MenuPanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());

        setup();
    }

    public void setup() {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        topPanel = new JPanel();
        downPanel = new JPanel();

        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.setBackground(new Color(30, 31, 34));
        topPanel.setLayout(new GridBagLayout());
        add(topPanel, gbc);

        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.gridx = 0;
        gbc.gridy = 1;
        downPanel.setBackground(new Color(15, 16, 17));
        downPanel.setLayout(new BorderLayout());
        add(downPanel, gbc);

        setupTopPanel();
        setupDownPanel();
    }

    public void setupTopPanel() {
        blankPanel1 = new JPanel();
        titleLabel = new JLabel("NoteSE");
        buttonPanel = new JPanel();

        // button
        helpButton = new JButton("Help");
        writeButton = new JButton("Write");
        settingButton = new JButton("Settings");

        gbc.weightx = 0.0001;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        blankPanel1.setBackground(new Color(30, 31, 34));
        topPanel.add(blankPanel1, gbc);

        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("HY견고딕", Font.BOLD, 30));
        topPanel.add(titleLabel, gbc);

        gbc.weightx = 0.9;
        gbc.weighty = 1.0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.setBackground(new Color(30, 31, 34)); // new Color(134, 134, 14)
        buttonPanel.setLayout(new GridBagLayout());
        topPanel.add(buttonPanel, gbc);

        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        helpButton.setFocusPainted(false);
        helpButton.setBackground(new Color(15, 16, 17));
        helpButton.setForeground(Color.WHITE);
        helpButton.setBorderPainted(false);
        helpButton.addActionListener(this);
        buttonPanel.add(helpButton, gbc);

        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        writeButton.setFocusPainted(false);
        writeButton.setBackground(new Color(15, 16, 17));
        writeButton.setForeground(Color.WHITE);
        writeButton.setBorderPainted(false);
        writeButton.addActionListener(this);
        buttonPanel.add(writeButton, gbc);

        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.gridx = 2;
        gbc.gridy = 0;
        settingButton.setFocusPainted(false);
        settingButton.setBackground(new Color(15, 16, 17));
        settingButton.setForeground(Color.WHITE);
        settingButton.setBorderPainted(false);
        settingButton.addActionListener(this);
        buttonPanel.add(settingButton, gbc);
    }

    public void setupDownPanel() {
        Map<String, String> list = new ReadNoteList().readList();

        model = new DefaultListModel();
        listList = new JList(model);
        listList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (String mapKey : list.keySet()) {
            model.addElement(mapKey + " [" + list.get(mapKey) + "]");
        }

        listList.setBackground(new Color(31, 32, 34));
        listList.setForeground(Color.WHITE);
        listList.setFont(new Font("HY견고딕", Font.PLAIN, 20));

        String patternString = "\\[(.*?)\\]";
        listList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    Pattern pattern = Pattern.compile(patternString);
                    Matcher matcher = pattern.matcher(list.getSelectedValue().toString());

                    String path = null;
                    while (matcher.find()) {
                        path = matcher.group(1);
                    }

                    assert path != null;
                    window.setContentPane(new NotePanel(new File(path)));
                    window.revalidate();

                }
            }
        });

        downPanel.add(listList, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(writeButton)) {
            String title = JOptionPane.showInputDialog("노트 제목");
            File newFile = new File(path + File.separator + title + ".txt");
            try {
                newFile.createNewFile();
                window.setContentPane(new NotePanel(newFile));
                window.revalidate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(settingButton)) {
            JOptionPane.showMessageDialog(this, "Now Developing", "Now Developing", JOptionPane.ERROR_MESSAGE);
        }
        if (e.getSource().equals(helpButton)) {
            helpWindow = new JFrame();
            helpWindow.setTitle("About NoteSE");
            helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            helpWindow.setResizable(false);

            JPanel helpPanel = new JPanel();
            helpPanel.setPreferredSize(new Dimension(400, 300));
            helpWindow.add(helpPanel);
            helpWindow.pack();

            helpWindow.setLocationRelativeTo(null);
            helpWindow.setVisible(true);

            helpPanel.setBackground(Color.BLACK);
            helpPanel.setLayout(new GridBagLayout());

            gbc.weightx = 1.0;
            gbc.weighty = 0.8;
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel helpTitleLabel = new JLabel("NoteSE 1.0");
            helpTitleLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
            helpTitleLabel.setVerticalAlignment(JLabel.CENTER);
            helpTitleLabel.setHorizontalAlignment(JLabel.CENTER);
            helpTitleLabel.setForeground(Color.WHITE);
            helpPanel.add(helpTitleLabel, gbc);

            gbc.weightx = 1.0;
            gbc.weighty = 0.2;
            gbc.gridx = 0;
            gbc.gridy = 1;
            JPanel helpButtonPanel = new JPanel();
            helpButtonPanel.setBackground(Color.BLACK);
            helpButtonPanel.setLayout(new GridLayout());
            helpPanel.add(helpButtonPanel, gbc);

            githubButton.setBackground(new Color(30, 31, 34));
            githubButton.setForeground(Color.WHITE);
            githubButton.setFont(new Font("HY견고딕", Font.PLAIN, 15));
            githubButton.addActionListener(this);
            helpButtonPanel.add(githubButton);

            licenseButton.setBackground(new Color(30, 31, 34));
            licenseButton.setForeground(Color.WHITE);
            licenseButton.setFont(new Font("HY견고딕", Font.PLAIN, 15));
            licenseButton.addActionListener(this);
            helpButtonPanel.add(licenseButton);
        }
        if (e.getSource().equals(githubButton)) {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    URI uri = new URI("https://github.com/PPark8093");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource().equals(licenseButton)) {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    URI uri = new URI("https://opensource.org/license/mit/");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
