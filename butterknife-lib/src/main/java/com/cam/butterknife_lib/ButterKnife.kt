package com.cam.butterknife_lib

import android.app.Activity


class ButterKnife {
    companion object {
        fun bind(activity: Activity) {
            val clazzName =
                "${activity.javaClass.`package`.name}.ButterKnife_${activity.javaClass.simpleName}_Binding"
            val clazz = Class.forName(clazzName)
            val constructor = clazz.getConstructor(activity.javaClass)
            constructor.newInstance(activity)
        }
    }
}