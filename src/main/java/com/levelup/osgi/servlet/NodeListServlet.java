package com.levelup.osgi.servlet;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by denis_zavadsky on 7/12/14.
 */
@SlingServlet(name = "XPath Query Node List Servlet", paths = "/libs/xpath_list", methods = "POST", metatype = true)
public class NodeListServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(NodeListServlet.class);

    private final static String QUERY_PARAMETER = "query";
    private final static String NODE_NAME_KEY = "name";
    private final static String NODE_PATH_KEY = "path";
    private final static String NODE_TYPE_KEY = "primaryType";

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String expression = request.getParameter(QUERY_PARAMETER);
        PrintWriter writer = response.getWriter();
        if (expression!=null && !expression.isEmpty()) {
            try {
                writeTableHeader(writer);
                Iterator<Resource> iterator = request.getResourceResolver().findResources(expression, Query.XPATH);
                while (iterator.hasNext()) {
                    Resource current = iterator.next();
                    Node cureentNode = current.adaptTo(Node.class);
                    HashMap<String, String> properties = new HashMap<String, String>();
                    properties.put(NODE_NAME_KEY, cureentNode.getName());
                    properties.put(NODE_PATH_KEY, cureentNode.getPath());
                    properties.put(NODE_TYPE_KEY, cureentNode.getProperty("jcr:primaryType").getString());
                    writeNode(properties, writer);
                }
                writeCloseTableTags(writer);
            } catch (RepositoryException re){
                LOG.error("NodeListServlet:",re);
            }
        }


    }

    private void writeCloseTableTags(PrintWriter writer) {
        writer.println("</tbody>");
        writer.println("</table>");
    }

    private void writeNode(HashMap<String, String> properties, PrintWriter writer) {
        writer.println("<tr>");
        writer.println("<td>");
        writer.println(properties.get(NODE_PATH_KEY));
        writer.println("</td>");
        writer.println("<td>");
        writer.println(properties.get(NODE_NAME_KEY));
        writer.println("</td>");
        writer.println("<td>");
        writer.println(properties.get(NODE_TYPE_KEY));
        writer.println("</td>");
        writer.println("</td>");
    }

    private void writeTableHeader(PrintWriter writer) {
        writer.println("<table id=\"myTable\" class=\"tablesorter\">");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>Path</th>");
        writer.println("<th>Name</th>");
        writer.println("<th>Primary Type</th>");
        writer.println("</tr>");
        writer.println("</thead>");
        writer.println("<tbody>");
    }
}
