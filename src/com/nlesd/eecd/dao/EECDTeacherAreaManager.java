package com.nlesd.eecd.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.constants.TeacherAreaStatus;
public class EECDTeacherAreaManager {
	public static int addTeacherArea(EECDTeacherAreaBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.add_teacher_area(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getPersonnelId());
			stat.setInt(3, vbean.getAreaId());
			stat.setString(4, vbean.getSchoolYear());
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addTeacherArea(EECDTeacherAreaBean vbean): "
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
	public static TreeMap<Integer,EECDTeacherAreaBean> getAllEECDAreas(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,EECDTeacherAreaBean> list = new TreeMap<Integer,EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_all_ta_areas(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDTeacherAreaBean abean = new EECDTeacherAreaBean();
				abean = createEECDTeacherAreaBean(rs,false);
				list.put(abean.getAreaId(),abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,EECDTeacherAreaBean> getAllEECDAreas(int pid): "
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
	public static TreeMap<Integer,EECDTeacherAreaBean> getAllEECDAreasSchoolYear(int pid,String syear) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,EECDTeacherAreaBean> list = new TreeMap<Integer,EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_all_ta_areas_sy(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.setString(3,syear);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDTeacherAreaBean abean = new EECDTeacherAreaBean();
				abean = createEECDTeacherAreaBean(rs,false);
				list.put(abean.getAreaId(),abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,EECDTeacherAreaBean> getAllEECDAreas(int pid): "
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
	public static void deleteTeacherArea(int aid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.delete_eecd_ta(?); end;");
			stat.setInt(1, aid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteTeacherArea(int aid): "
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
	public static void updateCurrentStatusTeacherArea(int aid, int tastatus) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.update_current_status(?,?); end;");
			stat.setInt(1, aid);
			stat.setInt(2, tastatus);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateCurrentStatusTeacherArea(int aid, int tastatus): "
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
	public static EECDTeacherAreaBean getEECDTeacherAreaById(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("EECDTeacherAreaBean getEECDTeacherAreaById(int pid): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTeacherApprovedListById(int listid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_area_approved_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, listid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTeacherApprovedListById(int listid): "
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
	public static void addToShortlist(int aid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.add_to_shortlist(?); end;");
			stat.setInt(1, aid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void addToShortlist(int aid)): "
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
	public static void addToShortlistNew(int aid,int sid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.add_to_shortlist_n(?,?); end;");
			stat.setInt(1, aid);
			stat.setInt(2, sid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void addToShortlist(int aid)): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTAShortListById(int listid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_short_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, listid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTAShortListById(int listid): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTAShortListByIdNew(int listid,int sid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_short_list_n(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, listid);
			stat.setInt(3, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTAShortListByIdNew(int listid,int sid): "
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
	public static void removeFromShortlist(int aid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.remove_from_shortlist(?); end;");
			stat.setInt(1, aid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void removeFromShortlist(int aid): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTAShortListByIds(String idlist) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_short_list_m(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, idlist);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTAShortListByIds(String idlist): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTAListStatusById(int listid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_list_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, listid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTAShortListById(int listid): "
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
	public static ArrayList<EECDTeacherAreaBean> getEECDTAListStatusByIdSchoolYear(int listid,String syear) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDTeacherAreaBean abean=null;
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_ta_list_status_sy(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, listid);
			stat.setString(3, syear);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createEECDTeacherAreaBean(rs,true);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDTeacherAreaBean> getEECDTAShortListById(int listid): "
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
	public static EECDTeacherAreaBean createEECDTeacherAreaBean(ResultSet rs,boolean extendedinfo) {
		EECDTeacherAreaBean abean = null;
		try {
				abean = new EECDTeacherAreaBean();
				abean.setId(rs.getInt("ID"));
				abean.setPersonnelId(rs.getInt("PERSONNEL_ID"));
				abean.setAreaId(rs.getInt("AREA_ID"));
				if(!(rs.getTimestamp("DATE_SUBMITTED") ==  null)){
					abean.setDateSubmitted(new java.util.Date(rs.getTimestamp("DATE_SUBMITTED").getTime()));
				}
				abean.setCurrentStatus(TeacherAreaStatus.get(rs.getInt("CURRENT_STATUS")));
				if(rs.getInt("IS_SELECTED") > 0){
					abean.setSelected(true);
				}else{
					abean.setSelected(false);
				}
				//query includes area description
				if(extendedinfo){
					abean.setAreaDescription(rs.getString("AREA_DESCRIPTION"));
					abean.setTeacherName(rs.getString("PERSONNEL_FIRSTNAME") + " " + rs.getString("PERSONNEL_LASTNAME"));
					abean.setSchoolName(rs.getString("SCHOOL_NAME"));
					abean.setTeacherEmail(rs.getString("PERSONNEL_EMAIL"));
				}
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
