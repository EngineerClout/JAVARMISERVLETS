package BRIAN.JOEL.JavaServletLayer;

//package BRIAN.JOEL.JavaServletLayer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/testServlet")
//public class TestServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/plain");
//        response.getWriter().write("Test servlet is working!");
//    }
//}

@WebServlet("/testForServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        response.getWriter().write("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Servlet Status</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body {
                        background-color: #f8f9fa;
                        font-family: 'Segoe UI', sans-serif;
                        padding: 2rem;
                    }
                    .container {
                        max-width: 800px;
                        margin: auto;
                        margin-top: 5rem;
                    }
                    .card {
                        border-radius: 0.75rem;
                        box-shadow: 0 0 12px rgba(0,0,0,0.1);
                    }
                    .section-title {
                        font-size: 1.25rem;
                        font-weight: 600;
                        margin-top: 1.5rem;
                    }
                    pre {
                        background-color: #f1f1f1;
                        padding: 1rem;
                        border-radius: 0.5rem;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="card p-4">
                        <h3 class="text-success">✔ Servlet Deployed Successfully</h3>
                        <p>The backend server is up and running. You may now proceed to set up the mobile application for integration testing.</p>

                        <div class="section-title">1. Configure Android App in Android Studio</div>
                        <ul>
                            <li>Ensure the Android device or emulator is on the <strong>same network</strong> as the machine running this server.</li>
                            <li>Update <code>BASE_URL</code> in <code>NetworkModule.kt</code>:</li>
                        </ul>
                        <pre>
For emulator:     "http://10.0.2.2:8080/"
For real device:  "http://YOUR_SERVER_IP:8080/"
// Find IP using: ipconfig (on Windows)
                        </pre>
                        <ul>
                            <li>Grant internet permission in <code>AndroidManifest.xml</code>:</li>
                        </ul>
                        <pre>
&lt;uses-permission android:name="android.permission.INTERNET"/&gt;
                        </pre>

                        <div class="section-title">2. Build and Run Android App</div>
                        <ul>
                            <li>Sync project with Gradle files</li>
                            <li>Build and install the app on the emulator or device</li>
                            <li>Ensure the device can access the server via the configured URL</li>
                        </ul>

                        <div class="section-title">3. Testing</div>
                        <ul>
                            <li>Verify full end-to-end functionality between the Android app and the servlet backend</li>
                        </ul>

                        <p class="mt-4 text-muted">If you encounter connection issues, verify firewall settings and ensure port 8080 is open.</p>
                    </div>
                </div>
            </body>
            </html>
        """);
    }
}
