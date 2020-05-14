package com.awsd.mail.dao;

import java.io.File;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.dao.DAOUtils;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EmailManager {

	public static void addEmailBean(EmailBean abean) throws EmailException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.ms_email.add_email(?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getFrom());
			stat.setString(2, abean.getToComplete());
			stat.setString(3, abean.getCCComplete());
			stat.setString(4, abean.getBCCComplete());
			stat.setString(5, abean.getSubject());

			oracle.sql.CLOB newClob = oracle.sql.CLOB.createTemporary(con, false, oracle.sql.CLOB.DURATION_SESSION);

			newClob.putString(1, abean.getBody());

			((OracleCallableStatement) stat).setCLOB(6, newClob);

			stat.setString(7, abean.getContentType());

			if (abean.getAttachments() != null)
				stat.setString(8, abean.getAttachments()[0].getAbsolutePath());
			else
				stat.setNull(8, OracleTypes.VARCHAR);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addEmailBean(EmailBean abean): " + e);
			throw new EmailException(e);
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

	public static void addEmailBean(EmailBean abean, Date queuedFor) throws EmailException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.ms_email.add_email_to_queue(?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getFrom());
			stat.setString(2, abean.getToComplete());
			stat.setString(3, abean.getCCComplete());
			stat.setString(4, abean.getBCCComplete());
			stat.setString(5, abean.getSubject());

			java.sql.Clob newClob = con.createClob(); //oracle.sql.CLOB.createTemporary(con, false, oracle.sql.CLOB.DURATION_SESSION);

			newClob.setString(1, abean.getBody());

			stat.setClob(6, newClob); //((OracleCallableStatement) stat).setCLOB(6, newClob);

			stat.setString(7, abean.getContentType());

			if (abean.getAttachments() != null)
				stat.setString(8, abean.getAttachments()[0].getAbsolutePath());
			else
				stat.setNull(8, OracleTypes.VARCHAR);

			if (queuedFor != null) {
				stat.setTimestamp(9, new java.sql.Timestamp(queuedFor.getTime()));
			}
			else {
				stat.setTimestamp(9, new java.sql.Timestamp((new Date()).getTime()));
			}

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addEmailBean(EmailBean abean): " + e);
			throw new EmailException(e);
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

	public static void sentEmailBean(int id, String smtp_error) throws EmailException {

		try (Connection con = DAOUtils.getConnection();
				CallableStatement stat = con.prepareCall("begin awsd_user.ms_email.process_email(?,?); end;")) {

			stat.setInt(1, id);
			stat.setString(2, smtp_error);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteEmailBean(int id): " + e);
			throw new EmailException("Can not delete EmailBean from DB.", e);
		}
	}

	public static void sentEmailBean(Connection con, int id, String smtp_error) throws EmailException {

		CallableStatement stat = null;

		try {
			stat = con.prepareCall("begin awsd_user.ms_email.process_email(?,?); end;");

			stat.setInt(1, id);
			stat.setString(2, smtp_error);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteEmailBean(int id): " + e);
			throw new EmailException("Can not delete EmailBean from DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
		}
	}

	public static void sentEmailBean(Connection con, CallableStatement stat, int id, String smtp_error)
			throws EmailException {

		try {
			stat.setInt(1, id);
			stat.setString(2, smtp_error);
			stat.addBatch();
		}
		catch (SQLException e) {
			System.err.println("void deleteEmailBean(int id): " + e);
			throw new EmailException("Can not delete EmailBean from DB.", e);
		}
	}

	public static EmailBean getEmailBean(int id) throws EmailException {

		EmailBean email = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ms_email.get_email(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				email = createEmailBean(rs);

		}
		catch (SQLException e) {
			System.err.println("EmailBean getEmailBean(int id): " + e);
			throw new EmailException("Can not extract EmailBean from DB.", e);
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

		return email;
	}

	public static EmailBean getNextEmailBean() throws EmailException {

		EmailBean email = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ms_email.get_next_email; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				email = createEmailBean(rs);

		}
		catch (SQLException e) {
			System.err.println("EmailBean getEmailBean(int id): " + e);
			throw new EmailException("Can not extract EmailBean from DB.", e);
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

		return email;
	}

	public static List<EmailBean> getNextEmailBeanBatch() throws EmailException {

		List<EmailBean> emails = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.ms_email.get_next_email_batch; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				emails.add(createEmailBean(rs));
		}
		catch (SQLException e) {
			System.err.println("EmailBean getNextEmailBeanBatch(): " + e);
			throw new EmailException("Can not extract EmailBean from DB.", e);
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

		return emails;
	}

	public static EmailBean createEmailBean(ResultSet rs) {

		EmailBean abean = null;

		try {
			abean = new EmailBean();

			abean.setId(rs.getInt("MSG_ID"));
			abean.setFrom(rs.getString("FROM"));
			abean.setTo(rs.getString("TO"));
			abean.setCC(rs.getString("CC"));
			abean.setBCC(rs.getString("BCC"));
			abean.setSubject(rs.getString("SUBJECT"));

			oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("BODY");
			InputStream is = clob.getAsciiStream();
			byte[] buffer = new byte[4096];
			java.io.OutputStream outputStream = new java.io.ByteArrayOutputStream();
			int read = -1;
			while ((read = is.read(buffer)) != -1)
				outputStream.write(buffer, 0, read);
			outputStream.close();
			is.close();
			abean.setBody(outputStream.toString());

			abean.setContentType(rs.getString("CONTENT_TYPE"));

			if (rs.getString("ATTACHMENT") != null) {
				abean.setAttachments(new File[] {
						new File(rs.getString("ATTACHMENT"))
				});
			}

			if (rs.getDate("SENT_DATE") != null)
				abean.setSentDate(new java.util.Date(rs.getDate("SENT_DATE").getTime()));

			abean.setDeleted(rs.getBoolean("DELETED"));
			abean.setSMTPError(rs.getString("SMTP_ERROR"));
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
