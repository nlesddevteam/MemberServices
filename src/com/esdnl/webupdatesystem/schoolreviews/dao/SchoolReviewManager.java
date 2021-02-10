package com.esdnl.webupdatesystem.schoolreviews.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionOptionBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolReviewManager {
	public static int addSchoolReview(SchoolReviewBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_review(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getSrName());
			if (ebean.getSrDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSrDescription());
				((OracleCallableStatement) stat).setClob(3, clobdesc);
			}
			else
			{
				stat.setNull(3, OracleTypes.CLOB);
			}
			stat.setString(4, ebean.getSrPhoto());

			stat.setString(5, ebean.getAddedBy());
			stat.setInt(6, ebean.getSrStatus());
			stat.setString(7, ebean.getSrSchoolYear());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addSchoolReview(SchoolReviewBean ebean) " + e);
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
	public static ArrayList<SchoolReviewBean> getSchoolReviews() {
		ArrayList<SchoolReviewBean> mms = null;
		SchoolReviewBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_school_reviews; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<SchoolReviewBean> getSchoolReviews() " + e);
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
	public static SchoolReviewBean getSchoolReviewById(int id) {
		SchoolReviewBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolReviewBean getSchoolReviewById(int id) " + e);
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
	public static void updateSchoolReview(SchoolReviewBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_school_review(?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getSrName());
			if (ebean.getSrDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSrDescription());
				((OracleCallableStatement) stat).setClob(2, clobdesc);
			}
			else
			{
				stat.setNull(2, OracleTypes.CLOB);
			}
			stat.setString(3, ebean.getSrPhoto());
			stat.setInt(4, ebean.getSrStatus());
			stat.setString(5, ebean.getSrSchoolYear());
			stat.setInt(6, ebean.getId());
			stat.execute();
			
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateSchoolReview(SchoolReviewBean ebean) " + e);
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
	public static void deleteSchoolReview(int rid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.WEB_UPDATE_SYSTEM_PKG.delete_school_review(?); end;");
			stat.setInt(1, rid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteSchoolReview(int rid): "
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
	public static ArrayList<SchoolReviewBean> getSchoolReviewsList() {
		ArrayList<SchoolReviewBean> mms = null;
		SchoolReviewBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_school_reviews_list; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static ArrayList<SchoolReviewBean> getSchoolReviewsList() " + e);
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
	public static ArrayList<SchoolReviewBean> getSchoolReviewsListFull() {
		ArrayList<SchoolReviewBean> mms = null;
		TreeMap <Integer,SchoolReviewSectionBean> mmssec = new TreeMap <Integer,SchoolReviewSectionBean>();
		ArrayList<SchoolReviewFileBean> flist = new ArrayList<SchoolReviewFileBean>();
		ArrayList<SchoolReviewSectionOptionBean> optionslist = new ArrayList<SchoolReviewSectionOptionBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_school_reviews_list_f; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int reviewcount=0;
			int currentreviewid=0;
			int currentsectionid=0;
			int currentfileid=0;
			SchoolReviewSectionBean secbean = null;
			SchoolReviewBean srbean =  null;
			while (rs.next()) {
				//first review
				if(reviewcount ==0) {
					srbean = SchoolReviewManager.createSchoolReviewBean(rs);
					//newly created bean we get the schools
					if(rs.getString("SCHOOLLIST") != null) {
						srbean.setSchoolList(rs.getString("SCHOOLLIST"));
					}
					currentreviewid=srbean.getId();
					//now check for sub records
					//sections
					if(rs.getInt("SEC_ID") > 0 ) {
						secbean = SchoolReviewSectionManager.createSchoolReviewSectionBean(rs);
						currentsectionid=secbean.getSecId();
						mmssec.put(rs.getInt("SEC_SORT_ID"),secbean);
					}
					//files
					if(rs.getInt("FILE_ID") > 0 && rs.getString("SO_TYPE") == "S") {
						SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
						currentfileid = filebean.getId();
						flist.add(filebean);
					}
					//section options
					if(rs.getInt("SO_ID") > 0) {
						SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
						optionslist.add(optionbean);
					}
					
					reviewcount++;
				}else {
					//next record
					if(currentreviewid == rs.getInt("ID")) {
						//same review figure out sub object
						if(currentsectionid == rs.getInt("SEC_ID")) {
							//same section check for file
							if(currentfileid == rs.getInt("FILE_ID")) {
								//should be a section option
								if(rs.getInt("SO_ID") > 0) {
									SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
									optionslist.add(optionbean);
								}
							}else {
								//new file
								if(rs.getInt("FILE_ID") > 0) {
									SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
									currentfileid = filebean.getId();
									flist.add(filebean);
								}else {
									SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
									optionslist.add(optionbean);
								}
								
							}
						}else {
							//new section
							//need to add sub objects to section objects and reset them
							if(!flist.isEmpty()) {
								secbean.setSecFiles(flist);
							}
							if(!optionslist.isEmpty()) {
								secbean.setSecOptions(optionslist);
							}
							
							//add section to 
							srbean.setSectionList(mmssec);
							//reset list
							flist = new ArrayList<SchoolReviewFileBean>();
							optionslist = new ArrayList<SchoolReviewSectionOptionBean>();
							//create section
							//now check for sub records
							//sections
							if(rs.getInt("SEC_ID") > 0 ) {
								secbean = SchoolReviewSectionManager.createSchoolReviewSectionBean(rs);
								currentsectionid=secbean.getSecId();
								mmssec.put(rs.getInt("SEC_SORT_ID"),secbean);
							}
							//files
							if(rs.getInt("FILE_ID") > 0 && rs.getString("SO_TYPE") == "S") {
								SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
								currentfileid = filebean.getId();
								flist.add(filebean);
							}
							//section options
							if(rs.getInt("SO_ID") > 0) {
								SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
								optionslist.add(optionbean);
							}
						}
					}else {
						//new review
						//add section list to school review
						srbean.setSectionList(mmssec);
						//add review to list
						mms.add(srbean);
						//reset review count to zero for new review
						reviewcount++;
						//last record and new review
							//reset list
							flist = new ArrayList<SchoolReviewFileBean>();
							optionslist = new ArrayList<SchoolReviewSectionOptionBean>();
							mmssec = new TreeMap <Integer,SchoolReviewSectionBean>();
							//create section
							//now check for sub records
							//sections
							srbean = SchoolReviewManager.createSchoolReviewBean(rs);
							if(rs.getString("SCHOOLLIST") != null) {
								srbean.setSchoolList(rs.getString("SCHOOLLIST"));
							}
							currentreviewid=srbean.getId();
							if(rs.getInt("SEC_ID") > 0) {
								secbean = SchoolReviewSectionManager.createSchoolReviewSectionBean(rs);
								currentsectionid=secbean.getSecId();
								mmssec.put(rs.getInt("SEC_SORT_ID"),secbean);
							}
							//files
							if(rs.getInt("FILE_ID") > 0 && rs.getString("SO_TYPE") == "S") {
								SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
								currentfileid = filebean.getId();
								flist.add(filebean);
							}
							//section options
							if(rs.getInt("SO_ID") > 0) {
								SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
								optionslist.add(optionbean);
							}
						
					}
				}
			}
			//now we add the last one
			if(!flist.isEmpty()) {
				secbean.setSecFiles(flist);
			}
			
			if(!optionslist.isEmpty()) {
				secbean.setSecOptions(optionslist);
			}
			if(!mmssec.isEmpty()) {
				srbean.setSectionList(mmssec);
			}
			
			mms.add(srbean);
			
			
			
		}
		catch (SQLException e) {
			System.err.println("static ArrayList<SchoolReviewBean> getSchoolReviewsList() " + e);
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
	public static SchoolReviewBean getSchoolReviewByIdFull(int rid) {
		TreeMap <Integer,SchoolReviewSectionBean> mmssec = new TreeMap <Integer,SchoolReviewSectionBean>();
		ArrayList<SchoolReviewFileBean> flist = new ArrayList<SchoolReviewFileBean>();
		ArrayList<SchoolReviewSectionOptionBean> optionslist = new ArrayList<SchoolReviewSectionOptionBean>();
		ArrayList<Integer> filesCreated = new ArrayList<Integer>();
		ArrayList<Integer> optionsCreated = new ArrayList<Integer>();
		SchoolReviewBean srbean =  null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_school_review_by_id_f(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int currentsectionid=0;
			SchoolReviewSectionBean secbean = null;
			boolean isFirst = true;
			while (rs.next()) {
					//newly created bean we get the schools
					if(isFirst) {
						srbean = SchoolReviewManager.createSchoolReviewBean(rs);
						if(rs.getString("SCHOOLLIST") != null) {
							srbean.setSchoolList(rs.getString("SCHOOLLIST"));
							isFirst=false;
						}
						isFirst=false;
						//same section
						//files
						if(rs.getInt("SEC_ID") > 0) {
							secbean = SchoolReviewSectionManager.createSchoolReviewSectionBean(rs);
							currentsectionid=secbean.getSecId();
							mmssec.put(rs.getInt("SORT_ORDER"),secbean);
							if(rs.getInt("FILE_ID") > 0) {
								SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
								flist.add(filebean);
								filesCreated.add(filebean.getId());
							}
						//section options
							if(rs.getInt("SO_ID") > 0) {
								SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
								optionslist.add(optionbean);
								optionsCreated.add(optionbean.getSectionOptionId());
							}
						}
					}else {
						//now we check to see if the section id is the same
						if(rs.getInt("SEC_ID") == currentsectionid) {
							//same section 
							if(rs.getInt("FILE_ID") > 0) {
								if(!(filesCreated.contains(rs.getInt("FILE_ID")))) {
									SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
									flist.add(filebean);
									filesCreated.add(filebean.getId());
								}
							}
						//section options
							if(rs.getInt("SO_ID") > 0) {
								if(!(optionsCreated.contains(rs.getInt("SO_ID")))) {
									SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
									optionslist.add(optionbean);
									optionsCreated.add(optionbean.getSectionOptionId());
								}
							}
						}else {
							//new section
							secbean.setSecFiles(flist);
							secbean.setSecOptions(optionslist);
							srbean.setSectionList(mmssec);
							flist = new ArrayList<SchoolReviewFileBean>();
							optionslist = new ArrayList<SchoolReviewSectionOptionBean>();
							filesCreated = new ArrayList<Integer>();
							optionsCreated = new ArrayList<Integer>();
							secbean = SchoolReviewSectionManager.createSchoolReviewSectionBean(rs);
							currentsectionid=secbean.getSecId();
							mmssec.put(rs.getInt("SORT_ORDER"),secbean);
							if(rs.getInt("FILE_ID") > 0) {
								SchoolReviewFileBean filebean = SchoolReviewFileManager.createSchoolReviewFileBean(rs);
								flist.add(filebean);
								filesCreated.add(filebean.getId());
							}
						//section options
							if(rs.getInt("SO_ID") > 0) {
								SchoolReviewSectionOptionBean optionbean = SchoolReviewSectionOptionManager.createSchoolReviewSectionOptionBean(rs);
								optionslist.add(optionbean);
								optionsCreated.add(optionbean.getSectionOptionId());
							}
						}
						
					}
				}
			//now we add the last one
			if(secbean != null) {
				secbean.setSecFiles(flist);
				secbean.setSecOptions(optionslist);
			}
			srbean.setSectionList(mmssec);
		}catch (SQLException e) {
			System.err.println("static SchoolReviewBean getSchoolReviewByIdFull(int rid) " + e);
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
		return srbean;
	}		
	public static ArrayList<SchoolReviewBean> getSchoolReviewsBySchoolYear(String syear) {
		ArrayList<SchoolReviewBean> mms = null;
		SchoolReviewBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_school_reviews_by_syear(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, syear);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static ArrayList<SchoolReviewBean> getSchoolReviewsBySchoolYear(String syear) " + e);
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
	public static SchoolReviewBean createSchoolReviewBean(ResultSet rs) {
		SchoolReviewBean abean = null;
		try {
			abean = new SchoolReviewBean();
			abean.setId(rs.getInt("ID"));
			abean.setSrName(rs.getString("SR_NAME"));
			Clob clob = rs.getClob("SR_DESCRIPTION");
			abean.setSrDescription(clob.getSubString(1, (int) clob.length()));
			abean.setSrPhoto(rs.getString("SR_PHOTO"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setSrStatus(rs.getInt("SR_STATUS"));
			abean.setSrSchoolYear(rs.getString("SR_SCHOOL_YEAR"));
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
}
