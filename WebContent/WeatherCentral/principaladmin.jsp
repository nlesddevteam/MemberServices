<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         isThreadSafe="false"
         import="java.sql.*,
                 java.util.*,
                 java.util.regex.*,
                 java.text.*,
                 java.io.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.security.*,com.awsd.school.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW,WEATHERCENTRAL-GLOBAL-ADMIN" />


<%
	User usr = (User) session.getAttribute("usr");
	SchoolSystem[] systems = null;
	SchoolSystem sys = null;
	School school = null;
	Iterator<School> sch_iter = null;
	Iterator<ClosureStatus> stat_iter = null;
	ClosureStatuses stats = null;
	ClosureStatus stat = null;
	ClosureStatus sstat = null;
	Personnel prec = null;
	int cnt;

  if(request.getParameter("pid") != null)
    prec = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("pid")));
  else
    prec = usr.getPersonnel();
    
  systems = (SchoolSystem[]) request.getAttribute("SchoolSystems");
    
  stats = new ClosureStatuses();
  cnt = 0;
  
  String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title></title>
<link rel="stylesheet" href="css/redmond/jquery-ui.min.css"  />
<style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
<style type="text/css">@import "css/weathercentral.css";</style>
<script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
<script type='text/javascript' src='js/jquery-ui.min.js'></script>

<script type='text/javascript'>
	function toggle(target) {
		$('#' + target).toggle();
	}
		
  $(function(){
	  $('.txt-status-note').keypress(function(event){
		  var keycode = event.which;
		  
		  if ((keycode == 8)||(keycode == 13)||(keycode == 39)||(keycode == 46)) {
			  return true;
	    }
	    else if($(this).val().length >= 150) {
		  	alert('Note cannot exceed 150 characters.');
		  	event.preventDefault();
	    }
	    else{
	    	return true;
	    }
	  });
	  
	  $('.datefield').datepicker({
		  dateFormat: "dd/mm/yy"
	  });
  });
</script>
</head>
<body>
  <form name="schoolstatus" method="post" action="updateSchoolClosureStatus.html">
  <input type="hidden" name="apply_all" value="0">
  <%if(request.getParameter("pid") != null){%>
    <input type="hidden" name="pid" value="<%=prec.getPersonnelID()%>">
  <%}%>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >	
		<tr>
			<td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td align="left" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">School Status Central</span><BR />
						</td>
						<td width="50" align="right" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1">
						</td>
					</tr>											
				</table>										
			</td>
		</tr>		
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">				
				<table width="60%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;" align="center">
					<tr>
						<td width="100%" align="left" valign="top">
							<span class="boldBlack11pxTitle">School Status Central Admin</span><BR />
							<hr size="1" width="100%">
              <%if((systems != null) && (systems.length > 0)){%>
                <%for(int i=0; i < systems.length; i++){%>
  							<span class="boldBlack10pxTitle">System Name:</span>&nbsp;<span class="normalBlack10pxText"><%=systems[i].getSchoolSystemName()%></span><BR />
  							<span class="boldBlack10pxTitle">Current Admin:</span>&nbsp;<span class="normalBlack10pxText"><%=(systems[i].getSchoolSystemAdmin() != null)?systems[i].getSchoolSystemAdmin().getFullNameReverse():"UNASSIGNED"%>
  							<%
									Personnel[] tmp = systems[i].getSchoolSystemAdminBackup();
									for(int k=0; ((tmp != null)&&( k < tmp.length));k++) {
										if(tmp[k] != null)
											out.println("/" + tmp[k].getFullNameReverse());
								}
  							%>
  							</span><BR /><BR />

                <span class="boldBlack11pxLower">Schools</span><BR />
                <hr size="1" width="60%">
                <%
                  sch_iter = systems[i].getSchoolSystemSchools().iterator();
                  
                  while(sch_iter.hasNext()){
                  school = (School)sch_iter.next();
                  sstat = school.getSchoolClosureStatus();
                %>
                  <!--<img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="60%" height="1">-->
                  <div style="padding:5 5 5 5;background-color:#ffffcc;border:solid 1px #cccccc;border-left:solid 10px #cccccc; width:60%;">
                    <table width="100%" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="boldBlack11pxLower"><%=school.getSchoolName()%></td>
                        <%=(cnt++==0)?"<td align='right' valign='middle'><a href='javascript:document.schoolstatus.apply_all.value=\"" + systems[i].getSchoolSystemID() + "\";document.schoolstatus.submit();' class='11pxBlueLink'><img src='images/apply_all_01.gif' border='0' onmouseover='src=\"images/apply_all_02.gif\";' onmouseout='src=\"images/apply_all_01.gif\";'><BR /></a></td>":""%>
                      </tr>
                    </table>
                  </div>
                  <!--<img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="60%" height="2"><BR />-->
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="8"><BR />
                  <span class="boldBlack10pxTitle">Current Status:</span>&nbsp;&nbsp;<span class="<%=SchoolClosureStatusWorker.cssClass(sstat.getClosureStatusID())%>"><%=((!(school.getSchoolName().endsWith("Office")))?sstat.getClosureStatusDescription():Pattern.compile("School").matcher(sstat.getClosureStatusDescription()).replaceAll("Office"))%></span><BR />
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR />
                  <label class="boldBlack10pxTitle" for="<%="WEATHER_RELATED_" + school.getSchoolID()%>">Is this weather related?</label>
                  <input type="checkbox" id="<%="WEATHER_RELATED_" + school.getSchoolID()%>" 
                  	name="<%="WEATHER_RELATED_" + school.getSchoolID()%>"
                  	onclick="this.checked ? $('#weather-related-closure-panel-<%=school.getSchoolID()%>').show() : $('#weather-related-closure-panel-<%=school.getSchoolID()%>').hide()"
                  	<%= sstat.isWeatherRelated() ? " CHECKED" : "" %>
                  	/>
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR /><BR />
                  <span class="boldBlack10pxTitle">New Status:</span>&nbsp;&nbsp;
                    <select class="selectBox" name="<%="SCH_" + school.getSchoolID()%>">
                    <%stat_iter = null;
                      stat_iter = stats.iterator();
                      while(stat_iter.hasNext()){
                        stat = (ClosureStatus) stat_iter.next(); 
                    %>  <option value="<%=stat.getClosureStatusID()%>" <%=(sstat.getClosureStatusID()==stat.getClosureStatusID())?"SELECTED":""%>><%=((!(school.getSchoolName().endsWith("Office")))?stat.getClosureStatusDescription():Pattern.compile("School").matcher(stat.getClosureStatusDescription()).replaceAll("Office"))%></option>
                    <%}%>
                    </select><BR /> 
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR /> 
                  <table cellpadding="0" cellspacing="0" align="left">
                    <tr>
                      <td width="65" align="left" style="padding-right:5px;" class="boldBlack10pxTitle">Notice Date:</td>
                      <td width="*" align="left">
                        <table width="100%" cellpadding="0" cellspacing="0" style="padding:0px;" align="left">
                          <tr style="height:18px;">
                            <td width="15%"><input class="requiredinput_date datefield" type="text" name="<%="START_DATE_" + school.getSchoolID()%>" style="width:75px;padding-left:5px;font-size:11px;line-height:14px;" value="<%=today%>" readonly="readonly"/></td>
                            <td width="15%" align="left">
                              <img class="requiredinput_popup_cal" src="images/cal_popup_02.gif" alt="choose date" />
                            </td>
                            <td width="175" align="right" style="padding-right:5px;" class="boldBlack10pxTitle">Repeat daily until event day?</td>
                            <td width="*"><input type="checkbox" name="<%="REPEAT_" + school.getSchoolID()%>"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table><BR />
                  <div id='weather-related-closure-panel-<%=school.getSchoolID()%>' style='display:<%=sstat.isWeatherRelated() ? "inline" : "none" %>;'>
	                  <BR /><BR />
	                  <label class="boldBlack10pxTitle" for="<%="RATIONALE_" + school.getSchoolID()%>">Weather Related Rationale (internal use only):</label><br />
	                  <textarea class='txt-status-note' id="<%="RATIONALE_" + school.getSchoolID()%>" name="<%="RATIONALE_" + school.getSchoolID()%>" rows="3" cols="50" ><%=(sstat.getRationale()!=null)?sstat.getRationale():""%></textarea>
	                  <BR /><BR />
                  </div>
                  <div>
	                  <BR /><BR />
	                  <label class="boldBlack10pxTitle" for="<%="NOTE_" + school.getSchoolID()%>">Note(optional, to be posted to public website.):</label><br />
	                  <textarea name="<%="NOTE_" + school.getSchoolID()%>" rows="3" cols="50" onKeyPress="return submitenter(this,event);"><%=(sstat.getSchoolClosureNote()!=null)?sstat.getSchoolClosureNote():""%></textarea>
	                  <BR /><BR /><img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="100%" height="5"><BR />
                  </div>
                  <BR /><BR />
                <%}%>
                <img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="100%" height="5"><BR />
                <%cnt=0;%>
              <%}%>
              <%} else {
                sys = (SchoolSystem) prec.getSchool().getSchoolSystem();
                school = prec.getSchool();
                sstat = school.getSchoolClosureStatus();%>
                <span class="boldBlack10pxTitle">System Name:</span>&nbsp;<span class="normalBlack10pxText"><%=sys.getSchoolSystemName()%></span><BR />
  							<span class="boldBlack10pxTitle">Current Admin:</span>&nbsp;<span class="normalBlack10pxText"><%=(sys.getSchoolSystemAdmin() != null)?sys.getSchoolSystemAdmin().getFullNameReverse():"UNASSIGNED"%>
  							<% 
  								Personnel[] tmp = sys.getSchoolSystemAdminBackup();
  								for(int i=0; ((tmp != null)&&( i < tmp.length));i++)
  									if(tmp[i] != null)
  										out.println("/" + tmp[i].getFullNameReverse());
  							%>
  							</span><BR /><BR />

                <span class="boldBlack10pxTitle"><%=(!(school.getSchoolName().endsWith("Office")))?"School":"Office"%> Name:</span>&nbsp;<span class="normalBlack10pxText"><%=school.getSchoolName()%></span><BR />
                  <img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="100%" height="1"><BR />
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="8"><BR />
                  <span class="boldBlack10pxTitle">Current Status:</span>&nbsp;&nbsp;<span class="<%=SchoolClosureStatusWorker.cssClass(sstat.getClosureStatusID())%>"><%=((!(school.getSchoolName().endsWith("Office")))?sstat.getClosureStatusDescription():Pattern.compile("School").matcher(sstat.getClosureStatusDescription()).replaceAll("Office"))%></span><BR />
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR />
                  <label class="boldBlack10pxTitle" for="<%="WEATHER_RELATED_" + school.getSchoolID()%>">Is this weather related?</label>
                  <input type="checkbox" id="<%="WEATHER_RELATED_" + school.getSchoolID()%>" 
                  	name="<%="WEATHER_RELATED_" + school.getSchoolID()%>"
                  	onclick="this.checked ? $('#weather-related-closure-panel-<%=school.getSchoolID()%>').show() : $('#weather-related-closure-panel-<%=school.getSchoolID()%>').hide()"
                  	<%= sstat.isWeatherRelated() ? " CHECKED" : "" %>
                  	/>
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR /><BR />
                  <span class="boldBlack10pxTitle">New Status:</span>&nbsp;&nbsp;
                    <select class="selectBox" name="<%="SCH_" + school.getSchoolID()%>">
                    <%stat_iter = stats.iterator();
                      while(stat_iter.hasNext()){
                        stat = (ClosureStatus) stat_iter.next();
                    %>  <option value="<%=stat.getClosureStatusID()%>" <%=(sstat.getClosureStatusID()==stat.getClosureStatusID())?"SELECTED":""%>><%=((!(school.getSchoolName().endsWith("Office")))?stat.getClosureStatusDescription():Pattern.compile("School").matcher(stat.getClosureStatusDescription()).replaceAll("Office"))%></option>
                    <%}%>
                    </select><BR />
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR /> 
                  <table cellpadding="0" cellspacing="0" align="left">
                    <tr>
                      <td width="65" align="left" style="padding-right:5px;" class="boldBlack10pxTitle">Notice Date:</td>
                      <td width="*" align="left">
                        <table width="100%" cellpadding="0" cellspacing="0" style="padding:0px;" align="left">
                          <tr style="height:18px;">
                            <td width="15%"><input class="datefield requiredinput_date" type="text" name="<%="START_DATE_" + school.getSchoolID()%>" style="width:75px;padding-left:5px;font-size:11px;line-height:14px;" value="<%=today%>" readonly="readonly"></td>
                            <td width="15%" align="left">
                              <img class="requiredinput_popup_cal" src="images/cal_popup_02.gif" alt="choose date" />
                            </td>
                            <td width="175" align="right" style="padding-right:5px;" class="boldBlack10pxTitle">Repeat daily until notice date?</td>
                            <td width="*"><input type="checkbox" name="<%="REPEAT_" + school.getSchoolID()%>"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table><BR />
                  <div id='weather-related-closure-panel-<%=school.getSchoolID()%>' style='display:<%=sstat.isWeatherRelated() ? "inline" : "none" %>;'>
	                  <BR /><BR />
	                  <label class="boldBlack10pxTitle" for="<%="RATIONALE_" + school.getSchoolID()%>">Weather Related Rationale (internal use only):</label><br />
	                  <textarea class='txt-status-note' id="<%="RATIONALE_" + school.getSchoolID()%>" name="<%="RATIONALE_" + school.getSchoolID()%>" rows="3" cols="50" ><%=(sstat.getRationale()!=null)?sstat.getRationale():""%></textarea>
	                  <BR /><BR />
                  </div>
                  <div>
	                  <BR /><BR />
	                  <label class="boldBlack10pxTitle" for="<%="NOTE_" + school.getSchoolID()%>">Note(optional, to be posted to public website.):</label><br />
	                  <textarea class='txt-status-note' id="<%="NOTE_" + school.getSchoolID()%>" name="<%="NOTE_" + school.getSchoolID()%>" rows="3" cols="50" ><%=(sstat.getSchoolClosureNote()!=null)?sstat.getSchoolClosureNote():""%></textarea>
	                  <BR /><BR /><img src="/MemberServices/MemberAdmin/images/cccccc_line.gif" width="100%" height="5"><BR />
                  </div>
              <%}%>
              
						</td>
					</tr>
					<tr>
					<td width="100%" align="left" valign="middle">
						<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0"
                 onClick="document.schoolstatus.submit();"
                 onMousedown="src='/MemberServices/MemberAdmin/images/ok_button_02.gif';" 
                 onMouseup="src='/MemberServices/MemberAdmin/images/ok_button_01.gif';">&nbsp;&nbsp;
            <img src="/MemberServices/MemberAdmin/images/cancel_button_01.gif" border="0" 
                 onClick="document.location='../home.html';" 
                 onMousedown="src='/MemberServices/MemberAdmin/images/cancel_button_02.gif';" 
                 onMouseup="src='/MemberServices/MemberAdmin/images/cancel_button_01.gif';"><BR /><BR />
					</td>
				</tr>
				</table>
			</td>
		</tr>					
	</table>
	</form>
</body>

</html>
