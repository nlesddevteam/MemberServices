<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
         				 com.esdnl.util.*" 
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
    		alert('Please enter your SOCIAL INSURANCE NUMBER (xxx-xxx-xxx).');
    	}else if(!validateDateFormat2(document.forms[0].dob)){
    		alert('Please enter your DATE OF BIRTH (dd/mm/yyyy).');
    	}else if(issupport == "Y"){
    		document.forms[0].op.value='accept'; 
			document.forms[0].submit();
    	}else{
    		if(confirm('As part of your acceptance of this position you will be removed from all substitute lists for which you have been approved.\nAre you sure you want to ACCEPT this offer?'))
        	{
        			document.forms[0].op.value='accept'; 
        			document.forms[0].submit();
        	}
    	}
    	
    }
    
    function validateDeclineOffer()
    {
    	if(confirm('Are you sure you want to DECLINE this offer?'))
    	{
    			document.forms[0].op.value='reject'; 
    			document.forms[0].submit();
    	}
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
                                  
                                        <job:ViewPost competitionNumber='<%=rec.getCompetitionNumber()%>' />
                                    
                                    <table class="table table-condensed table-striped" style="font-size:12px;">
                                    <tbody>
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
	                                        <a href="#" class="btn btn-sm btn-success" title="Accept Offer" value="ACCEPT" onclick="validateAcceptOffer();"><span class="glyphicon glyphicon-ok"></span> Accept Offer</a>                                    
	                                        <a href="#" class="btn btn-sm btn-danger" title="Decline Offer" value="DECLINE" onclick="validateDeclineOffer();"><span class="glyphicon glyphicon-remove"></span> Decline Offer</a>
	                               </div>
	                              </div>                                     
	                                     
                                    <%}%>
                                    
                                   
                                 
                                </form>
                                
                             </div></div>   
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
