<%@ page language="java"
         import="java.util.*,
                  java.text.*" isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:15%;}
.tableResult {font-weight:normal;width:85%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>
  <script type="text/javascript">
  
    function toggleRow(rid, state)
    {
      var row = document.getElementById(rid);
      row.style.display=state;
    }
    function checkemailaddress(email){
    	var re = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
        if (re.test(email)) {
            //if (email.indexOf('@nlesd.ca', email.length - '@nlesd.ca'.length) !== -1) {
            	//document.location.href='applicant_registration_step_9.jsp';
                return true;
            //} else {
              //  alert('Email must be a nlesd.ca e-mail address (your.name@nlesd.ca).');
                //return false;
           // }
        } else {
            alert('Not a valid e-mail address.');
            return false;
        }
    }
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
    	//check to see if user just entered email address
    	if($("#popemail").is(":visible")){
    		var email=$('#popemail').val();
    		if(checkemailaddress(email)){
    	    	emaila = $.trim($('#popemail').val());
    	    	refid = $.trim($('#refid').val());
    	    	opttype="new";
    		}else{
    			return false;
    		}
    		//check to see if the address entered was external
    		if (email.indexOf('@nlesd.ca', email.length - '@nlesd.ca'.length) !== -1){
    			externalentered=false;
    		}else{
    			externalentered=true;
    		}
    	}else{
    		emaila = $.trim($('#emaila').val());
    		refid = $.trim($('#refreqid').val());
    		opttype="update";
    		//check to see if the address entered was external
    		if (emaila.indexOf('@nlesd.ca', emaila.length - '@nlesd.ca'.length) !== -1){
    			externalentered=false;
    		}else{
    			externalentered=true;
    		}
    	}
    	//send ajax
    	var reftype=null;
    	if(externalentered){
    		reftype="E";
    	}else{
    		if(rtype == "E"){
        		reftype="E";
        	}else{
        		reftype=$('select[name=selType]').val();
        		if(reftype == "-1"){
        			//default to teaching
        			reftype='T';
        		}
        	}
    	}
    	
		
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
     								
     								$('.msgok').css('display','block').html("<b>SUCCESS:</b> Reference Request successfully sent.").delay(5000).fadeOut();     									
										isvalid=true;
										$("#refRequest").modal( "hide" );
							            window.location.href='applicant_registration_step_8.jsp';
							                
    	                   		}else{
    	                   			$('.msgerr').css('display','block').html("<b>ERROR:</b> Reference Request failed to send. Please check email address or contact support.").delay(5000).fadeOut(); 
     									
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
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">8</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 8: Editing your Teacher/Educator HR Application Profile 
</div>

<br/>Please add up to 3 references to your online profile.

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>8. REFERENCES</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

                                                       
                                <form id="frmPostJob" name="frmPostJob" action="applicantRegistration.html?step=8" method="post">
                                <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
                                
                                <tr>
                                  <td class="tableTitleL">Referencee Name*:</td>
                                  <td class="tableResultL"><input type="text" name="full_name" id="full_name" class="form-control"></td>
                                  
                                  <td class="tableTitleR">Position Title*:</td>
                                  <td class="tableResultR"><input type="text" name="title" id="title" class="form-control"></td>
                                </tr>
                                <tr>    
                                  <td class="tableTitle">Present Address*:</td>
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
                                  <a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>
                                  <input class="btn btn-xs btn-success" type="submit" id="butadd" name="butadd" value="Add Reference"> 
                                  </div>
                               
                                
                                <!-- List current references. 3 Max. -->  
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
      		Please Enter Email Address ( @nlesd.ca addresses only)<br/>
      		<input type="text" id='popemail' name='popemail' class="form-control">
      	</div>
      	Please Select Reference Type<br/>      	
        			<select id="selType" name="selType" class="form-control">
      				<option value="-1" selected>***** PLEASE SELECT *****</option>
      					<option value="T">Teaching Reference</option>
      					<option value="A">Administrative Reference</option>
      					<option value="G">Guidance Reference</option>
      					<option value="E">TLA/External Reference</option>
      				</select>
      <span style="font-size:10px;color:DimGrey;"><b>Note:</b> You do not have to select Reference Type if entering a non NLESD email address.</span>
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
	<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
                      
</body>
</html>
