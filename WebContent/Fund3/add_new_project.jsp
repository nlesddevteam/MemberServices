<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		 <link rel="stylesheet" href="includes/css/jquery-ui.css" />	
		<script type="text/javascript" src="includes/js/jquery-1.9.1.min.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<script src="includes/js/jquery.validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script src="includes/js/jquery.maskedinput.min.js" type="text/javascript"></script>
		
    	<script src="includes/js/jquery-ui.js"></script>
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
				var idpr=0;
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
						prot.find(".idf").attr("value", idf);
						
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
							//ide++;
							//var master = $(this).parents("table.dynatable");
							var master = $("#dynatablee");
							
							// Get a new row based on the prototype row
							var prot = master.find(".prototypee").clone();
							prot.attr("class", "");
							
							//now set the table values
							prot.find(".ide").attr("value", ide);
							
							prot.find(".expense").attr("value",edesc.value );
							master.find("tbody").append(prot);
							edesc.value="";
							var x=0;
						 	jQuery('.ide').each(function() {
    							var currentElement = $(this);
								currentElement.val(x);
								x++;
    							
							});
							
						});
						
						// Remove button functionality
						 $(document).on('click', 'button.removee', function () { // <-- changes
						     $(this).closest('tr').remove();
						 	//renumber list
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
						     	//renumber list
							 	var x=0;
							 	jQuery('.idpr').each(function() {
	    							var currentElement = $(this);
									currentElement.val(x);
									x++;
	    							
								});
							 	//ide--;
							     return false;
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
				width:100%;
			}
			.dynatable th,
			.dynatable td,
			.dynatablef th,
			.dynatablef td
			.dynatablee th,
			.dynatablee td   {
				border: solid 0px #000; 
				padding-top: 5px;
				text-align: left;
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
		<form id="pol_cat_frm" action="addNewProject.html" method="post">
      			<input type="hidden" id="op" name="op" value="CONFIRM">	
		<table width="1000px" border="0" cellspacing="5px" cellpadding="5px" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr align="center">
				<td>
					<table width="80%"  border='0' style="background: white;border-spacing: 10px;padding:5px;">
						<tr>
							<th colspan='2' align='center' class='fund3header'>ADD NEW PROJECT<br /></th>
						</tr>
						<tr>
							<th><br /></th>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Project Name</td>
							<td>
								<input type='text' id='projectname' size='100' title='Recommended to use name on Funding Letter' name='projectname' required>
								<br />
							</td>
						</tr>
						<tr>
							<td colspan='2'  nowrap  class='fund3formtext'>Project Region</td>
						</tr>
						<tr>
							<td colspan='2' align='center'>
							<table border='0'>
								<tr>
									<td>
										Eastern&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>
										<input type='checkbox' id='chkeastern' name='chkeastern'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<div id='diveastern' style='display: none;'>$<input type='text' id='easternbudget'  name='easternbudget' title='Numeric Values Only'></div>
										<br />
									</td>
								</tr>							
								<tr>
									<td >
										Central&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>	
										<input type='checkbox' id='chkcentral' name='chkcentral'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<div id='divcentral' style='display: none;'>$<input type='text' id='centralbudget'  name='centralbudget' title='Numeric Values Only'></div>
										<br />
									</td>
								</tr>
								<tr>
									<td>
										Western&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>
										<input type='checkbox' id='chkwestern' name='chkwestern'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<div id='divwestern' style='display: none;'>$<input type='text' id='westernbudget'  name='westernbudget' title='Numeric Values Only'></div>
										<br />
									</td>
								</tr>
								<tr>
									<td>
										Labrador&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>
										<input type='checkbox' id='chklabrador' name='chklabrador'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<div id='divlabrador' style='display: none;'>$<input type='text' id='labradorbudget'  name='labradorbudget' title='Numeric Values Only'></div>
										<br />
									</td>
								</tr>
								<tr>
									<td>
										Provincial&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>
										<input type='checkbox' id='chkprovincial' name='chkprovincial' onchange='populateschools()'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<div id='divprovincial' style='display: none;'>$<input type='text' id='provincialbudget'  name='provincialbudget' title='Numeric Values Only'></div>
										<br />
									</td>
								</tr>																								
							</table>
							</td>
						</tr>
						<tr>
							<td  colspan='2' class='fund3formtext'>School Budget(s)</td>

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
										<table class='dynatable' id="dynatable">
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
													<td class="displayTextMed"><input type="hidden" name="id[]" value="" class="id" readonly  style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="school[]" value="" class="school" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="budget[]" value="" class="budget" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="schoolid[]"  class="schoolid" value="" readonly style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="remove">Remove</button>
												</tr>
											
										</table>
									</td></tr>
									<tr><td colspan='3' class="displayTextMed"><hr /></td></tr>
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
										<td valign="bottom">
											<select id="lstpartners" style="max-width:75%;">
												<option value='-1'>Please Select</option>
												<c:forEach var="item" items="${items}" >
                									<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            									</c:forEach>
											</select>
										</td>
										<td valign="bottom">
											<input type='text' id='contactname' name='contactname' title="Contact's Name">
										</td>
										<td valign="bottom">
											<input type='text' id='contactemail' name='contactemail' title="Contact's Email">
										</td>
										<td valign="bottom">
											<input type='text' id='contactphone' name='contactphone' class='contactphone' title="Contact's Phone">
										</td>
										<td valign="bottom"><button class="addf">Add</button></td>
									</tr>
									<tr><td colspan='5'><hr /></td></tr>
									<tr><td colspan='5' align="center">
										<table class="dynatablef" id="dynatablef">
											<thead>
												<tr>
													<th class="fund3formtext"></th>
													<th class="fund3formtext">Partner</th>
													<th class="fund3formtext">Contact</th>
													<th class="fund3formtext">Email</th>
													<th class="fund3formtext">Phone</th>
													<th class="fund3formtext"></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypef">
													<td class="displayTextMed"><input type="text" name="idf[]" value="0" class="idf" readonly style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="partner[]" value="" class="partner" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="contact[]" value="" class="contact" readonly  style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="email[]"  class="email" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="phone[]"  class="phone" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="partnerid[]"  class="partnerid" value="" readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removef">Remove</button>
												</tr>
											
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
               									<option value="${positem.id}">${fn:toUpperCase(positem.ddText)}</option>
           									</c:forEach>
										</select>	
								</td>
						</tr>												
						<tr>
							<td nowrap class='fund3formtext'>Date Funding Approved</td>
							<td>
								<input type='text' id='datefundingapproved' title='Date of the funding letter or email(if no date identified, approximate date to be selected)' name='datefundingapproved' required>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Project Start Date</td>
							<td>
								<input type='text' id='projectstartdate' title='For when funding is identified for a specific timeframe, Example: 2016-2107 school year, or multi-year project)' name='projectstartdate'>
								To Be Determine&nbsp&nbsp&nbsp&nbsp&nbsp<input type='checkbox' id='chktbdstart' name='chktbdstart'>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Project End Date</td>
							<td>
								<input type='text' id='projectenddate' title='For when funding is identified for a specific timeframe, Example: 2016-2107 school year, or multi-year project)' name='projectenddate'>
								To Be Determine&nbsp&nbsp&nbsp&nbsp&nbsp<input type='checkbox' id='chktbdend' name='chktbdend'>
								<br />
							</td>
						</tr>
						<tr>
							<td nowrap class='fund3formtext'>Category</td>
								<td>
										<select id="lstcategory" name="lstcategory"  style="max-width:50%;">
											<option value='-1'>Please Select</option>
											<c:forEach var="catitem" items="${catitems}" >
               									<option value="${catitem.id}">${fn:toUpperCase(catitem.ddText)}</option>
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
               									<option value="${positem.id}">${fn:toUpperCase(positem.ddText)}</option>
           									</c:forEach>
										</select>	
								</td>
						</tr>
						<tr><td colspan="2"><br /></td></tr>
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
											<input type='text' id='employeeemailpr'  name='employeeemailp'><span class='spanemail'>@nlesd.ca</span>
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
													<th  class="fund3formtext"></th>
													<th  class="fund3formtext">Region</th>
													<th  class="fund3formtext">Name</th>
													<th  class="fund3formtext">Email</th>
													<th  class="fund3formtext">Phone</th>
													<th  class="fund3formtext"></th>
													<th  class="fund3formtext"></th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypepr">
													<td class="displayTextMed"><input type="text" name="idpr[]" value="0" class="idpr" readonly readonly style="width:15px;border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="regionspr[]" value="" class="regionspr" readonly readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="namepr[]" value="" class="namepr" readonly readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="emailpr[]"  class="emailpr" value="" readonly readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="text" name="phonepr[]"  class="phonepr" value="" readonly readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><input type="hidden" name="eregionid[]"  class="eregionid" value="" readonly readonly style="border: none; background-color: transparent;"/></td>
													<td class="displayTextMed"><button class="removepr">Remove</button>
												</tr>
										</table>
									</td></tr>
									<tr><td colspan='5'><hr /></td></tr>										
								</table>
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Description of Project</td>
							<td>
								<textarea rows="4" cols="50" id='projectdescription'  name='projectdescription' required></textarea>
								<br />
							</td>
						</tr>
						<tr><td colspan="2"><br /></td></tr>
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
											<input type='text' id='expensedes' name='expensedes' style="width:500px;">
										</td>
										<td><button class="adde">Add</button></td>
									</tr>
									<tr><td colspan='3'><hr /></td></tr>
									<tr><td colspan='3' align="center">
										<table class="dynatablee" id="dynatablee" cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<th colspan='3' class="fund3formtext">Expense Description</th>
												</tr>
											</thead>
											<tbody>
												<tr class="prototypee">
													<td align="left"><input type="text" name="ide[]" value="0" class="ide" readonly style="width:10px;border: none; background-color: transparent;"/></td>
													<td align="left"><input type="text" name="expense[]" value="" class="expense" readonly style="width:500px;border: none; background-color: transparent;"/></td>
													<td><button class="removee">Remove</button>
												</tr>
											
										</table>
									</td></tr>
									<tr><td colspan='3'><hr /></td></tr>
								</table>
								<br />
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Special Requirements</td>
							<td>
								<textarea rows="4" cols="50" id='specialreq'  name='specialreq' required></textarea>
								<br />
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Report Required</td>
							<td>
								<INPUT TYPE="radio" NAME="reportreq" VALUE="1" checked>Yes
								<INPUT TYPE="radio" NAME="reportreq" VALUE="0">No
							</td>
						</tr>
						<tr>
							<td  nowrap class='fund3formtext'>Date of first report to be submitted</td>
							<td><input type='text' id='firstreportdate'  name='firstreportdate'></td>
						</tr>
						<tr>
							<td class='fund3formtext'>Report Frequency</td>
							<td>
								<select id="lstreportfreq" name="lstreportfreq"   style="max-width:50%;">
									<option value='-1'>Please Select</option>
										<c:forEach var="freqitem" items="${freqitems}" >
               								<option value="${freqitem.id}">${fn:toUpperCase(freqitem.ddText)}</option>
           								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Report Required to be Sent to Whom</td>
								<td><input type='text' id='reportsentto'  name='reportsentto'><span class='spanemail'>@nlesd.ca</span></td>
						</tr>																		
						<tr>
							<td align="center" colspan='2'><input type="submit" value="ADD NEW PROJECT" class="btnExample">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="CANCEL" onclick="javascript:window.location='index.jsp';" class="btnExample"></td>	
						</tr>			
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
		<script>
		//$("#pol_cat_frm").validate();
		</script>
	</body>
</html>