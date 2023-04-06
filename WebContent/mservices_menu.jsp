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
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">      
  	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>	
		<style>
		.btn-group-xs > .btn, .btn-xs { padding: .25rem .25rem; text-transform:uppercase; margin:2px; font-size: .75rem; line-height: 1; border-radius: .2rem;}
		</style>
</head>

<body>
<div style="text-align:center;width:100%;white-space: nowrap;height:32px;background-color: rgba(31, 66, 121, 0.9);">

<img title="NLESD" src="/MemberServices/StaffRoom/includes/img/nltopleftlogo.png" border=0 style="height:25px;"/>
&nbsp;<span style="color:white;font-size:12px;">NLESD STAFFROOM</span>&nbsp;
<img title="Secure Resource" src="/MemberServices/StaffRoom/includes/img/lock_check.png" border=0 style="height:25px;"/>
<a class="btn btn-xs btn-primary" href="/MemberServices/memberServices.html" target="memberservicesmain" title="Back to StaffRoom Main Menu">MAIN MENU</a>
<a class="btn btn-xs btn-danger" href="javascript:window.location.href='/MemberServices/logout.html'" target="memberservicesmain" title="Sign out of StaffRoom">SIGN OUT</a>
</div>




</body>
</html>