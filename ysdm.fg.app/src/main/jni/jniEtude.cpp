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

/*
1. NDK 설치 및 Android studio에 경로 설정.

2. Native code 작성 (c, h)
경    로 : main/jni/*.c,
           main/java/com/humaxdigital/coc/JNIActivity.java
이름규격 : Java_package_class_file
패키지명 : com.humaxdigital.coc
클래스명 : JNIActivity
함수  명 : stringFromJNI
헤더파일은 java 파일을 입력으로 하여 javah.exe 로 자동 생성. 
Signature: (Ljava/lang/String)V;  javap로 확인 가능.

3. Java class 에서 생성한 C 호출

4. Android Studio의 기본 빌드도구는 CMake임, ndk-build를 사용하려면 아래 설정필요.
https://developer.android.com/studio/projects/add-native-code?utm_source=android-studio
Android 탭 -> 우클릭 -> Link C++ Project with Gradle
Path에 Android.mk 파일 선택
build.gradle 파일내에 자동 생성됨.
  externalNativeBuild {
        ndkBuild {
            path file('src/main/jni/Android.mk')
        }
    }
5. Android.mk
 https://developer.android.com/ndk/guides/android_mk

6. BUILD_SHARED_LIBRARY 설정시 libhello-jni.so 생성
7. BUILD_STATIC_LIBRARY 설정시 fail
 */
