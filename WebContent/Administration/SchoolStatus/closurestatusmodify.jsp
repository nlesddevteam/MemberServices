<%@ page language="java"
    session="true"
    import="java.sql.*,
            java.util.*,
            java.text.*,
            com.awsd.security.*,
            com.awsd.weather.*"%>

<%
  User usr = null;
String frmid = null;
String frmdesc= null;

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
<%}
 
 frmid=request.getParameter("status_id");
frmdesc=request.getParameter("status_desc");
%>

<html>
<head>
<title>Status Central Codes</title>
 <style>
		input {border: 1px solid silver;}	

	</style>
	
	<script>		
		$('document').ready(function(){
		
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
</head>
<body>

<div class="siteHeaderGreen">School Closure Status Admin</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Permissions Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

<div class="card">
  <div class="card-header">Add New Status</div>
  <div class="card-body">
  	<form name="modifyClosureStatusForm" action="weatherStatusCodeAdmin.html" method="get" class="was-validated">
		<input type="hidden" name="op" value="mod" />
		<input type="text" class="form-control" name="status_id" value="<%=frmid%>" required/>	
		<b>Description:</b><br/>
		<input type="text" class="form-control" name="status_desc" value="<%=frmdesc%>" required />													
		<br/><br/>
        <div align="center">																
		<a onclick="document.modifyClosureStatusForm.submit();loadingData();" class="btn btn-success btn-sm">Save</a> &nbsp; 		
		<a href="closurestatusadmin.jsp" onclick="loadingData();" class="btn btn-sm btn-danger">Cancel</a>
       </div>  		
														
			</form>
  
  </div>  
</div>	
	

	</div>
										
</body>
</html>
