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
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script type="text/javascript">

jQuery(function(){
	  $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
	 });


$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    		    $('#contact-form-up').bootstrapValidator({
    		        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
    		        feedbackIcons: {
    		            valid: 'glyphicon glyphicon-ok',
    		            invalid: 'glyphicon glyphicon-remove',
    		            validating: 'glyphicon glyphicon-refresh'
    		        },submitHandler: function(validator, form, submitButton) {
    		        	//$('#success_message').slideDown({ opacity: "show" }, "slow"); // Do something ...
    		          			$('#contact-form-up').data('bootstrapValidator').resetForm();
    		          				//var frm = $('#contact-form-up');
    		          				//frm.submit();
    		              //var bv = form.data('bootstrapValidator');
    		              // Use Ajax to submit form data
    		              //$.post(form.attr('action'), form.serialize(), function(result) {
    		                  //console.log(result);
    		              //}, 'json');
    		              
    		          			var form = $('#contact-form-up')[0]; // You need to use standard javascript object here
    		          			var formData = new FormData(form);
    		          			$.ajax({
    		          		        type: "POST",
    		          		        url: "updateSystemDocument.html",
    		          		        data: formData,
    		          		        success: function (xml) {
    		          		        	$(xml).find('CONTRACTOR').each(function(){
    		          							//now add the items if any
    		          							
    		          							

													
    		          							
    		          							
    		          							if($(this).find("MESSAGE").text() == "UPDATED")
    		          			 					{
    		          									$('#body_success_message_bottom').text("System Document Updated").css("display","block").delay(6000).fadeOut();
    		          									
    		          									    		          									   		          						
    		          									setTimeout(function() {
    		          										loadMainDivPage('admimViewSystemDocuments.html');
    														}, 4000);  
    		          									
    		          									
    		          									
    		          			 					}else{
    		          			 						$('#body_error_message_bottom').text($(this).find("MESSAGE").text()).css("display","block").delay(4000).fadeOut();
    		          			 					
    		          			 					}

    		          							});
    		          		        },
    		          		      	error: function (xml) {
    		          		            isvalid=false;
    		          		        },
    		          		      	processData: false,
    		          		      	contentType: false
    		          		    });
    		          			
    		        },
    		        fields: {
    		            documenttype: {
    		                validators: {
    		                      notEmpty: {
    		                        message: 'Please select type'
    		                    }
    		                }
    		            },
    		             documenttitle: {
    		                validators: {
    		                     stringLength: {
    		                        min: 2,
    		                    },
    		                    notEmpty: {
    		                        message: 'Please enter document title'
    		                    }
    		                }
    		            }
    		        }
    		       });

});
		</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">			
			  
			  <div class="BCSHeaderText">System Document</div>
			  <br/>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="updateSystemDocument.html" enctype='multipart/form-data'>
	  				
				  	<div class="form-group">
					  
			                <label class="control-label col-sm-2" for="email">Document Type:</label>
			                <div class="col-sm-5">
			                <select class="form-control" id="documenttype" name="documenttype"  style="width:auto;">
			                	<option value="">Please select type</option>
								<c:forEach var="entry" items="${dtypes}">
									<c:choose>
									<c:when test = "${document.documentType == entry.key }">
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
				      <label class="control-label col-sm-2" for="email">Document Title:</label><input type="hidden" id="did" name="did" value="${document.id}">
				      <div class="col-sm-5">
				        <input class="form-control" id="documenttitle" name="documenttitle" type="text" placeholder="Enter document title" value="${document.documentTitle}">
				      </div>
				      </div>
				     
				    <div class="form-group">
			                <label class="control-label col-sm-2" for="email">Document:</label> 
			                <div class="col-sm-8">
		      					<div class="form-inline">
					                <div class="col-sm-10">
										<input type="file" id="documentname" name="documentname" >
									
			                	<c:if test = "${document.documentPath != null}">
			                	<br/>Current Document: <a href='${dpath}${document.documentPath}' target="_blank" title="View Current Document">${document.documentPath}</a><br/><br/>
			                	To update/change document choose new file above to upload.
			                	</c:if>
			                	</div>
			                </div>
			         </div></div>
			        			    
				   	<div class="form-group">
						<label class="control-label col-sm-2" for="email">Accessibility:</label>
						<div class="col-sm-10">
		      				<div class="form-inline">
		      					  <label  for="email">
							      	<c:choose>
		         						<c:when test = "${document.vInternal eq 'Y'}">
				        					<input class="form-control" id="vinternal" name="vinternal" type="checkbox" checked value='1'>
				        				</c:when>
				        				<c:otherwise>
				        					<input class="form-control" id="vinternal" name="vinternal" type="checkbox" value='1'>
				        				</c:otherwise>
		      						</c:choose>
		      						&nbsp; Internal &nbsp;</label>
		      						
		      						
				      				<label  for="email">
									<c:choose>
		         						<c:when test = "${document.vExternal eq 'Y'}">
				        					<input class="form-control" id="vexternal" name="vexternal" type="checkbox" checked value='1'>
				        				</c:when>
				        				<c:otherwise>
				        				<input class="form-control" id="vexternal" name="vexternal" type="checkbox" value='1'>
				        				</c:otherwise>
		      						</c:choose>
		      						&nbsp; External&nbsp;</label>
		      						
				      		</div>
						</div>
					</div>	
						    
				   	<div class="form-group">
						<label class="control-label col-sm-2" for="email">Show Message on Contactor Login:</label>
						<div class="col-sm-10">
						<div class="form-inline">
							      <c:choose>
		         						<c:when test = "${document.showMessage eq 'Y'}">
				        					<input class="form-control" id="showmessage" name="showmessage" type="checkbox" checked value='1'>
				        				</c:when>
				        				<c:otherwise>
				        				<input class="form-control" id="showmessage" name="showmessage" type="checkbox" value='1'>
				        				</c:otherwise>
		      						</c:choose>
				      	</div>
					</div></div>
					
				   	<div class="form-group">
						<label class="control-label col-sm-2" for="email">Is Active:</label>
						<div class="col-sm-10">
						<div class="form-inline">
							      <c:choose>
		         						<c:when test = "${document.isActive eq 'Y'}">
				        					<input class="form-control" id="isactive" name="isactive" type="checkbox" checked value='1'>
				        				</c:when>
				        				<c:otherwise>
				        				<input class="form-control" id="isactive" name="isactive" type="checkbox" value='1'>
				        				</c:otherwise>
		      						</c:choose>
				      	</div>
					</div></div>
					<div class="form-group">
				      <label class="control-label col-sm-2" for="email">Number of Days to Show:</label>
				      <div class="col-sm-3">
				        <input class="form-control" id="messagedays" name="messagedays" type="text" placeholder="Enter number of days" value="${document.messageDays}">
				      </div>
				    </div>				
		    		<div class="form-group">        
				      <div class="col-sm-offset-2 col-sm-10">
				      	<br />
				        <button type="submit" class="btn btn-primary" id="submitupdate" name="submitupdate">Update Information</button>
				      </div>
				    </div>
			  </form>
		
		<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		
		
	</div>
<script src="includes/js/jQuery.print.js"></script>	