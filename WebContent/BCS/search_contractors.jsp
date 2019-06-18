<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="BCS-SYSTEM-ACCESS" />
	<script>
			$(document).ready(function(){  
				$('#BCS-Search').css("display","none");
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");    		    
    		    setOptions(); // on load
    		    $('#searchby').change(setOptions); // on change
    		    function setOptions() {       
    		        switch ($("#searchby").val()) {
    		            case "SELECT" :
    		                $("#divtext").hide();
    		                $("#divselectp").hide();
    		                $("#divselect").hide();
    		                break;    		            
    		            case "Province":
    		                $("#divselect").hide();
    		                $("#divselectp").show();
    		                $("#divtext").hide();
    		                break;
    		            case "Status":
    		                $("#divtext").hide();
    		                $("#divselect").show();
    		                $("#divselectp").hide()
    		                break;
    		            default:
    		                $("#divselect").hide();
    		            	$("#divselectp").hide();
    		                $("#divtext").show();
    		                break;
    		            }
    		    }
        	});</script>
        	
        	
        	
        	
        	
        	
        	<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script>
		
	<div id="printJob">	
      
	<div class="BCSHeaderText">Search Contractors</div>
			<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
            <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		
	<table style="width:320px;">
	<tr>
	<td style="width:150px;">Search By:<br/>
		<select id="searchby">
						<option value="SELECT">*** PLEASE SELECT ***</option>
						
					<c:forEach var="entry" items="${sby}">
						<option value='${entry.value}'>${entry.value}</option>
					</c:forEach>					
		</select>
	
	
	
	</td><td style="width:150px;padding:5px;">
			<div id="divtext" style="display:none">
			For:<br/>
				<input type="text" id="txtsearch" placeholder="Enter Search Text">
			</div>
			<div id="divselect"  style="display:none">
			For:<br/>
			<select id="status">
			<option value="-1">*** Select ***</option>
			<c:forEach var="test" items="${status}" >
				<option value='${test.key}'>${test.value}</option>
			</c:forEach>
			</select>
			</div>
			<div id="divselectp"  style="display:none">				
			For:<br/>	
			 <select  id="province" name="province">
				<option value="SELECT">*** Select ***</option>
				<option value="AB">Alberta</option>
				<option value="BC">British Columbia</option>
				<option value="MB">Manitoba</option>
				<option value="NB">New Brunswick</option>
				<option value="NL">Newfoundland and Labrador</option>
				<option value="NS">Nova Scotia</option>
				<option value="ON">Ontario</option>
				<option value="PE">Prince Edward Island</option>
				<option value="QC">Quebec</option>
				<option value="SK">Saskatchewan</option>
				<option value="NT">Northwest Territories</option>
				<option value="NU">Nunavut</option>
				<option value="YT">Yukon</option>
			</select>
		</div>
	</td>
	</tr>
	<tr><td colspan=2>&nbsp;</td></tr>
	<tr>
	<td colspan=2><input type="button" class="menuBCS" value="Search Contractors" onclick="ajaxSearchContractors()"></td>
	</tr>
	
	</table>
	
	<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
    <div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
		<br />
	      
    <form>
    <div id="BCS-Search">
      <table id="BCS-table" width="100%" class="BCSTable">
     
      	<THEAD>
     		<tr style="border-bottom:1px solid grey;" class="listHeader">
	      		<th width="20%" class="listdata">Name</th>
	      		<th width="10%" class="listdata">City</th>
	      		<th width="35%" class="listdata">Company</th>
	      		<th width="25%" class="listdata">Status</th>
	      		<th width="10%" class="listdata">Options</th>
      		</tr>
      	</THEAD>	      		
      		<tbody>
 			</tbody>
      </table>
      </div>
    </form>
    
     
    
</div>
	

  <script src="includes/js/jQuery.print.js"></script>	
 