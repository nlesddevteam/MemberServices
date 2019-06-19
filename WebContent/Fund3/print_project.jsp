<html>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<link href='includes/css/fund3.css' rel='stylesheet' type='text/css'>
<head>
<title>Print</title>
<style>
tr.border_bottom td {
  border-bottom:1pt solid black;
}
</style>
</head>
<body style='background-color:white'>
	<table width='100%'><tr><td align='center' colspan='2'><img src='includes/images/heading.png' alt='' width='500' height='100' border='0'></td></tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Project Name: </td>
			<td>
				${project.projectName}
			</td>
		</tr>
		<tr class="border_bottom">
			<td  nowrap  class='fund3formtext'>Project Status:</td>
			<c:choose>
							<c:when test="${project.projectStatus eq 1}">
						<td>
							<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
								APPROVED BY ${project.statusBy } ON ${project.statusDateFormatted }
								<br />
								${project.statusNotes }
							</p>
						</td>
							</c:when>
							<c:when test="${project.projectStatus eq 2}">
								<td>
									<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
										REJECTED BY ${project.statusBy } ON ${project.statusDateFormatted }
								<br />
								${project.statusNotes }
							</p>
						</td>										
							</c:when>
							<c:otherwise>
						<td>
							<p class="messageText" style="padding-top:5px;padding-bottom:5px;">
								SUBMITTED BY ${project.statusBy } ON ${project.statusDateFormatted }
								<br />
								${project.statusNotes }	
								</p>
							</td>									
							</c:otherwise>
				</c:choose>
		</tr>
		<tr class="border_bottom">
			<td  nowrap class='fund3formtext'>Project Availability:</td>
			<td>
				<c:choose>
							<c:when test="${project.isActive eq 1}">
									Active
							</c:when>
							<c:otherwise>
									Inactive
							</c:otherwise>
				</c:choose>

			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Availability Notes: </td>
			<td>
				${project.availabilityNotes}
			</td>
		</tr>
		<tr class="border_bottom">
			<td  nowrap class='fund3formtext'>Project Number</td>
			<td>
				${project.projectNumber}
			</td>
		</tr>
		<tr>
			<td nowrap class='fund3formtext' width='30%' colspan='2'>Project Region(s): </td>
			
		</tr>
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<tr>
					<td class='fund3formtext'>
						Region
					</td>
					<td class='fund3formtext'>
						Budget
					</td>
				</tr>
				<c:forEach var="entry" items="${regionbud}">
					<c:choose>
						<c:when test="${entry.value > 0}">
							<tr>
								<td>${entry.key}</td>
								<td>
									<fmt:formatNumber type="number" pattern="###.00" value="${entry.value}" />
								</td>
							</tr>
						</c:when>		
					</c:choose>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' colspan='2'>School Budget(s): </td>
			
		</tr>		
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<tr>
					<td class='fund3formtext'>
						School
					</td>
					<td class='fund3formtext'>
						Budget
					</td>
				</tr>
				<c:forEach var="entry" items="${project.projectSchool}">
				<tr>
					<td>${entry.school}</td>
					<td>
						<fmt:formatNumber type="number" pattern="###.00" value="${entry.budgetAmount}" />
					</td>
				</tr>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' colspan='2'>Funding Partner(s): </td>
			
		</tr>		
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<tr>
					<td class='fund3formtext'>
						Partner
					</td>
					<td class='fund3formtext'>
						Contact
					</td>
					<td class='fund3formtext'>
						Email
					</td>
					<td class='fund3formtext'>
						Phone
					</td>					
				</tr>
				<c:forEach var="entry" items="${project.projectFunding}">
				<tr>
					<td>${entry.fundingText}</td>
					<td>${entry.contactName }</td>
					<td>${entry.contactEmail}</td>
					<td>${entry.contactPhone}</td>
					
				</tr>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Fiscal Year: </td>
			<td>
				<c:forEach var="positem" items="${fiscalitems}" >
					<c:choose>
						<c:when test="${project.projectYear eq positem.id}">
							${positem.ddText}
						</c:when>
					</c:choose>
				</c:forEach>
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Date Funding Approved: </td>
			<td>
				${project.dateFundingApprovedFormatted }
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext'>Project Start Date:</td>
			<td>
				<c:choose>
					<c:when test="${project.projectStartDateTBD eq 1}">
						TBD
					</c:when>
					<c:otherwise>
						${project.projectStartDateFormatted }
					</c:otherwise>
				</c:choose>
				<br />
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext'>Project End Date:</td>
			<td>
				<c:choose>
					<c:when test="${project.projectEndDateTBD eq 1}">
						TBD
					</c:when>
					<c:otherwise>
						${project.projectEndDateFormatted }
					</c:otherwise>
				</c:choose>
				<br />
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext'>Category</td>
			<td>
				<c:forEach var="catitem" items="${catitems}" >
					<c:choose>
							<c:when test="${project.projectCategory eq catitem.id}">
								${catitem.ddText}
							</c:when>
					</c:choose>
       			</c:forEach>
        	</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext'>Position Responsible for Project:</td>
				<td>
					<c:forEach var="positem" items="${positems}" >
						<c:choose>
									<c:when test="${project.positionResponsible eq positem.id}">
										${positem.ddText}
									</c:when>
						</c:choose>
         			</c:forEach>
				</td>
		</tr>
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<tr>
					<td class='fund3formtext'>
						Region
					</td>
					<td class='fund3formtext'>
						Name
					</td>
					<td class='fund3formtext'>
						Email
					</td>
					<td class='fund3formtext'>
						Phone
					</td>					
				</tr>
				<c:forEach var="entry" items="${project.projectEmpRes}">
				<tr>
					<td>${entry.regionText }</td>
					<td>${entry.employeeName }</td>
					<td>${entry.employeeEmail }@nlesd.ca</td>
					<td>${entry.employeePhone}</td>
					
				</tr>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="border_bottom">
			<td  nowrap class='fund3formtext'>Description of Project:</td>
			<td>
				${project.projectDescription }
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' colspan='2'>Expense(s): </td>
			
		</tr>		
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<c:forEach var="entry" items="${project.projectExpense}">
				<tr>
					<td>${entry.expenseDetails }</td>
				</tr>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="border_bottom">
			<td  nowrap class='fund3formtext'>Special Requirements:</td>
			<td>
				${project.specialRequirements}
			</td>
		</tr>
		<tr class="border_bottom">
			<td  nowrap class='fund3formtext'>Report Required:</td>
			<td>
				<c:choose>
							<c:when test="${project.reportRequested eq 1}">
									Yes
							</c:when>
							<c:otherwise>
									No
							</c:otherwise>
				</c:choose>

			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Date of First Report to Be Submitted: </td>
			<td>
				${project.firstReportDateFormatted }
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext'>Report Frequency:</td>
				<td>
					<c:forEach var="positem" items="${freqitems}" >
						<c:choose>
									<c:when test="${project.reportFrequency eq freqitem.id}">
										${freqitem.ddText}
									</c:when>
						</c:choose>
         			</c:forEach>
				</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' >Report Required to be Sent to Whom: </td>
			<td>
				${project.reportEmail }@nlesd.ca
			</td>
		</tr>
		<tr class="border_bottom">
			<td nowrap class='fund3formtext' width='30%' colspan='2'>Project Documents(s): </td>
			
		</tr>		
		<tr class="border_bottom">
			<td colspan='2' align='center'> 
				<table  width='75%' border='1'>
				<tr>
					<td class='fund3formtext'>
						Document
					</td>
					<td class='fund3formtext'>
						Added By
					</td>
					<td class='fund3formtext'>
						Date Added
					</td>					
				</tr>
				<c:forEach var="p" items="${project.projectDocuments}">
				<tr>
					<td>${p.fileName}</td>
					<td>${p.addedBy }</td>
					<td>${p.dateAddedFormatted}</td>
					
				</tr>
				</c:forEach>
				</table>
			</td>
		</tr>																																												
	</table>
	<br />
	<script>
	 window.print();
	 setTimeout(window.close, 0);
	
	</script>
</body>
</html>