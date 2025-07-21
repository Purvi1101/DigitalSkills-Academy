import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/InquiryServlet")
public class InquiryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name    = request.getParameter("name");
        String email   = request.getParameter("email");
        String phone   = request.getParameter("phone");
        String course  = request.getParameter("course");
        String message = request.getParameter("message");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/digitalskills?useSSL=false","root","root"
            );
            Statement st = con.createStatement();
            String sql = "INSERT INTO inquiry(name, email, phone, course, message) VALUES ("
                + "'" + name + "','" + email + "','" + phone + "','" + course + "','" + message + "')";
            int rows = st.executeUpdate(sql);

            // Begin full HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'><head>");
            out.println("  <meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'>");
            out.println("  <title>Thank You - DigitalSkills Academy</title>");
            // link to your existing CSS (adjust path if needed)
            out.println("  <link rel='stylesheet' href='inquiry.css'>");
            out.println("</head><body>");

            // Header / Navbar (reuse your markup)
            out.println("  <header><div class='container'><h1 class='logo'>DigitalSkills Academy</h1><nav><ul>"
                + "<li><a href='home.html'>Home</a></li>"
                + "<li><a href='service.html'>Services</a></li>"
                + "<li><a href='about.html'>About</a></li>"
                + "<li><a href='gallery.html'>Gallery</a></li>"
                + "<li><a href='faq.html'>FAQ</a></li>"
                + "<li><a href='testimonial.html'>Testimonials</a></li>"
                + "<li><a href='blog.html'>Blog</a></li>"
                + "<li><a href='inquiry.html'>Inquiry</a></li>"
                + "<li><a href='contact.html'>Contact</a></li>"
                + "</ul></nav></div></header>");

            // Thank You Card
            out.println("<section class='inquiry-form-section' style='text-align:center;padding:80px 20px;'>");
            if (rows > 0) {
                out.println("  <div class='thankyou-card'>");
                out.println("    <h2>Thank You, " + name + "!</h2>");
                out.println("    <p>Your inquiry has been received.</p>");
                out.println("    <p>We will reach out to you at <strong>" + email + "</strong> soon.</p>");
                out.println("    <a href='home.html' class='btn'>Back to Home</a>");
                out.println("  </div>");
            } else {
                out.println("  <div class='thankyou-card error'>");
                out.println("    <h2>Oops!</h2>");
                out.println("    <p>There was a problem submitting your inquiry.</p>");
                out.println("    <a href='inquiry.html' class='btn'>Try Again</a>");
                out.println("  </div>");
            }
            out.println("</section>");

            // Footer
            out.println("  <footer><p>&copy; 2025 DigitalSkills Academy | All Rights Reserved</p></footer>");
            out.println("</body></html>");

            st.close();
            con.close();
        }
        catch (Exception e) {
            out.println("<h3 style='color:red;'>ERROR: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}
