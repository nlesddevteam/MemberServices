package com.awsd.ppgp.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.ppgp.PPGP;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPGoal;
import com.awsd.ppgp.PPGPTask;
import com.awsd.ppgp.TaskCategoryBean;
import com.awsd.ppgp.TaskCategoryManager;
import com.awsd.ppgp.TaskDomainBean;
import com.awsd.ppgp.TaskDomainManager;
import com.awsd.ppgp.TaskDomainStrengthBean;
import com.awsd.ppgp.TaskDomainStrengthManager;
import com.awsd.ppgp.TaskGradeBean;
import com.awsd.ppgp.TaskGradeManager;
import com.awsd.ppgp.TaskSpecificTopicBean;
import com.awsd.ppgp.TaskSpecificTopicManager;
import com.awsd.ppgp.TaskSubjectBean;
import com.awsd.ppgp.TaskSubjectManager;
import com.awsd.ppgp.TaskTopicAreaBean;
import com.awsd.ppgp.TaskTopicAreaManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ModifyGrowthPlanTaskRequestHandler extends RequestHandlerImpl {

	public ModifyGrowthPlanTaskRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Personnel teacher = null;

		PPGP ppgp = null;
		PPGPGoal goal = null;
		PPGPTask task = null;

		Boolean policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null) && !(usr.checkPermission("PPGP-VIEW-SUMMARY"))) {
			path = "policy.jsp";
		}
		

		if (form.exists("pid")) {
			teacher = PersonnelDB.getPersonnel(form.getInt("pid"));
			request.setAttribute("TEACHER", teacher);
		}

		ppgp = PPGPDB.getPPGP(form.getInt("pgpid"));
		goal = ppgp.get(form.getInt("gid"));
		task = goal.get(form.getInt("tid"));

		task.setDescription(form.get("Task"));
		task.setSchoolSupport(form.get("SchoolSupport"));
		task.setDistrictSupport(form.get("DistrictSupport"));
		task.setCompletionDate((new SimpleDateFormat("dd-MMM-yyyy")).format(form.getDate("CompletionDate")));
		task.setSelfEvaluation(form.get("SelfEvaluation"));
		//task.setCategory(TaskCategoryManager.getTaskCategoryBean(form.getInt("cat_id")));
		//if (form.exists("grade_id"))
			//task.setGrade(TaskGradeManager.getTaskGradeBean(form.getInt("grade_id")));
		//if (form.exists("subject_id"))
			//task.setSubject(TaskSubjectManager.getTaskSubjectBean(form.getInt("subject_id")));
		//task.setTopic(TaskTopicAreaManager.getTaskTopicAreaBean(form.getInt("topic_id")));
		//task.setSpecificTopic(TaskSpecificTopicManager.getTaskSpecificTopicBean(form.getInt("stopic_id")));
		String[] testyear = ppgp.getSchoolYear().split("-");
		if(Integer.parseInt(testyear[1]) > 2017){
			task.setCategory(new TaskCategoryBean());
			task.setGrade(new TaskGradeBean());
			task.setSubject(new TaskSubjectBean());
			task.setTopic(new TaskTopicAreaBean());
			task.setSpecificTopic(new TaskSpecificTopicBean());
			task.setDomain(TaskDomainManager.getTaskDomainBeanById(form.getInt("selectdomain")));
			task.setStrength(TaskDomainStrengthManager.getTaskDomainStrenthBeanById(form.getInt("selectstrength")));
			System.out.println(form.getInt("selectstrength"));
		}else{
			task.setCategory(TaskCategoryManager.getTaskCategoryBean(form.getInt("cat_id")));
			
			if(form.getInt("cat_id") > 1){
				task.setGrade(new TaskGradeBean());
				task.setSubject(new TaskSubjectBean());
				task.setTopic(TaskTopicAreaManager.getTaskTopicAreaBean((form.getInt("topic_id"))));
				task.setSpecificTopic(TaskSpecificTopicManager.getTaskSpecificTopicBean(form.getInt("stopic_id")));
			}else{
				task.setGrade(TaskGradeManager.getTaskGradeBean(form.getInt("grade_id")));
				task.setSubject(TaskSubjectManager.getTaskSubjectBean(form.getInt("subject_id")));
				task.setTopic(TaskTopicAreaManager.getTaskTopicAreaBean((form.getInt("topic_id"))));
				task.setSpecificTopic(TaskSpecificTopicManager.getTaskSpecificTopicBean(form.getInt("stopic_id")));
			}
			
			task.setDomain(new TaskDomainBean());
			task.setStrength(new TaskDomainStrengthBean());
		}
		
		
		
		task.setTechnologySupport(form.get("TechSupport"));
		task.setTechnologySchoolSupport(form.get("TechSchoolSupport"));
		task.setTechnologyDistrictSupport(form.get("TechDistrictSupport"));

		PPGPDB.modifyPPGPGoalTask(task);

		request.setAttribute("PPGP", ppgp);
		request.setAttribute("msgupdate", "Task has been updated");
		if (ppgp.isSelfReflectionComplete())
			request.setAttribute("REFRESH_ARCHIVE", new Boolean(true));

		path = "teacherSummary.jsp";
			
		

		return path;
	}
}