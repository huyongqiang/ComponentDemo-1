package com.hxh.annotations_compiler;

import com.google.auto.service.AutoService;
import com.hxh.annotations.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Created by HXH at 2019/9/17
 * 注解处理器
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    // 生成文件对象
    private Filer mFiler;
    // 日志相关的辅助类
    //private Messager mMessager;


    // 初始化的第一个方法
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        //mMessager = processingEnv.getMessager();
    }

    // 返回注解器要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    // 声明我们的注解处理器支持的java版本(源版本)
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    // 注解处理器的核心方法-写文件、写方法
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 拿到该模块中所有用到了BindPath注解的节点
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        // 结构化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            String key = annotation.value();
            String activityName = typeElement.getQualifiedName().toString();//类全路径名:包名+.+类名
            map.put(key, activityName);
        }
        if (map.size() > 0) {
            // 开始写文件
            Writer writer = null;
            // 创建的文件名-类名
            String utilName = "ActivityUtil" + System.currentTimeMillis();
            try {
                JavaFileObject javaFileObject = mFiler.createSourceFile("com.hxh.util." + utilName);
                writer = javaFileObject.openWriter();
                writer.write("package com.hxh.util;\n" +
                        "\n" +
                        "import com.hxh.route.ARoute;\n" +
                        "import com.hxh.route.IRoute;\n" +
                        "\n" +
                        "public class " + utilName + " implements IRoute {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                for (String k : map.keySet()) {
                    writer.write("        ARoute.getInstance().putActivity(\"" + k + "\", " + map.get(k) + ".class);\n");
                }
                writer.write("    }\n" +
                        "}");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
