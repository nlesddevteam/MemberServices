package com.esdnl.webupdatesystem.programs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.programs.bean.ProgramsException;
import com.esdnl.webupdatesystem.programs.bean.ProgramsFileBean;

public class ProgramsFileManager {
	public static int addProgramFile(ProgramsFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_programs_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getPfTitle());
			stat.setString(3, ebean.getPfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getProgramsId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addProgramsFile(ProgramsFileBean ebean)" + e);
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
	public static ArrayList<ProgramsFileBean> getProgramsFiles(Integer programid) throws ProgramsException {
		ArrayList<ProgramsFileBean> mms = null;
		ProgramsFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<ProgramsFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_programs_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, programid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProgramsFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProgramsFileBean> getProgramsFiles(Integer programid) " + e);
			throw new ProgramsException("Can not extract Programs Files from DB: " + e);
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
		return mms;
	}
	public static void deleteProgramFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_programs_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteProgramFile(Integer tid) " + e);
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
	public static ProgramsFileBean createProgramsFileBean(ResultSet rs) {
		ProgramsFileBean abean = null;
		try {
			abean = new ProgramsFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setPfTitle(rs.getString("PF_TITLE"));
			abean.setPfDoc(rs.getString("PF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setProgramsId(rs.getInt("PROGRAMS_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
