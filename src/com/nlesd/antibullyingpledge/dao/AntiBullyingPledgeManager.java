package com.nlesd.antibullyingpledge.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeBean;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeGradeLevelReport;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeSchoolListBean;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeSchoolTotalsBean;
public class AntiBullyingPledgeManager {
		public static List<AntiBullyingPledgeSchoolListBean> getSchoolListings() throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeSchoolListBean> listbeans =  new ArrayList<AntiBullyingPledgeSchoolListBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_school_info; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				AntiBullyingPledgeSchoolListBean bpslb = new AntiBullyingPledgeSchoolListBean();
				bpslb.setSchoolId(rs.getInt("SCHOOL_ID"));
				System.out.println(rs.getString("SCHOOL_NAME"));
				
				bpslb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpslb.setSchoolCrest(rs.getString("SCHOOL_CREST_FILENAME"));
				bpslb.setSchoolPhoto(rs.getString("SCHOOL_PHOTO_FILENAME"));
				listbeans.add(bpslb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getSchoolListings() "
					+ e);
			throw new JobOpportunityException("Can not get School Listings from DB.", e);
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
		return listbeans;
	}
		public static AntiBullyingPledgeBean addBullyingPledgeBean(AntiBullyingPledgeBean bpbbean)
			throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bullying_pledge_pkg.add_new_pledge(?,?,?,?,?,?,?,?); end;");
			stat.setString(1, bpbbean.getFirstName());
			stat.setString(2, bpbbean.getLastName());
			stat.setInt(3, bpbbean.getFkSchool());
			stat.setInt(4, bpbbean.getGradeLevel());
			stat.setDate(5, new java.sql.Date(bpbbean.getDateSubmittedUser().getTime()));
			stat.setString(6, bpbbean.getCancellation_Code());
			stat.setString(7, bpbbean.getSchoolImage());
			stat.setString(8, bpbbean.getEmail());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager addBullyingPledgeBean(BullyingPledgeBean bpbbean): "
					+ e);
			throw new JobOpportunityException("Can not add BullyingPledgeBean to DB.", e);
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
		return bpbbean;
	}
	
	public static boolean checkPledgeEmail(String email)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean isvalid=true;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.check_pledge_email(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email.toUpperCase());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				isvalid=false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager addBullyingPledgeBean(BullyingPledgeBean bpbbean): "
					+ e);
			throw new JobOpportunityException("Can not add BullyingPledgeBean to DB.", e);
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
		return isvalid;
	}
	
	public static void sendConfirmationEmail(AntiBullyingPledgeBean bpb)
	{
		EmailBean email = new EmailBean();
		try{
		email.setTo(bpb.getEmail());
		email.setTo(new String[] {
				bpb.getEmail()
				});
		email.setFrom("donotreply@nlesd.ca");
		email.setSubject("Bullying Pledge Confirmation Email");
		StringBuffer sb= new StringBuffer();
		sb.append("You have received this email because a NLESD Bullying Pledge was submitted using this email address.<br><br>");
		sb.append("To confirm your submission, please ");
		sb.append("<a href='https://www.nlesd.ca/MemberServices/AntiBullyingPledge/addantibullyingpledge.jsp?rid=" + bpb.getCancellation_Code() + "&ptype=confirmed'>CLICK HERE</a>");
		sb.append("<br><br>");
		sb.append("If you did not submit this pledge or wish to remove it, then please ");
		sb.append("<a href='https://www.nlesd.ca/MemberServices/AntiBullyingPledge/addantibullyingpledge.jsp?rid=" + bpb.getCancellation_Code() + "&ptype=removed'>CLICK HERE</a>");
		email.setBody(sb.toString());
		email.send();
		}catch(Exception e)
		{
			System.err.println("BullyingPledgeManager addBullyingPledgeBean(BullyingPledgeBean bpbbean): " + e);
			
		}
	}
	
	public static boolean cancelPledge(String rid) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean isvalid=true;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.delete_pledge(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				isvalid=false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager cancelPledge(varchar rid): "
					+ e);
			throw new JobOpportunityException("Can not delete Pledge from DB.", e);
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
		return isvalid;
	}
	public static boolean confirmPledge(String rid) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean isvalid=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.confirm_pledge(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				isvalid=true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager confirmPledge(varchar rid): "
					+ e);
			throw new JobOpportunityException("Can not update Pledge from DB.", e);
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
		return isvalid;
	}
	
	public static List<AntiBullyingPledgeBean> getTodaysPledges() throws JobOpportunityException{
		
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeBean> listpledges =  new ArrayList<AntiBullyingPledgeBean>();
		
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_todays_pledges; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
				bpb.setFirstName(rs.getString("FIRST_NAME"));
				bpb.setLastName(rs.getString("LAST_NAME"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setGradeLevel(rs.getInt("GRADE_LEVEL"));
				bpb.setEmail(rs.getString("EMAIL"));
				bpb.setPk(rs.getInt("PK"));
				listpledges.add(bpb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTodaysPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listpledges;
		
	}
	public static boolean deletePledge(int id) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean isvalid=true;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.delete_pledge_admin(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				isvalid=false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager deletePledge(int id): "
					+ e);
			throw new JobOpportunityException("Can not delete Pledge from DB.", e);
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
		return isvalid;
	}
	public static List<AntiBullyingPledgeBean> getRandomPledges(int tnumber) throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeBean> listpledges =  new ArrayList<AntiBullyingPledgeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_random_pledges(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tnumber);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				//map.put(rs.getString("SCHOOL_ID"), rs.getString("SCHOOL_NAME"));
				AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
				bpb.setFirstName(rs.getString("FIRST_NAME"));
				bpb.setLastName(rs.getString("LAST_NAME"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setGradeLevel(rs.getInt("GRADE_LEVEL"));
				bpb.setEmail(rs.getString("EMAIL"));
				bpb.setPk(rs.getInt("PK"));
				bpb.setFkSchool(rs.getInt("FK_SCHOOL"));
				listpledges.add(bpb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTodaysPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listpledges;
		
	}
	
	public static List<AntiBullyingPledgeBean> searchPledges(SearchBy searchby, String searchfor,String searchforschool,String searchdate,String searchforc,String searchforgrade) throws JobOpportunityException, ParseException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeBean> listpledges =  new ArrayList<AntiBullyingPledgeBean>();
		try {
			con = DAOUtils.getConnection();
			switch(searchby)
			{
				case CANCELLATION_CODE:
				{
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_c_code(?); end;");
					stat.setString(2,searchfor.toUpperCase());
					break;
				}
				case DATE_SUBMITTED:
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
					Date parsedDate = sdf.parse(searchdate);
					sdf.applyPattern("yyyy-mm-dd");
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_d_sub(?); end;");
					stat.setString(2,sdf.format(parsedDate));
					break;
				}
				case EMAIL:
				{
					//search by email
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_email(?); end;");
					stat.setString(2,searchfor.toUpperCase());
					break;
				}
				case FIRST_NAME:
				{
					//search by first name
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_first_name(?); end;");
					stat.setString(2,searchfor.toUpperCase());
					break;
				}
				case GRADE:
				{
					//search by grade level
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_grade(?); end;");
					stat.setInt(2,Integer.parseInt(searchforgrade));
					break;
				}
				case LAST_NAME:
				{
					//search by last name
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_last_name(?); end;");
					stat.setString(2,searchfor.toUpperCase());
					break;
				}
				case PLEDGE_CONFIRMED:
				{
					//search by confirmed or not confirmed
					if(searchforc.equals("Yes")){
						stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_conf; end;");
					}else{
						stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_nconf; end;");
					}
					break;
				}
				default:
				{	
					//search by school
					stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.search_pledge_by_school(?); end;");
					stat.setInt(2,Integer.parseInt(searchforschool));
					break;
				}
			}
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
				bpb.setFirstName(rs.getString("FIRST_NAME"));
				bpb.setLastName(rs.getString("LAST_NAME"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setGradeLevel(rs.getInt("GRADE_LEVEL"));
				bpb.setEmail(rs.getString("EMAIL"));
				bpb.setPk(rs.getInt("PK"));
				bpb.setDateSubmittedUser(new java.util.Date(rs.getTimestamp("DATE_SUBMITTED_USER").getTime()));
				listpledges.add(bpb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: searchPledges() "
					+ e);
			throw new JobOpportunityException("Can not search Pledges from DB.", e);
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
		return listpledges;
		
	}
	
	public static List<AntiBullyingPledgeSchoolTotalsBean> getRandomSchoolTotals(int tnumber) throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeSchoolTotalsBean> listpledges =  new ArrayList<AntiBullyingPledgeSchoolTotalsBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_random_pledges_totals(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tnumber);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				AntiBullyingPledgeSchoolTotalsBean bpb = new AntiBullyingPledgeSchoolTotalsBean();
				bpb.setSchoolId(rs.getInt("SCHOOL_ID"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setTotalPledges(rs.getInt("TOTALPLEDGES"));
				bpb.setSchoolPicture(Integer.toString(rs.getInt("SCHOOL_ID")));
				listpledges.add(bpb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTodaysPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listpledges;
	}
	
	public static List<AntiBullyingPledgeSchoolTotalsBean> getAllSchoolTotals() throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeSchoolTotalsBean> listpledges =  new ArrayList<AntiBullyingPledgeSchoolTotalsBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_all_pledges_totals; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				AntiBullyingPledgeSchoolTotalsBean bpb = new AntiBullyingPledgeSchoolTotalsBean();
				bpb.setSchoolId(rs.getInt("SCHOOL_ID"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setTotalPledges(rs.getInt("TOTALPLEDGES"));
				bpb.setSchoolPicture(Integer.toString(rs.getInt("SCHOOL_ID")));
				bpb.setTotalPledgesConfirmed(rs.getInt("TOTALCONFIRMED"));
				listpledges.add(bpb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTodaysPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listpledges;
	}
	public static List<AntiBullyingPledgeGradeLevelReport> getGradeLevelReport() throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		List<AntiBullyingPledgeGradeLevelReport> list = new ArrayList<AntiBullyingPledgeGradeLevelReport>();
		ResultSet rs = null;
		
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_pledge_totals_by_grade; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
					AntiBullyingPledgeGradeLevelReport report = new AntiBullyingPledgeGradeLevelReport();
					report.setGradeId(rs.getInt("grade_id"));
					report.setGradeName(rs.getString("grade_name"));
					report.setNotConfirmedCount(rs.getInt("notconfirmedcount"));
					report.setConfirmedCount(rs.getInt("confirmedcount"));
					report.setTotalCount(rs.getInt("totalcount"));
					list.add(report);
					
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getGradeLevelReport() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
	public static Integer getTotalPledges() throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		Integer totalPledges=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_pledge_totals_all; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
					totalPledges=rs.getInt("tcount");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTotalPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return totalPledges;
	}
	
	public static Integer getTotalPledgesConfirmed() throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		Integer totalPledges=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_pledge_totals_confirmed; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
					totalPledges=rs.getInt("tcount");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTotalPledgesConfirmed() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return totalPledges;
	}
	public static List<AntiBullyingPledgeBean> getAllPledges(int sortby) throws JobOpportunityException{
		String sortcolumnstring="";
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		List<AntiBullyingPledgeBean> listpledges =  new ArrayList<AntiBullyingPledgeBean>();
		SortBy sortbyobject = SortBy.getValue(sortby);
		
		switch(sortbyobject)
		{
		case LASTNAME:
			sortcolumnstring="lower(LAST_NAME)";
			break;
		
		case FIRSTNAME:
			sortcolumnstring="lower(FIRST_NAME)";
			break;
		
		case GRADE:
			sortcolumnstring="GRADE_LEVEL";
			break;
		
		case SCHOOL:
				sortcolumnstring="lower(SCHOOL_NAME)";
				break;
		case EMAIL:
			sortcolumnstring="lower(EMAIL)";
			break;				
		default:
			sortcolumnstring="DATE_SUBMITTED_USER";
			break;
		}
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_all_pledges(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,sortcolumnstring);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				//map.put(rs.getString("SCHOOL_ID"), rs.getString("SCHOOL_NAME"));
				AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
				bpb.setFirstName(rs.getString("FIRST_NAME"));
				bpb.setLastName(rs.getString("LAST_NAME"));
				bpb.setSchoolName(rs.getString("SCHOOL_NAME"));
				bpb.setGradeLevel(rs.getInt("GRADE_LEVEL"));
				bpb.setEmail(rs.getString("EMAIL"));
				bpb.setPk(rs.getInt("PK"));
				bpb.setDateSubmittedUser(new java.util.Date(rs.getTimestamp("DATE_SUBMITTED_USER").getTime()));
				bpb.setConfirmed(rs.getString("CONFIRMED"));
				if(rs.wasNull())
				{
					bpb.setConfirmed("N");
				}
				listpledges.add(bpb);
				System.out.println(bpb.getLastName() + "--" + bpb.getGradeLevel() );
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTodaysPledges() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listpledges;
	}
	public static TreeMap<Date,Integer> getPledgesByDateRange(int numberofdays) throws JobOpportunityException{
		Connection con = null;
		CallableStatement stat=null;
		ResultSet rs = null;
		TreeMap<Date,Integer> listbeans= new TreeMap<Date,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bullying_pledge_pkg.get_pledge_totals_time_range(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,numberofdays);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next())
			{
				listbeans.put(rs.getDate("reportdate"), rs.getInt("totalpledges"));
				
					
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BullyingPledgeManager: getTotalPledgesConfirmed() "
					+ e);
			throw new JobOpportunityException("Can not get Todays Pledges from DB.", e);
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
		return listbeans;
	}

	public enum SortBy {
		 LASTNAME(1), FIRSTNAME(2), GRADE(3), SCHOOL(4), EMAIL(5),DATESUBMITTED(6);
		 
		 public final int value;
		 
		 SortBy(final int value) {
		     this.value = value;
		  }
		 public int getId() {
		   return value;
		 }
		 static SortBy getValue(int value) {
			  for(SortBy e: SortBy.values()) {
			    if(e.value == value) {
			      return e;
			    }
			  }
			  return null;// not found
			}
	}
	
	public enum SearchBy {
		 PLEASE_SELECT(-1,"Please Select"), CANCELLATION_CODE(1,"Cancellation Code like"), DATE_SUBMITTED(2,"Date Submitted equal to"), 
		 EMAIL(3,"EMAIL like"), FIRST_NAME(4,"First Name like"),GRADE(5,"Grade equal to"),LAST_NAME(6,"Last Name like"),PLEDGE_CONFIRMED(7,"Pledge Confirmed"),
		 SCHOOL(8,"School equal to");
		 
		 public final int value;
		 public final String description;
		 
		 SearchBy(final int value,final String description) {
		     this.value = value;
		     this.description=description;
		  	}
		 public int getId() {
		   return value;
		 }
		 public String getDescription()
		 {
			 return description;
		 }
		 public static SearchBy getValue(int value) {
			  for(SearchBy e: SearchBy.values()) {
			    if(e.value == value) {
			      return e;
			    }
			  }
			  return null;// not found
			}
	}
	public enum ReportSelection{
		 PLEASE_SELECT(-1,"Please Select"), TOTAL_PLEDGES(1,"Total Pledges/Confirmed Pledges"), PLEDGES_BY_GRADE(2,"Total Pledges By Grade"), 
		 PLEDGES_BY_SCHOOL (3,"Total Pledges By School"),PLEDGES_BY_DATE (4,"Total Pledges By Date Range");
		 
		 public final int value;
		 public final String description;
		 
		 ReportSelection(final int value,final String description) {
		     this.value = value;
		     this.description=description;
		  }
		 public int getId() {
		   return value;
		 }
		 public String getDescription()
		 {
			 return description;
		 }
		 public static ReportSelection getValue(int value) {
			  for(ReportSelection e: ReportSelection.values()) {
			    if(e.value == value) {
			      return e;
			    }
			  }
			  return null;// not found
			}
	}

}
