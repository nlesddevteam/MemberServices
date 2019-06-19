<%@ page language="java"
        session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,java.text.*,com.awsd.common.*"
        isThreadSafe="false"%>

<%
  session.setAttribute("PPGP-POLICY", new Boolean(true));
%>
<%
User usr = (User) session.getAttribute("usr");
%>
<html>
<head>
<title>Professional Learning Plan</title>


</head>

<body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Professional Learning Plan Policy</b></div>
      			 	<div class="panel-body">	
	
					<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
					<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>  
					
					<div class="alert alert-danger" align="center"><b>NOTE:</b><br/>It is highly recommended to use a desktop computer, laptop, or tablet to complete your learning plan. <br/>Mobile users may find it difficult to navigate/complete their plan using small screens due to the content required.</div>
					
					
 	<%if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY") || usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST") ) { %>
		<div class="alert alert-info" align="center"><b>NOTE TO ADMINS:</b><br/>Administrator tools such as Principal Summaries and/or Progarm Specialists/Director of Schools Summaries can be found 
		above under the Administration menu or at bottom of page.</div>
	<%}%>                  
Welcome <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFullNameReverse() %></span>. A Professional Learning Plan is designed to enable employees to focus on specific aspects of their professional development that they identify as priorities. 
It is rooted in both the District Strategic Plan as well as the School Growth and Development Plan as well as the individual professional needs of the teacher. 

<p>
The continuous and deliberate learning that takes place should sustain members of the organization throughout the various phases of their career.

<br/><br/>The goals developed within the Professional Learning Plan should adhere to these guidelines:
<p><div style="float:right;"><img src="includes/img/plplg.gif" border="0"></div>
		<ul style="font-style:italic;">
			<li>all goals should be specific
			<li>all goals should be measurable
			<li>all goals should be attainable
			<li>all goals should be realistic 
			<li>all goals should be achievable within an academic year.
		</ul>

The Newfoundland and Labrador English School District believes that an established procedure is necessary to take the Professional Learning Plan from vision to action. 
The essential components of this process by the individual include:


<p>
<ul style="font-style:italic;">
	<li>a review of the School District Strategic Plan
	<li>a review of the School Growth and Development Plan 
	<li>development of two goals for current learning plan, noting
	<li>Goals
	<li>Strategies
	<li>Time Lines
	<li>Evaluation
	<li>share a copy of the plan with the school administrator
	<li>implement the plan with the support of a team of one or two colleagues
	<li>year end personal assessment of the plan with a colleague or school administration
</ul>

</li>
										
<br/><br/>
<div align="center">
					<a class="no-print btn btn-xs btn-success" href="ppgp_index.jsp" onclick="loadingData()" title="Continue to Your Learning Plan">Continue to Your Plan</a> 
				<%if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY")) { %>
						<a onclick="loadingData()" class="btn btn-xs btn-primary" title="Principal School Staff Summaries" href="viewGrowthPlanPrincipalSummary.html">Principal Summaries</a>
				<%}%> 

				<% if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST")) { %>
											<a onclick="loadingData()" class="btn btn-xs btn-primary" title="Director of Schools/Program Specialist Summaries" href="viewGrowthPlanProgramSpecialistSummary.html">DOS/PS Summaries</a>
				<%if(usr.getUserRoles().containsKey("SENIOR EDUCATION OFFICIER") || 
					 usr.getUserRoles().containsKey("SENIOR EDUCATION OFFICER") || 
					 usr.getUserRoles().containsKey("DIRECTOR OF SCHOOLS")){ %>
											<a onclick="loadingData()" class="btn btn-xs btn-primary" title="Print Summaries Summaries" href="printProgramSpecialistSummary.jsp">Print Summaries</a>
				<%} %>
											<a onclick="loadingData()" class="btn btn-xs btn-primary" title="Search All Learning Plan Summaries" href="searchPGP.html">Search Summaries</a>
				<%} %>
					<a class="no-print btn btn-xs btn-danger" title="Back to MemberServices" href="/MemberServices/">Exit</a>
</div>

</div></div></div>
</body>
</html>