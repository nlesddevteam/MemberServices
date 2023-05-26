<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
  User usr = null;
  Permissions permissions = null;
  Permission p = null;    
  Iterator<Permission> piter = null;
 
  usr = (User) session.getAttribute("usr");
  
  permissions = new Permissions();
 
%>

<html>
  <head>
     <title>MemberServices Permissions</title>
   
   <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".permissionsTable").dataTable({
				"order" : [[0,"asc"]],
				  "lengthMenu": [[25,50,100, 250, -1], [25,50,100, 250, "All"]],	
				responsive: true,					       	
								
				 "columnDefs": [
					 {
			                "targets": [2],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
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
<div class="siteHeaderGreen">Security Permissions</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Permissions Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

Below are the curent security permissions assigned in MemberServices. You can Add, Edit, or Delete any permission. To find a particular permission, use the search or scroll through the list below.
<br/><br/>
<div class="alert alert-danger"><b>NOTICE:</b> Please make sure you are aware that these permissions may be linked to various MS Applications and User Roles. 
Removing them may remove user access or functionality in any application on the system. 
Make sure you are aware of the changes you make!</div>
<br/>
<div align="center"><a class="btn btn-sm btn-primary" href="addPermission.html?passthrough=true" onclick="loadingData();"><i class="fas fa-plus"></i> Add New Permission</a> &nbsp;
<a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();"><i class="fas fa-undo-alt"></i> Back to Administration</a></div>
  <br/>
<table class="permissionsTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">					
					<th width="30%">PERMISSION ID</th>
					<th width="60%">DESCRIPTION</th>											
					<th width="10%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>                    
                    
          
            <%
              piter = permissions.iterator(); 
              while(piter.hasNext()) 
              {
                p = (Permission) piter.next();
            %>  
            <tr>
              <td width="30%"><%=p.getPermissionUID()%></td>  
              <td width="60%"> <%=p.getPermissionDescription()%></td>
              <td width="10%">            
              <a onclick="loadingData();" class="btn btn-warning btn-xs" href="modifyPermission.html?ouid=<%=p.getPermissionUID()%>"><i class="fas fa-edit"></i> EDIT</a>
              <a class="btn btn-danger btn-xs" onclick="return confirm('Are you sure you want to remove this permission? This cannot be undone.');" href="deletePermission.html?uid=<%=p.getPermissionUID()%>&confirmed=true"><i class="far fa-trash-alt"></i> DEL</a>
              </td>              
              </tr>
            <%}%>
         </tbody>
         </table>
  </div>
  
  
  </body>
  </html>     
         