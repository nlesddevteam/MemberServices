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

<esd:SecurityCheck />
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.10.2.js" type="text/javascript"></script>
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
		<script type="text/javascript" src="../js/changepopup.js"></script>	
		<script src="../js/jquery-ui.js"></script>
			
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
				<br/>
				<form id="pol_cat_frm" action="updateNewsPostings.html" method="post" ENCTYPE="multipart/form-data">
				<div class="pageTitleHeader siteHeaders">You Are Now Editing &quot;${newspostings.newsTitle}&quot; Item</div>
                      <div class="pageBody">
				
				 <%if(request.getAttribute("msg") != null){%>                    
                       
                    
                    <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewNewsPostings.html"><img src="../includes/img/addnews-off.png" class="img-swap menuImage" title="Add News"></a> 
                      <a href="viewNewsPostings.html"><img src="../includes/img/viewnews-off.png" class="img-swap menuImage" title="View News Postings"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    
                    
                    <%} else {%>
    
      					<input type="hidden" id="op" name="op" value="CONFIRM">
     					<input type="hidden" value="${newspostings.id}" id="id" name="id">
                      <p>Date:<br/>
                      <input type="text" class="requiredinput" id="news_date" name="news_date"  style="width:250px;"   value="${newspostings.newsDateFormatted}">   
                      <p>Category:<br/>                     
 						<select id="news_category" name="news_category" class="requiredinput">
								<c:forEach var="item" items="${categorylist}">
    								<c:choose>
    									<c:when test="${item.key eq newspostings.newsCategory.value}">
       										<option value="${item.key}" selected="selected">${item.value}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.key}">${item.value}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>
                      
                      <p>Location:<br/> 
 						<select id="news_location" name="news_location" class="requiredinput">
							<c:choose>
    							<c:when test="${newspostings.newsLocation eq null }">
    								<option value="-1" selected="selected">NONE</option>
    							</c:when>
    							<c:otherwise>
    								<option value="-1">NONE</option>
    							</c:otherwise>
    						</c:choose>
							<c:forEach var="item" items="${locationlist}">
    							<c:choose>
    								<c:when test="${item.locationId eq newspostings.newsLocation.locationId}">
       									<option value="${item.locationId}" selected="selected">${item.locationDescription}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.locationId}">${item.locationDescription}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>                        
                      
                      <p>Title:<br/> 
                      <input type="text" class="requiredinput" id="news_title"  name="news_title" style="width:350px;"  value="${newspostings.newsTitle}">
                      <p>Details:<br/> 
                      <textarea id="news_description" name="news_description" rows="10" cols="10">${newspostings.newsDescription}</textarea>
                      <p>Photo: <br/> 
                       <c:if test="${ newspostings.newsPhoto ne null }">	
						<img src="/includes/files/news/img/${newspostings.newsPhoto}" border=0 style="width:250px;"><p>
						</c:if>	                     
                      
                      
                      <input type="file" id="news_photo" name="news_photo"  class="requiredinput">
                      <p>Photo Caption:<br/> 
                      <input type="text" class="requiredinput" id="news_photo_caption"  name="news_photo_caption" style="width:350px;"  value="${newspostings.newsPhotoCaption}">
                      <p>Documentation (PDF): ${newspostings.newsDocumentation}<br/> 
                      
                      <c:if test="${ newspostings.newsDocumentation ne null }">	
                     <a href="/includes/files/news/doc/${newspostings.newsDocumentation}">${newspostings.newsDocumentation}</a><br/>
                     </c:if>
                      
                      <input type="file" id="news_documentation" name="news_documentation"  class="requiredinput">
                      <p>External Link:<br/> 
                      <input type="text" class="requiredinput" id="news_external_link"  name="news_external_link" style="width:250px;"  value="${newspostings.newsExternalLink}">
                      <p>External Link Title:<br/> 
                      <input type="text" class="requiredinput" id="news_external_link_title" name="news_external_link_title"  style="width:250px;"  value="${newspostings.newsExternalLinkTitle}">
                      <p>Status:<br/> 
 						<select id="news_status" name="news_status" class="requiredinput">
								<c:forEach var="item" items="${statuslist}">
    								<c:choose>
    									<c:when test="${item.key eq newspostings.newsStatus.value}">
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
                     <span style='color:#FF0000;font-weight:bold;'>*** <%=(String)request.getAttribute("msg")%> ***</span>
                    <%}%>
                    
                  <p>
                        <br><button id="butSave">Save Changes</button>
                  </div>
                  
                  
                    
                    <p><img src="../includes/img/bar.png" height=1 width=100%>
				 
					<p><div class="pageSectionHeader siteSubHeaders">Other News Postings Files</div>
					
					<div class="pageBody">	
					
					Other attachments such as documentation, forms, or presentations to add to this news item.<p>
					
					<a class="fancybox" href="#inline1" title="Add Other News Postings File" onclick="OpenPopUp('${newspostings.id}');">Add File</a>
					
					<p><table class="newsTable">

									<tr class="tableHeader">
																		
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">Document</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>		
					
					
						
							
						
						<c:forEach var="p" items="${newspostings.otherNewsFiles}" varStatus="counter">
							<tr>
								<td>${p.nfTitle}</td>
								<td>${p.nfDoc}</td>								
								<td>${p.dateAddedFormatted}</td>
								<td>
									<a class="small" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherNewsFile.html?id=${p.id}&fid=${p.nfDoc}&npid=${p.newId}'>Delete File</a>
		                    	</td>
							</tr>
						</c:forEach>
					</table>
				
				
                  <div id="inline1" style="width:600px;display: none;">
				<span class="headertitle">Add Other News Postings File</span>
					<table width="500px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">
						<tr>
							<td class="subheader" valign="middle" width='125px'>
								Other News Postings File Title:
							</td>
							<td>
								<input type="text" class="requiredinput" id="other_news_title"  name="other_news_title" style="width:250px;" >
							</td>
						</tr>
						<tr>
							<td class="subheader" valign="middle" width='125px'>
								Other News Postings File:
							</td>
							<td>
								<input type="file"  id="other_news_file" name="other_news_file"  class="requiredinput">
							</td>
						</tr>				
						<tr>
							<td colspan="2" valign="middle" align="center">
								<input type="button" value="Add File" onclick="sendnewsinfo();"/>
								<input type="button" value="Cancel" onclick="closewindow();"/>

							</td>
						</tr>
					</table>
			</div>  
			
			<div align="center">
					<A HREF='viewNewsPostings.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
								
					</div>
			
			<%} %>       
    </form>
    
    
    </div>
    
    
    
      <script>
    CKEDITOR.replace( 'news_description' );
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