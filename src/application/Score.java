/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author admin_master
 */
public class Score implements ActionListener {
    
    private Controller controller;
    private final JFrame frame;
    private final JLabel label;
    private final JPanel panel;
    private final JPanel labelPanel;
    private final JPanel buttonsPanel;
    private final JButton yesBtn;
    private final JButton noBtn;
    private final int score;
    /**
     * @brief Score constructor
     * @param c Controller
     * @see Controller
     * @param score int
     */
    public Score(Controller c, int score) {
        this.controller = c;
        this.frame = new JFrame();
        this.label = new JLabel();
        this.noBtn = new JButton("No");
        this.yesBtn = new JButton("Yes");
        this.yesBtn.addActionListener(this);
        this.noBtn.addActionListener(this);
        this.panel = (JPanel) this.frame.getContentPane();
        this.score = score;
        this.label.setText("Your score is " + score + "! Do you want to play again? ");
        this.labelPanel = new JPanel();
        this.labelPanel.setLayout(new BoxLayout(this.labelPanel, BoxLayout.LINE_AXIS));
        this.labelPanel.add(this.label);
        this.buttonsPanel = new JPanel();
        this.buttonsPanel.setLayout(new BoxLayout(this.buttonsPanel, BoxLayout.LINE_AXIS));
        this.buttonsPanel.add(this.yesBtn);
        this.buttonsPanel.add(this.noBtn);
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        this.panel.add(this.labelPanel);
        this.panel.add(this.buttonsPanel);
        this.frame.setSize(400, 100);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
    /**
     * @brief close method
     */
    public void close() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }
    /**
     * 
     * @param e ActionEvent
     * @see ActionEvent
     * @see Controller
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(btn.equals(this.yesBtn)){
            this.close();
            try {
                this.controller = new Controller(10, 10);
            } catch (IOException ex) {
                Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (btn.equals(this.noBtn)) {
            this.close();
            System.exit(0);
        }
    }
}
