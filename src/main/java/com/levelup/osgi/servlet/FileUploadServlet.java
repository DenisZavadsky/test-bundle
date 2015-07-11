package com.levelup.osgi.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by denis_zavadsky on 7/17/14.
 */
@SlingServlet(paths = "/libs/upload", metatype = true, methods = "POST")
public class FileUploadServlet extends SlingAllMethodsServlet{
    private static final Logger LOG = LoggerFactory.getLogger(MyServlet.class);

    @Reference
    private SlingRepository repository;

    private static final long serialVersionUID = 1L;

    private static final String DATA_FOLDER = "/content/uploads";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }



        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + "";

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            Session session = repository.loginAdministrative(null);
            Node uploadsRoot =session.getNode(DATA_FOLDER);

                    // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {

                    LOG.debug("FileName is: " +item.getName());

//                    String filePath = uploadFolder + File.separator + fileName;
//                    File uploadedFile = new File(filePath);
//                    System.out.println(filePath);
                    // saves the file to upload directory

                    String fileName = item.getName();
                    Node file = uploadsRoot.addNode(fileName);
                    file.setPrimaryType("nt:file");
                    session.save();

                    Node jcrContent = file.addNode("jcr:content");
                    jcrContent.setPrimaryType("nt:resource");
                    jcrContent.setProperty("jcr:mimeType","application/octet-stream");
                    ValueFactory valueFactory = session.getValueFactory();
                    Binary binary = valueFactory.createBinary(item.getInputStream());
                    jcrContent.setProperty("jcr:data", binary);
                    session.save();
                    LOG.debug("File saved to node: " + file.getPath());
//                    item.write(uploadedFile);

                }
            }


        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
