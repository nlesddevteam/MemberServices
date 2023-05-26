<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
<%@ page
	import="java.util.*,
					com.esdnl.webupdatesystem.newspostings.bean.*,
					com.esdnl.webupdatesystem.newspostings.dao.*,
					com.esdnl.webupdatesystem.newspostings.constants.*,
					com.esdnl.util.*"%> 
					    
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-ANNOUNCEMENTS" />
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>News Posting System</title>
					
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
   
	
		<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#news_date" ).datepicker({
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
<div class="siteHeaderGreen">Add News Posting</div>
  
 When you select a category, only Announcements, Media Releases, and Important Notice are currently implemented on the NLESD website.<p>
   
                      
    
    <form id="pol_cat_frm" action="addNewNewsPostings.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">
    
     <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3"> 
                    <b>Date:</b><br/>
                    <input type="text" id="news_date" name="news_date" required class="form-control">                      
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <b>Category:</b><br/>                      
                       <select id="news_category" name="news_category" required class="form-control">
						<c:forEach var="item" items="${categorylist}">
    						<c:if test="${item.key eq '2' or item.key eq '3' or item.key eq '5'}">
    						<option value="${item.key}">${item.value}</option>
    						</c:if>
						</c:forEach>
                        </select>
            </div>            
            <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">            
                    <b>Location:</b><br/>
                       <select id="news_location" name="news_location" required class="form-control">
                       		<option value='-1'>NONE</option>
                       		<c:forEach var="item" items="${locationlist}">
    							<option value="${item.locationId}">${item.locationDescription}</option>
							</c:forEach>
                        </select>       
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
             <b>Status:</b><br/>                      
                       <select id="news_status" name="news_status" required class="form-control">
						<c:forEach var="item" items="${statuslist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
            </div>
            </div>
            <br/><br/>
            <div class="row">
      		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">                          
                    <b>Title: (Max 60 characters)</b><br/>
                    <input type="text" id="news_title"  name="news_title"  maxlength="60" required class="form-control">
            </div>
            </div>
            <br/><br/>
            <div class="row">
      		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">        
                    <b>Details:</b><br/>
                    <textarea id="news_description" name="news_description" required class="form-control"></textarea>
            </div>
            </div>
            <br/><br/>
            <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">      	
                  	<b>Photo: (png or jpg ONLY)</b><br/>
                  	<input type="file" id="news_photo" name="news_photo" class="form-control" accept=".jpg,.png">
            </div>      	                   
            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">       	
                   	<b>Caption:</b><br/>
                   	<input type="text" id="news_photo_caption"  name="news_photo_caption" class="form-control">
            </div>
            </div>
            <br/><br/>
            <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">         	
                   	<b>Documentation: (PDF)</b><br/>
                   	<input type="file" id="news_documentation" name="news_documentation" class="form-control" accept=".pdf">
            </div>       	                   
            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">       	
                   	<b>External Link:</b><br/>
                   	<input type="text"  id="news_external_link"  name="news_external_link"  placeholder="http://" class="form-control">
            </div>       	
            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">       	
                   	<b>Link Title:</b><br/>
                   	<input type="text"  id="news_external_link_title" name="news_external_link_title" class="form-control">
            </div>       	
            </div>
                   
            <br/><br/>                          
                   <div align="center">
                   <button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">SAVE</button> &nbsp; <A HREF='viewNewsPostings.html' class="btn btn-sm btn-danger" onclick="loadingData();">CANCEL</a>
                   </div>
                      

          
    </form>
    
       
     <script>
    CKEDITOR.replace( 'news_description' );
    </script>
	
	</div>
	</div>
	</div>
			
  </body>

</html>