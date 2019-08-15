<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.common.Utils,
                  com.awsd.school.*, 
                  com.awsd.school.bean.*,
                  com.awsd.school.dao.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.nlesd.school.bean.*,
                  com.nlesd.school.service.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<%
  Collection<SchoolZoneBean> zones = SchoolZoneService.getSchoolZoneBeans();
  School[] schools = null;
  ApplicantProfileBean profile = (ApplicantProfileBean)session.getAttribute("APPLICANT");  
  HashMap<Integer, School> sel = ApplicantSubPrefManager.getApplicantSubPrefsMap(profile);  
  HashMap<Integer, ApplicantSubListInfoBean> sublists  = ApplicantSubListInfoManager.getApplicantSubListInfoBeanMap(profile);
  int cntr=0;
%>

<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>

  <script type="text/javascript">
    function toggleRow(rid, state)
    {
      var row = document.getElementById(rid);
      row.style.display=state;
    }

    $('document').ready(function(){

			var sublist_applications = <%= sublists.size() %>;

		  if(sublist_applications < 1){
			  $('#pref_submit').attr('disabled', 'disabled');
			  $(':checkbox').attr('disabled', 'disabled');
			  $('#msgerr').css('display','block').html('<b>ERROR:</b> You HAVE NOT submitted an application for any substitute list in the <%=Utils.getCurrentSchoolYear()%> school year! <br/> You MUST apply for AT LEAST ONE substitute list before you can set your substitute preferences.').delay(15000).fadeOut();
			 
			 // document.location.href = 'http://www.nlesd.ca/employment/teachingpositions.jsp';
		  }
    });
  </script>
</head>
<body>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>APPLICANT SUBSTITUTE PREFERENCES</b></div>
      			 	<div class="panel-body"> 
                    <div class="table-responsive"> 
					Below schools are grouped by Region and Zone. 
					Click on a Zone header to open the list and select locations you prefer to Substitute at. 
					Headers will be red until you make a selection.
                                
                                <form id="frmPostJob" action="applicantSubPrefs.html" method="post">
                               
<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>                              
                                  
    <%Collection<RegionBean> regions = null;
                                    for(SchoolZoneBean zone : zones) {
                                     regions = RegionManager.getRegionBeans(zone);
                                    %>  
                                    
                                    <br/>
                                    <div style="font-weight:bold;font-size:16px;text-transform:Capitalize;"><%=zone.getZoneName()%> Region</div>
                                   <br/>
                                    <%if(regions == null || regions.size() <= 1) { %>
                                    
                                    <table id="jobsapp" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='30%'>SCHOOL/BUILDING/REGION NAME</th>
									        <th width='20%'>GRADES</th>
									        <th width='25%'>TOWN/CITY</th>
									        <th width='15%'>TELEPHONE</th>
									        <th width='10%'>OPTIONS</th>								        															       
									      </tr>
									    </thead>
									    <tbody> 
                                    
                                    	<%schools = SchoolDB.getSchools(zone).toArray(new School[0]);                                      		
                                      		int middle = (schools.length % 2 == 0) ? schools.length/2 : schools.length/2 + 1;
	                                              	
	                                              for(int j=0; j < middle; j++){%>
	                                             
	                                             
								
								                <tr <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"style='background-color:#eafaea;'":""%>>
	                                                  <td>	
	                                                  <c:set var="query" value="<%=schools[j].getSchoolName()%>"/>
	                                                  <c:choose>	
														<c:when test="${fn:containsIgnoreCase(query, 'former') 
														or fn:containsIgnoreCase(query, 'office') 
														or fn:containsIgnoreCase(query, 'burin') 
														or fn:containsIgnoreCase(query, 'vista') 														
														or fn:containsIgnoreCase(query, 'center')
														or fn:containsIgnoreCase(query, 'cabin')
														or fn:containsIgnoreCase(query, 'headquarters')		
														or fn:containsIgnoreCase(query, 'rowan')																						
														or fn:containsIgnoreCase(query, 'depot') }">												
														<span style='color:DimGrey;'><%=schools[j].getSchoolName()%></span>
														</c:when>
												<c:otherwise>	
	                                          <a href="/schools/schoolprofile.jsp?id=<%=schools[j].getSchoolID()%>"><%=schools[j].getSchoolName()%></a>   
	                            				 </c:otherwise>
	                             				</c:choose>
	                                               </td>
	                                                  <td><%=(schools[j].getLowestGrade().getAbbrev() !="NA" )?schools[j].getLowestGrade().getAbbrev()+" -":"<span style='color:Silver;'>N/A</span>" %> <%=(schools[j].getHighestGrade().getAbbrev()!="NA")?schools[j].getHighestGrade().getAbbrev():"" %></td>
		                                              <td><%=schools[j].getTownCity()!=null?schools[j].getTownCity():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                  <td><%=schools[j].getTelephone()!=null?schools[j].getTelephone():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                  <td><input type="checkbox" id="s_<%=schools[j].getSchoolID()%>" name="s_<%=schools[j].getSchoolID()%>" <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"CHECKED":""%>></td>
	                                                </tr>
	                                              <%}%>
	                                          
	                                         
	                                         
	                                        
	                                              <%for(int j=middle; j < schools.length; j++){%>
	                                               <tr <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"style='background-color:#eafaea;'":""%>>
	                                                  <td>
	                                                  <c:set var="query" value="<%=schools[j].getSchoolName()%>"/>
	                                                  <c:choose>	
														<c:when test="${fn:containsIgnoreCase(query, 'former') 
														or fn:containsIgnoreCase(query, 'office') 
														or fn:containsIgnoreCase(query, 'burin') 
														or fn:containsIgnoreCase(query, 'vista') 														
														or fn:containsIgnoreCase(query, 'center')
														or fn:containsIgnoreCase(query, 'cabin')
														or fn:containsIgnoreCase(query, 'headquarters')		
														or fn:containsIgnoreCase(query, 'rowan')																						
														or fn:containsIgnoreCase(query, 'depot') }">													
														<span style='color:DimGrey;'><%=schools[j].getSchoolName()%></span>
														</c:when>
												<c:otherwise>	
	                                          <a href="/schools/schoolprofile.jsp?id=<%=schools[j].getSchoolID()%>"><%=schools[j].getSchoolName()%></a>   
	                            				 </c:otherwise>
	                             				</c:choose></td>
	                                                  <td><%=(schools[j].getLowestGrade().getAbbrev() !="NA" )?schools[j].getLowestGrade().getAbbrev()+" -":"<span style='color:Silver;'>N/A</span>" %> <%=(schools[j].getHighestGrade().getAbbrev()!="NA")?schools[j].getHighestGrade().getAbbrev():"" %></td>
		                                              <td><%=schools[j].getTownCity()!=null?schools[j].getTownCity():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                  <td><%=schools[j].getTelephone()!=null?schools[j].getTelephone():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                  <td><input type="checkbox" id="s_<%=schools[j].getSchoolID()%>" name="s_<%=schools[j].getSchoolID()%>" <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"CHECKED":""%>></td>
	                                                </tr>
	                                              <%}%>
	                                       
	                                       </tbody>
	                                       </table>
	                                         
                                      <%}else{
                                    	  
                                      		for(RegionBean region : regions) { %>
                                      		
                                      		<div class="panel-group" id="accordion">
                                      		
                                      		<%  if(region.getName().contains("all")) continue;                                     			
                                      			
                                      			schools = SchoolDB.getSchools(region).toArray(new School[0]); 
                                      			cntr++;
                                      			%>
                                      			
                                      			
                                      			 
                                      	
										  <div class="panel panel-default" id="schoolSelectPanel<%=cntr%>">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse<%=cntr%>"><span style="text-transform:Capitalize;"><%=region.getName()%> Zone </span>(Selected: <span id="numZonesSelected<%=cntr%>">0</span>)</a>
										      </h4>
										    </div>
										    <div id="collapse<%=cntr%>" class="panel-collapse collapse">
										      <div class="panel-body">
										      <table id="jobsapp" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='30%'>SCHOOL NAME</th>
									        <th width='20%'>GRADES</th>
									        <th width='25%'>TOWN/CITY</th>
									        <th width='15%'>TELEPHONE</th>
									        <th width='10%'>OPTIONS</th>								        															       
									      </tr>
									    </thead>
									    <tbody> 
                                      	
                                      			<% int middle = (schools.length % 2 == 0) ? schools.length/2 : schools.length/2 + 1;
		                                              	for(int j=0; j < middle; j++){%>  
		                                             <tr <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"style='background-color:#eafaea;'":""%>>
		                                                  	<td>
		                                                  	<c:set var="query" value="<%=schools[j].getSchoolName()%>"/>
		                                                  	<c:choose>	
														<c:when test="${fn:containsIgnoreCase(query, 'former') 
														or fn:containsIgnoreCase(query, 'office') 
														or fn:containsIgnoreCase(query, 'burin') 
														or fn:containsIgnoreCase(query, 'vista') 														
														or fn:containsIgnoreCase(query, 'center')
														or fn:containsIgnoreCase(query, 'cabin')
														or fn:containsIgnoreCase(query, 'headquarters')		
														or fn:containsIgnoreCase(query, 'rowan')																						
														or fn:containsIgnoreCase(query, 'depot') }">													
														<span style='color:DimGrey;'><%=schools[j].getSchoolName()%></span>
														</c:when>
												<c:otherwise>	
	                                          <a href="/schools/schoolprofile.jsp?id=<%=schools[j].getSchoolID()%>"><%=schools[j].getSchoolName()%></a>   
	                            				 </c:otherwise>
	                             				</c:choose></td>
		                                                   	<td><%=(schools[j].getLowestGrade().getAbbrev() !="NA" )?schools[j].getLowestGrade().getAbbrev()+" -":"<span style='color:Silver;'>N/A</span>" %> <%=(schools[j].getHighestGrade().getAbbrev()!="NA")?schools[j].getHighestGrade().getAbbrev():"" %></td>
		                                                   	<td><%=schools[j].getTownCity()!=null?schools[j].getTownCity():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                 	  	<td><%=schools[j].getTelephone()!=null?schools[j].getTelephone():"<span style='color:Silver;'>N/A</span>"%></td>
		                                                  	<td><input type="checkbox" class="schoolL<%=cntr%>" id="s_<%=schools[j].getSchoolID()%>" name="s_<%=schools[j].getSchoolID()%>" <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"CHECKED":""%>></td>
		                                                </tr>
		                                              <%}%>
		                                           
		                                           
		                                           
		                                              <%for(int j=middle; j < schools.length; j++){%>
		                                                <tr <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"style='background-color:#eafaea;'":""%>>
		                                                  	<td>
		                                                  	<c:set var="query" value="<%=schools[j].getSchoolName()%>"/>
		                                                  	<c:choose>	
														<c:when test="${fn:containsIgnoreCase(query, 'former') 
														or fn:containsIgnoreCase(query, 'office') 
														or fn:containsIgnoreCase(query, 'burin') 
														or fn:containsIgnoreCase(query, 'vista') 														
														or fn:containsIgnoreCase(query, 'center')
														or fn:containsIgnoreCase(query, 'cabin')
														or fn:containsIgnoreCase(query, 'headquarters')		
														or fn:containsIgnoreCase(query, 'rowan')																						
														or fn:containsIgnoreCase(query, 'depot') }">												
														<span style='color:DimGrey;'><%=schools[j].getSchoolName()%></span>
														</c:when>
												<c:otherwise>	
	                                          <a href="/schools/schoolprofile.jsp?id=<%=schools[j].getSchoolID()%>"><%=schools[j].getSchoolName()%></a>   
	                            				 </c:otherwise>
	                             				</c:choose></td>
		                                                   	<td><%=(schools[j].getLowestGrade().getAbbrev() !="NA" )?schools[j].getLowestGrade().getAbbrev()+" -":"<span style='color:Silver;'>N/A</span>" %> <%=(schools[j].getHighestGrade().getAbbrev()!="NA")?schools[j].getHighestGrade().getAbbrev():"" %></td>
		                                                   	<td><%=schools[j].getTownCity()!=null?schools[j].getTownCity():"<span style='color:Silver;'>N/A</span>"%></td>
	                                                  		<td><%=schools[j].getTelephone()!=null?schools[j].getTelephone():"<span style='color:Silver;'>N/A</span>"%></td>
		                                                  	<td><input type="checkbox" class="schoolL<%=cntr%>" id="s_<%=schools[j].getSchoolID()%>" name="s_<%=schools[j].getSchoolID()%>" <%=sel.containsKey(new Integer(schools[j].getSchoolID()))?"CHECKED":""%>></td>
		                                                </tr>
		                                              <%}%>
		                                 
		                                 
		                                 </tbody>
		                                 </table> 
		                                 
		                                 </div>
										    </div>
										  </div>
  											
  											<script language="JavaScript">
  
  											$('document').ready(function(){
  											
  											var degNum = $(".schoolL<%=cntr%>:checked").length;
  											if (degNum == 0) {			
  												$("#schoolSelectPanel<%=cntr%>").removeClass("panel-default").addClass("panel-danger");
  											} else {				
  												$("#schoolSelectPanel<%=cntr%>").removeClass("panel-danger").addClass("panel-success");
  											}
  											$("#numZonesSelected<%=cntr%>").html(degNum);
  											
  											$(".schoolL<%=cntr%>").click(function(){
  												$("#numZonesSelected<%=cntr%>").html($('.schoolL<%=cntr%>:checked').length);
  												if ($('.schoolL<%=cntr%>:checked').length == 0) {			
  													$("#schoolSelectPanel<%=cntr%>").removeClass("panel-default").removeClass("panel-success").addClass("panel-danger");
  												} else {				
  													$("#schoolSelectPanel<%=cntr%>").removeClass("panel-danger").addClass("panel-success");
  												}
  											});
  											
  										});	
  											</script>
                                      	<%}%>
                                      	
                                      	</div>	
                                      	
                                     	<%}%>
                                    <%}%>
                                   <div align="center"  class="no-print">
                                        <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  <a class="btn btn-xs btn-primary" href="view_applicant.jsp">Your Profile</a> <input id='pref_submit' class="btn btn-xs btn-success" type="submit" value="Save Preferences"/>
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
