#ifndef __VRATGINTERFACE_H__
#define __VRATGINTERFACE_H__

#define MAX_OILCAN        16
#define MAX_ARRAYSIZE     16
#define MAX_REMARK        256
#define MAX_REPORT        1024
#define MAX_CAPACITYHIGH  1024
#define SZ_DATE8          8
#define SZ_TIME6          6
#define SZ_DATETIME14     14
#define SZ_ALARMTYPE      4
#define SZ_ALARMSTATE     4
#define SZ_EQUIPCODE      64
#define SZ_FAILCODE       8
#define SZ_EQUIPBRAND     64
#define SZ_DEVICETYPE     2
#define SZ_PROBEMODEL     64
#define SZ_OILNO          8
#define SZ_OILNAME        64
#define SZ_DEVICEMODEL    32
#define SZ_SERIALADDRESS  32
#define SZ_SERIALBAUDRATE 8
#define SZ_SERIALSTOPBIT  1
#define SZ_SERIALCHECKBIT 1
#define SZ_SERIALDATABIT  1
#define SZ_IPADDRESS      32
#define SZ_IPPORT         8
#define SZ_PATH           128
#define SZ_VERSION        16

//返回错误值
enum ATG_RETURN_VALUE
{
   ATG_ERROR_CONFIG_INVALID           = 10000,
   ATG_ERROR_CONNECT                  = 10001,
   ATG_ERROR_OPECODE_INVALID          = 10002,
   ATG_ERROR_OPECODE                  = 10003,
   ATG_ERROR_OPECODE_CHECKTIME        = 10004,
   ATG_ERROR_OPECODE_ALARMSETTER      = 10005,
   ATG_ERROR_OPECODE_CHGOILINFO       = 10006,
   ATG_ERROR_OPECODE_SETCAPACITYTABLE = 10007,
   ATG_ERROR_OPECODE_GETCAPACITYTABLE = 10008,
   ATG_ERROR_OPECODE_STARTLIQUID      = 10009,
   ATG_ERROR_OPECODE_STOPLIQUID       = 10010,
   ATG_ERROR_OPECODE_GETDEVICEINFO    = 10012,
   ATG_ERROR_OPECODE_PARAMINVALID     = 10013
   ATG_ERROR_OPERATE_INVALID          = 10100
};

#ifndef ATTRIBUTE_PACK
   #define ATTRIBUTE_PACK __attribute__((packed))
#endif

//连接方式 网口串口
enum ConnectionModeType
{
   ConnectionMode_SerialPort = 0,
   ConnectionMode_Ethernet   = 1
};

//测漏类型
enum LeakDetectType
{
   LeakDetect_Static = 0
};

//初始化输入参数
struct atg_init_in_t
{
   char strDeviceType[SZ_DEVICETYPE + 1];
   int uConnMode;
   char strSerialAddress[SZ_SERIALADDRESS + 1];
   char strSerialBaudRate[SZ_SERIALBAUDRATE + 1];
   char strSerialStopBit[SZ_SERIALSTOPBIT + 1];
   char strSerialCheckBit[SZ_SERIALCHECKBIT + 1];
   char strSerialDataBit[SZ_SERIALDATABIT + 1];
   char strIPAddress[SZ_IPADDRESS + 1];
   char strIPPort[SZ_IPPORT + 1];
   char strLogPath[SZ_PATH + 1];
} ATTRIBUTE_PACK;

//探棒校正 输入参数 元素
struct atg_correction_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];
   char strProbeNo[SZ_PROBEMODEL + 1];
   int uOilType;
   double fOilCorrection;    
   double fWaterCorrection;  
   double fProbeOffset;
   double fTiltOffset;
   double fTemp1;           
   double fProbeTemp1;       
   double fTemp2;            
   double fProbeTemp2;       
   double fTemp3;            
   double fProbeTemp3;       
   double fTemp4;            
   double fProbeTemp4;       
   double fTemp5;            
   double fProbeTemp5;       
   double fInitDensity;      
   double fInitHighDiff;     
   double fCorrectionFactor; 
} ATTRIBUTE_PACK;

struct atg_correction_in_t
{
   int uCount;
   struct atg_correction_data_in_t* pCorrectionData;
} ATTRIBUTE_PACK;

//探棒油罐配置项
struct atg_probecan_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];
   char strProbeNo[SZ_PROBEMODEL + 1];
   int uProbePort;
   int uOilCanNo;
   int uOilType;
   char strOilNo[SZ_OILNO + 1];
} ATTRIBUTE_PACK;

//探棒有关配置参数
struct atg_probecan_in_t
{
   int uCount;
   struct atg_probecan_data_in_t* pCorrectionData;
} ATTRIBUTE_PACK;


struct atg_oilcan_data_in_t
{
   int uOilCanNo;
} ATTRIBUTE_PACK;

//库存采集输入
struct atg_stock_in_t
{
   int uCount;
   struct atg_oilcan_data_in_t* pOilCanData;
} ATTRIBUTE_PACK;

//实时库存采集输出项
struct atg_stock_data_out_t
{
   int uOilCanNo;
   char strDate[SZ_DATE8 + 1];
   char strTime[SZ_TIME6 + 1];
   double fOilCubage;
   double fOilStandCubage;
   double fEmptyCubage;
   double fTotalHeight;
   double fWaterHeight;
   double fOilTemp;
   double fOilTemp1;
   double fOilTemp2;
   double fOilTemp3;
   double fOilTemp4;
   double fOilTemp5;
   double fWaterBulk;
   double fApparentDensity;
   double fStandDensity;
} ATTRIBUTE_PACK;
//实时库存采集输出
struct atg_stock_out_t
{
   int uRetCount;
   struct atg_stock_data_out_t* pStockData;
} ATTRIBUTE_PACK;

//整点库存采集
struct atg_timestock_data_in_t
{
   int uOilCanNo;
   char strDateTime[SZ_DATETIME14 + 1];
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_timestock_in_t
{
   int uCount;
   struct atg_timestock_data_in_t* pTimeStockData;
} ATTRIBUTE_PACK;


//进油信息
struct atg_oilin_data_in_t
{
   int uOilCanNo;
   char strDateTime[SZ_DATETIME14 + 1];
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_oilin_in_t
{
   int uCount;
   struct atg_oilin_data_in_t* pOilInData;
} ATTRIBUTE_PACK;

//进油信息输出
struct atg_oilin_data_out_t
{
   int uOilCanNo;
   char strStartDate[SZ_DATE8 + 1];
   char strEndDate[SZ_DATE8 + 1];
   char strStartTime[SZ_TIME6 + 1];   
   char strEndTime[SZ_TIME6 + 1];
   double fStartCubage;
   double fStartStandCubage;
   double fStartOilHeight;
   double fStartWaterHeight;
   double fStartOilTemp;
   double fStartOilTemp1; 
   double fStartOilTemp2; 
   double fStartOilTemp3; 
   double fStartOilTemp4; 
   double fStartOilTemp5; 
   double fEndCubage;
   double fEndStandCubage;
   double fEndOilHeight;
   double fEndWaterHeight;
   double fEndOilTemp;
   double fEndOilTemp1;   
   double fEndOilTemp2;   
   double fEndOilTemp3;   
   double fEndOilTemp4;   
   double fEndOilTemp5;   
   double fEmptyCubage;
   double fApparentDensity;
   double fStandDensity;
} ATTRIBUTE_PACK;

struct atg_oilin_out_t
{
   int uRetCount;
   struct atg_oilin_data_out_t* pOilinData;
} ATTRIBUTE_PACK;


//预警信息输入项
struct atg_alarm_data_in_t
{
   int uOilCanNo;
   char strDateTime[SZ_DATETIME14 + 1];
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_alarm_in_t
{
   int uCount;
   struct atg_alarm_data_in_t* pAlarmData;
} ATTRIBUTE_PACK;

//报警信息输出项
struct atg_alarm_data_out_t
{
   int uOilCanNo;
   char strDate[SZ_DATE8 + 1];
   char strTime[SZ_TIME6 + 1];
   char strAlarmType[SZ_ALARMTYPE + 1];
   char strAlarmState[SZ_ALARMSTATE + 1];
   char strRemark[MAX_REMARK + 1];
   char strReport[MAX_REPORT + 1];
} ATTRIBUTE_PACK;

struct atg_alarm_out_t
{
   int uRetCount;
   struct atg_alarm_data_out_t* pAlarmData;
} ATTRIBUTE_PACK;


//故障采集输入项
struct atg_failure_data_in_t
{
   int uOilCanNo;
   char strDateTime[SZ_DATETIME14 + 1];
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_failure_in_t
{
   int uCount;
   struct atg_failure_data_in_t* pFailureData;
} ATTRIBUTE_PACK;

//故障采集输入项
struct atg_failure_data_out_t
{
   int uOilCanNo;
   char strDate[SZ_DATE8 + 1];
   char strTime[SZ_TIME6 + 1];
   char strDeviceType[SZ_DEVICETYPE + 1];
   char strFailureType[SZ_ALARMTYPE + 1];
   char strEquipCode[SZ_EQUIPCODE + 1];
   char strFailCode[SZ_FAILCODE + 1];
   char strEquipBrand[SZ_EQUIPBRAND + 1];
   char strProbeModel[SZ_PROBEMODEL + 1];
   char strRemark[MAX_REMARK + 1];
} ATTRIBUTE_PACK;

struct atg_failure_out_t
{
   int uRetCount;
   struct atg_failure_data_out_t* pFailureData;
} ATTRIBUTE_PACK;


//预警设置
struct atg_setalarm_data_in_t
{
   int uOilCanNo;
   double fLowWarning;
   double fLowAlarm;
   double fHighWarning;
   double fHighAlarm;
   double fWaterAlarm;
   double fThiefAlarm;         
   double fLeakAlarm;
   double fPercolatiingAlarm;  
   double fHighTempAlarm;      
   double fLowTempAlarm;       
} ATTRIBUTE_PACK;

//预警设置
struct atg_setalarm_in_t
{
   int uReqCount;
   struct atg_setalarm_data_in_t* pAlarmSetterData;
} ATTRIBUTE_PACK;


//油管油品变类
struct atg_chgoilinfo_data_in_t
{
   int uOilCanNo;
   char strOilNo[SZ_OILNO + 1];
   char strOilName[SZ_OILNAME + 1];
} ATTRIBUTE_PACK;

//获取容积表
struct atg_chgoilinfo_in_t
{
   int uCount;
   struct atg_chgoilinfo_data_in_t* pChgOilInfoData;
} ATTRIBUTE_PACK;

struct atg_getcapacity_in_t
{
   int uCount;
   int* uOilCanNo;
} ATTRIBUTE_PACK;

struct atg_capacitytable_data_in_t
{
   int uHigh;
   double fLiter;
} ATTRIBUTE_PACK;

struct atg_capacity_data_in_t
{
   int uOilCanNo;
   int uCapacitySize;
   struct atg_capacitytable_data_in_t* pCapacityTableData;
} ATTRIBUTE_PACK;

struct atg_getcapacity_out_t
{
   int uCount;
   struct atg_capacity_data_in_t* pCapacityData;
} ATTRIBUTE_PACK;


//容积表设置下发到液位仪
struct atg_setcapacity_in_t
{
   int uCount;
   struct atg_capacity_data_in_t* pCapacityData;
} ATTRIBUTE_PACK;

//启动静态液位异常报警
struct atg_startliquid_data_in_t
{
   int uOilCanNo;
   int uTestType;
   char strDateTime[SZ_DATETIME14 + 1];
   int uTestDuration;
   double fTestRate;
} ATTRIBUTE_PACK;

struct atg_startliquid_in_t
{
   int uCount;
   struct atg_startliquid_data_in_t* pLiquidData;
} ATTRIBUTE_PACK;

//停止静态液位异常
struct atg_stopliquid_data_in_t
{
   int uOilCanNo;
   int uTestType;
} ATTRIBUTE_PACK;

struct atg_stopliquid_in_t
{
   int uCount;
   struct atg_stopliquid_data_in_t* pLiquidData;
} ATTRIBUTE_PACK;


//液位异常报告数据
struct atg_liquidreport_data_out_t
{
   int uOilCanNo;
   int uRevealStatus;
   double fRevealRate;
   char strStartDate[SZ_DATE8 + 1];
   char strStartTime[SZ_DATE8 + 1];
   double fStartOilHeight;
   double fStartWaterHieght;
   double fStartOilTemp;
   double fStartOilTemp1;
   double fStartOilTemp2;
   double fStartOilTemp3;
   double fStartOilTemp4;
   double fStartOilTemp5;
   double fStartOilCubage;
   double fStartOilStandOilCubage;
   double fStartEmptyCubage;
   double fStartWaterBulk;
   char strEndDate[SZ_DATE8 + 1];
   char strEndTime[SZ_DATE8 + 1];
   double fEndOilHeight;
   double fEndWaterHieght;
   double fEndOilTemp;
   double fEndOilTemp1;
   double fEndOilTemp2;
   double fEndOilTemp3;
   double fEndOilTemp4;
   double fEndOilTemp5;
   double fEndOilCubage;
   double fEndOilStandOilCubage;
   double fEndEmptyCubage;
   double fEndWaterBulk;
} ATTRIBUTE_PACK;

typedef struct atg_stopliquid_out_t
{
   int uRetCount;
   struct atg_liquidreport_data_out_t*  pLiquidData;
} ATTRIBUTE_PACK;


//静态液位异常报告
struct atg_liquidreport_data_in_t
{
   int uOilCanNo;
   char strDateTime[SZ_DATETIME14 + 1];
   int uTestType;
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_liquidreport_in_t
{
   int uCount;
   struct atg_liquidreport_data_in_t* pLiquidData;
} ATTRIBUTE_PACK;

struct atg_liquid_data_out_t
{
   int uRetCount;
   struct atg_liquidreport_data_out_t*  pLiquidData;
};


//获取设备信息
struct atg_device_in_t
{
   int uCount;
   struct atg_oilcan_data_in_t* pOilCanData;
} ATTRIBUTE_PACK;

struct atg_device_data_out_t
{
   int uOilCanNo;
   char strDeviceModel[SZ_DEVICEMODEL + 1];
   char strProbeNo[SZ_PROBEMODEL + 1];
   char strSysVersion[SZ_VERSION + 1];
   char strMakeDate[SZ_DATETIME14 + 1];
} ATTRIBUTE_PACK;

struct atg_device_out_t
{
   int uCount;
   struct atg_device_data_out_t* pDeviceData;
} ATTRIBUTE_PACK;


//高升转换
struct atg_hightoliter_in_t
{
   int uCount;
   struct atg_capacity_data_in_t* pCapacityData;
   double fTotalHeight;
   double fWaterHeight;
   double fOilTemp;
   double fOilTemp1;
   double fOilTemp2;
   double fOilTemp3;
   double fOilTemp4;
   double fOilTemp5;
} ATTRIBUTE_PACK;

struct atg_hightoliter_data_out_t
{
   int uOilCanNo;
   double fOilCubage;
   double fOilStandCubage;
   double fEmptyCubage;
   double fWaterBulk;
} ATTRIBUTE_PACK;

struct atg_hightoliter_out_t
{
   int uRetCount;
   struct atg_hightoliter_data_out_t* pHighToLiterData;
} ATTRIBUTE_PACK;


//液位仪开关机记录
struct atg_powerrecord_in_t
{
   char strDateTime[SZ_DATETIME14 + 1];
   int uReqCount;
} ATTRIBUTE_PACK;

struct atg_powerrecord_data_out_t
{
   char strDate[SZ_DATE8 + 1];
   char strTime[SZ_TIME6 + 1];
   int uOperateType;
   int uOilCanNo;
   double fTotalHeight;
   double fWaterHeight;
   double fOilTemp;
   double fOilTemp1;
   double fOilTemp2;
   double fOilTemp3;
   double fOilTemp4;
   double fOilTemp5;
   double fOilCuabge;
   double fOilStandCuabge;
   double fEmptyCuabge;
   double fWaterBulk;
} ATTRIBUTE_PACK;

static const char* ATG_OPE_GETSTOCK         = "01";
static const char* ATG_OPE_GETTIMESTOCK     = "02";
static const char* ATG_OPE_GETOILIN         = "03";
static const char* ATG_OPE_GETALARM         = "04";
static const char* ATG_OPE_GETFAILURE       = "05";
static const char* ATG_OPE_CHECKTIME        = "06";
static const char* ATG_OPE_ALARMSETTER      = "07";
static const char* ATG_OPE_CHGOILINFO       = "08";
static const char* ATG_OPE_GETCAPACITYTABLE = "09";
static const char* ATG_OPE_SETCAPACITYTABLE = "10";
static const char* ATG_OPE_STARTLIQUID      = "11";
static const char* ATG_OPE_STOPLIQUID       = "12";
static const char* ATG_OPE_LIQUIDREPORT     = "13";
static const char* ATG_OPE_GETDEVICEINFO    = "14";
static const char* ATG_OPE_HIGHTOLITER      = "15";
static const char* ATG_OPE_SETCORRECTION    = "16";
static const char* ATG_OPE_SETPROBE         = "17";
static const char* ATG_OPE_GETPOWERRECORD   = "18";

#ifndef DYNAMIC_LIBRARY
   #ifdef __cplusplus
      extern "C" int (*atg_init)(const void *pInputData);
      extern "C" int (*atg_param)(const char *strOpeCode, const void *pInputData);
      extern "C" int (*atg_operate)(const char *strOpeCode, const void *pInputData, void *pOutputData);
      extern "C" int (*atg_clear)(void);
   #else
      int (*atg_init)(const void *pInputData);
      int (*atg_param)(const char *strOpeCode, const void *pInputData);
      int (*atg_operate)(const char *strOpeCode, const void *pInputData, void *pOutputData);
      int (*atg_clear)(void);
   #endif
#else 
   #ifdef __cplusplus
      extern "C" int atg_init(const void *pInputData);
      extern "C" int atg_param(const char *strOpeCode, const void *pInputData);
      extern "C" int atg_operate(const char *strOpeCode, const void *pInputData, void *pOutputData);
      extern "C" int atg_clear(void);
   #else
      int atg_init(const void *pInputData);
      int atg_param(const char *strOpeCode, const void *pInputData);
      int atg_operate(const char *strOpeCode, const void *pInputData, void *pOutputData);
      int atg_clear(void);
   #endif
#endif

#endif

