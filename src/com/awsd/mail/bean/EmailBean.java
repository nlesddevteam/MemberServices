package com.awsd.mail.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.awsd.mail.dao.EmailManager;
import com.awsd.personnel.Personnel;

public class EmailBean {

	public static final String EMAIL_PATTERN = "^([\\w-']+)(\\.[\\w-']+)*@([\\w-]+)(\\.[\\w-]+)*(\\.[A-Za-z]{2,})$";
	public static final String CONTENTTYPE_PLAIN = "text/plain";
	public static final String CONTENTTYPE_HTML = "text/html";

	private static Pattern emailPattern = null;

	private int id;
	private String from;
	private ArrayList<String> to;
	private ArrayList<String> cc;
	private ArrayList<String> bcc;
	private String subject;
	private String body;
	private boolean use_template;
	private Date sent_date;
	private boolean deleted;
	private String smtp_error;
	private String content_type;

	private File[] attachments;

	public static final String BASE_TEMPLATE = "<html><head><title></title></head><body>"
			+ "<table width='800px' border='0' cellspacing='0' cellpadding='0' style='font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; font-size: 12px; color: Black;'><tr><td width='800px'>&nbsp;</td></tr>"
			+ "<tr valign='top'><td width='800px' align='left'><a href='http://www.nlesd.ca'><img src='http://www.nlesd.ca/templates/email/email-header.png' alt='Newfoundland &amp; Labrador English School District' border='0' align='left'></a></td></tr>"
			+ "<tr style='color: Black;'><td width='800px' style='padding-left:10px;padding-right:10px;padding-top:20px;padding-bottom:25px;'>###EMAIL_BODY###</td></tr><tr><td width='800px'>&nbsp;</td></tr>"
			+ "<tr valign='bottom'><td width='800px'><img src='http://www.nlesd.ca/templates/email/email-footer.png' alt='Newfoundland &amp; Labrador English School District' border='0' align='left'></td></tr>"
			+ "</table></body></html>";

	static {
		emailPattern = Pattern.compile(EMAIL_PATTERN);
	}

	public EmailBean() {

		this.id = 0;
		this.from = "ms@nlesd.ca";
		this.to = null;
		this.cc = null;
		this.bcc = null;
		this.subject = null;
		this.body = null;
		this.attachments = null;
		this.sent_date = null;
		this.deleted = false;
		this.smtp_error = null;
		this.content_type = EmailBean.CONTENTTYPE_HTML;
		this.use_template = true;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getFrom() {

		return this.from;
	}

	public void setFrom(String from) {

		this.from = from;
	}

	public String[] getTo() {

		String tmp[] = null;
		if (this.to != null)
			tmp = (String[]) this.to.toArray(new String[0]);

		return tmp;
	}

	public String getToComplete() {

		StringBuffer buf = new StringBuffer();

		String tmp[] = getTo();
		for (int i = 0; ((tmp != null) && (i < tmp.length)); i++)
			buf.append(((i > 0) ? ";" : "") + tmp[i]);

		return buf.toString();
	}

	public void setTo(String to) {

		if (to == null || to.trim().equals(""))
			return;

		String tmp[] = to.split("[,;]");

		if (tmp == null || tmp.length < 1)
			return;

		this.to = new ArrayList<String>(tmp.length);

		for (int i = 0; i < tmp.length; i++) {
			if (EmailBean.isValidEmailAddress(tmp[i].trim()))
				this.to.add(tmp[i].trim());
			else
				this.smtp_error += "Invalid address format [" + tmp[i] + "] ";
		}
	}

	public void setTo(String[] to) {

		if (to == null || to.length < 1)
			return;

		this.to = new ArrayList<String>(to.length);

		for (int i = 0; i < to.length; i++) {
			if (EmailBean.isValidEmailAddress(to[i].trim()))
				this.to.add(to[i].trim());
			else
				this.smtp_error += "Invalid address format [" + to[i] + "] ";
		}
	}

	public void setTo(ArrayList<Personnel> to) {

		if (to == null)
			return;

		this.to = new ArrayList<String>(to.size());
		for (int i = 0; i < to.size(); i++) {
			if (EmailBean.isValidEmailAddress(((Personnel) to.get(i)).getEmailAddress().trim()))
				this.to.add(((Personnel) to.get(i)).getEmailAddress().trim());
			else
				this.smtp_error += "Invalid address format [" + ((Personnel) to.get(i)).getEmailAddress() + "] ";
		}
	}

	public void setTo(Personnel[] to) {

		if (to == null || to.length < 1)
			return;

		this.to = new ArrayList<String>(to.length);
		for (int i = 0; i < to.length; i++) {
			if (EmailBean.isValidEmailAddress(to[i].getEmailAddress().trim()))
				this.to.add(to[i].getEmailAddress().trim());
			else
				this.smtp_error += "Invalid address format [" + to[i].getEmailAddress() + "] ";
		}
	}

	public String[] getCC() {

		String tmp[] = null;
		if (this.cc != null)
			tmp = (String[]) this.cc.toArray(new String[0]);

		return tmp;
	}

	public String getCCComplete() {

		if (cc == null)
			return null;

		StringBuffer buf = new StringBuffer();

		String tmp[] = getCC();
		for (int i = 0; i < tmp.length; i++)
			buf.append(((i > 0) ? ";" : "") + tmp[i]);

		return buf.toString();
	}

	public void setCC(String cc) {

		if (cc == null || cc.trim().equals(""))
			return;

		String tmp[] = cc.split("[,;]");

		if (tmp == null || tmp.length < 1)
			return;

		this.cc = new ArrayList<String>(tmp.length);

		for (int i = 0; i < tmp.length; i++) {
			if (EmailBean.isValidEmailAddress(tmp[i].trim()))
				this.cc.add(tmp[i].trim());
			else
				this.smtp_error += "Invalid address format [" + tmp[i] + "] ";
		}
	}

	public void setCC(String[] cc) {

		if (cc == null || cc.length < 1)
			return;

		this.cc = new ArrayList<String>(cc.length);

		for (int i = 0; i < cc.length; i++) {
			if (EmailBean.isValidEmailAddress(cc[i].trim()))
				this.cc.add(cc[i].trim());
			else
				this.smtp_error += "Invalid address format [" + cc[i] + "] ";
		}
	}

	public String[] getBCC() {

		String tmp[] = null;

		if (this.bcc != null)
			tmp = (String[]) this.bcc.toArray(new String[0]);

		return tmp;
	}

	public String getBCCComplete() {

		if (bcc == null)
			return null;

		StringBuffer buf = new StringBuffer();

		String tmp[] = getBCC();

		for (int i = 0; i < tmp.length; i++)
			buf.append(((i > 0) ? ";" : "") + tmp[i]);

		return buf.toString();
	}

	public void setBCC(String bcc) {

		if (bcc == null || bcc.trim().equals(""))
			return;

		String tmp[] = bcc.split("[,;]");

		if (tmp == null || tmp.length < 1)
			return;

		this.bcc = new ArrayList<String>(tmp.length);

		for (int i = 0; i < tmp.length; i++) {
			if (EmailBean.isValidEmailAddress(tmp[i].trim()))
				this.bcc.add(tmp[i].trim());
			else
				this.smtp_error += "Invalid address format [" + tmp[i] + "] ";
		}

	}

	public void setBCC(String[] bcc) {

		if (bcc == null || bcc.length < 1)
			return;

		this.bcc = new ArrayList<String>(bcc.length);

		for (int i = 0; i < bcc.length; i++) {
			if (EmailBean.isValidEmailAddress(bcc[i].trim()))
				this.bcc.add(bcc[i].trim());
			else
				this.smtp_error += "Invalid address format [" + bcc[i] + "] ";
		}
	}

	public String getRecipientAddresses() {

		StringBuffer buf = new StringBuffer();

		buf.append("TO: " + this.getToComplete());
		buf.append(" CC: " + this.getCCComplete());
		buf.append(" BCC: " + this.getBCCComplete());

		return buf.toString();
	}

	public String getSubject() {

		return this.subject;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public String getBody() {

		return this.body;
	}

	public void setBody(String body) {

		this.body = body;
	}

	public String getContentType() {

		return this.content_type;
	}

	public void setContentType(String content_type) {

		this.content_type = content_type;
	}

	public boolean useTemplate() {

		return this.use_template;
	}

	public void setUseTemplate(boolean use_template) {

		this.use_template = use_template;
	}

	public File[] getAttachments() {

		return this.attachments;
	}

	public void setAttachments(File[] attachments) {

		this.attachments = attachments;
	}

	public Date getSentDate() {

		return this.sent_date;
	}

	public void setSentDate(Date sent_date) {

		this.sent_date = sent_date;
	}

	public boolean isDeleted() {

		return this.deleted;
	}

	public void setDeleted(boolean deleted) {

		this.deleted = deleted;
	}

	public String getSMTPError() {

		return this.smtp_error;
	}

	public void setSMTPError(String smtp_error) {

		this.smtp_error = smtp_error;
	}

	public boolean hasValidAddress() {

		return StringUtils.isNotBlank(this.getToComplete()) || StringUtils.isNotBlank(this.getCCComplete())
				|| StringUtils.isNotBlank(this.getBCCComplete());
	}

	public void removeInvalidAddresses(javax.mail.Address[] addresses) {

		for (int i = 0; i < addresses.length; i++) {
			if (this.to != null)
				this.to.remove(addresses[i].toString());

			if (this.cc != null)
				this.cc.remove(addresses[i].toString());

			if (this.bcc != null)
				this.bcc.remove(addresses[i].toString());
		}
	}

	public void send() throws EmailException {

		if (this.hasValidAddress()) {
			if (this.content_type.equals(EmailBean.CONTENTTYPE_HTML) && this.use_template) {
				String new_body = EmailBean.BASE_TEMPLATE.replace("###EMAIL_BODY###", this.getBody());
				this.setBody(new_body);
			}

			EmailManager.addEmailBean(this);
		}
		else {
			throw new EmailException("Email has no valid address. STMP_ERROR=" + this.getSMTPError());
		}
	}

	public void queue(Date queuedFor) throws EmailException {

		if (this.hasValidAddress()) {
			if (this.content_type.equals(EmailBean.CONTENTTYPE_HTML) && this.use_template) {
				String new_body = EmailBean.BASE_TEMPLATE.replace("###EMAIL_BODY###", this.getBody());
				this.setBody(new_body);
			}

			EmailManager.addEmailBean(this, queuedFor);
		}
		else {
			throw new EmailException("Email has no valid address. STMP_ERROR=" + this.getSMTPError());
		}
	}

	public String toString() {

		StringBuffer buf = new StringBuffer();

		buf.append("FROM: " + this.getFrom());

		if (this.to != null)
			buf.append("\nTO: " + this.getToComplete());

		if (this.cc != null)
			buf.append("\nCC: " + this.getCCComplete());

		if (this.bcc != null)
			buf.append("\nBCC: " + this.getBCCComplete());

		return buf.toString();
	}

	public static boolean isValidEmailAddress(String address) {

		Matcher matcher = emailPattern.matcher(address);

		return matcher.matches();

	}
}
