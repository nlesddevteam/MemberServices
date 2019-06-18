package com.esdnl.photocopier.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.photocopier.bean.PhotocopierBean;
import com.esdnl.photocopier.bean.PhotocopierException;

public class PhotocopierManager {

	public static PhotocopierBean[] getPhotocopierBeans() throws PhotocopierException {

		Vector v_opps = null;
		PhotocopierBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_photocopiers; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createPhotocopierBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("PhotocopierManager.getPhotocopierBeans(): " + e);
			throw new PhotocopierException("Can not extract PhotocopierBean from DB.", e);
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

		return (PhotocopierBean[]) v_opps.toArray(new PhotocopierBean[0]);
	}

	public static PhotocopierBean[] getPhotocopierBeans(School s) throws PhotocopierException {

		Vector v_opps = null;
		PhotocopierBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_photocopiers(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createPhotocopierBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("PhotocopierManager.getPhotocopierBeans(): " + e);
			throw new PhotocopierException("Can not extract PhotocopierBean from DB.", e);
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

		return (PhotocopierBean[]) v_opps.toArray(new PhotocopierBean[0]);
	}

	public static PhotocopierBean getPhotocopierBean(int photocopier_id) throws PhotocopierException {

		PhotocopierBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_photocopier(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, photocopier_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createPhotocopierBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("PhotocopierManager.getPhotocopierBeans(): " + e);
			throw new PhotocopierException("Can not extract PhotocopierBean from DB.", e);
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

	public static void addPhotocopierBean(PhotocopierBean abean) throws PhotocopierException {

		Connection con = null;
		OracleCallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = (OracleCallableStatement) con.prepareCall("begin awsd_user.photocopier_pkg.add_photocopier(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			if (abean.getSchool() != null)
				stat.setInt(1, abean.getSchool().getSchoolID());
			else
				stat.setNull(1, OracleTypes.NUMBER);

			stat.setInt(2, abean.getBrand().getId());
			stat.setString(3, abean.getModelNumber());
			stat.setInt(4, abean.getYearAcquired());
			stat.setInt(5, abean.getAcquiredFrom().getId());
			stat.setBoolean(6, abean.isPurchased());

			if (!abean.isPurchased())
				stat.setInt(7, abean.getLeaseTerm());
			else
				stat.setNull(7, OracleTypes.NUMBER);

			stat.setBoolean(8, abean.hasServiceContract());

			if (abean.hasServiceContract()) {
				stat.setDate(9, new java.sql.Date(abean.getContractStartDate().getTime()));
				stat.setInt(10, abean.getContractTerm());
				stat.setInt(11, abean.getServicedBy().getId());
				stat.setDouble(12, abean.getAverageServiceTime());
			}
			else {
				stat.setNull(9, OracleTypes.DATE);
				stat.setNull(10, OracleTypes.NUMBER);
				stat.setNull(11, OracleTypes.NUMBER);
				stat.setNull(12, OracleTypes.NUMBER);
			}

			stat.setInt(13, abean.getNumberCopies());
			stat.setDate(14, new java.sql.Date(abean.getEffectiveDate().getTime()));

			if ((abean.getOtherComments() != null) && !abean.getOtherComments().trim().equals(""))
				stat.setString(15, abean.getOtherComments());
			else
				stat.setNull(15, OracleTypes.VARCHAR);

			stat.execute();
		}
		catch (SchoolException e) {
			e.printStackTrace();

			System.err.println("PhotocopierManager.addPhotocopierBean(PhotocopierBean abean): " + e);
			throw new PhotocopierException("Can not add PhotocopierBean to DB.", e);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("PhotocopierManager.addPhotocopierBean(PhotocopierBean abean): " + e);
			throw new PhotocopierException("Can not add PhotocopierBean to DB.", e);
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

	public static void updatePhotocopierBean(PhotocopierBean abean) throws PhotocopierException {

		Connection con = null;
		OracleCallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = (OracleCallableStatement) con.prepareCall("begin awsd_user.photocopier_pkg.update_photocopier(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			if (abean.getSchool() != null)
				stat.setInt(1, abean.getSchool().getSchoolID());
			else
				stat.setNull(1, OracleTypes.NUMBER);

			stat.setInt(2, abean.getBrand().getId());
			stat.setString(3, abean.getModelNumber());
			stat.setInt(4, abean.getYearAcquired());
			stat.setInt(5, abean.getAcquiredFrom().getId());
			stat.setBoolean(6, abean.isPurchased());

			if (!abean.isPurchased())
				stat.setInt(7, abean.getLeaseTerm());
			else
				stat.setNull(7, OracleTypes.NUMBER);

			stat.setBoolean(8, abean.hasServiceContract());

			if (abean.hasServiceContract()) {
				stat.setDate(9, new java.sql.Date(abean.getContractStartDate().getTime()));
				stat.setInt(10, abean.getContractTerm());
				stat.setInt(11, abean.getServicedBy().getId());
				stat.setDouble(12, abean.getAverageServiceTime());
			}
			else {
				stat.setNull(9, OracleTypes.DATE);
				stat.setNull(10, OracleTypes.NUMBER);
				stat.setNull(11, OracleTypes.NUMBER);
				stat.setNull(12, OracleTypes.NUMBER);
			}

			stat.setInt(13, abean.getNumberCopies());
			stat.setDate(14, new java.sql.Date(abean.getEffectiveDate().getTime()));

			if ((abean.getOtherComments() != null) && !abean.getOtherComments().trim().equals(""))
				stat.setString(15, abean.getOtherComments());
			else
				stat.setNull(15, OracleTypes.VARCHAR);

			stat.setInt(16, abean.getId());

			stat.execute();
		}
		catch (SchoolException e) {
			e.printStackTrace();

			System.err.println("PhotocopierManager.addPhotocopierBean(PhotocopierBean abean): " + e);
			throw new PhotocopierException("Can not add PhotocopierBean to DB.", e);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("PhotocopierManager.addPhotocopierBean(PhotocopierBean abean): " + e);
			throw new PhotocopierException("Can not add PhotocopierBean to DB.", e);
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

	public static PhotocopierBean createPhotocopierBean(ResultSet rs) {

		PhotocopierBean abean = null;

		try {
			abean = new PhotocopierBean();

			abean.setAcquiredFrom(rs.getInt("ACQUIRED_FROM"));
			abean.setAverageServiceTime(rs.getDouble("AVERAGE_SERVICE_TIME"));
			abean.setBrandId(rs.getInt("BRAND_ID"));
			if (rs.getDate("CONTRACT_START_DATE") != null)
				abean.setContractDate(new java.util.Date(rs.getDate("CONTRACT_START_DATE").getTime()));
			abean.setContractTerm(rs.getInt("CONTRACT_TERM"));
			abean.setEffectiveDate(new java.util.Date(rs.getDate("EFFECTIVE_DATE").getTime()));
			abean.setId(rs.getInt("COPIER_ID"));
			abean.setLeaseTerm(rs.getInt("LEASE_TERM"));
			abean.setModelNumber(rs.getString("MODEL_NUM"));
			abean.setNumberCopies(rs.getInt("NUM_COPIES"));
			abean.setOtherComments(rs.getString("OTHER_COMMENTS"));
			abean.setPurchased(rs.getBoolean("PURCHASED"));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setServiceContract(rs.getBoolean("SERVICE_CONTRACT"));
			abean.setServicedBy(rs.getInt("SERVICED_BY"));
			abean.setYearAcquired(rs.getInt("YEAR_ACQUIRED"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}