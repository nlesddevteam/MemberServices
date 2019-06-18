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

public class AddGrowthPlanRequestHandler extends RequestHandlerImpl {

	public AddGrowthPlanRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Boolean policyRead = null;

		String goal = "";
		String task = "";
		String ssupport = "";
		String dsupport = "";
		String completion = "";
		String selfeval = "";
		int cat_id = 0;
		int grade_id = 0;
		int subject_id = 0;
		int topic_id = 0;
		int stopic_id = 0;
		PPGP ppgp = null;
		PPGPGoal g = null;

		policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if (policyRead == null) {
			path = "policy.jsp";
		}
		else {
			request.setAttribute("PPGP", (ppgp = PPGPDB.getPPGP(form.getInt("pgpid"))));

			if (form.exists("gid"))
				request.setAttribute("PPGP_GOAL", (g = PPGPDB.getPPGPGoal(form.getInt("gid"))));
			
			// save form state
			request.setAttribute("Goal", form.get("Goal"));
			request.setAttribute("cat_id", form.get("cat_id"));
			request.setAttribute("grade_id", form.get("grade_id"));
			request.setAttribute("subject_id", form.get("subject_id"));
			request.setAttribute("topic_id", form.get("topic_id"));
			request.setAttribute("stopic_id", form.get("stopic_id"));
			request.setAttribute("Task", form.get("Task"));
			request.setAttribute("SchoolSupport", form.get("SchoolSupport"));
			request.setAttribute("DistrictSupport", form.get("DistrictSupport"));
			request.setAttribute("CompletionDate", form.get("CompletionDate"));
			request.setAttribute("SelfEvaluation", form.get("SelfEvaluation"));
			request.setAttribute("Goal", (goal = form.get("Goal")));
			request.setAttribute("cat_id", (cat_id = form.getInt("cat_id")));

			if (cat_id == 1)
				request.setAttribute("grade_id", (grade_id = form.getInt("grade_id")));

			if (cat_id == 1)
				request.setAttribute("subject_id", (subject_id = form.getInt("subject_id")));
			
			if(form.exists("topic_id")){
				request.setAttribute("topic_id", (topic_id = form.getInt("topic_id")));
			}else{
				request.setAttribute("topic_id", 0);
			}
			if(form.exists("stopic_id")){
				request.setAttribute("stopic_id", (topic_id = form.getInt("stopic_id")));
			}else{
				request.setAttribute("stopic_id", 0);
			}
			
			request.setAttribute("Task", (task = request.getParameter("Task")));
			request.setAttribute("SchoolSupport", (ssupport = request.getParameter("SchoolSupport")));
			request.setAttribute("DistrictSupport", (dsupport = request.getParameter("DistrictSupport")));
			request.setAttribute("CompletionDate", (completion = request.getParameter("CompletionDate")));


			try {
				completion = (new SimpleDateFormat("dd-MMM-yyyy")).format((new SimpleDateFormat("dd/MM/yyyy")).parse(completion));
			}
			catch (ParseException e) {
				throw new PPGPException("Completion Date formated improperly.");
			}
			request.setAttribute("SelfEvaluation", (selfeval = request.getParameter("SelfEvaluation")));

			if (!form.exists("gid"))
				request.setAttribute("PPGP_GOAL", (g = PPGPDB.addPPGPGoal(ppgp, new PPGPGoal(goal))));

			PPGPTask task_obj = new PPGPTask();
			task_obj.setDescription(task);
			task_obj.setSchoolSupport(ssupport);
			task_obj.setDistrictSupport(dsupport);
			task_obj.setCompletionDate(completion);
			task_obj.setSelfEvaluation(selfeval);
			//check to see if new dropdowns or old ones are used
			String[] testyear = ppgp.getSchoolYear().split("-");
			if(Integer.parseInt(testyear[1]) > 2017){
				task_obj.setCategory(new TaskCategoryBean());
				task_obj.setGrade(new TaskGradeBean());
				task_obj.setSubject(new TaskSubjectBean());
				task_obj.setTopic(new TaskTopicAreaBean());
				task_obj.setSpecificTopic(new TaskSpecificTopicBean());
				task_obj.setDomain(TaskDomainManager.getTaskDomainBeanById(form.getInt("selectdomain")));
				task_obj.setStrength(TaskDomainStrengthManager.getTaskDomainStrenthBeanById(form.getInt("selectstrength")));
				System.out.println(form.getInt("selectstrength"));
			}else{
				task_obj.setCategory(TaskCategoryManager.getTaskCategoryBean(cat_id));
				task_obj.setGrade(TaskGradeManager.getTaskGradeBean(grade_id));
				task_obj.setSubject(TaskSubjectManager.getTaskSubjectBean(subject_id));
				task_obj.setTopic(TaskTopicAreaManager.getTaskTopicAreaBean(topic_id));
				task_obj.setSpecificTopic(TaskSpecificTopicManager.getTaskSpecificTopicBean(stopic_id));
				task_obj.setDomain(new TaskDomainBean());
				task_obj.setStrength(new TaskDomainStrengthBean());
			}

			task_obj.setTechnologySupport(form.get("TechSupport"));
			task_obj.setTechnologySchoolSupport(form.get("TechSchoolSupport"));
			task_obj.setTechnologyDistrictSupport(form.get("TechDistrictSupport"));

			PPGPDB.addPPGPGoalTask(g, task_obj);

			// clear state
			request.setAttribute("Goal", null);
			request.setAttribute("cat_id", null);
			request.setAttribute("grade_id", null);
			request.setAttribute("subject_id", null);
			request.setAttribute("topic_id", null);
			request.setAttribute("stopic_id", null);
			request.setAttribute("Task", null);
			request.setAttribute("SchoolSupport", null);
			request.setAttribute("DistrictSupport", null);
			request.setAttribute("CompletionDate", null);
			request.setAttribute("SelfEvaluation", null);

			request.setAttribute("msgupdate", "Goal/Task Submitted Successfully");
			
			request.setAttribute("cats", TaskCategoryManager.getTaskCategoryBeansMap());
			request.setAttribute("grades", TaskGradeManager.getTaskGradeBeansMap());
			request.setAttribute("domains", TaskDomainManager.getTaskDomainBeans());
				
			

			path = "growthPlan.jsp";
		}

		return path;
	}
}