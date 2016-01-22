/********************************************************************************************************
 *石化盈科北京分公司
 *
 *2015-11-09:
 *1.探棒油罐配置输入新增油品名称参数
 *2.删除油罐油品变类的应用类；删除油罐油品变类接口方法，其功能由探棒油罐配置接口方法实现
 ********************************************************************************************************
 
 *2015-11-13:
 *1.修改采集方法原型：int atg_operate(const char *strOpeCode, void *pInputData, void **pOutputData)
 ********************************************************************************************************/
 
#ifndef __ATGINTERFACE_H__
#define __ATGINTERFACE_H__

#define MAX_OILCAN                         16                /*最多油罐数                                */
#define MAX_ARRAYSIZE                      16                /*最大数组长度                              */
#define MAX_REMARK                         256               /*备注最大长度                              */
#define MAX_REPORT                         1024              /*报警报告最大长度                          */
#define MAX_CAPACITYHIGH                   1024              /*容积表高度数                              */
#define SZ_DATE8                           8                 /*日期型数据的长度，格式为"YYYYMMDD"        */
#define SZ_TIME6                           6                 /*时间型数据长度，格式为"hhmmss"            */
#define SZ_DATETIME14                      14                /*日期时间型数据长度，格式为"YYYYMMDDhhmmss"*/
#define SZ_ALARMTYPE                       4                 /*报警类型长度                              */
#define SZ_ALARMSTATE                      4                 /*报警状态长度                              */
#define SZ_EQUIPCODE                       64                /*设备代码长度                              */
#define SZ_FAILCODE                        8                 /*故障信息代码长度                          */
#define SZ_EQUIPBRAND                      64                /*设备品牌                                  */
#define SZ_DEVICETYPE                      2                 /*设备类型                                  */
#define SZ_PROBEMODEL                      64                /*探棒型号                                  */
#define SZ_OILTYPE                         4                 /*油品类型                                  */
#define SZ_OILNO                           8                 /*油品编码长度                              */
#define SZ_OILNAME                         64                /*油品名称（最长32个汉字）                  */
#define SZ_DEVICEMODEL                     32                /*设备型号长度                              */
#define SZ_SERIALADDRESS                   32                /*串口地址长度                              */
#define SZ_SERIALBAUDRATE                  8                 /*波特率                                    */
#define SZ_SERIALSTOPBIT                   1                 /*停止位                                    */
#define SZ_SERIALCHECKBIT                  1                 /*检验位                                    */
#define SZ_SERIALDATABIT                   1                 /*数据位                                    */
#define SZ_IPADDRESS                       32                /*IP地址                                    */
#define SZ_IPPORT                          8                 /*IP端口                                    */
#define SZ_PATH                            128               /*目录长度                                  */
#define SZ_VERSION                         16                /*版本号长度                                */
#define SZ_SYSVERSION					32				/*系统版本号长度*/

#define ATG_ERROR_CONFIG_INVALID           10000             /*无效的配置信息                            */
#define ATG_ERROR_CONNECT                  10001             /*液位仪连接失败                            */
#define ATG_ERROR_OPECODE_INVALID          10002             /*无效的应用类型                            */
#define ATG_ERROR_OPECODE                  10003             /*液位仪采集失败                            */
#define ATG_ERROR_OPECODE_CHECKTIME        10004             /*液位仪校时失败                            */
#define ATG_ERROR_OPECODE_ALARMSETTER      10005             /*液位仪高低位报警设置失败                  */
#define ATG_ERROR_OPECODE_CHGOILINFO       10006             /*液位仪油罐变类失败                        */
#define ATG_ERROR_OPECODE_SETCAPACITYTABLE 10007             /*设置液位仪容积表失败                      */
#define ATG_ERROR_OPECODE_GETCAPACITYTABLE 10008             /*读取液位仪容积表失败                      */
#define ATG_ERROR_OPECODE_STARTLIQUID      10009             /*启动静态液位异常报警失败                  */
#define ATG_ERROR_OPECODE_STOPLIQUID       10010             /*停止静态液位异常报警失败                  */
#define ATG_ERROR_OPECODE_GETDEVICEINFO    10012             /*获取液位仪设备信息失败                    */
#define ATG_ERROR_OPECODE_PARAMINVALID     10013             /*无效的输入参数                            */  
#define ATG_ERROR_OPERATE_INVALID          10100             /*液位仪设备不支持功能应用                  */                                   

#ifndef ATTRIBUTE_PACK                                       /*定义内存对齐方式                          */
   #define ATTRIBUTE_PACK __attribute__((packed))
#endif

struct atg_init_in_t
{
   char strDeviceType[SZ_DEVICEMODEL+ 1];                    /*设备型号                                  */
   int uConnMode;                                            /*通讯模式：0：串口1：网口(TCP/IP)          */
   char strSerialAddress[SZ_SERIALADDRESS + 1];              /*串口地址,例如：/dev/ttys0                 */
   char strSerialBaudRate[SZ_SERIALBAUDRATE + 1];            /*波特率                                    */
   char strSerialStopBit[SZ_SERIALSTOPBIT + 1];              /*停止位                                    */
   char strSerialCheckBit[SZ_SERIALCHECKBIT + 1];            /*检验位                                    */
   char strSerialDataBit[SZ_SERIALDATABIT + 1];              /*数据位                                    */
   char strIPAddress[SZ_IPADDRESS + 1];                      /*IP地址,例如：192.168.0.123                */
   char strIPPort[SZ_IPPORT + 1];                            /*IP端口                                    */
   char strLogPath[SZ_PATH + 1];                             /*日志目录,不包日志文件名。                 */
} ATTRIBUTE_PACK;

/*探棒校正参数设置*/
struct atg_correction_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];                  /*设备型号                                 */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*探棒编号                                 */
   char strOilType[SZ_OILTYPE+1];                            /*油品类型                                 */
   double fOilCorrection;                                    /*油位0点校正（毫米）                      */
   double fWaterCorrection;                                  /*水位0点校正（毫米）                      */
   double fProbeOffset;                                      /*探棒偏移（毫米）                         */
   double fTiltOffset;                                       /*倾斜偏移（毫米）                         */
   double fTemp1;                                            /*温度1实测值(℃)                          */
   double fProbeTemp1;                                       /*温度1探棒测量值(℃)                      */
   double fTemp2;                                            /*温度2实测值(℃)                          */
   double fProbeTemp2;                                       /*温度2探棒测量值(℃)                      */
   double fTemp3;                                            /*温度3实测值(℃)                          */
   double fProbeTemp3;                                       /*温度3探棒测量值(℃)                      */
   double fTemp4;                                            /*温度4实测值(℃)                          */
   double fProbeTemp4;                                       /*温度4探棒测量值(℃)                      */
   double fTemp5;                                            /*温度5实测值(℃)                          */
   double fProbeTemp5;                                       /*温度5探棒测量值(℃)                      */
   double fInitDensity;                                      /*初始密度(kg/m3)                          */
   double fInitHighDiff;                                     /*初始高度差(mm),油位与密度位之间的高度差  */
   double fCorrectionFactor;                                 /*密度的修正系数                           */
} ATTRIBUTE_PACK;

struct atg_correction_in_t                                   
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_correction_data_in_t* pCorrectionData;         /*输入结构数组                             */
} ATTRIBUTE_PACK;


/*探棒油罐配置*/
struct atg_probecan_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];                  /*设备型号                                 */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*探棒编号                                 */
   int uProbePort;                                           /*探棒端口(范围：1-256)                    */
   int uOilCanNo;                                            /*油罐编号                                 */
   char strOilType[SZ_OILTYPE+1];                            /*油品类型                                 */
   char strOilNo[SZ_OILNO + 1];                              /*油品编码                                 */
   char strOilName[SZ_OILNAME + 1];                          /*油品名称(最长32个汉字)                   */
} ATTRIBUTE_PACK;

struct atg_probecan_in_t
{
   int uCount;                                               /*输入结构数组大小                         */  
   struct atg_probecan_data_in_t *pProbeCanData;           /*输入结构数组                             */ 
} ATTRIBUTE_PACK;

/*实时库存采集*/
struct atg_oilcan_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
} ATTRIBUTE_PACK;

struct atg_stock_in_t
{
   int uCount;                                               /*油罐编号数组大小                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*油罐编号                                 */
} ATTRIBUTE_PACK;

struct atg_stock_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDate[SZ_DATE8 + 1];                               /*日期                                     */
   char strTime[SZ_TIME6 + 1];                               /*时间                                     */
   double fOilCubage;                                        /*净油体积，单位：升                       */
   double fOilStandCubage;                                   /*标准体积，单位：升                       */
   double fEmptyCubage;                                      /*空体积  ，单位：升                       */
   double fTotalHeight;                                      /*油水总高，单位：毫米                     */
   double fWaterHeight;                                      /*水高    ，单位：毫米                     */
   double fOilTemp;                                          /*平均温度，单位：摄氏度                   */
   double fOilTemp1;                                         /*5点温度                                  */
   double fOilTemp2;                                         /*5点温度                                  */
   double fOilTemp3;                                         /*5点温度                                  */
   double fOilTemp4;                                         /*5点温度                                  */
   double fOilTemp5;                                         /*5点温度                                  */
   double fWaterBulk;                                        /*水体积，单位：升                         */
   double fApparentDensity;                                  /*视密度，单位：克/毫升                    */
   double fStandDensity;                                     /*标准密度，单位：克/毫升                  */
} ATTRIBUTE_PACK;

struct atg_stock_out_t                                       
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_stock_data_out_t* pStockData;                  /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*整点库存采集*/
struct atg_timestock_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_timestock_in_t
{                                                            
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_timestock_data_in_t* pTimeStockData;           /*输入结构数组                             */
} ATTRIBUTE_PACK;

/*进油信息采集*/
struct atg_oilin_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_oilin_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_oilin_data_in_t* pOilInData;                   /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_oilin_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strStartDate[SZ_DATE8 + 1];                          /*开始日期                                 */
   char strEndDate[SZ_DATE8 + 1];                            /*结束日期                                 */
   char strStartTime[SZ_TIME6 + 1];                          /*开始时间                                 */
   char strEndTime[SZ_TIME6 + 1];                            /*结束时间                                 */
   double fStartCubage;                                      /*初始净油体积，单位：升                   */
   double fStartStandCubage;                                 /*初始标准体积，单位：升                   */
   double fStartOilHeight;                                   /*初始油水总高，单位：毫米                 */
   double fStartWaterHeight;                                 /*初始水高，单位：毫米                     */
   double fStartOilTemp;                                     /*初始油温，单位：摄氏度                   */
   double fStartOilTemp1;                                    /*初始5点温度                              */
   double fStartOilTemp2;                                    /*初始5点温度                              */
   double fStartOilTemp3;                                    /*初始5点温度                              */
   double fStartOilTemp4;                                    /*初始5点温度                              */
   double fStartOilTemp5;                                    /*初始5点温度                              */
   double fEndCubage;                                        /*结束体积，单位：升                       */
   double fEndStandCubage;                                   /*结束标准体积，单位：升                   */
   double fEndOilHeight;                                     /*结束油水总高，单位：毫米                 */
   double fEndWaterHeight;                                   /*结束水高，单位：毫米                     */
   double fEndOilTemp;                                       /*结束油温，单位：摄氏度                   */
   double fEndOilTemp1;                                      /*结束5点温度                              */
   double fEndOilTemp2;                                      /*结束5点温度                              */
   double fEndOilTemp3;                                      /*结束5点温度                              */
   double fEndOilTemp4;                                      /*结束5点温度                              */
   double fEndOilTemp5;                                      /*结束5点温度                              */
   double fEmptyCubage;                                      /*结束空容，单位：升                       */
   double fApparentDensity;                                  /*视密度，单位：克/毫升                    */
   double fStandDensity;                                     /*标准密度，单位：克/毫升                  */
} ATTRIBUTE_PACK;

struct atg_oilin_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_oilin_data_out_t* pOilinData;                  /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*油罐报警采集*/
struct atg_alarm_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_alarm_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_alarm_data_in_t* pAlarmData;                   /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_alarm_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDate[SZ_DATE8 + 1];                               /*日期                                     */
   char strTime[SZ_TIME6 + 1];                               /*时间                                     */
   char strAlarmType[SZ_ALARMTYPE + 1];                      /*报警类型                                 */
   char strAlarmState[SZ_ALARMSTATE + 1];                    /*报警状态                                 */
   char strRemark[MAX_REMARK + 1];                           /*报警描述                                 */
   char strReport[MAX_REPORT + 1];                           /*报警详细报告                             */
} ATTRIBUTE_PACK;

struct atg_alarm_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_alarm_data_out_t* pAlarmData;                  /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*设备故障采集*/
struct atg_failure_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_failure_in_t
{
   int uCount;                                               /*输入结构数组大小                         */ 
   struct atg_failure_data_in_t* pFailureData;               /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_failure_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDate[SZ_DATE8 + 1];                               /*日期                                     */
   char strTime[SZ_TIME6 + 1];                               /*时间                                     */
   char strAlarmState[SZ_ALARMSTATE+1];				/*报警状态                                */
   char strDeviceType[SZ_DEVICETYPE + 1];                    /*设备类型                                 */
   char strFailureType[SZ_ALARMTYPE + 1];                    /*故障类型                                 */
   char strEquipCode[SZ_EQUIPCODE + 1];                      /*设备代码                                 */
   char strFailCode[SZ_FAILCODE + 1];                        /*故障信息代码                             */
   char strEquipBrand[SZ_EQUIPBRAND + 1];                    /*设备品牌                                 */
   char strProbeModel[SZ_PROBEMODEL + 1];                    /*探棒型号                                 */
   char strRemark[MAX_REMARK + 1];                           /*故障描述                                 */
} ATTRIBUTE_PACK;

struct atg_failure_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_failure_data_out_t* pFailureData;              /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*预报警设置*/
struct atg_setalarm_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */ 
   double fLowWarning;                                       /*低液位预警,单位：毫米                    */
   double fLowAlarm;                                         /*低液位报警,单位：毫米                    */
   double fHighWarning;                                      /*高液位预警,单位：毫米                    */
   double fHighAlarm;                                        /*高液位报警,单位：毫米                    */
   double fWaterWarning;									 /*高水位预警,单位：毫米					*/
   double fWaterAlarm;                                       /*高水位报警，,单位：毫米                  */
   double fThiefAlarm;                                       /*盗油报警,单位：升/小时，默认300L/H       */
   double fLeakAlarm;                                        /*漏油报警,单位：升/小时，默认60L/H        */
   double fPercolatingAlarm;                                /*渗漏报警,单位：升/小时，默认0.8L/H       */
   double fHighTempAlarm;                                    /*高温报警，单位：摄氏度。温度>=55         */
   double fLowTempAlarm;                                     /*低温报警，单位：摄氏度。温度<=-10        */
} ATTRIBUTE_PACK;

struct atg_setalarm_in_t
{
   int uReqCount;                                            /*输入结构数组大小                         */
   struct atg_setalarm_data_in_t* pAlarmSetterData;          /*输入结构数组                             */
} ATTRIBUTE_PACK;

/*容积表(全罐表)上传*/
struct atg_getcapacity_in_t
{
   int uCount;                                               /*油罐编号数组大小                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*油罐编号                                 */
} ATTRIBUTE_PACK;

struct atg_capacitytable_data_in_t
{
   int uHigh;                                                /*高度,单位：毫米，整数                    */
   double fLiter;                                            /*升数,单位：升，整数                      */
} ATTRIBUTE_PACK;

struct atg_capacity_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strVersion[SZ_VERSION+1];               /*版本号                                    */
   int uCapacitySize;                                        /*容积表刻度数                             */
   struct atg_capacitytable_data_in_t* pCapacityTableData;   /*容积表数据结构                           */
} ATTRIBUTE_PACK;

struct atg_getcapacity_out_t
{
   int uCount;                                               /*输出数组大小                             */
   struct atg_capacity_data_in_t* pCapacityData;             /*输出结构数组                             */
} ATTRIBUTE_PACK;
                          
/*容积表(全罐表)下发*/
struct atg_setcapacity_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_capacity_data_in_t* pCapacityData;             /*输入结构数组                             */
} ATTRIBUTE_PACK;

/*启动静态液位异常测试*/
struct atg_startliquid_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                */
   int uTestType;                                            /*检测类型,0:静态异常检测                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*测漏启动时间                            */
   int uTestDuration;                                        /*测试持续时间(2-24小时)                  */
   double fTestRate;                                         /*测试的速率,0.76升/小时；0.38升/小时     */
} ATTRIBUTE_PACK;

struct atg_startliquid_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_startliquid_data_in_t* pLiquidData;            /*输入结构数组                             */
} ATTRIBUTE_PACK;

/*停止静态液位异常测试*/
struct atg_stopliquid_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                */
   int uTestType;                                            /*检测类型,0:静态异常检测                 */
} ATTRIBUTE_PACK;

struct atg_stopliquid_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_stopliquid_data_in_t* pLiquidData;             /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_liquidreport_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   int uRevealStatus;                                        /*泄漏状态,0:不泄漏,1:渗漏,2:漏油,3:盗油   */
   double fRevealRate;                                       /*泄漏速率,单位:升/每小时                  */
   char strStartDate[SZ_DATE8 + 1];                          /*起始日期                                 */
   char strStartTime[SZ_TIME6 + 1];                          /*起始时间                                 */
   double fStartOilHeight;                                   /*起始油水总高                             */
   double fStartWaterHeight;                                 /*起始水高                                 */
   double fStartOilTemp;                                     /*起始平均温度，单位：摄氏度               */
   double fStartOilTemp1;                                    /*起始5点温度                              */
   double fStartOilTemp2;                                    /*起始5点温度                              */
   double fStartOilTemp3;                                    /*起始5点温度                              */
   double fStartOilTemp4;                                    /*起始5点温度                              */
   double fStartOilTemp5;                                    /*起始5点温度                              */
   double fStartOilCubage;                                   /*起始净油体积，单位：升                   */
   double fStartOilStandCubage;                           /*起始标准体积，单位：升                   */
   double fStartEmptyCubage;                                 /*起始空体积  ，单位：升                   */
   double fStartWaterBulk;                                   /*起始水体积  ，单位：升                   */
   char strEndDate[SZ_DATE8 + 1];                            /*结束日期                                 */
   char strEndTime[SZ_TIME6 + 1];                            /*结束时间                                 */
   double fEndOilHeight;                                     /*结束油水总高                             */
   double fEndWaterHeight;                                   /*结束水高                                 */
   double fEndOilTemp;                                       /*结束平均温度，单位：摄氏度               */
   double fEndOilTemp1;                                      /*结束5点温度                              */
   double fEndOilTemp2;                                      /*结束5点温度                              */
   double fEndOilTemp3;                                      /*结束5点温度                              */
   double fEndOilTemp4;                                      /*结束5点温度                              */
   double fEndOilTemp5;                                      /*结束5点温度                              */
   double fEndOilCubage;                                     /*结束净油体积，单位：升                   */
   double fEndOilStandCubage;                             /*结束标准体积，单位：升                   */
   double fEndEmptyCubage;                                   /*结束空体积  ，单位：升                   */
   double fEndWaterBulk;                                     /*结束水体积  ，单位：升                   */
} ATTRIBUTE_PACK;

struct atg_stopliquid_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_liquidreport_data_out_t* pLiquidData;          /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*静态液位异常测试报告*/
struct atg_liquidreport_data_in_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uTestType;                                            /*检测类型,0:静态异常检测                  */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_liquidreport_in_t
{
   int uCount;                                               /*输入结构数组大小                         */ 
   struct atg_liquidreport_data_in_t* pLiquidData;           /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_liquidreport_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_liquidreport_data_out_t*  pLiquidData;         /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*设备基础信息*/
struct atg_device_in_t
{
   int uCount;                                               /*输入结构数组大小                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*输入结构数组                             */
} ATTRIBUTE_PACK;

struct atg_device_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*探棒序列号                              */
   char strProbeModel[SZ_DEVICEMODEL+1];                     /*探棒型号                                */
} ATTRIBUTE_PACK;

struct atg_device_out_t
{
   char strDeviceModel[SZ_DEVICEMODEL+1];                    /*产品型号                                 */
   char strEquipCode[SZ_EQUIPCODE + 1];                      /*设备代码                                 */
   char strSysVersion[SZ_SYSVERSION + 1];                         /*系统版本                                 */
   char strMakeDate[SZ_DATETIME14+1];                        /*制造日期                                 */
   int uRetCount;                                               /*返回输出结构数组大小                     */
   struct atg_device_data_out_t* pDeviceData;                /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*高升转换*/
struct atg_hightoliter_in_t
{
   int uCount;                                               /*全罐容积表数组大小                       */
   struct atg_capacity_data_in_t* pCapacityData;             /*输入结构数组,全罐容积表                  */
   double fTotalHeight;                                      /*油水总高，单位：毫米                     */
   double fWaterHeight;                                      /*水高    ，单位：毫米                     */
   double fOilTemp;                                          /*平均温度，单位：摄氏度                   */
   double fOilTemp1;                                         /*5点温度                                  */
   double fOilTemp2;                                         /*5点温度                                  */
   double fOilTemp3;                                         /*5点温度                                  */
   double fOilTemp4;                                         /*5点温度                                  */
   double fOilTemp5;                                         /*5点温度                                  */
} ATTRIBUTE_PACK;

struct atg_hightolite_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_hightoliter_data_out_t*  pHighToLiterData;     /*输出结构数组                             */
} ATTRIBUTE_PACK;

struct atg_hightoliter_data_out_t
{
   int uOilCanNo;                                            /*油罐编号                                 */
   double fOilCubage;                                        /*净油体积，单位：升                       */
   double fOilStandCubage;                                   /*标准体积，单位：升                       */
   double fEmptyCubage;                                      /*空体积  ，单位：升                       */
   double fWaterBulk;                                        /*水体积  ，单位：升                       */
} ATTRIBUTE_PACK;

struct atg_hightoliter_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_hightoliter_data_out_t* pHighToLiterData;      /*输出结构数组                             */
} ATTRIBUTE_PACK;

/*液位仪开关机记录*/
struct atg_powerrecord_in_t
{
   char strDateTime[SZ_DATETIME14 + 1];                      /*起始日期时间                             */
   int uReqCount;                                            /*请求返回笔数，默认为0                    */
} ATTRIBUTE_PACK;

struct atg_powerrecord_data_out_t
{
   char strDate[SZ_DATE8 + 1];                               /*操作日期                                 */
   char strTime[SZ_TIME6 + 1];                               /*操作时间                                 */
   int uOperateType;                                         /*操作类型：0开机；1关机                   */
   int uOilCanNo;                                            /*油罐编号                                 */
   double fTotalHeight;                                      /*油水总高，单位：毫米                     */
   double fWaterHeight;                                      /*水高    ，单位：毫米                     */
   double fOilTemp;                                          /*平均温度，单位：摄氏度                   */
   double fOilTemp1;                                         /*5点温度                                  */
   double fOilTemp2;                                         /*5点温度                                  */
   double fOilTemp3;                                         /*5点温度                                  */
   double fOilTemp4;                                         /*5点温度                                  */
   double fOilTemp5;                                         /*5点温度                                  */
   double fOilCubage;                                        /*净油体积，单位：升                       */
   double fOilStandCubage;                                   /*标准体积，单位：升                       */
   double fEmptyCubage;                                      /*空体积  ，单位：升                       */
   double fWaterBulk;                                        /*水体积  ，单位：升                       */
} ATTRIBUTE_PACK;

struct atg_powerrecord_out_t
{
   int uRetCount;                                            /*输出数组大小                             */
   struct atg_powerrecord_data_out_t* pPowerRecordData;      /*输出结构数组                             */
} ATTRIBUTE_PACK;

static const char* ATG_OPE_GETSTOCK         = "01";          /*实时库存                                 */
static const char* ATG_OPE_GETTIMESTOCK     = "02";          /*整点库存                                 */
static const char* ATG_OPE_GETOILIN         = "03";          /*进油信息                                 */
static const char* ATG_OPE_GETALARM         = "04";          /*油罐报警                                 */
static const char* ATG_OPE_GETFAILURE       = "05";          /*设备故障                                 */
static const char* ATG_OPE_CHECKTIME        = "06";          /*液位仪对时                               */
static const char* ATG_OPE_ALARMSETTER      = "07";          /*预报警设置                               */
static const char* ATG_OPE_CHGOILINFO       = "08";          /*油罐油品变类                             */
static const char* ATG_OPE_GETCAPACITYTABLE = "09";          /*容积表上传                               */
static const char* ATG_OPE_SETCAPACITYTABLE = "10";          /*容积表下发                               */
static const char* ATG_OPE_STARTLIQUID      = "11";          /*启动静态液位异常测试                     */
static const char* ATG_OPE_STOPLIQUID       = "12";          /*结束静态液位异常测试                     */
static const char* ATG_OPE_LIQUIDREPORT     = "13";          /*静态液位异常测试报告                     */
static const char* ATG_OPE_GETDEVICEINFO    = "14";          /*设备基础信息                             */
static const char* ATG_OPE_HIGHTOLITER      = "15";          /*高升转换                                 */
static const char* ATG_OPE_SETCORRECTION    = "16";          /*探棒校正参数                             */
static const char* ATG_OPE_SETPROBE         = "17";          /*探棒油罐配置                             */
static const char* ATG_OPE_GETPOWERRECORD   = "18";          /*电源箱操作记录报告                       */
/*
#ifndef DYNAMIC_LIBRARY
   #ifdef __cplusplus
      extern "C" int (*atg_init)(const void *pInputData);
      extern "C" int (*atg_param)(const char *strOpeCode, const void *pInputData);
      extern "C" int (*atg_operate)(const char *strOpeCode, const void *pInputData, void **pOutputData);
      extern "C" int (*atg_clear)(void);
   #else
      int (*atg_init)(const void *pInputData);
      int (*atg_param)(const char *strOpeCode, const void *pInputData);
      int (*atg_operate)(const char *strOpeCode, const void *pInputData, void **pOutputData);
      int (*atg_clear)(void);
   #endif
#else 
   #ifdef __cplusplus
      extern "C" int atg_init(const void *pInputData);
      extern "C" int atg_param(const char *strOpeCode, const void *pInputData);
      extern "C" int atg_operate(const char *strOpeCode, const void *pInputData, void **pOutputData);
      extern "C" int atg_clear(void);

   #else*/
      int atg_init(const void *pInputData);
      int atg_param(const char *strOpeCode, const void *pInputData);
      int atg_operate(const char *strOpeCode, const void *pInputData, void **pOutputData);
      int atg_clear(void);
   //#endif
//#endif

#endif

