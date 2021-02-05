function deleteconfirmed(){
	var ftype = $("#ftype").val();
	if(ftype == "S"){
		deletefile();
	}else if(ftype == "L" || ftype == "V" || ftype == "M"){
		deletelink();
	}
}

	$(document).ready(function(){    

				$('#multiselect').multiselect();
    		});
			function checkreviewfields(){
				if($("#reviewname").val() == ""){
					$(".msgerr").text("Please enter Review Name");
					$(".msgerr").show();
					$("#reviewname").focus();
					return false;
				}else{
					return true;
				}
			}


//sends new file info to server
function deletefile(){
	//all good, now we get the data for sending
	var fid = $("#fid").val();
	var filename = $("#fname").val();
	var ftype = $("#ftype").val();
	var requestd = new FormData();
	requestd.append('did', fid);
	requestd.append('dtype', ftype);
	requestd.append('filename', filename);
	//now we send the ajax request
	$.ajax({
		url : "deleteFile.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('FILE').each(
					function() {
						
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaldelete').modal('hide');
							if(ftype == "S"){
								$('#RS' + fid).remove();
								
							}
						
							
						} else {
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display","block").delay(3000).fadeOut();
			},
		dataType : "text",
		async : false
	});
}
//determines which button called and sets correct values
function openmodaldelete(fid,fname,ftype) {
	if(ftype == "S"){
		$('#modaltitleoptd').html("File Delete Confirmation");
		$('#modaldelete').modal('show');
		$("#fid").val(fid);
		$("#fname").val(fname);
		$("#ftype").val(ftype);
	}else if(ftype =="L"){
		$('#modaltitleoptd').html("Link Delete Confirmation");
		$('#modaldelete').modal('show');
		$("#fid").val(fid);
		$("#fname").val(fname);
		$("#ftype").val(ftype);
	}else if(ftype =="V"){
		$('#modaltitleoptd').html("Video Delete Confirmation");
		$('#modaldelete').modal('show');
		$("#fid").val(fid);
		$("#fname").val(fname);
		$("#ftype").val(ftype);
	}else if(ftype =="M"){
		$('#modaltitleoptd').html("Map Delete Confirmation");
		$('#modaldelete').modal('show');
		$("#fid").val(fid);
		$("#fname").val(fname);
		$("#ftype").val(ftype);
	}

	
}
//open add new section dialog
function openmodaladdsection() {
	
	$('#modaltitle').html("<i class='fas fa-plus'></i> Add New Section");
	CKEDITOR.instances.secdescription.setData('');
	//$("#secdescription").text("");
 	$("#sectitle").val("");
	$('#modaladd').modal('show');
}

//sends new file info to server
function addsection(){
	//all good, now we get the data for sending
	$("#moddivmsg").hide();
	var sectitle = $("#sectitle").val();
	var sectype = $("#sectype").val();
	var secstatus = $("#secstatus").val();
	var sectest = CKEDITOR.instances['secdescription'].getData();
	var reviewid=$("#id").val();
	var statustext = $( "#sectype option:selected" ).text();
	var requestd = new FormData();
	var secsortid = $("#secsortid").val();

	if(sectitle == ""){
		$("#modmsg").text("Please enter title");
		$("#moddivmsg").show();
		return;
	}
	if(sectest == ""){
		$("#modmsg").text("Please enter description");
		$("#moddivmsg").show();
		return;
	}
	if(secsortid == ""){
		$("#modmsg").text("Please enter sort number");
		$("#moddivmsg").show();
		return;
	}
	requestd.append('sectitle', sectitle);
	requestd.append('sectype', sectype);
	requestd.append('secstatus', secstatus);
	requestd.append('secdescription', sectest);
	requestd.append('secreviewid', reviewid);
	requestd.append('secsortid', secsortid);
	
	
	//now we send the ajax request
	$.ajax({
		url : "addNewSection.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			$(xml).find('RSECTION').each(
					function() {
						
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaladd').modal('hide');
							$(".msgok").html("SUCCESS: Section successfully added.").css("display","block").delay(3000).fadeOut();
							//show success message
							var newrow ="<tr id='RS" + $(this).find("SID").text() + "'>";
							//now we add each one to the table
							newrow += "<td width=7%' style='vertical-align:middle;'>" + secsortid + "</td>";
							newrow += "<td width='15%' style='vertical-align:middle;'>" + statustext + "</td>";
							newrow += "<td width='35%' style='vertical-align:middle;'>" + sectitle + "</td>";
							newrow += "<td width='8%' style='vertical-align:middle;text-align:center;'>0</td>";
							newrow += "<td width='15%' style='vertical-align:middle;'>" + $(this).find("USER").text() + "</td>"; 
							newrow += "<td width='10%' style='vertical-align:middle;background-color:#6495ED;color:White;text-align:center;'>NEW!</td>";
							newrow += "<td width='10%'' style='text-align:center;vertical-align:middle;'>";
							newrow += "<a class='btn btn-warning btn-xs' href='viewSchoolReviewSection.html?rid=" + $("#id").val() + "&sid=" + $(this).find("SID").text() + "'><i class='far fa-edit'></i> EDIT</a>";
							newrow += "<a href='#' class='btn btn-danger btn-xs' onclick=\"openmodaldeletereviewsection(\'" + $(this).find("SID").text() +  "\')\">" + "<i class='far fa-trash-alt'></i> DEL</a>";
							newrow += "</td>";
							newrow +="</tr>";
							$('table#showlists tr:first').after(newrow);
							//add row to section table
						
							
						} else {
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display","block").delay(3000).fadeOut();
			},
		dataType : "text",
		async : false
	});

}
// delete school review
function deleteschoolreview(){
	//all good, now we get the data for sending
	var requestd = new FormData();
	var rid =$("#currentid").val();
	requestd.append('rid', rid);
	//now we send the ajax request
	$.ajax({
		url : "deleteSchoolReview.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('REVIEW').each(
					function() {
						
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaldelete').modal('hide');
							$(".msgok").html("SUCCESS: Review has been deleted.").css("display", "block").delay(3000).fadeOut();
							$('#R' + rid).remove();
						
							
						} else {
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display","block").delay(3000).fadeOut();
			},
		dataType : "text",
		async : false
	});
}
//determines which button called and sets correct values
function openmodaldeletereview(rid) {
	$('#modaltitled').text("Delete Review Confirmation");
	$('#modaldelete').modal('show');
	$("#currentid").val(rid);
	
}
//delete school review
function deleteschoolreviewsection(){
	//all good, now we get the data for sending
	var requestd = new FormData();
	var rid =$("#currentid").val();
	requestd.append('sid', rid);
	//now we send the ajax request
	$.ajax({
		url : "deleteSchoolReviewSection.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {

			var i = 1;
			$(xml).find('REVIEW').each(
					function() {
						
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaldelete').modal('hide');
							$(".msgok").html("SUCCESS: Review Section has been deleted").css($('#RS' + rid).remove()).delay(3000).fadeOut();			
							$('#RS' + rid).remove();
						
							
						} else {
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display","block").delay(3000).fadeOut();
			},
		dataType : "text",
		async : false
	});
}
//determines which button called and sets correct values
function openmodaldeletereviewsection(rid) {
	$('#modaltitled').text("Delete Review Section Confirmation");
	$('#modaldelete').modal('show');
	$("#currentid").val(rid);
}
//open add new section file
function openmodaladdsectionfile() {
	$("#dtype").val("A");
	$("#fileid").val("");
	$("#filedate").val("");
	$("#filetitle").val("");
	$("#sfile").val("");				 
	$("#divfile").show();
	$("#divfileedit").hide();
	$('#modaltitle').text("Add New Section File");
	$('#modaladdfile').modal('show');
}
//sends new file info to server
function addsectionfile(){
	//check fields
	$("#moddivmsg").hide();
	if($("#filetitle").val() ==""){
		$("#modmsg").text("Please enter title");
		$("#moddivmsg").show();
		return;
	}
	if($("#filedate").val() ==""){
		$("#modmsg").text("Please enter date");
		$("#moddivmsg").show();
		return;
	}
	if($("#sfile").val() ==""){
		$("#modmsg").text("Please select file");
		$("#moddivmsg").show();
		return;
	}
	//all good, now we get the data for sending
	var ftitle = $("#filetitle").val();
	var fdate = $("#filedate").val();
	var ffile = $('#sfile')[0].files[0];
	var sectionid = $("#id").val();
	var requestd = new FormData();
	requestd.append('filetitle', ftitle);
	requestd.append('filedate', fdate);
	requestd.append('newfile', ffile);
	requestd.append('sectionid', sectionid);
	requestd.append('filetype', "S");
	//now we send the ajax request
	$.ajax({
		url : "addNewFile.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			$("#filelist").find("tr:gt(0)").remove();
			$(xml).find('SRFILE').each(
					function() {
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaladdfile').modal('hide');
							$(".msgok").html("SUCCESS: File Added").css("display", "block").delay(3000).fadeOut();								
							var newrow ="<tr id='RS" + $(this).find("ID").text() +"'>";
							//now we add each one to the table
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILETITLE").text() + "</td>";
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILEDATE").text() + "</td>";
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILEADDEDBY").text() + "</td>";
							newrow += "<td style='text-align:center;vertical-align:middle;'>";
							newrow += "<a class='btn btn-primary btn-xs' href='/includes/files/schoolreview/sections/files/" + $(this).find("FILEPATH").text() + "'";
							newrow += "target='_blank'><i class='far fa-eye'></i> VIEW</a>";
							newrow += "<a href='#' class='btn btn-warning btn-xs' onclick=\"openmodaleditsectionfile('" + $(this).find("ID").text() + "')\"";
							newrow += "><i class='far fa-edit'></i> EDIT</a>";
							newrow += "<a href='#' class='btn btn-danger btn-xs' onclick=\"openmodaldelete('" + $(this).find("ID").text() + "','" + $(this).find("FILEPATH").text() + "','S')\"";
							newrow += "><i class='far fa-trash-alt'></i> DEL</a>";
							newrow += "</td>"
							newrow +="</tr>";
							$('table#filelist tr:last').after(newrow);
							
						} else {
							
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
							$('#myModal').modal('hide');
							
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display", "block").delay(3000).fadeOut();
			$('#myModal').modal('hide');
		},
		dataType : "text",
		async : false
	});
}
//open add new section file
function openmodaleditsectionfile(fileid) {
	getSectionFile(fileid);
	$("#dtype").val("U");
	$("#divfile").show();
	$("#divfileedit").show();
	$("#sfile").val("");				 
	$('#modaltitle').text("Edit Section File");
	$('#modaladdfile').modal('show');
}
//sends new file info to server
function getSectionFile(fileid){
	//check fields
	$("#moddivmsg").hide();
	var fid = fileid;
	var requestd = new FormData();
	requestd.append('did', fid);
	//now we send the ajax request 
	$.ajax({
		url : "getFile.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			$(xml).find('SRFILE').each(
					function() {
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							//$(".msgok").html("SUCCESS: File Added").css("display", "block").delay(3000).fadeOut();
							$("#fileid").val($(this).find("ID").text());
							$("#filedate").val($(this).find("FILEDATEFORMATTED").text());
							$("#filetitle").val($(this).find("FILETITLE").text());
							var newrow = "<br/><a class='btn btn-primary btn-sm' href='/includes/files/schoolreview/sections/files/" + $(this).find("FILEPATH").text() + "'";
							newrow += "target='_blank'><i class='far fa-eye'></i> VIEW</a>";
							$("#spanfile").html(newrow);
						} else {
							
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
							$('#myModal').modal('hide');
							
						}
									
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css(	"display", "block").delay(3000).fadeOut();
			$('#myModal').modal('hide');
		},
		dataType : "text",
		async : false
	});
}
//sends new file info to server
function updatesectionfile(){
	//check fields
	$("#moddivmsg").hide();
	if($("#filetitle").val() ==""){
		$("#modmsg").text("Please enter title");
		$("#moddivmsg").show();
		return;
	}
	if($("#filedate").val() ==""){
		$("#modmsg").text("Please enter date");
		$("#moddivmsg").show();
		return;
	}
	//all good, now we get the data for sending
	var ftitle = $("#filetitle").val();
	var fdate = $("#filedate").val();
	var sectionid = $("#id").val();
	var fileid = $("#fileid").val();
	var ffile = $('#sfile')[0].files[0];
	var requestd = new FormData();
	requestd.append('filetitle', ftitle);
	requestd.append('filedate', fdate);
	requestd.append('fileid', fileid);
	requestd.append('sectionid', sectionid);
	requestd.append('newfile', ffile);
	requestd.append('filetype', "S");
	//now we send the ajax request
	$.ajax({
		url : "updateFile.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			$("#filelist").find("tr:gt(0)").remove();
			$(xml).find('SRFILE').each(
					function() {
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaladdfile').modal('hide');
							$(".msgok").html("SUCCESS: File Added").css("display", "block").delay(3000).fadeOut();	
							
							var newrow ="<tr id='RS" + $(this).find("ID").text() +"'>";
							//now we add each one to the table
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILETITLE").text() + "</td>";
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILEDATE").text() + "</td>";
							newrow += "<td style='vertical-align:middle;'>" + $(this).find("FILEADDEDBY").text() + "</td>";
							newrow += "<td style='text-align:center;vertical-align:middle;'>";
							newrow += "<a class='btn btn-primary btn-xs' href='/includes/files/schoolreview/sections/files/" + $(this).find("FILEPATH").text() + "'";
							newrow += "target='_blank'><i class='far fa-eye'></i> VIEW</a>";
							newrow += "<a href='#' class='btn btn-warning btn-xs' onclick=\"openmodaleditsectionfile('" + $(this).find("ID").text() + "')\"";
							newrow += "><i class='far fa-edit'></i> EDIT</a>";
							newrow += "<a href='#' class='btn btn-danger btn-xs' onclick=\"openmodaldelete('" + $(this).find("ID").text() + "','" + $(this).find("FILEPATH").text() + "','S')\"";
							newrow += "><i class='far fa-trash-alt'></i> DEL</a>";
							newrow += "</td>"
							newrow +="</tr>";
							$('table#filelist tr:last').after(newrow);
							
						} else {
							
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
							$('#myModal').modal('hide');
							
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display", "block").delay(3000).fadeOut();
			$('#myModal').modal('hide');
		},
		dataType : "text",
		async : false
	});
}
function addupdatesectionfile(){
	
	if($("#dtype").val() == "A"){
		addsectionfile();
	}else{
		updatesectionfile();
	}
}
//open add new section link
function openmodaladdoption(optiontype) {
	$("#optiontype").val(optiontype);
	//$("#fileid").val("");
	//$("#filedate").val("");
	//$("#filetitle").val("");
	//$("#divfile").show();
	//$("#divfileedit").hide();
	$('#optiontitle').val("");
	$('#optionlink').val("");	
	
	$('#optionembed').val("");
	//CKEDITOR.instances["optionembed"].setData('');
	
	if(optiontype == "L"){
		$('#modaltitleopt').text("Add New Section Link");
		$('#spantitle').text("External Link Title");
		$('#spanlink').text("External Link");
		$('#rowembed').hide();
		$('#rowembed2').hide();
		$('#rowtitle').show();
		$('#rowlink').show();
		$('#otype').val("L");
	}else if(optiontype == "V"){
		$('#modaltitleopt').text("Add New Section Video");
		$('#spantitle').text("Video Title");
		$('#spanlink').text("Video Link");
		$('#spanembed').text("Video Embed Code");
		$('#rowembed').show();
		$('#rowembed2').show();
		$('#rowtitle').show();
		$('#rowlink').show();
		$('#otype').val("V");
	}else if(optiontype == "M"){
		$('#modaltitleopt').text("Add New Section Map");
		$('#spantitle').text("Map Title");
		$('#spanembed').text("Map Embed Code");
		$('#spanlink').text("Map Link");
		$('#rowembed').show();
		$('#rowembed2').show();
		$('#rowtitle').show();
		$('#rowlink').show();
		$('#otype').val("M");
	}
	$("#optionaction").val("A");
	$('#modaladdoption').modal('show');
}
function addsectionoption(){
	//check fields
	$("#moddivmsgopt").hide();
	if($("#optiontitle").val() ==""){
		$("#modmsgopt").text("Please enter title");
		$("#moddivmsgopt").show();
		return;
	}
	
	//all good, now we get the data for sending
	var otitle = $("#optiontitle").val();
	var olink = $("#optionlink").val();
	var oembed = $("#optionembed").val();
	oembed = oembed.replace(/"/g, "'");
	//var oembed =CKEDITOR.instances['optionembed'].getData();
	var otype = $("#optiontype").val();
	var sectionid = $("#id").val();
	
	var requestd = new FormData();
	requestd.append('opttitle', otitle);
	requestd.append('optlink', olink);
	requestd.append('optembed', oembed);
	requestd.append('optsecid', sectionid);
	requestd.append('optsectype', otype);
	if($("#optionaction").val() == "E"){
		var optionid = $("#optionid").val();
		requestd.append('optionid', optionid);
	}
	
	//now we send the ajax request
	$.ajax({
		url : "addNewSectionOption.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			if(otype == "L"){
				$("#linkslist").find("tr:gt(0)").remove();
			}else if(otype == "V"){
				$("#videoslist").find("tr:gt(0)").remove();
			}else if(otype == "M"){
				$("#mapslist").find("tr:gt(0)").remove();
			}
			
			$(xml).find('SOOPTION').each(
					
					function() {
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaladdoption').modal('hide');
							$(".msgok").html("SUCCESS: Link Added").css(	"display", "block").delay(3000).fadeOut();
							
							if($(this).find("SECTIONOPTIONTYPE").text() == "L"){
								var newrow ="<tr id='SL" + $(this).find("SECTIONOPTIONID").text() +"'>";
								//now we add each one to the table
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONTITLE").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONLINK").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONADDEDBY").text() + "</td>";
								newrow += "<td style='text-align:center;vertical-align:middle;'>";
								newrow += "<a href='#' class='btn btn-xs btn-warning' onclick=\"getSectionOption('" + $(this).find("SECTIONOPTIONID").text() + "','','L')\"><i class='far fa-edit'></i> EDIT</a>";
								newrow += "<a href='#' class='btn btn-xs btn-danger' onclick=\"openmodaldelete('" + $(this).find("SECTIONOPTIONID").text() + "','','L')\"><i class='far fa-trash-alt'></i> DEL</a>";
								newrow += "</td>";
								newrow +="</tr>";
								$('table#linkslist tr:last').after(newrow);
							}else if($(this).find("SECTIONOPTIONTYPE").text() == "V"){
								var newrow ="<tr id='SV" + $(this).find("SECTIONOPTIONID").text() +"'>";
								//now we add each one to the table
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONTITLE").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONLINK").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONADDEDBY").text() + "</td>";
								newrow += "<td style='text-align:center;vertical-align:middle;'>";
								newrow += "<a href='#' class='btn btn-xs btn-warning' onclick=\"getSectionOption('" + $(this).find("SECTIONOPTIONID").text() + "','','V')\"><i class='far fa-edit'></i> EDIT</a>";
								newrow += "<a href='#' class='btn btn-xs btn-danger' onclick=\"openmodaldelete('" + $(this).find("SECTIONOPTIONID").text() + "','','V')\"><i class='far fa-trash-alt'></i> DEL</a>";
								newrow += "</td>";
								newrow +="</tr>";
								$('table#videoslist tr:last').after(newrow);
							}if($(this).find("SECTIONOPTIONTYPE").text() == "M"){
								var newrow ="<tr id='SM" + $(this).find("SECTIONOPTIONID").text() +"'>";
								//now we add each one to the table
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONTITLE").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONLINK").text() + "</td>";
								newrow += "<td style='vertical-align:middle;'>" + $(this).find("SECTIONOPTIONADDEDBY").text() + "</td>";
								newrow += "<td style='text-align:center;vertical-align:middle;'>";
								newrow += "<a href='#' class='btn btn-xs btn-warning' onclick=\"getSectionOption('" + $(this).find("SECTIONOPTIONID").text() + "','','M')\"><i class='far fa-edit'></i> EDIT</a>";
								newrow += "<a href='#' class='btn btn-xs btn-danger' onclick=\"openmodaldelete('" + $(this).find("SECTIONOPTIONID").text() + "','','M')\"><i class='far fa-trash-alt'></i> DEL</a>";
								newrow += "</td>";
								newrow +="</tr>";
								$('table#mapslist tr:last').after(newrow);
							}
							
							
							
						} else {
							
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
							$('#modaladdoption').modal('hide');
							
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css(	"display", "block").delay(3000).fadeOut();
			$('#modaladdoption').modal('hide');
		},
		dataType : "text",
		async : false
	});
}
//sends new file info to server
function deletelink(){
	//all good, now we get the data for sending
	var fid = $("#fid").val();
	var ftype = $("#ftype").val();
	var requestd = new FormData();
	requestd.append('did', fid);
	requestd.append('dtype', ftype);
	//now we send the ajax request
	$.ajax({
		url : "deleteLink.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			$(xml).find('SOOPTION').each(
					function() {
						
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$('#modaldelete').modal('hide');
							if(ftype == "S"){
								$('#RS' + fid).remove();
								
							}else if(ftype == "L"){
								$('#SL' + fid).remove();
							}else if(ftype == "V"){
								$('#SV' + fid).remove();
							}else if(ftype == "M"){
								$('#SM' + fid).remove();
							}
						
							
						} else {
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
						}
						
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css("display","block").delay(3000).fadeOut();
			},
		dataType : "text",
		async : false
	});
}
//sends new file info to server
function getSectionOption(fileid){
	//check fields
	$("#moddivmsgopt").hide();
	var fid = fileid;
	var requestd = new FormData();
	requestd.append('did', fid);
	//now we send the ajax request
	$.ajax({
		url : "getSectionOption.html",
		type : 'POST',
		data : requestd,
		contentType : false,
		cache : false,
		processData : false,
		success : function(xml) {
			var i = 1;
			$(xml).find('SOOPTION').each(
					function() {
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							if($(this).find("SECTIONOPTIONTYPE").text() == "L"){
								$('#modaltitleopt').text("Edit Section Link");
								$('#spantitle').text("External Link Title");
								$('#spanlink').text("External Link");
								$('#rowembed').hide();
								$('#rowembed2').hide();
							}else if($(this).find("SECTIONOPTIONTYPE").text() == "V"){
								$('#modaltitleopt').text("Edit Section Video");
								$('#spantitle').text("Video Title");
								$('#spanlink').text("Video Link");
								$('#spanembed').text("Video Embed");
								$('#rowembed').show();
								$('#rowembed2').show();
								$('#rowlink').show();
								$('#rowtitle').show();
							}else if($(this).find("SECTIONOPTIONTYPE").text() == "M"){
								$('#modaltitleopt').text("Edit Section Map");
								$('#spantitle').text("Map Title");
								$('#spanembed').text("Map Embed");
								$('#spanlink').text("Map Link");
								$('#rowembed').show();
								$('#rowembed2').show();
								$('#rowlink').show();
								$('#rowtitle').show();
							}
							$("#optionid").val($(this).find("SECTIONOPTIONID").text());
							$("#optiontype").val($(this).find("SECTIONOPTIONTYPE").text());
							$("#optiontitle").val($(this).find("SECTIONOPTIONTITLE").text());
							//CKEDITOR.instances["optionembed"].setData($(this).find("SECTIONOPTIONEMBED").text());
							$("#optionlink").val($(this).find("SECTIONOPTIONLINK").text());
							$("#optionaction").val("E");
							$('#modaladdoption').modal('show');
							
						} else {
							
							$(".msgerr").html($(this).find("MESSAGE").text()).css("display", "block").delay(3000).fadeOut();
							$('#myModal').modal('hide');
							
						}
									
					});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$(".msgerr").html(textStatus).css(	"display", "block").delay(3000).fadeOut();
			$('#myModal').modal('hide');
		},
		dataType : "text",
		async : false
	});
}


