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
  Roles roles = null;
  Role r = null;
  Iterator<Role> riter = null;
  usr = (User) session.getAttribute("usr"); 
  roles = new Roles();
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
			mTable = $(".rolesTable").dataTable({
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
<div class="siteHeaderGreen">Security Roles</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Roles Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
Below are the current security roles assigned in MemberServices. You can View, Add, Edit, or Delete any Role. To find a particular role, use the search or scroll through the list below. Each role has corresponding Permissions and user members associated with them.
<br/><br/>
<div class="alert alert-danger"><b>NOTICE:</b> Please make sure you are aware that these roles may be linked to various MS Applications and User Permissions. 
Removing them may remove user access or functionality in any application on the system. 
Make sure you are aware of the changes you make!</div>
<br/>
<div align="center"><a class="btn btn-sm btn-primary" onclick="loadingData();" href="addRole.html?passthrough=true"><i class="fas fa-plus"></i> Add New Role</a> &nbsp; 
<a class="btn btn-sm btn-danger" onclick="loadingData();" href="/MemberServices/navigate.jsp"><i class="fas fa-undo-alt"></i> Back to Administration</a></div>
  <br/>
<table class="rolesTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">					
					<th width="35%">ROLE ID</th>
					<th width="50%">DESCRIPTION</th>											
					<th width="15%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>         

            <%
              riter = (Iterator<Role>) roles.iterator();
              while(riter.hasNext()) 
              {
                r = (Role) riter.next();          
            %>
             <tr>
              <td width="35%"><%=r.getRoleUID() %></td>  
              <td width="50%"> <%=r.getRoleDescription() %></td>
              <td width="15%"> 
               <a onclick="loadingData();" class="btn btn-primary btn-xs" href="viewRole.html?uid=<%=r.getRoleUID()%>"><i class="far fa-eye"></i> VIEW</a>                      
              <!-- <a class="btn btn-warning btn-xs" href="modifyRole.html?ouid=<%=r.getRoleUID()%>"><i class="fas fa-edit"></i> EDIT</a>-->
              <a class="btn btn-danger btn-xs" onclick="return confirm('Are you sure you want to remove this role? This may effect user permissions! This cannot be undone!');" href="deleteRole.html?uid=<%=r.getRoleUID()%>&confirmed=true"><i class="far fa-trash-alt"></i> DEL</a>
              </td>              
              </tr>        
            
            <%}%>
 </tbody>
 </table>
 
 </div>
 </body>
 </html>
         