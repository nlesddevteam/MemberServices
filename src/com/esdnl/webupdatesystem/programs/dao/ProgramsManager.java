package com.esdnl.webupdatesystem.programs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.programs.bean.ProgramsBean;
import com.esdnl.webupdatesystem.programs.bean.ProgramsException;
import com.esdnl.webupdatesystem.programs.constants.ProgramsLevel;
import com.esdnl.webupdatesystem.programs.constants.ProgramsRegion;

public class ProgramsManager {
	public static int addProgram(ProgramsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_program(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getDescriptorTitle());
			stat.setInt(3, ebean.getpRegion().getValue());
			stat.setInt(4, ebean.getpLevel().getValue());
			stat.setString(5, ebean.getAddedBy());
			stat.setInt(6, ebean.getProgramStatus());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addProgram(ProgramsBean ebean) " + e);
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
	public static Vector<ProgramsBean> getPrograms() throws ProgramsException {
		Vector<ProgramsBean> mms = null;
		ProgramsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<ProgramsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_programs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProgramsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector<ProgramsBean> getPrograms() " + e);
			throw new ProgramsException("Can not extract Programs from DB: " + e);
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
	public static ProgramsBean getProgramById(int id) throws BlogsException {
		ProgramsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_program_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProgramsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("PolicyBean getProgramById(int id) " + e);
			throw new BlogsException("Can not extract Programs from DB: " + e);
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
	public static void updatePrograms(ProgramsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_program(?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getDescriptorTitle());
			stat.setInt(2, ebean.getpRegion().getValue());
			stat.setInt(3, ebean.getpLevel().getValue());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getId());
			stat.setInt(6, ebean.getProgramStatus());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void int updateProgram(ProgramsBean ebean) " + e);
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
	public static void deleteProgram(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_program(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteProgram(Integer tid) " + e);
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
	public static Vector<ProgramsBean> getProgramsByLevel(int levelid) throws ProgramsException {
		Vector<ProgramsBean> mms = null;
		ProgramsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<ProgramsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_programs_by_level(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, levelid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProgramsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector<ProgramsBean> getProgramsByLevel(int levelid) " + e);
			throw new ProgramsException("Can not extract Programs from DB: " + e);
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
	public static Vector<ProgramsBean> getProgramsByRegion(int regionid) throws ProgramsException {
		Vector<ProgramsBean> mms = null;
		ProgramsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<ProgramsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_programs_by_region(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, regionid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProgramsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector<ProgramsBean> getProgramsByRegion(int levelid) " + e);
			throw new ProgramsException("Can not extract Programs from DB: " + e);
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
	public static ProgramsBean createProgramsBean(ResultSet rs) {
		ProgramsBean abean = null;
		try {
			abean = new ProgramsBean();
			abean.setId(rs.getInt("ID"));
			abean.setDescriptorTitle(rs.getString("DESCRIPTOR_TITLE"));
			abean.setpRegion(ProgramsRegion.get(rs.getInt("P_REGION")));
			abean.setpLevel(ProgramsLevel.get(rs.getInt("P_LEVEL")));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setOtherProgramsFiles(ProgramsFileManager.getProgramsFiles(abean.getId()));
			abean.setProgramStatus(rs.getInt("STATUS"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (ProgramsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
