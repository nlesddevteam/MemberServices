<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
                 com.awsd.personnel.*,
                 com.awsd.financial.*,
                 java.util.*,
                 java.io.*"%>

<%!
  User usr = null;
  PersonnelReports reports = null;
  Report rpt = null;
  Iterator iter = null;
  int cnt;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("FINANCIAL-VIEW")))
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

  reports = new PersonnelReports(usr.getPersonnel());
  iter = reports.iterator();
  cnt=1;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Financial Package - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/financial.css";</style>		
	</head>
	<body>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="100%" align="left" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #000000; border-width: 1px;">
						<tr>
							<td width="100%" align="left" valign="top">
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="220" align="left" valign="middle">
											<img src="images/logo.gif"><BR>
										</td>
										<td width="330" align="left" valign="middle">
											<span class="boldTitle">Open Report</span>
										</td>
										<td width="200" align="right" valign="middle">
											
										</td>
									</tr>
									<tr>
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="50%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span style="color:#FFFFFF;font-weight:bold;">Instructions:</span>&nbsp;<span style="color:#FFFFFF;">Click on report icon to view report details.</span><BR>
																						</td>
																						<td width="10" align="left" valign="top">
																							<img src="images/right_title.jpg"><BR>
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
													<td width="100%" align="left" valign="top">
														<img src="images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="center" valign="top">
														<table cellpadding="0" cellspacing="10" border="0">
                              <tr height="20" style="background-color: #CCE8C2;">															
                                <td colspan="5" align="left" valign="middle">
                                  Summary Reports<BR>
                                </td>																
                              </tr>
                              <tr>
                                <td colspan="5" height="100" align="center" valign="middle">
                                  <table onclick="window.location.href='RevenueExpenditureReport.jsp';" title="Revenue Over Expenditure" width="100" height="100" cellpadding="0" cellspacing="0" onmouseover="this.style.borderColor='#000000';" onmouseout="this.style.borderColor='#CCCCCC';" style="border-style:solid;border-color:#CCCCCC;border-width:1px;background-color:#FFFFFF;cursor:hand;">
                                    <tr>
                                      <td width="100%" height="70%" align="center" valign="middle">
                                        <img src="/MemberServices/Financial/images/icon.gif">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td width="100%" height="30%" align="center" valign="top" >
                                        Revenue Over Expenditure<BR>
                                      </td>
                                    </tr>
                                  </table>
                                </td>
                              <tr>
                              <tr height="20" style="background-color: #CCE8C2;">															
                                <td colspan="5" align="left" valign="middle">
                                  User Defined Reports<BR>
                                </td>																
                              </tr>
                              <%if (!iter.hasNext()) {%>
                                <tr height="20" style="background-color: #CCE8C2;">															
                                  <td colspan="5" align="left" valign="middle">
                                   You do not have any reports available for viewing<BR>
                                  </td>																
                                </tr>
                              <%} else {%>
                                <tr>
                                  <%while(iter.hasNext())
                                    {
                                      rpt = (Report) iter.next();

                                      if((cnt++ % 5) == 0)
                                      {%>
                                        </tr><tr>
                                    <%}%>
                                      <td width="100" height="100" align="center" valign="middle">
                                        <table onclick="window.location.href='viewFinancialReport.html?rpt=<%=rpt.getReportID()%>';" title="<%=rpt.getReportName()%>" width="100" height="100" cellpadding="0" cellspacing="0" onmouseover="this.style.borderColor='#000000';" onmouseout="this.style.borderColor='#CCCCCC';" style="border-style:solid;border-color:#CCCCCC;border-width:1px;background-color:#FFFFFF;cursor:hand;">
                                          <tr>
                                            <td width="100%" height="70%" align="center" valign="middle">
                                              <img src="/MemberServices/Financial/images/icon.gif">
                                            </td>
                                          </tr>
                                          <tr>
                                            <td width="100%" height="30%" align="center" valign="top" >
                                              <%=rpt.getReportName()%><BR>
                                            </td>
                                          </tr>
                                        </table>
                                      </td>
                                  <%}%>
                                </tr>
                              <%}%>
                            </table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="50%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal"></span>
																						</td>
																					</tr>
																				</table>																				
																			</td>																			
																			<td width="50%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal"></span>
																						</td>
																						<td width="10" align="right" valign="top">
																							<img src="images/right_title.jpg"><BR>
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
										</td>
									</tr>
									<tr>
										<td width="100%" align="left" valign="top" colspan="3">
											<img src="images/spacer.gif" width="1" height="30"><BR>
										</td>
									</tr>
									<tr>
										<td width="100%" align="left" valign="top" colspan="3">
											<table width="100%" cellpadding="10" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="top">
														&copy; Copyright 2003 <a href="http://www.esdnl.ca">Eastern School District</a>. All Rights Reserved.<BR>
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
				<td width="4" align="left" valign="top" style="background-image: url('images/right_bg.jpg');">
					<img src="images/spacer.gif" width="4" height="1"><BR>
				</td>
			</tr>
			<tr>
				<td width="749" align="left" valign="top" style="background-image: url('images/botton_bg.jpg');">
				
				</td>
				<td width="5" align="left" valign="top">
					<img src="images/bottom_right_bg.jpg"><BR>
				</td>
			</tr>
		</table>
	</body>
</html>