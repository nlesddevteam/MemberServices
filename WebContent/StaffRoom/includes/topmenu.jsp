<%@ page language="java"
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>

<%
  User usr = null; 
  usr = (User) session.getAttribute("usr");
%>

<div class="srTopMenu">	
		<a href="https://myaccount.google.com" target="_blank">
			<img src="<%=session.getAttribute("gmailicon")%>" border=0 title="Your Google Profile Picture" onerror="this.onerror=null; this.src='/MemberServices/StaffRoom/includes/img/nltopleftlogo.png'"	style="height:26px;"/>
		</a>
			&nbsp;<span style="color:white;font-size:12px;"><%=usr!=null?usr.getPersonnel().getFirstName()+"'S":"NLESD" %> STAFFROOM</span>&nbsp;		
		<span class="srTopMenuOpt">
			<img title="Secure Resource" src="/MemberServices/StaffRoom/includes/img/lock_check.png" border=0 style="height:25px;"/>
			<a class="btn btn-xs btn-primary" href="/MemberServices/memberServices.html" title="Back to StaffRoom Main Menu">MAIN MENU</a>
			<a class="btn btn-xs btn-danger" href="/MemberServices/logout.html" title="Sign out of StaffRoom">SIGN OUT</a>
		</span>
</div>
