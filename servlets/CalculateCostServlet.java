package servlets;

import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.CalFruitCost;
import BRIAN.JOEL.JavaServletLayer.BaseFruitServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/fruits/calculate")
public class CalculateCostServlet extends BaseFruitServlet {
    private static final long serialVersionUID = 1L;

    public static class CalculateCostRequest {
        private String fruitName;
        private double quantity;

        public String getFruitName() { return fruitName; }
        public void setFruitName(String fruitName) { this.fruitName = fruitName; }
        public double getQuantity() { return quantity; }
        public void setQuantity(double quantity) { this.quantity = quantity; }
    }

    public static class CalculateCostResponse {
        private boolean success;
        private String fruitName;
        private double quantity;
        private double pricePerKg;
        private double totalCost;
        private String message;

        public CalculateCostResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public CalculateCostResponse(String fruitName, double quantity, double pricePerKg, double totalCost) {
            this.success = true;
            this.fruitName = fruitName;
            this.quantity = quantity;
            this.pricePerKg = pricePerKg;
            this.totalCost = totalCost;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getFruitName() { return fruitName; }
        public double getQuantity() { return quantity; }
        public double getPricePerKg() { return pricePerKg; }
        public double getTotalCost() { return totalCost; }
        public String getMessage() { return message; }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            CalculateCostRequest req = gson.fromJson(sb.toString(), CalculateCostRequest.class);

            if (req.getFruitName() == null || req.getFruitName().trim().isEmpty()) {
                sendErrorResponse(response, "Fruit name is required", 400);
                return;
            }

            if (req.getQuantity() <= 0) {
                sendErrorResponse(response, "Quantity must be greater than 0", 400);
                return;
            }

            Double totalCost = taskRegistry.executeTask(new CalFruitCost(req.getFruitName(), req.getQuantity()));

            if (totalCost == -1.0) {
                sendJsonResponse(response, new CalculateCostResponse(false, "Fruit not found"));
            } else {
                double pricePerKg = totalCost / req.getQuantity();
                sendJsonResponse(response, new CalculateCostResponse(req.getFruitName(), req.getQuantity(), pricePerKg, totalCost));
            }

        } catch (Exception e) {
            System.err.println("Error in CalculateCostServlet: " + e.getMessage());
            sendErrorResponse(response, "Internal server error", 500);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}
