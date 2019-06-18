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
import com.esdnl.complaint.model.bean.ComplaintCommentBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.dao.DAOUtils;

public class ComplaintCommentManager {

	public static void addComplaintCommentBean(ComplaintCommentBean comment) throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.add_complaint_comment(?,?,?); end;");

			stat.setInt(1, comment.getComplaint().getId());
			stat.setInt(2, comment.getMadeBy().getPersonnelID());
			stat.setString(3, comment.getComment());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addComplaintCommentBean(ComplaintCommentBean comment): " + e);
			throw new ComplaintException("Can not add ComplaintCommentBean to DB.", e);
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

	public static ComplaintCommentBean[] getComplaintCommentBean(ComplaintBean complaint) throws ComplaintException {

		TreeSet comments = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			comments = new TreeSet(new Comparator() {

				public int compare(Object o1, Object o2) {

					ComplaintCommentBean b1 = (ComplaintCommentBean) o1;
					ComplaintCommentBean b2 = (ComplaintCommentBean) o2;

					return b1.getSubmittedDate().compareTo(b2.getSubmittedDate());
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint_comments(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, complaint.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				comments.add(createComplaintCommentBean(rs, complaint));

		}
		catch (SQLException e) {
			System.err.println("ComplaintCommentBean[] getComplaintCommentBean(ComplaintBean complaint): " + e);
			throw new ComplaintException("Can not extract ComplaintCommentBean from DB.", e);
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

		return (ComplaintCommentBean[]) comments.toArray(new ComplaintCommentBean[0]);
	}

	public static ComplaintCommentBean createComplaintCommentBean(ResultSet rs, ComplaintBean complaint) {

		ComplaintCommentBean abean = null;

		try {
			abean = new ComplaintCommentBean();

			abean.setId(rs.getInt("COMMENT_ID"));
			abean.setComplaint(complaint);
			abean.setSubmittedDate(new java.util.Date(rs.getTimestamp("SUBMITTED_DATE").getTime()));
			try {
				abean.setMadeBy(PersonnelDB.getPersonnel(rs.getInt("BY_WHO")));
			}
			catch (PersonnelException e) {
				abean.setMadeBy(null);
			}

			abean.setComment(rs.getString("COMMENT_TEXT"));
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