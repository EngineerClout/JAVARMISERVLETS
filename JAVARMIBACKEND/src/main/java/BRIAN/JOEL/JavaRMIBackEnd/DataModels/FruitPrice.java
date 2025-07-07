package BRIAN.JOEL.JavaRMIBackEnd.DataModels;

//import java.io.Serial;
import java.io.Serial;
import java.io.Serializable;

public class FruitPrice implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String fruitName;
    private double pricePerKg;

    public FruitPrice() {}

    public FruitPrice(String fruitName, double pricePerKg) {
        this.fruitName = fruitName;
        this.pricePerKg = pricePerKg;
    }

    // Getters and Setters
    public String getFruitName() { return fruitName; }
    public void setFruitName(String fruitName) { this.fruitName = fruitName; }
    public double getPricePerKg() { return pricePerKg; }
    public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }

    @Override
    public String toString() {
        return "FruitPrice{fruitName='" + fruitName + "', pricePerKg=" + pricePerKg + "}";
    }
}