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
 * A {@link Comparator} for comparing {@link Iterable} objects by wrapping a
 * {@code Comparator} object comparing {@link Iterator iterators} of the
 * respective objects.
 *
 * @author <a href="mailto:todd.shore@unitb-consulting.de">Todd Shore</a>
 * @since 2013-10-15
 *
 */
public final class IterableComparator<E extends Comparable<E>> implements Comparator<Iterable<E>> {

	/**
	 * A simple length constant for building the return value of
	 * {@link #toString()}.
	 */
	private static final int ESTIMATED_MAX_STRING_REPR_LENGTH = 64;

	/**
	 * The {@link Comparator} to use for comparing the {@link Iterable} objects
	 * by their corresponding {@link Iterator iterators}.
	 */
	private final Comparator<Iterator<E>> iteratorComparator;

	/**
	 * @param iteratorComparator
	 *            The {@link Comparator} to use for comparing the
	 *            {@link Iterable} objects by their corresponding
	 *            {@link Iterator iterators}.
	 */
	public IterableComparator(final Comparator<Iterator<E>> iteratorComparator) {
		this.iteratorComparator = iteratorComparator;
	}

	@Override
	public int compare(final Iterable<E> o1, final Iterable<E> o2) {
		assert o1 != null;
		assert o2 != null;

		final Iterator<E> iter1 = o1.iterator();
		final Iterator<E> iter2 = o2.iterator();
		return iteratorComparator.compare(iter1, iter2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IterableComparator)) {
			return false;
		}
		final IterableComparator<?> other = (IterableComparator<?>) obj;
		if (iteratorComparator == null) {
			if (other.iteratorComparator != null) {
				return false;
			}
		} else if (!iteratorComparator.equals(other.iteratorComparator)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (iteratorComparator == null ? 0 : iteratorComparator.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(ESTIMATED_MAX_STRING_REPR_LENGTH);
		builder.append("IterableComparator [iteratorComparator=");
		builder.append(iteratorComparator);
		builder.append("]");
		return builder.toString();
	}

}
