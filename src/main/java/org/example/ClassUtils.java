package org.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  Utils for finding all classes loaded
 */
public class ClassUtils {

    /**
     * find classes by package name
     *
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Class<?>[] findClasses(String packageName) throws IOException, ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();
        File[] files = getFiles(packageName.replace(".", "/"));
        for (File file : files) {
            // get class's full name from file's absolute path
            String classDotPath = file.getPath().replace("\\", ".");
            String classFullName = classDotPath.substring(classDotPath.indexOf(packageName)).replaceAll(".class$", "");

            Class<?> cls = Class.forName(classFullName);
            classes.add(cls);
        }

        return classes.toArray(new Class<?>[0]);
    }

    /**
     * find classes by package name array
     *
     * @param packageNames
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Class<?>[] findClasses(String[] packageNames) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        for (String packageName : packageNames) {
            classes.addAll(Arrays.asList(findClasses(packageName)));
        }

        return classes.toArray(new Class<?>[0]);
    }

    /**
     * get files by package root
     *
     * @param root
     * @return
     * @throws IOException
     */
    private static File[] getFiles(String root) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> subUrls = classLoader.getResources(root);
        List<File> files = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();
        while (subUrls.hasMoreElements()) {
            URL subUrl = subUrls.nextElement();
            File file = new File(subUrl.getFile());
            files.addAll(listFiles(file));
        }

        return files.toArray(new File[0]);
    }

    /**
     * Recursion find files for sub directories
     * @param file
     * @return
     * @throws IOException
     */
    private static List<File> listFiles(File file) throws IOException {
        File[] tmpFiles = file.listFiles((dir, name) -> name.endsWith(".class"));
        List<File> files = new ArrayList<>();
        if (tmpFiles != null) {
            files.addAll(Arrays.asList(tmpFiles));
        }

        List<File> subDirectories = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory)
                .collect(Collectors.toList());

        for(File subDirectory:subDirectories){
            files.addAll(listFiles(subDirectory));
        }

        return files;
    }
}