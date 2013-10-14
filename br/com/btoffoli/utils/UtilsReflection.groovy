package br.com.btoffoli.utils

import org.hibernate.annotations.common.reflection.ReflectionUtil
import org.reflections.ReflectionUtils
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

import javax.persistence.Entity

//http://stackoverflow.com/questions/12021543/java-loading-annotated-classes
//http://stackoverflow.com/questions/862106/how-to-find-annotated-methods-in-a-given-package

class UtilsReflection {
	

//	List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
//        final List<Method> methods = new ArrayList<Method>();
//        Class<?> klass = type;
//        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
//            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
//            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
//            for (final Method method : allMethods) {
//                if (annotation == null || method.isAnnotationPresent(annotation)) {
//                    Annotation annotInstance = method.getAnnotation(annotation);
//                    // TODO process annotInstance
//                    methods.add(method);
//                }
//            }
//            // move to the upper class in the hierarchy in search for more methods
//            klass = klass.getSuperclass();
//        }
//        return methods;
//	}


    Set<Class<?>> getClassAnnotatedWith(String nmspacePackage){
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage( nmspacePackage ) ).setScanners(
                        new MethodAnnotationsScanner() ) );
        //Set<Method> methods = reflections.getMethodsAnnotatedWith(InstallerMethod.class);
        reflections.getTypesAnnotatedWith(Entity);
    }

    Set<Class<?>> getDomainClass(){
        String nmspaceDominio = 'br.com.btoffoli.dominio'

        def retorno = getClassAnnotatedWith(nmspaceDominio)

        println retorno.class

        retorno



    }





}