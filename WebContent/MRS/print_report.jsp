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
  MaintenanceRequest[] reqs = null;
  MaintenanceRequest req = null;
  Personnel requested_by = null;
  Personnel p = null;
  Vendor vendor = null;
  School school = null;
  RequestAssignment req_ass = null;
  RequestComment comment = null;
  SimpleDateFormat sdf = null;
  HashMap ass_per = null;
  HashMap ass_ven = null;
  ArrayList pages = null; 

  usr = (User) session.getAttribute("usr");
  
  pages = (ArrayList) session.getAttribute("OUTSTANDING_REQUESTS");

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
    <STYLE TYPE="text/css">
      br.pagebreak {page-break-before: always}
    </STYLE> 
	</head>
	<body style="margin:0px;" onload="window.print();self.close();">
	
		<esd:SecurityCheck permissions="MAINTENANCE-WORKORDERS-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-SCHOOL-VIEW" />
		
      <table width="100" cellpadding="0" cellspacing="0" align="left" valign="top">
        <% for(int i=0; i < pages.size(); i++){
            reqs = (MaintenanceRequest[])pages.get(i);
            for(int j=0; j < reqs.length; j++){
              req = reqs[j];
              requested_by = req.getRequestedBy();
              school = req.getSchool();
              ass_per = req.getAssignedMaintenancePersonnel();
              ass_ven = req.getAssignedVendors();%>
              
        <tr><td align="center" valign="top" style="padding-bottom:5px;"><img src="images/request_details_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent" width="100%" valign="top">
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
                      <td width="50%" valign="middle">
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
                        <%=req.getRequestType().getRequestTypeID()%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              
              <tr>
                <td width="50%" align="left">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td width="52%">
                        <span class="requiredstar">*</span><span class="label">School Priority:</span>
                      </td>
                      <td width="*" class="content">
                        <%=req.getSchoolPriority()%>
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
                      <td width="35%">
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
                RequestComment[] comments = req.getRequestComments();
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
              <%  for(int k=0; k < comments.length; k++){
              %>    <tr>
                      <td width="100%" align="left" colspan="2" class="small" style="padding-left:15px;">
                        Entered On <%=sdf.format(comments[k].getMadeOn())%> by <%=comments[k].getMadeBy().getFullNameReverse()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="left" colspan="2" class="content" style="padding-left:15px;">
                        <%=comments[k].getComment()%>
                      </td>
                    </tr>
                    <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="5"></td></tr>
              <%  }
                }%>
              <tr>
                <td width="100%" align="left" colspan="2">
                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                    <tr><td colspan="2"><span class="requiredstar">*</span><span class="label">Current Assignement:</span></td></tr>
                    <%Map.Entry[] ap = (Map.Entry[]) ass_per.entrySet().toArray(new Map.Entry[0]);
                    	Map.Entry[] vp = (Map.Entry[]) ass_ven.entrySet().toArray(new Map.Entry[0]);
                    	if((ap.length < 1)&&(vp.length < 1)){%>
                      <tr><td colspan="2" class="content" style="padding-left:15px;">No one currently assigned.</td></tr>
                    <%}else{%>
                      <tr>
                        <td width="50%" class="label_no_underline" style="border-right:solid 1px #072B61;padding-left:25px;">Maintentance Personnel</td>
                        <td width="50%" class="label_no_underline" style="padding-left:10px;">Outside Vendors</td>
                      </tr>
                      <tr>
                        <td width="50%" class="content" style="padding-left:25px;" valign="top">
                          <%if(ap.length < 1){%>
                            None assigned.
                          <%}else{%>
                            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                              <%
                                for(int k=0; k < ap.length; k++)
                                {
                                  req_ass = (RequestAssignment)ap[k].getValue();
                              %>  <tr>
                                    <td  class="content"><%=req_ass%></td>
                                    <td  align="right" style="border-right:solid 1px #072B61;padding-right:10px;"><%=sdf.format(req_ass.getAssignedDate())%></td>
                                  </tr>
                              <%}%>
                            </table>
                          <%}%>
                        </td>
                        <td width="50%" class="content" style="padding-left:10px;" valign="top">
                          <%if(vp.length < 1){%>
                            None assigned.
                          <%}else{%>
                            <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
                              <%
                                for(int k=0; k < vp.length; k++)
                                {
                                  req_ass = (RequestAssignment)vp[k].getValue();
                              %>  <tr>
                                    <td  class="content"><%=req_ass%></td>
                                    <td  align="right"><%=sdf.format(req_ass.getAssignedDate())%></td>
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
              <esd:SecurityAccessRequired permissions="MAINTENANCE-ADMIN-VIEW">
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
	              <tr style="display: <%=((req.getRequestCategory() != null) && req.getRequestCategory().getRequestCategoryID().equals("CAPITAL"))?"inline":"none"%>;">
	                <td width="100%" align="left" colspan="2">
	                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                    <tr>
	                      <td width="40%">
	                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                          <tr>
	                            <td width="110" align="left">
	                              <span class="requiredstar">*</span><span class="label">Capital Type:</span>
	                            </td>
	                            <td width="*" align="left" class="content">
	                               <%=(req.getCapitalType()!=null)?req.getCapitalType().getCapitalTypeID():"NOT ASSIGNED"%>
	                            </td>
	                          </tr>
	                        </table>
	                      </td>
	                      <td width="*">
	                        <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                          <tr>
	                            <td width="40%">
	                              <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                                <tr>
	                                  <td width="50%">
	                                    <span class="requiredstar">*</span><span class="label">Priority:</span>
	                                  </td>
	                                  <td width="*" class="content">
	                                    <%=req.getCapitalPriority()%>
	                                  </td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="*">
	                              <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                                <tr>
	                                  <td width="80%">
	                                    <span class="requiredstar">*</span><span class="label">FUNDING APPROVED?</span>
	                                  </td>
	                                  <td width="*" class="content">
	                                    <%=req.isCapitalFundingApproved()?"YES":"NO"%>
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
	                <td width="100%" align="left" colspan="2">
	                  <table width="100%" cellpadding="1" cellspacing="0" align="center" valign="top">
	                    <tr>
	                      <td width="137" align="left">
	                        <span class="requiredstar">*</span><span class="label">Estimated Cost:</span>
	                      </td>
	                      <td width="*" align="left" class="content">
	                        <%=(req.getEstimatedCost()>0)?(new DecimalFormat("$#,##0.00")).format(req.getEstimatedCost()):"UNDETERMINED"%>
	                      </td>
	                    </tr>
	                  </table>
	                </td>
	              </tr>
              </esd:SecurityAccessRequired>
              <tr><td colspan="2" class="footer" height="1"><img src="images/spacer.gif" width="1" height="1"></td></tr>
              <tr><td colspan="2" class="label_no_underline">ADDITIONAL COMMENTS</tr>
            </table>
          </td>
        </tr>
        <%if((i+1) < pages.size()){%>
        	<tr><td width="100%"><br class="pagebreak"></td></tr>
        <%}%>
      <%}}%>
      </table> 
	</body>
</html>