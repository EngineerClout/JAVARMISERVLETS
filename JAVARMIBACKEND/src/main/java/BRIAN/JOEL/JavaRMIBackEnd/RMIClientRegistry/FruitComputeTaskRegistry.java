package BRIAN.JOEL.JavaRMIBackEnd.RMIClientRegistry;

import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Compute;
import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.AddFruitPrice;
import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.CalFruitCost;
import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.UpdateFruitPrice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FruitComputeTaskRegistry {
    private Compute compute;

    public FruitComputeTaskRegistry() throws Exception {
        // Look up the remote object
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        compute = (Compute) registry.lookup("FruitComputeEngine");
        System.out.println("Connected to FruitComputeEngine");
    }

    public <T> T executeTask(Task<T> task) throws Exception {
        return compute.executeTask(task);
    }

    // Test client
    public static void main(String[] args) {
        try {
            FruitComputeTaskRegistry client = new FruitComputeTaskRegistry();

            // Testing adding a fruit
            Boolean added = client.executeTask(new AddFruitPrice("grape", 5.00));
            System.out.println("Grape added: " + added);

            // Testing calculating cost
            Double cost = client.executeTask(new CalFruitCost("apple", 2.5));
            System.out.println("Cost of 2.5kg apples: Ksh" + cost);

            // Testing updating price
            Boolean updated = client.executeTask(new UpdateFruitPrice("banana", 1.50));
            System.out.println("Banana price updated: " + updated);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
