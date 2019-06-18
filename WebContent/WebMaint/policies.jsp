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
  Policy pol = null;
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

    iter = ((Vector)request.getAttribute("POLICIES")).iterator();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript">
      function show_row(code, state)
      {
        v_row = document.getElementById('row-view-' + code);
        e_row = document.getElementById('row-edit-' + code)
        
        if(v_row && e_row)
        {
          if(state == 'VIEW')
          {
            v_row.style.display = 'inline';
            e_row.style.display = 'none';
          }
          else if(state == 'EDIT')
          {
            v_row.style.display = 'none';
            e_row.style.display = 'inline';
          }
        }
      }
    </script>
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
                  <img src="images/district_policies_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="70%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Current Policies</h2>
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
                            <td width="20%" align="left" class="label">Policy Code</td>
                            <td width="40%" align="left" class="label">Title</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(!iter.hasNext()){%>
                              <tr><td colspan="4" class='content' align="left" style="color:#FF0000;font-weight:bold;">No policies available.</td></tr>
                          <%}else{
                              int r_cnt = 0;
                              while(iter.hasNext()){
                                pol = (Policy)iter.next();%>
                                <tr id="row-view-<%=pol.getCategoryCode()%>-<%=pol.getCode()%>" style="font-weight:bold;display:inline;<%=((r_cnt++%2)==0)?"background-color:#FFFFFF;":"background-color:#E0E0E0;"%>">
                                  <td width="15%" align="left" class="content" style="padding-left:3px;border-bottom:solid 2px #c0c0c0;"><%=pol.getCategoryCode()%></td>
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=pol.getCode()%></td>
                                  <td width="40%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=pol.getTitle()%></td>
                                  <td width="*" align="left" class="content" style="padding-right:3px;border-bottom:solid 2px #c0c0c0;">
                                    <table width="100%" cellpadding="0" cellspacing="1" align="center" valign="top" border="0">
                                      <tr>
                                        <td width="100%">
                                          <img src="images/btn_view_01.gif"
                                            onmouseover="src='images/btn_view_02.gif';"
                                            onmouseout="src='images/btn_view_01.gif';"
                                            onclick="openWindow('VIEW_POLICY', 'http://www.esdnl.ca/about/policies/esd/<%=pol.getCategoryCode()%>_<%=pol.getCode()%>.pdf',800, 600, 1);">&nbsp;
                                          <img src="images/btn_reg_01.gif"
                                            onmouseover="src='images/btn_reg_02.gif';"
                                            onmouseout="src='images/btn_reg_01.gif';"
                                            onclick="document.location.href='policyRegulations.html?cat=<%=pol.getCategoryCode()%>&code=<%=pol.getCode()%>';">
                                        </td>
                                      </tr>
                                      <tr>
                                        <td width="100%">
                                          <img src="images/btn_edit_01.gif"
                                            onmouseover="src='images/btn_edit_02.gif';"
                                            onmouseout="src='images/btn_edit_01.gif';"
                                            onclick="show_row('<%=pol.getCategoryCode()%>-<%=pol.getCode()%>', 'EDIT');">&nbsp;
                                          <img src="images/btn_delete_01.gif"
                                            onmouseover="src='images/btn_delete_02.gif';"
                                            onmouseout="src='images/btn_delete_01.gif';"
                                            onclick="document.location.href='deletePolicy.html?cat=<%=pol.getCategoryCode()%>&code=<%=pol.getCode()%>';">
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr id="row-edit-<%=pol.getCategoryCode()%>-<%=pol.getCode()%>" style="display:none;" class="edit_row">
                                  <td width="15%" align="left" class="content" style="padding:14px 0px 14px 3px;border-left:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><%=pol.getCategoryCode()%></td>
                                  <td width="20%" align="left" class="content" style="padding:14px 0px 14px 0px;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><%=pol.getCode()%></td>
                                  <td width="40%" align="left" class="content" style="padding:14px 0px 14px 0px;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><input type="text" class="requiredinput" id="<%=pol.getCode()%>_title" name="<%=pol.getCode()%>_title" style="width:175px;" value="<%=pol.getTitle()%>"></td>
                                  <td width="*" align="left" class="content" style="padding:14px 3px 14px 0px;border-right:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    <img src="images/btn_save_01.gif"
                                         onmouseover="src='images/btn_save_02.gif';"
                                         onmouseout="src='images/btn_save_01.gif';"
                                         onclick="document.forms[0].action='editPolicy.html?cat=<%=pol.getCategoryCode()%>&code=<%=pol.getCode()%>'; document.forms[0].submit();">&nbsp;
                                    <img src="images/btn_cancel_01.gif"
                                         onmouseover="src='images/btn_cancel_02.gif';"
                                         onmouseout="src='images/btn_cancel_01.gif';"
                                         onclick="show_row('<%=pol.getCategoryCode()%>-<%=pol.getCode()%>', 'VIEW');">
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