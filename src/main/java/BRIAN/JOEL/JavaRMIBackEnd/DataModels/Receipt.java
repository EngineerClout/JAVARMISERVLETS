package BRIAN.JOEL.JavaRMIBackEnd.DataModels;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> items;
    private double totalCost;
    private double amountPaid;
    private double change;
    private String cashier;
//    private LocalDateTime timestamp;
    private String timestamp;

    public Receipt(List<String> items, double totalCost, double amountPaid, String cashier) {
        this.items = items;
        this.totalCost = totalCost;
        this.amountPaid = amountPaid;
        this.change = amountPaid - totalCost;
        this.cashier = cashier;
//        this.timestamp = LocalDateTime.parse(LocalDateTime.now().toString());
        this.timestamp = LocalDateTime.now().toString();
//        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    }

    // Getters
    public List<String> getItems() { return items; }
    public double getTotalCost() { return totalCost; }
    public double getAmountPaid() { return amountPaid; }
    public double getChange() { return change; }
    public String getCashier() { return cashier; }
//    public LocalDateTime getTimestamp() { return timestamp; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== RECEIPT ==========\n");
        sb.append("Date: ").append(timestamp).append("\n");
        sb.append("Cashier: ").append(cashier).append("\n");
        sb.append("Items:\n");
        for (String item : items) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append("Total Cost: Ksh").append(String.format("%.2f", totalCost)).append("\n");
        sb.append("Amount Paid: Ksh").append(String.format("%.2f", amountPaid)).append("\n");
        sb.append("Change: Ksh").append(String.format("%.2f", change)).append("\n");
        sb.append("============================");
        return sb.toString();
    }
}
