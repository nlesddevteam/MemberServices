<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         com.awsd.personnel.*, 
         java.util.*"%>

<%!DistrictPersonnel members = null;
  Personnel p = null;
  Role r = null;
  Iterator iter = null;
  User usr = null;%>

<%
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}
  
  r = (Role) request.getAttribute("Role");
  members = new DistrictPersonnel();  
%>

<html>
  <head>
    <title>Role Membership</title>
    
   <script>
   
   $('document').ready(function(){		
	   $(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	
   
   });
   </script>
    
  </head>

  <body>
  <div class="siteHeaderGreen">Modify/Edit <span style="color:red;"><%=r.getRoleUID() %> Role</span> Membership</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 
  
  You can add/remove members from the <%=r.getRoleUID() %> role by using the side by side select boxes below. 
  By default, the list has filtered out any invalid members on the list at left that do not have a valid user login (currently a nlesd.ca login). 
  If members listed in the right column do not have a valid MS login, please remove. They will be marked red.
  
    <br/><br/>
    
          <form name="membership" action="roleMembership.html" method="post">
          <input type="hidden" name="uid" value="<%=r.getRoleUID()%>">
          <input type="hidden" name="op" value="NONE">
          
           
           
           
           <table width="100%" align="center" style="font-size:12px;">
           <thead>
           <tr>
           <th width="45%">AVAILABLE MEMBERS (Name, Email, Position)</th>
           <th width="10%" style="text-align:center;"></th>
           <th width="45%"><%=r.getRoleMembership().size()%> ASSIGNED MEMBERS (Name, Email, Position)</th>
           </tr>
           </thead>
           <tbody>
           <tr>
         <td width="45%">
                          <select size="15" name="available" multiple class="form-control" width="100%" style="font-size:11px;">
                          <%
                            iter = members.iterator(); 
                            while(iter.hasNext())
                            {
                            	
                              p = (Personnel) iter.next();
                              
                              if(p.getUserName().contains("@nlesd.ca")) {                              
                              if(!r.getRoleMembership().containsKey(new Integer(p.getPersonnelID())))
                              {
                          %>    <option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%>  (<%=p.getEmailAddress()!=null?p.getEmailAddress():"N/A"%>) <%=p.getPersonnelCategory()!=null?p.getPersonnelCategory().getPersonnelCategoryName():"N/A"%></option>
                          <%  }
                            }}
                          %>
                          </select>
             </td>
             <td width="10%" style="text-align:center;">       
                    	<a href="#" class="btn btn-sm btn-success" style="width:100px;" onclick="document.membership.op.value='ADD'; document.membership.submit();">ADD <i class="fas fa-arrow-circle-right"></i></a>
                     	<br/><br/>
                     	<a href="#" class="btn btn-sm btn-danger" style="width:100px;" onclick="document.membership.op.value='REMOVE'; document.membership.submit();"><i class="fas fa-arrow-circle-left"></i> REMOVE</a>
             </td>            
            <td width="45%">         
                      <select size="15" name="assigned" muliple class="form-control" width="100%" style="font-size:11px;">
                          <%
                            iter = r.getRoleMembership().entrySet().iterator();
                            while(iter.hasNext())
                            {
                              p = (Personnel) (((Map.Entry)iter.next()).getValue());
                          %> 
                         <%if(!p.getUserName().contains("@nlesd.ca")) {%>
                         <option style="background-color:Red;color:white;" value="<%=p.getPersonnelID()%>"><%=p.getFullName()%> (<%=p.getEmailAddress()!=null?p.getEmailAddress():"N/A"%>)  <%=p.getPersonnelCategory()!=null?p.getPersonnelCategory().getPersonnelCategoryName():"N/A"%></option>
                          <%} else {%>
                           <option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%> (<%=p.getEmailAddress()!=null?p.getEmailAddress():"N/A"%>)  <%=p.getPersonnelCategory()!=null?p.getPersonnelCategory().getPersonnelCategoryName():"N/A"%></option>
                          <% }} %>  
                          </select>
              </td>            
             </tr>
             </tbody>             
                          
               </table>
          </form>
        
        <br/><br/>
        <div align="center">
          <a href="viewRole.html?uid=<%=r.getRoleUID()%>" onclick="loadingData();" class="btn btn-sm btn-danger">Exit <%=r.getRoleUID()%> Role Membership</a>
       </div>  
       </div>
  </body>
</html>