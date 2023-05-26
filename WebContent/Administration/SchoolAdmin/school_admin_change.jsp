<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*, com.esdnl.util.*,
                java.text.*"%>
                
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
				<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
				<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
                
                
<%!User usr = null;
  School school = null;
  Personnel principal = null;
  int principalID;
  HashMap asstprincipal = null;
  Personnel tmp = null;
  DistrictPersonnel personnel = null;
  Iterator iter = null;%>
<%
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  school = (School) request.getAttribute("School");
  principal = school.getSchoolPrincipal();
  if(principal != null)
  {
    principalID = principal.getPersonnelID();
  }
  else
  {
    principalID = -1;
  }
  
  //viceprincipal = school.getSchoolVicePrincipal();
  asstprincipal = school.getAssistantPrincipalsMap();
  
  personnel = new DistrictPersonnel();
  iter = personnel.iterator();
%>

<html>
  <head>
    <title> School Administration Change</title>
    <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
	<script>		
		$('document').ready(function(){		
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});		
		</script>

  </head>

<body>
<div class="siteHeaderGreen">School Administration Change</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 


<form name="change" action="/MemberServices/Administration/SchoolAdmin/schoolAdminChange.html?update&sid=<%=school.getSchoolID()%>" class="was-validated" method="post">
<input type="hidden" name="page" value="<%=request.getParameter("page")%>" />

<div class="card">
							  <div class="card-header"><b>SCHOOL ADMINISTRATION for <%=school.getSchoolName()%></b></div>
							  <div class="card-body">

							<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-5 col-12">
									<b>Principal:</b>
									<select class="form-control choicesP" name="principal">
                        <option value="-1">NO PRINCIPAL ASSIGNED TO SCHOOL</option>
                        <% while(iter.hasNext())
                          {
                            tmp = (Personnel) iter.next();
                            if(!tmp.getEmailAddress().endsWith("@nlesd.ca") || !tmp.getUserName().endsWith("@nlesd.ca")) continue;
                        %>
                          <option value="<%=tmp.getPersonnelID()%>" <%=(principalID ==tmp.getPersonnelID())?"SELECTED":""%>><%=StringUtils.encodeHTML2(tmp.getFullName())%>(<%=tmp.getUserName()%>)</option>
                        <% } %>
                      </select>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-7 col-12">
									<b>Assistant Principal(s):</b>
								 <select class="form-control choicesAP" name="viceprincipal" multiple>
                        <option value="-1" <%=((asstprincipal != null)&&(asstprincipal.size() == 0))?"SELECTED":""%>>NO ASSISTANT PRINCIPAL ASSIGNED</option>
                        <% 
                        	Personnel[] aps = school.getAssistantPrincipals();
                        	for(int i=0; i < aps.length; i++){
                        		out.println("<option value='" + aps[i].getPersonnelID() + "' SELECTED>" + StringUtils.encodeHTML2(aps[i].getFullName()) + "(" + aps[i].getUserName() + ")</option>");
                        	}
                        	
                        	iter = personnel.iterator(); 
                          while(iter.hasNext()){
                            tmp = (Personnel) iter.next();
                            if(asstprincipal.containsKey(new Integer(tmp.getPersonnelID())) || !tmp.getEmailAddress().endsWith("@nlesd.ca") || !tmp.getUserName().endsWith("@nlesd.ca")) continue;
                            	
                          	out.println("<option value='" + tmp.getPersonnelID() + "'>" + StringUtils.encodeHTML2(tmp.getFullName()) + "(" + tmp.getUserName() + ")</option>");
                        	}
                        %>
                      </select>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>								
						</div>


                    
                    <br/><br/>
						<div align="center">
						  <a class="btn btn-sm btn-success" name="add" id="add" href="#" onclick="document.change.submit();loadingData();">Update</a>
						  <a class="btn btn-sm btn-danger" href="school_admin_view.jsp" onclick="loadingData();">Back</a>
						</div>
                    
                     
                     
    </form>
    
    </div>
    
    <script>
    $(document).ready(function(){

    	var principalSelect = new Choices('.choicesP', {
    	removeItemButton: true,
    	maxItemCount:1,
    	paste: false,
        duplicateItemsAllowed: false,
        editItems: true,
    	searchResultLimit:20,
    	renderChoiceLimit:25
    	});
		
    	var assistantPrincipalSelect = new Choices('.choicesAP', {
        	removeItemButton: true,
        	maxItemCount:3,
        	paste: false,
            duplicateItemsAllowed: false,
            editItems: true,
        	searchResultLimit:20,
        	renderChoiceLimit:25
        	});
    	

    	});
				</script>
    
    
  </body>
</html>