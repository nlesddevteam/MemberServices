<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.awsd.security.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
		<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  AdRequestBean req = (AdRequestBean) request.getAttribute("AD_REQUEST");  
  User usr = (User) session.getAttribute("usr");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<script>
var pageWordCountConfVac = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 3990,
	}
var pageWordCountConfAd = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 3990,
	}	
</script>
<style>
		.tableTitle {font-weight:bold;width:15%;}
		.tableResult {font-weight:normal;width:85%;}
		.tableTitleL {font-weight:bold;width:15%;}
		.tableResultL {font-weight:normal;width:35%;}
		.tableTitleR {font-weight:bold;width:15%;}
		.tableResultR {font-weight:normal;width:35%;}
</style>
</head>
<body>

  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST" />
  


  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Advertisement Request (Viewing/Edit)</b></div>
      			 	<div class="panel-body">   
  						
  							<form id="frmAdRequest" action="requestAdvertisement.html" method="post">
                                  <input type="HIDDEN" name="request_id" id="request_id" value="<%=req.getId()%>">
                                  <input type="HIDDEN" name="op" id="op" value="GET_EMPLOYEES">
                                  <input type="HIDDEN" name="rid" id="rid" value="<%=req.getId()%>">
					
					<%if(request.getAttribute("msg")!=null){%>
	                           <div class="alert alert-warning" style="text-align:center;">                                       
	                               <%=(String)request.getAttribute("msg")%>
	                             </div>
	                <%}%>

                    <div class="container-fluid">
                    
                    
                    <table class='table table-striped table-condensed' style='font-size:12px;'>
                    <tbody>
                    <tr>
			     		<td class='tableTitle'>POSITION TITLE:</td>
			     		<td colspan=3 class='tableResult'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                           <input type="text" name="ad_title" id="ad_title" class="form-control" value="<%=((req!=null)&&!StringUtils.isEmpty(req.getTitle()))?req.getTitle():""%>">
		                        <%}else{%>
		                           <%=req.getTitle()%>
		                        <%}%>
			     		</td>
			     	</tr>
                    <tr>
			     		<td class='tableTitleL'>LOCATION:</td>
			     		<td class='tableResultL'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                           <personnel:Locations id='location' cls="form-control" value='<%=(req != null)?req.getLocation().getLocationDescription():""%>' onChange="doPost('GE','<%=req.getId()%>')" />
		                        <%}else{%>		                                        
		                           <%=req.getLocation().getLocationDescription()%>
		                        <%}%>
			     		</td>
			     		<td class='tableTitleR'>POSITION OWNER:</td>
			     		<td class='tableResultR'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                            <personnel:EmployeesByLocation id='owner' location='<%=(req != null)?req.getLocation().getLocationDescription():""%>' value='<%=((req != null)&&(req.getOwner() != null))?req.getOwner().getEmpId():""%>' multiple="false" cls='form-control' />
		                        <%}else{%>
		                             <%=(req.getOwner() != null)?req.getOwner().getFullnameReverse():"N/A"%>
		                        <%}%>
			     		</td>
			     	</tr>
                    <tr>
			     		<td class='tableTitleL'>UNITS:</td>
			     		<td class='tableResultL'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                             <personnel:UnitTime id="ad_unit" cls="form-control"  value='<%=(req != null)?req.getUnits():1.0%>' />
		                        <%}else{%>
		                             <%=req.getUnits()%>
		                        <%}%>
			     		</td>
			     		<td class='tableTitleR'>JOB TYPE:</td>
			     		<td class='tableResultR'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                     <job:JobType id="ad_job_type" cls="form-control" value='<%=((req != null)&&(req.getJobType() != null))?Integer.toString(req.getJobType().getValue()) :""%>' />
                                <%}else{%>
                                     <%=req.getJobType().getDescription()%>                                                                               
                                <%}%>
			     		</td>
			     	</tr>
                     <tr>
			     		<td class='tableTitle'>REASON FOR VACANCY:</td>
			     		<td colspan=3 class='tableResult'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                      <textarea name="vacancy_reason" id="vacancy_reason" class="form-control"><%=(req != null)?req.getVacancyReason():""%></textarea>
                                <%}else{%>
                                      <%=req.getVacancyReason()%>
                                <%}%>
			     		</td>
			     	</tr>
			     	 <tr>
			     		
			     		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
			     			<td colspan=4>
							  <div class="panel panel-default" id="degPanel">
							  <div class="panel-heading">
							  <h4 class="panel-title">
							  <a data-toggle="collapse" href="#collapse1" style="font-size:14px;color:DimGrey;">Degree(s) Selection <i>(Click to open list and select)</i>* (Selected: <span id="numDegreesSelected">0</span>)</a>
							  </h4>
							  </div>
							  <div id="collapse1" class="panel-collapse collapse">
							  <div class="panel-body">
							  Select degree(s) from the list below required to fill this position.
							  <job:Degrees id="ad_degree" cls="form-control" value='<%=((req!=null)&&(req.getDegrees() != null))?req.getDegrees():null%>' />
							  </div>
							  </div>
							  </div>
							</td>
						<%}else{ %>
								<td class='tableTitle'>DEGREE(S):</td>
			     				<td colspan=3 class='tableResult'>
						
							            <% if((req.getDegrees() != null)&&(req.getDegrees().length > 0)){							             	
							               for(int i=0; i < req.getDegrees().length; i++) {
							                 out.println("&middot; "+req.getDegrees()[i].getTitle()+"<br/>");
							               }							              
							             }else{
							               out.println("<span style='color:Red;'>None selected by requester for this position.</span>");
							             }%>
							  </td>           
							<%}%>   
					
					</tr>
			     	<tr>
			     	
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
			     				<td colspan=4>
							  		<div class="panel panel-default" id="majPanel">
							    	<div class="panel-heading">
							      	<h4 class="panel-title">
							        <a data-toggle="collapse" href="#collapse2" style="font-size:14px;color:DimGrey;">Major(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMajorsSelected">0</span>)</a>
							      	</h4>
							    	</div>
							    	<div id="collapse2" class="panel-collapse collapse">
							      	<div class="panel-body">
							  		Select prefered major(s) for this position from the list below, if any:
							      	<job:Subjects id="ad_major" cls="form-control" multiple="true" value='<%=((req != null)&&(req.getMajors() != null))?req.getMajors():null%>' />
								    </div>
							     	</div>
							  		</div> 
							  	</td>
								   <%}else{ %>
								   <td class='tableTitle'>MAJOR(S):</td>
			     					<td colspan=3 class='tableResult'>
								   
							               <% if((req.getMajors() != null)&&(req.getMajors().length > 0)){							                   
							                   for(int i=0; i < req.getMajors().length; i++) {
							                     out.println("&middot; "+req.getMajors()[i].getSubjectName()+"<br/>");
							                   }							                  
							                 }else{
							                   out.println("<span style='color:Red;'>None selected by requester for this position.</span>");
							                 } %>
							                 
							           </td>      
							      <%}%> 
			     	</tr>
			     	<tr>
			     		
			     		
			     		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
							  	<td colspan=4>			
							    <div class="panel panel-default" id="minPanel">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#collapse3" style="font-size:14px;color:DimGrey;">Minor(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMinorsSelected">0</span>)</a>
							      </h4>
							    </div>
							    <div id="collapse3" class="panel-collapse collapse">
							      <div class="panel-body">
							  		Select prefered minor(s) for this position from the list below, if any:
							      	<job:Subjects id="ad_minor" cls="form-control" multiple="true" value='<%=((req != null)&&(req.getMinors() != null))?req.getMinors():null%>' />
							     </div>
							     
							    </div>
							  </div> 
							  </td>
							<%}else{ %>
							 <td class='tableTitle'>MINOR(S):</td>
			     				<td colspan=3 class='tableResult'>
							
							                <% if((req.getMinors() != null)&&(req.getMinors().length > 0)){
							                	 
							                  for(int i=0; i < req.getMinors().length; i++) {
							                    out.println("&middot; " + req.getMinors()[i].getSubjectName()+"<br/>");
							                  }							                 
							                }else{
							                  out.println("<span style='color:Red;'>None selected by requester for this position.</span>");
							                } %>
							  </td>           
							<% }%>   
			           
			     	 </tr>	
			     	 <tr>
			     		<td class='tableTitle'>TRAINING METHOD:</td>
			     		<td colspan=3 class='tableResult'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                   <job:TrainingMethods id="ad_trnmtd" multiple="false" cls="form-control" value='<%=((req != null)&&(req.getTrainingMethod() != null))?Integer.toString(req.getTrainingMethod().getValue()):""%>' />
                                <%}else{%>
                                   <%=req.getTrainingMethod().getDescription()%>
                                <%}%>
			     		</td>
			     	</tr>
			     	 <tr>
			     		<td class='tableTitle'>AD TEXT:</td>
			     		<td colspan=3 class='tableResult'>
			     				<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                    <textarea name="ad_text" id="ad_text" class="form-control"><%=((req != null)&&!StringUtils.isEmpty(req.getAdText()))?req.getAdText():""%></textarea>
                                <%}else{%>
                                   <%=req.getAdText()%>
                                <%}%>
			     		</td>
			     	</tr>
			     	 <tr>
			     		<td class='tableTitle'>UNADVERTISED?</td>
			     		<td colspan=3 class='tableResult'>
			     						<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <input type="checkbox" id="is_unadvertised" name="is_unadvertised" <%=(req.isUnadvertised()?" CHECKED":"")%> />  
                                        <%}else{%>
                                         <%=(req.isUnadvertised()?"YES":"NO")%>
                                        <%}%>
			     		</td>
			     	</tr>                   
                    
                    
                     <tr>
			     		<td class='tableTitleL'>START DATE:</td>
			     		<td class='tableResultL'>
			     		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
			     		<input class="form-control requiredinput_date" type="text" name="start_date" value="<%=((req != null)&&(req.getStartDate() != null))?req.getFormatedStartDate():""%>" readonly>
			     		<%} else { %>
			     		<%=((req != null)&&(req.getStartDate() != null))?req.getFormatedStartDate():""%>
			     		<%} %>
			     		</td>
			     		<td class='tableTitleR'>END DATE:</td>
			     		<td class='tableResultR'>
			     		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
			     		<input class="form-control requiredinput_date" type="text" name="end_date" value="<%=((req != null)&&(req.getEndDate() != null))?req.getFormatedEndDate():""%>" readonly>
			     		<%} else { %>
			     		<%=((req != null)&&(req.getEndDate() != null))?req.getFormatedEndDate():""%>
			     		<%} %>
			     		</td>
			     	</tr>
                    
                    </tbody>
                    </table>
                   
							
					<%if(request.getAttribute("msg")!=null){%>
	                           <div class="alert alert-warning" style="text-align:center;"><%=(String)request.getAttribute("msg")%></div>
	                <%}%>
								
					<div class="row no-print" style="margin-top:5px;text-align:center;">
	      			 	      				 
					<a href='#' title='Print this page (pre-formatted)' class="btn btn-xs btn-primary" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Page</a>
		             			 
	 <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-UPDATE") && req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		 <a href="#" class="btn btn-xs btn-success" onclick="doPost('U','<%=req.getId()%>');">UPDATE AD</a>		 
	 <%} %>
	 <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE") && req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                        
	     <a href="#" class="btn btn-xs btn-success" onclick="doPost('A','<%=req.getId()%>');">APPROVE AD</a>
	     <a href="#" class="btn btn-xs btn-danger" onclick="doPost('D','<%=req.getId()%>');">DECLINE AD</a>
	     
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE") && req.getCurrentStatus().equals(RequestStatus.POSTED)){%>
     
      	<a class="btn btn-xs btn-warning" href="javascript:openWindow('CANCEL_POST', 'cancelPost.html?comp_num=<%=req.getCompetitionNumber()%>',390, 270, 1);">CANCEL POST</a>
     
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.APPROVED)){%>
                         
        <a href="#" class="btn btn-xs btn-primary" onclick="doPost('P','<%=req.getId()%>');">POST THIS AD</a>
                      
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.POSTED)){%>
     
     	<a href="#" class="btn btn-xs btn-primary" onclick="doPost('R','<%=req.getId()%>');">RE-POST AD</a>
                         
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.REJECTED)){%>
        
        <div class="alert alert-danger">Request has been REJECTED.</div>
      <%}%>
      <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
								    
						</div>
							
					</form>


					</div>
					</div>
</div>


                        
                              
 
  <script language="JavaScript">
  function setHeight(jq_in){
	    jq_in.each(function(index, elem){
	        // This line will work with pure Javascript (taken from NicB's answer):
	        elem.style.height = elem.scrollHeight+ 5 + 'px'; 
	    });
	}
	setHeight($('#ad_text')); 
	setHeight($('#vacancy_reason')); 
  
  
  $('document').ready(function(){
		$( ".requiredinput_date" ).datepicker({
	      	changeMonth: true,//this option for allowing user to select month
	      	changeYear: true, //this option for allowing user to select from year range
	      	dateFormat: "dd/mm/yy"	      
	 	});
		
	 
		  	CKEDITOR.replace('vacancy_reason',{wordcount: pageWordCountConfVac,height:150});
		    CKEDITOR.replace('ad_text',{wordcount: pageWordCountConfAd,height:350});
	    
		
		
		//Initialize the selected counts on page load, check for missing Degrees, etc.
		var degNum = $('.ad_degree:checked').length;		
		var majNum = $('.ad_major:checked').length;
		var minNum = $('.ad_minor:checked').length;
		
		if (degNum == 0) {			
			$("#degPanel").removeClass("panel-default").addClass("panel-danger");
		} else {				
			$("#degPanel").removeClass("panel-danger").addClass("panel-success");
		}
		if (majNum == 0) {			
			$("#majPanel").removeClass("panel-default").addClass("panel-danger");
		} else {				
			$("#majPanel").removeClass("panel-danger").addClass("panel-success");
		}
		if (minNum == 0) {			
			$("#minPanel").removeClass("panel-default").addClass("panel-danger");
		} else {				
			$("#minPanel").removeClass("panel-danger").addClass("panel-success");
		}
		$("#numDegreesSelected").html(degNum);		
		$("#numMajorsSelected").html(majNum);
		$("#numMinorsSelected").html(minNum);
		
		//Count on click.
		$("#collapse1").click(function(){
			$("#numDegreesSelected").html($('.ad_degree:checked').length);
			if ($('.ad_degree:checked').length == 0) {			
				$("#degPanel").removeClass("panel-default").removeClass("panel-success").addClass("panel-danger");
			} else {				
				$("#degPanel").removeClass("panel-danger").addClass("panel-success");
			}
		});
		$("#collapse2").click(function(){
			$("#numMajorsSelected").html($('.ad_major:checked').length);
			if ($('.ad_major:checked').length == 0) {			
				$("#majPanel").removeClass("panel-default").removeClass("panel-success").addClass("panel-danger");
			} else {				
				$("#majPanel").removeClass("panel-danger").addClass("panel-success");
			}
		});
		$("#collapse3").click(function(){
			$("#numMinorsSelected").html($('.ad_minor:checked').length);
			if ($('.ad_minor:checked').length == 0) {			
				$("#minPanel").removeClass("panel-default").removeClass("panel-success").addClass("panel-danger");
			} else {				
				$("#minPanel").removeClass("panel-danger").addClass("panel-success");
			}
		});
		
  });
  function doPost(posttype,rid){
	  if(posttype == 'A'){
		  var url="approveAdRequest.html?request_id=" + rid + "&op=APPROVED";
		  $("#frmAdRequest").attr('action', url);
		  $("#frmAdRequest").submit();
	  }else if(posttype == 'D'){
		  var url="declineAdRequest.html?request_id=" +rid;
		  $("#frmAdRequest").attr('action', url);
		  $("#frmAdRequest").submit();
	  }else if(posttype == 'P'){
		var url="postAdRequest.html?request_id=" +rid;
		  $("#frmAdRequest").attr('action', url);
		  $("#frmAdRequest").submit();
	  }else if(posttype == 'R'){
		var url="postAdRequest.html?request_id=" +rid;
		  $("#frmAdRequest").attr('action', url);
		  $("#frmAdRequest").submit();
	  }else if(posttype == 'U'){
			var url="updateAdRequest.html?request_id=" +rid;
			  $("#frmAdRequest").attr('action', url);
			  $("#frmAdRequest").submit();
		}else if(posttype == 'GE'){
			var url="viewAdRequest.html?rid=" +rid + "&op=GET_EMPLOYEES";
			  $("#frmAdRequest").attr('action', url);
			  $("#frmAdRequest").submit();
		}
  }
  </script>
 
</body>
</html>
