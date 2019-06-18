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
  AccountTransactions trans = null;
  AccountTransaction tran = null;
  DecimalFormat df = null;
  SimpleDateFormat sdf = null;
  SimpleDateFormat sdf2 = null;
  double atot = 0;
  double etot = 0;
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
  a = (Account) request.getAttribute("Account");
  trans = a.getAccountTransactions();
  
  iter = reports.iterator();
  df = new DecimalFormat("$#,##0.00;($#,##0.00)");
  sdf = new SimpleDateFormat("yyyyMMdd");
  sdf = new SimpleDateFormat("MM/dd/yyyy");
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
										<td width="25%" align="left" valign="middle">
											<img src="images/logo.gif"><BR>
										</td>
										<td width="50%" align="center" valign="middle">
											<span class="boldTitle"><%=a.getFormatedAccountCode() + " - " + a.getAccountDescription()%></span>
										</td>
										<td width="25%" align="right" valign="middle">
                      
											<select name="rpt" style="font-family: Arial, Helvetica, sans-serif; font-size: 10px;" onchange="document.viewnextrpt.submit();">
                      <% while(iter.hasNext()) {
                        tmp = (Report) iter.next();
                      %><option value="<%= tmp.getReportID()%>" <%=(tmp.getReportID()==rpt.getReportID())?"SELECTED":""%>><%=tmp.getReportName()%></option> 
                      <% } %>
											</select>
                      <img src="/MemberServices/Financial/images/spacer.gif" height="1" width="15" />
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
																							<span class="whiteTitle">Date</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>
																			<td width="17%" align="left" valign="middle">
																				<span class="whiteTitle">Vendor</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Invoice</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Purchase Order</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTitle">Actual</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Encumbrance</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
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
                              <%  atot = etot =0;
                                  iter = trans.iterator();
                                  while(iter.hasNext())
                                  {
                                    tran = (AccountTransaction) iter.next();
                              %>    <tr height="20" style="background-color: #CCE8C2;">															
                                      <td width="16%" align="left" valign="middle">
                                        <%=tran.getTransactionDate()%><BR>
                                      </td>
                                      <td width="17%" align="left" valign="middle">
                                        <%=(tran.getVendor()!=null)?tran.getVendor():""%><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <%=(tran.getInvoiceNumber()!=null)?tran.getInvoiceNumber():""%><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <%=(tran.getPurchaseOrderNumber()!=null)?tran.getPurchaseOrderNumber():""%><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                          <% atot += tran.getActual();%>
                                          <font color="<%=(tran.getActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(tran.getActual())%></font><BR>
                                      </td>
                                      <td width="16%" align="left" valign="middle">
                                        <% etot += tran.getEncumbrance();%>
                                        <font color="<%=(tran.getEncumbrance() < 0)?"#FF0000":"#000000"%>"><%=df.format(tran.getEncumbrance())%></font><BR>
                                      </td>
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
																				<span class="whiteTotal"></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTotal"></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="3" height="1"><%=df.format(atot)%></span>
																			</td>
																			<td width="16%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="2" height="1"><%=df.format(etot)%></span>
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
														<!--<a href="javascript:print();">Printer Friendly</a><BR>-->
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
