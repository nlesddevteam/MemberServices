<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>        
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");	
%>
<html>
	<head>
		<title>School Review System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
   <style>
		input {border: 1px solid silver;}
		.btn-group {float:left;}		
	
	</style>
	<script>		
		$('document').ready(function(){
			mTable = $(".schoolReviewSectionsDocsTable").dataTable({
				"order" : [[0,"asc"]],		
				"bPaginate": false,
				responsive: true,	
				sDom: 'lrtip',
				
				 "columnDefs": [
					 {
			                "targets": [3],			               
			                "searchable": false,
			                "orderable": false
			            },
			            {"targets":0, "type":"date-eu"}
			        ]
			});	
			
			mTable = $(".schoolReviewSectionsTable").dataTable({
				"order" : [[0,"asc"]],		
				"bPaginate": false,
				responsive: true,	
				sDom: 'lrtip',
				
				 "columnDefs": [
					 {
			                "targets": [3],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
			});	
			
			
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
		});
		
		
		
		</script>
	</head>

  <body>
  <div class="row pageBottomSpace">
  <div class="col siteBodyTextBlack">

<div class="siteHeaderGreen"> Edit School Review Section &quot;<span style="color:Red;">${section.secTitle eq null?'':section.secTitle}</span>&quot; for </div>
    
                  		 
<p>
<form action='updateSchoolReviewSection.html' method='POST' ENCTYPE="multipart/form-data" onsubmit="return checkreviewsectionfields()"> 
		<input type="hidden" id="id" name="id" value="${section.secId}">
		<input type="hidden" id="rid" name="rid" value="${section.secReviewId}">	  				
	  				
<div class="siteSubHeaderBlue">School Review Section Details</div>
	  				
<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Section Title:</span><br/>

<input type='text' id='sectitle' name='sectitle' autocomplete="false" class="form-control" value="${section.secTitle eq null?'':section.secTitle}" />
					
<br/>
<div style="float:left;width:33%;padding:5px;">								
<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Sort Order:</span><br/>
	<!-- <input type='text' id='secsortid' name='secsortid' autocomplete="false" class="form-control" value="${section.secSortId eq null?'':section.secSortId}" />-->
	<select id='secsortid' name='secsortid' class="form-control">
				<option value="0" ${section.secSortId eq 0 ?'SELECTED':''}>HIDE</option>
				<option value="1" ${section.secSortId eq 1 ?'SELECTED':''}>1</option>
				<option value="2" ${section.secSortId eq 2 ?'SELECTED':''}>2</option>
				<option value="3" ${section.secSortId eq 3 ?'SELECTED':''}>3</option>
				<option value="4" ${section.secSortId eq 4 ?'SELECTED':''}>4</option>
				<option value="5" ${section.secSortId eq 5 ?'SELECTED':''}>5</option>
				<option value="6" ${section.secSortId eq 6 ?'SELECTED':''}>6</option>
				<option value="7" ${section.secSortId eq 7 ?'SELECTED':''}>7</option>
				<option value="8" ${section.secSortId eq 8 ?'SELECTED':''}>8</option>
				<option value="9" ${section.secSortId eq 9 ?'SELECTED':''}>9</option>
				<option value="10" ${section.secSortId eq 10 ?'SELECTED':''}>10</option>
				<option value="11" ${section.secSortId eq 11 ?'SELECTED':''}>11</option>
				<option value="12" ${section.secSortId eq 12 ?'SELECTED':''}>12</option>
				<option value="13" ${section.secSortId eq 13 ?'SELECTED':''}>13</option>
				<option value="14" ${section.secSortId eq 14 ?'SELECTED':''}>14</option>
				<option value="15" ${section.secSortId eq 15 ?'SELECTED':''}>15</option>
			</select>
	
	
</div>						
 <div style="float:left;width:33%;padding:5px;">  					
<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Section Type:</span><br/>
		<select id="sectype" name="sectype" class="form-control">
			<c:forEach var="entry" items="${sectypes}">
				<option value='${entry.value}' ${section.secType eq entry.value ?'SELECTED':''} >${entry.key}</option>
			</c:forEach>
		</select>		   
</div>
<div style="float:left;width:33%;padding:5px;"> 								
<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Section Status:</span><br/>
			<select id="secstatus" name="secstatus" class="form-control">
				<option value="0" ${section.secStatus eq 0 ?'SELECTED':''}>Disabled</option>
				<option value="1" ${section.secStatus eq 1 ?'SELECTED':''}>Enabled</option>
			</select>
</div>							

<div style="clear:both;"></div>	

<br/>
							
<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Description:</span><br/>
			<textarea id='secdescription' name='secdescription'  autocomplete="false" name='reviewdescription' class="form-control">${section.secDescription eq null?'':section.secDescription}</textarea>

<br/><br/>	
							
<div align="center">	
			<button type="submit" class="btn btn-success btn-sm">Save Above Change(s)</button>
			<a href="viewSchoolReviewDetails.html?rid=${section.secReviewId}" class="btn btn-sm btn-danger" style="color:white;">Back to Review</a>			
</div> 	

<br/>
<div align="center" style="padding:5px;color:Red;">Section addition(s) and/or change(s) below are saved automatically when completed. No need to press Save above.</div>		
<br/>					
<div class="alert alert-primary">		
<div style="float:right;padding-bottom:5px;"><a onclick="openmodaladdsectionfile()" class="btn btn-sm btn-primary"><i class="far fa-file-alt"></i> Add Doc (PDF)</a></div>
<div style="font-size:14px;font-weight:bold;text-transform:uppercase;float:left;">Documents (PDF)</div><br/>

									<table class="schoolReviewSectionsDocsTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;"  id="filelist">						
										<thead class="thead-dark">
										<tr style="color:Black;font-size:12px;text-transform:uppercase;">
										<th style="width:15%;">Date</th>	
										<th style="width:45%;">Title</th>		
										<th style="width:15%;">Added By</th>
										<th style="width:25%;">Options</th>
										</tr>
										</thead>
										<tbody>
									<c:forEach var="p" items="${secfiles}" varStatus="counter">
										<tr id='RS${p.id}' style="vertical-align:middle;">
										<td width="15%" style="vertical-align:middle;">${p.fileDateFormatted}</td>	
											<td width="45%" style="vertical-align:middle;">${p.fileTitle}</td>																		
											<td width="15%" style="vertical-align:middle;">${p.fileAddedBy}</td>
											<td width="25%" style="vertical-align:middle;text-align:center;">
												<a class="btn btn-xs btn-primary" href="/includes/files/schoolreview/sections/files/${p.filePath }" target="_blank"><i class="far fa-eye"></i> VIEW</a>
												<a href="#" class="btn btn-xs btn-warning" onclick="openmodaleditsectionfile('${p.id}')"><i class="far fa-edit"></i> EDIT</a>
												<a href="#" class="btn btn-xs btn-danger" onclick="openmodaldelete('${p.id}','${p.filePath }','S')"><i class="far fa-trash-alt"></i> DEL</a>
		                    				</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							
</div>				
<div class="alert alert-warning">	
<div style="float:right;padding-bottom:5px;"><a onclick="openmodaladdoption('L')" class="btn btn-sm btn-warning"><i class="fas fa-link"></i> Add Link/Live Doc</a></div>
<div style="font-size:14px;font-weight:bold;text-transform:uppercase;float:left;">Link(s)/Live Doc Link(s)</div>
<div style="clear:both;"></div>							

								<table class="schoolReviewSectionsTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;" id="linkslist">						
									<thead class="thead-dark">
										<tr style="color:Black;font-size:12px;">
										<th style="width:40%;">External Link Title</th>										
										<th style="width:20%;">External Link</th>									
										<th style="width:20%;">Added By</th>
										<th style="width:20%;">Options</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="p" items="${seclinks}" varStatus="counter">
										<tr id='SL${p.sectionOptionId}' style="vertical-align:middle;">
											<td width="40%" style="vertical-align:middle;">${p.sectionOptionTitle}</td>
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionLink}</td>								
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionAddedBy}</td>
											<td width="20%" style="vertical-align:middle;text-align:center;">
											<a href="#" class="btn btn-xs btn-warning" onclick="getSectionOption('${p.sectionOptionId}')"><i class="far fa-edit"></i> EDIT</a>
											<a href="#" class="btn btn-xs btn-danger" onclick="openmodaldelete('${p.sectionOptionId}','','L')"><i class="far fa-trash-alt"></i> DEL</a>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
</div>
<div class="alert alert-info">	
<div style="float:right;padding-bottom:5px;"><a onclick="openmodaladdoption('V')" class="btn btn-sm btn-info"><i class="fab fa-youtube"></i> Add Video</a></div>
<div style="font-size:14px;font-weight:bold;text-transform:uppercase;float:left;">Video Link(s)/Embed (YouTube)</div>
<div style="clear:both;"></div>					
								<table class="schoolReviewSectionsTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;" id="videoslist">						
									<thead class="thead-dark">
										<tr style="color:Black;font-size:12px;">
										<th style="width:40%;">Video Title</th>										
										<th style="width:20%;">Video Link</th>									
										<th style="width:20%;">Added By</th>
										<th style="width:20%;">Options</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="p" items="${secvideos}" varStatus="counter">
										<tr id='SV${p.sectionOptionId}' style="vertical-align:middle;">
											<td width="40%" style="vertical-align:middle;">${p.sectionOptionTitle}</td>
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionLink}</td>								
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionAddedBy}</td>
											<td width="20%" style="vertical-align:middle;text-align:center;">
											<a href="#" class="btn btn-xs btn-warning" onclick="getSectionOption('${p.sectionOptionId}')"><i class="far fa-edit"></i> EDIT</a>
											<a href="#" class="btn btn-xs btn-danger" onclick="openmodaldelete('${p.sectionOptionId}','','V')"><i class="far fa-trash-alt"></i> DEL</a>
											</td>
										</tr>										
									</c:forEach>
									</tbody>
								</table>

</div>							
<div class="alert alert-secondary">	
<div style="float:right;padding-bottom:5px;"><a onclick="openmodaladdoption('M')" class="btn btn-sm btn-secondary"><i class="far fa-map"></i> Add Map</a></div>
<div style="font-size:14px;font-weight:bold;text-transform:uppercase;float:left;">Google Map(s) / School Catchment Area Map(s)</div>
<div style="clear:both;"></div>									
								<table class="schoolReviewSectionsTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;" id="mapslist">						
									<thead class="thead-dark">
										<tr style="color:Black;font-size:12px;">
										<th style="width:40%;">Map Title</th>	
										<th style="width:20%;">Map Link</th>									
										<th style="width:20%;">Added By</h>
										<th style="width:20%;">Options</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="p" items="${secmaps}" varStatus="counter">
										<tr id='SM${p.sectionOptionId}' style="vertical-align:middle;">
											<td width="40%" style="vertical-align:middle;">${p.sectionOptionTitle}</td>
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionLink}</td>
											<td width="20%" style="vertical-align:middle;">${p.sectionOptionAddedBy}</td>
											<td width="20%" style="vertical-align:middle;text-align:center;">
											<a  href="#" class="btn btn-xs btn-warning" onclick="getSectionOption('${p.sectionOptionId}')"><i class="far fa-edit"></i> EDIT</a> &nbsp;
											<a  href="#" class="btn btn-xs btn-danger" onclick="openmodaldelete('${p.sectionOptionId}','','M')"><i class="far fa-trash-alt"></i> DEL</a>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
</div>																														
					</form>
				
				
				</div></div>
				
				
				
<!-- Modal -->
<div class="modal fade" id="modaladdfile" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modaltitle"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="row">
  			<div class="col-sm-12" id="modtitle">
  				<div class="alert alert-warning" role="alert" style="display:none;" id="moddivmsg">
  					<span id="modmsg"></span><input type="hidden" id="modfiletype">
				</div>
  			</div>
		</div>
      	<div class="row">
      		<div class="col-sm-2" id="modtitle">File Title:</div>
  			<div class="col-sm-10"><Input type="text" id="filetitle" class="form-control">
  			<input type="hidden" id="fileid">
  			<input type="hidden" id="dtype">
  			</div>
		</div>
		
		<div class="row" style="padding-top:3px;">
      		<div class="col-sm-2" id="modtitle">File Date:</div>
  			<div class="col-sm-10" ><Input type="date" id="filedate" class="form-control"></div>
		</div>
		
		<div class="row" style="padding-top:3px;">
      		<div class="col-sm-2" id="modtitle">File:</div>
  			<div class="col-sm-10">
  			<div id="divfile"><Input type="file" id="sfile" class="form-control"></div>
  			<div id="divfileedit" style="display:none;"><span id="spanfile"></span></div>
  			</div> 
		</div>
		</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-sm btn-primary" onclick="addupdatesectionfile()">Add/Update File</button>
      </div>
    </div>
  </div>
</div>



	<div class="modal fade" id="modaladdoption" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="modaltitleopt"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div class="row"  style="padding-top:3px;">
  				<div class="col-sm-12" id="modtitle">
  					<div class="alert alert-warning" role="alert" style="display:none;" id="moddivmsgopt">
  						<span id="modmsgopt"></span>
					</div>
  				</div>
			</div>
			
	      	<div class="row" style="padding-top:3px;">
      			<div class="col-sm-2" id="modtitle"><span id="spantitle"></span></div>
  				<div class="col-sm-10"><Input type="text" id="optiontitle" class="form-control">
  				<input type="hidden" id="optiontype">
  				<input type="hidden" id="optionid">
  				<input type="hidden" id="optionaction">
  				</div>
  			</div>
  			
  			<div class="row" id="rowlink" style="padding-top:3px;">
      			<div class="col-sm-2" id="modtitle"><span id="spanlink"></span></div>
  				<div class="col-sm-10"><Input type="text" id="optionlink" class="form-control"></div>
  			</div>
  			 <div class="row" id="rowembed" style="padding-top:3px;">
      			<div class="col-sm-12" id="modtitle"><span id="spanembed"></span></div>
  			</div>
  			 <div class="row" id="rowembed2" style="padding-top:3px;">
      			<div class="col-sm-12"><textarea id="optionembed" class="form-control"></textarea></div>
  			</div>
  			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Cancel</button>
	        <button type="button" class="btn btn-sm btn-primary" onclick="addsectionoption()">Add/Update</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<div class="modal fade" id="modaldelete" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="modaltitleoptd"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div class="row">
	  			<div class="col-sm-12" id="modtitled">
	  			<b style="font-size:16px;">Are you sure you want to delete this?</b><br/>
	  			<span style="color:Red;">Once deleted it cannot be retrieved.</span>. 
	  				  			
	  			</div>
	  			<input type="hidden" id="fid">
	  			<input type="hidden" id="ftype">
	  			<input type="hidden" id="fname">
	  		</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-sm btn-primary" data-dismiss="modal">No</button>
	        <button type="button" class="btn btn-sm btn-danger" onclick="deleteconfirmed()">Delete</button>
	      </div>
	    </div>
	  </div>
	</div>      
    <script>
    
    var pageWordCountConf = {
    	    showParagraphs: true,
    	    showWordCount: true,
    	    showCharCount: true,
    	    countSpacesAsChars: true,
    	    countHTML: true,
    	    maxWordCount: -1,
    	    maxCharCount: 7950,
    	}

    //CKEDITOR.replace( 'optionembed',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
    CKEDITOR.replace( 'secdescription',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
  
    </script>        
 </body>

</html>	
			

			