<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,com.esdnl.util.*,
                 com.esdnl.webmaint.ceoweb.bean.*,
                 com.esdnl.webmaint.ceoweb.constants.*,
                 java.io.*,
                 java.text.*"%>

<%
  User usr = null;
  MessageBean[] vbeans = null;
  MessageBean[] abeans = null;
  SimpleDateFormat sdf_full = new SimpleDateFormat("MMMM dd, yyyy");
  SimpleDateFormat sdf_cal = new SimpleDateFormat("dd/MM/yyyy");
  MessageTypeConstant view_type = null;
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

    vbeans = ((MessageBean[])request.getAttribute("MESSAGES"));
    abeans = ((MessageBean[])request.getAttribute("ARCHIVED_MESSAGES"));
    view_type = MessageTypeConstant.get((request.getAttribute("VIEW_TYPE") != null)? ((Integer)request.getAttribute("VIEW_TYPE")).intValue():0);
    
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "../css/webmaint.css";</style>
    <script language="JavaScript" src="../../js/common.js"></script>
    <script language="JavaScript" src="../js/CalendarPopup.js"></script>
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
    <form id="pol_cat_frm" action="addMessage.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="../images/messages_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('../images/body_bkg.gif') top left repeat-y;">
                  <table width="75%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>Add Message</h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Message Type:</span></td>
                      <td width="*" align="left" valign="middle">
                        <select name="msg_type" class="requiredinput">
                          <option value="-1">SELECT MESSAGE TYPE</option>
                          <%
                            for(int i=0; i < MessageTypeConstant.ALL.length; i++)
                            {
                              out.println("<option value='" + MessageTypeConstant.ALL[i].getTypeID()+ "'>" + MessageTypeConstant.ALL[i].getDescription() + "</option>");
                            }
                          %>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Date:</span></td>
                      <td width="*" align="left" valign="middle">
                        <table width="100" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="51"><input class="requiredinput_date" type="text" name="msg_date" style="width:51px;" value="" readonly></td>
                              <td width="*" align="left">
                                <img class="requiredinput_popup_cal" src="../images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='../images/cal_popup_02.gif';"
                                    onmouseout="this.src='../images/cal_popup_01.gif';"
                                    onclick="datepicker.popup();"><br>
                              </td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Title:</span></td>
                      <td width="*" align="left" valign="middle">
                        <input type="text" class="requiredinput" id="msg_title" name="msg_title" style="width:200px;">
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Text:</span></td>
                      <td width="*" align="left" valign="middle">
                        <textarea class="requiredinput" id="msg_txt" name="msg_txt" style="width:250px; height:75px;"></textarea>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Optional Image:</span></td>
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
                  </table>
                  
                  <br><br>
                  <table width="75%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Current 
                         <select name="view_type" class="requiredinput" onchange="document.location.href='viewMessages.html?type='+forms[0].view_type.options[forms[0].view_type.selectedIndex].value;">
                            <option value="-1">SELECT MESSAGE TYPE</option>
                            <%
                              for(int i=0; i < MessageTypeConstant.ALL.length; i++)
                              {
                                out.println("<option value='" + MessageTypeConstant.ALL[i].getTypeID()+ "'" + (((view_type != null) && view_type.equals(MessageTypeConstant.ALL[i]))?" SELECTED":"") + ">" + MessageTypeConstant.ALL[i].getDescription() + "</option>");
                              }
                            %>
                          </select>
                        </h2>
                      </td>
                    </tr>
                    <%if(request.getAttribute("edit_msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("edit_msg")%> ***</td>
                      </tr>
                    <%}%>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="3" cellspacing="0" align="center" valign="top" border="0">
                          <tr>
                            <td width="15%" align="left" class="label">Date</td>
                            <td width="40%" align="left" class="label">Title</td>
                            <td width="20%" align="left" class="label">Thumbnail</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if((vbeans == null) || (vbeans.length <= 0)){%>
                              <tr><td colspan="4" class='content' align="left" style="color:#FF0000;font-weight:bold;">No messages available.</td></tr>
                          <%}else{
                              int r_cnt = -1;
                              for(int i=0; i < vbeans.length; i++){%>
                                <tr id="row-view-<%=vbeans[i].getMessageID()%>" style="display:inline;padding-bottom:4px;" class="view_row_<%=(++r_cnt)%2 %>">
                                  <td width="15%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;"><%=sdf_full.format(vbeans[i].getMessageDate())%></td>
                                  <td width="40%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;"><%=vbeans[i].getMessageTitle()%></td>
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">
                                    <%if(!StringUtils.isEmpty(vbeans[i].getMessageImage())){%>
                                      <img src='http://director.esdnl.ca/message_images/<%=vbeans[i].getMessageImage()%>' width='50' height='50'>
                                    <%}else{%>
                                      N/A
                                    <%}%>
                                  </td>
                                  <td width="*" align="right" class="content" style="border-bottom:solid 2px #C0C0C0;" rowspan="2">
                                    <table width="100%" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td>
                                          &nbsp;<img src="../images/btn_edit_01.gif"
                                               onmouseover="src='../images/btn_edit_02.gif';"
                                               onmouseout="src='../images/btn_edit_01.gif';"
                                               onclick="show_row('<%=vbeans[i].getMessageID()%>', 'EDIT');">&nbsp;
                                          <img src="../images/btn_archive_01.gif"
                                               onmouseover="src='../images/btn_archive_02.gif';"
                                               onmouseout="src='../images/btn_archive_01.gif';"
                                               onclick="document.location.href='archiveMessage.html?id=<%=vbeans[i].getMessageID()%>';">&nbsp;
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          &nbsp;<img src="../images/btn_delete_01.gif"
                                               onmouseover="src='../images/btn_delete_02.gif';"
                                               onmouseout="src='../images/btn_delete_01.gif';"
                                               onclick="document.location.href='deleteMessage.html?id=<%=vbeans[i].getMessageID()%>';">&nbsp;
                                          <!--
                                          <img src="../images/btn_view_01.gif"
                                               onmouseover="src='../images/btn_view_02.gif';"
                                               onmouseout="src='../images/btn_view_01.gif';"
                                               onclick="openWindow('VIEW_DIRECTOR_REPORT', 'http://director.esdnl.ca/school_visits/<%=vbeans[i].getMessageImage()%>',300, 300, 1);">&nbsp;
                                          -->
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr class="view_row_<%=(r_cnt)%2%>" style="padding-top:5px;">
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">&nbsp;</td>
                                  <td align="left" class="content" colspan="2" valign="top" style="border-bottom:solid 2px #C0C0C0;text-transform:none;"><span style="font-weight:bold;">MESSAGE:<br></span><%=vbeans[i].getMessage().replaceAll("\n", "<BR><BR>")%></td>
                                </tr>
                                <tr id="row-edit-<%=vbeans[i].getMessageID()%>" style="display:none;" class="edit_row">
                                  <td width="15%" align="left" class="content" style="border-left:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    <table width="90%" cellpadding="0" cellspacing="0" style="padding:0px;">
                                      <tr style="height:18px;">
                                        <td width="90%"><input class="requiredinput_date" type="text" name="msg_date_<%=vbeans[i].getMessageID()%>" style="width:100%;" value="<%=sdf_cal.format(vbeans[i].getMessageDate())%>" readonly></td>
                                        <td width="*" align="left">
                                          <img class="requiredinput_popup_cal" src="../images/cal_popup_01.gif" alt="choose date"
                                              onmouseover="this.src='../images/cal_popup_02.gif';"
                                              onmouseout="this.src='../images/cal_popup_01.gif';"
                                              onclick="datepicker_<%=vbeans[i].getMessageID()%>.popup();"><br>
                                        </td>
                                      </tr>
                                    </table>
                                    <script language="JavaScript">
                                      var datepicker_<%=vbeans[i].getMessageID()%> = new CalendarPopup(document.forms['pol_cat_frm'].elements['msg_date_<%=vbeans[i].getMessageID()%>']);
                                      datepicker_<%=vbeans[i].getMessageID()%>.year_scroll = true;
                                      datepicker_<%=vbeans[i].getMessageID()%>.time_comp = false;
                                    </script>
                                  </td>
                                  <td width="25%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">
                                    <input type="text" class="requiredinput" id="title<%=vbeans[i].getMessageID()%>" 
                                      name="msg_title<%=vbeans[i].getMessageID()%>" style="width:200px;" value="<%=vbeans[i].getMessageTitle()%>">
                                    
                                  </td>
                                  <td width="25%" align="left" class="content" style="border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    <textarea class="requiredinput" id="caption_<%=vbeans[i].getMessageID()%>" 
                                           name="msg_txt_<%=vbeans[i].getMessageID()%>" style="width:100%;height:50px;"><%=vbeans[i].getMessage()%></textarea>
                                  </td>
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">
                                    <%if(!StringUtils.isEmpty(vbeans[i].getMessageImage())){%>
                                      <img src='http://director.esdnl.ca/message_images/<%=vbeans[i].getMessageImage()%>' width='50' height='50'>
                                    <%}else{%>
                                      N/A
                                    <%}%>
                                  </td>
                                  <td width="*" align="right" class="content" style="border-right:solid 2px #3399ff;border-top:solid 2px #3399ff;border-bottom:solid 2px #3399ff;">
                                    <img src="../images/btn_save_01.gif"
                                         onmouseover="src='../images/btn_save_02.gif';"
                                         onmouseout="src='../images/btn_save_01.gif';"
                                         onclick="document.forms[0].action='editMessage.html?id=<%=vbeans[i].getMessageID()%>'; document.forms[0].submit();">&nbsp;
                                    <img src="../images/btn_cancel_01.gif"
                                         onmouseover="src='../images/btn_cancel_02.gif';"
                                         onmouseout="src='../images/btn_cancel_01.gif';"
                                         onclick="show_row('<%=vbeans[i].getMessageID()%>', 'VIEW');">
                                  </td>
                                </tr>
                            <%}%>
                          <%}%>
                        </table>
                      </td>
                    </tr>
                  </table>
                  
                  <% if(abeans != null){ %>
                  <br><br>
                  <table width="75%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Archived Messages</h2>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="3" cellspacing="0" align="center" valign="top" border="0">
                          <tr>
                            <td width="15%" align="left" class="label">Date</td>
                            <td width="25%" align="left" class="label">Title</td>
                            <td width="20%" align="left" class="label">Thumbnail</td>
                            <td width="*" align="center">&nbsp;</td>
                          </tr>
                          <%if(abeans.length <= 0){%>
                              <tr><td colspan="4" class='content' align="left" style="color:#FF0000;font-weight:bold;">No messages archived.</td></tr>
                          <%}else{
                              int r_cnt = -1;
                              for(int i=0; i < abeans.length; i++){%>
                                <tr id="row-view-<%=abeans[i].getMessageID()%>" style="display:inline;" class="view_row_<%=(++r_cnt)%2 %>">
                                  <td width="15%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;"><%=sdf_full.format(abeans[i].getMessageDate())%></td>
                                  <td width="40%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;"><%=abeans[i].getMessageTitle()%></td>
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">
                                    <%if(!StringUtils.isEmpty(abeans[i].getMessageImage())){%>
                                      <img src='http://director.esdnl.ca/message_images/<%=abeans[i].getMessageImage()%>' width='50' height='50'> 
                                    <%}else{%>
                                      N/A
                                    <%}%>
                                  </td>
                                  <td width="*" align="right" class="content" style="border-bottom:solid 2px #C0C0C0;" rowspan="2">                                    
                                    <img src="../images/btn_delete_01.gif"
                                         onmouseover="src='../images/btn_delete_02.gif';"
                                         onmouseout="src='../images/btn_delete_01.gif';"
                                         onclick="document.location.href='deleteMessage.html?id=<%=abeans[i].getMessageID()%>';">&nbsp;
                                    <!--
                                    <img src="../images/btn_view_01.gif"
                                            onmouseover="src='../images/btn_view_02.gif';"
                                            onmouseout="src='../images/btn_view_01.gif';"
                                            onclick="openWindow('VIEW_DIRECTOR_REPORT', 'http://director.esdnl.ca/school_visits/<%=abeans[i].getMessageImage()%>',300, 300, 1);">&nbsp;
                                    -->
                                  </td>
                                </tr>
                                <tr class="view_row_<%=(r_cnt)%2%>" style="padding-top:5px;">
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">&nbsp;</td>
                                  <td align="left" class="content" colspan="2" valign="top" style="border-bottom:solid 2px #C0C0C0;text-transform:none;"><span style="font-weight:bold;">MESSAGE:<br></span><%=abeans[i].getMessage().replaceAll("\n", "<BR><BR>")%></td>
                                </tr>
                            <%}%>
                          <%}%>
                        </table>
                      </td>
                    </tr>
                  </table>
                  <%}%>
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
    <script language="JavaScript">
      var datepicker = new CalendarPopup(document.forms['pol_cat_frm'].elements['msg_date']);
      datepicker.year_scroll = true;
      datepicker.time_comp = false;
    </script>
	</body>
</html>