package BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations;


import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.RMIServer.FruitComputeEngine;

public class CalFruitCost implements Task<Double> {
    private static final long serialVersionUID = 1L;

    private String fruitName;
    private double quantity;

    public CalFruitCost(String fruitName, double quantity) {
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    @Override
    public Double execute() {
        System.out.println("Calculating cost for " + quantity + "kg of " + fruitName);
        Double price = FruitComputeEngine.getFruitPrice(fruitName);
        if (price == null) {
            return -1.0; // Fruit not found
        }
        return price * quantity;
    }
}