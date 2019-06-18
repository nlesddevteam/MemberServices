package com.esdnl.survey.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveyResponseAnswerBean;
import com.esdnl.survey.bean.SurveyResponseBean;
import com.esdnl.util.StringUtils;

public class SurveyResponseManager {

	public static SurveyResponseBean addSurveyResponseBean(SurveyResponseBean sbean) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.add_survey_response(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, sbean.getSurveyId());
			stat.setString(3, sbean.getIpAddress());

			stat.execute();

			sbean.setResponseId(((OracleCallableStatement) stat).getInt(1));

			stat.close();

			if (sbean.getResponseId() < 1) {
				try {
					con.rollback();
				}
				catch (Exception e) {}

				throw new SurveyException("Can not add SurveyResponseBean to DB.");
			}

			stat = con.prepareCall("begin awsd_user.survey_pkg.add_survey_response_answer(?,?,?,?,?,?); end;");

			SurveyResponseAnswerBean[] answers = sbean.getAnswers();
			for (int i = 0; ((answers != null) && (i < answers.length)); i++) {
				stat.clearParameters();

				stat.setInt(1, sbean.getResponseId());

				stat.setInt(2, answers[i].getQuestionId());

				if (answers[i].getOptionId() > 0)
					stat.setInt(3, answers[i].getOptionId());
				else
					stat.setNull(3, OracleTypes.NUMBER);

				if ((answers[i].getOptionId() < 1) && StringUtils.isEmpty(answers[i].getBody()))
					stat.setBoolean(4, answers[i].isTrueOrFalse());
				else
					stat.setNull(4, OracleTypes.NUMBER);

				stat.setString(5, answers[i].getBody());

				if ((answers[i].getOptionId() > 0) && (answers[i].getRating() > 0))
					stat.setInt(6, answers[i].getRating());
				else
					stat.setNull(6, OracleTypes.NUMBER);

				stat.execute();
			}

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveyResponseBean SurveyResponseManager.addSurveyResponseBean(SurveyResponseBean sbean): "
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

	public static Collection<SurveyResponseBean> getSuveryResponseBeans(SurveyBean survey) throws SurveyException {

		Vector<SurveyResponseBean> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveyResponseBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey_responses(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, survey.getSurveyId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSurveyResponseBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<SurveyResponseBean> getSuverySectionQuestionBeans(SurveyBean survey): " + e);
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

		return v_opps;
	}

	public static SurveyResponseBean createSurveyResponseBean(ResultSet rs) {

		SurveyResponseBean sBean = null;

		try {
			sBean = new SurveyResponseBean();

			sBean.setSurveyId(rs.getInt("SURVEY_ID"));
			sBean.setResponseId(rs.getInt("RESPONSE_ID"));
			sBean.setIpAddress(rs.getString("IP_ADDRESS"));
			sBean.setResponseDate(new java.util.Date(rs.getTimestamp("RESPONSE_DATE").getTime()));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}

}
