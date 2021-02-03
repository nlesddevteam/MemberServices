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
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
     
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
<%
  User usr = (User) session.getAttribute("usr");
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
			mTable = $(".schoolReviewTable").dataTable({
				"order" : [[0,"asc"]],		
				"bPaginate": false,
				responsive: true,
								
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



 
<div class="siteHeaderGreen">View School Reviews</div>
					The following are current school review entries posted in the system and their current status. Click on the school review title to view/edit. 
	
		
									
									<p>
					  <table class="schoolReviewTable table table-sm responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th width="60%" style="border-right:1px solid white;">TITLE</th>					
					<th width="10%" style="border-right:1px solid white;">DATE</th>																
					<th width="10%" style="border-right:1px solid white;">STATUS</th>	
					<th width="20%" style="border-right:1px solid white;">OPTIONS</th>				
									
					</tr>
					</thead>
					<tbody>		

									<c:choose>
	                                  	<c:when test='${fn:length(reviews) gt 0 && reviews[0] ne null}'>
                                  		<c:forEach items='${reviews}' var='g'>
                                  		
                                  		<c:if test="${ g.srStatus eq 1}">	
										<c:set var="statusColor" value="activeColor"/>
										<c:set var="statusrColor" value="activerColor"/>	
										<c:set var="statusText" value="<i class='fas fa-check'></i> ACTIVE"/>									
										</c:if>	
										<c:if test="${ g.srStatus eq 0 }">	
										<c:set var="statusColor" value="disabledColor"/>
										<c:set var="statusrColor" value="disabledrColor"/>
										<c:set var="statusText" value="<i class='fas fa-ban'></i> DISABLED"/>
										</c:if>
										<c:if test="${ g.srStatus eq -1 }">	
										<c:set var="statusColor" value="hiddenColor"/>
										<c:set var="statusrColor" value="hiddenrColor"/>
										<c:set var="statusText" value="<i class='far fa-eye-slash'></i> HIDDEN"/>
										</c:if>
                                  		<tr class='datalist ${statusrColor}' id="R${g.id}" valign="middle" style="vertical-align:middle;">
                                  			<td  width="60%" style="vertical-align:middle;">${g.srName}</td>
		                                    <td  width="10%" style="text-align:center;vertical-align:middle;">${g.dateAddedFormatted}</td>
		                                    <td  width="10%" class="${statusColor}" style="text-align:center;vertical-align:middle;">${statusText}</td>
		                                    <td  width="20%" style="text-align:center;vertical-align:middle;">
		                                    <a title="Edit ${g.srName}" class="btn btn-warning btn-xs" href="viewSchoolReviewDetails.html?rid=${g.id}"><i class="far fa-edit"></i> EDIT</a>&nbsp;
		                                    <a title="Delete this Review" class="btn btn-danger btn-xs" href="#" onclick="openmodaldeletereview('${g.id}')"><i class="far fa-trash-alt"></i> DEL</a>		                                      
		                					</td>
		                				</tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr>
											<td>No School Reviews Found.</td>
											<td></td>
											<td></td>
											<td></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>	

</table>
		
			

<div align="center">
					<a href="addNewSchoolReview.html" class="btn btn-success btn-sm" title="Add School Review">Add Review</a>
					<a href="viewSchoolReviews.html" class="btn btn-primary btn-sm" title="View School Reviews">View / Refresh Reviews</a>					
</div>


    
</div></div>

    
	<div class="modal fade" id="modaldelete" tabindex="-1" role="dialog" aria-labelledby="modaladd" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="modaltitle"><i class="far fa-trash-alt"></i> DELETE REVIEW?</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div class="row">
	  			<div class="col-sm-12" id="modtitled"><b style="font-size:16px;">Are you sure you want to delete this review?</b><br/>
	  			<span style="color:Red;">Once deleted it cannot be retrieved</span>. 
	  			You will lose ALL content of this review including files, postings, documents, and images. 
	  			Please either HIDE or DISABLE the review unless you are absolutely sure you wish to remove this review. 
	  			You can set the status of the review if you EDIT it. For archival and future reference purposes its best to NOT delete any review.
	  			</div>
	  			<input type="hidden" id="currentid">
	  		</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-sm btn-success" data-dismiss="modal">NO</button>
	        <button type="button" class="btn btn-sm btn-danger" onclick="deleteschoolreview()">DELETE</button>
	      </div>
	    </div>
	  </div>
	</div>     
  </body>

</html>	
			
			
			