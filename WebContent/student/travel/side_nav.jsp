<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
%>

<html>
  <head>
    <title></title>
    <style>
        /* Style for tree item text */
        .t_i {font-family: Arial, Helvetica, sans-serif, sans-serif;font-size: 11px;color: #000000;background-color: #ffffff; text-decoration: none;}
 
				.t_i span { display:inline-block; width: 180px; }
				
        /* Style for tree item image */
        .t_im {border: 0px;width: 19px;height: 16px; vertical-align: top;}
        
        .t_i_div { border: none; }
    </style>
    <script language="JavaScript" src="js/tree.js"></script>
    <script language="JavaScript" src="js/tree_tpl.js"></script>
    <jsp:include page="side_nav_tree_items.jsp" flush="true"/>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <table width="228" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td id="MenuItem">
          <%if(usr.checkPermission("STUDENT-TRAVEL-PRINCIPAL-VIEW")){%>
            <a href="addRequest.html">Add Request</a> 
          <%}%>
        </td>
      </tr>
      <tr>
        <td width="100%" valign="top" bgcolor="#ffffff" height='480'>
          <script language="JavaScript">
            new tree (REQUEST_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
    </table>
  </body>
</html>