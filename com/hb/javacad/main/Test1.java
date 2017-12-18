package com.hb.javacad.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Test1 extends JPanel {
    private Color[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.WHITE, Color.GREEN};
    private int currentColorIndex;

    public void randomPaint() {
        Random random = new Random();
        currentColorIndex = random.nextInt(colors.length);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colors[currentColorIndex]);
        //g2d.fillRect(10, 10, 200, 200);
        g.drawLine(100,100,200,200);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame();
        final Test1 test = new Test1();
        JButton startButton = new JButton("Start");

        frame.getContentPane().add(startButton, BorderLayout.NORTH);
        frame.getContentPane().add(test, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                                test.randomPaint();
                                Thread.sleep(1000);
                            }
                        } catch (Exception ex) {
                        }
                    }
                });
                thread.start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}