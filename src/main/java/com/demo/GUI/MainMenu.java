package com.demo.GUI;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    JPanel titlePanel;
    JPanel contentPanel;
    JButton startBtn;
    JButton lbBtn;
    JButton exitBtn;
    JLabel title;

    public MainMenu() {
        super("Who wants to be a millionaire?");
        setUpFrame();

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        wrapperPanel.setBackground(new Color(0, 96, 153));
        contentPanel = createContentPanel();

        titlePanel = createTitlePanel();
        title = createMainTitle();
        titlePanel.add(title);

        startBtn = createBtn("Start Game");
        lbBtn = createBtn("Leaderboard");
        exitBtn = createBtn("Exit");

        contentPanel.add(startBtn);
        contentPanel.add(lbBtn);
        contentPanel.add(exitBtn);

        wrapperPanel.add(contentPanel);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(wrapperPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void setUpFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.getContentPane().setBackground(new Color(0, 76, 193));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    private JPanel createTitlePanel() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(600, 200));
        p.setBackground(new Color(0, 96, 153));
        p.setLayout(new GridLayout(1,1));
        return p;
    }

    private JLabel createMainTitle() {
        JLabel l = new JLabel("Who wants to be a millionaire?");
        l.setVerticalTextPosition(JLabel.CENTER);
        l.setHorizontalTextPosition(JLabel.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 35));
        contentPanel.setOpaque(true);
        return l;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(250, 150));
        contentPanel.setBackground(new Color(0, 96, 153));
        contentPanel.setLayout(new GridLayout(3, 1, 15, 15));
        return contentPanel;
    }

    private JButton createBtn(String content) {
        JButton btn = new JButton(content);
        btn.setFocusable(false);
        btn.setFont(new Font("Arial", Font.BOLD, 30));
        btn.setBackground(new Color(0, 51, 102));
        btn.setForeground(Color.white);
        return btn;
    }
}
