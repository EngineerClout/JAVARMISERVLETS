package BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations;

import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.RMIServer.FruitComputeEngine;

public class UpdateFruitPrice implements Task<Boolean> {
    private static final long serialVersionUID = 1L;

    private String fruitName;
    private double pricePerKg;

    public UpdateFruitPrice(String fruitName, double pricePerKg) {
        this.fruitName = fruitName;
        this.pricePerKg = pricePerKg;
    }

    @Override
    public Boolean execute() {
        System.out.println("Updating fruit: " + fruitName + " to Ksh" + pricePerKg + "/kg");
        return FruitComputeEngine.updateFruit(fruitName, pricePerKg);
    }
}