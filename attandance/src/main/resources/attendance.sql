--用户表
CREATE TABLE userinfo
(
    userID BIGINT PRIMARY KEY AUTO_INCREMENT,
    loginID VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(32),
    Regdate DATE,
    rights VARCHAR(200),
    ismanager CHAR(1),
    dept VARCHAR(20),
	salary DOUBLE,
    schedulingID BIGINT
);
--权限表
CREATE TABLE permission
(
    pID BIGINT PRIMARY KEY AUTO_INCREMENT,
    pgroup VARCHAR(20),
    rights VARCHAR(200)
);
--请假表
CREATE TABLE askforleave
(
    askID BIGINT PRIMARY KEY AUTO_INCREMENT,
    userID BIGINT,
    userName VARCHAR(20),
    entryDate DATE,
    days DOUBLE,
    chargeName VARCHAR(20),
    chargeComment VARCHAR(200),
    chargeDate DATE,
    hrName VARCHAR(20),
    hrComment VARCHAR(200),
    hrDate DATE,
    askDept VARCHAR(20),
    step INT,
    remark VARCHAR(200)
);
--出差表
CREATE TABLE travel
(
    travelID BIGINT PRIMARY KEY AUTO_INCREMENT,
    travelUserID BIGINT,
    travelUserName VARCHAR(20),
    travelDate DATE,
    travelDays INT,
    address VARCHAR(200),
    cost DOUBLE,
    tchargeName VARCHAR(20),
    tchargeComment VARCHAR(200),
    tchargeDate DATE,
    thrName VARCHAR(20),
    thrComment VARCHAR(200),
    thrDate DATE,
    tstep INT,
    remark INT
);
--考勤表
CREATE TABLE userattendance
(
  aID       BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  auserName VARCHAR(20)  ,
  beginTime DATETIME     ,
  amType    CHAR         ,
  endTime   DATETIME     ,
  pmType    CHAR         ,
  distance  DOUBLE       ,
  attRemark VARCHAR(200) ,
  aDept     VARCHAR(20)  ,
  aUserID   BIGINT
--旷工表LL
)
CREATE TABLE absent
(
    absentID BIGINT PRIMARY KEY AUTO_INCREMENT,
    abUserID BIGINT,
    abUserName VARCHAR(20),
    abDept VARCHAR(20),
    abDate DATE,
    abRemark VARCHAR(200)
);
--加班表
CREATE TABLE overtime
(
    oid BIGINT PRIMARY KEY AUTO_INCREMENT,
    oUserID BIGINT,
    oUserName VARCHAR(20),
    overTimeDate DATE,
    hours DOUBLE,
    reason VARCHAR(200),
    oStartTime DATETIME,
    oEndTime DATETIME,
    ochargeName VARCHAR(20),
    ochargeComment VARCHAR(200),
    ochargeDate DATE,
    ohrName VARCHAR(20),
    ohrComment VARCHAR(200),
    ohrDate DATE,
    odept VARCHAR(20),
    ostep INT
);
--补卡表
CREATE TABLE reissuecard
(
    rID BIGINT PRIMARY KEY AUTO_INCREMENT,
    rUserID BIGINT,
    rUserName VARCHAR(20),
    rDept VARCHAR(20),
    rDate DATE,
    rType CHAR(1),
    rchargeName VARCHAR(20),
    rchargeComment VARCHAR(20),
    rchargeDate DATE,
    rhrName VARCHAR(20),
    rhrComment VARCHAR(200),
    rhrDate DATE,
    rStep INT
);
--班次表
CREATE TABLE scheduling
(
    sID BIGINT PRIMARY KEY AUTO_INCREMENT,
    sType CHAR(1),
    sStartTime DATETIME,
    sEndTime DATETIME
);