package BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations;

import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.RMIServer.FruitComputeEngine;

public class DeleteFruitPrice implements Task<Boolean> {
    private static final long serialVersionUID = 1L;

    private String fruitName;

    public DeleteFruitPrice(String fruitName) {
        this.fruitName = fruitName;
    }

    @Override
    public Boolean execute() {
        System.out.println("Deleting fruit: " + fruitName);
        return FruitComputeEngine.deleteFruit(fruitName);
    }
}