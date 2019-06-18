package com.esdnl.survey.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;
import com.esdnl.survey.constant.SurveyQuestionTypeConstant;

public class SurveySectionQuestionManager {

	public static SurveySectionQuestionBean addSurveySectionQuestionBean(SurveySectionQuestionBean sbean)
			throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.add_survey_section_question(?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, sbean.getSectionId());
			stat.setInt(3, sbean.getQuestionType().getValue());
			stat.setString(4, sbean.getQuestionBody());
			stat.setBoolean(5, sbean.isManditory());
			stat.setInt(6, sbean.getBullets());
			stat.setInt(7, sbean.getBulletLength());

			stat.execute();

			sbean.setQuestionId(((OracleCallableStatement) stat).getInt(1));

			stat.close();

			if (sbean.getQuestionId() < 1) {
				try {
					con.rollback();
				}
				catch (Exception e) {}

				throw new SurveyException("Can not add SurveySectionQuestionBean to DB.");
			}

			if ((sbean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
					|| sbean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER) || sbean.getQuestionType().equals(
					SurveyQuestionTypeConstant.MULTIPLE_RATINGS))
					&& sbean.getMultipleChoiceOptionCount() > 0) {

				stat = con.prepareCall("begin awsd_user.survey_pkg.add_section_question_answer(?,?,?); end;");

				SurveySectionQuestionOptionBean[] options = sbean.getMultipleChoiceOptions();
				for (int i = 0; i < options.length; i++) {

					stat.clearParameters();

					stat.setInt(1, sbean.getQuestionId());
					stat.setString(2, options[i].getOptionBody());
					stat.setBoolean(3, options[i].isOther());

					stat.execute();
				}
			}

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveySectionQuestionBean SurveySectionQuestionManager.addSurveySectionQuestionBean(SurveySectionQuestionBean sbean): "
					+ e);
			e.printStackTrace();

			throw new SurveyException("Can not add SurveySectionQuestionBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return sbean;
	}

	public static SurveySectionQuestionBean updateSurveySectionQuestionBean(SurveySectionQuestionBean sbean)
			throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.update_survey_section_question(?,?,?,?,?); end;");

			stat.setInt(1, sbean.getQuestionId());
			stat.setInt(2, sbean.getSectionId());
			stat.setInt(3, sbean.getQuestionType().getValue());
			stat.setString(4, sbean.getQuestionBody());
			stat.setBoolean(5, sbean.isManditory());

			stat.execute();

			stat.close();

			if ((sbean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
					|| sbean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER) || sbean.getQuestionType().equals(
					SurveyQuestionTypeConstant.MULTIPLE_RATINGS))
					&& sbean.getMultipleChoiceOptionCount() > 0) {

				stat = con.prepareCall("begin awsd_user.survey_pkg.add_section_question_answer(?,?,?); end;");

				SurveySectionQuestionOptionBean[] options = sbean.getMultipleChoiceOptions();
				for (int i = 0; i < options.length; i++) {

					stat.clearParameters();

					stat.setInt(1, sbean.getQuestionId());
					stat.setString(2, options[i].getOptionBody());
					stat.setBoolean(3, options[i].isOther());

					stat.execute();
				}
			}

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveySectionQuestionBean updateSurveySectionQuestionBean(SurveySectionQuestionBean sbean): "
					+ e);
			e.printStackTrace();

			throw new SurveyException("Can not update SurveySectionQuestionBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return sbean;
	}

	public static SurveySectionQuestionBean[] getSuverySectionQuestionBeans(SurveySectionBean section)
			throws SurveyException {

		Vector<SurveySectionQuestionBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveySectionQuestionBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_section_questions(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, section.getSectionId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveySectionQuestionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionBean[] SurveySectionQuestionManager.getSuverySectionQuestionBeans(SurveySectionBean section): "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionBean from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return (SurveySectionQuestionBean[]) v_opps.toArray(new SurveySectionQuestionBean[0]);
	}

	public static SurveySectionQuestionBean[] getSuverySectionQuestionBeans(SurveyBean survey) throws SurveyException {

		Vector<SurveySectionQuestionBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveySectionQuestionBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey_questions(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, survey.getSurveyId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveySectionQuestionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionBean[] SurveySectionQuestionManager.getSuverySectionQuestionBeans(SurveyBean survey): "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionBean from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return (SurveySectionQuestionBean[]) v_opps.toArray(new SurveySectionQuestionBean[0]);
	}

	public static SurveySectionQuestionBean getSuverySectionQuestionBean(int question_id) throws SurveyException {

		SurveySectionQuestionBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_section_question(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, question_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sBean = createSurveySectionQuestionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionBean SurveySectionQuestionManager.getSuverySectionQuestionBean(int question_id): "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionBean from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return sBean;
	}

	public static SurveySectionQuestionBean createSurveySectionQuestionBean(ResultSet rs) {

		SurveySectionQuestionBean sBean = null;

		try {
			sBean = new SurveySectionQuestionBean();

			sBean.setQuestionId(rs.getInt("QUESTION_ID"));
			sBean.setSectionId(rs.getInt("SECTION_ID"));
			sBean.setQuestionBody(rs.getString("BODY"));
			sBean.setQuestionType(SurveyQuestionTypeConstant.get(rs.getInt("TYPE")));
			sBean.setSortOrder(rs.getInt("SORT_ORDER"));
			sBean.setManditory(rs.getBoolean("MANDITORY"));
			sBean.setBullets(rs.getInt("BULLETS"));
			sBean.setBulletLength(rs.getInt("BULLET_LENGTH"));

			if (sBean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
					|| sBean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)
					|| sBean.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {
				try {
					sBean.setOptions(SurveySectionQuestionOptionManager.getSuverySectionQuestionOptionBeanList(sBean));
				}
				catch (SurveyException e) {
					e.printStackTrace();
					sBean = null;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}
}
