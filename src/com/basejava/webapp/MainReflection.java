package com.basejava.webapp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException {
//        Resume resume = new Resume();
//        Field field = resume.getClass().getDeclaredFields()[0];
//        field.setAccessible(true);
//        System.out.println("Имя поля: " + field.getName());
//        System.out.println("uuid объекта resume: " + field.get(resume));
//        field.set(resume, "new_uuid");
//        System.out.println("Новый uuid объекта resume: " + field.get(resume));

        Class resumeClass = Class.forName("com.basejava.webapp.model.Resume");
        Constructor constructor = resumeClass.getConstructor(String.class);
        Object obj = constructor.newInstance("test");
        Method method = resumeClass.getMethod("toString");
        System.out.println(method.invoke(obj));
    }
}
