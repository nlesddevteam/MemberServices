package com.esdnl.colaboration.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.colaboration.bean.CollaborationException;
import com.esdnl.colaboration.bean.DiscussionBean;
import com.esdnl.colaboration.bean.DiscussionGroupBean;
import com.esdnl.colaboration.bean.GroupCommentBean;
import com.esdnl.dao.DAOUtils;

public class CollaborationManager {

	public static void addDiscussionBean(DiscussionBean abean) throws CollaborationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.collaboration_pkg.add_discussion(?, ?,?); end;");

			stat.setDate(1, new java.sql.Date(abean.getDiscussionDate().getTime()));
			stat.setString(2, abean.getTitle());
			stat.setString(3, abean.getDescription());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addDiscussionBean(DiscussionBean abean): " + e);
			throw new CollaborationException("Can not add NominationBean to DB.", e);
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

	public static DiscussionBean[] getDiscussionBeans() throws CollaborationException {

		Vector<DiscussionBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<DiscussionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_discussions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createDiscussionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DiscussionBean[] getDiscussionBeans(): " + e);
			throw new CollaborationException("Can not extract NominationBean from DB.", e);
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

		return ((DiscussionBean[]) beans.toArray(new DiscussionBean[0]));
	}

	public static DiscussionBean[] getReleasedDiscussionBeans() throws CollaborationException {

		Vector<DiscussionBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<DiscussionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_released_discussions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createDiscussionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DiscussionBean[] getDiscussionBeans(): " + e);
			throw new CollaborationException("Can not extract NominationBean from DB.", e);
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

		return ((DiscussionBean[]) beans.toArray(new DiscussionBean[0]));
	}

	public static DiscussionBean[] getTodaysDiscussionBeans() throws CollaborationException {

		Vector<DiscussionBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<DiscussionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_today_discussions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createDiscussionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DiscussionBean[] getDiscussionBeans(): " + e);
			throw new CollaborationException("Can not extract NominationBean from DB.", e);
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

		return ((DiscussionBean[]) beans.toArray(new DiscussionBean[0]));
	}

	public static DiscussionBean getDiscussionBean(int id) throws CollaborationException {

		DiscussionBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_discussion(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createDiscussionBean(rs);
		}
		catch (SQLException e) {
			System.err.println(" DiscussionBean getDiscussionBean(int id): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

	public static void deleteDiscussionBeans(int id) throws CollaborationException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.collaboration_pkg.del_discussion(?); end;");
			stat.setInt(1, id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("void deleteDiscussionBeans(int id): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

	public static void releaseDiscussionBeans(int id) throws CollaborationException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.collaboration_pkg.release_discussion(?); end;");
			stat.setInt(1, id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("void releaseDiscussionBeans(int id): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

	public static DiscussionGroupBean addDiscussionGroupBean(DiscussionGroupBean abean) throws CollaborationException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.add_discussion_group(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getDiscussion().getId());
			stat.setString(3, abean.getGroupName());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);

			abean.setId(id);

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addDiscussionBean(DiscussionBean abean): " + e);
			throw new CollaborationException("Can not add DiscussionBeans to DB.", e);
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

		return abean;
	}

	public static DiscussionGroupBean[] getDiscussionGroupBeans(DiscussionBean dbean) throws CollaborationException {

		Vector<DiscussionGroupBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<DiscussionGroupBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_discussion_groups(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, dbean.getId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createDiscussionGroupBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DiscussionGroupBean[] getDiscussionGroupBeans(DiscussionBean dbean): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

		return ((DiscussionGroupBean[]) beans.toArray(new DiscussionGroupBean[0]));
	}

	public static DiscussionGroupBean getDiscussionGroupBean(int id) throws CollaborationException {

		DiscussionGroupBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_discussion_group(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createDiscussionGroupBean(rs);
		}
		catch (SQLException e) {
			System.err.println(" DiscussionGroupBean getDiscussionGroupBean(int id): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

	public static GroupCommentBean[] addGroupCommentBean(GroupCommentBean abean) throws CollaborationException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector<GroupCommentBean> beans = null;

		try {
			beans = new Vector<GroupCommentBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.add_group_comment(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getGroup().getId());
			stat.setString(3, abean.getComment());

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createGroupCommentBean(rs));

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addDiscussionBean(DiscussionBean abean): " + e);
			throw new CollaborationException("Can not add DiscussionBeans to DB.", e);
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

		return (GroupCommentBean[]) beans.toArray(new GroupCommentBean[0]);
	}

	public static GroupCommentBean[] getGroupCommentBeans(DiscussionGroupBean dbean) throws CollaborationException {

		Vector<GroupCommentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<GroupCommentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.get_discussion_group_comments(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, dbean.getId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createGroupCommentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("GroupCommentBean[] getGroupCommentBeans(DiscussionGroupBean dbean): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

		return ((GroupCommentBean[]) beans.toArray(new GroupCommentBean[0]));
	}

	public static GroupCommentBean[] deleteGroupCommentBeans(int id) throws CollaborationException {

		Vector<GroupCommentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<GroupCommentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.collaboration_pkg.del_group_comment(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createGroupCommentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("GroupCommentBean[] getGroupCommentBeans(DiscussionGroupBean dbean): " + e);
			throw new CollaborationException("Can not extract DiscussionBeans from DB.", e);
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

		return ((GroupCommentBean[]) beans.toArray(new GroupCommentBean[0]));
	}

	public static DiscussionBean createDiscussionBean(ResultSet rs) {

		DiscussionBean abean = null;

		try {
			abean = new DiscussionBean();

			abean.setId(rs.getInt("DISCUSSION_ID"));
			abean.setTitle(rs.getString("DISCUSSION_TITLE"));
			abean.setDescription(rs.getString("DISCUSSION_DESC"));
			abean.setDiscussionDate(new java.util.Date(rs.getDate("DISCUSSION_DATE").getTime()));
			abean.setReleased(rs.getBoolean("RELEASED"));

			/*
			 * try{
			 * abean.setGroups(CollaborationManager.getDiscussionGroupBeans(abean)); }
			 * catch(CollaborationException ex){ abean.setGroups(null); }
			 */
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

	public static DiscussionGroupBean createDiscussionGroupBean(ResultSet rs) {

		DiscussionGroupBean abean = null;

		try {
			abean = new DiscussionGroupBean();

			abean.setId(rs.getInt("GROUP_ID"));
			abean.setGroupName(rs.getString("GROUP_NAME"));
			// abean.setDiscussion(createDiscussionBean(rs));

			try {
				abean.setComments(CollaborationManager.getGroupCommentBeans(abean));
			}
			catch (CollaborationException ex) {
				abean.setComments(null);
			}
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

	public static GroupCommentBean createGroupCommentBean(ResultSet rs) {

		GroupCommentBean cbean = null;

		try {
			cbean = new GroupCommentBean();

			cbean.setId(rs.getInt("COMMENT_ID"));
			cbean.setComment(rs.getString("GROUP_COMMENT"));
			cbean.setCommentDate(new java.util.Date(rs.getDate("COMMENT_DATE").getTime()));
			// cbean.setGroup(createDiscussionGroupBean(rs));
		}
		catch (SQLException e) {
			cbean = null;
		}

		return cbean;
	}

}
