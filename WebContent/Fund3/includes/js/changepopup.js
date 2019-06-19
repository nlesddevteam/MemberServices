	//open add new dropdown item
	function OpenPopUpAdd()
	{
		$('#headertitle').html('ADD NEW ITEM');
		$("#dropdowntext").val("");
		$("#dropdownstatus").val(1);
		$("#btnadd").val('ADD');
		$("#hidid").val($("#dropdown").val());
		//show box
			$(document).ready(function() {
				$('.fancybox').fancybox();
			});

	}
	//open edit dropdown item	
	function OpenPopUpEdit()
	{
		
		$('#headertitle').html('EDIT ITEM');
		$("#btnadd").val('UPDATE');
		if( $("#dropdownitems").val() == null )
    	{
    		alert("PLEASE SELECT DROPDOWN ITEM");
    		return false;
    	}else{

			$(document).ready(function() {
				$('.fancybox').fancybox();
			});
		}

	}
	//open delete dropdown item	
	function OpenPopUpDelete()
	{
		if( $("#dropdownitems").val() == null )
    	{
    		alert("PLEASE SELECT DROPDOWN ITEM");
    		return false;
    	}else{

			$(document).ready(function() {
				$('.fancybox').fancybox();
			});
		}

	}
	//open add new document
	function OpenPopUpAddDoc()
	{
		var projectname=$("#lstproject option:selected").text();
		$("#projectname").text(projectname);
		$('#modaladd').modal('show');
	}
	//open edit document
	function OpenPopUpAddDocE()
	{
		$(document).ready(function() {
			$('.fancybox').fancybox();
		});
		var projectname=$.trim($('#projectname').val());
		$("#projectnamed").text(projectname);
		
	}
	//function to determine if new or update function called for dropdown item
	function ChooseButtonAction()
	{
		var test = $("#btnadd").val();
		if(test == "ADD")
		{
			addNewDropdownItem();
		}else{
			updateDropdownItem();
		}
	}
	//close fancybox on all screens
    function closewindow()
    {
    	//blank fields
    	$.fancybox.close(); 
    }
    //returns dropdown items after dropdown parent is selected in dropdown
    function ajaxRequestInfo()
    {
    	
    	var isvalid=false;
    	if( $("#dropdown").val()== 0 )
    	{
    		alert("PLEASE SELECT DROPDOWN");
    	}else{
    		var id = $("#dropdown").val();
    		$("#hidid").val(id);
    		
    		$.ajax(
     			{
     				type: "POST",  
     				url: "getDropdownItems.html",
     				data: {
     					ddid: id
     				}, 
     				success: function(xml){
     					$("#dropdownitems").empty();
     					$(xml).find('ITEM').each(function(){
     						//now add the items if any

     						var option = new Option($(this).find("TEXT").text(), $(this).find("VALUE").text());
     						$("#dropdownitems").append($(option));

     					});     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    	}
    	return isvalid;
    }
    //gets an individual dropdown item for editing
    function getDropdownItem()
    {
    	var isvalid=false;
    	if( $("#dropdownitems").val() == null )
    	{
    		alert("PLEASE SELECT DROPDOWN ITEM");
    	}else{
    		var id = $("#dropdownitems").val();
    		
    		$.ajax(
     			{
     				type: "POST",  
     				url: "getDropdownItem.html",
     				data: {
     					ddid: id
     				}, 
     				success: function(xml){
     					
     					$(xml).find('ITEM').each(function(){
     						//now populate the values
     						
     						$("#dropdowntext").val($(this).find("TEXT").text());
     						$("#dropdownstatus").val($(this).find("ISACTIVE").text());
     						$("#hiditid").val($(this).find("VALUE").text());
     						$("#deleteitem").text($(this).find("TEXT").text());


     					});     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    	}
    	return isvalid;
    }
    //sends new dropdown item to server
    function addNewDropdownItem()
    {
    	
			var ddtextv = $("#dropdowntext").val();
			var ddstatusv = $("#dropdownstatus").val();
			var hididv = $("#hidid").val();
			
			
    		if(ddtextv == "")
    		{
    			alert("PLEASE ENTER DROPDOWN TEXT");
    			return false;
    		}else{
    			
        		
        		$.ajax(
         			{
         				type: "POST",  
         				url: "addNewDropdownItem.html",
         				data: {
         					ddtext: ddtextv, isactive: ddstatusv, ddid: hididv
         				}, 
         				success: function(xml){
         					
         					
         					if($(this).find("MESSAGE").text() == "")
         					{
         						$("#dropdownitems").empty();
               					$(xml).find('ITEM').each(function(){
             						//now add the items if any

             						var option = new Option($(this).find("TEXT").text(), $(this).find("VALUE").text());
             						$("#dropdownitems").append($(option));

             					});
               					closewindow();
         					}else{
         						alert("ERROR ADDING NEW ITEM");
         						return false;
         					}
     					
         				},
         				  error: function(xhr, textStatus, error){
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		}

    	return true;
    	
    }
    //update dropdown item with new values
    function updateDropdownItem()
    {
    	
			var ddtextv = $("#dropdowntext").val();
			var ddstatusv = $("#dropdownstatus").val();
			var hididv = $("#hidid").val();
			var hiditidv = $("#hiditid").val();
			
    		if(ddtextv == "")
    		{
    			alert("PLEASE ENTER DROPDOWN TEXT");
    			return false;
    		}else{
    			
        		
        		$.ajax(
         			{
         				type: "POST",  
         				url: "updateDropdownItem.html",
         				data: {
         					ddtext: ddtextv, isactive: ddstatusv, ddid: hididv, itemid: hiditidv
         				}, 
         				success: function(xml){
         					
         					
         					if($(this).find("MESSAGE").text() == "")
         					{
         						$("#dropdownitems").empty();
               					$(xml).find('ITEM').each(function(){
             						//now add the items if any

             						var option = new Option($(this).find("TEXT").text(), $(this).find("VALUE").text());
             						$("#dropdownitems").append($(option));

             					});
               					alert('ITEM UPDATED');
               					closewindow();
         					}else{
         						alert("ERROR UPDATING ITEM");
         						return false;
         					}
     					
         				},
         				  error: function(xhr, textStatus, error){
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		}

    	return true;
    	
    }
    //delete dropdown item from the database
    function deleteDropdownItem()
    {
    		var hididv = $("#hidid").val();
			var hiditidv = $("#hiditid").val();
			
    			
        		
        		$.ajax(
         			{
         				type: "POST",  
         				url: "deleteDropdownItem.html",
         				data: {
         					ddid: hididv, id: hiditidv
         				}, 
         				success: function(xml){
         					
         					
         					if($(this).find("MESSAGE").text() == "")
         					{
         						$("#dropdownitems").empty();
               					$(xml).find('ITEM').each(function(){
             						//now add the items if any

             						var option = new Option($(this).find("TEXT").text(), $(this).find("VALUE").text());
             						$("#dropdownitems").append($(option));

             					});
               					alert('ITEM DELETED');
               					closewindow();
         					}else{
         						alert("ERROR UPDATING ITEM");
         						return false;
         					}
     					
         				},
         				  error: function(xhr, textStatus, error){
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		

    	return true;
    	
    }
    //clears tables on all screens
    function cleartable()
    {
    	$('#showlists td').parent().remove();
    }
    //get list of schools to populate dropdowns
    function getschoolschecklist(regionid)
    {
    			$.ajax(
         			{
         				type: "POST",  
         				url: "getSchools.html",
         				data: {
         					ddid: regionid
         				}, 
         				success: function(xml){
         					var option="";
         					$("#lstschools").empty();
         					$("#lstschools").append($(option));
               				$(xml).find('SCHOOL').each(function(){
             					//now add the items if any
               					if($(this).find("MESSAGE").text() == "LISTFOUND")
               					{
                 					//var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
               						option =option + "<option value='" + $(this).find("SCHOOLID").text() + "'>" + $(this).find("SCHOOLNAME").text() + "</option>";
               					}else{
                 					alert("ERROR RETRIEVING SCHOOLS");
                 					return false;
               					}


             				});
         					  $("#lstschools").append(option);
         					  $('#lstschools').multiselect('rebuild');	
     					
         				},
         				  error: function(xhr, textStatus, error){
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		

    	return true;
    	
    }
    //get school list for downdowns
    function getschools(regionid)
    {
    	$.ajax(
         			{
         				type: "POST",  
         				url: "getSchools.html",
         				data: {
         					ddid: regionid
         				}, 
         				success: function(xml){
         					//$("#lstschools").empty();
         					//$("#lstschools").append($(option));
               				$(xml).find('SCHOOL').each(function(){
             					//now add the items if any
               					if($(this).find("MESSAGE").text() == "LISTFOUND")
               					{
               						
                 					var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
               						$("#lstschools").append(option);
               					  //$("#lstschools").append(new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text()));
               					  //$('#lstschools').multiselect('rebuild');
                 				}else{
                 					alert("ERROR RETRIEVING SCHOOLS");
                 					return false;
               					}


             				});
               					
     					
         				},
         				  error: function(xhr, textStatus, error){
         				      alert(xhr.statusText);
         				      alert(textStatus);
         				      alert(error);
         				  },
         				dataType: "text",
         				async: false
         			}
         		);   			
    		

    	return true;
    	
    }
    //populate school list based on checkbox list
    function populateschools(){
    	var regions="";
		var eastchk = document.getElementById("chkeastern").checked;
		var westchk = document.getElementById("chkwestern").checked;
		var centchk = document.getElementById("chkcentral").checked;
		var labchk = document.getElementById("chklabrador").checked;
		var provchk = document.getElementById("chkprovincial").checked;
		if(eastchk){
			regions="1";
		}
		if(westchk){
			if(regions.length == 0){
				regions="3";
			}else{
				regions = regions + ",3";
			}
		}
		if(centchk){
			if(regions.length == 0){
				regions="2";
			}else{
				regions = regions + ",2";
			}
		}
		if(labchk){
			if(regions.length == 0){
				regions="4";
			}else{
				regions = regions + ",4";
			}
		}
		if(provchk){
			//show all schools
			regions="1,2,3,4";
		}
		if(regions.length > 0){
			$("#lstschools").empty();
			getschools(regions);
		}else{
			$("#lstschools").empty();
		}
	
}
function populateschoolschecklist(){

	var regions="";
    $("#lstregion option:selected").each(function(){
        var optionText = $(this).text();
    	if(optionText ==  "EASTERN"){
    		regions="1";
    	}
    	if(optionText ==  "WESTERN"){
    		if(regions.length == 0){
    			regions="3";
    		}else{
    			regions = regions + ",3";
    		}
    	}
    	if(optionText ==  "CENTRAL"){
    		if(regions.length == 0){
    			regions="2";
    		}else{
    			regions = regions + ",2";
    		}
    	}
    	if(optionText ==  "LABRADOR"){
    		if(regions.length == 0){
    			regions="4";
    		}else{
    			regions = regions + ",4";
    		}
    	}
    	if(optionText ==  "PROVINCIAL"){
    		//show all schools
    		regions="1,2,3,4";
    	}

    });
    if(regions.length > 0){
		$("#lstschools").empty();
		getschoolschecklist(regions);
	}else{
		$("#lstschools").empty();
	}
	
}
//get list of project documents
function ajaxGetFiles()
{
	cleartable();
	var projectid = $('#lstproject').val();
	$('#projectid').val(projectid);
	var isvalid=false;
	$.ajax(
 			{
 				type: "POST",  
 				url: "getProjectFiles.html",
 			
 				data: {
 					pid: projectid
 				}, 
 				success: function(xml){
 					var i=1;
 					$(xml).find('DOCUMENT').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "LISTFOUND")
 								{
 								
 									var color="FFFFFF";
 									if(i % 2 == 0){
 										color="#E0E0E0";
 									}
 									var newrow="<tr style='background-color:" + color + ";' id='" + $(this).find("ID").text() + "'>";
									newrow += "<td class='displayTextTD1'><a href='./documents/" + $(this).find("OFILENAME").text()+ "' target='_blank'>" +  $(this).find("FILENAME").text() + "</a></td>";
		                            newrow += "<td class='displayTextTD1'>" + $(this).find("ADDEDBY").text() + "</td>";
                                    newrow += "<td class='displayTextTD1'>" + $(this).find("DATEADDED").text() + "</td>";
                                    newrow += "<td class='displayTextTD1' align='center'>";
                                    newrow += "<a href='' onclick=\"ajaxDeleteFile('" +$(this).find("ID").text() + "','" + $(this).find("OFILENAME").text() + "','" + projectid +  "','" + $(this).find("FILENAME").text() + "');return false;\" >Delete File</a>";
                                    $('table#showlists tr:last').after(newrow);
									i=i+1;
									isvalid=true;
	                   				
 								}else{
 									var color="FFFFFF";
 									if(i % 2 == 0){
 										color="#E0E0E0";
 									}
									var newrow="<tr style='background-color:" + color + ";'>";
                                    newrow += "<td class='displayTextTD1' colspan='4'>NO DOCUMENTS FOUND</td></tr>";
                                    $('table#showlists tr:last').after(newrow);
									i=i+1;
									isvalid=true;
 									
 								}
						});
					},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	return isvalid;
}
//clear table on all forms
function cleartable()
{
	$('#showlists td').parent().remove();
}
//add new project documents
function ajaxAddFile()
{
	if(! checkfields()){
		return false;
	}
	var isvalid=false;
	var documentname = $.trim($('#documentname').val());
	var pid = $.trim($('#projectid').val());
	var ufile = $('#newdocument')[0].files[0];
	//now we loop through regions
	var regions ="";
    var selected = $("#lstregion option:selected");
    selected.each(function () {
        if(regions == ""){
        	regions=$(this).val();
        }else{
        	regions = regions + "," + $(this).val();
        }
    });	
    var requestd = new FormData();
	requestd.append('documentname',documentname);
	requestd.append('projectid',pid);
	requestd.append('regions',regions);
	requestd.append('newdocument',ufile);
	//mimeType:"multipart/form-data",
	
    $.ajax({
        url: "addNewDocument.html",
        type: 'POST',
        data:  requestd,
        contentType: false,
        cache: false,
        processData:false,
        success: function(xml)
        {
        		$('#spansuccess').html('Document has been added.');
        		$('#divsuccess').show();
				var i=1;
				cleartable();
				$(xml).find('DOCUMENT').each(function(){
						
						if($(this).find("MESSAGE").text() == "LISTFOUND")
							{
							
								var color="FFFFFF";
								if(i % 2 == 0){
									color="#E0E0E0";
								}
							var newrow="<tr style='background-color:" + color + ";' id='" + $(this).find("ID").text() + "'>";
							newrow += "<td class='displayText'><a href='./documents/" + $(this).find("OFILENAME").text()+ "' target='_blank'>" +  $(this).find("FILENAME").text() + "</a></td>";
                            newrow += "<td class='displayText'>" + $(this).find("ADDEDBY").text() + "</td>";
                            newrow += "<td class='displayText'>" + $(this).find("DATEADDED").text() + "</td>";
                            newrow += "<td class='displayText'>";
                            newrow += "<a href='' onclick=\"ajaxDeleteFile('" +$(this).find("ID").text() + "','" + $(this).find("OFILENAME").text() + "','" + projectid +  "','" + $(this).find("FILENAME").text() + "');return false;\" >Delete File</a>";
                            $('table#showlists tr:last').after(newrow);
							i=i+1;
							isvalid=true;
               				
							}else{
								var color="FFFFFF";
								if(i % 2 == 0){
									color="#E0E0E0";
								}
							var newrow="<tr style='background-color:" + color + ";'>";
                            newrow += "<td class='displayText' colspan='4'>NO DOCUMENTS FOUND</td></tr>";
                            $('table#showlists tr:last').after(newrow);
							i=i+1;
							isvalid=true;
								
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
    $('#modaladd').modal('toggle');
	return isvalid;
}
//close fancybox screen
function closewindow()
{
	//blank fields
	$.fancybox.close(); 
}
//check mandatory fields on new dropdown
function checkfields()
{
	$('#divadddoc').hide();
	var documentname = $.trim($('#documentname').val());
	var selected = $("#lstregion option:selected");
	var pfile = $.trim($('#newdocument').val());
	if(documentname == "")
		{
			$('#spanadddoc').html("PLEASE ENTER VALUE FOR DOCUMENT NAME");
			$('#divadddoc').show();
			return false;
		}
	if(selected.length < 1)
	{
		$('#spanadddoc').html("PLEASE SELECT REGION(S)");
		$('#divadddoc').show();
		return false;
	}
	
	if(pfile ==  "")
	{
		$('#spanadddoc').html("PLEASE SELECT DOCUMENT");
		$('#divadddoc').show();
		return false;
	}
	
	
	return true;
	
	
	
}
//delete project document
function ajaxDeleteFile(vid,vfid,vpid,vfname)
{
	$.ajax(
 			{
 				type: "POST",  
 				url: "ajaxDeleteProjectDocument.html",
 			
 				data: {
 					id: vid,fid: vfid,pid: vpid, fname: vfname
 				}, 
 				success: function(xml){
 					$(xml).find('DOCUMENT').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "FILEDELETED")
 								{
 									$('table#showlists  tr#' + vid).remove();
 									$('#spansuccess').html('Document has been deleted.');
 					        		$('#divsuccess').show();
									isvalid=true;
								}
 							else{
 								$('#spanerror').html('Document has been deleted.');
					        	$('#diverror').show();
 							}
						});
					},
 				  error: function(xhr, textStatus, error){
 				     $('#spanerror').html(xhr.statusText + "<br />" + textStatus + "<br />" + error );
 				     $('#diverror').show();
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	return isvalid;
}
//update project status to approve/rejected
function ajaxUpdateProjectStatus()
{
	var vpid = $('#id').val();
	var selected = $("#lstProjectStatus option:selected").val();
	var vsnotes= $.trim($('#statusnotes').val());
	$.ajax(
 			{
 				type: "POST",  
 				url: "ajaxUpdateProjectStatus.html",
 			
 				data: {
 					pid: vpid, pstatus: selected, snotes: vsnotes
 				}, 
 				success: function(xml){
 					$(xml).find('PROJECTSTATUS').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "STATUSUPDATED")
 								{
 									alert("Status Updated");
 									//hide button
 									if(selected == 1){
 										$('#viewapprove').hide();
 									}
								}
 							else{
 								alert($(this).find("MESSAGE").text() );
 							}
						});
					},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	return true;
}
//update project number with sds number
function ajaxUpdateProjectNumber()
{
	var vpid = $('#id').val();
	var vpnumber= $.trim($('#txtprojectnumber').val());
	$.ajax(
 			{
 				type: "POST",  
 				url: "ajaxUpdateProjectNumber.html",
 			
 				data: {
 					pid: vpid, pnumber: vpnumber
 				}, 
 				success: function(xml){
 					$(xml).find('PROJECTNUMBER').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "NUMBERUPDATED")
 								{
 									alert("Project Number Updated");
 									$('#projectnumber').val(vpnumber);

								}
 							else{
 								alert($(this).find("MESSAGE").text() );
 							}
						});
					},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	return true;
}
//get list of funding contacts for region
function getfundingcontacts(regionid)
{
			$.ajax(
     			{
     				type: "POST",  
     				url: "ajaxGetFundingContacts.html",
     				data: {
     					ddid: regionid
     				}, 
     				success: function(xml){
     					var option="";
     					$("#lstsponsor").empty();
     					$("#lstsponsor").append($(option));
           				$(xml).find('CONTACT').each(function(){
         					//now add the items if any
           					if($(this).find("MESSAGE").text() == "LISTFOUND")
           					{
             					//var option = new Option($(this).find("SCHOOLNAME").text(), $(this).find("SCHOOLID").text());
           						option =option + "<option value='" + $(this).find("CONTACTNAME").text() + "'>" + $(this).find("CONTACTNAME").text() + "</option>";
           					}else{
             					alert("ERROR RETRIEVING SCHOOLS");
             					return false;
           					}


         				});
     					  $("#lstsponsor").append(option);
     					  $('#lstsponsor').multiselect('rebuild');	
 					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);   			
		

	return true;
	
}
//populate funding contacts dropdown
function populatefundingcontacts(){

		var regions="";
		$("#lstfunding option:selected").each(function(){
			var optionVal=$(this).val();
			if(regions.length == 0)
			{
				regions = optionVal;
			}else{
				regions = regions + "," + optionVal;
			}
		});
	    if(regions.length > 0){
			$("#lstsponsor").empty();
			getfundingcontacts(regions);
		}else{
			$("#lstsponsor").empty();
		}
	
}
//delete custom report
function ajaxDeleteReport(vid)
{
	$.ajax(
 			{
 				type: "POST",  
 				url: "ajaxDeleteReport.html",
 			
 				data: {
 					reportid: vid
 				}, 
 				success: function(xml){
 					$(xml).find('REPORT').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "REPORTDELETED")
 								{
 									//$('table#showlists  tr#' + vid).remove();
									isvalid=true;
								}
 							else{
 								alert($(this).find("MESSAGE").text() );
 							}
						});
					},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	return isvalid;
}
//update the order the policies show on the screen
function updatePolicySortOrder()
{
		var pids="";
		$("#sortlist > option").each(function() {
		    if(pids == ""){
		    	pids=this.value;
		    }else{
		    	pids = pids + "," + this.value; 
		    }
		});
			$.ajax(
     			{
     				type: "POST",  
     				url: "ajaxUpdatePolicySortOrder.html",
     				data: {
     					sortorder: pids
     				}, 
     				success: function(xml){
     					$(xml).find('POLICY').each(function(){
         					//now add the items if any
           					if($(this).find("MESSAGE").text() == "SUCCESS")
           					{
           						$("#msgtext").text("Sort Order Updated.");
           					}else{
           						$("#msgtext").text($(this).find("MESSAGE").text());
             					return false;
           					}


         				});
     				
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);   			
		

	return true;
	
}
//open modal save report screen
function opensavecustomreport(){
	$('#myModal').modal('toggle');
}
function savecustomreport(){
		$("#pol_cat_frm").attr('action', 'saveCustomReport.html');
		$("#pol_cat_frm").submit();
		$('#myModal').modal('toggle');
		
}