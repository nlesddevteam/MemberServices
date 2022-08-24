<%@ page language="java" 
         	session="true"
         	import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*"%>

<%
  User usr = null;
  SchoolFamilies families = null;
  SchoolFamily family = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator fam_iter = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;
  Vector admins = null;
  Personnel aps[] = null;
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

  families = new SchoolFamilies();
  fam_iter = families.iterator();
  admins = PersonnelDB.getDistrictPersonnel();
  p_iter = admins.iterator();
%>

<html>
<head>
<title>DOS School Family Admin</title>
  <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
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
<div class="siteHeaderGreen">Create School Family</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
								
     					
                          <form name="addfamily" action="schoolFamilyAdmin.html" method="post" class="was-validated">
                          <input type="hidden" name="op" value="add">                      
                          
                          <div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>NAME OF SCHOOL FAMILY:</b><br/>
									Please enter a name (up to 60 characters) for this family.
									<input type="text" name="family_name" class="form-control" maxlength="60" placeholder="Enter a Name for this Family" required autofill="off">
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-6 col-12">	
									<b>ADMINISTRATOR (DOS/SEO): (Seelct only 1)</b><br/>
									Start to type in the name of the DOS to find if not in the initial list.
									<select name="ps_id" class="form-control choicesDOS" required>
			                                  <option value="-1">NO DOS ASSIGNED</option>
			                                  <%while(p_iter.hasNext()){
			                                    p = (Personnel) p_iter.next();
			                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%> (<%=p.getUserName()%>)</option>
			                                  <%}%>
									  </select>
									  <div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
								</div>
								</div>							
								<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-12 col-12">							
											<b>SCHOOL(s) TO INCLUDE IN FAMILY (Select up to 40)</b><br/>
											Start to type in the name of the school to find if not in the initial list.
													<select multiple name="schools" class="form-control choicesFam" required>
						                                  <%
						                                  	sch_iter = SchoolDB.getSchoolsNotAssignedSchoolFamily().iterator();
						                                                                      while(sch_iter.hasNext()){
						                                                                        school = (School) sch_iter.next();
						                                  %><option value="<%=school.getSchoolID()%>"><%=school.getSchoolName()%></option>
						                                  <%}%>
													</select>
													<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
								</div>
								</div>
								
								<br/><br/>									
																
								<div align="center">			 
						  		<a class="btn btn-primary btn-sm" href="#" onclick="loadingData();document.addfamily.submit();" title="Create family"><i class="fas fa-users"></i> SAVE NEW FAMILY</a>						
								<a class="btn btn-sm btn-danger"  onclick="loadingData();" href="schoolfamilyadmin.jsp">EXIT</a>	
								</div>						
																
                                
                          </form>
											
					
						
</div>
						
	
			
	 <script>
	 
    $(document).ready(function(){

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
    	

    	});
				</script>					
										
</body>
</html>
