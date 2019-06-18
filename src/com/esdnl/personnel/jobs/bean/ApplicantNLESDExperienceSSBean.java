package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
public class ApplicantNLESDExperienceSSBean implements Serializable {

	private static final long serialVersionUID = 5304511837525367873L;
	private int id;
	private String currentlyEmployed;
	private String sin;
	private Date senorityDate;
	private String senorityStatus;
	private String position1;
	private int position1School;
	private String position1Hours;
	private String position2;
	private int position2School;
	private String position2Hours;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrentlyEmployed() {
		return currentlyEmployed;
	}
	public void setCurrentlyEmployed(String currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public Date getSenorityDate() {
		return senorityDate;
	}
	public void setSenorityDate(Date senorityDate) {
		this.senorityDate = senorityDate;
	}
	public String getSenorityStatus() {
		return senorityStatus;
	}
	public void setSenorityStatus(String senorityStatus) {
		this.senorityStatus = senorityStatus;
	}
	public String getPosition1() {
		return position1;
	}
	public void setPosition1(String position1) {
		this.position1 = position1;
	}
	public int getPosition1School() {
		return position1School;
	}
	public void setPosition1School(int position1School) {
		this.position1School = position1School;
	}
	public String getPosition1Hours() {
		return position1Hours;
	}
	public void setPosition1Hours(String position1Hours) {
		this.position1Hours = position1Hours;
	}
	public String getPosition2() {
		return position2;
	}
	public void setPosition2(String position2) {
		this.position2 = position2;
	}
	public int getPosition2School() {
		return position2School;
	}
	public void setPosition2School(int position2School) {
		this.position2School = position2School;
	}
	public String getPosition2Hours() {
		return position2Hours;
	}
	public void setPosition2Hours(String position2Hours) {
		this.position2Hours = position2Hours;
	}
	public String getSenorityDateFormatted() {
		String strDate="";
		if( this.getSenorityDate() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			strDate=sdf.format(this.getSenorityDate());
		}
		return strDate;
	}
	public String getSchool1Name(){
		School  s = null;
		String name="";
		if(this.getPosition1School()> 0){
			try {
				s=SchoolDB.getSchool(this.getPosition1School());
				if(s != null){
					name=s.getSchoolName();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SchoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return name;
	}
	public String getSchool2Name(){
		School  s = null;
		String name="";
		if(this.getPosition2School() > 0){
			try {
				s=SchoolDB.getSchool(this.getPosition2School());
				if(s != null){
					name=s.getSchoolName();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SchoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return name;
	}
	public String getSenorityStatusText(){
		String stext="";
		if(this.senorityStatus != null){
			if(this.senorityStatus.equals("C")){
				stext="Casual";
			}else if(this.senorityStatus.equals("P")){
				stext="Permanent";
			}else if(this.senorityStatus.equals("S")){
				stext="Substitute";
			}else if(this.senorityStatus.equals("T")){
				stext="Temporary";
			}else{
				stext="";
			}
			
		}
		return stext;
	}
}
