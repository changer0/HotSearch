package com.qq.reader.provider.page.compiler;

import com.qq.reader.provider.page.PageBuilderConstants;
import com.qq.reader.provider.page.annotations.PageType;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

/**
 * DataProvider 注解处理器
 * @author zhanglulu
 */
public class ProviderBuilderProcessor extends AbstractProcessor {

    private static final String TAG = "ProviderProcessor";
    //用于生成新的java文件的对象
    private Filer filer;
    private String curModuleName;
    private boolean isMainProject = false;//是否为主工程

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        curModuleName = processingEnv.getOptions().get("PROVIDER_MODULE_NAME");
        isMainProject = "true".equals(processingEnv.getOptions().get("IS_MAIN_PROJECT"));
        print("当前模块名称: " + curModuleName);
        print("是否为主工程: " + isMainProject);
    }

    /**
     * getSupportedAnnotationTypes()方法用于返回该Processor想要接收处理的注解，要返回全路径类名，通常使用getCanonicalName()方法。
     * 该方法也可以通过在Processor类上定义SupportedAnnotationTypes注解的方式指定。
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> res = new HashSet<>();
        res.add(PageType.class.getCanonicalName());
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
        Set<? extends Element> sets = roundEnvironment.getElementsAnnotatedWith(PageType.class);
        if (sets == null || sets.size() <= 0) {
            return;
        }

        //保存当前模块
        if (curModuleName != null && !isMainProject) {
            OtherModuleNameSaver.putModuleName(curModuleName);
        }
        //单例静态变量
        FieldSpec instanceFiled = FieldSpec.builder(
                ClassName.get(PageBuilderConstants.BUILDER_PACKAGE_NAME, PageBuilderConstants.BUILDER_FACTORY_SIMPLE_CLASS_NAME),
                "instance", Modifier.PUBLIC, Modifier.STATIC
        ).initializer(CodeBlock.builder().add("new " + PageBuilderConstants.BUILDER_FACTORY_IMPL_SIMPLE_CLASS_NAME + "()").build()).build();

        //构造方法
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
        constructorBuilder
                .addModifiers(Modifier.PRIVATE);

        // getProviderBuilder 方法
        MethodSpec.Builder getProviderBuilder = MethodSpec.methodBuilder(PageBuilderConstants.BUILDER_GET_METHOD_NAME)
                //.addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.PUBLIC);
        ClassName param = ClassName.get(String.class);
        getProviderBuilder.addParameter(param, "type");
        ClassName returnType = ClassName.get(PageBuilderConstants.BUILDER_PACKAGE_NAME, PageBuilderConstants.BUILDER_SIMPLE_CLASS);
        getProviderBuilder.returns(returnType);
        getProviderBuilder.addStatement(PageBuilderConstants.BUILDER_SIMPLE_CLASS + " builder = null");
        getProviderBuilder.addCode("switch (type) {\n");

        for (Element element : sets) {
            String type = element.getAnnotation(PageType.class).value();
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

        //在 return 前给主工程添加其他模块的 PageFactory
        if (isMainProject) {
            List<String> moduleNames = OtherModuleNameSaver.getModuleNames();
            print("获取到的模块名: " + moduleNames);
            for (String moduleName : moduleNames) {
                String otherModulePackageName = PageBuilderConstants.BUILDER_PACKAGE_NAME + "." + moduleName + ".";
                getProviderBuilder.addCode("if (builder == null) {");
                getProviderBuilder.addStatement("\rbuilder = "+ otherModulePackageName + "PageFactory.instance.getPage(type)");
                getProviderBuilder.addCode("} \n");
            }
        }

        getProviderBuilder.addStatement("return builder");

        //class 让其实现接口
        ClassName iGetViewModelMapInter = ClassName.get(PageBuilderConstants.BUILDER_PACKAGE_NAME, PageBuilderConstants.BUILDER_FACTORY_SIMPLE_CLASS_NAME);
        //class
        TypeSpec typeSpec = TypeSpec.classBuilder(PageBuilderConstants.BUILDER_FACTORY_IMPL_SIMPLE_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getProviderBuilder.build())
                .addMethod(constructorBuilder.build())
                .addField(instanceFiled)
                .addSuperinterface(iGetViewModelMapInter)
                .build();

        //file
        String packageName = PageBuilderConstants.BUILDER_PACKAGE_NAME;
        if (isMainProject) {
            OtherModuleNameSaver.clear();
        } else {
            packageName = PageBuilderConstants.BUILDER_PACKAGE_NAME + "." + curModuleName;
        }
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void print(String message) {
        //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        System.out.println(message);
    }
}
