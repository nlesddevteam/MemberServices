<%@ page contentType="text/html;charset=windows-1252"
         import=" java.util.*,
                  com.esdnl.util.*"
         isThreadSafe="false"%>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Member Services - PDReg</title>
    
    <script type="text/javascript">
      function customizeWindow()
      { 
        self.resizeTo(300,400);
      }
    </script>
  </head>
  
  <body onload="customizeWindow();">
    <table width="100%" celspacing="0" cellpadding="0">
      <tr>
        <td width="100%"><img src="images/cal_title_pt1.gif"><br><br></td>
      </tr>
      <tr>
        <td width="100%" style="font-weight:bold;color:#FF0000;">
          <%=request.getAttribute("msg").toString()%>
        </td>
      </tr>
      <%
        if(!StringUtils.isEmpty(request.getParameter("action")))
        {
          out.println("<tr><td width='100%' style='font-weight:bold;color:#FF0000;'>");
          out.println("\t<form action='" + request.getParameter("action") + "'>");
          out.println("\t\t<input type='hidden' name='CONFIRMED' value='TRUE'>");
          Iterator iter = request.getParameterMap().entrySet().iterator();
          Map.Entry entry = null;
          String key = null;
          String value = null;
          while(iter.hasNext())
          {
            entry = (Map.Entry) iter.next();
            key = (String)entry.getKey();
            value = request.getParameter(key);
            
            System.out.println(key + " : " + value);
            
            if(!key.equals("action"))
              out.println("\t\t<input type='hidden' name='" + key + "' value='" + value + "'>");
          }
          out.println("\t\t<table cellspacing='0' cellpadding='0' border='0' width='100%'>");
          out.println("\t\t\t<tr><td style='font-weight:bold;text-decoration:underline;'>Comments:</td></tr>");
          out.println("\t\t\t<tr><td><textarea name='comments' style='width:250px;height:150px;'></textarea></td></tr>");
          out.println("\t\t\t<tr><td align='center' style='padding-top:10px;'><input type='submit' value='DECLINE'></td></tr>");
          out.println("\t\t</table>");
          out.println("\t</form>");
          out.println("</td></tr>");
        }
      
      %>
        
    </table>
  </body>
</html>
