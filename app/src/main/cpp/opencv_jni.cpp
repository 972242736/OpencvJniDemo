#include <jni.h>
#include <string>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "OpencvJniDemo" , __VA_ARGS__)
using namespace cv;
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_mmf_opencvjnidemo_MainActivity_getGray(JNIEnv *env, jclass clazz, jintArray pixels_,
                                                jint w,
                                                jint h) {
    jint *pixels = env->GetIntArrayElements(pixels_, NULL);
    if (pixels == NULL) {
        return 0;
    }
    //图片一进来时是ARGB  通过mat转换BGRA
    Mat img(h, w, CV_8UC4, (uchar *) pixels);

    Mat temp;
    //转化为单通道灰度图
    cvtColor(img, temp, COLOR_RGBA2GRAY);

    //转化为四通道。特别注意：在调用ov图像处理函数时，一定要好好考虑一下图片的位数和通道.否则可能出现各种问题.
    cvtColor(temp, temp, COLOR_GRAY2BGRA);
    uchar *tempData = temp.data;

    //对应数据指针
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    //env->SetIntArrayRegion(result,0,size,pixels);
    env->SetIntArrayRegion(result, 0, size, (const jint *) tempData);
    env->ReleaseIntArrayElements(pixels_, pixels, 0);
    return result;

}