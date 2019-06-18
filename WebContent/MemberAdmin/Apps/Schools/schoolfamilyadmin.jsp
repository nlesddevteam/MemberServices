<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*"%>

<%!
  User usr = null;
  SchoolFamilies families = null;
  SchoolFamily family = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator fam_iter = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;
  Vector admins = null;
  Personnel aps[] = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  families = new SchoolFamilies();
  fam_iter = families.iterator();
  admins = PersonnelDB.getDistrictPersonnel();
  p_iter = admins.iterator();
%>

<html>
<head>
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
<body background="/MemberServices/MemberAdmin/images/bg.gif">
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
										<span class="boldBlack11pxLower">School Family Admin</span><BR><BR>																										
										<table width="100%" cellpadding="0" cellspacing="0" border="0">			
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
												</td>
											</tr>
											<tr height="25" style="background-color: #DBEAF5;">
												<td width="100%" align="left" valign="middle" colspan="4">
													&nbsp;<a href="javascript:(toggle('addNewSchoolFamily'));" class="11pxBlueLink">[+] Add New School Family</a>
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
                      <tr id="addNewSchoolFamily" style="display: none;">
												<td width="100%" align="left" valign="middle" colspan="4">
                          <form name="addfamily" action="schoolFamilyAdmin.html" method="post">
                          <input type="hidden" name="op" value="add">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">School Family:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<input type="text" name="family_name" style="width: 100%;" class="selectBox">
															</td>
														</tr>
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">Administrator:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select name="ps_id" style="width: 100%;" class="selectBox">
                                  <option value="-1">NO ADMINISTRATOR ASSIGNED</option>
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%> (<%=p.getUserName()%>)</option>
                                  <%}%>
                                </select>
															</td>
														</tr>
														<tr>
															<td width="20%" align="left" valign="top">
																&nbsp;<span class="boldBlack10pxTitle">Adding Schools:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select multiple name="schools" size="10" class="selectBox" style="width: 100%; height: 200px;">
                                  <%
                                  	sch_iter = SchoolDB.getSchoolsNotAssignedSchoolFamily().iterator();
                                                                      while(sch_iter.hasNext()){
                                                                        school = (School) sch_iter.next();
                                  %><option value="<%=school.getSchoolID()%>"><%=school.getSchoolName()%></option>
                                  <%}%>
																</select>
															</td>
														</tr>
														<tr>
															<td width="100%" align="right" valign="middle" colspan="2">
																<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0" 
                                     onclick="document.addfamily.submit();"
                                     onMousedown="src='/MemberServices/MemberAdmin/images/ok_button_02.gif';" 
                                     onMouseup="src='/MemberServices/MemberAdmin/images/ok_button_01.gif';">
                                <!--&nbsp;&nbsp;
                                <img src="/MemberServices/MemberAdmin/images/cancel_button_01.gif" border="0" 
                                     onClick="" 
                                     onMousedown="src='/MemberServices/MemberAdmin/images/cancel_button_02.gif';" 
                                     onMouseup="src='/MemberServices/MemberAdmin/images/cancel_button_01.gif';">
                                -->
                                <BR>
															</td>															
														</tr>
                            <tr>
                              <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
                                <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                              </td>
                            </tr>
													</table>
                          </form>
												</td>
											</tr>
                      <%if(!fam_iter.hasNext()){%>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #DBEAF5;padding-left:5px;">
                            &nbsp;<span class="normalGrey10pxText">No school families configured.</span>
                          </td>
                        </tr>
                      <%}else{
                        while(fam_iter.hasNext()) {
                          family = (SchoolFamily) fam_iter.next();
                        %>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
                            <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <tr height="25" style="background-color: #DBEAF5;">
                          <td width="50%" align="left" valign="middle">
                            &nbsp;<span class="boldBlack10pxTitle"><%=family.getSchoolFamilyName()%></span>
                          </td>
                          <td width="50%" align="left" valign="middle">
                            <span class="boldBlack10pxTitle"><%=(family.getProgramSpecialist()!=null)?family.getProgramSpecialist().getFullNameReverse():"UNASSIGNED"%></span>
                          </td>
                          <td width="36" align="right" valign="middle">
                            <a href="schoolFamilyAdmin.html?op=mod&family_id=<%=family.getSchoolFamilyID()%>"><img src="/MemberServices/MemberAdmin/images/modify_icon.gif" border="0" alt="Modify"></a>&nbsp;<BR>
                          </td>
                          <td width="39" align="right" valign="middle">
                            <a href="schoolFamilyAdmin.html?op=del&family_id=<%=family.getSchoolFamilyID()%>"><img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                          </td>
                        </tr>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                            <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <%sch_iter = family.getSchoolFamilySchools().iterator();
                          while(sch_iter.hasNext()){
                            school = (School) sch_iter.next();
                            aps = school.getAssistantPrincipals();
                        %>
                          <tr>
                            <td width="40%" align="left" valign="middle" style="border-top:dashed 1px #333333;border-collapse:collapse;">
                              &nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                            </td>
                            <td width="40%" align="left" valign="middle" style="border-top:dashed 1px #333333;border-collapse:collapse;">
                              <span class="normalGrey10pxText">
                              <%=(school.getSchoolPrincipal()!= null)?school.getSchoolPrincipal().getFullNameReverse():"No principal assign."%>
                              <%
                              	if((aps != null) && (aps.length > 0)){
                              		for(int i=0; i < aps.length; i++)
                              			out.println("<BR>" + aps[i].getFullNameReverse());
                              	}
                              	else
                              		out.println("<BR>No vice principal assign.");
                              %>
                              </span>
                            </td>
                            <td colspan="2" align="right" valign="middle" style="border-top:dashed 1px #333333; border-collapse:collapse;"> 
                              &nbsp;<a href="schoolFamilyAdmin.html?op=delsch&family_id=<%=family.getSchoolFamilyID()%>&sch_id=<%=school.getSchoolID()%>"><img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                            </td>
                          </tr>
                        <%
                          }
                        }
                      }%>
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
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
