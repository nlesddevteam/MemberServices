<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>  
        

        
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  Profile profile = null;
  String op;
  Iterator iter = null;
  PersonnelCategory cat = null;

  usr = (User) session.getAttribute("usr");
  
  profile = (Profile) request.getAttribute("CURRENT_PROFILE");
  op = request.getParameter("op");
  
  
  TravelClaims claims = null;
  TreeMap year_map = null;  
  TreeMap approved = null;
  TreeMap payment_pending = null;
  TreeMap paid_today = null; 
  TravelClaim claim = null;
 
  Iterator y_iter = null;
  Map.Entry item = null;
  DecimalFormat df = null;
  DecimalFormat dollar_f =  null;
  TravelBudget budget = null; 
  
   
  claims = usr.getPersonnel().getTravelClaims();
  budget = usr.getPersonnel().getCurrrentTravelBudget();

  
  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");
  
  
%>


		
			<script>
			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");   
    			
        		
        		
			      		
			});
			
			
			
			</script>
			
			<script>
				
				var surl="includes/usrClaims.jsp";				
				$("#pageContentList").load(surl);			
				
				
				 
				 
				 
				 </script>
	

	
		
					
	
	
    <div class="claimHeaderText"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%>'S Profile and Claims History</div>
    
    Your profile address can be found below. To update, please make any changes and select update <i>(Fields outlined in red are required)</i>. It is important that you keep your profile address and contact information current. You can also find below a complete listing of your previous Travel and PD claims and their current status, as well as a graph of the amounts you have claimed.
    <br/><br/>
    <form name="add_profile_form" method="post" action="myProfile.html">
      <input type="hidden" name="op" value="ADDED">
           
        	<div class="profileColumn"><b>Mailing Address:</b><br/>
			        	<% 	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
			        		<%=profile.getStreetAddress()%>         
			        	<%	}else{
			            %>
                			<input type="text" name="cur_street_addr" id="cur_street_addr" value="<%=((profile != null)&&op.equals("update"))?profile.getStreetAddress():""%>" style="width:250px;" maxlength="200" class="requiredInputBox">
			            <%  }  %>
        	</div>
        	    	
        	
        	
        	<div class="profileColumn">
        	<b>City/Town:</b><br/>
                        <%  if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        	<%=profile.getCommunity()%>
                        <%	
                        	}else{
                        %>
                          	<input type="text" name="cur_community" id="cur_community" value="<%=((profile != null)&&op.equals("update"))?profile.getCommunity():""%>" style="width:175px;" maxlength="100" class="requiredInputBox">
                        <%  }  %>
                  
        	
        	</div>
        	<%=claims.getPersonnel().getSDSInfo().getVendorNumber() %>
        	
        	
        	<div class="profileColumn">
        	<b>Prov:</b><br/>
                        <%	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        	<%=profile.getProvince()%>
                        <%	
                        	}else{
                        %>
                          <select name="cur_province" id="cur_province" class="requiredInputBox">
                            <option value="NL" SELECTED>NL</option>
                          </select>
                        <%  }  %>
        	</div>
        	
        	<div class="profileColumn">
        	 <b>Postal Code:</b><br/>
                        <% 	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){ %>
                        	
                        	<%=profile.getPostalCode()%>
                        <%	
                        	
                        	}else{
                        %>
                          <input type="text" name="cur_postal_code" id="cur_postal_code" value="<%=((profile != null)&&op.equals("update"))?profile.getPostalCode():""%>" style="width:75px;" maxlength="7" class="requiredInputBox">
                        <%  }  %>
        	</div>
        	
        	
        	
        	
        	<div style="clear:both;"></div>
       
                     
                   
             <div class="profileColumn">
             <b>Telephone:</b><br/>
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=profile.getPhoneNumber()%>
                        <%
                        	}else{
                        %>
                          <input type="text" name="home_phone" id="home_phone" value="<%=((profile != null)&&op.equals("update"))?profile.getPhoneNumber():""%>" maxlength="16" style="width:120px;" class="requiredInputBox">
                       <%  }  %>
             </div>     
                  
              <div class="profileColumn">
             <b>Cell:</b><br/>
                      
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>
                        <%
                        	}else{
                        %>
                          <input type="text" name="cell_phone" id="cell_phone" value="<%=((profile != null)&&op.equals("update"))&&(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>" maxlength="16" style="width:120px;" class="inputBox">
                        <%  }  %>  
             </div>         
                      
             <div class="profileColumn">
             <b>Fax:</b><br/>
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=(profile.getFaxNumber()!=null)?profile.getFaxNumber():""%>
                        <%
                        	}else{
                        %>
                          <input type="text" name="fax" id="fax" value="<%=((profile != null)&&op.equals("update")&&(profile.getFaxNumber()!=null))?profile.getFaxNumber():""%>" maxlength="16" style="width:120px;" class="inputBox">
                        <%  }  %>
             </div>  
             
              <div class="profileColumn">
             <b>Gender:</b><br/>
                        
                        <% if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        <%=profile.getGender().equalsIgnoreCase("M")?"Male":"Female"%>
                        <%		
                        	}else{
                        %>
                          <select name="gender" id="gender" class="requiredInputBox">
                            <option value="M" <%=((profile != null)&&op.equals("update")&&profile.getGender().equals("M"))?"SELECTED":""%>>Male</option>
                            <option value="F" <%=((profile != null)&&op.equals("update")&&profile.getGender().equals("F"))?"SELECTED":""%>>Female</option>
                          </select>
                        <%  }  %>
             </div>
             
             
        <div style="clear:both;"></div>
                        
                   
             <div class="profileColumn">
             <b>Position:</b><br/>
                        <% if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        
                        		<%=profile.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()%>
                        <%
                        	}else{
                        		 	iter = (new PersonnelCategories()).iterator();
                        %>
                        
                        
                        
                        <%if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-ADMIN")){%>
                        
                        
                        
                          <select name="position" id="position" class="requiredInputBox" >
                            <%
                            	while(iter.hasNext()){
                                     cat = (PersonnelCategory)iter.next();
                            %>
                                <option value="<%=cat.getPersonnelCategoryID()%>" <%=(cat.getPersonnelCategoryID()==usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryID())?"SELECTED":""%>><%=cat.getPersonnelCategoryName()%></option>
                            <%}%>
                          </select>
                           <%} else {%>
                        <%=usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()%>
                        <br/><i>* To change/update your position, please contact <a href="mailto:mssupport@nlesd.ca?subject=Travel Claim Support - Position Change">mssupport@nlesd.ca</a>.</i>
                       
                        
                        <%} %>
                        	
                        	<%}%>
                        
             </div>          
                    
                    
                
                    
                                    
          <div style="clear:both;"></div>
         
          
           
          <div class="no-print">            
         <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
         <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div><br/>
			
                      <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                         <img src="includes/img/edit-off.png" class="img-swap" onclick="process_message('server_message', 'Fetching profile...'); document.forms[0].op.value='EDIT';document.forms[0].submit();">&nbsp;
                       
                      <%}else if((profile != null)&&("UPDATE".equalsIgnoreCase(op))){%>
                        
                          <img src="includes/img/update-off.png" class="img-swap" title="Update Profile" onclick="findTheProfileInvalids();updatemyprofile('Updated');">&nbsp;
                       
                      <%}else if(profile == null){%>
                        
                           <img src="includes/img/submit-off.png" class="img-swap" title="Submit Profile" onclick="findTheProfileInvalids();updatemyprofile('Added');">&nbsp;
                        
                      <%}%>
                      <a href="index.jsp"><img src="includes/img/cancel-off.png" class="img-swap" title="Cancel"></a>
              </div>        	
            </form>
           <div style="clear:both;"></div>
          <br/>    
                    
         <p><div id="pageContentList"><div style="color:white;background-color:Red;padding:3px;text-align:center;"> &nbsp; <b>*** PLEASE WAIT ***</b><br/>Retrieving your claim records and graphing results. (This may take a few minutes.)&nbsp; </div></div>            
           

    
    

