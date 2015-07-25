package com.levelup.osgi.impl;

import com.levelup.osgi.TestService;
import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Property;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.io.ByteArrayInputStream;


/**
 * Created by denis_zavadsky on 6/24/14.
 */
@Component(name = "Test Service", description = "Our testing service", immediate = true)
@Service(value = com.levelup.osgi.TestService.class)
public class TestServiceImpl implements TestService{

    private static final Logger LOG = LoggerFactory.getLogger(TestServiceImpl.class);

    @Reference
    SlingRepository slingRepository;


    @Override
    public void test() {

    }

    @Activate
    @Modified
    private void configure(ComponentContext componentContext){
//        String name = (String) componentContext.getProperties().get("my.service.name");
//        Long order = (Long) componentContext.getProperties().get("my.service.order");
//        Boolean enabled = (Boolean) componentContext.getProperties().get("my.property.enabled");
        LOG.error("Test");

    }

//    @Activate
//    public void createOrUpdateNode(){
//
//        if (slingRepository==null){
//            LOG.error("Sling Repository is null");
//        } else {
//            try {
//                //Repository repository = JcrUtils.getRepository();
//                Session session = slingRepository.loginAdministrative(null);
//                Node node = session.getNode("/content");
//                node.addNode("my.jsp","nt:file");
//                LOG.info("Child node 'my.jsp' created");
//                Node myJspNode = node.getNode("my.jsp");
//                myJspNode.addNode("jcr:content","nt:resource");
//                LOG.info("Child node 'jcr:content' created");
//                Node jcrContent = myJspNode.getNode("jcr:content");
//                String text = "<jsp:useBean id='1'/>";
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
//                ValueFactory valueFactory = session.getValueFactory();
//                Binary binary = valueFactory.createBinary(inputStream);
//                valueFactory.createValue(binary);
//                jcrContent.setProperty("jcr:data", binary);
//                LOG.info("Property 'jcr:data' added");
//                session.save();
//            } catch (RepositoryException e) {
//                LOG.error("JCR Session error",e);
//            }
//        }
//    }



}
