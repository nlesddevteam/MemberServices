<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
         isThreadSafe="false"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW,TRAVEL-CLAIM-SUPERVISOR-VIEW" />

<%
  User usr = null;
  Personnel emp = null;
  TreeMap year_map = null;
  TravelClaims claims = null;
  TravelClaim claim = null;
  Vector y_claims = null;
  Iterator iter = null;
  Iterator y_iter = null;
  Map.Entry item = null;
  DecimalFormat df = null;
  SimpleDateFormat sdf_title = null;

  usr = (User) session.getAttribute("usr");
  
  emp = (Personnel) request.getAttribute("PERSONNEL");
  claims = emp.getTravelClaims();
  df = new DecimalFormat("#,##0");
  sdf_title = new SimpleDateFormat("EEE MMM dd, yyyy");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Claim History View</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="../css/calendar.css" rel="stylesheet">
    <style type="text/css">@import "css/travel.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript" src="../js/common.js"></script> 
	</head>
	<body style="margin:0px;" onload="resizeIFrame('claim_details', 360);">
    <form name="add_new_claim_form" method="post" action="addTravelClaim.html">
      <table width="100%" height="360" cellspacing=0 cellpadding="0">
        <tr>
          <td id="personnel_name" width="100%" height="20"><%=emp.getFullNameReverse()%></td>
        </tr>        
        <tr>
          <td width="100%" height="340" style="padding-bottom:1px;" valign="top">
            <table id="tab_bar" width="100%" cellpadding="0" cellspacing="0">
              <tr valign="bottom">
                <td class="tab_open" width="100" height="30" valign="bottom">
                  <img src="images/allclaims_tab_02.jpg"><br>
                </td>
                <td class="tab_end" width="*" height="30" valign="bottom" align="right">&nbsp;</td>
              </tr>
            </table>
            <table id="tab_content" width="100%" height="310" cellpadding="0" cellspacing="0">
              <tr>
                <td width="100%" height="100%" valign="top" style="padding:2px;" valign="top">
                  <table width="100%" cellspacing="2" cellpadding="0">
                    <tr style="padding-top:5px;">
                      <td style="padding-left:10px;" class="title">&nbsp;</td>
                    </tr>
                     <%if((claims != null) && (claims.size() > 0))
                      {
                        iter = claims.entrySet().iterator();
                        while(iter.hasNext())
                        {
                          item = (Map.Entry) iter.next();
                    %>    <tr><td style="padding-left:10px;" class="title"><%=((String)item.getKey())%></td></tr>
                          <tr><td style="padding-left:3px;"><ul>
                    <%      year_map = ((TreeMap)item.getValue());
                            if(year_map.get("MONTHLY-CLAIMS") != null){
                    %>        <li>Monthly Claims</li><ul>
                    <%        y_iter = ((Vector)year_map.get("MONTHLY-CLAIMS")).iterator();
                              while(y_iter.hasNext()){
                                claim = (TravelClaim) y_iter.next();
                                if(claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)|| claim.getCurrentStatus().equals(TravelClaimStatus.PAID)){
                    %>          <li><a class="claim" href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');"><%=Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())%></a></li>
                    <%          }
                              }
                    %>        </ul>
                    <%      }
                            if(year_map.get("PD-CLAIMS") != null){
                    %>        <li>PD Claims</li><ul>
                    <%        y_iter = ((Vector)year_map.get("PD-CLAIMS")).iterator();
                              while(y_iter.hasNext())
                              {
                                claim = (TravelClaim) y_iter.next();
                                if(claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)||claim.getCurrentStatus().equals(TravelClaimStatus.PAID)){
                    %>          <li><a class="claim" href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');"><%=((PDTravelClaim)claim).getPD().getTitle() + " - " + sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%></a></li>
                    <%          }
                              }
                    %>        </ul>           
                    <%      }
                    %>    </ul>
                          </td></tr>
                    <%  }
                      }
                      else
                      {
                    %>
                        <tr><td>0 claims on record.</td></tr>
                    <%}%>
                    <tr style="padding-top:5px;">
                      <td colspan="3">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
	</body>
</html>
