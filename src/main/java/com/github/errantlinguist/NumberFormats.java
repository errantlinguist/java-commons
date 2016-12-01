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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A utility class for manipulating {@link NumberFormat} instances.
 *
 * @author <a href="mailto:errantlinguist+github@gmail.com">Todd Shore</a>
 * @since 2013-10-30
 *
 */
public final class NumberFormats {

	/**
	 * A utility method for getting a {@link NumberFormat} instance which
	 * {@link NumberFormat#parse(String) parses} {@link BigDecimal} objects.
	 *
	 * @param locale
	 *            The {@link Locale} for which to get a {@link NumberFormat}
	 *            instance.
	 * @return A {@code NumberFormat} instance which which parses
	 *         {@link BigDecimal} objects.
	 */
	public static final NumberFormat getBigDecimalNumberFormatInstance(final Locale locale) {
		assert locale != null;
		final NumberFormat result = NumberFormat.getNumberInstance(locale);
		if (result instanceof DecimalFormat) {
			final DecimalFormat df = (DecimalFormat) result;
			df.setParseBigDecimal(true);
			df.setGroupingUsed(true);
		}

		return result;
	}

	private NumberFormats() {
		// Avoid instantiation
	}

}
