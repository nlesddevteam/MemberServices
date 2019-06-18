<%@ page language="java" session="true" isThreadSafe="false" import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*;"%> 
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>

<%  User usr = (User) session.getAttribute("usr"); %>
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
			
		<!-- Add mousewheel plugin (this is optional) -->
		<script type="text/javascript" src="../fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="../fancybox/jquery.fancybox.js?v=2.1.5"></script>
		<link rel="stylesheet" type="text/css" href="../fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
		<script type="text/javascript" src="../js/changepopup.js"></script>	<script src="../js/jquery-ui.js"></script>
			
			
		<script >		
		
		$(document).ready(function() {

			$(function() {
			    var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
			    $('html').css({'background': 'url(../includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
			    	'-webkit-background-size':'cover',
			    	'-moz-background-size':'cover',
			    	'-o-background-size':'cover',
			    	'background-size':'cover'});
			   });


		}); 
		</script>
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
			});
		</script>
	
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

	<script src="../includes/ckeditor/ckeditor.js"></script>
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				<form id="pol_cat_frm" action="updateBlogDetails.html" method="post" ENCTYPE="multipart/form-data">
				<div class="pageTitleHeader siteHeaders">Viewing Blog Details for &quot;${blog.blogTitle}&quot;</div>
                      <div class="pageBody">

						<%if(request.getAttribute("msg") != null){%>                    
                       
                    
                    <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewBlog.html"><img src="../includes/img/addblog-off.png" class="img-swap menuImage" title="Add Blog"></a> 
                      <a href="viewBlogs.html"><img src="../includes/img/viewblogs-off.png" class="img-swap menuImage" title="View Blogs"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    
                    
                    <%} else {%>

      <input type="hidden" id="op" name="op" value="CONFIRM">      
     <input type="hidden" value="${blog.id}" id="id" name="id">                     
                     
                     <p>Title:<br/>
                     <input type="text" class="requiredinput" id="blog_title"  name="blog_title" style="width:250px;" value="${blog.blogTitle}">
                     
                     <p>Date:<br/>
                     <input type="text" class="requiredinput" id="blog_date" name="blog_date"  style="width:250px;" value="${blog.blogDateFormatted}">
                    <p>Content:<br/>
                     <textarea id="blog_content" name="blog_content" rows="10" cols="10">${blog.blogContent}</textarea>
                     
                     <p>Photo:<br/>
                     <c:if test="${ blog.blogPhoto ne null }">	
										<img src="/includes/files/blog/img/${blog.blogPhoto}" border=0 style="width:250px;"><p>
										</c:if>	
                     <input type="file" size="60" id="blog_photo" name="blog_photo"  class="requiredinput">
                     
                     <p>Photo Caption:<br/>
                     <input type="text" class="requiredinput" id="blog_photo_caption"  name="blog_photo_caption" style="width:250px;" value="${blog.blogPhotoCaption}">
                     
                     <p>Document: (${blog.blogDocument})<br/>
                     <c:if test="${ blog.blogDocument ne null }">	
                     <a href="/includes/files/blog/doc/${blog.blogDocument}">${blog.blogDocument}</a><br/>
                     </c:if>
                      <input type="file" size="60" id="blog_document" name="blog_document"  class="requiredinput">
                     <p>Status:<br/>
                      <select id="blog_status" name="blog_status" class="requiredinput">
						<c:forEach var="item" items="${statuslist}">
    						
    						            				<c:choose>
    							<c:when test="${item.key eq blog.blogStatus.value}">
       									
       									<option value="${item.key}" selected="selected">${item.value}</option>
    							</c:when>
    							<c:otherwise>
        							<option value="${item.key}">${item.value}</option>
    							</c:otherwise>
    							</c:choose>
						</c:forEach>
                        </select>
                      
                    <p>
                    <%if(request.getAttribute("msg") != null){%>
                     
                        <span class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</span>
                      
                    <%}%>
                    
                   <p><button id="butSave">Update</button>
                     
               </div>      
                     
                     
                  <p><img src="../includes/img/bar.png" height=1 width=100%>
				 
					<p><div class="pageSectionHeader siteSubHeaders">Other Blog Files</div>
					
					<div class="pageBody">	
					
					Other attachments such as documentation, forms, or presentations to add to this blog item.<p>
					
					<a class="fancybox" href="#inline1" title="Add Other Blog File" onclick="OpenPopUp('${blog.id}');">Add File</a>
					   
                     
                     
					<table class="newsTable" id="showlists">						
						<tr class="tableHeader">
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">Document</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
						</tr>
						<c:forEach var="p" items="${blog.otherBlogFiles}" varStatus="counter">
							<tr>
								<td>${p.bfTitle}</td>
								<td>${p.bfDoc}</td>								
								<td>${p.dateAddedFormatted}</td>
								<td>
									<a class="small" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherBlogFile.html?id=${p.id}&fid=${p.bfDoc}&bid=${p.blogId}'>Delete File</a>
		                    	</td>
							</tr>
						</c:forEach>
					</table>
				
            <div id="inline1" style="width:600px;display: none;">
				<span class="headertitle">Add Other Blog File</span>
					<table width="500px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">
						<tr>
							<td class="subheader" valign="middle" width='125px'>
								Other Blog File Title:
							</td>
							<td>
								<input type="text" class="requiredinput" id="other_blog_title"  name="other_blog_title" style="width:250px;" >
							</td>
						</tr>
						<tr>
							<td class="subheader" valign="middle" width='125px'>
								Other Blog File:
							</td>
							<td>
								<input type="file"  id="other_blog_file" name="other_blog_file"  class="requiredinput">
							</td>
						</tr>				
						<tr>
							<td colspan="2" valign="middle" align="center">
								<input type="button" value="Add File" onclick="sendbloginfo();"/>
								<input type="button" value="Cancel" onclick="closewindow();"/>

							</td>
						</tr>
					</table>
			</div> 
    
  </div><p>
    <div align="center">
					<A HREF='viewBlogs.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
								
					</div>
    <%} %>
    
    </form>
    
    
    
      <script>
    CKEDITOR.replace( 'blog_content' );
    </script>
	
	
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>

</html>