<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.awsd.strike.*,com.awsd.school.*,com.awsd.personnel.*"%>

<%!User usr = null;
  SchoolStrikeGroups groups = null;
  SchoolStrikeGroup group = null;
  Schools schools = null;
  School school = null;
  DistrictPersonnel personnel = null;
  Personnel p = null;
  Iterator groups_iter = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;%>

<%
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  groups = new SchoolStrikeGroups();
  groups_iter = groups.iterator();

  personnel = new DistrictPersonnel();
  p_iter = personnel.iterator();
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

      if(obj.style.display == 'inline')
      {
        document.all.new_link.innerHTML = '[-] Add School Strike Group';
      }
      else
      {
        document.all.new_link.innerHTML = '[+] Add School Strike Group';
      }
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
										<span class="boldBlack11pxLower">School Strike Group Admin</span><BR><BR>																										
										<table width="100%" cellpadding="0" cellspacing="0" border="0">			
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #4791C5;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
												</td>
											</tr>
                      <%while(groups_iter.hasNext()) {
                        group = (SchoolStrikeGroup) groups_iter.next();
                      %>
                        <tr height="25" style="background-color: #DBEAF5;">
                          <td width="50%" align="left" valign="middle" colspan="2">
                            &nbsp;<span class="boldBlack10pxTitle">Group Liason:&nbsp;<%=group.getGroupCoordinator().getFullNameReverse()%></span>
                          </td>
                          <td width="39" align="left" valign="middle">
                            <a href="schoolStrikeGroupAdmin.html?op=del&group_id=<%=group.getGroupID()%>"><img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                          </td>
                          <td width="36" align="left" valign="middle">
                            <a href=""><img src="/MemberServices/MemberAdmin/images/modify_icon.gif" border="0" alt="Modify"></a>&nbsp;<BR>
                          </td>
                        </tr>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                            <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <%sch_iter = group.getSchoolStrikeGroupSchools().iterator();
                          while(sch_iter.hasNext()){
                            school = (School) sch_iter.next();
                        %><tr>
                            <td width="50%" align="left" valign="middle">
                              &nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                            </td>
                            <td width="50%" align="left" valign="middle">
                              &nbsp;<span class="normalGrey10pxText"><%=school.getSchoolPrincipal().getFullNameReverse()%></span>
                            </td>
                            <td width="39" align="left" valign="middle">
                              <a href=""><img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                            </td>
                            <td width="36" align="left" valign="middle">
                              <a href=""><img src="/MemberServices/MemberAdmin/images/modify_icon.gif" border="0" alt="Modify"></a>&nbsp;<BR>
                            </td>
                          </tr>
                        <%}
                      }%>
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
												</td>
											</tr>
											<tr height="25" style="background-color: #DBEAF5;">
												<td width="100%" align="left" valign="middle" colspan="4">
													&nbsp;<a id="new_link" href="javascript:(toggle('addNewSchoolStrikeGroup'));" class="11pxBlueLink">[+] Add School Strike Group</a>
												</td>												
											</tr>
											<tr>
												<td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
													<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="1"><BR>
												</td>
											</tr>
											<tr>
												<td width="100%" height="8" align="left" valign="middle" colspan="4">
													<img src="/MemberServices/MemberAdmin//images/spacer.gif" width="1" height="8"><BR>
												</td>
											</tr>
											<tr id="addNewSchoolStrikeGroup" style="display: none;">
												<td width="100%" align="left" valign="middle" colspan="4">
                          <form name="addgroup" action="schoolStrikeGroupAdmin.html" method="post">
                          <input type="hidden" name="op" value="add">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="15%" align="left" valign="top">
																&nbsp;<span class="boldBlack10pxTitle">Group Liason:</span>
															</td>
															<td width="85%" align="left" valign="middle">
																<select name="liason" style="width: 100%;" class="selectBox">
                                  <%while(p_iter.hasNext()){
                                    p = (Personnel) p_iter.next();
                                  %><option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                  <%}%>
                                </select>
															</td>
														</tr>
														<tr>
															<td width="15%" align="left" valign="top">
																&nbsp;<span class="boldBlack10pxTitle">Adding Schools:</span>
															</td>
															<td width="85%" align="left" valign="middle">
																<select multiple name="schools" size="10" class="selectBox" style="width: 100%; height: 200px;">
                                  <%
                                  	sch_iter = SchoolDB.getSchools().iterator();
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
                                     onclick="document.addgroup.submit();"
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
