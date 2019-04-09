#include <jni.h>
#include <string>

#include<opencv2/opencv.hpp>

using namespace cv;
using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_oyy_opencvdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "点我变灰";
    return env->NewStringUTF(hello.c_str());
}

/**
 * 把图片转成灰色
 */
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_oyy_opencvdemo_MainActivity_bitmapToGrey(JNIEnv *env, jobject instance, jintArray pixels_,
                                                  jint width, jint height) {

    jint *cbuf;
    jboolean ptfalse = false;

    cbuf = env->GetIntArrayElements(pixels_, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }

    Mat imgData(height, width, CV_8UC4, (unsigned char *) cbuf);
//    Mat imgData(height, width, CV_8UC4, cbuf); 这个也没毛病

    // 注意，Android的Bitmap是ARGB四通道,而不是RGB三通道
    //cvtColor 颜色空间转换函数，可以实现RGB颜色向HSV，HSI等颜色空间的转换，
    // 也可以转换为灰度图像，参数CV_RGB2GRAY是RGB到gray。
    cvtColor(imgData, imgData, CV_BGRA2GRAY);
    cvtColor(imgData, imgData, CV_GRAY2BGRA);


    int size = width * height;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(pixels_, cbuf, 0);
    return result;

}