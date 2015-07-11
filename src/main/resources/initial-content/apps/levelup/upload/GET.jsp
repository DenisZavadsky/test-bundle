<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<html>
<head>
    <title>Test page</title>
</head>
<body>
<form method="post" action="/libs/upload" enctype="multipart/form-data">
    Select file to upload:
    <input type="file" name="dataFile" id="fileChooser"/><br/><br/>
    <input type="submit" value="Upload" />
</form>
</body>
</html>

