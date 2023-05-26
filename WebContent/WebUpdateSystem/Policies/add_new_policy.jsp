<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>Web Policy Posting System</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">  
   	
	</head>

  <body>
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Add Policy</div>

Below you can add new policies to the website. To add other files to this policy, you will need to complete the adding of this policy and use the edit option.
                     
                    
<br/><br/>

    <form id="pol_cat_frm" action="addNewPolicy.html" method="post" ENCTYPE="multipart/form-data" class="was-validated" autocomplete="off">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4"> 
      <b>Category:</b><br/>
                            
                       <select id="policy_category" name="policy_category" required class="form-control">
						<c:forEach var="item" items="${categorylist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
       </div>
       <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
       <b>Status:</b><br/>
       <select id="policy_status" name="policy_status" class="form-control" required>
						<c:forEach var="item" items="${statuslist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
       </div>
       <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">               
      <b>Number: (Enter number. If GOV-100, enter 100)</b><br/>
           <input type="text" id="policy_number"  name="policy_number" required class="form-control">
      </div>
      </div>
      <br/><br/>
       <div class="row">
      		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
      <b>Policy Title:</b><br/>
                      <input type="text" class="form-control" id="policy_title" name="policy_title" required>
       </div>
       <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">               
      <b>Policy Documentation:</b><br/>
                      <input type="file" id="policy_documentation" name="policy_documentation"  required class="form-control" accept=".pdf">
       </div>
       <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">               
      <b>Policy Admin Procedures:</b><br/>
                      <input type="file" class="form-control" id="policy_admin_doc" name="policy_admin_doc" accept=".pdf">
        </div>
        </div>              
                    
       <br/><br/>           
                    
                   <div align="center">
					 <button id="butSave" class="btn btn-sm btn-success" onclick="loadingData();">SAVE</button> &nbsp; <A HREF='viewPolicies.html' class="btn btn-sm btn-danger" onclick="loadingData();">Cancel</a>
                   </div>
    </form>
		
  
 

</div> 
</div>
</div>
    
  
  </body>

</html>