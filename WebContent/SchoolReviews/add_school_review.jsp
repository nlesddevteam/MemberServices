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
		<title>NLESD - Web Update School Review</title>				

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  

	</head>

  <body>
  <div class="siteHeaderGreen"> Adding a School Review To NLESD Site</div>
 
 
                      		<c:if test="${ msgOK ne null }">  
                  				<div class="alert alert-success" id="memo_error_message" style="margin-top:10px;font-size:14px;margin-bottom:10px;padding:5px;">${ msgOK } </div>   
                  			</c:if>                  			
							<c:if test="${ msgERR ne null }">  
                  				<div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;font-size:14px;margin-bottom:10px;padding:5px;">${ msgERR } </div>   
                  			</c:if>
                  			<c:if test="${ msg ne null }">  
                  				<div class="alert alert-info" id="memo_error_message" style="margin-top:10px;font-size:14px;margin-bottom:10px;padding:5px;">${ msg } </div>   
                  			</c:if>  
		
					<form action='addNewSchoolReview.html' method='POST' ENCTYPE="multipart/form-data" onsubmit="return checkreviewfields();"> 
					<input type="hidden" id="op" name="op" value="CONFIRM">

						
<div class="siteSubHeaderBlue">School Review Details</div>	
				
 
	  		<span style="font-size:14px;font-weight:bold;text-transform:uppercase;">Review Name/Title:</span><br/>
			<input type='text' id='reviewname' name='reviewname' autocomplete="off" class="form-control" value="" placeholder="Enter a valid title for this Review"/>
			<br/>
			<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Description:</span><br/>
   			<textarea id='reviewdescription'  autocomplete="false" name='reviewdescription' class="form-control"></textarea>
			<br/>
			<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Review Photo:</span><br/>
			 Select the photo file to upload by clicking on <i>Choose File</i> below if necessary.					                     
			<br/><input type='file' name='reviewphoto' />
			<br/>	<br/>
			
			<div style="float:left;width:50%;padding:5px;">             
			<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">Review School Year:</span><br/>
								    <jobv2:SchoolYearListbox id="reviewschoolyear"  pastYears="3" futureYears="1" cls='form-control'/>
			</div>	
			
			<div style="float:left;width:50%;padding:5px;">             
			<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;"> Review Status:</span><br/>
								    <select id="reviewstatus" name="reviewstatus" autocomplete="false" class="form-control">				
								    <option value="-1" selected>Hidden</option>				    	
								    	<option value="1" >Enabled</option>								    	
								    	<option value="0">Disabled</option>
								    </select>								   
			</div>  						
			
			<div style="clear:both;"></div>					
			<br/><br/>					
			<div class="alert alert-warning"><b>NOTE:</b> Section(s) can be added/created after the review has been added to system.</div>
   							
					
			<br/>
			<span style="padding-top:10px;font-size:14px;font-weight:bold;text-transform:uppercase;">School Review School System</span><br/>
	
			 <div style="float:left;width:40%;padding:5px;">       	
			 <b>Schools NOT in the Review</b></br/>	  	
									    <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
									    		<c:forEach var="entry" items="${schools}">
													<option value='${entry.schoolId}'>${entry.schoolName}</option>
												</c:forEach>
									    </select>
			</div>						
			 <div style="float:left;width:20%;padding:5px;text-align:center;">	<br/>					
									    <!-- <button class="btn btn-success btn-sm" type="button" id="multiselect_rightAll" class="btn btn-block">Add All <i class="fas fa-angle-double-right"></i></button><br/>-->
									    <button class="btn btn-success btn-sm" type="button" id="multiselect_rightSelected" > Add School(s)<i class="fas fa-chevron-right"></i></button><br/><br/>
									    <button class="btn btn-danger btn-sm" type="button" id="multiselect_leftSelected" ><i class="fas fa-chevron-left"></i> Remove</button><br/><br/>
									    <button class="btn btn-danger btn-sm" type="button" id="multiselect_leftAll" ><i class="fas fa-angle-double-left"></i> Remove All</button>
				 </div>
				 <div style="float:left;width:40%;padding:5px;">
				 <b>Schools in the Review</b></br/>						
									    <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple"></select>
				</div>				
				<div style="clear:both;"></div>					
			<br/><br/>						 
						<div align="center">	
	        				<button type="submit" class="btn btn-success btn-sm">Create This Review</button>
	        				<a href="viewSchoolReviews.html" class="btn btn-sm btn-danger" style="color:white;">Back to School Review List</a>
						</div>            
               										
					</form>
			
          
    <script>
    
    var pageWordCountConf = {
    	    showParagraphs: true,
    	    showWordCount: true,
    	    showCharCount: true,
    	    countSpacesAsChars: true,
    	    countHTML: true,
    	    maxWordCount: -1,
    	    maxCharCount: 2250,
    	}

    CKEDITOR.replace( 'reviewdescription',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
    
    </script>     

  </body>

</html>	
			

			