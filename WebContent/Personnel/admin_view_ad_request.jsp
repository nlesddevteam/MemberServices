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
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 3990,
	}
</script>

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
					
					<%if(request.getAttribute("msg")!=null){%>
	                           <div class="alert alert-warning" style="text-align:center;">                                       
	                               <%=(String)request.getAttribute("msg")%>
	                             </div>
	                <%}%>

                    <div class="container-fluid"> 
      			 	       <div class="row">
		      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
									    <div class="input-group">
									    <span class="input-group-addon">Position Title*:</span>
									    			<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                                        	<input type="text" name="ad_title" id="ad_title" class="form-control" value="<%=((req!=null)&&!StringUtils.isEmpty(req.getTitle()))?req.getTitle():""%>">
		                                     	 	<%}else{%>
		                                     	 	<div class="form-control"><%=req.getTitle()%></div>
		                                        	
		                                      		<%}%>
										</div>
									</div>
							</div>
							<div class="row">	
									 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
									    <div class="input-group">
									    <span class="input-group-addon">Location*:</span>
									    		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                                          <personnel:Locations id='location' cls="form-control" value='<%=(req != null)?req.getLocation().getLocationDescription():""%>' onChange="document.forms[0].submit();" />
		                                        <%}else{%>		                                        
		                                        <div class="form-control"><%=req.getLocation().getLocationDescription()%></div>
		                                        <%}%>
										</div>
									</div>
							
		      			 	       <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
									    <div class="input-group">
									    <span class="input-group-addon">Owner of Position:</span>
									    		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                                          <personnel:EmployeesByLocation id='owner' 
		                                            location='<%=(req != null)?req.getLocation().getLocationDescription():""%>' 
		                                            value='<%=((req != null)&&(req.getOwner() != null))?req.getOwner().getEmpId():""%>'
		                                            multiple="false" cls='form-control' />
		                                        <%}else{%>
		                                         <div class="form-control"><%=(req.getOwner() != null)?req.getOwner().getFullnameReverse():"&nbsp;"%></div>
		                                          
		                                        <%}%>
										</div>
									</div>
							</div>
							<div class="row">		
									 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
									    <div class="input-group">
									    <span class="input-group-addon"> Units*:</span>
									    		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
		                                          <personnel:UnitTime id="ad_unit" cls="form-control"  value='<%=(req != null)?req.getUnits():1.0%>' />
		                                        <%}else{%>
		                                        <div class="form-control"><%=req.getUnits()%></div>
		                                          
		                                        <%}%>
										</div>
									</div>
							</div>
							
					       <div class="row">
	      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
								    <div class="input-group">
								    <span class="input-group-addon">Reason for Vacancy*:</span>								     
                                        <%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <textarea name="vacancy_reason" id="vacancy_reason" class="form-control"><%=(req != null)?req.getVacancyReason():""%></textarea>
                                        <%}else{%>
                                         <div class="form-control" style="height:auto;"><%=req.getVacancyReason()%></div>
                                          
                                        <%}%>
									</div>
								</div>								
							</div>
					       
					       <div class="row">
	      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
								    <div class="input-group">
								    <span class="input-group-addon">Job Type:</span>		   
                                      
                                        <%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <job:JobType id="ad_job_type" cls="form-control" value='<%=((req != null)&&(req.getJobType() != null))?Integer.toString(req.getJobType().getValue()) :""%>' />
                                        <%}else{%>
                                        <div class="form-control"><%=req.getJobType().getDescription()%></div>                                                                                 
                                        <%}%>
									</div>
								</div>
							</div>
							
							
<div class="row">
     <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
     
     
     			 				
     <div class="panel-group" id="accordion">
     <%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
  		<div class="panel panel-default" id="degPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" style="font-size:14px;color:DimGrey;">Degree(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numDegreesSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse1" class="panel-collapse collapse">
      	<div class="panel-body">Select degree(s) from the list below required to fill this position.
      	<job:Degrees id="ad_degree" cls="form-control" value='<%=((req!=null)&&(req.getDegrees() != null))?req.getDegrees():null%>' />
        </div>
    	</div>
  		</div>
  		<%}else{
             if((req.getDegrees() != null)&&(req.getDegrees().length > 0)){
             	out.print("<div class='input-group'><span class='input-group-addon alert-success'>Degree(s):</span><div class='form-control' style='height:auto;'>");
               for(int i=0; i < req.getDegrees().length; i++) {
                 out.println(req.getDegrees()[i].getTitle()+"<br/>");
               }
               out.print("</div></div>");
             }else{
               out.println("<div class='input-group' style='height:auto;'><span class='input-group-addon alert-danger'>Degree(s):</span><div class='form-control'>None Selected.</div></div>");
             }
           }%>   
  		
  		
  		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
  		<div class="panel panel-default" id="majPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" style="font-size:14px;color:DimGrey;">Major(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMajorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse2" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered major(s) for this position from the list below, if any:
      	<job:Subjects id="ad_major" cls="form-control" multiple="true" value='<%=((req != null)&&(req.getMajors() != null))?req.getMajors():null%>' />
	                                                                           
      	</div>
    	</div>
  		</div>
  		<%}else{
                 if((req.getMajors() != null)&&(req.getMajors().length > 0)){
                   out.print("<div class='input-group'><span class='input-group-addon alert-success'>Major(s):</span><div class='form-control' style='height:auto;'>");
                   for(int i=0; i < req.getMajors().length; i++) {
                     out.println(req.getMajors()[i].getSubjectName() + "<br>");
                   }
                   out.print("</div></div>");
                 }else{
                   out.println("<div class='input-group ' style='height:auto;'><span class='input-group-addon alert-danger'>Major(s):</span><div class='form-control'>None Selected.</div></div>");
                 }
               }%>  
  		
  		<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
  		<div class="panel panel-default" id="minPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse3" style="font-size:14px;color:DimGrey;">Minor(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMinorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse3" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered minor(s) for this position from the list below, if any:
      	<job:Subjects id="ad_minor" cls="form-control" multiple="true" value='<%=((req != null)&&(req.getMinors() != null))?req.getMinors():null%>' />
                                                                          
      	</div>
    	</div>
  		</div>
  		<%}else{
                if((req.getMinors() != null)&&(req.getMinors().length > 0)){
                	 out.print("<div class='input-group'><span class='input-group-addon alert-success'>Minor(s):</span><div class='form-control' style='height:auto;'>");
                  for(int i=0; i < req.getMinors().length; i++) {
                    out.println(req.getMinors()[i].getSubjectName() + "<br>");
                  }
                  	out.print("</div></div>");
                }else{
                  out.println("<div class='input-group' style='height:auto;'><span class='input-group-addon alert-danger'>Minor(s):</span><div class='form-control'>None Selected.</div></div>");
                }
              }%>   
  		
	</div>	
	</div>
     </div> 			 								
							
							
							<div class="row">		
								
							
	      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
								    <div class="input-group">
								    <span class="input-group-addon">Training Method:</span>								    
                                        <%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <job:TrainingMethods id="ad_trnmtd" multiple="false" cls="form-control" value='<%=((req != null)&&(req.getTrainingMethod() != null))?Integer.toString(req.getTrainingMethod().getValue()):""%>' />
                                        <%}else{%>
                                          <div class='form-control' style='height:auto;'><%=req.getTrainingMethod().getDescription()%></div>
                                        <%}%>
									</div>
								</div>
								 
							</div>
							
							
							<div class="row">
	      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
								    <div class="input-group">
								    <span class="input-group-addon">Ad Text*:</span>								    
                                        <%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <textarea name="ad_text" id="ad_text" class="form-control"><%=((req != null)&&!StringUtils.isEmpty(req.getAdText()))?req.getAdText():""%></textarea>
                                        <%}else{%>
                                          <div class='form-control' style='height:auto;'><%=req.getAdText()%></div>
                                        <%}%>
									</div>
								</div>								
							</div>
							
							<div class="row">
	      			 	       <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
								    <div class="input-group">
								    <span class="input-group-addon">Start Date*:</span>							    
                                      <input class="form-control" type="text" name="start_date" value="<%=((req != null)&&(req.getStartDate() != null))?req.getFormatedStartDate():""%>" readonly>
                                    </div>
								</div>
								 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">					 
								    <div class="input-group">
								    <span class="input-group-addon">End Date*:</span>								     
                                      <input class="form-control" type="text" name="end_date" value="<%=((req != null)&&(req.getEndDate() != null))?req.getFormatedEndDate():""%>" readonly>
                                    </div>
								</div>
							</div>
							
							
							<div class="row" style="margin-top:5px;margin-bottom:5px;">
      			 	       	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    							 <label class="checkbox-inline">						
    									<%if(req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                                          <input type="checkbox" id="is_unadvertised" name="is_unadvertised" <%=(req.isUnadvertised()?" CHECKED":"")%> /> Is position unadvertised?   
                                        <%}else{%>
                                         Is position unadvertised? <%=(req.isUnadvertised()?"YES":"NO")%>
                                        <%}%>
                                 </label>       
    						
    						</div>
    						</div>
							
							
							<div class="row" >
	      			 	       	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<%if(request.getAttribute("msg")!=null){%>
	                           <div class="alert alert-warning" style="text-align:center;">                                       
	                               <%=(String)request.getAttribute("msg")%>
	                             </div>
	                			<%}%>
								</div>
							</div>
							
							
							
							<div class="row no-print" style="margin-top:5px;text-align:center;">
	      			 	       <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					 
								 
								 
	 <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE") && req.getCurrentStatus().equals(RequestStatus.SUBMITTED)){%>
                        
	     <a href="#" class="btn btn-sm btn-success" onclick="doPost('A','<%=req.getId()%>');">APPROVE</a>
	     <a href="#" class="btn btn-sm btn-danger" onclick="doPost('D','<%=req.getId()%>');">DECLINE</a>
	     
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE") && req.getCurrentStatus().equals(RequestStatus.POSTED)){%>
     
      	<a class="btn btn-sm btn-warning" href="javascript:openWindow('CANCEL_POST', 'cancelPost.html?comp_num=<%=req.getCompetitionNumber()%>',390, 270, 1);">CANCEL POST</a>
     
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.APPROVED)){%>
                         
        <a href="#" class="btn btn-sm btn-primary" onclick="doPost('P','<%=req.getId()%>');">POST</a>
                      
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.POSTED)){%>
     
     	<a href="#" class="btn btn-sm btn-primary" onclick="doPost('R','<%=req.getId()%>');">RE-POST</a>
                         
     <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST") && req.getCurrentStatus().equals(RequestStatus.REJECTED)){%>
        
        <div class="alert alert-danger">Request has been REJECTED.</div>
      <%}%>
      <a class="btn btn-sm btn-danger" href="javascript:history.go(-1);">Back</a>
								    
						    
								    
								</div>								
							</div>
							
					
					
					
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
		
	 
		  	CKEDITOR.replace('vacancy_reason',{wordcount: pageWordCountConf,height:150});
		    CKEDITOR.replace('ad_text',{wordcount: pageWordCountConf,height:150});
	    
		
		
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
	  }
  }
  </script>
 
</body>
</html>
