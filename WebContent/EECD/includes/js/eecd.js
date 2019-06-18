/*******************************************************************************
 * Opens dialog for adding new document
 ******************************************************************************/
function openaddnewdialog() {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$('#myModal').modal(options);

}
function addnewarea(){
	$('#walert').css("display", "none");
	if($('#areadescription').val() == "" || $('#areadescription').val() == null){
		$('#errMsg').css("display", "block").text("ERROR: Please add valid Area Description.").delay(5000).fadeOut();
	}else{
		addAreaAjax($('#areadescription').val());
	}
		
	
}
/*******************************************************************************
 * Calls ajax post for adding new area description
 ******************************************************************************/
function addAreaAjax(adescription) {
	$.ajax({
		url : 'addAreaDescriptionAjax.html',
		type : 'POST',
		data : {
				areadescription : adescription
		},success : function(xml) {
			$(xml).find('CAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "ADDED") {
							//add new row to table
							var testrow="";
							testrow ="<tr id='div" + $(this).find("ID").text() +"'>"; 
							testrow += "<td>" + adescription +"</td>";    
							testrow += "<td>";
							testrow += "<a href='viewArea.html?id=" + $(this).find("ID").text() + "' class='btn btn-primary btn-xs' style='color:white;'>VIEW</a> ";
							testrow += "<a href='#' class='btn btn-danger btn-xs' style='color:white;'" + " onclick=\"opendeletedialog('" + $(this).find("ID").text() +"','" +
							adescription + "');\">DELETE</a>";
							testrow += "</td></tr>";
							$('#eecdlist').append(testrow);
							//populate success message
							$('#successMsg').text("SUCCESS: Area description added.").css("display", "block").delay(5000).fadeOut();							
							//close popup
							$('#myModal').modal('hide');
						} else {
							//show error message
							$('#errMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();							
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$('#errMsg').text(error).css("display", "block").delay(5000).fadeOut();			
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for adding new area description
 ******************************************************************************/
function deleteAreaAjax(areaid) {
	$.ajax({
		url : 'deleteAreaAjax.html',
		type : 'POST',
		data : {
				aid: areaid
		},success : function(xml) {
			$(xml).find('CAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "DELETED") {
							//populate success message
							$('#successMsg').text("SUCCESS: Area successfully deleted.").css("display","block").delay(5000).fadeOut();								
							//close popup
							$('#myModald').modal('hide');
						} else {
							//show error message
							$('#errMsgd').text($(this).find("MESSAGE").text()).css("display","block").delay(5000).fadeOut();						
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$('#errMsgd').text(error).css("display","block").delay(5000).fadeOut();				
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for delete area
 ******************************************************************************/
function opendeletedialog(aid,adescription) {
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	$("#hiddenid").val(aid);
	$("#spantitle1d").text("Are you sure you would like to delete the following area?");
	$("#spantitle2d").text(adescription);
	// now we add the onclick event
	$("#btndelete").click(function() {
		deleteAreaAjax(aid);
		var removediv="#div" + aid;
		$(removediv).remove();
	});
	$('#myModald').modal(options);

}
/*******************************************************************************
 * Opens dialog for approving/declining
 ******************************************************************************/
function openapprovedialog(taid,tname,adescription,ttype) {
	var tstatus=-1;
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	if(ttype == "A"){
		$("#maintitlespan").text("Approve Teacher Area");
		tstatus=2;
	}else{
		$("#maintitlespan").text("Decline Teacher Area");
		tstatus=3;
	}
	$('#textnotes').val("");
	$("#spantitle1d").text(tname);
	$("#spantitle2d").text(adescription);
	$('#myModal').modal(options);
	// now we add the onclick event
	$('#btnok').unbind('click').bind('click', function (e) {
		approvedeclinearea(taid,tstatus);
		});
	//$("#btnok").click(function() {
		//approvedeclinearea(taid,tstatus);
		//var removediv="#div" + aid;
		//$(removediv).remove();
	//});

}
/*******************************************************************************
 * Calls ajax post for approving/declining teacher areas
 ******************************************************************************/
function approvedeclinearea(taid,tstatus) {
	var statusnotes = $('#textnotes').val();
	$.ajax({
		url : 'approveDeclineAjax.html',
		type : 'POST',
		data : {
				aid: taid, astatus:tstatus, snotes:statusnotes
		},success : function(xml) {
			$(xml).find('TAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "UPDATED") {
							//populate success message
							$('#successMsg').text("SUCCESS: Area has been updated.").css("display", "block").delay(5000).fadeOut();							
							//close popup
							$('#myModal').modal('hide');
							//reomve row
							var removediv="#div" + taid;
							$(removediv).remove();
						} else {
							//show error message
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();							
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();			
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for adding to shortlist
 ******************************************************************************/
function openaddtoshortlistdialog(taid,tname,adescription,sname) {
	var tstatus=-1;
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	
	$("#maintitlespan").text("Add Teacher To Shortlist Area");
	$("#spantitle1d").text(tname);
	$("#spantitle2d").text(sname);
	$("#spantitle3d").text(adescription);
	$('#myModal').modal(options);
	// now we add the onclick event
	$('#btnok').unbind('click').bind('click', function (e) {
		addtoshortlist(taid);
		});
	//$("#btnok").click(function() {
		//approvedeclinearea(taid,tstatus);
		//var removediv="#div" + aid;
		//$(removediv).remove();
	//});

}
/*******************************************************************************
 * Calls ajax post for approving/declining teacher areas
 ******************************************************************************/
function addtoshortlist(taid) {
	$.ajax({
		url : 'addToShortlistAjax.html',
		type : 'POST',
		data : {
				aid: taid
		},success : function(xml) {
			$(xml).find('TAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "ADDED") {
							//populate success message
							$('#successMsg').text("Teacher Added To Shortlist.").css("display", "block").delay(5000).fadeOut();							
							//close popup
							$('#myModal').modal('hide');
							//reomve row
							var removediv="#div" + taid;
							$(removediv).remove();
						} else {
							//show error message
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();								
						}

					});
		},
		error : function(xhr, textStatus, error) {
			
			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();				
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Opens dialog for removing from shortlist
 ******************************************************************************/
function openremovefromshortlistdialog(taid,tname,adescription,sname) {
	var tstatus=-1;
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	
	$("#maintitlespan").text("Remove Teacher From Shortlist");
	$("#spantitle1d").text(tname);
	$("#spantitle2d").text(sname);
	$("#spantitle3d").text(adescription);
	$('#myModal').modal(options);
	// now we add the onclick event
	$('#btnok').unbind('click').bind('click', function (e) {
		removefromshortlist(taid);
		});
	//$("#btnok").click(function() {
		//approvedeclinearea(taid,tstatus);
		//var removediv="#div" + aid;
		//$(removediv).remove();
	//});

}
/*******************************************************************************
 * Calls ajax post for approving/declining teacher areas
 ******************************************************************************/
function removefromshortlist(taid) {
	$.ajax({
		url : 'removeFromShortlistAjax.html',
		type : 'POST',
		data : {
				aid: taid
		},success : function(xml) {
			$(xml).find('TAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "REMOVED") {
							//populate success message
							$('#successMsg').text("Teacher Removed From Shortlist.").css("display", "block").delay(5000).fadeOut();								
							//close popup
							$('#myModal').modal('hide');
							//reomve row
							var removediv="#div" + taid;
							$(removediv).remove();
						} else {
							//show error message
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();
							
						}

					});
		},
		error : function(xhr, textStatus, error) {			
			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();			
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for approving/declining teacher areas
 ******************************************************************************/
function markshortlistcomplete(taid, ttype) {
	$.ajax({
		url : 'shortlistCompletedAjax.html',
		type : 'POST',
		data : {
				aid: taid, stype: ttype
		},success : function(xml) {
			$(xml).find('TAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "COMPLETED") {
							//populate success message
							if(ttype == "C"){
								$('#successMsg').text("Shortlist marked completed.").css("display", "block").delay(5000).fadeOut();								
								//hide button
								$('#divcomplete').hide();
								$('#divunlock').show();
								$('#divremove').hide();
							}else{
								$('#successMsg').text("Shortlist unlocked.").css("display", "block").delay(5000).fadeOut();
								//hide button
								$('#divunlock').hide();
								$('#divcomplete').show();
								$('#divremove').show();
							}
							
						} else {
							//show error message
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();			
		},
		dataType : "text",
		async : false

	});

}