<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="java.util.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Web Update System - Policies</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "../css/webupdatesystem.css";</style>
    <script language="JavaScript" src="../js/common.js"></script>
    		<script language="Javascript" src="../js/jquery-1.9.1.min.js"></script>
	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="../fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="../fancybox/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="../fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
	<script type="text/javascript" src="../js/changepopup.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	<body style="margin-top:-30px;">
    <form id="pol_cat_frm" action="updateProgram.html" method="post" ENCTYPE="multipart/form-data">
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
                        <h2>View Program Details</h2><input type="hidden" value="${program.id}" id="id" name="id">
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Descriptor Title:</span></td>
                      <td width="*" align="left" valign="middle"><input type="text" class="requiredinput" id="descriptor_title"  name="descriptor_title" style="width:250px;" value="${program.descriptorTitle}"></td>
                    </tr>
                     <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Region:</span></td>
                       <td width="*" align="left" valign="middle">
                            <select id="program_region" name="program_region" class="requiredinput">
								<c:forEach var="item" items="${programsregions}">
    								<c:choose>
    									<c:when test="${item.key eq program.pRegion.value}">
       										<option value="${item.key}" selected="selected">${item.value}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.key}">${item.value}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>
                      </td>
                    </tr>
                     <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Level</span></td>
                       <td width="*" align="left" valign="middle">
                            <select id="program_level" name="program_level" class="requiredinput">
								<c:forEach var="item" items="${programslevels}">
    								<c:choose>
    									<c:when test="${item.key eq program.pLevel.value}">
       										<option value="${item.key}" selected="selected">${item.value}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.key}">${item.value}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <td width="125" align="left" valign="middle"><span class="requiredstar">*</span><span class="label">Program Status:</span></td>
                      <td width="*" align="left" valign="middle">
                     <c:choose>
    					<c:when test="${program.programStatus eq 1}">
    						Disabled<INPUT TYPE="radio" NAME="program_status" ID="program_status" VALUE="0">
                      		Enabled<INPUT TYPE="radio" NAME="program_status"  ID="program_status" VALUE="1" CHECKED>
    					</c:when>
						<c:otherwise>
    						Disabled<INPUT TYPE="radio" NAME="program_status" ID="program_status" VALUE="0" CHECKED>
                      		Enabled<INPUT TYPE="radio" NAME="program_status"  ID="program_status" VALUE="1">
    					</c:otherwise>
					</c:choose>

                      </td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr>
                        <td colspan="2" class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</td>
                      </tr>
                    <%}%>
                    
                    <tr>
                      <td colspan="2">
                        <br><button id="butSave">Update Program</button><br><br>
                      </td>
                    </tr>

                  </table>
                </td>
              </tr>
              <tr>
              <td>
					<table align="center" width="70%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="1" id="showlists">
					<tr><th colspan='3'>Other Program Files</th>
					<th colspan='2'><a class="fancybox" href="#inline1" title="Add Other Program File" onclick="OpenPopUp('${program.id}');">Add File</a>
					</th></tr>
					<tr>
					<th>Program File Title</th>
					<th>Program File Document</th>
					<th>Added By</th>
					<th>Date Added</th>
					<th></th>
					</tr>
					<c:forEach var="p" items="${otherfiles}" varStatus="counter">
						<tr>
						<td>${p.pfTitle}</td>
						<td>${p.pfDoc}</td>
						<td>${p.addedBy}</td>
						<td>${p.dateAddedFormatted}</td>
						<td>
						<a class="small" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherProgramDocument.html?id=${p.id}&fid=${p.pfDoc}&pid=${p.programsId}'>Delete File</a>
		                                      
						</td>
						</tr>
					</c:forEach>
					</table>
				</td>
				</tr>
			
				
            </table> 
          </td>
        </tr>
      </table>
      <div id="inline1" style="width:400px;display: none;">
		
			<span class="headertitle">Add Other Program File</span>
			<table width="300px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">

				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Other Program File Title:
					</td>
					<td>
						<input type="text" class="requiredinput" id="other_program_title"  name="other_program_title" style="width:250px;" >
					</td>
				</tr>
				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Other Program File:
					</td>
					<td>
						<input type="file"  id="other_program_file" name="other_program_file"  class="requiredinput">
					</td>
				</tr>				
				
				<tr>
					<td colspan="2" valign="middle" align="center">
						<input type="button" value="Add File" onclick="sendprograminfo();"/>
						<input type="button" value="Cancel" onclick="closewindow();"/>

					</td>
				</tr>
		</table>
	</div> 
    </form><br/><a href='javascript:history.go(-1)' title="Back">Back</a><br/>
	</body>
</html>