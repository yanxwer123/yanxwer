#include "Systemv_IPCKey.h"
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>

JNIEXPORT jint JNICALL Java_Systemv_IPCKey_getIntj(JNIEnv * env, jclass obj, jint ipckey)
{

return  (int)ftok("/smc20/conf/atg.msg",ipckey);
//return ipckey;
}