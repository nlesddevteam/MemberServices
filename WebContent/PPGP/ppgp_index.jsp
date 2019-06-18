<%@ page language="java"
         session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,
                 java.text.*,
                 java.util.*"
        isThreadSafe="false"%> 

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

<esd:SecurityCheck permissions='PPGP-VIEW' />

<html>
  <head>
    <title>Professional Growth Plan</title>
    <link rel="stylesheet" href="css/growthplan.css">
  </head>

  <body>
    <table style="width:620;" cellpadding="0" cellspacing="0" border="0" align='center'>
    	<tr>
				<td width="620" valign="top" colspan="2"><br/>	
				<span style="font-size:16px;font-weight:bold;color:Navy;">Professional Learning Plan</span>		
				</td>
			</tr>
			<tr>
				<td width="215" valign="top" align="left">
					<img src="images/gp_logo_2.gif"><BR>
				</td>
				<td width="405" valign="middle" bgcolor="#0066CC" style='padding:5px;'>
					<span style='color:white; font-weight:bold;'>To begin choose a PLP to view from the Learning Plan Archive on the left OR create a new PLP by clicking the "create" link also on the left, if available.</span><BR>
				</td>
			</tr>
			<tr>
				<td colspan='2' align='right'><%= PPGP.getCurrentGrowthPlanYear()%></td>
			</tr>
    </table>
    
  </body>
</html>