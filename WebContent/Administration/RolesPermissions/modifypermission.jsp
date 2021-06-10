<%@ page language="java"
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
  Permission p = null;
  boolean modified;
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
  
  p = (Permission) request.getAttribute("Permission");
  modified = ((Boolean) request.getAttribute("modified")).booleanValue();
%>

<html>
  <head>
    <title>Edit Permission</title>

    <script>
    $('document').ready(function(){		
    	$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	
		
	});    
  
    </script>
  </head>

  <body>
  <div class="siteHeaderGreen">Modify/Edit <span style="color:red;"><%=p.getPermissionUID()%></span> Permission</div>
	<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		
<div style="display:none;border:0px" class="loadPage"> 
  
    <form name="permissions" action="modifyPermission.html" method="post" class="was-validated">
    <input type="hidden" name="confirmed" value="true">
  <div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12"> 
   
   <b>Name of Permission:</b>  
   <input class="form-control" type="text" name="uid" maxlength="125" value="<%=p.getPermissionUID()%>" required>                       
 </div>
 </div>                 
  <div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12"> 
  <b>Description:</b>
  <textarea class="form-control" name="description" required><%=p.getPermissionDescription()%></textarea>
          
 </div>
 </div>        
 <br/><br/>                             
                    <div align="center">                      
                          		<input type="hidden" name="ouid" value="<%=p.getPermissionUID()%>">                          		
                          		<input type="submit" class="btn btn-success btn-sm" value="Save Changes">  &nbsp;
                    			<a href="viewpermissions.jsp" onclick="loadingData();" class="btn btn-sm btn-danger">Cancel</a>                         
                      </div>
    <br/><br/>
   
   
    </form>
    
    </div>
    
  </body>
</html>