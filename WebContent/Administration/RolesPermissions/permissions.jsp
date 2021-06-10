<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         com.awsd.personnel.*, 
         java.util.*"%>

<%!
  User usr = null;
  Permissions  perms = null;
  Permission p = null;
  Role r = null;
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
  perms = new Permissions();
%>

<html>
  <head>
    <title>Role Permissions</title>
    
   <script>
   
   $('document').ready(function(){		
	   $(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	
   
   });
   </script>
   
    
  </head>

  <body>
  <div class="siteHeaderGreen">Modify/Edit <span style="color:red;"><%=r.getRoleUID() %></span> Role Permissions</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 
  
  You can add/remove permissions to the <%=r.getRoleUID() %> role by using the side by side select boxes below. 
  
    <br/><br/>
          <form name="permission" action="rolePermissions.html" method="post">
          <input type="hidden" name="uid" value="<%=r.getRoleUID()%>">
          <input type="hidden" name="op" value="NONE">
          
           <table width="100%" align="center" style="font-size:12px;">
           <thead>
           <tr>
           <th width="45%">AVAILABLE ROLES</th>
           <th width="10%" style="text-align:center;"></th>
           <th width="45%">ASSIGNED ROLES</th>
           </tr>
           </thead>
           <tbody>
           <tr>
         	<td width="45%">
            <select size="15" name="available" multiple class="form-control" width="100%" style="font-size:11px;">
           
                          <%
                            iter = perms.iterator(); 
                            while(iter.hasNext())
                            {
                              p = (Permission) iter.next();
                              if(!r.getRolePermissions().containsKey(p.getPermissionUID()))
                              {
                          %>    <option value="<%=p.getPermissionUID()%>"><%=p.getPermissionUID()%></option>
                          <%  }
                            }
                          %>
                          </select>
             </td>
            <td width="10%" style="text-align:center;">       
                    	<a href="#" class="btn btn-sm btn-success" style="width:100px;" onclick="document.permission.op.value='ADD'; document.permission.submit();">ADD <i class="fas fa-arrow-circle-right"></i></a>
                     	<br/><br/>
                     	<a href="#" class="btn btn-sm btn-danger" style="width:100px;" onclick="document.permission.op.value='REMOVE'; document.permission.submit();"><i class="fas fa-arrow-circle-left"></i> REMOVE</a>
             </td>            
            <td width="45%">         
                      <select size="15" name="assigned" muliple class="form-control" width="100%" style="font-size:11px;">            
                 
                          <%
                            iter = r.getRolePermissions().entrySet().iterator();
                            while(iter.hasNext())
                            {
                              p = (Permission) (((Map.Entry)iter.next()).getValue());
                          %>  <option value="<%=p.getPermissionUID()%>"><%=p.getPermissionUID()%></option>
                          <% } %>  
                          </select>
              </td>
            </tr>
            </tbody>
            </table>     
                 
          </form>
       
      <br/><br/>
        <div align="center">
          <a href="viewRole.html?uid=<%=r.getRoleUID()%>" onclick="loadingData();" class="btn btn-sm btn-danger">Exit <%=r.getRoleUID()%> Role Permissions</a>
       </div>  
       </div>
       
       
  </body>
</html>
