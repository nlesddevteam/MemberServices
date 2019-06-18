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
  PolicyCategory pol_cat = null;
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

    iter = ((Vector)request.getAttribute("POLICY_CATEGORIES")).iterator();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin-top:-30px;">
    <form id="pol_cat_frm" action="addPolicy.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="images/add_policy_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="60%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>Add Policy</h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Category Code:</span></td>
                      <td width="*" align="left" valign="middle">
                        <select id="cat_code" name="cat_code" class="requiredinput">
                          <%while(iter.hasNext()){
                              pol_cat = (PolicyCategory) iter.next();%>
                              <option value="<%=pol_cat.getCode()%>"><%=pol_cat.getCode()%></option>
                          <%}%>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Policy Code:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="pol_code" name="pol_code" style="width:50px;"></td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Policy Title:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="pol_title" name="pol_title" style="width:200px;"></td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">PDF File:</span></td>
                      <td valign="top" align="left" width="*"><input type="file" size="60" name="filedata" class="requiredinput"><BR></td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</td>
                      </tr>
                    <%}%>
                    
                    <tr>
                      <td colspan="2">
                        <br><img src="images/btn_save_01.gif"
                             onmouseover="src='images/btn_save_02.gif';"
                             onmouseout="src='images/btn_save_01.gif';"
                             onclick="progressBarInit(); document.forms[0].submit();"><br><br>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center">
                        <script language="javascript" src="js/timerbar.js"></script>
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