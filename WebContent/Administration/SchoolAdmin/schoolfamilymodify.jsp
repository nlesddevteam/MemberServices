<%@ page 
		language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*"%>

<%!
  User usr = null;
  SchoolFamily family = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;
  Vector specialists = null;
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
<%
	}

  family = (SchoolFamily)request.getAttribute("FAMILY");
  specialists = PersonnelDB.getDistrictPersonnel();
  p_iter = specialists.iterator();
%>

<html>
<head>
  <title>Modify Family</title>
   <style>
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
	<script>			
	$('document').ready(function(){		
		
		var schoolsForFamilySelect = new Choices('.choicesFam', {
	    	removeItemButton: true,
	    	maxItemCount:40,
	    	paste: false,
	        duplicateItemsAllowed: false,
	        editItems: true,
	    	searchResultLimit:20,
	    	renderChoiceLimit:25
	    	});		
	    	
	    	var DOSForFamilySelect = new Choices('.choicesDOS', {
	        	removeItemButton: true,
	        	maxItemCount:1,
	        	paste: false,
	            duplicateItemsAllowed: false,
	            editItems: true,
	        	searchResultLimit:20,
	        	renderChoiceLimit:25
	        	});				
		
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");		
		
	});
		</script>	
</head>
<body>
<div class="siteHeaderGreen">Modify/Edit School Family</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 
	
	 <form name="modfamily" id="modfamily" action="schoolFamilyAdmin.html" method="post" class="was-validated">
                          <input type="hidden" name="op" value="mod">
                          <input type="hidden" name="confirmed" value="true">
                          <input type="hidden" name="family_id" value="<%=family.getSchoolFamilyID()%>">
                          
							<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
								<b>NAME OF SCHOOL FAMILY:</b><br/>
								Please enter a name (up to 60 characters) for this family.
								<input type="text" maxlength="60" class="form-control" id="family_name" name="family_name"  value="<%=family.getSchoolFamilyName()%>" required>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-6 col-12">						
									<b>ADMINISTRATOR (DOS/SEO): (Select only 1)</b><br/>
									Start to type in the name of the DOS to find if not in the initial list.														
								<select name="ps_id" class="form-control choicesDOS" required>
                                  <option value="-1">NO DOS ASSIGNED</option>
                                  <option value="<%=family.getProgramSpecialist().getPersonnelID()%>" SELECTED><%=family.getProgramSpecialist().getFullNameReverse()%></option>
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>" ><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
                                <div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
							</div>
							</div>
							<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-12 col-12">
							<b>SCHOOL(s) TO INCLUDE IN FAMILY (Select up to 40)</b><br/>
							Start to type in the name of the school to find if not in the initial list.													
							<select multiple name="schools" id="schools" class="form-control choicesFam" required>
                                  <%sch_iter = family.getSchoolFamilySchools().iterator();
                                    while(sch_iter.hasNext())
                                    {
                                      school = (School) sch_iter.next();
                                  %>  <option value="<%=school.getSchoolID()%>" SELECTED><%=school.getSchoolName()%></option>
                                  <%
                                  	}
                                                                    
                                    sch_iter = SchoolDB.getSchoolsNotAssignedSchoolFamily().iterator();
                                    while(sch_iter.hasNext()){
                                      school = (School) sch_iter.next();
                                  %>  <option value="<%=school.getSchoolID()%>"><%=school.getSchoolName()%></option>
                                  <%}%>
							</select>
							<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
							
							</div>
							</div>
	                             <br/><br/>									
																
								<div align="center">			 
						  		<a class="btn btn-primary btn-sm saveIT" href="#" onclick="loadingData();document.modfamily.submit();" title="Edit Family"><i class="fas fa-users"></i> SAVE CHANGES</a>						
								<a class="btn btn-sm btn-danger"  onclick="loadingData();" href="schoolfamilyadmin.jsp">EXIT</a>	
								</div>			    
	                                 
                              
                          </form>
                         
	
	</div>
						
						
					
								
</body>
</html>