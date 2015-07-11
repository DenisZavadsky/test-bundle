<%@ page import="javax.jcr.Node" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.1" %>
<sling:defineObjects/>

<html>
<head>
    <title>XPATH Test page</title>
    <script src="http://code.jquery.com/jquery-1.11.1.js"></script>
    <script src="/libs/levelup/js/jquery.tablesorter.js"></script>
    <link rel="stylesheet" href="/libs/levelup/css/style.css"/>
    <script>
        $(document).ready(function(){
            var element =$("button");
            element.click(function(){
                var query = $("#xpath-query").val();
                if (query.length>0) {
                    $.ajax({
                        url:"/libs/xpath_query",
                        data:{
                            'query':query
                        },
                        type: "POST",
                        dataType: 'text',
                        success: function(data){
                            $(".content_wrapper").html(data);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>

<div class="main_query">

    XPath Query: <input id="xpath-query" name="query" type="text">
    <button name="submit_query" >Execute Query</button>
</div>
<div class="main_content">
    <div class="content_wrapper">

    </div>
</div>
</body>
</html>

