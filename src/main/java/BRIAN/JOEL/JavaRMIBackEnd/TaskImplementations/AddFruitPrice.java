package BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations;


import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.RMIServer.FruitComputeEngine;

public class AddFruitPrice implements Task<Boolean> {
    private static final long serialVersionUID = 1L;

    private String fruitName;
    private double pricePerKg;

    public AddFruitPrice(String fruitName, double pricePerKg) {
        this.fruitName = fruitName;
        this.pricePerKg = pricePerKg;
    }

    @Override
    public Boolean execute() {
        System.out.println("Adding fruit: " + fruitName + " at Ksh" + pricePerKg + "/kg");
        return FruitComputeEngine.addFruit(fruitName, pricePerKg);
    }
}