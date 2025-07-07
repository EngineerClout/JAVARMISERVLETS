package BRIAN.JOEL.JavaRMIBackEnd.RMIServer;

import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Compute;
import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FruitComputeEngine extends UnicastRemoteObject implements Compute {
    private static final long serialVersionUID = 1L;

    // In-memory fruit price database
    private static final Map<String, Double> fruitPriceTable = new ConcurrentHashMap<>();

    // Initialize with some default fruits
    static {
        fruitPriceTable.put("apple", 2.50);
        fruitPriceTable.put("banana", 1.20);
        fruitPriceTable.put("orange", 3.00);
        fruitPriceTable.put("mango", 4.50);
    }

    public FruitComputeEngine() throws RemoteException {
        super();
    }

    @Override
    public <T> T executeTask(Task<T> task) throws RemoteException {
        System.out.println("Executing task: " + task.getClass().getSimpleName());
        return task.execute();
    }

    // Static methods for database operations
    public static synchronized boolean addFruit(String name, double price) {
        if (fruitPriceTable.containsKey(name.toLowerCase())) {
            return false; // Fruit already exists
        }
        fruitPriceTable.put(name.toLowerCase(), price);
        return true;
    }

    public static synchronized boolean updateFruit(String name, double price) {
        if (!fruitPriceTable.containsKey(name.toLowerCase())) {
            return false; // Fruit doesn't exist
        }
        fruitPriceTable.put(name.toLowerCase(), price);
        return true;
    }

    public static synchronized boolean deleteFruit(String name) {
        return fruitPriceTable.remove(name.toLowerCase()) != null;
    }

    public static synchronized Double getFruitPrice(String name) {
        return fruitPriceTable.get(name.toLowerCase());
    }

    public static synchronized Map<String, Double> getAllFruits() {
        return new ConcurrentHashMap<>(fruitPriceTable);
    }

    // Main method to start RMI server
    public static void main(String[] args) {
        try {
            // Create RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create compute engine instance
            FruitComputeEngine engine = new FruitComputeEngine();

            // Bind the remote object in the registry
            registry.rebind("FruitComputeEngine", engine);

            System.out.println("FruitComputeEngine server ready on port 1099");
            System.out.println("Fruit database initialized with: " + fruitPriceTable);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}