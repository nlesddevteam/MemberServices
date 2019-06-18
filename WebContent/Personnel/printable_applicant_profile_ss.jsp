<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.awsd.school.*,
                  com.awsd.school.bean.*,
                  com.awsd.mail.bean.AlertBean,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.awsd.security.crypto.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	
	ApplicantProfileBean profile = (ApplicantProfileBean) ApplicantProfileManager.getApplicantProfileBean(request.getParameter("sin"));
    SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    ApplicantNLESDExperienceSSBean esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(profile.getSIN());
    ApplicantEducationSecSSBean edu = ApplicantEducationSecSSManager.getApplicantEducationSecSSBeanBySin(profile.getSIN());
    ApplicantEducationOtherSSBean other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(profile.getSIN());
    //RegionBean[] regionPrefs = ApplicantRegionalPreferenceManager.getApplicantRegionalPreferencesMap(profile).values().toArray(new RegionBean[0]);
    //HashMap<Integer, ApplicantRegionalJobPoolSSBean> hmap = ApplicantRegionalJobPoolSSManager.getApplicantRegionalJobPoolPreferencesMap(profile.getSIN());
    ApplicantSupervisorBean[] refs = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
    NLESDReferenceListBean[] chks = NLESDReferenceListManager.getReferenceBeansByApplicant(profile.getSIN());
    boolean validReference = true;
    if(chks.length == 0 || refs == null){
    	validReference = false;
    }
    Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
%>
		
	<!-- APPLICANT PROFILE-->		
		<div align="center"><img src="includes/img/nlesd-colorlogo.png" width="400"><br/><br/>
		<span style="font-size:16px;font-weight:bold;"><%=profile.getFullNameReverse() %></span>
		</div>
		
		<table class="table table-condensed" style="font-size:10px;">
		<thead>
		<tr>
		<th colspan=4 style="font-weight:bold;border-top:1px solid black;">APPLICANT PROFILE</th>
		</tr>
		</thead>
		<tbody>		
		<tr>
		<td class="tableTitle" colspan=1>NAME:</td>
		<td class="tableResult" colspan=3>
		<%=profile.getFirstname() + " " + ((profile.getMiddlename() != null)?profile.getMiddlename() + " ":"") + profile.getSurname() 
		          + ((profile.getMaidenname()!= null)?" ("+profile.getMaidenname()+")":"")%>
		
		</td>		
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>ADDRESS:</td>
		<td class="tableResult" colspan=3>
					<%=profile.getAddress1()%>		            
		             <%=(profile.getAddress2()!=null)?" &middot; "+profile.getAddress2():""%>		            
		            , <%=profile.getProvince() + " &middot: " + profile.getCountry() + " &middot; " + profile.getPostalcode()%>
		</td>
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>TELEPHONE: </td>
		<td class="tableResult" colspan=3>
		(Res) <%=(profile.getHomephone()!=null)?profile.getHomephone():"N/A"%> &middot; 
		(Work) <%=(profile.getWorkphone()!=null)?profile.getWorkphone():"N/A"%> &middot; 
		(Cell) <%=(profile.getCellphone()!=null)?profile.getCellphone():"N/A"%>
		</td>
		</tr>
		<tr>
		<td class="tableTitle" colspan=1>EMAIL:</td>
		<td class="tableResult" colspan=3><%=profile.getEmail()%></td>
		</tr>		
		
		</tbody>
		</table>
		      
<!-- EXPERIENCE -->			
		
		<table class="table table-condensed borderless" style="font-size:10px;">
		<thead>
		<tr>
		<th colspan=4 style="font-size:14px;">
		NLESD EXPERIENCE
		</th>
		</tr>
		</thead>
		<tbody>
		 <%if(esd_exp != null){%>
		<tr>
        <td colspan=3>Are you currently employed with the NLESD?</td>
         <td><%=esd_exp.getCurrentlyEmployed().equals("Y") ? "Yes" : "No"%></td>
        </tr>

                       <tr>
                         <td>Current NLESD Position(s)</td>
                         <td colspan=3>
                        		<job:ApplicantCurrentPositionsSS  showdelete="view"/>
                         </td>
                       </tr>
        <%}else{%>
                       <tr>
                         <td colspan=4>No Experience currently on file.</td>
                       </tr>
          <%}%>
          </tbody> 
         </table>
         
<!-- EMPLOYMENT HISTORY -->         	
          								<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">EMPLOYMENT HISTORY</th>
									    </tr>
 										</thead>  
 										<tbody>
 										<tr>
 										<td><job:ApplicantEmploymentSS showdelete="view"/></td>
 										</tr>
 										</tbody>
 										</table>    
 										
 										
 										
<!-- EDUCATION -->                     
             <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=2 style="font-weight:bold;border-top:1px solid black;">EDUCATION</th>
									    </tr>
 										</thead>  
 										<tbody>          
                      
                      
          
                     	<%if(edu != null){%>
                         <tr>
                           <td class="tableTitle">Education Level (Highest Level)</td>
                           <td class="tableResult"><%=edu.getEducationLevel() != null ? edu.getEducationLevel() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Name</td>
                           <td class="tableResult"><%=edu.getSchoolName() != null ? edu.getSchoolName() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Town/City</td>
                           <td class="tableResult"><%=edu.getSchoolCity() != null ? edu.getSchoolCity() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">School Province/State</td>
                           <td class="tableResult"><%=edu.getSchoolProvince() != null ? edu.getSchoolProvince() : ""%></td>
                         </tr>
                         <tr>
                           <td class="tableTitle">Did you graduate?</td>
                           <td class="tableResult"><%=edu.getGraduatedText() != null ? edu.getGraduatedText() : ""%></td>
                         </tr>
                       <%}else{%>
                         <tr>
                           <td colspan=2><span style="color:Grey;">No education currently on file.</span></td>
                         </tr>
                       <%}%>
                       </tbody>
                     </table>
                     
                     
<!-- DEGREES AND OTHER PAPER -->  
                  
          			<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">DEGREES/DIPLOMAS/CERTIFICATES</th>
									    </tr>
 										</thead>  
 										<tbody>
 										<tr>
 										<td><job:ApplicantEducationPostSS showdelete="view"/></td>
 										</tr>
 										</tbody>
 										</table> 
 										
 <!-- OTHER INFORMATION -->             
               				<table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th style="font-weight:bold;border-top:1px solid black;">OTHER INFORMATION</th>
									    </tr>
 										</thead>
 										 <tbody> 
               
							               <%if((other_info != null) && !StringUtils.isEmpty(other_info.getOtherInformation())) { %>                    
							                    <tr><td><%=other_info %></td></tr>                  
							                    <%} else { %>
							                    <tr>
												<td><span style="color:Grey;">No Other Information currently on file.</span></td>
							                    </tr>
							                    <%}%>             
							               </tbody>
							               </table> 										

                                  
                                  
                                  
  <!-- REFERENCE CHECKS -->                                
                                  
 <% if((chks != null) && (chks.length > 0)) { %>
                               <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    
									      <tr style="border-top:1px solid black;">
									        <th width='15%'>REFERENCE DATE</th>
									        <th width='20%'>PROVIDED BY</th>	
									        <th width='50%'>POSITION</th>							       								        
									        	
									      </tr>
									    </thead>
							    
							    <tbody>
                               
                               <% for(int i=0; i < chks.length; i ++)
                                  {
                               %>
							    <tr>
							    <td><%=chks[i].getProvidedDateFormatted() %></td>
							    <td><%=chks[i].getProvidedBy() %></td>							    
							    <td><%=chks[i].getProviderPosition() %></td>
							    
							    </tr>
							    <%}%>
							    </tbody>
							    </table>
							    <%} else { %>							   
                                    <span style="color:Grey;">No Reference Checks currently on file.</span>
                                    <script>$("#section7").removeClass("panel-success").addClass("panel-danger");</script>
                                 <% } %> 
 
 <!-- REFERENCES -->
 
 <table class="table table-striped table-condensed" style="font-size:10px;">
									    <thead>
									    <tr>
									    <th colspan=4 style="font-weight:bold;border-top:1px solid black;">REFERENCES</th>
									    </tr>
 										</thead>
 										 <tbody> 
	
	<% if((refs != null) && (refs.length > 0))
                              		{ %>
                             
									    
									      <tr style="font-weight:bold;">
									        <td width='20%'>FULL NAME</td>
									        <td width='20%'>TITLE</td>	
									        <td width='45%'>PRESENT ADDRESS</td>								       								        
									        <td width='15%'>TELEPHONE</td>		
									      </tr>
									   
                              
                              
                              <% for(int i=0; i < refs.length; i ++)
                                  	{
                               %>
							    <tr>
							    <td><%=refs[i].getName()%></td>
							    <td><%=refs[i].getTitle()%></td>							    
							    <td><%=refs[i].getAddress()%></td>
							    <td><%=refs[i].getTelephone()%></td>
							    </tr>
							    <%}%>
							   
							    <%} else { %>							   
                                    <tr>
							    <td colspan=4><span style="color:Grey;">No References currently on file.</span></td>
							    	</tr>
                                   
                                 <% } %> 
	
	 							</tbody>
							    </table>
             
             
             
                               	                                                                                                                                    		                         	                                                    
		
