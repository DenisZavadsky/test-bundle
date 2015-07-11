package com.levelup.osgi.servlet;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferenceStrategy;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by user on 01.07.14.
 */
@SlingServlet(name = "MyServlet", paths = "/libs/my_service", methods = "GET", metatype = true)

public class MyServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MyServlet.class);
    @Reference
    SlingRepository slingRepository;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOG.info("############### START");
        if (slingRepository == null) {
            LOG.error("Sling Repository is null");
        } else {
            try {
                Session session = slingRepository.loginAdministrative(null);
                String path = request.getParameter("path");
                Node node = session.getNode(path);
                LOG.info("###############Parametr node" + request.getParameter("path"));
                response.getWriter().println("<html>");
                response.getWriter().println("<body>");
                PropertyIterator iterator = node.getProperties();
                response.getWriter().println("<table>");
                while (iterator.hasNext()) {


                    Property property = iterator.nextProperty();
                    response.getWriter().println("</tr>");
                    response.getWriter().println("<td>");
                    response.getWriter().println(property.getName());
                    response.getWriter().println("</td>");
                    response.getWriter().println("<td>");
                    response.getWriter().println(property.getString());
                    response.getWriter().println("</td>");
                    response.getWriter().println("</tr>");


                }

                response.getWriter().println("<table>");
                response.getWriter().println("</body>");
                response.getWriter().println("</html>");
                response.flushBuffer();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }


        }

    }
}