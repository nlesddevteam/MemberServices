<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>
         <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
         <table style="margin:auto;">
			<tr>
				<td align="center">
				
							<ul id="menu">
							<li><a href="index.html">HOME</a></li>
							<li><a href="#" class="sub">PROJECTS</a>
								<ul class="right">
							  		<esd:SecurityAccessRequired roles="FUND3 ADMIN,FUND3 NORMAL USER">
							  			<li><a href="addNewProject.html">ADD NEW PROJECT</a></li>
							  		</esd:SecurityAccessRequired>
									<li><a href="projectsPendingApproval.html">PROJECTS PENDING APPROVAL</a></li>
							  		<li><a href="addProjectDocuments.html">ADD PROJECT DOCUMENTS</a></li>
							  	</ul>
							</li>
							<li><a href="#" class="sub">REPORTS</a>
									<ul class="right">
							  			<li><a href="createCustomReport.html">CREATE REPORT</a></li>
							  			<li><a href="#">GENERAL REPORT TEMPLATES</a></li>
							  			<li><a href="viewMyReports.html">SAVED REPORT TEMPLATES</a></li>
							 		</ul>
							 </li>
							<li><a href="#"  class="sub">POLICIES & FORMS</a>
							 	<ul  class="right">
							  			<li><a href="viewPolicyInformation.html">VIEW</a></li>
							 		</ul>
							 </li>
							 <esd:SecurityAccessRequired roles="FUND3 ADMIN">
							 <li><a href="#"  class="sub">SITE ADMINISTRATION</a>
							 	<ul class="right">
							  			<li><a href="#"   class="sub">CONTACTS</a>
							  				<ul class="right">
							 						<li><a href="addNewContact.html">ADD NEW CONTACT</a></li>
							 						<li><a href="viewContacts.html">VIEW CONTACTS</a></li>
							  				</ul>
							  			</li>
							  			<li><a href="#"   class="sub">DROPDOWNS</a>
							  				<ul class="right">
							 						<li><a href="viewDropdowns.html">VIEW DROPDOWNS</a></li>
							 				</ul>
							  			</li>
							  			<li><a href="#"   class="sub">POLICIES & FORMS</a>
							  				<ul class="right">
							  						<li><a href="addNewPolicy.html">ADD NEW</a></li>
							 						<li><a href="viewPolicies.html">VIEW/EDIT</a></li>
							 						<li><a href="sortPolicies.html">SORT</a></li>
							 				</ul>
							  			</li>							  			
							  		</ul>							  		
							  </li>							 
							 </esd:SecurityAccessRequired>
							</ul>
							<br style="clear: left" />
							
					</td>
				</tr>
			</table>
			<br />
			<div class="alert alert-success" id="divsuccess" style="display:none;">
    			<strong><span id="spansuccess"></span></strong>
  			</div>
  			<div class="alert alert-danger" id="diverror" style="display:none;">
    			<strong><span id="spanerror"></span></strong>
  			</div>