<%@ page language="java"
         import="java.util.*,
                  java.text.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'includes/home.css';</style>
</head>
<body>

            	<%if(request.getAttribute("msg")!=null){%>
                                  
                                      <%=(String)request.getAttribute("msg")%>
                                    
                 <%}else{%>
                                  
                                      Request has been declined.  Thank you for your time.
              	<%} %>

			

