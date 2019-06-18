<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  TravelClaim claim = null;
  TravelClaimItem item = null;
  boolean no_permission = false;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;

  usr = (User) session.getAttribute("usr");

  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  item = (TravelClaimItem) request.getAttribute("TRAVELCLAIMITEM");

  if(request.getAttribute("NOPERMISSION") != null)
  {
    no_permission = true;
  }
  else
  {
    no_permission = false;
  }

  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Delete Claim item</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/travel.css";</style>
        <script language="JavaScript" src="js/common.js"></script>
    	<script language="JavaScript" src="js/travel.js"></script>
    	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    	<link rel="stylesheet" href="css/jquery-ui.css" />
    	<script src="js/jquery-ui.js"></script>     
	</head>
	<body style="margin:1px;">
    <form name="delete_claim__item_form" method="post" action="deleteTravelClaimItem.html">
      <input type="hidden" name="op" value="CONFIRM">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border:solid 1px #005100;">
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/delete_claim_item_header.jpg"><br>
          </td>
        </tr>
        <tr>
          <td id="form_body" height="*" width="100%">
            <input type="hidden" name="cid" value="<%=claim.getClaimID()%>">
            <input type="hidden" name="iid" value="<%=item.getItemID()%>">
            <table width="100%" height="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Claim:</td>
                      <td width="*" align="left">
                        <%=Utils.getMonthString(claim.getFiscalMonth()) + " " + claim.getFiscalYear()%>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" class="label" style="padding-right:5px;">Item Date</td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" class="field_content"><%=item.getItemDate().toString()%></td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" class="label" style="padding-right:5px;">Description</td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" class="field_content"><%=item.getItemDescription()%></td>
                    </tr>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="2" border="0">
                          <tr>
                            <td width="25%" align="center" class="label">Kms</td>
                            <td width="25%" align="center" class="label">Meals</td>
                            <td width="25%" align="center" class="label">Lodging</td>
                            <td width="25%" align="center" class="label">Other</td>
                          </tr>
                          <tr>
                            <td width="25%" align="center" class="field_content"><%=kms_df.format(item.getItemKMS())%></td>
                            <td width="25%" align="center" class="field_content"><%=curr_df.format(item.getItemMeals())%></td>
                            <td width="25%" align="center" class="field_content"><%=curr_df.format(item.getItemLodging())%></td>
                            <td width="25%" align="center" class="field_content"><%=curr_df.format(item.getItemOther())%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td colspan="2" align="center" style="padding-right:5px;">
                        Are you sure you want to delete this claim item?
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
                      <%if((request.getAttribute("RESULT") == null) && !no_permission){%>
                        <td width="15%" align="right" valign="middle">
                          <img src="images/btn_yes_01.gif"
                            onmouseover="this.src='images/btn_yes_02.gif';"
                            onmouseout="this.src='images/btn_yes_01.gif';"
                            onclick="deletetravelclaimitem('<%=claim.getClaimID()%>','<%=item.getItemID()%>')"><br>
                        </td>
                        <td width="15%" align="left" valign="middle">
                          <img src="images/btn_no_01.gif"
                            onmouseover="this.src='images/btn_no_02.gif';"
                            onmouseout="this.src='images/btn_no_01.gif';"
                            onclick="closedialogcancelbutton();"><br>
                        </td>
                      <%}else{%>
                        <td width="30%" align="right" valign="middle">
                          <%if((request.getAttribute("RESULT") != null) 
                            && ((String)request.getAttribute("RESULT")).equalsIgnoreCase("SUCCESS")){%>
                              <img src="images/btn_close_01.gif"
                                onmouseover="this.src='images/btn_close_02.gif';"
                                onmouseout="this.src='images/btn_close_01.gif';"
                                onclick="opener.parent.frames[0].document.location.href='tree_items.jsp'; opener.document.location.href='viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>';self.close();"><br>
                          <%}else{%>
                              <img src="images/btn_close_01.gif"
                                onmouseover="this.src='images/btn_close_02.gif';"
                                onmouseout="this.src='images/btn_close_01.gif';"
                                onclick="self.close();"><br>
                          <%}%>
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
