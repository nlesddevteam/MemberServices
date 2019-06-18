<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*, 
                 java.util.*,org.apache.commons.lang.*"
         isThreadSafe="false"%>

<%
  User usr = (User) session.getAttribute("usr");
  

  if(usr == null){%>  
    <jsp:forward page="signin.jsp">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}else if(!usr.isRegistered()){%>  
	    <jsp:forward page="reqister.jsp"/>
<%}else if(usr.getPersonnel().getEmailAddress().indexOf("@nlesd.ca") >= 0){ %>  
	    <jsp:forward page="memberservices_frame.jsp"/>
<%}
  
  Personnel p = usr.getPersonnel();
%>

<HTML>
  <HEAD>
    
    <TITLE>Members Services  -  Member Services/FirstClass Account Update - Newfoundland &amp; Labrador English School District</title>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link href="css/memberservices.css" rel="stylesheet">

		<script type="text/javascript" src="/MemberServices/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$('#frm-convert').submit(function(){
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
	 	     	else if($.trim($('#uid').val()) == "") {
	 	    	 	error = 'Firstclass userid required for registration.';
	 	        check = false;
	 	      }
	 	     	else if($.trim($('#confirm_uid').val()) == "") {
	 	    	 	error = 'Firstclass userid confirmation required for registration.';
	 	        check = false;
	 	      }
	 	     	else if($.trim($('#uid').val()) != $.trim($('#confirm_uid').val())) {
	 	    		error = 'Firstclass userid confirmation does not match.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#emailaddr').val()) == "") {
	 	    	 	error = 'Email required for registration.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#confirm_emailaddr').val()) == "") {
	 	    	 	error = 'Email confirmation required for registration.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#emailaddr').val()) != $.trim($('#confirm_emailaddr').val())) {
	 	    	 	error = 'Email confirmation does not match.';
	 	        check = false;
	 	      }
	 	      else if(emailCheck($('#emailaddr').val())==false) {
	 	        check = false;
	 	       	error = 'Invalid FirstClass Email Address (ie; johndoe@nlesd.ca).';
	 	      }
	 	      else if($.trim($('#password').val()) == "") {
	 	    	 	error = 'Password required for registration.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#cpassword').val()) == "") {
	 	    	 	error = 'Password confirmation required for registration.';
	 	        check = false;
	 	      }
	 	      else if($.trim($('#password').val()) != $.trim($('#cpassword').val())) {
	 	    		error = 'Password confirmation does not match.';
	 	        check = false;
	 	      }
	 	      
	 	      if(!check) {
	 	    	  $('#msg td').text('ERROR: ' + error);
	 	    		$('#processing').hide();
		 	    	$('#btn-add').show();
	 	      }
	 	      
	 	      return check;
	    	});
    		
    		$('#btn-add').click(function(){
	 	    	$(this).hide();
	 	    	$('#msg td').text("");
	 	    	$('#processing').show();
	 	    	
	 	    	
	 	    	$('#frm-convert').submit();
	 	    });
    	});
    	
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
    <form id="frm-convert" action="awsbAcountConvert.html" method="POST">
    <CENTER>
      <table width="770" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td valign="top">
            <img src="images/nlesdms.jpg"><BR>
          </td>
          <td width="161" valign="bottom" align="right">
            <a href="logout.html">LOGOUT</A>
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
            <center>
              <table width="450" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="450" bgcolor="#FFFFFF" colspan="2" valign="middle" >
                    <img src="images/important_notice.gif"><BR>
                  </td>
                </tr>
                <tr>
                  <td width="450" bgcolor="#FFFFCC" colspan="2" valign="middle" style="border:solid 1px #FF0000; padding:5px;">
                    <span class="text3">
                        To maintain your current Member Services subscription, it is necessary to update your Member Services 
                        account information with your new FirstClass account information. Please complete the form below.
                    </span><BR>
                  </td>
                </tr>
              </table><br><br>
              <table width="450" cellpadding="0" cellspacing="0" bgcolor="#666666" border="0">
                <tr>
                  <td width="450">
                    <table width="450" cellpadding="4" cellspacing="1" border="0">
                      <tr>
                        <td width="450" colspan="2" valign="middle">
                          <span class="title2">Account Update</span><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">First Name</span><BR>
                        </td>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <input type="text" id="firstname" name="firstname" size="30" value="<%=p.getFirstName()%>">
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Last Name</span><BR>
                        </td>
                        <td width="200" bgcolor="#f4f4f4" colspan="1" valign="middle" align="left">
                          <input type="text" id="lastname" name="lastname" size="30" value="<%=p.getLastName()%>">
                        </td>
                      </tr>
                      
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">Previous FirstClass/Member Services User ID</span><BR>
                        </td>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <input type="text" id="previous_uid" name="previous_uid" size="30" value="<%=p.getUserName()%>" disabled>
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">Previous FirstClass/Member Services Email Address</span><BR>
                        </td>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <input type="text" id="previous_email" name="previous_email" size="30" value="<%=p.getEmailAddress()%>" disabled>
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">NLESD FirstClass User ID</span><BR>
                        </td>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="left">
                          <input type="text" id="uid" name="uid" size="30">
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Confirm NLESD FirstClass User ID</span><BR>
                        </td>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="left">
                          <input type="text" id="confirm_uid" name="confirm_uid" size="30">
                        </td>
                      </tr>
                      
                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">NLESD FirstClass Password</span><BR>
                        </td>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <input type="password" id="password" name="password" size="30" value="">
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Confirm NLESD FirstClass Password</span><BR>
                        </td>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="left">
                          <input type="password" id="cpassword" name="cpassword" size="30" value="">
                        </td>
                      </tr>

                      <tr>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="right">
                          <span class="text3">NLESD FirstClass Email Address</span><BR>
                        </td>
                        <td width="200" bgcolor="#FFFFFF" colspan="1" valign="middle" align="left">
                          <input type="text" id="emailaddr" name="emailaddr" size="30" value="">
                        </td>
                      </tr>
                      <tr>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="right">
                          <span class="text3">Confirm NLESD FirstClass Email Address</span><BR>
                        </td>
                        <td width="200" bgcolor="#F4f4f4" colspan="1" valign="middle" align="left">
                          <input type="text" id="confirm_emailaddr" name="confirm_emailaddr" size="30" value="">
                        </td>
                      </tr>  
                    </table>
                  </td>
                </tr>
              </table>
            </center>
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
            <img id='btn-add' src="images/update_01.jpg" 
              onMouseOver="src='images/update_02.jpg'" 
              onMouseOut="src='images/update_01.jpg'" 
              onMouseDown="src='images/update_03.jpg'" 
              onMouseUp="src='images/update_02.jpg'"><BR><BR>
            <img src="images/assistance_2.gif"><BR>
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
    </CENTER>
    </form>
  </body>
</html>