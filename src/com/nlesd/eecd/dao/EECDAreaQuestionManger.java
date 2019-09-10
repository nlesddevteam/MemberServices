package com.nlesd.eecd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDAreaQuestionBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EECDAreaQuestionManger {
	public static TreeMap<Integer,EECDAreaQuestionBean> getAreaQuestions(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,EECDAreaQuestionBean> list = new TreeMap<Integer,EECDAreaQuestionBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_area_questions(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaQuestionBean abean = new EECDAreaQuestionBean();
				abean = createEECDAreaQuestionBean(rs);
				list.put(abean.getQuestionSort(),abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,EECDAreaQuestionBean> getAreaQuestions(int pid): "
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
	public static int addTeacherAreaQuestion(EECDAreaQuestionBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.add_area_question(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getAreaId());
			stat.setInt(3, vbean.getId());
			stat.setString(4, vbean.getTeacherAnswer());
			stat.setInt(5, vbean.getPersonnelId());
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println(" int addTeacherAreaQuestion(EECDAreaQuestionBean vbean:) "
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
	public static TreeMap<Integer,EECDAreaQuestionBean> getAreaQuestionsAnswers(int aid,int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,EECDAreaQuestionBean> list = new TreeMap<Integer,EECDAreaQuestionBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_area_questions_ans(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aid);
			stat.setInt(3, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDAreaQuestionBean abean = new EECDAreaQuestionBean();
				abean = createEECDAreaQuestionBean(rs);
				list.put(abean.getQuestionSort(),abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,EECDAreaQuestionBean> getAreaQuestions(int pid): "
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
	public static void updateTeacherAreaQuestion(EECDAreaQuestionBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.eecd_pkg.update_area_question(?,?); end;");
			stat.setString(1, vbean.getTeacherAnswer());
			stat.setInt(2, vbean.getTeacherAnswerId());
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println(" void updateTeacherAreaQuestion(EECDAreaQuestionBean vbean) "
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
	public static EECDAreaQuestionBean createEECDAreaQuestionBean(ResultSet rs) {
		EECDAreaQuestionBean abean = null;
		try {
				abean = new EECDAreaQuestionBean();
				abean.setId(rs.getInt("ID"));
				abean.setQuestionText(rs.getString("Question_Text"));
				abean.setQuestionSort(rs.getInt("QUESTION_SORT"));
				abean.setIsActive(rs.getInt("IS_ACTIVE"));
				abean.setAreaId(rs.getInt("AREA_ID"));
				abean.setAreaDescription(rs.getString("AREA_DESCRIPTION"));
				abean.setEligibleTeachers(rs.getString("ELIGIBLE_TEACHERS"));
				if(rs.getString("ANSWER") == null) {
					abean.setTeacherAnswer("");
				}else {
					abean.setTeacherAnswer(rs.getString("ANSWER"));
				}
				
				abean.setTeacherAnswerId(rs.getInt("TAID"));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
