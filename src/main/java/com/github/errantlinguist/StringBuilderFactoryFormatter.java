/*
 * 	Copyright 2012 Todd Shore
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

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;

/**
 * A wrapper for a {@link Formatter} object using a {@link StringBuilderFactory}
 * instance for generating strings as formatted output. <strong>NOTE:</strong>
 * This class is not thread-safe!
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2012-04-13
 */
public class StringBuilderFactoryFormatter implements Closeable, Flushable {

	/**
	 * The {@link Formatter} instance used for formatting.
	 */
	private final Formatter formatter;

	/**
	 * The {@link StringBuilderFactory} instance used for building strings.
	 */
	private final StringBuilderFactory stringBuilderFactory;

	/**
	 *
	 * @param stringBuilderFactory
	 *            The {@link StringBuilderFactory} instance to use for building
	 *            strings.
	 */
	public StringBuilderFactoryFormatter(final StringBuilderFactory stringBuilderFactory) {
		this(stringBuilderFactory, new Formatter(stringBuilderFactory));
	}

	/**
	 *
	 * @param stringBuilderFactory
	 *            The {@link StringBuilderFactory} instance to use for building
	 *            strings.
	 *
	 * @param formatter
	 *            The {@link Formatter} instance to use for formatting.
	 */
	public StringBuilderFactoryFormatter(final StringBuilderFactory stringBuilderFactory, final Formatter formatter) {
		this.stringBuilderFactory = stringBuilderFactory;
		this.formatter = formatter;
	}

	/**
	 *
	 * @param stringBuilderFactory
	 *            The {@link StringBuilderFactory} instance to use for building
	 *            strings.
	 * @param locale
	 *            The {@link Locale} instance to use for formatting.
	 */
	public StringBuilderFactoryFormatter(final StringBuilderFactory stringBuilderFactory, final Locale locale) {
		this(stringBuilderFactory, new Formatter(stringBuilderFactory, locale));
	}

	@Override
	public void close() throws IOException {
		formatter.close();
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
		if (!(obj instanceof StringBuilderFactoryFormatter)) {
			return false;
		}
		final StringBuilderFactoryFormatter other = (StringBuilderFactoryFormatter) obj;
		if (formatter == null) {
			if (other.formatter != null) {
				return false;
			}
		} else if (!formatter.equals(other.formatter)) {
			return false;
		}
		if (stringBuilderFactory == null) {
			if (other.stringBuilderFactory != null) {
				return false;
			}
		} else if (!stringBuilderFactory.equals(other.stringBuilderFactory)) {
			return false;
		}
		return true;
	}

	@Override
	public void flush() throws IOException {
		formatter.flush();
	}

	/**
	 * Formats a string using the specified locale, format string, and
	 * arguments; This method clears the {@link StringBuilderFactoryFormatter}
	 * afterwards, meaning that calling this method has no side-effects other
	 * than the potential increase of capacity of the
	 * {@link StringBuilderFactory} in the case that the formatted string is too
	 * large for the array currently backing it.
	 *
	 * @param l
	 *            The {@link Locale locale} to apply during formatting. If l is
	 *            null then no localization is applied. This does not change
	 *            this object's locale that was set during construction.
	 * @param format
	 *            A format string as described in
	 *            {@link Formatter#format(Locale, String, Object...) Format
	 *            string syntax}.
	 * @param args
	 *            Arguments referenced by the format specifiers in the format
	 *            string. If there are more arguments than format specifiers,
	 *            the extra arguments are ignored. The maximum number of
	 *            arguments is limited by the maximum dimension of a Java array
	 *            as defined by the Java Virtual Machine Specification.
	 * @return The formatted string.
	 */
	public String format(final Locale l, final String format, final Object... args) {
		formatter.format(l, format, args);
		return stringBuilderFactory.get();
	}

	/**
	 * Formats a string using the specified format string and arguments; This
	 * method clears the {@link StringBuilderFactoryFormatter} afterwards,
	 * meaning that calling this method has no side-effects other than the
	 * potential increase of capacity of the {@link StringBuilderFactory} in the
	 * case that the formatted string is too large for the array currently
	 * backing it.
	 *
	 * @param format
	 *            A format string as described in
	 *            {@link Formatter#format(Locale, String, Object...) Format
	 *            string syntax}.
	 * @param args
	 *            Arguments referenced by the format specifiers in the format
	 *            string. If there are more arguments than format specifiers,
	 *            the extra arguments are ignored. The maximum number of
	 *            arguments is limited by the maximum dimension of a Java array
	 *            as defined by the Java Virtual Machine Specification.
	 * @return The formatted string.
	 */
	public String format(final String format, final Object... args) {
		formatter.format(format, args);
		return stringBuilderFactory.get();

	}

	/**
	 * @return the stringBuilderFactory
	 */
	public StringBuilderFactory getStringBuilderFactory() {
		return stringBuilderFactory;
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
		result = prime * result + (formatter == null ? 0 : formatter.hashCode());
		result = prime * result + (stringBuilderFactory == null ? 0 : stringBuilderFactory.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	public CharSequence subSequence(final int start, final int end) {
		return stringBuilderFactory.subSequence(start, end);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final String stringBilderFactoryFieldPrefix = "StringBuilderFactoryFormatter [stringBuilderFactory=";
		final String stringBuilderFactoryStr = Objects.toString(stringBuilderFactory);
		final String formatterFieldPrefix = ", formatter=";
		final String formatterStr = Objects.toString(formatter);
		final StringBuilder builder = new StringBuilder(stringBilderFactoryFieldPrefix.length()
				+ stringBuilderFactoryStr.length() + formatterFieldPrefix.length() + formatterStr.length() + 1);
		builder.append(stringBilderFactoryFieldPrefix);
		builder.append(stringBuilderFactoryStr);
		builder.append(formatterFieldPrefix);
		builder.append(formatterStr);
		builder.append(']');
		return builder.toString();
	}

}
