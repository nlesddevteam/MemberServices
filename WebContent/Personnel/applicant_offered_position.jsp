<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
         				 com.esdnl.util.*,com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>
<job:ApplicantViewPositionOfferAuthorization/>  

<%
  TeacherRecommendationBean rec = (TeacherRecommendationBean) request.getAttribute("RECOMMENDATION_BEAN");
	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	JobOpportunityBean jbean = JobOpportunityManager.getJobOpportunityBean(rec.getCompetitionNumber());
%>

<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Applicant Position Offer</title>
<title>MyHRP Applicant Profiling System</title>
		
  <script type="text/javascript">
  	function validateNotEmpty(fld) 
		{
		    return /\S+/.test(fld.value);
		}
		
		function validateSINFormat(fld)
		{
		  return /^\d{3}[ ,-]?\d{3}[ ,-]?\d{3}$/.test(fld.value);
		}
		//function name changed because another function in msapp.js file has same name
		//and different format.  stopping form submission
		function validateDateFormat2(fld)
		{
			return /^\d{2}\/\d{2}\/\d{4}$/.test(fld.value);
		}
		
    function validateAcceptOffer()
    {
    	
    	var issupport=$("#issupport").val();
    	//check that the sin2 and dob are not empty
    	if(!validateSINFormat(document.forms[0].sin2)){
    		//alert('Please enter your SOCIAL INSURANCE NUMBER (xxx-xxx-xxx).');
    		$(".msgerr").css("display", "block").html("Please enter your SOCIAL INSURANCE NUMBER (xxx-xxx-xxx).").delay(3000).fadeOut(2000);
    	}else if(!validateDateFormat2(document.forms[0].dob)){
    		//alert('Please enter your DATE OF BIRTH (dd/mm/yyyy).');
    		$(".msgerr").css("display", "block").html("Please enter your DATE OF BIRTH (dd/mm/yyyy).").delay(3000).fadeOut(2000);
    	}else if($("#hidsc").val() == "Yes" && $('input[name="radAcceptSC"]:checked').val() == "0"){
    		//check to make sure they have selected yes radio
    		//alert("Please accept special conditions attached to this offer");
    		$(".msgerr").css("display", "block").html("Please accept special conditions attached to this offer.").delay(3000).fadeOut(2000);
    	}else if(issupport == "Y"){
    		document.forms[0].op.value='accept'; 
			document.forms[0].submit();
    	}else{
    		//show the dialog for accepting an offer
    		$("#modaltitle").html("Accept Offer");
    		$("#modalmsg").html("As part of your acceptance of this position you will be removed from all substitute lists for which you have been approved.\nAre you sure you want to ACCEPT this offer?");
    		$("#modalmsg").show();
    		$("#btn_qd_ok").unbind("click").click(
				function () {
					$("#btnaccept").prop('disabled', true);
					$("#btn_qd_ok").prop('disabled', true);
					document.forms[0].op.value='accept'; 
        			document.forms[0].submit();
				}
			)
			var options = {
				"backdrop" : "static",
				"show" : true
			};
			$('#questionsdialog').modal(options);
    	}
    	
    }
    
    function validateDeclineOffer()
    {
    	//show the dialog for accepting an offer
		$("#modaltitle").html("Decline Offer");
		$("#modalmsg").html("Are you sure you want to DECLINE this offer?");
		$("#modalmsg").show();
		$("#btn_qd_ok").unbind("click").click(
			function () {
				document.forms[0].op.value='reject'; 
    			document.forms[0].submit();
			}
		)
		var options = {
			"backdrop" : "static",
			"show" : true
		};
		$('#questionsdialog').modal(options);
    	
    }
  </script>
  <style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
		</style>
		<script>
			$("#loadingSpinner").css("display","none");
		</script>
  
  
</head>
<body>


<br/>
<div class="panel panel-success">
  <div class="panel-heading"><b>Applicant Position Offer</b><br/>Fields marked <span class="requiredStar">*</span> are required.</div>
  <div class="panel-body">




                                <form id="frmPostJob" action="applicantPositionOfferController.html" method="post">
                                	<input type="hidden" name="id" value="<%=rec.getRecommendationId()%>" />
                                	<input type="hidden" name="op" value="" />
                                	<input type="hidden" name="issupport" id="issupport" value="<%=rec.getJob().getIsSupport()%>" />
                                 
                                 
                                    <%if(request.getAttribute("msg")!=null){%>
                                      
                                         <div class="alert alert-danger"><%=(String)request.getAttribute("msg")%></div>
                                       
                                    <%}%>
                                  
                                        
                                    
                                    <input type="hidden" id="hidsc" name="hidsc" value="<%=rec.getSpecialConditions()%>">
                                    <table class="table table-condensed table-striped" style="font-size:12px;width:75%;margin-left: auto;margin-right: auto;">
                                    <tbody>
                                    <%if(jbean.isSupport()){ 
                                    	RequestToHireBean rbean =  RequestToHireManager.getRequestToHireByCompNum(rec.getCompetitionNumber()); %>
                                   		<tr>
											<td class="tableTitle" style="width:50%">Competition Number:</td>
											<td style="width:50%"><%=rec.getCompetitionNumber() %></td>
										</tr>
										<tr>
											<td class="tableTitle" style="width:50%">School/Work Location:</td>
											<td style="width:50%"><%=rbean.getWorkLocation() ==  null ? "" : rbean.getLocationDescription() %></td>
										</tr>
										<tr>
											<td class="tableTitle">Position:</td>
											<td><%=rbean.getJobTitle() ==  null ? "" : rbean.getJobTitle() %></td>
										</tr>
										<tr>
											<td class="tableTitle">Hours/Week:</td>
											<td><%=rbean.getPositionHours() ==  null ? "" : rbean.getPositionHours() %></td>
										</tr>
										<tr>
											<td class="tableTitle">10 or 12 Month:</td>
											<td><%=rbean.getPositionTerm() ==  0 ? "" : rbean.getPositionTermString() %></td>
										</tr>
										<tr>
											<td class="tableTitle">Type:</td>
											<td><%=rbean.getPositionType() ==  0 ? "" : rbean.getPositionTypeString() %></td>
										</tr>
										<tr>
											<td class="tableTitle">Start Date:</td>
											<td><%=rbean.getStartDate () ==  null ? "" : rbean.getStartDateFormatted() %></td>
										</tr>
										<tr>
											<td class="tableTitle">End Date(if applicable):</td>
											<td><%=rbean.getEndDate () ==  null ? "" : rbean.getEndDateFormatted() %></td>
										</tr>
						
                                    <%}else{%>
                                    	<tr>
											<td class="tableTitle" style="width:50%">Competition Number:</td>
											<td style="width:50%"><%=rec.getCompetitionNumber() %></td>
										</tr>
                                    	<tr>
											<td>School:</td>
											<td><%=jbean.getJobLocation()%></td>
										</tr>
										<tr>
											<td>Position:</td>
											<td><%= jbean.getPositionTitle() %></td>
										</tr>
										<tr>
											<td>Percentage:</td>
										<%if(rec.getTotalUnits() > 0){%> 
											<td><%=String.format("%.2f",rec.getTotalUnits()) %></td>
										<%}else if(jbean.getAdRequest().getUnits() > 0){%>
											<td><%=String.format("%.2f",jbean.getAdRequest().getUnits())%></td>
										<%}else{%>
											<td>0.0</td>
										<%} %>
										</tr>
										<tr>
											<td>Type:</td>
											<td><%= rec.getEmploymentStatus().getDescription()%></td>
										</tr>
										<%if(jbean.getAdRequest().getStartDate() != null){%>
										<tr>
											<td>Start Date (dd/mm/yyyy):</td>
											<td><%=jbean.getAdRequest().getFormatedStartDate()%></td>
										</tr>
										<%}%>
										<%if(jbean.getAdRequest().getEndDate() != null){%>
										<tr>
											<td>End Date (dd/mm/yyyy):</td>
											<td><%=jbean.getAdRequest().getFormatedEndDate()%></td>
										</tr>
										<%}%>
						
                                    
                                    <%}%>
                                    
                                    <%if(rec.getSpecialConditions().equals("Yes")){ %>
                                    <tr><td colspan='2'></td></tr>
                                    <tr>
                                    	<td class="tableTitle">DO YOU ACCEPT THESE SPECIAL CONDITIONS ON YOUR OFFER?</td>
                                    	
                                    	<td class="tableResult" style="white-space: nowrap;">
                							<div id="sc_row_accept">
                							<%if(!rec.isOfferAccepted() && !rec.isOfferRejected()){ %>
                								<input type="radio" name="radAcceptSC" value="1"><span>YES</span><input type="radio" name="radAcceptSC" value="0" checked><span>No</span>
                							<%}else{%>
                								<%if(rec.isConfirmedSpecialConditions()) {%>
                									Yes
                								<%}else{%>
                									No
                								<%} %>
                							<%}%>
                							</div>
					                	</td>
					                	
                                    </tr>
                                    <tr>
					                <td class="tableTitle">SPECIAL CONDITIONS:</td>
					                <td class="tableResult">
                					<div id="sc_row">
                						<%= rec.getSpecialConditionsComment() == null ? "None" :rec.getSpecialConditionsComment() %>
                                      	
                                      </div>
					                </td>
					              </tr>
					              <tr><td colspan='2'></td></tr>
					              <%} %>
					              
					     			<tr>
					                <td class="tableTitle">SOCIAL INSURANCE NUMBER (SIN):</td>
					                <td class="tableResult">
                					<div id="sin2_row">
                                      	<%if(!rec.isOfferAccepted() && !rec.isOfferRejected()){ %>
                                      		<input type='text' id='sin2' name='sin2' class='form-control' value='<%=(profile!=null && !StringUtils.isEmpty(profile.getSIN2()))?profile.getSIN2():""%>' />
                                      	<%}else{%>
                                      		<%=(profile!=null && !StringUtils.isEmpty(profile.getSIN2()))?profile.getSIN2():""%>
                                      	<%}%>
                                      </div>
					                </td>
					              </tr>
              						<tr>
              						<td class="tableTitle">DATE OF BIRTH (dd/mm/yyyy):</td>
					                <td class="tableResult">
					                <div id="dob_row">
                                      	<%if(!rec.isOfferAccepted() && !rec.isOfferRejected()){ %>
                                      		<input type='text' id='dob' name='dob' class='form-control requiredinput_date' autocomplete="off" value='<%=(profile!=null && profile.getDOB()!=null)?profile.getDOBFormatted():""%>' />
                                      	<%}else{%>
                                      		<%=(profile!=null && profile.getDOB()!=null)?profile.getDOBFormatted():""%>
                                      	<%}%>
                                      </div>
					                
					                </td>
              						</tr>
                                    
                                      
                                   </tbody>
                                   </table> 
                                    
                                   <br/>    						
                                    
                                    <%if(!rec.isOfferAccepted() && !rec.isOfferRejected()){%>
                                  <div class="alert alert-danger">
									<div align="center"><span style="font-weight:bold;font-size:14px;">**** NOTICE OF POSITION ACCEPTANCE ****</span>
									<br/> By clicking <b>Accept Offer</b>, I am confirming that all the information used by the Newfoundland and Labrador English School District in determining that I am the successful candidate 
                                    for this position is up to date and accurate.  I understand that should this not be the case, that the Newfoundland and Labrador English School District reserves the right to rescind this job offer.
                                    <br/><br/>                                   
	                                        <a href="#" class="btn btn-sm btn-success" title="Accept Offer" value="ACCEPT" onclick="validateAcceptOffer();" id="btnaccept"><span class="glyphicon glyphicon-ok"></span> Accept Offer</a>                                    
	                                        <a href="#" class="btn btn-sm btn-danger" title="Decline Offer" value="DECLINE" onclick="validateDeclineOffer();"><span class="glyphicon glyphicon-remove"></span> Decline Offer</a>
	                               </div>
	                              </div>                                     
	                                     
                                    <%}%>
                                    
                                   
                                 
                                </form>
                                
                             </div></div>
                             
 <div class="modal fade" id="questionsdialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="modaltitle"></h4>
        <input type='hidden' id='hidbutton'>
      </div>
      <div class="modal-body">
        <p>
        <div class="alert alert-warning" role="alert" id="modalmsg" style="display:none;"></div>
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" id='btn_qd_ok' class="btn btn-success btn-xs" style="float: left;" onclick="submitform();">OK</button>
		<button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">CLOSE</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->   
 <script language="JavaScript">
  
  $('document').ready(function(){
		$( ".requiredinput_date" ).datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "dd/mm/yy",
	      	yearRange: '-80:+0',
	      
	 	})		
  });
	</script>	
</body>
</html>
