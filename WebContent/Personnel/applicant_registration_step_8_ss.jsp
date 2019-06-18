<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
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
	.tableTitleL {font-weight:bold;width:20%;}
	.tableResultL {font-weight:normal;width:30%;}
	.tableTitleR {font-weight:bold;width:20%;}
	.tableResultR {font-weight:normal;width:30%;}
	input { border:1px solid silver;}
</style>
  <script type="text/javascript">
  
    

    $( document ).ready(function() {
    		$('#frmPostJob').submit(function() {
    			var email=$('#email').val();
        		var test = checkemailaddress(email);
        		return test;
    		});
    		
    		//check the total and hid button and show message
    		var testcount = $("#hidcount").val();
    		if(testcount > 2){
    			$( "#butadd" ).prop( "disabled", true );
    		}else{
    			$( "#butadd" ).prop( "disabled", false );
    		}
    });
    function opensend(rid,em){
    	$("#emaila").val(em);
    	$("#refreqid").val(rid);
    	$("#refRequest").modal( "show" );
    	$("#divemail").hide();
    }
    function sendexternalemail(rid,em){
    	$("#emaila").val(em);
    	$("#refreqid").val(rid);
    	sendrequest("E");
    }
    function sendrequest(rtype){
    	var isvalid=false;
    	var emaila = null;
    	var refid = null;
    	var opttype=null;
    	var externalentered=false;
    	
    	if($("#popemail").is(":visible")){
    		var email=$('#popemail').val();
    		emaila = $.trim($('#popemail').val());
    	    refid = $.trim($('#refid').val());
    	    opttype="new";
    		
    	}else{
    		emaila = $.trim($('#emaila').val());
    		refid = $.trim($('#refreqid').val());
    		opttype="update";
    		
    	}
    	//send ajax
    	var reftype=null;
    	reftype=$('select[name=selType]').val();
        
    	
    	
		
    	$.ajax(
     				{
     					type: "POST",  
     					url: "sendApplicantRefRequest.html",
     					data: {
     						em: emaila,rid: refid,rt: reftype,opt:opttype
     					},
     					cache: false,
     					success: function(xml){
     						$(xml).find('RCHECK').each(function(){
     							if($(this).find("MESSAGE").text() == "SUCCESS"){
     								$('#msgok').css('display','block').html("<b>SUCCESS:</b> Reference Request successfully sent.").delay(5000).fadeOut();     									
									isvalid=true;
									$("#refRequest").modal( "hide" );
						            window.location.href='applicant_registration_step_8_ss.jsp';
									
							                
    	                   		}else{
    	                   			$('#msgerr').css('display','block').html("<b>ERROR:</b> Reference Request failed to send. Please check email address or contact support.").delay(5000).fadeOut(); 
     								
     									
     							}
     						});
     					},
     					error: function(xhr, textStatus, error){
     						alert(xhr.statusText);
     						alert(textStatus);
     						alert(error);
     					},
     					dataType: "text",
     					async: false
     				}
     			);
    	return isvalid;
    
    	
    }
    function opensendemail(rid){
    	$("#refid").val(rid);
    	$("#refRequest").modal( "show" );
    	$("#divemail").show();
    }
  </script>

</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">7</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 7: Editing your Support Staff/Management HR Application Profile
</div>

<br/>Please add up to 3 references to your online profile.

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>7. REFERENCES</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

					<form id="frmPostJob" name="frmPostJob" action="applicantRegistrationSS.html?step=7" method="post" onsubmit="return checknewreference()">
                                 
                                 
                                 <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
                                
                                <tr>
                                  <td class="tableTitleL">Referencee Name*:</td>
                                  <td class="tableResultL"><input type="text" name="full_name" id="full_name" class="form-control"></td>
                                  
                                  <td class="tableTitleR">Position Title*:</td>
                                  <td class="tableResultR"><input type="text" name="title" id="title" class="form-control"></td>
                                </tr>
                                <tr>    
                                  <td class="tableTitle">Company Name/Address*:</td>
                                  <td class="tableResult" colspan=3><input type="text" name="address" id="address" class="form-control"></td>
                                </tr>  
                                <tr>
                                  <td class="tableTitleL">Telephone*:</td>
                                  <td class="tableResultL"><input type="text" name="telephone" id="telephone" class="form-control"></td>                                  
                                  <td class="tableTitleR">Email*:</td>
                                  <td class="tableResultR"><input type="text" name="email" id="email" class="form-control"></td>
                                </tr>
                                </tbody>
                                </table>           
                                <div align="center">
                                  <a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a>
                                  <input class="btn btn-xs btn-success" type="submit" id="butadd" name="butadd" value="Add Reference"> 
                                </div> 
                                
                                 <job:ApplicantReferences />                                     
                                   
                                     
                                </form>


</div></div></div></div>

<div id="refRequest" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Send Reference Request</h4>
      </div>
      <div class="modal-body">
        <div id='divemail' name='divemail' style="display:none;">
      		Please Enter Email Address<br/>
      		<input type="text" id='popemail' name='popemail' class="form-control">
      	</div>
      	Please Select Reference Type<br/>      	
        			<select id="selType" name="selType" class="form-control">
      				<option value="-1" selected>***** PLEASE SELECT *****</option>
      					<option value="SS" selected>Support Staff</option>
      					<option value="M">Management</option>
      				</select>
      
      </div>
      <div class="modal-footer">
        <input type="button" class="btn btn-xs btn-success" value="Send Request" onclick="sendrequest('Z');">
      			<input type="hidden" id="emaila" name="emaila">
      			<input type="hidden" id="refreqid" name="refreqid">
      			<input type="hidden" id="refid" name="refid">        
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
     
              
</body>
</html>
