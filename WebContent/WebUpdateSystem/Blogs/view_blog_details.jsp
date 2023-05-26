<%@ page language="java" session="true" isThreadSafe="false" import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%> 
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%  User usr = (User) session.getAttribute("usr"); %>
<html>

	<head>
		<title>BLOG Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
   
	
	
	<script>
	 $(document).ready(function () {		 
		  
		    $( "#blog_date" ).datepicker({
		      changeMonth: true,
		      changeYear: true, 
		      dateFormat: "dd/mm/yy"
		    });
		  
		  
		 aTable = $(".blogTable").dataTable({
			"order" : [[ 1, "asc" ]],			
			  "paging":   false,
			  "searching": false,			 
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
	$('#blogModal').modal('toggle');
						
}
	
	function checkblogfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_blog_title').val());
		var bfile = $.trim($('#other_blog_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Blog Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Blog file");
			isvalid=false;
		}
		return isvalid;
	}
    function sendbloginfo()
    {
    	var test=checkblogfields();
    	
    	if(ajaxSendBlogInfo())
    	{
    		$('#blogModal').modal('toggle');
    	}
    }
    function ajaxSendBlogInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_blog_title').val());
		var bfile = $('#other_blog_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_blog_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('blogid',bid);
		requestd.append('blogtitle',btitle);
		requestd.append('blogfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherBlogFile.html",
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
									
									newrow += "<td>" + $(this).find("BFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("BFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
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
<div class="siteHeaderGreen">Edit Blog Details</div>
  						
				<form id="pol_cat_frm" action="updateBlogDetails.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
				<input type="hidden" id="op" name="op" value="CONFIRM">
				<input type="hidden" value="${blog.id}" id="id" name="id">                     
                 
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">      
                     <b>Title:</b><br/>
                     <input type="text" id="blog_title" name="blog_title" value="${blog.blogTitle}" required class="form-control" maxlength="60">
                </div> 
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                     <b>Date:</b><br/>
                     <input type="text" id="blog_date" name="blog_date" value="${blog.blogDateFormatted}" required class="form-control">
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">    
                    <b>Status:</b><br/>
                      <select id="blog_status" name="blog_status" required class="form-control">
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
                 </div>
                 </div>   
                 <br/><br/>
                 <div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                   
                      <b>Content:</b><br/>
                     <textarea id="blog_content" name="blog_content" maxlength="2000" required class="form-control">${blog.blogContent}</textarea>
                 
                 </div>
                 </div>
                 <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                     <b>Photo:</b><br/>
                     <c:if test="${ blog.blogPhoto ne null }">	
										<img src="/includes/files/blog/img/${blog.blogPhoto}" border=0 style="width:250px;" /><p>
										</c:if>	
                     <input type="file" id="blog_photo" name="blog_photo" accept=".jpg,.png" class="form-control">
                </div>     
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                     <b>Photo Caption:</b><br/>
                     <input type="text" id="blog_photo_caption"  name="blog_photo_caption" value="${blog.blogPhotoCaption}" maxlength="60" class="form-control">
                
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                     <b>Document (PDF):</b><br/>
                     <input type="file" id="blog_document" name="blog_document" accept=".pdf" class="form-control">
                     <c:if test="${ blog.blogDocument ne null }">	
                     	<br/><b>Current File:</b> <a href="/includes/files/blog/doc/${blog.blogDocument}" target="_blank">${blog.blogDocument}</a><br/>
                     </c:if>
                </div>
                </div>      
                     
                      
                     
                     
                     
   <hr>             
				 
<div class="siteSubHeaderGreen"> Other BLOG Files</div>
             
					
Other attachments such as documentation, forms, or presentations to add to this BLOG item.
					
							   
   
<br/><br/>                  
                     
					<table class="blogTable table table-sm responsive" id="showlists" style="font-size:11px;width:100%;">
					<thead class="thead-dark">
					<tr class="tableHeader">
						<th width="50%">Title</th>							
						<th width="10%">Added On</th>
						<th width="30%">Added By</th>
						<th width="10%">Options</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${blog.otherBlogFiles}" varStatus="counter">
							<tr>
								<td>${p.bfTitle}</td>
								<td>${p.dateAddedFormatted}</td>
								<td style="text-transform:Capitalize;">${p.addedBy}</td>	
								<td><a class="btn btn-xs btn-warning" href="/includes/files/blog/doc/${p.bfDoc}" target="_blank">VIEW</a> &nbsp; 
									<a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherBlogFile.html?id=${p.id}&fid=${p.bfDoc}&bid=${p.blogId}'>DEL</a>
		                    	</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				
<!-- MODAL -->				
      <div class="modal" id="blogModal">
      <div class="modal-dialog">
      <div class="modal-content">
			<div class="modal-header">
	        <h4 class="modal-title">Add Other BLOG File</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      	</div>			
			<div class="modal-body">
			<b>Title:</b><br/>
			<input type="text"  id="other_blog_title"  name="other_blog_title" class="form-control" maxlength="60">
			<b>File: (PDF)</b><br/>
			<input type="file"  id="other_blog_file" name="other_blogm_file" class="form-control" accept=".pdf">
			</div>
			 <div class="modal-footer">	
					<input type="button" class="btn btn-sm btn-primary" value="Add File" onclick="sendbloginfo();"/>
					<button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
			</div>
					
		</div> 
	</div></div>
	<br/><br/>
			
    
    
    
    				<div align="center">				
					<button class="btn btn-sm btn-success" id="butSave" onclick="loadingData();">SAVE CHANGES</button> &nbsp;
					<a class="btn btn-sm btn-primary" href="#" title="Add Other Blog File" onclick="OpenPopUp('${blog.id}');">ADD FILE</a> &nbsp;
					<A class="btn btn-sm btn-danger" HREF='viewBlogs.html' onclick="loadingData();">CANCEL</a>
					</div>
					
					
   
    
    </form>
    
    
    
<script>
CKEDITOR.replace( 'blog_content' );
</script>
</div>
</div>
</div>	
	
  </body>

</html>