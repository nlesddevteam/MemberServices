package com.esdnl.survey.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveyResponseAnswerBean;
import com.esdnl.survey.bean.SurveyResponseBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;

public class SurveyResponseAnswerManager {

	public static SurveyResponseAnswerBean addSurveyResponseAnswerBean(SurveyResponseAnswerBean sbean)
			throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.add_survey_response_answer(?,?,?,?,?,?); end;");

			stat.setInt(1, sbean.getResponseId());
			stat.setInt(2, sbean.getQuestionId());
			stat.setInt(3, sbean.getOptionId());
			stat.setBoolean(4, sbean.isTrueOrFalse());
			stat.setString(5, sbean.getBody());
			stat.setInt(6, sbean.getRating());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" SurveyResponseAnswerBean SurveyResponseAnswerManager.addSurveyResponseAnswerBean(SurveyResponseAnswerBean sbean): "
					+ e);
			e.printStackTrace();

			throw new SurveyException("Can not add SurveyResponseBean to DB.", e);
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

	public static ArrayList<SurveyResponseAnswerBean> getSurveyResponseAnswerBeanList(SurveyResponseBean response)
			throws SurveyException {

		ArrayList<SurveyResponseAnswerBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<SurveyResponseAnswerBean>(3);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey_response_answers(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, response.getResponseId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveyResponseAnswerBean(rs));
		}
		catch (SQLException e) {
			System.err.println("ArrayList SurveyResponseAnswerManager.getSurveyResponseAnswerBeanList(SurveyResponseBean response) "
					+ e);
			throw new SurveyException("Can not extract SurveyResponseAnswerBean from DB.", e);
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

	public static HashMap<Integer, SurveyResponseAnswerBean> getSurveyResponseAnswerBeanMap(SurveyResponseBean response)
			throws SurveyException {

		HashMap<Integer, SurveyResponseAnswerBean> v_opps = null;

		v_opps = new HashMap<Integer, SurveyResponseAnswerBean>(3);

		for (SurveyResponseAnswerBean srab : getSurveyResponseAnswerBeanList(response)) {
			v_opps.put(srab.getQuestionId(), srab);
		}

		return v_opps;
	}

	public static ArrayList<SurveyResponseAnswerBean> getSurveyResponseAnswerBeanList(SurveySectionQuestionBean question)
			throws SurveyException {

		ArrayList<SurveyResponseAnswerBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<SurveyResponseAnswerBean>(3);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_answers_by_question(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, question.getQuestionId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveyResponseAnswerBean(rs));
		}
		catch (SQLException e) {
			System.err.println("ArrayList SurveyResponseAnswerManager.getSurveyResponseAnswerBeanList(SurveyResponseBean response) "
					+ e);
			throw new SurveyException("Can not extract SurveyResponseAnswerBean from DB.", e);
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

	public static ArrayList<SurveyResponseAnswerBean> getSurveyResponseAnswerBeanList(SurveyResponseBean sr,
																																										SurveySectionQuestionBean question)
			throws SurveyException {

		ArrayList<SurveyResponseAnswerBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<SurveyResponseAnswerBean>(3);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_answers_by_question(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, sr.getResponseId());
			stat.setInt(3, question.getQuestionId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveyResponseAnswerBean(rs));
		}
		catch (SQLException e) {
			System.err.println("ArrayList<SurveyResponseAnswerBean> getSurveyResponseAnswerBeanList(SurveyResponseBean sr, SurveySectionQuestionBean question) "
					+ e);
			throw new SurveyException("Can not extract SurveyResponseAnswerBean from DB.", e);
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

	public static SurveyResponseAnswerBean createSurveyResponseAnswerBean(ResultSet rs) {

		SurveyResponseAnswerBean sBean = null;

		try {
			sBean = new SurveyResponseAnswerBean();

			sBean.setOptionId(rs.getInt("ANSWER_ID"));
			sBean.setBody(rs.getString("BODY"));
			sBean.setQuestionId(rs.getInt("QUESTION_ID"));
			sBean.setResponseId(rs.getInt("RESPONSE_ID"));
			sBean.setTrueOrFalse(rs.getBoolean("TRUE_FALSE"));
			sBean.setRating(rs.getInt("RATING"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}
}
