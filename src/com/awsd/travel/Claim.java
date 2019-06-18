// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 28/04/2008 2:04:28 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Claim.java

package com.awsd.travel;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import java.util.Date;

// Referenced classes of package com.awsd.travel:
//            TravelClaimException, TravelClaimStatus, TravelClaimItems, TravelClaimSummary, 
//            TravelClaimRateSummaries, HistoryItems, TravelClaimNotes

public interface Claim
{

    public abstract int getClaimID();

    public abstract Personnel getPersonnel()
        throws PersonnelException;

    public abstract Date getCreatedDate();

    public abstract Date getSubmitDate();

    public abstract TravelClaimStatus getCurrentStatus();

    public abstract String getFiscalYear();

    public abstract int getFiscalMonth();

    public abstract boolean setCurrentStatus(Personnel personnel, TravelClaimStatus travelclaimstatus);

    public abstract TravelClaimItems getItems()
        throws TravelClaimException;

    public abstract TravelClaimSummary getSummaryTotals()
        throws TravelClaimException;

    public abstract TravelClaimRateSummaries getRateSummaryTotals()
        throws TravelClaimException;

    public abstract Personnel getSupervisor();

    public abstract String getSDSGLAccountCode();

    public abstract void setSDSGLAccountCode(String s);

    public abstract Date getApprovedDate();

    public abstract void setApprovedDate(Date date);

    public abstract Date getPaidDate();

    public abstract void setPaidDate(Date date);

    public abstract Date getExportDate();

    public abstract void setExportDate(Date date);

    public abstract boolean isPaidThroughTeacherPayroll();

    public abstract void setPaidThroughTeacherPayroll(boolean flag);

    public abstract HistoryItems getHistory()
        throws TravelClaimException;

    public abstract TravelClaimNotes getNotes()
        throws TravelClaimException;
}