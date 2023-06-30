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
		<title>Meeting Minutes Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
   
<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#mm_date" ).datepicker({
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
<div class="siteHeaderGreen">Add Minutes</div>

Below you can add new Minutes to the web site. To add other files to this meeting, you will need to complete the adding of these Minutes and use the edit option.
<br/><br/>
    <form id="pol_cat_frm" action="addNewMeetingMinutes.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      		<div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
      				<b>Title: (Max 60 Characters)</b><br/>
     				 <input type="text" id="mm_title"  name="mm_title" required class="form-control" maxlength="60"/>
      		</div>
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
      				<b>Minute Category:</b><br/>
      					<select id="meeting_category" name="meeting_category" required class="form-control">
            				<c:forEach var="item" items="${categorylist}" >
                				<option value="${item.key}">${item.value}</option>
            				</c:forEach>
                        </select>
             </div>
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">        
                     <b>Meeting Held On Date:</b><br/>
                     <input type="text" id="mm_date" name="mm_date" required class="form-control"/>
             </div>
             </div>
             <br/><br/>
             <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                     <b>Minutes Doc: (PDF Files ONLY)</b><br/>
                     <input type="file" id="mm_doc" name="mm_doc" required class="form-control" accept=".pdf"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
					<b>Presentation Title:</b><br/>
					<input type="text" id="mm_rel_pre_title" name="mm_rel_pre_title" class="form-control" maxlength="60"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">		
					<b>Presentation File (PDF):</b><br/>
					<input type="file"  id="mm_rel_pre_doc" name="mm_rel_pre_doc" class="form-control" accept=".pdf"/>
			</div>
			</div>
			<br/><br/>
			
			<div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">		
					<b>Related Documentation Title:</b><br/>
					<input type="text" id="mm_rel_doc_title" name="mm_rel_doc_title" class="form-control" maxlength="60"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">		
					<b>Related Documentation File (PDF):</b><br/>
                      <input type="file" id="mm_rel_doc" name="mm_rel_doc" class="form-control" accept=".pdf"/> 
             </div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">			
			<b>YouTube Meeting Link:</b><br/>
       		<input type="text" id="mm_meeting_video" name="mm_meeting_video" class="form-control" placeholder="https://">
            </div> 
            </div>
                  
            <br/><br/>
                   <div align="center">
					<button class="btn btn-sm btn-success" id="butSave" onclick="loadingData();">SAVE</button> &nbsp; 
					<A class="btn btn-sm btn-danger" HREF='viewMeetingMinutes.html' onclick="loadingData();">CANCEL</a>
                   </div>
                   
    </form>
   
   </div>
   </div>
  </div>
    
    
  </body>

</html>
    