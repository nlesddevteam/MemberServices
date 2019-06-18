<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*, java.util.*,java.text.*,com.awsd.security.*, com.awsd.weather.*,com.awsd.school.*,com.awsd.personnel.*"
         isThreadSafe="false" %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>


<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = null;
  SchoolSystems systems = null;
  SchoolSystem sys = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator<SchoolSystem> sys_iter = null;
  Iterator<School> sch_iter = null;
  Iterator<Personnel> p_iter = null;
  Vector<Personnel> principals = null;
  Personnel ap[] = null;

  systems = new SchoolSystems();
  
  sys_iter = systems.iterator();
  principals = PersonnelDB.getDistrictPersonnel();
  p_iter = principals.iterator();
%>

<html>
<head>
<meta http-equiv="Cache-Control" content="no-store,no-cache, must-revalidate,post-check=0, pre-check=0,max-age=0">
<META HTTP-EQUIV="Expires" CONTENT="Mon, 15 Sep 2003 1:00:00 GMT">
<title></title>
<style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
<script language="JavaScript">
	function toggle(target)
		{
			obj=(document.all) ? document.all[target] : document.getElementById(target);
			obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
		}
		
	function toggleHeight(target2)
		{
			obj=(parent.document.all) ? parent.document.all[target2] : parent.document.getElementById(target2);
			obj.style.height=(obj.style.height=='26px') ? '208px' : '26px';
			
		}
</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >	
		<tr>
			<td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Members Admin</span><BR>
						</td>
						<td width="50" align="right" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
						</td>
					</tr>											
				</table>										
			</td>
		</tr>		
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">
        
				<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
					<tr>
						<td width="100%" align="left" valign="top">								
							<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
								<tr>
									<td width="100%" align="left" valign="top">
										<span class="boldBlack11pxLower">School System Admin</span><BR><BR>
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%">
                          <form name="modsys" action="schoolSystemAdmin.html" method="post">
                            <input type="hidden" name="op" value="mod">
                            <input type="hidden" name="ss_id" value="">
                            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                              <tr>
                                <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
                                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                                </td>
                              </tr>
                              <%while(sys_iter.hasNext()) {
                                sys = (SchoolSystem) sys_iter.next();
                              %>
                                <tr height="25" style="background-color: #DBEAF5;">
                                  <td width="50%" align="left" valign="middle">
                                    &nbsp;<span class="boldBlack10pxTitle"><%=sys.getSchoolSystemName()%></span>
                                  </td>
                                  <td width="50%" align="left" valign="middle">
                                    <span class="boldBlack10pxTitle"><%=(sys.getSchoolSystemAdmin()!=null)?"<a href='/MemberServices/loginAs.html?pid=" + sys.getSchoolSystemAdmin().getPersonnelID() + "'  >" + sys.getSchoolSystemAdmin().getFullNameReverse() + "</a>":"UNASSIGNED"%>
                                    <%
	                                    Personnel[] backup = sys.getSchoolSystemAdminBackup();
	                    								for(int i=0; ((backup != null)&&( i < backup.length));i++)
	                    									if(backup[i] != null)
	                    										out.println("/<a href=javascript:top.location.href='/MemberServices/loginAs.html?pid=" + backup[i].getPersonnelID() + "' >" + backup[i].getFullNameReverse() + "</a>");
                                    %>
                                    </span>
                                    
                                  </td>
                                  <td width="39" align="left" valign="middle">
                                    <a href=""><img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                                  </td>
                                  <td width="36" align="left" valign="middle">
                                    <a href="" onclick="document.forms[0].ss_id.value='<%=sys.getSchoolSystemID()%>';document.forms[0].submit();return false;"><img src="/MemberServices/MemberAdmin/images/modify_icon.gif" border="0" alt="Modify"></a>&nbsp;<BR>
                                  </td>
                                </tr>
                                <tr>
                                  <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                                    <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                                  </td>
                                </tr>
                                <%sch_iter = sys.getSchoolSystemSchools().iterator();
                                  while(sch_iter.hasNext()){
                                    school = (School) sch_iter.next();
                                    //System.err.println("------SCHOOL: " + school.getSchoolName());
                                %><tr>
                                    <td width="40%" align="left" valign="middle">
                                      &nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                                    </td>
                                    <td width="40%" align="left" valign="middle">
                                      &nbsp;<span class="normalGrey10pxText"><%=(school.getSchoolPrincipal()!= null)?school.getSchoolPrincipal().getFullNameReverse():"No principal assign."%>
                                      <%
                                      	ap = school.getAssistantPrincipals();
                                      	if(ap != null && ap.length > 0){
                                      		for(int i=0; i < ap.length; i++)
                                      			out.println("/" + ap[i].getFullNameReverse());
                                      	}else
                                      		out.println("/No assistant principal assign.");
                                      %>
                                      </span>
                                    </td>
                                    <td colspan="2" align="left" valign="middle">
                                      &nbsp;
                                    </td>
                                  </tr>
                                <%
                                }
                                out.flush();
                              }%>
                              <tr>
                                <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                                </td>
                              </tr>
                              <tr height="25" style="background-color: #DBEAF5;">
                                <td width="100%" align="left" valign="middle" colspan="4">
                                  &nbsp;<a href="javascript:(toggle('addNewSchoolSystem'));" class="11pxBlueLink">[+] Add New School System</a>
                                </td>												
                              </tr>
                              <tr>
                                <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                                </td>
                              </tr>
                              <tr>
                                <td width="100%" height="8" align="left" valign="middle" colspan="4">
                                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="8"><BR>
                                </td>
                              </tr>
                            </table>
                          </form>
                        </td>
                      </tr>
											<tr id="addNewSchoolSystem" style="display: none;">
												<td width="100%" align="left" valign="middle" colspan="4">
                          <form name="addsys" action="schoolSystemAdmin.html" method="post">
                          <input type="hidden" name="op" value="add">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">School System:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<input type="text" name="ss_name" style="width: 100%;" class="selectBox">
															</td>
														</tr>
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">Administrator:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select name="ss_admin" style="width: 100%;" class="selectBox">
                                  <option value="-1">NO ADMINISTRATOR ASSIGNED</option>
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
															</td>
														</tr>
                            <tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">Backup Administrator:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select name="ss_admin_bckup" style="width: 100%;" class="selectBox">
                                  <option value="-1">NO BACKUP ADMINISTRATOR ASSIGNED</option>
                                  <%p_iter = principals.iterator();
                                    while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
															</td>
														</tr>
														<tr>
															<td width="20%" align="left" valign="top">
																&nbsp;<span class="boldBlack10pxTitle">Adding Schools:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select multiple="multiple" name="schools" size="10" class="selectBox" style="width: 100%; height: 200px;">
                                  <%
                                  	for(School s : SchoolDB.getSchoolsNotAssignedSchoolSystem()){
                                  %>
                                  	<option value="<%=s.getSchoolID()%>">
                                  		<%=s.getSchoolName()%> [<%= s.getZone() != null ? s.getZone().getZoneName() : "" %><%= s.getRegion() != null ? " - " + s.getRegion().getName():"" %>]
                                  	</option>
                                  <%}%>
																</select>
															</td>
														</tr>
														<tr>
															<td width="100%" align="right" valign="middle" colspan="2">
																<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0" 
                                     onclick="document.addsys.submit();"
                                     onMousedown="src='/MemberServices/MemberAdmin/images/ok_button_02.gif';" 
                                     onMouseup="src='/MemberServices/MemberAdmin/images/ok_button_01.gif';">
                                     &nbsp;&nbsp;
                                <img src="/MemberServices/MemberAdmin/images/cancel_button_01.gif" border="0" 
                                     onClick="" 
                                     onMousedown="src='/MemberServices/MemberAdmin/images/cancel_button_02.gif';" 
                                     onMouseup="src='/MemberServices/MemberAdmin/images/cancel_button_01.gif';">
                                <BR>
															</td>															
														</tr>
													</table>
                          </form>
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
	</table>
</body>
</html>
