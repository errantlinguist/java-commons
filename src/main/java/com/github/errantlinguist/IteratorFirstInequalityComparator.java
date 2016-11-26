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
package com.github.errantlinguist;

import java.util.Comparator;
import java.util.Iterator;

/**
 * A {@link Comparator} which compares two {@link Iterator iterators} of objects
 * in a "first past the post" fashion, whereby each {@link Iterator#next() next
 * element} of one {@code Iterator} is compared against the next of the other
 * until the result of comparing the two with
 * {@link Comparable#compareTo(Object)} is a non-zero value.
 *
 * @param <E>
 *            the type of the objects to compare.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-16
 *
 */
public final class IteratorFirstInequalityComparator<E extends Comparable<E>> implements Comparator<Iterator<E>> {

	/**
	 * Compares two {@link Iterator} objects based on the value of their first
	 * elements, if they have any; In the case that one has at least one element
	 * but the other has none, the one with no elements is considered to be
	 * "less than" the one with elements, i.e.&nbsp; occurs before the one with
	 * elements in a natural ordering.
	 *
	 * @param iter1
	 *            The first {@code Iterator} to compare.
	 * @param iter2
	 *            The second {@code Iterator} to compare.
	 * @return a negative integer, zero, or a positive integer as the first
	 *         element of the first argument is less than, equal to, or greater
	 *         than the first element of the second.
	 */
	private static final <T extends Comparable<T>> int compareFirstItems(final Iterator<T> iter1,
			final Iterator<T> iter2) {
		assert iter1 != null;
		assert iter2 != null;
		final int result;

		if (iter1.hasNext()) {
			result = compareToNextItem(iter1, iter2);
		} else if (iter2.hasNext()) {
			result = -1;
		} else {
			result = 0;
		}

		return result;
	}

	/**
	 * Compares the next element of a given {@link Iterator} against the next
	 * element of another {@code Iterator}, if it has any; If the other has
	 * none, it is considered to be "less than" the first one, i.e.&nbsp; occurs
	 * before the first one in a natural ordering.
	 *
	 * @param iter
	 *            The non-empty {@code Iterator} to compare.
	 * @param otherIter
	 *            The {@code Iterator} to compare the non-empty {@code Iterator}
	 *            to.
	 * @return a negative integer, zero, or a positive integer as the next
	 *         element of the first argument is less than, equal to, or greater
	 *         than the next element of the second.
	 */
	private static final <T extends Comparable<T>> int compareToNextItem(final Iterator<T> iter,
			final Iterator<T> otherIter) {
		assert iter != null;
		assert otherIter != null;
		final int result;

		if (otherIter.hasNext()) {
			final T element1 = iter.next();
			final T element2 = otherIter.next();

			result = element1.compareTo(element2);

		} else {
			result = 1;
		}

		return result;
	}

	@Override
	public int compare(final Iterator<E> o1, final Iterator<E> o2) {
		assert o1 != null;
		assert o2 != null;
		int result = compareFirstItems(o1, o2);
		while (result == 0 && o1.hasNext()) {
			result = compareToNextItem(o1, o2);
		}
		// If the elements are all equal, try comparing against the rest of the
		// second iterator's items (if it has any)
		if (result == 0) {
			result = compare(o2, o1);
		}

		return result;
	}

}
