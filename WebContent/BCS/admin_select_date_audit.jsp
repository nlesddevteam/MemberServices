<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*,
                 com.nlesd.bcs.constants.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/jquery.dataTables.min.js"></script>
<script src="includes/js/dataTables.bootstrap.min.js"></script>
<script src="includes/js/buttons.print.min.js"></script>
<script src="includes/js/buttons.html5.min.js"></script>
<script src="includes/js/dataTables.buttons.min.js"></script>
<script src="includes/js/jszip.min.js"></script>
<script src="includes/js/pdfmake.min.js"></script>
<script src="includes/js/vfs_fonts.js"></script>
<script src="includes/js/bcs.js"></script>


	<script>
   		$(document).ready(function () {
   			//clear spinner on load
			$('#loadingSpinner').css("display","none");
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});
    		
    		var now = new Date();
			var day = ("0" + now.getDate()).slice(-2);
    		var month = ("0" + (now.getMonth() + 1)).slice(-2);
			var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
			$('#edate').val(today);
			var sdate = new Date();
			sdate.setDate(now.getDate() - 14);
			day = ("0" + sdate.getDate()).slice(-2);
    		month = ("0" + (sdate.getMonth() + 1)).slice(-2);
			today = sdate.getFullYear()+"-"+(month)+"-"+(day) ;
			$('#sdate').val(today);
			
			$('#selecttype').on('change', function() {
				 	if(this.value == "1"){
				 		$('#divemployee').show();
				 		$('#divvehicle').hide();
				 	}else{
				 		$('#divemployee').hide();
				 		$('#divvehicle').show();
				 	}
				});
			
		});
	</script>
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		<div class="BCSHeaderText">Select Date Audit Report Values</div>
		<p>	
		<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		 </p>
		 <p>
			  Select Audit Type
			  </p>
			  <p>
			  	<select id="selecttype" name="selecttype">
			  		<option value="1">Employee Records</option>
			  		<option value="2">Vehicle Records</option>
			  	</select>
			  </p>
			  Select Contractor			  
			  <p>
			  	<select id="selectcon" name="selectcon">
			  		<option value="-1">Please select</option>
			  		<option value="0">All Contractors</option>
			  		<c:forEach var="entry" items="${contractors}">
			  			<option value="${entry.value.id}">${entry.value.contractorName}</option>
			  		</c:forEach>
			  	</select>
			  </p>
			  Select Date Field			  
			  <p>
			  	<div id="divemployee" name="divemployee">
			  	<select id="selectfield" name="selectfield">
			  		<option value="-1">Please select</option>
			  		<%for(DateFieldConstant d : DateFieldConstant.ALL){ %>
			  			<%if(d.getDateType().equals("E")){ %>
			  				<option value="<%=d.getValue()%>"><%=d.getDescription()%></option>
			  			<%} %>
			  		<% }%>
			  	</select>
			  	</div>
			  	<div id="divvehicle" name="divvehicle" style="display:none;">
			  	<select id="selectfieldv" name="selectfieldv">
			  		<option value="-1">Please select</option>
			  		<%for(DateFieldConstant d : DateFieldConstant.ALL){ %>
			  			<%if(d.getDateType().equals("V")){ %>
			  				<option value="<%=d.getValue()%>"><%=d.getDescription()%></option>
			  			<%} %>
			  		<% }%>
			  	</select>
			  	</div>
			  </p>
			  Select Date Range
			  <p>
			  	<table>
			  		<tr>
			  		<td>From:</td>
			  		<td><input type="date" name="sdate" id="sdate"> </td>
			  		<td>To:</td>
			  		<td><input type="date" name="edate" id="edate"> </td>
			  		</tr>
			  	</table>
			  	 
			  </p>			  	
			  	<p>
			  	<input type="button" value="Run Audit"  onclick="checkDateAuditLogFields();">

			</p>
			
		</div>
	