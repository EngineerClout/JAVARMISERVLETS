package BRIAN.JOEL.JavaServletLayer;

import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.UpdateFruitPrice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/fruits/update")
public class UpdateFruitServlet extends BaseFruitServlet {
    private static final long serialVersionUID = 1L;

    public static class UpdateFruitRequest {
        private String fruitName;
        private double pricePerKg;

        public String getFruitName() { return fruitName; }
        public void setFruitName(String fruitName) { this.fruitName = fruitName; }
        public double getPricePerKg() { return pricePerKg; }
        public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }
    }

    public static class UpdateFruitResponse {
        private boolean success;
        private String message;

        public UpdateFruitResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            UpdateFruitRequest req = gson.fromJson(sb.toString(), UpdateFruitRequest.class);

            if (req.getFruitName() == null || req.getFruitName().trim().isEmpty()) {
                sendErrorResponse(response, "Fruit name is required", 400);
                return;
            }

            if (req.getPricePerKg() <= 0) {
                sendErrorResponse(response, "Price must be greater than 0", 400);
                return;
            }

            Boolean result = taskRegistry.executeTask(new UpdateFruitPrice(req.getFruitName(), req.getPricePerKg()));

            if (result) {
                sendJsonResponse(response, new UpdateFruitResponse(true, "Fruit updated successfully"));
            } else {
                sendJsonResponse(response, new UpdateFruitResponse(false, "Fruit not found"));
            }

        } catch (Exception e) {
            System.err.println("Error in UpdateFruitServlet: " + e.getMessage());
            sendErrorResponse(response, "Internal server error", 500);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}