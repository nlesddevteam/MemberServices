package com.nlesd.eecd.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.constants.TeacherAreaStatus;
public class EECDAreaManager {
	public static ArrayList<EECDAreaBean> getAllEECDAreas() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_all_eecd_areas; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaBean abean = new EECDAreaBean();
				abean = createEECDAreaBean(rs,false);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDAreaBean> getAllEECDAreas(): "
					+ e);
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
		return list;
	}
	public static ArrayList<EECDAreaBean> getAllEECDAreasByPID(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_all_ta_areas_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaBean abean = new EECDAreaBean();
				abean = createEECDAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDAreaBean> getAllEECDAreas(): "
					+ e);
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
		return list;
	}	
	public static int addAreaDescription(EECDAreaBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.add_area_description(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getAreaDescription());
			stat.setString(3, vbean.getAddedBy());
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addAreaDescription(EECDAreaBean vbean): "
					+ e);
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
		return sid;
	}
	public static void deleteArea(int aid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.delete_eecd_area(?); end;");
			stat.setInt(1, aid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteArea(int aid): "
					+ e);
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
	public static void shortlistCompleted(int aid,String completedby) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.shortlist_completed(?,?); end;");
			stat.setInt(1, aid);
			stat.setString(2, completedby);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("shortlistCompleted(int aid,String completedby): "
					+ e);
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
	public static EECDAreaBean getEECDAreaById(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDAreaBean abean = new EECDAreaBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_eecd_area_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDAreaBean(rs,false);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("EECDAreaBean getEECDAreaById(int pid): "
					+ e);
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
	public static void unlockShortlist(int aid,String completedby) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.unlock_shortlist(?,?); end;");
			stat.setInt(1, aid);
			stat.setString(2, completedby);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void unlockShortlist(int aid,String completedby): "
					+ e);
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
	public static ArrayList<EECDAreaBean> getAllEECDAreasShortlisted() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_all_areas_sl; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaBean abean = new EECDAreaBean();
				abean = createEECDAreaBean(rs,false);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDAreaBean> getAllEECDAreasShortlisted(): "
					+ e);
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
		return list;
	}
	public static ArrayList<EECDAreaBean> getTeacherSelectedAreasByPID(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_teacher_selected_for(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaBean abean = new EECDAreaBean();
				abean = createEECDAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDAreaBean> getTeacherSelectedAreasByPID(int pid): "
					+ e);
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
		return list;
	}		
	public static EECDAreaBean createEECDAreaBean(ResultSet rs,boolean extendeddata) {
		EECDAreaBean abean = null;
		try {
				abean = new EECDAreaBean();
				abean.setId(rs.getInt("ID"));
				abean.setAreaDescription(rs.getString("AREA_DESCRIPTION"));
				abean.setAddedBy(rs.getString("ADDED_BY"));
				if(!(rs.getTimestamp("DATE_ADDED") ==  null)){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
				}
				if(rs.getInt("IS_DELETED") == 0){
					abean.setDeleted(true);
				}else{
					abean.setDeleted(false);
				}
				//now we check to see if
				if(extendeddata){
					//this is the query used on the teacher area page and the query includes additional information about the approval
					if(rs.getInt("CURRENT_STATUS") == TeacherAreaStatus.APPROVED_SCHOOL_ADMIN.getValue()){
						abean.setAdditionalText("Approved By School Administrator");
						abean.setCurrentStatus(rs.getInt("CURRENT_STATUS"));
					}else if(rs.getInt("CURRENT_STATUS") == TeacherAreaStatus.DECLINED_SCHOOL_ADMIN.getValue()){
						abean.setAdditionalText("Declined By School Administrator");
						abean.setCurrentStatus(rs.getInt("CURRENT_STATUS"));
					}
				}
				if(!(rs.getTimestamp("DATE_COMPLETED") ==  null)){
					abean.setDateCompleted(new java.util.Date(rs.getTimestamp("DATE_COMPLETED").getTime()));
				}
				if(rs.getInt("SHORTLIST_COMPLETED") == 1){
					abean.setShortlistCompleted(true);
				}else{
					abean.setShortlistCompleted(false);
				}
				abean.setCompletedBy(rs.getString("COMPLETED_BY"));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
