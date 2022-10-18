package Model;

import Controller.MainViewController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler implements Runnable {

    private ArrayList<Queue> queues;
    private ArrayList<Client> clients;
    private int simulationTime;
    private int numberQueues;
    private int numberClients;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;

    private double averageWaitingTime;
    private double averageServiceTime;

    public static AtomicInteger currentSimulationTime;

    private File outputFile;
    private FileWriter fileWriter;

    private MainViewController observer;

    public Scheduler(int numberQueues, int numberClients, int simulationTime,
                     int minArrivalTime, int maxArrivalTime,
                     int minServiceTime, int maxServiceTime) {
        this.simulationTime = simulationTime;
        this.numberQueues = numberQueues;
        this.numberClients = numberClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;

        clients = Client.getRandomClients(numberClients, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);
        averageServiceTime = getAverageServiceTime();
        queues = new ArrayList<Queue>(0);

        currentSimulationTime = new AtomicInteger(1);

        averageWaitingTime = 0.0;

        // creating the queues and threads
        for (int i = 0; i < numberQueues; i++) {
            Queue newQueue = new Queue(i);
            queues.add(newQueue);
            Thread thread = new Thread(newQueue);
            thread.start();
        }

        // creating the file to log the outputs in
        try {
            outputFile = new File("result.txt");

            if(outputFile.createNewFile()) {
                System.out.println("File created\n");
            } else {
                System.out.println("File already exists\n");
            }

            fileWriter = new FileWriter("result.txt");
            fileWriter.write("LOG OF EVENTS\n\n");
        } catch (IOException e) {
            System.out.println("Could not create result file\n");
        }
    }

    @Override
    public void run() {
        System.out.println("Simulation started, please wait until it is finished and check the log file");

        int totalWaitingTime = 0;
        int totalPeopleProcessed = 0;

        int maxClientLoad = 0;
        int peakHour = 1;

        while (currentSimulationTime.get() <= simulationTime) {
            int index = 0;

            while (index < clients.size()) {
                if (clients.get(index).getArrivalTime().get() <= currentSimulationTime.get()) {
                    Queue bestQueue = getShortestQueue();
                    bestQueue.addClient(clients.get(index));
                    clients.remove(clients.get(index));
                } else {
                    break;
                }
            }

            int totalClientsLoad = 0;
            for (Queue queue: queues) {
                totalClientsLoad = totalClientsLoad + queue.getClients().size();
            }

            if (totalClientsLoad > maxClientLoad) {
                maxClientLoad = totalClientsLoad;
                peakHour = currentSimulationTime.get();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            writeUpdateInFile();
            observer.updateSimulation(currentSimulationTime, queues, clients);

            currentSimulationTime.getAndIncrement();
        }

        for (Queue queue: queues) {
            queue.setActiveSimulation(false);
            totalWaitingTime = totalWaitingTime + queue.getTotalWaitingTime().get();
            totalPeopleProcessed = totalPeopleProcessed + queue.getTotalPeopleProcessed().get();
        }

        double averageWaitingTime = (double)((double)totalWaitingTime / (double)totalPeopleProcessed);

        writeFinalFeedBackInFile(averageWaitingTime, peakHour, averageServiceTime);

        System.out.println("Finished writing in file");
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Could not close in file\n");
        }

        observer.finishedSimulation(averageWaitingTime, peakHour, averageServiceTime);
    }

    // Getters
    public ArrayList<Queue> getQueues() {
        return queues;
    }

    public int getNumberQueues() {
        return numberQueues;
    }

    public void setObserver(MainViewController observer) {
        this.observer = observer;
    }

    // Private Functions
    private Queue getShortestQueue() {
        Queue bestQueue = queues.get(0);

        for (Queue queue: queues) {
            if (queue.getWaitingPeriod().get() < bestQueue.getWaitingPeriod().get()) {
                bestQueue = queue;
            }
        }

        return bestQueue;
    }

    private void writeUpdateInFile() {
        try {
            fileWriter.write("Time: " + (currentSimulationTime.get()) + "\n");

            fileWriter.write("Waiting list: " + Client.getClientsString(clients) + "\n");

            for (Queue queue: queues) {
                fileWriter.write(queue.getWaitingListsString() + "\n");
            }

            fileWriter.write("\n\n");
        } catch (IOException e) {
            System.out.println("Could not write in file\n");
        }
    }

    private void writeFinalFeedBackInFile(double averageWaitingTime, int peakHour, double averageServiceTime) {
        try {
            fileWriter.write("Average waiting time: " + averageWaitingTime + "\n");

            fileWriter.write("Peak hour: " + peakHour + "\n");

            fileWriter.write("Average service time: " + averageServiceTime + "\n");

            fileWriter.write("\n\n");
        } catch (IOException e) {
            System.out.println("Could not write in file\n");
        }
    }

    private double getAverageServiceTime() {
        int totalServiceTime = 0;
        for (Client client: clients) {
            totalServiceTime = totalServiceTime + client.getServiceTime().get();
        }

        double result = (double)totalServiceTime / (double)clients.size();
        return result;
    }

}
