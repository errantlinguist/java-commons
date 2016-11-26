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

import java.util.Map;
import java.util.Map.Entry;

/**
 * A utility class for manipulating {@link Map} objects containing
 * {@link Integer} objects.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-22
 *
 */
public final class IntegerMap {

	/**
	 * Increments all the {@link Integer} values of a given {@link Map} by a
	 * given amount.
	 *
	 * @param map
	 *            The {@code Map} to increment the values of.
	 * @param increment
	 *            The value to add to each element.
	 */
	public static final <K> void incrementValues(final Map<K, Integer> map, final int increment) {
		assert map != null;
		for (final Entry<K, Integer> entry : map.entrySet()) {
			final Integer value = entry.getValue();
			final Integer incrementedValue = value + increment;
			entry.setValue(incrementedValue);
		}
	}

	private IntegerMap() {
		// Avoid instantiation
	}

}
