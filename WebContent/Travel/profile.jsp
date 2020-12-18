<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

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
			$("#loadingSpinner").css("display","none");
</script>

<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/profile.png" border=0 style="max-width:150px;float:right;"/>
<div class="pageHeader">
	<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase() %>
</div>
<div class="pageBodyText">
 		Your profile address can be found below. This address is what the system uses for your payment account. 
 		<br/><br/><b>NOTE: It is important that you keep your profile address and contact information current.</b> 
 		<br/><br/>To update, please make any changes and select Update Profile below.		
 	

 		
 		<!-- You can also find below a complete listing of your previous Travel and PD claims and their current status, as well as a graph of the amounts you have claimed.-->
</div>

    <form class="form-inline" name="add_profile_form" method="post" action="myProfile.html">
      <input type="hidden" name="op" value="ADDED">      
     
      	 		<div class="input-group mb-3 col-lg-12">
       				<div class="input-group-prepend">
      					<span class="input-group-text">Address:</span>
    				</div>
       				<% 	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
			        		<%=profile.getStreetAddress()%>         
			        	<% } else { %>
                			<input class="form-control" placeholder="Enter Mailing Address"  type="text" name="cur_street_addr" id="cur_street_addr" value="<%=((profile != null)&&op.equals("update"))?profile.getStreetAddress():""%>" >
			            <%  }  %>
        		</div>
        		
        		<div class="input-group mb-3 col-lg-6" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">City/Town:</span>
    			</div>     
                        <%  if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        	<%=profile.getCommunity()%>
                        <% } else { %>  
                        	<input class="form-control"  type="text" name="cur_community" id="cur_community" value="<%=((profile != null)&&op.equals("update"))?profile.getCommunity():""%>" >                          	
                        <%  }  %>
                 </div> 
                 
                <div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Prov:</span>
    			</div>     
                 	 <%	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        	<%=profile.getProvince()%>
                        <%	
                        	}else{
                        %>
                          <select name="cur_province" id="cur_province" class="form-control">
                            <option value="NL" SELECTED>NL</option>
                          </select>
                        <%  }  %>
        		</div>
        		<div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Postal Code:</span>
    			</div>    
      
                        <% 	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){ %>
                        	
                        	<%=profile.getPostalCode()%>
                        <%	
                        	
                        	}else{
                        %>
                          <input class="form-control" type="text" name="cur_postal_code" id="cur_postal_code" maxlength="7" value="<%=((profile != null)&&op.equals("update"))?profile.getPostalCode():""%>">
                          	
                        <%  }  %>
                 </div>
                 
                 
                <div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Tel:</span>
    			</div>     
        	
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=profile.getPhoneNumber()%>
                        <%
                        	}else{
                        %>
                          <input class="form-control" type="text" name="home_phone" id="home_phone" value="<%=((profile != null)&&op.equals("update"))?profile.getPhoneNumber():""%>" maxlength="14">
                          	
                       <%  }  %>
          </div>
          <div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Cell:</span>
    			</div>            
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>
                        <%
                        	}else{
                        %>
                          <input class="form-control" type="text" name="cell_phone" id="cell_phone" value="<%=((profile != null)&&op.equals("update"))&&(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>" maxlength="14">
                        <%  }  %>  
            </div>            
             <div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Fax:</span>
    			</div>              
         
                        <%
                        	if((profile != null) && ("VIEW".equalsIgnoreCase(op))){
                        %>
                          <%=(profile.getFaxNumber()!=null)?profile.getFaxNumber():""%>
                        <%
                        	}else{
                        %>
                          <input class="form-control"  type="text" name="fax" id="fax" value="<%=((profile != null)&&op.equals("update")&&(profile.getFaxNumber()!=null))?profile.getFaxNumber():""%>" maxlength="14">
                        <%  }  %>
            </div>
            <div class="input-group mb-3 col-lg-3" >
       			<div class="input-group-prepend">
      					<span class="input-group-text">Gender:</span>
    			</div>                         
                        <% if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                        <%=profile.getGender().equalsIgnoreCase("M")?"Male":"Female"%>
                        <%		
                        	}else{
                        %>
                          <select name="gender" id="gender" class="form-control">
                            <option value="M" <%=((profile != null)&&op.equals("update")&&profile.getGender().equals("M"))?"SELECTED":""%>>Male</option>
                            <option value="F" <%=((profile != null)&&op.equals("update")&&profile.getGender().equals("F"))?"SELECTED":""%>>Female</option>
                          </select>
                          	<div class="valid-feedback">OK</div>
    						<div class="invalid-feedback">Please fill out this field.</div>
                        <%  }  %>
         </div>
                             
           
                     
         <div class="input-group mb-3 no-print" style="margin: 0 auto;text-align:center;">			
                      <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                      <a href="#" class="btn btn-sm btn-warning mr-2" onclick="document.forms[0].op.value='EDIT';document.forms[0].submit();">Edit Profile</a>                                               
                      <%}else if((profile != null)&&("UPDATE".equalsIgnoreCase(op))){%>
                         <a href="#" class="btn btn-sm btn-primary mr-2" onclick="findTheProfileInvalids();updatemyprofile('Updated');">Update Profile</a>                 
                      <%}else if(profile == null){%>
                        <a href="#" class="btn btn-sm btn-success mr-2" onclick="findTheProfileInvalids();updatemyprofile('Added');">Submit</a>
                       <%}%>
                      <a href="index.jsp" class="btn btn-danger btn-sm mr-2" onclick="loadingData()">Cancel</a>
          </div>      	
            </form>
     
 
     
         