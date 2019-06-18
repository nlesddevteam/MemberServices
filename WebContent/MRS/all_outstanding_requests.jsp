<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.awsd.school.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  User usr = null;
  MaintenanceRequest[] reqs = null;
  DecimalFormat df = null;
  SimpleDateFormat sdf = null;
  int num_pages = 0;
  int cur_page = 1;
  int i = 0;
  String color_on;
  String color_off;
  School school = null;

  usr = (User) session.getAttribute("usr");
  num_pages = ((ArrayList)session.getAttribute("OUTSTANDING_REQUESTS")).size();
  cur_page = Integer.parseInt(request.getParameter("page"));
  
  df = new DecimalFormat("JOB-#000000");
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  color_off = "#FFFFFF";
  color_on = "#D8E7FC";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
     <script language="JavaScript" src="../js/common.js"></script>
	</head>
	<body style="margin:0px;" onload="loaded();if(top != self){resizeIFrame2('maincontent_frame', 0, 0);}">
	
		<esd:SecurityCheck permissions="MAINTENANCE-ADMIN-VIEW" />
		
		<div id='preloaded' style='display:inline;'>
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
	        <tr><td align="center" style="padding-bottom:5px;"><img src="images/outstanding_school_requests_title.gif"><br></td></tr>
	        <tr><td align="center" style="padding-bottom:5px;color:#E76B10;font-weight:bold;" >LOADING REQUESTS...</td></tr>
	    </table>
		</div>
		<div id='loaded' style='display:none;'>
    <form id="add_request_form" name="add_request_form" action="searchJob.html" method="post">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/outstanding_school_requests_title.gif"><br></td></tr>
        
        <tr>
          <td id="maincontent" align="center" width="100%">
            <table width="95%" cellpadding="0" cellspacing="3" align="center" valign="top">
              <tr>
                <td align="left" class="content" style="padding-bottom:5px;" colspan="4" valign="middle">
                  <table width="100%" cellpadding="0" cellspacing="3" align="center" valign="top">
                    <tr>
                      <td align="left" class="content" style="border-right:solid 1px #E0E0E0;" width="185" valign="middle">
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td class="label" width="65">Find Job#:</td>
                            <td width="75"><input type="text" id="req" name="req" value="" style="WIDTH:75px;" class="requiredinput"></td>
                            <td width="30">
                              <a href="" onclick="document.forms[0].submit(); return false;" class="small_blue">
                                <img src="images/search_off.gif" 
                                     style="cursor:hand;padding-left:4px;" border="0"
                                     onmouseover="src='images/search_on.gif';"
                                     onmouseout="src='images/search_off.gif';"><br>
                              </a>
                            </td>
                            <td width="*">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                      <td align="left" width="85">
                        <a href="" onclick="openWindow('PRINT_REPORT', 'print_report.jsp',600, 300, 0); return false;" class="small_blue">
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="100%" align="center"><img src="images/printer.gif" style="cursor:hand;" border="0"><br>Detailed Report</td>
                            </tr>
                            
                          </table>
                        </a>
                      </td>
                      <td align="left" width="85">
                        <a href="" onclick="openWindow('PRINT_REPORT', 'print_summary_report.jsp',600, 300, 0); return false;" class="small_blue">
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="100%" align="center"><img src="images/printer.gif" style="cursor:hand;" border="0"><br>Summary Report</td>
                            </tr>
                            
                          </table>
                        </a>
                      </td>
                      <td width="*">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr><td colspan="4" style="border-top:solid 2px #072B61; padding:0;" ><img src="mages/spacer.gif" width="1" height="1"></td></tr>
              <tr><td colspan="4" style="border-top:solid 1px #c0c0c0;border-bottom:solid 2px #072B61;background-color:#E0E0E0;" class="label_no_underline" align="center">Pages: <%= num_pages %></td></tr>
              <tr>
                <td colspan="4" align="center" valign="top">
                  <%
	                  int start_page = cur_page - 7;
	                	if(start_page < 1)
	                		start_page = 1;
	                	
	                	int end_page = cur_page + 7;
	                	if(end_page > num_pages)
	                		end_page = num_pages;
	                	
	                	if(start_page > 1)
	                		out.println("<a href='all_outstanding_requests.jsp?page=1' class='small_pagenum'>&lt;&lt;</a>&nbsp;");
	                	
                  	if(cur_page > 1)
                  		out.println("<a href='all_outstanding_requests.jsp?page=" + (cur_page-1) + "' class='small_pagenum'>&lt;</a>&nbsp;");
                  
                  	for(int j=start_page; j <= end_page; j++){
                      if(cur_page != j)
                      	out.println("<a href='all_outstanding_requests.jsp?page=" + j + "' class='small_pagenum'>" + j + "</a>&nbsp;");
                  		else
                  			out.println("<span class='small_curpage'>" + j + "</span>&nbsp");
                  	}
                  	
                  	if(cur_page < num_pages)
                  		out.println("<a href='all_outstanding_requests.jsp?page=" + (cur_page+1) + "' class='small_pagenum'>&gt;</a>&nbsp;");
                  	
                  	if(end_page < num_pages)
	                		out.println("<a href='all_outstanding_requests.jsp?page=" + num_pages + "' class='small_pagenum'>&gt;&gt;</a>&nbsp;");
                  %>
                </td>
              </tr>
              <tr><td colspan="4" style="border-top:solid 2px #072B61;background-color:#E0E0E0;" align="left"><img src="mages/spacer.gif" width="1" height="1"></td></tr>
              <tr><td colspan="4" style="border-top:solid 1px #C0C0C0;border-bottom:solid 2px #072B61;background-color:#E0E0E0;" class="label_no_underline" align="center">Current Page: <%=cur_page%></td></tr>
              <tr><td colspan="4"><img src="images/spacer.gif" width="1" height="5"><br></td></tr>
              
              <tr>
                <td class="label" width="50%" align="left">PROBLEM DESCRIPTION</td>
                <td class="label" width="20%" align="center">DATE<br>REQUESTED</td>
                <td class="label" width="20%" align="center">STATUS</td>
                <td width="10%" align="center">&nbsp;</td>
              </tr>
              <%if(num_pages < 1){%>
                <tr>
                  <td class="label_no_underline" colspan="3" align="left">NO REQUESTS CURRENTLY OUTSTANDING.</td>
                </tr>
              <%}else{
                  reqs = ((MaintenanceRequest[])((ArrayList)session.getAttribute("OUTSTANDING_REQUESTS")).get(cur_page - 1));
                  for(int r=0; r < reqs.length; r++){
                    if(!reqs[r].getSchool().equals(school))
                    {
                      if(school != null)
                        out.println("<tr><td colspan='4' style='border-top:1px solid #072B61;' align='left'><img src='images/spacer.gif' width='1' height='5'></td></tr>");
                      school = reqs[r].getSchool();
                      out.println("<tr><td colspan='4' class='school_name' align='left'>"+school.getSchoolName()+"</td></tr>");
                    }
              %>
                    <tr id="item_row_<%=reqs[r].getRequestID()%>" style="cursor:hand;background-color:<%=color_off%>;"
                        onmouseover="toggleTableRowHighlight('item_row_<%=reqs[r].getRequestID()%>', '<%=color_on%>');"
                        onmouseout="toggleTableRowHighlight('item_row_<%=reqs[r].getRequestID()%>', '<%=color_off%>');">
                      <td class="content applyborder" width="50%" align="left" onclick="document.location.href='viewAdminRequestDetails.html?req=<%=reqs[r].getRequestID()%>&page=<%=cur_page%>';">
                        <table width="100%" cellpadding="0" cellspacing="1" align="center" valign="top">
                          <tr>
                            <td width="22" valign="top">
                              <img src="images/view_details_01.gif"
                                onmouseover="src='images/view_details_02.gif';"
                                onmouseout="src='images/view_details_01.gif';"><br>
                            </td>
                            <td width="*"><%=reqs[r].geProblemDescription()%></td>
                          </tr>
                        </table>
                      </td>
                      <td class="content applyborder" width="20%" align="center" valign="top" onclick="document.location.href='viewAdminRequestDetails.html?req=<%=reqs[r].getRequestID()%>&page=<%=cur_page%>';"><%=sdf.format(reqs[r].getRequestedDate())%></td>
                      <td class="content applyborder" width="20%" align="center" valign="top" onclick="document.location.href='viewAdminRequestDetails.html?req=<%=reqs[r].getRequestID()%>&page=<%=cur_page%>';"><%=reqs[r].getCurentStatus().getStatusCodeID()%></td>
                      <td class="content applyborder" width="*" valign="top">
                      	<a class="small" onclick="return confirm('Are you sure you want to DELETE this request?');" href="deleteRequest.html?req=<%=reqs[r].getRequestID()%>&page=<%=cur_page%>">delete<br>request</a>
                      </td>
                    </tr>
                  <%}%>
              <%}%>
            </table>
          </td>
        </tr>
      </table> 
    </form>
    </div>
	</body>
</html>