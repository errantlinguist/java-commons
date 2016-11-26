/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 */
package com.github.errantlinguist.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ian Robertson
 * @since 2003-06-27
 * @see <a href=
 *      "http://www.artima.com/weblogs/viewpost.jsp?thread=208860">http://www.artima.com/weblogs/viewpost.jsp?thread=208860</a>
 *
 */
public final class ReflectingGenerics {

	/**
	 * Returns an array of {@link Type} objects representing the actual type
	 * arguments to the given {@code Type}, regardless if it represents a
	 * parameterized type or not.
	 *
	 * @param type
	 *            The {@code Type} to get the arguments of.
	 * @return An array of {@code Type} objects representing the actual type
	 *         arguments to this type.
	 */
	public static final Type[] getActualTypeArguments(final Type type) {
		final Type[] result;

		if (type instanceof Class<?>) {
			result = ((Class<?>) type).getTypeParameters();
		} else {
			result = ((ParameterizedType) type).getActualTypeArguments();
		}

		return result;
	}

	/**
	 * Get the underlying class for a type, or null if the type is a variable
	 * type.
	 *
	 * @since 2003-06-27
	 * @see <a href=
	 *      "http://www.artima.com/weblogs/viewpost.jsp?thread=208860">http://www.artima.com/weblogs/viewpost.jsp?thread=208860</a>
	 * @param type
	 *            the type
	 * @return the underlying class
	 */
	public static final Class<?> getClass(final Type type) {
		final Class<?> result;

		if (type instanceof Class<?>) {
			result = (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			final Type rawType = ((ParameterizedType) type).getRawType();
			result = getClass(rawType);
		} else if (type instanceof GenericArrayType) {
			final Type componentType = ((GenericArrayType) type).getGenericComponentType();
			final Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				result = Array.newInstance(componentClass, 0).getClass();
			} else {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Get the actual type arguments a child class has used to extend a generic
	 * base class.
	 *
	 * @since 2003-06-27
	 * @see <a href=
	 *      "http://www.artima.com/weblogs/viewpost.jsp?thread=208860">http://www.artima.com/weblogs/viewpost.jsp?thread=208860</a>
	 * @param baseClass
	 *            the base class
	 * @param childClass
	 *            the child class
	 * @return a list of the raw classes for the actual type arguments.
	 */
	public static final <T> List<Class<?>> getTypeArguments(final Class<T> baseClass,
			final Class<? extends T> childClass) {
		final Map<Type, Type> typeParameterActualArguments = new HashMap<>();
		final Type type = mapTypeParametersToActualArguments(baseClass, childClass, typeParameterActualArguments);
		// finally, for each actual type argument provided to baseClass,
		// determine (if possible)
		// the raw class for that type argument.
		return createTypeArgumentList(type, typeParameterActualArguments);
	}

	/**
	 * Recursively finds the actual types of the parameters of a generic
	 * {@link Class}.
	 *
	 * @param baseClass
	 *            The base {@code Class} defining the generic parameters to get
	 *            the actual types of.
	 * @param childClass
	 *            The child {@code Class} to get the actual type parameters of.
	 * @param typeParameterActualArguments
	 *            A {@link Map} of the {@code Type} objects from the raw type,
	 *            mapped to the {@code Type} object representing the actual type
	 *            of the parameter.
	 * @return A {@link Type} object representing the base class.
	 */
	public static final <T> Type mapTypeParametersToActualArguments(final Class<T> baseClass,
			final Class<? extends T> childClass, final Map<Type, Type> typeParameterActualArguments) {
		Type result = childClass;
		// start walking up the inheritance hierarchy until we hit baseClass
		while (!getClass(result).equals(baseClass)) {
			if (result instanceof Class<?>) {
				// there is no useful information for us in raw types, so just
				// keep going.
				result = ((Class<?>) result).getGenericSuperclass();
			} else {
				final ParameterizedType parameterizedType = (ParameterizedType) result;
				final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
				mapTypeParametersToActualArguments(rawType, parameterizedType, typeParameterActualArguments);

				if (!rawType.equals(baseClass)) {
					result = rawType.getGenericSuperclass();
				}
			}
		}

		return result;
	}

	/**
	 * Maps the {@link TypeVariable types} of the parameters of the type
	 * represented by a raw {@link Class} object to the actual object
	 * {@link Type} each represent.
	 *
	 * @param parameterizedType
	 *            The {@code ParameterizedType} to map the types of.
	 * @param resolvedTypes
	 *            A {@link Map} of the {@code Type} objects from the raw type,
	 *            mapped to the {@code Type} object representing the actual type
	 *            of the parameter.
	 */
	public static final void mapTypeParametersToActualArguments(final ParameterizedType parameterizedType,
			final Map<Type, Type> resolvedTypes) {
		final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
		mapTypeParametersToActualArguments(rawType, parameterizedType, resolvedTypes);
	}

	/**
	 * Creates a {@link List} of {@link Class} objects which represent the
	 * actual types denoted by the parameters of a given {@link Type}, if it has
	 * any.
	 *
	 * @param type
	 *            The {@code Type} to get the parameter types of.
	 * @param typeParameterActualArguments
	 *            A {@link Map} of the {@code Type} objects from the raw type,
	 *            mapped to the {@code Type} object representing the actual type
	 *            of the parameter.
	 * @return A {@code List} of {@code Class} objects, for which the object at
	 *         index <em>i</em> represents the type of the {@code Type} object
	 *         at index <em>i</em> of the array returned by
	 *         {@link #getActualTypeArguments(Type)}.
	 */
	private static final List<Class<?>> createTypeArgumentList(final Type type,
			final Map<Type, Type> typeParameterActualArguments) {
		final Type[] actualTypeArguments = getActualTypeArguments(type);
		final List<Class<?>> result = new ArrayList<>(actualTypeArguments.length);

		// resolve types by chasing down type variables.
		for (Type baseType : actualTypeArguments) {
			while (typeParameterActualArguments.containsKey(baseType)) {
				baseType = typeParameterActualArguments.get(baseType);
			}
			result.add(getClass(baseType));
		}

		return result;
	}

	/**
	 * Maps the {@link TypeVariable types} of the parameters of the type
	 * represented by a raw {@link Class} object to the actual object
	 * {@link Type} each represent.
	 *
	 * @param rawType
	 *            The raw {@link Class} object to get the actual type arguments
	 *            of. * @param parameterizedType The {@code ParameterizedType}
	 *            to map the get actual type arguments from.
	 * @param typeParameterActualArguments
	 *            A {@link Map} of the {@code Type} objects from the raw type,
	 *            mapped to the {@code Type} object representing the actual type
	 *            of the parameter.
	 */
	private static final void mapTypeParametersToActualArguments(final Class<?> rawType,
			final ParameterizedType parameterizedType, final Map<Type, Type> typeParameterActualArguments) {
		final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
		final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		for (int i = 0; i < actualTypeArguments.length; i++) {
			typeParameterActualArguments.put(typeParameters[i], actualTypeArguments[i]);
		}
	}

	private ReflectingGenerics() {
		// Avoid instantiation
	}
}
