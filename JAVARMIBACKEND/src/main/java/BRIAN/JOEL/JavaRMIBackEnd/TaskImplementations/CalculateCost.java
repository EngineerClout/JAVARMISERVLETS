package BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations;

// CalculateCost AND THEN Generate Receipt

import BRIAN.JOEL.JavaRMIBackEnd.DataModels.Receipt;
import BRIAN.JOEL.JavaRMIBackEnd.Interfaces.Task;
import BRIAN.JOEL.JavaRMIBackEnd.RMIServer.FruitComputeEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculateCost implements Task<Receipt> {
    private static final long serialVersionUID = 1L;

    private Map<String, Double> fruitQuantities; // fruit name -> quantity
    private double amountPaid;
    private String cashier;

    public CalculateCost(Map<String, Double> fruitQuantities, double amountPaid, String cashier) {
        this.fruitQuantities = fruitQuantities;
        this.amountPaid = amountPaid;
        this.cashier = cashier;
    }

    @Override
    public Receipt execute() {
        System.out.println("Generating receipt for cashier: " + cashier);

        List<String> items = new ArrayList<>();
        double totalCost = 0.0;

        for (Map.Entry<String, Double> entry : fruitQuantities.entrySet()) {
            String fruitName = entry.getKey();
            double quantity = entry.getValue();

            Double price = FruitComputeEngine.getFruitPrice(fruitName);
            if (price != null) {
                double cost = price * quantity;
                totalCost += cost;
                items.add(String.format("%s: %.2fkg @ Ksh%.2f/kg = Ksh%.2f",
                        fruitName, quantity, price, cost));
            }else {
                items.add(fruitName + ": Not available");
                System.err.println("Fruit not found in price map: " + fruitName);
            }


        }
        if (items.isEmpty()) {
            throw new RuntimeException("No valid fruits were priced. Check fruit names.");
        }
        return new Receipt(items, totalCost, amountPaid, cashier);
    }
}