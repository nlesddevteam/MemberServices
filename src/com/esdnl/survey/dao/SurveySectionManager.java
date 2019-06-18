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

public class SurveySectionManager {

	public static SurveySectionBean addSurveySectionBean(SurveySectionBean sbean) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.add_survey_section(?,?,?,?,?); end;");

			stat.setInt(1, sbean.getSurveyId());
			stat.setString(2, sbean.getHeading());
			stat.setBoolean(3, sbean.isHeaderDisplayed());
			stat.setString(4, sbean.getIntroduction());
			stat.setString(5, sbean.getInstructions());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveySectionBean SurveySectionManager.addSurveySectionBean(SurveySectionBean sbean): " + e);
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

	public static SurveySectionBean updateSurveySectionBean(SurveySectionBean sbean) throws SurveyException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.survey_pkg.update_survey_section(?,?,?,?,?); end;");

			stat.setInt(1, sbean.getSectionId());
			stat.setString(2, sbean.getHeading());
			stat.setBoolean(3, sbean.isHeaderDisplayed());
			stat.setString(4, sbean.getIntroduction());
			stat.setString(5, sbean.getInstructions());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SurveySectionBean SurveySectionManager.updateSurveySectionBean(SurveySectionBean sbean): "
					+ e);
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

	public static SurveySectionBean[] getSuverySectionBeans(SurveyBean survey) throws SurveyException {

		Vector<SurveySectionBean> v_opps = null;
		SurveySectionBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SurveySectionBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey_sections(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, survey.getSurveyId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sBean = createSurveySectionBean(rs);

				v_opps.add(sBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SurveySectionBean[] SurveySectionManager.getSuverySectionBeans(SurveyBean survey): " + e);
			throw new SurveyException("Can not extract SurveySectionBean from DB.", e);
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

		return (SurveySectionBean[]) v_opps.toArray(new SurveySectionBean[0]);
	}

	public static SurveySectionBean getSuverySectionBean(int section_id) throws SurveyException {

		SurveySectionBean sBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_survey_section(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, section_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sBean = createSurveySectionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SurveySectionBean SurveySectionManager.getSuverySectionBean(int section_id): " + e);
			throw new SurveyException("Can not extract SurveySectionBean from DB.", e);
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

	public static SurveySectionBean createSurveySectionBean(ResultSet rs) {

		SurveySectionBean sBean = null;

		try {
			sBean = new SurveySectionBean();

			sBean.setSectionId(rs.getInt("SECTION_ID"));
			sBean.setSurveyId(rs.getInt("SURVEY_ID"));
			sBean.setHeading(rs.getString("NAME"));
			sBean.setHeaderDisplayed(rs.getBoolean("HEADER_DISPLAYED"));
			sBean.setSortOrder(rs.getInt("SORT_ORDER"));
			sBean.setInstructions(rs.getString("INSTRUCTIONS"));
			sBean.setIntroduction(rs.getString("INTRODUCTION"));
			sBean.setQuestionCount(rs.getInt("QUESTION_COUNT"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}
}
