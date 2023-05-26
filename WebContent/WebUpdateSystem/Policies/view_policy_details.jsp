<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               


<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>Web Policy Posting System</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		aTable = $(".policyTable").dataTable({
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

	$('#policyModal').modal('toggle');
						

	}
	function checkfields()
	{
		isvalid=true;
		var ptitle = $.trim($('#other_policy_title').val());
		var pfile = $.trim($('#other_policy_file').val());
		if(ptitle == "")
			{
			alert("Please enter value for Policy Title");
			isvalid=false;
			}
		if(pfile == "")
		{
			alert("Please select Policy file");
			isvalid=false;
		}
		
		if(!pfile.match(/pdf$/i))
		{
			alert("Only PDF files can be uploaded");
			isvalid=false;
		}
			
		
		return isvalid;
		
		
		
	}
    function closewindow()
    {
    	//blank fields
    	$.fancybox.close(); 
    }
	
    function sendinfo()
    {
    	
    	var test=checkfields();
    	
    	if(ajaxSendInfo())
    	{
    		$('#policyModal').modal('toggle');
    	}
    }
    function ajaxSendInfo()
    {
    	var isvalid=false;
		var ptitle = $.trim($('#other_policy_title').val());
		var pfile = $('#other_policy_file');
		var pid = $.trim($('#id').val());
		//var ufile= pfile.files[0];
		//var ufile = $('#other_policy_title').prop('files');
		var ufile = $('#other_policy_file')[0].files[0];
		//poltitle: ptitle,polid:pid,polfile: ufile 
		var requestd = new FormData();
		requestd.append('polid',pid);
		requestd.append('poltitle',ptitle);
		requestd.append('polfile',ufile);
		//mimeType:"multipart/form-data",
		
        $.ajax({
            url: "addOtherPolicyFile.html",
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
									
									newrow ="<tr id='" + $(this).find("ID").text() + "'>";
 									newrow += "<td>" + $(this).find("PFTITLE").text() + "</td>";									
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";									
									newrow += "<td><a class='btn btn-xs btn-warning' href='/includes/files/policies/doc/${p.pfDoc}' target='_blank'>VIEW</a><a class='btn btn-xs btn-danger' onclick='return confirm(\"Are you sure you want to DELETE this document?\");' href='deleteOtherPolicyDocument.html?id=${p.id}&fid=${p.pfDoc}&pid=${p.policyId}'>DEL</a></td>";
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
    function cleartable()
    {
    	//$('#showlists td').parent().remove();
    	//$("#showlists").remove("tr:gt(0)");
    	$("#showlists").find("tr:gt(0)").remove();
    }
	
	
		
		</script>

  
	</head>

  <body>
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Policy Details</div>
 
  				
 <form id="pol_cat_frm" action="updatePolicy.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
				
                       
   
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <input type="hidden" value="${policy.id}" id="id" name="id">
      
       <div class="row">
   					<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                  	<b>Policy Category:</b><br/>
                     
                     <select id="policy_category" name="policy_category" required class="form-control">
							<c:forEach var="item" items="${categorylist}">
								<c:choose>
									<c:when test="${item.key eq policy.policyCategory.value}">
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
                   <b>Policy Status:</b><br/>
                            <select id="policy_status" name="policy_status" required class="form-control">
								<c:forEach var="item" items="${statuslist}">
    								<c:choose>
    									<c:when test="${item.key eq policy.policyStatus.value}">
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
      				<b>Number:</b> (Enter number. If GOV-100, enter 100)<br/>
                      <input type="text" required class="form-control" id="policy_number"  name="policy_number" value="${policy.policyNumber}">
                  </div>
                  </div>
                  <br/><br/>
                  <div class="row">
   				  <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <b>Title:</b><br/>
                      <input type="text" required class="form-control" id="policy_title" name="policy_title" value="${policy.policyTitle}">
					</div>
	 				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <b>Documentation:</b><br/>
                    <input type="file" id="policy_documentation" name="policy_documentation"  class="form-control" accept=".pdf">
                    <c:if test="${ policy.policyDocumentation ne null }">	
                     <b>Current File:</b> <a target="_blank" href="/includes/files/policies/doc/${policy.policyDocumentation}">${policy.policyDocumentation}</a><br/>
                     </c:if>                    
                    </div>
	 				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <b>Procedures/Regs:</b><br/>
                    <input type="file" id="policy_admin_doc" name="policy_admin_doc"  class="form-control" accept=".pdf">
                    <c:if test="${ policy.policyAdminDoc ne null }">	
                     <b>Current File:</b> <a target="_blank" href="/includes/files/policies/doc/${policy.policyAdminDoc}">${policy.policyAdminDoc}</a><br/>
                     </c:if>  
                    </div>
                    </div>
                    

         
   <hr>                                  
<div class="siteSubHeaderGreen">Other Policy Files</div>				 

Other attachments such as documentation, forms, or presentations to add to this policy item.<p>
					
					
					<p>
					<table class="policyTable table table-sm responsive" id="showlists" style="font-size:11px;width:100%;">
					<thead class="thead-dark">
					<tr class="tableHeader">
						<th width="50%">Title</th>							
						<th width="10%">Added On</th>
						<th width="30%">Added By</th>
						<th width="10%">Options</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="p" items="${otherfiles}" varStatus="counter">
						<tr>
						<td>${p.pfTitle}</td>											
						<td>${p.dateAddedFormatted}</td>
						<td style="text-transform:Capitalize;">${p.addedBy}</td>
						<td>
						<a class="btn btn-xs btn-warning" href="/includes/files/policies/doc/${p.pfDoc}" target="_blank">VIEW</a>
						<a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherPolicyDocument.html?id=${p.id}&fid=${p.pfDoc}&pid=${p.policyId}'>DEL</a>
		                </td>
						</tr>
						
					</c:forEach>
					</tbody>
					</table>
				
			
<!-- MODAL -->				
      <div class="modal" id="policyModal">
      <div class="modal-dialog">
      <div class="modal-content">
			<div class="modal-header">
	        <h4 class="modal-title">Add Other Policy File</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      	</div>			
			<div class="modal-body">
			Title:
			<input type="text"  id="other_policy_title"  name="other_policy_title" class="form-control" >
			File:
			<input type="file"  id="other_policy_file" name="other_policy_file" class="form-control" accept=".pdf">
			</div>
			 <div class="modal-footer">	
					<input type="button" class="btn btn-sm btn-primary" value="Add File" onclick="sendinfo();"/>
					 <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
			</div>
					
		</div> 
	</div></div>
	
	
	
	
	<div align="center">
		<button id="butSave" class="btn btn-sm btn-success">Save Changes</button> &nbsp;  
		<a class="btn btn-sm btn-primary" href="#" title="Add Other Policy File" onclick="OpenPopUp('${policy.id}');">Add Other Policy File</a> &nbsp;  
		<A HREF='viewPolicies.html' class="btn btn-sm btn-danger">Cancel</a>
	</div>
	
	
	
    </form>
  
  
    </div>
    </div>
    </div>
    
  </body>

</html>