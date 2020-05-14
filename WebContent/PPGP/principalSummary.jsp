<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,com.awsd.school.*"
       isThreadSafe="false"%>
       
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
       

<esd:SecurityCheck permissions='PPGP-VIEW-SUMMARY,PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST' />

<%
  User usr = (User) session.getAttribute("usr");
  PPGP ppgp = null;  
  PPGPGoal goal = null;
  PPGPTask task = null;  
  Vector<Personnel> teachers = null;
  Personnel p = null;

  if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"))
  {
    if(request.getAttribute("PRINCIPAL")!=null)
    {
      p = (Personnel) request.getAttribute("PRINCIPAL");
    }
    else
    {
%>    
<jsp:forward page="/MemberServices/PPGP/viewGrowthPlanProgramSpecialistSummary.html"/>
<%
	}
  }
  else
  {
    p = usr.getPersonnel();
  }
  
  teachers = PersonnelDB.getPersonnelList(p.getSchool());
  session.setAttribute("PPGP-POLICY", new Boolean(true));
  
  
 String syear="CUR";
  if(request.getAttribute("syear")!=null){
	  syear=request.getAttribute("syear").toString();
  }

  
  
%>
<c:set var="query" value="${ param.syear }"/>
<c:if test="${empty param.syear}">
	<c:set var="query" value="CUR"/>
</c:if>


<html>

	<head>
		<title>Principals Summary</title>		
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold; font-size:16px;}
.tableResult {font-weight:normal;}
.tableTitleWide {column-span: all;}
.tableTitleL {font-weight:bold;font-size:16px;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;font-size:16px;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>	
	 
	</head>

	<body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">
	               	<div style="float:right;font-size:36px;font-weight:bold;margin-top:-10px;color:rgba(0, 102, 153, 0.3);">
		               	<c:choose>
						<c:when test="${param.syear eq 'PREV'}">
						<%=PPGP.getPreviousGrowthPlanYear()%>
						</c:when>
						<c:otherwise>
						<%=PPGP.getCurrentGrowthPlanYear()%>
						</c:otherwise>
						</c:choose>
	               	</div>
	               		               	
	               	<span style="font-size:16px;"><span style="text-transform:capitalize;font-weight:bold;"><%=p.getFullNameReverse()%></span>(Principal)<br/>
	               	
	               	<c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<%=PPGP.getPreviousGrowthPlanYear()%>
					</c:when>
					<c:otherwise>
					<%=PPGP.getCurrentGrowthPlanYear()%>
					</c:otherwise>
					</c:choose>
	               	 Learning Plan Summaries for <br/>
	               	<b><%=p.getSchool().getSchoolName()%></b> Teaching Staff</span><br/>
	               	<b>Date:</b> <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%>
	               	</div>
      			 	<div class="panel-body">
			        Below are your current school Teaching Staff's Learning Plan Summaries for 
			        <c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<%=PPGP.getPreviousGrowthPlanYear()%>
					</c:when>
					<c:otherwise>
					<%=PPGP.getCurrentGrowthPlanYear()%>
					</c:otherwise>
					</c:choose>.
			        You can view each Learning plan, delete various Learning Plans, remove an educator from your school staff (if they no longer teach there), or retire an individual if they are officially retired.
	<br/><br/>
	
	
		<% for(Personnel teacher : teachers) { %>		
		
		
			<!-- Only display the below positions as to not allow Admin to delete secretary. -->
			<% String position = teacher.getPersonnelCategory().getPersonnelCategoryName().toString();
			
			  		if (position.equalsIgnoreCase("Teacher") || 
					  position.equalsIgnoreCase("Teaching and Learning Assistant") || 
					  position.equalsIgnoreCase("Guidance Counsellor") ||
					  position.equalsIgnoreCase("Substitute Teacher") ||					  
					  position.equalsIgnoreCase("Vice Principal") ||
					  position.equalsIgnoreCase("Principal")) {%>
				
				
				<table class="table table-condensed table-bordered" style="font-size:12px;">
					<tbody>
					<tr style="background-color:#0066cc;color:White;">
					<td colspan=4><span style="text-transform:capitalize;font-size:14px;"><b><%=teacher.getFullName()%></b> (<%=position%>)</span>
			        <div style="float:right;">
			<% if(usr.checkPermission("PPGP-VIEW-SUMMARY-EDIT")){ %>
					    <a href="viewGrowthPlanSummary.html?pid=<%=teacher.getPersonnelID()%>&syear=${query}" title="Click here to view detailed summary of this persons Learning Plan." class="blankPLP<%=teacher.getPersonnelID()%> no-print btn btn-xs btn-info" onclick="loadingData()" style="text-transform:Capitalize;">VIEW PLP</a>
			<% } %>
					 
			<% if(usr.checkPermission("PPGP-VIEW-SUMMARY-DELETE") || usr.checkPermission("PERSONNEL-CHANGESCHOOL") || usr.checkPermission("PERSONNEL-CHANGECATEGORY")){
				
			    	if((ppgp!=null)&&usr.checkPermission("PPGP-VIEW-SUMMARY-DELETE")){ %>
					      <a href='deleteGrowthPlan.html?id=<%=ppgp.getPPGPID()%>&syear=${query}' class="blankPLP<%=teacher.getPersonnelID()%> no-print btn btn-xs btn-danger" title="Delete this Learning Plan?">DELETE PLP</a>
					<%}
			    		if(usr.checkPermission("PERSONNEL-CHANGESCHOOL")){ %>
					      <a href='removeTeacherFromList.html?pid=<%=teacher.getPersonnelID()%>&sid=-1&referrer=viewGrowthPlanPrincipalSummary.html?syear=${query}' title="Remove this person from your school." class="no-print btn btn-warning btn-xs">REMOVE FROM LIST</a></span>
					    <%}
			    				if(usr.checkPermission("PERSONNEL-CHANGECATEGORY")){ %>
					     		<a href='retireTeacher.html?pid=<%=teacher.getPersonnelID()%>&category=111&referrer=viewGrowthPlanPrincipalSummary.html?syear=${query}' title="Is this person Retired?" class="no-print btn btn-danger btn-xs">RETIRED?</a></span>
					    <%}}%>
					 </div>   
					</td>
					</tr>	
					
					<c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<% ppgp = teacher.getPreviousPPGP();  %>
					</c:when>
					<c:otherwise>
					<% ppgp = teacher.getPPGP(); %>
					</c:otherwise>
					</c:choose>
					
									
					
					<% if(ppgp==null) { %>
					<script>
					$(".blankPLP<%=teacher.getPersonnelID()%>").css("display","none");
					</script>
					      <tr>			      
					      <td colspan=4><div class="alert alert-danger" align="center"><span style="text-transform:Capitalize;font-weight:bold;"><%=teacher.getFullNameReverse()%></span> has not yet submitted a Learning Plan.</div></td>
					      </tr>
					<% } else { %>
						<script>
						$(".blankPLP<%=teacher.getPersonnelID()%>").css("display","inline");
						</script>
						<%
						int goalNum=0;
					   for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
					    goal = entry.getValue(); 
					    goalNum++;%>					
					
					<tr style="color:#0066cc;background-color:#e6f2ff;font-size:14px;">
					<td colspan=4><b>GOAL #<%=goalNum%>:</b> <%=goal.getPPGPGoalDescription()%></td>
					</tr>
					
					<%int cntrt=0; %>
					
					<% for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
					          task = g_entry.getValue(); 
					          cntrt++;
					          %>
					          
														<tr class="warning">
													    <td colspan=4><b>TASK/STRATEGY #<%=cntrt%>:</b> <%=task.getDescription()%></td>	
													    </tr>
													    <tr class="active">
													    <td colspan=4><b>How may technology support the successfully attainment of your goal?</b></td>
													    </tr>
													    <tr>
													    <td colspan=4><%= task.getTechnologySupport() %></td>	
													    </tr>
													    <tr class="active">
													    <td colspan=2 style="text-align:center;font-weight:bold;">RESOURCES/SUPPORT</td>
													    <td colspan=2 style="text-align:center;font-weight:bold;">TECHNOLOGY</td>
													    </tr>
													    <tr class="active">
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													   	</tr>
													    <tr>
													    <td><%=task.getSchoolSupport()%></td>
													    <td><%=task.getDistrictSupport()%></td>
													    <td><%=(task.getTechnologySchoolSupport()!=null)?task.getTechnologySchoolSupport():""%></td>
													    <td><%=(task.getTechnologyDistrictSupport()!=null)?task.getTechnologyDistrictSupport():""%></td>
													   	</tr>
													 	<tr>
													 	<td  class="active"><b>COMPLETION DATE:</b></td>
													 	<td colspan=3><%=task.getCompletionDate()%></td>
													 	</tr>
													 	<tr>
													 	<td class="active"><b>SELF EVALUATION:</b></td>
													 	<td colspan=3>
													 	<%=task.getSelfEvaluation() !=null?task.getSelfEvaluation():"<div class='alert alert-danger' align='center'><span style='text-transform:Capitalize;font-weight:bold;'>"+teacher.getFullNameReverse()+"</span> has not yet completed the self evaluation for this Goal. Completion Date may not have yet passed.</div>"%>
													 	</td>
													 	</tr>
									
					<% } %>
					<% }} %>
					
					</tbody>
					</table>
					<div class="pagebreak"></div>
				<% } %>
					
		<%}%>
	
	</div></div></div>	
		
	</body>
</html>