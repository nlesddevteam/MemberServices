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
	 $(document).ready(function () {		 
		  
		    $( "#mm_date" ).datepicker({
		      changeMonth: true,
		      changeYear: true, 
		      dateFormat: "dd/mm/yy"
		    });
		  
		  
		 aTable = $(".minuteTable").dataTable({
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
	$('#minuteModal').modal('toggle');
						
}

function sendmminfo()
{
	var test=checkmmfields();
	
	if(ajaxSendMMInfo())
	{
		$.fancybox.close();
	}
}
function checkmmfields()
{
	isvalid=true;
	var btitle = $.trim($('#other_mm_title').val());
	var bfile = $.trim($('#other_mm_file').val());
	if(btitle == "")
		{
		alert("Please enter value for Meeting Minutes Title");
		isvalid=false;
		}
	if(bfile == "")
	{
		alert("Please select Meeting Minutes file");
		isvalid=false;
	}
	return isvalid;
}
function ajaxSendMMInfo()
{
	var isvalid=false;
	var btitle = $.trim($('#other_mm_title').val());
	var bfile = $('#other_mm_file');
	var bid = $.trim($('#id').val());
	var ufile = $('#other_mm_file')[0].files[0];
	var requestd = new FormData();
	requestd.append('mmid',bid);
	requestd.append('mmtitle',btitle);
	requestd.append('mmfile',ufile);
	//mimeType:"multipart/form-data",
	$.ajax({
        url: "addOtherMMFile.html",
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
								
								newrow += "<td>" + $(this).find("MMFTITLE").text() + "</td>";
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
<div class="siteHeaderGreen">Editing Minute Details</div>
  
  	
	<form id="pol_cat_frm" action="updateMeetingMinutesDetails.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
	  <input type="hidden" id="op" name="op" value="CONFIRM">
      <input type="hidden" value="${meetingminutes.id}" id="id" name="id">
                      
      <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">                
                     <b>Title:</b><br/>
                      <input type="text" id="mm_title"  name="mm_title" value="${meetingminutes.mMTitle}" required class="form-control">
                 </div>
                  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">    
                      <b>Category:</b><br/>
 						<select id="meeting_category" name="meeting_category" required class="form-control">
							<c:forEach var="item" items="${categorylist}">
								<c:choose>
									<c:when test="${item.key eq meetingminutes.meetingCategory.value}">
 										<option value="${item.key}" selected="selected">${item.value}</option>
									</c:when>
									<c:otherwise>
  										<option value="${item.key}">${item.value}</option>
									</c:otherwise>
									</c:choose>
							</c:forEach>
                  		</select>
                 </div>
                 <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                      <b>Held On Date:</b><br/>
                      <input type="text" id="mm_date" name="mm_date" value="${meetingminutes.mMDateFormatted}" required class="form-control">
                 </div>
                 </div>
                  <br/><br/>
                <div class="row">
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">      
                     <b>Minutes Doc:</b><br/>  
                     <input type="file" id="mm_doc" name="mm_doc" class="form-control" accept=".pdf">                   
                      <c:if test="${meetingminutes.mMDoc ne null }">	
                     <br/><b>Current File:</b><a href="/includes/files/minutes/doc/${meetingminutes.mMDoc}">${meetingminutes.mMDoc}</a><br/>
                     </c:if>   
                  </div>    
                  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">    
                     <b>Presentation Title (if any):</b><br/>
                     <input type="text" id="mm_rel_pre_title" name="mm_rel_pre_title" value="${meetingminutes.mMRelPreTitle}" class="form-control">
                   </div>   
                  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">    
                     <b>Presentation File (PDF):</b><br/>
                     <input type="file" id="mm_rel_pre_doc" name="mm_rel_pre_doc" class="form-control" accept=".pdf">
                     <c:if test="${meetingminutes.mMRelPreDoc ne null }">	
                     <br/><b>Current File:</b> <a href="/includes/files/minutes/doc/${meetingminutes.mMRelPreDoc}">${meetingminutes.mMRelPreDoc}</a>
                     </c:if> 
                   </div>
                    </div>
                   <br/><br/>
                <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">   
                      <b>Meeting (YouTube) Video:</b><br/>
                      <input type="text" id="mm_meeting_video" name="mm_meeting_video" value="${meetingminutes.mMMeetingVideo}" class="form-control" placeholder="https://">
				   </div>
      			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4"> 
                     <b>Related Documentation Title:</b><br/>
                     <input type="text" id="mm_rel_doc_title" name="mm_rel_doc_title" value="${meetingminutes.mMRelDocTitle}" class="form-control">
                 </div>    
                  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">     
                     <b>Related Doc File:</b><br/>
                     <input type="file" id="mm_rel_doc" name="mm_rel_doc" class="form-control" accept=".pdf">
                     <c:if test="${meetingminutes.mMRelDoc ne null }">	
                     <br/><b>Current File:</b> <a href="/includes/files/minutes/doc/${meetingminutes.mMRelDoc}">${meetingminutes.mMRelDoc}</a>
                     </c:if>                     
                   </div>    
                  
				   </div>
 
 
             <hr>       
                  
                    
<div class="siteSubHeaderGreen"> Other Minute Files</div>
             
					
Other attachments such as documentation, forms, or presentations to add to this Minute item.


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
						<c:forEach var="p" items="${meetingminutes.otherMMFiles}" varStatus="counter">
							<tr>
								<td>${p.MMfTitle}</td>
								<td>${p.dateAddedFormatted}</td>
								<td style="text-transform:Capitalize;">${p.addedBy}</td>	
								<td>
								<a class="btn btn-xs btn-warning" href="/includes/files/minutes/doc/${p.MMfDoc}" target="_blank">VIEW</a>
								<a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherMMFile.html?id=${p.id}&fid=${p.MMfDoc}&mmid=${p.MMId}'>DEL</a>
		                    	</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				
				
				
                  <!-- MODAL -->				
      <div class="modal" id="minuteModal">
      <div class="modal-dialog">
      <div class="modal-content">
			<div class="modal-header">
	        <h4 class="modal-title">Add Other Minutes File</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      	</div>			
			<div class="modal-body">
			<b>Title:</b><br/>
			<input type="text"  id="other_mm_title"  name="other_mm_title" class="form-control" >
			<b>File: (PDF)</b><br/>
			<input type="file"  id="other_mm_file" name="other_mm_file" class="form-control" accept=".pdf">
			</div>
			 <div class="modal-footer">	
					<input type="button" class="btn btn-sm btn-primary" value="Add File" onclick="sendmminfo();"/>
					<button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
			</div>
					
		</div> 
	</div></div>
	<br/><br/>
    				<div align="center">
					<button class="btn btn-sm btn-success" id="butSave">SAVE CHANGES</button> &nbsp;
					<a class="btn btn-sm btn-primary" href="#" title="Add Other Meeting Minutes File" onclick="OpenPopUp('${meetingminutes.id}');">ADD FILE</a> &nbsp;
					<A class="btn btn-sm btn-danger" HREF='viewMeetingMinutes.html'>CANCEL</a>
								
					</div>
   			
			
			  
    </form>


 </div>
 </div>
 </div>
    
	
  </body>

</html>
    
    