package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Comparable<Client> {

    private int ID;
    private AtomicInteger arrivalTime;
    private AtomicInteger serviceTime;
    public static final int maxNumberClients = 1000;

    public Client(int ID, AtomicInteger arrivalTime, AtomicInteger serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Client o) {
        return this.getArrivalTime().get() - o.getArrivalTime().get();
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public AtomicInteger getArrivalTime() {
        return arrivalTime;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(AtomicInteger serviceTime) {
        this.serviceTime = serviceTime;
    }

    // Static Methods
    public static ArrayList<Client> getRandomClients(int numberClients,
                                              int minArrivalTime, int maxArrivalTime,
                                              int minServiceTime, int maxServiceTime) {
        ArrayList<Client> randomClients = new ArrayList<Client>();

        for (int i = 0; i < numberClients; i++) {
            int arrivalTime = getRandomNumber(minArrivalTime, maxArrivalTime);
            int serviceTime = getRandomNumber(minServiceTime, maxServiceTime);
            Client newClient = new Client(i, new AtomicInteger(arrivalTime), new AtomicInteger(serviceTime));

            randomClients.add(newClient);
        }

        Collections.sort(randomClients);
        return randomClients;
    }

    public static String getClientsString(ArrayList<Client> clients) {
        String result = "";

        for (Client client: clients) {
            result = result + "(";
            result = result + client.getID() + " " + client.getArrivalTime() + " " + client.getServiceTime();
            result = result + ") ";
        }

        return result;
    }

    // Private Methods
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
