//
// Created by kjeom on 2019-02-18.
//

#ifndef YSDM_01_JNIETUDE_H
#define YSDM_01_JNIETUDE_H

#include <jni.h>

extern "C" {
    JNIEXPORT jstring Java_com_example_kjeom_ysdm_101_JNIActivity_N1(JNIEnv *env, jobject thiz);
    JNIEXPORT void Java_com_example_kjeom_ysdm_101_JNIActivity_N2(JNIEnv *env, jobject thiz);
}

#endif //YSDM_01_JNIETUDE_H
