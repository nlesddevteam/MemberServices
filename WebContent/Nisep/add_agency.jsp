<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.servlet.*,
                  com.esdnl.nicep.beans.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  AgencyDemographicsBean agency = (AgencyDemographicsBean) request.getAttribute("AGENCYBEAN");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - NISEP Administration</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'css/home.css';</style>
  
</head>
<body style="margin-top:15px;">

  <esd:SecurityCheck permissions="NISEP-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <jsp:include page="header.jsp" flush="true" />
  <table width="760" cellpadding="0" cellspacing="0" border="0"  align="center" style="border: solid 1px #FFB700;">
    <tr>
      <td>   
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="178" align="left" valign="top" style="padding-left:5px;border-right:solid 1px #FFB700;">
                    <img src="images/spacer.gif" width="1" height="5"><BR>
                    
                    <jsp:include page="side_nav.jsp" flush="true"/>
                    
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                  </td>
                  <td width="15" align="left" valign="top">
                    <img src="images/spacer.gif" width="15" height="1"><BR>
                  </td>
                  <td width="*" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top" style="padding-top:5px;padding-right:10px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><%=(agency == null)?"Add":"Update"%> Agency</td>
                            </tr>
                            
                            <tr style="padding-top:8px;">
                              <td>
                                <form id="frmAddAgency" action="addAgency.html" method="post">
                                  <%if(agency != null){%>
                                    <input type='hidden' name='op' value='UPDATE'>
                                    <input type='hidden' name='id' value='<%=agency.getAgencyId()%>'>
                                  <%}else{%>
                                    <input type='hidden' name='op' value='ADD'>
                                  <%}%>
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding:5px;">
                                    <tr>
                                      <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Name</td>
                                      <td>
                                        <input type="text" name="agency_name" id="agency_name" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("agency_name"):(agency != null)?agency.getName():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Address</td>
                                      <td>
                                        <input type="text" name="address1" id="address1" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("address1"):(agency != null)?agency.getAddress1():""%>"><br>
                                        <input type="text" name="address2" id="address2" style="width:175px;" class="requiredInputBox" value="<%=((form!=null)&&!StringUtils.isEmpty(form.get("address2")))?form.get("address2"):((agency != null)&&!StringUtils.isEmpty(agency.getAddress2()))?agency.getAddress2():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>City/Town</td>
                                      <td>
                                        <input type="text" name="city_town" id="city_town" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("city_town"):(agency != null)?agency.getCityTown():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Province/State</td>
                                      <td>
                                        <input type="text" name="province_state" id="province_state" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("province_state"):(agency != null)?agency.getProvinceState():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Country</td>
                                      <td>
                                        <input type="text" name="country" id="country" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("country"):(agency != null)?agency.getCountry():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='150px'><span class="requiredStar">*&nbsp;</span>Zipcode</td>
                                      <td>
                                        <input type="text" name="zipcode" id="zipcode" style="width:175px;" class="requiredInputBox" value="<%=(form!=null)?form.get("zipcode"):(agency != null)?agency.getZipcode():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">
                                        <input type="submit" value="<%=(agency == null)?"Add":"Update"%>">
                                      </td>
                                    </tr>
                                  </table>
                                </form>
                                
                                <%if(request.getAttribute("msg") != null){%>
                                  <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td class="messageText"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
                                <%}%>
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
  </table>
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
