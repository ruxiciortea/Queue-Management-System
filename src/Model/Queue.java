package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {

    private int ID;
    private ArrayBlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private boolean activeSimulation;

    private AtomicInteger totalWaitingTime;
    private AtomicInteger totalPeopleProcessed;

    public Queue(int ID) {
        this.ID = ID;
        clients = new ArrayBlockingQueue<Client>(Client.maxNumberClients);
        waitingPeriod = new AtomicInteger(0);
        activeSimulation = true;

        totalWaitingTime = new AtomicInteger(0);
        totalPeopleProcessed = new AtomicInteger(0);
    }

    // Getters and Setters
    public ArrayBlockingQueue<Client> getClients() {
        return clients;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public boolean isActiveSimulation() {
        return activeSimulation;
    }

    public AtomicInteger getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public AtomicInteger getTotalPeopleProcessed() {
        return totalPeopleProcessed;
    }

    public void setActiveSimulation(boolean activeSimulation) {
        this.activeSimulation = activeSimulation;
    }

    // Public Methods
    public void run() {
        while (activeSimulation) {
            if (clients.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                continue;
            }

            Client currentClient = clients.peek();

            if (currentClient == null) {
                continue;
            }

            totalWaitingTime.getAndAdd(Scheduler.currentSimulationTime.get() - currentClient.getArrivalTime().get());
            totalPeopleProcessed.getAndIncrement();

            AtomicInteger currentServiceTime = currentClient.getServiceTime();

            while (currentServiceTime.get() > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentServiceTime.getAndDecrement();
                currentClient.setServiceTime(currentServiceTime);
                waitingPeriod.getAndAdd(-1);
            }

            clients.remove(currentClient);
        }
    }

    public void addClient(Client client) {
        clients.add(client);
        waitingPeriod.getAndAdd(client.getServiceTime().get());
    }

    public String getWaitingListsString() {
        String result = "Queue " + ID + ": ";

        for (Client client: clients) {
            result = result + "(";
            result = result + client.getID() + " " + client.getArrivalTime() + " " + client.getServiceTime();
            result = result + ") ";
        }

        if (clients.isEmpty()) {
            result = result + "closed";
        }

        result = result + "      waiting time: " + waitingPeriod.get();

        return result;
    }

}
