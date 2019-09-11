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

<html>

	<head>
		<title>Education and Early Childhood Development Groups Admin</title>
					
<script>
$('document').ready(function(){
$("#loadingSpinner").css("display","none");	

 });
    </script>

	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Working Groups</b></div>
      			 	<div class="panel-body"> 
  							To fulfill its mandate, the Department of Education and Early Childhood Development (EECD) annually establishes working groups of educators. EECD working groups can have a variety of
							purposes. For example, curriculum writing/review, policy development, examination preparation, etc. Typical working groups will have educator representation from a variety of
							school sizes (small, medium, large) and class configurations (single grade, multiage/grade, combined grades), as well as a range of years of experience. Groups may be comprised of a
							combination of area specialists as well as generalist educators. There will be English and French language representation, where appropriate.
                 			<br/><br/>
                 			Depending on the project, the time commitment of an EECD working group membership can vary. Groups could meet for two days, 2-3 times per school year, or they could just meet once
							for 1-2 days. Administrative details such as travel costs, substitute time and so on are provided in advance of any meeting.
							<br /><br/>
							To be considered, please visit member services and click on EECD Teacher Working Groups. Here you will find a list of potential EECD working groups and a brief description of their
							project. If you are interested in participating in an EECD working group this school year, please take the time to review the list and register for the working group(s) that are of interest to you
							by Friday, September 20, 2019. By registering, you are agreeing to be considered for an EECD working group. Registration is not a guarantee that you will become a working group member.
							You will be notified if you are approved.
							<br /><br/>
							Being a member of an EECD working group can provide valuable professional learning and a tremendous opportunity to contribute to the education of students in Newfoundland and
							Labrador. All educators are encouraged to put their names forward for consideration.
							
                 	<jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
                 	
                 	
                 	<ul id="check-list-box" class="list-group checked-list-box">
                  					<c:forEach items='${ areas }' var='area'>
                  						<c:choose>
                  							<c:when test='${ area.additionalText ne null }'>
                  									<li class="list-group-item" id="${ area.id }" nocheck="Y">
                  									<c:choose>
                  									<c:when test="${ area.currentStatus eq 2 }">
                  										<div class="alert alert-success" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:when>
                  									<c:when test="${ area.currentStatus eq 3 }">
                  										<div class="alert alert-danger" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:when>
                  									<c:otherwise>
                  										<div class="alert alert-primary" role="alert"> ${ area.areaDescription }  [${ area.additionalText }]</div></li>
                  									</c:otherwise>
                  									</c:choose>
                  							</c:when>
                  							<c:otherwise>
                  							<li class="list-group-item" id="${ area.id }" nocheck="N" qupdated="N">
                  							 ${ area.areaDescription }
                  							 <c:choose>
                  								<c:when test="${ area.required eq 'Y' }">
                  								<div id="divhref${ area.id }" style="display:none;"><a onclick="openedit('${ area.id }');" style="color:#FFFFFF;">Add/Edit Answers</a></div>
                  								</c:when>
                  							</c:choose>
                  							 
                  							 <br>
                  							 <div class="alert alert-info" role="alert">Eligible teachers:${ area.eligibleTeachers }</div>
                  							 </li>
                  							  
                  							</c:otherwise>
                  						</c:choose>
                  						
                  					</c:forEach>
                  					
                  				</ul>
                		
                		<input type="hidden" id="firstsave" name="firstsave" value="${firstsave}">
                		<input type="hidden" id="teacherareas" name="teacherareas" value="${teacherareas}">
                		<input type="hidden" id="selectedid" name="selectedid" value="">
                		<input type="hidden" id="editselected" name="editselected" value="N">
                		<div align="center"><button class="btn btn-success btn-xs" id="get-checked-data">Update Teacher Areas</button></div>
                		<div class="alert alert-success" id="successMsgb" style="text-align:center;display:none;" align="center"></div>
            		</div>
</div></div></div>            			

  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled"><span id="maintitlespan"></span></h4>
                </div>
                <div class="modal-body">
					<form id="frmquestions" action="">
                    <table id="tquestions">
                    <tbody>
                    <tr><td colspan='2' align="center"><h4 class="modal-title" id="title1"></h4></td></tr>
                    <tr><td colspan='2' align="center"><h4 class="modal-title" id="title2">Maximum 500 characters per question</h4></td></tr>
                    </tbody>			
                    </table>
                    </form>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" id="btnok" name="btnok">OK</button>
        				<button type="button" class="btn btn-xs btn-default" data-dismiss="modal">CANCEL</button>
        				<input type="hidden" id="hiddenid">
                </div>
                
            </div>
      
    </div>
  </div>
         			
</body>
</html>	
			

			