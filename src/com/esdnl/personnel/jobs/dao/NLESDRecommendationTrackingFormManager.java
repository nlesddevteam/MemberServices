package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;

import com.awsd.mail.bean.AlertBean;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationBean;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRecommendationTrackingFormBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceAdminBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSManageBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSSupportBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceTeacherBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.nlesd.school.bean.SchoolZoneBean;

public class NLESDRecommendationTrackingFormManager {

	public static ArrayList<NLESDRecommendationTrackingFormBean> getNLESDTrackingFormList(String[][] names,
																																												TeacherRecommendationBean rec) {

		ArrayList<NLESDRecommendationTrackingFormBean> listbeans = null;
		ArrayList<Integer> interviewCols = new ArrayList<Integer>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		InterviewSummaryBean isb = null;
		Integer totalICols=0;
		StringBuilder sHeader = new StringBuilder();
		int imaxcols=0;

		boolean isCompetitionSpecific = false;

		JobOpportunityBean job = null;

		try {
			job = rec.getJob();
		}
		catch (JobOpportunityException e) {
			job = null;

			try {
				new AlertBean(e).send();
			}
			catch (Exception ex) {}
		}

		boolean isAdministrative = ((job != null) ? job.getJobType().equal(JobTypeConstant.ADMINISTRATIVE) : false);
		boolean isLeadership = ((job != null) ? job.getJobType().equal(JobTypeConstant.LEADERSHIP) : false);

		SchoolZoneBean zone = null;

		try {
			zone = ((job != null) ? job.get(0).getLocationZone() : null);
		}
		catch (Exception e) {
			zone = null;
		}

		int adjustcount = 0;

		try {
			listbeans = new ArrayList<NLESDRecommendationTrackingFormBean>(5);
			int count = 0;
			for (int x = 0; x < names.length; x++) {
				String s = "-1";
				if(!(names[x][0] == null)){
					s=names[x][0];
				}
				StringBuilder sRow = new StringBuilder();

				isCompetitionSpecific = false;

				if (!s.equals(null) && !s.equals("-1") && !s.equals("None Selected")) {
					//first we the interview summary
					NLESDRecommendationTrackingFormBean eBean = new NLESDRecommendationTrackingFormBean();
					//try to find the interview for this comp
					con = DAOUtils.getConnection();
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rec_tra_inter_summary(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setString(2, s);
					stat.setString(3, rec.getCompetitionNumber());
					stat.execute();
					rs = ((OracleCallableStatement) stat).getCursor(1);

					//if not there then try to find last interview
					if (!rs.isBeforeFirst()) {
						stat.close();

						stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_summaries_by_app(?); end;");
						stat.registerOutParameter(1, OracleTypes.CURSOR);
						stat.setString(2, s);
						stat.execute();
						rs = ((OracleCallableStatement) stat).getCursor(1);

						isCompetitionSpecific = false;
					}
					else {
						isCompetitionSpecific = true;
					}

					double totalscore = 0;
					int totalsummaries = 0;
					double currentscore = 0;

					String competitionNumber = "";

					SchoolZoneBean isbZone = null;

					while (rs.next()) {

						if (!isCompetitionSpecific) {
							isb = InterviewSummaryManager.createInterviewSummaryBean(rs);

							if ((isAdministrative && !isb.isAdministrative()) || (isLeadership && !isb.isLeadership()))
								continue;

							try {
								isbZone = isb.getCompetition().get(0).getLocationZone();
							}
							catch (Exception e) {
								isbZone = null;
							}

							//only use interview summary for the specific zone if not job specific
							if (zone != null && isbZone != null && !zone.equals(isbZone))
								continue;

							if (StringUtils.isBlank(competitionNumber)) {
								competitionNumber = isb.getCompetition().getCompetitionNumber();
							}
							else if (StringUtils.isNotBlank(competitionNumber)
									&& !competitionNumber.equals(isb.getCompetition().getCompetitionNumber())) {
								// ONLY ONE COMPETITION INTERVIEW SUMMARY ALLOWED!!!
								break;
							}
						}

						//calculate the total score
						if (isCompetitionSpecific) {
							currentscore = rs.getDouble("TOTALSCORE");
						}
						else {
							double tscore = rs.getDouble("score_1") + rs.getDouble("score_2") + rs.getDouble("score_3")
									+ rs.getDouble("score_4") + rs.getDouble("score_5") + rs.getDouble("score_6")
									+ rs.getDouble("score_7") + rs.getDouble("score_8") + rs.getDouble("score_9")
									+ rs.getDouble("score_10");

							currentscore = tscore;
						}
						//set the interview top and bottom and calculate the total
						eBean.setScaleBottom(rs.getInt("scalebottom"));
						eBean.setScaleTop(rs.getInt("scaletop"));
						eBean.setScaleTotal(rs.getInt("totalquestions") * rs.getInt("scaletop"));

						double avgscore = 0;
						avgscore = currentscore / rs.getInt("totalquestions");
						totalscore += avgscore;
						DecimalFormat dfas = new DecimalFormat("#.00");

						sRow.append("<td class='displayText2'>"
								+ dfas.format(avgscore) + "</td>");
						totalsummaries += 1;
						
						if (x == 0) {
							//build the header text
							sHeader.append("<th class='captionTitle3'>"
									+ totalsummaries + "</th>");
							adjustcount += 1;
							totalICols=adjustcount;
							
						}

					}
					//check to see if # of interview match the recommended candidate, if not append blank fields
					sRow.append(checkInterviewString(sRow.toString(), adjustcount));
					if (totalscore > 0 && totalsummaries > 0) {
						double avgscore = totalscore / totalsummaries;
						DecimalFormat df = new DecimalFormat("#.00");
						if (x == 0) {
							sHeader.append("<th class='captionTitle3'>Total(/"
									+ eBean.getScaleTop() + ")</th>");

						}
						sRow.append("INSERTHERE<td class='displayText2'>" + df.format(avgscore)
								+ "</td>");
						eBean.setInterviewScoreTotal(df.format(avgscore));

					}
					else {
						if (x == 0) {
							sHeader.append("<th class='captionTitle3'>Total(/25)</th>");
						}
						sRow.append("<td class='displayText2'>0</td>");
						eBean.setInterviewScoreTotal("0");
					}
					eBean.setTotalInterviews(totalsummaries + 1);
					eBean.setInterviewHeader(sHeader.toString());
					eBean.setInterviewRow(sRow.toString());
					//add columns to array for making table even
					interviewCols.add(totalsummaries);
					if(totalsummaries > imaxcols){
						imaxcols=totalsummaries;
					}
					//set the comments for the comment secton
					eBean.setComments(names[x][1]);
					// Next we get the candidate name
					ApplicantProfileBean ap = ApplicantProfileManager.getApplicantProfileBean(s);
					eBean.setCandidateName(ap.getFullName());
					if(job.getIsSupport().equals("N")){
						
					
					// Now we get permanent Experience
					ApplicantEsdExperienceBean exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(s);
					if(exp != null){
						if (exp.getPermanentLTime() == 0) {
							eBean.setExperience("Permanent: " + " 0 Years");
						}
						else {
							double eyears = exp.getPermanentLTime() / 10;
							DecimalFormat df = new DecimalFormat("#.00");
							eBean.setExperience("Permanent: " + df.format(eyears) + " Years");
	
						}
					}
					eBean.getExperience();
					//now we populate their education
					ApplicantEducationBean[] edu = ApplicantEducationManager.getApplicantEducationBeans(s);
					StringBuilder sb = new StringBuilder();
					for (ApplicantEducationBean aeb : edu) {
						if (aeb.getDegreeConferred() != null) {
							sb.append(aeb.getDegreeConferred() + ", ");
						}
					}
					eBean.setQualifications(sb.toString());
					}
					boolean reffound = false;
					//last we get the reference info
					if (count == 0)//recommendee
					{
						if(job.getIsSupport().equals("Y")){
							boolean supportreffound=false;
							//check support first
							NLESDReferenceSSSupportBean ad = NLESDReferenceSSSupportManager.getNLESDReferenceSSSupportBean(rec.getReferenceId());
							if (ad != null) {
								eBean.setReferenceScale(ad.getReferenceScale());
								//new reference 16 questions zeros not included
								DecimalFormat df = new DecimalFormat("0.00");

								double avgScore = 0;
								if( ad.getPossibleTotal() > 0){
									avgScore = (double) ad.getTotalScore() / ad.getPossibleTotal();
								}

								eBean.setReferenceScore(df.format(avgScore));
								eBean.setReferenceRecommendation(ad.getQ10());
								supportreffound=true;
							}
							
							//check management second
							
							NLESDReferenceSSManageBean adm = NLESDReferenceSSManageManager.getNLESDReferenceSSManageBean(rec.getReferenceId());
							if (adm != null) {
								eBean.setReferenceScale(adm.getReferenceScale());
								//new reference 16 questions zeros not included
								DecimalFormat df = new DecimalFormat("0.00");

								double avgScore = 0;
								if( adm.getPossibleTotal() > 0){
									avgScore = (double) adm.getTotalScore() / adm.getPossibleTotal();
								}

								eBean.setReferenceScore(df.format(avgScore));
								eBean.setReferenceRecommendation(adm.getQ10());
								supportreffound=true;
							}
							
							if(!(supportreffound)){
								eBean.setReferenceScore("None on File");
							}
						}else{
							if(isAdministrative || isLeadership){
								NLESDReferenceAdminBean ad = NLESDReferenceAdminManager.getNLESDReferenceAdminBean(rec.getReferenceId());
								if (ad != null) {
									eBean.setReferenceScale(ad.getReferenceScale());
									//new reference 16 questions zeros not included
									DecimalFormat df = new DecimalFormat("0.00");

									double avgScore = 0;
									if( ad.getPossibleTotal() > 0){
										avgScore = (double) ad.getTotalScore() / ad.getPossibleTotal();
									}

									eBean.setReferenceScore(df.format(avgScore));
									eBean.setReferenceRecommendation(ad.getQ6());
								}
							}else{
								//no good way to find guidance
								boolean isguide=false;
								String testing = job.getPositionTitle().toUpperCase();
								if(testing.indexOf("GUID") >= 0){
									isguide = true;
								}
								if(isguide){
									NLESDReferenceGuideBean gb = new NLESDReferenceGuideBean();
									gb=NLESDReferenceGuideManager.getNLESDReferenceGuideBean(rec.getReferenceId());
									
									if(!(gb == null)){
										eBean.setReferenceScale(gb.getReferenceScale());
										//new reference 16 questions zeros not included
										DecimalFormat df = new DecimalFormat("0.00");

										double avgScore = 0;
										if( gb.getPossibleTotal() > 0){
											avgScore = (double) gb.getTotalScore() / gb.getPossibleTotal();
										}


										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(gb.getQ6());
										reffound=true;
									}else{
										//now we check the teaching references since it could be both
										NLESDReferenceTeacherBean tb = new NLESDReferenceTeacherBean();
										tb=NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(rec.getReferenceId());
										if(!(tb == null)){
											eBean.setReferenceScale(tb.getReferenceScale());
											//new reference 16 questions zeros not included
											DecimalFormat df = new DecimalFormat("0.00");

											double avgScore = 0;
											if( tb.getPossibleTotal() > 0){
												avgScore = (double) tb.getTotalScore() / tb.getPossibleTotal();
											}

											eBean.setReferenceScore(df.format(avgScore));
											eBean.setReferenceRecommendation(tb.getQ6());
											reffound =true;
										}
										
										
									}
									//now we check to see if one is found
									//if not then we check external references
									//first by id then by sin
									//if not then add none found entry
									if(!(reffound)){
										NLESDReferenceExternalBean xbean = new NLESDReferenceExternalBean();
										if(rec.getReferenceId() > 0){
											xbean = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(rec.getReferenceId());
										}else{
											xbean = NLESDReferenceExternalManager.getLatestNLESDReferenceExternalBean(s);
										}
										if(xbean == null){
											eBean.setReferenceScore("None on File");
										}else{
											eBean.setReferenceScale(xbean.getReferenceScale());
											//new reference 16 questions zeros not included
											DecimalFormat df = new DecimalFormat("0.00");

											double avgScore = 0;
											if( xbean.getPossibleTotal() > 0){
												avgScore = (double) xbean.getTotalScore() / xbean.getPossibleTotal();
											}

											eBean.setReferenceScore(df.format(avgScore));
											eBean.setReferenceRecommendation(xbean.getQ7());
											reffound =true;
										}

										
									}


									
								}else{
									reffound = false;
									NLESDReferenceTeacherBean tb = new NLESDReferenceTeacherBean();
									tb=NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(rec.getReferenceId());
									if(!(tb == null)){
										eBean.setReferenceScale(tb.getReferenceScale());
										//new reference 16 questions zeros not included
										DecimalFormat df = new DecimalFormat("0.00");

										double avgScore = 0;
										if( tb.getPossibleTotal() > 0){
											avgScore = (double) tb.getTotalScore() / tb.getPossibleTotal();
										}


										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(tb.getQ6());
										reffound =true;
									}else{
										NLESDReferenceGuideBean gb = new NLESDReferenceGuideBean();
										gb=NLESDReferenceGuideManager.getNLESDReferenceGuideBean(rec.getReferenceId());
										if(!(gb == null)){
											eBean.setReferenceScale(gb.getReferenceScale());
											//new reference 16 questions zeros not included
											DecimalFormat df = new DecimalFormat("0.00");

											double avgScore = 0;
											if( gb.getPossibleTotal() > 0){
												avgScore = (double) gb.getTotalScore() / gb.getPossibleTotal();
											}

											eBean.setReferenceScore(df.format(avgScore));
											eBean.setReferenceRecommendation(gb.getQ6());
											reffound=true;
										}
										
									}
									//now we check to see if one is found, if not then add none found entry
									//if(!(reffound)){
										//eBean.setReferenceScore("None on File");
									//}
									if(!(reffound)){
										NLESDReferenceExternalBean xbean = new NLESDReferenceExternalBean();
										if(rec.getReferenceId() > 0){
											xbean = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(rec.getReferenceId());
										}else{
											xbean = NLESDReferenceExternalManager.getLatestNLESDReferenceExternalBean(s);
										}
										if(xbean == null){
											eBean.setReferenceScore("None on File");
										}else{
											eBean.setReferenceScale(xbean.getReferenceScale());
											//new reference 16 questions zeros not included
											DecimalFormat df = new DecimalFormat("0.00");

											double avgScore = 0;
											if( xbean.getPossibleTotal() > 0){
												avgScore = (double) xbean.getTotalScore() / xbean.getPossibleTotal();
											}

											eBean.setReferenceScore(df.format(avgScore));
											eBean.setReferenceRecommendation(xbean.getQ7());
											reffound =true;
										}
									}
								}
							}
							

						}

					}else {
						//now we check for a reference for the other candidates using id and comp_num
						//first see if one was entered
						if(job.getIsSupport().equals("Y")){
							boolean supportreffound=false;
							NLESDReferenceSSSupportBean ad = NLESDReferenceSSSupportManager.getLatestNLESDReferenceSSSupportBean(s);
							if (ad != null) {
								//new reference 16 questions
								eBean.setReferenceScale(ad.getReferenceScale());
								DecimalFormat df = new DecimalFormat("0.00");
								double avgScore = 0;
								if( ad.getPossibleTotal() > 0){
									avgScore = (double) ad.getTotalScore() / ad.getPossibleTotal();
								}
								eBean.setReferenceScore(df.format(avgScore));
								eBean.setReferenceRecommendation(ad.getQ10());

								if (StringUtils.isBlank(eBean.getComments())) {
									if (StringUtils.isNotBlank(ad.getQ12())) {
										eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + ad.getProvidedBy() + " (" + ad.getProvidedByPosition()
												+ ")</i><br/>" + ad.getQ12());
									}
									else {
										eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + ad.getProvidedBy() + " (" + ad.getProvidedByPosition()
												+ ")</i><br/>No comments added.");
									}
								}
								supportreffound=true;
							}
							NLESDReferenceSSManageBean adm = NLESDReferenceSSManageManager.getLatestNLESDReferenceSSManageBean(s);
							if (adm != null) {
								//new reference 16 questions
								eBean.setReferenceScale(adm.getReferenceScale());
								DecimalFormat df = new DecimalFormat("0.00");
								double avgScore = 0;
								if( adm.getPossibleTotal() > 0){
									avgScore = (double) adm.getTotalScore() / adm.getPossibleTotal();
								}
								eBean.setReferenceScore(df.format(avgScore));
								eBean.setReferenceRecommendation(adm.getQ10());

								if (StringUtils.isBlank(eBean.getComments())) {
									if (StringUtils.isNotBlank(adm.getQ12())) {
										eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + adm.getProvidedBy() + " (" + adm.getProvidedByPosition()
												+ ")</i><br/>" + adm.getQ12());
									}
									else {
										eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + adm.getProvidedBy() + " (" + adm.getProvidedByPosition()
												+ ")</i><br/>No comments added.");
									}
								}
								supportreffound=true;
							}
							if(!(supportreffound)){
								eBean.setReferenceScore("None on File");
							}
						}else if(isAdministrative || isLeadership){
							NLESDReferenceAdminBean ad = NLESDReferenceAdminManager.getLatestNLESDReferenceAdminBean(s);
							if (ad != null) {
								//new reference 16 questions
								eBean.setReferenceScale(ad.getReferenceScale());
								DecimalFormat df = new DecimalFormat("0.00");
								double avgScore = 0;
								if( ad.getPossibleTotal() > 0){
									avgScore = (double) ad.getTotalScore() / ad.getPossibleTotal();
								}
								eBean.setReferenceScore(df.format(avgScore));
								eBean.setReferenceRecommendation(ad.getQ6());

								if (StringUtils.isBlank(eBean.getComments())) {
									if (StringUtils.isNotBlank(ad.getQ6Comment())) {
										eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + ad.getProvidedBy() + " (" + ad.getProvidedByPosition()
												+ ")</i><br/>" + ad.getQ6Comment());
									}
									else {
										eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + ad.getProvidedBy() + " (" + ad.getProvidedByPosition()
												+ ")</i><br/>No comments added.");
									}
								}
							} else {
								if(!(reffound)){
									NLESDReferenceExternalBean xbean = new NLESDReferenceExternalBean();
									xbean = NLESDReferenceExternalManager.getLatestNLESDReferenceExternalBean(s);
									if(xbean == null){
										eBean.setReferenceScore("None on File");
									}else{
										eBean.setReferenceScale(xbean.getReferenceScale());
										//new reference 16 questions zeros not included
										DecimalFormat df = new DecimalFormat("0.00");

										double avgScore = 0;
										if( xbean.getPossibleTotal() > 0){
											avgScore = (double) xbean.getTotalScore() / xbean.getPossibleTotal();
										}

										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(xbean.getQ7());
										reffound =true;
									}
								}
							}
						}else{
							//no good way to find guidance
							boolean isguide=false;
							String testing = job.getPositionTitle().toUpperCase();
							if(testing.indexOf("GUID") >= 0){
								isguide = true;
							}
							if(isguide){
								NLESDReferenceGuideBean gb = new NLESDReferenceGuideBean();
								gb=NLESDReferenceGuideManager.getLatestNLESDReferenceGuideBean(s);
								reffound = false;
								if(!(gb == null)){
									eBean.setReferenceScale(gb.getReferenceScale());
									//new reference 16 questions
									DecimalFormat df = new DecimalFormat("0.00");
									double avgScore = 0;
									if( gb.getPossibleTotal() > 0){
										avgScore = (double) gb.getTotalScore() / gb.getPossibleTotal();
									}
									eBean.setReferenceScore(df.format(avgScore));
									eBean.setReferenceRecommendation(gb.getQ6());
									if (StringUtils.isBlank(eBean.getComments())) {
										if (StringUtils.isNotBlank(gb.getQ6Comment())) {
											eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + gb.getProvidedBy() + " (" + gb.getProvidedByPosition()
													+ ")</i><br/>" + gb.getQ6Comment());
										}
										else {
											eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + gb.getProvidedBy() + " (" + gb.getProvidedByPosition()
													+ ")</i><br/>No comments added.");
										}
									}
									reffound=true;
								}else{
									//now we check the teaching references since it could be both
									NLESDReferenceTeacherBean tb = new NLESDReferenceTeacherBean();
									tb=NLESDReferenceTeacherManager.getLatestNLESDReferenceTeacherBean(s);
									if(!(tb == null)){
										eBean.setReferenceScale(tb.getReferenceScale());
										//new reference 16 questions
										DecimalFormat df = new DecimalFormat("0.00");
										double avgScore = 0;
										if( tb.getPossibleTotal() > 0){
											avgScore = (double) tb.getTotalScore() / tb.getPossibleTotal();
										}
										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(tb.getQ6());
										if (StringUtils.isBlank(eBean.getComments())) {
											if (StringUtils.isNotBlank(tb.getQ7Comment())) {
												eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + tb.getProvidedBy() + " (" + tb.getProvidedByPosition()
														+ ")</i><br/>" + tb.getQ7Comment());
											}
											else {
												eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + tb.getProvidedBy() + " (" + tb.getProvidedByPosition()
														+ ")</i><br/>No comments added.");
											}
										}
										reffound =true;
									}
									
									
								}
								//now we check to see if one is found, if not then add none found entry
								if(!(reffound)){
									NLESDReferenceExternalBean xbean = new NLESDReferenceExternalBean();
									xbean = NLESDReferenceExternalManager.getLatestNLESDReferenceExternalBean(s);
									if(xbean == null){
										eBean.setReferenceScore("None on File");
									}else{
										eBean.setReferenceScale(xbean.getReferenceScale());
										//new reference 16 questions zeros not included
										DecimalFormat df = new DecimalFormat("0.00");

										double avgScore = 0;
										if( xbean.getPossibleTotal() > 0){
											avgScore = (double) xbean.getTotalScore() / xbean.getPossibleTotal();
										}

										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(xbean.getQ7());
										reffound =true;
									}
								}
							}else{
								//now we check the teaching references since it could be both
								reffound=false;
								NLESDReferenceTeacherBean tb = new NLESDReferenceTeacherBean();
								tb=NLESDReferenceTeacherManager.getLatestNLESDReferenceTeacherBean(s);
								if(!(tb == null)){
									eBean.setReferenceScale(tb.getReferenceScale());
									//new reference 16 questions
									DecimalFormat df = new DecimalFormat("0.00");
									double avgScore = 0;
									if( tb.getPossibleTotal() > 0){
										avgScore = (double) tb.getTotalScore() / tb.getPossibleTotal();
									}
									eBean.setReferenceScore(df.format(avgScore));
									eBean.setReferenceRecommendation(tb.getQ6());
									if (StringUtils.isBlank(eBean.getComments())) {
										if (StringUtils.isNotBlank(tb.getQ7Comment())) {
											eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + tb.getProvidedBy() + " (" + tb.getProvidedByPosition()
													+ "</i><br/>" + tb.getQ7Comment());
										}
										else {
											eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + tb.getProvidedBy() + " (" + tb.getProvidedByPosition()
													+ ")</i><br/>No comments added.");
										}
									}
									reffound =true;
								}else{
									NLESDReferenceGuideBean gb = new NLESDReferenceGuideBean();
									gb=NLESDReferenceGuideManager.getLatestNLESDReferenceGuideBean(s);
									if(!(gb == null)){
										eBean.setReferenceScale(gb.getReferenceScale());
										//new reference 16 questions
										DecimalFormat df = new DecimalFormat("0.00");
										double avgScore = 0;
										avgScore = 0;
										if( tb.getPossibleTotal() > 0){
											avgScore = (double) tb.getTotalScore() / tb.getPossibleTotal();
										}
										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(gb.getQ6());
										if (StringUtils.isBlank(eBean.getComments())) {
											if (StringUtils.isNotBlank(gb.getQ6Comment())) {
												eBean.setComments("<i style='color:DimGrey;'>Reference Comment by " + gb.getProvidedBy() + " (" + gb.getProvidedByPosition()
														+ ")</i><br/>" + gb.getQ6Comment());
											}
											else {
												eBean.setComments("<i style='color:DimGrey;'>Reference provided by " + gb.getProvidedBy() + " (" + gb.getProvidedByPosition()
														+ ")</i><br/>No comments added.");
											}
										}
										reffound=true;
									}
								}
								//now we check to see if one is found, if not then add none found entry
								if(!(reffound)){
									NLESDReferenceExternalBean xbean = new NLESDReferenceExternalBean();
									xbean = NLESDReferenceExternalManager.getLatestNLESDReferenceExternalBean(s);
									if(xbean == null){
										eBean.setReferenceScore("None on File");
									}else{
										eBean.setReferenceScale(xbean.getReferenceScale());
										//new reference 16 questions zeros not included
										DecimalFormat df = new DecimalFormat("0.00");

										double avgScore = 0;
										if( xbean.getPossibleTotal() > 0){
											avgScore = (double) xbean.getTotalScore() / xbean.getPossibleTotal();
										}

										eBean.setReferenceScore(df.format(avgScore));
										eBean.setReferenceRecommendation(xbean.getQ7());
										reffound =true;
									}
								}
							}
						}
						
						
						
						
						
						
					}
					listbeans.add(eBean);

					

				}else{
					if(count < 3){
						NLESDRecommendationTrackingFormBean eBean = new NLESDRecommendationTrackingFormBean();
						eBean.setCandidateName("None Selected");
						eBean.setReferenceScore("");
						//eBean.setInterviewRow("<td style='border: solid 1px black;height: 100%;' class='displayText2' colspan='" + totalICols+1 + "'></td>");
						String test =checkInterviewString("<td>&nbsp;</td>",imaxcols+2);
						eBean.setInterviewRow(test);
						interviewCols.add(totalICols+2);
						listbeans.add(eBean);
					}
					
				}
				count = count + 1;
			}

			//Collections.sort(listbeans);
			//now we fix the interview section if there are interviews with different number of interviewers
			int zz=0;
			
			for(Integer xx : interviewCols){
				StringBuilder sbfix = new StringBuilder();
				if(xx < imaxcols){
					
					for(int x=0;x < imaxcols-xx;x++){
						if(listbeans.get(zz).getCandidateName().equals("None Selected")){
							sbfix.append("<td>&nbsp;</td>");
						}else{
							sbfix.append("<td>NA</td>");
						}
						
					}
					
				}
				
				//now we replace the string
				String replacestring = "";
				if(sbfix.length() > 0){
					replacestring = listbeans.get(zz).getInterviewRow().replaceFirst("INSERTHERE",sbfix.toString());
				}else{
					replacestring = listbeans.get(zz).getInterviewRow().replaceAll("INSERTHERE", "");
				}
				//String replacestring = listbeans.get(zz).getInterviewRow() + sbfix.toString();
				
				listbeans.get(zz).setInterviewRow(replacestring);
				
				if(zz == 0){
					if(xx < imaxcols){
						//now we fix the header
						StringBuilder headerfix = new StringBuilder();
						for(int a=0; a < imaxcols; a++){
							headerfix.append("<th>"
									+ (a+1) + "</th>");
						}
						headerfix.append("<th>Total(/"
							+ listbeans.get(zz).getScaleTop() + ")</th>");
						
						listbeans.get(zz).setInterviewHeader(headerfix.toString());
						listbeans.get(zz).setTotalInterviews(imaxcols+1);
					}
				}
				
				
				zz++;
				
			}
			

			return (listbeans);
		}
		catch (Exception e) {
			System.err.println("NLESDReferenceAdminManager.getNLESDReferenceAdminBean(int): " + e);
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
		return listbeans;
	}

	private static String checkInterviewString(String interviewstring, int adjustcount) {

		StringBuilder sb = new StringBuilder();
		Integer count = StringUtils.countMatches(interviewstring, "<td");
		if ((adjustcount - count) > 0) {
			//now loop through and pad the columns
			for (int x = 0; x < adjustcount - count; x++) {
				sb.append("<td>&nbsp;</td>");
			}
		}
		return sb.toString();
	}
}
