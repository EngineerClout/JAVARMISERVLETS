package BRIAN.JOEL.JavaServletLayer;

import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.AddFruitPrice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/fruits/add")
public class AddFruitServlet extends BaseFruitServlet {
    private static final long serialVersionUID = 1L;

    public static class AddFruitRequest {
        private String fruitName;
        private double pricePerKg;

        // Getters and setters
        public String getFruitName() { return fruitName; }
        public void setFruitName(String fruitName) { this.fruitName = fruitName; }
        public double getPricePerKg() { return pricePerKg; }
        public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }
    }

    public static class AddFruitResponse {
        private boolean success;
        private String message;

        public AddFruitResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Read JSON from request body
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            AddFruitRequest req = gson.fromJson(sb.toString(), AddFruitRequest.class);

            if (req.getFruitName() == null || req.getFruitName().trim().isEmpty()) {
                sendErrorResponse(response, "Fruit name is required", 400);
                return;
            }

            if (req.getPricePerKg() <= 0) {
                sendErrorResponse(response, "Price must be greater than 0", 400);
                return;
            }

            // Execute RMI task
            Boolean result = taskRegistry.executeTask(new AddFruitPrice(req.getFruitName(), req.getPricePerKg()));

            if (result) {
                sendJsonResponse(response, new AddFruitResponse(true, "Fruit added successfully"));
            } else {
                sendJsonResponse(response, new AddFruitResponse(false, "Fruit already exists"));
            }

        } catch (Exception e) {
            System.err.println("Error in AddFruitServlet: " + e.getMessage());
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h3>This endpoint only supports POST. Use a form or tool like Postman.</h3>");
    }


}