package com.levelup.osgi.servlet;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by user on 01.07.14.
 */
    @SlingServlet(name = "Node Servlet",paths = "/libs/nodeservlet", methods = "GET", metatype = true)

public class NodeTableServlet extends SlingAllMethodsServlet{
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Resource resource = request.getResourceResolver().getResource("/content/levelup");
        Node node = resource.adaptTo(Node.class);

        response.getWriter().println("<html>");
        response.getWriter().println("<body>");
        HashMap<String,String> properties = new HashMap<String, String>();
        try {
            PropertyIterator iterator = node.getProperties();
            response.getWriter().println("<tr>");
            while (iterator.hasNext()){


                Property property = iterator.nextProperty();
//                properties.put(property.getName(),property.getString());
                response.getWriter().println("<td>");
                response.getWriter().println(property.getName());
                response.getWriter().println("</td>");
                response.getWriter().println("<td>");
                response.getWriter().println(property.getString());
                response.getWriter().println("</td>");



            }
            response.getWriter().println("</tr>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        } catch (RepositoryException e) {
            e.printStackTrace();

        }

    }
}