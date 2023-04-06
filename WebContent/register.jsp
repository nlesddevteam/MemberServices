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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>


<%
	Form form = (Form) request.getAttribute("form");

	User usr = null;
  PersonnelCategories cats = new PersonnelCategories();
  Schools schools = new Schools();
  String email_addr = "";
  String sysERR1 = null;
  
  usr = (User) session.getAttribute("usr");
  
  if(usr == null || usr.isAdmin()) {
  	usr = new User();
  }

  if(usr.isRegistered()) { 
  	request.getRequestDispatcher("memberservices_frame.jsp").forward(request, response);
  }
  
  JSONObject preload = (JSONObject) session.getAttribute("REGISTATION-GOOGLE-PRELOADED-DATA");
  
  if(form == null && preload == null) {
  	//throw new RuntimeException("How did you get here!!");
  	sysERR1="The FORM submission and/or Google input data is invalid.";
  	
  } else {
  
  preload = preload.getJSONObject("user");
  }
%> 
  <c:set var="sysERR" value="<%=sysERR1%>"/>

<HTML>
  <HEAD>
    
    <TITLE>NLESD StaffRoom Registration</title>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
   	<meta name="viewport" content="width=device-width, initial-scale=1"/>
     <script type="text/javascript">
	    $(function(){	 	    
	 	    $('#btn-add').click(function(){
	 	    	$(this).hide();
	 	    	$('#msg').text("");
	 	    	$('#processing').show();
	 	    	$('#frm-register').submit();
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
		
		    	var specialChars="\\(\\)><_@,;:\\\\\\\"\\.\\[\\]";
		
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

 <body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">NLESD StaffRoom - First Time Registration</div>



   
           <c:choose>
           <c:when test="${ sysERR ne null }"> 
           <div class="alert alert-danger" style="text-align:center;font-size:12px;"><b>SYSTEM ERROR:</b> There has been an error. ${sysERR} Please contact <a href="https://forms.gle/rpYgeZfm81Wt5c138" target="_blank">StaffRoom HelpDesk</a> for assistance.</div>
          
          
          <a href="../index.jsp" class="btn btn-sm btn-primary">EXIT TO NLESD</a>
           </c:when>
           <c:otherwise>
           <form id='frm-register' action="memberRegistration.html" method="POST" class="was-validated">
           <!-- SET LOCATION AS NONE -->
   			<input type="hidden" id="school" name="school" value='-1' />
                  
           Please confirm your name below before you register. Being a new user you cannot edit your email or classification status at this time. 
           Your name, email and classification status are set automatically during first time registration. 
           <br/><br/>
           <div class="alert alert-danger"><b>NOTICE:</b> If you already had a StaffRoom (MemberServices) Account and you are on this page, you may have changed your email address. 
           Please cancel this step and contact the <a href="mailto:geofftaylor@nlesd.ca?subject=StaffRoom New User Registration">System Administrator</a>
						to update your current account with your updated email address. Provide your original email address and your updated email address.</div> 
           
           
         
             <div class="row">
    <div class="col">         
                      <b>First Name</b>
                      <input type="hidden" id="firstname" name="firstname" value='<%= form != null ? form.get("firstname"): preload != null ? preload.getString("firstname") : "" %>' />
                      <input type="text" class="form-control" value='<%= form != null ? form.get("firstname"): preload != null ? preload.getString("firstname") : "" %>' required/>
    
    </div>
    <div class="col">
                      <b>Last Name</b>
                      <input  id="lastname" name="lastname" type="text" class="form-control"  value='<%= form != null ? form.get("lastname"): preload != null ? preload.getString("lastname") : "" %>' required/>
    </div>
    </div>
    <br/><br/>
    <div class="row">
    <div class="col"> 
                     
                     <b>Email Address</b>
                     <input type="hidden" id="emailaddr" name="emailaddr" value='<%= form != null ? form.get("emailaddr"): preload != null ? preload.get("email") : "" %>' />
                     <input type="text" class="form-control" value='<%= form != null ? form.get("emailaddr"): preload != null ? preload.get("email") : "" %>' disabled />
   </div>   
   <div class="col"> 
   					<b>Classification/Status</b>
                    <input type="text" id="job" name="job" class="form-control" value='NEW USER' readonly /> 
   </div>
   </div>              
       <br/><br/>
                    
     <div class="alert alert-warning"><b>NOTICE:</b> You must have your supervisor or HR contact the <a href="mailto:geofftaylor@nlesd.ca?subject=StaffRoom New User Registration">System Administrator</a> 
     to confirm your job position and location as to set proper user permissions for StaffRoom application access once you are successfully registered.</div>       
     
                        
              <div id="processing" class="alert alert-info" style="text-align:center;display:none;">PROCESSING. Please wait...</div>       
    <br/><br/>               
         	<div align="center"><a id="btn-add" href="#" class="btn btn-sm btn-primary">REGISTER</a> <a href="../index.jsp" class="btn btn-sm btn-danger">CANCEL</a></div>
            
             
    </form>
    </c:otherwise>
    </c:choose>
  </div>
  </div>
  </div>
    
  </body>
</html>