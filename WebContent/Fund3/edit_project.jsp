<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		 <link rel="stylesheet" href="includes/css/jquery-ui.css" />	
		<script src="includes/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="includes/js/jquery-1.9.1.min.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<script src="includes/js/jquery.validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script src="includes/js/jquery.maskedinput.min.js" type="text/javascript"></script>
		<script src="includes/js/jquery-ui.js" type="text/javascript"></script>
		<script src="includes/js/jquery.multiselect.js" type="text/javascript"></script>
		<!-- Add mousewheel plugin (this is optional) -->
		<script type="text/javascript" src="includes/fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="includes/fancybox/jquery.fancybox.js?v=2.1.5"></script>
		<link rel="stylesheet" type="text/css" href="includes/fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
		<script src="includes/js/changepopup.js"></script>
  <%
	User usr = (User) session.getAttribute("usr");
    
  %>


		
		<script>

			$(document).ready(function() {
				$("#projectnumber").keypress(function(event) {
		            return /\d/.test(String.fromCharCode(event.keyCode));
		        });
		        
		        $('#chkcentral').change(function(){
		            if(this.checked)
		            	$("#divcentral").css("display", "inline");
		            else
		            	$("#divcentral").css("display", "none");
		            
		            populateschools();

		        });
		        $('#chkeastern').change(function(){
		            if(this.checked)
		            	$("#diveastern").css("display", "inline");
		            else
		            	$("#diveastern").css("display", "none");
		            
		            populateschools();

		        });
		        $('#chkwestern').change(function(){
		            if(this.checked)
		            	$("#divwestern").css("display", "inline");
		            else
		            	$("#divwestern").css("display", "none");
		            
		            populateschools();

		        });	
		        $('#chklabrador').change(function(){
		            if(this.checked)
		            	$("#divlabrador").css("display", "inline");
		            else
		            	$("#divlabrador").css("display", "none");
		            
		            populateschools();

		        });
		        $('#chkprovincial').change(function(){
		            if(this.checked)
		            	$("#divprovincial").css("display", "inline");
		            else
		            	$("#divprovincial").css("display", "none");
		            
		            populateschools();

		        });			        
				$(function() {
					$( document ).tooltip();
				});
				var id = 0;
				var idf = 0;
				var ide = 0;
				var idpr = 0;
				// Add button functionality
				$("button.add").click(function(e) {
					e.preventDefault();
					var e = document.getElementById("lstschools");
					var e2 = document.getElementById("schoolbudget");
					if(e.options[e.selectedIndex].text == "Please Select"){
						alert("Please select school");
						return false;
					}
					if(e2.value  == ""){
						alert("Please enter budget");
						return false;
					}
					id++;
					//var master = $(this).parents("table.dynatable");
					var master = $("#dynatable");
					
					// Get a new row based on the prototype row
					var prot = master.find(".prototype").clone();
					prot.attr("class", "");
					
					//now set the table values
					prot.find(".id").attr("value", -1);
					
					prot.find(".school").attr("value",e.options[e.selectedIndex].text );
					prot.find(".schoolid").attr("value",e.options[e.selectedIndex].value );
					
					
					prot.find(".budget").attr("value",e2.value );
					
					master.find("tbody").append(prot);
					
				});
				
				// Remove button functionality
				 $(document).on('click', 'button.remove', function () { // <-- changes
				     $(this).closest('tr').remove();
				     return false;
				 });
					// Add button functionality
					$("button.addf").click(function(e) {
						e.preventDefault();
						var epartners = document.getElementById("lstpartners");
						var econtact = document.getElementById("contactname");
						var eemail = document.getElementById("contactemail");
						var ephone = document.getElementById("contactphone");
						if(epartners.options[epartners.selectedIndex].text == "Please Select"){
							alert("Please select partner");
							return false;
						}
						if(econtact.value  == ""){
							alert("Please enter name");
							return false;
						}
						if(eemail.value  == ""){
							alert("Please enter email address");
							return false;
						}
						if(ephone.value  == ""){
							alert("Please enter phone number");
							return false;
						}						
						idf++;
						//var master = $(this).parents("table.dynatable");
						var master = $("#dynatablef");
						
						// Get a new row based on the prototype row
						var prot = master.find(".prototypef").clone();
						prot.attr("class", "");
						
						//now set the table values
						prot.find(".idf").attr("value", -1);
						
						prot.find(".partner").attr("value",epartners.options[epartners.selectedIndex].text );
						prot.find(".partnerid").attr("value",epartners.options[epartners.selectedIndex].value );
						prot.find(".contact").attr("value",econtact.value );
						prot.find(".email").attr("value",eemail.value );
						prot.find(".phone").attr("value",ephone.value );
						
						master.find("tbody").append(prot);
						
					});
					
					// Remove button functionality
					 $(document).on('click', 'button.removef', function () { // <-- changes
					     $(this).closest('tr').remove();
						 	//renumber list
						 	var x=0;
						 	jQuery('.idf').each(function() {
							var currentElement = $(this);
								currentElement.val(x);
								x++;
							
							});
						 	//ide--;
						     return false;
					 });
					 $("#contactphone").mask("(999) 999-9999? x99999");
		    		 $( "#datefundingapproved" ).datepicker({
		      		      changeMonth: true,//this option for allowing user to select month
		      		      changeYear: true, //this option for allowing user to select from year range
		      		      dateFormat: "dd/mm/yy"
		      		 });
		    		 $( "#projectstartdate" ).datepicker({
		      		      changeMonth: true,//this option for allowing user to select month
		      		      changeYear: true, //this option for allowing user to select from year range
		      		      dateFormat: "dd/mm/yy"
		      		 });
		    		 $( "#projectenddate" ).datepicker({
		      		      changeMonth: true,//this option for allowing user to select month
		      		      changeYear: true, //this option for allowing user to select from year range
		      		      dateFormat: "dd/mm/yy"
		      		 });
						// Add button functionality
						$("button.adde").click(function(e) {
							e.preventDefault();
							var edesc = document.getElementById("expensedes");
							if(edesc.value == ""){
								alert("Please enter expense description");
								return false;
							}						
							ide++;
							//var master = $(this).parents("table.dynatable");
							var master = $("#dynatablee");
							
							// Get a new row based on the prototype row
							var prot = master.find(".prototypee").clone();
							prot.attr("class", "");
							
							//now set the table values
							prot.find(".ide").attr("value", -1);
							
							prot.find(".expense").attr("value",edesc.value );
							master.find("tbody").append(prot);
							
						});
						
						// Remove button functionality
						 $(document).on('click', 'button.removee', function () { // <-- changes
							//renumber list
							$(this).closest('tr').remove();
							 	var x=0;
							 	jQuery('.ide').each(function() {
	    							var currentElement = $(this);
									currentElement.val(x);
									x++;
	    							
								});
							 	//ide--;
							     return false;
						 });
			    		 $( "#firstreportdate" ).datepicker({
			      		      changeMonth: true,//this option for allowing user to select month
			      		      changeYear: true, //this option for allowing user to select from year range
			      		      dateFormat: "dd/mm/yy"
			      		 });
			    		 $("#employeephone").mask("(999) 999-9999? x99999");
			    		 $("#employeephonepr").mask("(999) 999-9999? x99999");
			    		 $("input[name='isactive']").on("click", function() {
			    			 if($(this).val()== 0){
					            	$("#divnotesl").css("display", "inline");
					            	$("#divnotest").css("display", "inline");
					            }else{
					            	$("#divnotesl").css("display", "none");
				            		$("#divnotest").css("display", "none");
					            }
						 });
			    		 
			    		 //populate school dropdowns
			    		 populateschools();
			    		 $("#lstregion").multiselect();
			    		 //add modal dialog to button
			    		  $(function() {
			    			    $( "#auditlog" ).dialog({
			    			    	dialogClass: "no-close",
			    			        autoOpen: false,
			    			        modal:true,
			    			        width:600,
			    			        title: 'VIEW AUDIT LOG',
			    			        buttons: {
			    			        	
			    			            CLOSE: function() {
			    			              $( this ).dialog( "close" );
			    			            }
			    			        }
			    			    });
			    			  });
			    		  $("#viewauditlog").click(function(){
			    			  		//$(this).hide();
			    			        $( "#auditlog" ).dialog('open');
			    			    });
			    		  $(function() {
			    			    $( "#approvereject" ).dialog({
			    			    	dialogClass: "no-close",
			    			        autoOpen: false,
			    			        modal:true,
			    			        width:600,
			    			        title: 'APPROVE\REJECT PROJECT',
			    			        buttons: {
	    			            		UPDATE: function() {
		    			              		//call ajax to update status
		    			              		ajaxUpdateProjectStatus();
	    			            			$( this ).dialog( "close" );
		    			            	},
			    			            CLOSE: function() {
			    			              $( this ).dialog( "close" );
			    			            }

			    			        }
			    			    });
			    			  });
			    		  $("#viewapprove").click(function(){
		    			  		//$(this).hide();
		    			  		$( "#approvereject" ).dialog('open');
		    			    });
			    		  $(function() {
			    			    $( "#setprojectnumber" ).dialog({
			    			    	dialogClass: "no-close",
			    			        autoOpen: false,
			    			        modal:true,
			    			        width:600,
			    			        title: 'SET PROJECT NUMBER',
			    			        buttons: {
	    			            		UPDATE: function() {
		    			              		//call ajax to update status
		    			              		ajaxUpdateProjectNumber();
	    			            			$( this ).dialog( "close" );
		    			            	},
			    			            CLOSE: function() {
			    			              $( this ).dialog( "close" );
			    			            }

			    			        }
			    			    });
			    			  });
			    		  $("#butprojectnumber").click(function(){
		    			  		//$(this).hide();
		    			  		$( "#setprojectnumber" ).dialog('open');
		    			    });
							$("button.addpr").click(function(e) {
								e.preventDefault();
								var eregions = document.getElementById("lstregionspr");
								var ename = document.getElementById("employeenamepr");
								var eemail = document.getElementById("employeeemailpr");
								var ephone = document.getElementById("employeephonepr");
								if(eregions.options[eregions.selectedIndex].text == "Please Select"){
									alert("Please select region");
									return false;
								}
								if(ename.value  == ""){
									alert("Please enter name");
									return false;
								}
								if(eemail.value  == ""){
									alert("Please enter email address");
									return false;
								}
								if(ephone.value  == ""){
									alert("Please enter phone number");
									return false;
								}						
								idpr++;
								//var master = $(this).parents("table.dynatable");
								var master = $("#dynatablepr");
								
								// Get a new row based on the prototype row
								var prot = master.find(".prototypepr").clone();
								prot.attr("class", "");
								
								//now set the table values
								prot.find(".idpr").attr("value", idpr);
								
								prot.find(".regionspr").attr("value",eregions.options[eregions.selectedIndex].text );
								prot.find(".eregionid").attr("value",eregions.options[eregions.selectedIndex].value );
								prot.find(".namepr").attr("value",ename.value );
								prot.find(".emailpr").attr("value",eemail.value );
								prot.find(".phonepr").attr("value",ephone.value );
								
								master.find("tbody").append(prot);
								
							});
							// Remove button functionality
						 	$(document).on('click', 'button.removepr', function () { // <-- changes
						     	$(this).closest('tr').remove();
						     	return false;
						 	});	
						 	$("#printreport").click(function(){
									var printlink = 'printProject.html?pid=' + $("#id").val();
					    			var mywindow = window.open(printlink, 'Print', 'height=600,width=800');
					    			//mywindow.document.close();
					    		    //mywindow.focus();
					    		    //mywindow.print();
					    		    
					    		});
			    		 
				 
		    });

		</script>
		<style>
			label {
				display: inline-block;
				width: 5em;
			}
			.dynatable, .dynatablef, .dynatablee {
				border: solid 0px #000; 
				border-collapse: collapse;
			}
			.dynatable th,
			.dynatable td,
			.dynatablef th,
			.dynatablef td
			.dynatablee th,
			.dynatablee td   {
				border: solid 0px #000; 
				padding: 2px 10px;
				width: 170px;
				text-align: center;
			}
			.dynatable .prototype, .dynatablef .prototypef, .dynatablee .prototypee, .dynatablepr .prototypepr{
				display:none;
			}
			.spanemail {
				display: inline-block;
				color: red;
			}
		</style>

		
	</head>

	<body style="margin:20px;">
		<form id="pol_cat_frm" action="updateProject.html" method="post">
      			<input type="hidden" id="op" name="op" value="CONFIRM">	
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
				<td align="center">
					<jsp:include page="header.jsp" />
					<br />
					<br />
				</td>
			</tr>
			<tr align="center">
				<td>
				<table width="80%" border='0' style="background: white;border-spacing: 10px;padding:5px;">
						<tr>
							<th colspan='2' align='center' class='fund3header'>EDIT PROJECT <input type="hidden" id="id" name="id" value ="${project.projectId }"></th>
						</tr>
												<tr>
							<th colspan='2' align='center'>
								<c:if test="${msg ne null}">
            							<div class="alert alert-success">
  											<strong>${msg}</strong>
										</div>
        						</c:if>
							</th>
						</tr>
						<tr>
							<td  nowrap  class='fund3formtext'>Project Status</td>
							<c:choose>
  									<c:when test="${project.projectStatus eq 1}">
										<td>
											<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
												APPROVED BY ${project.statusBy } ON ${project.statusDateFormatted }
												<br />
												${project.statusNotes }
											</p>
										</td>
  									</c:when>
  									<c:when test="${project.projectStatus eq 2}">
  										<td>
  											<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
  												REJECTED BY ${project.statusBy } ON ${project.statusDateFormatted }
												<br />
												${project.statusNotes }
											</p>
										</td>										
  									</c:when>
  									<c:otherwise>
										<td>
											<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
												SUBMITTED BY ${project.statusBy } ON ${project.statusDateFormatted }
												<br />
												${project.statusNotes }	
												</p>
											</td>									
  									</c:otherwise>
								</c:choose>
						</tr>						
						<tr>
							<td  nowrap class='fund3formtext'>Project Availability</td>
							<td>
								<c:choose>
  									<c:when test="${project.isActive eq 1}">
										<INPUT TYPE="radio" NAME="isactive" VALUE="1" checked>&nbsp&nbsp&nbspActive&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
										<INPUT TYPE="radio" NAME="isactive" VALUE="0">&nbsp&nbsp&nbspInactive
  									</c:when>
  									<c:otherwise>
 										<INPUT TYPE="radio" NAME="isactive" VALUE="1" >&nbsp&nbsp&nbspActive&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
										<INPUT TYPE="radio" NAME="isactive" VALUE="0" checked>&nbsp&nbsp&nbspInactive
  									</c:otherwise>
								</c:choose>

							</td>
						</tr>
						<c:choose>
  							<c:when test="${project.isActive eq 0}">
								<tr>
									<td nowrap class='fund3formtext'>
										<div id="divnotesl">Availability Notes</div>
									</td>
									<td nowrap>
										<div id="divnotest">
											<textarea rows="5" cols="50" id='anotes'  name='anotes' >${project.availabilityNotes}</textarea>
																				
										</div>
									</td>
								</tr>		
  							</c:when>
  							<c:otherwise>
								<tr>
									<td nowrap class='fund3formtext'>
										<div id="divnotesl" style="display: none">Availability Notes</div>
									</td>
									<td nowrap>
										<div id="divnotest"  style="display: none">
											<textarea rows="5" cols="50" id='anotes'  name='anotes' >${project.availabilityNotes}</textarea>										
										</div>
									</td>
								</tr>	
  							</c:otherwise>
							</c:choose>												
						<tr>
							<td nowrap class='fund3formtext'>Project Name</td>
							<td>
								<input type='text' id='projectname' size='100' title='Recommended to use name on Funding Letter' name='projectname' value='${project.projectName}' required>
								<br />
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Project Number</td>
							<td>
								<input type='text' id='projectnumber'  name='projectnumber' size='25' value='${project.projectNumber}' readonly>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<esd:SecurityAccessRequired roles="FUND3 SENIOR ACCOUNTANT">
									<input type="button" value="Change Project Number" id="butprojectnumber" name="butprojectnumber">
								</esd:SecurityAccessRequired>
								<br />
							</td>
						</tr>
						<tr>
							<td colspan='2'  nowrap class='fund3formtext'>Project Region</td>
						</tr>
						<tr>
							<td colspan='2' align='center'>
							<table border='0'>
							<c:forEach var="entry" items="${regionbud}">
								<c:choose>
									<c:when test="${entry.value < 0}">
										<tr>
											<td>${entry.key}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
												<td>
													<input type='checkbox' id='chk${fn:toLowerCase(entry.key)}' name='chk${fn:toLowerCase(entry.key)}'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<div id='div${fn:toLowerCase(entry.key)}' style='display: none;'>
														$<input type='text' id='${fn:toLowerCase(entry.key)}budget' name='${fn:toLowerCase(entry.key)}budget' title='Numeric Values Only'>
													</div>													
													<br />
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr><td>${entry.key}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>									
											<input type='checkbox' id='chk${fn:toLowerCase(entry.key)}' name='chk${fn:toLowerCase(entry.key)}' checked>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<div id='div${fn:toLowerCase(entry.key)}' style='display: inline-block;'>
												$<input type='text' id='${fn:toLowerCase(entry.key)}budget' name='${fn:toLowerCase(entry.key)}budget' title='Numeric Values Only' 
												value='<fmt:formatNumber type="number" pattern="###.00" value="${entry.value}" />'>
												
											</div>											
											<br /></td></tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>																							
							</table>
							</td>
						</tr>
						<tr>
							<td  colspan='2'  class='fund3formtext'>School Budget(s)</td>

						</tr>
						<tr>
							<td colspan='2'>
								<table width='100%'>
									<tr>
										<td>Select School
										<select id="lstschools" name="lstschools" size="1"  style="max-width:50%;">
											<option value='-1'>Please Select</option>
										</select>
										</td>
										<td>
											<input type='text' id='schoolbudget' name='schoolbudget' title='Numeric Values Only'>
										</td>
										<td><button class="add">Add</button></td>
									</tr>
									<tr><td colspan='3'><hr /></td></tr>
									<tr><td colspan='3' align="center">
										<table class="dynatable" id="dynatable">
											<thead>
												<tr>
													<th class='fund3formtext'></th>
													<th class='fund3formtext'>School</th>
													<th class='fund3formtext'>Budget</th>
													<th class='fund3formtext'></th>
													<th class='fund3formtext'></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototype">
													<td class="displayTextMed"><input type="hidden" name="id[]" value="" class="id" readonly   style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="school[]" value="" class="school" readonly   style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="budget[]" value="" class="budget" readonly   style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="schoolid[]"  class="schoolid" value="" readonly   style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="remove">Remove</button>
												</tr>
												
												<c:forEach var="entry" items="${project.projectSchool}">
													<tr>
														<td class="displayTextMed"><input type="hidden" name="id[]" value="${entry.id }" class="id" readonly   style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="school[]" value="${entry.school }" class="school" readonly   style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="budget[]" value="${ entry.budgetAmount}" class="budget" readonly   style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="hidden" name="schoolid[]"  class="schoolid" value="${entry.schoolId }" readonly   style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><button class="remove">Remove</button>
													</tr>
												</c:forEach>
											
										</table>
									</td></tr>
									<tr><td colspan='3'><hr /></td></tr>
								</table>
								<br />
							</td>
						</tr>
						<tr>
							<td  colspan='2' class='fund3formtext'>Funding Partner(s)</td>

						</tr>						
						<tr>
							<td colspan='2'>
								<table width='100%'>
									<tr>
										<td>Partner</td>
										<td>Contact's Name</td>
										<td>Contact's Email</td>
										<td>Contact's Phone</td>
										<td></td>
									</tr>
									<tr>
										<td>
											<select id="lstpartners"  style="max-width:75%;">
												<option value='-1'>Please Select</option>
												<c:forEach var="item" items="${items}" >
                									<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            									</c:forEach>
											</select>
										</td>
										<td>
											<input type='text' id='contactname' name='contactname' title="Contact's Name">
										</td>
										<td>
											<input type='text' id='contactemail' name='contactemail' title="Contact's Email">
										</td>
										<td>
											<input type='text' id='contactphone' name='contactphone' class='contactphone' title="Contact's Phone">
										</td>
										<td><button class="addf">Add</button></td>
									</tr>
									<tr><td colspan='5'><hr /></td></tr>
									<tr><td colspan='5' align="center">
										<table class="dynatablef" id="dynatablef">
											<thead>
												<tr>
													<th class='fund3formtext'></th>
													<th class='fund3formtext'>Partner</th>
													<th class='fund3formtext'>Contact</th>
													<th class='fund3formtext'>Email</th>
													<th class='fund3formtext'>Phone</th>
													<th class='fund3formtext'></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypef">
													<td class="displayTextMed"><input type="hidden" name="idf[]" value="" class="idf" readonly  style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="partner[]" value="" class="partner" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="contact[]" value="" class="contact" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="email[]"  class="email" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="phone[]"  class="phone" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="partnerid[]"  class="partnerid" value="" readonly  style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removef">Remove</button>
												</tr>
												<c:forEach var="entry" items="${project.projectFunding}">
													<tr class="">
														<td class="displayTextMed"><input type="hidden" name="idf[]" value="${entry.id }" class="idf" readonly style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="partner[]" value="${entry.fundingText }" class="partner" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="contact[]" value="${entry.contactName }" class="contact" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="email[]"  class="email" value="${entry.contactEmail }" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="phone[]"  class="phone" value="${entry.contactPhone }" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="hidden" name="partnerid[]"  class="partnerid" value="${entry.fundingId }" readonly style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><button class="removef">Remove</button>
													</tr>
												</c:forEach>
										</table>
									</td></tr>
									<tr><td colspan='5'><hr /></td></tr>
								</table>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Fiscal Year</td>
								<td>
										<select id="lstfiscal" name="lstfiscal"   style="max-width:50%;">
											<option value='-1'>Please Select</option>
											<c:forEach var="positem" items="${fiscalitems}" >
												<c:choose>
  													<c:when test="${project.projectYear eq positem.id}">
  														<option value="${positem.id}" selected="selected">${fn:toUpperCase(positem.ddText)}</option>
  													</c:when>
  													<c:otherwise>
  														<option value="${positem.id}" >${fn:toUpperCase(positem.ddText)}</option>
  													</c:otherwise>
												</c:choose>
               								</c:forEach>
										</select>	
								</td>
						</tr>												
						<tr>
							<td nowrap class='fund3formtext'>Date Funding Approved</td>
							<td>
								<input type='text' id='datefundingapproved' value ='${project.dateFundingApprovedFormatted }' title='Date of the funding letter or email(if no date identified, approximate date to be selected)' name='datefundingapproved' required>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Project Start Date</td>
							<td>
								<input type='text' id='projectstartdate' value ='${project.projectStartDateFormatted }' title='For when funding is identified for a specific timeframe, Example: 2016-2107 school year, or multi-year project)' name='projectstartdate'>
								To Be Determine&nbsp&nbsp&nbsp&nbsp&nbsp
								<c:choose>
  									<c:when test="${project.projectStartDateTBD eq 1}">
  										<input type='checkbox' id='chktbdstart' name='chktbdstart' checked>
  									</c:when>
  									<c:otherwise>
  										<input type='checkbox' id='chktbdstart' name='chktbdstart'>
  									</c:otherwise>
								</c:choose>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Project End Date</td>
							<td>
								<input type='text' id='projectenddate' value ='${project.projectEndDateFormatted }' title='For when funding is identified for a specific timeframe, Example: 2016-2107 school year, or multi-year project)' name='projectenddate'>
								To Be Determine&nbsp&nbsp&nbsp&nbsp&nbsp
								<c:choose>
  									<c:when test="${project.projectEndDateTBD eq 1}">
  										<input type='checkbox' id='chktbdend' name='chktbdend' checked>
  									</c:when>
  									<c:otherwise>
  										<input type='checkbox' id='chktbdend' name='chktbdend'>
  									</c:otherwise>
								</c:choose>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Category</td>
								<td>
										<select id="lstcategory" name="lstcategory"  style="max-width:50%;">
											<option value='-1'>Please Select</option>
											<c:forEach var="catitem" items="${catitems}" >
												<c:choose>
  													<c:when test="${project.projectCategory eq catitem.id}">
  														<option value="${catitem.id}" selected="selected">${fn:toUpperCase(catitem.ddText)}</option>
  													</c:when>
  													<c:otherwise>
  														<option value="${catitem.id}" >${fn:toUpperCase(catitem.ddText)}</option>
  													</c:otherwise>
												</c:choose>
               								</c:forEach>
           								</select>	
								</td>
						</tr>						
						<tr>
							<td nowrap class='fund3formtext'>Position Responsible for Project</td>
								<td>
										<select id="lstposition" name="lstposition"   style="max-width:50%;">
											<option value='-1'>Please Select</option>
											<c:forEach var="positem" items="${positems}" >
												<c:choose>
  													<c:when test="${project.positionResponsible eq positem.id}">
  														<option value="${positem.id}" selected="selected">${fn:toUpperCase(positem.ddText)}</option>
  													</c:when>
  													<c:otherwise>
  														<option value="${positem.id}" >${fn:toUpperCase(positem.ddText)}</option>
  													</c:otherwise>
												</c:choose>
               								</c:forEach>
           								</select>	
								</td>
						</tr>
						<tr>
							<td colspan='2'>
								<table width='100%'>
									<tr>
										<td  nowrap class='fund3formtext'>Region</td>
										<td  nowrap class='fund3formtext'>Employee Name</td>
										<td  nowrap class='fund3formtext'>Employee Email</td>
										<td  nowrap class='fund3formtext'>Employee Phone Number</td>
										<td></td>
									</tr>
									<tr>
										<td>
											<select id='lstregionspr' name='lstregionspr'>
												<option value='-1'>Please Select</option>
												<c:forEach var="item" items="${regions}" >
                									<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            									</c:forEach>
											</select>
										</td>
										<td>
											<input type='text' id='employeenamepr'  name='employeenamepr'>
										</td>
										<td>
											<input type='text' id='employeeemailpr'  name='employeeemailpr'><span class='spanemail'>@nlesd.ca</span>
										</td>
										<td>
											<input type='text' id='employeephonepr'  name='employeephonepr' class='employeephone'>
										</td>
										<td><button class="addpr">Add</button></td>
									</tr>
									<tr><td colspan='5'><hr /></td></tr>
									<tr><td colspan='5' align="center">
										<table class="dynatablepr" id="dynatablepr">
											<thead>
												<tr>
													<th class="fund3formtext"></th>
													<th class="fund3formtext">Region</th>
													<th class="fund3formtext">Name</th>
													<th class="fund3formtext">Email</th>
													<th class="fund3formtext">Phone</th>
													<th class="fund3formtext"></th>
													<th class="fund3formtext"></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypepr">
													<td class="displayTextMed"><input type="hidden" name="idpr[]" value="0" class="idpr" readonly  style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="regionspr[]" value="" class="regionspr" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="namepr[]" value="" class="namepr" readonly style="15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="emailpr[]"  class="emailpr" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="phonepr[]"  class="phonepr" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="eregionid[]"  class="eregionid" value="" readonly style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removepr">Remove</button>
												</tr>
												<c:forEach var="entry" items="${project.projectEmpRes}">
													<tr class="">
														<td class="displayTextMed"><input type="hidden" name="idpr[]" value="${entry.id }" class="idpr" readonly  style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="regionspr[]" value="${entry.regionText }" class="regionspr" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="namepr[]" value="${entry.employeeName }" class="namepr" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="emailpr[]"  class="emailpr" value="${entry.employeeEmail }" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="text" name="phonepr[]"  class="phonepr" value="${entry.employeePhone }" readonly style="border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><input type="hidden" name="eregionid[]"  class="eregionid" value="${entry.regionId }" readonly style="width:15px;border: none; background-color: transparent;"/></td>
														<td class="displayTextMed"><button class="removepr">Remove</button>
													</tr>
												</c:forEach>											
										</table>
									</td></tr>									
								</table>
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Description of Project</td>
							<td>
								<textarea rows="4" cols="50" id='projectdescription'  name='projectdescription'  required>${project.projectDescription }</textarea>
								<br />
							</td>
						</tr>
						<tr>
							<td  colspan='2' class='fund3formtext'>List Eligible Expense(s)</td>

						</tr>
						<tr>
							<td colspan='2'>
								<table width='100%'>
									<tr>
										<td class="fund3formtext">Expense Description
										</td>
										<td>
											<input type='text' id='expensedes' name='expensedes'>
										</td>
										<td><button class="adde">Add</button></td>
									</tr>
									<tr><td colspan='3'><hr /></td></tr>
									<tr><td colspan='3' align="center">
										<table class="dynatablee" id="dynatablee">
											<thead>
												<tr>
													<th class="fund3formtext"></th>
													<th class="fund3formtext">Description</th>
													<th class="fund3formtext"></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypee">
													<td class="displayTextMed"><input type="hidden" name="ide[]" value="" class="ide" readonly  style="width:10px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="expense[]" value="" class="expense" readonly  style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removee">Remove</button>
												</tr>
												<c:forEach var="entry" items="${project.projectExpense}">
													<tr class="">
													<td class="displayTextMed"><input type="hidden" name="ide[]" value="${entry.id }" class="ide" readonly  style="width:10px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="expense[]" value="${entry.expenseDetails }" class="expense" readonly  style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removee">Remove</button>
													</tr>
												</c:forEach>											
										</table>
									</td></tr>
									<tr><td colspan='3' class="displayTextMed"><hr /></td></tr>
								</table>
								<br />
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Special Requirements</td>
							<td>
								<textarea rows="4" cols="50" id='specialreq'  name='specialreq'  required>${project.specialRequirements}</textarea>
								<br />
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Report Required</td>
							<td>
								<c:choose>
  									<c:when test="${project.reportRequested eq 1}">
										<INPUT TYPE="radio" NAME="reportreq" VALUE="1" checked>Yes
										<INPUT TYPE="radio" NAME="reportreq" VALUE="0">No
  									</c:when>
  									<c:otherwise>
 										<INPUT TYPE="radio" NAME="reportreq" VALUE="1" >Yes
										<INPUT TYPE="radio" NAME="reportreq" VALUE="0" checked>No
  									</c:otherwise>
								</c:choose>

							</td>
						</tr>
						<tr>																																													
						<tr>
							<td class='fund3formtext'>Date of first report to be submitted</td>
							<td><input type='text' id='firstreportdate'  name='firstreportdate' value='${project.firstReportDateFormatted }'></td>
						</tr>
						<tr>
							<td class='fund3formtext'>Report Frequency</td>
							<td>
											<select id="lstreportfreq" name="lstreportfreq"   style="max-width:50%;">
												<option value='-1'>Please Select</option>
													<c:forEach var="freqitem" items="${freqitems}" >
														<c:choose>
  															<c:when test="${project.reportFrequency eq freqitem.id}">
  																<option value="${freqitem.id}" selected="selected">${fn:toUpperCase(freqitem.ddText)}</option>
  															</c:when>
  															<c:otherwise>
  																<option value="${freqitem.id}" >${fn:toUpperCase(freqitem.ddText)}</option>
  															</c:otherwise>
														</c:choose>
               										</c:forEach>
											</select>
							</td>
						</tr>
						<tr>
										<td class='fund3formtext'>Report Required to be Sent to Whom</td>
										<td><input type='text' id='reportsentto'  name='reportsentto' value='${project.reportEmail }'><span class='spanemail'>@nlesd.ca</span></td>
						</tr>																		
						<tr>
							<td  colspan='2' class='fund3formtext'>Funding Letter/Documentation</td>
						</tr>
						<tr>
							<td  colspan='2'>
							<table align="center" id="showlists" border="0" width="100%">
									<tr class="header">
										<th>Document</th>
										<th>Added By</th>
										<th>Date Added</th>
										<th><a class="fancybox" href="#inline1" title="Add New Document" onclick="OpenPopUpAddDocE();">Add New Document</a></th>
									</tr>
									<c:forEach var="p" items="${project.projectDocuments}" varStatus="counter">
										<tr id='${p.id}'>
											<td>${p.fileName}</td>
											<td>${p.addedBy}</td>
											<td>${p.dateAddedFormatted}</td>
											<td>
												<a class="small" href='' onclick="ajaxDeleteFile('${p.id}','${p.oFileName}','${p.projectId}','${p.fileName}'); return false;">Delete File</a>
		                    				</td>
										</tr>
									</c:forEach>
								</table>
								<br />
								<br />							
							</td>
						</tr>												
						<tr>
							<td align="center" colspan='2'>
							<input type="submit" value="UPDATE PROJECT" class="btnExample">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="CANCEL" onclick="javascript:window.location='index.jsp';" class="btnExample">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="VIEW AUDIT LOG" id="viewauditlog" class="btnExample">
							<input type="button" value="PRINT" id="printreport" class="btnExample">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
  								<c:when test="${project.projectStatus eq 1}">
  								</c:when>
  								<c:otherwise>
									<c:choose>
										<c:when test="${fn:length(regionbudused) eq 1}">
											<c:forEach items="${regionbudused}" var="treemap">
												<c:choose>
													<c:when test="${treemap.key eq 'PROVINCIAL'}">
														<%if(usr.getUserRoles().containsKey("FUND3 DISTRICT AD")){%>
															<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  														<%}%>													
													</c:when>
													<c:when test="${treemap.key eq 'WESTERN'}">
														<%if(usr.getUserRoles().containsKey("FUND3 REGIONAL ADP WEST")){%>
															<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  														<%}%>													
													</c:when>
													<c:when test="${treemap.key eq 'EASTERN'}">
														<%if(usr.getUserRoles().containsKey("FUND3 REGIONAL ADP EAST")){%>
															<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  														<%}%>													
													</c:when>
													<c:when test="${treemap.key eq 'CENTRAL'}">
														<%if(usr.getUserRoles().containsKey("FUND3 REGIONAL ADP CENTRAL")){%>
															<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  														<%}%>													
													</c:when>
													<c:when test="${treemap.key eq 'LABRADOR'}">
														<%if(usr.getUserRoles().containsKey("FUND3 REGIONAL ADP LAB")){%>
															<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  														<%}%>													
													</c:when>																																																				
												</c:choose>
											</c:forEach>	 
  										</c:when>
										<c:when test="${fn:length(regionbudused) gt 1}">
											<%if(usr.getUserRoles().containsKey("FUND3 DISTRICT AD")){%>
												<input type="button" value="APPROVE\REJECT" id="viewapprove" class="btnExample">
  											<%}%> 
  										</c:when>
  										<c:otherwise>
  										</c:otherwise>
									</c:choose>
  								</c:otherwise>
							</c:choose>  								
							</td>				
					</table>
					<br />
					<br />
					<br />
				</td>
			</tr>
			<jsp:include page="fund3links.jsp" />

			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		</form>
		<div id="inline1" style="width:500px;height:400px;display: none;">
		<span class="applicantTitle">ADD PROJECT DOCUMENT</span>
			<table width="400px;" cellpadding="2" cellspacing="3" align="center" border="0" style="padding: 5px;">
				<tr>
					<td class="displayHeaderTitle" valign="middle" width='125px'>
						PROJECT
					</td>
					<td class="displayHeaderTitle" valign="middle">
						<span id="projectnamed" id="projectnamed"></span>
					</td>					
				</tr>
				<tr><td colspan='2'><br/></td></tr>
				<tr>
					<td class="displayHeaderTitle" valign="middle" width='125px'>
						DOCUMENT NAME<input type="hidden" id="projectid" name="projectid" value ="${project.projectId }">
					</td>
					<td>
						<input type="text" name="documentname" id="documentname" style="width: 175px;" class="requiredInputBox">
					</td>
				</tr>
				<tr><td colspan='2'><br /></td></tr>							
				<tr>
					<td>REGION</td>
						<td>
							<select id='lstregion' name='lstregion' multiple>
								<c:forEach var="item" items="${regions}" >
                					<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            					</c:forEach>
							</select>
						</td>
				</tr>
				<tr><td colspan='2'><br /></td></tr>
				<tr>
					<td>DOCUMENT</td>
						<td>
							<input type='file' name='newdocument' id='newdocument' style="width:40%;min-width:175px;"/>
						</td>
				</tr>
				<tr><td colspan='2'><br /></td></tr>
				<tr>
					<td colspan="2" valign="middle" align="center">
						<input type="button" value="Submit" onclick="ajaxAddFile();"/>
						<input type="button" value="Cancel" onclick="closewindow();"/>

					</td>
				</tr>
		</table>
	</div>
	<div id="auditlog" title="VIEW AUDIT LOG" style="display: none;">
			<table border='0'>
				<thead>
				<tr>
					<tr>
						<th class="fund3tableheader">Entry Date</th>
						<th class="fund3tableheader">User</th>
						<th class="fund3tableheader">Log Entry</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entry" items="${project.auditLog}">
						<tr class="guide-info">
							<td class='displayText1'>${entry.auditDateFormatted}</td>
							<td class='displayText1'>${entry.userName}</td>
							<td class='displayText1'>${entry.logEntry}</td>
						</tr>
					</c:forEach>
				
			</table>	
	</div>
	<div id="approvereject" title="APPROVE\REJECT PROJECT" style="display: none;">
			<table border='0'>
				<tr>
					<td class="fund3tableheader">PROJECT STATUS</td>
					<td>
						<select id='lstProjectStatus'>
							<option value='1' selected>APPROVED</option>
							<option value='2'>REJECTED</option>	
						</select>
					</td>
				</tr>
				<tr>
					<td class="fund3tableheader">Availability Notes</td>
					<td>
						<textarea rows="4" cols="50" id='statusnotes'  name='statusnotes' ></textarea>										
					</td>
				</tr>			
			</table>	
	</div>
	<div id="setprojectnumber" title="SET PROJECT NUMBER" style="display: none;">
			<table border='0'>
				<tr>
					<td class="fund3tableheader">PROJECT NUMBER</td>
					<td>
						<input type="text" id="txtprojectnumber" name="txtprojectnumber">
					</td>
				</tr>			
			</table>	
	</div>	
	</body>
</html>