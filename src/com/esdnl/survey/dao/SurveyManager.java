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

public class SurveyManager {

	public static SurveyBean addSurveyBean(SurveyBean sbean) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.add_survey(?,?,?,?,?,?,?,?); end;");
			stat.setString(1, sbean.getName());
			stat.setString(2, sbean.getPassword());

			if (sbean.getStartDate() != null)
				stat.setDate(3, new java.sql.Date(sbean.getStartDate().getTime()));
			else
				stat.setNull(3, OracleTypes.DATE);

			if (sbean.getEndDate() != null)
				stat.setDate(4, new java.sql.Date(sbean.getEndDate().getTime()));
			else
				stat.setNull(4, OracleTypes.DATE);

			stat.setString(5, sbean.getIntroduction());
			stat.setString(6, sbean.getInstructions());
			stat.setString(7, sbean.getThankYouMessage());
			stat.setBoolean(8, sbean.isInternal());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveyBean SurveyManager.addSurveyBean(SurveyBean sbean): " + e);
			e.printStackTrace();

			throw new SurveyException("Can not add SurveyBean to DB.", e);
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

	public static SurveyBean updateSurveyBean(SurveyBean sbean) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.update_survey(?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, sbean.getName());
			stat.setString(2, sbean.getPassword());

			if (sbean.getStartDate() != null)
				stat.setDate(3, new java.sql.Date(sbean.getStartDate().getTime()));
			else
				stat.setNull(3, OracleTypes.DATE);

			if (sbean.getEndDate() != null)
				stat.setDate(4, new java.sql.Date(sbean.getEndDate().getTime()));
			else
				stat.setNull(4, OracleTypes.DATE);

			stat.setString(5, sbean.getIntroduction());
			stat.setString(6, sbean.getInstructions());
			stat.setString(7, sbean.getThankYouMessage());
			stat.setBoolean(8, sbean.isInternal());
			stat.setInt(9, sbean.getSurveyId());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveyBean SurveyManager.updateSurveyBean(SurveyBean sbean): " + e);
			e.printStackTrace();

			throw new SurveyException("Can not update SurveyBean to DB.", e);
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

	public static SurveyBean[] getSuveryBeans() throws SurveyException {

		Vector<SurveyBean> v_opps = null;
		SurveyBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveyBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_surveys; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sBean = createSurveyBean(rs);

				v_opps.add(sBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SurveyBean[] getSuveryBeans(): " + e);
			throw new SurveyException("Can not extract SurveyBean from DB.", e);
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

		return (SurveyBean[]) v_opps.toArray(new SurveyBean[0]);
	}

	public static SurveyBean getSuveryBean(int survey_id) throws SurveyException {

		SurveyBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, survey_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sBean = createSurveyBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SurveyBean[] getSuveryBeans(): " + e);
			throw new SurveyException("Can not extract SurveyBean from DB.", e);
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

	public static SurveyBean createSurveyBean(ResultSet rs) {

		SurveyBean sBean = null;

		try {
			sBean = new SurveyBean();

			sBean.setSurveyId(rs.getInt("SURVEY_ID"));

			if (rs.getDate("END_DATE") != null)
				sBean.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));

			if (rs.getDate("START_DATE") != null)
				sBean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));

			sBean.setInstructions(rs.getString("INSTRUCTIONS"));
			sBean.setIntroduction(rs.getString("INTRODUCTION"));
			sBean.setName(rs.getString("NAME"));
			sBean.setPassword(rs.getString("PASSWORD"));
			sBean.setResponseCount(rs.getInt("response_count"));
			sBean.setThankYouMessage(rs.getString("THANKYOU"));
			sBean.setInternal(rs.getBoolean("INTERNAL"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}
}
