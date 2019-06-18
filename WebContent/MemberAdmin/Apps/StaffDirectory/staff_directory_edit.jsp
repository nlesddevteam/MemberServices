<%@ page language="java" session="true"%>
<%@ page import='com.awsd.security.*,java.util.*'%>
<%@ page import='com.nlesd.webmaint.bean.*'%>
<%@ page import='com.nlesd.webmaint.service.*'%>
<%@ page import='org.apache.commons.lang.StringUtils' %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />


<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>


<c:set var='divisions' value='<%= StaffDirectoryContactBean.STAFF_DIRECTORY_DIVISION.values()  %>' />

<html>
  <head>
    <title>Members Admin - Staff Directory</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
		<script >
		
		
		$(document).ready(function() {

			$(function() {
			    var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
			    $('html').css({'background': 'url(../includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
			    	'-webkit-background-size':'cover',
			    	'-moz-background-size':'cover',
			    	'-o-background-size':'cover',
			    	'background-size':'cover'});
			   });


		}); 
		</script>
		
    <script type="text/javascript">
	    $(function(){
	    	
	    	if(${contact.contactId eq 0}){
		    	$('#divisionId, #zoneId').change(function(){
		    		getNextContactSortOrder($('#divisionId').val(), $('#zoneId').val());
		    	});
	    	}
	    	
	    	$('#directoryListStaff input[type=text]').css({'width' : '100%'});
	    	//$('#directoryListStaff tr:odd td').css({'background-color' : '#E5F2FF'});
	    	
	    });
	    
	    function getNextContactSortOrder(divisionid, zoneid){
	    	if(divisionid != "" && zoneid != ""){
		    	$.ajax(
	     			{
	     				type: "POST",  
	     				url: "/MemberServices/MemberAdmin/Apps/StaffDirectory/getNextContactSortOrder.html",
	     				data: {
	     					d_id: divisionid,
	     					z_id : zoneid,
	     					ajax : true 
	     				}, 
	     				success: function(data){
	     					if($(data).find('GET-NEXT-CONTACT-SORTORDER-RESPONSE').length > 0) {
	     						$('#sortorder').val($(data).find('GET-NEXT-CONTACT-SORTORDER-RESPONSE').attr('sortorder'));
	     					}
	     				}, 
	     				dataType: "xml",
	     				async: false
	     			}
	     		);
	    	}
	    }
	    
    </script>
    
    <script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
				
			});
		</script>
	
	<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#closing_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

    		);

	</script>
    
    <style>    
    .inputtag { width:100px; }
    </style>
    
  </head>

	<body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">Office Staff Contact Edit/Add</div>
                      <div class="pageBody">  
                      
                     <c:if test="${ msg ne null }">  
                  <div style="text-align:center;color:Red;font-size:12px;">                
                    ${ msg }  </div>
                    <p>                
                  </c:if>
                  
                  
                 
                      
                  <form action='updateStaffDirectoryContact.html' method='POST' >
                  	<input type='hidden' name='contactId' value='${contact.contactId }' />
	                  <table id='directoryListStaff' cellpadding="5" cellspacing="0" style="width:100%;font-size:11px;max-width:500px;">
	                  	<span style="text-align:center;color:#007F01;font-weight:bold;font-size:16px;">${ contact.fullName }</span>
	                   	<tr>
	                   		<td width="20%">Division</td>
	                      <td width="80%">
	                      	<select id='divisionId' name='divisionId'>
	                      		<c:forEach items="${ divisions }" var='division'>
	                      			<option value='${ division.id }' ${ contact.division.id eq division.id ? "SELECTED" : "" }>${ division.name }</option>
	                      		</c:forEach>
	                      	</select>
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>Region</td>
	                    	<td><sch:SchoolZonesDDL id="zoneId" dummy="true" value='${ contact.zone }'  /></td>
	                    </tr>
	                    <tr>
	                    	<td>Position/Title</td>
	                      <td><input type='text' id='position' name='position' class="inputtag" value="${ contact.position }" /></td>
	                    </tr>
	                    <tr>
	                    	<td>Full Name</td>
	                    	<td><input type='text' name='fullname' id='fullname' style="max-width:300px;" value="${ contact.fullName }"/></td>
	                    </tr>
	                    <tr>
	                    	<td>Telephone</td>
	                      <td><input type='text' id='telephone' name='telephone' style="max-width:120px;" value='${ contact.telephone }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Extension</td>
	                      <td><input type='text' id='extension' name='extension' style="max-width:40px;" value='${ contact.extension }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Cellphone</td>
	                      <td><input type='text' id='cellphone' name='cellphone' style="max-width:120px;" value='${ contact.cellphone }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Fax</td>
	                      <td><input type='text' id='fax' name='fax' style="max-width:120px;" value='${ contact.fax }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Email</td>
	                      <td><input type='text' id='email' name='email' style="max-width:250px;" value='${ contact.email }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Sort Order</td>
	                    	<td><input type='text' id='sortorder' name='sortorder' style="max-width:40px;" value='${ contact.sortorder }' /></td>
	                    </tr>
	                    <tr>
	                    	<td colspan='2'><div align="center"><input type='submit' value='Update Staff Contact' /></div></td>
	                    </tr>
	                  </table>
                  </form>
               
   </div>
    
     <div align="center"><a href="staff_directory.jsp"><img src="../includes/img/back-off.png" class="img-swap menuImage" title="Back to Staff Listing"></a></div>
		
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>

</html>             
               
               