<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  User usr = null;
  MaintenanceRequest req = null;
  Personnel requested_by = null;
  Personnel p = null;
  Vendor vendor = null;
  School school = null;
  Iterator iter = null;
  RequestComment comment = null;
  RequestType req_type = null;
  SimpleDateFormat sdf = null;
  HashMap ass_per = null;
  HashMap ass_ven = null;
  RequestAssignment req_ass = null;
  int max_school_priority;
%>
<%
  usr = (User) session.getAttribute("usr");
  
    req = (MaintenanceRequest) request.getAttribute("MAINT_REQUEST");
    
    if(req != null)
    {
      requested_by = req.getRequestedBy();
      school = req.getSchool();
    }    
    
    ass_per = (HashMap) request.getAttribute("ASSIGNED_PERSONNEL");
    ass_ven = (HashMap) request.getAttribute("ASSIGNED_VENDORS");
    max_school_priority = ((Integer) request.getAttribute("MAX_PRIORITY")).intValue();
    sdf = new SimpleDateFormat("E, MMMM dd yyyy");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript" src="../js/common.js"></script>
    <script type="text/javascript">
      function cancel_add()
      {
        var frm = document.forms[0];
        
        frm.request_type.selectedIndex = -1;
        frm.rname_num.value = "";
        frm.request_priority.selectedIndex = -1;
        frm.request_desc.value = "";
      }
      
      function change_school_priority()
      {
        var frm = document.forms[0];
        
        frm.action = 'changeSchoolPriority.html?admin=true&req=<%=req.getRequestID()%>';
        frm.submit();
      }
      
      function change_request_type()
      {
        var frm = document.forms[0];
        
        frm.action = 'changeRequestType.html?admin=true&req=<%=req.getRequestID()%>';
        frm.submit();
      }
    </script>
	</head>
	<body style="margin:0px;" onload="loaded(); if(top != self){resizeIFrame('maincontent_frame', 0);}">
		
		<esd:SecurityCheck permissions="MAINTENANCE-SCHOOL-VIEW" />
		
		<div id='preloaded' style='display:inline;'>
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
	        <tr><td align="center" style="padding-bottom:5px;"><img src="images/request_details_title.gif"><br></td></tr>
	        <tr><td align="center" style="padding-bottom:5px;color:#E76B10;font-weight:bold;" >LOADING REQUEST DETAILS...</td></tr>
	    </table>
		</div>
		<div id='loaded' style='display:none;'>
    <form id="details_form" name="details_form" action="viewSchoolRequestDetails.html" method="post">
      <input type="hidden" id="req" name="req" value="<%=req.getRequestID()%>">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="100" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/request_details_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent">
            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
              <tr>
                <td colspan="2" align="center" class="header">
                  <%=(new DecimalFormat("JOB-000000")).format(req.getRequestID())%>
                </td>
              </tr>
              <tr>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="44%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">Requested By:</span>
                      </td>
                      <td width="*" class="content">
                        <%=requested_by.getFullNameReverse()%>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="30%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">School:</span>
                      </td>
                      <td width="*" class="content" align="left">
                        <%=school.getSchoolName()%>(<%=school.getSchoolDeptID()%>)
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              
              <tr>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="44%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">Requested Date:</span>
                      </td>
                      <td width="*" class="content">
                        <%=(new SimpleDateFormat("dd/MM/yyyy")).format(req.getRequestedDate())%>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="42%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">Request Type:</span>
                      </td>
                      <td width="*" class="content" align="left">
                        <%if(req.getCurentStatus().getStatusCodeID().equals("UNASSIGNED")){%>
                          <select id="req_type" name="req_type" class="requiredinput" style="width:154px;" onchange="change_request_type();">
                            <option value="-1">Select Request TYPE</option>
                            <%RequestType[] rtypes = (RequestType[])request.getAttribute("REQUEST_TYPES");
                              for(int i=0; i < rtypes.length; i++){
                            %>  <option value="<%=rtypes[i].getRequestTypeID()%>" <%=(req.getRequestType().equals(rtypes[i]))?"SELECTED":""%>><%=rtypes[i].getRequestTypeID()%></option>
                            <%}%>
                        <%}else{%>
                          <%=req.getRequestType().getRequestTypeID()%>
                        <%}%>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              
              <tr>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="47%">
                        <span class="requiredstar">*</span><span class="label">School Priority:</span>
                      </td>
                      <td width="*" class="content">
                        <select id="priority" name="priority" class="requiredinput" style="width:50px;" onchange="change_school_priority();">
                          
                          <%for(int i=1; i <= max_school_priority; i++){%>
                            <option value="<%=i%>" <%=(req.getSchoolPriority() == i)?"SELECTED":""%>><%=i%></option>
                          <%}%>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="37%">
                        <span class="requiredstar">*</span><span class="label">Status:</span>
                      </td>
                      <td width="*" class="content" align="left">
                        <%=req.getCurentStatus().getStatusCodeID()%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="27%">
                        <span class="requiredstar">*</span><span class="label">Room Name/Number:</span>
                      </td>
                      <td width="*" align="left" class="content">
                        <%=req.getRoomNameNumber()%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="35%" valign="top">
                        <span class="requiredstar">*</span><span class="label">Request Description:</span>
                      </td>
                      <td width="*" class="content" align="left">
                        <%=req.geProblemDescription()%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <% 
                RequestComment[] comments = (RequestComment[])request.getAttribute("REQUEST_COMMENTS");
                if(comments.length > 0){
              %>
                  <tr>
                    <td width="100%" align="left" colspan="2">
                      <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                        <tr>
                          <td COLSPAN="2">
                            <span class="requiredstar">*</span><span class="label">Comments:</span>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              <%  for(int i=0; i < comments.length; i++){%>
                  	<tr>
                      <td width="100%" align="left" colspan="2" class="small" style="padding-left:15px;">
                        Entered On <%=sdf.format(comments[i].getMadeOn())%> by <%=comments[i].getMadeBy().getFullNameReverse()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="left" colspan="2" class="content" style="padding-left:15px;">
                        <%=comments[i].getComment()%>
                      </td>
                    </tr>
                    <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="5"></td></tr>
              <%  }
                }%>
              
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr><td colspan="2"><span class="requiredstar">*</span><span class="label">Current Assignement:</span></td></tr>
                    <%if((ass_per.size() < 1)&&(ass_ven.size() < 1)){%>
                      <tr><td colspan="2" class="content" style="padding-left:15px;">No one currently assigned.</td></tr>
                    <%}else{%>
                      <tr>
                        <td width="50%" class="label_no_underline" style="border-right:solid 1px #072B61;padding-left:25px;">Maintentance Personnel</td>
                        <td width="50%" class="label_no_underline" style="padding-left:10px;">Outside Vendors</td>
                      </tr>
                      <tr>
                        <td width="50%" class="content" style="padding-left:25px;" valign="top">
                          <%Map.Entry[] ap = (Map.Entry[]) ass_per.entrySet().toArray(new Map.Entry[0]);
                          	if(ap.length < 1){%>
                            	None assigned.
                          <%}else{%>
                            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                              <%
                                for(int i=0; i < ap.length; i++)
                                {
                                  req_ass = (RequestAssignment)ap[i].getValue();
                              %>  <tr>
                                    <td width="60%" class="content"><%=req_ass%></td>
                                  </tr>
                              <%}%>
                            </table>
                          <%}%>
                        </td>
                        <td width="50%" class="content" style="padding-left:10px;" valign="top">
                          <% Map.Entry[] vp = (Map.Entry[]) ass_ven.entrySet().toArray(new Map.Entry[0]);
                          	if(vp.length < 1){%>
                            	None assigned.
                          <%}else{%>
                            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                              <%
                                for(int i=0; i < vp.length; i++)
                                {
                                  req_ass = (RequestAssignment)vp[i].getValue();
                              %>  <tr>
                                    <td width="60%" class="content"><%=req_ass%></td>
                                  </tr>
                              <%}%>
                            </table>
                          <%}%>
                        </td>
                      </tr>
                    <%}%>
                  </table>
                </td>
              </tr>
              
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="100%">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="137" align="left">
                              <span class="requiredstar">*</span><span class="label">Request Category:</span>
                            </td>
                            <td width="*" align="left" class="content">
                                <%=(req.getRequestCategory()!=null)?req.getRequestCategory().getRequestCategoryID():"NOT ASSIGNED"%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              
              <tr><td colspan="2" align="right" valign="top"><a href="" onclick="openWindow('ADD_REQUEST_COMMENT', 'addRequestComment.html?req=<%=req.getRequestID()%>',600, 300, 0);return false;" class="small_blue">ADD COMMENT</a> | <a href="" onclick="openWindow('PRINT_REQUEST_DETAILS', 'printRequestDetails.html?req=<%=req.getRequestID()%>',600, 600, 0);return false;" class="small_blue"><img src="images/printer.gif" style="cursor:hand;" border="0"></a></td></tr>
              <tr><td colspan="2" class="footer" height="1"><img src="images/spacer.gif" width="1" height="1"></td></tr>
              <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="10"></td></tr>
              
            </table>
          </td>
        </tr>
      </table> 
    </form>
    </div>
	</body>
</html>