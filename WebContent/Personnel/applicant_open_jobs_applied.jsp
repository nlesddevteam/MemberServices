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
  JobOpportunityBean[] jobs = null;
  
  jobs = JobOpportunityManager.getApplicantOpenJobOpportunityBeans(profile.getSIN());
  HashMap sublists = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
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
					
					<div style="color:DimGrey;font-size:14px;font-weight:bold;margin-top:10px;">Current Job Competition Application(s)</div>  
									
					<%if(jobs.length > 0){%>
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
					
			             <hr>		
						<div style="color:DimGrey;font-size:14px;font-weight:bold;margin-top:10px;">Current Substitute List Application(s)</div>  
									
					
									
			                                  <%Map.Entry[] entries = (Map.Entry[])sublists.entrySet().toArray(new Map.Entry[0]);
			                                  	ApplicantSubListInfoBean info = null;
			                                  	if(entries.length > 0){ %>
			                   <table class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='60%'>LIST</th>
									        <th width='20%'>DATE APPLIED</th>									        															       
									        <th width='20%'>STATUS</th>
									      </tr>
									    </thead>
									    <tbody>
									    <%for(int i=0; i < entries.length; i++){
			                                    	info = (ApplicantSubListInfoBean) entries[i].getValue();
			                                  %>
			                                    <tr>
			                                      <td><%=info.getSubList().getTitle()%> (<%=info.getSubList().getSchoolYear()%> - <%=info.getSubList().getRegion().getName()%>)</td>
			                                      <td><%=info.getAppliedDateFormatted()%></td>
			                                      <td><%=(info.isNewApplicant()?"<span style='background-color:Blue;color:white;'>&nbsp;NEW&nbsp;</span>":(info.isShortlisted()?"<span style='background-color:Green;color:white;'>&nbsp;APPROVED&nbsp;</span>":(info.isNotApproved()?"<span style='background-color:Red;color:white;'>&nbsp;NOT APPROVED&nbsp;</span>":"<span style='background-color:Yellow;color:black;'>&nbsp;IN POSITION / REMOVED&nbsp;</span>")))%></td>
			                                    </tr>
			                                 <% } %>
			                                 
			                            </tbody>
			                        </table>
			                                 
			                                 <% }else{%>
			                                <span style="color:Grey;">No open job applications on file.</span>
			                                 <%}%>
			                                 
			                        <br/>         
			                                 
			                      <div align="center" class="no-print">
                                        <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  <a class="btn btn-xs btn-primary" href="view_applicant.jsp">Your Profile</a>
                                   </div>            
			                                 

</div></div></div></div>   


<!-- View Job -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">View Competition Application</h4>
      </div>
      <div class="modal-body">
       <iframe src="" width="500" height="400" frameborder="0"></iframe>
    
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<script>
$(document).ready(function(){
	 $(".showModal").click(function(e){
	   e.preventDefault();
	   var url = $(this).attr("data-href");
	   $("#myModal iframe").attr("src", url);
	   $("#myModal").modal("show");
	 });
	});

</script>
                             	
</body>
</html>
