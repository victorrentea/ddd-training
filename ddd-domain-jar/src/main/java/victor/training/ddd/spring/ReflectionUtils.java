package victor.training.ddd.spring;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * Retrieve all <i>declared</i> fields annotated with a given annotation class within a given
     * class.
     * 
     * @param <T> The annotation type
     * @param clazz to search for annotated field
     * @param annotation the annotation object
     * @return all declared fields who are annotated
     */
    public static <T extends Annotation> List<Field> getDeclaredAnnotatedFields(final Class<?> clazz,
            final Class<T> annotation) {
        final List<Field> result = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(annotation) != null) {
                result.add(field);
            }
        }

        return result;

    }

    /**
     * Retrieve all fields annotated with a given annotation class within a given class throughout
     * its class hierarchy.
     * 
     * @param <T> The annotation type
     * @param clazz to search for annotated field
     * @param annotation the annotation object
     * @return all fields who are annotated
     */
    public static <T extends Annotation> List<Field> getAnnotatedFields(final Class<?> clazz, final Class<T> annotation) {
        final List<Field> result = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(annotation) != null) {
                result.add(field);
            }
        }
        if (clazz.getSuperclass() != Object.class) {
            result.addAll(getAnnotatedFields(clazz.getSuperclass(), annotation));
        }
        return result;
    }

    /**
     * Retrieve field by its name within a given class throughout the class hierarchy.
     * 
     * @param clazz the class to search for the field
     * @param fieldName the name of the field to search for
     * @return Field a field with the corresponding fieldName or null if no field was found
     */
    public static Field getField(final Class<?> clazz, final String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }

        if (clazz.getSuperclass() != Object.class) {
            return getField(clazz.getSuperclass(), fieldName);
            // 18:10
        }
        return null;
    }
    
    /**
     * Obtain the value of a field by its name in a given instance in which the field is defined.
     * Retrieve field value by its name within a given object.
     * 
	 * @param source the object containing the value 
     * @param fieldName the name of the field to search for
     * @return the field value
     */
    public static Object getFieldValueFromObjectByFieldName(final Object source, final String fieldName) {
    	return getFieldValue(getField(source.getClass(), fieldName), source);
    }
    
    

    /**
     * Retrieve all fields of a given type within the given class throughout the class hierarchy.
     * 
     * @param <T> The annotation type
     * @param clazz to search in
     * @param type the type of the field to search for
     * @return all fields of the given type
     */
    public static <T extends Object> List<Field> getFields(final Class<T> clazz, final Class<?> type) {
        final List<Field> result = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(type)) {
                result.add(field);
            }
        }
        if (clazz.getSuperclass() != Object.class) {
            result.addAll(getFields(clazz.getSuperclass(), type));
        }
        return result;

    }

    /**
     * Obtain the value of a given field for a given instance in which the field is defined. Remark:
     * in order to obtain this value, the field's accessibility may need to be compromised due to
     * visibility constraints. This of course will only work in case that the actual security
     * manager does not put any restriction on altering the field's accessibility.
     * 
     * @param field the field to get the value for
     * @param source the object containing the value
     * @return the field value
     */
    public static Object getFieldValue(final Field field, final Object source) {
        try {
            if (field.isAccessible()) {
                return field.get(source);
            } else {
                field.setAccessible(true);
                final Object v = field.get(source);
                field.setAccessible(false);
                return v;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access field: " + field.getName() + " check security policy", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Couldn't switch acessible property for field: " + field.getName()
                    + " check security policy", e);
        }
    }

    public static void setFieldValue(final Field field, final Object source, final Object newValue) {
        try {
            if (field.isAccessible()) {
                field.set(source, newValue);
            } else {
                field.setAccessible(true);
                field.set(source, newValue);
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access field: " + field.getName() + " check security policy", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Couldn't switch acessible property for field: " + field.getName()
                    + " check security policy", e);
        }
    }

    /**
     * Test whether a field has been declared as transient.
     * 
     * @param field the field to inspect
     * @return true if the field is transient
     */
    public static boolean isTransientField(final Field field) {
        return Modifier.isTransient(field.getModifiers());
    }

    /**
     * Test whether a field has been declared as static.
     * 
     * @param field the field to inspect
     * @return true if the field is static
     */
    public static boolean isStaticField(final Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * Find class through overall java type information (Type can be a raw type, parameterized type,
     * array type, type variable or a primitive type.
     * 
     * @param type
     * @return Class definition found
     */
    @SuppressWarnings("rawtypes")
    public static Class<?> getClass(final Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            final Type componentType = ((GenericArrayType) type).getGenericComponentType();
            final Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Retrieve all type arguments for a generic class.
     * 
     * @param <T>
     * @param baseClass Anchestor class up to which to navigate in the class hierarchy
     * @param clazz The class for which to retrieve the generic type information
     * @return List of all generic type arguments found
     */
    public static <T> List<Class<?>> getTypeArguments(final Class<T> baseClass, final Class<? extends T> clazz) {
        final Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        if (baseClass == null || !baseClass.isAssignableFrom(clazz) || baseClass.isInterface()) {
            return new ArrayList<Class<?>>();
        }
        Type type = clazz;
        while (type != null && !baseClass.equals(getClass(type))) {
            if (type instanceof Class) {
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                final ParameterizedType parameterizedType = (ParameterizedType) type;
                final Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class<?>) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        final List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            Class<?> baseTypeClazz = getClass(baseType);
            if (baseTypeClazz != null) {
                typeArgumentsAsClasses.add(baseTypeClazz);
            }
        }
        return typeArgumentsAsClasses;
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Variant of Class.getMethod that doesn't throw any Checked exceptions
     * 
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return the Method
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Variant of Method.invoke, that doesn't throw any checked exceptions.
     * 
     * @param obj
     * @param method
     * @param arguments
     * @return
     */
    public static Object invoke(Object obj, Method method, Object... arguments) {
        try {
            return method.invoke(obj, arguments);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCallerMethodName() {
        return new RuntimeException().getStackTrace()[2].getMethodName();
    }
    
    public static void assertNotNullFields(Object object, List<String> excludedFields) throws IllegalAccessException {
		for (Field field : object.getClass().getDeclaredFields()) {
			int suspectModifiers = Modifier.TRANSIENT | Modifier.STATIC | Modifier.PRIVATE | Modifier.FINAL;
			if((field.getModifiers() & suspectModifiers) == suspectModifiers) {
				continue;
			}
			if (! excludedFields.contains(field.getName())) {
				assertNotNull("Field '"+field.getName()+"' of class "+object.getClass()+" is not null", field.get(object));
			}
		}
	}
    
    public static void trimAllStringFields(Object o) {
		for (Field field : o.getClass().getDeclaredFields()) {
			if (!field.getType().equals(String.class)) {
				continue;
			}
			String fieldValue = (String) ReflectionUtils.getFieldValue(field, o);
			if (fieldValue == null) {
				continue;
			}
			ReflectionUtils.setFieldValue(field, o, fieldValue.trim());
		}
    }
    
    
    public static Method getGetter(Field field) {
    	String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		return getMethod(field.getDeclaringClass(), getterName);
    }
    
    public static Method getSetter(Field field) {
    	String setterName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		return getMethod(field.getDeclaringClass(), setterName, field.getType());
    }
    

}
