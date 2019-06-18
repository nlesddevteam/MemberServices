<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 com.esdnl.webmaint.ceoweb.bean.*,
                 java.io.*,
                 java.text.*"%>

<%
  User usr = null;
  DirectorReportBean[] rpt = null;
  SimpleDateFormat sdf_full = new SimpleDateFormat("MMMM yyyy");
  SimpleDateFormat sdf_file = new SimpleDateFormat("MM_yyyy");
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
        && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DIRECTORSWEB")))
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

    rpt = ((DirectorReportBean[])request.getAttribute("DIRECTOR-REPORTS"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "../css/webmaint.css";</style>
    <script language="JavaScript" src="../../js/common.js"></script>
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
    <form id="pol_cat_frm" action="addDirectorReport.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="../images/director_report_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('../images/body_bkg.gif') top left repeat-y;">
                  <table width="75%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>Add Report</h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Date:</span></td>
                      <td width="*" align="left" valign="middle">
                        <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td>
                                <select id="month" name="month" class="requiredinput">
                                  <option value="01">January</option>
                                  <option value="02">Febuary</option>
                                  <option value="03">March</option>
                                  <option value="04">April</option>
                                  <option value="05">May</option>
                                  <option value="06">June</option>
                                  <option value="07">July</option>
                                  <option value="08">August</option>
                                  <option value="09">September</option>
                                  <option value="10">October</option>
                                  <option value="11">November</option>
                                  <option value="12">December</option>
                                </select>
                              </td>
                              <td>
                                <select id="year" name="year" class="requiredinput">
                                  <%
                                    Calendar now = Calendar.getInstance();
                                    for(int i=0; i < 5; i++)
                                    {
                                      out.println("<option value='" + now.get(Calendar.YEAR) + "'>" + now.get(Calendar.YEAR) + "</option>");
                                      now.add(Calendar.YEAR, -1);
                                    }
                                  %>
                                </select>
                              </td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Title:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="title" name="title" style="width:200px;"></td>
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
                        <br><img src="../images/btn_save_01.gif"
                             onmouseover="src='../images/btn_save_02.gif';"
                             onmouseout="src='../images/btn_save_01.gif';"
                             onclick="progressBarInit(); document.forms[0].submit();"><br><br>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center" valign="middle">
                        <script language="javascript" src="../js/timerbar.js"></script><br>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Current Reports</h2>
                      </td>
                    </tr>
                    <%if(request.getAttribute("edit_msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("edit_msg")%> ***</td>
                      </tr>
                    <%}%>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="3" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="30%" align="left" class="label">Date</td>
                            <td width="40%" align="left" class="label">Title</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(rpt.length <= 0){%>
                              <tr><td colspan="3" class='content' align="left" style="color:#FF0000;font-weight:bold;">No reports available.</td></tr>
                          <%}else{
                              int r_cnt = 0;
                              for(int i=0; i < rpt.length; i++){%>
                                <tr id="row-view-<%=sdf_file.format(rpt[i].getReportDate())%>" style="display:inline;<%=((r_cnt++%2)==0)?"background-color:#FFFFFF;":"background-color:#E0E0E0;"%>">
                                  <td width="30%" align="left" class="content" style="padding-left:3px;border-bottom:solid 2px #c0c0c0;"><%=sdf_full.format(rpt[i].getReportDate())%></td>
                                  <td width="40%" align="left" class="content" style="border-bottom:solid 2px #c0c0c0;"><%=rpt[i].getReportTitle()%></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-bottom:solid 2px #c0c0c0;">
                                    <img src="../images/btn_edit_01.gif"
                                         onmouseover="src='../images/btn_edit_02.gif';"
                                         onmouseout="src='../images/btn_edit_01.gif';"
                                         onclick="show_row('<%=sdf_file.format(rpt[i].getReportDate())%>', 'EDIT');">&nbsp;
                                    <img src="../images/btn_delete_01.gif"
                                         onmouseover="src='../images/btn_delete_02.gif';"
                                         onmouseout="src='../images/btn_delete_01.gif';"
                                         onclick="document.location.href='deleteDirectorReport.html?dt=<%=sdf_file.format(rpt[i].getReportDate())%>';">&nbsp;
                                    <img src="../images/btn_view_01.gif"
                                            onmouseover="src='../images/btn_view_02.gif';"
                                            onmouseout="src='../images/btn_view_01.gif';"
                                            onclick="openWindow('VIEW_DIRECTOR_REPORT', 'http://director.esdnl.ca/director_reports/<%=sdf_file.format(rpt[i].getReportDate())%>.pdf',800, 600, 1);">&nbsp;
                                  </td>
                                </tr>
                                <tr id="row-edit-<%=sdf_file.format(rpt[i].getReportDate())%>" style="display:none;" class="edit_row">
                                  <td width="30%" align="left" class="content" style="padding-left:3px;border-left:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><%=sdf_full.format(rpt[i].getReportDate())%></td>
                                  <td width="40%" align="left" class="content" style="border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;"><input type="text" class="requiredinput" id="<%=sdf_file.format(rpt[i].getReportDate())%>_title" name="<%=sdf_file.format(rpt[i].getReportDate())%>_title" style="width:170px;" value="<%=rpt[i].getReportTitle()%>"></td>
                                  <td width="*" align="right" class="content" style="padding-right:3px;border-right:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    &nbsp;<img src="../images/btn_save_01.gif"
                                         onmouseover="src='../images/btn_save_02.gif';"
                                         onmouseout="src='../images/btn_save_01.gif';"
                                         onclick="document.forms[0].action='editDirectorReport.html?dt=<%=sdf_file.format(rpt[i].getReportDate())%>'; document.forms[0].submit();">&nbsp;
                                    <img src="../images/btn_cancel_01.gif"
                                         onmouseover="src='../images/btn_cancel_02.gif';"
                                         onmouseout="src='../images/btn_cancel_01.gif';"
                                         onclick="show_row('<%=sdf_file.format(rpt[i].getReportDate())%>', 'VIEW');">
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
                <td width="100%" height="104" align="center" valign="middle" style="background: url('../images/footer_bkg.gif') top left no-repeat;">
                  <img src="../images/btn_home_01.gif"
                       onmouseover="src='../images/btn_home_02.gif';"
                       onmouseout = "src='../images/btn_home_01.gif';"
                       onclick="document.location.href='../viewWebMaintenance.html';">
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>