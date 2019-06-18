<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
	<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

		<script type="text/javascript">
					personnelmenu.init({
					mainmenuid: "adminMenu", 
					orientation: 'h', 
					classname: 'personnelmenu', 
					contentsource: "markup" })
		</script>


<div id="adminMenu" class="personnelmenu">
<ul>
<li><a href="admin_index.jsp"><img src="images/v2/home.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;Home</a></li>

<li><a href="#"><img src="images/v2/admin.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;Admin Operations</a>
  <ul>
  <li><a href="#">Subject Groups</a></li>
   </ul>
</li>

<esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-RELOAD-TABLES">
		<li><a href="#"><img src="images/v2/sync.png" width="16" height="15" alt="" border="0" align="left" />&nbsp;Sync Manager</a>
  			<ul>
  				<li><a href="startSync.html">Start Sync</a></li>
  			</ul>
		</li>
</esd:SecurityAccessRequired>

<esd:SecurityAccessRequired permissions="PERSONNEL-EMP-OPPS-VIEW">
 		<li><a href="#"><img src="images/v2/emp.png" width="16" height="15" alt="" border="0" align="left" />&nbsp;Employment Opportunities</a>
  			<ul>
  				<li><a href="#">Position Planning</a>
    				<ul>
    					<li><a href="#">View</a></li>
    				</ul>
    	
    	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-VIEW">
				<li><a href="#">Advertisement Requests</a>
					<ul>
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
  	 					<li><a href="request_ad.jsp">Request Advertisement</a></li>
  	 				</esd:SecurityAccessRequired>
  	 				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
						<li><a href="admin_list_ad_requests.jsp?status=<%=RequestStatus.SUBMITTED.getId()%>">Pending Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.SUBMITTED)%>)</a></li>
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST">
						<li><a href="admin_list_ad_requests.jsp?status=<%=RequestStatus.APPROVED.getId()%>">Approved Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.APPROVED)%>)</a></li>
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
						<li><a href="admin_list_ad_requests.jsp?status=<%=RequestStatus.PREDISPLAYED.getId()%>">Pre-Display Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.PREDISPLAYED)%>)</a></li>
					</esd:SecurityAccessRequired>
					</ul>
		</esd:SecurityAccessRequired>			
		
		<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-JOBS">
				<li><a href="admin_applicants_index.jsp">Applicant Lists</a>
					<ul>
						<li><a href="addTeacherReference.html" target="_blank">Add Applicant Reference</a>
    					<li><a href="#">A - E</a>
							<ul>
    							<li><a href="admin_view_applicant_list.jsp?surname_part=A">A</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=B">B</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=C">C</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=D">D</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=E">E</a></li>
							</ul>
						</li>	
						<li><a href="#">F - K</a>
							<ul>
    							<li><a href="admin_view_applicant_list.jsp?surname_part=A">F</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=A">G</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=A">H</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=A">I</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=A">J</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=A">K</a></li>
							</ul>
						</li>
						<li><a href="#">L - Q</a>
							<ul>
    							<li><a href="admin_view_applicant_list.jsp?surname_part=L">L</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=M">M</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=N">N</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=O">O</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=P">P</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=Q">Q</a></li>
							</ul>
						</li>
						<li><a href="#">R - V</a>
							<ul>
    							<li><a href="admin_view_applicant_list.jsp?surname_part=R">R</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=S">S</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=T">T</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=U">U</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=V">V</a></li>		
							</ul>
						</li>
						<li><a href="#">W - Z</a>
							<ul>
    							<li><a href="admin_view_applicant_list.jsp?surname_part=W">W</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=X">X</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=Y">Y</a></li>
								<li><a href="admin_view_applicant_list.jsp?surname_part=Z">Z</a></li>
							</ul>
						</li>	
					</ul>	
	 			</li>
	 			<li><a href="#">Educational Positions</a>
	 				<ul>
	 				<esd:SecurityAccessRequired roles="ADMINISTRATOR">
  	 					<li><a href="addSEOStaffingAssignment.html">SEO Staffing Assignments</a></li>
  	 				</esd:SecurityAccessRequired>
		            <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTJOB">	
						<li><a href="admin_post_job.jsp">Post Job</a></li>
					</esd:SecurityAccessRequired>	
						<li><a href="#">View Posts</a>
							<ul>
    							<li><a href="admin_view_job_posts.jsp?status=All">View All</a></li>
								<li><a href="admin_view_job_posts.jsp?status=Open">Open</a></li>
								<li><a href="admin_view_job_posts.jsp?status=Unadvertised">Unadvertised</a></li>
								<li><a href="admin_view_job_posts.jsp?status=Closed">Closed</a></li>
								<li><a href="admin_view_job_posts.jsp?status=NoShortlist">Closed No Shortlist</a></li>
								<li><a href="admin_view_job_posts.jsp?status=Cancelled">Cancelled</a></li>
								<li><a href="admin_view_job_posts.jsp?status=Awarded">Awarded</a></li>
							</ul>
						</li>		
					</ul>
				</li>
		</esd:SecurityAccessRequired>
		
		<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTOTHERJOB">
				<li><a href="#">Support Staff Positions</a>
					<ul>
    					<li><a href="admin_post_job_other.jsp">Post Job</a></li>
					</ul>	
				</li>
		</esd:SecurityAccessRequired>
		
		<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUBLISTS">		
				<li><a href="#">Substitute Lists</a>
					<ul>
					<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-CREATE-SUBLIST">
    					<li><a href="admin_create_sub_list.jsp">Create List</a></li>
    				</esd:SecurityAccessRequired>	
    				<%for(int i=0; i < SubstituteListConstant.ALL.length; i++){%>
						<li><a href="#"><%=SubstituteListConstant.ALL[i].getDescription()%></a>
							<ul>
    							<li><a href="admin_view_sub_lists.jsp?type=<%=SubstituteListConstant.ALL[i].getValue()%>">View Lists</a></li>
							</ul>
						</li>
					<%}%>	
					</ul>
				</li>
		</esd:SecurityAccessRequired>
  			</ul>
		</li>
</esd:SecurityAccessRequired>

<esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-VIEW">
		<li><a href="#"><img src="images/v2/sub.png" width="22" height="15" alt="" border="0" align="left" />&nbsp;Substitutes</a>
			<ul>
		<esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-STUDASS-VIEW">
    			<li><a href="#">Student Assistants</a>
					<ul>
    					<li><a href="availability_list.jsp">Availability List</a></li>
					</ul>			
				</li>
		 </esd:SecurityAccessRequired>					
			</ul>
		</li>
</esd:SecurityAccessRequired>


		<li><a href="#"><img src="images/v2/sss.png" width="17" height="15" alt="" border="0" align="left" />&nbsp;SSS Profiles</a>
			<ul>
    			<li><a href="#">View/Edit Profile</a></li>
			</ul>
	</li>
</ul>	
</div>



 
 
 
 
 
 
 
 
 
 
 
 
 
 
  			
      