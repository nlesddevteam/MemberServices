<%@ page language="java"
         import="java.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<%
ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
JobOpportunityBean[] jobs = JobOpportunityManager.getApplicantOpenJobOpportunityBeans(profile.getSIN());
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>

</head>
<body>


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Applicant Position Application(s)</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
										
					<%if(jobs.length > 0){%>			
					Below are your current job competition applications:<br/>	
							<table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='15%'>COMPETITION #</th>
									        <th width='50%'>POSITION TITLE</th>
									        <th width='15%'>CLOSING DATE</th>									        															       
									        <th width='20%' class="no-print">OPTIONS</th>
									      </tr>
									    </thead>
									    <tbody> 
			                                    <%for(int i=0; i < jobs.length; i++){%>
			                                    <tr>
			                                      <td><%=jobs[i].getCompetitionNumber()%></td>
			                                      <td><%=jobs[i].getPositionTitle()%></td>
			                                      <td><%=jobs[i].getFormatedCompetitionEndDate()%></td>
			                                      <td class="no-print"><a href="#" data-href="/employment/view_job_post.jsp?comp_num=<%=jobs[i].getCompetitionNumber()%>" class="showModal btn btn-xs btn-primary">VIEW</a></td>
			                                    </tr>
			                                 <% } %>
			                            </tbody>
			                        </table>
			                                 
			                                 <% }else{%>
			                                <span style="color:Grey;">No open job applications on file.</span>
			                                 <%}%>
									    
</div></div></div></div>   

 <div align="center" class="no-print">
                                        <a class="btn btn-xs btn-danger" href="/employment/index.jsp">Return to Employment</a>  <a class="btn btn-xs btn-danger" href="view_applicant.jsp">Return to Your Profile</a>
</div>  									    
									    
<br/>
</body>
</html>
