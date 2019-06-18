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
                  <img src="images/policy_categories_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                  <table width="50%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>Add Category</h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Category Code:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="cat_code" name="cat_code" style="width:50px;"></td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Category Title:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="cat_title" name="cat_title" style="width:200px;"></td>
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
                             onclick="document.forms[0].submit();"><br><br>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Current Categories</h2>
                      </td>
                    </tr>
                    <%if(request.getAttribute("edit_msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("edit_msg")%> ***</td>
                      </tr>
                    <%}%>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="10%" align="left" class="label">Code</td>
                            <td width="35%" align="left" class="label">Title</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(!iter.hasNext()){%>
                              <tr><td colspan="3" class='content' align="left" style="color:#FF0000;font-weight:bold;">No categories available.</td></tr>
                          <%}else{
                              int r_cnt = 0;
                              while(iter.hasNext()){
                                pol_cat = (PolicyCategory)iter.next();%>
                                <tr id="row-view-<%=pol_cat.getCode()%>" style="display:inline;<%=((r_cnt++%2)==0)?"background-color:#FFFFFF;":"background-color:#E0E0E0;"%>">
                                  <td width="10%" align="left" class="content" style="padding-left:3px;border-bottom:solid 2px #c0c0c0;"><%=pol_cat.getCode()%></td>
                                  <td width="50%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=pol_cat.getTitle()%></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-bottom:solid 2px #c0c0c0;">
                                    <img src="images/btn_edit_01.gif"
                                         onmouseover="src='images/btn_edit_02.gif';"
                                         onmouseout="src='images/btn_edit_01.gif';"
                                         onclick="show_row('<%=pol_cat.getCode()%>', 'EDIT');">&nbsp;
                                    <img src="images/btn_delete_01.gif"
                                         onmouseover="src='images/btn_delete_02.gif';"
                                         onmouseout="src='images/btn_delete_01.gif';"
                                         onclick="document.location.href='deletePolicyCategory.html?code=<%=pol_cat.getCode()%>';">
                                  </td>
                                </tr>
                                <tr id="row-edit-<%=pol_cat.getCode()%>" style="display:none;" class="edit_row">
                                  <td width="10%" align="left" class="content" style="padding-left:3px;border-left:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><%=pol_cat.getCode()%></td>
                                  <td width="50%" align="left" class="content" style="border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><input type="text" class="requiredinput" id="<%=pol_cat.getCode()%>_title" name="<%=pol_cat.getCode()%>_title" style="width:175px;" value="<%=pol_cat.getTitle()%>"></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-right:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    <img src="images/btn_save_01.gif"
                                         onmouseover="src='images/btn_save_02.gif';"
                                         onmouseout="src='images/btn_save_01.gif';"
                                         onclick="document.forms[0].action='editPolicyCategory.html?code=<%=pol_cat.getCode()%>'; document.forms[0].submit();">&nbsp;
                                    <img src="images/btn_cancel_01.gif"
                                         onmouseover="src='images/btn_cancel_02.gif';"
                                         onmouseout="src='images/btn_cancel_01.gif';"
                                         onclick="show_row('<%=pol_cat.getCode()%>', 'VIEW');">
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