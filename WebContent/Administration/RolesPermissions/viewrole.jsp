<%@ page language="java"
         session="true"
         import="java.util.*,
         com.awsd.security.*,
         com.awsd.personnel.*,
         com.awsd.school.*"%>

<%
  User usr = null;
  Role r = null;
  Permission p = null;
  Personnel per = null; 
  Iterator iter = null;
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
<%}  
  r = (Role) request.getAttribute("Role");
%>

<html>
  <head>
    <title>View Role</title>
     <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	
	<script>		
		$('document').ready(function(){
			pTable = $(".permissionsTable").dataTable({
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
			
			mTable = $(".membershipTable").dataTable({
				"order" : [[1,"asc"]],
				  "lengthMenu": [[25,50,100, 250, -1], [25,50,100, 250, "All"]],	
				responsive: true,					       	
								
				 "columnDefs": [
					 {
			                "targets": [4],			               
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
 <div class="siteHeaderGreen">Viewing  <span style="color:red;"><%=r.getRoleUID()%></span> Role</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Roles Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
   
  <b>Unique Identifier:</b><br/><%=r.getRoleUID()%><br/>
  <br/>
  <b>Description:</b><br/>
  <%=r.getRoleDescription()%>                    
 
 
 <br/>
    <div align="center">              
         <a onclick="loadingData();" class="btn btn-sm btn-primary" href="rolePermissions.html?uid=<%=r.getRoleUID()%>">Edit Permissions</a> &nbsp; 
         <a onclick="loadingData();" class="btn btn-sm btn-primary" href="roleMembership.html?uid=<%=r.getRoleUID()%>">Edit Membership</a> &nbsp;
         <a onclick="loadingData();" class="btn btn-sm btn-warning" href="modifyRole.html?ouid=<%=r.getRoleUID()%>">Edit Role</a> &nbsp;
         <a class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to remove this role? This may effect user permissions, and will remove any members of this role! This cannot be undone!');" href="deleteRole.html?uid=<%=r.getRoleUID()%>&confirmed=true" >Delete Role</a>
     	 <a onclick="loadingData();" href="viewroles.jsp" class="btn btn-sm btn-danger">Exit <%=r.getRoleUID()%> Role</a>
     </div>       
 <br>
 
 
  <div class="siteSubHeaderBlue">Applied Permissions:</div>         
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
                              iter = (r.getRolePermissions()).entrySet().iterator();
                              while(iter.hasNext()) 
                              {
                                p = (Permission) (((Map.Entry)iter.next()).getValue());
                            %>  
                            <tr>
                            <td width="30%"><%=p.getPermissionUID()%></td>
                           	<td width="60%"><%=p.getPermissionDescription()%></td>
                           	<td width="10%"><a onclick="return confirm('Are you sure you want to remove this permission from this role? This cannot be undone!');" class="btn btn-xs btn-danger" href="removeRolePermission.html?rid=<%=r.getRoleUID()%>&pid=<%=p.getPermissionUID()%>">REMOVE</a></td>
                            </tr>   
                            <%}%>
              </tbody>
              </table>
   <br/><hr>                  
 <div class="siteSubHeaderBlue">Membership: (<%=r.getRoleMembership().size()%>)</div>
 				<table class="membershipTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
				<thead class="thead-dark">
				<tr style="color:Black;font-size:12px;">					
				<th width="5%">USER ID</th>
				<th width="30%">NAME</th>											
				<th width="20%">EMAIL</th>
				<th width="30%">POSITION</th>			
				<th width="15%">OPTIONS</th>						
				</tr>
				</thead>
				<tbody>
                            <%                         
                              iter = r.getRoleMembershipList().iterator();
                              while(iter.hasNext()) 
                              {                           
                                per = (Personnel) iter.next();                               
                            %>  
                    <tr>
                    <td width="5%"><%=per.getPersonnelID()%></td>
                    <td width="30%"><%=per.getFullName()%></td>
                   
                    <%if(!per.getUserName().contains("@nlesd.ca")) { %>
                     <td width="20%" style="background-color:Red;color:white;">
                    &nbsp; <i class="fas fa-exclamation-triangle"></i> <%=per.getUserName()%>
                     </td>
                    <%} else { %>
                      <td width="20%">
                     <%=per.getUserName()%>
                   	</td>
                    
                    <%}%>
                    
                    <td width="30%"> <%=per.getPersonnelCategory()!=null?per.getPersonnelCategory().getPersonnelCategoryName():"N/A" %></td>                     
                    <td width="15%">
                    		<a onclick="return confirm('Are you sure you want to remove this user from this role?');" class="btn btn-xs btn-danger" href="removeRoleMember.html?rid=<%=r.getRoleUID()%>&pid=<%=per.getPersonnelID()%>">REMOVE</a>
                            <a class="btn btn-xs btn-info" href="" target="_blank" onclick="top.document.location.href='../../loginAs.html?pid=<%=per.getPersonnelID()%>'; return false;">LOGIN</a>                    
                    </td>
                    </tr>        
                      <%}%>
                      </tbody>
                      </table>
      
      <br/><br/>
      <div align="center">              
         <a onclick="loadingData();" class="btn btn-sm btn-primary" href="rolePermissions.html?uid=<%=r.getRoleUID()%>">Permissions</a> &nbsp; 
         <a onclick="loadingData();" class="btn btn-sm btn-primary" href="roleMembership.html?uid=<%=r.getRoleUID()%>">Membership</a> &nbsp;
         <a onclick="loadingData();" class="btn btn-sm btn-warning" href="modifyRole.html?ouid=<%=r.getRoleUID()%>">Edit Role</a> &nbsp;
         <a class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to remove this role? This may effect user permissions, and will remove any members of this role! This cannot be undone!');" href="deleteRole.html?uid=<%=r.getRoleUID()%>&confirmed=true" >Delete Role</a>
     	<a href="viewroles.jsp" onclick="loadingData();" class="btn btn-sm btn-danger">Exit <%=r.getRoleUID()%> Role</a>
     </div>       
   <br/><br/>                  
    </div>                
                    
  </body>
</html>
