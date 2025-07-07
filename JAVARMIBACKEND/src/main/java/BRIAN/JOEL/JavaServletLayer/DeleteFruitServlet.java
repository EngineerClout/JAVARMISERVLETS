package BRIAN.JOEL.JavaServletLayer;

import BRIAN.JOEL.JavaRMIBackEnd.TaskImplementations.DeleteFruitPrice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/fruits/delete")
public class DeleteFruitServlet extends BaseFruitServlet {
    private static final long serialVersionUID = 1L;

    public static class DeleteFruitResponse {
        private boolean success;
        private String message;

        public DeleteFruitResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String fruitName = request.getParameter("name");

            if (fruitName == null || fruitName.trim().isEmpty()) {
                sendErrorResponse(response, "Fruit name parameter is required", 400);
                return;
            }

            Boolean result = taskRegistry.executeTask(new DeleteFruitPrice(fruitName));

            if (result) {
                sendJsonResponse(response, new DeleteFruitResponse(true, "Fruit deleted successfully"));
            } else {
                sendJsonResponse(response, new DeleteFruitResponse(false, "Fruit not found"));
            }

        } catch (Exception e) {
            System.err.println("Error in DeleteFruitServlet: " + e.getMessage());
            sendErrorResponse(response, "Internal server error", 500);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}