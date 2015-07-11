package com.levelup.osgi.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by denis_zavadsky on 3/9/15.
 */
@Component(name = "Test Resource", description = "Our testing resource", immediate = true)
//@Service(value = com.levelup.osgi.impl.TestResource.class)
@Service(Object.class)
@Properties({
//        @Property(name = "service.description", value = "JAX-RS Simple Root Resource"),
//        @Property(name = "service.vendor", value = "The Apache Software Foundation"),
//        @Property(name="sling.servlet.paths", value={
//        "/demo/foo"
//        }),
        @Property(name="javax.ws.rs", boolValue=true)

})
@Path("/test-resource")
public class TestResource{

    @GET
    @Produces("text/plain")
    public String greeting() {
        return "hello world";
    }
}
