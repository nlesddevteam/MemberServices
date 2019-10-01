package com.esdnl.student.travel.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolFamily;
import com.esdnl.dao.DAOUtils;
import com.esdnl.student.travel.bean.StudentTravelException;
import com.esdnl.student.travel.bean.TravelRequestBean;
import com.esdnl.student.travel.constant.RequestStatus;

public class TravelRequestManager {

	public static int addTravelRequestBean(TravelRequestBean abean) throws StudentTravelException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.student_travel.add_request(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getSchoolId());
			stat.setDate(3, new java.sql.Date(abean.getDepartureDate().getTime()));
			stat.setDate(4, new java.sql.Date(abean.getReturnDate().getTime()));
			stat.setString(5, abean.getRational());
			stat.setString(6, abean.getGrades());
			stat.setInt(7, abean.getNumStudents());
			stat.setString(8, abean.getTeacherChaperon());
			stat.setString(9, abean.getOtherChaperon());
			stat.setString(10, abean.getEmergencyContact());
			stat.setInt(11, abean.getRequestedBy());
			stat.setDate(12, new java.sql.Date(abean.getRequestedDate().getTime()));
			stat.setInt(13, abean.getStatus().getValue());
			stat.setInt(14, abean.getActionedBy());
			stat.setDate(15, new java.sql.Date(abean.getActionDate().getTime()));
			stat.setString(16, abean.getDestination());
			stat.setString(17, abean.getIteneraryFilename());
			stat.setDouble(18, abean.getDaysMissed());
			stat.setInt(19, abean.getTotalChaperons());
			stat.setInt(20, abean.getTotalTeacherChaperons());
			stat.setInt(21, abean.getTotalOtherChaperons());
			stat.setBoolean(22, abean.isChaperonsApproved());
			stat.setBoolean(23, abean.isBilletingInvolved());
			stat.setBoolean(24, abean.isSchoolFundraising());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTravelRequestBean(TravelRequestBean abean): " + e);
			throw new StudentTravelException("Can not add TravelRequestBean to DB.", e);
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

	public static void updateTravelRequestBean(TravelRequestBean abean) throws StudentTravelException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.student_travel.update_request(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getRequestId());
			stat.setInt(2, abean.getSchoolId());
			stat.setDate(3, new java.sql.Date(abean.getDepartureDate().getTime()));
			stat.setDate(4, new java.sql.Date(abean.getReturnDate().getTime()));
			stat.setString(5, abean.getRational());
			stat.setString(6, abean.getGrades());
			stat.setInt(7, abean.getNumStudents());
			stat.setString(8, abean.getTeacherChaperon());
			stat.setString(9, abean.getOtherChaperon());
			stat.setString(10, abean.getEmergencyContact());
			stat.setInt(11, abean.getRequestedBy());
			stat.setDate(12, new java.sql.Date(abean.getRequestedDate().getTime()));
			stat.setInt(13, abean.getStatus().getValue());
			stat.setInt(14, abean.getActionedBy());
			stat.setDate(15, new java.sql.Date(abean.getActionDate().getTime()));
			stat.setString(16, abean.getDestination());
			stat.setString(17, abean.getIteneraryFilename());
			stat.setDouble(18, abean.getDaysMissed());
			stat.setInt(19, abean.getTotalChaperons());
			stat.setInt(20, abean.getTotalTeacherChaperons());
			stat.setInt(21, abean.getTotalOtherChaperons());
			stat.setBoolean(22, abean.isChaperonsApproved());
			stat.setBoolean(23, abean.isBilletingInvolved());
			stat.setBoolean(24, abean.isSchoolFundraising());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTravelRequestBean(TravelRequestBean abean): " + e);
			throw new StudentTravelException("Can not add TravelRequestBean to DB.", e);
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

	public static TravelRequestBean[] getTravelRequestBeans(boolean currentOnly) throws StudentTravelException {

		Vector<TravelRequestBean> v_opps = null;
		TravelRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TravelRequestBean>(5);

			con = DAOUtils.getConnection();
			if (currentOnly)
				stat = con.prepareCall("begin ? := awsd_user.student_travel.get_current_requests; end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.student_travel.get_requests; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelRequestBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new StudentTravelException("Can not extract AssignmentSubjectBean from DB.", e);
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

		return (TravelRequestBean[]) v_opps.toArray(new TravelRequestBean[0]);
	}

	public static TravelRequestBean[] getTravelRequestBeans(SchoolFamily family) throws StudentTravelException {

		Vector<TravelRequestBean> v_opps = null;
		TravelRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TravelRequestBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.student_travel.get_family_current_requests(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, family.getSchoolFamilyID());

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelRequestBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelRequestBean[] getTravelRequestBeans(SchoolFamily family): " + e);
			throw new StudentTravelException("Can not extract TravelRequestBean from DB.", e);
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

		return (TravelRequestBean[]) v_opps.toArray(new TravelRequestBean[0]);
	}

	public static TravelRequestBean[] getTravelRequestBeans(School school, boolean currentOnly)
			throws StudentTravelException {

		Vector<TravelRequestBean> v_opps = null;
		TravelRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TravelRequestBean>(5);

			con = DAOUtils.getConnection();
			if (currentOnly)
				stat = con.prepareCall("begin ? := awsd_user.student_travel.get_current_requests(?); end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.student_travel.get_requests(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelRequestBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new StudentTravelException("Can not extract AssignmentSubjectBean from DB.", e);
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

		return (TravelRequestBean[]) v_opps.toArray(new TravelRequestBean[0]);
	}

	public static TravelRequestBean getTravelRequestBean(int id) throws StudentTravelException {

		TravelRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.student_travel.get_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createTravelRequestBean(rs);
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new StudentTravelException("Can not extract AssignmentSubjectBean from DB.", e);
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
	public static void deleteRequest(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.STUDENT_TRAVEL.delete_student_travel(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteRequest(Integer tid):" + e);
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
	public static TravelRequestBean createTravelRequestBean(ResultSet rs) {

		TravelRequestBean abean = null;

		try {
			abean = new TravelRequestBean();

			abean.setActionDate(new java.util.Date(rs.getDate("STATUS_DATE").getTime()));
			abean.setActionedBy(rs.getInt("STATUS_BY"));
			abean.setDepartureDate(new java.util.Date(rs.getDate("DEPARTURE_DATE").getTime()));
			abean.setDestination(rs.getString("DESTINATION"));
			abean.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));
			abean.setGrades(rs.getString("GRADES"));
			abean.setIteneraryFilename(rs.getString("ITINERARY_FILENAME"));
			abean.setNumStudents(rs.getInt("NUM_STUDENTS"));
			abean.setOtherChaperon(rs.getString("OTHER_CHAPERONS"));
			abean.setRational(rs.getString("RATIONAL"));
			abean.setRequestedBy(rs.getInt("REQUESTED_BY"));
			abean.setRequestedDate(new java.util.Date(rs.getDate("REQUEST_DATE").getTime()));
			abean.setRequestId(rs.getInt("REQUEST_ID"));
			abean.setReturnDate(new java.util.Date(rs.getDate("RETURN_DATE").getTime()));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setStatus(RequestStatus.get(rs.getInt("CURRENT_STATUS")));
			abean.setTeacherChaperon(rs.getString("TEACHER_CHAPERONS"));
			abean.setDaysMissed(rs.getDouble("SCHOOL_DAYS_MISSED"));
			abean.setTotalChaperons(rs.getInt("TOTAL_CHAPERONES"));
			abean.setTotalTeacherChaperons(rs.getInt("TOTAL_TEACHER_CHAPERONES"));
			abean.setTotalOtherChaperons(rs.getInt("TOTAL_OTHER_CHAPERONES"));
			abean.setChaperonsApproved(rs.getBoolean("PRINCIPAL_APPROVED"));
			abean.setBilletingInvolved(rs.getBoolean("BILLETING"));
			abean.setSchoolFundraising(rs.getBoolean("FUNDRAISING"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}