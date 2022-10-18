package Controller;

import Model.Client;
import Model.Queue;
import Model.Scheduler;
import View.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

enum TestOption {
    First,
    Second,
    Third
}

public class MainViewController implements ActionListener {

    private int simulationTime;
    private int minServiceTime;
    private int maxServiceTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberQueues;
    private int numberClients;

    private MainView view;
    private Scheduler scheduler;

    private TestOption currentTestOption;

    public MainViewController(MainView mainView){
        view = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getFirstTestButton())) {
            currentTestOption = TestOption.First;
            setUpSimulationValues();
        } else if (e.getSource().equals(view.getSecondTestButton())) {
            currentTestOption = TestOption.Second;
            setUpSimulationValues();
        } else if (e.getSource().equals(view.getThirdTestButton())) {
            currentTestOption = TestOption.Third;
            setUpSimulationValues();
        }
    }

    // Public Functions
    public void updateSimulation(AtomicInteger simulationTime, ArrayList<Queue> queues, ArrayList<Client> clients) {
        view.getTimeLabel().setText("Time: " + (simulationTime.get()) + "\n");
        view.getTimeLabel().setVisible(true);

        view.getWaitingListLabel().setText("Waiting list: " + Client.getClientsString(clients) + "\n");
        view.getWaitingListLabel().setVisible(true);

        for (int i = 0; i < queues.size(); i++) {
            view.getLabels().get(i).setText(queues.get(i).getWaitingListsString() + "\n");
        }
    }

    public void finishedSimulation(double averageWaitingTime, int peakHour, double averageServiceTime) {
        setButtonsEnabled(true);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        view.getAverageWaitingTimeLabel().setText("Average waiting time: " + decimalFormat.format(averageWaitingTime));
        view.getPeakHourLabel().setText("Peak hour: " + peakHour);
        view.getAverageServiceTimeLabel().setText("Average service time: " + decimalFormat.format(averageServiceTime));
        view.getAverageWaitingTimeLabel().setVisible(true);
        view.getPeakHourLabel().setVisible(true);
        view.getAverageServiceTimeLabel().setVisible(true);

        if (currentTestOption.equals(TestOption.First)) {
            view.setSize(600, 280);
        } else if (currentTestOption.equals(TestOption.Second)) {
            view.setSize(700, 370);
        } else if (currentTestOption.equals(TestOption.Third)) {
            view.setSize(800, 670);
        }

        view.setLocationRelativeTo(null);
    }

    // Private Functions
    void setUpSimulationValues() {
        if (currentTestOption.equals(TestOption.First)) {
            numberClients = 4;
            numberQueues = 2;
            simulationTime = 60;
            minArrivalTime = 2;
            maxArrivalTime = 30;
            minServiceTime = 2;
            maxServiceTime = 4;
        } else if (currentTestOption.equals(TestOption.Second)) {
            numberClients = 50;
            numberQueues = 5;
            simulationTime = 60;
            minArrivalTime = 2;
            maxArrivalTime = 40;
            minServiceTime = 1;
            maxServiceTime = 7;
        } else if (currentTestOption.equals(TestOption.Third)) {
            numberClients = 1000;
            numberQueues = 20;
            simulationTime = 200;
            minArrivalTime = 10;
            maxArrivalTime = 100;
            minServiceTime = 3;
            maxServiceTime = 9;
        }

        setButtonsEnabled(false);

        showLabels();
        scheduler = new Scheduler(numberQueues, numberClients, simulationTime,
                minArrivalTime, maxArrivalTime,
                minServiceTime, maxServiceTime);
        scheduler.setObserver(this);
        Thread mainThread = new Thread(scheduler);
        mainThread.start();
    }

    private void showLabels() {
        view.getAverageWaitingTimeLabel().setVisible(false);
        view.getPeakHourLabel().setVisible(false);
        view.getAverageServiceTimeLabel().setVisible(false);

        for (int i = 0; i < numberQueues; i++) {
            view.getLabels().get(i).setText("Queue " + i + ": closed");
            view.getLabels().get(i).setVisible(true);
        }

        for (int i = numberQueues; i < 20; i++) {
            view.getLabels().get(i).setVisible(false);
        }

        if (currentTestOption.equals(TestOption.First)) {
            view.setSize(600, 220);
        } else if (currentTestOption.equals(TestOption.Second)) {
            view.setSize(700, 300);
        } else if (currentTestOption.equals(TestOption.Third)) {
            view.setSize(800, 630);
        }

        view.getResultPanel().setVisible(true);
        view.getScrollPanel().setVisible(true);
        view.setLocationRelativeTo(null);
    }

    private void setButtonsEnabled(boolean visibility) {
        view.getFirstTestButton().setEnabled(visibility);
        view.getSecondTestButton().setEnabled(visibility);
        view.getThirdTestButton().setEnabled(visibility);
    }

}
