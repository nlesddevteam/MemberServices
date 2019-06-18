<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.school.*,
                 com.esdnl.mrs.*,
                 java.util.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  RequestType[] rtype = (RequestType[])request.getAttribute("REQUEST_TYPES");
  School[] s = (School[]) request.getAttribute("SCHOOLS");
  StatusCode[] code = (StatusCode[])request.getAttribute("STATUS_CODES");
  RequestCategory[] cats = (RequestCategory[]) request.getAttribute("REQUEST_CATEGORIES");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script type="text/javascript">
      function toggle_row(row_id)
      {
        var row = document.getElementById(row_id);
        
        if(row)
        {
          if(row.style.display && row.style.display=='inline')
          {
            row.style.display='none';
          }
          else
          {
            row.style.display = 'inline';
          }
        }
      }
      
      function submit_report_request(sel)
      {
        if(sel)
        {
          if((sel.selectedIndex == -1)||(sel.options[sel.selectedIndex].value == "-1"))
            return;
          else
            document.location.href = sel.options[sel.selectedIndex].value;
        }
      }
      
      function school_status_report_check()
      {
        var s = document.getElementById('schoolstatus_school');
        var sc = document.getElementById('schoolstatus_statuscode');
        
        if(s == null)
          alert('school select not found.');
        else if(sc == null)
          alert('status select not found.');
        else if(s.options[s.selectedIndex].value == '-1')
          alert('Please select a SCHOOL.');
        else if(sc.options[sc.selectedIndex].value == '-1')
          alert('Please select a STATUS CODE.' + '[value=' + sc.options[sc.selectedIndex].value + ']' +
            ' [selectedIndex='+ sc.selectedIndex + ']');
        else
        {
          document.location.href = 'viewSchoolStatusCodeReport.html?school=' + s.options[s.selectedIndex].value
            + '&status=' + sc.options[sc.selectedIndex].value;
        }
      }
      
      function school_category_report_check()
      {
        var s = document.getElementById('schoolcategory_school');
        var sc = document.getElementById('schoolcategory_category');
        
        if(s == null)
          alert('school select not found.');
        else if(sc == null)
          alert('status select not found.');
        else if(s.options[s.selectedIndex].value == '-1')
          alert('Please select a SCHOOL.');
        else if(sc.options[sc.selectedIndex].value == '-1')
          alert('Please select a CATEGORY.' + '[value=' + sc.options[sc.selectedIndex].value + ']' +
            ' [selectedIndex='+ sc.selectedIndex + ']');
        else
        {
          document.location.href = 'viewSchoolCategoryReport.html?school=' + s.options[s.selectedIndex].value
            + '&cat=' + sc.options[sc.selectedIndex].value;
        }
      }
    </script>
    <style>
    	select{
    		font-size:9px;
    	}
    </style>
	</head>
	<body style="margin:0px;" onload="if(top != self){resizeIFrame('maincontent_frame', 317);}">
		
		<esd:SecurityCheck permissions="MAINTENANCE-ADMIN-VIEW" />
		
    <form>
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="left" style="padding-bottom:5px;"><img src="images/admin_utility_menu_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent" align="center">
            <table width="80%" height="100%" cellpadding="5" cellspacing="0" align="left" valign="top">
              <tr>
                <td width="100%" align="left" class="header">Action Reports</td>
              </tr>
              <tr>
                <td width="100%" style="padding-left:15px;">
                  <table width="100%" height="100%" cellpadding="5" cellspacing="0" border="0" align="left" valign="top">
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                      
                        <a href="" class="admin_menu_item" onclick="toggle_row('request_type_row'); return false;">Outstanding Requests By Request Type...</a></td>
                    </tr>
                    <tr id="request_type_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                          <span class="label_no_underline">Request Type: </span>
                          <select id="req_type" onchange="submit_report_request(this);">
                            <option value="-1" SELECTED>SELECT REQUEST TYPE</option>
                            <%for(int i=0; i < rtype.length; i++){%>
                                  <option value="viewRequestTypeReport.html?type_id=<%=rtype[i].getRequestTypeID()%>"><%=rtype[i].getRequestTypeID()%></option>
                            <%}%>
                        </select>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                      
                        <a href="" class="admin_menu_item" onclick="toggle_row('request_category_row'); return false;">Outstanding Requests By Request Category...</a></td>
                    </tr>
                    <tr id="request_category_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                          <span class="label_no_underline">Request Category: </span>
                          <select id="req_cat" name="req_cat" onchange="submit_report_request(this);">
                            <option value="-1" SELECTED>SELECT REQUEST CATEGORY</option>
                            <%for(int i=0; i < cats.length; i++){%>
                                  <option value="viewRequestCategoryReport.html?cat_id=<%=cats[i].getRequestCategoryID()%>"><%=cats[i].getRequestCategoryID()%></option>
                            <%}%>
                        </select>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                        <a href="" class="admin_menu_item" onclick="toggle_row('schools_row'); return false;">Outstanding Requests By School...</a></td>
                    </tr>
                    <tr id="schools_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                        <span class="label_no_underline">School: </span>
                          <select id="school" onchange="submit_report_request(this);">
                            <option value="-1" SELECTED>SELECT SCHOOL</option>
                          	<%for(int i=0; i < s.length; i++){%>
                               <option value="viewSchoolReport.html?s_id=<%=s[i].getSchoolID()%>"><%=s[i].getSchoolName()%></option>
                          	<%}%>
                        </select>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                        <a href="" class="admin_menu_item" onclick="toggle_row('statuscodes_row'); return false;">Requests By Status...</a></td>
                    </tr>
                    <tr id="statuscodes_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                        <span class="label_no_underline">Status: </span>
                          <select id="statuscode" onchange="submit_report_request(this);">
                            <option value="-1" SELECTED>SELECT STATUS</option>
                              <%for(int i=0; i < code.length;i++){%>
                                <option value="viewStatusCodeReport.html?code=<%=code[i].getStatusCodeID()%>"><%=code[i].getStatusCodeID()%></option>
                              <%}%>
                        </select>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                        <a href="" class="admin_menu_item" onclick="toggle_row('schoolstatuscodes_row'); return false;">Requests By School &amp; Status...</a></td>
                    </tr>
                    <tr id="schoolstatuscodes_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                        <table cellpadding="0" cellspacing="0" border="0">
                          <tr>
                            <td width="100%" style="padding-left:15px;" align="center">
                              <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                  <td width="75%">
                                    <span class="label_no_underline">School: </span>
                                    <select id="schoolstatus_school">
                                      <option value="-1" SELECTED>SELECT SCHOOL</option>
                                        <%for(int i=0; i < s.length; i++){%>
                                          <option value="<%=s[i].getSchoolID()%>"><%=s[i].getSchoolName()%></option>
                                        <%}%>
                                    </select>
                                  <td width="*">
                                   &nbsp;
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%" style="padding-left:15px;" align="center">
                              <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                  <td width="75%">
                                    <span class="label_no_underline">Status: </span>
                                    <select id="schoolstatus_statuscode">
                                      <option value="-1" SELECTED>SELECT STATUS</option>
                                      <%for(int i=0; i < code.length; i++){%>
                                       	<option value="<%=code[i].getStatusCodeID()%>"><%=code[i].getStatusCodeID()%></option>
                                      <%}%>
                                    </select>
                                  </td>
                                  <td width="*">
                                    <input type="button" value="GO" onclick="school_status_report_check();">
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="middle" align="left"><img src="images/arrow_bullet_orange.gif">
                        <a href="" class="admin_menu_item" onclick="toggle_row('schoolcategory_row'); return false;">Requests By School &amp; Category...</a></td>
                    </tr>
                    <tr id="schoolcategory_row" style="display:none;">
                      <td width="100%" style="padding-left:15px;" align="center">
                        <table cellpadding="0" cellspacing="0" border="0">
                          <tr>
                            <td width="100%" style="padding-left:15px;" align="center">
                              <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                  <td width="75%">
                                    <span class="label_no_underline">School: </span>
                                    <select id="schoolcategory_school">
                                      <option value="-1" SELECTED>SELECT SCHOOL</option>
                                        <%for(int i=0; i < s.length; i++){%>
                                          <option value="<%=s[i].getSchoolID()%>"><%=s[i].getSchoolName()%></option>
                                        <%}%>
                                    </select>
                                  <td width="*">
                                   &nbsp;
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td width="100%" align="center">
                              <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                  <td width="75%">
                                    <span class="label_no_underline">Category: </span>
                                    <select id="schoolcategory_category">
                                      <option value="-1" SELECTED>SELECT CATEGORY</option>
                                      <%for(int i=0; i < cats.length; i++){%>
                                       	<option value="<%=cats[i].getRequestCategoryID()%>"><%=cats[i].getRequestCategoryID()%></option>
                                      <%}%>
                                    </select>
                                  </td>
                                  <td width="*">
                                    <input type="button" value="GO" onclick="school_category_report_check();">
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" class="header">Printable Reports</td>
              </tr>
              <tr>
                <td width="100%" style="padding-left:15px;">
                  <table width="100%" height="100%" cellpadding="5" cellspacing="0" align="left" valign="top">
                    <tr>
                      <td width="26" valign="middle"><img src="images/arrow_bullet_orange.gif"><br></td>
                      <td width="*" align="left">
                        <a href="" class="admin_menu_item" onclick="document.location.href='viewAllOutstandingReport.html'; return false;">All Outstanding Requests</a></td>
                    </tr>
                    <tr>
                      <td width="26" valign="middle"><img src="images/arrow_bullet_orange.gif"><br></td>
                      <td width="*" align="left">
                        <a href="" class="admin_menu_item" onclick="document.location.href='viewCapitalReport.html'; return false;">All Outstanding Capital</a></td>
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