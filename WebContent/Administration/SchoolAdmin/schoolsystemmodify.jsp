<%@ page language="java"
        session="true"
        import="java.util.*,
        com.awsd.security.*,
        com.awsd.school.*,
        com.awsd.personnel.*,
                java.text.*"%>
                
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>                
                
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%!User usr = null;
  School school = null;
  Personnel principal = null;
  int principalID;
  Personnel viceprincipal = null;
  int viceprincipalID;
  Personnel tmp = null;
  Iterator sch_iter = null;
  DistrictPersonnel personnel = null;
  Iterator iter = null;%>
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

%>
<html>
  <head>
    <title>School System Edit</title>
    <style>
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
		<script>			
	$('document').ready(function(){		
		
		$('input[type=text]').addClass('form-control');
    	$('select[name="schoolSystemAdmin.personnelID"]').addClass('form-control').addClass('choicesAdmin');
    	$('select[name="schoolSystemAdminBackupAsIntArray"]').addClass('form-control').addClass('choicesBakAdmin');    	
    	
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
		
        		var schoolSystemSelect = new Choices('.choicesSchools', {
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
<div class="siteHeaderGreen">Modify/Edit School System</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 

<form name="modsys" id="modsys" action="schoolSystemAdmin.html" method="post" class="was-validated">

<html:hidden property="op" value="mod-confirm" />  
<html:hidden name="SCHOOLSYSTEM" property="schoolSystemID" />
 
<div class="row container-fluid" style="padding-top:5px;">
<div class="col-lg-12 col-12">
<b>School System Name:</b>
<html:text name="SCHOOLSYSTEM" property="schoolSystemName"  />
</div>
</div>

<br/><br/>

<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
								<b>Administrator (Max 1)</b><br/>
 								Start typing name to search/select.
 						<logic:notEmpty name="SCHOOLSYSTEM" property="schoolSystemAdmin">
                          <html:select name="SCHOOLSYSTEM" property="schoolSystemAdmin.personnelID" size="1">
                            <html:option value="-1">SELECT ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
 						</logic:notEmpty>
                       
 						<logic:empty name="SCHOOLSYSTEM" property="schoolSystemAdmin">
                          <html:select property="schoolSystemAdmin.personnelID" size="1" value="-1">
                            <html:option value="-1">SELECT ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                       </logic:empty>
                      		</div>
                      		<div class="col-lg-6 col-12">                      
 <b>Backup Administrator (Max 6)</b><br/>
 Start typing name to search/select.
                        <logic:notEmpty name="SCHOOLSYSTEM" property="schoolSystemAdminBackup">
                          <html:select name="SCHOOLSYSTEM" property="schoolSystemAdminBackupAsIntArray" multiple="true">
                            <html:option value="-1">SELECT BACKUP ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                        </logic:notEmpty>
                        
                        <logic:empty name="SCHOOLSYSTEM" property="schoolSystemAdminBackup">
                          <html:select property="schoolSystemAdminBackupAsIntArray" value="-1" multiple="true">
                            <html:option value="-1">SELECT BACKUP ADMINISTRATOR</html:option>
                            <html:options collection="PERSONNEL" property="personnelID" labelProperty="display" /> 
                          </html:select>
                        </logic:empty>
                    </div>
</div>
<br/><br/>

<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-12 col-12">
								<b>Schools in System (Max 75)</b><br/>
 								Start typing name to search/select schools.
<select name="schools" id="schools" class="form-control choicesSchools" size="10" multiple="multiple">
 										<c:forEach var="entrys" items="${SELECTEDSCHOOLS}">
								    	 <option value="${entrys.key}" selected>${entrys.value}</option>
								    	 </c:forEach>								    	
								    	<%
                                  	for(School s : SchoolDB.getSchoolsNotAssignedSchoolSystem()){
                                 	 %>
                                  	<option value="<%=s.getSchoolID()%>">
                                  		<%=s.getSchoolName()%> [<%= s.getZone() != null ? s.getZone().getZoneName() : "" %><%= s.getRegion() != null ? " - " + s.getRegion().getName():"" %>]
                                  	</option>
                                  <%}%>
								    </select>		
								    
								    <input type="hidden" id="systemSchools" name="systemSchools">

</div></div>

 <br/><br/>
                         
                         <div align="center">
                         <input type="submit" class="btn btn-success btn-sm" onclick="loadingData();getvalues();" value="Save Changes"> &nbsp; 
                       	 <a class="btn btn-sm btn-danger"  onclick="loadingData();" href="schoolsystemadmin.jsp">Cancel</a>	
                        </div>
                     
    </form>
    
    </div>
    
    <script>
    function getvalues(){
	    var listvalues = "";
	    $("#schools option:selected").each(function() {
	        if(listvalues == ""){
	            listvalues = this.value;
	        }else{
	            listvalues = listvalues + "," + this.value;
	        }
	    });
	    $("#systemSchools").val(listvalues);
	};
    
    </script>
    
    
  </body>
</html>