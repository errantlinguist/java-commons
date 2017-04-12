package edu.berkeley.cs;

/*
 * Copyright (c) 1987, 1993
 *	The Regents of the University of California.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *	This product includes software developed by the University of
 *	California, Berkeley and its contributors.
 * 4. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 *	@(#)sysexits.h	8.1 (Berkeley) 6/2/93
 */

/**
 * SYSEXITS.H &mdash; Exit status codes for system programs.
 *
 * <p>
 * Converted from a C header file to a Java {@code public final} class.
 * </p>
 *
 * <p>
 * This include file attempts to categorize possible error exit statuses for
 * system programs, notably <code>delivermail</code> and the Berkeley network.
 * </p>
 *
 * <p>
 * Error numbers begin at {@link #EX__BASE} to reduce the possibility of
 * clashing with other exit statuses that random programs may already return.
 * The meaning of the codes is approximately as follows:
 * </p>
 *
 * <dl>
 * <dt>{@link #EX_USAGE}</dt>
 * <dd>The command was used incorrectly, e.g.&nbsp;with the wrong number of
 * arguments, a bad flag, a bad syntax in a parameter, or whatever.</dd>
 * <dt>{@link #EX_DATAERR}</dt>
 * <dd>The input data was incorrect in some way. This should only be used for
 * user's data and not system files.</dd>
 * <dt>{@link #EX_NOINPUT}</dt>
 * <dd>An input file (not a system file) did not exist or was not readable. This
 * could also include errors like "No message" to a mailer (if it cared to catch
 * it).</dd>
 * <dt>{@link #EX_NOUSER}</dt>
 * <dd>The user specified did not exist. This might be used for mail addresses
 * or remote logins.</dd>
 * <dt>{@link #EX_NOHOST}</dt>
 * <dd>The host specified did not exist. This is used in mail addresses or
 * network requests.</dd>
 * <dt>{@link #EX_UNAVAILABLE}</dt>
 * <dd>A service is unavailable. This can occur if a support program or file
 * does not exist. This can also be used as a catch-all message when something
 * you wanted to do doesn't work, but you don't know why.</dd>
 * <dt>{@link #EX_SOFTWARE}</dt>
 * <dd>An internal software error has been detected. This should be limited to
 * non-operating system related errors as much as possible.</dd>
 * <dt>{@link #EX_OSERR}</dt>
 * <dd>An operating system error has been detected. This is intended to be used
 * for such things as "cannot fork", "cannot create pipe", or the like. It
 * includes things like <code>getuid</code> returning a user that does not exist
 * in the <code>passwd</code> file.</dd>
 * <dt>{@link #EX_OSFILE}</dt>
 * <dd>Some system file (e.g.&nbsp;{@code /etc/passwd}, {@code /etc/utmp}, etc.)
 * does not exist, cannot be opened, or has some sort of error (e.g.&nbsp;syntax
 * error).</dd>
 * <dt>{@link #EX_CANTCREAT}</dt>
 * <dd>A (user specified) output file cannot be created.</dd>
 * <dt>{@link #EX_IOERR}</dt>
 * <dd>An error occurred while doing IO on some file.</dd>
 * <dt>{@link #EX_TEMPFAIL}</dt>
 * <dd>Temporary failure, indicating something that is not really an error. In
 * <code>sendmail</code>, this means that a mailer (e.g.) could not create a
 * connection, and the request should be re-attempted later.</dd>
 * <dt>{@link #EX_PROTOCOL}</dt>
 * <dd>The remote system returned something that was "not possible" during a
 * protocol exchange.</dd>
 * <dt>{@link #EX_NOPERM}</dt>
 * <dd>You did not have sufficient permission to perform the operation. This is
 * not intended for file system problems, which should use {@link #EX_NOINPUT}
 * or {@link #EX_CANTCREAT}, but rather for higher level permissions.</dd>
 * </dl>
 */
public final class SysExits {
	/** base value for error messages */
	public static final int EX__BASE = 64;

	/** maximum listed value */
	public static final int EX__MAX = 78;

	/** A (user specified) output file cannot be created. */
	public static final int EX_CANTCREAT = 73;
	/** Configuration error */
	public static final int EX_CONFIG = 78;
	/**
	 * The input data was incorrect in some way. This should only be used for
	 * user's data and not system files.
	 */
	public static final int EX_DATAERR = 65;
	/** An error occurred while doing IO on some file. */
	public static final int EX_IOERR = 74;
	/**
	 * The host specified did not exist. This is used in mail addresses or
	 * network requests.
	 */
	public static final int EX_NOHOST = 68;
	/**
	 * An input file (not a system file) did not exist or was not readable. This
	 * could also include errors like "No message" to a mailer (if it cared to
	 * catch it).
	 */
	public static final int EX_NOINPUT = 66;
	/**
	 * You did not have sufficient permission to perform the operation. This is
	 * not intended for file system problems, which should use
	 * {@link #EX_NOINPUT} or {@link #EX_CANTCREAT}, but rather for higher level
	 * permissions.
	 */
	public static final int EX_NOPERM = 77;
	/**
	 * The user specified did not exist. This might be used for mail addresses
	 * or remote logins.
	 */
	public static final int EX_NOUSER = 67;
	/** successful termination */
	public static final int EX_OK = 0;
	/**
	 * An operating system error has been detected. This is intended to be used
	 * for such things as "cannot fork", "cannot create pipe", or the like. It
	 * includes things like <code>getuid</code> returning a user that does not
	 * exist in the <code>passwd</code> file.
	 */
	public static final int EX_OSERR = 71;
	/**
	 * Some system file (e.g.&nbsp;{@code /etc/passwd}, {@code /etc/utmp}, etc.)
	 * does not exist, cannot be opened, or has some sort of error
	 * (e.g.&nbsp;syntax error).
	 */
	public static final int EX_OSFILE = 72;
	/**
	 * The remote system returned something that was "not possible" during a
	 * protocol exchange.
	 */
	public static final int EX_PROTOCOL = 76;
	/**
	 * An internal software error has been detected. This should be limited to
	 * non-operating system related errors as much as possible.
	 */
	public static final int EX_SOFTWARE = 70;
	/**
	 * Temporary failure, indicating something that is not really an error. In
	 * <code>sendmail</code>, this means that a mailer (e.g.) could not create a
	 * connection, and the request should be re-attempted later.
	 */
	public static final int EX_TEMPFAIL = 75;
	/**
	 * A service is unavailable. This can occur if a support program or file
	 * does not exist. This can also be used as a catch-all message when
	 * something you wanted to do doesn't work, but you don't know why.
	 */
	public static final int EX_UNAVAILABLE = 69;

	/**
	 * The command was used incorrectly, e.g.&nbsp;with the wrong number of
	 * arguments, a bad flag, a bad syntax in a parameter, or whatever.
	 */
	public static final int EX_USAGE = 64;

	private SysExits() {
		// avoid instantiation
	}
}
