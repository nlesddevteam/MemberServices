<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  TravelClaim claim = null;
  HistoryItem item = null;
  Iterator items = null;
  Iterator h_items = null;
  SimpleDateFormat sdf_title = null;
  usr = (User) session.getAttribute("usr");
  sdf_title = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  items = claim.getItems().iterator();
  h_items = claim.getHistory().iterator();
  
%>

<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
<script src="includes/js/travel.js"></script>

			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none"); 
    			  
    			
			});
			
			</script>
    <div id="printJob"> 
      <table width="100%" cellspacing=0 cellpadding="0">
        
       
        <tr class="no-print">
          <td width="100%" height="30" valign="bottom">
            <jsp:include page="tab_bar.jsp" flush="true">
              <jsp:param name="tab" value="history" />
              <jsp:param name="id" value="<%=claim.getClaimID()%>" />
              <jsp:param name="status" value="<%=claim.getCurrentStatus().getID()%>" />
              <jsp:param name="hasItems" value="<%=items.hasNext()%>" />
            </jsp:include>
          </td>
        </tr>
        
        <tr>
         <td width="100%"  class="claimTabContent" valign="top">
            			<table id="tab_content" width="100%" cellpadding="0" cellspacing="0">
              				<tr>
                				<td width="100%" valign="top" valign="top">
        
        
        
         <table width="100%">
          <tr>
            <td>
            
            <div class="claimHeaderText">Claim History for: <span style="text-transform:capitalize;"><%=claim.getPersonnel().getFullNameReverse()%></span> 
             			
		              <div style="float:right;">
		               <%if(claim instanceof PDTravelClaim){%>              
		              <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		              <%}else if(claim instanceof TravelClaim){%>
		              <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		              <%}%>
		              </div>
		              
              		
             </div> 
            
            
            </td>
            
           </tr> 
            
            
            
            
              <tr>
                <td width="100%" valign="top">
                  <table width="100%">                   
                    <tr style="border-bottom:1px solid grey;">
                      <td  width="33%" valign="top" class="itemsHeader">Date</td>
                      <td width="33%" valign="top" class="itemsHeader">Action</td>
                      <td width="33%" valign="top" class="itemsHeader">Performed By</td>
                    </tr>
                    <%while(h_items.hasNext()){
                      item = (HistoryItem) h_items.next();%>
                      <tr style="border-bottom:1px dashed silver;">
                        <td width="33%" valign="top" class="field_content"><%=item.getDatePerformed().toString()%></td>
                        <td width="33%" valign="top" class="field_content"><%=item.getActionPerformed()%></td>
                        <td width="33%" valign="top" class="field_content"><%=item.getPerformedBy().getFullName()%></td>
                      </tr>
                    <%}%>
                    <tr class="no-print">
                      <td colspan="3" align="right">
                       <a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img border=0 width=300 src=includes/img/nlesd-colorlogo.png></div><br/><br/>',append:'<div align=center style=font-size:9px;margin-top:20px;>95 Elizabeth Avenue, St. John&acute;s, NL &middot; A1B 1R6 &middot; Tel: (709) 758-2372 &middot; Fax: (709) 758-2706</div>'});">
                        <img style="padding-right:10px;padding-bottom:2px;padding-top:10px;" border=- src="includes/img/print-off.png" class="img-swap" title="Print claim history.">
                        </a> 
                      
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
 </td>
        </tr>
      </table>   
  
	</div>
	
	  		<!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>

