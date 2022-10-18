package View;

import Controller.MainViewController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainView extends JFrame {

    private MainViewController controller;
    private JPanel mainPanel;
    private JButton firstTestButton;
    private JButton secondTestButton;
    private JButton thirdTestButton;
    private JPanel resultPanel;
    private JLabel queue1;
    private JLabel queue2;
    private JLabel queue3;
    private JLabel queue4;
    private JLabel queue5;
    private JLabel queue6;
    private JLabel queue7;
    private JLabel queue8;
    private JLabel queue9;
    private JLabel queue10;
    private JLabel queue11;
    private JLabel queue12;
    private JLabel queue13;
    private JLabel queue14;
    private JLabel queue15;
    private JLabel queue16;
    private JLabel queue17;
    private JLabel queue18;
    private JLabel queue19;
    private JLabel queue20;
    private JLabel timeLabel;
    private JPanel timePanel;
    private JLabel waitingListLabel;
    private JPanel testOptionsPanel;
    private JScrollPane scrollPanel;
    private JPanel waitingListPanel;
    private JPanel finalDataPanel;
    private JLabel averageWaitingTimeLabel;
    private JLabel peakHourLabel;
    private JLabel averageServiceTimeLabel;

    public MainView() {
        setTitle("Queue Management System");
        setSize(500, 100);
        setMaximumSize(new Dimension(1400, 800));
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        controller = new MainViewController(this);
        firstTestButton.addActionListener(controller);
        secondTestButton.addActionListener(controller);
        thirdTestButton.addActionListener(controller);

        setQueuesNonVisible();
        timeLabel.setVisible(false);
        waitingListLabel.setVisible(false);
        resultPanel.setVisible(false);
        scrollPanel.setVisible(false);
        averageWaitingTimeLabel.setVisible(false);
        peakHourLabel.setVisible(false);
        averageServiceTimeLabel.setVisible(true);
    }

    // Getters and setters
    public JButton getFirstTestButton() {
        return firstTestButton;
    }

    public JButton getSecondTestButton() {
        return secondTestButton;
    }

    public JButton getThirdTestButton() {
        return thirdTestButton;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getWaitingListLabel() {
        return waitingListLabel;
    }

    public JPanel getResultPanel() {
        return resultPanel;
    }

    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public JLabel getAverageWaitingTimeLabel() {
        return averageWaitingTimeLabel;
    }

    public JLabel getPeakHourLabel() {
        return peakHourLabel;
    }

    public JLabel getAverageServiceTimeLabel() {
        return averageServiceTimeLabel;
    }

    public ArrayList<JLabel> getLabels() {
        ArrayList<JLabel> labels = new ArrayList<JLabel>();

        labels.add(queue1);
        labels.add(queue2);
        labels.add(queue3);
        labels.add(queue4);
        labels.add(queue5);
        labels.add(queue6);
        labels.add(queue7);
        labels.add(queue8);
        labels.add(queue9);
        labels.add(queue10);
        labels.add(queue11);
        labels.add(queue12);
        labels.add(queue13);
        labels.add(queue14);
        labels.add(queue15);
        labels.add(queue16);
        labels.add(queue17);
        labels.add(queue18);
        labels.add(queue19);
        labels.add(queue20);

        return labels;
    }

    public void setResultPanel(JPanel resultPanel) {
        this.resultPanel = resultPanel;
    }

    // Private Functions
    private void setQueuesNonVisible() {
        queue1.setVisible(false);
        queue2.setVisible(false);
        queue3.setVisible(false);
        queue4.setVisible(false);
        queue5.setVisible(false);
        queue6.setVisible(false);
        queue7.setVisible(false);
        queue8.setVisible(false);
        queue9.setVisible(false);
        queue10.setVisible(false);
        queue11.setVisible(false);
        queue12.setVisible(false);
        queue13.setVisible(false);
        queue14.setVisible(false);
        queue15.setVisible(false);
        queue16.setVisible(false);
        queue17.setVisible(false);
        queue18.setVisible(false);
        queue19.setVisible(false);
        queue20.setVisible(false);
    }
}
