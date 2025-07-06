package BRIAN.JOEL.JavaServletLayer;

import BRIAN.JOEL.JavaRMIBackEnd.DataModels.Receipt;
import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.CalculateCost;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/receipt/generate")
public class GenerateReceiptServlet extends BaseFruitServlet {
    private static final long serialVersionUID = 1L;

    public static class GenerateReceiptRequest {
        private Map<String, Double> fruitQuantities;
        private double amountPaid;
        private String cashier;

        public Map<String, Double> getFruitQuantities() { return fruitQuantities; }
        public void setFruitQuantities(Map<String, Double> fruitQuantities) { this.fruitQuantities = fruitQuantities; }
        public double getAmountPaid() { return amountPaid; }
        public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
        public String getCashier() { return cashier; }
        public void setCashier(String cashier) { this.cashier = cashier; }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            GenerateReceiptRequest req = gson.fromJson(sb.toString(), GenerateReceiptRequest.class);

            if (req.getFruitQuantities() == null || req.getFruitQuantities().isEmpty()) {
                sendErrorResponse(response, "Fruit quantities are required", 400);
                return;
            }

            if (req.getCashier() == null || req.getCashier().trim().isEmpty()) {
                sendErrorResponse(response, "Cashier name is required", 400);
                return;
            }

            if (req.getAmountPaid() < 0) {
                sendErrorResponse(response, "Amount paid must be non-negative", 400);
                return;
            }

            System.out.println("About to execute CalculateCost with:");
            System.out.println("Fruit quantities: " + req.getFruitQuantities());
            System.out.println("Amount paid: " + req.getAmountPaid());
            System.out.println("Cashier: " + req.getCashier());

            Receipt receipt = taskRegistry.executeTask(new CalculateCost(req.getFruitQuantities(), req.getAmountPaid(), req.getCashier()));
            sendJsonResponse(response, receipt);

        } catch (Exception e) {
            System.err.println("Error in GenerateReceiptServlet: " + e.getMessage());
            e.printStackTrace();
//            sendErrorResponse(response, "Internal server", 400);
            System.out.println("Request Data: " + sb);
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
