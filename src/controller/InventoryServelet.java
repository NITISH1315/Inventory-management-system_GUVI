package controller;

import model.Product;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

/*
 * Servlet acting as Controller
 * Uses ArrayList as in-memory storage
 */
public class InventoryServlet extends HttpServlet {

    // Acts as database
    private static ArrayList<Product> products = new ArrayList<>();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String action = req.getParameter("action");

        try {

            if (action.equals("add")) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                int qty = Integer.parseInt(req.getParameter("quantity"));
                double price = Double.parseDouble(req.getParameter("price"));

                if (name.isEmpty() || qty < 0 || price < 0) {
                    out.println("<h3>Invalid Input</h3>");
                    return;
                }

                products.add(new Product(id, name, qty, price));
                out.println("<h3>Product Added Successfully</h3>");
            }

            else if (action.equals("update")) {
                int id = Integer.parseInt(req.getParameter("id"));

                for (Product p : products) {
                    if (p.getId() == id) {
                        p.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                        p.setPrice(Double.parseDouble(req.getParameter("price")));
                        out.println("<h3>Product Updated Successfully</h3>");
                        return;
                    }
                }
                out.println("<h3>Product Not Found</h3>");
            }

            else if (action.equals("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));

                for (Product p : products) {
                    if (p.getId() == id) {
                        products.remove(p);
                        out.println("<h3>Product Deleted Successfully</h3>");
                        return;
                    }
                }
                out.println("<h3>Product Not Found</h3>");
            }

            else if (action.equals("view")) {
                out.println("<h2>Inventory List</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Qty</th><th>Price</th></tr>");

                for (Product p : products) {
                    out.println("<tr>");
                    out.println("<td>" + p.getId() + "</td>");
                    out.println("<td>" + p.getName() + "</td>");
                    out.println("<td>" + p.getQuantity() + "</td>");
                    out.println("<td>" + p.getPrice() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

        } catch (Exception e) {
            out.println("<h3>Error: Invalid Data Entered</h3>");
        }
    }
}
