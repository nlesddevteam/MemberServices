package com.awsd.ppgp.handler;

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.SearchResults;
import com.awsd.ppgp.TaskCategoryManager;
import com.awsd.ppgp.TaskDomainManager;
import com.awsd.ppgp.TaskDomainStrengthManager;
import com.awsd.ppgp.TaskGradeManager;
import com.awsd.ppgp.TaskSpecificTopicManager;
import com.awsd.ppgp.TaskSubjectManager;
import com.awsd.ppgp.TaskTopicAreaManager;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.form.element.DependentFormElements;
import com.esdnl.servlet.form.element.RequiredValueGreaterThanFormElement;
import com.esdnl.util.StringUtils;

public class SearchPGPRequestHandler extends RequestHandlerImpl {

	public SearchPGPRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		int cat_id = -1;
		int grade_id = -1;
		int subject_id = -1;
		int topic_id = -1;
		int stopic_id = -1;
		int domain_id = -1;
		int strength_id = -1;
		

		SearchResults results = null;

		cat_id = (form.exists("cat_id") ? form.getInt("cat_id") : 0);
		grade_id = (form.exists("grade_id") ? form.getInt("grade_id") : 0);
		subject_id = (form.exists("subject_id") ? form.getInt("subject_id") : 0);
		topic_id = (form.exists("topic_id") ? form.getInt("topic_id") : 0);
		stopic_id = (form.exists("stopic_id") ? form.getInt("stopic_id") : 0);
		domain_id = (form.exists("selectdomain") ? form.getInt("selectdomain") : 0);
		strength_id = (form.exists("selectstrength") ? form.getInt("selectstrength") : 0);

		// save search state
		request.setAttribute("year", form.get("year"));
		request.setAttribute("region", form.get("region"));
		request.setAttribute("keywords", form.get("keywords"));
		request.setAttribute("cat_id", form.get("cat_id"));
		request.setAttribute("grade_id", form.get("grade_id"));
		request.setAttribute("subject_id", form.get("subject_id"));
		request.setAttribute("topic_id", form.get("topic_id"));
		request.setAttribute("stopic_id", form.get("stopic_id"));
		request.setAttribute("selectstrength", form.get("selectstrength"));
		request.setAttribute("selectdomain", form.get("selectdomain"));

		if (form.exists("op")) {
			if (form.hasValue("op", "SUBMIT_CATEGORY")) {
				this.validator = new FormValidator(new FormElement[] {
					new RequiredValueGreaterThanFormElement("cat_id", 0, "Category ID required for PPGP.")
				});

				if (validate_form())
					request.setAttribute("cat_id", form.get("cat_id"));
				else
					request.setAttribute("msg", this.validator.getErrorString());

				session.setAttribute("SEARCH-RESULTS", null);
			}
			else if (form.hasValue("op", "SUBMIT_GRADE")) {
				this.validator = new FormValidator(new FormElement[] {
						new RequiredValueGreaterThanFormElement("cat_id", 0, "Category ID required for PPGP."),
						new RequiredValueGreaterThanFormElement("grade_id", 0, "Grade ID required for PPGP.")
				});

				if (validate_form()) {
					request.setAttribute("cat_id", form.get("cat_id"));
					request.setAttribute("grade_id", form.get("grade_id"));
				}
				else
					request.setAttribute("msg", this.validator.getErrorString());

				session.setAttribute("SEARCH-RESULTS", null);
			}
			else if (form.hasValue("op", "SUBMIT_SUBJECT")) {
				this.validator = new FormValidator(new FormElement[] {
						new RequiredValueGreaterThanFormElement("cat_id", 0, "Category ID required for PPGP."),
						new RequiredValueGreaterThanFormElement("grade_id", 0, "Grade ID required for PPGP."),
						new RequiredValueGreaterThanFormElement("subject_id", 0, "Subject ID required for PPGP.")
				});

				if (validate_form()) {
					request.setAttribute("cat_id", form.get("cat_id"));
					request.setAttribute("grade_id", form.get("grade_id"));
					request.setAttribute("subject_id", form.get("subject_id"));
				}
				else
					request.setAttribute("msg", this.validator.getErrorString());

				session.setAttribute("SEARCH-RESULTS", null);
			}
			else if (form.hasValue("op", "SUBMIT_TOPICAREA")) {
				FormElement cat_id_ele = new RequiredValueGreaterThanFormElement("cat_id", 0, "Category ID required for PPGP.");
				this.validator = new FormValidator(new FormElement[] {
						cat_id_ele,
						new DependentFormElements(new RequiredValueGreaterThanFormElement("grade_id", 0, "Grade ID required for PPGP."), cat_id_ele, "1"),
						new DependentFormElements(new RequiredValueGreaterThanFormElement("subject_id", 0, "Subject ID required for PPGP."), cat_id_ele, "1"),
						new RequiredValueGreaterThanFormElement("topic_id", 0, "Topic ID required for PPGP.")
				});

				if (validate_form()) {
					request.setAttribute("cat_id", form.get("cat_id"));
					if (form.hasValue("cat_id", "1")) {
						request.setAttribute("grade_id", form.get("grade_id"));
						request.setAttribute("subject_id", form.get("subject_id"));
					}
					request.setAttribute("topic_id", form.get("topic_id"));
				}
				else
					request.setAttribute("msg", this.validator.getErrorString());

				session.setAttribute("SEARCH-RESULTS", null);
			}
			else {
				boolean useNewCriteria = false;
				boolean haveCriteria = true;
				String[] keywords = null;
				String year = null;
				String region = null;

				if (form.exists("year"))
					year = form.get("year");
				else
					year = Utils.getCurrentGrowthPlanYear();

				//now check the year to see if
				String years[] = year.split("-");
				if(Integer.parseInt(years[1]) > 2017){
					useNewCriteria=true;
				}
				region = form.get("region");
				if (form.hasValue("region", "5")) // entire district
					region = null;

				if (form.exists("keywords")) {
					StringTokenizer st = new StringTokenizer(form.get("keywords"), ", ;:/|", false);
					keywords = new String[st.countTokens()];
					for (int i = 0; st.hasMoreTokens(); i++)
						keywords[i] = st.nextToken();

					haveCriteria = true;
				}
				else
					keywords = new String[0];
				if(useNewCriteria){
					if((domain_id > 0) || (strength_id > 0)){
						haveCriteria=true;
					}
				}else{
					if ((cat_id > 0) || (grade_id > 0) || (subject_id > 0) || (topic_id > 0) || (stopic_id > 0))
						haveCriteria = true;
				}

				if (haveCriteria) {
					results = PPGPDB.search(year, region, keywords, cat_id, grade_id, subject_id, topic_id, stopic_id,useNewCriteria,domain_id,strength_id);

					results.setSchoolYear(year);
					try {
						if (StringUtils.isEmpty(region))
							results.setRegion(RegionManager.getRegionBean(5));
						else
							results.setRegion(RegionManager.getRegionBean(form.getInt("region")));
					}
					catch (RegionException e) {}
					results.setCategory(TaskCategoryManager.getTaskCategoryBean(cat_id));
					results.setGrade(TaskGradeManager.getTaskGradeBean(grade_id));
					results.setTaskSpecificTopicBean(TaskSpecificTopicManager.getTaskSpecificTopicBean(stopic_id));
					results.setTaskSubjectBean(TaskSubjectManager.getTaskSubjectBean(subject_id));
					results.setTaskTopicAreaBean(TaskTopicAreaManager.getTaskTopicAreaBean(topic_id));
					results.setKeywords(request.getParameter("keywords"));
					results.setDomain(TaskDomainManager.getTaskDomainBeanById(domain_id));
					results.setStrength(TaskDomainStrengthManager.getTaskDomainStrenthBeanById(strength_id));
					session.setAttribute("SEARCH-RESULTS", results);
					request.setAttribute("sreturn", "sreturn");
				}
				else
					request.setAttribute("msg", "Please enter search criteria.");

			}
		}
		else {
			// clear state
			request.setAttribute("year", null);
			request.setAttribute("region", null);
			request.setAttribute("keywords", null);
			request.setAttribute("cat_id", null);
			request.setAttribute("grade_id", null);
			request.setAttribute("subject_id", null);
			request.setAttribute("topic_id", null);
			request.setAttribute("stopic_id", null);

			session.setAttribute("SEARCH-RESULTS", null);
		}
		request.setAttribute("cats", TaskCategoryManager.getTaskCategoryBeansMap());
		request.setAttribute("grades", TaskGradeManager.getTaskGradeBeansMap());
		request.setAttribute("domains", TaskDomainManager.getTaskDomainBeans());
		path = "search.jsp";

		return path;
	}
}