// libATGManager.cpp : Defines the entry point for the DLL application.
//
/*
	类型     类型
	boolean	Z
	byte	B
	char	C
	short	S
	int		I
	long	L
	float	F
	double	D
	void	V
	object对象	LClassName;      L类名;
	Arrays	
	[array-type        [数组类型
	methods方法	(argument-types)return-type     (参数类型)返回类型
*/
#include "com_kld_gsm_ATGDevice_ATGDevice.h"
#include "ATGInterface.h"
#include <string.h>
#include <stdlib.h>
#include <dlfcn.h> 
#include <time.h>
#include <pthread.h> 

#define LIB_ATG_FILENAME  "ATGLib.so"

#define LOG_PATH "/smc20/gsm/logs/ctrl/"
#define SZ_MAX_BUFFER 1024

#define LOG_DEBUG(format, args...) write_atgmanager_log(__func__,__LINE__,format, ##args)

void write_atgmanager_log(const char *strFunc, int nLineNO, const char *strFmt,...) 
{
	FILE *fp = NULL;
	char logFileName[128] = { 0 };
	
	struct tm *tmBuf;
	time_t theTime = time(NULL);

	va_list ap;
	char strTime[14 + 1] = {0};
	char strBuf[SZ_MAX_BUFFER ] = {0};
    char strMsg[SZ_MAX_BUFFER ] = {0};

	tmBuf = localtime(&theTime);
	sprintf(strTime, "%04d%02d%02d%02d%02d%02d", tmBuf->tm_year + 1900, tmBuf->tm_mon + 1, tmBuf->tm_mday, tmBuf->tm_hour, tmBuf->tm_min, tmBuf->tm_sec);	
	sprintf(logFileName, "%s%04d%02d%02d.atg", LOG_PATH, tmBuf->tm_year + 1900, tmBuf->tm_mon + 1, tmBuf->tm_mday);	

	va_start(ap, strFmt);
	vsprintf(strBuf, strFmt, ap);
    sprintf(strMsg, "%s\t%s\t%ld\t%s", strTime, strFunc, nLineNO, strBuf);
   if(NULL != (fp = fopen(logFileName, "a+")))
   {
      //fprintf(fp,"%ld\t%ld\t%s\n", (long)getpid(),(long)syscall(__NR_gettid),strMsg);
	  fprintf(fp,"%s\n", strMsg);
      fclose(fp);
   }
   va_end(ap);
}

jstring ctojstring(JNIEnv *env, char* tmpstr) {
	jclass Class_string;
	jmethodID mid_String,mid_getBytes;
	jbyteArray bytes;
	jbyte* log_utf8;
	jstring codetype,jstr;
	//LOG_DEBUG("afer1=%s",tmpstr);
	Class_string = (*env)->FindClass(env,"java/lang/String");//获取class
	//先将gbk字符串转为java里的string格式
	mid_String = (*env)->GetMethodID(env,Class_string, "<init>", "([BLjava/lang/String;)V");
	bytes = (*env)->NewByteArray(env,strlen(tmpstr) + 1);
	(*env)->SetByteArrayRegion(env,bytes, 0, strlen(tmpstr), (jbyte*)tmpstr);
	codetype = (*env)->NewStringUTF(env, "gbk");
	jstr = (jstring)(*env)->NewObject(env,Class_string, mid_String, bytes, codetype);
	//LOG_DEBUG("afer2=%s",bytes);
	(*env)->DeleteLocalRef(env,bytes);

	//再将string变utf-8字符串。
	mid_getBytes = (* env)->GetMethodID(env,Class_string,   "getBytes",   "(Ljava/lang/String;)[B");
	codetype = (*env)->NewStringUTF(env,"utf-8");
	bytes=(jbyteArray)(*env)->CallObjectMethod(env,jstr,mid_getBytes,codetype);
	log_utf8=(*env)->GetByteArrayElements(env,bytes,JNI_FALSE);
	//LOG_DEBUG("afer3=%s",log_utf8);
	return (*env)->NewStringUTF(env,log_utf8);
}

/*
 * Class:     ATGDevice_ATGManager
 * Method:    init
 * Signature: (LATGDevice/atg_init_in_t;)I
 */
JNIEXPORT jint JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_init(JNIEnv *env, jobject obj, jobject jobj)
{
	//获得jobj对象的句柄
	jclass jlistclazz = (*env)->GetObjectClass(env,jobj);
	//传入参数
	struct atg_init_in_t* patg_init_in;
	jfieldID field_strDeviceType = (*env)->GetFieldID(env,jlistclazz,"strDeviceType","Ljava/lang/String;");
	jfieldID field_uConnMode = (*env)->GetFieldID(env,jlistclazz,"uConnMode","I");
	jfieldID field_strSerialAddress = (*env)->GetFieldID(env,jlistclazz,"strSerialAddress","Ljava/lang/String;");
	jfieldID field_strSerialBaudRate = (*env)->GetFieldID(env,jlistclazz,"strSerialBaudRate","Ljava/lang/String;");
	jfieldID field_strSerialStopBit = (*env)->GetFieldID(env,jlistclazz,"strSerialStopBit","Ljava/lang/String;");
	jfieldID field_strSerialCheckBit = (*env)->GetFieldID(env,jlistclazz,"strSerialCheckBit","Ljava/lang/String;");
	jfieldID field_strSerialDataBit = (*env)->GetFieldID(env,jlistclazz,"strSerialDataBit","Ljava/lang/String;");
	jfieldID field_strIPAddress = (*env)->GetFieldID(env,jlistclazz,"strIPAddress","Ljava/lang/String;");
	jfieldID field_strIPPort = (*env)->GetFieldID(env,jlistclazz,"strIPPort","Ljava/lang/String;");
	jfieldID field_strLogPath = (*env)->GetFieldID(env,jlistclazz,"strLogPath","Ljava/lang/String;");

	jstring strDeviceType = (jstring)(*env)->GetObjectField(env,jobj,field_strDeviceType);
	jint uConnMode = (*env)->GetIntField(env,jobj,field_uConnMode);
	jstring strSerialAddress = (jstring)(*env)->GetObjectField(env,jobj,field_strSerialAddress);
	jstring strSerialBaudRate = (jstring)(*env)->GetObjectField(env,jobj,field_strSerialBaudRate);
	jstring strSerialStopBit = (jstring)(*env)->GetObjectField(env,jobj,field_strSerialStopBit);
	jstring strSerialCheckBit = (jstring)(*env)->GetObjectField(env,jobj,field_strSerialCheckBit);
	jstring strSerialDataBit = (jstring)(*env)->GetObjectField(env,jobj,field_strSerialDataBit);
	jstring strIPAddress = (jstring)(*env)->GetObjectField(env,jobj,field_strIPAddress);
	jstring strIPPort = (jstring)(*env)->GetObjectField(env,jobj,field_strIPPort);
	jstring strLogPath = (jstring)(*env)->GetObjectField(env,jobj,field_strLogPath);
	int ret=1;
	int * (*funcPtrB)(void* p) = NULL; 
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);

    LOG_DEBUG("begin...");
	patg_init_in = (struct atg_init_in_t*)malloc(sizeof(struct atg_init_in_t));
	strcpy(patg_init_in->strDeviceType,(*env)->GetStringUTFChars(env,strDeviceType, NULL));
	patg_init_in->uConnMode=uConnMode;
	strcpy(patg_init_in->strLogPath,(*env)->GetStringUTFChars(env,strLogPath, NULL));
	if(NULL != strSerialAddress){
		strcpy(patg_init_in->strSerialAddress,(*env)->GetStringUTFChars(env,strSerialAddress, NULL));
		strcpy(patg_init_in->strSerialBaudRate,(*env)->GetStringUTFChars(env,strSerialBaudRate, NULL));
		strcpy(patg_init_in->strSerialStopBit,(*env)->GetStringUTFChars(env,strSerialStopBit, NULL));
		strcpy(patg_init_in->strSerialCheckBit,(*env)->GetStringUTFChars(env,strSerialCheckBit, NULL));
		strcpy(patg_init_in->strSerialDataBit,(*env)->GetStringUTFChars(env,strSerialDataBit, NULL));
		
		LOG_DEBUG("%d|%s|%s|%s|%s|%s|%s|%s|",
			patg_init_in->uConnMode,
			patg_init_in->strSerialAddress,
			patg_init_in->strSerialBaudRate,
			patg_init_in->strSerialStopBit,
			patg_init_in->strSerialCheckBit,
			patg_init_in->strSerialDataBit,
			patg_init_in->strLogPath,patg_init_in->strDeviceType);
	}
	if(NULL!=strIPAddress){
		strcpy(patg_init_in->strIPAddress,(*env)->GetStringUTFChars(env,strIPAddress, NULL));
		strcpy(patg_init_in->strIPPort,(*env)->GetStringUTFChars(env,strIPPort, NULL));
		LOG_DEBUG("%d|%s|%s|%s|%s|",patg_init_in->uConnMode,patg_init_in->strIPAddress,patg_init_in->strIPPort,patg_init_in->strLogPath,patg_init_in->strDeviceType);
	}
	if(filehandle){
		funcPtrB = dlsym(filehandle, "atg_init"); 
		if (funcPtrB) 
		{ 
			LOG_DEBUG("func|begin");
			ret = (int)funcPtrB(patg_init_in); 
			LOG_DEBUG("func|atg_init|end|ret=[%d]", ret);
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(filehandle){
			dlclose(filehandle);
		}
		return ret;	
	}
	LOG_DEBUG("end...");
	return ret;
}

/*
 * Class:     ATGDevice_ATGManager
 * Method:    getStock
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getStock
  (JNIEnv * env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_stock_in_t* atg_stock_in;
	struct  atg_oilcan_data_in_t *atg_oilcan_data_in;
	int i=0;
	//定义返回struct
	struct atg_stock_out_t* atg_stock_out = NULL;
    struct atg_stock_out_t**  patg_stock_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);

	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID
	jfieldID uOilCanNo;
	jfieldID strDate;
	jfieldID strTime;
	jfieldID fOilCubage;
	jfieldID fOilStandCubage;
	jfieldID fEmptyCubage;
	jfieldID fTotalHeight;
	jfieldID fWaterHeight;
	jfieldID fOilTemp;
	jfieldID fOilTemp1;
	jfieldID fOilTemp2;
	jfieldID fOilTemp3;
	jfieldID fOilTemp4;
	jfieldID fOilTemp5;
	jfieldID fWaterBulk;
	jfieldID fApparentDensity;
	jfieldID fStandDensity;
	jmethodID jno_intValue;
	jint jno;
	int ret;

	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");
	
	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;
	//定义数组的大小（malloc最后要释放）
	atg_oilcan_data_in = (struct atg_oilcan_data_in_t*)malloc(sizeof(struct atg_oilcan_data_in_t) * len);
	atg_stock_in = (struct atg_stock_in_t*)malloc(sizeof(struct atg_stock_in_t));
	atg_stock_in[0].uCount = len;
	//循环
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		
		jno_intValue = (*env)->GetMethodID(env,cls_data,"intValue","()I");
		jno = (*env)->CallIntMethod(env,obj_data,jno_intValue);
		atg_oilcan_data_in[i].uOilCanNo = jno;
		LOG_DEBUG("%d|%d|%d|",i,atg_oilcan_data_in[i].uOilCanNo,atg_stock_in[0].uCount);
	}
	atg_stock_in[0].pOilCanData = atg_oilcan_data_in;

	//调用接口
	if(filehandle){
        functC = dlsym(filehandle, "atg_operate"); 
        if (functC) 
        { 
			patg_stock_out = &atg_stock_out;
			LOG_DEBUG("func|begin");
			ret = (int)functC(ATG_OPE_GETSTOCK, atg_stock_in, (void *)(patg_stock_out));//ATG_OPE_GETSTOCK, atg_stock_in, atg_stock_out
			LOG_DEBUG("func|ret=[%d]",ret);
		} 
	}else{
		LOG_DEBUG("failed");
	}
	
	if(atg_stock_out!=NULL){
		LOG_DEBUG("atg_stock_out.uRetCount=[%d]",atg_stock_out[0].uRetCount);
	}else {
		LOG_DEBUG("atg_stock_out|is|null");
		if(atg_stock_out){
			if(atg_stock_out->pStockData){
				free(atg_stock_out->pStockData);
			}
			free(atg_stock_out);
		}	
		if(filehandle){
			dlclose(filehandle);
		}
		return obj_ArrayList;	
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_stock_in){
			if(atg_stock_in->pOilCanData){
				free(atg_stock_in->pOilCanData);
			}
			free(atg_stock_in);
		}
		if(atg_stock_out){
			if(atg_stock_out->pStockData){
				free(atg_stock_out->pStockData);
			}
			free(atg_stock_out);
		}	
		if(filehandle){
			dlclose(filehandle);
		}
		return obj_ArrayList;	
	}
	
	for(i=0;i<atg_stock_out[0].uRetCount;i++){	
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_stock_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID
		uOilCanNo = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNo","I");
		strDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDate","Ljava/lang/String;");
		strTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strTime","Ljava/lang/String;");
		fOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilCubage","D");
		fOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilStandCubage","D");
		fEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEmptyCubage","D");
		fTotalHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fTotalHeight","D");
		fWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fWaterHeight","D");
		fOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp","D");
		fOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp1","D");
		fOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp2","D");
		fOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp3","D");
		fOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp4","D");
		fOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp5","D");
		fWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fWaterBulk","D");
		fApparentDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fApparentDensity","D");
		fStandDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStandDensity","D");

		//给String赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNo,atg_stock_out[0].pStockData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strDate,(*env)->NewStringUTF(env,atg_stock_out[0].pStockData[i].strDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strTime,(*env)->NewStringUTF(env,atg_stock_out[0].pStockData[i].strTime));
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilCubage,atg_stock_out[0].pStockData[i].fOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilStandCubage,atg_stock_out[0].pStockData[i].fOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEmptyCubage,atg_stock_out[0].pStockData[i].fEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fTotalHeight,atg_stock_out[0].pStockData[i].fTotalHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fWaterHeight,atg_stock_out[0].pStockData[i].fWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp,atg_stock_out[0].pStockData[i].fOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp1,atg_stock_out[0].pStockData[i].fOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp2,atg_stock_out[0].pStockData[i].fOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp3,atg_stock_out[0].pStockData[i].fOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp4,atg_stock_out[0].pStockData[i].fOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp5,atg_stock_out[0].pStockData[i].fOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fWaterBulk,atg_stock_out[0].pStockData[i].fWaterBulk);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fApparentDensity,atg_stock_out[0].pStockData[i].fApparentDensity);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStandDensity,atg_stock_out[0].pStockData[i].fStandDensity);
		
		LOG_DEBUG("%d|%d|%s|%s|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|",i
			,atg_stock_out[0].pStockData[i].uOilCanNo
			,atg_stock_out[0].pStockData[i].strDate
			,atg_stock_out[0].pStockData[i].strTime
			,atg_stock_out[0].pStockData[i].fOilCubage
			,atg_stock_out[0].pStockData[i].fOilStandCubage
			,atg_stock_out[0].pStockData[i].fEmptyCubage
			,atg_stock_out[0].pStockData[i].fTotalHeight	
			,atg_stock_out[0].pStockData[i].fWaterHeight
			,atg_stock_out[0].pStockData[i].fOilTemp
			,atg_stock_out[0].pStockData[i].fOilTemp1
			,atg_stock_out[0].pStockData[i].fOilTemp2
			,atg_stock_out[0].pStockData[i].fOilTemp3
			,atg_stock_out[0].pStockData[i].fOilTemp4
			,atg_stock_out[0].pStockData[i].fOilTemp5
			,atg_stock_out[0].pStockData[i].fWaterBulk
			,atg_stock_out[0].pStockData[i].fApparentDensity
			,atg_stock_out[0].pStockData[i].fStandDensity
			);

		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);		
	}

	if(atg_stock_in){
		if(atg_stock_in->pOilCanData){
			free(atg_stock_in->pOilCanData);
		}
		free(atg_stock_in);
	}
	if(atg_stock_out){
		if(atg_stock_out->pStockData){
			free(atg_stock_out->pStockData);
		}
		free(atg_stock_out);
	}
    if(filehandle){
		dlclose(filehandle);
	}
	LOG_DEBUG("end...");
	return obj_ArrayList;
  }
/*
 * Class:     ATGDevice_ATGManager
 * Method:    getTimeStock
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getTimeStock (JNIEnv *env, jobject obj, jobject jlist)
{
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_timestock_in_t* atg_timestock_in;
	struct  atg_timestock_data_in_t *atg_timestock_data_in;
	int i=0;
	jfieldID field_uOilCanNo;
	jfieldID field_strDataTime;
	jfieldID field_uReqCount;

	jint uOilCanNo;
	jstring strDataTime;
	jint uReqCount;
	int ret;

	const char * charDataTime;
	//定义返回struct
	struct atg_stock_out_t* atg_stock_out = NULL;
    struct atg_stock_out_t**  patg_stock_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID
	jfieldID out_uOilCanNo;
	jfieldID strDate;
	jfieldID strTime;
	jfieldID fOilCubage;
	jfieldID fOilStandCubage;
	jfieldID fEmptyCubage;
	jfieldID fTotalHeight;
	jfieldID fWaterHeight;
	jfieldID fOilTemp;
	jfieldID fOilTemp1;
	jfieldID fOilTemp2;
	jfieldID fOilTemp3;
	jfieldID fOilTemp4;
	jfieldID fOilTemp5;
	jfieldID fWaterBulk;
	jfieldID fApparentDensity;
	jfieldID fStandDensity;
	
	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;

	//定义数组的大小（malloc最后要释放）
	atg_timestock_data_in = (struct atg_timestock_data_in_t*)malloc(sizeof(struct atg_timestock_data_in_t) * len);
	atg_timestock_in = (struct atg_timestock_in_t*)malloc(sizeof(struct atg_timestock_in_t));
	atg_timestock_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNo","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);
		
		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*

		strcpy(atg_timestock_data_in[i].strDateTime,charDataTime);
		atg_timestock_data_in[i].uOilCanNo = uOilCanNo;		
		atg_timestock_data_in[i].uReqCount = uReqCount;		
	}
	LOG_DEBUG("%d|%s|%d|"
		,atg_timestock_data_in[0].uOilCanNo
		,atg_timestock_data_in[0].strDateTime
		,atg_timestock_data_in[0].uReqCount);
	
	atg_timestock_in[0].pTimeStockData = atg_timestock_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_stock_out = &atg_stock_out;
			 LOG_DEBUG("func begin");
		     ret = (int)functC(ATG_OPE_GETTIMESTOCK,atg_timestock_in,(void *)(patg_stock_out));
			 LOG_DEBUG("func ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_timestock_in->pTimeStockData){
			free(atg_timestock_in->pTimeStockData);
		}
		if(atg_timestock_in){
			free(atg_timestock_in);
		}
		if(atg_stock_out){
			if(atg_stock_out->pStockData){
				free(atg_stock_out->pStockData);
			}
			free(atg_stock_out);
		}
		if(filehandle){
			dlclose(filehandle);
		}
		return obj_ArrayList;	
	}
			LOG_DEBUG("%d|%s|%s|%f|%f|%f|%f|"
			,atg_stock_out[0].pStockData[0].uOilCanNo
			,atg_stock_out[0].pStockData[0].strDate
			,atg_stock_out[0].pStockData[0].strTime
			,atg_stock_out[0].pStockData[0].fOilCubage
			,atg_stock_out[0].pStockData[0].fOilTemp
			,atg_stock_out[0].pStockData[0].fTotalHeight
			,atg_stock_out[0].pStockData[0].fWaterHeight);

	LOG_DEBUG("uRetCount=[%d]",atg_stock_out[0].uRetCount);
	for(i=0;i<atg_stock_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_stock_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID
		out_uOilCanNo = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNo","I");
		strDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDate","Ljava/lang/String;");
		strTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strTime","Ljava/lang/String;");
		fOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilCubage","D");
		fOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilStandCubage","D");
		fEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEmptyCubage","D");
		fTotalHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fTotalHeight","D");
		fWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fWaterHeight","D");
		fOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp","D");
		fOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp1","D");
		fOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp2","D");
		fOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp3","D");
		fOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp4","D");
		fOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilTemp5","D");
		fWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fWaterBulk","D");
		fApparentDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fApparentDensity","D");
		fStandDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStandDensity","D");

		//赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,out_uOilCanNo,atg_stock_out[0].pStockData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strDate,(*env)->NewStringUTF(env,atg_stock_out[0].pStockData[i].strDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strTime,(*env)->NewStringUTF(env,atg_stock_out[0].pStockData[i].strTime));
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilCubage,atg_stock_out[0].pStockData[i].fOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilStandCubage,atg_stock_out[0].pStockData[i].fOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEmptyCubage,atg_stock_out[0].pStockData[i].fEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fTotalHeight,atg_stock_out[0].pStockData[i].fTotalHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fWaterHeight,atg_stock_out[0].pStockData[i].fWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp,atg_stock_out[0].pStockData[i].fOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp1,atg_stock_out[0].pStockData[i].fOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp2,atg_stock_out[0].pStockData[i].fOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp3,atg_stock_out[0].pStockData[i].fOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp4,atg_stock_out[0].pStockData[i].fOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilTemp5,atg_stock_out[0].pStockData[i].fOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fWaterBulk,atg_stock_out[0].pStockData[i].fWaterBulk);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fApparentDensity,atg_stock_out[0].pStockData[i].fApparentDensity);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStandDensity,atg_stock_out[0].pStockData[i].fStandDensity);
		
		LOG_DEBUG("%d|%d|%s|%s|%f|%f|%f|%f|",i
			,atg_stock_out[0].pStockData[i].uOilCanNo
			,atg_stock_out[0].pStockData[i].strDate
			,atg_stock_out[0].pStockData[i].strTime
			,atg_stock_out[0].pStockData[i].fOilCubage
			,atg_stock_out[0].pStockData[i].fOilTemp
			,atg_stock_out[0].pStockData[i].fTotalHeight
			,atg_stock_out[0].pStockData[i].fWaterHeight);

		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
	
	if(atg_timestock_in->pTimeStockData){
		free(atg_timestock_in->pTimeStockData);
	}
	if(atg_timestock_in){
		free(atg_timestock_in);
	}
	if(atg_stock_out){
		if(atg_stock_out->pStockData){
			free(atg_stock_out->pStockData);
		}
		free(atg_stock_out);
	}
    if(filehandle){
		dlclose(filehandle);
	}
	LOG_DEBUG("end...");
	return obj_ArrayList;
}
/*
 * Class:     ATGDevice_ATGManager
 * Method:    getOilIn
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getOilIn
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_oilin_in_t* atg_oilin_in;
	struct  atg_oilin_data_in_t *atg_oilin_data_in;
	//定义返回struct
	struct atg_oilin_out_t* atg_oilin_out  = NULL;
    struct atg_oilin_out_t**  patg_oilin_out  = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;

	int i=0;

	jfieldID field_uOilCanNo;
	jfieldID field_strDataTime;
	jfieldID field_uReqCount;

	jint uOilCanNo;
	jstring strDataTime;
	jint uReqCount;

	const char * charDataTime;
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;

	jfieldID uOilCanNO;
	jfieldID strStartDate;
	jfieldID strEndDate;
	jfieldID strStartTime;
	jfieldID strEndTime;
	jfieldID fStartCubage;
	jfieldID fStartStandCubage;
	jfieldID fStartOilHeight;
	jfieldID fStartWaterHeight;
	jfieldID fStartOilTemp;
	jfieldID fStartOilTemp1;
	jfieldID fStartOilTemp2;
	jfieldID fStartOilTemp3;
	jfieldID fStartOilTemp4;
	jfieldID fStartOilTemp5;
	jfieldID fEndCubage;
	jfieldID fEndStandCubage;
	jfieldID fEndOilHeight;
	jfieldID fEndWaterHeight;
	jfieldID fEndOilTemp;
	jfieldID fEndOilTemp1;
	jfieldID fEndOilTemp2;
	jfieldID fEndOilTemp3;
	jfieldID fEndOilTemp4;
	jfieldID fEndOilTemp5;
	jfieldID fEmptyCubage;
	jfieldID fApparentDensity;
	jfieldID fStandDensity;
	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;
	//定义数组的大小（malloc最后要释放）
	atg_oilin_data_in = (struct atg_oilin_data_in_t*)malloc(sizeof(struct atg_oilin_data_in_t) * len);
	atg_oilin_in = (struct atg_oilin_in_t*)malloc(sizeof(struct atg_oilin_in_t));
	atg_oilin_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);

		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*
		atg_oilin_data_in[i].uOilCanNo = uOilCanNo;
		strcpy(atg_oilin_data_in[i].strDateTime,charDataTime);
		atg_oilin_data_in[i].uReqCount = uReqCount;
		LOG_DEBUG("%d|%d|%s|%d",i
			,atg_oilin_data_in[i].uOilCanNo
			,atg_oilin_data_in[i].strDateTime
			,atg_oilin_data_in[i].uReqCount);		
	}		
	atg_oilin_in[0].pOilInData = atg_oilin_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_oilin_out = &atg_oilin_out;
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_GETOILIN, atg_oilin_in, (void *)(patg_oilin_out));
			 LOG_DEBUG("func|ret=[%d]",ret);
		} 
	}else{
		LOG_DEBUG("failed");
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_oilin_out){
			if(atg_oilin_out->pOilinData){
				free(atg_oilin_out->pOilinData);
			}
			free(atg_oilin_out);
		}
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}
	//循环给返回的list赋值
	LOG_DEBUG("uRetCount=[%d]",atg_oilin_out[0].uRetCount);
	for(i=0;i<atg_oilin_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_oilin_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");

		uOilCanNO = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNO","I");
		strStartDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartDate","Ljava/lang/String;");
		strEndDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndDate","Ljava/lang/String;");
		strStartTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartTime","Ljava/lang/String;");
		strEndTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndTime","Ljava/lang/String;");
		fStartCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartCubage","D");
		fStartStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartStandCubage","D");
		fStartOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilHeight","D");
		fStartWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartWaterHeight","D");
		fStartOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp","D");
		fStartOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp1","D");
		fStartOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp2","D");
		fStartOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp3","D");
		fStartOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp4","D");
		fStartOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp5","D");
		fEndCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndCubage","D");
		fEndStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndStandCubage","D");
		fEndOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilHeight","D");
		fEndWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndWaterHeight","D");
		fEndOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp","D");
		fEndOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp1","D");
		fEndOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp2","D");
		fEndOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp3","D");
		fEndOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp4","D");
		fEndOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp5","D");
		fEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEmptyCubage","D");
		fApparentDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fApparentDensity","D");
		fStandDensity = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStandDensity","D");

		//赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNO,atg_oilin_out[0].pOilinData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartDate,(*env)->NewStringUTF(env,atg_oilin_out[0].pOilinData[i].strStartDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndDate,(*env)->NewStringUTF(env,atg_oilin_out[0].pOilinData[i].strEndDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartTime,(*env)->NewStringUTF(env,atg_oilin_out[0].pOilinData[i].strStartTime));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndTime,(*env)->NewStringUTF(env,atg_oilin_out[0].pOilinData[i].strEndTime));
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartCubage,atg_oilin_out[0].pOilinData[i].fStartCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartStandCubage,atg_oilin_out[0].pOilinData[i].fStartStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilHeight,atg_oilin_out[0].pOilinData[i].fStartOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartWaterHeight,atg_oilin_out[0].pOilinData[i].fStartWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp,atg_oilin_out[0].pOilinData[i].fStartOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp1,atg_oilin_out[0].pOilinData[i].fStartOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp2,atg_oilin_out[0].pOilinData[i].fStartOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp3,atg_oilin_out[0].pOilinData[i].fStartOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp4,atg_oilin_out[0].pOilinData[i].fStartOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp5,atg_oilin_out[0].pOilinData[i].fStartOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndCubage,atg_oilin_out[0].pOilinData[i].fEndCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndStandCubage,atg_oilin_out[0].pOilinData[i].fEndStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilHeight,atg_oilin_out[0].pOilinData[i].fEndOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndWaterHeight,atg_oilin_out[0].pOilinData[i].fEndWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp,atg_oilin_out[0].pOilinData[i].fEndOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp1,atg_oilin_out[0].pOilinData[i].fEndOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp2,atg_oilin_out[0].pOilinData[i].fEndOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp3,atg_oilin_out[0].pOilinData[i].fEndOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp4,atg_oilin_out[0].pOilinData[i].fEndOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp5,atg_oilin_out[0].pOilinData[i].fEndOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEmptyCubage,atg_oilin_out[0].pOilinData[i].fEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fApparentDensity,atg_oilin_out[0].pOilinData[i].fApparentDensity);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStandDensity,atg_oilin_out[0].pOilinData[i].fStandDensity);
		LOG_DEBUG("%d|%d|%s|%s|%s|%s|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|",i
			,atg_oilin_out[0].pOilinData[i].uOilCanNo
			,atg_oilin_out[0].pOilinData[i].strStartDate
			,atg_oilin_out[0].pOilinData[i].strStartTime
			,atg_oilin_out[0].pOilinData[i].strEndDate
			,atg_oilin_out[0].pOilinData[i].strEndTime
			,atg_oilin_out[0].pOilinData[i].fStartCubage
			,atg_oilin_out[0].pOilinData[i].fStartCubage
			,atg_oilin_out[0].pOilinData[i].fStartOilHeight
			,atg_oilin_out[0].pOilinData[i].fStartWaterHeight
			,atg_oilin_out[0].pOilinData[i].fStartOilTemp
			,atg_oilin_out[0].pOilinData[i].fEndCubage
			,atg_oilin_out[0].pOilinData[i].fEndStandCubage
			,atg_oilin_out[0].pOilinData[i].fEndOilHeight
			,atg_oilin_out[0].pOilinData[i].fEndWaterHeight
			,atg_oilin_out[0].pOilinData[i].fEndOilTemp
			,atg_oilin_out[0].pOilinData[i].fApparentDensity
			,atg_oilin_out[0].pOilinData[i].fStandDensity
		);

		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
	if(atg_oilin_out){
		if(atg_oilin_out->pOilinData){
			free(atg_oilin_out->pOilinData);
		}
		free(atg_oilin_out);
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_ArrayList;
  }
/*
 * Class:     ATGDevice_ATGManager
 * Method:    getAlarm
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getAlarm
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_alarm_in_t* atg_alarm_in;
	struct  atg_alarm_data_in_t *atg_alarm_data_in;
	//定义返回struct
	struct atg_alarm_out_t* atg_alarm_out = NULL;
    struct atg_alarm_out_t**  patg_alarm_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;

	int i=0;

	jfieldID field_uOilCanNo;
	jfieldID field_strDataTime;
	jfieldID field_uReqCount;

	jint uOilCanNo;
	jstring strDataTime;
	jint uReqCount;

	const char * charDataTime;

	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID
	jfieldID uOilCanNO;
	jfieldID strDate;
	jfieldID strTime;
	jfieldID strAlarmType;
	jfieldID strAlarmState;
	jfieldID strRemark;
	jfieldID strReport;

	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;
//*********************************修改传入参数开始***************************************************************
	//定义数组的大小（malloc最后要释放）
	atg_alarm_data_in = (struct atg_alarm_data_in_t*)malloc(sizeof(struct atg_alarm_data_in_t) * len);
	atg_alarm_in = (struct atg_alarm_in_t*)malloc(sizeof(struct atg_alarm_in_t));
	atg_alarm_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);

		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*

		atg_alarm_data_in[i].uOilCanNo = uOilCanNo;
		strcpy(atg_alarm_data_in[i].strDateTime,charDataTime);
		atg_alarm_data_in[i].uReqCount = uReqCount;

		LOG_DEBUG("%d|%d|%s|%d|",i,atg_alarm_data_in[i].uOilCanNo
			,atg_alarm_data_in[i].strDateTime
			,atg_alarm_data_in[i].uReqCount);		
	}		
	atg_alarm_in[0].pAlarmData = atg_alarm_data_in;
//***************************************修改传入参数结束****************************************************

	//调用接口
		if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_alarm_out = &atg_alarm_out;
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_GETALARM,atg_alarm_in,(void *)(patg_alarm_out));
			 LOG_DEBUG("func|ret=%d",ret);
		}else{
			 LOG_DEBUG("failed");
		}
	}

	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_alarm_in->pAlarmData){
			free(atg_alarm_in->pAlarmData);
		}
		if(atg_alarm_in){
			free(atg_alarm_in);
		}
		if(atg_alarm_out){
			if(atg_alarm_out->pAlarmData){
				free(atg_alarm_out->pAlarmData);
			}
			free(atg_alarm_out);
		}
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}
//****************************************修改传出参数开始**********************************************************	
	//循环给返回的list赋值
	for(i=0;i<atg_alarm_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_alarm_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID
		uOilCanNO = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNO","I");
		strDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDate","Ljava/lang/String;");
		strTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strTime","Ljava/lang/String;");
		strAlarmType = (*env)->GetFieldID(env,cls_atg_data_in_t,"strAlarmType","Ljava/lang/String;");
		strAlarmState = (*env)->GetFieldID(env,cls_atg_data_in_t,"strAlarmState","Ljava/lang/String;");
		strRemark = (*env)->GetFieldID(env,cls_atg_data_in_t,"strRemark","Ljava/lang/String;");
		strReport = (*env)->GetFieldID(env,cls_atg_data_in_t,"strReport","Ljava/lang/String;");
	
		//赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNO,atg_alarm_out[0].pAlarmData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strDate,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strTime,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strTime));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strAlarmType,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strAlarmType));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strAlarmState,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strAlarmState));
		//(*env)->SetObjectField(env,obj_atg_data_in_t,strRemark,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strRemark));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strRemark,ctojstring(env,atg_alarm_out[0].pAlarmData[i].strRemark));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strReport,(*env)->NewStringUTF(env,atg_alarm_out[0].pAlarmData[i].strReport));
		
		LOG_DEBUG("%d|%d|%s|%s|%s|%s|%s|%s|",i
			,atg_alarm_out[0].pAlarmData[i].uOilCanNo
			,atg_alarm_out[0].pAlarmData[i].strDate
			,atg_alarm_out[0].pAlarmData[i].strTime
			,atg_alarm_out[0].pAlarmData[i].strAlarmType
			,atg_alarm_out[0].pAlarmData[i].strAlarmState
			,atg_alarm_out[0].pAlarmData[i].strRemark
			,atg_alarm_out[0].pAlarmData[i].strReport);
		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
//****************************************修改传出参数结束**********************************************************	
	if(atg_alarm_in->pAlarmData){
		free(atg_alarm_in->pAlarmData);
	}
	if(atg_alarm_in){
		free(atg_alarm_in);
	}
	if(atg_alarm_out){
		if(atg_alarm_out->pAlarmData){
			free(atg_alarm_out->pAlarmData);
		}
		free(atg_alarm_out);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_ArrayList;	
  }
/*
 * Class:     ATGDevice_ATGManager
 * Method:    getfailure
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getFailure
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_failure_in_t* atg_failure_in;
	struct atg_failure_data_in_t *atg_failure_data_in;	
	//定义返回struct
	struct atg_failure_out_t* atg_failure_out = NULL;
    struct atg_failure_out_t**  patg_failure_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;

	jfieldID field_uOilCanNo;
	jfieldID field_strDataTime;
	jfieldID field_uReqCount;
	jint uOilCanNo;
	jstring strDataTime;
	jint uReqCount;
	const char * charDataTime;
	int i=0;

	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID
	
	jfieldID uOilCanNO;
	jfieldID strDate;
	jfieldID strTime;
	jfieldID strDeviceType;
	jfieldID strFailureType;
	jfieldID strEquipCode;
	jfieldID strFailCode;
	jfieldID strEquipBrand;
	jfieldID strProbeModel;
	jfieldID strAlarmState;
		
	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;
	//定义数组的大小（malloc最后要释放）
	atg_failure_data_in = (struct atg_failure_data_in_t*)malloc(sizeof(struct atg_failure_data_in_t) * len);
	atg_failure_in = (struct atg_failure_in_t*)malloc(sizeof(struct atg_failure_in_t*));
	atg_failure_in[0].uCount = len;
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);

		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*
		strcpy(atg_failure_data_in[i].strDateTime,charDataTime);
		atg_failure_data_in[i].uOilCanNo = uOilCanNo;
		atg_failure_data_in[i].uReqCount = uReqCount;
		LOG_DEBUG("%d|%d|%d|",i,uOilCanNo,uReqCount);
	}
	atg_failure_in[0].pFailureData = atg_failure_data_in;

	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_failure_out = &atg_failure_out;
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_GETFAILURE,atg_failure_in,(void *)(patg_failure_out));
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_failure_in->pFailureData){
			free(atg_failure_in->pFailureData);
		}
		if(atg_failure_in){
			free(atg_failure_in);
		}

		if(atg_failure_out){
			if(atg_failure_out->pFailureData){
				free(atg_failure_out->pFailureData);
			}
			free(atg_failure_out);
		}	
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}
	//循环给返回的list赋值
	for(i=0;i<atg_failure_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_failure_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID
		uOilCanNO = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNO","I");
		strDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDate","Ljava/lang/String;");
		strTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strTime","Ljava/lang/String;");
		strDeviceType = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDeviceType","Ljava/lang/String;");
		strFailureType = (*env)->GetFieldID(env,cls_atg_data_in_t,"strFailureType","Ljava/lang/String;");
		strEquipCode = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEquipCode","Ljava/lang/String;");
		strFailCode = (*env)->GetFieldID(env,cls_atg_data_in_t,"strFailCode","Ljava/lang/String;");
		strEquipBrand = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEquipBrand","Ljava/lang/String;");
		strProbeModel = (*env)->GetFieldID(env,cls_atg_data_in_t,"strProbeModel","Ljava/lang/String;");
		strAlarmState = (*env)->GetFieldID(env,cls_atg_data_in_t,"strAlarmState","Ljava/lang/String;");
		
		//赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNO,atg_failure_out[0].pFailureData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strDate,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strTime,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strTime));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strDeviceType,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strDeviceType));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strFailureType,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strFailureType));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEquipCode,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strEquipCode));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strFailCode,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strFailCode));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEquipBrand,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strEquipBrand));
		//(*env)->SetObjectField(env,obj_atg_data_in_t,strProbeModel,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strProbeModel));
		
		(*env)->SetObjectField(env,obj_atg_data_in_t,strProbeModel,ctojstring(env,atg_failure_out[0].pFailureData[i].strProbeModel));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strAlarmState,(*env)->NewStringUTF(env,atg_failure_out[0].pFailureData[i].strAlarmState));
		LOG_DEBUG("%d|%d|%s|%s|%s|%s|%s|%s|%s|%s|",i
			,atg_failure_out[0].pFailureData[i].uOilCanNo
			,atg_failure_out[0].pFailureData[i].strDate
			,atg_failure_out[0].pFailureData[i].strTime
			,atg_failure_out[0].pFailureData[i].strDeviceType
			,atg_failure_out[0].pFailureData[i].strFailureType
			,atg_failure_out[0].pFailureData[i].strEquipCode
			,atg_failure_out[0].pFailureData[i].strFailCode
			,atg_failure_out[0].pFailureData[i].strProbeModel
			,atg_failure_out[0].pFailureData[i].strAlarmState);
		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
	if(atg_failure_in->pFailureData){
		free(atg_failure_in->pFailureData);
	}
	if(atg_failure_in){
		free(atg_failure_in);
	}
	if(atg_failure_out){
		if(atg_failure_out->pFailureData){
			free(atg_failure_out->pFailureData);
		}
		free(atg_failure_out);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_ArrayList;
  }

/*
 * Class:     ATGDevice_ATGManager
 * Method:    checkTime
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_checkTime
  (JNIEnv *env, jobject obj, jstring jobj)
  {
	const char * pInputData = (*env)->GetStringUTFChars(env,jobj, NULL); //转为C的char*
	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	char* pOutputData;
	int ret;
	LOG_DEBUG("begin...");
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_CHECKTIME,(void*)pInputData,(void*)&pOutputData);
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		 if(filehandle){
			dlclose(filehandle);
		 } 
		return (*env)->NewStringUTF(env,"1");	
	}
    if(filehandle){
		dlclose(filehandle);
	 }  
	LOG_DEBUG("end...");
	return (*env)->NewStringUTF(env,"0");
}
/*
 * Class:     ATGDevice_ATGManager
 * Method:    alarmSetter
 * Signature: (Ljava/util/List;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_alarmSetter
  (JNIEnv *env, jobject obj, jobject jlist)
  {
  	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_setalarm_in_t* atg_setalarm_in;
	struct  atg_setalarm_data_in_t *atg_setalarm_data_in;
	int i=0;
	//定义返回struct
	char *pOutputData;
	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;

	jfieldID field_uOilCanNo;
	jfieldID field_fLowWarning;
	jfieldID field_fLowAlarm;
	jfieldID field_fHighWarning;
	jfieldID field_fHighAlarm;
	jfieldID field_fWaterWarning;
	jfieldID field_fWaterAlarm;
	jfieldID field_fThiefAlarm;
	jfieldID field_fLeakAlarm;
	jfieldID field_fPercolatingAlarm;
	jfieldID field_fHighTempAlarm;
	jfieldID field_fLowTempAlarm;

	jint uOilCanNo;
	jdouble fLowWarning;
	jdouble fLowAlarm;
	jdouble fHighWarning;
	jdouble fHighAlarm;
	jdouble fWaterWarning;
	jdouble fWaterAlarm;
	jdouble fThiefAlarm;
	jdouble fLeakAlarm;
	jdouble fPercolatingAlarm;
	jdouble fHighTempAlarm;
	jdouble fLowTempAlarm;

	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return NULL;

	//定义数组的大小（malloc最后要释放）
	atg_setalarm_data_in = (struct atg_setalarm_data_in_t*)malloc(sizeof(struct atg_setalarm_data_in_t) * len);
	atg_setalarm_in = (struct atg_setalarm_in_t*)malloc(sizeof(struct atg_setalarm_in_t));
	atg_setalarm_in[0].uReqCount = len;
	LOG_DEBUG("uReqCount=[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
		field_fLowWarning = (*env)->GetFieldID(env,cls_data,"fLowWarning","D");
		field_fLowAlarm = (*env)->GetFieldID(env,cls_data,"fLowAlarm","D");
		field_fHighWarning = (*env)->GetFieldID(env,cls_data,"fHighWarning","D");
		field_fHighAlarm = (*env)->GetFieldID(env,cls_data,"fHighAlarm","D");
		field_fWaterWarning = (*env)->GetFieldID(env,cls_data,"fWaterWarning","D");
		field_fWaterAlarm = (*env)->GetFieldID(env,cls_data,"fWaterAlarm","D");
		field_fThiefAlarm = (*env)->GetFieldID(env,cls_data,"fThiefAlarm","D");
		field_fLeakAlarm = (*env)->GetFieldID(env,cls_data,"fLeakAlarm","D");
		field_fPercolatingAlarm = (*env)->GetFieldID(env,cls_data,"fPercolatingAlarm","D");
		field_fHighTempAlarm = (*env)->GetFieldID(env,cls_data,"fHighTempAlarm","D");
		field_fLowTempAlarm = (*env)->GetFieldID(env,cls_data,"fLowTempAlarm","D");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		fLowWarning = (*env)->GetDoubleField(env,obj_data,field_fLowWarning);
		fLowAlarm = (*env)->GetDoubleField(env,obj_data,field_fLowAlarm);
		fHighWarning = (*env)->GetDoubleField(env,obj_data,field_fHighWarning);
		fHighAlarm = (*env)->GetDoubleField(env,obj_data,field_fHighAlarm);
		fWaterWarning = (*env)->GetDoubleField(env,obj_data,field_fWaterWarning);
		fWaterAlarm = (*env)->GetDoubleField(env,obj_data,field_fWaterAlarm);
		fThiefAlarm = (*env)->GetDoubleField(env,obj_data,field_fThiefAlarm);
		fLeakAlarm = (*env)->GetDoubleField(env,obj_data,field_fLeakAlarm);
		fPercolatingAlarm = (*env)->GetDoubleField(env,obj_data,field_fPercolatingAlarm);
		fHighTempAlarm = (*env)->GetDoubleField(env,obj_data,field_fHighTempAlarm);
		fLowTempAlarm = (*env)->GetDoubleField(env,obj_data,field_fLowTempAlarm);

		atg_setalarm_data_in[i].uOilCanNo=uOilCanNo;
		atg_setalarm_data_in[i].fLowWarning=fLowWarning;
		atg_setalarm_data_in[i].fLowAlarm=fLowAlarm;
		atg_setalarm_data_in[i].fHighWarning=fHighWarning;
		atg_setalarm_data_in[i].fHighAlarm=fHighAlarm;
		atg_setalarm_data_in[i].fWaterWarning=fWaterWarning;
		atg_setalarm_data_in[i].fWaterAlarm=fWaterAlarm;
		atg_setalarm_data_in[i].fThiefAlarm=fThiefAlarm;
		atg_setalarm_data_in[i].fLeakAlarm=fLeakAlarm;
		atg_setalarm_data_in[i].fPercolatingAlarm=fPercolatingAlarm;
		atg_setalarm_data_in[i].fHighTempAlarm=fHighTempAlarm;
		atg_setalarm_data_in[i].fLowTempAlarm=fLowTempAlarm;
		LOG_DEBUG("%d|%d|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|",i
			,atg_setalarm_data_in[i].uOilCanNo
			,atg_setalarm_data_in[i].fLowWarning
			,atg_setalarm_data_in[i].fLowAlarm
			,atg_setalarm_data_in[i].fHighWarning
			,atg_setalarm_data_in[i].fHighAlarm
			,atg_setalarm_data_in[i].fWaterWarning
			,atg_setalarm_data_in[i].fWaterAlarm
			,atg_setalarm_data_in[i].fThiefAlarm
			,atg_setalarm_data_in[i].fLeakAlarm
			,atg_setalarm_data_in[i].fPercolatingAlarm
			,atg_setalarm_data_in[i].fHighTempAlarm
			,atg_setalarm_data_in[i].fLowTempAlarm);
	}
	atg_setalarm_in[0].pAlarmSetterData = atg_setalarm_data_in;

	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_ALARMSETTER,atg_setalarm_in,(void*)&pOutputData);
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);	
		if(atg_setalarm_in){
			if(atg_setalarm_in->pAlarmSetterData){
				free(atg_setalarm_in->pAlarmSetterData);
			}	
			free(atg_setalarm_in);
		}
		if(filehandle){
			dlclose(filehandle);
		 }  
		return (*env)->NewStringUTF(env,"1");	
	}
	
	if(atg_setalarm_in){
		if(atg_setalarm_in->pAlarmSetterData){
			free(atg_setalarm_in->pAlarmSetterData);
		}	
		free(atg_setalarm_in);
	}	
    if(filehandle){
		dlclose(filehandle);
	}  
	LOG_DEBUG("end...");	
	return (*env)->NewStringUTF(env,"0");//只能返回0~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  }

/*
 * Class:     ATGDevice_ATGManager
 * Method:    chgOilInfo
 * Signature: (Ljava/util/List;)Ljava/lang/String;
 */
 /*1）	3.6.2.2输入新增油品名称参数
	2）	3.5.1删除08:油罐油品变类的应用类型
	3）	3.6.3.8油罐油品变类删除，其功能有3.6.2.2实现
JNIEXPORT jstring JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_chgOilInfo
  (JNIEnv * env, jobject obj, jobject jlist)
  {
	  	jclass jlistclazz;//list参数
		jmethodID list_size;//list的Size()方法
		jmethodID list_get;//list的get()方法
		jint len;//list的长度
		jobject obj_data;//list里的元素
		jclass cls_data;//list里元素的类型

		//定义待传入到atg_operate接口的参数数组的指针
		struct atg_chgoilinfo_in_t* atg_chgoilinfo_in;
		struct  atg_chgoilinfo_data_in_t *atg_chgoilinfo_data_in;
		int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
		//加载配置信息
		void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
		int ret;
		char *pOutputData;
		
		int i=0;

		jfieldID field_uOilCanNo;
		jfieldID field_strOilNo;
		jfieldID field_strOilName;

		jint uOilCanNo;
		jstring strOilNo;
		jstring strOilName;

		const char * charstrOilNo;
		const char * charstrOilName;

		//获得jlist对象的句柄
		jlistclazz = (*env)->GetObjectClass(env,jlist);
		list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
		list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
		//jlistclazz的method_size方法，即调用list的Size()方法
		len = (*env)->CallIntMethod(env,jlist,list_size);  
		if(len==0) return 2;

		//定义数组的大小（malloc最后要释放）
		atg_chgoilinfo_data_in = (struct atg_chgoilinfo_data_in_t*)malloc(sizeof(struct atg_chgoilinfo_data_in_t) * len);
		atg_chgoilinfo_in = (struct atg_chgoilinfo_in_t*)malloc(sizeof(struct atg_chgoilinfo_in_t));
		atg_chgoilinfo_in[0].uCount = len;
		//LOG_DEBUG("%d\n",len);
		//循环从java取值赋给带传入struct
		for(i=0;i<len;i++)
		{
			obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
			cls_data = (*env)->GetObjectClass(env,obj_data);  
			field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
			field_strOilNo = (*env)->GetFieldID(env,cls_data,"strOilNo","Ljava/lang/String;");
			field_strOilName = (*env)->GetFieldID(env,cls_data,"strOilName","Ljava/lang/String;");

			uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
			strOilNo = (jstring)(*env)->GetObjectField(env,obj_data,field_strOilNo);
			strOilName = (jstring)(*env)->GetObjectField(env,obj_data,field_strOilName);

			charstrOilNo = (*env)->GetStringUTFChars(env,strOilNo, NULL); //转为C的char*
			charstrOilName = (*env)->GetStringUTFChars(env,strOilName, NULL); //转为C的char*

			atg_chgoilinfo_data_in[i].uOilCanNO=uOilCanNo;
			strcpy(atg_chgoilinfo_data_in[i].strOilNo,charstrOilNo);
			strcpy(atg_chgoilinfo_data_in[i].strOilName,charstrOilName);

			LOG_DEBUG("\nC input atg_chgoilinfo_data_in[%d].uOilCanNo:%d",i,atg_chgoilinfo_data_in[i].uOilCanNO);
			LOG_DEBUG("\nC input atg_chgoilinfo_data_in[%d].strOilNo:%s",i,atg_chgoilinfo_data_in[i].strOilNo);			
		}
		atg_chgoilinfo_in[0].pChgOinInfoData = atg_chgoilinfo_data_in;

		//ATG_OPE_CHGOILINFO调用接口,返回的是String，直接返回给java
		//char* ret = atg_operate(ATG_OPE_CHGOILINFO, atg_timestock_in);	
		//return (*env)->NewStringUTF(env,ret);
		//调用接口
		if(filehandle){
			functC = dlsym(filehandle, "atg_operate"); 
			if (functC) 
			{ 
				 ret = (int)functC(ATG_OPE_CHGOILINFO,atg_chgoilinfo_in,pOutputData);
				 LOG_DEBUG("ATG_OPE_CHGOILINFO:%d\n",ret);
			}else{
				LOG_DEBUG("faild\n");
			}
		}
	if(ret)
	{
		LOG_DEBUG("ATG_OPE_CHGOILINFO ret is not 0! ret is:%d\n",ret);
		if(atg_chgoilinfo_in){
			free(atg_chgoilinfo_in);
		}	
		if(atg_chgoilinfo_in->pChgOinInfoData){
			free(atg_chgoilinfo_in->pChgOinInfoData);
		}	
		dlclose(filehandle); 
		return ret;	
	}
	if(atg_chgoilinfo_in){
		free(atg_chgoilinfo_in);
	}	
	if(atg_chgoilinfo_in->pChgOinInfoData){
		free(atg_chgoilinfo_in->pChgOinInfoData);
	}	
	dlclose(filehandle); 
	return (*env)->NewStringUTF(env,pOutputData);
  }
*/
/*
 * Class:     ATGDevice_ATGManager
 * Method:    getCapacityTable
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getCapacityTable
  (JNIEnv * env, jobject obj, jobject obj_jlist)
  {
	jclass cls_list = (*env)->GetObjectClass(env,obj_jlist);
	jmethodID list_get = (*env)->GetMethodID(env,cls_list,"get","(I)Ljava/lang/Object;");
	jmethodID list_size = (*env)->GetMethodID(env,cls_list,"size","()I");
	jint len = (*env)->CallIntMethod(env,obj_jlist,list_size);
	//定义传入struct
	struct atg_getcapacity_in_t *atg_getcapacity_in;
	struct atg_oilcan_data_in_t* atg_oilcan_data_in;

	int i=0;
	int j=0;

	jobject obj_jno;
	jclass cls_jno;
	jmethodID jno_intValue;
	jint jno;
	//输出struct
	struct atg_getcapacity_out_t* atg_getcapacity_out = NULL;
    struct atg_getcapacity_out_t**  patg_getcapacity_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;



	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	//创建ArrayList中的对象
	jclass cls_atg_capacity_data_in_t;
	//获得构造方法
	jmethodID construct_atg_capacity_data_in_t;
	jobject obj_atg_atg_capacity_data_in_t;
	//获取变量
	jfieldID OilCanNO;
	jfieldID strVersion;//atg_capacity_data_in_t新增加了版本号strVersion  yzg20151121修改
	jfieldID uCapacitySize;
	jfieldID pCapacityTableData;
	//初始化ArrayList
	jobject obj_ArrayList2;

	//创建ArrayList中对象包含的对象
	jclass cls_atg_capacitytable_data_in_t;
	//获得构造方法
	jmethodID construct_atg_capacitytable_data_in_t;
	jobject obj_atg_capacitytable_data_in_t;
	//获取变量
	jfieldID uHigh;
	jfieldID fLiter;

	LOG_DEBUG("begin...");	
	atg_getcapacity_in = (struct atg_getcapacity_in_t*)malloc(sizeof(struct atg_getcapacity_in_t));
	atg_oilcan_data_in = (struct atg_oilcan_data_in_t*)malloc(sizeof(struct atg_oilcan_data_in_t)*len);
	atg_getcapacity_in[0].uCount = len;
	for(i=0;i<len;i++)
	{
			obj_jno = (*env)->CallObjectMethod(env,obj_jlist,list_get,i);
			cls_jno = (*env)->GetObjectClass(env,obj_jno);
			jno_intValue = (*env)->GetMethodID(env,cls_jno,"intValue","()I");
			jno = (*env)->CallIntMethod(env,obj_jno,jno_intValue);
			atg_oilcan_data_in[i].uOilCanNo = jno;
			LOG_DEBUG("%d|%d|",i,jno);
	}
	atg_getcapacity_in[0].pOilCanData = atg_oilcan_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_getcapacity_out = &atg_getcapacity_out;
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_GETCAPACITYTABLE,atg_getcapacity_in,(void *)(patg_getcapacity_out));
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}	
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_getcapacity_in){
			if(atg_getcapacity_in->pOilCanData){
				free(atg_getcapacity_in->pOilCanData);
			}
			free(atg_getcapacity_in);
		}
		
		if(atg_getcapacity_out){
			if(atg_getcapacity_out->pCapacityData){
				free(atg_getcapacity_out->pCapacityData);
			}
			free(atg_getcapacity_out);
		}	
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}
	LOG_DEBUG("uCount=[%d]",atg_getcapacity_out[0].uCount);
	for(i=0;i<atg_getcapacity_out[0].uCount;i++)
	 {
		//创建ArrayList中的对象
		cls_atg_capacity_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_capacity_data_in_t");
		//获得构造方法
		construct_atg_capacity_data_in_t = (*env)->GetMethodID(env,cls_atg_capacity_data_in_t,"<init>","()V");
		obj_atg_atg_capacity_data_in_t = (*env)->NewObject(env,cls_atg_capacity_data_in_t,construct_atg_capacity_data_in_t,"");
		//获取变量
		OilCanNO = (*env)->GetFieldID(env,cls_atg_capacity_data_in_t,"uOilCanNO","I");
		strVersion = (*env)->GetFieldID(env,cls_atg_capacity_data_in_t,"strVersion","Ljava/lang/String;");
		uCapacitySize = (*env)->GetFieldID(env,cls_atg_capacity_data_in_t,"uCapacitySize","I");
		pCapacityTableData = (*env)->GetFieldID(env,cls_atg_capacity_data_in_t,"pCapacityTableData","Ljava/util/List;");
		//初始化ArrayList
		obj_ArrayList2 = (*env)->NewObject(env,cls_ArrayList,construct,"");
		for(j=0;j<atg_getcapacity_out[0].pCapacityData[i].uCapacitySize;j++){
			//创建ArrayList中对象包含的对象
			cls_atg_capacitytable_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_capacitytable_data_in_t");
			//获得构造方法
			construct_atg_capacitytable_data_in_t = (*env)->GetMethodID(env,cls_atg_capacitytable_data_in_t,"<init>","()V");
			obj_atg_capacitytable_data_in_t = (*env)->NewObject(env,cls_atg_capacitytable_data_in_t,construct_atg_capacitytable_data_in_t,"");
			//获取变量
			uHigh = (*env)->GetFieldID(env,cls_atg_capacitytable_data_in_t,"uHigh","I");
			fLiter = (*env)->GetFieldID(env,cls_atg_capacitytable_data_in_t,"fLiter","D");
			//给java类赋值
			(*env)->SetIntField(env,obj_atg_capacitytable_data_in_t,uHigh,atg_getcapacity_out[0].pCapacityData[i].pCapacityTableData[j].uHigh);
			(*env)->SetDoubleField(env,obj_atg_capacitytable_data_in_t,fLiter,atg_getcapacity_out[0].pCapacityData[i].pCapacityTableData[j].fLiter);
			LOG_DEBUG("%d|%d|%d|%f|",i,j
				,atg_getcapacity_out[0].pCapacityData[i].pCapacityTableData[j].uHigh
				,atg_getcapacity_out[0].pCapacityData[i].pCapacityTableData[j].fLiter);
			//放入list
			(*env)->CallBooleanMethod(env,obj_ArrayList2,arrayList_add,obj_atg_capacitytable_data_in_t);			
		}		
		//赋值
		(*env)->SetIntField(env,obj_atg_atg_capacity_data_in_t,OilCanNO,atg_getcapacity_out[0].pCapacityData[i].uOilCanNo);
		(*env)->SetIntField(env,obj_atg_atg_capacity_data_in_t,uCapacitySize,atg_getcapacity_out[0].pCapacityData[i].uCapacitySize);	
		(*env)->SetObjectField(env,obj_atg_atg_capacity_data_in_t,strVersion,(*env)->NewStringUTF(env,atg_getcapacity_out[0].pCapacityData[i].strVersion));		
		(*env)->SetObjectField(env,obj_atg_atg_capacity_data_in_t,pCapacityTableData,obj_ArrayList2);
		LOG_DEBUG("%d|%d|%s|%d",i
			,atg_getcapacity_out[0].pCapacityData[i].uOilCanNo
			,atg_getcapacity_out[0].pCapacityData[i].strVersion
			,atg_getcapacity_out[0].pCapacityData[i].uCapacitySize);
		//放入list
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_atg_capacity_data_in_t);
	}
	if(atg_getcapacity_in->pOilCanData){
		free(atg_getcapacity_in->pOilCanData);
	}
	if(atg_getcapacity_in){
		free(atg_getcapacity_in);
	}
	if(atg_getcapacity_out){
		if(atg_getcapacity_out->pCapacityData){
			free(atg_getcapacity_out->pCapacityData);
		}
		free(atg_getcapacity_out);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");	
	return obj_ArrayList;
  }


/*
 * Class:     ATGDevice_ATGManager
 * Method:    setCapacityTable
 * Signature: (LATGDevice/atg_capacity_data_in_t;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_setCapacityTable
  (JNIEnv *env, jobject obj, jobject jobj)
  {
	//获得jobj对象的句柄
	jclass jobjclazz = (*env)->GetObjectClass(env,jobj);

	jfieldID field_uOilCanNO = (*env)->GetFieldID(env,jobjclazz,"uOilCanNO","I");
	jfieldID field_strVersion = (*env)->GetFieldID(env,jobjclazz,"strVersion","Ljava/lang/String;");
	jfieldID field_uCapacitySize = (*env)->GetFieldID(env,jobjclazz,"uCapacitySize","I");
	jfieldID field_pCapacityTableData = (*env)->GetFieldID(env,jobjclazz,"pCapacityTableData","Ljava/util/List;");

	jint uOilCanNO = (*env)->GetIntField(env,jobj,field_uOilCanNO);
	jstring strVersion = (jstring)(*env)->GetObjectField(env,jobj,field_strVersion);
	jint uCapacitySize = (*env)->GetIntField(env,jobj,field_uCapacitySize);
	jobject pCapacityTableData = (*env)->GetObjectField(env,jobj,field_pCapacityTableData);
	jclass cls_pCapacityTableData = (*env)->GetObjectClass(env,pCapacityTableData);	
	jmethodID method_add = (*env)->GetMethodID(env,cls_pCapacityTableData,"get","(I)Ljava/lang/Object;");
	jmethodID method_size = (*env)->GetMethodID(env,cls_pCapacityTableData,"size","()I");
	//传入参数
	struct atg_setcapacity_in_t* atg_setcapacity_in;
	struct atg_capacity_data_in_t* atg_capacity_data_in;
	struct atg_capacitytable_data_in_t* atg_capacitytable_data_in;
	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	char *pOutputData;

	jint len=0;
	jobject obj_atg_capacitytable_data_in;
	jclass cls_atg_capacitytable_data_in;

	jfieldID field_uHigh;
	jfieldID field_fLiter;
	jint uHigh;
	jdouble fLiter;
	int i=0;

	LOG_DEBUG("begin...");	
	atg_capacity_data_in = (struct atg_capacity_data_in_t*)malloc(sizeof(struct atg_capacity_data_in_t));	
	atg_setcapacity_in = (struct atg_setcapacity_in_t*)malloc(sizeof(struct atg_setcapacity_in_t)); 

	atg_capacity_data_in[0].uOilCanNo = uOilCanNO;
	strcpy(atg_capacity_data_in[0].strVersion,(*env)->GetStringUTFChars(env,strVersion, NULL));
	LOG_DEBUG("%d|%d|",atg_capacity_data_in[0].uOilCanNo,atg_capacity_data_in[0].uCapacitySize);
    len = (*env)->CallIntMethod(env,pCapacityTableData,method_size);
	atg_capacitytable_data_in = (struct atg_capacitytable_data_in_t*)malloc(sizeof(struct atg_capacitytable_data_in_t)*len);
	for(i=0;i<len;i++)
	 {
			obj_atg_capacitytable_data_in = (*env)->CallObjectMethod(env,pCapacityTableData,method_add,i);
			cls_atg_capacitytable_data_in = (*env)->GetObjectClass(env,obj_atg_capacitytable_data_in);
			field_uHigh = (*env)->GetFieldID(env,cls_atg_capacitytable_data_in,"uHigh","I");
			field_fLiter = (*env)->GetFieldID(env,cls_atg_capacitytable_data_in,"fLiter","D");
			uHigh = (*env)->GetIntField(env,obj_atg_capacitytable_data_in,field_uHigh);
			fLiter = (*env)->GetDoubleField(env,obj_atg_capacitytable_data_in,field_fLiter);
			atg_capacitytable_data_in[i].uHigh = uHigh;
			atg_capacitytable_data_in[i].fLiter = fLiter;
			LOG_DEBUG("%d|%d|%f|",i,atg_capacitytable_data_in[i].uHigh,atg_capacitytable_data_in[i].fLiter);
	}
	atg_capacity_data_in[0].pCapacityTableData = atg_capacitytable_data_in;
	atg_capacity_data_in[0].uCapacitySize = len;
	atg_setcapacity_in[0].pCapacityData = atg_capacity_data_in;
	atg_setcapacity_in[0].uCount = 1;//下发只能传一个罐号，这里给出默认值。
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate");
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
			 ret = (int)functC(ATG_OPE_SETCAPACITYTABLE,atg_setcapacity_in,(void*)&pOutputData);
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}	
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_setcapacity_in){
			if(atg_setcapacity_in->pCapacityData){
				if(atg_setcapacity_in->pCapacityData->pCapacityTableData){
					free(atg_setcapacity_in->pCapacityData->pCapacityTableData);
				}			
				free(atg_setcapacity_in->pCapacityData);
			}
			free(atg_setcapacity_in);
		}
		if(filehandle){
			dlclose(filehandle);
		}
        return (*env)->NewStringUTF(env,"1");
	}
	if(atg_setcapacity_in){
		if(atg_setcapacity_in->pCapacityData){
			if(atg_setcapacity_in->pCapacityData->pCapacityTableData){
				free(atg_setcapacity_in->pCapacityData->pCapacityTableData);
			}			
			free(atg_setcapacity_in->pCapacityData);
		}
		free(atg_setcapacity_in);
	}
	if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");	
	return (*env)->NewStringUTF(env,"0");
  }


	/*
 * Class:     ATGDevice_ATGManager
 * Method:    startLiquid
 * Signature: (Ljava/util/List;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_startLiquid
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_startliquid_in_t* atg_startliquid_in;
	struct atg_startliquid_data_in_t *atg_startliquid_data_in;	

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	char *pOutputData;

	int i=0;

	jfieldID field_uOilCanNo;
	jfieldID field_uTestType;
	jfieldID field_strDataTime;
	jfieldID field_uTestDuration;
	jfieldID field_fTestRate;
	jint uOilCanNo;
	jint uTestType;
	jstring strDataTime;
	jint uTestDuration;
	jdouble fTestRate;
	const char * charDataTime;

	LOG_DEBUG("begin...");	
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return NULL;
 
	//定义数组的大小（malloc最后要释放）
	atg_startliquid_data_in = (struct atg_startliquid_data_in_t*)malloc(sizeof(struct atg_startliquid_data_in_t) * len);
	atg_startliquid_in = (struct atg_startliquid_in_t*)malloc(sizeof(struct atg_startliquid_in_t));
	atg_startliquid_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	//循环从java取值赋给带传入struct	
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNo","I");
		field_uTestType = (*env)->GetFieldID(env,cls_data,"uTestType","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uTestDuration = (*env)->GetFieldID(env,cls_data,"uTestDuration","I");
		field_fTestRate = (*env)->GetFieldID(env,cls_data,"fTestRate","D");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		uTestType = (*env)->GetIntField(env,obj_data,field_uTestType);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uTestDuration = (*env)->GetIntField(env,obj_data,field_uTestDuration);
		fTestRate = (*env)->GetDoubleField(env,obj_data,field_fTestRate);

		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*
		strcpy(atg_startliquid_data_in[i].strDateTime,charDataTime);
		
		atg_startliquid_data_in[i].uOilCanNo = uOilCanNo;
		atg_startliquid_data_in[i].uTestType = uTestType;
		atg_startliquid_data_in[i].uTestDuration = uTestDuration;
		atg_startliquid_data_in[i].fTestRate = fTestRate;
	
		LOG_DEBUG("%d|%d|%d|%f|",uOilCanNo,uTestType,uTestDuration,fTestRate);
	}		
	atg_startliquid_in[0].pLiquidData = atg_startliquid_data_in;
	
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
			LOG_DEBUG("func|begin");
			 ret = (int)functC(ATG_OPE_STARTLIQUID,atg_startliquid_in,(void*)&pOutputData);
			LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);	
		if(atg_startliquid_in->pLiquidData){
			free(atg_startliquid_in->pLiquidData);
		}	
		if(atg_startliquid_in){
			free(atg_startliquid_in);
		}
		if(filehandle){
			dlclose(filehandle);
		}
		return (*env)->NewStringUTF(env,pOutputData);
	}
	if(atg_startliquid_in->pLiquidData){
		free(atg_startliquid_in->pLiquidData);
	}	
	if(atg_startliquid_in){
		free(atg_startliquid_in);	
	}	
	if(filehandle){
		dlclose(filehandle);
	}
	if(pOutputData){
		return NULL;
	} 
	if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");	
	return (*env)->NewStringUTF(env,pOutputData);
}

/*
 * Class:     ATGDevice_ATGManager
 * Method:    stopLiquid
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_stopLiquid
  (JNIEnv *env, jobject obj, jobject jlist)
   {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	int i=0;

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_stopliquid_in_t* atg_stopliquid_in;
	struct atg_stopliquid_data_in_t *atg_stopliquid_data_in;

	jfieldID field_uOilCanNo;
	jfieldID field_uTestType;

	jint uOilCanNo;
	jint uTestType;
	//定义返回struct
	struct atg_stopliquid_out_t* atg_stopliquid_out = NULL;
    struct atg_stopliquid_out_t**  patg_stopliquid_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;


	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID

	jfieldID out_uOilCanNo;
	jfieldID uRevealStatus;
	jfieldID fRevealRate;
	jfieldID strStartDate;
	jfieldID strStartTime;
	jfieldID fStartOilHeight;
	jfieldID fStartWaterHeight;
	jfieldID fStartOilTemp;
	jfieldID fStartOilTemp1 ;
	jfieldID fStartOilTemp2;
	jfieldID fStartOilTemp3;
	jfieldID fStartOilTemp4 ;
	jfieldID fStartOilTemp5;
	jfieldID fStartOilCubage;
	jfieldID fStartOilStandCubage;
	jfieldID fStartEmptyCubage;
	jfieldID fStartWaterBulk;
	jfieldID strEndDate;
	jfieldID strEndTime;
	jfieldID fEndOilHeight;
	jfieldID fEndWaterHeight;
	jfieldID fEndOilTemp;
	jfieldID fEndOilTemp1;
	jfieldID fEndOilTemp2;
	jfieldID fEndOilTemp3;
	jfieldID fEndOilTemp4;
	jfieldID fEndOilTemp5;
	jfieldID fEndOilCubage;
	jfieldID fEndOilStandCubage;
	jfieldID fEndEmptyCubage;
	jfieldID fEndWaterBulk;
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	LOG_DEBUG("begin...");	
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;

	//定义数组的大小（malloc最后要释放）
	atg_stopliquid_data_in = (struct atg_stopliquid_data_in_t*)malloc(sizeof(struct atg_stopliquid_data_in_t) * len);
	atg_stopliquid_in = (struct atg_stopliquid_in_t*)malloc(sizeof(struct atg_stopliquid_in_t*));
	atg_stopliquid_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNo","I");
		field_uTestType = (*env)->GetFieldID(env,cls_data,"uTestType","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		uTestType = (*env)->GetIntField(env,obj_data,field_uTestType);
		
		atg_stopliquid_data_in[i].uOilCanNo = uOilCanNo;
		atg_stopliquid_data_in[i].uTestType = uTestType;
		LOG_DEBUG("%d|%d|%d|",i,uOilCanNo,uTestType);
	}		
	atg_stopliquid_in[0].pLiquidData = atg_stopliquid_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_stopliquid_out = &atg_stopliquid_out;
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_STOPLIQUID,atg_stopliquid_in,(void *)(patg_stopliquid_out));
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		
		if(atg_stopliquid_in->pLiquidData){
			free(atg_stopliquid_in->pLiquidData);
		}
		if(atg_stopliquid_in){
			free(atg_stopliquid_in);
		}
		if(atg_stopliquid_out){
			if(atg_stopliquid_out->pLiquidData){
				free(atg_stopliquid_out->pLiquidData);
			}
			free(atg_stopliquid_out);
		}	
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}

	
	//循环给返回的list赋值
	for(i=0;i<atg_stopliquid_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_liquidreport_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID
		out_uOilCanNo = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNo","I");
		uRevealStatus = (*env)->GetFieldID(env,cls_atg_data_in_t,"uRevealStatus","I");
		fRevealRate = (*env)->GetFieldID(env,cls_atg_data_in_t,"fRevealRate","D");
		strStartDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartDate","Ljava/lang/String;");
		strStartTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartTime","Ljava/lang/String;");
		fStartOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilHeight","D");
		fStartWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartWaterHeight","D");
		fStartOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp","D");
		fStartOilTemp1  = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp1","D");
		fStartOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp2","D");
		fStartOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp3","D");
		fStartOilTemp4  = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp4","D");
		fStartOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp5","D");
		fStartOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilCubage","D");
		fStartOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilStandCubage","D");
		fStartEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartEmptyCubage","D");
		fStartWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartWaterBulk","D");
		strEndDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndDate","Ljava/lang/String;");
		strEndTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndTime","Ljava/lang/String;");
		fEndOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilHeight","D");
		fEndWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndWaterHeight","D");
		fEndOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp","D");
		fEndOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp1","D"); 
		fEndOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp2","D");
		fEndOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp3","D");
		fEndOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp4","D");
		fEndOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp5","D");
		fEndOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilCubage","D");
		fEndOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilStandCubage","D");
		fEndEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndEmptyCubage","D");
		fEndWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndWaterBulk","D");
		
		//赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,out_uOilCanNo,atg_stopliquid_out[0].pLiquidData[i].uOilCanNo);
		(*env)->SetIntField(env,obj_atg_data_in_t,uRevealStatus,atg_stopliquid_out[0].pLiquidData[i].uRevealStatus);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fRevealRate,atg_stopliquid_out[0].pLiquidData[i].fRevealRate);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartDate,(*env)->NewStringUTF(env,atg_stopliquid_out[0].pLiquidData[i].strStartDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartTime,(*env)->NewStringUTF(env,atg_stopliquid_out[0].pLiquidData[i].strStartTime));

		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilHeight,atg_stopliquid_out[0].pLiquidData[i].fStartOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartWaterHeight,atg_stopliquid_out[0].pLiquidData[i].fStartWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp1,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp2,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp3,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp4,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp5,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilCubage,atg_stopliquid_out[0].pLiquidData[i].fStartOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilStandCubage,atg_stopliquid_out[0].pLiquidData[i].fStartOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartEmptyCubage,atg_stopliquid_out[0].pLiquidData[i].fStartEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartWaterBulk,atg_stopliquid_out[0].pLiquidData[i].fStartWaterBulk);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndDate,(*env)->NewStringUTF(env,atg_stopliquid_out[0].pLiquidData[i].strEndDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndTime,(*env)->NewStringUTF(env,atg_stopliquid_out[0].pLiquidData[i].strEndTime));
		
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilHeight,atg_stopliquid_out[0].pLiquidData[i].fEndOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndWaterHeight,atg_stopliquid_out[0].pLiquidData[i].fEndWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp1,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp2,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp3,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp4,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp5,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilCubage,atg_stopliquid_out[0].pLiquidData[i].fEndOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilStandCubage,atg_stopliquid_out[0].pLiquidData[i].fEndOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndEmptyCubage,atg_stopliquid_out[0].pLiquidData[i].fEndEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndWaterBulk,atg_stopliquid_out[0].pLiquidData[i].fEndWaterBulk);
		LOG_DEBUG("%d|%d|%d|%s|%s|%f|%f|%f|%f|%f|%f|%f|%f|",i
			,atg_stopliquid_out[0].pLiquidData[i].uOilCanNo
			,atg_stopliquid_out[0].pLiquidData[i].uRevealStatus
			,atg_stopliquid_out[0].pLiquidData[i].strStartDate
			,atg_stopliquid_out[0].pLiquidData[i].strStartTime
			,atg_stopliquid_out[0].pLiquidData[i].fStartOilHeight
			,atg_stopliquid_out[0].pLiquidData[i].fStartWaterHeight
			,atg_stopliquid_out[0].pLiquidData[i].fStartOilCubage
			,atg_stopliquid_out[0].pLiquidData[i].fStartOilStandCubage
			,atg_stopliquid_out[0].pLiquidData[i].fStartEmptyCubage
			,atg_stopliquid_out[0].pLiquidData[i].fStartWaterBulk
			,atg_stopliquid_out[0].pLiquidData[i].fStartOilTemp
			,atg_stopliquid_out[0].pLiquidData[i].fEndOilTemp);
		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
	if(atg_stopliquid_in->pLiquidData){
		free(atg_stopliquid_in->pLiquidData);
	}
	if(atg_stopliquid_in){
		free(atg_stopliquid_in);
	}
	if(atg_stopliquid_out){
		if(atg_stopliquid_out->pLiquidData){
			free(atg_stopliquid_out->pLiquidData);
		}
		free(atg_stopliquid_out);
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");	
	return obj_ArrayList;
  }


 /*
 * Class:     ATGDevice_ATGManager
 * Method:    liquidReport
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_liquidReport
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_liquidreport_in_t* atg_liquidreport_in;
	struct atg_liquidreport_data_in_t *atg_liquidreport_data_in;
	//定义返回struct
	struct atg_liquidreport_out_t* atg_liquidreport_out = NULL;
    struct atg_liquidreport_out_t**  patg_liquidreport_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;

	jfieldID field_uOilCanNo;
	jfieldID field_strDataTime;
	jfieldID field_uTestType;
	jfieldID field_uReqCount;

	jint uOilCanNo;
	jstring strDataTime;
	jint uTestType;
	jint uReqCount;

	const char * charDataTime;
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");
	
	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID
	jfieldID uOilCanNO;
	jfieldID uRevealStatus;
	jfieldID fRevealRate;
	jfieldID strStartDate;
	jfieldID strStartTime;
	jfieldID fStartOilHeight;
	jfieldID fStartWaterHeight;
	jfieldID fStartOilTemp;
	jfieldID fStartOilTemp1 ;
	jfieldID fStartOilTemp2;
	jfieldID fStartOilTemp3;
	jfieldID fStartOilTemp4 ;
	jfieldID fStartOilTemp5;
	jfieldID fStartOilCubage;
	jfieldID fStartOilStandCubage;
	jfieldID fStartEmptyCubage;
	jfieldID fStartWaterBulk;
	jfieldID strEndDate;
	jfieldID strEndTime;
	jfieldID fEndOilHeight;
	jfieldID fEndWaterHeight;
	jfieldID fEndOilTemp;
	jfieldID fEndOilTemp1;
	jfieldID fEndOilTemp2;
	jfieldID fEndOilTemp3;
	jfieldID fEndOilTemp4;
	jfieldID fEndOilTemp5;
	jfieldID fEndOilCubage;
	jfieldID fEndOilStandCubage;
	jfieldID fEndEmptyCubage;
	jfieldID fEndWaterBulk ;
	int i=0;

	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;
	//定义数组的大小（malloc最后要释放）
	atg_liquidreport_data_in = (struct atg_liquidreport_data_in_t*)malloc(sizeof(struct atg_liquidreport_data_in_t) * len);
	atg_liquidreport_in = (struct atg_liquidreport_in_t*)malloc(sizeof(struct atg_liquidreport_in_t*));
	atg_liquidreport_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);	
  //循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNo","I");
		field_strDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uTestType = (*env)->GetFieldID(env,cls_data,"uTestType","I");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");

		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strDataTime = (jstring)(*env)->GetObjectField(env,obj_data,field_strDataTime);
		uTestType = (*env)->GetIntField(env,obj_data,field_uTestType);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);

		charDataTime = (*env)->GetStringUTFChars(env,strDataTime, NULL); //转为C的char*
		strcpy(atg_liquidreport_data_in[i].strDateTime,charDataTime);
		atg_liquidreport_data_in[i].uOilCanNo = uOilCanNo;
		atg_liquidreport_data_in[i].uTestType = uTestType;
		atg_liquidreport_data_in[i].uReqCount = uReqCount;
		
	}		
		atg_liquidreport_in[0].pLiquidData = atg_liquidreport_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_liquidreport_out = &atg_liquidreport_out;
		     ret = (int)functC(ATG_OPE_LIQUIDREPORT,atg_liquidreport_in,(void *)(patg_liquidreport_out));
		}else{
			LOG_DEBUG("faild\n");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ATG_OPE_LIQUIDREPORT ret is not 0! ret is:%d\n",ret);
		if(atg_liquidreport_in->pLiquidData){
			free(atg_liquidreport_in->pLiquidData);
		}
		if(atg_liquidreport_in){
			free(atg_liquidreport_in);
		}
		if(atg_liquidreport_out){
			if(atg_liquidreport_out->pLiquidData){
				free(atg_liquidreport_out->pLiquidData);
			}
			free(atg_liquidreport_out);
		}
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}

	//循环给返回的list赋值
	for(i=0;i<atg_liquidreport_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_liquidreport_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID

		uOilCanNO = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNo","I");
		uRevealStatus = (*env)->GetFieldID(env,cls_atg_data_in_t,"uRevealStatus","I");
		fRevealRate = (*env)->GetFieldID(env,cls_atg_data_in_t,"fRevealRate","D");
		strStartDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartDate","Ljava/lang/String;");
		strStartTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strStartTime","Ljava/lang/String;");
		fStartOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilHeight","D");
		fStartWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartWaterHeight","D");
		fStartOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp","D");
		fStartOilTemp1  = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp1","D");
		fStartOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp2","D");
		fStartOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp3","D");
		fStartOilTemp4  = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp4","D");
		fStartOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilTemp5","D");
		fStartOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilCubage","D");
		fStartOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartOilStandCubage","D");
		fStartEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartEmptyCubage","D");
		fStartWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fStartWaterBulk","D");
		strEndDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndDate","Ljava/lang/String;");
		strEndTime = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEndTime","Ljava/lang/String;");
		fEndOilHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilHeight","D");
		fEndWaterHeight = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndWaterHeight","D");
		fEndOilTemp = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp","D");
		fEndOilTemp1 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp1","D"); 
		fEndOilTemp2 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp2","D");
		fEndOilTemp3 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp3","D");
		fEndOilTemp4 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp4","D");
		fEndOilTemp5 = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilTemp5","D");
		fEndOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilCubage","D");
		fEndOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndOilStandCubage","D");
		fEndEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndEmptyCubage","D");
		fEndWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEndWaterBulk","D");
		
			//给String赋值
		
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNO,atg_liquidreport_out[0].pLiquidData[i].uOilCanNo);
		(*env)->SetIntField(env,obj_atg_data_in_t,uRevealStatus,atg_liquidreport_out[0].pLiquidData[i].uRevealStatus);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fRevealRate,atg_liquidreport_out[0].pLiquidData[i].fRevealRate);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartDate,(*env)->NewStringUTF(env,atg_liquidreport_out[0].pLiquidData[i].strStartDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strStartTime,(*env)->NewStringUTF(env,atg_liquidreport_out[0].pLiquidData[i].strStartTime));

		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilHeight,atg_liquidreport_out[0].pLiquidData[i].fStartOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartWaterHeight,atg_liquidreport_out[0].pLiquidData[i].fStartWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp1,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp2,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp3,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp4,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilTemp5,atg_liquidreport_out[0].pLiquidData[i].fStartOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilCubage,atg_liquidreport_out[0].pLiquidData[i].fStartOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartOilStandCubage,atg_liquidreport_out[0].pLiquidData[i].fStartOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartEmptyCubage,atg_liquidreport_out[0].pLiquidData[i].fStartEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fStartWaterBulk,atg_liquidreport_out[0].pLiquidData[i].fStartWaterBulk);
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndDate,(*env)->NewStringUTF(env,atg_liquidreport_out[0].pLiquidData[i].strEndDate));
		(*env)->SetObjectField(env,obj_atg_data_in_t,strEndTime,(*env)->NewStringUTF(env,atg_liquidreport_out[0].pLiquidData[i].strEndTime));
		
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilHeight,atg_liquidreport_out[0].pLiquidData[i].fEndOilHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndWaterHeight,atg_liquidreport_out[0].pLiquidData[i].fEndWaterHeight);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp1,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp1);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp2,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp2);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp3,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp3);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp4,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp4);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilTemp5,atg_liquidreport_out[0].pLiquidData[i].fEndOilTemp5);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilCubage,atg_liquidreport_out[0].pLiquidData[i].fEndOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndOilStandCubage,atg_liquidreport_out[0].pLiquidData[i].fEndOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndEmptyCubage,atg_liquidreport_out[0].pLiquidData[i].fEndEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEndWaterBulk,atg_liquidreport_out[0].pLiquidData[i].fEndWaterBulk);

		LOG_DEBUG("\nC output~~:atg_liquidreport_out[0].pLiquidData[%d].uOilCanNO:%d",i,atg_liquidreport_out[0].pLiquidData[i].uOilCanNo);
		LOG_DEBUG("\nC output~~:atg_liquidreport_out[0].pLiquidData[%d].uRevealStatus:%d",i,atg_liquidreport_out[0].pLiquidData[i].uRevealStatus);
		LOG_DEBUG("\nC output~~:atg_liquidreport_out[0].pLiquidData[%d].strStartDate:%s",i,atg_liquidreport_out[0].pLiquidData[i].strStartDate);
		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
	if(atg_liquidreport_in->pLiquidData){
		free(atg_liquidreport_in->pLiquidData);
	}
	if(atg_liquidreport_in){
		free(atg_liquidreport_in);
	}
	if(atg_liquidreport_out){
		if(atg_liquidreport_out->pLiquidData){
			free(atg_liquidreport_out->pLiquidData);
		}
		free(atg_liquidreport_out);
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	return obj_ArrayList;
  }



/*
 * Class:     ATGDevice_ATGManager
 * Method:    getDeviceInfo
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getDeviceInfo
  (JNIEnv *env, jobject obj, jobject jlist)
  {  
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_device_in_t* atg_device_in;
	struct  atg_oilcan_data_in_t *atg_oilcan_data_in;
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	int i=0;
	jmethodID jno_intValue=NULL;
	jint jno=0;
	jclass cls_atg_data_in_t;
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	jclass cls_atg_device_in_t;
	jmethodID construct_atg_device_in_t;
	jobject obj_atg_device_in_t;
	jfieldID uOilCanNo;
	jfieldID strProbeNo ;
	jfieldID strProbeModel;
	jfieldID strSysVersion;
	jfieldID strMakeDate;
	jfieldID strDeviceModel;
	jfieldID strEquipCode;
	jfieldID uRetCount;
	jfieldID pDeviceData;

	//定义接口返回struct
	struct atg_device_out_t* atg_device_out = NULL;
    struct atg_device_out_t**  patg_device_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");	

	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_atg_data_in_t;

	//定义数组的大小（malloc最后要释放）
	atg_oilcan_data_in = (struct atg_oilcan_data_in_t*)malloc(sizeof(struct atg_oilcan_data_in_t) * len);
	atg_device_in = (struct atg_device_in_t*)malloc(sizeof(struct atg_device_in_t));
	atg_device_in[0].uCount = len;
	//循环
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  

		jno_intValue = (*env)->GetMethodID(env,cls_data,"intValue","()I");
		jno = (*env)->CallIntMethod(env,obj_data,jno_intValue);

		atg_oilcan_data_in[i].uOilCanNo = jno;
		LOG_DEBUG("%d|%d|",i,atg_oilcan_data_in[i].uOilCanNo);
	}
	atg_device_in[0].pOilCanData = atg_oilcan_data_in;
	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_device_out = &atg_device_out;
			LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_GETDEVICEINFO,atg_device_in,(void *)(patg_device_out));
			LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_device_in->pOilCanData){
			free(atg_device_in->pOilCanData);
		}
		if(atg_device_in){
			free(atg_device_in);
		}
		if(filehandle){
			dlclose(filehandle);
		} 
		return NULL;	
	}
	//循环给返回的list赋值
	//创建ArrayList中的对象
	cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_device_out_t");
	//获得构造方法
	construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
	obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
	strDeviceModel = (*env)->GetFieldID(env,cls_atg_data_in_t,"strDeviceModel","Ljava/lang/String;");
	strEquipCode = (*env)->GetFieldID(env,cls_atg_data_in_t,"strEquipCode","Ljava/lang/String;");
	strSysVersion = (*env)->GetFieldID(env,cls_atg_data_in_t,"strSysVersion","Ljava/lang/String;");
	strMakeDate = (*env)->GetFieldID(env,cls_atg_data_in_t,"strMakeDate","Ljava/lang/String;");
	pDeviceData = (*env)->GetFieldID(env,cls_atg_data_in_t,"pDeviceData","Ljava/util/List;");
	uRetCount = (*env)->GetFieldID(env,cls_atg_data_in_t,"uRetCount","I");
	for(i=0;i<atg_device_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_device_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_device_data_out_t");
		//获得构造方法
		construct_atg_device_in_t = (*env)->GetMethodID(env,cls_atg_device_in_t,"<init>","()V");
		obj_atg_device_in_t = (*env)->NewObject(env,cls_atg_device_in_t,construct_atg_device_in_t,"");
		//获取变量ID
		uOilCanNo = (*env)->GetFieldID(env,cls_atg_device_in_t,"uOilCanNo","I");
		strProbeNo = (*env)->GetFieldID(env,cls_atg_device_in_t,"strProbeNo","Ljava/lang/String;");
		strProbeModel = (*env)->GetFieldID(env,cls_atg_device_in_t,"strProbeModel","Ljava/lang/String;");
		//给String赋值
		(*env)->SetIntField(env,obj_atg_device_in_t,uOilCanNo,atg_device_out[0].pDeviceData[i].uOilCanNo);
		(*env)->SetObjectField(env,obj_atg_device_in_t,strProbeNo,(*env)->NewStringUTF(env,atg_device_out[0].pDeviceData[i].strProbeNo));
		(*env)->SetObjectField(env,obj_atg_device_in_t,strProbeModel,ctojstring(env,atg_device_out[0].pDeviceData[i].strProbeModel));
		LOG_DEBUG("%d|%d|%s|%s|",i
			,atg_device_out[0].pDeviceData[i].uOilCanNo
			,atg_device_out[0].pDeviceData[i].strProbeNo
			,atg_device_out[0].pDeviceData[i].strProbeModel);
		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_device_in_t);
	}
	(*env)->SetIntField(env,obj_atg_data_in_t,uRetCount,atg_device_out[0].uRetCount);
	(*env)->SetObjectField(env,obj_atg_data_in_t,strDeviceModel,ctojstring(env,atg_device_out[0].strDeviceModel));
	(*env)->SetObjectField(env,obj_atg_data_in_t,strEquipCode,(*env)->NewStringUTF(env,atg_device_out[0].strEquipCode));
	(*env)->SetObjectField(env,obj_atg_data_in_t,strSysVersion,(*env)->NewStringUTF(env,atg_device_out[0].strSysVersion));
	(*env)->SetObjectField(env,obj_atg_data_in_t,strMakeDate,(*env)->NewStringUTF(env,atg_device_out[0].strMakeDate));	

	LOG_DEBUG("%d|%s|%s|%s|%s|"
		,atg_device_out[0].uRetCount
		,atg_device_out[0].strDeviceModel
		,atg_device_out[0].strEquipCode
		,atg_device_out[0].strSysVersion
		,atg_device_out[0].strMakeDate);

	(*env)->SetObjectField(env,obj_atg_data_in_t,pDeviceData,obj_ArrayList);	

	if(atg_device_in->pOilCanData){
		free(atg_device_in->pOilCanData);
	}
	if(atg_device_in){
		free(atg_device_in);
	}
	if(atg_device_out){
		if(atg_device_out->pDeviceData){
			free(atg_device_out->pDeviceData);
		}
		free(atg_device_out);
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_atg_data_in_t;
  }

/*
 * Class:     ATGDevice_ATGManager
 * Method:    HightOLiter
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_HightOLiter
  (JNIEnv *env, jobject obj, jobject jlist)
  {
    jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_hightoliter_in_t* atg_hightoliter_in;
	int i=0;
	jfieldID field_uCount;
	jfieldID field_fTotalHeight;
	jfieldID field_fWaterHeight;
	jfieldID field_fOilTemp;
	jfieldID field_fOilTemp1;
	jfieldID field_fOilTemp2;
	jfieldID field_fOilTemp3;
	jfieldID field_fOilTemp4;
	jfieldID field_fOilTemp5;

	jint uCount;
	jdouble fTotalHeight;
	jdouble fWaterHeight;
	jdouble fOilTemp;
	jdouble fOilTemp1;
	jdouble fOilTemp2;
	jdouble fOilTemp3;
	jdouble fOilTemp4;
	jdouble fOilTemp5;
	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_capacity_data_in_t *atg_capacity_data_in;

	jclass pCapacityDataclazz;//list参数
	jmethodID pCapacityData_size;//list的Size()方法
	jmethodID pCapacityData_get;//list的get()方法

	jfieldID field_pCapacityData;
	jobject pCapacityData;

	int j=0;
	jfieldID field_uOilCanNo;
	jfieldID field_uCapacitySize;

	jint uOilCanNo;
	jint uCapacitySize;
	//定义待传入到atg_operate接口的参数数组的指针	
	struct atg_capacitytable_data_in_t *atg_capacitytable_data_in;
	jfieldID field_uHigh;
	jfieldID field_fLiter;

	jint uHigh;
	jdouble fLiter;
	jfieldID field_pCapacityTableData;
	jobject pCapacityTableData;

	jclass pCapacityTableDataclazz;//list参数
	jmethodID pCapacityTableData_size;//list的Size()方法
	jmethodID pCapacityTableData_get;//list的get()方法
	int k=0;
	 //定义返回struct	
	struct atg_hightolite_out_t* atg_hightoliter_out = NULL;
    struct atg_hightolite_out_t**  patg_hightoliter_out = NULL;

	int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	
	//开始实现返回List
	//创建java的ArrayList对象
	jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
	//获得构造函数
	jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
	//初始化ArrayList
	jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
	//取得ArrayList的add方法签名
	jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

	//创建ArrayList中的对象
	jclass cls_atg_data_in_t;
	//获得构造方法
	jmethodID construct_atg_data_in_t;
	jobject obj_atg_data_in_t;
	//获取变量ID

	jfieldID uOilCanNO;
	jfieldID fOilCubage;
	jfieldID fOilStandCubage;
	jfieldID fEmptyCubage;
	jfieldID fWaterBulk;


	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return obj_ArrayList;	
   
  //定义数组的大小（malloc最后要释放）
	atg_hightoliter_in = (struct atg_hightoliter_in_t*)malloc(sizeof(struct atg_hightoliter_in_t));
	atg_hightoliter_in[0].uCount = len;
	LOG_DEBUG("%d\n",len);
	
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_uCount = (*env)->GetFieldID(env,cls_data,"uCount","I");
		field_fTotalHeight = (*env)->GetFieldID(env,cls_data,"fTotalHeight","D");
		field_fWaterHeight = (*env)->GetFieldID(env,cls_data,"fWaterHeight","D");
		field_fOilTemp = (*env)->GetFieldID(env,cls_data,"fOilTemp","D");
		field_fOilTemp1 = (*env)->GetFieldID(env,cls_data,"fOilTemp1","D");
		field_fOilTemp2 = (*env)->GetFieldID(env,cls_data,"fOilTemp2","D");
		field_fOilTemp3 = (*env)->GetFieldID(env,cls_data,"fOilTemp3","D");
		field_fOilTemp4 = (*env)->GetFieldID(env,cls_data,"fOilTemp4","D");
		field_fOilTemp5 = (*env)->GetFieldID(env,cls_data,"fOilTemp5","D");

		uCount = (*env)->GetIntField(env,obj_data,field_uCount);//~~~~~~~要修改为int型
		//uCount=1;//测试使用，正式的时候用上边的注释
		fTotalHeight = (*env)->GetDoubleField(env,obj_data,field_fTotalHeight);
		fWaterHeight = (*env)->GetDoubleField(env,obj_data,field_fWaterHeight);
		fOilTemp =  (*env)->GetDoubleField(env,obj_data,field_fOilTemp);
		fOilTemp1 = (*env)->GetDoubleField(env,obj_data,field_fOilTemp1);
		fOilTemp2 = (*env)->GetDoubleField(env,obj_data,field_fOilTemp2);
		fOilTemp3 = (*env)->GetDoubleField(env,obj_data,field_fOilTemp3);
		fOilTemp4 = (*env)->GetDoubleField(env,obj_data,field_fOilTemp4);
		fOilTemp5 = (*env)->GetDoubleField(env,obj_data,field_fOilTemp5);
		//定义数组的大小（malloc最后要释放）
		atg_capacity_data_in = (struct atg_capacity_data_in_t*)malloc(sizeof(struct atg_capacity_data_in_t) * uCount);
		field_pCapacityData = (*env)->GetFieldID(env,cls_data,"pCapacityData","Ljava/util/List;");
		pCapacityData = (*env)->GetObjectField(env,obj_data,field_pCapacityData);

		//获得jlist对象的句柄
		pCapacityDataclazz = (*env)->GetObjectClass(env,pCapacityData);
		pCapacityData_size = (*env)->GetMethodID(env,pCapacityDataclazz,"size","()I");
		pCapacityData_get = (*env)->GetMethodID(env,pCapacityDataclazz,"get","(I)Ljava/lang/Object;");
		for(j=0;j<uCount;j++)
		{
			obj_data = (*env)->CallObjectMethod(env,pCapacityData,pCapacityData_get,j);  
			cls_data = (*env)->GetObjectClass(env,obj_data);  
			field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNO","I");
			field_uCapacitySize = (*env)->GetFieldID(env,cls_data,"uCapacitySize","I");
			uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
			uCapacitySize = (*env)->GetIntField(env,obj_data,field_uCapacitySize);
			 //定义数组的大小（malloc最后要释放）
			atg_capacitytable_data_in = (struct atg_capacitytable_data_in_t*)malloc(sizeof(struct atg_capacitytable_data_in_t) * uCapacitySize);
		
			field_pCapacityTableData = (*env)->GetFieldID(env,cls_data,"pCapacityTableData","Ljava/util/List;");
			pCapacityTableData = (*env)->GetObjectField(env,obj_data,field_pCapacityTableData);
			//获得jlist对象的句柄
			pCapacityTableDataclazz = (*env)->GetObjectClass(env,pCapacityTableData);
			pCapacityTableData_size = (*env)->GetMethodID(env,pCapacityTableDataclazz,"size","()I");
			pCapacityTableData_get = (*env)->GetMethodID(env,pCapacityTableDataclazz,"get","(I)Ljava/lang/Object;");
			LOG_DEBUG("uCapacitySize=[%d]",uCapacitySize);
			for(k=0;k<uCapacitySize;k++)
			{
				obj_data = (*env)->CallObjectMethod(env,pCapacityTableData,pCapacityTableData_get,k);  
				cls_data = (*env)->GetObjectClass(env,obj_data);  
				field_uHigh = (*env)->GetFieldID(env,cls_data,"uHigh","I");
				field_fLiter = (*env)->GetFieldID(env,cls_data,"fLiter","D");

				uHigh = (*env)->GetIntField(env,obj_data,field_uHigh);
				fLiter = (*env)->GetDoubleField(env,obj_data,field_fLiter);
				atg_capacitytable_data_in[k].uHigh = uHigh;
				atg_capacitytable_data_in[k].fLiter = fLiter;
				//LOG_DEBUG("%d|%d|%f|",k,atg_capacitytable_data_in[k].uHigh,atg_capacitytable_data_in[k].fLiter);
			}
			atg_capacity_data_in[i].pCapacityTableData = atg_capacitytable_data_in;
			
			atg_capacity_data_in[i].uOilCanNo = uOilCanNo;
			atg_capacity_data_in[i].uCapacitySize = uCapacitySize;
			//LOG_DEBUG("%d|%d|%d|",i,atg_capacity_data_in[i].uOilCanNo,atg_capacity_data_in[i].uCapacitySize);
		}
		 atg_hightoliter_in[i].pCapacityData = atg_capacity_data_in;
		 atg_hightoliter_in[i].fTotalHeight =fTotalHeight;
		 atg_hightoliter_in[i].fWaterHeight =fWaterHeight;
		 atg_hightoliter_in[i].fOilTemp =fOilTemp;
		 atg_hightoliter_in[i].fOilTemp1 =fOilTemp1;
		 atg_hightoliter_in[i].fOilTemp2 =fOilTemp2;
		 atg_hightoliter_in[i].fOilTemp3 =fOilTemp3;
		 atg_hightoliter_in[i].fOilTemp4 =fOilTemp4;
		 atg_hightoliter_in[i].fOilTemp5 =fOilTemp5;
		 LOG_DEBUG("%d|%f|%f|%f|%f|%f|%f|%f|%f|",i
			 ,atg_hightoliter_in[i].fTotalHeight
			 ,atg_hightoliter_in[i].fWaterHeight
			 ,atg_hightoliter_in[i].fOilTemp
			 ,atg_hightoliter_in[i].fOilTemp1
			 ,atg_hightoliter_in[i].fOilTemp2
			 ,atg_hightoliter_in[i].fOilTemp3
			 ,atg_hightoliter_in[i].fOilTemp4
			 ,atg_hightoliter_in[i].fOilTemp5);
	}		

	//调用接口
	if(filehandle){
		functC = dlsym(filehandle, "atg_operate"); 
		if (functC) 
		{ 
		     patg_hightoliter_out = &atg_hightoliter_out;
			LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_HIGHTOLITER,atg_hightoliter_in,(void *)(patg_hightoliter_out));
			LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		
		if(atg_hightoliter_in->pCapacityData->pCapacityTableData){
			free(atg_hightoliter_in->pCapacityData->pCapacityTableData);
		}
		if(atg_hightoliter_in->pCapacityData){
			free(atg_hightoliter_in->pCapacityData);
		}
		if(atg_hightoliter_in){
			free(atg_hightoliter_in);
		}
		if(atg_hightoliter_out){
			if(atg_hightoliter_out->pHighToLiterData){
				free(atg_hightoliter_out->pHighToLiterData);
			}
			free(atg_hightoliter_out);
		}
		if(filehandle){
			dlclose(filehandle);
		} 
		return obj_ArrayList;	
	}
	//循环给返回的list赋值
	for(i=0;i<atg_hightoliter_out[0].uRetCount;i++){
		//创建ArrayList中的对象
		cls_atg_data_in_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_hightoliter_data_out_t");
		//获得构造方法
		construct_atg_data_in_t = (*env)->GetMethodID(env,cls_atg_data_in_t,"<init>","()V");
		obj_atg_data_in_t = (*env)->NewObject(env,cls_atg_data_in_t,construct_atg_data_in_t,"");
		//获取变量ID

		uOilCanNO = (*env)->GetFieldID(env,cls_atg_data_in_t,"uOilCanNo","I");
		fOilCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilCubage","D");
		fOilStandCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fOilStandCubage","D");
		fEmptyCubage = (*env)->GetFieldID(env,cls_atg_data_in_t,"fEmptyCubage","D");
		fWaterBulk = (*env)->GetFieldID(env,cls_atg_data_in_t,"fWaterBulk","D");
	
		//给String赋值
		(*env)->SetIntField(env,obj_atg_data_in_t,uOilCanNO,atg_hightoliter_out[0].pHighToLiterData[i].uOilCanNo);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilCubage,atg_hightoliter_out[0].pHighToLiterData[i].fOilCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fOilStandCubage,atg_hightoliter_out[0].pHighToLiterData[i].fOilStandCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fEmptyCubage,atg_hightoliter_out[0].pHighToLiterData[i].fEmptyCubage);
		(*env)->SetDoubleField(env,obj_atg_data_in_t,fWaterBulk,atg_hightoliter_out[0].pHighToLiterData[i].fWaterBulk);

		LOG_DEBUG("%d|%d|%f|%f|%f|%f|",i
			,atg_hightoliter_out[0].pHighToLiterData[i].uOilCanNo
			,atg_hightoliter_out[0].pHighToLiterData[i].fOilCubage
			,atg_hightoliter_out[0].pHighToLiterData[i].fOilStandCubage
			,atg_hightoliter_out[0].pHighToLiterData[i].fEmptyCubage
			,atg_hightoliter_out[0].pHighToLiterData[i].fWaterBulk);

		//对象加入到list中
		(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_data_in_t);
	}
		
	if(atg_hightoliter_in->pCapacityData->pCapacityTableData){
		free(atg_hightoliter_in->pCapacityData->pCapacityTableData);
	}
	if(atg_hightoliter_in->pCapacityData){
		free(atg_hightoliter_in->pCapacityData);
	}
	if(atg_hightoliter_in){
		free(atg_hightoliter_in);
	}
	if(atg_hightoliter_out){
		if(atg_hightoliter_out->pHighToLiterData){
			free(atg_hightoliter_out->pHighToLiterData);
		}
		free(atg_hightoliter_out);
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_ArrayList;
  }

  /*
 * Class:     ATGDevice_ATGManager
 * Method:    setCorrection
 * Signature: (Ljava/util/List;)I
 */
JNIEXPORT jint JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_setCorrection
  (JNIEnv *env, jobject obj, jobject jlist)
  {
	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_correction_in_t* atg_correction_in;
	struct atg_correction_data_in_t *atg_correction_data_in;

	int * (*functC)(const char* p1, void* p2) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	void* temp;
	//定义传入参数、
	int i=0;
	jfieldID field_strDeviceModel;
	jfieldID field_strProbeNo;
	jfieldID field_uOilType;
	jfieldID field_fOilCorrection;
	jfieldID field_fWaterCorrection;
	jfieldID field_fProbeOffset;
	jfieldID field_fTiltOffset;
	jfieldID field_fTemp1;
	jfieldID field_fProbeTemp1;
	jfieldID field_fTemp2;
	jfieldID field_fProbeTemp2;
	jfieldID field_fTemp3;
	jfieldID field_fProbeTemp3;
	jfieldID field_fTemp4;
	jfieldID field_fProbeTemp4;
	jfieldID field_fTemp5;
	jfieldID field_fProbeTemp5;
	jfieldID field_fInitDesnsity;
	jfieldID field_fInitHighDiff;
	jfieldID field_fCorrectionFactor;

	jstring strDeviceModel;
	jstring strProbeNo;
	jstring strOilType;
	jdouble fOilCorrection;
	jdouble fWaterCorrection;
	jdouble fProbeOffset;
	jdouble fTiltOffset;
	jdouble fTemp1;
	jdouble fProbeTemp1;
	jdouble fTemp2;
	jdouble fProbeTemp2;
	jdouble fTemp3;
	jdouble fProbeTemp3;
	jdouble fTemp4;
	jdouble fProbeTemp4;
	jdouble fTemp5;
	jdouble fProbeTemp5;
	jdouble fInitDesnsity;
	jdouble fInitHighDiff;
	jdouble fCorrectionFactor;

	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return 2;

	//定义数组的大小（malloc最后要释放）
	atg_correction_data_in = (struct atg_correction_data_in_t*)malloc(sizeof(struct atg_correction_data_in_t) * len);
	atg_correction_in = (struct atg_correction_in_t*)malloc(sizeof(struct atg_correction_in_t));
	atg_correction_in[0].uCount = len;
	LOG_DEBUG("uCount=[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_strDeviceModel = (*env)->GetFieldID(env,cls_data,"strDeviceModel","Ljava/lang/String;");
		field_strProbeNo = (*env)->GetFieldID(env,cls_data,"strProbeNo","Ljava/lang/String;");
		field_uOilType = (*env)->GetFieldID(env,cls_data,"uOilTy","Ljava/lang/String;");//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~测试时候注释，java里需要修改为String类型
		field_fOilCorrection = (*env)->GetFieldID(env,cls_data,"fOilCorrection","D");
		field_fWaterCorrection = (*env)->GetFieldID(env,cls_data,"fWaterCorrection","D");
		field_fProbeOffset = (*env)->GetFieldID(env,cls_data,"fProbeOffset","D");
		field_fTiltOffset = (*env)->GetFieldID(env,cls_data,"fTiltOffset","D");
		field_fTemp1 = (*env)->GetFieldID(env,cls_data,"fTemp1","D");
		field_fProbeTemp1 = (*env)->GetFieldID(env,cls_data,"fProbeTemp1","D");
		field_fTemp2 = (*env)->GetFieldID(env,cls_data,"fTemp2","D");
		field_fProbeTemp2 = (*env)->GetFieldID(env,cls_data,"fProbeTemp2","D");
		field_fTemp3 = (*env)->GetFieldID(env,cls_data,"fTemp3","D");
		field_fProbeTemp3 = (*env)->GetFieldID(env,cls_data,"fProbeTemp3","D");
		field_fTemp4 = (*env)->GetFieldID(env,cls_data,"fTemp4","D");
		field_fProbeTemp4 = (*env)->GetFieldID(env,cls_data,"fProbeTemp4","D");
		field_fTemp5 = (*env)->GetFieldID(env,cls_data,"fTemp5","D");
		field_fProbeTemp5 = (*env)->GetFieldID(env,cls_data,"fProbeTemp5","D");
		field_fInitDesnsity = (*env)->GetFieldID(env,cls_data,"fInitDesnsity","D");
		field_fInitHighDiff = (*env)->GetFieldID(env,cls_data,"fInitHighDiff","D");
		field_fCorrectionFactor = (*env)->GetFieldID(env,cls_data,"fCorrectionFactor","D");

		strDeviceModel = (jstring)(*env)->GetObjectField(env,obj_data,field_strDeviceModel);
		strProbeNo = (jstring)(*env)->GetObjectField(env,obj_data,field_strProbeNo);
		strOilType = (jstring)(*env)->GetObjectField(env,obj_data,field_uOilType);//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`测试时候注释，java里需要修改为String类型
		fOilCorrection = (*env)->GetDoubleField(env,obj_data,field_fOilCorrection);
		fWaterCorrection = (*env)->GetDoubleField(env,obj_data,field_fWaterCorrection);
		fProbeOffset = (*env)->GetDoubleField(env,obj_data,field_fProbeOffset);
		fTiltOffset = (*env)->GetDoubleField(env,obj_data,field_fTiltOffset);
		fTemp1 = (*env)->GetDoubleField(env,obj_data,field_fTemp1);
		fProbeTemp1 = (*env)->GetDoubleField(env,obj_data,field_fProbeTemp1);
		fTemp2 = (*env)->GetDoubleField(env,obj_data,field_fTemp2);
		fProbeTemp2 = (*env)->GetDoubleField(env,obj_data,field_fProbeTemp2);
		fTemp3 = (*env)->GetDoubleField(env,obj_data,field_fTemp3);
		fProbeTemp3 = (*env)->GetDoubleField(env,obj_data,field_fProbeTemp3);
		fTemp4 = (*env)->GetDoubleField(env,obj_data,field_fTemp4);
		fProbeTemp4 = (*env)->GetDoubleField(env,obj_data,field_fProbeTemp4);
		fTemp5 = (*env)->GetDoubleField(env,obj_data,field_fTemp5);
		fProbeTemp5 = (*env)->GetDoubleField(env,obj_data,field_fProbeTemp5);
		fInitDesnsity = (*env)->GetDoubleField(env,obj_data,field_fInitDesnsity);
		fInitHighDiff = (*env)->GetDoubleField(env,obj_data,field_fInitHighDiff);
		fCorrectionFactor = (*env)->GetDoubleField(env,obj_data,field_fCorrectionFactor);
		
		strcpy(atg_correction_data_in[i].strDeviceModel,(*env)->GetStringUTFChars(env,strDeviceModel, NULL));
		strcpy(atg_correction_data_in[i].strProbeNo,(*env)->GetStringUTFChars(env,strProbeNo, NULL));
		strcpy(atg_correction_data_in[i].strOilType,(*env)->GetStringUTFChars(env,strOilType, NULL));//~~~~~~~~~~~~~~~~`测试时候注释，java里需要修改为String类型
		atg_correction_data_in[i].fOilCorrection = fOilCorrection;
		atg_correction_data_in[i].fWaterCorrection = fWaterCorrection;
		atg_correction_data_in[i].fProbeOffset = fProbeOffset;
		atg_correction_data_in[i].fTiltOffset = fTiltOffset;
		atg_correction_data_in[i].fTemp1 = fTemp1;
		atg_correction_data_in[i].fProbeTemp1 = fProbeTemp1;
		atg_correction_data_in[i].fTemp2 = fTemp2;
		atg_correction_data_in[i].fProbeTemp2 = fProbeTemp2;
		atg_correction_data_in[i].fTemp3 = fTemp3;
		atg_correction_data_in[i].fProbeTemp3 = fProbeTemp3;
		atg_correction_data_in[i].fTemp4 = fTemp4;
		atg_correction_data_in[i].fProbeTemp4 = fProbeTemp4;
		atg_correction_data_in[i].fTemp5 = fTemp5;
		atg_correction_data_in[i].fProbeTemp5 = fProbeTemp5;
		atg_correction_data_in[i].fInitDensity = fInitDesnsity;
		atg_correction_data_in[i].fInitHighDiff = fInitHighDiff;
		atg_correction_data_in[i].fCorrectionFactor = fCorrectionFactor;
		
		LOG_DEBUG("%d|%s|%s|%s|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|",i
			,atg_correction_data_in[i].strDeviceModel
			,atg_correction_data_in[i].strProbeNo
			,atg_correction_data_in[i].strOilType
			,atg_correction_data_in[i].fOilCorrection
			,atg_correction_data_in[i].fWaterCorrection
			,atg_correction_data_in[i].fProbeOffset
			,atg_correction_data_in[i].fTiltOffset
			,atg_correction_data_in[i].fTemp1
			,atg_correction_data_in[i].fProbeTemp1
			,atg_correction_data_in[i].fTemp2
			,atg_correction_data_in[i].fProbeTemp2
			,atg_correction_data_in[i].fTemp3
			,atg_correction_data_in[i].fProbeTemp3
			,atg_correction_data_in[i].fTemp4
			,atg_correction_data_in[i].fProbeTemp4
			,atg_correction_data_in[i].fTemp5
			,atg_correction_data_in[i].fProbeTemp5
			,atg_correction_data_in[i].fInitDensity
			,atg_correction_data_in[i].fInitHighDiff
			,atg_correction_data_in[i].fCorrectionFactor
			);
	}
	atg_correction_in[0].pCorrectionData = atg_correction_data_in;
	
	if(filehandle){
		functC = dlsym(filehandle, "atg_param"); 
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_SETCORRECTION,atg_correction_in);
			 LOG_DEBUG("func|ret=[%d]");
		}else{
			LOG_DEBUG("failed");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_correction_in->pCorrectionData){
			free(atg_correction_in->pCorrectionData);
		}
		if(atg_correction_in){
			free(atg_correction_in);
		}	
		if(filehandle){
			dlclose(filehandle);
		} 
		return ret;	
	}
	if(atg_correction_in->pCorrectionData){
		free(atg_correction_in->pCorrectionData);
	}
	if(atg_correction_in){
		free(atg_correction_in);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return ret;
  
  }



/*
 * Class:     ATGDevice_ATGManager
 * Method:    setProbe
 * Signature: (Ljava/util/List;)I
 */
JNIEXPORT jint JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_setProbe
  (JNIEnv *env, jobject obj, jobject jlist)
  {
  	jclass jlistclazz;//list参数
	jmethodID list_size;//list的Size()方法
	jmethodID list_get;//list的get()方法
	jint len;//list的长度
	jobject obj_data;//list里的元素
	jclass cls_data;//list里元素的类型

	//定义待传入到atg_operate接口的参数数组的指针
	struct atg_probecan_in_t* atg_probecan_in;
	struct  atg_probecan_data_in_t *atg_probecan_data_in;
	int * (*functC)(const char* p1, void* p2) = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	void* temp;
	//传入循环中的参数
	int i=0;
	jfieldID field_strDeviceModel;
	jfieldID field_strProbeNo;
	jfieldID field_uProbePort;
	jfieldID field_uOilCanNo;
	jfieldID field_strOilType;
	jfieldID field_strOilNo;
	jfieldID field_strOilName;

	jstring strDeviceModel;
	jstring strProbeNo;
	jint uProbePort;
	jint uOilCanNo;
	jstring strOilType;
	jstring strOilNo;
	jstring strOilName;
	const char * charstrDeviceModel;
	const char * charstrProbeNo;
	const char * charstrOilType;
	const char * charstrOilNo;
	const char * charstrOilName;

	LOG_DEBUG("begin...");
	//获得jlist对象的句柄
	jlistclazz = (*env)->GetObjectClass(env,jlist);
	list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
	list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
	//jlistclazz的method_size方法，即调用list的Size()方法
	len = (*env)->CallIntMethod(env,jlist,list_size);  
	if(len==0) return 2;

	//定义数组的大小（malloc最后要释放）
	atg_probecan_data_in = (struct atg_probecan_data_in_t*)malloc(sizeof(struct atg_probecan_data_in_t) * len);
	atg_probecan_in = (struct atg_probecan_in_t*)malloc(sizeof(struct atg_probecan_in_t));
	atg_probecan_in[0].uCount = len;
	LOG_DEBUG("uCount[%d]",len);
	//循环从java取值赋给带传入struct
	for(i=0;i<len;i++)
	{
		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,i);  
		cls_data = (*env)->GetObjectClass(env,obj_data);  
		field_strDeviceModel = (*env)->GetFieldID(env,cls_data,"strDeviceModel","Ljava/lang/String;");
		field_strProbeNo = (*env)->GetFieldID(env,cls_data,"strProbeNo","Ljava/lang/String;");
		field_uProbePort = (*env)->GetFieldID(env,cls_data,"uProbePort","I");
		field_uOilCanNo = (*env)->GetFieldID(env,cls_data,"uOilCanNo","I");
		field_strOilType = (*env)->GetFieldID(env,cls_data,"uOilType","Ljava/lang/String;");//~~测试注释 要在java里atg_probecan_data_in_t加入strOilType String
		field_strOilNo = (*env)->GetFieldID(env,cls_data,"strOilNo","Ljava/lang/String;");
		field_strOilName = (*env)->GetFieldID(env,cls_data,"strOilName","Ljava/lang/String;");//~~测试注释 要在java里atg_probecan_data_in_t加入strOilName String

		strDeviceModel = (jstring)(*env)->GetObjectField(env,obj_data,field_strDeviceModel);
		strProbeNo = (jstring)(*env)->GetObjectField(env,obj_data,field_strProbeNo);
		uProbePort = (*env)->GetIntField(env,obj_data,field_uProbePort);
		uOilCanNo = (*env)->GetIntField(env,obj_data,field_uOilCanNo);
		strOilType = (*env)->GetObjectField(env,obj_data,field_strOilType);//~~测试注释 要在java里atg_probecan_data_in_t加入strOilType String
		strOilNo = (jstring)(*env)->GetObjectField(env,obj_data,field_strOilNo);
		strOilName = (jstring)(*env)->GetObjectField(env,obj_data,field_strOilName);//~~测试注释 要在java里atg_probecan_data_in_t加入strOilName String
		charstrDeviceModel = (*env)->GetStringUTFChars(env,strDeviceModel, NULL); //转为C的char*
		charstrProbeNo= (*env)->GetStringUTFChars(env,strProbeNo, NULL); //转为C的char*
		charstrOilType = (*env)->GetStringUTFChars(env,strOilType, NULL); //转为C的char*~~测试注释 要在java里atg_probecan_data_in_t加入strOilType String
		charstrOilNo = (*env)->GetStringUTFChars(env,strOilNo, NULL); //转为C的char*
		charstrOilName = (*env)->GetStringUTFChars(env,strOilName, NULL); //转为C的char*~~测试注释 要在java里atg_probecan_data_in_t加入strOilName String
		strcpy(atg_probecan_data_in[i].strDeviceModel, charstrDeviceModel);
		strcpy(atg_probecan_data_in[i].strProbeNo, charstrProbeNo);
		atg_probecan_data_in[i].uProbePort = uProbePort;
		atg_probecan_data_in[i].uOilCanNo = uOilCanNo;
		strcpy(atg_probecan_data_in[i].strOilType,charstrOilType);//~~测试注释 要在java里atg_probecan_data_in_t加入strOilType String
		//strcpy(atg_probecan_data_in[i].strOilType,"1901");//测试的油品~~记得要删除，用上边的注释过的strOilType  String
		strcpy(atg_probecan_data_in[i].strOilNo, charstrOilNo);
		//strcpy(atg_probecan_data_in[i].strOilName, "oilname");//测试的油品名称~~记得要删除，用上边的注释过的strOilName  String
		strcpy(atg_probecan_data_in[i].strOilName, charstrOilName);//~~测试注释 要在java里atg_probecan_data_in_t加入strOilName String
		//LOG_DEBUG("%d|%s",atg_probecan_data_in[i].strDeviceModel,atg_probecan_data_in[i].strProbeNo);
		LOG_DEBUG("%d|%s|%s|%d|%d|%s|%s|%s|",i
			,atg_probecan_data_in[i].strDeviceModel
			,atg_probecan_data_in[i].strProbeNo
			,atg_probecan_data_in[i].uProbePort
			,atg_probecan_data_in[i].uOilCanNo
			,atg_probecan_data_in[i].strOilType
			,atg_probecan_data_in[i].strOilNo
			,atg_probecan_data_in[i].strOilName
			);

	}						     
	atg_probecan_in[0].pProbeCanData = atg_probecan_data_in;

	if(filehandle){
		functC = dlsym(filehandle, "atg_param"); 
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
		     ret = (int)functC(ATG_OPE_SETPROBE,atg_probecan_in);
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed\n");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(atg_probecan_in->pProbeCanData){
			free(atg_probecan_in->pProbeCanData);
		}
		if(atg_probecan_in){
			free(atg_probecan_in);
		}	
		if(filehandle){
			dlclose(filehandle);
		} 
		return ret;	
	}
	if(atg_probecan_in->pProbeCanData){
		free(atg_probecan_in->pProbeCanData);
	}
	if(atg_probecan_in){
		free(atg_probecan_in);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return ret;
  
  }


/*
 * Class:     ATGDevice_ATGManager
 * Method:    getPowerRecord
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_getPowerRecord
  (JNIEnv *env, jobject obj, jobject jlist)
  {
		jclass jlistclazz;//list参数
		jmethodID list_size;//list的Size()方法
		jmethodID list_get;//list的get()方法
		jint len;//list的长度
		jobject obj_data;//list里的元素
		jclass cls_data;//list里元素的类型

		//定义传入到atg_operate接口的参数
		struct atg_powerrecord_in_t* atg_powerrecord_in;

		jfieldID field_jstrDataTime;
		jfieldID field_uReqCount;
		//赋值
		jstring jstr;
		jint uReqCount;
		const char *str;
		
		//输出struct
		struct atg_powerrecord_out_t* atg_powerrecord_out = NULL;
		struct atg_powerrecord_out_t**  patg_powerrecord_out = NULL;

		int * (*functC)(const char* p1, void* p2, void* p3) = NULL;
		//加载配置信息
		void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
		int ret;

		int i=0;

		//开始实现返回List
		/*创建java的ArrayList对象*/
		jclass cls_ArrayList = (*env)->FindClass(env,"java/util/ArrayList");
		//获得构造函数
		jmethodID construct = (*env)->GetMethodID(env,cls_ArrayList,"<init>","()V");
		//初始化ArrayList
		jobject obj_ArrayList = (*env)->NewObject(env,cls_ArrayList,construct,"");
		//取得ArrayList的add方法签名
		jmethodID arrayList_add = (*env)->GetMethodID(env,cls_ArrayList,"add","(Ljava/lang/Object;)Z");

		//创建ArrayList中的对象
		jclass cls_atg_powerrecord_data_out_t;
		//获得构造方法
		jmethodID construct_atg_powerrecord_data_out_t;
		jobject obj_atg_powerrecord_data_out_t;
		//获取变量
		jfieldID strDate;
		jfieldID strTime;
		jfieldID strOperateType;
		jfieldID uOilCanNo;
		jfieldID fTotalHeight;
		jfieldID fWaterHeight;
		jfieldID fOilTemp;
		jfieldID fOilTemp1;
		jfieldID fOilTemp2;
		jfieldID fOilTemp3;
		jfieldID fOilTemp4;
		jfieldID fOilTemp5;
		jfieldID fOilCubage;
		jfieldID fOilStandCubage;
		jfieldID fEmptyCubage;
		jfieldID fWaterBulk;

		LOG_DEBUG("begin...");
		//获得jlist对象的句柄
		jlistclazz = (*env)->GetObjectClass(env,jlist);
		list_size = (*env)->GetMethodID(env,jlistclazz,"size","()I");
		list_get = (*env)->GetMethodID(env,jlistclazz,"get","(I)Ljava/lang/Object;");
		//jlistclazz的method_size方法，即调用list的Size()方法
		len = (*env)->CallIntMethod(env,jlist,list_size);  

		obj_data = (*env)->CallObjectMethod(env,jlist,list_get,0); 
		cls_data = (*env)->GetObjectClass(env,obj_data);

		field_jstrDataTime = (*env)->GetFieldID(env,cls_data,"strDataTime","Ljava/lang/String;");
		field_uReqCount = (*env)->GetFieldID(env,cls_data,"uReqCount","I");
		//赋值
		jstr = (jstring)(*env)->GetObjectField(env,obj_data,field_jstrDataTime);
		uReqCount = (*env)->GetIntField(env,obj_data,field_uReqCount);
		str = (*env)->GetStringUTFChars(env,jstr,NULL);
		atg_powerrecord_in = (struct atg_powerrecord_in_t*)malloc(sizeof(struct atg_powerrecord_in_t));
		strcpy(atg_powerrecord_in[0].strDateTime, str);
		atg_powerrecord_in[0].uReqCount = uReqCount;
		//调用接口
		if(filehandle){
			functC = dlsym(filehandle, "atg_operate"); 
			if (functC) 
			{ 
				 patg_powerrecord_out = &atg_powerrecord_out;
				 LOG_DEBUG("func|begin");
				 ret = (int)functC(ATG_OPE_GETPOWERRECORD,atg_powerrecord_in,(void *)(patg_powerrecord_out));
				 LOG_DEBUG("func|ret=[%d]",ret);
			}else{
				LOG_DEBUG("failed\n");
			}
		}
		if(ret)
		{
			LOG_DEBUG("ret=[%d]",ret);
			if(atg_powerrecord_in){
				free(atg_powerrecord_in);
			}
			if(atg_powerrecord_out){
				if(atg_powerrecord_out->pPowerRecordData){
					free(atg_powerrecord_out->pPowerRecordData);
				}
				free(atg_powerrecord_out);
			}
			if(filehandle){
				dlclose(filehandle);
			} 
			return obj_ArrayList;	
		}
		for(i=0;i<atg_powerrecord_out[0].uRetCount;i++){
			//创建ArrayList中的对象
			cls_atg_powerrecord_data_out_t = (*env)->FindClass(env,"com/kld/gsm/ATGDevice/atg_powerrecord_data_out_t");
			//获得构造方法
			construct_atg_powerrecord_data_out_t = (*env)->GetMethodID(env,cls_atg_powerrecord_data_out_t,"<init>","()V");
			obj_atg_powerrecord_data_out_t = (*env)->NewObject(env,cls_atg_powerrecord_data_out_t,construct_atg_powerrecord_data_out_t,"");
			//获取变量
			strDate = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"strDate","Ljava/lang/String;");
			strTime = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"strTime","Ljava/lang/String;");
			strOperateType = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"strOperateType","I");
			uOilCanNo = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"uOilCanNO","I");
			fTotalHeight = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fTotalHeight","D");
			fWaterHeight = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fWaterHeight","D");
			fOilTemp = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp","D");
			fOilTemp1 = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp1","D");
			fOilTemp2 = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp2","D");
			fOilTemp3 = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp3","D");
			fOilTemp4 = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp4","D");
			fOilTemp5 = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilTemp5","D");
			fOilCubage = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilCubage","D");
			fOilStandCubage = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fOilStandCubage","D");
			fEmptyCubage = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fEmptyCubage","D");
			fWaterBulk = (*env)->GetFieldID(env,cls_atg_powerrecord_data_out_t,"fWaterBulk","D");

			//给变量赋值
			(*env)->SetObjectField(env,obj_atg_powerrecord_data_out_t,strDate,(*env)->NewStringUTF(env,atg_powerrecord_out[0].pPowerRecordData[i].strDate));
			(*env)->SetObjectField(env,obj_atg_powerrecord_data_out_t,strTime,(*env)->NewStringUTF(env,atg_powerrecord_out[0].pPowerRecordData[i].strTime));
			(*env)->SetIntField(env,obj_atg_powerrecord_data_out_t,strOperateType,atg_powerrecord_out[0].pPowerRecordData[i].uOperateType);
			(*env)->SetIntField(env,obj_atg_powerrecord_data_out_t,uOilCanNo,atg_powerrecord_out[0].pPowerRecordData[i].uOilCanNo);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fTotalHeight,atg_powerrecord_out[0].pPowerRecordData[i].fTotalHeight);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fWaterHeight,atg_powerrecord_out[0].pPowerRecordData[i].fWaterHeight);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp1,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp1);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp2,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp2);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp3,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp3);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp4,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp4);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilTemp5,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp5);

			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilCubage,atg_powerrecord_out[0].pPowerRecordData[i].fOilCubage);																									
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fOilStandCubage,atg_powerrecord_out[0].pPowerRecordData[i].fOilStandCubage);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fEmptyCubage,atg_powerrecord_out[0].pPowerRecordData[i].fEmptyCubage);
			(*env)->SetDoubleField(env,obj_atg_powerrecord_data_out_t,fWaterBulk,atg_powerrecord_out[0].pPowerRecordData[i].fWaterBulk);

			LOG_DEBUG("%d|%s|%s|%d|%d|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|%f|",i
				,atg_powerrecord_out[0].pPowerRecordData[i].strDate
				,atg_powerrecord_out[0].pPowerRecordData[i].strTime
				,atg_powerrecord_out[0].pPowerRecordData[i].uOperateType
				,atg_powerrecord_out[0].pPowerRecordData[i].uOilCanNo
				,atg_powerrecord_out[0].pPowerRecordData[i].fTotalHeight
				,atg_powerrecord_out[0].pPowerRecordData[i].fWaterHeight
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp1
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp2
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp3
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp4
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilTemp5
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilCubage
				,atg_powerrecord_out[0].pPowerRecordData[i].fOilStandCubage
				,atg_powerrecord_out[0].pPowerRecordData[i].fEmptyCubage
				,atg_powerrecord_out[0].pPowerRecordData[i].fWaterBulk
				);

			(*env)->CallBooleanMethod(env,obj_ArrayList,arrayList_add,obj_atg_powerrecord_data_out_t);
	}

	if(atg_powerrecord_in){
		free(atg_powerrecord_in);
	}
	if(atg_powerrecord_out){
		if(atg_powerrecord_out->pPowerRecordData){
			free(atg_powerrecord_out->pPowerRecordData);
		}
		free(atg_powerrecord_out);
	}	
    if(filehandle){
		dlclose(filehandle);
	} 
	LOG_DEBUG("end...");
	return obj_ArrayList;
  }


/*
 * Class:     ATGDevice_ATGManager
 * Method:    clear
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_kld_gsm_ATGDevice_ATGDevice_clear
  (JNIEnv *env, jobject obj)
  {
	int * (*functC)() = NULL;
	//加载配置信息
	void*  filehandle = dlopen(LIB_ATG_FILENAME, RTLD_GLOBAL);
	int ret;
	//调用接口
	if(filehandle)
	{
		functC = dlsym(filehandle, "atg_clear"); 
		if (functC) 
		{ 
			 LOG_DEBUG("func|begin");
			 ret = (int)functC();
			 LOG_DEBUG("func|ret=[%d]",ret);
		}else{
			LOG_DEBUG("failed\n");
		}
	}
	if(ret)
	{
		LOG_DEBUG("ret=[%d]",ret);
		if(filehandle){
			dlclose(filehandle);
		}
		return 1;	
	}
    if(filehandle){
		dlclose(filehandle);
	} 
	return ret;
  }
