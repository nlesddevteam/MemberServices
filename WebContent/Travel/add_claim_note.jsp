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

<esd:SecurityCheck permissions="TRAVEL-CLAIM-SUPERVISOR-VIEW,TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW" />

<%
  User usr = null;
  TravelClaim claim = null;
  boolean no_permission = false;

  usr = (User) session.getAttribute("usr");
  
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");

  if(request.getAttribute("NOPERMISSION") != null)
  {
    no_permission = true;
  }
  else
  {
    no_permission = false;
  }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Add Claim Note</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
		<style type="text/css">@import "css/jquery-ui.css";</style>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="../js/common.js"></script>
    	<script src="js/jquery-ui.js"></script>
    	<script src="js/travel.js"></script>
    	<script language="JavaScript" src="js/common.js"></script> 
	</head>
	<body style="margin:1px;">
    <form name="add_claim_note_form" method="post" action="addTravelClaimNote.html">
      <input type="hidden" name="op" value="CONFIRM">
      <input type="hidden" name="id" id="id" value="<%=claim.getClaimID()%>">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/add_note_header.jpg"><br>
          </td>
        </tr>
        <tr>
          <td id="form_body" height="*" width="100%">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="personnel_name" align="center" colspan="2">
                        <%=claim.getPersonnel().getFullNameReverse()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="center">
                        <span class="label" style="padding-right:5px;">Claim:</span>
                        <%=!(claim instanceof PDTravelClaim)?Utils.getMonthString(claim.getFiscalMonth()) + " " +  
                          Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()):"PD - " + ((PDTravelClaim)claim).getPD().getTitle()%>
                      </td>
                    </tr>
                    <tr style="padding-left:14px;">
                      <td colspan="2" align="left" class="label">Note:</td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" class="label">
                        <textarea id="note" name="note" class="requiredinput" name="note" style="width:90%;height:100px;"></textarea>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" height="*" align="right" valign="bottom">
                  <table width="100%"  height="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="server_message" width="70%" align="center" valign="middle">
                      <%if(request.getAttribute("msg") != null){%>
                          <%=request.getAttribute("msg")%>
                      <%}else{%>
                          &nbsp;
                      <%}%>
                      </td>
                      <td width="15%" align="right" valign="middle">
                        <img src="images/btn_add_01.gif"
                          onmouseover="this.src='images/btn_add_02.gif';"
                          onmouseout="this.src='images/btn_add_01.gif';"
                          onclick="addnewtravelclaimnote();"><br>
                      </td>
                      <td width="15%" align="left" valign="middle">
                        <img src="images/btn_close_01.gif"
                          onmouseover="this.src='images/btn_close_02.gif';"
                          onmouseout="this.src='images/btn_close_01.gif';"
                          onclick="closedialogcancelbutton();"><br>
                      </td>
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
