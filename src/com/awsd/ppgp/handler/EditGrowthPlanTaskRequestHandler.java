package com.awsd.ppgp.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.ppgp.PPGP;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.PPGPGoal;
import com.awsd.ppgp.PPGPTask;
import com.awsd.ppgp.TaskCategoryManager;
import com.awsd.ppgp.TaskDomainManager;
import com.awsd.ppgp.TaskDomainStrengthManager;
import com.awsd.ppgp.TaskGradeManager;
import com.awsd.ppgp.TaskSpecificTopicManager;
import com.awsd.ppgp.TaskSubjectManager;
import com.awsd.ppgp.TaskTopicAreaManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class EditGrowthPlanTaskRequestHandler extends RequestHandlerImpl {

	public EditGrowthPlanTaskRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("tid"), new RequiredFormElement("gid"), new RequiredFormElement("ppgpid")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		Boolean policyRead = null;

		PPGP ppgp = null;
		PPGPGoal goal = null;
		PPGPTask task = null;

		super.handleRequest(request, response);

		policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null) && !(usr.checkPermission("PPGP-VIEW-SUMMARY"))) {
			path = "policy.jsp";
		}
		else {
			if (validate_form()) {

				ppgp = PPGPDB.getPPGP(form.getInt("ppgpid"));
				goal = ppgp.get(form.getInt("gid"));
				task = goal.get(form.getInt("tid"));

				request.setAttribute("PPGP", ppgp);
				request.setAttribute("PPGP_GOAL", goal);
				request.setAttribute("PPGP_TASK", task);

				request.setAttribute("cat_id", Integer.toString(task.getCategory().getCategoryID()));
				if (task.getCategory().getCategoryID() == 1) {
					request.setAttribute("grade_id", Integer.toString(task.getGrade().getGradeID()));
					request.setAttribute("subject_id", Integer.toString(task.getSubject().getSubjectID()));
					//now we add the dropdown values to setup initial state
					request.setAttribute("subjects", TaskSubjectManager.getTaskSubjectBeans(task.getGrade().getGradeID()));
					request.setAttribute("topics", TaskTopicAreaManager.getTaskTopicAreaBeans(task.getCategory().getCategoryID(), task.getGrade().getGradeID(), task.getSubject().getSubjectID()));
					request.setAttribute("stopics", TaskSpecificTopicManager.getTaskSpecificTopicBeans(task.getCategory().getCategoryID(), task.getGrade().getGradeID(), task.getSubject().getSubjectID(),task.getTopic().getTopicID()));
				}else{
					request.setAttribute("topics", TaskTopicAreaManager.getTaskTopicAreaBeans(task.getCategory().getCategoryID()));
					request.setAttribute("stopics", TaskSpecificTopicManager.getTaskSpecificTopicBeans(task.getCategory().getCategoryID(),task.getTopic().getTopicID()));
				}
				request.setAttribute("topic_id", Integer.toString(task.getTopic().getTopicID()));
				request.setAttribute("stopic_id", Integer.toString(task.getSpecificTopic().getSpecificTopicID()));
				request.setAttribute("Task", task.getDescription());
				request.setAttribute("SchoolSupport", task.getSchoolSupport());
				request.setAttribute("DistrictSupport", task.getDistrictSupport());
				try {
					request.setAttribute(
							"CompletionDate",
							(new SimpleDateFormat("dd/MM/yyyy")).format(((new SimpleDateFormat("dd-MMM-yyyy")).parse(task.getCompletionDate()))));
				}
				catch (ParseException e) {
					request.setAttribute("msg", "Invalid date format.");
				}
				request.setAttribute("SelfEvaluation", task.getSelfEvaluation());
				request.setAttribute("TechSupport", task.getTechnologySupport());
				request.setAttribute("TechSchoolSupport", task.getTechnologySchoolSupport());
				request.setAttribute("TechDistrictSupport", task.getTechnologyDistrictSupport());
				request.setAttribute("domains", TaskDomainManager.getTaskDomainBeans());
				request.setAttribute("cats", TaskCategoryManager.getTaskCategoryBeansMap());
				request.setAttribute("grades", TaskGradeManager.getTaskGradeBeansMap());
				//added fields for 2018
				if(task.getDomain() == null){
					request.setAttribute("domainid", "0");
				}else{
					request.setAttribute("domainid", task.getDomain().getDomainID());
				}
				if(task.getStrength() == null){
					request.setAttribute("strengthid", "0");
				}else{
					//add dropdown values
					if(task.getStrength().getStrengthID() > 0){
						request.setAttribute("strengths", TaskDomainStrengthManager.getTaskDomainStrengthBeansById(task.getDomain().getDomainID()));
					}
					
					request.setAttribute("strengthid", task.getStrength().getStrengthID());
				}
				

			}
			else
				throw new PPGPException("Task ID required for deletion.");

			path = "growthPlan.jsp";
		}

		return path;
	}
}