/********************************************************************************************************
 *ʯ��ӯ�Ʊ����ֹ�˾
 *
 *2015-11-09:
 *1.̽���͹���������������Ʒ���Ʋ���
 *2.ɾ���͹���Ʒ�����Ӧ���ࣻɾ���͹���Ʒ����ӿڷ������书����̽���͹����ýӿڷ���ʵ��
 ********************************************************************************************************
 
 *2015-11-13:
 *1.�޸Ĳɼ�����ԭ�ͣ�int atg_operate(const char *strOpeCode, void *pInputData, void **pOutputData)
 ********************************************************************************************************/
 
#ifndef __ATGINTERFACE_H__
#define __ATGINTERFACE_H__

#define MAX_OILCAN                         16                /*����͹���                                */
#define MAX_ARRAYSIZE                      16                /*������鳤��                              */
#define MAX_REMARK                         256               /*��ע��󳤶�                              */
#define MAX_REPORT                         1024              /*����������󳤶�                          */
#define MAX_CAPACITYHIGH                   1024              /*�ݻ���߶���                              */
#define SZ_DATE8                           8                 /*���������ݵĳ��ȣ���ʽΪ"YYYYMMDD"        */
#define SZ_TIME6                           6                 /*ʱ�������ݳ��ȣ���ʽΪ"hhmmss"            */
#define SZ_DATETIME14                      14                /*����ʱ�������ݳ��ȣ���ʽΪ"YYYYMMDDhhmmss"*/
#define SZ_ALARMTYPE                       4                 /*�������ͳ���                              */
#define SZ_ALARMSTATE                      4                 /*����״̬����                              */
#define SZ_EQUIPCODE                       64                /*�豸���볤��                              */
#define SZ_FAILCODE                        8                 /*������Ϣ���볤��                          */
#define SZ_EQUIPBRAND                      64                /*�豸Ʒ��                                  */
#define SZ_DEVICETYPE                      2                 /*�豸����                                  */
#define SZ_PROBEMODEL                      64                /*̽���ͺ�                                  */
#define SZ_OILTYPE                         4                 /*��Ʒ����                                  */
#define SZ_OILNO                           8                 /*��Ʒ���볤��                              */
#define SZ_OILNAME                         64                /*��Ʒ���ƣ��32�����֣�                  */
#define SZ_DEVICEMODEL                     32                /*�豸�ͺų���                              */
#define SZ_SERIALADDRESS                   32                /*���ڵ�ַ����                              */
#define SZ_SERIALBAUDRATE                  8                 /*������                                    */
#define SZ_SERIALSTOPBIT                   1                 /*ֹͣλ                                    */
#define SZ_SERIALCHECKBIT                  1                 /*����λ                                    */
#define SZ_SERIALDATABIT                   1                 /*����λ                                    */
#define SZ_IPADDRESS                       32                /*IP��ַ                                    */
#define SZ_IPPORT                          8                 /*IP�˿�                                    */
#define SZ_PATH                            128               /*Ŀ¼����                                  */
#define SZ_VERSION                         16                /*�汾�ų���                                */
#define SZ_SYSVERSION					32				/*ϵͳ�汾�ų���*/

#define ATG_ERROR_CONFIG_INVALID           10000             /*��Ч��������Ϣ                            */
#define ATG_ERROR_CONNECT                  10001             /*Һλ������ʧ��                            */
#define ATG_ERROR_OPECODE_INVALID          10002             /*��Ч��Ӧ������                            */
#define ATG_ERROR_OPECODE                  10003             /*Һλ�ǲɼ�ʧ��                            */
#define ATG_ERROR_OPECODE_CHECKTIME        10004             /*Һλ��Уʱʧ��                            */
#define ATG_ERROR_OPECODE_ALARMSETTER      10005             /*Һλ�Ǹߵ�λ��������ʧ��                  */
#define ATG_ERROR_OPECODE_CHGOILINFO       10006             /*Һλ���͹ޱ���ʧ��                        */
#define ATG_ERROR_OPECODE_SETCAPACITYTABLE 10007             /*����Һλ���ݻ���ʧ��                      */
#define ATG_ERROR_OPECODE_GETCAPACITYTABLE 10008             /*��ȡҺλ���ݻ���ʧ��                      */
#define ATG_ERROR_OPECODE_STARTLIQUID      10009             /*������̬Һλ�쳣����ʧ��                  */
#define ATG_ERROR_OPECODE_STOPLIQUID       10010             /*ֹͣ��̬Һλ�쳣����ʧ��                  */
#define ATG_ERROR_OPECODE_GETDEVICEINFO    10012             /*��ȡҺλ���豸��Ϣʧ��                    */
#define ATG_ERROR_OPECODE_PARAMINVALID     10013             /*��Ч���������                            */  
#define ATG_ERROR_OPERATE_INVALID          10100             /*Һλ���豸��֧�ֹ���Ӧ��                  */                                   

#ifndef ATTRIBUTE_PACK                                       /*�����ڴ���뷽ʽ                          */
   #define ATTRIBUTE_PACK __attribute__((packed))
#endif

struct atg_init_in_t
{
   char strDeviceType[SZ_DEVICEMODEL+ 1];                    /*�豸�ͺ�                                  */
   int uConnMode;                                            /*ͨѶģʽ��0������1������(TCP/IP)          */
   char strSerialAddress[SZ_SERIALADDRESS + 1];              /*���ڵ�ַ,���磺/dev/ttys0                 */
   char strSerialBaudRate[SZ_SERIALBAUDRATE + 1];            /*������                                    */
   char strSerialStopBit[SZ_SERIALSTOPBIT + 1];              /*ֹͣλ                                    */
   char strSerialCheckBit[SZ_SERIALCHECKBIT + 1];            /*����λ                                    */
   char strSerialDataBit[SZ_SERIALDATABIT + 1];              /*����λ                                    */
   char strIPAddress[SZ_IPADDRESS + 1];                      /*IP��ַ,���磺192.168.0.123                */
   char strIPPort[SZ_IPPORT + 1];                            /*IP�˿�                                    */
   char strLogPath[SZ_PATH + 1];                             /*��־Ŀ¼,������־�ļ�����                 */
} ATTRIBUTE_PACK;

/*̽��У����������*/
struct atg_correction_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];                  /*�豸�ͺ�                                 */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*̽�����                                 */
   char strOilType[SZ_OILTYPE+1];                            /*��Ʒ����                                 */
   double fOilCorrection;                                    /*��λ0��У�������ף�                      */
   double fWaterCorrection;                                  /*ˮλ0��У�������ף�                      */
   double fProbeOffset;                                      /*̽��ƫ�ƣ����ף�                         */
   double fTiltOffset;                                       /*��бƫ�ƣ����ף�                         */
   double fTemp1;                                            /*�¶�1ʵ��ֵ(��)                          */
   double fProbeTemp1;                                       /*�¶�1̽������ֵ(��)                      */
   double fTemp2;                                            /*�¶�2ʵ��ֵ(��)                          */
   double fProbeTemp2;                                       /*�¶�2̽������ֵ(��)                      */
   double fTemp3;                                            /*�¶�3ʵ��ֵ(��)                          */
   double fProbeTemp3;                                       /*�¶�3̽������ֵ(��)                      */
   double fTemp4;                                            /*�¶�4ʵ��ֵ(��)                          */
   double fProbeTemp4;                                       /*�¶�4̽������ֵ(��)                      */
   double fTemp5;                                            /*�¶�5ʵ��ֵ(��)                          */
   double fProbeTemp5;                                       /*�¶�5̽������ֵ(��)                      */
   double fInitDensity;                                      /*��ʼ�ܶ�(kg/m3)                          */
   double fInitHighDiff;                                     /*��ʼ�߶Ȳ�(mm),��λ���ܶ�λ֮��ĸ߶Ȳ�  */
   double fCorrectionFactor;                                 /*�ܶȵ�����ϵ��                           */
} ATTRIBUTE_PACK;

struct atg_correction_in_t                                   
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_correction_data_in_t* pCorrectionData;         /*����ṹ����                             */
} ATTRIBUTE_PACK;


/*̽���͹�����*/
struct atg_probecan_data_in_t
{
   char strDeviceModel[SZ_DEVICEMODEL + 1];                  /*�豸�ͺ�                                 */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*̽�����                                 */
   int uProbePort;                                           /*̽���˿�(��Χ��1-256)                    */
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strOilType[SZ_OILTYPE+1];                            /*��Ʒ����                                 */
   char strOilNo[SZ_OILNO + 1];                              /*��Ʒ����                                 */
   char strOilName[SZ_OILNAME + 1];                          /*��Ʒ����(�32������)                   */
} ATTRIBUTE_PACK;

struct atg_probecan_in_t
{
   int uCount;                                               /*����ṹ�����С                         */  
   struct atg_probecan_data_in_t *pProbeCanData;           /*����ṹ����                             */ 
} ATTRIBUTE_PACK;

/*ʵʱ���ɼ�*/
struct atg_oilcan_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
} ATTRIBUTE_PACK;

struct atg_stock_in_t
{
   int uCount;                                               /*�͹ޱ�������С                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*�͹ޱ��                                 */
} ATTRIBUTE_PACK;

struct atg_stock_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDate[SZ_DATE8 + 1];                               /*����                                     */
   char strTime[SZ_TIME6 + 1];                               /*ʱ��                                     */
   double fOilCubage;                                        /*�����������λ����                       */
   double fOilStandCubage;                                   /*��׼�������λ����                       */
   double fEmptyCubage;                                      /*�����  ����λ����                       */
   double fTotalHeight;                                      /*��ˮ�ܸߣ���λ������                     */
   double fWaterHeight;                                      /*ˮ��    ����λ������                     */
   double fOilTemp;                                          /*ƽ���¶ȣ���λ�����϶�                   */
   double fOilTemp1;                                         /*5���¶�                                  */
   double fOilTemp2;                                         /*5���¶�                                  */
   double fOilTemp3;                                         /*5���¶�                                  */
   double fOilTemp4;                                         /*5���¶�                                  */
   double fOilTemp5;                                         /*5���¶�                                  */
   double fWaterBulk;                                        /*ˮ�������λ����                         */
   double fApparentDensity;                                  /*���ܶȣ���λ����/����                    */
   double fStandDensity;                                     /*��׼�ܶȣ���λ����/����                  */
} ATTRIBUTE_PACK;

struct atg_stock_out_t                                       
{
   int uRetCount;                                            /*��������С                             */
   struct atg_stock_data_out_t* pStockData;                  /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*������ɼ�*/
struct atg_timestock_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_timestock_in_t
{                                                            
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_timestock_data_in_t* pTimeStockData;           /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*������Ϣ�ɼ�*/
struct atg_oilin_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_oilin_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_oilin_data_in_t* pOilInData;                   /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_oilin_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strStartDate[SZ_DATE8 + 1];                          /*��ʼ����                                 */
   char strEndDate[SZ_DATE8 + 1];                            /*��������                                 */
   char strStartTime[SZ_TIME6 + 1];                          /*��ʼʱ��                                 */
   char strEndTime[SZ_TIME6 + 1];                            /*����ʱ��                                 */
   double fStartCubage;                                      /*��ʼ�����������λ����                   */
   double fStartStandCubage;                                 /*��ʼ��׼�������λ����                   */
   double fStartOilHeight;                                   /*��ʼ��ˮ�ܸߣ���λ������                 */
   double fStartWaterHeight;                                 /*��ʼˮ�ߣ���λ������                     */
   double fStartOilTemp;                                     /*��ʼ���£���λ�����϶�                   */
   double fStartOilTemp1;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp2;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp3;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp4;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp5;                                    /*��ʼ5���¶�                              */
   double fEndCubage;                                        /*�����������λ����                       */
   double fEndStandCubage;                                   /*������׼�������λ����                   */
   double fEndOilHeight;                                     /*������ˮ�ܸߣ���λ������                 */
   double fEndWaterHeight;                                   /*����ˮ�ߣ���λ������                     */
   double fEndOilTemp;                                       /*�������£���λ�����϶�                   */
   double fEndOilTemp1;                                      /*����5���¶�                              */
   double fEndOilTemp2;                                      /*����5���¶�                              */
   double fEndOilTemp3;                                      /*����5���¶�                              */
   double fEndOilTemp4;                                      /*����5���¶�                              */
   double fEndOilTemp5;                                      /*����5���¶�                              */
   double fEmptyCubage;                                      /*�������ݣ���λ����                       */
   double fApparentDensity;                                  /*���ܶȣ���λ����/����                    */
   double fStandDensity;                                     /*��׼�ܶȣ���λ����/����                  */
} ATTRIBUTE_PACK;

struct atg_oilin_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_oilin_data_out_t* pOilinData;                  /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*�͹ޱ����ɼ�*/
struct atg_alarm_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_alarm_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_alarm_data_in_t* pAlarmData;                   /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_alarm_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDate[SZ_DATE8 + 1];                               /*����                                     */
   char strTime[SZ_TIME6 + 1];                               /*ʱ��                                     */
   char strAlarmType[SZ_ALARMTYPE + 1];                      /*��������                                 */
   char strAlarmState[SZ_ALARMSTATE + 1];                    /*����״̬                                 */
   char strRemark[MAX_REMARK + 1];                           /*��������                                 */
   char strReport[MAX_REPORT + 1];                           /*������ϸ����                             */
} ATTRIBUTE_PACK;

struct atg_alarm_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_alarm_data_out_t* pAlarmData;                  /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*�豸���ϲɼ�*/
struct atg_failure_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_failure_in_t
{
   int uCount;                                               /*����ṹ�����С                         */ 
   struct atg_failure_data_in_t* pFailureData;               /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_failure_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDate[SZ_DATE8 + 1];                               /*����                                     */
   char strTime[SZ_TIME6 + 1];                               /*ʱ��                                     */
   char strAlarmState[SZ_ALARMSTATE+1];				/*����״̬                                */
   char strDeviceType[SZ_DEVICETYPE + 1];                    /*�豸����                                 */
   char strFailureType[SZ_ALARMTYPE + 1];                    /*��������                                 */
   char strEquipCode[SZ_EQUIPCODE + 1];                      /*�豸����                                 */
   char strFailCode[SZ_FAILCODE + 1];                        /*������Ϣ����                             */
   char strEquipBrand[SZ_EQUIPBRAND + 1];                    /*�豸Ʒ��                                 */
   char strProbeModel[SZ_PROBEMODEL + 1];                    /*̽���ͺ�                                 */
   char strRemark[MAX_REMARK + 1];                           /*��������                                 */
} ATTRIBUTE_PACK;

struct atg_failure_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_failure_data_out_t* pFailureData;              /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*Ԥ��������*/
struct atg_setalarm_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */ 
   double fLowWarning;                                       /*��ҺλԤ��,��λ������                    */
   double fLowAlarm;                                         /*��Һλ����,��λ������                    */
   double fHighWarning;                                      /*��ҺλԤ��,��λ������                    */
   double fHighAlarm;                                        /*��Һλ����,��λ������                    */
   double fWaterWarning;									 /*��ˮλԤ��,��λ������					*/
   double fWaterAlarm;                                       /*��ˮλ������,��λ������                  */
   double fThiefAlarm;                                       /*���ͱ���,��λ����/Сʱ��Ĭ��300L/H       */
   double fLeakAlarm;                                        /*©�ͱ���,��λ����/Сʱ��Ĭ��60L/H        */
   double fPercolatingAlarm;                                /*��©����,��λ����/Сʱ��Ĭ��0.8L/H       */
   double fHighTempAlarm;                                    /*���±�������λ�����϶ȡ��¶�>=55         */
   double fLowTempAlarm;                                     /*���±�������λ�����϶ȡ��¶�<=-10        */
} ATTRIBUTE_PACK;

struct atg_setalarm_in_t
{
   int uReqCount;                                            /*����ṹ�����С                         */
   struct atg_setalarm_data_in_t* pAlarmSetterData;          /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*�ݻ���(ȫ�ޱ�)�ϴ�*/
struct atg_getcapacity_in_t
{
   int uCount;                                               /*�͹ޱ�������С                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*�͹ޱ��                                 */
} ATTRIBUTE_PACK;

struct atg_capacitytable_data_in_t
{
   int uHigh;                                                /*�߶�,��λ�����ף�����                    */
   double fLiter;                                            /*����,��λ����������                      */
} ATTRIBUTE_PACK;

struct atg_capacity_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strVersion[SZ_VERSION+1];               /*�汾��                                    */
   int uCapacitySize;                                        /*�ݻ���̶���                             */
   struct atg_capacitytable_data_in_t* pCapacityTableData;   /*�ݻ������ݽṹ                           */
} ATTRIBUTE_PACK;

struct atg_getcapacity_out_t
{
   int uCount;                                               /*��������С                             */
   struct atg_capacity_data_in_t* pCapacityData;             /*����ṹ����                             */
} ATTRIBUTE_PACK;
                          
/*�ݻ���(ȫ�ޱ�)�·�*/
struct atg_setcapacity_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_capacity_data_in_t* pCapacityData;             /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*������̬Һλ�쳣����*/
struct atg_startliquid_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                */
   int uTestType;                                            /*�������,0:��̬�쳣���                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��©����ʱ��                            */
   int uTestDuration;                                        /*���Գ���ʱ��(2-24Сʱ)                  */
   double fTestRate;                                         /*���Ե�����,0.76��/Сʱ��0.38��/Сʱ     */
} ATTRIBUTE_PACK;

struct atg_startliquid_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_startliquid_data_in_t* pLiquidData;            /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*ֹͣ��̬Һλ�쳣����*/
struct atg_stopliquid_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                */
   int uTestType;                                            /*�������,0:��̬�쳣���                 */
} ATTRIBUTE_PACK;

struct atg_stopliquid_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_stopliquid_data_in_t* pLiquidData;             /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_liquidreport_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   int uRevealStatus;                                        /*й©״̬,0:��й©,1:��©,2:©��,3:����   */
   double fRevealRate;                                       /*й©����,��λ:��/ÿСʱ                  */
   char strStartDate[SZ_DATE8 + 1];                          /*��ʼ����                                 */
   char strStartTime[SZ_TIME6 + 1];                          /*��ʼʱ��                                 */
   double fStartOilHeight;                                   /*��ʼ��ˮ�ܸ�                             */
   double fStartWaterHeight;                                 /*��ʼˮ��                                 */
   double fStartOilTemp;                                     /*��ʼƽ���¶ȣ���λ�����϶�               */
   double fStartOilTemp1;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp2;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp3;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp4;                                    /*��ʼ5���¶�                              */
   double fStartOilTemp5;                                    /*��ʼ5���¶�                              */
   double fStartOilCubage;                                   /*��ʼ�����������λ����                   */
   double fStartOilStandCubage;                           /*��ʼ��׼�������λ����                   */
   double fStartEmptyCubage;                                 /*��ʼ�����  ����λ����                   */
   double fStartWaterBulk;                                   /*��ʼˮ���  ����λ����                   */
   char strEndDate[SZ_DATE8 + 1];                            /*��������                                 */
   char strEndTime[SZ_TIME6 + 1];                            /*����ʱ��                                 */
   double fEndOilHeight;                                     /*������ˮ�ܸ�                             */
   double fEndWaterHeight;                                   /*����ˮ��                                 */
   double fEndOilTemp;                                       /*����ƽ���¶ȣ���λ�����϶�               */
   double fEndOilTemp1;                                      /*����5���¶�                              */
   double fEndOilTemp2;                                      /*����5���¶�                              */
   double fEndOilTemp3;                                      /*����5���¶�                              */
   double fEndOilTemp4;                                      /*����5���¶�                              */
   double fEndOilTemp5;                                      /*����5���¶�                              */
   double fEndOilCubage;                                     /*���������������λ����                   */
   double fEndOilStandCubage;                             /*������׼�������λ����                   */
   double fEndEmptyCubage;                                   /*���������  ����λ����                   */
   double fEndWaterBulk;                                     /*����ˮ���  ����λ����                   */
} ATTRIBUTE_PACK;

struct atg_stopliquid_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_liquidreport_data_out_t* pLiquidData;          /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*��̬Һλ�쳣���Ա���*/
struct atg_liquidreport_data_in_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uTestType;                                            /*�������,0:��̬�쳣���                  */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_liquidreport_in_t
{
   int uCount;                                               /*����ṹ�����С                         */ 
   struct atg_liquidreport_data_in_t* pLiquidData;           /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_liquidreport_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_liquidreport_data_out_t*  pLiquidData;         /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*�豸������Ϣ*/
struct atg_device_in_t
{
   int uCount;                                               /*����ṹ�����С                         */
   struct atg_oilcan_data_in_t* pOilCanData;                 /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_device_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                */
   char strProbeNo[SZ_PROBEMODEL + 1];                       /*̽�����к�                              */
   char strProbeModel[SZ_DEVICEMODEL+1];                     /*̽���ͺ�                                */
} ATTRIBUTE_PACK;

struct atg_device_out_t
{
   char strDeviceModel[SZ_DEVICEMODEL+1];                    /*��Ʒ�ͺ�                                 */
   char strEquipCode[SZ_EQUIPCODE + 1];                      /*�豸����                                 */
   char strSysVersion[SZ_SYSVERSION + 1];                         /*ϵͳ�汾                                 */
   char strMakeDate[SZ_DATETIME14+1];                        /*��������                                 */
   int uRetCount;                                               /*��������ṹ�����С                     */
   struct atg_device_data_out_t* pDeviceData;                /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*����ת��*/
struct atg_hightoliter_in_t
{
   int uCount;                                               /*ȫ���ݻ��������С                       */
   struct atg_capacity_data_in_t* pCapacityData;             /*����ṹ����,ȫ���ݻ���                  */
   double fTotalHeight;                                      /*��ˮ�ܸߣ���λ������                     */
   double fWaterHeight;                                      /*ˮ��    ����λ������                     */
   double fOilTemp;                                          /*ƽ���¶ȣ���λ�����϶�                   */
   double fOilTemp1;                                         /*5���¶�                                  */
   double fOilTemp2;                                         /*5���¶�                                  */
   double fOilTemp3;                                         /*5���¶�                                  */
   double fOilTemp4;                                         /*5���¶�                                  */
   double fOilTemp5;                                         /*5���¶�                                  */
} ATTRIBUTE_PACK;

struct atg_hightolite_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_hightoliter_data_out_t*  pHighToLiterData;     /*����ṹ����                             */
} ATTRIBUTE_PACK;

struct atg_hightoliter_data_out_t
{
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   double fOilCubage;                                        /*�����������λ����                       */
   double fOilStandCubage;                                   /*��׼�������λ����                       */
   double fEmptyCubage;                                      /*�����  ����λ����                       */
   double fWaterBulk;                                        /*ˮ���  ����λ����                       */
} ATTRIBUTE_PACK;

struct atg_hightoliter_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_hightoliter_data_out_t* pHighToLiterData;      /*����ṹ����                             */
} ATTRIBUTE_PACK;

/*Һλ�ǿ��ػ���¼*/
struct atg_powerrecord_in_t
{
   char strDateTime[SZ_DATETIME14 + 1];                      /*��ʼ����ʱ��                             */
   int uReqCount;                                            /*���󷵻ر�����Ĭ��Ϊ0                    */
} ATTRIBUTE_PACK;

struct atg_powerrecord_data_out_t
{
   char strDate[SZ_DATE8 + 1];                               /*��������                                 */
   char strTime[SZ_TIME6 + 1];                               /*����ʱ��                                 */
   int uOperateType;                                         /*�������ͣ�0������1�ػ�                   */
   int uOilCanNo;                                            /*�͹ޱ��                                 */
   double fTotalHeight;                                      /*��ˮ�ܸߣ���λ������                     */
   double fWaterHeight;                                      /*ˮ��    ����λ������                     */
   double fOilTemp;                                          /*ƽ���¶ȣ���λ�����϶�                   */
   double fOilTemp1;                                         /*5���¶�                                  */
   double fOilTemp2;                                         /*5���¶�                                  */
   double fOilTemp3;                                         /*5���¶�                                  */
   double fOilTemp4;                                         /*5���¶�                                  */
   double fOilTemp5;                                         /*5���¶�                                  */
   double fOilCubage;                                        /*�����������λ����                       */
   double fOilStandCubage;                                   /*��׼�������λ����                       */
   double fEmptyCubage;                                      /*�����  ����λ����                       */
   double fWaterBulk;                                        /*ˮ���  ����λ����                       */
} ATTRIBUTE_PACK;

struct atg_powerrecord_out_t
{
   int uRetCount;                                            /*��������С                             */
   struct atg_powerrecord_data_out_t* pPowerRecordData;      /*����ṹ����                             */
} ATTRIBUTE_PACK;

static const char* ATG_OPE_GETSTOCK         = "01";          /*ʵʱ���                                 */
static const char* ATG_OPE_GETTIMESTOCK     = "02";          /*������                                 */
static const char* ATG_OPE_GETOILIN         = "03";          /*������Ϣ                                 */
static const char* ATG_OPE_GETALARM         = "04";          /*�͹ޱ���                                 */
static const char* ATG_OPE_GETFAILURE       = "05";          /*�豸����                                 */
static const char* ATG_OPE_CHECKTIME        = "06";          /*Һλ�Ƕ�ʱ                               */
static const char* ATG_OPE_ALARMSETTER      = "07";          /*Ԥ��������                               */
static const char* ATG_OPE_CHGOILINFO       = "08";          /*�͹���Ʒ����                             */
static const char* ATG_OPE_GETCAPACITYTABLE = "09";          /*�ݻ����ϴ�                               */
static const char* ATG_OPE_SETCAPACITYTABLE = "10";          /*�ݻ����·�                               */
static const char* ATG_OPE_STARTLIQUID      = "11";          /*������̬Һλ�쳣����                     */
static const char* ATG_OPE_STOPLIQUID       = "12";          /*������̬Һλ�쳣����                     */
static const char* ATG_OPE_LIQUIDREPORT     = "13";          /*��̬Һλ�쳣���Ա���                     */
static const char* ATG_OPE_GETDEVICEINFO    = "14";          /*�豸������Ϣ                             */
static const char* ATG_OPE_HIGHTOLITER      = "15";          /*����ת��                                 */
static const char* ATG_OPE_SETCORRECTION    = "16";          /*̽��У������                             */
static const char* ATG_OPE_SETPROBE         = "17";          /*̽���͹�����                             */
static const char* ATG_OPE_GETPOWERRECORD   = "18";          /*��Դ�������¼����                       */
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

