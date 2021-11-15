package com.cam.butterknife_compile_lib

import com.cam.butterknife_annotation_lib.BindView
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class ButterKnifeAnnotationProcessor : AbstractProcessor() {
    lateinit var filer: Filer
    override fun  init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        println("getSupportedAnnotationTypes is running")
        val x = mutableSetOf(BindView::class.java.canonicalName)
        return x
    }


    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        if (p0.isNullOrEmpty() || p1 == null) {
            return false
        }
        println("process is running........")
        for (element in p1.rootElements) {
            val packageName = element.enclosingElement.toString()
            val className = element.simpleName
            var needWrite = false
            val clazzActivity = ClassName.bestGuess("${packageName}.${className}")
            val constructorFun = FunSpec.constructorBuilder()
                .addParameter("activity", clazzActivity)
                .callThisConstructor()
            for (innerElement in element.enclosedElements) {
                val annotationElement = innerElement.getAnnotation(BindView::class.java)
                if (annotationElement != null) {
                    needWrite = true
                    constructorFun.addStatement("activity.${innerElement} =activity.findViewById(${annotationElement.value})")
                    println("$packageName.$className.$innerElement")
                }
            }
            if (needWrite) {
                val bindClassName = "ButterKnife_${className}_Binding"
                val classType = TypeSpec.classBuilder(bindClassName)
                    .primaryConstructor(FunSpec.constructorBuilder().build())
                    .addFunction(constructorFun.build())
                    .build()
                val file = FileSpec.builder(packageName, bindClassName)
                    .addType(classType)
                    .build()
                file.writeTo(filer)
            }
        }
        return true
    }
}