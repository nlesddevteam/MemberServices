package com.esdnl.fund3.handler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.fund3.bean.ProjectEmployeeResponsibleBean;
import com.esdnl.fund3.bean.ProjectExpensesBean;
import com.esdnl.fund3.bean.ProjectFundingBean;
import com.esdnl.fund3.bean.ProjectRegionBean;
import com.esdnl.fund3.bean.ProjectSchoolBean;
import com.esdnl.fund3.dao.AuditLogManager;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.fund3.dao.ProjectEmployeeResponsibleManager;
import com.esdnl.fund3.dao.ProjectExpenseManager;
import com.esdnl.fund3.dao.ProjectFundingManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.fund3.dao.ProjectRegionManager;
import com.esdnl.fund3.dao.ProjectSchoolManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class EditProjectRequestHandler extends RequestHandlerImpl {
	public EditProjectRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			int projectid = form.getInt("id");
			ProjectBean origpb = ProjectManager.getProjectById(projectid);
			if(form.get("op") == null)
			{
				//add the project bean to the request
				request.setAttribute("project", origpb);
				//funding partners=5
				ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(5);
				request.setAttribute("items", items);
				//category = 6
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("catitems", catitems);
				//positions = 2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positems", positems);
				//report frequency = 7
				ArrayList<DropdownItemBean> freqitems = DropdownManager.getDropdownItems(7);
				request.setAttribute("freqitems", freqitems);
				//fiscal year = 4
				ArrayList<DropdownItemBean> fiscalitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalitems", fiscalitems);
				//add the region budget amount
				TreeMap<String,Double> tm = ProjectRegionManager.getProjectRegionByIdEdit(projectid);
				request.setAttribute("regionbud", tm);
				//add the regions that were used
				TreeMap<String,Double> tmm = ProjectRegionManager.getProjectRegionsByProject(projectid);
				request.setAttribute("regionbudused", tmm);
				//regions=3
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				path = "edit_project.jsp";
			}else{
				//now we get the form values and save them
				int id=form.getInt("id");
				//first we save the project info
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				ProjectBean pb = new ProjectBean();
				pb.setProjectId(id);
				pb.setProjectName(form.get("projectname"));
				pb.setProjectNumber(form.get("projectnumber"));
				pb.setProjectYear(form.getInt("lstfiscal"));
				if(form.get("datefundingapproved") == null)
				{
					pb.setDateFundingApproved(null);
				}else{
					pb.setDateFundingApproved(sdf.parse(form.get("datefundingapproved").toString()));
				}
				if(form.get("chktbdstart") == null){
					pb.setProjectStartDateTBD(0);
				}else{
					pb.setProjectStartDateTBD(1);
				}
				if(form.get("projectstartdate").isEmpty())
				{
					pb.setProjectStartDate(null);
				}else{
					pb.setProjectStartDate(sdf.parse(form.get("projectstartdate").toString()));
				}
				if(form.get("chktbdend") == null){
					pb.setProjectEndDateTBD(0);
				}else{
					pb.setProjectEndDateTBD(1);
				}
				if(form.get("projectenddate").isEmpty())
				{
					pb.setProjectEndDate(null);
				}else{
					pb.setProjectEndDate(sdf.parse(form.get("projectenddate").toString()));
				}
				pb.setProjectCategory(form.getInt("lstcategory"));
				pb.setPositionResponsible(form.getInt("lstposition"));
				pb.setEmployeeName("");pb.setEmployeeEmail("");
				pb.setEmployeePhone("");
				pb.setProjectDescription(form.get("projectdescription"));
				pb.setSpecialRequirements(form.get("specialreq"));
				pb.setReportRequested(form.getInt("reportreq"));
				if(form.get("firstreportdate").isEmpty())
				{
					pb.setFirstReportDate(null);
				}else{
					pb.setFirstReportDate(sdf.parse(form.get("firstreportdate").toString()));
				}			
				pb.setReportFrequency(form.getInt("lstreportfreq"));
				if(form.get("reportsentto").isEmpty())
				{
					pb.setReportEmail("");
				}else{
					pb.setReportEmail(form.get("reportsentto"));
				}
				AuditLogBean alb = new AuditLogBean();
				//check to see if project is being marked active/inactive
				if(form.getInt("isactive") != origpb.getIsActive())
				{
					if(origpb.getIsActive() == 0)
					{
						alb.setLogEntry("Project Status Changed to Active");
					}else{
						alb.setLogEntry("Project Status Changed to Inactive");
					}
				}
				alb.setProjectId(id);
				alb.setUserName(usr.getPersonnel().getFullNameReverse());
				AuditLogManager.addNewAuditLog(alb);
				
				pb.setIsActive(form.getInt("isactive"));
				pb.setAvailabilityNotes(form.get("anotes"));
				//pb.setStatusNotes(form.get("statusnotes"));
				pb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				ProjectManager.updateNewProject(pb);
				//now we update the audit log
				
				alb.setUserName(usr.getPersonnel().getFullNameReverse());
				alb.setProjectId(id);
				alb.setLogEntry("Project Updated BY: [" + usr.getPersonnel().getFullNameReverse() + "]");
				AuditLogManager.addNewAuditLog(alb);
				//add audit log for position responsibile
				//now we update the audit log
				if(origpb.getEmployeeName() != form.get("employeename"))
				{
					alb.setUserName(usr.getPersonnel().getFullNameReverse());
					alb.setLogEntry("Position Responsible For Project Updated to: [" + pb.getEmployeeName()  + "]");
					alb.setProjectId(id);
					AuditLogManager.addNewAuditLog(alb);
				}

				//int id = ProjectManager.addNewProject(pb);
				//we save the child objects to their tables
				//project-region-budgets
				//eastern-15 central-16 western-17 labrador-18 provincial-19
				//remove the regions and add them again
				ProjectRegionManager.deleteRegion(id);
				if(!(form.get("chkeastern") == null)){
					ProjectRegionBean prb = new ProjectRegionBean();
					prb.setRegionId(1);
					prb.setProjectId(id);
					prb.setBudgetAmount(form.getDouble("easternbudget"));
					ProjectRegionManager.addNewProjectRegion(prb);
				}
				if(!(form.get("chkcentral") == null)){
					ProjectRegionBean prb = new ProjectRegionBean();
					prb.setRegionId(2);
					prb.setProjectId(id);
					prb.setBudgetAmount(form.getDouble("centralbudget"));
					ProjectRegionManager.addNewProjectRegion(prb);
				}
				if(!(form.get("chkwestern") == null)){
					ProjectRegionBean prb = new ProjectRegionBean();
					prb.setRegionId(3);
					prb.setProjectId(id);
					prb.setBudgetAmount(form.getDouble("westernbudget"));
					ProjectRegionManager.addNewProjectRegion(prb);
				}				
				if(!(form.get("chklabrador") == null)){
					ProjectRegionBean prb = new ProjectRegionBean();
					prb.setRegionId(4);
					prb.setProjectId(id);
					prb.setBudgetAmount(form.getDouble("labradorbudget"));
					ProjectRegionManager.addNewProjectRegion(prb);
				}
				if(!(form.get("chkprovincial") == null)){
					ProjectRegionBean prb = new ProjectRegionBean();
					prb.setRegionId(5);
					prb.setProjectId(id);
					prb.setBudgetAmount(form.getDouble("provincialbudget"));
					ProjectRegionManager.addNewProjectRegion(prb);
				}
				//next we loop through the schools if any
				ProjectSchoolManager.deleteSchool(id);
				String[] schools = form.getArray("schoolid[]");
				if(schools.length >0)
				{
					String[] budgets= form.getArray("budget[]");
					int x=0;
					while(x < schools.length)
					{
						if(!(schools[x].isEmpty())){
							ProjectSchoolBean psb = new ProjectSchoolBean();
							psb.setProjectId(id);
							psb.setSchoolId(Integer.parseInt(schools[x]));
							psb.setBudgetAmount(Double.parseDouble(budgets[x]));
							ProjectSchoolManager.addNewProjectSchool(psb);
							
						}
						x++;
					}
				}
				//next we loop through the funding partners
				ProjectFundingManager.deleteFunding(id);
				String[] partners = form.getArray("partnerid[]");
				String[] partnerstext = form.getArray("partner[]");
				if(partners.length >0)
				{
					String[] contactnames= form.getArray("contact[]");
					String[] contactemails= form.getArray("email[]");
					String[] contactphones= form.getArray("phone[]");
					int x=0;
					while(x < partners.length)
					{
						if(!(partners[x].isEmpty())){
							ProjectFundingBean psb = new ProjectFundingBean();
							psb.setProjectId(id);
							psb.setFundingId(Integer.parseInt(partners[x]));
							psb.setContactName(contactnames[x]);
							psb.setContactEmail(contactemails[x]);
							psb.setContactPhone(contactphones[x]);
							ProjectFundingManager.addNewProjectFunding(psb);
							//now we update the audit log
							AuditLogBean albn = new AuditLogBean();
							albn.setUserName(usr.getPersonnel().getFullNameReverse());
							albn.setLogEntry("Added Funding Partner: [" + partnerstext[x]  + "]");
							albn.setProjectId(id);
							AuditLogManager.addNewAuditLog(albn);
							
						}
						x++;
					}
				}
				//next we loop through the employees responsible
				ProjectEmployeeResponsibleManager.deleteEmployeesResponsible(id);
				String[] employeesregs = form.getArray("eregionid[]");
				String[] employeesregstext = form.getArray("regionspr[]");
				if(employeesregs.length >0)
				{
					String[] employeenames= form.getArray("namepr[]");
					String[] employeeemails= form.getArray("emailpr[]");
					String[] employeephones= form.getArray("phonepr[]");
					
					int x=0;
					while(x < employeesregs.length)
					{
						if(!(employeesregs[x].isEmpty())){
							ProjectEmployeeResponsibleBean psb = new ProjectEmployeeResponsibleBean();
							
							psb.setProjectId(id);
							psb.setRegionId(Integer.parseInt(employeesregs[x]));
							psb.setEmployeeName(employeenames[x]);
							psb.setEmployeeEmail(employeeemails[x]);
							psb.setEmployeePhone(employeephones[x]);
							ProjectEmployeeResponsibleManager.addNewEmployeeResponsible(psb);
							//now we update the audit log
							AuditLogBean albn = new AuditLogBean();
							albn.setUserName(usr.getPersonnel().getFullNameReverse());
							albn.setLogEntry("Added Employee Responsible: [" + employeenames[x]+ "(" +  employeesregstext[x]  + ")]");
							albn.setProjectId(id);
							AuditLogManager.addNewAuditLog(albn);
							
						}
						x++;
					}
				}	
				//next we loop through the project expenses
				ProjectExpenseManager.deleteExpenses(id);
				String[] expenses = form.getArray("expense[]");
				if(expenses.length >0)
				{
					int x=0;
					while(x < expenses.length)
					{
						if(!(expenses[x].isEmpty())){
							ProjectExpensesBean psb = new ProjectExpensesBean();
							psb.setProjectId(id);
							psb.setExpenseDetails(expenses[x]);
							ProjectExpenseManager.addNewProjectExpense(psb);
							
						}
						x++;
					}
				}
				ProjectBean pbb = ProjectManager.getProjectById(id);
				request.setAttribute("project", pbb);
				//funding partners=5
				ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(5);
				request.setAttribute("items", items);
				//category = 6
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("catitems", catitems);
				//positions = 2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positems", positems);
				//report frequency = 7
				ArrayList<DropdownItemBean> freqitems = DropdownManager.getDropdownItems(7);
				request.setAttribute("freqitems", freqitems);
				//fiscal year = 4
				ArrayList<DropdownItemBean> fiscalitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalitems", fiscalitems);
				//add the region budget amount
				TreeMap<String,Double> tm = ProjectRegionManager.getProjectRegionByIdEdit(projectid);
				request.setAttribute("regionbud", tm);
				//add the regions that were used
				TreeMap<String,Double> tmm = ProjectRegionManager.getProjectRegionsByProject(projectid);
				request.setAttribute("regionbudused", tmm);
				request.setAttribute("msg", "Project has been updated!");
				path = "edit_project.jsp";
				
				
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				//funding partners=5
				ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(5);
				request.setAttribute("items", items);
				//category = 6
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("catitems", catitems);
				//positions = 2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positems", positems);
				//report frequency = 7
				ArrayList<DropdownItemBean> freqitems = DropdownManager.getDropdownItems(7);
				request.setAttribute("freqitems", freqitems);
				//fiscal year = 4
				ArrayList<DropdownItemBean> fiscalitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalitems", fiscalitems);

			} catch (Fund3Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			path = "edit_project.jsp";
		}
		return path;
	}


}
