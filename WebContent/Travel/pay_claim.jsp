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
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW" />

<%
  User usr = null;
  TravelClaim claim = null;
  SDSInfo sds = null;
  boolean no_permission = false;
  String acct_code = null;
  
  usr = (User) session.getAttribute("usr");
  
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");

  sds = claim.getPersonnel().getSDSInfo();
  
  if((claim.getSDSGLAccountCode()!=null)&&(!claim.getSDSGLAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = claim.getSDSGLAccountCode();
  }
  //else if((sds != null) && (sds.getAccountCode()!= null) && (!sds.getAccountCode().trim().equals("10000000000000000")))
  //{
    //acct_code = sds.getAccountCode();
  //}
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
		<title>Pay Travel Claim</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
    	<script language="JavaScript" src="js/common.js"></script>
    	<script language="JavaScript" src="js/travel.js"></script>
    	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    	<link rel="stylesheet" href="css/jquery-ui.css" />
    	<script src="js/jquery-ui.js"></script> 
    <script language="JavaScript">
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
	<body style="margin:1px;">
    <form name="payment_pending_form" method="post" action="payTravelClaim.html">
      <input type="hidden" name="op" value="CONFIRM">
      <input type="hidden" name="id" id="id" value="<%=claim.getClaimID()%>">
      
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          <td id="form_header" width="100%" height="75">
              <img src="images/paid_claim_header.jpg"><br>
          </td>
        </tr>
        <tr>
          <td id="form_body" height="*" width="100%">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="personnel_name" width="*" align="center">
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
                    <%if((request.getAttribute("RESULT") == null) && !no_permission){%>
                      <tr style="padding-top:10px;">
                        <td align="center" style="padding-right:5px;">
                          Are you sure you want to PAY this claim?
                        </td>
                      </tr>
                    
                        <tr>
                          <td width="100%" align="center">
                            <table width="100%" cellpadding="0" cellspacing="0">
                              <tr>
                                <td width="50%" valign="middle" align="right">
                                  <span class="label" style="padding-right:5px;">SDS Vendor Number:</span>
                                </td>
                                <td valign="middle" align="left"><input type=text id="sds_ven_num" name="sds_ven_num" style="width:100px;" class="requiredinput" value="<%=(sds!=null)?sds.getVendorNumber():""%>"></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      <tr style="padding-top:10px;">
                        <td align="center" valign="middle" style="padding-right:5px;">
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="50%" valign="middle" align="right"><span class="label" valign="middle" style="padding-right:5px;">GL Account Code:</span></td>
                              <td width="35%" valign="middle" align="center" class="requiredinput">
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
                              </td>
                              <td width="*">&nbsp;</td>
                            </tr>
                          </table>  
                        </td>
                      </tr>
                      <tr style="padding-top:10px;">
                        <td align="center" valign="middle" style="padding-right:5px;">
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="*" valign="middle" align="right" style="padding-right:5px;"><input type="checkbox" id="sds_tchr_par" name="sds_tchr_par"></td>
                              <td width="70%" valign="middle" align="left"><span valign="middle">Process through teacher payroll?</span></td>
                            </tr>
                          </table>  
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
                            onclick="paytravelclaim();"><br>
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
