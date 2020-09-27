package com.transform.internel

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

class Inject {
    private static ClassPool pool = ClassPool.getDefault()


    static void injectDir(String path, Project project) {
        pool.appendClassPath(path)
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (filePath.endsWith(".class")) {
                    String className = filePath.replace('\\', '.').replace('/', '.')
                    Map<String, Map<String,Class>> map = project.abtest.data
                    map.each { item ->
                        String clz = item.key
                        if (className.contains(clz)) {
                            className = clz.substring(0, clz.length() - 6)
                            println('-------map clz-----' + className)
                            Class mockClass = Class.forName(clz)
                            mockClass.getAnnotation()
                            injectClass(className, path, clz, item.value)
                        }
                    }

                }
            }
        }
    }

    private static void injectClass(String className, String path, String clz, Map<String,Class> map) {
        CtClass ctClass = pool.get(className)
        if (ctClass == null) {
            return
        }
        map.each { method,returnType ->
            CtMethod ctMethod = ctClass.getDeclaredMethod(method)
            if (ctMethod != null) {
                println('des:' + getDes(className, method,returnType))
                ctMethod.insertBefore(getDes(className, method,returnType))
            }
        }

        ctClass.writeFile(path)
        ctClass.detach()
    }


    private static String getDes(String clz, String method,Class returnType) {
        return "if (com.leo.events.abtest.ABTest.isDebug()) {" +
                "            com.leo.events.abtest.ABTestModel abTestModel = com.leo.events.abtest.ABTest.get(\"" + clz + "\",\"" + method + "\");" +
                "            if (abTestModel != null) {" +
                "                return "+ getReturnValue(returnType)+";"+
                "            }" +
                "        }"
    }


    private static String getReturnValue(Class returnType){
        if (returnType.isAssignableFrom(String.class)){
            return 'abTestModel.value'
        }else if (returnType.isAssignableFrom(int.class)){
            return 'Integer.parseInt(abTestModel.value)'
        }else if (returnType.isAssignableFrom(long.class)){
            return 'Long.parseLong(abTestModel.value)'
        }else if (returnType.isAssignableFrom(double .class)){
            return 'Double.parseDouble(abTestModel.value)'
        }else if (returnType.isAssignableFrom(float .class)){
            return 'Float.parseFloat(abTestModel.value)'
        }else if (returnType.isAssignableFrom(boolean .class)){
            return 'Boolean.parseBoolean(abTestModel.value)'
        }else if (returnType.isAssignableFrom(short .class)){
            return 'Short.parseShort(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Integer.class)){
            return 'Integer.valueOf(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Long.class)){
            return 'Long.valueOf(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Double.class)){
            return 'Double.valueOf(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Float.class)){
            return 'Float.valueOf(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Boolean.class)){
            return 'Boolean.valueOf(abTestModel.value)'
        }else if (returnType.isAssignableFrom(Short.class)){
            return 'Short.valueOf(abTestModel.value)'
        }
    }

//
}
