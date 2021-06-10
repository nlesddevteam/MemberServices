<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
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
<%}%>



<html>
  <head>
    <title>Add Role</title>
     
    <script>
    $('document').ready(function(){		
    	$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	
		
	});      
   
    </script>
  </head>

  <body>
  <div class="siteHeaderGreen">Add a New Role</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		
<div style="display:none;border:0px" class="loadPage"> 



    <form name="roles" action="addRole.html" method="post" class="was-validated">
<div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12">     
    <b>Unique Name:</b><br/>
     Give this role a unique name relating to the actual job position or a abbreviated prefix thereof.
	<input type="text" name="uid" class="form-control" maxlength="100"  value="" placeholder="Enter a unique name for this Role" required>
</div></div>
<div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12">     
     <b>Description:</b><br/>
     Give a description for what this Role is used for.
     <textarea name="description" required class="form-control" placeholder="Enter a valid description for this new role."></textarea>
 </div></div>   
    
    <br/><br/>                             
                    <div align="center"> 
                    <input type="submit" class="btn btn-success btn-sm" value="Save">  &nbsp;
                    <a href="viewroles.jsp" onclick="loadingData();" class="btn btn-sm btn-danger">Cancel</a>                         
                      </div>
    <br/><br/>     
                    
    </form>
    
    
 </div>
    
    
    
  </body>
</html>