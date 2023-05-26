<%@ page language="java"
        session="true"
        import="java.util.*,
        com.awsd.security.*,
        com.awsd.school.*,
        com.awsd.personnel.*,
        com.awsd.personnel.PersonnelDB,
        com.awsd.security.Role,
		com.awsd.security.RoleDB"
        %>
        
 <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>     
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
        
<%
	  User usr = null;
	  Personnel p = null;
	  PersonnelCategories categories = null;
	  PersonnelCategory cat = null;
	  Iterator iter = null;
	  School s = null;
	  School ss = null;
	  
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    
<jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}} else { 
	
	%>  
		<jsp:forward page="/MemberServices/login.html">
      			<jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    	</jsp:forward>
<%
	}
  p = (Personnel) request.getAttribute("Personnel");
  categories = new PersonnelCategories();
  iter = categories.iterator();
  s = p.getSchool();
  ss = p.getSchool();
  
  Map<String, Role> roles = RoleDB.getRoles(p);
  request.setAttribute("prec", p);
  request.setAttribute("roles", roles);
  
%>
<html>
  <head>
    <style>
		input {border: 1px solid silver;}
		.btn-group {float:left;}			
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

<div class="siteHeaderGreen">MemberServices USER Profile Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Members Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage">  


<form name="change" action="/MemberServices/Administration/PersonnelAdmin/personnelAdminChange.html?update&pid=<%=p.getPersonnelID()%>" method="post" class="was-validated">



<div class="card">
							  <div class="card-header"><b>PROFILE INFORMATION for</b><br/><span style="color:Red;font-size:18px;font-weight:bold;text-transform:Capitalize;"><%=p.getFullNameReverse()%></span><br/> 
							  ID: <%=p.getPersonnelID() %> </div>
							  <div class="card-body">
						 Please complete the following information: <br/>
					<%
					//Fix the ALL CAPITALS in some names to just a first capital letter when saving.
					String fname = p.getFirstName().toLowerCase();
					String lname = p.getLastName().toLowerCase();			       
			        String firstLetStr = fname.substring(0, 1);			      
			        String remLetStr = fname.substring(1);			 			      
			        firstLetStr = firstLetStr.toUpperCase();				     
			        String firstCapName = firstLetStr + remLetStr;			        
			        firstLetStr = lname.substring(0, 1);			     
			        remLetStr = lname.substring(1);			     
			        firstLetStr = firstLetStr.toUpperCase();				        
			        String secondCapName = firstLetStr + remLetStr;
					%>
										
								
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>First Name:</b>
									<input type="text" placeholder="Employee First Name" class='required form-control' name='firstname' value="<%=firstCapName%>" required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-6 col-12">
									<b>Last Name:</b>
									<input type="text" placeholder="Employee Last Name" class='required form-control' name='lastname' value="<%=secondCapName%>" required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>								
						</div>
						
						<br/>
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>Email Address:</b>
									<input type="text" placeholder="Employee Email Address" class='required form-control' name='email' value="<%=p.getEmailAddress()%>" required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-6 col-12">
									<b>Username:</b>
									<input type="text" placeholder="Employee Username" class='required form-control' name='username' value="<%=p.getUserName()%>" required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>								
						</div>
						<br/>
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>Category:</b>
									<select name="category" class="form-control required" required autocomplete="f">
					                        <option value="-1">PLEASE SELECT CATEGORY</option>
					                        <%
					                        	while(iter.hasNext())
					                             {
					                              cat = (PersonnelCategory) iter.next();
					                        %>
					                          <option value="<%=cat.getPersonnelCategoryID()%>" <%=(p.getPersonnelCategory().getPersonnelCategoryID()==cat.getPersonnelCategoryID())?"SELECTED":""%>><%=cat.getPersonnelCategoryName() %></option>
					                        <% } %>
                      			</select>									
										<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please select.</div>
								</div>
								<div class="col-lg-6 col-12">
									<b>School/Location:</b>
									<html:hidden name="Personnel" property="personnelID" />
									 <logic:notEmpty name="Personnel" property="school">
                          <html:select name="Personnel" property="school.schoolID" size="1">
                          <html:option value="-1">PLEASE SELECT</html:option>                          
                            <html:option value="277">District/Avalon Regional Office</html:option>
                            <html:option value="279">Central Regional Office</html:option>
                            <html:option value="280">Western Regional Office</html:option>
                            <html:option value="278">Labrador Regional Office</html:option>
                            <html:option value="-1">----SCHOOLS----</html:option>
                            <html:option value="-1">No Location</html:option>
                            <html:options collection="ALL_SCHOOLS" property="schoolID" labelProperty="schoolName" /> 
                          </html:select>
                        </logic:notEmpty>
                        <logic:empty name="Personnel" property="school">
                          <html:select property="school.schoolID" size="1" value="-1">
                           <html:option value="-1">PLEASE SELECT</html:option>                            
                            <html:option value="277">District/Avalon Regional Office</html:option>
                            <html:option value="279">Central Regional Office</html:option>
                            <html:option value="280">Western Regional Office</html:option>
                            <html:option value="278">Labrador Regional Office</html:option>
                            <html:option value="-1">----SCHOOLS----</html:option>
                            <html:option value="-1">No Location</html:option>
                            <html:options collection="ALL_SCHOOLS" property="schoolID" labelProperty="schoolName" /> 
                          </html:select>
                        </logic:empty>
									
									
						<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>		
								
								
								
														
						</div>
						
						<br/>
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>Startup Application: </b>
									<%if(p.getViewOnNextLogon() != null){%>                                  
                                    <%=p.getViewOnNextLogon()%>
                                <%} else {%>
									None
								<%} %>	
						</div>
						</div>
						
						<br/><br/>
						<div align="center">
						  <a class="btn btn-sm btn-success" name="add" id="add" href="#" onclick="document.change.submit();loadingData();">Update</a>
						   <a class="btn btn-sm btn-warning" onclick="loadingData();" href="/MemberServices/loginAs.html?pid=<%=p.getPersonnelID() %>" title="Login as this user"> Login As</a>
						  <a class="btn btn-sm btn-danger" onclick="loadingData();" href="personnel_admin_view.jsp">Back to Member List</a>
						</div>
						
						</div>
</div>
</form>

<br/><br/>
<div class="siteSubHeaderBlue">Currently Assigned Role(s)/Permission(s)</div>

To change Roles and Permissions, please use the Security Roles/permissions options.

<br/><br/>
<%int cnt=0; %>

  <form name="changep" action="personnelEffectivePermissions.html?pid=${prec.personnelID}" method="post">
			
		
    

   <c:choose>
               	<c:when test="${fn:length(roles) gt 0}">		
               	<div id="accordion">                 		
				                 		<c:forEach items="${roles}" var="rentry">
				                 		<%cnt++;%>

									<div class="card">
									  <div class="card-header" style="font-size:16px;"><a class="card-link card<%=cnt%>" data-toggle="collapse" href="#collapse<%=cnt%>"><span id="icon<%=cnt%>"><i class='fas fa-folder'></i></span> ${rentry.key}</a> 
									  <div style="float:right;padding-right:5px;"><a onclick='return confirm("Are you sure you want to remove this user from \"${rentry.key}\"?");' href="personnelEffectivePermissions.html?remove&pid=${prec.personnelID}&rid=${rentry.key}"><i class="fas fa-trash-alt"></i> REMOVE</a></div></div>
									<div id="collapse<%=cnt%>" class="collapse" data-parent="#accordion">
									 <div class="card-body">			 					                 				
									      			<c:if test="${fn:length(rentry.value.rolePermissions) gt 0 }">						                 			
									        			<c:forEach items="${rentry.value.rolePermissions}" var='pentry'>
									        				<div class="bg-primary" style="color:white;font-weight:bold;float:left;min-width:200px;border-radius: 10px; padding:5px;margin:3px;text-align:center;white-space: nowrap;">
									        				${pentry.key} 
									        				</div>
									        			</c:forEach>
									       			
									      			</c:if>
									         			<br/><br/>
									</div>
									</div>
									</div>
			
					                 		</c:forEach>
					                 		</div>	
		                 	</c:when>
		                 	<c:otherwise>
		                 	User has no roles/permissions assigned.		                 	
		                	</c:otherwise>
		          			</c:choose>
</form>

    
      
      
      
      
      
    
      
      
      
      
      
      
      
      
      
      
      
      
      
    
    </div>
    
    <script>
    $("select[name='school.schoolID']").addClass("form-control").attr('required');    
    
	$('.card<%=cnt%>').on("click", function(e){             	 
        if( $("#collapse<%=cnt%>").hasClass("show")) {
        	$("#icon<%=cnt%>").html("<i class='fas fa-folder'></i>");
        } else {                	
        	 $("#icon<%=cnt%>").html("<i class='fas fa-folder-open'></i>");
        }                	 
        	 e.preventDefault();                	 
        	});    
    </script>
    
    
  </body>
</html>