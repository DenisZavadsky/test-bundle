package com.levelup.osgi.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * Created by denis_zavadsky on 6/28/14.
 */
@SlingServlet(name = "JSON Servlet",extensions = "json",resourceTypes = "sling/servlet/default", methods = "GET", metatype = true)
public class JsonServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Resource resource = request.getResource();
        Resource res = request.getResourceResolver().getResource("");

        Node node = resource.adaptTo(Node.class);

        Gson gson = new Gson();
        HashMap<String,String> properties = new HashMap<String, String>();
        try {
            PropertyIterator iterator = node.getProperties();
            while (iterator.hasNext()){
                Property property = iterator.nextProperty();
                properties.put(property.getName(),property.getString());
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        String jsonValue = gson.toJson(properties);

        response.getWriter().print(jsonValue);
        response.flushBuffer();
    }
}
