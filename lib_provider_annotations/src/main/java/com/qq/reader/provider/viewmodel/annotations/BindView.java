package com.qq.reader.provider.viewmodel.annotations;

//import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhanglulu on 2020/10/27.
 * for
 */
//指定注解只保留在源文件中，不会被保留到class字节码文件中：因为在编译前期通过扫描源文件就使用完了注解。
//@Retention(RetentionPolicy.RUNTIME)
@Retention(RetentionPolicy.SOURCE)
//定该注解只能使用在类上：因为我们的功能是注册接口和实现类的关系。
@Target(value = ElementType.FIELD)
public @interface BindView {
    int value() default 0;
}
