<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<html>
  <head>
    <title>Members Admin - Personnel Administration :: Effective Permissions</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
    <style type="text/css">
    	.title {font-weight: bold;}
    	a { text-decoration:none; vertical-align:middle;}
    	a:hover { color: red; }
    	li { vertical-align: middle;}
    </style>
  </head>

	<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
		<form name="change" action="/MemberServices/MemberAdmin/Apps/Personnel/personnelEffectivePermissions.html?pid=${prec.personnelID}" method="post">
			<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
			  <tr>
			    <td width="100%" valign="top" bgcolor="#333333">
			      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
			    </td>
			  </tr>
			  <tr>
		      <td width="100%" valign="top" align='center'>
		      	<table width="70%" cellpadding="0" cellspacing="0" border="0">
		         	<tr>
		         		<td>
		         			<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="40"><BR>
		               <hr noshade color="#333333" size="2" width="100%" align="right">
		               <span class="header1">Personnel Administration :: Effective Roles/Permissions</span><BR>
		               <hr noshade color="#333333" size="2" width="100%" align="right">
		         		</td>
		         	</tr>
		           <tr>
		             <td width="100%" valign="top" style='padding-left: 25px;'>
		               <table cellpadding="5" cellspacing="0" border="0" >
		                 <tr>
		                   <td class="title" valign="middle">${prec.fullName}</td>
		                 </tr>
		                 <c:choose>
		                 	<c:when test="${fn:length(roles) gt 0}">
		                 		<tr>
		                 			<td>
		                 				<ol>
					                 		<c:forEach items="${roles}" var="rentry">
					                 			<li>
					                 				<i>${rentry.key}</i>&nbsp;<a onclick='return confirm("Are you sure you want to remove \"${prec.fullName}\" from \"${rentry.key}\"?");' href="personnelEffectivePermissions.html?remove&pid=${prec.personnelID}&rid=${rentry.key}">remove</a>
					                 				
						                 			<c:if test="${fn:length(rentry.value.rolePermissions) gt 0 }">
						                 				<ul>
								                 			<c:forEach items="${rentry.value.rolePermissions}" var='pentry'>
								                 				<li>${pentry.key}</li>
								                 			</c:forEach>
							                 			</ul>
						                 			</c:if>
					                 			</li>
					                 		</c:forEach>
		                 				</ol>
		                 			</td>
			                	</tr>
		                 	</c:when>
		                 	<c:otherwise>
		                 		<tr>
		                 			<td style='padding-left: 25px;'>User has no roles/permissions assigned.</td>
		                 		</tr>
		                	</c:otherwise>
		          			</c:choose>
		               </table>
		             </td>
		           </tr>
		           <tr>
		             <td align="left" colspan="2">
		               <hr noshade color="#333333" size="1" width="100%" align="right">
		             </td>
		           </tr>
		         </table>
		      </td>
		    </tr>
	    </table>
    </form>
  </body>
</html>