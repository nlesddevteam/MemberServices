<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.pdreg.*,
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

<%
  User usr = null;
  Iterator iter = null;
  Personnel p = null;
  Personnel sup = null;
  TravelClaim claim = null;

  usr = (User) session.getAttribute("usr");
  
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  iter = ((Supervisors) request.getAttribute("SUPERVISORS")).iterator();
  sup = claim.getSupervisor();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Change Supervisor</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
		<style type="text/css">@import "css/jquery-ui.css";</style>
		<script type="text/javascript" src="js/jquery-2.2.4.js"></script>
		<script type="text/javascript" src="js/jquery-ui.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
	</head>
	<body style="margin:1px;">
    <%if(request.getAttribute("SUCCESS") != null){%>
      <script type="text/javascript">
        opener.document.location.href='viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>';
        self.close();
      </script>
    <%}else{%>      
      
    <form name="add_new_claim_form" method="post" action="changeSupervisor.html">
      <input type="hidden" name="op" value="YEAR-SELECT">
      <input type="hidden" name="claim_id" value="<%=claim.getClaimID()%>">
      <input type="hidden" name="osupervisor_id"  id="osupervisor_id" value="<%=sup.getPersonnelID()%>">
     
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/change_supervisor_header.jpg"><br>
          </td>
        </tr>
        <tr>
          <td id="form_body" height="100" width="100%">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%" valign="top" height="50">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td colspan="2" class="label">Claim Supervisor Information<br><br></td>
                    </tr>
                    <tr>
                      <td width="30%" align="right" style="padding-right:5px;">Your Supervisor:</td>
                      <td width="*" align="left">
                        <select name="supervisor_id" id="supervisor_id" class="requiredinput">
                          <option value="SELECT YEAR">SELECT SUPERVISOR</option>
                          <%while(iter.hasNext())
                            {
                              p = (Personnel) iter.next();
                              if((p.getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
                              <option style="text-transform:capitalize;" value="<%=p.getPersonnelID()%>" <%=((sup != null)&&(sup.getPersonnelID() == p.getPersonnelID()))?"SELECTED":""%>><%=p.getFullName().toLowerCase()%></option>
                          <%  }
                            }
                          %>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr id="buttons">
                <td width="100%" height="50" align="right" valign="bottom">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="server_message" width="70%" align="center" valign="middle">
                      <%if(request.getAttribute("msg") != null){%>
                          <%=request.getAttribute("msg")%>
                      <%}else{%>
                          &nbsp;
                      <%}%>
                      </td>
                      <td width="15%" align="right" valign="middle">
                        <img src="images/btn_submit_01.gif"
                            onmouseover="this.src='images/btn_submit_02.gif';"
                            onmouseout="this.src='images/btn_submit_01.gif';"
                            onclick="updatetravelclaimsupervisor('<%=claim.getClaimID()%>');"><br>
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
    <%}%>
	</body>
</html>
