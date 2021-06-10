<%@ page language="java"
    session="true"
    import="java.sql.*,
            java.util.*,
            java.text.*,
            com.awsd.security.*,
            com.awsd.weather.*"%>

<%
  User usr = null;
  ClosureStatuses stats = null;
  ClosureStatus stat = null;
  Iterator iter = null;

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

  stats = new ClosureStatuses();
  iter = stats.iterator();
%>

<html>
<head>
<title>Status Central Codes</title>
 <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".codesTable").dataTable({
				"order" : [[0,"asc"]],				
				responsive: true			       	
			});			
			
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
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
Here you can add/modify school closure status codes. Be careful as these codes are possibly in use. 
Check status central before making changes. <br/>
Preferably make changes ONLY when school is finished for the year between July 1 and August 31 when Administrators are not accessing the system.

<br/><br/>

<table class="permissionsTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">					
					<th width="10%">CODE  ID</th>
					<th width="80%">DESCRIPTION</th>
					<th width="10%">OPTIONS</th>
					</tr>
				</thead>
				<tbody> 
                      <%while(iter.hasNext())
                        {
                          stat = (ClosureStatus) iter.next();
                      %>  <tr>
                            <td width="10%">
                            <%=stat.getClosureStatusID()%>
                            </td>
                            <td width="80%">
                             <%=stat.getClosureStatusDescription()%>
                            </td>
                           <td width="10%">                       
                            <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to remove this status code? This cannot be undone.');" href="weatherStatusCodeAdmin.html?op=del&status_id=<%=stat.getClosureStatusID()%>">DEL</a>
                           <a class="btn btn-xs btn-warning" href="closurestatusmodify.jsp?op=mod&status_id=<%=stat.getClosureStatusID()%>&status_desc=<%=stat.getClosureStatusDescription()%>">EDIT</a>
                           </td>
                           </tr> 
                      <%}%>
					</tbody>
					</table>
					
			<hr>
			
					
	<div class="card bg-primary">
  <div class="card-header" style="color:White;"><b>ADD A NEW STATUS</b></div>
  <div class="card-body" style="background-color:White;">
  Please give the new status a valid description. The code ID will be assigned automatically.
  	<form name="addClosureStatusForm" action="weatherStatusCodeAdmin.html" method="get" class="was-validated">
		<input type="hidden" name="op" value="add" />
		<b>Description:</b><br/>
		<input type="text" class="form-control" name="status_desc" required/>													
		<br/><br/>
        <div align="center">																
		<a href="" onclick="document.addClosureStatusForm.submit();" class="btn btn-success btn-sm">Save</a> &nbsp; 		
		<a href="../index.jsp" onclick="loadingData();" class="btn btn-sm btn-danger">Exit/Cancel</a>
       </div>  		
														
			</form>
  
  </div>  
</div>					
					
	</div>
										
</body>
</html>
