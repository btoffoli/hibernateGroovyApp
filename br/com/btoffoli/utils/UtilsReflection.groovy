package br.com.btoffoli.utils

import org.hibernate.annotations.common.reflection.ReflectionUtil
import org.reflections.ReflectionUtils
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

import br.com.btoffoli.dominio.*

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


    Set<Class<? extends Object>> getClassAnnotatedWith(String nmspacePackage){
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage( nmspacePackage ) ).setScanners(
                        new MethodAnnotationsScanner() ) );
        //Set<Method> methods = reflections.getMethodsAnnotatedWith(InstallerMethod.class);
        println "reflections - ${reflections.getTypesAnnotatedWith(Entity, true)}"
        reflections.getTypesAnnotatedWith(Entity);
    }

    Set<Class<? extends Object>> getDomainClass(){
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix('br.com.btoffoli.dominio'))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        println classes

        return classes


    }


    List<Class> listarClassesDeDominio(){
        final String path = './br/com/btoffoli/dominio'
        final File fileDir = new File(path)
        final String pkgName = 'br.com.btoffoli.dominio'
        //return findClasses(dir, pkgName)

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.class.classLoader)

        fileDir.list().collect {String className ->
            className -= '.groovy'
            groovyClassLoader.loadClass("br.com.btoffoli.dominio.$className")}
    }







    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
    throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

/**
 * Recursive method used to find all classes in a given directory and subdirs.
 *
 * @param directory   The base directory
 * @param packageName The package name for classes found inside the base directory
 * @return The classes
 * @throws ClassNotFoundException
 */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }






}