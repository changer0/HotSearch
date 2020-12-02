package com.qq.reader.provider.build.compiler;

import com.qq.reader.provider.build.ProviderBuilderConstants;
import com.qq.reader.provider.build.annotations.ProviderBuilderType;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.lang.model.element.Element;

/**
 * by zhanglulu
 */
public class ProviderBuilderProcessor extends AbstractProcessor {

    private static final String TAG = "ProviderProcessor";
    //用于生成新的java文件的对象
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * getSupportedAnnotationTypes()方法用于返回该Processor想要接收处理的注解，要返回全路径类名，通常使用getCanonicalName()方法。
     * 该方法也可以通过在Processor类上定义SupportedAnnotationTypes注解的方式指定。
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> res = new HashSet<>();
        res.add(ProviderBuilderType.class.getCanonicalName());
        return res;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        handleProviderGeneratorType(roundEnvironment);
        return true;
    }



    private void handleProviderGeneratorType(RoundEnvironment roundEnvironment) {
        //获取该注解的元素
        Set<? extends Element> sets = roundEnvironment.getElementsAnnotatedWith(ProviderBuilderType.class);
        if (sets == null || sets.size() <= 0) {
            return;
        }

        //构造方法
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
        constructorBuilder
                .addModifiers(Modifier.PRIVATE);

        // getProviderBuilder 方法
        MethodSpec.Builder getProviderBuilder = MethodSpec.methodBuilder(ProviderBuilderConstants.BUILDER_GET_METHOD_NAME).addModifiers(Modifier.PUBLIC);
        ClassName param = ClassName.get(String.class);
        getProviderBuilder.addParameter(param, "type");
        ClassName returnType = ClassName.get(ProviderBuilderConstants.BUILDER_PACKAGE_NAME, ProviderBuilderConstants.BUILDER_SIMPLE_CLASS);
        getProviderBuilder.returns(returnType);
        getProviderBuilder.addStatement("IProviderBuilder builder = null");
        getProviderBuilder.addCode("switch (type) {\n");

        for (Element element : sets) {
            String type = element.getAnnotation(ProviderBuilderType.class).value();
            print("注解值 providerGeneratorType：" + type);
            String packageName = element.getEnclosingElement().toString();
            String simpleClazzName = element.getSimpleName().toString();

            getProviderBuilder.addCode("case");
            getProviderBuilder.addCode("\"" + type);
            getProviderBuilder.addCode("\":");
            getProviderBuilder.addStatement("\rbuilder = new " + packageName + "." + simpleClazzName + "()");
            getProviderBuilder.addStatement("break");
        }
        getProviderBuilder.addCode("}\n");
        getProviderBuilder.addStatement("return builder");

        //class 让其实现接口
        ClassName iGetViewModelMapInter = ClassName.get(ProviderBuilderConstants.BUILDER_PACKAGE_NAME, ProviderBuilderConstants.BUILDER_FACTORY_SIMPLE_CLASS_NAME);
        //class
        TypeSpec typeSpec = TypeSpec.classBuilder(ProviderBuilderConstants.BUILDER_FACTORY_IMPL_SIMPLE_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getProviderBuilder.build())
                .addSuperinterface(iGetViewModelMapInter)
                .build();
        //file
        JavaFile javaFile = JavaFile.builder(ProviderBuilderConstants.BUILDER_PACKAGE_NAME, typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void print(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}
