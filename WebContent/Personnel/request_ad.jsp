<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  AdRequestBean req = (AdRequestBean) request.getAttribute("AD_REQUEST");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
	function submitform(){
		$("#op").val('ADD_REQUEST');
		$("#frmAdRequest").submit();
	}
</script>
<style>
	input[type=checkbox] {vertical-align:middle;position: relative;bottom: 0px;}
</style>

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
  
<esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-REQUEST" />
  
  
   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Advertisement Request</b><br/>Fields marked with * are required. 
	               	You can check multiple items in the Degree, Major, and Minor categories. If nothing is selected, the tab will display red.</div>
      			 	<div class="panel-body"> 
      			 	     
      			 	     
      			 	<form id="frmAdRequest" action="requestAdvertisement.html" method="post">
                       <input type="HIDDEN" name="op" id="op" value="GET_EMPLOYEES">      			 	     
      			 	
      			 	 	<div class="container-fluid">
	                       	    <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<%if(request.getAttribute("msg")!=null){%>
	                                      <div class="alert alert-warning" style="text-align:center;">                                       
	                                          <%=(String)request.getAttribute("msg")%>
	                                        </div>
	                                    <%}%>									
      			 					</div>
      			 				</div>
      			 				
      			 				<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Position Title*:</span>
		    								<input type="text" name="ad_title" id="ad_title" class="form-control" value="<%=((req!=null)&&!StringUtils.isEmpty(req.getTitle()))?req.getTitle():""%>">
                                     	</div>
		  							</div>	
		  						</div>
      			 				
      			 				<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Location:*</span>
		    								<personnel:Locations id='location' cls="form-control" value='<%=((req != null) && (req.getLocation() != null)) ? req.getLocation().getLocationDescription():""%>' onChange="this.form.submit();" />
                                      	</div>
		  							</div>	
		  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
		  								<div class="input-group">
		    								<span class="input-group-addon">Owner of Position:</span>
		    								<personnel:EmployeesByLocation id='owner' location='<%=((req != null) && (req.getLocation() != null))?req.getLocation().getLocationDescription():""%>' value='<%=((req != null)&&(req.getOwner() != null))?req.getOwner().getEmpId():""%>' multiple="false" cls='form-control' />							    
		  							 	</div>
	  								</div>
	  							</div>
      			 		
      			 				<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Reason for Vacancy*:<br/><br/><span style="color:Grey;font-size:9px;">Limit 3990 characters, including spaces.</span></span>
		    								<textarea name="vacancy_reason" id="vacancy_reason" class="form-control"><%=(req != null)?req.getVacancyReason():""%></textarea>							    
		  								</div>
		  							</div>	
		  						</div>
      			 		
      			 		        <div class="row">
									<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Unit(s)*:</span>
		    								<personnel:UnitTime id="ad_unit" cls="form-control" value='<%=(req != null)?req.getUnits():1.0%>' />
		    							</div>
		  							</div>	
		  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
		  								<div class="input-group">
		    								<span class="input-group-addon">Job Type:</span>
		    								<job:JobType id="ad_job_type" cls="form-control" value='<%=((req != null)&&(req.getJobType() != null))?Integer.toString(req.getJobType().getValue()) :""%>' />
                                        </div>
	  								</div>
	  							</div>
      			 				
     <div class="row">
     <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 			 				
     <div class="panel-group" id="accordion">
  		
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
  		<div class="panel panel-default" id="majPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" style="font-size:14px;color:DimGrey;">Major(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMajorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse2" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered major(s) for this position from the list below, if any:
      	<job:Subjects id="ad_major" cls="form-control" value='<%=((req != null)&&(req.getMajors() != null))?req.getMajors():null%>' />                                      
      	</div>
    	</div>
  		</div>
  		<div class="panel panel-default" id="minPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse3" style="font-size:14px;color:DimGrey;">Minor(s) Selection <i>(Click to open list and select)</i>*: (Selected: <span id="numMinorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse3" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered minor(s) for this position from the list below, if any:
      	<job:Subjects id="ad_minor" cls="form-control" value='<%=((req != null)&&(req.getMinors() != null))?req.getMinors():null%>' />                                     
      	</div>
    	</div>
  		</div>
	</div>	
	</div>
     </div> 			 				
      			 				

		  							
		  						<div class="row">
		  							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	
		  								<div class="input-group">
		    								<span class="input-group-addon">Training Method:</span>
		    								<div class="form-control">
		    								<job:TrainingMethods id="ad_trnmtd" multiple="false" cls="form-control" value='<%=((req != null)&&(req.getTrainingMethod() != null))?Integer.toString(req.getTrainingMethod().getValue()):""%>' />
                                     		</div>
		    							</div>
	  								</div>
	  							</div>
      			 		
      			 		
      			 		
      			 				<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon">Ad Text:<br/><br/><span style="color:Grey;font-size:9px;">Limit 3990 characters, including spaces.</span></span>
		    								<textarea name="ad_text" id="ad_text" class="form-control"><%=((req != null)&&!StringUtils.isEmpty(req.getAdText()))?req.getAdText():""%></textarea>
                                      
		    							</div>
		  							</div>
		  				 		</div>
      			 			
      			 				<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                        
		                             	<div class="input-group">
		    								<span class="input-group-addon"> Start Date*:</span>
		    								<input class="form-control requiredinput_date" type="text" name="start_date" value="">
		    							</div>
		  							</div>	
		  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
		  								<div class="input-group">
		    								<span class="input-group-addon">End Date</span>
		    								<input class="form-control requiredinput_date" type="text" name="end_date" value="" >
		    							</div>
	  								</div>
	  							</div>
      			 				
      			 				<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          						 		<div class="checkbox">
  											<label><input type="checkbox" id="is_unadvertised" name="is_unadvertised" />  Is position unadvertised?</label>
										</div> 
          							</div>
		  							
	  							</div>
      			 		       
      			 		        <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<%if(request.getAttribute("msg")!=null){%>
	                                      <div class="alert alert-warning" style="text-align:center;">                                       
	                                          <%=(String)request.getAttribute("msg")%>
	                                        </div>
	                                    <%}%>									
      			 					</div>
      			 				</div>
      			 				
      			 		        <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="margin-top:5px;">
      			 		            	
      			 		       		</div>
      			 		       </div>
      			 		 
      			 		</div>			
      			 	
      			 	
      			 	<div align="center" class="no-print">
      			 	<a href="#" class="btn btn-xs btn-primary" onclick="submitform();">Add Request</a> <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
      			 	</div>    
                   </form>
   					</div>
   					</div>
   	</div>   			 	
  
      
                              
  
  
  <script>  
  	CKEDITOR.replace('vacancy_reason',{wordcount: pageWordCountConf,height:150});
    CKEDITOR.replace('ad_text',{wordcount: pageWordCountConf,height:150});
  </script>                  
  <script language="JavaScript">
  
  $('document').ready(function(){
		$( ".requiredinput_date" ).datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "dd/mm/yy"
	      
	 	});		
		
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
  
   
  
  
  
  </script>
</body>
</html>
