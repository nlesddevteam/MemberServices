<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         				 com.awsd.personnel.*,
         				 com.awsd.school.*, 
                 com.esdnl.servlet.Form,
                 java.util.*,
                 org.apache.commons.lang.*,
                 net.sf.json.*"
         isThreadSafe="false"%>

<%
	Form form = (Form) request.getAttribute("form");

	User usr = null;
  PersonnelCategories cats = new PersonnelCategories();
  Schools schools = new Schools();
  String email_addr = "";
  
  usr = (User) session.getAttribute("usr");
  
  if(usr == null || usr.isAdmin()) {
  	usr = new User();
  }

  if(usr.isRegistered()) { 
  	request.getRequestDispatcher("memberservices_frame.jsp").forward(request, response);
  }
  
  JSONObject preload = (JSONObject) session.getAttribute("REGISTATION-GOOGLE-PRELOADED-DATA");
  
  if(form == null && preload == null) {
  	throw new RuntimeException("How did you get here!!");
  } 
  
  preload = preload.getJSONObject("user");
  
%> 
  

<HTML>
  <HEAD>
    
    <TITLE>Members Services  -  Registration - Newfoundland &amp; Labrador English School District</title>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link href="css/memberservices.css" rel="stylesheet" />
    
    <script type="text/javascript" src="/MemberServices/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
	    $(function(){
	    	$('#frm-register').submit(function(){
	    		var check = true;
	    		var error = "";
	    		
	 	      if($.trim($('#firstname').val()) == "") {
	 	        error = 'First name required for registration.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#lastname').val()) == "") {
	 	    	 	error = 'Last name required for registration.';
	 	        check = false;
	 	      }
	 	     	else if($.trim($('#emailaddr').val()) == "") {
	 	    	 	error = 'Email required for registration.';
	 	        check = false;
	 	      }
	 	      else if($('#job').val() == "") {
	 	    	 	error = 'Job Function required for registration.';
	 	        check = false;
	 	      }
	 	      else if(($.trim($('#job').val()) != "") && schoolRequired() && ($('#school').val() == "")) {
	 	        error = 'School required for registration.';
	 	        check = false;  
	 	      }
	 	      else if(emailCheck($('#emailaddr').val())==false) {
	 	        check = false;
	 	       	error = 'Invalid Email Address (ie; johndoe@nlesd.ca).';
	 	      }
	 	      if(!check) {
	 	    	  $('#msg td').text('ERROR: ' + error);
	 	    		$('#processing').hide();
		 	    	$('#btn-add').show();
	 	      }
	 	      
	 	      return check;
	    	});
	 	    
	 	    $('#job').change(function(){
	 	    		if(schoolRequired()) {
	 	    			 $('#school').removeAttr('disabled');
	 	    	  }
 	    	    else {
 	    	    	$('#school').attr('disabled', 'disabled');
 	    	    }
	 	    		
 	    	    $('#school').val("");
	 	    });
	 	    
	 	    $('#btn-add').click(function(){
	 	    	$(this).hide();
	 	    	$('#msg td').text("");
	 	    	$('#processing').show();
	 	    	
	 	    	
	 	    	$('#frm-register').submit();
	 	    });
	 	   
	      if($('#job').val() != ""){
	    	  $('#school').removeAttr('disabled')
	      }
	      else {
	      	$('#school').val("").removeAttr('disabled').attr('disabled', 'disabled');
	      }
	      
	    });
    
    	function isTeacher() {
    		var check = false;
    		
    	  if($('#jobs').val() != "") {
    		  var job = $('#job option:selected').text();
    		  
    	    if((job == "TEACHER") || (job == "VICE PRINCIPAL") || (job == "PRINCIPAL") || (job == "GUIDANCE COUNSELLOR")) {
    	     check = true;
    	    }
    	  }
    	  
    	  return check;
    	}

    	function isSecretary() {
    		var check = false;
    		
    	  if($('#jobs').val() != "") {
    		  var job = $('#job option:selected').text();
    		  
    	    if(job == "SCHOOL SECRETARY") {
    	      check = true;
    	    }
    	  }
    	  
    	  return check;
    	}
    	
    	function isSchoolMaintenance() {
    		var check = false;
    		
     	  if($('#jobs').val() != "") {
     		  var job = $('#job option:selected').text();
     		  
     	    if(job == "SCHOOL MAINTENANCE") {
     	      check = true;
     	    }
     	  }
     	  
     	  return check;
     	}
    	
    	function isStudentAssistant() {
    		var check = false;
    		
     	  if($('#jobs').val() != "") {
     		  var job = $('#job option:selected').text();
     		  
     	    if(job == "STUDENT ASSISTANT") {
     	      check = true;
     	    }
     	  }
     	  
     	  return check;
     	}
    	
    	function schoolRequired() {
    		return isTeacher() || isSecretary() || isSchoolMaintenance() || isStudentAssistant();
    	}
    	
    	function emailCheck (emailStr) {
	    	/* The following variable tells the rest of the function whether or not
	    	to verify that the address ends in a two-letter country or well-known
	    	TLD.  1 means check it, 0 means don't. */
	
	    	var checkTLD=1;
	
	    	/* The following is the list of known TLDs that an e-mail address must end with. */
	
	    	var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|ca)$/;
	
	    	/* The following pattern is used to check if the entered e-mail address
	    	fits the user@domain format.  It also is used to separate the username
	    	from the domain. */
	
	    	var emailPat=/^(.+)@(.+)$/;
	
	    	/* The following string represents the pattern for matching all special
	    	characters.  We don't want to allow special characters in the address. 
	    	These characters include ( ) < > @ , ; : \ " . [ ] */
	
	    	var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
	
	    	/* The following string represents the range of characters allowed in a 
	    	username or domainname.  It really states which chars aren't allowed.*/
	
	    	var validChars="\[^\\s" + specialChars + "\]";
	
	    	/* The following pattern applies if the "user" is a quoted string (in
	    	which case, there are no rules about which characters are allowed
	    	and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
	    	is a legal e-mail address. */
	
	    	var quotedUser="(\"[^\"]*\")";
	
	    	/* The following pattern applies for domains that are IP addresses,
	    	rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
	    	e-mail address. NOTE: The square brackets are required. */
	
	    	var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
	
	    	/* The following string represents an atom (basically a series of non-special characters.) */
	
	    	var atom=validChars + '+';
	
	    	/* The following string represents one word in the typical username.
	    	For example, in john.doe@somewhere.com, john and doe are words.
	    	Basically, a word is either an atom or quoted string. */
	
	    	var word="(" + atom + "|" + quotedUser + ")";
	
	    	// The following pattern describes the structure of the user
	
	    	var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
	
	    	/* The following pattern describes the structure of a normal symbolic
	    	domain, as opposed to ipDomainPat, shown above. */
	
	    	var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");
	
	    	/* Finally, let's start trying to figure out if the supplied address is valid. */
	
	    	/* Begin with the coarse pattern to simply break up user@domain into
	    	different pieces that are easy to analyze. */
	
	    	var matchArray=emailStr.match(emailPat);
	
	    	if (matchArray==null) {
	
	    	/* Too many/few @'s or something; basically, this address doesn't
	    	even fit the general mould of a valid e-mail address. */
	    	
	    	return false;
	    	}
	    	var user=matchArray[1];
	    	var domain=matchArray[2];
	
	    	// Start by checking that only basic ASCII characters are in the strings (0-127).
	
	    	for (i=0; i<user.length; i++) {
	    		if (user.charCodeAt(i)>127) {
	    			return false;
	    	  }
	    	}
	    	for (i=0; i<domain.length; i++) {
	    		if (domain.charCodeAt(i)>127) {
	    			return false;
	    	  }
	    	}
	
	    	// See if "user" is valid 
	
	    	if (user.match(userPat)==null) {
	    		// user is not valid
	    		return false;
	    	}
	
	    	/* if the e-mail address is at an IP address (as opposed to a symbolic
	    	host name) make sure the IP address is valid. */
	
	    	var IPArray=domain.match(ipDomainPat);
	    	if (IPArray!=null) {
	    	// this is an IP address
		
		    	for (var i=1;i<=4;i++) {
		    		if (IPArray[i]>255) {
		    	
		    			return false;
		    	  }
		    	}
		    	return true;
	    	}
	
	    	// Domain is symbolic name.  Check if it's valid.
	    	 
	    	var atomPat=new RegExp("^" + atom + "$");
	    	var domArr=domain.split(".");
	    	var len=domArr.length;
	
	    	if(((domArr[0]!="nlesd"))||(domArr[1]!="ca")) {
	    	  return false;
	    	}
	
	    	/* domain name seems valid, but now make sure that it ends in a
	    	known top-level domain (like com, edu, gov) or a two-letter word,
	    	representing country (uk, nl), and that there's a hostname preceding 
	    	the domain or country. */
	
	    	if (checkTLD && domArr[domArr.length-1].length!=2 && domArr[domArr.length-1].search(knownDomsPat)==-1) {
	    	
	    		return false;
	    	}
	
	    	// Make sure there's a host name preceding the domain.
	
	    	if (len<2) {
	    	
	    		return false;
	    	}
	
	    	// If we've gotten this far, everything's valid!
	    	return true;
    	}
    </script>
  </head>

  <BODY topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <script>
      history.go(1);
    </script>
    <form id='frm-register' action="memberRegistration.html" method="POST">
      
    	<br/><br/>
    	<div align="center">
      <table width="770" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td valign="top">
            <img src="images/nlesdms.jpg"><BR>
          </td>
          <td width="161" valign="bottom" align="right">
          	<% if(usr != null && StringUtils.isNotEmpty(usr.getUsername())) { %>
            	<a href="logout.html">LOGOUT</A>
            <%} else { %>
            	&nbsp;
            <% } %>
          </td>
        </tr>
        <tr>
          <td  valign="top" colspan="2">
            <HR noshade size="1" color="#CCCCCC" width="100%">
            <BR><BR>
          </td>
        </tr>
      </table>
      <table width="770" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="770" valign="top">
           
              <table width="600" cellpadding="0" cellspacing="0" bgcolor="#666666" border="0" align="center">
                <tr>
                  <td width="100%">
                    <table width="100%" cellpadding="4" cellspacing="1" border="0">
                      <tr>
                        <td colspan="2" valign="middle">
                          <span class="title2">One Time Registration for Member Services</span><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">First Name</span><BR>
                        </td>
                        <td width="*" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                        	<input type="hidden" id="firstname" name="firstname" value='<%= form != null ? form.get("firstname"): preload != null ? preload.getString("firstname") : "" %>' />
                          <input type="text" size="30" value='<%= form != null ? form.get("firstname"): preload != null ? preload.getString("firstname") : "" %>' />
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Last Name</span><BR>
                        </td>
                        <td width="*" bgcolor="#f4f4f4" colspan="1" valign="middle" align="left">
                        
                          <input  id="lastname" name="lastname" type="text" size="30" value='<%= form != null ? form.get("lastname"): preload != null ? preload.getString("lastname") : "" %>' />
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#f4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Email Address</span><BR>
                        </td>
                        <td width="*" bgcolor="#f4f4f4" colspan="1" valign="middle" align="left">
                        	<input type="hidden" id="emailaddr" name="emailaddr" value='<%= form != null ? form.get("emailaddr"): preload != null ? preload.get("email") : "" %>' />
                          <input type="text" size="30" value='<%= form != null ? form.get("emailaddr"): preload != null ? preload.get("email") : "" %>' disabled />
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Job Function</span><BR>
                        </td>
                        <td width="*" bgcolor="#F4f4f4" colspan="1" valign="middle" align="left">
                          <select size="1" id="job" name="job">
                            <option value="">--- Select Job Category ---</option>
                            <% for(PersonnelCategory cat : cats) {%>  
                            	<option value="<%=cat.getPersonnelCategoryID()%>" <%= ((form != null) && (form.getInt("job") == cat.getPersonnelCategoryID())) ? " selected" : "" %>><%=cat.getPersonnelCategoryName()%></option>
                            <%}%>
                          </select>
                        </td>
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">School</span><BR>
                        </td>
                        <td width="*" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <select size="1" id="school" name="school">
                            <option value="">--- Select School ---</option>
                            <% for(School s : schools){ %>
                            	<option value="<%=s.getSchoolID()%>" <%= ((form != null) && form.exists("school") && (form.getInt("school") == s.getSchoolID())) ? " selected" : "" %>><%=s.getSchoolName()%> (<%= s.getTownCity() %>)</option>
                            <%}%>
                          </select>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            
          </td>
        </tr>
        <tr id='msg'>
          <td align="center" style='color: red;font-weight: bold; padding: 3px;'>
            <%=StringUtils.isNotEmpty((String)request.getAttribute("msg"))? request.getAttribute("msg") : "&nbsp;"%>
          </td>
        </tr>
        <tr>
          <td align="center">
            <BR>
            <img id='processing' src="/MemberServices/images/processing_ani.gif" style='display:none;'>
            <img id='btn-add' src="images/register_01.jpg" 
              onMouseOver="src='images/register_02.jpg'" 
              onMouseOut="src='images/register_01.jpg'" 
              onMouseDown="src='images/register_03.jpg'" 
              onMouseUp="src='images/register_02.jpg'">
          </td>
        </tr>
      </table>
      <table width="770" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="100%" valign="top" colspan="2">
            <img src="images/spacer.gif" width="1" height="30"><BR>
            <HR noshade size="1" color="#CCCCCC" width="100%">
          </td>
        </tr>
        <tr>
          <td width="200" valign="top" aling="left">
            <a href="http://www.nlesd.ca">www.nlesd.ca</a><BR>
          </td>
          <td width="*" valign="top" align="right">
            <span class="smalltext">&copy; 2013 Newfoundland &amp; Labrador English School District. All Right Reserved.</span><BR>
          </td>
        </tr>
      </table>
   </div>
    </form>
  </body>
</html>