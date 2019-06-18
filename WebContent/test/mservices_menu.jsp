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
<title></title>

<style>

a {font-family: Arial, Helvetica, sans-serif; font-size: 10px; text-decoration: none;color : #FF6633; line-height: 14px;}

a:hover {font-family: Arial, Helvetica, sans-serif; font-size: 10px; text-decoration: none; color:#003399; line-height: 14px;}

body {font-family: Arial, Helvetica, sans-serif; font-size: 10px; text-decoration: none; color:#CCCCCC; line-height: 14px;}

</style>
<script language="Javascript" src="js/jquery-1.9.1.min.js"></script>
     <script>

       jQuery(function(){
    	     $(".swap").hover(
    	          function(){this.src = this.src.replace("-off","-on");},
    	          function(){this.src = this.src.replace("-on","-off");
    	     });
    	});


						</script>
</head>

<body bgcolor="#FFFFFF">

<table align="center" width="900" height="30" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="197" valign="top" align="left" bgcolor="#FFFFFF">
<a href="/MemberServices/memberServices.html" target="memberservicesmain">
<img src="images/mspageheader.png" border="0" alt="Member Services Home/Eastern School District"></a>
</td>

<td width="100%" valign="top" align="center">

<style>
body{
overflow-x:hidden;
overflow-y:scroll;
}
</style>

</td>

<td width="100" valign="top" align="right" bgcolor="#FFFFFF">
<a href="javascript:window.location.href='/MemberServices/logout.html'" target="memberservicesmain">
<img src="images/signout-off.png" class="swap" border="0" alt="Logout of Member Services"></a> 
</td><td>&nbsp;</td>
</tr>

</table>

</body>
</html>