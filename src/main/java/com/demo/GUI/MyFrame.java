package com.demo.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        JLabel label = getJLabel();

        this.setTitle("Who wants to be a millionaire?");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(700, 600);
        this.setLayout(null);

        ImageIcon img = new ImageIcon("money.png");
        this.setIconImage(img.getImage());
        this.getContentPane().setBackground(new Color(20, 10, 190));
        this.add(label);
        this.setVisible(true);
    }

    private JLabel getJLabel() {
        Border border = BorderFactory.createLineBorder(Color.YELLOW, 3);

        JLabel label = new JLabel();
        label.setText("Who wants to be a millionaire?");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(Color.YELLOW);
        label.setFont(new Font("Helvetica", Font.BOLD, 24));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setBorder(border);
        label.setBounds(0, 0, 700, 40);
        return label;
    }
}
