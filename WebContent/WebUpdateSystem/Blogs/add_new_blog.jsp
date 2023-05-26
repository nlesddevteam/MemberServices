<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>   

<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>BLOG Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    
	<script>
    $(document).ready(
    		
    		  		
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#blog_date" ).datepicker({
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
<div class="siteHeaderGreen">Add Blog</div>
  
  
        Below you can add new Blog to the web site. To add other files to this meeting, you will need to complete the adding of these Minutes and use the edit option.            
                   
<br/><br/>
    <form id="pol_cat_frm" action="addNewBlog.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">

     <div class="row">
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
     <b>Title: (Max 60 Characters)</b><br/>
     <input type="text" id="blog_title"  name="blog_title" required class="form-control" maxlength="60">   
     </div>
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">                   
     <b>Date:</b><br/>
     <input type="text" id="blog_date" name="blog_date" required class="form-control">
     </div>
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
     <b>Status:</b><br/>
     <select id="blog_status" name="blog_status" required class="form-control">
						<c:forEach var="item" items="${statuslist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
     </div>
     </div>
     
     <br/><br/>
     
     <div class="row">
     <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
     <b>Content:</b><br/>
     <textarea id="blog_content" name="blog_content" required maxlength="2200" class="form-control"></textarea>   
     </div>
     </div>
      <br/><br/>
      <div class="row">
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">                   
     <b>Photo:</b><br/>
     <input type="file" id="blog_photo" name="blog_photo" class="form-control" accept=".png,.jpg">
     </div>
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
     <b>Photo Caption:</b><br/>
     <input type="text" id="blog_photo_caption"  name="blog_photo_caption" class="form-control" maxlength="60">
     </div>
     <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
     <b>Document (PDF):</b><br/>
     <input type="file" id="blog_document" name="blog_document" class="form-control" accept=".pdf">
     </div>
     </div>
     
      
      <br/><br/>
                      
            <div align="center">       
    				<button class="btn btn-sm btn-success" id="butSave" onclick="loadingData();">SAVE</button> &nbsp; 
     				<A class="btn btn-sm btn-danger" HREF='viewBlogs.html' onclick="loadingData();">CANCEL</a>
			</div>               
         
    </form>
    
    
    
    <script>
    
    CKEDITOR.replace( 'blog_content' );
    </script>
    </div>
    
    
	
  </body>

</html>