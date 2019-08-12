CREATE PROCEDURE PODetail(IN:FYR_START INTEGER)
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
		ENCUM DECIMAL(19, 3)
		);
BEGIN
	SELECT
		GLAMF.ACSEGVAL01 AS ACCTSEG,
		GLAMF.ACSEGVAL02 AS DEPRT,
		GLAMF.ACSEGVAL03 AS PROJT,
		GLAMF.ACCTFMTTD AS ACCTFRMT,
		POPORH1.DATE,
		POPORH1.VDNAME AS VENDOR,
		CONVERT(NULL(), SQL_CHAR)AS INVNUM,
		POPORH1.PONUMBER AS PONUM,
		CONVERT(NULL(), SQL_CHAR) AS Actual,
		SUM(POPORL.EXTENDED) AS ENCUM 
	FROM 
		POPORH1 RIGHT JOIN 
		(GLAMF LEFT JOIN POPORL ON GLAMF.ACCTFMTTD = POPORL.GLACEXPENS) 
		ON POPORH1.PORHSEQ = POPORL.PORHSEQ 
	GROUP BY 
		GLAMF.ACSEGVAL01, 
		GLAMF.ACSEGVAL02,
		GLAMF.ACSEGVAL03,
		GLAMF.ACCTFMTTD,  
		POPORH1.DATE, 
		POPORH1.VDNAME,
		POPORH1.PONUMBER, 
		POPORH1.ISCOMPLETE
	HAVING 
		((POPORH1.DATE > :FYR_START) AND (POPORH1.ISCOMPLETE = 0));
END;