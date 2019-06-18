<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,com.awsd.school.*"
       isThreadSafe="false"%>
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
%>    <jsp:forward page="/MemberServices/PPGP/viewGrowthPlanProgramSpecialistSummary.html"/>
<%
	}
  }
  else
  {
    p = usr.getPersonnel();
  }
  
  teachers = PersonnelDB.getPersonnelList(p.getSchool());
  session.setAttribute("PPGP-POLICY", new Boolean(true));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

	<head>
		<title>Principals Summary</title>
		<link rel="stylesheet" href="css/summary.css" />
		<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
		<script type='text/javascript'>
			$('document').ready(function(){
				$('.pgp-list').each(function(){ 
					$(this).children().children('.task:odd').children().css({'background-color':"white"});
				})
			});
		</script>
	</head>

	<body topmargin="10" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
	
		<table align="center" width="80%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="40%" valign="top" align="left">
					<img src="images/principalsplpsummary.png" /><br />
				</td>
				<td width="33%" valign="middle" align="left">
				  <table>
				  	<tr>
				      <td>
				        <b>School Year:</b>
				      </td>
				      <td>
				        <%=PPGP.getCurrentGrowthPlanYear()%>
				      </td>
				    </tr>
				    <tr>
				      <td>
				        <b>Principal:</b>
				      </td>
				      <td>
				        <%=p.getFullName()%>
				      </td>
				    </tr>
				    <tr>
				      <td>
				        <b>School:</b>
				      </td>
				      <td>
				        <%=p.getSchool().getSchoolName()%>
				      </td>
				    </tr>
				    <tr>
				      <td>
				        <b>Date:</b>
				      </td>
				      <td>
				        <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%><br />
				      </td>
				    </tr>
				  </table>
				</td>
				<%if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST")) { %>
				  <td align='right'>
				    <img style="cursor:hand;" src="images/progspecsum-off.png"
				        onclick="self.location.href='viewGrowthPlanProgramSpecialistSummary.html';" /><br />
				  </td>
				<%}%>
			</tr>
		</table>
	
		<% for(Personnel teacher : teachers) { %>
			<center>
				<fieldset style='width:80%;padding:6px;padding-bottom:15px;'>
					<legend style='vertical-align:top;'>
						<% if(usr.checkPermission("PPGP-VIEW-SUMMARY-EDIT")){ %>
					    <span class="text"><a href="viewGrowthPlanSummary.html?pid=<%=teacher.getPersonnelID()%>" style="font-weight:bold;" title="Click here to view detailed summary"><%=teacher.getFullName()%></a></span>
					  <% } else { %>
					    <span class="text"><%=teacher.getFullName()%></span>
					  <% } %>
					  &nbsp;&nbsp;
					  <% if(usr.checkPermission("PPGP-VIEW-SUMMARY-DELETE")
					    || usr.checkPermission("PERSONNEL-CHANGESCHOOL")
					    || usr.checkPermission("PERSONNEL-CHANGECATEGORY")){
					    
					    int cnt=0;
					  %>[&nbsp;
					    <% if((ppgp!=null)&&usr.checkPermission("PPGP-VIEW-SUMMARY-DELETE")){ %>
					      <span class="text"><a href='deleteGrowthPlan.html?id=<%=ppgp.getPPGPID()%>' style='color:#FF0000; font-size:10px;'>DELETE PGP</a></span>
					    <%cnt++;}%>
					    
					    <% if(usr.checkPermission("PERSONNEL-CHANGESCHOOL")){ %>
					      <span class="text"><%=(cnt > 0)?"&nbsp;|&nbsp;":""%><a href='removeTeacherFromList.html?pid=<%=teacher.getPersonnelID()%>&sid=-1&referrer=viewGrowthPlanPrincipalSummary.html' style='color:#FF0000;font-size:10px;'>REMOVE FROM LIST</a></span>
					    <%cnt++;}%>
					    
					    <% if(usr.checkPermission("PERSONNEL-CHANGECATEGORY")){ %>
					      <span class="text"><%=(cnt > 0)?"&nbsp;|&nbsp;":""%><a href='retireTeacher.html?pid=<%=teacher.getPersonnelID()%>&category=111&referrer=viewGrowthPlanPrincipalSummary.html' style='color:#FF0000;font-size:10px;'>RETIRED?</a></span>
					    <%cnt++;}%>
					    &nbsp;]
					  <%}%>
					</legend>
					
					<table class='pgp-list' width='95%' align="center" cellpadding="0" cellspacing="1" border="0">
						<tr>
							<th width="35%" valign="middle" align="center">
								<span class="title">PD Requested</span><br />
							</th>
							<th width="25%" valign="middle" align="center">
								<span class="title">School Support</span><br />
							</th>
							<th width="25%" valign="middle" align="center">
								<span class="title">District Support</span><br />
							</th>
							<th width="15%" valign="middle" align="center">
								<span class="title">Completion Date</span><br />
							</th>
						</tr>
						<% ppgp = teacher.getPPGP(); %>  
					    
						<% if(ppgp==null) { %>
					      <tr class='task'>
					        <td colspan="4" valign="middle">
					          <span class="text" align="center"><b><font color="#FF0000">NO PPGP SUBMITTED.</font></b></span><br />
					        </td>
					      </tr>
						<% } else { 
					      for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
					        goal = entry.getValue(); %>
					        <tr class='goal'>
					          <td class='last' colspan="4" valign="top">
					          	<span class="title2">Goal:&nbsp;<%=goal.getPPGPGoalDescription()%></span>
					          </td>
					        </tr>
									<% for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
					          task = g_entry.getValue(); %>
					          <tr class='task'>
					            <td width="35%" valign="top">
					              <span class="text">
					              	<%=task.getDescription()%><br /><br />
					              	<i>How may techology support the successfully attainment of your goal?</i><br />
					              	<%= task.getTechnologySupport() %>
					              </span><br />
					            </td>
					            <td width="25%" valign="top">
					              <span class="text">
					              	<i><U>Resource:</U></i><br />
					              	<%=task.getSchoolSupport()%>
					              	<br/><i><u>Technology:</u></i><br/>
					              	<%if(task.getTechnologySchoolSupport() != null){ %>
					              		<%=task.getTechnologySchoolSupport()%>
					              	<%} %>
					              </span><br />
					            </td>
					            <td width="25%" valign="top">
					              <span class="text">
					              	<i><u>Resource:</u></i><br />
					              	<%=task.getDistrictSupport()%><br/>
					              	<i><u>Technology:</u></i><br/>
					              	<%if(task.getTechnologyDistrictSupport() != null){ %>
					              		<%=task.getTechnologyDistrictSupport()%>
					              	<%} %>
					              </span>
					              <br />
					            </td>
					            <td class='last' width="15%" valign="top" align="center">
					              <span class="text"><%=task.getCompletionDate()%></span><br />
					            </td>
					          </tr>
									<% } %>
					      <% } %>
					  <% } %>
					</table>
				</fieldset>
			</center>
		<%}%>
	
		<table width='100%'>
			<tr>
				<td valign="bottom" bgcolor="#FFCC00" style='padding:0;'>
					<img src="images/spacer.gif" border='0' width="1" height="2" /><br />
				</td>
			</tr>
		</table>
		
	</body>
</html>