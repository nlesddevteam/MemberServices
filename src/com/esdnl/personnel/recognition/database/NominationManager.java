package com.esdnl.personnel.recognition.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.recognition.model.bean.NominationBean;
import com.esdnl.personnel.recognition.model.bean.NominationPeriodBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;

public class NominationManager {

	public static void addNominationBean(NominationBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.add_nomination(?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getNomineeFirstname());
			stat.setString(2, abean.getNomineeLastname());
			stat.setInt(3, abean.getNomineeLocation().getSchoolID());
			stat.setInt(4, abean.getNominationPeriod().getId());
			if (abean.getNominator() != null)
				stat.setInt(5, abean.getNominator().getPersonnelID());
			else
				stat.setNull(5, OracleTypes.NUMBER);
			stat.setTimestamp(6, new Timestamp(abean.getNominationDate().getTime()));
			stat.setString(7, abean.getRationaleFilename());
			stat.setString(8, abean.getNominatorFirstName());
			stat.setString(9, abean.getNominatorLastName());
			try {
				stat.setString(10, abean.getNominatorLocation());
			}
			catch (PersonnelException e) {
				stat.setNull(10, OracleTypes.VARCHAR);
			}

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addNominationBean(NominationBean abean): " + e);
			throw new RecognitionException("Can not add NominationBean to DB.", e);
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

	public static void deleteNominationBean(NominationBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.del_nomination(?); end;");

			stat.setInt(1, abean.getUID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void delNominationBean(NominationBean abean): " + e);
			throw new RecognitionException("Can not delete NominationBean to DB.", e);
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

	public static NominationBean getNominationBean(int id) throws RecognitionException {

		NominationBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_nomination(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createNominationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NominationBean getNominationBean(int id): " + e);
			throw new RecognitionException("Can not extract NominationBean from DB.", e);
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

	public static NominationBean[] getNominationBeans(NominationPeriodBean period) throws RecognitionException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_cat_np_nominations(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, period.getId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createNominationBean(rs));
		}
		catch (SQLException e) {
			System.err.println("NominationBean[] getNominationBeans(NominationPeriodBean period): " + e);
			throw new RecognitionException("Can not extract NominationBean from DB.", e);
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

		return ((NominationBean[]) beans.toArray(new NominationBean[0]));
	}

	public static NominationBean createNominationBean(ResultSet rs) {

		NominationBean abean = null;

		try {
			abean = new NominationBean();

			abean.setUID(rs.getInt("NOMINATION_ID"));

			try {
				abean.setNomineeLocation(SchoolDB.getSchool(rs.getInt("NOMINEE_LOCATION_ID")));
			}
			catch (SchoolException e) {
				e.printStackTrace();
				abean.setNomineeLocation(null);
			}

			abean.setNominationDate(new java.util.Date(rs.getDate("NOMINATION_DATE").getTime()));

			if (rs.getInt("PERIOD_ID") > 0)
				abean.setNominationPeriod(NominationPeriodManager.createNominationPeriodBean(rs));
			else
				abean.setNominationPeriod(null);

			try {
				if (rs.getInt("NOMINATOR_ID") > 0)
					abean.setNominator(PersonnelDB.getPersonnel(rs.getInt("NOMINATOR_ID")));
			}
			catch (PersonnelException e) {
				e.printStackTrace();
				abean.setNominator(null);
			}

			abean.setNomineeFirstname(rs.getString("NOMINEE_FIRSTNAME"));
			abean.setNomineeLastname(rs.getString("NOMINEE_LASTNAME"));
			abean.setRationaleFilename(rs.getString("RATIONALE_FILE"));

			abean.setNominatorFirstName(rs.getString("NOMINATOR_FIRSTNAME"));
			abean.setNominatorLastName(rs.getString("NOMINATOR_LASTNAME"));
			abean.setNominatorLocation(rs.getString("NOMINATOR_LOCATION"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}