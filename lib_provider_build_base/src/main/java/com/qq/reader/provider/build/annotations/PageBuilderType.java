package com.qq.reader.provider.build.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 指定 Provider 类型，作用在类上，用于生成对应的 ProviderGenerator
 */

//1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
//2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
//3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.SOURCE)
//定该注解只能使用在类上：因为我们的功能是注册接口和实现类的关系。
@Target(value = ElementType.TYPE)
public @interface PageBuilderType {
    String value() default "";
}
