<%@ page language="java"
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>

<%!
  User usr = null;
  UserPermissions p = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr == null)
  {
%>  <jsp:forward page="signin.jsp">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
  }
  p = usr.getUserPermissions();
%>

<html>
<head>
<title>MS Menu</title>

</head>

<body>
<div style="width:100%;white-space: nowrap;border-bottom:1px solid silver;height:31px">
<div style="width:49%;min-width:130px;float:left;margin-left:2px;"><a href="/MemberServices/memberServices.html" target="memberservicesmain" title="Back to MS Main Menu"><img src="/MemberServices/includes/img/ms-topleftlogo.png" border=0></a></div>
<div style="float:left;margin-right:2px;width:49%;min-width:130px;text-align:right;padding-top:2px;"><a href="javascript:window.location.href='/MemberServices/logout.html'" target="memberservicesmain" title="Sign out of Member Services"><img src="/MemberServices/includes/img/menu/signout-off.png" class="img-swap" border=0></a></div>
</div>




</body>
</html>