<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 com.esdnl.sds.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-CLAIM-SUPERVISOR-VIEW" />

<%
  User usr = null;
  TravelClaim claim = null;
  SDSInfo sds = null;
  String acct_code = null;
  boolean no_permission = false;

  usr = (User) session.getAttribute("usr");
  
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  sds = claim.getPersonnel().getSDSInfo();
  
  if((claim.getSDSGLAccountCode()!=null)&&(!claim.getSDSGLAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = claim.getSDSGLAccountCode();
  }
  else if((sds != null) && (sds.getAccountCode()!= null) && (!sds.getAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = sds.getAccountCode();
  }
  else
  {
    acct_code = null;
  }

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
		<title>Approve/Decline Claim</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
    	<script language="JavaScript" src="js/common.js"></script>
    	<script language="JavaScript" src="js/travel.js"></script>
    	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    	<link rel="stylesheet" href="css/jquery-ui.css" />
    	<script src="js/jquery-ui.js"></script> 
    <script type="text/javascript">	
      function format_text(fld, length)
      {
        var i = 0;
        var tmp = fld.value;
        if(tmp.length < length)
        {
          for(i=(length-tmp.length); i > 0; i--)
            tmp = "0" + tmp;

          fld.value = tmp;
        }
      }
    </script>
	</head>
	<body style="margin:1px;" onload="self.resizeTo(document.body.scrollWidth+11, document.body.scrollHeight+60);">
    <form name="approve_decline_claim_form" method="post" action="approvedeclineTravelClaim.html">
      <input type="hidden" name="op" id="op" value="<%=request.getAttribute("op") + "D"%>">
      <input type="hidden" name="id" id="id" value="<%=claim.getClaimID()%>">
      
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          <td id="form_header" width="100%" height="75">
            <%if(((String)request.getAttribute("op")).equalsIgnoreCase("APPROVE")){%>
              <img src="images/approve_claim_header.jpg"><br>
            <%}else if(((String)request.getAttribute("op")).equalsIgnoreCase("DECLINE")){%>
              <img src="images/decline_claim_header.jpg"><br>
            <%}%>
          </td>
        </tr>
        <tr>
          <td id="form_body" height="*" width="100%">
            <input type="hidden" name="id" value="<%=claim.getClaimID()%>">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="personnel_name" width="*" align="center" colspan="2">
                        <%=claim.getPersonnel().getFullNameReverse()%>
                      </td>
                    </tr>
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Claim:</td>
                      <td width="*" align="left">
                        <%=!(claim instanceof PDTravelClaim)?Utils.getMonthString(claim.getFiscalMonth()) + " " + claim.getFiscalYear():"PD - " + ((PDTravelClaim)claim).getPD().getTitle()%>
                      </td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td colspan="2" align="center" style="padding-right:5px;">
                        Are you sure you want to 
                        <%if(((String)request.getAttribute("op")).equalsIgnoreCase("APPROVE")){%>
                          APPROVE
                        <%}else if(((String)request.getAttribute("op")).equalsIgnoreCase("DECLINE")){%>
                          DECLINE
                        <%}%> 
                        this claim?
                      </td>
                    </tr>
                    <%if(((String)request.getAttribute("op")).equalsIgnoreCase("APPROVE")){%>
                      <tr style="padding-top:10px;">
                        <td align="center" valign="middle" style="padding-right:5px;" colspan="2">
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr><td colspan="3" align="center" style="font-size:11px;font-weight:bold;padding-bottom:3px;">Optional Information</td></tr>
                            <tr>
                              <td width="50%" valign="middle" align="right"><span class="label" valign="middle" style="padding-right:5px;">GL Account Code:</span></td>
                              <td width="35%" valign="middle" align="center" class="requiredinput">
                              	<%if(request.getAttribute("RESULT") == null){%>
                                <table width="100%" cellpadding="0" cellspacing="0">
                                  <td valign="middle"><input type=text id="gl_acc_part_1" name="gl_acc_part_1" style="width:5px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(0, 1):"1"%>" onfocus="this.select();" onblur="format_text(this, 1);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_2" name="gl_acc_part_2" style="width:20px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(1, 5):"0000"%>" onfocus="this.select();" onblur="format_text(this, 4);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_3" name="gl_acc_part_3" style="width:5px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(5, 6):"0"%>" onfocus="this.select();" onblur="format_text(this, 1);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_4" name="gl_acc_part_4" style="width:10px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(6, 8):"00"%>" onfocus="this.select();" onblur="format_text(this, 2);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_5" name="gl_acc_part_5" style="width:20px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(8, 12):"0000"%>" onfocus="this.select();" onblur="format_text(this, 4);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_6" name="gl_acc_part_6" style="width:10px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(12, 14):"00"%>" onfocus="this.select();" onblur="format_text(this, 2);"></td>
                                  <td valign="middle">-</td>
                                  <td valign="middle"><input type=text id="gl_acc_part_7" name="gl_acc_part_7" style="width:15px;" class="requiredinput_gl_acc" value="<%=(acct_code!=null)?acct_code.substring(14):"000"%>" onfocus="this.select();" onblur="format_text(this, 3);"></td>
                                </table>
                                <% } else { %>
                                	<%= claim.getSDSGLAccountCode() %>
                                <% } %>
                              </td>
                              <td width="*">&nbsp;</td>
                            </tr>
                          </table>  
                        </td>
                      </tr>
                    <%}else if(((String)request.getAttribute("op")).equalsIgnoreCase("DECLINE")){%>
                      <tr style="padding-top:10px;">
                        <td align="center"  colspan="2" style="padding-right:5px;">
                          <span class="label">Note:</span> <span class="small_text">(optional)</span>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" align="center" class="label" style="padding-right:5px;">
                          <textarea id="note" name="note" style="width:90%;height:100px;"></textarea>
                        </td>
                      </tr>
                    <%}%>
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
                      <%if((request.getAttribute("RESULT") == null) && !no_permission){%>
                        <td width="15%" align="right" valign="middle">
                          <img src="images/btn_yes_01.gif"
                            onmouseover="this.src='images/btn_yes_02.gif';"
                            onmouseout="this.src='images/btn_yes_01.gif';"
                            onclick="approvedeclinetravelclaim();"><br>
                        </td>
                        <td width="15%" align="left" valign="middle">
                          <img src="images/btn_no_01.gif"
                            onmouseover="this.src='images/btn_no_02.gif';"
                            onmouseout="this.src='images/btn_no_01.gif';"
                            onclick="closedialogcancelbutton();"><br>
                        </td>

                      <%}%>
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
