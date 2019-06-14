package com.nan.nanlib.compile;

import com.google.auto.service.AutoService;
import com.nan.nanlib.request.annotation.RequestApi;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ServiceProcess extends AbstractProcessor {
    private String PACKAGE_NAME = "com.nan.nanlib.request.impl";
    private String IMPORT_RETROFIT_GENERATOR = "import com.nan.nanlib.request.ServiceGenerator;";
    private static final String SUFFIX = "Impl";

    private Messager mMessager;
    private Filer mFiler;
    private Map<String, List<Element>> elementMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        info("AbstractProcessor:  ");
        if (set == null || set.isEmpty()) {
            info(">>> set is null... <<<");
            return true;
        }
        info(">>> Found field, start... <<<");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RequestApi.class);
        if (elements == null || elements.isEmpty()) {
            info(">>> elements is null... <<<");
            return true;
        }



        elementMap = new HashMap<>();
        // 获取所有service注解
        for (Element annotatedElement : elements) {
            info(annotatedElement.getSimpleName().toString() + "  "+ annotatedElement.getKind() + "  mirror size:  "+annotatedElement.getAnnotationMirrors().size());
            // 检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.INTERFACE) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        GET.class.getSimpleName());
                return true; // 退出处理
            }
            String key = annotatedElement.getSimpleName().toString();
            addToElementMap(key, annotatedElement);
        }

        Set<? extends Element> getMethodsElements = roundEnvironment.getElementsAnnotatedWith(GET.class);
        Set<? extends Element> postMethodsElements = roundEnvironment.getElementsAnnotatedWith(POST.class);
        if (getMethodsElements != null && !getMethodsElements.isEmpty()) {
            // 获取所有get注解
            for (Element annotatedElement : getMethodsElements) {
                if (annotatedElement.getKind() != ElementKind.METHOD) {
                    error(annotatedElement, "Only classes can be annotated with @%s",
                            GET.class.getSimpleName());
                    return true; // 退出处理
                }
                Element tm = annotatedElement.getEnclosingElement();
                addToElementMap(tm.getSimpleName().toString(), annotatedElement);
            }
        }

        if (postMethodsElements != null && !postMethodsElements.isEmpty()) {
            for (Element annotatedElement : postMethodsElements) {
                if (annotatedElement.getKind() != ElementKind.METHOD) {
                    error(annotatedElement, "Only classes can be annotated with @%s",
                            POST.class.getSimpleName());
                    return true; // 退出处理
                }
                Element tm = annotatedElement.getEnclosingElement();
                addToElementMap(tm.getSimpleName().toString(), annotatedElement);
            }
        }



        analysisAnnotated();
        createFactory();
        return true;
    }


    private void addToElementMap(String key, Element element) {
        List<Element> elements = new ArrayList<>();
        if (elementMap.containsKey(key)) {
            elements = elementMap.get(key);
        }
        elements.add(element);
        elementMap.put(key, elements);
    }


    private void analysisAnnotated()
    {
        for (String key : elementMap.keySet()) {
            List<Element> elements = elementMap.get(key);
            createImplFile(elements);
        }
        info(">>> analysisAnnotated is finish... <<<");
    }

    private void createImplFile(List<Element> elements) {
        String name = "";
        String qualifiedName = "";
        StringBuilder methodsStr = new StringBuilder();
        for (Element element : elements) {
            if (element.getKind() == ElementKind.INTERFACE) {
                name = element.getSimpleName().toString();;
                TypeElement enclosingElement = (TypeElement) element;
                qualifiedName = enclosingElement.getQualifiedName().toString();
            } else if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement methodElement = (ExecutableElement) element;
                String methodName = element.getSimpleName().toString();
                List<? extends VariableElement> methodParameters = methodElement.getParameters();
                List<String> paramsStrList = new ArrayList<>();
                List<String> paramsValueList = new ArrayList<>();
                for (VariableElement variableElement : methodParameters) {
                    TypeMirror methodParameterType = variableElement.asType();
                    if (methodParameterType instanceof TypeVariable) {
                        TypeVariable typeVariable = (TypeVariable) methodParameterType;
                        methodParameterType = typeVariable.getUpperBound();

                    }
                    //参数名
                    String parameterName = variableElement.getSimpleName().toString();
                    //参数类型
                    String parameteKind = methodParameterType.toString();
                    paramsStrList.add(String.format("%s %s",parameteKind, parameterName));
                    paramsValueList.add(parameterName);
                }
                String returnType = methodElement.getReturnType().toString();//获取TypeMirror;

                methodsStr.append(String.format("\tpublic %s %s(%s) {return api.%s(%s);}\n\n", returnType, methodName,String.join(",", paramsStrList), methodName, String.join(",",paramsValueList)));
            }
        }

        String newClassName = name + SUFFIX;

        // 引入包
        String importStr = String.format("%s\nimport %s;\n",IMPORT_RETROFIT_GENERATOR, qualifiedName);

        // api
        String apiFieldStr = String.format("\tprivate %s api;\n",name);

        // 构造函数
        String constructorStr = String.format("\tpublic %s() {\n\t\tthis.api = ServiceGenerator.createNormalService(%s.class);\n\t}\n\n",newClassName,name);

        StringBuilder builder = new StringBuilder()
                .append("package ")
                .append(PACKAGE_NAME)
                .append(";\n\n")
                .append(importStr)
                .append("public class ")
                .append(newClassName)
                .append(" implements ")
                .append(name)
                .append(" {\n\n") // open class
                .append(apiFieldStr)
                .append(constructorStr)
                .append(methodsStr)
                .append("\n}"); // close class


        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(PACKAGE_NAME+ "." + newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }

    private void createFactory() {
        String name = "";
        StringBuilder importStr = new StringBuilder();
        StringBuilder serviceFieldStr = new StringBuilder();
        StringBuilder methodsStr = new StringBuilder();
        String lockField = "\tprivate static final Object LOCK = new Object();\n";
        for (String key : elementMap.keySet()) {
            List<Element> elements = elementMap.get(key);
            for (Element element : elements) {
                if (element.getKind() == ElementKind.INTERFACE) {
                    name = element.getSimpleName().toString();;
                    TypeElement enclosingElement = (TypeElement) element;
                    String qualifiedName = enclosingElement.getQualifiedName().toString();
                    importStr.append(String.format("import %s;\n", qualifiedName));
                    importStr.append(String.format("import %s.%s;\n",PACKAGE_NAME,name+SUFFIX));

                    serviceFieldStr.append(String.format("\tprivate static %s %s;\n",name, toLowerCaseFirstOne(name)));

                    methodsStr.append(String.format("\tpublic static %s get%s() {\n" +
                            "        synchronized (LOCK) {\n" +
                            "            if (%s == null) {\n" +
                            "                %s = new %s();\n" +
                            "            }\n" +
                            "        }\n" +
                            "        return %s;\n" +
                            "    }\n\n",name, name, toLowerCaseFirstOne(name), toLowerCaseFirstOne(name), name+SUFFIX,toLowerCaseFirstOne(name) ));
                }
            }
        }


        StringBuilder builder = new StringBuilder()
                .append("package ")
                .append(PACKAGE_NAME)
                .append(";\n\n")
                .append(importStr)
                .append("public class ServiceFactory")
                .append(" {\n\n") // open class
                .append(lockField)
                .append(serviceFieldStr)
                .append(methodsStr)
                .append("\n}");

        try {
            JavaFileObject source = mFiler.createSourceFile(PACKAGE_NAME+ "." + "ServiceFactory");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            info(e.getMessage());
        }

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(RequestApi.class.getCanonicalName());
        annotations.add(GET.class.getCanonicalName());
        annotations.add(POST.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args));
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
