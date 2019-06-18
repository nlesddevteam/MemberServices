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
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
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
    		                $("#divselectt").hide();
    		                $("#divselectr").hide();
    		                $("#divselectd").hide();
    		                break;
    		            case "Name":
       		                $("#divtext").show();
    		                $("#divselectt").hide();
    		                $("#divselectr").hide();
    		                $("#divselectd").hide();
    		                break;
    		            case "Type":
       		                $("#divtext").hide();
    		                $("#divselectt").show();
    		                $("#divselectr").hide();
    		                $("#divselectd").hide();
    		                break;
    		            case "Notes":
       		                $("#divtext").show();
    		                $("#divselectt").hide();
    		                $("#divselectr").hide();
    		                $("#divselectd").hide();
    		                break;
    		            case "Region":
       		                $("#divtext").hide();
    		                $("#divselectt").hide();
    		                $("#divselectr").show();
    		                $("#divselectd").hide();
    		                break;
    		            case "Expiry Date":
       		                $("#divtext").hide();
    		                $("#divselectt").hide();
    		                $("#divselectr").hide();
    		                $("#divselectd").show();
    		                break;     		                
    		            default:
    		                $("#divselect").hide();
    		            	$("#divselectp").hide();
    		                $("#divtext").show();
    		                break;
    		            }
    		    }
    		    var date_inputdle=$('#expirydate');
    		    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      	      	var options={
      	        	format: 'mm/dd/yyyy',
      	        	container: container,
      	        	todayHighlight: true,
      	        	autoclose: true,
      	      	};
				date_inputdle.datepicker(options);
        	});</script>
		
	<div id="printJob">	
      
	<div class="BCSHeaderText">Search Contracts</div>
			<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
            <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<table style="max-width:320px;">
	<tr>
	<td>
		Search By:<br/>
		
			<select id="searchby">
				<option value="SELECT">*** PLEASE SELECT ***</option>
				<c:forEach var="entry" items="${sby}">
					<option value='${entry.value}'>${entry.value}</option>
				</c:forEach>
			</select>

		<br/>
		
		<div id="divtext" style="display:none">
		For:<br/> 
		<input type="text" id="txtsearch" placeholder="Enter Search text"><br/>
		</div>
		<div id="divselectt"  style="display:none">
		For Type:<br/> 
		<select id="type">
			<option value="-1">*** Select ***</option>
			<c:forEach var="test" items="${types}" >
				<option value='${test.key}'>${test.value}</option>
			</c:forEach>
		</select><br/>
		</div>
		<div id="divselectr"  style="display:none">
		For Region<br/> 
		<p><select id="region">
			<option value="-1">*** Select ***</option>
			<c:forEach var="test" items="${regions}" >
				<option value='${test.key}'>${test.value}</option>
			</c:forEach>
		</select><br/>
		</div>		
		<div id="divselectd"  style="display:none">
		For:<br/> 
		<input class="form-control input-sm" id="expirydate" name="expirydate" placeholder="MM/DD/YYYY" type="text">
		<br/>
		</div>	
		
		</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
		<td>
		<input type="button" value="Search Contracts" onclick="ajaxSearchContracts()">	
		</td></tr></table>
		
		
	<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
    <div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:5px;margin-bottom:5px;padding:2px;"></div>
     <br/>     
    <form>
    <div id="BCS-Search">
      <table id="BCS-table" width="100%" class="BCSTable">
      	<THEAD>
  			<tr class="listHeader">
  				<th width="30%" class="listdata">Name</th>
  				<th width="15%" class="listdata">Type</th>
  				<th width="15%" class="listdata">Region</th>
  				<th width="15%" class="listdata">Expiry Date</th>
  				<th width="15%" class="listdata">Status</th>
  				<th width="10%" class="listdata">Options</th>
  			</tr>
      	</THEAD>	
      		
      		<tbody>
 			</tbody>
      </table>
      </div>
    </form>
</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Contract</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>	

  <script src="includes/js/jQuery.print.js"></script>	
 