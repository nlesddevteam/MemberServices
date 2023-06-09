CREATE PROCEDURE PORETDetail(IN:FYR CHAR(4))
RETURNS(
		ACCTSEG CHAR(5),
		DEPRT CHAR(3),
		PROJT CHAR(7),
		ACCTFRMT CHAR(45),
		DT DECIMAL(9),
		VENDOR CHAR(60),
		INVNUM CHAR(22),
		PONUM CHAR(22),
		ACTUAL DECIMAL(19, 3),
		ENCUM DECIMAL(19, 3),
		FISCYR CHAR(4)
		);
BEGIN
	SELECT
		GLAMF.ACSEGVAL01 AS ACCTSEG,
		GLAMF.ACSEGVAL02 AS DEPRT,
		GLAMF.ACSEGVAL03 AS PROJT,
		GLAMF.ACCTFMTTD AS ACCTFRMT,
		PORETH1.DATE,
		PORETH1.VDNAME AS VENDOR,
		PORETH1.RCPNUMBER AS INVNUM,
		PORETH1.PONUMBER AS PONUM,
		0-PORETL.EXTENDED AS Actual,
		0 AS ENCUM,
		PORETH1.FISCYEAR AS FISCYR  
	FROM 
		(GLAMF LEFT OUTER JOIN PORETL ON GLAMF.ACCTFMTTD = PORETL.GLACEXPENS) 
		LEFT OUTER JOIN PORETH1 ON PORETL.RETHSEQ = PORETH1.RETHSEQ 
	GROUP BY 
		GLAMF.ACSEGVAL01, 
		GLAMF.ACSEGVAL02,
		GLAMF.ACSEGVAL03, 
		GLAMF.ACCTFMTTD, 
		PORETH1.DATE, 
		PORETH1.VDNAME, 
		PORETH1.RCPNUMBER, 
		PORETH1.PONUMBER, 
		PORETL.EXTENDED, 
		PORETH1.FISCYEAR, 
		PORETH1.ISCOMPLETE 
	HAVING 
		((PORETL.EXTENDED <> 0) 
		AND 
		(PORETH1.FISCYEAR = :FYR) 
		AND 
		(PORETH1.ISCOMPLETE = 0));
END;
