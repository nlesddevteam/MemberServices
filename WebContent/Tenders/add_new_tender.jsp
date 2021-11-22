<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>   

<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = (User) session.getAttribute("usr");
%>
<esd:SecurityCheck />
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
    	<script>
 $('document').ready(function(){  		

    		    $( "#closing_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		    
    		    $( "#awarded_date" ).datepicker({
        		      changeMonth: true,//this option for allowing user to select month
        		      changeYear: true, //this option for allowing user to select from year range
        		      dateFormat: "dd/mm/yy"
        		    });
    		  }
    		);

	</script>
	
	<style>
	
	.hide {
    position: absolute !important;
    top: -9999px !important;
    left: -9999px !important;
    visibility: hidden !important;
}
	
	</style>
	
	</head>

  <body>
  
  <c:set var="now" value="<%=new java.util.Date()%>" /> 
  <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="theClosingDate" />	
 <div class="row pageBottomSpace">
<div class="col siteBodyTextBlack">

<div class="siteHeaderGreen">Add New Open Tender/Exception</div>
	   		
		
 		<ul> 
		<li>Any Open Tender you add here is assumed to be new and open. 
		<li>Tender Number is NOT required for an Exemption.
		<li>You can change this status once the tender is posted by editing or deleting.
		<li>Open Tenders and Exemptions to Open Tenders will have different entry requirements. 
		<li>Required fields will be individually outlined in Red. Non-required and/or completed fields will be outlines in Green.
		<li>Selecting Exemptions, more fields will become available below and some fields will be grayed out or made non-required. 
		
 </ul>
 <br/>
 Please enter the Open Tender or Exemption  information below. 
 
			<%if(request.getAttribute("msgOK") != null){%>
                      
                        <p>                       
                       <div class="alert alert-success alert-dismissible" style="text-align:center;">
                       <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                       *** <%=(String)request.getAttribute("msgOK")%> ***
                       </div>
                        <p>  
                        <div align="center" style="margin-bottom:10px;">  
                        
                        <esd:SecurityAccessRequired permissions="TENDER-ADMIN">
					 	<a class="btn btn-sm btn-success" style="color:white;margin-top:5px;" role="button" href="addNewTender.html">Add Another </a> &nbsp; 
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">  
                      	<a class="btn btn-sm btn-primary" style="color:white;margin-top:5px;" role="button" href="viewTenders.html">Back to List</a> 
                    </esd:SecurityAccessRequired>            
                   
                      
                      </div>
                    <%} else {%>


    <form id="pol_cat_frm" name="TenderForm" action="addNewTender.html" method="post" ENCTYPE="multipart/form-data" class="was-validated">
      <input type="hidden" id="op" name="op" value="CONFIRM">         
     
     <p>     
      	<div class="row container-fluid">
    	
    	<div class="col-lg-3 col-12">
			   	<b>STATUS: (Open Tender or Exception)</b>
						    <select id="tender_status" name="tender_status" class="form-control" required>
								<c:forEach var="item" items="${statuslist}">
									<c:if test="${item.value eq 'OPEN' or item.value eq 'EXCEPTIONS'}">
										<option value="${item.key}">${item.value}</option>
									</c:if>
								</c:forEach>
						    </select>
			</div>    	
    	
      		<div class="col-lg-9 col-12">
    			<b>TITLE:</b>  
  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" placeholder="Enter Tender or Exception Title" required></input>  
  				<div class="invalid-feedback">Please fill out this field.</div>			
			</div>
				
			
			
		</div>
		    <br/><br/>
      	<div class="row container-fluid">
      		<div class="col-lg-3 col-12">
				  <b>TENDER NUMBER: (i.e. 21-0123)</b>
				  <input type="text" class="form-control"  id="tender_number"  name="tender_number" placeholder="eg: 21-0123" maxlength="15"  required>				
    				<div class="invalid-feedback">Please fill out this field.</div>
			</div>
	
			
			<div class="col-lg-3 col-12">      		
				  <b>FOR REGION:</b>  
				  <select class="form-control" id="region" name="region" required>
				  
				  			<c:forEach var="item" items="${regions}" >
				            
          						<c:choose>
											<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">											
												<option value="${item.zoneId}">avalon</option>
											</c:when>
											<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
												<option value="${item.zoneId}">provincial</option>
											</c:when>
											<c:otherwise>
												<option value="${item.zoneId}">${item.zoneName}</option>
											</c:otherwise>
								</c:choose>
				            
				            </c:forEach>
				  </select>
 			</div>
 			<div class="col-lg-3 col-12">
 			<b>CLOSING ON:</b> 
  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" placeholder="Select Closing Date" required></input>
 			</div>
 			<div class="col-lg-3 col-12">
 			<b>OPENING AT:</b>  
  				<select id="opening_location" name="opening_location" class="form-control" required>
			            <c:forEach var="item" items="${regions}" >
			            
         					<c:choose>
										<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">						
											<option value="${item.zoneId}">avalon</option>
										</c:when>
										<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
											<option value="${item.zoneId}">provincial</option>
										</c:when>
										<c:otherwise>
											<option value="${item.zoneId}">${item.zoneName}</option>
										</c:otherwise>
							</c:choose>	            
			            				
			            </c:forEach>
			     </select>      		
 			</div>
 			
 			
 		</div>
    <br/><br/>
    
      			<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
  			<b>ADD DOCUMENT: (PDF)</b>  			
  			<div class="custom-file">
    <input type="file" class="custom-file-input form-control" id="tender_doc" name="tender_doc" required>
    <div class="invalid-feedback">Please fill out this field.</div>
    <label class="custom-file-label" for="customFile">Choose file</label>
  </div>
  			
  			
     		
   		</div>
   		</div>
   		<br/><br/>   		
		    
 <!-- EXCEPTIONS DETAILS -->  		
   		<div id="exceptionsDetails" class="hide" style="border:1px solid red;padding:5px;background:#FFF5EE;padding-bottom:50px;margin-bottom:10px;">
   		<b>EXCEPTION DETAILS<b/>
   		<br/><br/>
   		
    	
    	<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Contract Description:</b>
    			  <textarea  autocomplete="false"  id="edescription" name="edescription" class="form-control" style="height:100px;" maxlength="3500" required></textarea>    	
    			  <div class="invalid-feedback">Please fill out this field.</div>				
			</div>
		</div>
		<br/><br/>
		
			
		<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Vendor Name:</b>  
  				<input type="text" class="form-control" id="vendor_name" name="vendor_name" maxlength="1000" placeholder="Enter Vendor Name" required></input>  	
  				<div class="invalid-feedback">Please fill out this field.</div>		
			</div>
		</div>
		
		
		<br/><br/>
    	<div class="row container-fluid">
      		<div class="col-lg-9 col-12">
    			<b>Vendor Address:</b> (Town/City) 
    			<input type="text" class="form-control" id="eaddress" name="eaddress" maxlength="1000" placeholder="Enter Address" required></input>  		
    			<div class="invalid-feedback">Please fill out this field.</div>			
			</div>		     
      		<div class="col-lg-3 col-12">
    			<b>Vendor Province/State :</b>      			  		
    			<select class="form-control" id="elocation" name="elocation" required>
	<option value="">Select One</option>
	<optgroup label="Canadian Provinces">
		<option value="AB">Alberta</option>
		<option value="BC">British Columbia</option>
		<option value="MB">Manitoba</option>
		<option value="NB">New Brunswick</option>
		<option value="NL">Newfoundland and Labrador</option>
		<option value="NT">Northwest Territories</option>
		<option value="NS">Nova Scotia</option>
		<option value="NU">Nunavut</option>
		<option value="ON">Ontario</option>
		<option value="PE">Prince Edward Island</option>
		<option value="QC">Quebec</option>
		<option value="SK">Saskatchewan</option>
		<option value="YT">Yukon Territory</option>
	</optgroup>
	<optgroup label="U.S. States/Territories">
		<option value="AK">Alaska</option>
		<option value="AL">Alabama</option>
		<option value="AR">Arkansas</option>
		<option value="AZ">Arizona</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DC">District of Columbia</option>
		<option value="DE">Delaware</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="IA">Iowa</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
		<option value="KS">Kansas</option>
		<option value="KY">Kentucky</option>
		<option value="LA">Louisiana</option>
		<option value="MA">Massachusetts</option>
		<option value="MD">Maryland</option>
		<option value="ME">Maine</option>
		<option value="MI">Michigan</option>
		<option value="MN">Minnesota</option>
		<option value="MO">Missouri</option>
		<option value="MS">Mississippi</option>
		<option value="MT">Montana</option>
		<option value="NC">North Carolina</option>
		<option value="ND">North Dakota</option>
		<option value="NE">Nebraska</option>
		<option value="NH">New Hampshire</option>
		<option value="NJ">New Jersey</option>
		<option value="NM">New Mexico</option>
		<option value="NV">Nevada</option>
		<option value="NY">New York</option>
		<option value="OH">Ohio</option>
		<option value="OK">Oklahoma</option>
		<option value="OR">Oregon</option>
		<option value="PA">Pennsylvania</option>
		<option value="PR">Puerto Rico</option>
		<option value="RI">Rhode Island</option>
		<option value="SC">South Carolina</option>
		<option value="SD">South Dakota</option>
		<option value="TN">Tennessee</option>
		<option value="TX">Texas</option>
		<option value="UT">Utah</option>
		<option value="VA">Virginia</option>
		<option value="VT">Vermont</option>
		<option value="WA">Washington</option>
		<option value="WI">Wisconsin</option>
		<option value="WV">West Virginia</option>
		<option value="WY">Wyoming</option>
	</optgroup>
	
</select>
   <div class="invalid-feedback">Please fill out this field.</div> 
			</div>
		</div>
		<br/><br/>
    	<div class="row container-fluid">
      		<div class="col-lg-3 col-12">
    			<b>Price:</b>  
  				<input type="text" class="form-control" id="eprice" name="eprice" maxlength="20" placeholder="Enter Price" required></input>  			
  				<div class="invalid-feedback">Please fill out this field.</div>
			</div>
		
      		<div class="col-lg-5 col-12">
    			<b>PO Number:</b>  
  				<input type="text" class="form-control" id="po_number" name="po_number" maxlength="10" placeholder="Enter PO Number 00 0000" required></input>  			
  				<div class="invalid-feedback">Please fill out this field.</div>
			</div>
			
			<div class="col-lg-4 col-12">
    			<b>Renewal:</b>  
  				<select id="erenewal" name="erenewal" class="form-control" required>
					<c:forEach var="item" items="${renewallist}">
						<option value="${item.value}">${item.description}</option>
					</c:forEach>
				</select> 
			</div>
			
		</div>
		
		<div class="row container-fluid">
			<div class="col-lg-12 col-12">
			<div id="divother" style="display:none;">
			<b>If Other, please explain briefly:</b><br/>
			<input type="text" class="form-control" id="erenewalother" name="erenewalother" maxlength="1000" placeholder="Enter Other"></input></div>  
			</div>
			
</div>
		
		
				
   		<br/><br/>
   		
    	<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Terms:</b>    			  
    			<textarea  autocomplete="false"  id="eterms" name="eterms"class="form-control" style="height:100px;" maxlength="3500" required></textarea>  		
    			<div class="invalid-feedback">Please fill out this field.</div>			
			</div>
		</div>
		<br/><br/>
     <div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Exception Clause:</b>  
    			<textarea  autocomplete="false" id="eclause" name="eclause" class="form-control"  maxlength="3500" required></textarea>  	
    			<div class="invalid-feedback">Please fill out this field.</div>					
			</div>
		</div>
		
		
		
	</div>
<!-- END EXCEPTIONS BLOCK -->	
		<br/><br/>
		<!-- AWARDED DATE  BLOCK -->			
		<div id="theAwardDate" class="hide" style="z-index:-999;border:1px solid red;padding:5px;background:#F0FFF0;margin-bottom:10px;">		
		<b><span id="awardType"></span> AWARDED DATE</b>
	<div class="form-group">
  				<label for="awarded_date">Select Date: (Defaults to current date for an exception. Click date field to change.)</label>
  				 <input type="text" class="form-control" id="awarded_date" name="awarded_date" autocomplete="off" required></input>
		</div>
	</div>
	<br/><br/>
		 <div align="center">             
    <button class="btn btn-sm btn-success" style="color:white;margin-top:5px;" id="butSave">Add This Tender/Exception</button> &nbsp; 
    <a class="btn btn-sm btn-danger" style="color:white;margin-top:5px;" role="button" HREF='viewTenders.html'>Back to List</a> &nbsp;   
      </div>    
	<br/><br/>
	<!-- AWARDED DETAILS  BLOCK -->						    
   <div id="extraDetails" class="hide" style="z-index:-999;border:1px solid red;padding:5px;background:#ffffe6;margin-bottom:10px;">
      
   <b>TENDER AWARDED DETAILS</b><br/>
   Please fill out the below to update the tender details to complete the tender listing if this tender you are adding is not currently listed and is already awarded. 
    These details can be edited later. If multiple awarded, please enter each company and the amount awarded to that company. (i.e. Company A for $###.##; Company B for $####.##, etc.) with 
							   total value of tender entered in the field below.
    <br/><br/>
    
  
		
		<div class="form-group">
  				<label for="awarded_to">Awarded Details:</label>  			
  				<textarea  autocomplete="false" id="awarded_to" name="awarded_to" class="form-control" style="height:100px;" maxlength="3500" required></textarea>
  				
		</div>
		<b>Amount:</b> <br/>
		<div class="input-group">  	
					
  				<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
  				 <input type="text" class="form-control" id="contract_value" name="contract_value" required></input>
		</div>
		<br/>
       
    
   
    </div>      
             
    
    <br/><br/>
    </form>
  	

    <%}%>
   
    
  </div></div>

		
		

<script>
$(document).ready(function(){	

	
	 $('#po_number').mask('00 0000');	 
	 $('#tender_number').mask('00-0000');	 
	
	var pageWordCountConf = {
    	    showParagraphs: true,
    	    showWordCount: true,
    	    showCharCount: true,
    	    countSpacesAsChars: true,
    	    countHTML: true,
    	    maxWordCount: -1,
    	    maxCharCount: 3500,
    	}
	
	  CKEDITOR.replace( 'edescription',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
	    CKEDITOR.replace( 'eterms',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
	    CKEDITOR.replace( 'eclause',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
	    CKEDITOR.replace( 'awarded_to',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
	
	    
	    $('#vendor_name').removeAttr('required').prop("readonly",true);
		  $('#eaddress').removeAttr('required').prop("readonly",true);
		  $('#elocation').removeAttr('required').prop("readonly",true);
		  $('#eprice').removeAttr('required').prop("readonly",true);
		  $('#po_number').removeAttr('required').prop("readonly",true);
		  $('#erenewal').removeAttr('required').prop("readonly",true);			
		  
		  $('#edescription').removeAttr('required');
		  $('#eterms').removeAttr('required');
		  $('#eclause').removeAttr('required');
		  
		  $('#awarded_to').removeAttr('required').prop("readonly",true);
		  $('#contract_value').removeAttr('required').prop("readonly",true);
		  $('#awarded_date').removeAttr('required').prop("readonly",true);
		  
		
			$("#contract_value").val("0.00");
			$("#awarded_date").val("");		  
			
			CKEDITOR.instances['awarded_to'].setData("TBA");
	
			
			$('#erenewal').change(function(){
				
				  if($(this).val() == '5'){ 		
					  $('#divother').css("display","block");			  
					  
				  } else {
					  $('#divother').css("display","none");		
				  };
			});
			
			
			
			
	$('#tender_status').change(function(){
				
		  if($(this).val() == '9'){ 
			  $("#awardType").text("EXCEPTIONS");
					  $("#exceptionsDetails").removeClass("hide");
			  $("#theAwardDate").removeClass("hide");			  
			  $("#extraDetails").addClass("hide");
			  
			  //$("#exceptionsDetails").css("visibility","visible").css("width","100%");
			 // $("#extraDetails").css("position","absolute").css("visibility","hidden");
			  $('#tender_number').removeAttr('required').prop("readonly",true);
			  $('#closing_date').removeAttr('required').prop("readonly",true);
			  $("#awarded_date").val("${theClosingDate}");		   
			  $('#closing_date').val("${theClosingDate}");
			  $('#tender_number').val("00-0000");
			  $('#tender_doc').removeAttr('required');
			  $('#opening_location').removeAttr('required').prop("readonly",true);
			  $('#awarded_to').removeAttr('required').prop("readonly",true);
			  $('#contract_value').removeAttr('required').prop("readonly",true);
			  //$('#awarded_date').removeAttr('required').prop("readonly",true);
			 $('#awarded_date').removeAttr('readonly').prop("required",true);
				 
			$('#vendor_name').removeAttr('readonly').prop("required",true);
			  $('#eaddress').removeAttr('readonly').prop("required",true);
			  $('#elocation').removeAttr('readonly').prop("required",true);
			  $('#eprice').removeAttr('readonly').prop("required",true);
			  $('#po_number').removeAttr('readonly').prop("required",true);
			  $('#erenewal').removeAttr('readonly').prop("required",true);
			  
			  $('#edescription').prop('required');
			  $('#eterms').prop('required');
			  $('#eclause').prop('required');
			  
			  CKEDITOR.instances['edescription'].setReadOnly(false);
			  CKEDITOR.instances['eterms'].setReadOnly(false);
			  CKEDITOR.instances['eclause'].setReadOnly(false);
			  
			  			  
			  
		  }  else {
			  $('#tender_doc').prop('required');
			  $("#awardType").text("");
			  $('#tender_number').val("");
			  $("#awarded_date").val("");	
			  $("#theAwardDate").addClass("hide");	
			  $("#exceptionsDetails").addClass("hide");
			  $("#extraDetails").addClass("hide");
			  //$("#exceptionsDetails").css("visibility","hidden").css("position","absolute");
			  //$("#extraDetails").css("visibility","hidden").css("position","absolute");
			  $('#tender_number').removeAttr('readonly').prop("required",true);
			  $('#closing_date').removeAttr('readonly').prop("required",true);
			  $('#opening_location').removeAttr('readonly').prop("required",true);
			  
			  $('#vendor_name').removeAttr('required').prop("readonly",true);
			  $('#eaddress').removeAttr('required').prop("readonly",true);
			  $('#elocation').removeAttr('required').prop("readonly",true);
			  $('#eprice').removeAttr('required').prop("readonly",true);
			  $('#po_number').removeAttr('required').prop("readonly",true);
			  $('#erenewal').removeAttr('required').prop("readonly",true);			
			  $('#awarded_date').removeAttr('required').prop("readonly",true);
			  $('#edescription').removeAttr('required');
			  $('#eterms').removeAttr('required');
			  $('#eclause').removeAttr('required');
			  CKEDITOR.instances['edescription'].setReadOnly(true);
			  CKEDITOR.instances['eterms'].setReadOnly(true);
			  CKEDITOR.instances['eclause'].setReadOnly(true);
			
			  
			 
			  
			  
			  
			  
			  
		  }
		});
	
	
	

	//var maxChars = $("#awarded_to");
	//var max_length = maxChars.attr('maxlength');
	//if (max_length > 0) {
	  //  maxChars.bind('keyup', function(e){
	    //    length = new Number(maxChars.val().length);
	      //  counter = max_length-length;
	       // $("#sessionNum_counter").text(counter);
	  //  });
	//}
	
});


</script>
    <script>
$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
    
    
    
  </body>

</html>