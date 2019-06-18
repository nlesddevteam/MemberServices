package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectFundingBean;
public class ProjectFundingManager {
	public static Integer addNewProjectFunding(ProjectFundingBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_new_project_funding(?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setInt(3,pb.getFundingId());
				stat.setString(4,pb.getContactName());
				stat.setString(5,pb.getContactEmail());
				stat.setString(6,pb.getContactPhone());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProjectFunding " + e);
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
	public static ProjectFundingBean getProjectFundingById(int id) throws Fund3Exception {
		ProjectFundingBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_funding_by_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectFundingBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectFundingBean getProjectFundingById(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectFunding from DB: " + e);
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
		return mm;
	}
	public static ArrayList<ProjectFundingBean> getProjectFundingProjectId(int id) throws Fund3Exception {
		ArrayList<ProjectFundingBean>list = new ArrayList<ProjectFundingBean>();
		ProjectFundingBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_funding_by_pid(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectFundingBean(rs);
					list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProjectFundingBean> getProjectFundingProjectId(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectFunding from DB: " + e);
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
		return list;
	}
	public static void deleteFunding(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_funding(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteFunding(Integer tid) " + e);
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
	public static ArrayList<String> getProjectFundingContacts(String ids) throws Fund3Exception {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_fp_contacts(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, ids);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					list.add(rs.getString("CONTACT_NAME"));
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<String> getProjectFundingCOntacts(String ids " + e);
			throw new Fund3Exception("Can not extract ProjectFunding from DB: " + e);
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
		return list;
	}
	public static ProjectFundingBean createProjectFundingBean(ResultSet rs) {
		ProjectFundingBean abean = null;
		try {
				abean = new ProjectFundingBean();
				abean.setId(rs.getInt("ID"));
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setContactName(rs.getString("CONTACT_NAME"));
				abean.setContactEmail(rs.getString("CONTACT_EMAIL"));
				abean.setContactPhone(rs.getString("CONTACT_PHONE"));
				abean.setFundingText(rs.getString("PFTEXT"));
				abean.setFundingId(rs.getInt("FUNDING_ID"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
