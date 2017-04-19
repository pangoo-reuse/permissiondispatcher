package com.xy.open;

import com.xy.open.bean.ParserBean;
import com.xy.open.bean.Property;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by yonehsiung@gmail.com on 2017/4/14.
 */

@SupportedAnnotationTypes("com.xy.open.RequestPermissions")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PermissionsProcessor extends AbstractProcessor {
    private static final String PREFIX = "check";
    private static final String SUFFIX = "WithPermissions";
    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(RequestPermissions.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    static String PACKAGE = "com.xy.open.processor.generated";
    static String GENERATED_CLASS_NAME = "PermissionCheck";
    static String DATA = "Properties";
    static String PARSER = "Parser";


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Property properties = process(roundEnv);
        if (properties != null){
            writeParser();
            writeProperties(properties);
        }
        return true;
    }

    StringBuffer importBuilder = new StringBuffer();

    private Property process(RoundEnvironment roundEnv) {
        Set<String> importSet = new LinkedHashSet<String>();
        try {
            importBuilder.delete(0, importBuilder.length());
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringBuilder mainBuilder = new StringBuilder()
                .append("package " + PACKAGE + ";\n\n");
        Property property = new Property();
        property.properties = new ArrayList<Property.Properties>();
        StringBuilder bodyBuilder = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(RequestPermissions.class)) {
            if (element.getKind() != ElementKind.METHOD) {
                error("Only method can be annotated with @%s",
                        RequestPermissions.class.getSimpleName());
                return null; // exit
            }

            Property.Properties properties = new Property.Properties();
            LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
            List<String> excepti = new ArrayList<String>();

            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String fullClassName = typeElement.getQualifiedName().toString();
            String simpleMethodName = element.getSimpleName().toString();


            RequestPermissions annotation = element.getAnnotation(RequestPermissions.class);
            String[] permissions = annotation.permissions();
            int grantCode = annotation.grantCode();
            String denyMessage = annotation.denyMessage();

            properties.grantCode = grantCode;
            properties.denyMessage = denyMessage;
            properties.permissions = Arrays.asList(permissions);

            StringBuilder argBuilder = new StringBuilder();
            TypeKind kind = element.asType().getKind();
            if (kind == TypeKind.EXECUTABLE) {
                ExecutableElement executableElement = (ExecutableElement) element;
                List<? extends VariableElement> parameters = executableElement.getParameters();
                List<? extends TypeMirror> thrownTypes = executableElement.getThrownTypes();
                TypeMirror returnType = executableElement.getReturnType();
                String returnTypeStr = returnType.toString();

                if (parameters != null && parameters.size() != 0) {
                    for (VariableElement variableElement : parameters) {
                        TypeMirror typeMirror = variableElement.asType();
                        String type = typeMirror.toString();
                        String paraName = variableElement.getSimpleName().toString();
                        params.put(type, paraName);
                        addImport(importSet, type);
                    }
                }

                if (thrownTypes != null && thrownTypes.size() != 0) {
                    for (TypeMirror tm : thrownTypes) {
                        TypeKind thrownTk = tm.getKind();
                        String thrownStr = tm.toString();
                        excepti.add(thrownStr);
                        addImport(importSet, thrownStr);
                    }
                }
                properties.method = fullClassName + "." + simpleMethodName;
                String methodName = buildMethodName(simpleMethodName);
                bodyBuilder.append("\n\t@MethodTarget(\"" + properties.method + "\")\n");
                bodyBuilder.append("\tpublic  final void " + methodName + "(");

                if (params != null && params.size() != 0) {
                    properties.paramPairs = params;
                    int i = 0;
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        String def = entry.getKey();
                        String filed = entry.getValue();
                        if (i == 0) {
                            bodyBuilder.append(" " + def + " " + filed + " ");
                            argBuilder.append(filed);
                        } else {
                            bodyBuilder.append(" , " + def + " " + filed + " ");
                            argBuilder.append(" , " + filed);
                        }
                        ++i;
                    }
                }
                bodyBuilder.append(")");
                if (excepti != null && excepti.size() != 0) {
                    bodyBuilder.append(" throws ");
                    for (int i = 0; i < excepti.size(); i++) {
                        String exp = excepti.get(i);
                        if (i == 0) {
                            bodyBuilder.append(exp);
                        } else {
                            bodyBuilder.append("," + exp);
                        }
                    }
                }
                bodyBuilder.append("{\n\t\t");

            }

            // this is appending to the return statement
            bodyBuilder.append("System.out.println(\"" + simpleMethodName + " says hello!\");\n");
            String methods = "";
            if (params != null && params.size() != 0) {
                methods = properties.method + "\",";
            } else {
                methods = properties.method + "\"";
            }
            bodyBuilder.append("\t\tparser(this,\"" + methods + argBuilder.toString() + ");\n\n");

            bodyBuilder.append("\t}\n"); // close method

            property.properties.add(properties);
        }
        bodyBuilder.append("\n\n\tpublic Property.Properties parser(PermissionCheck object, String method, Object... args) {\n" +
                "\t\tif (object != null) {\n" +
                "\t\t\tfor (Property.Properties permission : permissions) {\n" +
                "\t\t\t\tif (permission.method.equals(method)) {\n" +
                "\t\t\t\t\tparser.setData(permission, args);\n" +
                "\t\t\t\t\tparser.parse(permission, args);\n" +
                "\t\t\t\t\treturn permission;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\treturn null;\n" +
                "\t}");
        mainBuilder.append("import com.xy.open.permission.Util;\n\n")
                .append("import com.xy.open.MethodTarget;")
                .append("import com.xy.open.bean.Property;")
                .append("\nimport java.util.List;\n")
                .append(importBuilder)
                .append("\n\n")
                .append("public class " + GENERATED_CLASS_NAME + "{\n\n")
                .append(
                        "\tprivate static PermissionCheck permissionCheck;\n" +
                        "\tprivate final List<Property.Properties> permissions;\n" +
                        "\tprivate Parser parser;\n" +
                        "\n" +
                        "\tpublic PermissionCheck() {\n" +
                        "    if (parser == null) parser = Parser.getInstances();\n" +
                        "\t\tProperty property = Util.buildProperty(Properties.properties);\n" +
                        "\t\tthis.permissions = property.properties;\n" +
                        "\t}\n" +
                        "\tpublic static PermissionCheck getInstants() {\n" +
                        "    if (permissionCheck == null) {\n" +
                        "         permissionCheck = new PermissionCheck();\n" +
                        "    }\n" +
                        "    return permissionCheck;\n" +
                        "\t}")

                .append(bodyBuilder)
                .append("}\n"); // close class
        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + GENERATED_CLASS_NAME);


            Writer writer = source.openWriter();
            writer.write(mainBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
        return property;
    }

    private void addImport(Set<String> importSet, String type) {
        if (type.contains(".")) {
            boolean add = importSet.add(type);
            if (add) {
                importBuilder.append("\n")
                        .append("import ")
                        .append(type)
                        .append(";\n");
            }

        }
    }

    private void writeProperties(Property property) {
        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + DATA);
            StringBuilder builder = new StringBuilder();
            builder.append("package " + PACKAGE + ";\n\n");
            builder.append("import java.lang.System; \n");
            builder.append("import java.lang.String; \n\n");
            builder.append("public interface " + DATA + "{\n\n");
            builder.append("String properties = ");
            builder.append(property.toString());
            builder.append(";");
            builder.append("\n\n}");// method close
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    private void writeParser() {
        try {
            JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + PARSER);
            Writer writer = source.openWriter();
            writer.write(ParserBean.parserBean);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildMethodName(String simpleMethodName) {
        String start = simpleMethodName.substring(0, 1);
        String end = simpleMethodName.substring(1, simpleMethodName.length());
        return PREFIX + start.toUpperCase() + end + SUFFIX;
    }


    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }


}
