<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />


		 <script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");  
			});
			
			
			
			
			
			</script>
		  <!-- Using my own API code key for now (Geoff). System MUST have a valid Google Maps API key to work-->
		   <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjNgMkM-j0ZxEWk0RQVnvSrrJg8V7vapU&callback=initMap" type="text/javascript"></script>
  		   <script src="includes/js/geocode.js" type="text/javascript"></script>
  			
 
 <img src="includes/img/gmaps.png" title="Powered by Google Maps" style="width:100%;max-width:150px;float:right;" border=0>
		<div class="claimHeaderText">		
			Travel Claim Distance Calculator 
		</div>
		Welcome to our travel claim distance calculator. 
		This system calculates the shortest and quickest valid distance9s) between two known locations for travel claim use. 
		<b>Your results may include more than one valid route to your destination.</b>
		 Be aware that results will only be as accurate as the information you provide. 
		 Select below what type of calculation you wish to use. 
		 The first selection assumes you know the full street address and location. 
		 The second selection will calculate based on the information you enter if you do not know the full address.
		 
		<br/><br/><span style="color:Red;">When calculating and given multiple route options, you must use the lowest distance of the options listed. 
		The lowest distance may NOT be the fastest route. 
		You can however take the faster or any route to your destination, but in calculating travel, we will only accept the lowest distance value.</span>
	
		 
		 
		 <br/><br/><b>NOTE:</b><i> This system is for assisting in calculating the distance of your travel based on the valid quickest/shortest driving distance between two places. You may have other special travel associated with your distance for your current claim(s).
		 The <b>lowest value kms</b> on the map results below (or any confirmed written distance reference documentation) should be used. <b>Round to nearest whole number</b>.
		 Final correct distance may be edited if neccessary in your claim for final approved payment.
		 </i>
		 
		  
	 <br/><br/>
	 
	 	A. <input type="radio" name="calculate-type" id="useForm1" onclick="show('form1');hide('form2');"> Calculate using full street addresses (i.e. 95 Elizabeth Avenue, St. John's, NL) - <span style="color:Green;">RECOMMENDED</span><br/>	
		B. <input type="radio" name="calculate-type" id="useForm2" onclick="show('form2');hide('form1');"> 
		Calculate using known information (address, street and/or town/city, location, building, etc). 
		<br/><br/>
	<div id="form1" style="width:100%;display:none;padding:10px;margin-bottom:5px;margin-top:5px;font-size:11px;border:1px solid silver;background-color:#FFF8DC;"">
		<b>A. Calculate using full street addresses</b> <span style="color:Green;">(Recommended)</span><p>
		<br/><b>Your Destination Address:</b><br/>
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="streetnum2" id="streetnum2" placeholder="#" size="4"/> <input type="text" name="addressb" id="addressb" placeholder="Street Name" size="30"/></div>
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="town2" id="town2" placeholder="Town/City" size="30" /> <input type="text" name="prov2" id="prov2" value="NL" disabled size="3"/></div>
		<div style="clear:both;"></div>	
		<br/><b>Leaving From:</b><br/>
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="streetnum1" id="streetnum1" placeholder="#" size="4"/> <input type="text" name="addressa" id="addressa" placeholder="Street Name" size="30"/></div>
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="town1" id="town1" placeholder="Town/City" size="30" /> <input type="text" name="prov1" id="prov1" value="NL" disabled size="3"/></div>
		<div style="clear:both;"></div>				
		<input type="button" id="travel_calculate1" value="Calculate Distance" onclick="setTimeout('initialize();', 3000 );"/><br/><br/>If map fails to load below or refresh based on new data, click Calculate to resubmit.
	</div>
	<br/>
	<div id="form2" style="width:100%;display:none;padding:10px;margin-bottom:5px;margin-top:5px;font-size:11px;border:1px solid silver;background-color:#F0F8FF;">
	<b>B. Calculate using known information</b> <span style="color:Red;">(Use only if you have limited address information)</span><p>
	<br/>Please be aware that this is NOT the best way to calculate distance as we recommend using the full street address system of A above. <p>
		The results you get using this feature here maybe off slightly as it estimates based on the amount of information you provide.<p>
		Only use this if there is no known street address. 
	    <p><b>Your Destination Address:</b><br/>	
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="addressd" id="addressd" placeholder="School, Address, and/or Town/City" size="35"/> <input type="text" name="prov4" id="prov4" value="NL" disabled size="3"/></div>
		<div style="clear:both;"></div>					
		<br/><b>Leaving From:</b><br/>
		<div style="float:left;padding-right:5px;padding-bottom:5px;"><input type="text" name="addressc" id="addressc" placeholder="School, Address, and/or Town/City" size="35"/> <input type="text" name="prov3" id="prov3" value="NL" disabled size="3"/></div>
		<div style="clear:both;"></div>
			
		<p>
		<input type="button" id="travel_calculate2" value="Calculate Distance" onclick="setTimeout('initialize();', 3000 );"/>
		
		<script>
		$( "#travel_calculate1" ).click(function() {
			  $("#calculateGG").css("display","block").delay(5000).fadeOut();
			});
		$( "#travel_calculate2" ).click(function() {
			  $("#calculateGG").css("display","block").delay(5000).fadeOut();
			});
		
		</script>
		
		<!-- <input type="button" id="travel_calculate" value="Calculate Distance" onclick="initialize();"/>--><br/><br/>If map fails to load below or refresh based on new data, click Calculate to resubmit.
	</div>
	<br/>
	<div id="calculateGG" style="display:none;color:white;background-color:Red;padding:3px;text-align:center;">VALIDATING DATA...</div>
	<div id="loadMes" style="display:none;color:white;background-color:Red;padding:3px;text-align:center;"> &nbsp; <b>*** PLEASE WAIT ***</b><br/>Verifying data and calculating your distance. If map fails to load, please re-submit data above. &nbsp; </div>
	
	<div class="alert alert-danger" style="display:none;" id="errMessageDisplay"></div>
	<div style="width:100%;text-align:center;" id="distance_direct"></div>
	<div style="width:100%;text-align:center;" id="distance_road"></div>
	<div id="map_directions" style="display:none;width:100%;padding-bottom:10px;">Below is a map of directions based on your data entry above. Select a Suggested Route as listed in the table at the right to display that route and driving directions.</div>
	<div style="clear:both;"></div>
	<div id="map_canvas" style="float:left;display:none;width:70%; height:100%;"></div>
	<div id="directionsPanel" style="display:none;font-size:11px;float:left;width:30%;height:auto;"></div>
	
	



 



			<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-lg">
			<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h5 class="modal-title">TRAVEL DISTANCE FORM CHECK</h5>
			</div>
			<div class="modal-body">
			<div id="errDisplay"></div>
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
			</div>
			</div>
			</div>

		

	
	
	
	
	
	
	
	
	
	