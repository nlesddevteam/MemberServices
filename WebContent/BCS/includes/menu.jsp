<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.common.*,
                 com.nlesd.bcs.dao.*,
                 com.nlesd.bcs.bean.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 com.nlesd.bcs.constants.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="BCS-SYSTEM-ACCESS" />

<% 
User usr = (User) session.getAttribute("usr");
BussingContractorSystemCountsBean cbean = null; 
if(usr.checkPermission("BCS-VIEW-WESTERN")){
	cbean = BussingContractorSystemCountsManager.getBussingContractorSystemCountsReg(BoardOwnedContractorsConstant.WESTERN.getValue());
}else if(usr.checkPermission("BCS-VIEW-CENTRAL")){
	cbean = BussingContractorSystemCountsManager.getBussingContractorSystemCountsReg(BoardOwnedContractorsConstant.CENTRAL.getValue());
}else if(usr.checkPermission("BCS-VIEW-LABRADOR")){
	cbean = BussingContractorSystemCountsManager.getBussingContractorSystemCountsReg(BoardOwnedContractorsConstant.LABRADOR.getValue());
}else{
	cbean = BussingContractorSystemCountsManager.getBussingContractorSystemCounts(); 
}

%>

<script src="includes/js/menu.js"></script>
   	<script>
   		$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});
    		
    		  $('.menuBCSC1').click(function () {
    		    	$("#loadingSpinner").css("display","inline").delay(3000).fadeOut();
    		    });
		});
	</script>
<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/animated-bus.gif" width="200" border=0><br/>Transporting data, please wait...</div></div>

<div id="busingMenu" style="z-index:999;">
  <ul>
     <li><a href="#" ><i class="fa fa-fw fa-folder-open-o"></i> File</a>
        <ul>
           <li><a href="index.jsp"><i class="fa fa-fw fa-home"></i> Home</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('admimViewSystemDocuments.html');"><i class="fa fa-fw fa-copy"></i> Policies &amp; Procedures</a></li>
           <li><a href="#" class="menuBCS1" title='Print this page (pre-formatted)' onclick="closeMenu();jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fa fa-fw fa-print"></i> Print</a></li>
           <li><a href="/navigate.jsp"><i class="fa fa-fw fa-mail-reply-all"></i> Exit to MS</a></li>
        </ul>
     </li>
     <esd:SecurityAccessRequired permissions="BCS-VIEW-CONTRACTORS">
     <li><a href="#"><i class="fa fa-fw fa-male"></i> Operator</a>
        <ul>
        	<li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminAddContractor.html');"><i class="fa fa-fw fa-search"></i> Add New Operator</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewContactorsApproval.html?status=p');"><i class="fa fa-fw fa-clock-o"></i> Pending Approval <% if (cbean.getSubmittedContractors()!=0) {out.print("("+cbean.getSubmittedContractors()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewContactorsApproval.html?status=a');"><i class="fa fa-fw fa-check"></i> Approved <% if (cbean.getApprovedContractors()!=0) {out.print("("+cbean.getApprovedContractors()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewContactorsApproval.html?status=s');"><i class="fa fa-fw fa-ban"></i> Suspended  <% if (cbean.getSuspendedContractors()!=0) {out.print("("+cbean.getSuspendedContractors()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewContactorsApproval.html?status=r');"><i class="fa fa-fw fa-close"></i> Rejected <% if (cbean.getRejectedContractors()!=0) {out.print("("+cbean.getRejectedContractors()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewContactorsApproval.html?status=re');"><i class="fa fa-fw fa-close"></i> Removed <% if (cbean.getRemovedContractors()!=0) {out.print("("+cbean.getRemovedContractors()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('searchContractors.html');"><i class="fa fa-fw fa-search"></i> Search Operator</a></li>
           <li><a href="contractorLogin.html">Operator Login</a></li>
           <li><a href="register.html">Operator Registration</a></li>
        </ul>
     </li>
     </esd:SecurityAccessRequired>
     <li><a href="#"><i class="fa fa-fw fa-users"></i> Employee</a>
        <ul>
        	<li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewEmployee.html?vid=-1');"><i class="fa fa-fw fa-search"></i> Add New Employee</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=ns');"><i class="fa fa-fw fa-clock-o"></i> Not Submitted <% if (cbean.getNotSubmittedContractorsEmployees()!=0) {out.print("("+cbean.getNotSubmittedContractorsEmployees()+")");};%></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=p');"><i class="fa fa-fw fa-clock-o"></i> Pending Approval <% if (cbean.getSubmittedContractorsEmployees()!=0) {out.print("("+cbean.getSubmittedContractorsEmployees()+")");};%></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=a');"><i class="fa fa-fw fa-check"></i> Approved <% if (cbean.getApprovedContractorsEmployees()!=0) {out.print("("+cbean.getApprovedContractorsEmployees()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=s');"><i class="fa fa-fw fa-ban"></i> Suspended <% if (cbean.getSuspendedContractorsEmployees()!=0) {out.print("("+cbean.getSuspendedContractorsEmployees()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=r');"><i class="fa fa-fw fa-close"></i> Rejected <% if (cbean.getRejectedContractorsEmployees()!=0) {out.print("("+cbean.getRejectedContractorsEmployees()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesApprovals.html?status=re');"><i class="fa fa-fw fa-close"></i> Removed <% if (cbean.getRemovedContractorsEmployees()!=0) {out.print("("+cbean.getRemovedContractorsEmployees()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('searchEmployees.html');"><i class="fa fa-fw fa-search"></i> Search Employees</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-bus"></i> Vehicle</a>
        <ul>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewVehicle.html?cid=-1');"><i class="fa fa-fw fa-search"></i> Add New Vehicle</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=ns');"><i class="fa fa-fw fa-clock-o"></i> Not Submitted <% if (cbean.getNotSubmittedContractorsVehicles()!=0) {out.print("("+cbean.getNotSubmittedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=p');"><i class="fa fa-fw fa-clock-o"></i> Pending Approval <% if (cbean.getSubmittedContractorsVehicles()!=0) {out.print("("+cbean.getSubmittedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=a');"><i class="fa fa-fw fa-check"></i> Approved <% if (cbean.getApprovedContractorsVehicles()!=0) {out.print("("+cbean.getApprovedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=s');"><i class="fa fa-fw fa-ban"></i> Suspended <% if (cbean.getSuspendedContractorsVehicles()!=0) {out.print("("+cbean.getSuspendedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=r');"><i class="fa fa-fw fa-close"></i> Rejected <% if (cbean.getRejectedContractorsVehicles()!=0) {out.print("("+cbean.getRejectedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesApprovals.html?status=re');"><i class="fa fa-fw fa-close"></i> Removed <% if (cbean.getRemovedContractorsVehicles()!=0) {out.print("("+cbean.getRemovedContractorsVehicles()+")");}; %></a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('searchVehicles.html');"><i class="fa fa-fw fa-search"></i> Search Vehicles</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-file-text-o"></i> Contract</a>
        <ul>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewContract.html');"><i class="fa fa-fw fa-plus"></i> Add New</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewContracts.html');"><i class="fa fa-fw fa-tripadvisor"></i> View Contracts</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminSearchContracts.html');"><i class="fa fa-fw fa-search"></i> Search Contracts</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-arrow-circle-o-up"></i> Route</a>
        <ul>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewRoute.html');"><i class="fa fa-fw fa-plus"></i> Add New</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('adminViewRoutes.html');"><i class="fa fa-fw fa-tripadvisor"></i> View Routes</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-file-text-o"></i> Reports</a>
        <ul>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('createNewReport.html');"><i class="fa fa-fw fa-plus"></i> Add New</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewMyReports.html');"><i class="fa fa-fw fa-file-text-o"></i> My Reports</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewEmployeesReport.html');"><i class="fa fa-fw fa-group"></i> Employee Reports</a></li>
           <li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewVehiclesReport.html');"><i class="fa fa-fw fa-car"></i> Vehicle Reports</a></li>
           <esd:SecurityAccessRequired permissions="BCS-VIEW-CONTRACTORS">
           		<li><a href="#" class="menuBCS" onclick="closeMenu();loadMainDivPage('viewAllContractors.html');"><i class="fa fa-fw fa-file-text-o"></i> Operator Reports</a></li>
        	</esd:SecurityAccessRequired>
        </ul>
     </li>
  </ul>
</div>


<p>
