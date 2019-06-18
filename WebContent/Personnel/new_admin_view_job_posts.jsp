<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
         				java.util.*, 
          				java.text.*, 
          				com.awsd.pdreg.*,
          				com.awsd.security.*,
          				com.nlesd.school.bean.*,
          				com.nlesd.school.service.*,
          				org.apache.commons.lang.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	int zoneid = 0;

	try {
		if(StringUtils.isNotBlank(request.getParameter("zoneid"))) {
			zoneid = Integer.parseInt(request.getParameter("zoneid").toString());
		}
	}
	catch(NumberFormatException e) {
		zoneid = 0;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<script language="JavaScript" src="js/CalendarPopup.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript">
  function toggleTableRowHighlight(target, color)
  {
    var rowSelected = document.getElementById(target);
  
    rowSelected.style.backgroundColor=color; 
  }
  
  function toggle_row(id)
  {
    v_row = document.getElementById(id);
    img = document.getElementById(id + "_img");
        
    if(v_row)
    {
      cur = v_row.style.display;
          
      if((cur == null) || (cur == 'none'))
      {
        v_row.style.display = 'inline';
            
        if(img)
          img.src = "/MemberServices/Personnel/v2.0/images/collapse.jpg";
      }
      else
      {
        v_row.style.display = 'none';
            
        if(img)
          img.src = "/MemberServices/Personnel/v2.0/images/expand.jpg";
      }
    }
  }
  	$( document ).ready(function() {
  		$('.btnRegionView').click(function(){
  			self.location.href = 'admin_view_job_posts.jsp?status=' + $('#status').val() + '&zoneid=' + $(this).attr('data-region-id');
  		});
  		//$('#regionized-view').buttonset();
  		$('.btnRegionView').each(function(){
  			if($(this).attr('data-region-id') == $('#zoneid').val()){
   				$(this).css({'color': 'red', 'font-weight' : 'bold'});
   			}
 		});
  	
  });
</script>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
	<form action='managePosts.html' method="post">
	<input type="hidden" name="op" value="AWARD" />
	<input type="hidden" name="status" id="status" value="<%=request.getParameter("status")%>" />
	<input type="hidden" name="zoneid" id="zoneid" value="<%=zoneid%>" />
  <table class="all_content" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true">
        	<jsp:param name='showHead' value='false' />
        </jsp:include>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="admin_side_nav.jsp" flush="true"/>
                  <td width="551" align="left" valign="top">		
                    <table width="551" cellpadding="0" cellspacing="0" border="0">
						<tr style="padding-bottom:10px;">
                        <td width="551" align="left" valign="top" style="padding-top:8px;">
                          <table width="551" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle" colspan="2"><%=request.getParameter("status")%> Job Opportunities</td>
                            </tr>
                            <tr>
                    			<td colspan='2' align="center">
	                    			<div style='float:left; vertical-align:middle;'>
	                      				<fieldset style='border: none;'>
		                      				<div id='regionized-view'>
			                      				<button class='btnRegionView' type='button' data-region-id='0'>All Regions</button>
			                      					<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ 
			                      						if(sz.getZoneId() == 5) continue; //NLESD - PROVINCIAL
			                      					%>
			                      				<button class='btnRegionView' type='button' data-region-id="<%= sz.getZoneId() %>"><%= StringUtils.capitalize(sz.getZoneName())%></button>
			                      					<%}%>
		                      				</div>
	                      				</fieldset>
	                      			</div>
                    			</td>
                    		</tr>
                            
                            <%for(int i = 0; i < JobTypeConstant.ALL.length; i++){
                              if(JobTypeConstant.ALL[i].equals(JobTypeConstant.AWARDED))
                                continue;%>
                              <tr style="padding-top:10px;padding-bottom:3px;">
                                <td colspan="2">
                                  <a href="javascript:toggle_row('TYPE<%=JobTypeConstant.ALL[i].getValue()%>');" class="categoryLink"><img id="TYPE<%=JobTypeConstant.ALL[i].getValue()%>_img" src="/MemberServices/Personnel/v2.0/images/collapse.jpg" border="0">&nbsp;<%=JobTypeConstant.ALL[i].getDescription()%></a>
                                </td>
                              </tr>
                              <tr id="TYPE<%=JobTypeConstant.ALL[i].getValue()%>" style="display:inline;">
                                <td width="5">&nbsp;</td>
                                <td width="546" style="padding-left:10px;border-left:solid 1px #c0c0c0;padding-bottom:3px;">                                  
                                  <job:JobPosts status='<%=request.getParameter("status")%>' type='<%=JobTypeConstant.ALL[i].getValue()%>' zone='<%=zoneid %>'/>  
                                </td>
                              </tr>
                            <%}%>
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
    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-AWARD-MULTIPLE">
	    <tr>
	    	<td align="right"><input type="submit" value="Award Selected" /></td>
	    </tr>
    </esd:SecurityAccessRequired>
  </table>
  </form>
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
