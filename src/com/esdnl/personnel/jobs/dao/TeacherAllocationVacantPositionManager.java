package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacantPositionBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;

public class TeacherAllocationVacantPositionManager {

	public static TeacherAllocationVacantPositionBean addTeacherAllocationVacantPositionBean(	TeacherAllocationVacantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_tchr_allocation_vacancy(?,?,?,?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAllocationId());
			stat.setString(3, abean.getJobDescription());
			stat.setInt(4, abean.getType().getValue());
			if (abean.getEmployee() != null)
				stat.setString(5, abean.getEmployee().getEmpId().trim());
			else
				stat.setNull(5, OracleTypes.VARCHAR);
			stat.setString(6, abean.getVacancyReason());

			if (abean.getTermStart() != null)
				stat.setDate(7, new java.sql.Date(abean.getTermStart().getTime()));
			else
				stat.setNull(7, OracleTypes.DATE);

			if (abean.getTermEnd() != null)
				stat.setDate(8, new java.sql.Date(abean.getTermEnd().getTime()));
			else
				stat.setNull(8, OracleTypes.DATE);

			stat.setDouble(9, abean.getUnit());
			stat.setBoolean(10, abean.isAdvertised());
			stat.setBoolean(11, abean.isFilled());

			stat.execute();

			int id = stat.getInt(1);

			abean.setPositionId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("TeacherAllocationVacantPositionBean addTeacherAllocationVacantPositionBean(TeacherAllocationVacantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add TeacherAllocationVacantPositionBean to DB.", e);
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

	public static void updateTeacherAllocationVacantPositionBean(TeacherAllocationVacantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_vacancy(?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getPositionId());
			stat.setString(2, abean.getJobDescription());
			stat.setInt(3, abean.getType().getValue());
			if (abean.getEmployee() != null)
				stat.setString(4, abean.getEmployee().getEmpId().trim());
			else
				stat.setNull(4, OracleTypes.VARCHAR);
			stat.setString(5, abean.getVacancyReason());

			if (abean.getTermStart() != null)
				stat.setDate(6, new java.sql.Date(abean.getTermStart().getTime()));
			else
				stat.setNull(6, OracleTypes.DATE);

			if (abean.getTermEnd() != null)
				stat.setDate(7, new java.sql.Date(abean.getTermEnd().getTime()));
			else
				stat.setNull(7, OracleTypes.DATE);

			stat.setDouble(8, abean.getUnit());
			stat.setBoolean(9, abean.isAdvertised());
			stat.setBoolean(10, abean.isFilled());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationVacantPositionBean(TeacherAllocationVacantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not update TeacherAllocationVacantPositionBean in DB.", e);
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

	public static void deleteTeacherAllocationVacantPositionBean(TeacherAllocationVacantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_tchr_allocation_vacancy(?); end;");

			stat.setInt(1, abean.getPositionId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTeacherAllocationVacantPositionBean(TeacherAllocationVacantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not delete TeacherAllocationVacantPositionBean to DB.", e);
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
	public static void updateTeacherAllocationVacantPositionAdRequest(int rid, int pid)
		throws JobOpportunityException {

			Connection con = null;
				CallableStatement stat = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
			
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_vacant_position(?,?); end;");
			
				stat.setInt(1, rid);
				stat.setInt(2, pid);
			
				stat.execute();
			}
			catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
			
				System.err.println("static void updateTeacherAllocationVacantPositionAdRequest(int rid, int pid): "
						+ e);
				throw new JobOpportunityException("Can not update TeacherAllocationVacantPositionBean to DB.", e);
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
	public static TeacherAllocationVacantPositionBean getTeacherAllocationVacantPositionBean(int id)
			throws JobOpportunityException {

		TeacherAllocationVacantPositionBean position = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_vacancy(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				position = createTeacherAllocationVacantPositionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationVacantPositionBean getTeacherAllocationVacantPositionBean(int id): " + e);
			throw new JobOpportunityException("Can not extract TeacherAllocationVacantPositionBean from DB.", e);
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

		return position;
	}

	public static Collection<TeacherAllocationVacantPositionBean> getTeacherAllocationVacantPositionBeans(TeacherAllocationBean allocation)
			throws JobOpportunityException {

		Collection<TeacherAllocationVacantPositionBean> vacancies = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			vacancies = new ArrayList<TeacherAllocationVacantPositionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_vacancies(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, allocation.getAllocationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				vacancies.add(createTeacherAllocationVacantPositionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<TeacherAllocationVacantPositionBean> getTeacherAllocationVacantPositionBeans(TeacherAllocationBean allocation): "
					+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationVacantPositionBean from DB.", e);
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

		return vacancies;
	}
	public static void updateTeacherAllocationVacantPositionAdvertised(int rid)
			throws JobOpportunityException {

				Connection con = null;
					CallableStatement stat = null;

				try {
					con = DAOUtils.getConnection();
					con.setAutoCommit(true);
				
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_vacancy_advertised(?); end;");
				
					stat.setInt(1, rid);
				
					stat.execute();
				}
				catch (SQLException e) {
					e.printStackTrace();
					try {
						con.rollback();
					}
					catch (Exception ex) {}
				
					System.err.println("static void updateTeacherAllocationVacantPositionAdvertised(int rid)): "
							+ e);
					throw new JobOpportunityException("Can not update TeacherAllocationVacantPositionBean to DB.", e);
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
	public static void updateTeacherAllocationVacantPositionFilled(int rid)
			throws JobOpportunityException {

				Connection con = null;
					CallableStatement stat = null;

				try {
					con = DAOUtils.getConnection();
					con.setAutoCommit(true);
				
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_vacancy_filled(?); end;");
				
					stat.setInt(1, rid);
				
					stat.execute();
				}
				catch (SQLException e) {
					e.printStackTrace();
					try {
						con.rollback();
					}
					catch (Exception ex) {}
				
					System.err.println("updateTeacherAllocationVacantPositionFilled(int rid): "
							+ e);
					throw new JobOpportunityException("Can not update TeacherAllocationVacantPositionBean to DB.", e);
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
	public static TeacherAllocationVacantPositionBean createTeacherAllocationVacantPositionBean(ResultSet rs) {

		TeacherAllocationVacantPositionBean abean = null;

		try {
			abean = new TeacherAllocationVacantPositionBean();

			abean.setAllocationId(rs.getInt("ALLOCATION_ID"));
			abean.setPositionId(rs.getInt("POSITION_ID"));
			abean.setJobDescription(rs.getString("JOB_DESC"));
			abean.setType(EmploymentConstant.get(rs.getInt("POSITION_TYPE")));
			try {
				if (!StringUtils.isEmpty(rs.getString("OWNER_EMP_ID")))
					abean.setEmployee(EmployeeManager.getEmployeeBean(rs.getString("OWNER_EMP_ID").trim()));
				else
					abean.setEmployee(null);
			}
			catch (EmployeeException e) {
				abean.setEmployee(null);
			}
			abean.setVacancyReason(rs.getString("VACANCY_REASON"));

			if (rs.getDate("TERM_START_DATE") != null)
				abean.setTermStart(new Date(rs.getDate("TERM_START_DATE").getTime()));
			else
				abean.setTermStart(null);

			if (rs.getDate("TERM_END_DATE") != null)
				abean.setTermEnd(new Date(rs.getDate("TERM_END_DATE").getTime()));
			else
				abean.setTermEnd(null);

			abean.setUnit(rs.getDouble("UNIT"));

			//get job ad associated with vacancy
			if(rs.getInt("AD_REQUEST_ID") > 0){
				abean.setAdRequest(AdRequestManager.getAdRequestBean(rs.getInt("AD_REQUEST_ID")));
				if(!(abean.getAdRequest() == null)){
					if(abean.getAdRequest().getCurrentStatus() == RequestStatus.POSTED){
						abean.setAdvertised(true);
						//now we check to see if it is filled
						JobOpportunityBean jb = JobOpportunityManager.getJobOpportunityBean(abean.getAdRequest().getCompetitionNumber());
						if(!(jb == null)){
							if(jb.isAwarded()){
								abean.setFilled(true);
							}else{
								abean.setFilled(false);
							}
						}
					}else{
						abean.setAdvertised(rs.getBoolean("ADVERTISED"));
						abean.setFilled(rs.getBoolean("FILLED"));
					}
				}
			}else{
				abean.setAdRequest(null);
				abean.setAdvertised(rs.getBoolean("ADVERTISED"));
				abean.setFilled(rs.getBoolean("FILLED"));
			}
		}
		catch (SQLException e) {
			abean = null;
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return abean;
	}

}
