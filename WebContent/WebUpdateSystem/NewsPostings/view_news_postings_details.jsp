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
		<title>News Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
 
 
   <script>
   
   $(document).ready(function () {		 
 		  
 		    $( "#news_date" ).datepicker({
 		      changeMonth: true,
 		      changeYear: true, 
 		      dateFormat: "dd/mm/yy"
 		    });
 		  
 		  
 		 aTable = $(".newsTable").dataTable({
 			"order" : [[ 1, "asc" ]],			
 			  "paging":   false,
 			  "searching": true,			 
 				responsive: true,				
 				"columnDefs": [
 					 {
 			           "targets": [0,1,2],			               
 			                "sortable": true,
 			                "visible": true,
 			            },
 			        ]
 		});
 		  
 		  
	 });

   function OpenPopUp(pid)
	{
	$('#newsModal').modal('toggle');
						
}
   
   function sendnewsinfo()
   {
   	var test=checknewsfields();
   	
   	if(ajaxSendNewsInfo())
   	{
   		$.fancybox.close();
   	}
   }
	function checknewsfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_news_title').val());
		var bfile = $.trim($('#other_news_file').val());
		if(btitle == "")
			{
			alert("Please enter value for News Postings Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select News Postings file");
			isvalid=false;
		}
		return isvalid;
	}
   function ajaxSendNewsInfo()
   {
   	var isvalid=false;
		var btitle = $.trim($('#other_news_title').val());
		var bfile = $('#other_news_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_news_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('npid',bid);
		requestd.append('nptitle',btitle);
		requestd.append('npfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
           url: "addOtherNewsFile.html",
           type: 'POST',
           data:  requestd,
           contentType: false,
           cache: false,
           processData:false,
           success: function(xml)
           {
           	
					var i=1;
					cleartable();
					$(xml).find('FILES').each(function(){
							
							if($(this).find("MESSAGE").text() == "SUCCESS")
								{
									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
									}else{
										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
									}
									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("NPFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";									
									newrow += "<td></td>";									
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
								}else{
									alert($(this).find("MESSAGE").text()+ "1");
									
								}
						});
           },
           error: function(jqXHR, textStatus, errorThrown) 
           {
           		alert("error");
           },
				dataType: "text",
				async: false
      });

   	return isvalid;
   }
   
   
   </script>
 	
		
	
   
	
	</head>

  <body>
    <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">News Details for &quot;${newspostings.newsTitle}&quot;</div>
  
  
  
  
				<form id="pol_cat_frm" action="updateNewsPostings.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
				<input type="hidden" id="op" name="op" value="CONFIRM">
     			<input type="hidden" value="${newspostings.id}" id="id" name="id">
     			<div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
     				<b>Date:</b><br/>
                      <input type="text" id="news_date" name="news_date" value="${newspostings.newsDateFormatted}" required class="form-control">   
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">      
                      <b>Category:</b><br/>                     
 						<select id="news_category" name="news_category" class="form-control" required>
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
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">                      
                      <b>Location:</b><br/> 
 						<select id="news_location" name="news_location" class="form-control" required>
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
                </div>        
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">                               
                       <b>Status:</b><br/> 
 						<select id="news_status" name="news_status" class="form-control" required>
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
                </div>
                </div> 
                <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">       
                      <b>Title: (Max 60 characters)</b><br/> 
                      <input type="text" class="form-control" id="news_title" required name="news_title" maxlength="60" value="${newspostings.newsTitle}">
                </div>
                </div>
                 <br/><br/>
                  <div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">      
                      <b>Details:</b><br/> 
                      <textarea id="news_description" name="news_description" class="form-control" required>${newspostings.newsDescription}</textarea>
                </div>
                </div>
                <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        
                      <b>Photo:</b> <br/> 
                      <input type="file" id="news_photo" name="news_photo" class="form-control" accept=".jpg,.png">
                       <c:if test="${ newspostings.newsPhoto ne null }">	
						<img src="/includes/files/news/img/${newspostings.newsPhoto}" border=0 style="width:100%;max-width:600px;" class="thumbnail">
						</c:if>	                     
                       
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                     
                      <b>Photo Caption:</b><br/> 
                      <input type="text" class="form-control" id="news_photo_caption"  name="news_photo_caption" value="${newspostings.newsPhotoCaption}">
                </div>
                </div>
                <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">       
                      <b>Documentation (PDF):</b><br/> 
                      <input type="file" id="news_documentation" name="news_documentation" class="form-control" accept=".pdf">
                      <c:if test="${ newspostings.newsDocumentation ne null }">	
                     	<b>Current File: </b><a href="/includes/files/news/doc/${newspostings.newsDocumentation}">${newspostings.newsDocumentation}</a>
                     </c:if>
                </div>
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">  
                      <b>External Link:</b><br/> 
                      <input type="text" class="form-control" id="news_external_link" name="news_external_link" value="${newspostings.newsExternalLink}">
                 </div>     
                 <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                      <b>External Link Title:</b><br/> 
                      <input type="text" class="form-control" id="news_external_link_title" name="news_external_link_title" value="${newspostings.newsExternalLinkTitle}">
                </div>                            
                </div>
                   
                  
                  
                    
                  <hr>
				 
<div class="siteSubHeaderGreen">Other Related Files to this Story</div>
					
				Other attachments such as documentation, forms, or presentations to add to this news item.
		
					
					
					<p>
					<table class="newsTable table table-sm responsive" style="font-size:11px;width:100%;">
					<thead class="thead-dark">
					<tr class="tableHeader">
						<th width="50%">Title</th>							
						<th width="10%">Added On</th>
						<th width="30%">Added By</th>
						<th width="10%">Options</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${newspostings.otherNewsFiles}" varStatus="counter">
							<tr>
								<td>${p.nfTitle}</td>
								<td>${p.dateAddedFormatted}</td>
								<td style="text-transform:Capitalize;">${p.addedBy}</td>								
								<td>
								<a class="btn btn-xs btn-warning" href="/includes/files/news/doc/${p.nfDoc}" target="_blank">VIEW</a>
								<a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherNewsFile.html?id=${p.id}&fid=${p.nfDoc}&npid=${p.newId}'>DEL</a>
		                    	</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
				
	
	
<!-- MODAL -->				
      <div class="modal" id="newsModal">
      <div class="modal-dialog">
      <div class="modal-content">
			<div class="modal-header">
	        <h4 class="modal-title">Add Other News File</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      	</div>			
			<div class="modal-body">
			Title:
			<input type="text"  id="other_news_title"  name="other_news_title" class="form-control" >
			File:
			<input type="file"  id="other_news_file" name="other_news_file" class="form-control" accept=".pdf">
			</div>
			 <div class="modal-footer">	
					<input type="button" class="btn btn-sm btn-primary" value="Add File" onclick="sendnewsinfo();"/>
					 <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
			</div>
					
		</div> 
	</div></div>	
	
 
			
			<div align="center">
			<button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">SAVE CHANGES</button> &nbsp;
			<a class="btn btn-sm btn-primary" href="#" title="Add Other News Postings File" onclick="OpenPopUp('${newspostings.id}');">ADD FILE</a> &nbsp;
			<A class="btn btn-sm btn-danger" HREF='viewNewsPostings.html' onclick="loadingData();">CANCEL</a></div>
			     
    </form>
    

    
    
    
      <script>
    CKEDITOR.replace( 'news_description' );
    </script>
	
	</div>
	</div>
	</div>
		
  </body>

</html>