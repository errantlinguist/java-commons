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

import java.util.Comparator;
import java.util.Map.Entry;

/**
 * A {@link Comparator} which compares two {@link Entry} objects based on their
 * respective {@link Comparable} keys.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-15
 *
 */
public final class EntryComparableKeyComparator<K extends Comparable<K>> implements Comparator<Entry<K, ?>> {

	@Override
	public int compare(final Entry<K, ?> o1, final Entry<K, ?> o2) {
		assert o1 != null;
		assert o2 != null;
		final K key1 = o1.getKey();
		final K key2 = o2.getKey();
		return key1.compareTo(key2);
	}

}
