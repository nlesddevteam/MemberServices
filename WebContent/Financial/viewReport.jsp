<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
                 com.awsd.personnel.*,
                 com.awsd.financial.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
  PersonnelReports reports = null;
  Report rpt = null;
  Report tmp = null;
  Iterator iter = null;
  Account a = null;
  AccountSummary sum = null;
  DecimalFormat df = null;
  double btot = 0;
  double atot = 0;
  double etot = 0;
  double ttot = 0;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("FINANCIAL-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
    else
    {
      rpt = (Report) request.getAttribute("Report");

      if(!usr.getPersonnel().validateReportRequest(rpt))
      {
%>      <jsp:forward page="myFinancialReports.jsp"/>      
<%     }
    }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  reports = new PersonnelReports(usr.getPersonnel());
  iter = reports.iterator();
  df = new DecimalFormat("$#,##0.00;($#,##0.00)");
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
    <form name="viewnextrpt" action="viewFinancialReport.html" method="post">
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
											<span class="boldTitle"><%=rpt.getReportName()%></span>
										</td>
										<td width="200" align="right" valign="middle">
											<select name="rpt" style="font-family: Arial, Helvetica, sans-serif; font-size: 10px;" onchange="document.viewnextrpt.submit();">
                      <% while(iter.hasNext()) {
                        tmp = (Report) iter.next();
                      %><option value="<%= tmp.getReportID()%>" <%=(tmp.getReportID()==rpt.getReportID())?"SELECTED":""%>><%=tmp.getReportName()%></option> 
                      <% } %>
											</select>
										</td>
									</tr>
									<tr>
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="90%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Account ID</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>
																			<td width="17%" align="left" valign="middle">
																				<span class="whiteTitle">Account Name</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Budget</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Actual</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Committed</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Balance</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
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
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
                              <%  btot = atot = etot = ttot = 0;
                                  iter = rpt.getReportAccounts().entrySet().iterator();
                                  while(iter.hasNext())
                                  {
                                    a = (Account) ((Map.Entry)iter.next()).getValue();
                                    sum = a.getAccountSummary();
                              %>    <tr height="20" style="background-color: #CCE8C2;">															
                                      <td width="16%" align="left" valign="middle">
                                        <a href="viewAccountDetails.html?rpt=<%=rpt.getReportID()%>&aid=<%=a.getFormatedAccountCode()%>" class="detailsLink"><%=a.getFormatedAccountCode()%></a><BR>
                                      </td>
                                      <td width="17%" align="left" valign="middle">
                                        <%=a.getAccountDescription()%><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <% btot += sum.getAccountBudget();%>
                                        <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <% atot += sum.getAccountActual();%>
                                        <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                          <% etot += sum.getAccountEncumbrance();%>
                                          <font color="<%=(sum.getAccountEncumbrance() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountEncumbrance())%></font><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <%double bal = sum.getAccountBudget() - (-1 * sum.getAccountActual()) - sum.getAccountEncumbrance();
                                          ttot += bal;
                                        %>                                          
                                        <font color="<%=(bal < 0)?"#FF0000":"#000000"%>"><%=df.format(bal)%></font><BR>                                      </td>																															
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
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal">Totals:</span>
																						</td>
																					</tr>
																				</table>																				
																			</td>
                                      <td width="17%" align="left" valign="middle">
																				<span class="whiteTotal"></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="2" height="1"><%=df.format(btot)%><BR></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="3" height="1"><%=df.format(atot)%></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="3" height="1"><%=df.format(etot)%></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="2" height="1"><%=df.format(ttot)%></span>
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
										<td width="100%" align="center" valign="bottom" colspan="3">
											<table width="90%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="70%" align="left" valign="middle">
                          <img src="images/spacer.gif" width="1" height="10"><BR>
													<a href="javascript:history.go(-1);">back</a><BR>
                          <img src="images/spacer.gif" width="1" height="10"><BR>
													</td>
													<td width="30%" align="right" valign="middle">
														<img src="images/spacer.gif" width="1" height="10"><BR>
														<!--a href="javascript:print();">Printer Friendly</a><BR>-->
														<img src="images/spacer.gif" width="1" height="10"><BR>
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
    </form>
	</body>
</html>
