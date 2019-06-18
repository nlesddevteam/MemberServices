package com.esdnl.complaint.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.TreeSet;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintDocumentBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.dao.DAOUtils;

public class ComplaintDocumentManager {

	public static void addComplaintDocumentBean(ComplaintDocumentBean doc) throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.add_complaint_doc(?,?,?,?); end;");

			stat.setInt(1, doc.getComplaint().getId());
			stat.setInt(2, doc.getUploadedBy().getPersonnelID());
			stat.setString(3, doc.getTitle());
			stat.setString(4, doc.getFilename());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addComplaintDocumentBean(ComplaintDocumentBean doc): " + e);
			throw new ComplaintException("Can not add ComplaintDocumentBean to DB.", e);
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

	public static ComplaintDocumentBean[] getComplaintDocumentBean(ComplaintBean complaint) throws ComplaintException {

		TreeSet docs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			docs = new TreeSet(new Comparator() {

				public int compare(Object o1, Object o2) {

					ComplaintDocumentBean b1 = (ComplaintDocumentBean) o1;
					ComplaintDocumentBean b2 = (ComplaintDocumentBean) o2;

					return b1.getUploadedDate().compareTo(b2.getUploadedDate());
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint_docs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, complaint.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				docs.add(createComplaintDocumentBean(rs, complaint));

		}
		catch (SQLException e) {
			System.err.println("ComplaintDocumentBean[] getComplaintDocumentBean(ComplaintBean complaint): " + e);
			throw new ComplaintException("Can not extract ComplaintDocumentBean from DB.", e);
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

		return (ComplaintDocumentBean[]) docs.toArray(new ComplaintDocumentBean[0]);
	}

	public static ComplaintDocumentBean createComplaintDocumentBean(ResultSet rs, ComplaintBean complaint) {

		ComplaintDocumentBean abean = null;

		try {
			abean = new ComplaintDocumentBean();

			abean.setId(rs.getInt("DOC_ID"));
			abean.setComplaint(complaint);
			abean.setTitle(rs.getString("DOC_TITLE"));
			abean.setFilename(rs.getString("DOC_FILENAME"));
			abean.setUploadedDate(new java.util.Date(rs.getTimestamp("UPLOAD_DATE").getTime()));
			try {
				abean.setUploadedBy(PersonnelDB.getPersonnel(rs.getInt("UPLOADED_BY")));
			}
			catch (PersonnelException e) {
				abean.setUploadedBy(null);
			}
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