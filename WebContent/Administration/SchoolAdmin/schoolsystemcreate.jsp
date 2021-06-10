<%@ page language="java" 
         session="true"
         import="java.sql.*, 
         java.util.*,
         java.text.*,
         com.awsd.security.*, 
         com.awsd.weather.*,
         com.awsd.school.*,
         com.awsd.personnel.*"
         isThreadSafe="false" %>
         
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = null;
  SchoolSystems systems = null;
  SchoolSystem sys = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator<SchoolSystem> sys_iter = null;
  Iterator<School> sch_iter = null;
  Iterator<Personnel> p_iter = null;
  Vector<Personnel> principals = null;
  Personnel ap[] = null;

  systems = new SchoolSystems();
  
  sys_iter = systems.iterator();
  principals = PersonnelDB.getDistrictPersonnel();
  p_iter = principals.iterator();
%>

<html>
<head>
<title>School System Administration</title>
    <style>
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
	<script>	
		
	$('document').ready(function(){

		var adminForSystemSelect = new Choices('.choicesAdmin', {
        	removeItemButton: true,
        	maxItemCount:1,
        	paste: false,
            duplicateItemsAllowed: false,
            editItems: true,
        	searchResultLimit:20,
        	renderChoiceLimit:25
        	});		
    	
    		var backupAdminForSystemSelect = new Choices('.choicesBakAdmin', {
        	removeItemButton: true,
        	maxItemCount:6,
        	paste: false,
            duplicateItemsAllowed: false,
            editItems: true,
        	searchResultLimit:20,
        	renderChoiceLimit:25
        	});		
    		
    		var schoolsForSystemSelect = new Choices('.choicesSchools', {
            	removeItemButton: true,
            	maxItemCount:75,
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
<div class="siteHeaderGreen">Create a School System</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage">         

 <form name="addsys" action="schoolSystemAdmin.html" method="post" class="was-validated">
                          <input type="hidden" name="op" value="add">
													
	<div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12">
<b>School System Name: (Max 75 characters)</b>	
<input type="text" name="ss_name" class="form-control" placeholder="Please enter a name for this system" maxlength="75">
</div>
</div>
<br/>
<div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-6 col-12"><b>Administrator: (Max 1)</b><br/>
 Start typing name to search/select.
										<select name="ss_admin" class="form-control choicesAdmin">
                                  <option value="-1">NO ADMINISTRATOR ASSIGNED</option>
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
</div>
<div class="col-lg-6 col-12"><b>Backup Administrator: (Max 6)</b><br/>
 Start typing name to search/select.
									<select name="ss_admin_bckup" class="form-control choicesBakAdmin">
                                  <option value="-1">NO BACKUP ADMINISTRATOR ASSIGNED</option>
                                  <%p_iter = principals.iterator();
                                    while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
 </div>
 </div>
 <br/>                               
  <div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12">                              
<b>Schools in System (Max 75):</b><br/>
 Start typing name to search/select. Only schools not currently assigned to a system will be displayed.
			<select multiple="multiple" name="schools" class="form-control choicesSchools">
                                  <%
                                  	for(School s : SchoolDB.getSchoolsNotAssignedSchoolSystem()){
                                  %>
                                  	<option value="<%=s.getSchoolID()%>">
                                  		<%=s.getSchoolName()%> [<%= s.getZone() != null ? s.getZone().getZoneName() : "" %><%= s.getRegion() != null ? " - " + s.getRegion().getName():"" %>]
                                  	</option>
                                  <%}%>
			</select>
</div>
</div>			
			
								<br/><br/>		
										<div align="center">						 
						 <a onclick="loadingData();document.addsys.submit();" class="btn btn-sm btn-primary" href="#">Save School System</a> &nbsp; 
						  <a onclick="loadingData();" class="btn btn-sm btn-danger" href="schoolsystemadmin.jsp">Cancel</a>
						</div>		
								
                          </form>
	</div>										
</body>
</html>
