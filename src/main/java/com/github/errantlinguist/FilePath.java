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

import java.io.IOException;

/**
 * A utility class for manipulating file/directory paths.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-11-03
 *
 */
public final class FilePath {

	/**
	 * Gets the extension of a filename, e.g.&nbsp;<em>txt</em> of
	 * <em>/home/user/filename.txt</em>.
	 *
	 * @param filename
	 *            The filename to get the extension of.
	 * @return The extension.
	 * @throws IOException
	 *             If no extension could be parsed.
	 */
	public static final String getFilenameExtension(final String filename) throws IOException {
		final String result;

		final int offset = filename.lastIndexOf(".");
		if (offset == -1) {
			throw new IOException("No filename extension found.");
		} else {
			result = filename.substring(offset + 1);
		}

		return result;
	}

	private FilePath() {
		// Avoid instantiation
	}

}
