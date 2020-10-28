package com.qq.reader.provider.viewmodel.compiler;

import com.qq.reader.provider.viewmodel.annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author zhanglulu on 2020/10/28.
 * for
 */
public class ProviderProcessor extends AbstractProcessor {
    private static final String TAG = "ProviderProcessor";
    private Filer filer;//用于生成新的java文件的对象
    private Map<String, String> mapper = new HashMap<>();
    private long time;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        System.out.println("ProviderProcessor 调用");
        handleBindView(roundEnvironment);
        return true;
    }

    private void handleBindView(RoundEnvironment roundEnvironment) {
        //获取该注解的元素
        Set<? extends Element> sets = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        if (sets == null || sets.size() <= 0) {
            return;
        }
        for (Element element : sets) {
            //这是类
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            println("正在 handleBindView enclosingElement 注解：" + enclosingElement + "\n"
                    + "enclosingElement.getSimpleName() : " + enclosingElement.getSimpleName() + "\n"
                    + "enclosingElement.getQualifiedName() : " + enclosingElement.getQualifiedName() + "\n"
                    + "enclosingElement.getNestingKind() : " + enclosingElement.getNestingKind() + "\n"

            );

            //这是字段
            println("正在 handleBindView element 注解：" + element + "\n"
                    + "element.getSimpleName() : " + element.getSimpleName() + "\n"
                    + "element.getKind() : " + element.getKind() + "\n"
                    + "element.getAnnotationMirrors() : " + element.getAnnotationMirrors() + "\n"
            );
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                println("annotationMirror : " + annotationMirror);
            }
            int value = element.getAnnotation(BindView.class).value();
            String packageName = enclosingElement.getEnclosingElement().toString();
            String className = enclosingElement.getSimpleName().toString();
            String filedName = element.toString();
            println("packageName：" + packageName);
            println("className：" + className);
            println("fieldName：" + filedName);
            println("注解值：" + value);
        }
    }


    /**
     * getSupportedAnnotationTypes()方法用于返回该Processor想要接收处理的注解，要返回全路径类名，通常使用getCanonicalName()方法。
     * 该方法也可以通过在Processor类上定义SupportedAnnotationTypes注解的方式指定。
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> res = new HashSet<>();
        res.add(BindView.class.getCanonicalName());
        return res;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private void println(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}
