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
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

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
			
 <script>
    $( document ).ready(function() {
    	$('#loadingSpinner').css("display","none");  
    	$( "#start_date" ).datepicker({
		      changeMonth: true,//this option for allowing user to select month
		      changeYear: true, //this option for allowing user to select from year range
		      dateFormat: "dd/mm/yy"
		 });
    	
    	$('#submit').click(function() {
            if (!$('#start_date').val()) {
                alert('Please enter start date.');
                return false;
            } else {
            	loadingData();
            }
        })
    	
    });
    </script>

<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/exportfile.png" style="max-width:200px;" border=0/> 
		<div class="siteHeaderBlue">Export Claims to a File</div>

    <form name="claim_export_form" method="post" action="exportPaidTravelClaims.html">
      <input type="hidden" name="op" value="CONFIRM">
 Click on the box below to select starting date to export data from:<br/><br/>
             <div class="pageBodyText">    
      
     <input  class="form-control" type="text" name="start_date" id="start_date" readonly style="max-width:250px;" placeholder="Select Date for File Export"><br/>
      
      <input type="checkbox" id="include_exported" name="include_exported"> Include already exported claims?<br/>
      
      <input type="checkbox" id="dataTableExcel" name="dataTableExcel"> Build Excel/CVS ready Table? (This will NOT export a SDS .dat file. Uncheck this option if you wish to do old way.)
      <br/><br/>
                      
				  <%if((request.getAttribute("RESULT") == null) && !no_permission){%>
				                 <button id="submit" type="submit" class="btn btn-sm btn-success" title="Export Data">Export Data File</button>                                        
				                 <a href="index.jsp" class="btn btn-sm btn-danger" title="Cancel Export"  onclick="loadingData();">Cancel Export</a>
				 <%}else{%>
				                  <a href="index.jsp" class="btn btn-sm btn-danger" title="Cancel Export"  onclick="loadingData();">Cancel Export</a>
				<%}%>
              </div>
              
    </form>
 

