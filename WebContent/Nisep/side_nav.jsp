<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("NISEP-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}%>

<html>
  <head>
    <title></title>
    <style>
        /* Style for tree item text */
        .t_i {font-family: Arial, Helvetica, sans-serif, sans-serif;font-size: 11px;color: #000000;background-color: #ffffff; text-decoration: none;}

        /* Style for tree item image */
        .t_im {border: 0px;width: 19px;height: 16px;}
    </style>
    <script language="JavaScript" src="js/tree.js"></script>
    <script language="JavaScript" src="js/tree_tpl.js"></script>
    <jsp:include page="side_nav_tree_items.jsp" flush="true"/>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      
      <tr>
        <td width="*" valign="top" bgcolor="#ffffff">
          <script language="JavaScript">
            new tree (AGENCY_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
      
      <tr>
        <td width="*" valign="top" bgcolor="#ffffff">
          <script language="JavaScript">
            new tree (STUDENT_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
      
      <tr>
        <td width="*" valign="top" bgcolor="#ffffff">
          <script language="JavaScript">
            new tree (COORDINATOR_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
      
      <tr>
        <td width="*" valign="top" bgcolor="#ffffff">
          <script language="JavaScript">
            new tree (HOSTFAMILY_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
       
    </table>
  </body>
</html>