/*
 * 	Copyright 2013 Todd Shore
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.github.errantlinguist.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A utility class for manipulating {@link Iterable} objects containing
 * {@link Integer} objects.
 *
 * @author <a href="mailto:errantlinguist+github@gmail.com">Todd Shore</a>
 * @since 2013-10-22
 *
 */
public final class IntegerIterable {

	/**
	 * Increments all the {@link Integer} elements of a given {@link Collection}
	 * by a given amount.
	 *
	 * @param valuesToIncrement
	 *            The {@code Collection}, the elements of which should be added
	 *            to.
	 * @param increment
	 *            The value to add to each element.
	 * @return A new {@link List} of the elements with the given added value.
	 */
	public static final List<Integer> incrementValues(final Collection<Integer> valuesToIncrement,
			final int increment) {
		assert valuesToIncrement != null;
		final List<Integer> result = new ArrayList<>(valuesToIncrement.size());

		incrementValues(valuesToIncrement, increment, result);

		return result;
	}

	/**
	 * Increments all the {@link Integer} elements of a given {@link Iterable}
	 * by a given amount.
	 *
	 * @param valuesToIncrement
	 *            The {@code Iterable}, the elements of which should be added
	 *            to.
	 * @param increment
	 *            The value to add to each element.
	 * @return A new {@link List} of the elements with the given added value.
	 */
	public static final List<Integer> incrementValues(final Iterable<Integer> valuesToIncrement, final int increment) {
		final List<Integer> result = new LinkedList<>();

		incrementValues(valuesToIncrement, increment, result);

		return result;
	}

	/**
	 * Increments all the {@link Integer} elements of a given {@link Iterable}
	 * by a given amount.
	 *
	 * @param valuesToIncrement
	 *            The {@code Iterable}, the elements of which should be added
	 *            to.
	 * @param increment
	 *            The value to add to each element.
	 * @param incrementedValues
	 *            The list of incremented values, to which each element in the
	 *            given {@code Iterable} are added to after the addition
	 *            operation.
	 */
	public static final void incrementValues(final Iterable<Integer> valuesToIncrement, final int increment,
			final Collection<Integer> incrementedValues) {
		assert valuesToIncrement != null;
		assert incrementedValues != null;

		for (final Integer valueToIncrement : valuesToIncrement) {
			final boolean wasAdded = incrementedValues.add(valueToIncrement + increment);
			assert wasAdded;
		}
	}

	/**
	 * Increments all the {@link Integer} elements of a given {@link Set} by a
	 * given amount.
	 *
	 * @param valuesToIncrement
	 *            The {@code Set} to increment the elements of.
	 * @param increment
	 *            The value to add to each element.
	 * @return A new {@code Set} of the elements with the given added value.
	 */
	public static final Set<Integer> incrementValues(final Set<Integer> valuesToIncrement, final int increment) {
		assert valuesToIncrement != null;
		final Set<Integer> result = new HashSet<>(valuesToIncrement.size());

		incrementValues(valuesToIncrement, increment, result);

		return result;
	}

	private IntegerIterable() {
		// Avoid instantiation
	}

}
