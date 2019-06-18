<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.nlesd.school.bean.*,
                 com.nlesd.school.service.*" 
         isThreadSafe="false" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<c:set var='prefs' value='<%= ApplicantRegionalPreferenceManager
		.getApplicantRegionalPreferencesMap((ApplicantProfileBean)session.getAttribute("APPLICANT")) %>' />

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;color:DimGrey;}

input {border:1px solid silver;}

</style>
		
		<script type="text/javascript">
			$(function(){
				$('.zone-select-all').click(function(){
					$('input:not(.zone-select-all)[type=checkbox][zoneid='+ $(this).attr('zoneid') + ']').prop('checked', $(this).is(':checked'));
				})
			});
		</script>
	</head>
	
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">9</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 9: Editing your Teacher/Educator HR Application Profile 
</div>

<br/>Regional Preferences apply to the Pool Positions. Please Select the regions you prefer to work in.

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>9. REGIONAL PREFERENCES</b></div>
      			 	<div class="panel-body"> 
      			 	
					<div class="table-responsive"> 
			              <form id="frmPostJob" action="applicantRegistration.html?step=9" method="post">
	         
	         				<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
                                <c:set var='zones' value='<%= SchoolZoneService.getSchoolZoneBeans() %>' />
                                                              
	         					<c:forEach items='${ zones }' var='zone'>
	         					<tr> 
						          		<td class="tableTitle"><div style="text-transform:Capitalize;">
						          		${ zone.zoneName } Region
						          		</div></td>
						          		<td class="tableResult">	          			
	          								<c:set var='regions' value='<%= RegionManager.getRegionBeans((SchoolZoneBean)pageContext.getAttribute("zone")) %>' />
		            							<c:forEach items="${regions}" var="region" varStatus='status'>
		            							
		                       						<label class="checkbox-inline"><input type="checkbox" name="region_id" id="region_id" class="${fn:contains(region.name, 'all')? ' zone-select-all' : '' }" zoneid="${ zone.zoneId }"
		                							value="${region.id}" <%= ((HashMap<Integer, RegionBean>)pageContext.getAttribute("prefs")).containsKey(new Integer(((RegionBean)pageContext.getAttribute("region")).getId()))?"CHECKED":"" %> />
		              								<span style="text-transform:Capitalize;">
		              								<c:choose>
		              								<c:when test="${ region.name eq 'all eastern region' or region.name eq 'all avalon region' }">
						          					All Avalon
						          					</c:when>
		              								<c:when test="${ region.name eq 'all central region' }">
						          					All Central
						          					</c:when>
						          					<c:when test="${ region.name eq 'all western region' }">
						          					All Western
						          					</c:when>
						          					<c:when test="${ region.name eq 'all labrador region' }">
						          					All Labrador
						          					</c:when>
		              								<c:when test="${region.name eq 'nlesd - provincal'}">Provincial</c:when>
		              								<c:otherwise>${region.name}</c:otherwise>
		              								</c:choose>
		              								
		              								</span></label>
		              								</div>
				              					</c:forEach>
		            					</td>
		            					</tr>
	            				</c:forEach>
	           					
	           					</tbody>
	           				</table>
	           				 <div align="center">
	           				 <a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>
				                <input type="submit" value="Save/Update" class="btn btn-xs btn-success">				               
	             			</div>
	           
	        </form>
				
</div></div></div></div>

<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>

		
	</body>
	
</html>
