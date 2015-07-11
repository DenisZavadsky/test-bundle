<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<html>
<head>
    <title>Test page</title>
</head>
<body>
<div>Requested page: GET.jsp</div>
<div>Page title: <%=resource.adaptTo(Node.class).getProperty("pageTitle").getString()%></div>
</body>
</html>

