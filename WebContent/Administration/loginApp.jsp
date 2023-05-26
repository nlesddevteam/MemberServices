<%@ page language="java" 
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 java.util.*"%>

<%!User usr = null;
  PersonnelCategories categories = null;
  PersonnelCategory category = null;
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

  categories = new PersonnelCategories();
%>

<html>
  <head>
    <title></title>
   
   
  </head>

  <body>
  
  <div class="row pageBottomSpace">
  <div class="col siteBodyTextBlack">
		<div class="siteHeaderGreen"> Set Next Login Application for Group</div>
					
    Here you set the MemberServices application that will open automatically for a particular user group.
  
    <form name="loginapp" action="/MemberServices/Administration/setNextLoginApp.html" method="post">
  
   
   <div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-6 col-12">
									<b>Select Group:</b>
									<SELECT name="group" class="form-control form-control-sm">
					                          <OPTION VALUE="-1">PLEASE SELECT GROUP</OPTION>
					                        <%
					                        	iter = categories.iterator();
					                                                   while(iter.hasNext())
					                                                   {
					                                                      category = (PersonnelCategory) iter.next();
					                        %>    <OPTION value="<%=category.getPersonnelCategoryID()%>"><%=category.getPersonnelCategoryName()%></OPTION>
					                        <% } %>
					                   </SELECT>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-6 col-12">
									<b>Application:</b>
									<SELECT name="app" class="form-control form-control-sm">
			                          <OPTION VALUE="-1">PLEASE SELECT APPLICATION</OPTION>
			                          <OPTION value="NONE">NONE</OPTION>
			                          <OPTION value="PROFILE">PROFILE</OPTION>
			                        </SELECT>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>								
	</div>
   
                    <br/><br/>
						<div align="center">
						  <a class="btn btn-sm btn-success" name="add" id="add" href="#" onclick="document.loginapp.submit();">Update</a>
						  <a class="btn btn-sm btn-danger" href="../navigate.jsp">Back</a>
						</div>
                        
                      
                     
    </form>
    </div>
    </div>
  </body>
</html>