package com.esdnl.sca.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.GradeDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.sca.model.bean.Assessment;
import com.esdnl.sca.model.bean.AssessmentStatus;
import com.esdnl.sca.model.bean.AssessmentType;
import com.esdnl.sca.model.bean.Pathway;
import com.esdnl.sca.model.bean.ReferralReason;
import com.esdnl.sca.model.bean.SCAException;

public class SCAManager {

	public static int addAssessment(Assessment abean) throws SCAException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.add_assessment(?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getGrade().getGradeID());
			if (abean.getReferralDate() != null)
				stat.setDate(3, new java.sql.Date(abean.getReferralDate().getTime()));
			else
				stat.setDate(3, null);

			stat.setInt(4, abean.getCurrentPathway().getId());
			if (abean.getPreviousAssessmentDate() != null)
				stat.setDate(5, new java.sql.Date(abean.getPreviousAssessmentDate().getTime()));
			else
				stat.setDate(5, null);
			stat.setInt(6, AssessmentStatus.NOT_YET_BEGUN.getId());
			if (abean.getStartDate() != null)
				stat.setDate(7, new java.sql.Date(abean.getStartDate().getTime()));
			else
				stat.setDate(7, null);
			if (abean.getCompletedDate() != null)
				stat.setDate(8, new java.sql.Date(abean.getCompletedDate().getTime()));
			else
				stat.setDate(8, null);
			stat.setString(9, abean.getStudentName());
			stat.setString(10, abean.getStudentMCP());
			stat.setInt(11, abean.getSchool().getSchoolID());
			stat.setInt(12, abean.getAdder().getPersonnelID());

			stat.execute();
			stat.close();

			id = ((OracleCallableStatement) stat).getInt(1);
			if (id > 0) {
				stat = con.prepareCall("begin awsd_user.sca_pkg.add_assessment_test(?,?); end;");
				for (int i = 0; i < abean.getTests().length; i++) {
					stat.clearParameters();
					stat.setInt(1, id);
					stat.setInt(2, abean.getTests()[i].getId());
					stat.execute();
				}
				stat.close();

				stat = con.prepareCall("begin awsd_user.sca_pkg.add_assessment_rr(?,?); end;");
				for (int i = 0; i < abean.getReasons().length; i++) {
					stat.clearParameters();
					stat.setInt(1, id);
					stat.setInt(2, abean.getReasons()[i].getId());
					stat.execute();
				}
				stat.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("int addAssessment(Assessment abean): " + e);
			throw new SCAException("Can not add Assessment to DB.", e);
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

		return id;
	}

	public static void updateAssessment(Assessment abean) throws SCAException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.sca_pkg.update_assessment(?,?); end;");
			stat.setInt(1, abean.getId());
			stat.setInt(2, abean.getStatus().getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateAssessessment(Assessment abean): " + e);
			throw new SCAException("Can not update Assessment to DB.", e);
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

	public static Assessment[] getAssessmentBeans(School s) throws SCAException {

		Vector v_opps = null;
		Assessment eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_assessments(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssessment(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getReferralReasonBeans(): " + e);
			throw new SCAException("Can not extract ReferralReasonBean from DB.", e);
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

		return (Assessment[]) v_opps.toArray(new Assessment[0]);
	}

	public static Assessment getAssessmentBean(int id) throws SCAException {

		Assessment eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_assessment(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createAssessment(rs);
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getAssessmentBean(int id): " + e);
			throw new SCAException("Can not extract AssessmentBean from DB.", e);
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

		return eBean;
	}

	public static Assessment createAssessment(ResultSet rs) {

		Assessment abean = null;

		try {
			abean = new Assessment();

			abean.setId(rs.getInt("ID"));
			abean.setAdder(PersonnelDB.getPersonnel(rs.getInt("PERSONNEL_ID")));
			if (rs.getDate("COMPLETE_DATE") != null)
				abean.setCompletedDate(new java.util.Date(rs.getDate("COMPLETE_DATE").getTime()));
			abean.setCurrentPathway(Pathway.get(rs.getInt("CURRENT_PATHWAY_ID")));
			abean.setGrade(GradeDB.getGrade(rs.getInt("GRADE_ID")));
			if (rs.getDate("PREVIOUS_ASSESSMENT_DATE") != null)
				abean.setPreviousAssessmentDate(new java.util.Date(rs.getDate("PREVIOUS_ASSESSMENT_DATE").getTime()));
			if (rs.getDate("REFERRAL_DATE") != null)
				abean.setReferralDate(new java.util.Date(rs.getDate("REFERRAL_DATE").getTime()));
			abean.setSchool(SchoolDB.getSchool(rs.getInt("SCHOOL_ID")));
			if (rs.getDate("START_DATE") != null)
				abean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			abean.setStatus(AssessmentStatus.get(rs.getInt("STATUS_ID")));
			abean.setStudentMCP(rs.getString("STUDENT_MCP"));
			abean.setStudentName(rs.getString("STUDENT_NAME"));
			abean.setTests(getAssessmentTypeBeans(abean));
			abean.setReasons(getReferralReasonBeans(abean));
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	/***********************************************************************/

	public static ReferralReason[] getReferralReasonBeans() throws SCAException {

		Vector v_opps = null;
		ReferralReason eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_referral_reasons; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createReferralReasonBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getReferralReasonBeans(): " + e);
			throw new SCAException("Can not extract ReferralReasonBean from DB.", e);
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

		return (ReferralReason[]) v_opps.toArray(new ReferralReason[0]);
	}

	public static ReferralReason getReferralReasonBean(int id) throws SCAException {

		ReferralReason eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_referral_reason(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createReferralReasonBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getReferralReasonBeans(): " + e);
			throw new SCAException("Can not extract ReferralReasonBean from DB.", e);
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

		return eBean;
	}

	public static ReferralReason[] getReferralReasonBeans(String[] id) throws SCAException {

		Vector v_opps = null;
		ReferralReason eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_referral_reason(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			for (int i = 0; i < id.length; i++) {
				stat.setInt(2, Integer.parseInt(id[i]));

				stat.execute();

				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					eBean = createReferralReasonBean(rs);

					v_opps.add(eBean);
				}

				rs.close();
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getReferralReasonBeans(String[]): " + e);
			throw new SCAException("Can not extract ReferralReasonBean from DB.", e);
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

		return (ReferralReason[]) v_opps.toArray(new ReferralReason[0]);
	}

	public static ReferralReason[] getReferralReasonBeans(Assessment abean) throws SCAException {

		Vector v_opps = null;
		ReferralReason eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_referral_reasons(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createReferralReasonBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getReferralReasonBeans(): " + e);
			throw new SCAException("Can not extract ReferralReasonBean from DB.", e);
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

		return (ReferralReason[]) v_opps.toArray(new ReferralReason[0]);
	}

	public static void addReferralReason(ReferralReason abean) throws SCAException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.sca_pkg.add_referral_reason(?); end;");
			stat.setString(1, abean.getDescription());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addReferralReason(ReferralReason abean): " + e);
			throw new SCAException("Can not add Referral Reason to DB.", e);
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

	public static ReferralReason createReferralReasonBean(ResultSet rs) {

		ReferralReason abean = null;

		try {
			abean = new ReferralReason();

			abean.setId(rs.getInt("ID"));
			abean.setDescription(rs.getString("DESCRIPTION"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	/****************************************************************/

	public static AssessmentType[] getAssessmentTypeBeans() throws SCAException {

		Vector v_opps = null;
		AssessmentType eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_assessment_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssessmentTypeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getAssessmentTypeBeans(): " + e);
			throw new SCAException("Can not extract AssessmentTypeBean from DB.", e);
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

		return (AssessmentType[]) v_opps.toArray(new AssessmentType[0]);
	}

	public static AssessmentType[] getAssessmentTypeBeans(String[] id) throws SCAException {

		Vector v_opps = null;
		AssessmentType eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_assessment_type(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			for (int i = 0; i < id.length; i++) {
				stat.setInt(2, Integer.parseInt(id[i]));

				stat.execute();

				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					eBean = createAssessmentTypeBean(rs);

					v_opps.add(eBean);
				}

				rs.close();
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getAssessmentTypeBeans(String[]): " + e);
			throw new SCAException("Can not extract AssessmentTypeBean from DB.", e);
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

		return (AssessmentType[]) v_opps.toArray(new AssessmentType[0]);
	}

	public static AssessmentType[] getAssessmentTypeBeans(Assessment abean) throws SCAException {

		Vector v_opps = null;
		AssessmentType eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sca_pkg.get_assessment_types(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssessmentTypeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SCAManager.getAssessmentTypeBeans(): " + e);
			throw new SCAException("Can not extract AssessmentTypeBean from DB.", e);
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

		return (AssessmentType[]) v_opps.toArray(new AssessmentType[0]);
	}

	public static void addAssessessmentType(AssessmentType abean) throws SCAException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.sca_pkg.add_assessment_type(?); end;");
			stat.setString(1, abean.getDescription());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAssessessmentType(AssessmentType abean): " + e);
			throw new SCAException("Can not add AssessmentType to DB.", e);
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

	public static AssessmentType createAssessmentTypeBean(ResultSet rs) {

		AssessmentType abean = null;

		try {
			abean = new AssessmentType();

			abean.setId(rs.getInt("ID"));
			abean.setDescription(rs.getString("DESCRIPTION"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

}