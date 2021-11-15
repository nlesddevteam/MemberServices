package com.esdnl.webupdatesystem.tenders.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.bean.TendersBean;
import com.esdnl.webupdatesystem.tenders.bean.TendersFileBean;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;
public class TendersManager {
	static Map<Integer,SchoolZoneBean> map = null;
	public static int addNewTender(TendersBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_tender(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getTenderNumber());
			stat.setInt(3, ebean.getTenderZone().getZoneId());
			stat.setString(4, ebean.getTenderTitle());
			stat.setTimestamp(5, new Timestamp(ebean.getClosingDate().getTime()));
			stat.setString(6, ebean.getTenderDoc());
			stat.setInt(7, ebean.getTenderOpeningLocation().getZoneId());
			stat.setInt(8, ebean.getTenderStatus().getValue());
			stat.setString(9, ebean.getAddedBy());
			stat.setString(10, ebean.getDocUploadName());
			if(ebean.getAwardedDate() != null){
				stat.setTimestamp(11, new Timestamp(ebean.getAwardedDate().getTime()));
			}else{
				stat.setTimestamp(11, null);
			}
			stat.setString(12, ebean.getAwardedTo());
			stat.setDouble(13, ebean.getContractValue());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewTender(TendersBean ebean) " + e);
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
	
	public static Vector<TendersBean> getTenders() throws TenderException {
		Vector<TendersBean> tenders = null;
		TendersBean tender = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			tenders = new Vector<TendersBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_tenders; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				tender = createTenderBean(rs);
				tenders.add(tender);
			}
		}
		catch (Exception e) {
			System.err.println("TendersManager.getTenders: " + e);
			throw new TenderException("Can not extract tenders from DB: " + e);
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
		return tenders;
	}
	public static Vector<TendersBean> getTendersFull() throws TenderException {
		Vector<TendersBean> tenders = null;
		TendersBean tender = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<TendersFileBean>fbeans = new ArrayList<TendersFileBean>();
		try {
			tenders = new Vector<TendersBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_tenders_full; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int currentid=-1;
			while (rs.next()) {
				if(currentid == -1) {
					//first tender
					tender = createTenderBeanFull(rs);
					currentid=tender.getId();
					if(rs.getInt("FILEID") > 0) {
						//tender file create bean and add to collection
						TendersFileBean filebean = TendersFileManager.createTendersFileBeanFull(rs);
						fbeans.add(filebean);
					}
				}else {
					if(currentid == rs.getInt("ID")) {
						//same tender another file
						TendersFileBean filebean = TendersFileManager.createTendersFileBeanFull(rs);
						fbeans.add(filebean);
					}else {
						//next tender
						//add files to tender
						if(!fbeans.isEmpty()) {
							tender.setOtherTendersFiles(fbeans);
						}
						//add tender to list
						tenders.add(tender);
						//reset objects
						tender = createTenderBeanFull(rs);
						currentid=tender.getId();
						fbeans = new ArrayList<TendersFileBean>();
						if(rs.getInt("FILEID") > 0) {
							TendersFileBean filebean = TendersFileManager.createTendersFileBeanFull(rs);
							fbeans.add(filebean);
						}
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println("TendersManager.getTendersFull: " + e);
			throw new TenderException("Can not extract tenders from DB: " + e);
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
		return tenders;
	}	
	public static void updateTender(TendersBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_tender(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getTenderNumber());
			stat.setInt(2, ebean.getTenderZone().getZoneId());
			stat.setString(3, ebean.getTenderTitle());
			stat.setTimestamp(4, new Timestamp(ebean.getClosingDate().getTime()));
			stat.setString(5, ebean.getTenderDoc());
			stat.setInt(6, ebean.getTenderOpeningLocation().getZoneId());
			stat.setInt(7, ebean.getTenderStatus().getValue());
			stat.setString(8, ebean.getAddedBy());
			stat.setString(9, ebean.getDocUploadName());
			stat.setInt(10,ebean.getId());
			if(ebean.getAwardedDate() != null){
				stat.setTimestamp(11, new Timestamp(ebean.getAwardedDate().getTime()));
			}else{
				stat.setTimestamp(11, null);
			}
			stat.setString(12, ebean.getAwardedTo());
			stat.setDouble(13, ebean.getContractValue());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int updateTender(TendersBean ebean) " + e);
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
	public static void deleteTender(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_tender(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int deleteTender(TendersBean ebean) " + e);
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
	public static TendersBean getTenderById(int id) throws TenderException {
		TendersBean tender = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_tender_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				tender = createTenderBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("TendersManager.getTenderById: " + e);
			throw new TenderException("Can not extract tender from DB: " + e);
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
		return tender;
	}
	public static Vector<TendersBean> getTendersByStatus(int statusid) throws TenderException {
		Vector<TendersBean> tenders = null;
		TendersBean tender = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			tenders = new Vector<TendersBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_tenders_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, statusid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				tender = createTenderBean(rs);
				tenders.add(tender);
			}
		}
		catch (Exception e) {
			System.err.println("Vector<TendersBean> getTendersByStatus(int statusid): " + e);
			throw new TenderException("Can not extract tenders from DB: " + e);
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
		return tenders;
	}	
	public static TendersBean createTenderBean(ResultSet rs) {
		TendersBean abean = null;
		try {
			
			abean = new TendersBean();
			abean.setId(rs.getInt("ID"));
			abean.setTenderNumber(rs.getString("TENDER_NUMBER"));
			//abean.setTenderZone(SchoolZoneService.getSchoolZoneBean(rs.getInt("REGION")));
			abean.setTenderZone(getSchoolZone(rs.getInt("REGION")));
			abean.setTenderTitle(rs.getString("TENDER_TITLE"));
			abean.setClosingDate(new java.util.Date(rs.getTimestamp("CLOSING_DATE").getTime()));
			abean.setTenderDoc(rs.getString("TENDER_DOC"));
			abean.setTenderOpeningLocation(getSchoolZone(rs.getInt("OPENING_LOCATION")));
			//abean.setTenderOpeningLocation(SchoolZoneService.getSchoolZoneBean(rs.getInt("OPENING_LOCATION")));
			abean.setTenderStatus(TenderStatus.get(rs.getInt("TENDER_STATUS")));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setDocUploadName(rs.getString("DOC_UPLOAD_NAME"));
			Timestamp ts= rs.getTimestamp("AWARDED_DATE");
			if(ts != null){
				abean.setAwardedDate(new java.util.Date(rs.getTimestamp("AWARDED_DATE").getTime()));
			}
			abean.setAwardedTo(rs.getString("AWARDED_TO"));
			abean.setContractValue(rs.getDouble("CONTRACT_VALUE"));
			abean.setOtherTendersFiles(TendersFileManager.getTendersFiles(abean.getId()));
			try {
				abean.setTeBean(TenderExceptionManager.createTenderExceptionBean(rs));
			}catch(Exception ee) {
				abean.setTeBean(null);
			}
			
			
		}
		catch (SQLException e) {
			abean = null;
		}  catch (TenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}
	public static SchoolZoneBean getSchoolZone(Integer zid) {
		SchoolZoneBean z=null;
		if(map == null) {
			try {
				map = SchoolZoneService.getSchoolZoneBeansMap();
			} catch (SchoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			z=map.get(zid);
			
		}else {
			z=map.get(zid);
		}
		return z;
	}
	public static TendersBean createTenderBeanFull(ResultSet rs) {
		TendersBean abean = null;
		try {
			
			abean = new TendersBean();
			abean.setId(rs.getInt("ID"));
			abean.setTenderNumber(rs.getString("TENDER_NUMBER"));
			//abean.setTenderZone(SchoolZoneService.getSchoolZoneBean(rs.getInt("REGION")));
			abean.setTenderZone(getSchoolZone(rs.getInt("REGION")));
			abean.setTenderTitle(rs.getString("TENDER_TITLE"));
			abean.setClosingDate(new java.util.Date(rs.getTimestamp("CLOSING_DATE").getTime()));
			abean.setTenderDoc(rs.getString("TENDER_DOC"));
			abean.setTenderOpeningLocation(getSchoolZone(rs.getInt("OPENING_LOCATION")));
			//abean.setTenderOpeningLocation(SchoolZoneService.getSchoolZoneBean(rs.getInt("OPENING_LOCATION")));
			abean.setTenderStatus(TenderStatus.get(rs.getInt("TENDER_STATUS")));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setDocUploadName(rs.getString("DOC_UPLOAD_NAME"));
			Timestamp ts= rs.getTimestamp("AWARDED_DATE");
			if(ts != null){
				abean.setAwardedDate(new java.util.Date(rs.getTimestamp("AWARDED_DATE").getTime()));
			}
			abean.setAwardedTo(rs.getString("AWARDED_TO"));
			abean.setContractValue(rs.getDouble("CONTRACT_VALUE"));
			//abean.setOtherTendersFiles(TendersFileManager.getTendersFiles(abean.getId()));
			try {
				abean.setTeBean(TenderExceptionManager.createTenderExceptionBean(rs));
			}catch(Exception ee) {
				abean.setTeBean(null);
			}
			
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
