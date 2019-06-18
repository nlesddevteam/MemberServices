package com.esdnl.payadvice.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.awsd.servlet.ControllerServlet;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.cryptography.RC4128Security;
import com.cete.dynamicpdf.merger.ImportedPage;
import com.cete.dynamicpdf.pageelements.Label;
import com.esdnl.payadvice.bean.NLESDPayAdviceAftTaxesDedBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceAftTaxesDedTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceBefTaxesDedBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceBefTaxesDedTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEarningsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEarningsTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmpPaidBenefitsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmpPaidBenefitsTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeSecurityBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceInformationBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceMessageBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceTaxesBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceTaxesTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;

public class NLESDPayAdviceCreateStubManager {

	public static String createStubForEmployee(String empnumber, Integer paygroupid, String foldername, String email,
																							boolean isTest) {

		String filename = "";
		Document document = new Document();
		try {
			// Create a document and set it's properties
			document.setCreator("viewpaystub");
			document.setAuthor("nlesd");
			document.setTitle("Paystub");
			String template = ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.TEMPLATE_BASEPATH
					+ "payrolltemplate.pdf";
			//retrieve the employee information and write it to pdf
	        TreeMap<Integer,String>pslist =NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeePayStubs(empnumber, paygroupid);
	        
	        for(Map.Entry<Integer,String> entry : pslist.entrySet()) {
					Page objPage = new ImportedPage(template, 1);
					document.getPages().add(objPage);
		        	Integer empinfoid= entry.getKey();
					NLESDPayAdviceEmployeeInfoBean eibean = NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeeInfoBean(
							paygroupid, empnumber,empinfoid);
					//School/Office
					objPage.getElements().add(
							new Label(eibean.getLocnCode(), 50, 675, 175, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//Postal Name
					objPage.getElements().add(
							new Label(eibean.getEmpName(), 50, 690, 175, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//Postal Address
					objPage.getElements().add(
							new Label(eibean.getEmpAddrL1(), 50, 702, 275, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//Postal city
					objPage.getElements().add(
							new Label(eibean.getEmpAddrL2(), 50, 714, 275, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//Pay Location
					objPage.getElements().add(
							new Label(eibean.getLocnCode(), 25, 500, 290, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//employee number
					objPage.getElements().add(
							new Label(eibean.getEmpNumber(), 305, 413, 60, 12, Font.getHelvetica(), 10, TextAlign.CENTER));
					//Name
					objPage.getElements().add(
							new Label(eibean.getEmpName(), 50, 420, 165, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
			        //Address
			        objPage.getElements().add(new Label(eibean.getEmpAddrL1(), 50, 432, 275,
			                         12, Font.getHelvetica(), 10, TextAlign.LEFT));
			        //Address
			        objPage.getElements().add(new Label(eibean.getEmpAddrL2(), 50, 444, 275,
			                         12, Font.getHelvetica(), 10, TextAlign.LEFT));
					//TD1 Claim Fed
					objPage.getElements().add(
							new Label("FED :       " + String.format("%.2f", !isTest ? eibean.getNetClmAmt() : 0), 340, 495, 90, 10, Font.getHelvetica(), 10, TextAlign.LEFT));
					//TD1 Claim Prov
					objPage.getElements().add(
							new Label("PROV:       " + String.format("%.2f", !isTest ? eibean.getProvNclmAmt() : 0), 340, 505, 90, 10, Font.getHelvetica(), 10, TextAlign.LEFT));
	
					//now we addPaidBenefits
					TreeMap<Integer, NLESDPayAdviceEmpPaidBenefitsBean> epb = NLESDPayAdviceEmpPaidBenefitsManager.getNLESDPayAdviceEmpPaidBenefitsBean(
							paygroupid, empnumber,empinfoid);
					float initialValueY = 255;
					float yOffset = 0;
					Set set = epb.entrySet();
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						Map.Entry mentry = (Map.Entry) iterator.next();
						NLESDPayAdviceEmpPaidBenefitsBean ebean = (NLESDPayAdviceEmpPaidBenefitsBean) mentry.getValue();
						objPage.getElements().add(
								new Label(ebean.getEpbDesc(), 20, initialValueY + yOffset, 165, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? ebean.getEpbCur() : 0), 175, initialValueY + yOffset, 70, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? ebean.getEpbYtd() : 0), 260, initialValueY + yOffset, 60, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
						yOffset += 12;
	
					}
					//now we print the Paid Benefits totals
			        //now we print the Paid Benefits totals
			        NLESDPayAdviceEmpPaidBenefitsTotalsBean epbt = NLESDPayAdviceEmpPaidBenefitsTotalsManager.getNLESDPayAdviceEmpPaidBenefitsTotalsBean(paygroupid, empnumber,empinfoid);
			         //Paid Benefits Totals Current
			        if(!(epbt == null))
			        {
				        	objPage.getElements().add(new Label(String.format("%.2f",epbt.getEpbTtlCur()), 175, 390, 70,
				        				12, Font.getHelvetica(), 10, TextAlign.RIGHT));        
				        			//Paid Benefits Totals YTD
				        	objPage.getElements().add(new Label(String.format("%.2f",epbt.getEpbTtlYtd()), 250, 390, 70,
				        				12, Font.getHelvetica(), 10, TextAlign.RIGHT));
			        }
					//now we print the Earnings
					TreeMap<Integer, NLESDPayAdviceEarningsBean> ern = NLESDPayAdviceEarningsManager.getNLESDPayAdviceEarningsBean(
							paygroupid, empnumber,empinfoid);
					initialValueY = 77;
					yOffset = 0;
					Set ernset = ern.entrySet();
					Iterator erniterator = ernset.iterator();
					while (erniterator.hasNext()) {
						Map.Entry mentry = (Map.Entry) erniterator.next();
						NLESDPayAdviceEarningsBean ebean = (NLESDPayAdviceEarningsBean) mentry.getValue();
						objPage.getElements().add(
								new Label(ebean.getHeDesc(), 20, initialValueY + yOffset, 165, 12, Font.getHelvetica(), 10, TextAlign.LEFT));
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? ebean.getCurErn() : 0), 175, initialValueY + yOffset, 70, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? ebean.getYtdErn() : 0), 255, initialValueY + yOffset, 60, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
						yOffset += 12;
	
					}
					//now we print the Earnings totals
					NLESDPayAdviceEarningsTotalsBean erntot = NLESDPayAdviceEarningsTotalsManager.getNLESDPayAdviceEarningsTotalsBean(
							paygroupid, empnumber,empinfoid);
					if(!(erntot == null))
					{		
						//Earnings Totals Current
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? erntot.getTtlCurErn() : 0), 175, 210, 70, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
						//Earnings Totals YTD
						objPage.getElements().add(
								new Label(String.format("%.2f", !isTest ? erntot.getTtlYtdErn() : 0), 250, 210, 70, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
					}
					//now we print the Taxes
				      //now we print the Taxes
			        TreeMap<Integer,NLESDPayAdviceTaxesBean> tax = NLESDPayAdviceTaxesManager.getNLESDPayAdviceTaxesBean(paygroupid, empnumber,empinfoid);
			        TreeMap<Integer,NLESDPayAdviceBefTaxesDedBean> btax = NLESDPayAdviceBefTaxesDedManager.getNLESDPayAdviceBefTaxesDedBean(paygroupid, empnumber,empinfoid);
			        TreeMap<Integer,NLESDPayAdviceAftTaxesDedBean> atax = NLESDPayAdviceAftTaxesDedManager.getNLESDPayAdviceAftTaxesDedBean(paygroupid, empnumber,empinfoid);
			        Integer fontsize=11;
			        if(btax.size() + atax.size() > 18){
			        	fontsize=10;
			        }
			        initialValueY=77;
			        yOffset=0;
			        Set taxset = tax.entrySet();
			        Iterator taxiterator = taxset.iterator();
			        while(taxiterator.hasNext()) {
			        	Map.Entry mentry = (Map.Entry)taxiterator.next();
			        	NLESDPayAdviceTaxesBean ebean = (NLESDPayAdviceTaxesBean) mentry.getValue();
			            objPage.getElements().add(new Label(ebean.getTxDesc(), 330, initialValueY+yOffset, 110,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.LEFT));            
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getTxCur()), 445, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getTxYtd()), 515, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            yOffset += fontsize;
			        	
			        }
				      //now we print the BEfore Taxes Ded
			        Set btaxset = btax.entrySet();
			        Iterator btaxiterator = btaxset.iterator();
			        while(btaxiterator.hasNext()) {
			        	Map.Entry mentry = (Map.Entry)btaxiterator.next();
			        	NLESDPayAdviceBefTaxesDedBean ebean = (NLESDPayAdviceBefTaxesDedBean) mentry.getValue();
			        	objPage.getElements().add(new Label(ebean.getBtxdDescr(), 330, initialValueY+yOffset, 110,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.LEFT));            
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getBtxdCur()), 445, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getBtxdYtd()), 515, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            yOffset += fontsize;
			        	
			        }
				      //now we print the After Taxes Ded
			        //TreeMap<Integer,NLESDPayAdviceAftTaxesDedBean> atax = NLESDPayAdviceAftTaxesDedManager.getNLESDPayAdviceAftTaxesDedBean(paygroupid, empnumber);
			        Set ataxset = atax.entrySet();
			        Iterator ataxiterator = ataxset.iterator();
			        while(ataxiterator.hasNext()) {
			        	Map.Entry mentry = (Map.Entry)ataxiterator.next();
			        	NLESDPayAdviceAftTaxesDedBean ebean = (NLESDPayAdviceAftTaxesDedBean) mentry.getValue();
			        	objPage.getElements().add(new Label(ebean.getAtxdDescr(), 330, initialValueY+yOffset, 110,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.LEFT));            
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getAtxdCur()), 445, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            objPage.getElements().add(new Label(String.format("%.2f",ebean.getAtxdYtd()), 515, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            yOffset += fontsize;
			        }
			        //now we check to see if they have an federal addition taxes and show them
			        if(eibean.getFedAddlAmt() != "" && eibean.getFedAddlAmt() != null)
			        {
			            objPage.getElements().add(new Label("Fed Addl Tax", 330, initialValueY+yOffset, 110,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.LEFT));            
			            objPage.getElements().add(new Label(eibean.getFedAddlAmt(), 445, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			            objPage.getElements().add(new Label("", 515, initialValueY+yOffset, 60,
			            		fontsize, Font.getHelvetica(), fontsize, TextAlign.RIGHT));
			        }			        
					//now we print the Deductions totals
					NLESDPayAdviceTaxesTotalsBean taxtot = NLESDPayAdviceTaxesTotalsManager.getNLESDPayAdviceTaxesTotalsBean(
							paygroupid, empnumber,empinfoid);
					NLESDPayAdviceAftTaxesDedTotalsBean ataxtot = NLESDPayAdviceAftTaxesDedTotalsManager.getNLESDPayAdviceBefTaxesAftTotalsBean(
							paygroupid, empnumber,empinfoid);
					NLESDPayAdviceBefTaxesDedTotalsBean btaxtot = NLESDPayAdviceBefTaxesDedTotalsManager.getNLESDPayAdviceBefTaxesDedTotalsBean(
							paygroupid, empnumber,empinfoid);
					Double curtotal = (!(taxtot == null) ?taxtot.getTxTtlCur(): 0) + ((!(ataxtot == null)) ? ataxtot.getAtxdTtlCur():0) 
						+ ((!(btaxtot == null)) ? btaxtot.getBtxdTtlCur():0);
					Double ytdtotal = (!(taxtot == null) ?taxtot.getTxTtlYtd(): 0) + ((!(ataxtot == null)) ? ataxtot.getAtxdTtlYtd():0) 
					+ ((!(btaxtot == null)) ? btaxtot.getBtxdTtlYtd():0);
					//Earnings ded current totals
					objPage.getElements().add(
							new Label(String.format("%.2f", !isTest ? curtotal : 0), 445, 340, 60, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
					//Earnings ded ytd totals
					objPage.getElements().add(
							new Label(String.format("%.2f", !isTest ? ytdtotal : 0), 515, 340, 60, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
					//Now we get the payroll Message
					NLESDPayAdviceMessageBean mbean = NLESDPayAdviceMessageManager.getNLESDPayAdviceMessageBean(paygroupid);
					if (mbean != null) {
						objPage.getElements().add(
								new Label(mbean.getMessage(), 335, 365, 235, 36, Font.getHelvetica(), 10, TextAlign.LEFT));
					}
					//Now we get the net pay
					NLESDPayAdviceInformationBean ibean = NLESDPayAdviceInformationManager.getNLESDPayAdviceInformationBean(
							paygroupid, empnumber,empinfoid);
					objPage.getElements().add(
							new Label(!isTest ? ibean.getDdNetPay() : "0.00", 505, 413, 60, 12, Font.getHelvetica(), 10, TextAlign.RIGHT));
					//Now we get the pay period
					NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(paygroupid);
					//Pay period
					objPage.getElements().add(
							new Label(" (Ending " + pgbean.getPayEndDt() + ")", 445, 495, 120, 10, Font.getHelvetica(), 10, TextAlign.LEFT));
					//Deposited Date
					objPage.getElements().add(
							new Label("DEPOSIT :  " + pgbean.getCheckDt(), 445, 505, 120, 10, Font.getHelvetica(), 10, TextAlign.LEFT));
		    }

			NLESDPayAdviceCreateHistoryPageManager cman = new NLESDPayAdviceCreateHistoryPageManager();
			//now we check for history and add the pages
			String hreturnstring = cman.createWorkHistoryPage(empnumber, paygroupid, "", document);
			// Outputs the Invoices to the current web page
			// Create RC4 128 bit encryption security object that prevents text copying.
			//check to see if they have a password
			NLESDPayAdviceEmployeeSecurityBean secbean = NLESDPayAdviceEmployeeSecurityManager.getNLESDPayAdviceEmployeeSecurityBean(empnumber);
			String pdfpassword = "";
			if (secbean.getPassword() == null) {
				//we need to create the password
				pdfpassword = NLESDPayAdviceEmployeeSecurityManager.createNewPassword(secbean, email);
			}
			else {
				pdfpassword = secbean.getPassword();
				
			}
			RC4128Security security = new RC4128Security(pdfpassword);
			security.setAllowCopy(false);
			
			// Add the security object to the document
			document.setSecurity(security);

			// Outputs the W-9 to the file
			filename = foldername + "/stubs/PS" + empnumber + paygroupid + ".pdf";
			System.out.println(filename);
			document.draw(filename);

		}
		catch (Exception e) {
			e.printStackTrace(System.err);

		}
		return filename;
	}
}
