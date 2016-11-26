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

import java.io.Serializable;
import java.util.Objects;

/**
 * A factory for efficiently building strings, implemented as a wrapper class
 * for a {@link StringBuilder} which clears it whenever {@link #create()} is
 * called. Therefore, just like {@code StringBuilder}, it is not thread-safe.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2012-04-13
 */
public final class StringBuilderFactory implements Appendable, CharSequence, Serializable {

	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = -8181181050603622276L;

	/**
	 * The {@link StringBuilder} object used for building strings.
	 */
	private final StringBuilder builder;

	/**
	 * @param builder
	 *            The {@link StringBuilder} object to be used for building
	 *            strings.
	 */
	public StringBuilderFactory(final StringBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Appends the specified {@code boolean} to this
	 * {@link StringBuilderFactory}.
	 *
	 * @param b
	 *            The {@code boolean} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final boolean b) {
		builder.append(b);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Appendable#append(char)
	 */
	@Override
	public StringBuilderFactory append(final char c) {
		builder.append(c);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
	@Override
	public StringBuilderFactory append(final CharSequence csq) {
		builder.append(csq);
		return this;
	}

	/**
	 * Appends an arbitrary number of {@link CharSequence character sequences}
	 * to this {@code StringBuilderFactory}.
	 *
	 * @param charSequences
	 *            The arbitrary number of character sequences to append.
	 * @return A reference to the given {@link StringBuilderFactory} object.
	 */
	public StringBuilderFactory append(final CharSequence... charSequences) {
		for (final CharSequence csq : charSequences) {
			append(csq);
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
	@Override
	public StringBuilderFactory append(final CharSequence csq, final int start, final int end) {
		builder.append(csq, start, end);
		return this;
	}

	/**
	 * Appends the specified {@code double} to this {@link StringBuilderFactory}
	 * .
	 *
	 * @param d
	 *            The {@code double} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final double d) {
		builder.append(d);
		return this;
	}

	/**
	 * Appends the specified {@code float} to this {@link StringBuilderFactory}.
	 *
	 * @param f
	 *            The {@code float} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final float f) {
		builder.append(f);
		return this;
	}

	/**
	 * Appends the specified {@code int} to this {@link StringBuilderFactory}.
	 *
	 * @param i
	 *            The {@code int} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final int i) {
		builder.append(i);
		return this;
	}

	/**
	 * Appends the specified {@code long} to this {@link StringBuilderFactory}.
	 *
	 * @param l
	 *            The {@code long} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final long l) {
		builder.append(l);
		return this;
	}

	/**
	 * Appends the specified {@link Object} to this {@link StringBuilderFactory}
	 * .
	 *
	 * @param obj
	 *            The {@code Object} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final Object obj) {
		builder.append(obj);
		return this;
	}

	/**
	 * Appends the specified {@link String} to this {@link StringBuilderFactory}
	 * .
	 *
	 * @param str
	 *            The {@code String} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final String str) {
		builder.append(str);
		return this;
	}

	/**
	 * Appends the specified {@link StringBuffer} to this
	 * {@link StringBuilderFactory}.
	 *
	 * @param sb
	 *            The {@code StringBuffer} to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory append(final StringBuffer sb) {
		builder.append(sb);
		return this;
	}

	/**
	 * Appends the specified code point to this {@link StringBuilderFactory}.
	 *
	 * @param codePoint
	 *            The code point to append
	 * @return A reference to this {@code StringBuilderFactory}.
	 */
	public StringBuilderFactory appendCodePoint(final int codePoint) {
		builder.appendCodePoint(codePoint);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.CharSequence#charAt(int)
	 */
	@Override
	public char charAt(final int index) {
		return builder.charAt(index);
	}

	/**
	 * Creates a new string and {@link #reset() resets} the
	 * {@code StringBuilderFactory}.
	 *
	 * @return A new string with all the previously-appended characters.
	 */
	public String create() {
		final String string = builder.toString();
		reset();
		return string;
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
		if (!(obj instanceof StringBuilderFactory)) {
			return false;
		}
		final StringBuilderFactory other = (StringBuilderFactory) obj;
		if (builder == null) {
			if (other.builder != null) {
				return false;
			}
		} else if (!builder.equals(other.builder)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the builder
	 */
	public StringBuilder getBuilder() {
		return builder;
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
		result = prime * result + (builder == null ? 0 : builder.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.CharSequence#length()
	 */
	@Override
	public int length() {
		return builder.length();
	}

	/**
	 * Removes any previously-appended characters.
	 */
	public void reset() {
		builder.setLength(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(final int start, final int end) {
		return builder.subSequence(start, end);
	}

	@Override
	public String toString() {
		return Objects.toString(builder);
	}

}
