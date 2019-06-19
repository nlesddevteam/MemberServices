<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="java.util.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Web Update System - Programs</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "./css/webupdatesystem.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	<body style="margin-top:-30px;">
    <form id="pol_cat_frm" action="addNewProgram.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="800" height="650" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <!--<img src="../images/spacer.gif" width="1" height="125"><br>-->
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">

              <tr>
                <td width="100%" align="center" valign="middle">
                  <table width="60%" cellpadding="0" cellspacing="0" align="center" valign="top">
                    <tr>
                      <td colspan="2" align="left">
                      <br />
                      <br />
                      <br />
                      <br />
                        <h2>Add New Program</h2>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Descriptor Title:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="descriptor_title"  name="descriptor_title" style="width:250px;"></td>
                    </tr>
                     <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Region:</span></td>
                      <td width="*" align="left" valign="middle">
                      
                       <select id="program_region" name="program_region" class="requiredinput">
						<c:forEach var="item" items="${programsregions}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
                      </td>
                    </tr>
                     <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Level:</span></td>
                      <td width="*" align="left" valign="middle">
                      
                       <select id="program_level" name="program_level" class="requiredinput">
						<c:forEach var="item" items="${programslevels}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Program Status:</span></td>
                      <td width="*" align="left" valign="middle">
                      Disabled<INPUT TYPE="radio" NAME="program_status" ID="program_status" VALUE="0">
                      Enabled<INPUT TYPE="radio" NAME="program_status"  ID="program_status" VALUE="1" CHECKED>
                      </td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</td>
                      </tr>
                    <%}%>
                    
                    <tr>
                      <td colspan="2">
                        <br><button id="butSave">Add New Program</button><br><br>
                      </td>
                    </tr>

                  </table>
                </td>
              </tr>

            </table> 
          </td>
        </tr>
      </table> 
    </form><br/><a href='javascript:history.go(-1)' title="Back">Back</a><br/>
	</body>
</html>