<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<html>
<head>
    <title>Home jsp page </title>
    <script type="text/javascript" src="/libs/jquery.js">

    </script>
    <script>

        $(document).ready(function(){
            alert("Loaded!");
        });
    </script>
</head>
<body>
<div>Requested page:Edit Selector</div>
<div>Page title: <%=resource.adaptTo(Node.class).getProperty("pageTitle").getString()%></div>
</body>
</html>