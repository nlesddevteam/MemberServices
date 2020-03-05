package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;

public class CountryTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String value;
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setCls(String cls)
  {
    this.cls = cls;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    try
    {
      out = pageContext.getOut();
      
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT COUNTRY</OPTION>");
      out.println("<OPTION VALUE='CA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CA"))?" SELECTED":"") + ">Canada</OPTION>");
      out.println("<OPTION VALUE='US'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("US"))?" SELECTED":"") + ">United States</OPTION>");
      out.println("<OPTION VALUE='-1'>---------------------------------------------</OPTION>");
  	out.println("<OPTION value='AF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AF"))?" SELECTED":"") + ">Afghanistan</OPTION>");
  	out.println("<OPTION value='AX'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AX"))?" SELECTED":"") + ">Aland Islands</OPTION>");
  	out.println("<OPTION value='AL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AL"))?" SELECTED":"") + ">Albania</OPTION>");
  	out.println("<OPTION value='DZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DZ"))?" SELECTED":"") + ">Algeria</OPTION>");
  	out.println("<OPTION value='AS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AS"))?" SELECTED":"") + ">American Samoa</OPTION>");
  	out.println("<OPTION value='AD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AD"))?" SELECTED":"") + ">Andorra</OPTION>");
  	out.println("<OPTION value='AO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AO"))?" SELECTED":"") + ">Angola</OPTION>");
  	out.println("<OPTION value='AI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AI"))?" SELECTED":"") + ">Anguilla</OPTION>");
  	out.println("<OPTION value='AQ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AQ"))?" SELECTED":"") + ">Antarctica</OPTION>");
  	out.println("<OPTION value='AG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AG"))?" SELECTED":"") + ">Antigua and Barbuda</OPTION>");
  	out.println("<OPTION value='AR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AR"))?" SELECTED":"") + ">Argentina</OPTION>");
  	out.println("<OPTION value='AM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AM"))?" SELECTED":"") + ">Armenia</OPTION>");
  	out.println("<OPTION value='AW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AW"))?" SELECTED":"") + ">Aruba</OPTION>");
  	out.println("<OPTION value='AU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AU"))?" SELECTED":"") + ">Australia</OPTION>");
  	out.println("<OPTION value='AT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AT"))?" SELECTED":"") + ">Austria</OPTION>");
  	out.println("<OPTION value='AZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AZ"))?" SELECTED":"") + ">Azerbaijan</OPTION>");
  	out.println("<OPTION value='BS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BS"))?" SELECTED":"") + ">Bahamas</OPTION>");
  	out.println("<OPTION value='BH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BH"))?" SELECTED":"") + ">Bahrain</OPTION>");
  	out.println("<OPTION value='BD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BD"))?" SELECTED":"") + ">Bangladesh</OPTION>");
  	out.println("<OPTION value='BB'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BB"))?" SELECTED":"") + ">Barbados</OPTION>");
  	out.println("<OPTION value='BY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BY"))?" SELECTED":"") + ">Belarus</OPTION>");
  	out.println("<OPTION value='BE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BE"))?" SELECTED":"") + ">Belgium</OPTION>");
  	out.println("<OPTION value='BZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BZ"))?" SELECTED":"") + ">Belize</OPTION>");
  	out.println("<OPTION value='BJ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BJ"))?" SELECTED":"") + ">Benin</OPTION>");
  	out.println("<OPTION value='BM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BM"))?" SELECTED":"") + ">Bermuda</OPTION>");
  	out.println("<OPTION value='BT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BT"))?" SELECTED":"") + ">Bhutan</OPTION>");
  	out.println("<OPTION value='BO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BO"))?" SELECTED":"") + ">Bolivia, Plurinational State of</OPTION>");
  	out.println("<OPTION value='BQ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BQ"))?" SELECTED":"") + ">Bonaire, Sint Eustatius and Saba</OPTION>");
  	out.println("<OPTION value='BA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BA"))?" SELECTED":"") + ">Bosnia and Herzegovina</OPTION>");
  	out.println("<OPTION value='BW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BW"))?" SELECTED":"") + ">Botswana</OPTION>");
  	out.println("<OPTION value='BV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BV"))?" SELECTED":"") + ">Bouvet Island</OPTION>");
  	out.println("<OPTION value='BR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BR"))?" SELECTED":"") + ">Brazil</OPTION>");
  	out.println("<OPTION value='IO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IO"))?" SELECTED":"") + ">British Indian Ocean Territory</OPTION>");
  	out.println("<OPTION value='BN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BN"))?" SELECTED":"") + ">Brunei Darussalam</OPTION>");
  	out.println("<OPTION value='BG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BG"))?" SELECTED":"") + ">Bulgaria</OPTION>");
  	out.println("<OPTION value='BF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BF"))?" SELECTED":"") + ">Burkina Faso</OPTION>");
  	out.println("<OPTION value='BI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BI"))?" SELECTED":"") + ">Burundi</OPTION>");
  	out.println("<OPTION value='KH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KH"))?" SELECTED":"") + ">Cambodia</OPTION>");
  	out.println("<OPTION value='CM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CM"))?" SELECTED":"") + ">Cameroon</OPTION>");  
  	out.println("<OPTION value='CV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CV"))?" SELECTED":"") + ">Cape Verde</OPTION>");
  	out.println("<OPTION value='KY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KY"))?" SELECTED":"") + ">Cayman Islands</OPTION>");
  	out.println("<OPTION value='CF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CF"))?" SELECTED":"") + ">Central African Republic</OPTION>");
  	out.println("<OPTION value='TD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TD"))?" SELECTED":"") + ">Chad</OPTION>");
  	out.println("<OPTION value='CL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CL"))?" SELECTED":"") + ">Chile</OPTION>");
  	out.println("<OPTION value='CN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CN"))?" SELECTED":"") + ">China</OPTION>");
  	out.println("<OPTION value='CX'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CX"))?" SELECTED":"") + ">Christmas Island</OPTION>");
  	out.println("<OPTION value='CC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CC"))?" SELECTED":"") + ">Cocos (Keeling) Islands</OPTION>");
  	out.println("<OPTION value='CO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CO"))?" SELECTED":"") + ">Colombia</OPTION>");
  	out.println("<OPTION value='KM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KM"))?" SELECTED":"") + ">Comoros</OPTION>");
  	out.println("<OPTION value='CG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CG"))?" SELECTED":"") + ">Congo</OPTION>");
  	out.println("<OPTION value='CD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CD"))?" SELECTED":"") + ">Congo, the Democratic Republic of the</OPTION>");
  	out.println("<OPTION value='CK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CK"))?" SELECTED":"") + ">Cook Islands</OPTION>");
  	out.println("<OPTION value='CR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CR"))?" SELECTED":"") + ">Costa Rica</OPTION>");
  	out.println("<OPTION value='CI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CI"))?" SELECTED":"") + ">Côte d'Ivoire</OPTION>");
  	out.println("<OPTION value='HR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HR"))?" SELECTED":"") + ">Croatia</OPTION>");
  	out.println("<OPTION value='CU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CU"))?" SELECTED":"") + ">Cuba</OPTION>");
  	out.println("<OPTION value='CW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CW"))?" SELECTED":"") + ">Curaçao</OPTION>");
  	out.println("<OPTION value='CY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CY"))?" SELECTED":"") + ">Cyprus</OPTION>");
  	out.println("<OPTION value='CZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CZ"))?" SELECTED":"") + ">Czech Republic</OPTION>");
  	out.println("<OPTION value='DK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DK"))?" SELECTED":"") + ">Denmark</OPTION>");
  	out.println("<OPTION value='DJ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DJ"))?" SELECTED":"") + ">Djibouti</OPTION>");
  	out.println("<OPTION value='DM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DM"))?" SELECTED":"") + ">Dominica</OPTION>");
  	out.println("<OPTION value='DO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DO"))?" SELECTED":"") + ">Dominican Republic</OPTION>");
  	out.println("<OPTION value='EC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("EC"))?" SELECTED":"") + ">Ecuador</OPTION>");
  	out.println("<OPTION value='EG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("EG"))?" SELECTED":"") + ">Egypt</OPTION>");
  	out.println("<OPTION value='SV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SV"))?" SELECTED":"") + ">El Salvador</OPTION>");
  	out.println("<OPTION value='GQ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GQ"))?" SELECTED":"") + ">Equatorial Guinea</OPTION>");
  	out.println("<OPTION value='ER'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ER"))?" SELECTED":"") + ">Eritrea</OPTION>");
  	out.println("<OPTION value='EE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("EE"))?" SELECTED":"") + ">Estonia</OPTION>");
  	out.println("<OPTION value='ET'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ET"))?" SELECTED":"") + ">Ethiopia</OPTION>");
  	out.println("<OPTION value='FK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FK"))?" SELECTED":"") + ">Falkland Islands (Malvinas)</OPTION>");
  	out.println("<OPTION value='FO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FO"))?" SELECTED":"") + ">Faroe Islands</OPTION>");
  	out.println("<OPTION value='FJ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FJ"))?" SELECTED":"") + ">Fiji</OPTION>");
  	out.println("<OPTION value='FI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FI"))?" SELECTED":"") + ">Finland</OPTION>");
  	out.println("<OPTION value='FR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FR"))?" SELECTED":"") + ">France</OPTION>");
  	out.println("<OPTION value='GF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GF"))?" SELECTED":"") + ">French Guiana</OPTION>");
  	out.println("<OPTION value='PF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PF"))?" SELECTED":"") + ">French Polynesia</OPTION>");
  	out.println("<OPTION value='TF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TF"))?" SELECTED":"") + ">French Southern Territories</OPTION>");
  	out.println("<OPTION value='GA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GA"))?" SELECTED":"") + ">Gabon</OPTION>");
  	out.println("<OPTION value='GM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GM"))?" SELECTED":"") + ">Gambia</OPTION>");
  	out.println("<OPTION value='GE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GE"))?" SELECTED":"") + ">Georgia</OPTION>");
  	out.println("<OPTION value='DE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("DE"))?" SELECTED":"") + ">Germany</OPTION>");
  	out.println("<OPTION value='GH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GH"))?" SELECTED":"") + ">Ghana</OPTION>");
  	out.println("<OPTION value='GI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GI"))?" SELECTED":"") + ">Gibraltar</OPTION>");
  	out.println("<OPTION value='GR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GR"))?" SELECTED":"") + ">Greece</OPTION>");
  	out.println("<OPTION value='GL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GL"))?" SELECTED":"") + ">Greenland</OPTION>");
  	out.println("<OPTION value='GD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GD"))?" SELECTED":"") + ">Grenada</OPTION>");
  	out.println("<OPTION value='GP'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GP"))?" SELECTED":"") + ">Guadeloupe</OPTION>");
  	out.println("<OPTION value='GU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GU"))?" SELECTED":"") + ">Guam</OPTION>");
  	out.println("<OPTION value='GT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GT"))?" SELECTED":"") + ">Guatemala</OPTION>");
  	out.println("<OPTION value='GG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GG"))?" SELECTED":"") + ">Guernsey</OPTION>");
  	out.println("<OPTION value='GN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GN"))?" SELECTED":"") + ">Guinea</OPTION>");
  	out.println("<OPTION value='GW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GW"))?" SELECTED":"") + ">Guinea-Bissau</OPTION>");
  	out.println("<OPTION value='GY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GY"))?" SELECTED":"") + ">Guyana</OPTION>");
  	out.println("<OPTION value='HT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HT"))?" SELECTED":"") + ">Haiti</OPTION>");
  	out.println("<OPTION value='HM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HM"))?" SELECTED":"") + ">Heard Island and McDonald Islands</OPTION>");
  	out.println("<OPTION value='VA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VA"))?" SELECTED":"") + ">Holy See (Vatican City State)</OPTION>");
  	out.println("<OPTION value='HN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HN"))?" SELECTED":"") + ">Honduras</OPTION>");
  	out.println("<OPTION value='HK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HK"))?" SELECTED":"") + ">Hong Kong</OPTION>");
  	out.println("<OPTION value='HU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("HU"))?" SELECTED":"") + ">Hungary</OPTION>");
  	out.println("<OPTION value='IS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IS"))?" SELECTED":"") + ">Iceland</OPTION>");
  	out.println("<OPTION value='IN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IN"))?" SELECTED":"") + ">India</OPTION>");
  	out.println("<OPTION value='ID'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ID"))?" SELECTED":"") + ">Indonesia</OPTION>");
  	out.println("<OPTION value='IR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IR"))?" SELECTED":"") + ">Iran, Islamic Republic of</OPTION>");
  	out.println("<OPTION value='IQ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IQ"))?" SELECTED":"") + ">Iraq</OPTION>");
  	out.println("<OPTION value='IE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IE"))?" SELECTED":"") + ">Ireland</OPTION>");
  	out.println("<OPTION value='IM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IM"))?" SELECTED":"") + ">Isle of Man</OPTION>");
  	out.println("<OPTION value='IL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IL"))?" SELECTED":"") + ">Israel</OPTION>");
  	out.println("<OPTION value='IT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("IT"))?" SELECTED":"") + ">Italy</OPTION>");
  	out.println("<OPTION value='JM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("JM"))?" SELECTED":"") + ">Jamaica</OPTION>");
  	out.println("<OPTION value='JP'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("JP"))?" SELECTED":"") + ">Japan</OPTION>");
  	out.println("<OPTION value='JE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("JE"))?" SELECTED":"") + ">Jersey</OPTION>");
  	out.println("<OPTION value='JO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("JO"))?" SELECTED":"") + ">Jordan</OPTION>");
  	out.println("<OPTION value='KZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KZ"))?" SELECTED":"") + ">Kazakhstan</OPTION>");
  	out.println("<OPTION value='KE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KE"))?" SELECTED":"") + ">Kenya</OPTION>");
  	out.println("<OPTION value='KI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KI"))?" SELECTED":"") + ">Kiribati</OPTION>");
  	out.println("<OPTION value='KP'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KP"))?" SELECTED":"") + ">Korea, Democratic People's Republic of</OPTION>");
  	out.println("<OPTION value='KR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KR"))?" SELECTED":"") + ">Korea, Republic of</OPTION>");
  	out.println("<OPTION value='KW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KW"))?" SELECTED":"") + ">Kuwait</OPTION>");
  	out.println("<OPTION value='KG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KG"))?" SELECTED":"") + ">Kyrgyzstan</OPTION>");
  	out.println("<OPTION value='LA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LA"))?" SELECTED":"") + ">Lao People's Democratic Republic</OPTION>");
  	out.println("<OPTION value='LV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LV"))?" SELECTED":"") + ">Latvia</OPTION>");
  	out.println("<OPTION value='LB'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LB"))?" SELECTED":"") + ">Lebanon</OPTION>");
  	out.println("<OPTION value='LS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LS"))?" SELECTED":"") + ">Lesotho</OPTION>");
  	out.println("<OPTION value='LR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LR"))?" SELECTED":"") + ">Liberia</OPTION>");
  	out.println("<OPTION value='LY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LY"))?" SELECTED":"") + ">Libya</OPTION>");
  	out.println("<OPTION value='LI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LI"))?" SELECTED":"") + ">Liechtenstein</OPTION>");
  	out.println("<OPTION value='LT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LT"))?" SELECTED":"") + ">Lithuania</OPTION>");
  	out.println("<OPTION value='LU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LU"))?" SELECTED":"") + ">Luxembourg</OPTION>");
  	out.println("<OPTION value='MO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MO"))?" SELECTED":"") + ">Macao</OPTION>");
  	out.println("<OPTION value='MK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MK"))?" SELECTED":"") + ">Macedonia, the former Yugoslav Republic of</OPTION>");
  	out.println("<OPTION value='MG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MG"))?" SELECTED":"") + ">Madagascar</OPTION>");
  	out.println("<OPTION value='MW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MW"))?" SELECTED":"") + ">Malawi</OPTION>");
  	out.println("<OPTION value='MY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MY"))?" SELECTED":"") + ">Malaysia</OPTION>");
  	out.println("<OPTION value='MV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MV"))?" SELECTED":"") + ">Maldives</OPTION>");
  	out.println("<OPTION value='ML'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ML"))?" SELECTED":"") + ">Mali</OPTION>");
  	out.println("<OPTION value='MT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MT"))?" SELECTED":"") + ">Malta</OPTION>");
  	out.println("<OPTION value='MH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MH"))?" SELECTED":"") + ">Marshall Islands</OPTION>");
  	out.println("<OPTION value='MQ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MQ"))?" SELECTED":"") + ">Martinique</OPTION>");
  	out.println("<OPTION value='MR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MR"))?" SELECTED":"") + ">Mauritania</OPTION>");
  	out.println("<OPTION value='MU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MU"))?" SELECTED":"") + ">Mauritius</OPTION>");
  	out.println("<OPTION value='YT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("YT"))?" SELECTED":"") + ">Mayotte</OPTION>");
  	out.println("<OPTION value='MX'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MX"))?" SELECTED":"") + ">Mexico</OPTION>");
  	out.println("<OPTION value='FM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("FM"))?" SELECTED":"") + ">Micronesia, Federated States of</OPTION>");
  	out.println("<OPTION value='MD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MD"))?" SELECTED":"") + ">Moldova, Republic of</OPTION>");
  	out.println("<OPTION value='MC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MC"))?" SELECTED":"") + ">Monaco</OPTION>");
  	out.println("<OPTION value='MN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MN"))?" SELECTED":"") + ">Mongolia</OPTION>");
  	out.println("<OPTION value='ME'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ME"))?" SELECTED":"") + ">Montenegro</OPTION>");
  	out.println("<OPTION value='MS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MS"))?" SELECTED":"") + ">Montserrat</OPTION>");
  	out.println("<OPTION value='MA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MA"))?" SELECTED":"") + ">Morocco</OPTION>");
  	out.println("<OPTION value='MZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MZ"))?" SELECTED":"") + ">Mozambique</OPTION>");
  	out.println("<OPTION value='MM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MM"))?" SELECTED":"") + ">Myanmar</OPTION>");
  	out.println("<OPTION value='NA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NA"))?" SELECTED":"") + ">Namibia</OPTION>");
  	out.println("<OPTION value='NR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NR"))?" SELECTED":"") + ">Nauru</OPTION>");
  	out.println("<OPTION value='NP'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NP"))?" SELECTED":"") + ">Nepal</OPTION>");
  	out.println("<OPTION value='NL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NL"))?" SELECTED":"") + ">Netherlands</OPTION>");
  	out.println("<OPTION value='NC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NC"))?" SELECTED":"") + ">New Caledonia</OPTION>");
  	out.println("<OPTION value='NZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NZ"))?" SELECTED":"") + ">New Zealand</OPTION>");
  	out.println("<OPTION value='NI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NI"))?" SELECTED":"") + ">Nicaragua</OPTION>");
  	out.println("<OPTION value='NE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NE"))?" SELECTED":"") + ">Niger</OPTION>");
  	out.println("<OPTION value='NG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NG"))?" SELECTED":"") + ">Nigeria</OPTION>");
  	out.println("<OPTION value='NU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NU"))?" SELECTED":"") + ">Niue</OPTION>");
  	out.println("<OPTION value='NF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NF"))?" SELECTED":"") + ">Norfolk Island</OPTION>");
  	out.println("<OPTION value='MP'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MP"))?" SELECTED":"") + ">Northern Mariana Islands</OPTION>");
  	out.println("<OPTION value='NO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("NO"))?" SELECTED":"") + ">Norway</OPTION>");
  	out.println("<OPTION value='OM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("OM"))?" SELECTED":"") + ">Oman</OPTION>");
  	out.println("<OPTION value='PK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PK"))?" SELECTED":"") + ">Pakistan</OPTION>");
  	out.println("<OPTION value='PW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PW"))?" SELECTED":"") + ">Palau</OPTION>");
  	out.println("<OPTION value='PS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PS"))?" SELECTED":"") + ">Palestinian Territory, Occupied</OPTION>");
  	out.println("<OPTION value='PA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PA"))?" SELECTED":"") + ">Panama</OPTION>");
  	out.println("<OPTION value='PG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PG"))?" SELECTED":"") + ">Papua New Guinea</OPTION>");
  	out.println("<OPTION value='PY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PY"))?" SELECTED":"") + ">Paraguay</OPTION>");
  	out.println("<OPTION value='PE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PE"))?" SELECTED":"") + ">Peru</OPTION>");
  	out.println("<OPTION value='PH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PH"))?" SELECTED":"") + ">Philippines</OPTION>");
  	out.println("<OPTION value='PN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PN"))?" SELECTED":"") + ">Pitcairn</OPTION>");
  	out.println("<OPTION value='PL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PL"))?" SELECTED":"") + ">Poland</OPTION>");
  	out.println("<OPTION value='PT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PT"))?" SELECTED":"") + ">Portugal</OPTION>");
  	out.println("<OPTION value='PR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PR"))?" SELECTED":"") + ">Puerto Rico</OPTION>");
  	out.println("<OPTION value='QA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("QA"))?" SELECTED":"") + ">Qatar</OPTION>");
  	out.println("<OPTION value='RE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("RE"))?" SELECTED":"") + ">Réunion</OPTION>");
  	out.println("<OPTION value='RO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("RO"))?" SELECTED":"") + ">Romania</OPTION>");
  	out.println("<OPTION value='RU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("RU"))?" SELECTED":"") + ">Russian Federation</OPTION>");
  	out.println("<OPTION value='RW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("RW"))?" SELECTED":"") + ">Rwanda</OPTION>");
  	out.println("<OPTION value='BL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("BL"))?" SELECTED":"") + ">Saint Barthelemy</OPTION>");
  	out.println("<OPTION value='SH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SH"))?" SELECTED":"") + ">Saint Helena, Ascension and Tristan da Cunha</OPTION>");
  	out.println("<OPTION value='KN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("KN"))?" SELECTED":"") + ">Saint Kitts and Nevis</OPTION>");
  	out.println("<OPTION value='LC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LC"))?" SELECTED":"") + ">Saint Lucia</OPTION>");
  	out.println("<OPTION value='MF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("MF"))?" SELECTED":"") + ">Saint Martin (French part)</OPTION>");
  	out.println("<OPTION value='PM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("PM"))?" SELECTED":"") + ">Saint Pierre and Miquelon</OPTION>");
  	out.println("<OPTION value='VC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VC"))?" SELECTED":"") + ">Saint Vincent and the Grenadines</OPTION>");
  	out.println("<OPTION value='WS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("WS"))?" SELECTED":"") + ">Samoa</OPTION>");
  	out.println("<OPTION value='SM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SM"))?" SELECTED":"") + ">San Marino</OPTION>");
  	out.println("<OPTION value='ST'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ST"))?" SELECTED":"") + ">Sao Tome and Principe</OPTION>");
  	out.println("<OPTION value='SA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SA"))?" SELECTED":"") + ">Saudi Arabia</OPTION>");
  	out.println("<OPTION value='SN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SN"))?" SELECTED":"") + ">Senegal</OPTION>");
  	out.println("<OPTION value='RS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("RS"))?" SELECTED":"") + ">Serbia</OPTION>");
  	out.println("<OPTION value='SC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SC"))?" SELECTED":"") + ">Seychelles</OPTION>");
  	out.println("<OPTION value='SL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SL"))?" SELECTED":"") + ">Sierra Leone</OPTION>");
  	out.println("<OPTION value='SG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SG"))?" SELECTED":"") + ">Singapore</OPTION>");
  	out.println("<OPTION value='SX'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SX"))?" SELECTED":"") + ">Sint Maarten (Dutch part)</OPTION>");
  	out.println("<OPTION value='SK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SK"))?" SELECTED":"") + ">Slovakia</OPTION>");
  	out.println("<OPTION value='SI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SI"))?" SELECTED":"") + ">Slovenia</OPTION>");
  	out.println("<OPTION value='SB'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SB"))?" SELECTED":"") + ">Solomon Islands</OPTION>");
  	out.println("<OPTION value='SO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SO"))?" SELECTED":"") + ">Somalia</OPTION>");
  	out.println("<OPTION value='ZA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ZA"))?" SELECTED":"") + ">South Africa</OPTION>");
  	out.println("<OPTION value='GS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GS"))?" SELECTED":"") + ">South Georgia and the South Sandwich Islands</OPTION>");
  	out.println("<OPTION value='SS'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SS"))?" SELECTED":"") + ">South Sudan</OPTION>");
  	out.println("<OPTION value='ES'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ES"))?" SELECTED":"") + ">Spain</OPTION>");
  	out.println("<OPTION value='LK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("LK"))?" SELECTED":"") + ">Sri Lanka</OPTION>");
  	out.println("<OPTION value='SD'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SD"))?" SELECTED":"") + ">Sudan</OPTION>");
  	out.println("<OPTION value='SR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SR"))?" SELECTED":"") + ">Suriname</OPTION>");
  	out.println("<OPTION value='SJ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SJ"))?" SELECTED":"") + ">Svalbard and Jan Mayen</OPTION>");
  	out.println("<OPTION value='SZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SZ"))?" SELECTED":"") + ">Swaziland</OPTION>");
  	out.println("<OPTION value='SE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SE"))?" SELECTED":"") + ">Sweden</OPTION>");
  	out.println("<OPTION value='CH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CH"))?" SELECTED":"") + ">Switzerland</OPTION>");
  	out.println("<OPTION value='SY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("SY"))?" SELECTED":"") + ">Syrian Arab Republic</OPTION>");
  	out.println("<OPTION value='TW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TW"))?" SELECTED":"") + ">Taiwan, Province of China</OPTION>");
  	out.println("<OPTION value='TJ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TJ"))?" SELECTED":"") + ">Tajikistan</OPTION>");
  	out.println("<OPTION value='TZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TZ"))?" SELECTED":"") + ">Tanzania, United Republic of</OPTION>");
  	out.println("<OPTION value='TH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TH"))?" SELECTED":"") + ">Thailand</OPTION>");
  	out.println("<OPTION value='TL'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TL"))?" SELECTED":"") + ">Timor-Leste</OPTION>");
  	out.println("<OPTION value='TG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TG"))?" SELECTED":"") + ">Togo</OPTION>");
  	out.println("<OPTION value='TK'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TK"))?" SELECTED":"") + ">Tokelau</OPTION>");
  	out.println("<OPTION value='TO'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TO"))?" SELECTED":"") + ">Tonga</OPTION>");
  	out.println("<OPTION value='TT'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TT"))?" SELECTED":"") + ">Trinidad and Tobago</OPTION>");
  	out.println("<OPTION value='TN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TN"))?" SELECTED":"") + ">Tunisia</OPTION>");
  	out.println("<OPTION value='TR'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TR"))?" SELECTED":"") + ">Turkey</OPTION>");
  	out.println("<OPTION value='TM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TM"))?" SELECTED":"") + ">Turkmenistan</OPTION>");
  	out.println("<OPTION value='TC'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TC"))?" SELECTED":"") + ">Turks and Caicos Islands</OPTION>");
  	out.println("<OPTION value='TV'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("TV"))?" SELECTED":"") + ">Tuvalu</OPTION>");
  	out.println("<OPTION value='UG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("UG"))?" SELECTED":"") + ">Uganda</OPTION>");
  	out.println("<OPTION value='UA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("UA"))?" SELECTED":"") + ">Ukraine</OPTION>");
  	out.println("<OPTION value='AE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("AE"))?" SELECTED":"") + ">United Arab Emirates</OPTION>");
  	out.println("<OPTION value='GB'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("GB"))?" SELECTED":"") + ">United Kingdom</OPTION>");  	
  	out.println("<OPTION value='UM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("UM"))?" SELECTED":"") + ">United States Minor Outlying Islands</OPTION>");
  	out.println("<OPTION value='UY'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("UY"))?" SELECTED":"") + ">Uruguay</OPTION>");
  	out.println("<OPTION value='UZ'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("UZ"))?" SELECTED":"") + ">Uzbekistan</OPTION>");
  	out.println("<OPTION value='VU'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VU"))?" SELECTED":"") + ">Vanuatu</OPTION>");
  	out.println("<OPTION value='VE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VE"))?" SELECTED":"") + ">Venezuela, Bolivarian Republic of</OPTION>");
  	out.println("<OPTION value='VN'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VN"))?" SELECTED":"") + ">Viet Nam</OPTION>");
  	out.println("<OPTION value='VG'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VG"))?" SELECTED":"") + ">Virgin Islands, British</OPTION>");
  	out.println("<OPTION value='VI'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("VI"))?" SELECTED":"") + ">Virgin Islands, U.S.</OPTION>");
  	out.println("<OPTION value='WF'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("WF"))?" SELECTED":"") + ">Wallis and Futuna</OPTION>");
  	out.println("<OPTION value='EH'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("EH"))?" SELECTED":"") + ">Western Sahara</OPTION>");
  	out.println("<OPTION value='YE'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("YE"))?" SELECTED":"") + ">Yemen</OPTION>");
  	out.println("<OPTION value='ZM'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ZM"))?" SELECTED":"") + ">Zambia</OPTION>");
  	out.println("<OPTION value='ZW'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("ZW"))?" SELECTED":"") + ">Zimbabwe</OPTION>");
      
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}