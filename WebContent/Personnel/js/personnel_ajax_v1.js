function initRequest() 
{
  var xmlHttp = null;
  
  try
  {    
    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
  }
  catch (e)
  {    
    // Internet Explorer    
    try
    {      
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
    }
    catch (e)
    {      
      try
      {        
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
      }
      catch (e)
      {        
        alert("Your browser does not support AJAX!");
      }      
    }    
  }
  
  return xmlHttp;
}

// ----- Teacher Recommendation Form -----

function parseCandidateSelection(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    //formatting applicant address
    var address = document.getElementById("candidate_address");
    var addr_str = null;
    if(xmlDoc.getElementsByTagName("ADDRESS1").length > 0)
      addr_str = xmlDoc.getElementsByTagName("ADDRESS1")[0].childNodes[0].nodeValue;
    if(xmlDoc.getElementsByTagName("ADDRESS2").length > 0)
      addr_str = addr_str + "<BR>" + xmlDoc.getElementsByTagName("ADDRESS2")[0].childNodes[0].nodeValue;
    addr_str = addr_str + "<BR>" + xmlDoc.getElementsByTagName("PROVINCE")[0].childNodes[0].nodeValue;
    addr_str = addr_str + ", " + xmlDoc.getElementsByTagName("COUNTRY")[0].childNodes[0].nodeValue;
    addr_str = addr_str + ", " + xmlDoc.getElementsByTagName("POSTAL-CODE")[0].childNodes[0].nodeValue;
    address.innerHTML = addr_str;
     
    //formatting phone numbers
    var phone = document.getElementById("candidate_telephone");
    var phone_str = null;
    if(xmlDoc.getElementsByTagName("HOME-PHONE").length > 0)
      phone_str = xmlDoc.getElementsByTagName("HOME-PHONE")[0].childNodes[0].nodeValue + " (home)";
    if(xmlDoc.getElementsByTagName("WORK-PHONE").length > 0)
    {
      if(phone_str)
        phone_str = phone_str + "<BR>" + xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue + " (work)";
      else
        phone_str = xmlDoc.getElementsByTagName("WORK-PHONE")[0].childNodes[0].nodeValue + " (work)";
    }
    if(xmlDoc.getElementsByTagName("CELL-PHONE").length > 0)
    {
      if(phone_str)
        phone_str = phone_str + "<BR>" + xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue + " (cell)";
      else
        phone_str = xmlDoc.getElementsByTagName("CELL-PHONE")[0].childNodes[0].nodeValue + " (cell)";
    }
    if(!phone_str)
      phone_str = 'NONE ON RECORD';
    phone.innerHTML = phone_str;
    
    /*
    var sin2 = document.getElementById('sin2_row');
    if(xmlDoc.getElementsByTagName("SIN2").length > 0)
    	sin2.innerHTML = "<input type='hidden' id='sin2' name='sin2' value='"
    	  +xmlDoc.getElementsByTagName("SIN2")[0].childNodes[0].nodeValue + "' />" 
    	  //+ "XXX-XXX-XXX";
    		+ xmlDoc.getElementsByTagName("SIN2")[0].childNodes[0].nodeValue;
    else if((xmlDoc.getElementsByTagName("SIN").length > 0) && (xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue.length < 12))
    	sin2.innerHTML = "<input type='hidden' id='sin2' name='sin2' value='"
    	  +xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue + "' />"
    	  //+ "XXX-XXX-XXX"; 
    		+ xmlDoc.getElementsByTagName("SIN")[0].childNodes[0].nodeValue;
    else
    	sin2.innerHTML = "<input type='text' id='sin2' name='sin2' class='requiredInputBox' />";
    //sin2.innerHTML = "XXX-XXX-XXX";
    */
    /*
    var dob = document.getElementById('dob_row');
    if(xmlDoc.getElementsByTagName("DOB").length > 0)
    	dob.innerHTML = "<input type='hidden' id='dob' name='dob' value='"
    	  + xmlDoc.getElementsByTagName("DOB")[0].childNodes[0].nodeValue + "' />" 
    		+ xmlDoc.getElementsByTagName("DOB")[0].childNodes[0].nodeValue;
    else
    	dob.innerHTML = "<input type='text' id='dob' name='dob' class='requiredInputBox' />";
    */
    
    //formatting candidate info
    var perm_status = document.getElementById("perm_status");
    if(xmlDoc.getElementsByTagName("PERM-SCHOOL").length > 0)
    {
	    var sch_str = null;
	    if(xmlDoc.getElementsByTagName("PERM-SCHOOL").length > 0)
	    {
	      sch_str = xmlDoc.getElementsByTagName("PERM-SCHOOL")[0].childNodes[0].nodeValue;
	      sch_str = "YES<BR>" + sch_str + "<BR>" + xmlDoc.getElementsByTagName("PERM-POSITION")[0].childNodes[0].nodeValue;
	    }
	    else if(xmlDoc.getElementsByTagName("REPLACEMENT-CONTRACT-SCHOOL").length > 0)
	    {
	      sch_str = xmlDoc.getElementsByTagName("REPLACEMENT-CONTRACT-SCHOOL")[0].childNodes[0].nodeValue;
	      sch_str = "REPLACEMENT<BR>" + sch_str + "<BR> (REPLACEMENT ENDDATE: " + xmlDoc.getElementsByTagName("REPLACEMENT-CONTRACT-ENDDATE")[0].childNodes[0].nodeValue + ")";
	    }
	    else
	    {
	      sch_str = 'NO';
	    }
	    
	    perm_status.innerHTML = sch_str ;
	  }
    else
      perm_status.innerHTML = "NO";
    
    var trn_mtd = document.getElementById("trn_mtd");
    if(xmlDoc.getElementsByTagName("TRAINING-METHOD").length > 0)
      trn_mtd.innerHTML = xmlDoc.getElementsByTagName("TRAINING-METHOD")[0].childNodes[0].nodeValue;
    
    var cert_lvl = document.getElementById("cert_lvl");
    if(xmlDoc.getElementsByTagName("TEACHING-CERTIFICATE-LEVEL").length > 0)
      cert_lvl.innerHTML = xmlDoc.getElementsByTagName("TEACHING-CERTIFICATE-LEVEL")[0].childNodes[0].nodeValue;
      
    //formatting reference check requests
    parseCurrentReferenceCheckRequestsReponse(xmlHttp);
    
    toggleRow('candidate_loading_msg', 'none');
    toggleRow('candidate_info', 'inline');
  }
}

function getCurrentReferenceCheckRequests(uid)
{
  if(uid == -1)
  {
    return;
  }
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseSendReferenceCheckRequestResponse(xmlHttp);
    }
    xmlHttp.open("GET","getCurrentReferenceCheckRequests.html?uid="+uid,true);
    xmlHttp.send(null);
  }
}

function parseCurrentReferenceCheckRequestsReponse(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    //formatting reference check request beans
    //var ref_chk_display = document.getElementById('current_ref_requests');
    var ref_chk_str = "<span style='color:#FF0000;'>No references on record.</span>";
    
    var beans = xmlDoc.getElementsByTagName("REFERENCE");
    
    var show_rec_info = false;
    
    if(beans.length > 0)
    {	
    	ref_chk_str = "<table width='100%' align='center' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
    	
    	ref_chk_str = ref_chk_str + "<tr><td style='border:solid 1px #d4d4d4;' class='displayHeaderTitle'>Ref. Date</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>Provided By</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>Position</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>Select</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>View</td></tr>";
    	for(var i = 0; i < beans.length; i++)
    	{
    		//reference date
    		ref_chk_str = ref_chk_str + "<tr><td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" 
    			+((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[1].childNodes[0].nodeValue + "</td>";
    		
    		//provided by
    		if(beans[i].childNodes[3] != null)
    			ref_chk_str = ref_chk_str + "<td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[3].childNodes[0].nodeValue + "</td>";
    		else 
    			ref_chk_str = ref_chk_str + "<td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'><SPAN style='color:#FF0000;'>UNKNOWN</SPAN></td>";
    		
    		//provided by position
    		if(beans[i].childNodes[4].childNodes[0].nodeValue != "null")
    			ref_chk_str = ref_chk_str + "<td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[4].childNodes[0].nodeValue + "</td>";
    		else
    			ref_chk_str = ref_chk_str + "<td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'><SPAN style='color:#FF0000;'>PHONE</SPAN></td>";
    		
    		//select reference
    		ref_chk_str = ref_chk_str + "<td align='center' class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" 
				+((i%2==0)?"background-color:#f0f0f0;":"") + "'><input type='radio' value='" + beans[i].childNodes[0].childNodes[0].nodeValue + "' name='reference_id' />"
				+ "</td>";
    		
    		//view reference
    		ref_chk_str = ref_chk_str + "<td align='center' class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" 
    			+((i%2==0)?"background-color:#f0f0f0;":"") + "'><a target='_blank' style='color:#FF0000;font-weight:bold;text-decoration:none;' href='viewApplicantReference.html?id=" + beans[i].childNodes[0].childNodes[0].nodeValue + "'>view</a>" 
    			+ "</td></tr>";
    				
    		//show_rec_info = false;
    	}
    	
    	ref_chk_str = ref_chk_str + "</table>";
    }
    //else
    //	toggleRequestReferenceCheck();
    
    //if(show_rec_info)
    //	toggleRow('candidate-recommendation-info', 'inline');
    //else
    //	toggleRow('candidate-recommendation-info', 'none');
	
    $('#current_ref_requests').html(ref_chk_str);
    //ref_chk_display.innerHTML = ref_chk_str;
    
    $('form input:radio').click(function(){
		$('#candidate-recommendation-info').show();
	});
  }
}

function onCandidateSelected(sin)
{
  if(sin == -1)
  {
    return;
  }
  
  toggleRow('candidate_info', 'none');
  toggleRow('candidate-recommendation-info', 'none');
  toggleRow('candidate_loading_msg', 'inline');
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseCandidateSelection(xmlHttp);
    }
    xmlHttp.open("GET","addJobTeacherRecommendation.html?op=CANDIDATE_DETAILS&sin="+sin,true);
    xmlHttp.send(null);
  }
}

function onSendReferenceCheckRequest()
{
	var uid = document.getElementById('candidate_name').value;
	
	var email = document.getElementById('referrer_email').value;
	
  if((uid == -1)||(email == ""))
  {
    return;
  }
  
  toggleRow('sending_email_msg', 'inline');
  document.getElementById('referrer_email').value = "";
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseSendReferenceCheckRequestResponse(xmlHttp);
    }
    xmlHttp.open("GET","sendReferenceCheckRequest.html?uid="+uid+"&email="+email,true);
    xmlHttp.send(null);
  }
}

function parseSendReferenceCheckRequestResponse(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    parseCurrentReferenceCheckRequestsReponse(xmlHttp);
    
    //formatting send response
    var msg_display = document.getElementById("request_response_msg");
    
    if(xmlDoc.getElementsByTagName("RESPONSE-MSG").length > 0)
      msg_display.innerHTML = xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue;
    else
    	msg_display.innerHTML = "<SPAN style='color:#FF0000;'>Could not send request.</SPAN>";
    
     toggleRow('sending_email_msg', 'none');
     toggleRow('request_response_row', 'inline');   
  }
}

function onManualReferenceCheckRequest()
{
	var uid = document.getElementById('candidate_name').value;
	
  if(uid == -1)
  {
    return;
  }
  
  openWindow('MANUAL_REFERENCE_CHECK',"manualReferenceCheckRequest.html?uid="+uid, 850, 600, 1) 
}

function parseAddGSUResponse(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    //formatting gsu beans
    var gsu_display = document.getElementById('gsu_display');
    var gsu_str = "<span color='#FF0000'>None Added.</span>";
    
    var beans = xmlDoc.getElementsByTagName("GSU-BEAN");
    
    if(beans.length > 0)
    {	
    	gsu_str = "<table width='80%' align='center' cellpadding='3' cellspacing='3' border='0' style='border:solid 1px #d4d4d4;'>";
    	
    	gsu_str = gsu_str + "<tr><td style='border:solid 1px #d4d4d4;' class='displayHeaderTitle'>Grade</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>Subject</td><td class='displayHeaderTitle' style='border:solid 1px #d4d4d4;'>Unit %</td></tr>";
    	for(var i = 0; i < beans.length; i++)
    	{
    		//grade
    		gsu_str = gsu_str + "<tr><td class='displayText' style='border:solid 1px #d4d4d4;padding-left:3px;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[1].childNodes[0].nodeValue + "</td>";
    		
    		//subject
    		gsu_str = gsu_str + "<td class='displayText' style='padding-left:3px;border:solid 1px #d4d4d4;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[2].childNodes[0].nodeValue + "</td>";
    		
    		//percent
    		gsu_str = gsu_str + "<td class='displayText' style='padding-left:3px;border:solid 1px #d4d4d4;" +((i%2==0)?"background-color:#f0f0f0;":"") + "'>" + beans[i].childNodes[3].childNodes[0].nodeValue + "%</td></tr>";
    	}
    	
    	gsu_str = gsu_str + "<tr><td colspan='3'" +((beans.length%2==0)?" style='background-color:#f0f0f0;'":"") 
    		+ " align='center'><input type='button' style='font-size:10px;' value='Clear' onclick='clearGSU();' /></td></tr>";
    	
    	gsu_str = gsu_str + "</table>";
    } 
	
    gsu_display.innerHTML = gsu_str;    
  }
}


function addGSU()
{
  var g = document.getElementById('gsu_grade').value;
  var s = document.getElementById('gsu_subject').value;
  var u = document.getElementById('gsu_percent').value;
  
  //alert("g = " + g + "\ns = " + s + "\nu = " + u);
  
  /*
  if((g == "-1") || (u == ""))
  {
    return;
  }
  */
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseAddGSUResponse(xmlHttp);
    }
    if((g != "-1") && (u != ""))
    	xmlHttp.open("GET","addJobTeacherRecommendationGSU.html?g="+g+"&s="+s+"&u="+u,true);
    else
    	xmlHttp.open("GET","addJobTeacherRecommendationGSU.html",true);
    	
    xmlHttp.send(null);
  }
}

function refreshGSU(){
	addGSU();
}


function clearGSU()
{
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseAddGSUResponse(xmlHttp);
    }
    xmlHttp.open("GET","clearJobTeacherRecommendationsGSU.html",true);
    xmlHttp.send(null);
  }
}

function toggleRequestReferenceCheck()
{
	var row = document.getElementById('request_reference_info');
	var img = document.getElementById('req_ref_chk_img');
	
	if(img.src.indexOf('expand2.jpg') >= 0)
	{
		img.src = 'images/collapse2.jpg';
		row.style.display='inline';
	}
	else
	{
		img.src = 'images/expand2.jpg';
		row.style.display='none';
	}
}

function onDeleteRefCheck(id)
{
	if((id == null) || (id < 1))
  {
    return;
  }
  
  if(confirm('Are you sure you want to delete this reference check request?'))
  {
	  var xmlHttp = initRequest();
	  
	  if(xmlHttp)
	  {
	    xmlHttp.onreadystatechange = function()
	    {
	      parseCurrentReferenceCheckRequestsReponse(xmlHttp);
	    }
	    xmlHttp.open("GET","deleteTeacherReferenceCheckRequest.html?id="+id,true);
	    xmlHttp.send(null);
	  }
  }
}



function onSendApplicantLoginInfoEmail(uid)
{
	if(confirm('Send login info email?')) {
	  if((uid == null) || (uid < 1))
	  {
	    return;
	  }
	  
	  var xmlHttp = initRequest();
	  
	  if(xmlHttp)
	  {
	    xmlHttp.onreadystatechange = function()
	    {
	      parseSendLoginInfoEmailResponse(xmlHttp);
	    }
	    xmlHttp.open("GET","sendApplicantLoginInfoEmail.html?uid="+uid,true);
	    xmlHttp.send(null);
	    
	    var msg_display = document.getElementById("email_pwd_status");
	    msg_display.innerHTML = "<SPAN style='color:#FF0000;'>Sending...</SPAN>";
	  }
	}
}

function parseSendLoginInfoEmailResponse(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
   
    //formatting send response
    var msg_display = document.getElementById("email_pwd_status");
    
    if(xmlDoc.getElementsByTagName("RESPONSE-MSG").length > 0)
      msg_display.innerHTML = "<SPAN style='color:#FF0000;'>" + xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue + "</SPAN>";
    else
    	msg_display.innerHTML = "<SPAN style='color:#FF0000;'>Could not send email.</SPAN>";
  }
}

function onSendAllApplicantLoginInfoEmail()
{
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseSendAllApplicantLoginInfoEmailResponse(xmlHttp);
    }
    xmlHttp.open("GET","sendAllApplicantLoginInfoEmail.html",true);
    xmlHttp.send(null);
    
    var msg_display = document.getElementById("email_pwd_status");
    msg_display.innerHTML = "<SPAN style='color:#FF0000;'>Sending Request...</SPAN>";
  }
}

function parseSendAllApplicantLoginInfoEmailResponse(xmlHttp)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
   
    //formatting send response
    var msg_display = document.getElementById("email_pwd_status");
    
    if(xmlDoc.getElementsByTagName("RESPONSE-MSG").length > 0)
      msg_display.innerHTML = "<SPAN style='color:#FF0000;'>" + xmlDoc.getElementsByTagName("RESPONSE-MSG")[0].childNodes[0].nodeValue + "</SPAN>";
    else
    	msg_display.innerHTML = "<SPAN style='color:#FF0000;'>Could not send request.</SPAN>";
  }
}

