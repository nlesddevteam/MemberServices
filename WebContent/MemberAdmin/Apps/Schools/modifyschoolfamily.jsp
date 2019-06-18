<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*"%>

<%!
  User usr = null;
  SchoolFamily family = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;
  Vector specialists = null;
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

  family = (SchoolFamily)request.getAttribute("FAMILY");
  specialists = PersonnelDB.getDistrictPersonnel();
  p_iter = specialists.iterator();
%>

<html>
<head>
  <title></title>
  <style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
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
										<span class="boldBlack11pxLower">School Family Admin - Modify Family</span><BR><BR>																										
										<table width="100%" cellpadding="0" cellspacing="0" border="0">			
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
												</td>
											</tr>
                      <tr>
												<td width="100%" align="left" valign="middle" colspan="4">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="15"><BR>
												</td>
											</tr>
                      <tr id="addNewSchoolFamily">
												<td width="100%" align="left" valign="middle" colspan="4">
                          <form name="modfamily" action="schoolFamilyAdmin.html" method="post">
                          <input type="hidden" name="op" value="mod">
                          <input type="hidden" name="confirmed" value="true">
                          <input type="hidden" name="family_id" value="<%=family.getSchoolFamilyID()%>">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">School Family:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<input type="text" name="family_name" style="width: 100%;" class="selectBox" value="<%=family.getSchoolFamilyName()%>">
															</td>
														</tr>
														<tr>
															<td width="20%" align="left" valign="middle">
																&nbsp;<span class="boldBlack10pxTitle">Program Specialist:</span>
															</td>
															<td width="80%" align="left" valign="middle">
																<select name="ps_id" style="width: 100%;" class="selectBox">
                                  <option value="-1">NO PROGRAM SPECIALIST ASSIGNED</option>
                                  <option value="<%=family.getProgramSpecialist().getPersonnelID()%>" SELECTED><%=family.getProgramSpecialist().getFullNameReverse()%></option>
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>" ><%=p.getFullName()%></option>
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
                                  <%sch_iter = family.getSchoolFamilySchools().iterator();
                                    while(sch_iter.hasNext())
                                    {
                                      school = (School) sch_iter.next();
                                  %>  <option value="<%=school.getSchoolID()%>" SELECTED><%=school.getSchoolName()%></option>
                                  <%
                                  	}
                                                                    
                                                                      sch_iter = SchoolDB.getSchoolsNotAssignedSchoolFamily().iterator();
                                                                      while(sch_iter.hasNext()){
                                                                        school = (School) sch_iter.next();
                                  %>  <option value="<%=school.getSchoolID()%>"><%=school.getSchoolName()%></option>
                                  <%}%>
																</select>
															</td>
														</tr>
														<tr>
															<td width="100%" align="right" valign="middle" colspan="2">
																<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0" 
                                     onclick="document.modfamily.submit();"
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
													</table>
                          </form>
												</td>
											</tr>                    
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