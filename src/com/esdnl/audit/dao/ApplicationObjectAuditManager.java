package com.esdnl.audit.dao;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.bean.AuditException;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.dao.DAOUtils;

public class ApplicationObjectAuditManager {

	public static ApplicationObjectAuditBean addApplicationObjectAudit(ApplicationObjectAuditBean sbean)
			throws AuditException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.application_audit.add_trail(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, sbean.getApplication().getValue());
			stat.setString(3, sbean.getObjectType());
			stat.setString(4, sbean.getObjectId());

			if (sbean.getWhen() != null)
				stat.setDate(5, new java.sql.Date(sbean.getWhen().getTime()));
			else
				stat.setNull(5, OracleTypes.DATE);

			stat.setString(6, sbean.getWho());
			stat.setString(7, sbean.getAction());
			stat.setString(8, sbean.getActionType().getValue());

			if (sbean.getOldData() != null) {
				oracle.sql.CLOB oldClob = oracle.sql.CLOB.createTemporary(con, false, oracle.sql.CLOB.DURATION_SESSION);
				oldClob.putString(1, sbean.getOldData());
				((OracleCallableStatement) stat).setCLOB(9, oldClob);
			}
			else
				stat.setNull(9, OracleTypes.CLOB);

			if (sbean.getNewData() != null) {
				oracle.sql.CLOB newClob = oracle.sql.CLOB.createTemporary(con, false, oracle.sql.CLOB.DURATION_SESSION);
				newClob.putString(1, sbean.getNewData());
				((OracleCallableStatement) stat).setCLOB(10, newClob);
			}
			else
				stat.setNull(10, OracleTypes.CLOB);

			stat.execute();

			sbean.setAuditId(((OracleCallableStatement) stat).getInt(1));

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicationObjectAuditBean ApplicationObjectAuditManager.addApplicationObjectAudit(ApplicationObjectAuditBean sbean): "
					+ e);
			e.printStackTrace();

			throw new AuditException("Can not add ReportBean to DB.", e);
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

	public static ApplicationObjectAuditBean createApplicationObjectAuditBean(ResultSet rs) {

		ApplicationObjectAuditBean sBean = null;

		try {
			sBean = new ApplicationObjectAuditBean();

			sBean.setAction(rs.getString("ACTION"));
			sBean.setApplication(ApplicationConstant.get(rs.getInt("APPLICATION")));
			sBean.setAuditId(rs.getInt("AUDIT_ID"));
			sBean.setObjectId(rs.getString("OBJECT_ID"));
			sBean.setObjectType(rs.getString("OBJECT_TYPE"));
			sBean.setWho(rs.getString("WHO"));

			if (rs.getDate("WHEN") != null)
				sBean.setWhen(new java.util.Date(rs.getDate("WHEN").getTime()));

			sBean.setActionType(ActionTypeConstant.get(rs.getString("ACTION_TYPE")));

			oracle.sql.CLOB oldclob = (oracle.sql.CLOB) rs.getClob("OBJECT_DATA_OLD");
			if (oldclob != null) {
				try {
					InputStream is = oldclob.getAsciiStream();
					byte[] buffer = new byte[4096];
					java.io.OutputStream outputStream = new java.io.ByteArrayOutputStream();
					int read = -1;
					while ((read = is.read(buffer)) != -1)
						outputStream.write(buffer, 0, read);
					outputStream.close();
					is.close();
					sBean.setOldData(outputStream.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			oracle.sql.CLOB newclob = (oracle.sql.CLOB) rs.getClob("OBJECT_DATA_NEW");
			if (newclob != null) {
				try {
					InputStream is = newclob.getAsciiStream();
					byte[] buffer = new byte[4096];
					java.io.OutputStream outputStream = new java.io.ByteArrayOutputStream();
					int read = -1;
					while ((read = is.read(buffer)) != -1)
						outputStream.write(buffer, 0, read);
					outputStream.close();
					is.close();
					sBean.setNewData(outputStream.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}

}
