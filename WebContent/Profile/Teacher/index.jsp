<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*,com.awsd.school.*,com.awsd.personnel.*"%>

<esd:SecurityCheck />
<%
  User usr = null;
  Personnel p = null;
  School s = null;
  School ss = null;
  usr = (User) session.getAttribute("usr");
  
  if(usr == null)
  {%>
  	 <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="This is a Secure Resource!<br>Please Login."/>
    </jsp:forward>
  <%}
  
  
  p = usr.getPersonnel();
  s = p.getSchool();
  ss = p.getSchool();

 %> 


<html>
  <head>
    <title>Member Services - Teacher Profile</title>    
 
  </head>
  <body>
  
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">
    Welcome to the Teacher/Support Staff Profile Update Manager. This is where you will update your teaching and/or support staff school/office assignment as well as any changes to your name we have on file. This will effect various online applications such as your Travel Claims and Learning Plans.
        			</div>
  				</div>
 </div>   
 <br/>   
  	<div class="panel panel-info">
  		<div class="panel-heading"><b>Profile Name Update</b></div>
  			<div class="panel-body" style="font-size:12px;">
  				<form name='modifyName' action='modifyPersonnelName.html?id=<%=p.getPersonnelID()%>' method='POST'>
  				<b>NAME ON FILE:</b> <%= p.getFirstName()%> <%= p.getLastName()%><br/><br/>
  				
  				Please verify and make any necessary changes to your name below.
  					<p>
  									  
				  	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						  	<div class="input-group">
						    	<span class="input-group-addon">First Name:</span>
						    	<input name='firstname' id="firstName" class="form-control" placeholder="Enter your First Name" value='<%=p.getFirstName()%>'>
						  	</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						  	<div class="input-group">
						    	<span class="input-group-addon">Last Name:</span>
						    	<input type="text" id="lastName" class="form-control" name='lastname' placeholder="Enter your Last Name" value='<%=p.getLastName()%>'>
						  	</div>
				  
				  	</div>
				  	<br/><br/>
                    <div align="center" style="padding-top:10px;padding-bottom:5px;">
                    <input type='submit' value='Save Name Change' class="btn btn-xs btn-success" onclick="return validateName(document.modifyName);">
                    </div>  
  					
  					
  					
  					<div class="alert alert-danger" id="nameMsgERR" style="display:none;text-align:center;"><%= (String) request.getAttribute("msgERR") %></div>
                    <div class="alert alert-success" id="nameMsgOK" style="display:none;text-align:center;"><%= (String) request.getAttribute("msgOK") %></div>
  					
  					
  					
  				</form>
  			</div>
	</div>
	
  
        <%if(request.getAttribute("msgOK") != null){%>
        <script>$("#nameMsgOK").css("display","block").delay(3000).fadeOut();</script>
      	<%}%>   
      	<%if(request.getAttribute("msgERR") != null){%>
         <script>$("#nameMsgERR").css("display","block").delay(5000).fadeOut();</script>
      	<%}%>       
    
<!-- TEACHER SCHOOL ASSIGNMENT -->
    
    
    
    
    <div class="panel panel-info">
  <div class="panel-heading"><b>School/Office Assignment</b></div>
  <div class="panel-body" style="font-size:12px;">
  <form  name='modifySchool' action='modifyTeacherSchool.html?id=<%=p.getPersonnelID()%>' method='POST'>
  <b>SCHOOL:</b> <%= (s != null)?s.getSchoolName():"No School/Office Association on file."%><br/>
  <b>PRINCIPAL:</b> <span style="text-transform:Capitalize;"><%=((s != null)&&(s.getSchoolPrincipal() != null))?s.getSchoolPrincipal().getFullNameReverse():"No Principal Assigned."%></span>
  <br/><br/>If your school association is incorrect above, please change using the form below. If no change, no action is required.
  Please make sure you always update your school assignment when you change schools as this effects your Learning Plans and other MemberServices applications.
     <p><div class="input-group">
				    	<span class="input-group-addon">Your School:</span>
				    	<select name='school' id="schoolSelect" class="form-control">
                          <option value="-1">PLEASE SELECT</option>
                          <%
                          	Collection<School> schools = SchoolDB.getSchoolsOffices();
                            Iterator iter = schools.iterator();%>
							
                            <option value="277" <%= (s != null)?(s.getSchoolID()!=277?"":" SELECTED"):""%>>District/Avalon Regional Office</option>
                            <option value="279" <%= (s != null)?(s.getSchoolID()!=279?"":" SELECTED"):""%>>Central Regional Office</option>
                           	<option value="280" <%= (s != null)?(s.getSchoolID()!=280?"":" SELECTED"):""%>>Western Regional Office</option>
                            <option value="278" <%= (s != null)?(s.getSchoolID()!=278?"":" SELECTED"):""%>>Labrador Regional Office</option>
                            <option value="-1">----SCHOOLS----</option>					
							
							<% while(iter.hasNext())
                                  {
                                  ss = (School) iter.next();
                          %>  
                          
                          <option value='<%=ss.getSchoolID()%>' <%= (s != null)?(s.getSchoolID()!=ss.getSchoolID()?"":" SELECTED"):""%>><%=ss.getSchoolName()%></option>
                          <%}%>
                        </select>
				  	</div>
      <br/>
      
        
        <div align="center" style="padding-top:5px;padding-bottom:5px;">
        <input type='submit' value='Save School Assignment' class="btn btn-xs btn-success" onclick="return validateAdd(document.modifySchool.school);">
        </div>
					<div class="alert alert-danger" id="schoolMsgERR" style="display:none;text-align:center;"><%= (String) request.getAttribute("smsgERR") %></div>
                    <div class="alert alert-success" id="schoolMsgOK" style="display:none;text-align:center;"><%= (String) request.getAttribute("smsgOK") %></div>
  					
      </form>
       <%if(request.getAttribute("smsgOK") != null){%>
        <script>$("#schoolMsgOK").css("display","block").delay(3000).fadeOut();</script>
      	<%}%>   
      	<%if(request.getAttribute("smsgERR") != null){%>
         <script>$("#schoolMsgERR").css("display","block").delay(5000).fadeOut();</script>
      	<%}%>        
 </div></div>                
  
 
<div align="center" style="padding-top:5px;padding-bottom:10px;"><a href="/MemberServices/navigate.jsp" class="btn btn-danger btn-xs">Exit to Member Services</a></div>
   
    
 
    
  </body>
</html>
