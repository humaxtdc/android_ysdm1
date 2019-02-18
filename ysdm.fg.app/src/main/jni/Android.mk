LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := jni-etude
LOCAL_SRC_FILES := jniEtude.cpp
#LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
