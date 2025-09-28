package io.bluebeaker.mteenoughitems.utils;

import io.bluebeaker.mteenoughitems.MTEEnoughItems;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class FieldAccessor<C,T> {
    private Field field;
    public FieldAccessor(Class<C> clazz, String fieldName){
        try {
            field=clazz.getField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            MTEEnoughItems.getLogger().error("Error init accessor for {}.{}:",clazz,fieldName,e);
        }
    }
    @Nullable
    public T get(C object){
        try {
            Object o = field.get(object);
            return (T) o;
        } catch (Throwable e) {
            MTEEnoughItems.getLogger().error("Error getting field {}:",field,e);
        }
        return null;
    }
}
