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
	<div id="printJob">	
	<br/>
		 <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
		 		<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
            <div class="BCSHeaderText">Security Information</div>
		  
		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Password:</label><input type="hidden" id="cid" name="cid" value="${sec.id}">
		      <input type="hidden" id="scid" name="scid" value="${sec.contractorId}">
		      <div class="col-sm-8">
		        <input class="form-control" id="npassword" name="npassword" type="password" placeholder="Enter new password" value="${sec.password}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Confirm Password:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="cnpassword" name="cnpasssword" type="password" placeholder="Confirm new password"  value="${sec.password}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Security Question:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="question" name="question" type="text" placeholder="Enter security question" value="${sec.securityQuestion}">
		      </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-3" for="email">Answer:</label>
		      <div class="col-sm-8">
		        <input class="form-control" id="answer" name="answer" type="text" placeholder="Enter answer" value="${sec.sqAnswer}">
		      </div>
		    </div>
  <img src="includes/img/bar.png" width="100%" style="padding-bottom:10px;">
			<div class="form-group">        
		      <div class="col-sm-offset-2 col-sm-10">
		      	<br />
		        <button type="button" class="btn btn-sm btn-default" id="submitupdate" name="submitupdate" onclick="confirmSecurityFields();">Update Information</button>
		      </div>
		    </div>
		  </form>
		
	</div>
	

   <script src="includes/js/jQuery.print.js"></script>