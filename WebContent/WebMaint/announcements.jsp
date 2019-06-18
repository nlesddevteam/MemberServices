<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,com.esdnl.util.*,
                 com.esdnl.webmaint.esdweb.bean.*,
                 com.esdnl.webmaint.esdweb.constants.*,
                 java.io.*,
                 java.text.*" %>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-ANNOUNCEMENTS" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>English School District Web Maintenance - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/webmaint.css";</style>
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="js/CalendarPopup.js"></script>
    <style type="text/css">@import "css/smoothness/jquery-ui-1.8.4.custom.css";</style> 
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
		
    <script type="text/javascript">
			$('document').ready(function() {

				$('#msg_date').datepicker({
					autoSize: true,
					showOn: 'focus',
					showAnim: 'drop',
					dateFormat: 'dd/mm/yy'
				});

				$('#btnSave').click(function() {
					$(this).css('display', 'none');
					
					progressBarInit();
					
					$('#frmAnnouncement').attr('action', 'addAnnouncement.html');
					$('#frmAnnouncement').submit();
				});

				$('#btnUpdate').click(function() {
					$(this).css('display', 'none');
					
					progressBarInit();
					
					$('#frmAnnouncement').attr('action', 'editAnnouncement.html');
					$('#frmAnnouncement').submit();
				});

				$('#btnArchive').click(function() {
					$('#frmAnnouncement').attr('action', 'archiveAnnouncement.html');
					$('#frmAnnouncement').submit();
				});

				if(${message.fullStoryLink ne null}) {
					$('#lblfullStoryLink').css('padding-top', '4px');
				}

			});
    </script>
	</head>
	<body style="margin-top:-30px;">
    <form id="frmAnnouncement" action="addAnnouncement.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <c:if test="${message ne null}">
      	<input type="hidden" id="id" name="id" value="${message.ID}">
      </c:if>
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center">
              <tr>
                <td width="100%" align="left" valign="top">
                  <img src="images/announcements_title.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="center" valign="middle" style="background: url('images/body_bkg.gif') top left repeat-y;">
                
                  <table width="75%" cellpadding="0" cellspacing="3" align="center">
                    <tr>
                      <td colspan="2" align="left">
                        <h2>
                        	${message ne null ? 'Edit Message' : 'Add Message'}
                        </h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Message Type:</span></td>
                      <td width="*" align="left" valign="middle">
                        <select name="msg_type" class="requiredinput">
                          <option value="-1">SELECT MESSAGE TYPE</option>
                          <c:forEach items='<%= AnnouncementTypeConstant.ALL %>' var='type'>
                          	<option value='${type.typeID}' ${message.type eq type ? 'SELECTED' :''}>${type.description}</option>
                          </c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                    	<td width="125" align="left" valign="middle"><span class="optionalstar">*</span><span class="label">Location:</span></td>
                      <td width="*" align="left" valign="middle">
                        <select name="school_id" class="optionalinput">
                          <option value="-1">SELECT LOCATION</option>
                          <c:forEach items='${SCHOOLS}' var='school'>
                          	<option value='${school.schoolID}' ${message.school eq school ? 'SELECTED' :''}>${school.schoolName}</option>
                          </c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Date:</span></td>
                      <td width="*" align="left" valign="middle">
                        <table width="100" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="51"><input class="requiredinput_date" type="text" id='msg_date' name="msg_date" style="width:51px;" value='${message.formattedDate}'></td>
                              <td width="*" align="left">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';" /><br>
                              </td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Header:</span></td>
                      <td width="*" align="left" valign="middle">
                        <input type="text" 
                        			 class="requiredinput" 
                        			 id="msg_header" 
                        			 name="msg_header" 
                        			 style="width:325px;text-transform:none;"
                        			 value="${message.header}" />
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="top"><span class="requiredstar">*</span><span class="label">Body:</span></td>
                      <td width="*" align="left" valign="middle">
                        <textarea class="requiredinput" 
                        					id="msg_body" 
                        					name="msg_body" 
                        					style="width:325px; height:150px;text-transform:none;">${message.body}</textarea>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="top"><span class="optionalstar">*</span><span class="label">Image:</span></td>
                      <td valign="top" align="left" width="*">
                      	<c:if test='${message.image ne null}'>
                      		<img src='https://www.nlesd.ca/announcements/img/${message.image}' style='width:325px;padding-bottom:5px;' /><br/>
                      		<table cellspacing='0' cellpadding='0' border='0' style='padding-bottom:5px;'>
                      			<tr>
                      				<td><span class="label" style='text-decoration:none;'>Delete Image?</span></td>
                      				<td valign='middle'><input type='checkbox' name='delete-image' class="optionalinput" /></td>
                      			</tr>
                      		</table>
                      	</c:if>
                      	<input type="file" size="60" name="msg_img" class="optionalinput" />
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="top"><span class="optionalstar">*</span><span class="label">Image Caption:</span></td>
                      <td width="*" align="left" valign="middle">
                        <textarea class="optionalinput" 
                        				  id="msg_img_caption" 
                        				  name="msg_img_caption" 
                        				  style="width:325px; height:75px;text-transform:none;">${message.imageCaption}</textarea>
                      </td>
                    </tr>
                    <tr>
                      <td id='lblfullStoryLink' width="125" align="left" valign="top"><span class="optionalstar">*</span><span class="label">Full Story (PDF):</span></td>
                      <td valign="middle" align="left" width="*">
                      	<c:if test='${message.fullStoryLink ne null}'>
                      		<table cellspacing='0' cellpadding='0' border='0' style='padding-bottom:3px;'>
                      			<tr>
                      				<td valign="top">
                      					<a target='_blank' href='https://www.nlesd.ca/announcements/doc/${message.fullStoryLink}' />${message.fullStoryLink}</a>
                      					<span class="label" style='text-decoration:none;'>Delete?</span>
                      				</td>
                      				<td valign='middle'><input type='checkbox' name='delete-pdf' class="optionalinput" /></td>
                      			</tr>
                      		</table>
                      	</c:if>
                      	<input type="file" size="60" name="msg_full_story" class="optionalinput" />
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="optionalstar">*</span><span class="label">Front Page?:</span></td>
                      <td width="*" align="left" valign="middle">
                        <input type="checkbox" 
                        			 class="optionalinput" 
                        			 id="msg_front_page" 
                        			 name="msg_front_page" ${message.showingOnFrontPage ? 'CHECKED':''} />
                      </td>
                    </tr>
                    <c:if test='${message ne null}'>
                    	<tr>
	                      <td width="125" align="left" valign="middle"><span class="optionalstar">*</span><span class="label">Archived?:</span></td>
	                      <td width="*" align="left" valign="middle">
	                        <input type="checkbox" 
	                        			 class="optionalinput" 
	                        			 id="msg_archived" 
	                        			 name="msg_archived" ${message.archived ? 'CHECKED':''} />
	                      </td>
	                    </tr>
                    </c:if>
                    
                    <c:if test="${requestScope.msg ne null}">
                    	<tr>
                        <td colspan="2" class="message_info"><br>*** ${requestScope.msg} ***</td>
                    	</tr>
                    </c:if>
                   
                    <tr>
                      <td colspan="2" align="center" valign="middle">
                        <script language="javascript" src="js/timerbar.js"></script>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" style='height:25px;'>
                      	<c:choose>
                        	<c:when test='${message eq null}'>
                        		<img id='btnSave' src="images/btn_save_01.gif"
                             		 onmouseover="src='images/btn_save_02.gif';"
                             		 onmouseout="src='images/btn_save_01.gif';" /><br><br>
                        	</c:when>
                        	<c:otherwise>
                        		<img id='btnUpdate' src="images/btn_update_01.gif"
                             		 onmouseover="src='images/btn_update_02.gif';"
                             		 onmouseout="src='images/btn_update_01.gif';" /><br><br>
                        	</c:otherwise>
                        </c:choose>                        
                      </td>
                    </tr>
                  </table>
                  
                  <table width="75%" cellpadding="0" cellspacing="0" align="center">
                    <tr>
                      <td colspan="2" align="left">
                       <h2>Current 
                         <select name="view_type" class="requiredinput" onchange="document.location.href='viewAnnouncements.html?type='+forms[0].view_type.options[forms[0].view_type.selectedIndex].value;">
                            <option value="-1">SELECT MESSAGE TYPE</option>
                            <c:forEach items='<%= AnnouncementTypeConstant.ALL %>' var='type'>
                            	<option value='${type.typeID}' ${type.typeID eq VIEW_TYPE ? 'SELECTED' : ''}>${type.description}</option>
                            </c:forEach>
                          </select>
                        </h2>
                      </td>
                    </tr>
                    <c:if test="${requestScope.edit_msg ne null}">
                    	<tr>
                        <td colspan="2" class="message_info"><br>*** ${requestScope.edit_msg} ***</td>
                    	</tr>
                    </c:if>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="3" cellspacing="0" align="center" border="0">
                          <tr>
                            <td width="10%" align="left" class="label">Date</td>
                            <td width="25%" align="left" class="label">Header</td>
                            <td width="10%" align="left" class="label">Has Image?</td>
                            <td width="10%" align="left" class="label">Full Story Link?</td>
                            <td width="10%" align="left" class="label">Front Page?</td>
                            <td width="*" align="center">
                            	<img id='btnArchive' src="images/btn_archive_01.gif"
                                   onmouseover="src='images/btn_archive_02.gif';"
                                   onmouseout="src='images/btn_archive_01.gif';"/>
                            </td>
                          </tr>
                          <c:choose>
                          	<c:when test="${fn:length(MESSAGES) le 0}">
                              <tr><td colspan="6" class='content' align="left" style="color:#FF0000;font-weight:bold;">No messages available.</td></tr>
                            </c:when>
                          	<c:otherwise>
                              <% int r_cnt = 0; %>
                              <c:forEach items='${MESSAGES}' var='message'>
                                <tr id="row-view-${message.ID}" style="display:inline;padding-bottom:4px;" class="view_row_<%=(++r_cnt)%2%>">
                                  <td width="20%" align="left" class="content">
                                  	<table cellspacing='0' cellpadding='0' border='0'>
                                  		<tr>
                                  			<td valign='middle'><input type='checkbox' id='archive-id' name='archive-id' value="${message.ID}" /></td>
                                  			<td valign='middle'>${message.longFormattedDate}</td>
                                  		</tr>
                                  	</table>
                                  </td>
                                  <td width="40%" align="left" class="content" style='text-transform:none;'>${message.header}</td>
                                  <td width="10%" align="left" class="content" >${message.image ne null ? 'YES' : 'N0'}</td>
                                  <td width="10%" align="left" class="content" >${message.fullStoryLink ne null ? 'YES' : 'NO'}</td>
                                  <td width="10%" align="left" class="content" >${message.showingOnFrontPage ? 'YES' : 'NO'}</td>
                                  <td width="*" align="right" valign="bottom" class="content" rowspan="2" style="border-bottom:solid 2px #C0C0C0;">
                                    <table width="100%" cellspacing="0" cellpadding="0">
                                    	<c:if test='${message.showingOnFrontPage}'>
                                        <tr>
                                          <td>
                                            <img src="images/btn_frontpage_01.gif"
                                                 onmouseover="src='images/btn_frontpage_02.gif';"
                                                 onmouseout="src='images/btn_frontpage_01.gif';"
                                                 onclick="document.location.href='removeFrontPageAnnouncement.html?id=${message.ID}&type='+forms[0].view_type.options[forms[0].view_type.selectedIndex].value;" />&nbsp;
                                          </td>
                                        </tr>
                                      </c:if>
                                      <tr>
                                        <td>
                                          <img src="images/btn_edit_01.gif"
                                               onmouseover="src='images/btn_edit_02.gif';"
                                               onmouseout="src='images/btn_edit_01.gif';"
                                               onclick="document.location.href='editAnnouncement.html?id=${message.ID}&type='+forms[0].view_type.options[forms[0].view_type.selectedIndex].value;" />&nbsp;
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <img src="images/btn_delete_01.gif"
                                               onmouseover="src='images/btn_delete_02.gif';"
                                               onmouseout="src='images/btn_delete_01.gif';"
                                               onclick="document.location.href='deleteAnnouncement.html?id=${message.ID}&type='+forms[0].view_type.options[forms[0].view_type.selectedIndex].value;" />&nbsp;
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr class="view_row_<%=(r_cnt)%2%>" style="padding-top:5px;">
                                  <td width="20%" align="left" class="content" style="border-bottom:solid 2px #C0C0C0;">&nbsp;</td>
                                  <td align="left" class="content" colspan="4" valign="top" style="border-top:solid 1px #000000;border-bottom:solid 2px #C0C0C0;text-transform:none;"><span style="font-weight:bold;">BODY:<br></span>${message.body}</td>
                                </tr>
                            	</c:forEach>
                            </c:otherwise>
													</c:choose>
                        </table>
                      </td>
                    </tr>
                  </table>
                  
                </td>
              </tr>
              <tr>
                <td width="100%" height="104" align="center" valign="middle" style="background: url(images/footer_bkg.gif) top left;">
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