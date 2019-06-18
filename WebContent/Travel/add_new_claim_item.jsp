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

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Add New Claim Item</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
		<style type="text/css">@import "css/jquery-ui.css";</style>
		<script type="text/javascript" src="js/jquery-2.2.4.js"></script>
		<script type="text/javascript" src="js/jquery-ui.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script src="js/travel.js"></script>
		<%
    		Integer claimid=(Integer)request.getAttribute("claimid");
			Integer claimmonth=(Integer)request.getAttribute("fiscalmonth");
			Integer claimyear=(Integer)request.getAttribute("fiscalyear");
			Integer lastdaymonth=(Integer)request.getAttribute("lastdaymonth");
			TravelClaimItem item = (TravelClaimItem) request.getAttribute("claimitem");
    	%>



	</head>
	<body style="margin:1px;">
 	<form name="add_new_claim_form" method="post">
      <input type="hidden" name="claim_id" id="claim_id" value='<%=claimid%>'>
      <input type="hidden" name="item_id" id="item_id" value='<%=item == null ? "" :item.getItemID()%>'>
     <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          	<td id="form_header" width="100%" height="75">
            	<img src="images/add_new_claim_header.jpg"><br>
          	</td>
        </tr>
		<tr>
          <td id="form_body" height="100" width="100%">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%" valign="top" height="50">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      	<td colspan='2' align="center"><h3>Add/Edit Claim Item</h3><br><br></td>
                    </tr>
				    <tr>
                      	<td class="label">DATE</td>
                      	<td><input class="requiredinput_date" type="text" name="item_date" id="item_date" style="width:50%;" value='<%=item == null ? "" :item.getItemDateFormatted()%>'></td>
                    </tr>
                    <tr>
                    	<td class="label">DEPARTURE TIME</td>
                    	<td><input class="requiredinput" type="text" name="item_departure_time" id="item_departure_time" style="width:50%;" onkeyup='check()' onfocus="this.select();" value='<%= item == null ? "" : item.getDepartureTime() == null ? "" : item.getDepartureTime()%>'></td>
                    </tr>
                    <tr>
                    	<td class="label">RETURN TIME</td>
                    	<td><input class="requiredinput" type="text" name="item_return_time" id="item_return_time" style="width:50%;" onfocus="this.select();" value='<%=item == null ? "" : item.getReturnTime() == null ? "" : item.getReturnTime()%>'></td>
                    </tr>
                    <tr>
                    	<td class="label">KMS</td>
                    	<td><input class="requiredinput" type="text" name="item_kms" id="item_kms" style="width:50%;" onfocus="this.select();" onblur="return validateInteger(this);" value='<%=item==null ? 0 : item.getItemKMS()%>'></td>
                    </tr>
                    <tr>
                    	<td class="label">MEALS</td>
                    	<td ><input class="requiredinput" type="text" name="item_meals" id="item_meals" style="width:50%;" onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);" value='<%=item == null ? 0 : item.getItemMeals()%>'></td></tr>
                    <tr>
                    	<td class="label">LODGING</td>
                    	<td><input class="requiredinput" type="text" name="item_lodging" id="item_lodging" style="width:50%;"   onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);" value='<%=item == null ? 0 : item.getItemLodging()%>'></td></tr>
                    <tr>
                    	<td class="label">OTHER</td>
                    	<td><input class="requiredinput" type="text" name="item_other" id="item_other" style="width:50%;" onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);" value='<%=item == null ? 0 : item.getItemOther()%>'></td></tr>
                    <tr>
                    	<td class="label">DESCRIPTION</td>
                    	<td>
	                    	
	                      	<span style="font-size:10px;">Description should include all necessary information to review the claim (eg. departure and return points, and items included in other category).</span>
	                   </td>
	                </tr>
	                <tr>
	                	<td colspan='2'>
	                		<input class="requiredinput" type="text" name="item_desc" id="item_desc" style="width:100%;"   onfocus="this.select();" value='<%=item == null ? "" : item.getItemDescription()%>'><br />
	                	</td>
	                </tr>
                    <tr>
                        <td  align="center" colspan='2' style="padding-top:5px;padding-right:10px;">
                            <img style="padding-right:5px;" src="images/cancel_edit_claim_item_01.gif" alt="Cancel edit claim item."
                               onmouseover="this.src='images/cancel_edit_claim_item_02.gif';"
                               onmouseout="this.src='images/cancel_edit_claim_item_01.gif';"
                               onclick="closedialogcancelbutton();">
                               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <img src="images/add_claim_item_01.gif" alt="<%=(request.getAttribute("EDIT")!=null)?"Submit edited":"Add"%> claim item."
                               onmouseover="this.src='images/add_claim_item_02.gif';"
                               onmouseout="this.src='images/add_claim_item_01.gif';"
                               onclick="addnewtravelclaimitem('<%=claimid%>');"><br>
                        </td>
                      </tr>                                                                                                                        
                  </table>
                </td>
              </tr>
                <tr>
                  <td id="details_error_message" align="center" valign="middle">
						
                  </td>
                 </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
        	<script>
		$('document').ready(function(){
    			$( ".requiredinput_date" ).datepicker({
      		      	changeMonth: false,//this option for allowing user to select month
      		      	changeYear: false, //this option for allowing user to select from year range
      		      	dateFormat: "dd/mm/yy",
      		      	minDate: new Date(<%=claimyear%>, <%=claimmonth%>, 1),
  		    		maxDate: new Date(<%=claimyear%>,<%=claimmonth%>, <%=lastdaymonth%>)
      		    	//set date range for claim
      		 	});
    			$( "#item_meals" ).blur();
    			$( "#item_lodging" ).blur();
    			$( "#item_other" ).blur();
		});
		</script>
    </body>
</html>
