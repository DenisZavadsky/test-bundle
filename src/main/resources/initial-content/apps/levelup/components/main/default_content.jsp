<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<div>Requested page:Edit Selector</div>

<div>Page resource is: <%=resource.getPath()%></div>
<div>Property 1: <%=currentNode.getProperty("property1").getString()%></div>
<div>Property 2: <%=currentNode.getProperty("property2").getString()%></div>