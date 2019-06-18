<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.nlesd.school.bean.*,
                 com.nlesd.school.service.*,
                 com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false" %>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	HashMap<Integer, ApplicantRegionalJobPoolSSBean> hmap = new HashMap<Integer, ApplicantRegionalJobPoolSSBean>();
	if(profile != null){
		hmap = ApplicantRegionalJobPoolSSManager.getApplicantRegionalJobPoolPreferencesMap(profile.getSIN());
	}
	
%>
<c:set var='prefs' value='<%= ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap((ApplicantProfileBean)session.getAttribute("APPLICANT")) %>' />

<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<style>
	.tableTitle {font-weight:bold;width:20%;}
	.tableResult {font-weight:normal;width:80%;}
	.tableTitleL {font-weight:bold;width:20%;}
	.tableResultL {font-weight:normal;width:30%;}
	.tableTitleR {font-weight:bold;width:20%;}
	.tableResultR {font-weight:normal;width:30%;}
	input { border:1px solid silver;}
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
		
		Step 7: Pool
		
						If you wish to have your application placed in a pool for future openings, 
						<br />please indicate by checking regions and positions your prefer to work in.
					      
	        <form id="frmPostJob" action="applicantRegistrationSS.html?step=7" method="post">
	         
	    Position(s)
	    <input type="checkbox" name="custodian" id="custodian" <%=hmap.containsKey(ApplicantPoolJobSSConstant.CUSTODIAN.getValue()) ? "CHECKED" : "" %>/>Custodian
		<input type="checkbox" name="carpenter" id="carpenter" <%=hmap.containsKey(ApplicantPoolJobSSConstant.CARPENTER.getValue()) ? "CHECKED" : "" %>/> Trades - Carpenter (Journeyperson)
		<input type="checkbox" name="cleaner" id="cleaner" <%=hmap.containsKey(ApplicantPoolJobSSConstant.CLEANER.getValue()) ? "CHECKED" : "" %>/> Cleaner
		<input type="checkbox" name="electrician" id="electrician" <%=hmap.containsKey(ApplicantPoolJobSSConstant.ELECTRICIAN.getValue()) ? "CHECKED" : "" %>/> Trades - Electrician (Journeyperson)
		<input type="checkbox" name="secretary" id="secretary" <%=hmap.containsKey(ApplicantPoolJobSSConstant.SCHOOL_SECRETARY.getValue()) ? "CHECKED" : "" %>/> School Secretary
		<input type="checkbox" name="officeadm" id="officeadm" <%=hmap.containsKey(ApplicantPoolJobSSConstant.OFFICE_ADMIN.getValue()) ? "CHECKED" : "" %>/> Office Administration
		<input type="checkbox" name="studentassistant" id="studentassistant" <%=hmap.containsKey(ApplicantPoolJobSSConstant.STUDENT_ASSISTANT.getValue()) ? "CHECKED" : "" %>/> Student Assistant
		<input type="checkbox" name="othertrades" id="othertrades" <%=hmap.containsKey(ApplicantPoolJobSSConstant.OTHER.getValue()) ? "CHECKED" : "" %>/> Trades - Other
		
		
		
		
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
		
		
		
		
		
		
	          
	             
	                <input type="submit" value="Update">&nbsp;&nbsp;<input type="button" value="Continue" onclick="document.location.href='applicant_registration_step_8_ss.jsp'">
	            
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
