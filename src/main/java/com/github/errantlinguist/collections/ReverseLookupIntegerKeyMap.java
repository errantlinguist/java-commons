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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A {@link Map} implementation which decorates another {@link Map} instance,
 * maintaining a {@link List} of the map's values based on the respective
 * {@link Integer} keys mapping to each value.
 *
 * @author <a href="mailto:errantlinguist+github@gmail.com">Todd Shore</a>
 *
 */
public final class ReverseLookupIntegerKeyMap<V> implements Map<Integer, V>, Serializable {

	/**
	 * A constant for calculating the size of the {@link StringBuilder} created
	 * by {@link #toString()}.
	 */
	private static final int ESTIMATED_MAX_ENTRY_STRING_REPR_LENGTH = 32;

	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = 140929989425054569L;

	/**
	 * The decorated {@link Map} instance.
	 */
	private final Map<Integer, V> decorated;

	/**
	 * The backing {@link List} containing the indexed values of
	 * {@link #decorated}.
	 */
	private transient final List<V> indexedValues;

	/**
	 *
	 * @param decorated
	 *            The {@link Map} instance to decorate.
	 */
	public ReverseLookupIntegerKeyMap(final Map<Integer, V> decorated) {
		this.decorated = decorated;
		this.indexedValues = ListIndex.createListFromIndexMap(decorated);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		decorated.clear();
		indexedValues.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(final Object key) {
		return decorated.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(final Object value) {
		return decorated.containsValue(value);
	}

	@Override
	public Set<Entry<Integer, V>> entrySet() {
		return decorated.entrySet();
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
		if (!(obj instanceof ReverseLookupIntegerKeyMap)) {
			return false;
		}
		final ReverseLookupIntegerKeyMap<?> other = (ReverseLookupIntegerKeyMap<?>) obj;
		if (decorated == null) {
			if (other.decorated != null) {
				return false;
			}
		} else if (!decorated.equals(other.decorated)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(final Object key) {
		return decorated.get(key);
	}

	/**
	 * @return An unmodifiable view of the decorated {@link Map} instance.
	 */
	public Map<Integer, V> getDecorated() {
		return Collections.unmodifiableMap(decorated);
	}

	/**
	 * @return An unmodifiable view of the backing {@link List} containing the
	 *         indexed values of {@link #getDecorated() the decorated
	 *         <code>Map</code>}.
	 */
	public List<V> getIndexedValues() {
		return Collections.unmodifiableList(indexedValues);
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
		result = prime * result + (decorated == null ? 0 : decorated.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return decorated.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<Integer> keySet() {
		return decorated.keySet();
	}

	@Override
	public V put(final Integer key, final V value) {
		assert key != null;
		final V putValue = decorated.put(key, value);

		final int index = key.intValue();
		ListIndex.ensureIndex(indexedValues, index);
		final V result = indexedValues.set(index, value);
		assert Objects.equals(putValue, result);

		return result;
	}

	@Override
	public void putAll(final Map<? extends Integer, ? extends V> m) {
		assert m != null;

		decorated.putAll(m);

		// Find the maximum index in order to pre-set the list length
		final int maxIndex = Collections.max(m.keySet());
		ListIndex.ensureIndex(indexedValues, maxIndex);

		ListIndex.setIndexedElements(indexedValues, m);

		throw new RuntimeException(new UnsupportedOperationException("Not yet implemented"));
		// TODO Auto-generated method stub

	}

	public V remove(final Integer key) {
		assert key != null;

		final V removedValue = decorated.remove(key);
		final V result = indexedValues.remove(key.intValue());
		assert Objects.equals(removedValue, result);
		return result;
	}

	@Override
	public V remove(final Object key) {
		final V result;
		if (key instanceof Integer) {
			result = remove((Integer) key);
		} else {
			result = null;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return decorated.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final String getDecoratedPropertyPrefix = "ReverseLookupIntegerValueMap [getDecorated()=";
		final String reprEnd = "]";
		final int estimatedMaxStringReprLength = getDecoratedPropertyPrefix.length() + reprEnd.length()
				+ ESTIMATED_MAX_ENTRY_STRING_REPR_LENGTH * decorated.size();
		final StringBuilder builder = new StringBuilder(estimatedMaxStringReprLength);
		builder.append(getDecoratedPropertyPrefix);
		builder.append(getDecorated());
		builder.append(reprEnd);
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		return decorated.values();
	}

}
