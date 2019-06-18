package com.esdnl.personnel.recognition.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.recognition.model.bean.NominationPeriodBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionCategoryBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;

public class NominationPeriodManager {

	public static int addNominationPeriodBean(NominationPeriodBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;
		int return_code = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.add_rec_cat_np(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getCategory().getUID());
			stat.setDate(3, new java.sql.Date(abean.getStartDate().getTime()));
			stat.setDate(4, new java.sql.Date(abean.getEndDate().getTime()));

			stat.execute();

			return_code = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addNominationPeriodBean(NominationPeriodBean abean): " + e);
			throw new RecognitionException("Can not add NominationPeriodBean to DB.", e);
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

		return return_code;
	}

	public static void delNominationPeriodBean(NominationPeriodBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.del_rec_cat_np(?); end;");
			stat.setInt(1, abean.getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void delNominationPeriodBean(NominationPeriodBean abean): " + e);
			throw new RecognitionException("Can not add NominationPeriodBean to DB.", e);
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

	public static NominationPeriodBean getNominationPeriodBean(int id) throws RecognitionException {

		NominationPeriodBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_rec_cat_np(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createNominationPeriodBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NominationPeriodBean[] getNominationPeriodBeans(RecognitionCategoryBean cat) " + e);
			throw new RecognitionException("Can not extract NominationPeriodBean from DB.", e);
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

		return bean;
	}

	public static NominationPeriodBean[] getNominationPeriodBeans(RecognitionCategoryBean cat)
			throws RecognitionException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_rec_cat_nps(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat.getUID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createNominationPeriodBean(rs));
		}
		catch (SQLException e) {
			System.err.println("NominationPeriodBean[] getNominationPeriodBeans(RecognitionCategoryBean cat) " + e);
			throw new RecognitionException("Can not extract NominationPeriodBean from DB.", e);
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

		return ((NominationPeriodBean[]) beans.toArray(new NominationPeriodBean[0]));
	}

	public static NominationPeriodBean[] getActiveNominationPeriodBeans() throws RecognitionException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_active_rec_cat_nps; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createNominationPeriodBean(rs));
		}
		catch (SQLException e) {
			System.err.println("NominationPeriodBean[] getActiveNominationPeriodBeans() " + e);
			throw new RecognitionException("Can not extract NominationPeriodBean from DB.", e);
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

		return ((NominationPeriodBean[]) beans.toArray(new NominationPeriodBean[0]));
	}

	public static NominationPeriodBean createNominationPeriodBean(ResultSet rs) {

		NominationPeriodBean abean = null;

		try {
			abean = new NominationPeriodBean();

			abean.setCategory(RecognitionCategoryManager.createRecognitionCategoryBean(rs));
			abean.setId(rs.getInt("NP_ID"));
			abean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			abean.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
			abean.setNominationCount(rs.getInt("NOMINATION_COUNT"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}