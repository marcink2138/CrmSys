package com.example.crmapi.reflectionApi;

import com.example.crmapi.lead.LeadType;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class ReflectionApiInvoker {
    private Method method;
    private String methodName;
    private Class<?> parameterType;

    public void runInvocation(Object obj, String varName, Object varValue) {
        setParametersType(obj, varName);
        setMethod(obj, methodName, parameterType);
        invokeMethod(obj, varValue);
    }


    public void createSetterMethodName(String classFieldName) {
        methodName = "set" + classFieldName.substring(0, 1).toUpperCase(Locale.ROOT) + classFieldName.substring(1);
    }

    private void setParametersType(Object obj, String variableName) {
        try {
            parameterType = obj.getClass().getDeclaredField(variableName).getType();
        } catch (NoSuchFieldException e) {
            String error = "Cannot find variable " + variableName + " in " + obj.getClass().getName();
            throw new NotYetImplementedException(error);
        }
    }

    private void setMethod(Object obj, String methodName, Class<?>... parametersTypes) {
        try {
            method = obj.getClass().getMethod(methodName, parametersTypes);
        } catch (NoSuchMethodException e) {
            String error = "Cannot find method " + methodName + " in " + obj.getClass().getName();
            throw new NotYetImplementedException(error);
        }
    }

    private void invokeMethod(Object obj, Object argValue) {
        try {
            if (parameterType.equals(String.class)) {
                    method.invoke(obj, argValue);
                    return;
            }
            if (parameterType.equals(LeadType.class)){
                method.invoke(obj, Enum.valueOf(LeadType.class, (String) argValue));
                return;
            }
            if (parameterType.equals(LocalDateTime.class)){
                method.invoke(obj, LocalDateTime.parse(argValue.toString()));
            }
            else
                method.invoke(obj, Long.parseLong((String) argValue));

        } catch (IllegalAccessException | InvocationTargetException e) {
            String error = "Internal Java error";
            throw new NotYetImplementedException(error + e);
        }
    }
}
