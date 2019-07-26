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
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script src="includes/js/multiselect.js"></script>
<%
String tabs = (String)request.getParameter("tab");
String settab="";
if(tabs != null){
	settab="D";
}
pageContext.setAttribute("settab1", settab);
%>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$('#multiselect').multiselect();
    			if($("#settab1").val() == "D"){
					$('.nav-tabs a:last').tab('show'); 
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
    		          		        url: "adminAddNewRoute.html",
    		          		        data: frm.serialize(),
    		          		        success: function (xml) {
    		          		        	$(xml).find('CONTRACTOR').each(function(){
    		          							//now add the items if any
    		          							if($(this).find("MESSAGE").text() == "SUCCESS")
    		          			 					{
    		          									$('#body_success_message_bottom').text("Route Info Updated").css("display","block").delay(4000).fadeOut();
    		          									var surl = "adminViewRoute.html?vid=" + $(this).find("ID").text();
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
    		            routename: {
    		                validators: {
    		                        stringLength: {
    		                        min: 5,
    		                    },
    		                        notEmpty: {
    		                        message: 'Please supply route name'
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
    		$("#loadingSpinner").css("display","inline").delay(3000).fadeOut();
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
		  <div class="BCSHeaderText">Viewing Route <span style="font-weight:bold;text-transform:capitalize;">${route.routeName}</span></div>
		Below you will find the details on the <span style="text-transform:capitalize;">${route.routeName}</span> route. 
			  
			  
			<div style="max-width:90%;padding-top:10px;font-weight:normal;">	
			<ul class="nav nav-tabs">
    			<li class="active"><a data-toggle="tab" href="#details">Route Details</a></li>
    			<li><a data-toggle="tab" href="#documents">Documents</a></li>
  			</ul>
  			<div class="tab-content">
	  			<div id="details" class="tab-pane fade in active">
	  			<br />
		  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
			  	  	<div class="form-group">
			      		<label class="control-label col-sm-2" for="email"><img src='includes/css/images/asterisk-small.png'/> Route Name:</label><input type="hidden" id="cid" name="cid" value="${route.id}">
			      		<input type="hidden" id="rcid" name="rcid" value="${route.contractBean.id}">
			      		<div class="col-sm-5">
			        		<input class="form-control" id="routename" name="routename" type="text" placeholder="Enter route name" value="${route.routeName}" >
			      		<input type="hidden" id="settab1" name="settab1" value="${settab1}">
			      		<input type="hidden" id="routeschool" name="routeschool" value="-987">
			      		</div>
			    	</div>
			    	<div class="form-group">
			      		<label class="control-label col-sm-2" for="email">Run(s):</label><input type="hidden" id="cid" name="cid" value="${route.id}">
			      		<div class="col-sm-8">
			      		<c:choose>
				      			<c:when test="${route.id > 0}">
				      			<button type="button" class="btn btn-xs btn-success" onclick="openaddnewbusrun('${route.id}','A');">Add New Run</button>
				      			</c:when>
				      			<c:otherwise>
				      			<div class="alert alert-warning" role="alert">
				      				<label class="control-label col-sm-8" for="email">Please update route before adding runs</label>
				      			</div>
				      			</c:otherwise>
			      		</c:choose>
			      		</div>
			      	</div>
			    	<div class="form-group">
			    		<label class="control-label col-sm-2" for="email"></label>
			    		<div  class="control-label col-sm-9">  	  		  	
						<div id="MEMO-Table">	
						<table id="BCS-tableA" width="100%" class="BCSTable">
					  		<thead>
					  			<tr class="listHeader">
					  				<th width="15%" class="listdata" style="padding:2px;">Run</th>
					  				<th width="20%" class="listdata" style="padding:2px;">Time</th>
					  				<th width="35%" class="listdata" style="padding:2px;">School</th>
					  				<th width="20%" class="listdata" style="padding:2px;">Options</th>
					  			</tr>
					  		</thead>
					  		<tbody>
							<c:choose>
				      			<c:when test="${fn:length(route.routeRuns) > 0}">
					      			<c:forEach items="${route.routeRuns}" var="rule">
					      			<c:set var="countRuns" value="${countRuns + 1}" />
					 					<tr style="border-bottom:1px solid silver;">
					      					<td class="field_content">${rule.routeRun}</span></td>
					      					<td class="field_content">${rule.routeTime}</td>
					      					<td class="field_content">${rule.runSchools}</td>
					      					<td align="right" class="field_content">
					      					
					      					<button type="button" class="btn btn-xs btn-primary" onclick="getRouteRun('${rule.id}','${route.id}');">View</button>
				      					    <button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogrouterun('${rule.id}','${route.id}');">Del</button>
					      					
					      						</td>
					      				</tr>
				        			</c:forEach>
		     							</c:when>
		     							<c:otherwise>
		     								<tr><td colspan='5' style="color:Red;">No runs found</td></tr>
		     							</c:otherwise>
		     						</c:choose>
					  		</tbody>
					  	</table>
					  	</div>
					  	</div>
					</div>
						            <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Vehicle Type:</label>
	                <div class="col-sm-5">
	                <select class="form-control" id="vtype" name="vtype"  style="width:auto;" onchange="getSubDropdownItems();">
	                	<option value="-1">Please select type</option>
						<c:forEach var="entry" items="${vtypes}">
							<c:choose>
							<c:when test = "${route.vehicleType == entry.key }">
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
	                <label class="control-label col-sm-2" for="email">Vehicle Size:</label>
	                <div class="col-sm-5">
	                <select class="form-control" id="vsize" name="vsize"  style="width:auto;">
	                	<option value="-1">Please select size</option>
						<c:forEach var="entry" items="${vsizes}">
							<c:choose>
							<c:when test = "${route.vehicleSize == entry.key }">
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
				      <label class="control-label col-sm-2" for="email">Route Notes:</label>
				      	<div class="col-md-10">
		            		<textarea class="form-control" rows="4" id="routenotes"  name="routenotes" placeholder="Enter route notes">${route.routeNotes}</textarea>
		        		</div>
				    </div>
				    <div class="form-group">
				    	
				    		<label class="control-label col-sm-2" for="email">Contract:</label>
		      				<div class="form-inline col-sm-10">
				      		<c:choose>
				      		<c:when test = "${route.id lt 1}">
				      		</c:when>
							<c:when test = "${route.contractBean.id lt 1}">
							<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">SORRY: There is no contract currently assigned to this route.</div>
							<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddcontract('${route.id}','${route.routeName}');">Add Contract</button></div>     					
							</c:when>
							<c:otherwise>
								<span style="text-transform:capitalize;">${route.contractBean.contractName}</span> &nbsp;&nbsp;
							<button type="button" class="btn btn-xs btn-primary menuBCSC" onclick="loadMainDivPage('adminViewContract.html?vid=${route.contractBean.id}');">View</button>
							<button type="button" class="btn btn-xs btn-danger" onclick="openremovecontract('${route.contractBean.id}','${route.id}');">Remove</button>
								</c:otherwise>
							</c:choose>
							</div>
						
				    </div>
				    <div class="form-group">
		      			<label class="control-label col-sm-2" for="email">Operator:</label>
		      			<div class="col-sm-8">
		      				<div class="form-inline">
		        				
		        			<c:choose>
				      			<c:when test="${route ne null}">
				      				<c:choose>
				      					<c:when test="${route.contractorName ne ''}">
				      						<p class="form-control-static">${route.contractorName} &nbsp;&nbsp;
				      					</c:when>
				      					<c:otherwise>
				      						<p class="form-control-static">&nbsp;&nbsp;
				      					</c:otherwise>
				      				</c:choose>
				      				
				      			</c:when>
				      			<c:otherwise>
				      				<p class="form-control-static"> &nbsp;&nbsp;
				      				</c:otherwise>
			      			</c:choose>
		        				
		        				</div>
		      			</div>
		    			</div>				    
				    <div class="form-group">
		      			<label class="control-label col-sm-2" for="email">Assigned Driver:</label>
		      			<div class="col-sm-8">
		      				<div class="form-inline">
		        				
		        				<c:choose>
				      				<c:when test="${route.id > 0}">
				      				<p class="form-control-static">${ assigneddriver} &nbsp;&nbsp;
				      			<button type="button" class="btn btn-xs btn-primary" id="changedriver" name="changedriver" onclick="openchangedriveradmin('${route.id}');">Change</button></p>
		      				</c:when>
				      				<c:otherwise>
				      				<div class="alert alert-warning" role="alert">
				      					<label class="control-label col-sm-8" for="email">Please update route before changing</label>
				      					</div>
				      				</c:otherwise>
			      				</c:choose>
		        				
		        				</div>
		      			</div>
		    			</div>
		    				<div class="form-group">
		      				<label class="control-label col-sm-2" for="email">Assigned Vehicle:</label>
		      				<div class="col-sm-7">
		      					<div class="form-inline">
		        				
		        				<c:choose>
				      				<c:when test="${route.id > 0}">
				      				<p class="form-control-static">${assignedvehicle}&nbsp;&nbsp;
				      					<button type="button" class="btn btn-xs btn-primary" id="changevehicle" name="changevehicle" onclick="openchangevehicleadmin('${route.id}');">Change</button></p>
		      					</c:when>
				      				<c:otherwise>
				      				<div class="alert alert-warning" role="alert">
				      					<label class="control-label col-sm-8" for="email">Please update route before changing</label>
				      					</div>
				      				</c:otherwise>
			      				</c:choose>
		        				
		        			</div>
		      			</div>
		    		</div>
				    <div class="form-group">        
			      		<div class="col-sm-offset-2 col-sm-10">
			      		<br />
			        	<button type="submit" class="btn btn-xs btn-primary">Update Route</button>
			      		</div>
	    			</div>			
				</form>
			</div>
				<div id="documents" class="tab-pane fade">
						<div class="alert alert-danger" id="doc_details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			 		<div class="alert alert-success" id="doc_details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
				
						<c:choose>
						<c:when test = "${route.id gt 0 }">
						<p><div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogroute('${route.id}');">Add Document</button></div>			  	
				  		<br/></c:when>						
						</c:choose>
					
					<div id="BCS-Search">
				  	<table id="BCS-table" width="100%" class="BCSTable">
				  	<thead>
		     		<tr style="border-bottom:1px solid silver;" class="listHeader">
		      		<th width="40%" class="listdata">Document Title</th>
		      		<th width="30%" class="listdata">Date Uploaded</th>
		      		<th width="10%" class="listdata">Options</th>
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
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedocroutedialog('${rule.documentTitle}','${rule.id}','${route.id}');">Del</button>
		      					
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
      		<script>$('#doc_details_success_message').html("There are <b>${countDocs}</b> route document(s).").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#doc_details_error_message').html("Sorry, there are no route documents at this time.").css("display","block");</script>
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
    					Browse <input type="file" id="documentname" name="documentname" accept="application/pdf" >(PDF file format only)
					</span>
 		    		</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonright"></button>
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
                    <p class="text-warning" id="title1d"><span id="spantitle1d" name="spantitle1d"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2d" name="spantitle2d"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-danger"" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModal3" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitlec">Add Contract</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1c"><span id="spantitle1c" name="spantitle1c"></span></p>
                    <p class="text-warning" id="title2c"><span id="spantitle2c" name="spantitle2c"></span></p>
                    <p>
                    	<select class="form-control" id="contracts" name="contracts"  style="width:auto;">
		                	<option value=" ">Please select contract</option>
							<c:forEach var="entry" items="${contracts}">
								<option value='${entry.id}'>${entry.contractName}</option>
							</c:forEach>
				  		</select>
				  	</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success""  id="buttonleftc"></button>
                    <button type="button" class="btn btn-xs btn-danger"" data-dismiss="modal" id="buttonrightc"></button>
                </div>
            </div>
   		</div>
   	</div>   	
   	<div id="modald" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add Driver</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1"><span id="spantitle1" name="spantitle1">Select driver to assign to route</span></p>
                    <p class="text-warning" id="title2"><span id="spantitle2" name="spantitle2"></span></p>
                    <p>
                    	<select class="form-control" id="drivers" name="drivers"  style="width:auto;">
		                	<option value=" ">Please select driver</option>
							<c:forEach var="entry" items="${drivers}">
								<c:choose>
									<c:when test="${entry.id ne assigneddriverid}">
										<option value='${entry.id}'>${entry.lastName},${entry.firstName}</option>
									</c:when>
								</c:choose>
							</c:forEach>
				  		</select>
				  	</p>
				  	
				  	
				  	
				  	
				  	
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"  id="buttonleftdr"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonrightdr"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="modalv" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitlev">Add Vehicle</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1v"><span id="spantitle1v" name="spantitle1v">Select vehicle to assign to route</span></p>
                    <p class="text-warning" id="title2v"><span id="spantitle2v" name="spantitle2v"></span></p>
                    <p>
                    	<select class="form-control" id="vehicles" name="vehicles"  style="width:auto;">
		                	<option value=" ">Please select vehicle</option>
							<c:forEach var="entry" items="${vehicles}">
								<c:choose>
									<c:when test="${entry.id ne assignedvehicleid}">
										<option value='${entry.id}'>${entry.vPlateNumber} [${entry.vSerialNumber}]</option>
									</c:when>
								</c:choose>
							</c:forEach>
				  		</select>
				  	</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"  id="buttonleftv"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonrightv"></button>
                </div>
            </div>
   		</div>
   	</div>
   	   	<div id="modalnewrun" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitlenr"></h4>
                </div>
                <div class="modal-body">
                	<table>
                		<tr>
                			<td colspan='2'>
                				<div class="alert alert-danger" style="display:none;" id="dalertnr" align="center">
  									<span id="demessagenr"></span>
								</div>
								<div class="alert alert-success" style="display:none;" id="dalertsnr" align="center">
  									<span id="dsmessagenr"></span>
								</div>
                			</td>
                		</tr>
                	   <tr>
                			<td>
                				<p class="text-warning" id="title1v"><span id="spantitle1v" name="spantitle1v">Run</span></p>
                			</td>
                			<td>
                				<select class="form-control" id="runs" name="runs"  style="width:auto;">
                					<option value=" ">Please select</option>
                					<option value="A">A</option>
                					<option value="B">B</option>
                					<option value="C">C</option>
                					<option value="D">D</option>
                					<option value="E">E</option>
                					<option value="F">F</option>
                				</select>
                			</td>
                		</tr>
                		<tr>
                			<td>
                				<p class="text-warning" id="title1v"><span id="spantitle1v" name="spantitle1v">Time</span></p>
                			</td>
                			<td>
                				<select class="form-control" id="times" name="times"  style="width:auto;">
                					<option value=" ">Please select</option>
                					<option value="AM">AM</option>
                					<option value="PM">PM</option>
                					<option value="AM/PM">AM/PM</option>
                				</select>
                			</td>
                		</tr>
						<tr>
							<td colspan='2'><p class="text-warning" id="title1v"><span id="spantitle1v" name="spantitle1v">School(s)</span></p></td>
						</tr>
						<tr>
							<td colspan='2'>
								<table cellpadding="10">
									<tr>
										<td align="left">
									 		<select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
								    			<c:forEach var="entry" items="${schools}">
													<option value='${entry.value}'>${entry.key}</option>
												</c:forEach>
								    		</select>
										</td>
										<td align="center">
											<button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
								    		<button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
								    		<button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
								    		<button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
								  		</td>
								  		<td align="right">
								  			<select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple">
								    	</select>
								  		</td>
									</tr>
								</table>
							</td>
						</tr>
                	</table>
                	
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"  id="buttonleftnr"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonrightnr"></button>
                </div>
            </div>
   		</div>
   	</div>  	
<script src="includes/js/jQuery.print.js"></script>	