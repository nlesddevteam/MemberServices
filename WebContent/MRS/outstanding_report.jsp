<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,com.awsd.school.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%!
  User usr = null;
  MaintenanceRequest req = null;
  Iterator<MaintenanceRequest> iter = null;
  DecimalFormat df = null;
  DecimalFormat cf = null;
  SimpleDateFormat sdf = null;
  int num_reqs = 0;
  int i = 0;
  String color_on;
  String color_off;
  School school = null;
  int school_id = -1;
  double school_total_cost;
  double total_cost;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
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
  iter = ((Vector<MaintenanceRequest>)session.getAttribute("OUTSTANDING_REQUESTS")).iterator();
    
  df = new DecimalFormat("JOB-#000000");
  cf = new DecimalFormat("$#,##0.00");
  sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  color_off = "#FFFFFF";
  color_on = "#D8E7FC";
  school_id = -1;
  school_total_cost = 0;
  total_cost = 0;
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
	<body style="margin:0px;" onload="if(top != self){resizeIFrame('maincontent_frame', 317);}">
    <form id="add_request_form" name="add_request_form" action="addRequest.html" method="post">
      <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
        <!--<tr><td align="center" style="padding-bottom:5px;"><img src="images/capital_report_title.gif"><br></td></tr>-->
        <tr><td align="center"><h2>Eastern School District <br> Outstanding Maintenance Request Report</h2></td></tr>
        <tr><td align="center" style="padding-bottom:5px;"><h4><%=sdf.format(Calendar.getInstance().getTime())%></h4></td></tr>
        <tr>
          <td id="maincontent" align="center" width="100%">
            <table width="98%" cellpadding="3" cellspacing="0" align="center" valign="top" border="0">
              <!--<tr><td align="right" style="padding-bottom:5px;" colspan="3"><a href="" onclick="openWindow('PRINT_REPORT', 'print_report.jsp',600, 300, 0); return false;" class="small_blue"><img src="images/printer.gif" style="cursor:hand;" border="0"></a></td></tr>-->
              <%if(!iter.hasNext()){%>
                <tr>
                  <td class="label_no_underline" colspan="5" align="left">NO REQUESTS CURRENTLY OUTSTANDING.</td>
                </tr>
              <%}else{
                  while(iter.hasNext()){
                    req = (MaintenanceRequest) iter.next();
                    if(req.getSchoolID() != school_id)
                    {
                      if(school_id != -1){
              %>        <tr>
                          <td class="content" colspan="3" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;" align="left"><b><i>Cost of all required work in this school:</i></b></td>
                          <td width="*" class="content" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;" align="center"><b><u><%=cf.format(school_total_cost)%></u></b></td>
                        </tr>
              <%      }
                      school = req.getSchool();
                      school_id = school.getSchoolID(); 
                      school_total_cost = 0;
              %>      <tr>
                        <td colspan="4" style="padding-top:3px;border-top:solid 3px #E76B10;background-color:#E0E0E0;font-size:20px;font-weight:bold;" align="left" valign="middle"><%=school.getSchoolName()%></td>
                      </tr>
                      <tr>
                        <td class="label_no_underline" width="40%" align="left" style="border-top:solid 1px #000000; border-bottom:solid 1px #000000;">DESCRIPTION</td>
                        <td class="label_no_underline" width="20%" align="center" style="border-top:solid 1px #000000; border-bottom:solid 1px #000000;">REF#</td>
                        <td class="label_no_underline" width="20%" align="center" style="border-top:solid 1px #000000; border-bottom:solid 1px #000000;">TYPE</td>
                        <!--<td class="label_no_underline"  align="center" style="border-top:solid 1px #000000; border-bottom:solid 1px #000000;">STATUS</td>-->
                        <td class="label_no_underline" width="20%" align="center" style="border-top:solid 1px #000000; border-bottom:solid 1px #000000;">EST. COST</td>
                      </tr>
               <%     
                    }
                    school_total_cost += req.getEstimatedCost();
                    total_cost += req.getEstimatedCost();
               %>
                    <tr>
                      <td class="content" width="40%" align="left" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;">
                        <%=req.geProblemDescription()%>
                      </td>
                      <td class="content" width="20%" align="center" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;"><%=df.format(req.getRequestID())%></td>
                      <td class="content" width="20%" align="center" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;"><%=req.getRequestType().getRequestTypeID()%></td>
                      <!--<td class="content"  align="center" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;"><%=req.getCurentStatus().getStatusCodeID()%></td>-->
                      <td class="content" width="20%" align="center" style="padding-bottom:5px;border-bottom:solid 1px #e0e0e0;"><%=cf.format(req.getEstimatedCost())%></td>
                    </tr>
                  <%}%>
                  <tr>
                    <td class="content" colspan="3" style="padding-bottom:5px;" align="left"><b><i>Cost of all required work in this school:</i></b></td>
                    <td width="*" class="content" style="padding-bottom:5px;" align="center"><b><u><%=cf.format(school_total_cost)%></u></b></td>
                  </tr>
                  
                  <tr>
                    <td class="content" colspan="3" style="background-color:#E0E0E0; border-top:solid 1px #000000; border-bottom:solid 1px #000000;" align="left"><b><i>Overall Cost:</i></b></td>
                    <td width="*" class="content" style="background-color:#E0E0E0; border-top:solid 1px #000000; border-bottom:solid 1px #000000;" align="center"><b><u><%=cf.format(total_cost)%></u></b></td>
                  </tr>
              <%}%>
            </table>
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>