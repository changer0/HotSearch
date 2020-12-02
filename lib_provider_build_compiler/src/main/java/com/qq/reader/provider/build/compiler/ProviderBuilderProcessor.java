package com.qq.reader.provider.build.compiler;

import com.qq.reader.provider.build.IProviderBuilderFactory;
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

        //providerGeneratorMap 字段
        ParameterizedTypeName mapType = ParameterizedTypeName.get(ClassName.get(Map.class),
                ClassName.get(String.class), ClassName.get(String.class));
        FieldSpec providerGeneratorMap = FieldSpec.builder(mapType, "providerGeneratorMap",
                Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC).initializer("new java.util.HashMap<>()").build();

        //静态代码块
        CodeBlock.Builder staticBlockBuilder = CodeBlock.builder();

        print("handleProviderGeneratorType 注解集合：" + sets.size());
        for (Element element : sets) {
            String providerGeneratorType = element.getAnnotation(ProviderBuilderType.class).value();
            print("注解值 providerGeneratorType：" + providerGeneratorType);
            String packageName = element.getEnclosingElement().toString();
            String simpleClazzName = element.getSimpleName().toString();
            String value = packageName + "." + simpleClazzName;


            print("value" + value);

            StringBuilder codeBuilder = new StringBuilder("providerGeneratorMap.put(");
            codeBuilder.append("\"");
            codeBuilder.append(providerGeneratorType);
            codeBuilder.append("\"");
            codeBuilder.append(", ");
            codeBuilder.append("\"");
            codeBuilder.append(value);
            codeBuilder.append("\"");
            codeBuilder.append(");");
            codeBuilder.append("\n");
            staticBlockBuilder.add(codeBuilder.toString());
        }


        // getLoadProvider 方法
        MethodSpec.Builder getLoadProvider = MethodSpec.methodBuilder("getProviderBuilder").addModifiers(Modifier.PUBLIC);
        ClassName param = ClassName.get(String.class);
        getLoadProvider.addParameter(param, "type");
        TypeName returnType = TypeVariableName.get(String.class);//返回值
        getLoadProvider.returns(returnType);
        getLoadProvider.addStatement("return providerGeneratorMap.get(type)");

        //class 让其实现接口
        ClassName iGetViewModelMapInter = ClassName.get(IProviderBuilderFactory.class);
        //class
        TypeSpec typeSpec = TypeSpec.classBuilder(ProviderBuilderConstants.GENERATOR_SIMPLE_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(providerGeneratorMap)
                .addMethod(getLoadProvider.build())
                .addStaticBlock(staticBlockBuilder.build())
                .addSuperinterface(iGetViewModelMapInter)
                .build();
        //file
        JavaFile javaFile = JavaFile.builder(ProviderBuilderConstants.GENERATOR_PACKAGE_NAME, typeSpec).build();
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
