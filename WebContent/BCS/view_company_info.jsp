<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
        		$('#loadingSpinner').css("display","none");

});
</script>    	

<script>

  
  $("#crsameas").on("change", function(){
		 
	  // Same as address click with backup!!
    if (this.checked) {
    	//copy physical address to mailing address
	      $("#crfirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#crlastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#crphonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#cremail").val("${contractor.email}").prop("disabled",true);	    
	          
  
    } else {
    	//re-populate with old values from memory (if any) in case ticked by mistake.
    	 $("#crfirstname").val($("#crfirstnametemp").val()).prop("disabled",false);    	 
    	 $("#crlastname").val($("#crlastnametemp").val()).prop("disabled",false);    	
    	 $("#crphonenumber").val($("#crphonenumbertemp").val()).prop("disabled",false);    	
    	 $("#cremail").val($("#cremailtemp").val()).prop("disabled",false);    	
    	
    	 
    }
  });
  
  
  $("#tosameas").on("change", function(){
		 
	  // Same as address click with backup!!
    if (this.checked) {
    	//copy physical address to mailing address
    	 $("#tofirstname").val($("#firstname").val()).prop("disabled",true);	     
	      $("#tolastname").val($("#lastname").val()).prop("disabled",true);	      
	      $("#tophonenumber").val($("#cellphone").val()).prop("disabled",true);	     
	      $("#toemail").val("${contractor.email}").prop("disabled",true);	       
  
    } else {
    	//re-populate with old values from memory (if any) in case ticked by mistake.
    	 $("#tofirstname").val($("#tofirstnametemp").val()).prop("disabled",false);    	 
    	 $("#tolastname").val($("#tolastnametemp").val()).prop("disabled",false);    	
    	 $("#tophonenumber").val($("#tophonenumbertemp").val()).prop("disabled",false);    	
    	 $("#toemail").val($("#toemailtemp").val()).prop("disabled",false);    	
    	 
    }
  });
  
  
</script>













	    
	<div id="printJob">	
	<br/>
	 <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		<div class="BCSHeaderText">Company Information</div>
		
				
		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
            <div class="form-group">
                <label class="control-label col-sm-4" for="email">Types of Transportation:</label>
                <label class="checkbox-inline">
      				<input type="checkbox"  id="tregular" name="tregular" <c:out value="${company.tRegular eq 'Y' ? 'CHECKED' : ''}"/>>Regular 
    			</label>
    			<label class="checkbox-inline">
      				<input type="checkbox"  id="talternate" name="talternate" <c:out value="${company.tAlternate eq 'Y' ? 'CHECKED' : ''}"/>>Alternate
    			</label>
    			<input type="hidden" id="cid" name="cid" value="${company.id}">
                <input type="hidden" id="scid" name="scid" value="${company.contractorId}">
            </div>
            <div class="form-group">
            	<label class="control-label col-sm-4" for="email">I am a parent/guardian of the student</label>
                <label class="checkbox-inline">
                	<input type="checkbox"  id="tparent" name="tparent" <c:out value="${company.tParent eq 'Y' ? 'CHECKED' : ''}"/>>
    			</label>
            </div>
            <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
            <div class="BCSHeaderText">Contractor Representative</div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="email">Same as contact information &nbsp;&nbsp;<input type="checkbox"  id="crsameas" name="crsameas" <c:out value="${company.crSameAs eq 'Y' ? 'CHECKED' : ''}"/>></label>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">First Name:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="crfirstname" name="crfirstname" type="text" placeholder="Enter first name" value="${company.crFirstName}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Last Name:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="crlastname" name="crlastname" type="text" placeholder="Enter last name" value="${company.crLastName}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Mobile Phone:</label>
                <div class="col-sm-4">
                    <input class="form-control" id="crphonenumber" name="crphonenumber" type="text" placeholder="Enter mobile phone" value="${company.crPhoneNumber}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Email:</label>
                <div class="col-sm-4">
                    <input class="form-control" id="cremail" name="cremail" type="text" placeholder="Enter email" value="${company.crEmail}">
                </div>
            </div>
            
             <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
            <div class="BCSHeaderText">Transportation Officer</div>
            
		<div class="form-group">
                
                <label class="control-label col-sm-4" for="email">Same as contact information &nbsp;&nbsp;                
                
                
                <input type="checkbox"  id="tosameas" name="tosameas" <c:out value="${company.toSameAs eq 'Y' ? 'CHECKED' : ''}"/>>
    			</label>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">First Name:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="tofirstname" name="tofirstname" type="text" placeholder="Enter first name" value="${company.toFirstName}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Last Name:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="tolastname" name="tolastname" type="text" placeholder="Enter last name" value="${company.toLastName}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Mobile Phone:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="tophonenumber" name="tophonenumber" type="text" placeholder="Enter mobile phone" value="${company.toPhoneNumber}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Email:</label>
                <div class="col-sm-7">
                    <input class="form-control" id="toemail" name="toemail" type="text" placeholder="Enter email" value="${company.toEmail}">
                </div>
            </div>
		     <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">  
			<div class="form-group">        
		      <div class="col-sm-offset-2 col-sm-10">
		      	<br />
		        <button type="button" class="btn btn-xs btn-primary" id="submitupdate" name="submitupdate" onclick="confirmCompanyFields();">Update Information</button>
		      </div>
		    </div>
		  </form>
				<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	</div>
	

  <script src="includes/js/jQuery.print.js"></script>	