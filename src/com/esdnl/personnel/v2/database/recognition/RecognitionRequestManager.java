package com.esdnl.personnel.v2.database.recognition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.recognition.bean.RecognitionRequestBean;
import com.esdnl.personnel.v2.model.recognition.constant.RequestStatus;
import com.esdnl.personnel.v2.model.recognition.constant.RequestType;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.util.StringUtils;

public class RecognitionRequestManager {

	public static RecognitionRequestBean[] getRecognitionRequestBean(RequestStatus status) throws EmployeeException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_requests_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRecognitionRequestBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new EmployeeException("Can not extract RecognitionRequestBean from DB.", e);
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

		return ((RecognitionRequestBean[]) beans.toArray(new RecognitionRequestBean[0]));
	}

	public static RecognitionRequestBean getRecognitionRequestBean(int id) throws EmployeeException {

		RecognitionRequestBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				req = createRecognitionRequestBean(rs);

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new EmployeeException("Can not extract RecognitionRequestBean from DB.", e);
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

		return req;
	}

	public static void addRecognitionRequestBean(RecognitionRequestBean abean, Personnel p) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.add_request(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, p.getPersonnelID());
			stat.setInt(3, abean.getType().getId());
			stat.setString(4, abean.getDescription());
			stat.execute();
			stat.close();

			int request_id = ((OracleCallableStatement) stat).getInt(1);

			if (request_id > 0) {
				Object[] entities = abean.getEntities();

				if (entities.length > 0) {
					stat = con.prepareCall("begin awsd_user.personnel_recognition.add_request_entity(?,?); end;");

					for (int i = 0; i < entities.length; i++) {
						stat.clearParameters();
						stat.setInt(1, request_id);
						if (entities[i] instanceof EmployeeBean)
							stat.setString(2, ((EmployeeBean) entities[i]).getEmpId());
						stat.execute();
					}

					stat.close();
				}
				else
					con.rollback();
			}
			else
				con.rollback();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addRecognitionRequestBean(RecognitionRequestBean abean): " + e);
			throw new EmployeeException("Can not add RecognitionRequestBean to DB.", e);
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

	public static void approveRecognitionRequestBean(RecognitionRequestBean abean, Personnel p) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.approve_request(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setInt(2, p.getPersonnelID());
			stat.setInt(3, abean.getAwardType().getTypeId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addRecognitionRequestBean(RecognitionRequestBean abean): " + e);
			throw new EmployeeException("Can not add RecognitionRequestBean to DB.", e);
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

	public static void declineRecognitionRequestBean(RecognitionRequestBean abean, Personnel p) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.decline_request(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setInt(2, p.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addRecognitionRequestBean(RecognitionRequestBean abean): " + e);
			throw new EmployeeException("Can not add RecognitionRequestBean to DB.", e);
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

	public static void processRecognitionRequestBean(RecognitionRequestBean abean, Personnel p) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.process_request(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setInt(2, p.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addRecognitionRequestBean(RecognitionRequestBean abean): " + e);
			throw new EmployeeException("Can not add RecognitionRequestBean to DB.", e);
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

	public static RecognitionRequestBean createRecognitionRequestBean(ResultSet rs) {

		RecognitionRequestBean abean = null;

		try {
			abean = new RecognitionRequestBean();

			abean.setCurrentStatus(RequestStatus.get(rs.getInt("CUR_STATUS")));
			abean.setDescription(rs.getString("DESCRIPTION"));
			abean.setId(rs.getInt("REQUEST_ID"));
			if (!StringUtils.isEmpty(rs.getString("TEMPLATE_DOC")))
				abean.setTemplateDocument(rs.getString("TEMPLATE_DOC"));
			abean.setType(RequestType.get(rs.getInt("TYPE_ID")));

			abean.addEntities(EntityManager.getEntityBean(abean));

			abean.setHistory(HistoryManager.getRecognitionRequestHistory(abean));

			if (rs.getInt("AWARD_ID") > 0)
				abean.setAwardType(AwardTypeManager.getAwardTypeBean(rs.getInt("AWARD_ID")));
		}
		catch (com.esdnl.personnel.v2.model.bean.PersonnelException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}