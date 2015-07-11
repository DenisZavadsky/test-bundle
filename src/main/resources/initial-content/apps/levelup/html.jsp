<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<html>
<head>
    <title>Test page</title>
</head>
<body>
<sling:include resource="${resource}" resourceType="/apps/levelup/components/layout/header.jsp"/>
<sling:include path="/content/levelup/components/default" resourceType="/apps/levelup/components/main/default_content.jsp"/>
<sling:include resource="${resource}" resourceType="/apps/levelup/components/layout/footer.jsp"/>
</body>
</html>

