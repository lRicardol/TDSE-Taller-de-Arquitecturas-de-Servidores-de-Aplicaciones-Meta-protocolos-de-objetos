package org.example;

import org.example.framework.MicroFramework;
import org.example.framework.annotations.GetMapping;
import org.example.framework.annotations.RequestParam;
import org.example.framework.annotations.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MicroSpringBoot {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: MicroSpringBoot <ControllerClass>");
            return;
        }

        String controllerClassName = args[0];

        Class<?> controllerClass = Class.forName(controllerClassName);

        if (controllerClass.isAnnotationPresent(RestController.class)) {

            Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();

            Method[] methods = controllerClass.getDeclaredMethods();

            for (Method method : methods) {

                if (method.isAnnotationPresent(GetMapping.class)) {

                    GetMapping mapping = method.getAnnotation(GetMapping.class);
                    String path = mapping.value();

                    MicroFramework.get(path, (req, res) -> {

                        try {

                            Parameter[] parameters = method.getParameters();
                            Object[] argsValues = new Object[parameters.length];

                            for (int i = 0; i < parameters.length; i++) {

                                Parameter param = parameters[i];

                                if (param.isAnnotationPresent(RequestParam.class)) {

                                    RequestParam requestParam = param.getAnnotation(RequestParam.class);

                                    String name = requestParam.value();
                                    String defaultValue = requestParam.defaultValue();

                                    String value = req.getValues(name);

                                    if (value == null) {
                                        value = defaultValue;
                                    }

                                    argsValues[i] = value;
                                }
                            }

                            return (String) method.invoke(controllerInstance, argsValues);

                        } catch (Exception e) {
                            e.printStackTrace();
                            return "Error executing method";
                        }
                    });

                    System.out.println("Registered route: " + path);
                }
            }
        }

        MicroFramework.staticfiles("webroot");
        MicroFramework.start(8080);
    }
}