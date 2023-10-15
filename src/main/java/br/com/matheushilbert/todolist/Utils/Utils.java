package br.com.matheushilbert.todolist.Utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source,target, getNullProperties(source));
    }

    public static String[] getNullProperties(Object source){

        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

        Set<String> nullProperties = new HashSet<>();

        for (PropertyDescriptor property : propertyDescriptors){
            Object PropertyValue = src.getPropertyValue(property.getName());
            if (PropertyValue == null){
                nullProperties.add(property.getName());
            }
        }
        String[] result = new String[nullProperties.size()];
        return nullProperties.toArray(result);
    }
}
