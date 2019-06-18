<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 com.esdnl.webmaint.policies.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
  PolicyRegulation reg = null;
  Iterator iter = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
        && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DISTRICTPOLICIES")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

    iter = ((Vector)request.getAttribute("POLICY_REGULATIONS")).iterator();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="../js/common.js"></script>
	</head>
	<body style="margin-top:-30px;">
    <form id="pol_cat_frm" action="addPolicyCategory.html" method="post">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="images/district_policy_regulations_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="70%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Policy Regulations</h2>
                      </td>
                    </tr>
                    <%if(request.getAttribute("edit_msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("edit_msg")%> ***<br></td>
                      </tr>
                    <%}%>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="15%" align="left" class="label">Cat. Code</td>
                            <td width="15%" align="left" class="label">Policy Code</td>
                            <td width="15%" align="left" class="label">Reg. Code</td>
                            <td width="35%" align="left" class="label">Title</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(!iter.hasNext()){%>
                              <tr><td colspan="5" class='content' align="left" style="color:#FF0000;font-weight:bold;">This policy has no attached regulations.</td></tr>
                          <%}else{
                              int r_cnt = 0;
                              while(iter.hasNext()){
                                reg = (PolicyRegulation)iter.next();%>
                                <tr style="font-weight:bold;display:inline;<%=((r_cnt++%2)==0)?"background-color:#FFFFFF;":"background-color:#E0E0E0;"%>">
                                  <td width="15%" align="left" class="content" style="padding-left:3px;border-bottom:solid 2px #c0c0c0;"><%=reg.getCategoryCode()%></td>
                                  <td width="15%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=reg.getPolicyCode()%></td>
                                  <td width="15%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=reg.getRegulationCode()%></td>
                                  <td width="35%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=reg.getTitle()%></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-bottom:solid 2px #c0c0c0;">
                                    <table width="100%" cellpadding="0" cellspacing="1" align="center" valign="top" border="0">
                                      <tr>
                                        <td width="100%">
                                          <img src="images/btn_view_01.gif"
                                            onmouseover="src='images/btn_view_02.gif';"
                                            onmouseout="src='images/btn_view_01.gif';"
                                            onclick="openWindow('VIEW_POLICY', 'http://www.esdnl.ca/about/policies/esd/regulations/<%=reg.getCategoryCode()%>_<%=reg.getPolicyCode()%>_<%=reg.getRegulationCode()%>.pdf',800, 600, 1);">&nbsp;
                                          <img src="images/btn_delete_01.gif"
                                            onmouseover="src='images/btn_delete_02.gif';"
                                            onmouseout="src='images/btn_delete_01.gif';"
                                            onclick="document.location.href='deletePolicyRegulation.html?cat=<%=reg.getCategoryCode()%>&code=<%=reg.getPolicyCode()%>&r_code=<%=reg.getRegulationCode()%>';">
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                            <%}%>
                          <%}%>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" height="104" align="center" valign="middle" style="background: url('images/footer_bkg.gif') top left no-repeat;">
                  <img src="images/btn_home_01.gif"
                       onmouseover="src='images/btn_home_02.gif';"
                       onmouseout = "src='images/btn_home_01.gif';"
                       onclick="document.location.href='viewWebMaintenance.html';">
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>