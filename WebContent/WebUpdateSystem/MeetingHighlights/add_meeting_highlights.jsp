<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>Meeting Highlights Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
  

<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#mh_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

	);

</script>

	
	</head>

  <body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Add Highlights</div>
Below you can add new Highlights to the web site. To add other files to this meeting, you will need to complete the adding of these Minutes and use the edit option.


    <form id="pol_cat_frm" action="addNewMeetingHighlights.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      
      <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
		      <b>Title:</b><br/>
		      <input type="text" id="mh_title"  name="mh_title" class="form-control" required maxlength="60">
	  </div>
	  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">      
      <b>Date:</b><br/>
      <input type="text" id="mh_date" name="mh_date" required class="form-control">
     </div>
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
     <b>Document (PDF):</b><br/>
      <input type="file" id="mm_doc" name="mh_doc" required class="form-control" accept=".pdf">      
     </div>
     </div>
     <br/><br/>
     <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
      <b>Presentation Title (If Any):</b><br/>
      <input type="text" id="mh_pre_title" name="mh_pre_title" class="form-control" maxlength="60">
     </div>
     <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
      <b>Presentation File (PDF):</b><br/>
      <input type="file" id="mh_pre_doc" name="mh_pre_doc" class="form-control" accept=".pdf">      
    </div>
    <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
      <b>Related Document Title:</b><br/>
      <input type="text" id="mh_rel_doc_title" name="mh_rel_doc_title" class="form-control" maxlength="60">
    </div>
    <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
      <b>Related Document File (PDF):</b><br/>
      <input type="file" id="mh_rel_doc" name="mh_rel_doc" class="form-control" accept=".pdf">
     </div>
     </div>
     <br/><br/>
     <div class="row">
     <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
      <b>YouTube Meeting Link:</b><br/>
      <input type="text" id="mh_meeting_video"  name="mh_meeting_video" class="form-control" placeholder="https://">
   </div>
   </div>
 
 
                    <br/><br/>
					<div align="center"> 
                  <button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">SAVE</button> &nbsp; 
                  <A class="btn btn-sm btn-danger" HREF='viewMeetingHighlights.html' onclick="loadingData();">CANCEL</a>
                  </div>
</form>
    
     
  </div>
  </div>
  </div>
     
     
  </body>

</html>