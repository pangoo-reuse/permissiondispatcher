package com.xy.open.bean;

/**
 * Created by admin on 2017/4/18.
 */

public interface ParserBean {
    String parserBean = "package com.xy.open.processor.generated;\n" +
            "\n" +
            "import android.text.TextUtils;\n" +
            "import android.util.Log;\n" +
            "\n" +
            "import com.xy.open.GrantListener;\n" +
            "import com.xy.open.MethodTarget;\n" +
            "import com.xy.open.bean.ClassValue;\n" +
            "import com.xy.open.bean.Property;\n" +
            "import com.xy.open.permission.Dispatch;\n" +
            "import com.xy.open.permission.Dispatcher;\n" +
            "import com.xy.open.permission.RequestListener;\n" +
            "import com.xy.open.pool.BaseTask;\n" +
            "import com.xy.open.util.MappingObject;\n" +
            "\n" +
            "import java.lang.annotation.Annotation;\n" +
            "import java.lang.reflect.InvocationTargetException;\n" +
            "import java.lang.reflect.Method;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.LinkedHashMap;\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "import java.util.Set;\n" +
            "\n" +
            "/**\n" +
            " * Created by yonehsiung@gmail.com on 2017/4/11.\n" +
            " */\n" +
            "\n" +
            "public class Parser extends BaseTask {\n" +
            "    private static Parser parsePermission;\n" +
            "    private Map<String, GrantListener> callbacks = new LinkedHashMap<String, GrantListener>();\n" +
            "    private Dispatch dispatcher;\n" +
            "\n" +
            "    public static Parser getInstances() {\n" +
            "        if (parsePermission == null) {\n" +
            "            parsePermission = new Parser();\n" +
            "\n" +
            "        }\n" +
            "        return parsePermission;\n" +
            "    }\n" +
            "\n" +
            "    public Parser() {\n" +
            "        if (dispatcher == null) {\n" +
            "            dispatcher = Dispatcher.instants.getDispatcher();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public String[] getMethodParameters(Method method) {\n" +
            "        Annotation[][] parameterAnnotations = method.getParameterAnnotations();\n" +
            "        if (parameterAnnotations == null || parameterAnnotations.length == 0) {\n" +
            "            return null;\n" +
            "        }\n" +
            "        String[] parameterNames = new String[parameterAnnotations.length];\n" +
            "        int i = 0;\n" +
            "        for (Annotation[] parameterAnnotation : parameterAnnotations) {\n" +
            "            for (Annotation annotation : parameterAnnotation) {\n" +
            "                if (annotation instanceof MethodTarget) {\n" +
            "                    MethodTarget param = (MethodTarget) annotation;\n" +
            "                    parameterNames[i++] = param.value();\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return parameterNames;\n" +
            "    }\n" +
            "\n" +
            "    public void parse(Property.Properties permission, Object... args) {\n" +
            "        if (permission != null){\n" +
            "            innerParser(permission, args);\n" +
            "            runTask();\n" +
            "        }\n" +
            "            \n" +
            "    }\n" +
            "\n" +
            "    private void innerParser(final Property.Properties permission, final Object... args) {\n" +
            "        String[] permissions = permission.permissions.toArray(new String[permission.permissions.size()]);\n" +
            "        if (dispatcher.checkNeedGrantedPermission(permission.grantCode, permission.denyMessage, permissions)) {\n" +
            "            dispatcher.setCheckedListener(new RequestListener() {\n" +
            "                @Override\n" +
            "                public void requestResult(int requestCode, String[] permissions, boolean promise) {\n" +
            "                    run(permission.invoke,permission.method, permission.paramPairs, promise, args);\n" +
            "                }\n" +
            "            });\n" +
            "            dispatcher.requestPermission(permission.grantCode, permissions);\n" +
            "        } else {\n" +
            "            run(permission.invoke,permission.method, permission.paramPairs, true, args);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    private void invoke(String className, String simpleMethodName, boolean promise, ClassValue params) {\n" +
            "        if (params != null) {\n" +
            "            try {\n" +
            "                Class<?> clazz = Class.forName(className);\n" +
            "                try {\n" +
            "                    try {\n" +
            "                        Object[] paramsFields = params.getFields();\n" +
            "                        Method method = clazz.getMethod(simpleMethodName, params.getClasses());\n" +
            "                        Object invoke = method.invoke(clazz.newInstance(), paramsFields);\n" +
            "                        if (callbacks.size() != 0) {\n" +
            "                            GrantListener listener = callbacks.remove(simpleMethodName);\n" +
            "                            if (listener != null)\n" +
            "                                listener.onPromise(promise, invoke);\n" +
            "                           \n" +
            "                        }\n" +
            "                        remove(this);\n" +
            "                        \n" +
            "                    } catch (NoSuchMethodException e) {\n" +
            "                        e.printStackTrace();\n" +
            "                    }\n" +
            "                } catch (IllegalAccessException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                } catch (InstantiationException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                } catch (InvocationTargetException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                }\n" +
            "            } catch (ClassNotFoundException e) {\n" +
            "                e.printStackTrace();\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public void run(boolean invoke, String fullMethodName, LinkedHashMap<String, String> params, boolean promise, Object[] args) {\n" +
            "        String[] split = fullMethodName.split(\"\\\\.\");\n" +
            "        if (split != null && split.length != 0) {\n" +
            "            String simpleMethodName = split[split.length - 1];\n" +
            "            String className = fullMethodName.substring(0, fullMethodName.lastIndexOf(\".\" + simpleMethodName));\n" +
            "            ClassValue classes = getClasses(simpleMethodName, params, args);\n" +
            "            if (invoke)\n" +
            "            invoke(className, simpleMethodName, promise, classes);\n" +
            "            else {\n" +
            "                if (callbacks.size() != 0) {\n" +
            "                    GrantListener listener = callbacks.remove(simpleMethodName);\n" +
            "                    if (listener != null)\n" +
            "                        listener.onPromise(promise, null);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private ClassValue getClasses(String simpleMethodName, LinkedHashMap<String, String> params, Object[] fields) {\n" +
            "        List<Class> classes = new ArrayList<Class>();\n" +
            "        List<Object> args = new ArrayList<Object>();\n" +
            "        if (params != null && params.size() != 0) {\n" +
            "            Set<String> keySet = params.keySet();\n" +
            "            String[] keys = keySet.toArray(new String[keySet.size()]);\n" +
            "            for (int i = 0; i < keys.length; i++) {\n" +
            "                String key = keys[i];\n" +
            "                String unpackFiled = MappingObject.getUnpackFiled(key);\n" +
            "                String type = TextUtils.isEmpty(unpackFiled) ? key : unpackFiled;\n" +
            "                Object field = fields[i];\n" +
            "                if (type.contains(\".\")) {\n" +
            "                    try {\n" +
            "                        Class<?> classType = Class.forName(type);\n" +
            "                        classes.add(classType);\n" +
            "\n" +
            "                    } catch (ClassNotFoundException e) {\n" +
            "                        Log.d(\"Wrap_Object\", e.getMessage());\n" +
            "\n" +
            "                    }\n" +
            "                } else {\n" +
            "                    classes.add(MappingObject.getFiled(type));\n" +
            "                }\n" +
            "                args.add(field);\n" +
            "                if (field instanceof GrantListener) {\n" +
            "                    callbacks.put(simpleMethodName, (GrantListener) field);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        try {\n" +
            "            Class[] classArray = classes.toArray(new Class[classes.size()]);\n" +
            "            Object[] argsArray = args.toArray(new Object[args.size()]);\n" +
            "            return new ClassValue(classArray, argsArray);\n" +
            "        } catch (Exception e) {\n" +
            "            e.printStackTrace();\n" +
            "        }\n" +
            "        return null;\n" +
            "    }\n" +
            "\n" +
            "}\n";
}
