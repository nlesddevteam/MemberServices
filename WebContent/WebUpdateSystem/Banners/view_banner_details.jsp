<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>Web Banner Posting System</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">  

	
	</head>

  <body>
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Edit Banner Details</div>
You cane edit the banner below. Banner file MUST BE 900 x 200 pixels in size and a PNG or JPG format.






<form id="pol_cat_frm" action="updateBanner.html" method="post" ENCTYPE="multipart/form-data" class="was-validated">
 <input type="hidden" id="op" name="op" value="CONFIRM">
 <input type="hidden" value="${banner.id}" id="id" name="id">
                     <div class="row">
      					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                    	<b>Current Banner:</b><br/>
                      		<c:if test="${banner.bannerFile ne null }">	
                   				<img src="/includes/files/banners/img/${banner.bannerFile}" style="max-width:900px;"><br/>
                     		</c:if>    
                     		<b>New Banner File? (Select a .png or .jpg):</b><br/>
                     		<input type="file" id="banner_file" name="banner_file" class="form-control">
                     	</div>
                     </div>	 
            <br/><br/>          
            <div class="row">      		
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
			      <b>Rotation Order (#):</b><br/>
			      <select id="banner_rotation"  name="banner_rotation" class="form-control" required>
			       <option value='1' ${ banner.bannerRotation eq "1" ? "SELECTED" : "" }>1</option>   
			       <option value='1' ${ banner.bannerRotation eq "2" ? "SELECTED" : "" }>2</option> 
			       <option value='1' ${ banner.bannerRotation eq "3" ? "SELECTED" : "" }>3</option> 
			       <option value='1' ${ banner.bannerRotation eq "4" ? "SELECTED" : "" }>4</option> 
			       <option value='1' ${ banner.bannerRotation eq "5" ? "SELECTED" : "" }>5</option> 
			       <option value='1' ${ banner.bannerRotation eq "6" ? "SELECTED" : "" }>6</option> 
			       <option value='1' ${ banner.bannerRotation eq "7" ? "SELECTED" : "" }>7</option> 
			       <option value='1' ${ banner.bannerRotation eq "8" ? "SELECTED" : "" }>8</option> 
			      </select>
		      </div>
		      <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
			      <b>Status:</b><br/>
			      	<select NAME="banner_status" ID="banner_status" class="form-control" required>
			       	<option value="1" ${ banner.bannerStatus eq "1" ? "SELECTED" : "" }>ENABLED</option>
			      	<option value="0" ${ banner.bannerStatus eq "0" ? "SELECTED" : "" }>DISABLED</option>
			      	</select>
		      </div>
      </div>
      <br/><br/>
      <div class="row">
      <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
       <b>Web Link: (Enter a valid URL or path. If no link, use #)</b><br/>
      <input type="text" id="banner_link" name="banner_link" required class="form-control" value="${banner.bannerLink}">
      </div>
      </div>
       <br/><br/>
       <div class="row">
      <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">                      
       <b>Start Date:</b><br/>
       <input type="date" id="bstartdate" name="bstartdate" class="form-control" value="${banner.bStartDate eq null? '': banner.bStartDateFormatted }">
       </div>
       <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
      	<b>End Date:</b><br/>
          <input type="date" id="benddate" name="benddate" class="form-control" value="${banner.bEndDate eq null? '': banner.bEndDateFormatted}">
          </div>
          <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">                     
      	<b>Repeat till Manually Disabled:</b><br/>
      	
      	
      	<select NAME="brepeat" ID="brepeat" class="form-control" required>
			       	<option value="1" ${ banner.bRepeat eq "Y" ? "SELECTED" : "" }>YES</option>
			      	<option value="0" ${ banner.bRepeat eq "N" ? "SELECTED" : "" }>NO</option>
			      	</select>
      	
      						
          </div>
          </div>
                   
                   <div style="display:none;">
                     Public:
                      <c:choose>
    					<c:when test="${banner.bannerShowPublic eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public">
    					</c:otherwise>
					</c:choose>
                      
                     Staff:
                      
                     <c:choose>
    					<c:when test="${banner.bannerShowStaff eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff">
    					</c:otherwise>
					</c:choose>
                      
                      Show Business:
                      
                      <c:choose>
    					<c:when test="${banner.bannerShowBusiness eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business">
    					</c:otherwise>
					</c:choose>
                                          
                      
                     Code:<br/>
                      <input type="text" class="requiredinput" id="banner_code" name="banner_code"  style="width:250px;" value="${banner.bannerCode}">
                    </div>                    
                    
                   <p>
                   
                   
                   <div align="center">
                   <button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">Update</button>
                   <A HREF='viewBanners.html' class="btn btn-sm btn-danger" onclick="loadingData();">Cancel</a>
					</div>
                   
                
                   
    </form>
   


</div> 
</div>
</div>
  </body>

</html>		