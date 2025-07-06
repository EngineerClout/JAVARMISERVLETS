package servlets;

import BRIAN.JOEL.JavaRMIBackEnd.RMIClientRegistry.FruitComputeTaskRegistry;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BaseFruitServlet.java - Base servlet with common functionality
 */
public abstract class BaseFruitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected FruitComputeTaskRegistry taskRegistry;
    protected Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            taskRegistry = new FruitComputeTaskRegistry();
            gson = new Gson();
            System.out.println("Servlet initialized with RMI connection");
        } catch (Exception e) {
            throw new ServletException("Failed to connect to RMI server", e);
        }
    }

    protected void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(data));
        out.flush();
    }

    protected void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        ErrorResponse error = new ErrorResponse(message);
        sendJsonResponse(response, error);
    }

    // Error response class
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
    }
}
