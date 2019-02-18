//
// Created by kjeom on 2019-02-18.
//

#include "jniEtude.h"

JNIEXPORT jstring Java_com_example_kjeom_ysdm_101_JNIActivity_N1(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Hello from JNI ! Compiled with ABI .");
}

JNIEXPORT void Java_com_example_kjeom_ysdm_101_JNIActivity_N2(JNIEnv *env, jobject thiz) {
    char msg[50] = "This string is from native.";
    jstring result = env->NewStringUTF(msg);

    jclass javaProvideClass = env->FindClass("com/example/kjeom/ysdm_01/JNIActivity");
    jmethodID javaProvideMethod = env->GetStaticMethodID(javaProvideClass, "J1", "(Ljava/lang/String;)V");
    env->CallStaticVoidMethod(javaProvideClass, javaProvideMethod, result);

    return;
}
