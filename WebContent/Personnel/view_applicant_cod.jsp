<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<job:ApplicantLoggedOn/>



<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>		


<style>
@media screen {
  #printSection {
      display: none;
  }
}

@media print {
  body * {
    visibility:hidden;
  }
  #printSection, #printSection * {
    visibility:visible;
  }
  #printSection {
    position:absolute;
    left:0;
    top:0;
  }
  #print-footer {
    display: block;
    position: fixed;
    bottom: 0;
    left:0;
}
}
</style>
</head>
<body>
    <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Criminal Offense Declaration</b></div>
      			 	<div class="panel-body">
  			<table width="700" style="font-size:11px;border:0;" align="center">	
  			<tr><td>					
         <div id="printThis" style="width:100%;max-width:700px;">
         <div align="center"><img src="includes/img/empltr_header.png" border="0" width='700' /></div>
         <br/>
         <table width="100%" style="font-size:11px;border:0;" align="center">
         <tr><td width="20%"><b>NAME:</b></td><td width="30%">${cod.applicant.fullName}</td><td width="20%"><b>DATE OF BIRTH:</b></td><td width="30%">${cod.applicant.DOB ne null ? cod.applicant.DOBFormatted : "NOT RECORDED"}</td></tr>
         <tr><td width="20%"><b>POSITION:</b></td><td width="35%">${cod.position}</td><td width="20%"><b>LOCATION/SCHOOL:</b></td><td width="30%">${cod.location}</td></tr>
         
         </table>
       <br/>
       <b>DECLARATION DATE:</b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${cod.declarationDate}" />
       <br/><br/>
       
                    <span style="font-size:11px;">               
              
				            				<ol>
				            					<li>
				            						<b>I DECLARE</b>, since the last Criminal Reference Check (CRC) provided to Newfoundland and Labrador English School District, or since the last Criminal Offence Declaration (COD) given by me to the district, that:
				            							<ol style='padding-top:5px;list-style-type: lower-alpha;'>
				            								<li>
				            									I have <b>no</b> convictions under the <i>Criminal Code of Canada</i> up and including the date of this
				            									declaration for which a pardon has not been issued or granted under the <i>Criminal Records Act (Canada)</i>.
				            								</li>
				            								<li>
				            									I have <b>no</b> charges pending under the <i>Criminal Code of Canada</i> up to and including the date
				            									of this declaration.
				            								</li>
				            							</ol>
				            					</li>
				            				</ol>
				            		<br/>
				            		<b>OR</b>
				            		<br/>
				            	
				            				<ol start='2'>
				            					<li>
				            						I have the following convictions/charges for offences under the <i>Criminal Code of Canada</i>
				            						for which a pardon under the <i>Criminal Records Act (Canada)</i> has <b>not</b> been
				            						issued or granted.<br>
				            						<b>List of Offences:</b>
				            						<c:choose>
				            							<c:when test="${fn:length(cod.offences) gt 0}">				
				              						<ol id='ol-offences'>
				              							<c:forEach items="${cod.offences}" var='codo'>
				               							<li>
				               								<ol style='list-style-type: lower-alpha;'>
				               									<li><b>Date:</b>&nbsp;&nbsp;<fmt:formatDate type="both" dateStyle="medium" value="${codo.offenceDate}" /></li>
				               									<li><b>Court Location:</b>&nbsp;&nbsp;${codo.courtLocation}</li>
				               									<li><b>Conviction/Charge:</b>&nbsp;&nbsp;${codo.conviction}</li>
				               								</ol>
				               							</li>
				              							</c:forEach>
				              						</ol>
				            							</c:when>
				            							<c:otherwise>
				            								<br /><span style='color:red;padding:0px;'>No offences listed.</span>
				            							</c:otherwise>
				            						</c:choose>
				            					</li>
				            				</ol>
         </span>     
         <br/><br/>
         <div id="print-footer">
         <div align="center"><img src="includes/img/empltr_footer.png" border="0" width='700' /></div>
         </div>
       </div>
       </td></tr></table>
       
       <div align="center"><button id="btnPrint" type="button" class="btn btn-sm btn-primary">Print This Letter</button></div>
         
         </div></div></div>
	

<script>
document.getElementById("btnPrint").onclick = function () {
    printElement(document.getElementById("printThis"));
}
function printElement(elem) {
    var domClone = elem.cloneNode(true);    
    var $printSection = document.getElementById("printSection");    
    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }    
    $printSection.innerHTML = "";
    $printSection.appendChild(domClone);
    window.print();
}
</script>

	</body>
</html>
