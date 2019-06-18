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
  AccountGroupSummary sum = null;
  DecimalFormat df = null;
  double variance = 0;

  double btot = 0;
  double atot = 0;
  
  double trevb = 0;
  double treva = 0;
  
  double texpb = 0;
  double texpa = 0;
  
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!((usr.getUserPermissions().containsKey("FINANCIAL-VIEW"))&&(usr.getUserPermissions().containsKey("FINANCIAL-VIEW-REVENUE-EXPENDITURE"))))
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

  df = new DecimalFormat("$#,##0.00;($#,##0.00)");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
										<td colspan="2" align="left" valign="middle">
											<span class="boldTitle">Eastern School Board<br>Revenue &AMP; Expense Statement<BR> <%=(new SimpleDateFormat("MM/dd/yyyy")).format(Calendar.getInstance().getTime())%></td>
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
																			<td width="34%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Category</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>
																			<td width="22%" align="left" valign="middle">
																				<span class="whiteTitle">Actual</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="22%" align="left" valign="middle">
																				<span class="whiteTitle">Budget</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
																			</td>
																			<td width="22%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Variance</span>&nbsp;<a href="sort"><img src="images/sort.gif" alt="Sort By" border="0"></a><BR>
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
                              <tr height="20" style="background-color:#CCE8C2;">															
                                <td colspan="4" align="left" valign="middle">REVENUES</td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">															
                                <td colspan="4" align="left" valign="middle" style="padding-left:15px;">PROVINCIAL GROVERNMENT GRANTS</td>
                              </tr>
                              
                              <%  
                                btot = atot = 0;
                              %>
                              
                              <tr height="20" style="background-color: #CCE8C2;">															
                                <% sum = new AccountGroupSummary(new AccountGroup("32011"), "Operating Grant"); %>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>
                              
                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup("35061"), "Provincial French Grant");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32021", "32022"}), "Teacher Salaries");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32023"}), "Student Assistant Salaries");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32017"}), "Director and Asst. Director Salaries");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32031-32033"}), "Pupil Transportation");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32034", "34011", "34032"}), "Other");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"32016"}), "Special Grants");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:25px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <B>TOTAL PROVINCIAL GOVN'MT GRANTS</B>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(atot < 0)?"#FF0000":"#000000"%>"><%=df.format(atot)%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(btot < 0)?"#FF0000":"#000000"%>"><%=df.format(btot)%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=((Math.abs(btot) - Math.abs(atot)) < 0)?"#FF0000":"#000000"%>"><%=df.format(Math.abs(btot) - Math.abs(atot))%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"33012-33014"}), "Donations");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35021"}), "Recovery of Expenditures");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35025"}), "Interest on Investments");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35081", "35084"}), "Federal Grants");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35082"}), "Education Foundation");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35051"}), "Insurance Proceeds");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"34021"}), "School Rentals");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35085"}), "Computer for Schools");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35041"}), "Federal Rebates");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35091"}), "Textbook Revenues");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"35092"}), "Miscellaneous/Sundry");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"71011-78011"}), "Capital Revenue");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += sum.getAccountActual();%>
                                  <font color="<%=(sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += sum.getAccountBudget();%>
                                  <font color="<%=(sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <% 
                                  treva = atot;
                                  trevb = btot;
                                %>
                                <td width="34%" align="left" valign="middle">
                                  <B>TOTAL REVENUES</B>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(atot < 0)?"#FF0000":"#000000"%>"><%=df.format(atot)%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(btot < 0)?"#FF0000":"#000000"%>"><%=df.format(btot)%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=((Math.abs(btot) - Math.abs(atot)) < 0)?"#FF0000":"#000000"%>"><%=df.format(Math.abs(btot) - Math.abs(atot))%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <td colspan="4" align="left" valign="middle">&nbsp;</td>
                              </tr>
                              
                              <tr height="20" style="background-color: #CCE8C2;">
                                <%
                                  atot = 0;
                                  btot = 0;
                                %>
                                <td colspan="4" align="left" valign="middle"><B>EXPENDITURES</B></td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"51010-51099"}), "Administrative");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"52009-52099","57011"}), "Instruction");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"53008-53099"}), "Operation & Maintenance - Schools");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"54013-54043", "55011", "54080"}), "Pupil Transportation");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"56012-56014"}), "Debt Charges");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <%sum = new AccountGroupSummary(new AccountGroup(new String[]{"81010-83013", "58011"}), "Capital Expenditures");%>
                                <td width="34%" align="left" valign="middle" style="padding-left:15px;">
                                  <%=sum.getDescription()%>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <% atot += -sum.getAccountActual();%>
                                  <font color="<%=(-sum.getAccountActual() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountActual())%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <% btot += -sum.getAccountBudget();%>
                                  <font color="<%=(-sum.getAccountBudget() < 0)?"#FF0000":"#000000"%>"><%=df.format(-sum.getAccountBudget())%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <%variance =  Math.abs(sum.getAccountBudget()) - Math.abs(sum.getAccountActual());%>                                          
                                  <font color="<%=(variance < 0)?"#FF0000":"#000000"%>"><%=df.format(variance)%></font><BR>
                                </td>
                              </tr>

                              <tr height="20" style="background-color: #CCE8C2;">
                                <% 
                                  texpa = atot;
                                  texpb = btot;
                                %>
                                <td width="34%" align="left" valign="middle">
                                  TOTAL EXPENDITURES
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(atot < 0)?"#FF0000":"#000000"%>"><%=df.format(atot)%></font><BR>
                                </td>    
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=(btot < 0)?"#FF0000":"#000000"%>"><%=df.format(btot)%></font><BR>
                                </td>
                                <td width="22%" align="left" valign="middle">
                                  <font color="<%=((Math.abs(btot) - Math.abs(atot)) < 0)?"#FF0000":"#000000"%>"><%=df.format(Math.abs(btot) - Math.abs(atot))%></font><BR>
                                </td>
                              </tr>

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
																			<td width="34%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal">EXCESS OF REVENUE OVER EXPENDITURE</span>
																						</td>
																					</tr>
																				</table>																				
																			</td>
																			<td width="21%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="2" height="1"><%=df.format(treva - texpa)%><BR></span>
																			</td>
																			<td width="21%" align="left" valign="middle">
																				<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="3" height="1"><%=df.format(trevb - texpb)%></span>
																			</td>
																			<td width="21%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>																						
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTotal"><img src="/MemberServices/Financial/images/Spacer.gif" width="2" height="1"><%=df.format(Math.abs(trevb - texpb) - Math.abs(treva - texpa))%></span>
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
