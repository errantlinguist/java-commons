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
package com.github.errantlinguist.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node of the {@link Tree} class.
 *
 * @param <T>
 *            The type of the {@link #data} the node contains.
 * @author <a href="mailto:errantlinguist+github@gmail.com">Todd Shore</a>
 */
public class Node<T> {

	private static final int ESTIMATED_MAX_CHILD_COUNT = 10;

	/**
	 * A simple length constant for building the return value of
	 * {@link #toString()}.
	 */
	private static final int ESTIMATED_MAX_DATA_STRING_REPR_LENGTH = 32;

	private static final <T> ArrayList<Node<T>> createDefaultChildList() {
		return new ArrayList<>(ESTIMATED_MAX_CHILD_COUNT);
	}

	/**
	 * The child nodes of this node.
	 */
	private List<Node<T>> children;

	/**
	 * The data contained by this node.
	 */
	private T data;

	/**
	 * Default constructor.
	 */
	public Node() {
		this(null);
	}

	/**
	 * Convenience constructor for creating a {@link Node} object with an
	 * instance of {@code T}.
	 *
	 * @param data
	 *            an instance of {@code T}.
	 */
	public Node(final T data) {
		this(null, Node.<T>createDefaultChildList());
	}

	/**
	 * Convenience constructor for creating a {@link Node} object with an
	 * instance of {@code T}.
	 *
	 * @param data
	 *            an instance of {@code T}.
	 * @param children
	 *            A list of {@link Node} objects for which the constructed
	 *            {@code Node} instance is the parent.
	 */
	protected Node(final T data, final List<Node<T>> children) {
		this.data = data;
		this.children = children;
	}

	/**
	 * Recursively counts all ancestor nodes of this node.
	 *
	 * @return The count of all nodes ancestor to this node.
	 */
	public int countAncestorNodes() {
		int result = children.size();

		for (final Node<T> child : children) {
			result += child.countAncestorNodes();
		}

		return result;

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
		if (!(obj instanceof Node)) {
			return false;
		}
		final Node<?> other = (Node<?>) obj;
		if (children == null) {
			if (other.children != null) {
				return false;
			}
		} else if (!children.equals(other.children)) {
			return false;
		}
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a {@link List} of the children of this node.
	 *
	 * @return The children of this node.
	 */
	public List<Node<T>> getChildren() {
		return this.children;
	}

	/**
	 *
	 * @return The data contained by this node.
	 */
	public T getData() {
		return this.data;
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
		result = prime * result + (children == null ? 0 : children.hashCode());
		result = prime * result + (data == null ? 0 : data.hashCode());
		return result;
	}

	/**
	 * Sets the children of the node.
	 *
	 * @param children
	 *            the list to set.
	 */
	public void setChildren(final List<Node<T>> children) {
		this.children = children;
	}

	/**
	 *
	 * @param data
	 *            The data to set.
	 */
	public void setData(final T data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Estimate the length of the string representation of the child nodes:
		// The length of the children's children is not estimated because it is
		// recursively calculated in the Node.toString() call on the children
		// themselves
		final int estimatedMaxChildStringReprLength = children.size() * ESTIMATED_MAX_DATA_STRING_REPR_LENGTH;
		final int estimatedMaxStringReprLength = estimatedMaxChildStringReprLength
				+ ESTIMATED_MAX_DATA_STRING_REPR_LENGTH;
		final StringBuilder builder = new StringBuilder(estimatedMaxStringReprLength);

		builder.append("Node [children=");
		builder.append(children);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");

		return builder.toString();
	}

}
