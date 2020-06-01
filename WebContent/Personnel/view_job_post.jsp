<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*, 
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.awsd.security.*,
                 java.util.*,java.lang.*,
                 java.text.*,
                 org.apache.commons.lang.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<%
User usr = null;
JobOpportunityBean opp = null;
TeacherRecommendationBean[] rec = null;
AdRequestBean ad = null;
RequestToHireBean rth = null;
usr = (User) session.getAttribute("usr");

if(request.getParameter("comp_num") != null)
  opp = JobOpportunityManager.getJobOpportunityBean(request.getParameter("comp_num"));

if(opp != null){
	  	ad = AdRequestManager.getAdRequestBean(opp.getCompetitionNumber());
		rec = RecommendationManager.getTeacherRecommendationBean(opp.getCompetitionNumber());
 }

session.setAttribute("JOB", null);
session.setAttribute("SUBLIST", null);
session.setAttribute("sfilterparams", null);
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
</style>
<script type="text/javascript">
  function openWindow(id,url,w,h, scroll) {
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;
  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}
</script>

<script type="text/javascript">
$('document').ready(function(){
	
	$('#btn_show_add_applicant_dialog').click(function(){
		$("#add_applicant_dialog").dialog('open');
		return false;
	});
	
	$('#btn_apply_filter').click(function(){
		$('#applicant_uid').empty();
		$('#applicant_uid').css("display","block");
		$('#response_msg').empty();
		$('#response_msg').hide().removeClass("alert-success alert-danger").addClass("alert-info");
		
		if($('#applicant_uid_filter').val() != '')
			$.post("getFilteredApplicants.html", { filter: $('#applicant_uid_filter').val(),filtertype: $('#filtertype').val() }, 
				function(data){
					parseFilteredApplicantsResponse(data);
				}, "xml");
		else{
			$('#response_msg').html('Apply a filter to generate applicant list.');
			$('#response_msg').show();
		}
	});
	
	$('#btn_add_applicant').click(function(){
		
		$.post("addJobApplicant.html", { comp_num: '<%=opp.getCompetitionNumber()%>', sin: $('#applicant_uid').val(), ajax: true }, 
				function(data){
					parseAddApplicantResponse(data);
				}, "xml");
	});
});

function parseFilteredApplicantsResponse(data){
	var xmlDoc=data.documentElement;
	var beans = xmlDoc.getElementsByTagName("APPLICANT-PROFILE");
	
	if(beans.length > 0){
		var uid = "";
		var fullname = "";
		var cntr = 0;
		
		$(data).find('APPLICANT-PROFILE').each(function(){
			uid = $(this).find('SIN').text();
			fullname = $(this).find('SURNAME').text() + ', ' + $(this).find('FIRST-NAME').text() + ' (' + $(this).find('EMAIL').text() + ')';
			cntr++;
			$('#applicant_uid').append('<OPTION VALUE="' + uid + '">' + fullname +'</OPTION>');
		});
		successMsg = "Success! " + cntr + " matches found.<br/>Select an applicant from the list below to add to position <%=opp.getCompetitionNumber()%>.";
		$('#response_msg').html(successMsg).removeClass("alert-info").addClass("alert-success");
		$('#applicant_uid').css("display","block");
		$('#response_msg').show();
		$("#btn_add_applicant").css("display","block");
	}
	else{
		$('#response_msg').html('Sorry, no matching applicants found. Try again.').removeClass("alert-info").addClass("alert-danger");
		$('#applicant_uid').css("display","none");
		$("#btn_add_applicant").css("display","none");
		
		$('#response_msg').show();
	}
}

function parseAddApplicantResponse(data){
	var xmlDoc=data.documentElement;
	var msg = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
	
	$('#response_msg').html(msg);
	$('#response_msg').show();
}
</script>

		<script>
			$("#loadingSpinner").css("display","none");
		</script>
		
 <script type="text/javascript">
    function confirmDelete()
    {
      	
    	$.ajax({
            url: "deletePost.html?comp_num=<%=request.getParameter("comp_num")%>&confirmed=true",           
            success: function(){
            	$("#deleteOK").css("display","inline");
            	$("#deleteBtn").css("display","none");
            },
            error: function(){
            	$("#deleteError").css("display","inline").delay(5000).fadeOut();
            }
   		 });
 	
    }  
    
    function confirmCancel()
    {
        var textReason = document.getElementById("textReason");

        if (textReason.value == '') {
            $("#textWarning").css("display","inline").delay(5000).fadeOut();
            $("#textReason").focus();
            $("#cancelBtn").css("display","none");
            return false;
        } else {
        	$.ajax({
                url: 'cancelPost.html?comp_num=<%=request.getParameter("comp_num")%>&confirmed=true&textReason=' + textReason.value,           
                success: function(){
                	$("#cancelOK").css("display","inline");
                	$("#cancelBtn").css("display","none");
                },
                error: function(){
                	$("#cancelError").css("display","inline").delay(5000).fadeOut();
                }
       		 });
     	
        }
   	}
  </script>
  		
</head>
<body><br/>

<%if(request.getAttribute("msg")!=null){%>
	<div class="alert alert-warning" style="text-align:center;">                                       
		<%=(String)request.getAttribute("msg")%>
	</div>
<%}%>	

<div class="panel panel-success">
  <div class="panel-heading"><b>Viewing Job Post <%=request.getParameter("comp_num")%></b></div>
  <div class="panel-body">
  				<div class="table-responsive">      			 	       
      			<job:ViewPost job="<%= opp %>" />
   				<input type="hidden" id="filtertype" name="fitlertype" value="<%=opp != null ? opp.getIsSupport() : "T"%>">
   				<table class='table table-striped table-condensed' style='font-size:12px;'>
   				
   				<tbody>
			     <tr>
			     <td class='tableTitle'>STATUS:</td>
			     <td class='tableResult'>
			     				<%if((opp != null) && (opp.isCandidateListPrivate())){ %>
                                	<span style="color:red;">Candidate List is Private</span><br/>
                                <%}%>
                                
                                <%if(ad != null){%>
	                                <span>Ad Requested by <span style="text-transform:Capitalize;"><%= ad.getHistory(RequestStatus.SUBMITTED).getPersonnel().getFullNameReverse() + (ad.isUnadvertised()?" is <b>unadvertised</b>":"")%></span></span><br/>
                                <%}%>
                                
                                <%if((opp != null) && (opp.isCancelled())){ %>
                                	<span style="color:red;">Position cancelled on <%= opp.getFormatedCancelledDate() %></span><br/>
                                <%}%>
                                <%if((opp != null) && (opp.isReopened())){ %>
                                	<span style="color:red;">Position reopened on <%= opp.getFormattedReopenedDate() %> by <%= StringUtils.capitaliseAllWords(opp.getReopenedBy().getFullNameReverse()) %></span>
                                <%}%>
			     </td>
			     </tr>
    
			     </tbody>
			     </table>
   				</div>				
							
</div>
</div>	


<!-- ADMINISTRATIVE FUNCTIONS -->
		                             
<div class="no-print" align="center">
                    
                <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">	                                       
		             <%if(opp != null){%>
				     <a href='#' title='Print this page (pre-formatted)' class="btn btn-xs btn-primary" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Page</a>
		             <% } %>			                                					
                </esd:SecurityAccessRequired>                               				
                                       
                <%if(opp != null && !opp.isCancelled() && !opp.isAwarded()){%>
	           <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">	                                         					
		       		<a class="btn btn-xs btn-primary" href='viewJobInterviewGuide.html?comp_num=<%=request.getParameter("comp_num")%>'>View/Set Interview Guide</a>
		       </esd:SecurityAccessRequired>
               <% } %>
                                         				
				<%if(!opp.isCandidateListPrivate() || usr.checkPermission("PERSONNEL-ADMIN-VIEW-PRIVATE-CANDIDATE-LIST")){ %>
					<%if(!(opp.getJobType().equal(JobTypeConstant.LEADERSHIP) && usr.checkRole("SENIOR EDUCATION OFFICIER")) || usr.checkRole("JOB APPS - VIEW PRIVATE")){ %>
  				    <a class="btn btn-xs btn-primary" onclick="loadingData()" href='viewJobApplicants.html?comp_num=<%=request.getParameter("comp_num")%>'>View Applicants</a>
  				
 				<%}%>
 				<% if(opp.getJobType().equal(JobTypeConstant.POOL)) { %>
 					<a class="btn btn-xs btn-primary" onclick="loadingData()" href='viewPoolHighlyRecommendedList.html?comp_num=<%=request.getParameter("comp_num")%>'>View Highly Recommended</a>
 				<% } %>
 			<%}%>
                                          			
       		<%if(opp != null && !opp.isCancelled() && !opp.isAwarded()){%>
        		<%if(usr.checkPermission("PERSONNEL-ADMIN-ADVANCED") || ((ad != null) && ad.isUnadvertised() && ad.getHistory(RequestStatus.SUBMITTED).getPersonnel().equals(usr.getPersonnel()))){%>
          			<a data-toggle="modal" data-target="#add_applicant_dialog" id="btn_show_add_applicant_dialog" class="btn btn-xs btn-success" href="#" onclick="return false;">Add Applicant</a>
          		<%}%>
			<%}%>
		                            						
		    <%if((rec != null)&& (rec.length > 0)){%>
			<a class="btn btn-xs btn-primary"  href='admin_view_job_recommendation_list.jsp?comp_num=<%=request.getParameter("comp_num")%>'>View Recommendation(s)</a>
			<%}%>
		    
		    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-EXTENDED">
	                                       
	        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-MODIFY">
               <% if ((opp != null) && (!opp.isAwarded() && !opp.isClosed())) {%>
               		<a class="btn btn-xs btn-danger" href="#" data-toggle="modal" data-target="#deletePost">Delete Post</a>               	
                 	<a class="btn btn-xs btn-info"  href='admin_post_job.jsp?comp_num=<%=request.getParameter("comp_num")%>'>Edit Post</a>
               <%}%>
               <%if ((opp != null) && (!opp.isCancelled() && !opp.isAwarded())) {%>
                 	<a class="btn btn-xs btn-warning" href="#" data-toggle="modal" data-target="#cancelPost">Cancel Post</a>
               <%}%>
            </esd:SecurityAccessRequired>
                                                  
             
          </esd:SecurityAccessRequired>
          
                     
          
          <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
      
</div>                               
    <br/>                    
<!-- Add Job Applicant Modal Revised for Bootstrap ----------------------------------------------------------------->
<div id="add_applicant_dialog" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Job Applicant to <%=request.getParameter("comp_num")%></h4>
      </div>
      <div class="modal-body">
      
				  <div class="input-group">
				  <input type="text" class="form-control input-sm" id='applicant_uid_filter' placeholder="Who you looking for?"  autocomplete="off">
   						<div class="input-group-btn">
      					<button class="btn btn-success" id='btn_apply_filter' type="submit">
        				<i class="glyphicon glyphicon-search" style="height:16px;"></i>
      					</button>
    					</div>	  
				   </div>
				 <div class="alert alert-info" id="response_msg">Enter the applicant's first and/or last name to search.</div>
				  <div class="input-group">
				  <SELECT name='applicant_uid' id='applicant_uid' class="form-conrol" style="display:none;"></SELECT> 				
				  </div>
				  
				
      </div>
      <div class="modal-footer">
        <button type="button" id='btn_add_applicant' class="btn btn-success btn-xs" style="display:none;float:left;">Add Applicant</button>
        <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


<!-- Modal for Delete Post ------------------------------------------------------------------------------------>
<div id="deletePost" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Delete Post <%=request.getParameter("comp_num")%></h4>
      </div>
      <div class="modal-body">      
       
 		<%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%> 
        <div class="alert alert-danger">Are you sure you want to delete posting <%=request.getParameter("comp_num")%>?</div>                               
        <div id="deleteOK" class="alert alert-success" style="display:none;">SUCCESS! Posting deleted.</div>
         <div id="deleteError" class="alert alert-danger" style="display:none;">ERROR! Posting could not be deleted.</div>
       </div>
      <div class="modal-footer">
      <form>
       <a href="#" id="deleteBtn" type="button" class="btn btn-success btn-xs" style="float:left;" onclick="confirmDelete();">Yes</a>
       
       <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Cancel</button>
       </form>
      </div>
    </div>

  </div>
</div>

<!-- Modal for Cancel Post------------------------------------------------------------------------------------->
<div id="cancelPost" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Cancel Post <%=request.getParameter("comp_num")%></h4>
      </div>
      <div class="modal-body">
        <div class="alert alert-danger">Are you sure you want to cancel posting <%=request.getParameter("comp_num")%>?</div>
       
       Please enter reason for cancellation:<br/>
       <form>
       <textarea rows="6" class="form-control" id="textReason"></textarea>
       <div class="alert alert-danger" id="textWarning" style="display:none;">Please enter valid reason for cancellation.</div>
       </form>
       <div id="cancelOK" class="alert alert-success" style="display:none;">SUCCESS! Posting cancelled.</div>
       <div id="cancelError" class="alert alert-danger" style="display:none;">ERROR! Posting could not be cancelled.</div>
       
      </div>
      <div class="modal-footer">
        <a href="#" id="cancelBtn" type="button" class="btn btn-success btn-xs" style="float:left;" onclick="confirmCancel();">Yes</a>
         <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Cancel</button>
      </div>
    </div>

  </div>
</div>
  
</body>
</html>
