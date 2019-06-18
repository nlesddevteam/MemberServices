package com.esdnl.survey.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;

public class SurveySectionQuestionOptionManager {

	public static SurveySectionQuestionOptionBean addSurveySectionQuestionOptionBean(SurveySectionQuestionOptionBean sbean)
			throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.add_section_question_answer(?,?,?); end;");

			stat.setInt(1, sbean.getQuestionId());
			stat.setString(2, sbean.getOptionBody());
			stat.setBoolean(3, sbean.isOther());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveySectionQuestionAnswerBean SurveySectionQuestionAnswerManager.addSurveySectionQuestionAnswerBean(SurveySectionQuestionAnswerBean sbean): "
					+ e);
			e.printStackTrace();

			throw new SurveyException("Can not add SurveySectionQuestionAnswerBean to DB.", e);
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

	public static SurveySectionQuestionOptionBean[] getSuverySectionQuestionOptionBeans(SurveySectionQuestionBean question)
			throws SurveyException {

		Vector<SurveySectionQuestionOptionBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveySectionQuestionOptionBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_section_question_answers(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, question.getQuestionId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveySectionQuestionOptionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionAnswerBean[] SurveySectionQuestionAnswerManager.getSuverySectionQuestionAnswerBeans(SurveySectionQuestionBean question) "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionAnswerBean from DB.", e);
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

		return (SurveySectionQuestionOptionBean[]) v_opps.toArray(new SurveySectionQuestionOptionBean[0]);
	}

	public static ArrayList<SurveySectionQuestionOptionBean> getSuverySectionQuestionOptionBeanList(SurveySectionQuestionBean question)
			throws SurveyException {

		ArrayList<SurveySectionQuestionOptionBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<SurveySectionQuestionOptionBean>(3);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_section_question_answers(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, question.getQuestionId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveySectionQuestionOptionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionAnswerBean[] SurveySectionQuestionAnswerManager.getSuverySectionQuestionAnswerBeanList(SurveySectionQuestionBean question) "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionAnswerBean from DB.", e);
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

		return v_opps;
	}

	public static SurveySectionQuestionOptionBean getSuverySectionQuestionOptionBean(int answer_id)
			throws SurveyException {

		SurveySectionQuestionOptionBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_section_question_answer(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, answer_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sBean = createSurveySectionQuestionOptionBean(rs);

		}
		catch (SQLException e) {
			System.err.println("SurveySectionQuestionAnswerBean SurveySectionQuestionAnswerManager.getSuverySectionQuestionAnswerBean(int answer_id): "
					+ e);
			throw new SurveyException("Can not extract SurveySectionQuestionAnswerBean from DB.", e);
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

	public static void deleteSuverySectionQuestionOptionBeans(SurveySectionQuestionBean question) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin awsd_user.survey_pkg.del_section_question_answers(?); end;");

			stat.setInt(1, question.getQuestionId());

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("void deleteSuverySectionQuestionOptionBeans(SurveySectionQuestionBean question): " + e);
			throw new SurveyException("Can not extract SurveySectionQuestionAnswerBean from DB.", e);
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

	}

	public static SurveySectionQuestionOptionBean createSurveySectionQuestionOptionBean(ResultSet rs) {

		SurveySectionQuestionOptionBean sBean = null;

		try {
			sBean = new SurveySectionQuestionOptionBean();

			sBean.setOptionId(rs.getInt("ANSWER_ID"));
			sBean.setQuestionId(rs.getInt("QUESTION_ID"));
			sBean.setOptionBody(rs.getString("BODY"));
			sBean.setSortOrder(rs.getInt("SORT_ORDER"));
			sBean.setOther(rs.getBoolean("IS_OTHER"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}
}
