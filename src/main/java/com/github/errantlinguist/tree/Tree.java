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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a tree of objects of generic type {@code T} by decorating a root
 * {@link Node} instance, which points to a list of children.
 *
 * @param <D>
 *            The type of the {@link Node#getData() data} the nodes contain.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 *
 */
public class Tree<D> {

	/**
	 * Walks the {@link Tree} in pre-order style. This is a recursive method: It
	 * appends to the second argument, which is passed by reference as it
	 * recurses down the tree.
	 *
	 * @param node
	 *            The starting node.
	 * @param visitedNodes
	 *            The output of the walk.
	 * @param <T>
	 *            The type of the data contained by the nodes.
	 */
	private static final <T> void walk(final Node<T> node, final Collection<Node<T>> visitedNodes) {
		visitedNodes.add(node);
		for (final Node<T> data : node.getChildren()) {
			walk(data, visitedNodes);
		}
	}

	/**
	 * The root node.
	 */
	private final Node<D> rootNode;

	/**
	 * @param rootNode
	 *            The root node.
	 */
	protected Tree(final Node<D> rootNode) {
		this.rootNode = rootNode;
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
		if (!(obj instanceof Tree)) {
			return false;
		}
		final Tree<?> other = (Tree<?>) obj;
		if (rootNode == null) {
			if (other.rootNode != null) {
				return false;
			}
		} else if (!rootNode.equals(other.rootNode)) {
			return false;
		}
		return true;
	}

	/**
	 * Return the root {@link Node} of the tree.
	 *
	 * @return the root node.
	 */
	public Node<D> getRootNode() {
		return this.rootNode;
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
		result = prime * result + (rootNode == null ? 0 : rootNode.hashCode());
		return result;
	}

	/**
	 * Returns the {@link Tree} as a {@link List} of {@link Node} objects. The
	 * nodes of the {@code List} are generated from a pre-order traversal of the
	 * tree.
	 *
	 * @return a new {@code List} of the all the nodes.
	 */
	public List<Node<D>> toList() {
		final List<Node<D>> list = new LinkedList<>();
		walk(list);
		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int initialCapacity = 19; // The exact sum of the lengths of the
										// string literals below
		final StringBuilder builder = new StringBuilder(initialCapacity);

		builder.append("Tree [rootNode=");
		builder.append(rootNode);
		builder.append("]");

		return builder.toString();
	}

	/**
	 * Walks the tree, starting from {@link #rootNode the root node}.
	 *
	 * @param visitedNodes
	 *            The output of the walk.
	 */
	private final void walk(final Collection<Node<D>> visitedNodes) {
		walk(rootNode, visitedNodes);
	}

}
