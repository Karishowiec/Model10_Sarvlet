package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezone = request.getParameter("timezone");
        ZoneId zoneId;

        if (timezone != null && !timezone.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezone);
            } catch (Exception e) {
                zoneId = ZoneId.of("UTC");
            }
        } else {
            zoneId = ZoneId.of("UTC");
        }

        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        String formattedTime = currentTime.format(formatter);

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h1>Current Time in UTC</h1>");
            out.println("<p>" + formattedTime + " UTC</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}