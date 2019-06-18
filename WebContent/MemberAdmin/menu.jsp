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
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
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
    <link href="../css/memberadmin.css" rel="stylesheet">
    <style>
        /* Style for tree item text */
        .t0i {font-family: Arial, Helvetica, sans-serif, sans-serif;font-size: 11px;color: #000000;background-color: #ffffff; text-decoration: none;}

        /* Style for tree item image */
        .t0im {border: 0px;width: 19px;height: 16px;}
    </style>
    <script language="JavaScript" src="js/tree.js"></script>
    <script language="JavaScript" src="js/tree_tpl.js"></script>
    <jsp:include page="tree_items.jsp" flush="true"/>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="20" valign="top" bgcolor="#FFFFFF">
        &nbsp;
        </td>
        <td width="100%" valign="top" bgcolor="#ffffff">
          <img src="images/spacer.gif" width="1" height="4"><BR>
          <script language="JavaScript">
            new tree (TREE_ITEMS, tree_tpl);
          </script>
          <img src="images/spacer.gif" width="1" height="4"><BR>
        </td>
      </tr>
      <tr>
        <td width="100%" valign="top" bgcolor="#E5F2FF" colspan="2">
          <img src="images/spacer.gif" width="1" height="20"><BR>
        </td>
      </tr>
    </table>
  </body>
</html>