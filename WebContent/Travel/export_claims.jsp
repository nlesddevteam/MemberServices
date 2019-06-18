<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 com.esdnl.sds.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
         isThreadSafe="false"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-SDS-EXPORT" />

<%
  User usr = null;
  boolean no_permission = false;

  usr = (User) session.getAttribute("usr");
  
  if(request.getAttribute("NOPERMISSION") != null)
  {
    no_permission = true;
  }
  else
  {
    no_permission = false;
  }
%>

			<script type="text/javascript" src="includes/js/travel_ajax_v1.js"></script>
			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");  
			});
			
			</script>
			
			 <script>
    $( document ).ready(function() {
    	$( "#start_date" ).datepicker({
		      changeMonth: true,//this option for allowing user to select month
		      changeYear: true, //this option for allowing user to select from year range
		      dateFormat: "dd/mm/yy"
		 });
    });

    </script>

    <div class="claimHeaderText">Export Claims to SDS</div>
    
    <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
    
  
    	    
    <form name="claim_export_form" method="post" action="exportPaidTravelClaims.html">
      <input type="hidden" name="op" value="CONFIRM">
      
      <b>Select Starting Date:</b><br/>
      
      <input class="requiredinput_date" type="text" name="start_date" id="start_date" style="width:200px;" readonly><br/><br/>
      
      <input type="checkbox" id="include_exported" name="include_exported"> Include already exported claims?
      
      <br/><br/>
      
                 
                      <%if(request.getAttribute("msg") != null){%>
                          <%=request.getAttribute("msg")%>
                      <%}else{%>
                          &nbsp;
                      <%}%>
                     
                      <%if((request.getAttribute("RESULT") == null) && !no_permission){%>
                       
                          <img src="includes/img/export-off.png" border=0 class="img-swap" onclick="process_message('server_message', 'Processing request...');document.forms[0].submit();"> &nbsp; 
                          <a href="index.jsp" title="Cancel Export"><img src="includes/img/cancel-off.png" border=0 class="img-swap"></a>
                      
                      <%}else{%>
                       
                         <a href="index.jsp" title="Cancel Export"><img src="includes/img/cancel-off.png" border=0 class="img-swap"></a>
                      <%}%>
                  
              
    </form>
    <script language="JavaScript">
      var datepicker = new CalendarPopup(document.forms['claim_export_form'].elements['start_date']);
      datepicker.year_scroll = true;
      datepicker.time_comp = false;
    </script>

