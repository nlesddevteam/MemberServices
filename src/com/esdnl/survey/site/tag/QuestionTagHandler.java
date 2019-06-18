package com.esdnl.survey.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.servlet.Form;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;
import com.esdnl.survey.constant.SurveyQuestionTypeConstant;
import com.esdnl.survey.dao.SurveySectionManager;
import com.esdnl.survey.dao.SurveySectionQuestionManager;

public class QuestionTagHandler extends TagSupport {

	private static final long serialVersionUID = -6602129909861828664L;

	private static final int DEFAULT_RATING = 5;

	private SurveyBean survey = null;
	private SurveySectionBean section = null;
	private SurveySectionQuestionBean question = null;

	public void setSurvey(SurveyBean survey) {

		this.survey = survey;
	}

	public void setSection(SurveySectionBean section) {

		this.section = section;
	}

	public void setQuestion(SurveySectionQuestionBean question) {

		this.question = question;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		int scnt = 0;
		int qcnt = 0;

		try {
			out = pageContext.getOut();

			Form f = (Form) pageContext.getAttribute("FORM", PageContext.REQUEST_SCOPE);

			if (survey != null) {

				SurveySectionBean[] sections = null;
				SurveySectionQuestionBean[] questions = null;
				SurveySectionQuestionOptionBean[] options = null;

				sections = SurveySectionManager.getSuverySectionBeans(survey);

				for (int s = 0; s < sections.length; s++) {

					if (sections[s].isHeaderDisplayed()) {
						out.println("<table align='center' width='100%' cellspacing='2' cellpadding='2' border='0' class='subheaderbox'>");
						out.println("<tr class='subheadertitle'><td>" + Character.toString((char) (65 + (scnt++)))
								+ ".&nbsp;&nbsp;" + sections[s].getHeading() + "</td></tr>");
						if (sections[s].hasIntroduction())
							out.println("<tr><td class='sectionintro'>" + sections[s].getIntroduction() + "</td></tr>");
						if (sections[s].hasInstructions())
							out.println("<tr><td class='sectioninstr'>" + sections[s].getInstructions() + "</td></tr>");
						out.println("</table>");
					}

					questions = SurveySectionQuestionManager.getSuverySectionQuestionBeans(sections[s]);

					for (int q = 0; q < questions.length; q++) {

						String cls = (questions[q].isManditory() ? "requiredInputBox" : "inputBox");

						out.println("<table width='99%' border='0' cellspacing='2' cellpadding='2' align='center' class='questionbox'>");

						out.println("<tr class='question'><td width='100%'>" + (++qcnt) + ".&nbsp;&nbsp;"
								+ questions[q].getQuestionBody() + "</td></tr>");

						if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.LONG_ANSWER)) {
							out.println("<tr class='answer'><td width='100%'><textarea name='q_"
									+ questions[q].getQuestionId()
									+ "' class='"
									+ cls
									+ "' style='width:98%;height:100px;'>"
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId())) ? f.get("q_"
											+ questions[q].getQuestionId()) : "") + "</textarea></td></tr>");
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.SHORT_ANSWER)) {
							out.println("<tr class='answer'><td width='100%'><input type='text' name='q_"
									+ questions[q].getQuestionId()
									+ "' style='width:98%;' class='"
									+ cls
									+ "' "
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId())) ? "value='"
											+ f.get("q_" + questions[q].getQuestionId()) + "'" : "") + " /></td></tr>");
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.TRUE_OR_FALSE)) {
							out.println("<tr class='answer'><td width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"
									+ "<tr><td width='25'>A.&nbsp;&nbsp;</td><td width='*'><input type='radio' name='q_"
									+ questions[q].getQuestionId()
									+ "' class='"
									+ cls
									+ "' value='true' "
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && f.getBoolean("q_"
											+ questions[q].getQuestionId())) ? "CHECKED" : "") + " />&nbsp;TRUE</td></tr></table></td></tr>");
							out.println("<tr class='answer'><td width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"
									+ "<tr><td width='25'>B.&nbsp;&nbsp;</td><td width='*'><input type='radio' name='q_"
									+ questions[q].getQuestionId()
									+ "' class='"
									+ cls
									+ "' value='false' "
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && !f.getBoolean("q_"
											+ questions[q].getQuestionId())) ? "CHECKED" : "") + " />&nbsp;FALSE</td></tr></table></td></tr>");
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)) {
							options = questions[q].getMultipleChoiceOptions();
							for (int o = 0; o < options.length; o++) {
								out.println("<tr class='answer'><td width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td width='25'>"
										+ Character.toString((char) (65 + o))
										+ ".&nbsp;&nbsp;</td><td width='*'><input type='radio' name='q_"
										+ questions[q].getQuestionId()
										+ "' class='"
										+ cls
										+ "' value='"
										+ options[o].getOptionId()
										+ "' "
										+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && (f.getInt("q_"
												+ questions[q].getQuestionId()) == options[o].getOptionId())) ? "CHECKED" : "")
										+ " />&nbsp;"
										+ options[o].getOptionBody()
										+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + questions[q].getQuestionId()
												+ "_other'  class='" + cls + "' />" : "") + "</td></tr></table></td></tr>");
							}
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
							options = questions[q].getMultipleChoiceOptions();
							for (int o = 0; o < options.length; o++) {
								out.println("<tr class='answer'><td width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td width='25'>"
										+ Character.toString((char) (65 + o))
										+ ".&nbsp;&nbsp;</td><td width='*'><input type='checkbox' name='q_"
										+ questions[q].getQuestionId()
										+ "_"
										+ options[o].getOptionId()
										+ "' class='"
										+ cls
										+ "' value='"
										+ options[o].getOptionId()
										+ "' "
										+ (((f != null) && f.exists("q_" + questions[q].getQuestionId() + "_" + options[o].getOptionId()) && (f.getInt("q_"
												+ questions[q].getQuestionId() + "_" + options[o].getOptionId()) == options[o].getOptionId())) ? "CHECKED"
												: "")
										+ " />&nbsp;"
										+ options[o].getOptionBody()
										+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + questions[q].getQuestionId()
												+ "_other'  class='" + cls + "' />" : "") + "</td></tr></table></td></tr>");
							}
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {
							out.println("<tr class='answer'><td width='100%'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"
									+ "<tr><td valign='top' width='75%'>&nbsp;</td>"
									+ "<td width='*' valign='top'><table border='0' cellspacing='0' cellpadding='0'><tr>");
							for (int r = 1; r <= QuestionTagHandler.DEFAULT_RATING; r++)
								out.println("<td width='24' align='center' valign='bottom' style='font-weight:bold;'>" + r + "</td>");
							out.println("</tr></table></td></tr></table></td></tr>");

							options = questions[q].getMultipleChoiceOptions();

							for (int o = 0; o < options.length; o++) {
								out.println("<tr class='answer'><td  width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"
										+ "<tr><td valign='top' width='75%'>"
										+ Character.toString((char) (65 + o))
										+ ".&nbsp;&nbsp;"
										+ options[o].getOptionBody()
										+ "</td>"
										+ "<td width='*' valign='top'><table border='0' cellspacing='0' cellpadding='2'><tr>");

								for (int r = 1; r <= QuestionTagHandler.DEFAULT_RATING; r++)
									out.println("<td><input type='radio' name='q_"
											+ questions[q].getQuestionId()
											+ "_"
											+ options[o].getOptionId()
											+ "' class='"
											+ cls
											+ "' value='"
											+ r
											+ "' "
											+ (((f != null) && f.exists("q_" + questions[q].getQuestionId() + "_" + options[o].getOptionId()) && (f.getInt("q_"
													+ questions[q].getQuestionId() + "_" + options[o].getOptionId()) == r)) ? "CHECKED" : "")
											+ "/></td>");

								out.println("</tr></table></td></tr></table></td></tr>");
							}
						}
						else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)) {
							for (int o = 0; o < questions[q].getBullets(); o++) {
								out.println("<tr class='answer'><td width='100%' style='padding-left:15px;'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td width='25'>"
										+ Character.toString((char) (65 + o))
										+ ".&nbsp;&nbsp;</td><td width='*'><div class='bullet-answers-chars' id='q_"
										+ questions[q].getQuestionId()
										+ "_"
										+ o
										+ "_chars'>"
										+ questions[q].getBulletLength()
										+ " characters remaining.</div><div><input type='text' name='q_"
										+ questions[q].getQuestionId()
										+ "_"
										+ o
										+ "' class='"
										+ cls
										+ " bullet-answer' bullet-length='"
										+ questions[q].getBulletLength()
										+ "' value='"
										+ (((f != null) && f.exists("q_" + questions[q].getQuestionId() + "_" + o)) ? f.get("q_"
												+ questions[q].getQuestionId() + "_" + o) : "") + "' /></div></td></tr></table></td></tr>");
							}
						}
						out.println("</table><br>&nbsp;<br>");
					}
				}
			}
			else if (section != null) {
				SurveySectionQuestionBean[] questions = null;
				SurveySectionQuestionOptionBean[] options = null;

				if (section.isHeaderDisplayed()) {
					out.println("<table align='center' width='100%' cellspacing='2' cellpadding='2' border='0' class='headerbox'>");
					out.println("<tr class='headertitle'><td>" + section.getHeading() + "</td></tr>");
					if (section.hasIntroduction())
						out.println("<tr><td class='sectionintro'>" + section.getIntroduction() + "</td></tr>");
					if (section.hasInstructions())
						out.println("<tr><td class='sectioninstr'>" + section.getInstructions() + "</td></tr>");
					out.println("</table>");
				}

				questions = SurveySectionQuestionManager.getSuverySectionQuestionBeans(section);

				for (int q = 0; q < questions.length; q++) {

					String cls = (questions[q].isManditory() ? "requiredInputBox" : "requiredInputBox");

					out.println("<table width='99%' border='0' cellspacing='2' cellpadding='2' align='center' class='questionbox'>");

					out.println("<tr class='question'><td>" + (++qcnt) + ".&nbsp;&nbsp;" + questions[q].getQuestionBody()
							+ "</td></tr>");

					if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.LONG_ANSWER)) {
						out.println("<tr class='answer'><td><textarea name='q_"
								+ questions[q].getQuestionId()
								+ "' class='"
								+ cls
								+ "' style='width:98%;height:100px;'>"
								+ (((f != null) && f.exists("q_" + questions[q].getQuestionId())) ? f.get("q_"
										+ questions[q].getQuestionId()) : "") + "</textarea></td></tr>");
					}
					else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.SHORT_ANSWER)) {
						out.println("<tr class='answer'><td><input type='text' name='q_"
								+ questions[q].getQuestionId()
								+ "' style='width:98%;' class='"
								+ cls
								+ "' "
								+ (((f != null) && f.exists("q_" + questions[q].getQuestionId())) ? "value='"
										+ f.get("q_" + questions[q].getQuestionId()) + "'" : "") + " /></td></tr>");
					}
					else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.TRUE_OR_FALSE)) {
						out.println("<tr class='answer'><td>A.&nbsp;&nbsp;<input type='radio' name='q_"
								+ questions[q].getQuestionId()
								+ "' class='"
								+ cls
								+ "' value='true' "
								+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && f.getBoolean("q_"
										+ questions[q].getQuestionId())) ? "CHECKED" : "") + " />&nbsp;TRUE</td></tr>");
						out.println("<tr class='answer'><td>B.&nbsp;&nbsp;<input type='radio' name='q_"
								+ questions[q].getQuestionId()
								+ "' class='"
								+ cls
								+ "' value='false' "
								+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && !f.getBoolean("q_"
										+ questions[q].getQuestionId())) ? "CHECKED" : "") + " />&nbsp;FALSE</td></tr>");
					}
					else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)) {
						options = questions[q].getMultipleChoiceOptions();
						for (int o = 0; o < options.length; o++) {
							out.println("<tr class='answer'><td>"
									+ Character.toString((char) (65 + o))
									+ ".&nbsp;&nbsp;<input type='radio' name='q_"
									+ questions[q].getQuestionId()
									+ "' class='"
									+ cls
									+ "' value='"
									+ options[o].getOptionId()
									+ "' "
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId()) && (f.getInt("q_"
											+ questions[q].getQuestionId()) == options[o].getOptionId())) ? "CHECKED" : "")
									+ " />&nbsp;"
									+ options[o].getOptionBody()
									+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + questions[q].getQuestionId()
											+ "_other'  class='" + cls + "' />" : "") + "</td></tr>");
						}
					}
					else if (questions[q].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
						options = questions[q].getMultipleChoiceOptions();
						for (int o = 0; o < options.length; o++) {
							out.println("<tr class='answer'><td>"
									+ Character.toString((char) (65 + o))
									+ ".&nbsp;&nbsp;<input type='checkbox' name='q_"
									+ questions[q].getQuestionId()
									+ "_"
									+ options[o].getOptionId()
									+ "' class='"
									+ cls
									+ "' value='"
									+ options[o].getOptionId()
									+ "' "
									+ (((f != null) && f.exists("q_" + questions[q].getQuestionId() + "_" + options[o].getOptionId()) && (f.getInt("q_"
											+ questions[q].getQuestionId() + "_" + options[o].getOptionId()) == options[o].getOptionId())) ? "CHECKED"
											: "")
									+ " />&nbsp;"
									+ options[o].getOptionBody()
									+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + questions[q].getQuestionId()
											+ "_other'  class='" + cls + "' />" : "") + "</td></tr>");
						}
					}
					out.println("</table><br>&nbsp;<br>");
				}
			}
			else if (question != null) {
				String cls = (question.isManditory() ? "requiredInputBox" : "requiredInputBox");

				SurveySectionQuestionOptionBean[] options = null;

				out.println("<table width='99%' border='0' cellspacing='2' cellpadding='2' align='center' class='questionbox'>");

				out.println("<tr class='question'><td>" + (++qcnt) + ".&nbsp;&nbsp;" + question.getQuestionBody()
						+ "</td></tr>");

				if (question.getQuestionType().equals(SurveyQuestionTypeConstant.LONG_ANSWER)) {
					out.println("<tr class='answer'><td><textarea name='q_"
							+ question.getQuestionId()
							+ "' class='"
							+ cls
							+ "' style='width:98%;height:100px;'>"
							+ (((f != null) && f.exists("q_" + question.getQuestionId())) ? f.get("q_" + question.getQuestionId())
									: "") + "</textarea></td></tr>");
				}
				else if (question.getQuestionType().equals(SurveyQuestionTypeConstant.SHORT_ANSWER)) {
					out.println("<tr class='answer'><td><input type='text' name='q_"
							+ question.getQuestionId()
							+ "' style='width:98%;' class='"
							+ cls
							+ "' "
							+ (((f != null) && f.exists("q_" + question.getQuestionId())) ? "value='"
									+ f.get("q_" + question.getQuestionId()) + "'" : "") + " /></td></tr>");
				}
				else if (question.getQuestionType().equals(SurveyQuestionTypeConstant.TRUE_OR_FALSE)) {
					out.println("<tr class='answer'><td>A.&nbsp;&nbsp;<input type='radio' name='q_"
							+ question.getQuestionId()
							+ "' class='"
							+ cls
							+ "' value='true' "
							+ (((f != null) && f.exists("q_" + question.getQuestionId()) && f.getBoolean("q_"
									+ question.getQuestionId())) ? "CHECKED" : "") + " />&nbsp;TRUE</td></tr>");
					out.println("<tr class='answer'><td>B.&nbsp;&nbsp;<input type='radio' name='q_"
							+ question.getQuestionId()
							+ "' class='"
							+ cls
							+ "' value='false' "
							+ (((f != null) && f.exists("q_" + question.getQuestionId()) && !f.getBoolean("q_"
									+ question.getQuestionId())) ? "CHECKED" : "") + " />&nbsp;FALSE</td></tr>");
				}
				else if (question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)) {
					options = question.getMultipleChoiceOptions();
					for (int o = 0; o < options.length; o++) {
						out.println("<tr class='answer'><td>"
								+ Character.toString((char) (65 + o))
								+ ".&nbsp;&nbsp;<input type='radio' name='q_"
								+ question.getQuestionId()
								+ "' class='"
								+ cls
								+ "' value='"
								+ options[o].getOptionId()
								+ "' "
								+ (((f != null) && f.exists("q_" + question.getQuestionId()) && (f.getInt("q_"
										+ question.getQuestionId()) == options[o].getOptionId())) ? "CHECKED" : "")
								+ " />&nbsp;"
								+ options[o].getOptionBody()
								+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + question.getQuestionId()
										+ "_other'  class='" + cls + "' />" : "") + "</td></tr>");
					}
				}
				else if (question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
					options = question.getMultipleChoiceOptions();
					for (int o = 0; o < options.length; o++) {
						out.println("<tr class='answer'><td>"
								+ Character.toString((char) (65 + o))
								+ ".&nbsp;&nbsp;<input type='checkbox' name='q_"
								+ question.getQuestionId()
								+ "_"
								+ options[o].getOptionId()
								+ "' class='"
								+ cls
								+ "' value='"
								+ options[o].getOptionId()
								+ "' "
								+ (((f != null) && f.exists("q_" + question.getQuestionId() + "_" + options[o].getOptionId()) && (f.getInt("q_"
										+ question.getQuestionId() + "_" + options[o].getOptionId()) == options[o].getOptionId())) ? "CHECKED"
										: "")
								+ " />&nbsp;"
								+ options[o].getOptionBody()
								+ ((options[o].isOther()) ? "&nbsp;<input type='text' name='q_" + question.getQuestionId()
										+ "_other'  class='" + cls + "' />" : "") + "</td></tr>");
					}
				}
				out.println("</table><br>&nbsp;<br>");
			}
		}
		catch (SurveyException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
