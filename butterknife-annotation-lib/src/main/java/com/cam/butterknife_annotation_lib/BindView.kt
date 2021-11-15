package com.cam.butterknife_annotation_lib

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class BindView(val value:Int)