<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*"%>
                 
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
	<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

  <script language="JavaScript">
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
              img.src = "images/collapse.jpg";
          }
          else
          {
            v_row.style.display = 'none';
            
            if(img)
              img.src = "images/expand.jpg";
          }
        }
      }
  </script>
  				<td valign="top" class='noprint'>
  					<table width= "100%" cellspacing="0" cellpadding="0" border="0" style="padding-bottom:10px;">
  						<tr>
                  <td width="10" align="left" valign="top">
                    <img src="<c:url value='/Personnel/images/spacer.gif' />" width="10" height="1" /><BR />
                  </td> 
                  <td width="178" align="left" valign="top">
                    <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="20" /><BR />
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="3" height="1" /><span class="displayBoxTitle">Quick Links</span>
                        </td>
                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
                          <img src="<c:url value='/Personnel/images/help_icon.gif' />" style="cursor: help;" alt="The options below will change depending on which features you use most often." /><img src="<c:url value='/Personnel/images/spacer.gif' />" width="2" height="1" /><img src="<c:url value='/Personnel/images/minimize_icon.gif' />" /><img src="<c:url value='/Personnel/images/spacer.gif' />" width="2" height="1" /><img src="<c:url value='/Personnel/images/close_icon.gif' />" /><img src="<c:url value='/Personnel/images/spacer.gif' />" width="2" height="1" /><BR />
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="left" valign="top" style="background-color: #FFFFFF;" colspan="2">
                        
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                          <a href="<c:url value='/Personnel/admin_index.jsp' />" class="homeSideNavCategoryLink" style='padding-left:5px;'>&gt;&nbsp;Home</a><BR />
                          
                          <esd:SecurityAccessRequired roles="ADMINISTRATOR">
                            &nbsp;<a href="javascript:toggle_row('admin_operations');" class="homeSideNavCategoryLink"><img id="admin_operations_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Admin Operations</a><BR />
                            <div id="admin_operations" style="display:inline;">
                              <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:13px;">
                              	<tr><td><a href="<c:url value='/Personnel/admin/viewSubjectGroups.html' />" class="homeSideNavLink">&gt;&nbsp;Subject Groups</a></td></tr>
                              </table>
                            </div>
                            <!-- Global Spacer for Alignment -->
                          	<img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          	<!-- Global Spacer for Alignment -->
                          </esd:SecurityAccessRequired>
                          
                          <esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-RELOAD-TABLES">
                            &nbsp;<a href="javascript:toggle_row('sync_manager');" class="homeSideNavCategoryLink"><img id="sync_manager_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Sync Manager</a><BR />
                            <div id="sync_manager" style="display:inline;">
                              <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:13px;">
                              	<tr><td><a href="<c:url value='/Personnel/startSync.html' />" class="homeSideNavLink">&gt;&nbsp;Start Sync</a></td></tr>
                              </table>
                            </div>
                            <!-- Global Spacer for Alignment -->
                          	<img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          	<!-- Global Spacer for Alignment -->
                          </esd:SecurityAccessRequired>

                          <esd:SecurityAccessRequired permissions="PERSONNEL-RECOGNITION-VIEW">
                            &nbsp;<a href="javascript:toggle_row('personnel_recognition');" class="homeSideNavCategoryLink"><img id="personnel_recognition_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Recognition Policy Program</a><BR />
                            <div id="personnel_recognition" style="display:inline;">
                              <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:13px;">
                              	<tr><td><a href="<c:url value='/Personnel/listRecognitionCategories.html' />" class="homeSideNavLink">&gt;&nbsp;Recognition Categories</a></td></tr>
                              	<tr><td><a href="<c:url value='/Personnel/addRecognitionNomination.html' />" class="homeSideNavLink">&gt;&nbsp;Add Nomination</a></td></tr>
                              </table>
                            </div>
                          </esd:SecurityAccessRequired>
                          
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                          
                          
                          <esd:SecurityAccessRequired permissions="PERSONNEL-EMP-OPPS-VIEW">
	                          &nbsp;<a href="javascript:toggle_row('emp_opps');" class="homeSideNavCategoryLink"><img id="emp_opps_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Employment Opportunities</a><BR />
	                          <div id="emp_opps" style="display:inline;">
	                            <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:13px;">
	                            	<esd:SecurityAccessRequired permissions="PERSONNEL-INTERVIEW-GUIDES-VIEW">
	                                <tr>
	                                  <td width="100%">
	                                    &nbsp;<a href="javascript:toggle_row('interview_guides');" class="homeSideNavCategoryLink"><img id="interview_guides_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Interview Guides</a><BR />
	                                    <div id="interview_guides" style="display:inline;">
	                                      <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
	                                      		<tr><td><a href="<c:url value='/Personnel/addInterviewGuide.html' />" class="homeSideNavLink">&gt;&nbsp;Add Guide</a></td></tr>
	                                          <tr><td><a href="<c:url value='/Personnel/listInterviewGuides.html?status=active' />" class="homeSideNavLink">&gt;&nbsp;View Active Guides</a></td></tr>
	                                      <tr><td><a href="<c:url value='/Personnel/listInterviewGuides.html?status=inactive' />" class="homeSideNavLink">&gt;&nbsp;View Inactive Guides</a></td></tr>
	                                      </table>
	                                    </div>
	                                  </td>
	                                </tr>
	                              </esd:SecurityAccessRequired>
	                              
	                            	<esd:SecurityAccessRequired permissions="PERSONNEL-POSITION-PLANNING-VIEW">
	                                <tr>
	                                  <td width="100%">
	                                    &nbsp;<a href="javascript:toggle_row('position_planning');" class="homeSideNavCategoryLink"><img id="position_planning_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Position Planning</a><BR />
	                                    <div id="position_planning" style="display:inline;">
	                                      <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
	                                          <tr><td><a href="<c:url value='/Personnel/viewPositionPlanning.html' />" class="homeSideNavLink">&gt;&nbsp;View</a></td></tr>
	                                      </table>
	                                    </div>
	                                  </td>
	                                </tr>
	                              </esd:SecurityAccessRequired>
	                              
	                              <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-VIEW">
	                                <tr>
	                                  <td width="100%">
	                                    &nbsp;<a href="javascript:toggle_row('ad_requests');" class="homeSideNavCategoryLink"><img id="ad_requests_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Advertisement Requests</a><BR />
	                                    <div id="ad_requests" style="display:inline;">
	                                      <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
	                                    	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                          <tr><td><a href="<c:url value='/Personnel/request_ad.jsp' />" class="homeSideNavLink">&gt;&nbsp;Request Advertisement</a></td></tr>
	                                        </esd:SecurityAccessRequired>
	                                        
	                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
	                                          <tr><td><a href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.SUBMITTED.getId()%>&issupport=N" class="homeSideNavLink">&gt;&nbsp;Pending Requests <span style="color:#FF0000;font-weight:bold;">(<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.SUBMITTED)%>)</span></a></td></tr>
	                                        </esd:SecurityAccessRequired>
	                                        
	                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST">
	                                          <tr><td><a href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.APPROVED.getId()%>&issupport=N" class="homeSideNavLink">&gt;&nbsp;Approved Requests <span style="color:#FF0000;font-weight:bold;">(<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.APPROVED)%>)</span></a></td></tr>
	                                        </esd:SecurityAccessRequired>
	                                        
	                                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">  
	                                          <tr><td><a href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.PREDISPLAYED.getId()%>&issupport=N" class="homeSideNavLink">&gt;&nbsp;Pre-Display Requests <span style="color:#FF0000;font-weight:bold;">(<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.PREDISPLAYED)%>)</span></a></td></tr>
	                                        </esd:SecurityAccessRequired>
	                                        </table>
	                                    </div>
	                                  </td>
	                                </tr>
	                              </esd:SecurityAccessRequired>
      		                              
	                              <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-JOBS">
		                              <tr>
		                                <td width="100%">
		                                  &nbsp;<a href="javascript:toggle_row('applicant_lists');" class="homeSideNavCategoryLink"><img id="applicant_lists_img" src="<c:url value='/Personnel/images/expand.jpg' />" border="0" /></a>&nbsp;<a href="<c:url value='/Personnel/admin_applicants_index.jsp' />" class="homeSideNavCategoryLink">Applicant Lists</a><BR />
		                                  <div id="applicant_lists" style="display:none;">
		                                    <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
		                                      
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDAdminReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add Administrator Reference</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDGuideReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add Guidance Reference</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDTeacherReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add Teacher Reference</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDExternalReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add External Reference</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDSupportReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add Support Staff Reference</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/addNLESDManageReference.html' />" class="homeSideNavLink" style='color:#FF0000;' target="_blank">&gt;&nbsp;Add Management Reference</a></td></tr>
		                                      		                                      		                                      
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=A' />" class="homeSideNavLink">&gt;&nbsp;A</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=B' />" class="homeSideNavLink">&gt;&nbsp;B</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=C' />" class="homeSideNavLink">&gt;&nbsp;C</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=D' />" class="homeSideNavLink">&gt;&nbsp;D</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=E' />" class="homeSideNavLink">&gt;&nbsp;E</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=F' />" class="homeSideNavLink">&gt;&nbsp;F</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=G' />" class="homeSideNavLink">&gt;&nbsp;G</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=H' />" class="homeSideNavLink">&gt;&nbsp;H</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=I' />" class="homeSideNavLink">&gt;&nbsp;I</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=J' />" class="homeSideNavLink">&gt;&nbsp;J</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=K' />" class="homeSideNavLink">&gt;&nbsp;K</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=L' />" class="homeSideNavLink">&gt;&nbsp;L</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=M' />" class="homeSideNavLink">&gt;&nbsp;M</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=N' />" class="homeSideNavLink">&gt;&nbsp;N</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=O' />" class="homeSideNavLink">&gt;&nbsp;O</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=P' />" class="homeSideNavLink">&gt;&nbsp;P</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=Q' />" class="homeSideNavLink">&gt;&nbsp;Q</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=R' />" class="homeSideNavLink">&gt;&nbsp;R</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=S' />" class="homeSideNavLink">&gt;&nbsp;S</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=T' />" class="homeSideNavLink">&gt;&nbsp;T</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=U' />" class="homeSideNavLink">&gt;&nbsp;U</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=V' />" class="homeSideNavLink">&gt;&nbsp;V</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=W' />" class="homeSideNavLink">&gt;&nbsp;W</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=X' />" class="homeSideNavLink">&gt;&nbsp;X</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=Y' />" class="homeSideNavLink">&gt;&nbsp;Y</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_applicant_list.jsp?surname_part=Z' />" class="homeSideNavLink">&gt;&nbsp;Z</a></td></tr>
		                                    </table>
		                                  </div>
		                                </td>
		                              </tr>
		                              <tr>
		                                <td width="100%">
		                                  &nbsp;<a href="javascript:toggle_row('education_positions');" class="homeSideNavCategoryLink"><img id="education_positions_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Educational Positions</a><BR />
		                                  <div id="education_positions" style="display:inline;">
		                                    <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
		                                    	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-STAFFING-STATS">
		                                        <tr><td><a href="<c:url value='/Personnel/admin/stats/admin_staffing_statistics.jsp' />" class="homeSideNavLink">&gt;&nbsp;View Staffing Statistics</a></td></tr>
		                                      </esd:SecurityAccessRequired>
		                                    	<esd:SecurityAccessRequired roles="ADMINISTRATOR">
		                                        <tr><td><a href="<c:url value='/Personnel/addSEOStaffingAssignment.html' />" class="homeSideNavLink">&gt;&nbsp;SEO Staffing Assignments</a></td></tr>
		                                      </esd:SecurityAccessRequired>
		                                      <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTJOB">
		                                      	<!-- 
		                                        <tr><td><a href="<c:url value='/Personnel/admin_post_job.jsp' />" class="homeSideNavLink">&gt;&nbsp;Post Job</a></td></tr>
		                                         -->
		                                      </esd:SecurityAccessRequired>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=All&zoneid=0' />" class="homeSideNavLink">&gt;&nbsp;View Posts</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=Open&zoneid=0' />" class="homeSideNavSubLink">Open</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=Unadvertised&zoneid=0' />" class="homeSideNavSubLink">Unadvertised</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=Closed&zoneid=0' />" class="homeSideNavSubLink">Closed</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=NoShortlist&zoneid=0' />" class="homeSideNavSubLink">Closed No Shortlist</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=Cancelled&zoneid=0' />" class="homeSideNavSubLink">Cancelled</a></td></tr>
		                                      <tr><td><a href="<c:url value='/Personnel/admin_view_job_posts.jsp?status=Awarded&zoneid=0' />" class="homeSideNavSubLink">Awarded</a></td></tr>
		                                      
		                                    </table>
		                                  </div>
		                                </td>
		                              </tr>
	                              </esd:SecurityAccessRequired>
				                  <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUPPORT-JOBS">  
	                                <tr>
	                                  <td width="100%">
	                                    &nbsp;<a href="javascript:toggle_row('support_staff');" class="homeSideNavCategoryLink"><img id="other_positions_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Support Staff </a><BR />
	                                    <div id="support_staff" style="display:inline;">
	                                   		<table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
	                                    	<tr><td>
	                                    	&nbsp;<a href="javascript:toggle_row('ss_rth');" class="homeSideNavCategoryLink"><img id="other_positions_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Request To Hire</a><BR />
	                                    		<div id="ss_rth" style="display:inline;">
	                                    		<table width="100%" cellpadding="0" cellspacing="0" style="padding-left:18px;">
				                              	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                          			<tr><td><a href="<c:url value='/Personnel/addRequestToHire.html' />" class="homeSideNavLink">&gt;&nbsp;Add New Request</a></td></tr>
	                                        	</esd:SecurityAccessRequired>
	                                        	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
	                                          		<tr><td><a href="<c:url value='/Personnel/adminViewRequestsToHire.html?status=0' />" class="homeSideNavLink">&gt;&nbsp;Pending Requests</a></td></tr>
	                                        	</esd:SecurityAccessRequired>
	                                        	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
	                                          		<tr><td><a href="<c:url value='/Personnel/adminViewRequestsToHire.html?status=5' />" class="homeSideNavLink">&gt;&nbsp;Approved Requests</a></td></tr>
	                                        	</esd:SecurityAccessRequired>
												<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
	                                          		<tr><td><a href="<c:url value='/Personnel/adminViewRequestsToHire.html?status=7' />" class="homeSideNavLink">&gt;&nbsp;Rejected Requests</a></td></tr>
	                                        	</esd:SecurityAccessRequired>
	                                        	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
	                                          		<tr><td><a href="<c:url value='/Personnel/adminViewRequestsToHire.html?status=6' />" class="homeSideNavLink">&gt;&nbsp;Competition Posted</a></td></tr>
	                                        	</esd:SecurityAccessRequired>	                                        		                                        		 	                                        		                                        	
				                              </table>
	                                    		</div>
	                                    		&nbsp;<a href="javascript:toggle_row('ss_positions);" class="homeSideNavCategoryLink"><img id="other_positions_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;View Posts</a><BR />
	                                    		<div id="ss_positions" style="display:inline;">
													<table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
	                                      					
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=Open&zoneid=0' />" class="homeSideNavSubLink">Open</a></td></tr>
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=Unadvertised&zoneid=0' />" class="homeSideNavSubLink">Unadvertised</a></td></tr>
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=Closed&zoneid=0' />" class="homeSideNavSubLink">Closed</a></td></tr>
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=NoShortlist&zoneid=0' />" class="homeSideNavSubLink">Closed No Shortlist</a></td></tr>
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=Cancelled&zoneid=0' />" class="homeSideNavSubLink">Cancelled</a></td></tr>
		                                      				<tr><td><a href="<c:url value='/Personnel/admin_view_job_posts_other.jsp?status=Awarded&zoneid=0' />" class="homeSideNavSubLink">Awarded</a></td></tr>
	                                      			</table>
	                                    		
	                                    		</div>
	                                    		</td></tr>
	                                    		</table>
	                                    		
	                                    </div>
	                                    </td>
	                                    </tr>
	                              </esd:SecurityAccessRequired>

	                              <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUBLISTS">
		                              <tr>
		                                <td width="100%">
		                                  &nbsp;<a href="javascript:toggle_row('substitute_lists');" class="homeSideNavCategoryLink"><img id="substitute_lists_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Substitute Lists</a><BR />
		                                  <div id="substitute_lists" style="display:inline;">
		                                    <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
		                                    	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-CREATE-SUBLIST">
		                                      	<tr><td><a href="<c:url value='/Personnel/admin_create_sub_list.jsp' />" class="homeSideNavLink">&gt;&nbsp;Create List</a></td></tr>
		                                      </esd:SecurityAccessRequired>
		                                      <%for(SubstituteListConstant sc : SubstituteListConstant.ALL){%>
		                                        <tr><td><a href="" class="homeSideNavSubCategoryLink"  onclick="return false;"><%=sc.getDescription()%></a></td></tr> 
		                                        <tr><td><a href="/MemberServices/Personnel/admin_view_sub_lists.jsp?type=<%=sc.getValue()%>" class="homeSideNavSubLink">&gt;&nbsp;View Lists</a></td></tr>
<!--  
		                                        <tr><td><a href="/MemberServices/Personnel/viewSublistsSchoolsList.html?type=<%=sc.getValue()%>" class="homeSideNavSubLink">&gt;&nbsp;By Schools</a></td></tr>
-->
		                                      <%}%>
		                                    </table>
		                                  </div>
		                                </td>
		                              </tr>
	                              </esd:SecurityAccessRequired>
	                            </table>
	                          </div>
                          </esd:SecurityAccessRequired>
                          
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                          
                          <esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-VIEW">
                            &nbsp;<a href="javascript:toggle_row('substitutes');" class="homeSideNavCategoryLink"><img id="substitutes_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Substitutes</a><BR />
                            <div id="substitutes" style="display:inline;">
                              <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:13px;">
                                <esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-STUDASS-VIEW">
                                  <tr>
                                    <td width="100%">
                                      &nbsp;<a href="javascript:toggle_row('sub_stud_ass');" class="homeSideNavCategoryLink"><img id="sub_stud_ass_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;Student Assistants</a><BR />
                                      <div id="sub_stud_ass" style="display:inline;">
                                        <table width="100%" cellspacing="0" cellpadding="0" style="padding-left:18px;">
                                          <tr><td><a href="<c:url value='/Personnel/availability_list.jsp' />" class="homeSideNavLink">&gt;&nbsp;Availability List</a></td></tr>
                                        </table>
                                      </div>
                                    </td>
                                  </tr>
                                </esd:SecurityAccessRequired>
                              </table>
                            </div>
                          </esd:SecurityAccessRequired>
                          
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                          
                          <esd:SecurityAccessRequired permissions="PERSONNEL-SSS-PROFILE-VIEW">
                            &nbsp;<a href="javascript:toggle_row('sss-profile');" class="homeSideNavCategoryLink"><img id="sss-profile_img" src="<c:url value='/Personnel/images/collapse.jpg' />" border="0" />&nbsp;SSS Profile(s)</a><BR />
                            <div id="sss-profile" style="display:inline;">
                              <table width="100%" cellpadding="0" cellspacing="0" style="padding-left:18px;">
                              	<tr><td><a href='<c:url value="/Personnel/SSS/index.html" />' class="homeSideNavLink">&gt;&nbsp;view/edit profile</a></td></tr>
                              </table>
                            </div>
                          </esd:SecurityAccessRequired>
                          
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                          
                        </td>
                      </tr>
                    </table>
                    <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="10" /><BR />
                    <table width="178" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #EBEBEB; border-width: 1px;">
                      <tr>
                        <td width="120" height="24" align="left" valign="middle" style="background-color: #EBEBEB;">
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="3" height="1" /><span class="displayBoxTitle">Help</span>
                        </td>
                        <td width="58" height="24" align="right" valign="middle" style="background-color: #EBEBEB;">
                          <img src="<c:url value='/Personnel/images/minimize_icon.gif' />" /><img src="<c:url value='/Personnel/images/spacer.gif' />" width="2" height="1" /><img src="<c:url value='/Personnel/images/close_icon.gif' />" /><img src="<c:url value='/Personnel/images/spacer.gif' />" width="2" height="1" /><BR />
                        </td>
                      </tr>
                      <tr>
                        <td width="178" align="center" valign="top" style="background-color: #FFFFFF;" colspan="2">
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment //Insert contents below -->
                          <img src="<c:url value='/Personnel/images/help_content.gif' />" /><BR />
                          <!-- Global Spacer for Alignment -->
                          <img src="<c:url value='/Personnel/images/spacer.gif' />" width="1" height="5" /><BR />
                          <!-- Global Spacer for Alignment -->
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="21" align="left" valign="top">
                    <img src="<c:url value='/Personnel/images/spacer.gif' />" width="21" height="1" /><BR />
                  </td>
                </tr>
             </table>
           </td>
      