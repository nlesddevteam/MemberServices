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
  School school = null;
  SimpleDateFormat sdf = null;
  HashMap ass_per = null;
  HashMap ass_ven = null;
  RequestAssignment req_ass = null;
  int max_school_priority;

  usr = (User) session.getAttribute("usr");
  
    req = (MaintenanceRequest) request.getAttribute("MAINT_REQUEST");
    
    if(req != null)
    {
      requested_by = req.getRequestedBy();
      school = req.getSchool();
      max_school_priority = ((Integer) request.getAttribute("MAX_PRIORITY")).intValue();
    } 
    else
    {
      max_school_priority = 1;
    }
    
    ass_per = (HashMap) request.getAttribute("ASSIGNED_PERSONNEL");
    ass_ven = (HashMap) request.getAttribute("ASSIGNED_VENDORS");
    
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
      
      function toggle_assign_personnel_row()
      {
        var row = document.getElementById("assign_person_row");
        var sel_stat = document.getElementById("new_status");
        
        if(row)
        {          
          if(sel_stat.selectedIndex > -1)
          {
            if(sel_stat.options[sel_stat.selectedIndex].value == 'ASSIGNED')
            {
              row.style.display = 'inline';
              resizeIFrame('maincontent_frame', 317);
            }
            else
            {
              row.style.display = 'none';
            }
          }
        }
      }
      
      function toggle_capital_type_row()
      {
        var row = document.getElementById("capital_type_row");
        var sel_stat = document.getElementById("cat_id");
        
        if(row)
        {          
          if(sel_stat.selectedIndex > -1)
          {
            if(sel_stat.options[sel_stat.selectedIndex].value == 'CAPITAL')
            {
              row.style.display = 'inline';
              resizeIFrame('maincontent_frame', 317);
            }
            else
            {
              row.style.display = 'none';
            }
          }
        }
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
	<body style="margin:0px;" onload="loaded();if(top != self){resizeIFrame('maincontent_frame', 0);}">
		
		<esd:SecurityCheck permissions="MAINTENANCE-ADMIN-VIEW" />
		
		<div id='preloaded' style='display:inline;'>
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
	        <tr><td align="center" style="padding-bottom:5px;"><img src="images/request_details_title.gif"><br></td></tr>
	        <tr><td align="center" style="padding-bottom:5px;color:#E76B10;font-weight:bold;" >LOADING REQUEST DETAILS...</td></tr>
	    </table>
		</div>
		<div id='loaded' style='display:none;'>
    <form id="details_form" name="details_form" action="viewAdminRequestDetails.html" method="post">
      <input type="hidden" id="req" name="req" value="<%=req.getRequestID()%>">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="100" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/request_details_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent">
            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
              <%if((request.getAttribute("JOB_FOUND") != null) && !Boolean.valueOf((String)request.getAttribute("JOB_FOUND")).booleanValue()){%>
                <tr>
                  <td colspan="2" align="center" class="message_info">
                    Job not found in database.
                  </td>
                </tr>
              <%}else{%>
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
                        <%=school.getSchoolName()%>
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
                  <table width="100%" cellpadding="1" corder="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="42%" valign="middle">
                        <span class="requiredstar">*</span><span class="label">Request Type:</span>
                      </td>
                      <td width="*" class="content" align="left">
                        <select id="req_type" name="req_type" class="requiredinput" style="width:154px;" onchange="change_request_type();">
                          <option value="-1">Select Request TYPE</option>
                          <%RequestType[] req_types = (RequestType[])request.getAttribute("REQUEST_TYPES");
                            for(int i=0; i < req_types.length; i++){
                          %>  <option value="<%=req_types[i].getRequestTypeID()%>" <%=(req.getRequestType().getRequestTypeID().equals(req_types[i].getRequestTypeID()))?"SELECTED":""%>><%=req_types[i].getRequestTypeID()%></option>
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
                <td colspan="2" align="right">
                  <a href="" onclick="openWindow('ADD_REQUEST_COMMENT', 'addRequestComment.html?req=<%=req.getRequestID()%>',600, 300, 0);return false;" class="small_blue">ADD COMMENT</a> | 
                  <a href="" onclick="openWindow('PRINT_REQUEST_DETAILS', 'printRequestDetails.html?req=<%=req.getRequestID()%>',600, 600, 0);return false;" class="small_blue">PRINT</a> | 
                  <a href="" onclick="document.location.href='all_outstanding_requests.jsp?page=<%=(request.getParameter("page")!= null)?request.getParameter("page"):"1"%>';return false;" class="small_blue">Back to report</a>
                </td>
              </tr>
              <tr><td colspan="2" class="footer" height="1"><img src="images/spacer.gif" width="1" height="1"></td></tr>
              <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="10"></td></tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="137">
                        <span class="requiredstar">*</span><span class="label">New Status:</span>
                      </td>
                      <td width="*" class="content">
                        <select id="new_status" name="new_status" class="requiredinput" style="width:154px;" onchange="toggle_assign_personnel_row();">
                          <option value="-1">Select STATUS CODE</option>
                          <%StatusCode[] codes = (StatusCode[])request.getAttribute("STATUS_CODES");
                            for(int i=0; i < codes.length; i++){%>
                            	<option value="<%=codes[i].getStatusCodeID()%>" <%=(req.getCurentStatus().getStatusCodeID().equals(codes[i].getStatusCodeID()))?"SELECTED":""%>><%=codes[i].getStatusCodeID()%></option>
                          <%}%>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
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
                          <%Map.Entry[] ap = (Map.Entry[])(ass_per.entrySet()).toArray(new Map.Entry[0]);
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
                                    <td width="*" align="right" style="border-right:solid 1px #072B61;padding-right:10px;">
                                      <%if(!req.getCurentStatus().getStatusCodeID().equalsIgnoreCase("COMPLETED")){%>
                                        <a href="unassignMaintenancePersonenl.html?req=<%=req.getRequestID()%>&pid=<%=req_ass.getId()%>" class="small">UNASSIGN</a>
                                      <%}else{%>
                                        &nbsp;
                                      <%}%>
                                    </td>
                                  </tr>
                              <%}%>
                            </table>
                          <%}%>
                        </td>
                        <td width="50%" class="content" style="padding-left:10px;" valign="top">
                          <%Map.Entry[] vp = (Map.Entry[]) ass_ven.entrySet().toArray(new Map.Entry[0]);
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
                                    <td width="*" align="right"><a href="unassignVendor.html?req=<%=req.getRequestID()%>&pid=<%=req_ass.getId()%>" class="small_blue">UNASSIGN</a></td>
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
              <tr id="assign_person_row" style="display:none;">
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" valign="top">
                        <span class="requiredstar">*</span><span class="label">Assigned To:</span>
                      </td>
                    </tr> 
                    <tr>
                      <td colspan="2" class="content" align="bottom" style="padding-left:30px;">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="45%" valign="middle">
                              <span class="label_no_underline">Maintenance:</span>
                            </td>
                            <td class="content" width="10%">&nbsp;</td>
                            <td width="45%">
                              <span class="label_no_underline">Vendor:</span>
                            </td>
                          </tr>
                          <tr>
                            <td class="content" width="45%">
                               <select id="maint_per_id" name="maint_per_id" class="requiredinput" style="width:190px;height:90px;" MULTIPLE>
                                  <option value="-1">Select MAINTENACNE PERSON</option>
                                  <%for(int i=0; i < PersonnelType.ALL.length; i++){%>
                                    <option value="<%=PersonnelType.ALL[i].getValue()%>"><%=PersonnelType.ALL[i].getDescription()%></option>
                                  <%}
                                  
                                    Personnel[] p = (Personnel[])request.getAttribute("MAINT_PERSONNEL");
                                    for(int i=0; i < p.length; i++){
                                      if((ass_per != null) && !ass_per.containsKey(new Integer(p[i].getPersonnelID()))){
                                    %>  <option value="<%=p[i].getPersonnelID()%>"><%=p[i].getFullNameReverse()%></option>
                                  <%}}%>
                               </select>
                            </td>
                            <td width="10%" align="center" class="content">AND/OR</td>
                            <td width="45%" class="content">
                               <select id="vendor_id" name="vendor_id" class="requiredinput" style="width:175px;height:90px;" MULTIPLE>
                                  <option value="-1">Select VENDOR</option>
                                  <%Vendor[] v = (Vendor[]) request.getAttribute("VENDORS");
                                    for(int i=0; i < v.length; i++){
                                      if((ass_ven != null) && !ass_ven.containsKey(new Integer(v[i].getVendorID()))){
                                  %>  <option value="<%=v[i].getVendorID()%>"><%=v[i].getVendorName()%></option>
                                   <%}}%>
                               </select>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
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
                            <td width="*" align="left">
                              <select id="cat_id" name="cat_id" class="requiredinput" style="width:175px;" onchange="toggle_capital_type_row();">
                                  <option value="-1">Select CATEGORY TYPE</option>
                                  <%RequestCategory[] cats = (RequestCategory[])request.getAttribute("CATEGORIES");
                                    for(int i=0; i < cats.length; i++){
                                  %>  <option value="<%=cats[i].getRequestCategoryID()%>" <%=((req.getRequestCategory()!=null)&&(req.getRequestCategory().getRequestCategoryID().equals(cats[i].getRequestCategoryID())))?"SELECTED":""%>><%=cats[i].getRequestCategoryID()%></option>
                                  <%}%>
                               </select>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              
              <tr id="capital_type_row" style="display: <%=((req.getRequestCategory()!= null) && req.getRequestCategory().getRequestCategoryID().equals("CAPITAL"))?"inline":"none"%>;">
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="60%">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="137" align="left">
                              <span class="requiredstar">*</span><span class="label">Capital Type:</span>
                            </td>
                            <td width="*" align="left">
                              <select id="cap_type_id" name="cap_type_id" class="requiredinput" style="width:175px;">
                                  <option value="-1">Select CAPITAL TYPE</option>
                                  <%CapitalType[] ctypes = (CapitalType[])request.getAttribute("CAPITAL_TYPES");
                                    for(int i=0; i < ctypes.length; i++){
                                  %>  <option value="<%=ctypes[i].getCapitalTypeID()%>" <%=((req.getCapitalType()!=null)&&(req.getCapitalType().getCapitalTypeID().equals(ctypes[i].getCapitalTypeID())))?"SELECTED":""%>><%=ctypes[i].getCapitalTypeID()%></option>
                                  <%}%>
                               </select>
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="*">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="50%">
                              <span class="requiredstar">*</span><span class="label">Priority:</span>
                            </td>
                            <td width="*" class="content">
                              <select id="capital_priority" name="capital_priority" class="requiredinput" style="width:50px;">
                                <option value="1" <%=(req.getCapitalPriority() == 1)?"SELECTED":""%>>1</option>
                                <option value="2" <%=(req.getCapitalPriority() == 2)?"SELECTED":""%>>2</option>
                                <option value="3" <%=(req.getCapitalPriority() == 3)?"SELECTED":""%>>3</option>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2">
                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                          <tr>
                            <td width="100%" class="content" align="right" style="padding-right: 57px;">
                              <span class="requiredstar">*</span><input type="checkbox" id="fund_approved" name="fund_approved" <%=req.isCapitalFundingApproved()?"CHECKED":""%>><span class="label_no_underline">Funding Approved?</span>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="137" align="left">
                        <span class="requiredstar">*</span><span class="label">Estimated Cost:</span>
                      </td>
                      <td width="*" align="left">
                        <input type="text" id="est_cost" name="est_cost" value="<%=req.getEstimatedCost()%>" style="width:100px;" class="requiredinput" >
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="10"></td></tr>
              <tr>
                <td width="100%" align="center" colspan="2">
                  <center>
                  <table width="60%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="50%" align="left" style="padding-left:12px;">
                        <img src="images/btn_submit_01.gif"
                          onmouseover="src='images/btn_submit_02.gif';"
                          onmouseout="src='images/btn_submit_01.gif';"
                          onclick="document.forms[0].submit();"><br>
                      </td>
                      <td width="50%" align="left">
                        <img src="images/btn_cancel_01.gif"
                          onmouseover="src='images/btn_cancel_02.gif';"
                          onmouseout="src='images/btn_cancel_01.gif';"
                          onclick="cancel_add();"><br>
                      </td>
                    </tr>
                  </table></center>
                </td>
              </tr>
              <%if(request.getAttribute("msg") != null){%>
                <tr><td><img src="images/spacer.gif" height="10" width="1"></td></tr>
                <tr>
                  <td width="100%" align="center" colspan="2" class="message_info">
                    <span class="requiredstar">*** </span><%=request.getAttribute("msg")%><span class="requiredstar"> ***</span>
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