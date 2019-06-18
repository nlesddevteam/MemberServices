package com.awsd.mail;

public class EmailThread extends Thread
{
  private String recipients[];
  private String cc[];
  private String bcc[];
  private String subject;
  private String message;
  private String from;
  private int max_attempts;
  
  public EmailThread(String recipients[], String subject, String message , String from, int max_attempts)
  {
    this.recipients = recipients;
    this.cc = null;
    this.bcc = null;
    this.subject = subject;
    this.message = message;
    this.from = from;
    this.max_attempts = max_attempts;
  }
  
  public EmailThread(String recipients[], String cc[], String bcc[], String subject, String message , String from, int max_attempts)
  {
  	this(recipients, subject, message, from, max_attempts);
  	
  	this.cc = cc;
  	this.bcc = bcc;
  }
  
  public void run()
  {
    boolean success = false;
    int attempt = 0;
    
    SMTPAuthenticatedMail smtp = new SMTPAuthenticatedMail("mail.esdnl.ca", "ms", "services");
    
    while (!success && (attempt < max_attempts))
    {
      try
      {
        smtp.postMail(recipients, cc, bcc, subject, message, from);
        success = true;
      }
      catch(Exception e)
      {
        e.printStackTrace();
        success = false;
        attempt++;
      }
    }
  }
}