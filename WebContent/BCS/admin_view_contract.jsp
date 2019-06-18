<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<c:set var="countDocs" value="0" />
<c:set var="countRoutes" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>

<%
String tabs = (String)request.getParameter("tab");
String settab="";
if(tabs != null){
	settab="D";
}
pageContext.setAttribute("settab1", settab);
String zonename="";
if(!(request.getAttribute("czone") == null)){
	zonename=(String)request.getAttribute("czone");
}
pageContext.setAttribute("czonetext", zonename);
%>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    		    //$("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
    		    //$("#claims-table tr:odd").css("background-color", "#E3F1E6");
    		    var date_inputdle=$('#contractexpirydate');
    		    var date_inputdle2=$('#contractstartdate');
    		    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      	      	var options={
      	        	format: 'mm/dd/yyyy',
      	        	container: container,
      	        	todayHighlight: true,
      	        	autoclose: true,
      	      	};
				date_inputdle.datepicker(options);
				date_inputdle2.datepicker(options);
				if($("#settab1").val() == "D"){
					$('.nav-tabs a:last').tab('show'); 
      	      	}
				if(!($("#hidczone").val() == "")){
					$("#contractregion option:contains(" + $("#hidczone").val() + ")").attr('selected', 'selected');
					$('#contractregion').attr("disabled", true);
	      	    }
    		    $('#contact-form-up').bootstrapValidator({
    		        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
    		        feedbackIcons: {
    		            valid: 'glyphicon glyphicon-ok',
    		            invalid: 'glyphicon glyphicon-remove',
    		            validating: 'glyphicon glyphicon-refresh'
    		        },submitHandler: function(validator, form, submitButton) {
    		        	//$('#success_message').slideDown({ opacity: "show" }, "slow"); // Do something ...
    		          			$('#contact-form-up').data('bootstrapValidator').resetForm();
    		        			var frm = $('#contact-form-up');
    		          			$.ajax({
    		          		        type: "POST",
    		          		        url: "adminAddNewContract.html",
    		          		        data: frm.serialize(),
    		          		        success: function (xml) {
    		          		        	$(xml).find('CONTRACTOR').each(function(){
    		          							//now add the items if any
    		          							if($(this).find("MESSAGE").text() == "SUCCESS")
    		          			 					{
    		          									$('#body_success_message_bottom').text("Contract Info Updated").css("display","block").delay(4000).fadeOut();
    		          									var surl = "adminViewContract.html?vid=" + $(this).find("ID").text();
    		          									// $("#pageContentBody").load(surl);
    		          									loadMainDivPage(surl);
    		          									
    		          			 					}else{
    		          			 						$('#body_error_message_bottom').text($(this).find("MESSAGE").text()).css("display","block").delay(4000).fadeOut;
    		          			 						
    		          			 					}

    		          							});
    		          		        },
    		          		        error: function (xml) {
    		          		            isvalid=false;
    		          		        },
    		          		    });
    		        },
    		        fields: {
    		            contractname: {
    		                validators: {
    		                        stringLength: {
    		                        min: 5,
    		                    },
    		                        notEmpty: {
    		                        message: 'Please supply contract name'
    		                    }
    		                }
    		            },
    		             contracttype: {
    		                validators: {
    		                     notEmpty: {
    		                        message: 'Please select contract type'
    		                    }
    		                }
    		            },
    		         	contractregion: {
    		                validators: {
    		                     
    		                    notEmpty: {
    		                        message: 'Please supply contract region'
    		                    }
    		                }
    		            } 		        
    		          }
    		       });
    		    
});
		</script>
		
		<script>
   			$(document).ready(function () {
    		$('.menuBCSD').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script> 
		<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script> 
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		<div class="BCSHeaderText">Viewing Contract <span style="font-weight:bold;text-transform:capitalize;">${contract.contractName}</span></div>
		Below you will find the details on the <span style="text-transform:capitalize;">${contract.contractName}</span> contract. 
		<div style="max-width:100%;padding-top:10px;font-weight:normal;">	
			<ul class="nav nav-tabs">
    			<li class="active"><a data-toggle="tab" href="#details">Contract Details</a></li>
    			<li><a data-toggle="tab" href="#documents2">Documents</a></li>
  			</ul>
	  		  <div class="tab-content">
	  			<div id="details" class="tab-pane fade in active">
		  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
		  		  <br />
			  	  	<div class="form-group">
			      		<label class="control-label col-sm-2" for="email">Contract Status:</label>
			      		<div class="col-sm-5">
			      		<c:choose>
			      			<c:when test="${contract.contractHistory.statusString eq 'Awarded' }">
			      			<span style="background-color:green;color:White;padding:3px;">&nbsp;AWARDED&nbsp;</span>
			      				<p class="form-control-static">Contract awarded to <span style="text-transform:capitalize;">${contract.contractHistory.contractorBean.company }</span> on 
			      				${contract.contractHistory.statusDateFormatted }
			      				</p>
			      			</c:when>
			      			<c:when test="${contract.contractHistory.statusString eq 'Suspended' }">
			      			<span style="background-color:black;color:White;padding:3px;">&nbsp;SUSPENDED&nbsp;</span>
			      				<p class="form-control-static">Contract suspended for <span style="text-transform:capitalize;">${contract.contractHistory.contractorBean.company }</span> on 
			      				${contract.contractHistory.statusDateFormatted }
			      				</p>
			      			</c:when>
			      			<c:when test="${contract.contractHistory.statusString eq 'Cancelled' }">
			      			<span style="background-color:Red;color:White;padding:3px;">&nbsp;CANCELLED&nbsp;</span>
			      				<p class="form-control-static">Contract cancelled by <span style="text-transform:capitalize;">${contract.contractHistory.statusBy }</span> on 
			      				${contract.contractHistory.statusDateFormatted }
			      				</p>
			      			</c:when>
			      			<c:otherwise>
			      			<span style="background-color:yellow;color:black;padding:3px;">&nbsp;NOT AWARDED&nbsp;</span>
			      			<p class="form-control-static">Contract not awarded.</p>
			      			</c:otherwise>			      			
			      		</c:choose>
			        	</div>
			    	</div>		  		  
			  	  	<div class="form-group">
			      		<label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Contract Name:</label><input type="hidden" id="cid" name="cid" value="${contract.id}">
			      		<input type="hidden" id="hidczone" name="hidczone" value="${czonetext}">
			      		<div class="col-sm-5">
			        		<input class="form-control" id="contractname" name="contractname" type="text" placeholder="Enter contact name" value="${contract.contractName}">
			      		<input type="hidden" id="settab1" name="settab1" value="${settab1}">
			      		</div>
			    	</div>   	  		  	
				  	<div class="form-group">
				  		<br />
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Contract Type:</label>
		                <div class="col-md-5">
		                <select class="form-control" id="contracttype" name="contracttype"  style="width:auto;">
		                	<option value=" ">Please select type</option>
							<c:forEach var="entry" items="${types}">
								<c:choose>
								<c:when test = "${contract.contractType == entry.key }">
									<option value='${entry.key}' SELECTED>${entry.value}</option>
								</c:when>
								<c:otherwise>
									<option value='${entry.key}'>${entry.value}</option>
								</c:otherwise>
								</c:choose>
							</c:forEach>
				  		</select>
				  		</div>
			      	</div>
	 				<div class="form-group">
				      <label class="control-label col-sm-2" for="email">Contract Notes:</label>
				      	<div class="col-md-5">
		            		<textarea class="form-control" rows="4" id="contractnotes"  name="contractnotes" placeholder="Enter contract notes">${contract.contractNotes}</textarea>
		        		</div>
				    </div>    
				  	<div class="form-group">
				  		<br />
		                <label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/>Contract Region:</label>
		                <div class="col-md-5">
		                <select class="form-control" id="contractregion" name="contractregion"  style="width:auto;">
		                	<option value=" ">Please select region</option>
							<c:forEach var="entry" items="${regions}">
								<c:choose>
								<c:when test = "${contract.contractRegion == entry.key }">
									<option value='${entry.key}' SELECTED>${entry.value}</option>
								</c:when>
								<c:otherwise>
									<option value='${entry.key}'>${entry.value}</option>
								</c:otherwise>
								</c:choose>
							</c:forEach>
				  		</select>
				  		</div>
			      	</div>

		       	   <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Contract Start Date:</label> 
		                <div class="col-sm-5">
		                    <input class="form-control" id="contractstartdate" name="contractstartdate" placeholder="MM/DD/YYYY" type="text" 
		                    value="${contract.contractStartDate == null ? '' : contract.contractStartDateFormatted}">
		                </div>
					</div>	            			      	
		       	   <div class="form-group">
		                <label class="control-label col-sm-2" for="email">Contract Expiry Date:</label> 
		                <div class="col-sm-5">
		                    <input class="form-control" id="contractexpirydate" name="contractexpirydate" placeholder="MM/DD/YYYY" type="text" 
		                    value="${contract.contractExpiryDate == null ? '' : contract.contractExpiryDateFormatted}">
		                </div>
					</div>
					<div class="form-group">
				    		<label class="control-label col-sm-2" for="email">Route(s):</label>
		      				<div class="col-sm-10">

							<p>
				    		<div class="alert alert-danger" id="route_details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			 		<div class="alert alert-success" id="route_details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				 			
				 			<div id="BCS-RoutesA">
				    		
				    		<c:choose>
		      						<c:when test="${fn:length(croutes) > 0}">
				    		
					      			<table id="BCS-table" width="100%" class="BCSTable">
								  <thead>
								    <tr style="border-bottom:1px solid grey;" class="listHeader">
								      <th  width="50%" class="listdata">Route</th>
								      <th width="40%" class="listdata">School</th>
								      <th width="10%" class="listdata">Options</th>
								    </tr>
								  </thead>
								  <tbody>
								  
									<c:forEach var="entry" items="${croutes}">
									<c:set var="countRoutes" value="${countRoutes + 1}" />
										<tr style="border-bottom:1px solid silver;">
											<td class="field_content">${entry.routeName}</td>
											<td class="field_content">${entry.routeSchoolString}</td>
											<td class="field_content">
															<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('adminViewRoute.html?vid=${entry.id}');">View</button>
		      								<button type="button" class="btn btn-xs btn-danger" onclick="openremovecontractroute('${entry.routeName}','${entry.id}','${contract.id}');">Del</button>
		      				</td>
										</tr>
									</c:forEach>
									
									</tbody>
								</table>
								</c:when>
									<c:otherwise><span style="color:Red;">No routes currently listed for this contract.</span></c:otherwise>
									</c:choose>
								
						</div>
						<br/>
								      					<c:choose>
									<c:when test = "${contract.id gt 0 }">
				      					<a href="#" onclick="openaddroute('${contract.id}','${contract.contractName}');" class="btn btn-primary btn-sm">Add Route to Contract</a><br/><br/>
				      				</c:when>
				      			</c:choose>
				    </div>
				    </div>
				    <div class="form-group">        
			      		<div class="col-sm-offset-2 col-sm-10">
			      		<br />
			        	<button type="submit" class="btn btn-xs btn-primary">Update Contract</button>
			        	<c:choose>
			        	<c:when test="${contract.id gt 0 }">
			        		<c:choose>
			      			<c:when test="${contract.contractHistory.statusString eq 'Awarded' }">
			      				<button type="button" class="btn btn-xs btn-warning" onclick="opensuspendcontract('${contract.id}','${contract.contractName}');">Suspend Contract</button>
			      			</c:when>
			      			<c:when test="${contract.contractHistory.statusString eq 'Suspended' }">
			      				<button type="button" class="btn btn-xs btn-info">Unsuspend Contract</button>
			      			</c:when>
			      			<c:when test="${contract.contractHistory.statusString eq 'Cancelled' }">
			      			</c:when>
			      			<c:otherwise>
			      					<button type="button" class="btn btn-xs btn-success" onclick="openawardcontract('${contract.id}','${contract.contractName}');">Award Contract</button>
			      					<button type="button" class="btn btn-xs btn-danger" onclick="opencancelcontract('${contract.id}','${contract.contractName}');">Cancel Contract</button>
			      			</c:otherwise>			      			
			      		</c:choose>
			        	</c:when>
			        	</c:choose>
			        	
			      		</div>
	    			</div>		    						
				</form>
			</div>
				<div id="documents2" class="tab-pane fade">
											   
				 <c:choose>
						<c:when test = "${contract.id gt 0 }">
						<br/>
						<div align="right">
						<button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogcontract('${contract.id}');">Add New Document</button>						
						</div>
						</c:when>
			    </c:choose>
			     		<div class="alert alert-danger" id="doc_details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			 		<div class="alert alert-success" id="doc_details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				 
				   <div id="BCS-Search">
				  	<table id="BCS-table" width="100%" class="BCSTable">
				  	<thead>
		     		<tr style="border-bottom:1px solid silver;" class="listHeader">
		     		<th width="40%" class="listdata">Document Title</th>
		      		<th width="30%" class="listdata">Date Uploaded</th>
		      		<th width="10%" class="listdata">Options</a></th>
					</tr>
		      		</thead>
		      		<tbody>
		      		<c:choose>
		      		<c:when test="${fn:length(documents) > 0}">
			      		<c:forEach items="${documents}" var="rule">
			      		<c:set var="countDocs" value="${countDocs + 1}" />
		 					<tr style="border-bottom:1px solid silver;">
		      					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>
		      					<td align="right" class="field_content">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('${spath}${rule.documentPath}','_blank');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedoccontdialog('${rule.documentTitle}','${rule.id}','${contract.id}');">Del</button>
		      					</td>
		      				</tr>
		        		</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			<tr><td colspan='5' style="color:Red;">No documents found.</td></tr>
		        		</c:otherwise>
		        		</c:choose>
		        	</tbody>
	      		</table>
			  </div>
			</div>
				</div>
			</div>
	</div>

				<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 


		<c:choose>
      	<c:when test="${countDocs >0 }">
      		<script>$('#doc_details_success_message').html("There are <b>${countDocs}</b> contract document(s).").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#doc_details_error_message').html("Sorry, there are no contract documents at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
		<c:choose>
      	<c:when test="${countRoutes >0 }">
      		<script>$('#route_details_success_message').html("There are <b>${countRoutes}</b> route(s) associated with this contract.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-RoutesA').css("display","none");$('#route_details_error_message').html("Sorry, there are no routes associated with this contract at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>      	
	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Document</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalert" align="center">
  							<span id="demessage"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalerts" align="center">
  							<span id="dsmessage"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title2">Document Title:</p>
                    <p>
                    <input class="form-control" id="documenttitle" name="documenttitle" placeholder="document title" type="text">
                    </p>
 		    		<p class="text-warning" id="title3">Document:</p>
 		    		<p>
 		    		<span class="btn btn-default btn-file" >
    					Browse <input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only)
					</span>
 		    		</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Document</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModal3" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitlec">Add Route</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1c"><span id="spantitle1c" name="spantitle1c"></span></p>
                    <p class="text-warning" id="title2c"><span id="spantitle2c" name="spantitle2c"></span></p>
                    <p>
                    	<select class="form-control" id="routes" name="routes"  style="width:auto;">
		                	<option value=" ">Please select route</option>
							<c:forEach var="entry" items="${allroutes}">
								<option value='${entry.id}'>${entry.routeName}</option>
							</c:forEach>
				  		</select>
				  	</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftc"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightc"></button>
                </div>
            </div>
   		</div>
   	</div>   
   	  	
   	<div id="myModal4" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle4">Add Route</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title14"><span id="spantitle14" name="spantitle14"></span></p>
                    <p class="text-warning" id="title24"><span id="spantitle24" name="spantitle24"></span></p>
                    <p>
                    	<select class="form-control" id="contractors" name="contractors"  style="width:auto;">
		                	<option value=" ">Please select contractor</option>
							<c:forEach var="entry" items="${allcontractors}">
								<option value='${entry.id}'>
								${entry.company}( ${entry.lastName}, ${entry.firstName})
								</option>
							</c:forEach>
				  		</select>
				  	</p>
				  	<div class="modal-body24" style="text-align:center;" id="modalnotes">
      					<p>Notes:</p>
      					<br>
        				<textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotes"></textarea>
      				</div>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleft4"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright4"></button>
                </div>
            </div>
   		</div>
   	</div>	
<script src="includes/js/jQuery.print.js"></script>	