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
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author zhanglulu on 2020/10/28.
 * for
 */
public class ProviderProcessor extends AbstractProcessor {
    private static final String TAG = "ProviderProcessor";
    //用于生成新的java文件的对象
    private Filer filer;
    //类名作为 Key，保存当前类中所有的被注解标识过的 field
    private Map<String, ClazzBuilderInfo> builderMap = new HashMap<>();
    private long time;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        handleBindView(roundEnvironment);
        return true;
    }

    /**
     * 处理 BindView 注解
     * @param roundEnvironment
     */
    private void handleBindView(RoundEnvironment roundEnvironment) {
        //获取该注解的元素
        Set<? extends Element> sets = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        if (sets == null || sets.size() <= 0) {
            return;
        }
        for (Element element : sets) {
            //这是类
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            int resId = element.getAnnotation(BindView.class).value();
            String packageName = enclosingElement.getEnclosingElement().toString();
            String className = enclosingElement.getQualifiedName().toString();
            String simpleClassName = enclosingElement.getSimpleName().toString();
            String filedName = element.toString();
            println("packageName：" + packageName);
            println("className：" + className);
            println("simpleClassName：" + filedName);
            println("fieldName：" + filedName);
            println("注解值：" + resId);

            ClazzBuilderInfo clazzBuilderInfo = builderMap.get(className);
            if (clazzBuilderInfo == null) {
                clazzBuilderInfo = new ClazzBuilderInfo(packageName, simpleClassName);
                builderMap.put(className, clazzBuilderInfo);
            }
            clazzBuilderInfo.filedMap.put(resId, filedName);
        }
        generate();
    }

    /**
     * 根据 builderMap 构建类
     */
    private void generate() {

        for (Map.Entry<String, ClazzBuilderInfo> clazzEntry : builderMap.entrySet()) {
            String className = clazzEntry.getKey();
            ClazzBuilderInfo clazzInfo = clazzEntry.getValue();
            ClassName consParamType = ClassName.get(clazzInfo.packageName, clazzInfo.simpleClassName);
            //构造方法
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
            constructorBuilder
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(consParamType, "viewBindItem");

            Map<Integer, String> filedMap = clazzInfo.filedMap;
            for (Map.Entry<Integer, String> infoEntry : filedMap.entrySet()) {
                fillViewModelMap(constructorBuilder, infoEntry.getKey(), infoEntry.getValue());
            }
            MethodSpec cons = constructorBuilder.build();
            //viewModelMap
            ParameterizedTypeName mapType = ParameterizedTypeName.get(ClassName.get(Map.class),
                    ClassName.get(Integer.class), ClassName.get("com.qq.reader.provider.viewmodel","IModel"));
            FieldSpec map = FieldSpec.builder(mapType, "viewModelMap", Modifier.PRIVATE, Modifier.FINAL).initializer("new java.util.HashMap<>()").build();

            //ClassName baseViewBindItemClassName = ClassName.get("com.qq.reader.provider.viewmodel", "BaseViewBindModelItem");
            // viewBindItem
            //FieldSpec viewBindItem = FieldSpec.builder(baseViewBindItemClassName, "viewBindItem", Modifier.PRIVATE).build();
            // getViewModelMap
            MethodSpec.Builder getViewModelMapBuilder = MethodSpec.methodBuilder("getViewModelMap").addModifiers(Modifier.PUBLIC);
            TypeName returnType = TypeVariableName.get(Map.class);
            getViewModelMapBuilder.returns(returnType);
            getViewModelMapBuilder.addStatement("return viewModelMap");

            //class 让其实现接口
            ClassName iGetViewModelMapInter = ClassName.get("com.qq.reader.provider.viewmodel", "IGetViewModelMapInter");
            //class
            TypeSpec typeSpec = TypeSpec.classBuilder(clazzInfo.simpleClassName + "_ProviderViewBindModel")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(map)
                    //.addField(viewBindItem)
                    .addMethod(cons)
                    .addMethod(getViewModelMapBuilder.build())
                    .addSuperinterface(iGetViewModelMapInter)
                    .build();
            //file
            JavaFile javaFile = JavaFile.builder(clazzInfo.packageName, typeSpec).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void fillViewModelMap(MethodSpec.Builder constructorBuilder, int resId, String filedName) {
        String fieldStr = "viewBindItem." + filedName;
        constructorBuilder.addCode("if(" + fieldStr + " != null){");
        constructorBuilder.addStatement("\r viewModelMap.put(" + resId + ", " + fieldStr + ")");
        constructorBuilder.addCode("}\n");
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
