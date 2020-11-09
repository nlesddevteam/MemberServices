<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
  AdRequestBean ad_req = null;
  JobOpportunityBean opp = null;
  JobOpportunityAssignmentBean[] ass = null;
  AssignmentTrainingMethodBean[] trnbean = null;
  RequestToHireBean rth = null;
  
  String deg_str = "";
  String mjr_str = "";
  String min_str = "";
  String trn_str = "";

  
  if(request.getAttribute("JOB_OPP") != null)
  {
    opp = (JobOpportunityBean) request.getAttribute("JOB_OPP");
    if(opp.getIsSupport().equals("N")){
    	ad_req = (AdRequestBean) request.getAttribute("AD_REQUEST");
    	//ass = (JobOpportunityAssignmentBean[]) opp.toArray(new JobOpportunityAssignmentBean[0]);
        ass = opp.getAssignments();
        if((ass != null) && (ass.length > 0))
        {
          if((ass[0] != null)&&(ass[0].getRequriedTrainingMethods() != null) && (ass[0].getRequriedTrainingMethods().length > 0))
          {
            trnbean = new AssignmentTrainingMethodBean[ass[0].getRequriedTrainingMethods().length];
            for(int i=0; i < ass[0].getRequriedTrainingMethods().length; i++)
              trnbean[i] = new AssignmentTrainingMethodBean(ass[0].getRequriedTrainingMethods()[i]);
          }
        }
    }else{
    	rth = (RequestToHireBean)request.getAttribute("RTH");
    	ass = opp.getAssignments();
    }
  }
  else if(request.getParameter("comp_num") != null)
  {
    opp = JobOpportunityManager.getJobOpportunityBean(request.getParameter("comp_num"));
    if(opp != null)
    {
    	if(opp.getIsSupport().equals("N")){
        	ad_req = AdRequestManager.getAdRequestBean(opp.getCompetitionNumber());
            ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);
            if((ass != null) && (ass.length > 0))
              trnbean = AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeans(ass[0]);
    	}else{
    		//get request to hire by comp_num
    		rth = RequestToHireManager.getRequestToHireByCompNum(request.getParameter("comp_num"));
    		ass = opp.getAssignments();
    	}

    }
  }
  
  //day before today - yesterday
  Calendar cal = Calendar.getInstance();
  if(opp != null && opp.getCompetitionEndDate() != null)
  	cal.setTime(opp.getCompetitionEndDate());
  else
  	cal.add(Calendar.DATE, -1);
  Date yesterday = cal.getTime();
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
  
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>	


<style>
.bootstrap-datetimepicker-widget table td.cw{font-size:11px;}
.bootstrap-datetimepicker-widget table {font-size:11px;}

</style>
<script>
$("#loadingSpinner").css("display","none");
</script>
<script type="text/javascript">
    $(function () {
        $('#ad_comp_end_date').datetimepicker({
        	format:'DD/MM/YYYY HH:mm:A'					                	
        	
        });
    });
 </script>
 <script>
var pageWordCountConfAd = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 8000,
	}
	
	
	
</script>
 
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Post Position</b><br/>Fields marked with * are required. You can select multiple items in the Degree, Major, and Minor categories (Hold CTRL key while selecting to select more than one).</div>
      			 	<div class="panel-body">  


                                <form id="frmPostJob" action="postJob.html" method="post">
                                  <%if(opp != null){%>
                                  	<%if(!StringUtils.isEmpty(opp.getCompetitionNumber())){ %>
                                  		<input type="hidden" name="ad_comp_num" value="<%=opp.getCompetitionNumber()%>">
                                  		<input type="hidden" name="edit" value="true">
                                    <%}%>
                                    
                                    <%if(ad_req != null){%>
                                      <input type="hidden" name="request_id" value="<%=ad_req.getId()%>">
                                      <input type="hidden" name="issupport" id="issupport" value="N">                                      
                                    <%}%>
                                    <%if(rth != null){%>
                                      <input type="hidden" name="request_id" value="<%=rth.getId()%>">
                                      <input type="hidden" name="issupport" id="issupport" value="Y">                                      
                                    <%}%>
                                  <%}%>
                                  
<div class="container-fluid">
	                       	    <div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                                  
		                                    <%if(request.getAttribute("msgERR")!=null){%>
		                                    	<div class="alert alert-danger"><b>ERROR:</b> <%=(String)request.getAttribute("msgERR")%></div>                                     
		                                   	<%} else if(request.getAttribute("msgOK")!=null){%>
		                                    	<div class="alert alert-success"><b>SUCCESS:</b> <%=(String)request.getAttribute("msgOK")%></div>                                     
		                                   	<%} else if(request.getAttribute("msg")!=null){%>
		                                   		<div class="alert alert-warning"><b>WARNING:</b> <%=(String)request.getAttribute("msg")%></div> 
		                                   	<%} %>
	                                  	</div>
                                 </div>
                                 
                                   
                                   
                                  <%if((opp != null)&& !StringUtils.isEmpty(opp.getCompetitionNumber())){%>
                                  <div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">  
											<div class="input-group">
			    								<span class="input-group-addon">Competition #:</span>                                    
	                                            <div class="form-control"><%=opp.getCompetitionNumber()%></div>
	                                      	</div>
                                      	</div>
                                   </div>   	
                                      
                                  <% } %>
                                    
                                   <div class="row">
										<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">  
											<div class="input-group"> 
				                                    <span class="input-group-addon">Position Title*</span>
				                                    <input type="text" class="form-control" name="ad_title" id="ad_title" value="<%=(opp!=null)?opp.getPositionTitle():""%>">
                                    		</div>
                                      	</div>
                                   
										<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">  
											<div class="input-group"> 
				                                    <span class="input-group-addon">Location*</span>
                                       				<job:JobLocation id="ad_location" cls="form-control" value='<%=((ass != null)&&(ass.length > 0))?Integer.toString(ass[0].getLocation()):""%>' />
                                     		</div>
                                      	</div>
                                   </div>
                                     
                                    <%if((ad_req != null) && opp.getIsSupport().equals("N")){ %>
                                    
 <div class="row">   
 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                                
 <div class="panel-group" id="accordion">
  		<div class="panel panel-default" id="degPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" style="font-size:14px;color:DimGrey;">Degree(s) Selection <i>(Click to open list and select)</i>*:  (Selected: <span id="numDegreesSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse1" class="panel-collapse collapse">
      	<div class="panel-body">Select degree(s) from the list below required to fill this position.
      	<job:Degrees id="ad_degree" cls="form-control" value='<%=opp%>' />                                   
      	</div>
    	</div>
  		</div>
  		<div class="panel panel-default" id="majPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" style="font-size:14px;color:DimGrey;">Major(s) Selection <i>(Click to open list and select)</i>*:  (Selected: <span id="numMajorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse2" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered major(s) for this position from the list below, if any:
      	<job:MajorMinor id="ad_major" cls="form-control" value='<%=opp%>' />                                    
      	</div>
    	</div>
  		</div>
  		<div class="panel panel-default" id="minPanel">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse3" style="font-size:14px;color:DimGrey;">Minor(s) Selection <i>(Click to open list and select)</i>*:  (Selected: <span id="numMinorsSelected">0</span>)</a>
      			</h4>
   			</div>
    	<div id="collapse3" class="panel-collapse collapse">
      	<div class="panel-body">
      	Select prefered minor(s) for this position from the list below, if any:
      	<job:MajorMinor id="ad_minor"  cls="form-control" minor="true" value='<%=opp%>' />                                   
      	</div>
    	</div>
  		</div>
	</div>	                                  
   </div>   
   </div>                            
   <div class="row">
                                  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    		<div class="input-group"> 
				                                    <span class="input-group-addon">Training Method</span>
				                       <div class="form-control">                                   
                                      <%if((trnbean!=null)){ %>
                                        <job:TrainingMethods id="ad_trnmtd" multiple="false" cls="form-control" value='<%=trnbean[0].getTrainingMethod() != null ? Integer.toString(trnbean[0].getTrainingMethod().getValue()):""%>' />
                                      <%}else{%>
                                      	<job:TrainingMethods id="ad_trnmtd" multiple="false" cls="form-control" value="" />
                                      <%} %>
                                      </div>
                                   			</div>
                                   	</div>
                                   	</div>
                                    <%} %>
                                    <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    		<div class="input-group"> 
				                                    <span class="input-group-addon">*Ad Text:<br/><br/><span style="color:Grey;font-size:9px;">Limit 8000 characters, including spaces.</span></span>
                                        			<textarea class="form-control" name="ad_text" id="ad_text"><%=(opp!=null)?opp.getJobAdText():""%></textarea>
                                        			
                                    		</div> 
                                     </div>
                                     </div>
                                     
                                     <div class="row">
                                      	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    		<div class="input-group"> 
				                                    <span class="input-group-addon">End Date/Time*</span>
                                      	<%if((ad_req == null) || !ad_req.isUnadvertised()){ %>
	                                   	<input class="form-control" id="ad_comp_end_date" name="ad_comp_end_date" value="<%=((opp!=null)&&(opp.getCompetitionEndDate()!=null))?sdf.format(opp.getCompetitionEndDate()):""%>" />
          								<%}else{%>
                                        	<input class="form-control" type='hidden' name='ad_comp_end_date' value="<%=sdf.format(yesterday)%>" />
                                        	<div class="form-control">UNADVERTISED</div>
                                        <%}%>
                                      		</div>
    									</div>
                                      	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    		<div class="input-group"> 
				                                    <span class="input-group-addon">Listing Date</span>
                                    
                                      	<%if((ad_req == null) || !ad_req.isUnadvertised()){ %>
	                                        <input class="form-control" name="ad_listing_date" id="ad_listing_date" value="<%=((opp!=null)&&(opp.getListingDate() != null))?(new SimpleDateFormat("dd/MM/yyyy")).format(opp.getListingDate()):(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%>" readonly="readonly">
	                                        
                                       	<%}else{%>
                                        	<input class="form-control" type='hidden' name='ad_listing_date' value="<%=sdf.format(yesterday)%>" />
                                        	<div class="form-control">UNADVERTISED</div>
                                        <%}%> 
                                      		</div>
                                      	</div>
                                      </div>
                                      <div class="row">
                                      	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    		<div class="input-group"> 
				                                    <span class="input-group-addon">Position Type*</span>
				                                    	<job:JobType id="ad_job_type" cls="form-control" value='<%=(opp != null)?Integer.toString(opp.getJobType().getValue()) :""%>' />
				                                    
                                      		</div>
                                      	</div>
                                      </div>		
                                      
                                    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-PRIVATIZE-CANDIDATE-LIST">
	                                 
	                                 <div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          						 			<div class="checkbox">
          						 			<%if((ad_req != null) && opp.getIsSupport().equals("N")){ %>
          						 				<label><input type="checkbox" id="candidatelist_private" name="candidatelist_private" <%=(opp != null && opp.isCandidateListPrivate())?"CHECKED" :""%> />Is Candidate List Private?*</label>
          						 			<%} %>
          						 			
  											<%if(rth != null){ %>
  												<label><input type="checkbox" id="candidatelist_private" name="candidatelist_private" <%=rth.getPrivateList() == 1?"CHECKED" :""%> readonly />Is Candidate List Private?*</label>
  											<%} %>  											
  												
											</div> 
          								</div>
		  							 </div>
	                                 
	                                 
                                    </esd:SecurityAccessRequired>
                                    <div class="row no-print">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="margin-top:5px;text-align:center;">
                                 <!-- <a href='#' class="btn btn-sm btn-info" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Advertisement Posting</b></div><br/><br/>'});">Print Page</a> -->
                               <input type="submit" class="btn btn-sm btn-primary" value="Submit">  <a class="btn btn-sm btn-danger" href="javascript:history.go(-1);">Back</a>
                                     </div> 
                                     </div>
                                     <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="margin-top:5px;">
                                    <%if(request.getAttribute("msg")!=null){%>                                      
                                         <div class="alert alert-danger"><%=(String)request.getAttribute("msg")%></div>                                        
                                    <%}%>
									 </div>                             
									 </div> 
									 </div>  
                                </form>
                    
 </div></div></div>
 
   <script>
    CKEDITOR.replace( 'ad_text',{wordcount: pageWordCountConfAd,height:350});
    </script>
    <script language="JavaScript">
  
  $('document').ready(function(){
		$("#ad_listing_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "dd/mm/yy"
	   });
		//Verify number of Degrees, Minors, and Majors selected and if none or not.
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
  
  </script>
    
</body>
</html>
