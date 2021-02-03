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
			mTable = $(".schoolReviewSectionsTable").dataTable({
				"order" : [[0,"asc"]],		
				"bPaginate": false,
				responsive: true,								
				 "columnDefs": [
					 {
			                "targets": [[5],[6]],			               
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



 
<div class="siteHeaderGreen"> Editing School Review &quot;<span style="color:Red;">${review.srName eq null?'':review.srName}</span>&quot;</div>
  
 
                      		
                  		 
						<p>
				
					<form action='updateSchoolReview.html' method='POST' ENCTYPE="multipart/form-data" onsubmit="return checkreviewfields();"> 
					<input type="hidden" id="id" name="id" value="${review.id}">
	  				
	  				
	  				<div class="siteSubHeaderBlue">School Review Details</div>
	  				
	  				
					<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Review Name/Title:</span><br/>
					 <input type='text' id='reviewname' name='reviewname' autocomplete="false" class="form-control" value="${review.srName eq null?'':review.srName}" />
					<br/>
					<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Description:</span><br/>
   					<textarea id='reviewdescription'  autocomplete="false" name='reviewdescription' class="form-control">${review.srDescription eq null?'':review.srDescription}</textarea>
					<br/>
					<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Review Photo:</span><br/>
			 						<c:if test="${ review.srPhoto ne null }">	
										<img src="/includes/files/schoolreview/photo/${review.srPhoto}" border=0 style="width:250px;"><p>
										<input type="hidden" id="hidphoto" name="hidphoto" value="${review.srPhoto}">
									</c:if>	
                     				<input type="file"  id="reviewphoto" name="reviewphoto"  class="form-control">
                     		<br/>	<br/>
                     		<div style="float:left;width:50%;padding:5px;">             
							<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Review School Year:</span><br/>
								    <jobv2:SchoolYearListbox id="reviewschoolyear"  pastYears="3" futureYears="1" cls='form-control' value='${review.srSchoolYear}'/>
							</div>  
                     		<div style="float:left;width:50%;padding:5px;">             
							<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;"> Review Status:</span><br/>
								    <select id="reviewstatus" name="reviewstatus" autocomplete="false" class="form-control">
								    	<option value="0" ${review.srStatus eq 0 ? 'SELECTED':''}>Disabled</option>
								    	<option value="1" ${review.srStatus eq 1 ? 'SELECTED':''} >Enabled</option>
								    	<option value="-1" ${review.srStatus eq -1 ? 'SELECTED':''}>Hidden</option>
								    </select>
							 </div>
							<div style="clear:both;"></div>				
<br/>					
<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">School Review School System</span><br/>
	
			 <div style="float:left;width:40%;padding:5px;">       	
			 <b>Schools NOT in the Review</b></br/>	  	
					
									    <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
									   		<c:forEach var="entry" items="${schools}">
													<c:choose>
									   					<c:when test="${entry.selected eq false}">
									   						<option value='${entry.schoolId}'>${entry.schoolName}</option>
									   					</c:when>
									   				</c:choose>
											</c:forEach>
										</select>
					</div>						
			 <div style="float:left;width:20%;padding:5px;text-align:center;">	<br/>					
									    <!-- <button class="btn btn-success btn-sm" type="button" id="multiselect_rightAll" class="btn btn-block">Add All <i class="fas fa-angle-double-right"></i></button><br/>-->
									    <button class="btn btn-success btn-sm" type="button" id="multiselect_rightSelected" > Add School(s)<i class="fas fa-chevron-right"></i></button><br/><br/>
									    <button class="btn btn-danger btn-sm" type="button" id="multiselect_leftSelected" ><i class="fas fa-chevron-left"></i> Remove School(s)</button><br/><br/>
									    <button class="btn btn-danger btn-sm" type="button" id="multiselect_leftAll" ><i class="fas fa-angle-double-left"></i> Remove All</button>
				 </div>
				 <div style="float:left;width:40%;padding:5px;">
				 <b>Schools in the Review</b></br/>						
									    <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple">
											<c:forEach var="entry" items="${schools}">
									   				<c:choose>
									   					<c:when test="${entry.selected eq true}">
									   						<option value='${entry.schoolId}'>${entry.schoolName}</option>
									   					</c:when>
									   				</c:choose>
											</c:forEach>
									    </select>
					</div>				
				<div style="clear:both;"></div>	
					<br/><br/>
				<div align="center">	
						<button type="submit" class="btn btn-success btn-sm">Save Above Change(s)</button>		
		  		  		 <a href="#" onclick="openmodaladdsection()" class="btn btn-sm btn-primary">Add New Section (Below)</a>	        			
	        			<a href="viewSchoolReviews.html" class="btn btn-danger btn-sm" title="View School Reviews">Back to Reviews</a>	        			
				</div> 			
					
			<br/><br/>						 


<div class="alert alert-info">			
<div class="siteSubHeaderBlue">School Review Sections</div>
						
			These section addition(s) and/or change(s) are saved automatically when completed. No need to press Save above.			
	
	  
							
					<table class="schoolReviewSectionsTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;" id="showlists">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th width="5%" style="border-right:1px solid white;">SORT #</th>
					<th width="15%" style="border-right:1px solid white;">TYPE</th>					
					<th width="40%" style="border-right:1px solid white;">TITLE</th>										
					<th width="5%" style="border-right:1px solid white;"># FILES</th>													
					<th width="15%" style="border-right:1px solid white;">ADDED BY</th>							
					<th width="10%" style="border-right:1px solid white;">STATUS</th>
					<th width="10%" style="border-right:1px solid white;">OPTIONS</th>
					</tr>
					</thead>
					<tbody>
									<c:forEach var="p" items="${reviewsecs}" varStatus="counter">	
									
										<tr id='RS${p.secId}' valign="middle" style="vertical-align:middle;">
											<td width="5%" style="vertical-align:middle;">${p.secSortId}</td>	
											<td width="15%" style="vertical-align:middle;">${p.secTypeText}</td>
											<td width="40%" style="vertical-align:middle;">${p.secTitle}</td>														
											<td width="5%" style="vertical-align:middle;text-align:center;">${p.fileCount}</td>																
											<td width="15%" style="vertical-align:middle;">${p.secAddedBy}</td>										
											<c:choose>
											<c:when test="${p.secStatus eq 1}">
											<td width="10%" style="vertical-align:middle;background-color:Green;color:White;text-align:center;">ENABLED</td>
											</c:when>
											<c:when test="${p.secStatus eq 0}">
											<td width="10%" style="vertical-align:middle;background-color:Red;color:White;text-align:center;">DISABLED</td>
											</c:when>
											<c:otherwise>
											<td width="10%" style="vertical-align:middle;background-color:Silver;color:White;text-align:center;">HIDDEN</td>
											</c:otherwise>
											</c:choose>
									
											<td width="10%" style="vertical-align:middle;text-align:center;">
												<a title="Edit ${p.secTitle}" class="btn btn-warning btn-xs" href="viewSchoolReviewSection.html?rid=${p.secReviewId}&sid=${p.secId}"><i class="far fa-edit"></i> EDIT</a>&nbsp;
												<a title="Delete this Section" href="#" class="btn btn-danger btn-xs" onclick="openmodaldeletereviewsection('${p.secId}')"><i class="far fa-trash-alt"></i> DEL</a>
		                    				</td>
										</tr>
									</c:forEach>
					</tbody>									
					</table>
</div>			
						
						
			
						
						
																		
					</form>
				
          
          
          
    </div>
    </div>




    <br/>
<!-- Modal -->
<div class="modal fade" id="modaladd" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modaltitle"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	
  				<div class="alert alert-warning" role="alert" style="display:none;" id="moddivmsg">
  					<span id="modmsg"></span><input type="hidden" id="modfiletype">
				</div>
  			
  			<b>Title: </b><br/>
  			<Input type="text" id="sectitle" class="form-control">
  			<br/>  			
  			<b>Type:</b><br/>  			
				<select id="sectype" class="form-control">
					<c:forEach var="entry" items="${sectypes}">
						<option value='${entry.value}'>${entry.key}</option>
					</c:forEach>
				</select>
		<br/>
		<b>Status:</b><br/>
		
				<select id="secstatus" class="form-control">
					<option value="0">Disabled</option>
					<option value="1" SELECTED>Enabled</option>
				</select>
			<b>Sort Order: </b><br/>
  			<Input type="text" id="secsortid" class="form-control">
  			<br/>  
			<br/>
			<b>Description:</b><br/>
			<textarea id='secdescription'   name='secdescription' class="form-control"></textarea>
		
			
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-sm btn-success" onclick="addsection()">Add Section</button>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="modaldelete" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modaltitle"><i class="far fa-trash-alt"></i> DELETE THIS SECTION?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="row">
  			<div class="col-sm-12" id="modtitled"><b style="font-size:16px;">Are you sure you want to delete this section?</b><br/>
	  			<span style="color:Red;">Once deleted it cannot be retrieved</span>. 
	  			You will lose ALL content of this section including files, postings, documents, and images. 
	  			Please DISABLE the section unless you are absolutely sure you wish to remove this section from the review. 
	  			You can set the status of this section if you EDIT it. For archival and future reference purposes its best to NOT delete any section.
	  			</div>
  			<input type="hidden" id="currentid">
  		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-success" data-dismiss="modal">NO</button>
        <button type="button" class="btn btn-sm btn-danger" onclick="deleteschoolreviewsection()">Delete</button>
      </div>
    </div>
  </div>
</div>

    
   <script>
//Configure char count for just this page. Default max is 2460. 4000 for this page.


var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 7950,
	};
	
	

CKEDITOR.replace( 'reviewdescription',{wordcount: pageWordCountConf} );	  
CKEDITOR.replace( 'secdescription',{wordcount: pageWordCountConf} );
</script>  


 </body>

</html>	
			

			