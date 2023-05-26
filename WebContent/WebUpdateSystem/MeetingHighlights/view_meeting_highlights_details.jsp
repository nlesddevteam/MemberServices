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
    <meta charset="utf-8">
    
    
    <script>
	 $(document).ready(function () {		 
		  
		    $( "#mm_date" ).datepicker({
		      changeMonth: true,
		      changeYear: true, 
		      dateFormat: "dd/mm/yy"
		    });
		  
		  
		 aTable = $(".highlightTable").dataTable({
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
	$('#highlightModal').modal('toggle');
						
}
    
    
    function sendmhinfo()
    {
    	var test=checkmhfields();
    	
    	if(ajaxSendMHInfo())
    	{
    		$('#highlightModal').modal('toggle');
    	}
    }
	function checkmhfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_mh_title').val());
		var bfile = $.trim($('#other_mh_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Meeting Highlights Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Meeting Highlights file");
			isvalid=false;
		}
		return isvalid;
	}
    function ajaxSendMHInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_mh_title').val());
		var bfile = $('#other_mh_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_mh_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('mhid',bid);
		requestd.append('mhtitle',btitle);
		requestd.append('mhfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherMHFile.html",
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
									
									newrow += "<td>" + $(this).find("MHFTITLE").text() + "</td>";		
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
<div class="siteHeaderGreen">Editing Highlight Details</div>
  
<form id="pol_cat_frm" action="updateMeetingHighlightsDetails.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
				
	 <input type="hidden" id="op" name="op" value="CONFIRM">
     <input type="hidden" value="${meetinghighlights.id}" id="id" name="id">
                     
                 <div class="row">
 					 <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4"> 
                     <b>Title:</b><br/>
                     <input type="text" id="mh_title"  name="mh_title" value="${meetinghighlights.mHTitle}" required class="form-control">
                 </div>
                 <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                 <b>Date:</b><br/>
                     <input type="text"  id="mh_date" name="mh_date" value="${meetinghighlights.mHDateFormatted}" required class="form-control">
                 </div>
                 <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">   
                     <b>Highlight Document (PDF):</b><br/>
                     <input type="file" id="mh_doc" name="mh_doc" class="form-control" accept=".pdf">
                     <c:if test="${meetinghighlights.mHDoc ne null }">	
                   	<br/><b>Current File:</b> <a href="/includes/files/highlights/doc/${meetinghighlights.mHDoc}" target="_blank">${meetinghighlights.mHDoc}</a><br/>
                     </c:if> 
                     
                     
                 </div>
               	 </div>
               	 
               	 <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">  
               	      
                     <b>Presentation Title:</b><br/>
                     <input type="text" id="mh_rel_pre_title" name="mh_rel_pre_title" value="${meetinghighlights.mHRelPreTitle}" class="form-control">
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">     
                     <b>Presentation File:</b><br/>      
                     <input type="file"  id="mh_rel_pre_doc" name="mh_rel_pre_doc"  accept=".pdf" class="form-control">               
	                     <c:if test="${meetinghighlights.mHRelPreDoc ne null }">	
	                   		<br/><b>Current File:</b> <a href="/includes/files/highlights/doc/${meetinghighlights.mHRelPreDoc}" target="_blank">${meetinghighlights.mHRelPreDoc}</a><br/>
	                     </c:if> 
                </div>
                </div>
                <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">     
                     
                     <b>Related Document Title:</b><br/> 
                     <input type="text"  id="mh_rel_doc_title" name="mh_rel_doc_title" value="${meetinghighlights.mHRelDocTitle}" class="form-control">
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">     
                     <b>Related Document File:</b><br/>
                     <input type="file" id="mh_rel_doc" name="mh_rel_doc"  accept=".pdf" class="form-control"> 
	                     <c:if test="${meetinghighlights.mHRelDoc ne null }">	
	                   		<br/><b>Current File:</b> <a href="/includes/files/highlights/doc/${meetinghighlights.mHRelDoc}" target="_blank">${meetinghighlights.mHRelDoc}</a><br/>
	                     </c:if> 
                  </div>
                  </div>   
                  <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">                                        
                      <b>Meeting (YouTube) Video:</b> <br/>
                     <input type="text" id="mh_meeting_video"  name="mh_meeting_video" value="${meetinghighlights.mHMeetingVideo}" class="form-control" placeholder="https://">
                 </div>
                 </div>   
                   
                    
                  
    <hr>               
                
				 
<div class="siteSubHeaderGreen"> OtherOther Highlight Files</div>
					
				
				
					
					Other attachments such as documentation, forms, or presentations to add to this Highlight item. Make sure you SAVE CHANGES after you add or update anything.<p>
                   
                   
                   
                   
              
				<br/><br/>
					<table class="minutesTable table table-sm responsive" id="showlists" style="font-size:11px;width:100%;">
					<thead class="thead-dark">
					<tr class="tableHeader">
						<th width="50%">Title</th>							
						<th width="10%">Added On</th>
						<th width="30%">Added By</th>
						<th width="10%">Options</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${meetinghighlights.otherMHFiles}" varStatus="counter">
							<tr>
								<td>${p.MHfTitle}</td>
								<td>${p.dateAddedFormatted}</td>
								<td style="text-transform:Capitalize;">${p.addedBy}</td>	
								<td>
								<a class="btn btn-xs btn-warning" href="/includes/files/highlights/doc/${p.MHfDoc}" target="_blank">VIEW</a>
								<a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherMHFile.html?id=${p.id}&fid=${p.MHfDoc}&mhid=${p.MHId}'>DEL</a>
		                    	</td>
							</tr>
						</c:forEach>
					</table>
				
               


 <!-- MODAL -->				
      <div class="modal" id="highlightModal">
      <div class="modal-dialog">
      <div class="modal-content">
			<div class="modal-header">
	        <h4 class="modal-title">Add Other Highlight File</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      	</div>			
			<div class="modal-body">
			<b>Title:</b><br/>
			<input type="text"  id="other_mh_title"  name="other_mh_title" class="form-control" >
			<b>File: (PDF)</b><br/>
			<input type="file"  id="other_mh_file" name="other_mh_file" class="form-control" accept=".pdf">
			</div>
			 <div class="modal-footer">	
					<input type="button" class="btn btn-sm btn-primary" value="Add File" onclick="sendmhinfo();"/>
					<button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
			</div>
					
		</div> 
	</div></div>
	<br/><br/>

     
    				<div align="center">					
					 <button class="btn btn-sm btn-success" id="butSave" onclick="loadingData();">SAVE CHANGES</button> &nbsp; 
					 <a class="btn btn-sm btn-primary" href="#inline1" title="Add Other Meeting Highlights File" onclick="OpenPopUp('${meetinghighlights.id}');">ADD FILE</a> &nbsp;
					 <A class="btn btn-sm btn-danger" HREF='viewMeetingHighlights.html' onclick="loadingData();">CANCEL</a>
                   </div>
  
		
			
 </form>

</div>
</div>
</div>

	
  </body>

</html>			
			
