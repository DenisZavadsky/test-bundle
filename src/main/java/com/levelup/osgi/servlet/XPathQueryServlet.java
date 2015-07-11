package com.levelup.osgi.servlet;

import com.google.gson.Gson;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by denis_zavadsky on 7/5/14.
 */
@SlingServlet(name = "XPath Query Servlet", paths = "/libs/xpath_query", methods = "POST", metatype = true)
public class XPathQueryServlet extends SlingAllMethodsServlet{
    private static final Logger LOG = LoggerFactory.getLogger(XPathQueryServlet.class);

    @Reference
    private SlingRepository repository;


    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String expression = request.getParameter("query");
        if (expression!=null && expression.length()>0){
            try {
                Session session = repository.loginAdministrative(null);
                QueryManager queryManager = session.getWorkspace().getQueryManager();

                Query query = queryManager.createQuery(expression,Query.XPATH);
                QueryResult result = query.execute();

                NodeIterator iterator = result.getNodes();
                Gson gson = new Gson();
                HashMap<String,HashMap> nodes = new HashMap<String, HashMap>();
                while (iterator.hasNext()){
                    Node node = iterator.nextNode();
                    HashMap<String,String> properties = new HashMap<String, String>();
                    try {
//                        PropertyIterator propertyIterator = node.getProperties();
//                        while (iterator.hasNext()){
//                            Property property = propertyIterator.nextProperty();
//                            properties.put(property.getName(),property.getString());
//                        }
                        properties.put("name",node.getName());
                        properties.put("path",node.getPath());
                        properties.put("jcr:primaryType",node.getProperty("jcr:primaryType").getString());
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                    nodes.put(node.getName(),properties);
                }
                String jsonValue = gson.toJson(nodes);
                response.getWriter().print(jsonValue);
                response.flushBuffer();
            } catch (RepositoryException e) {
                LOG.error("Session exception",e);
            }
        }
    }
}
