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
<div class="siteHeaderGreen">Add Banner</div>
Banner file MUST BE 900 x 200 pixels in size and a PNG or JPG format. 
Setting a banner as DISABLED overrides all display options and prevent the banner from being displayed. 
The Repeat option will override the start and end dates and display the banner live upon adding and will remain on display until manually DISABLED.
<br/><br/>
    <form id="pol_cat_frm" action="addNewBanner.html" method="post" ENCTYPE="multipart/form-data" class="was-validated"  autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">
     <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4"> 
      			<b>Banner File (Must be a .png or .jpg):</b><br/>
      			<input type="file" id="banner_file" name="banner_file" class="form-control" required accept=".jpg,.png">
      		</div>
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
			      <b>Rotation Order (#):</b><br/>
			      <select id="banner_rotation"  name="banner_rotation" class="form-control" required>
			      <option value="1">1</option>
			      <option value="2">2</option>
			      <option value="3">3</option>
			      <option value="4">4</option>
			      <option value="5">5</option>
			      <option value="6">6</option>
			      <option value="7">7</option>
			      <option value="8">8</option>
			      </select>
		      </div>
		      <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
			      <b>Status:</b><br/>
			      	<select NAME="banner_status" ID="banner_status" class="form-control" required>
			       	<option value="1" selected>ENABLED</option>
			      	<option value="0">DISABLED</option>
			      	</select>
		      </div>
      </div>
      <br/><br/>
      <div class="row">
      <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
       <b>Web Link: (Enter a valid URL or path. If no link, use #)</b><br/>
      <input type="text" id="banner_link" name="banner_link" required class="form-control">
      </div>
      </div>
       <br/><br/>
      <div class="row">
      <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
      <b>Start Date:</b><br/>
        <input type="date" id="bstartdate" name="bstartdate" required class="form-control">
       </div>
       <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">  
     	<b>End Date:</b><br/>
       <input type="date" id="benddate" name="benddate" required class="form-control">
       </div>
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">                     
      	<b>Repeat till Manually Disabled? (Ignore start/end dates)</b><br/>
      	
      	<select NAME="brepeat" ID="brepeat" class="form-control" required>			       	
			      	<option value="N">NO</option>
			      	<option value="Y">YES</option>
			      	</select>     
          
      </div>
      </div>
      
      
       <div style="display:none;">
      <INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public" checked> 
      <INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff">
      <INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business">
       Code:<br/>
      <input type="text"  id="banner_code" name="banner_code">
      </div>                
        <br/><br/>                              
        <div align="center"><button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">SAVE</button> &nbsp; 
        <A HREF='viewBanners.html' class="btn btn-sm btn-danger" onclick="loadingData();">EXIT/CANCEL</a></div>
   
    </form>
    
     
    
</div>
</div>
</div>

  </body>

</html>
   