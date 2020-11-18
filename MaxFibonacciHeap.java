import java.util.*;

/* 
	Max Fibonacci Heap Class and corresponding methods 
*/
public final class MaxFibonacciHeap{

	Node maxNode = null;
	int heapSize = 0;



	// Check if the heap is empty - O(1)
	public boolean isEmpty() {
		return maxNode == null;
	}

	

	/* 	method:Merge
		Merges the two given subtrees.


	*/
	public static Node merge(Node node1, Node node2) {

		if (node1 == null && node2 == null) {
			return null;
		} else if (node1 != null && node2 == null) {
			return node1;
		} else if (node1 == null && node2 != null) {
			return node2;
		} else {
			Node oneNext = node1.rightSib;
			node1.rightSib = node2.rightSib;
			node1.rightSib.leftSib = node1;
			node2.rightSib = oneNext;
			node2.rightSib.leftSib = node2;

			if(node1.frequency>=node2.frequency){
				return node1;
			}else{
				return node2;
			}

			// return node1.frequency > node2.frequency ? node1 : node2;
		}
	}
		/* Add data into the  Max fibonacci Heap - O(1) Complexity */
	public Node createNode(int value) {
		Node node = new Node(value);

		// Merge the nodes into a list and get the max of the two nodes
		maxNode = merge(maxNode, node);

		// Increment size of the heap
		heapSize++;

		// Return address of the newly created node.
		return node;
	}

	// Return size of the Max Fibonacci Heap
	public int Size() {
		return heapSize;
	}

	// Update existing value : increment frequency - O(1)
	public void increaseKey(Node entry, Integer newData) {
		if (newData < 0){
			throw new IllegalArgumentException("frequency of Hash-tag cannot be negative");}

		// Set new frequency(Value)
		entry.frequency = newData;

		// Check if the value at parent node is gerater than the updated value
		// if true, cascadingCut the parent from the tree and add to root list
		if(entry.parent != null && entry.frequency >= entry.parent.frequency)
			{cascadingCut(entry);}


		if(entry.frequency >= maxNode.frequency){
			maxNode = entry;
		}
	}

	// Remove the node's parent from the tree and add to rootlist
	private void cascadingCut(Node entry) {

		// set childcut to false
		entry.childCut = false;

		if (entry.parent == null)
			return;

		// while the node is not the only child
		if (entry.rightSib != entry) {
			entry.rightSib.leftSib = entry.leftSib;
			entry.leftSib.rightSib = entry.rightSib;
		}

		if (entry.parent.child == entry) {
			if (entry.rightSib != entry) {
				entry.parent.child = entry.rightSib;
			} else {
 				entry.parent.child = null;
			}
		}

		entry.parent.setDegree(entry.parent.getDegree() - 1);

		entry.leftSib = entry.rightSib = entry;
		maxNode = merge(maxNode, entry);

		if (entry.parent.childCut)
			cascadingCut(entry.parent);
		else
			entry.parent.childCut = true;

		entry.parent = null;

	}
	
	// Return the root node with max value - O(1)
	Node max() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is Empty");
		return maxNode;
	}

	// Remove max element node(root) and append children to the root list and
	// pairwise combine - O(log n)
	public Node removeMax() {

		if (isEmpty()){
			throw new NoSuchElementException("Heap is empty.");}

		heapSize--;

		Node maxElement = maxNode;

		// max is not the only node in the tree
		if (maxNode.rightSib != maxNode) {
			maxNode.leftSib.rightSib = maxNode.rightSib;
			maxNode.rightSib.leftSib = maxNode.leftSib;
			maxNode = maxNode.rightSib;
		}
		// empty the tree if max is the only node
		else
			maxNode = null;

		// if max node has children
		if (maxElement.child != null) {
			// save max node's child for iteration
			Node maxChild = maxElement.child;
			do {
				// set the parent field of all children of max to null
				maxChild.parent = null;
				// Move to next sibling
				maxChild = maxChild.rightSib;

			} while (maxChild != maxElement.child);
		}

		// merge all children of max node(to be deleted) to root list
		maxNode = merge(maxElement.child, maxNode);

		if (maxNode == null)
			return maxElement;

		// lists to make a pairwise combine of nodes in root list
		List<Node> pairWiseCombine = new ArrayList<Node>();
		List<Node> rootList = new ArrayList<Node>();

		// parse all nodes in the root list and add to the list 'rootList'
		for (Node curr = maxNode; rootList.isEmpty() || rootList.get(0) != curr; curr = curr.rightSib)
			rootList.add(curr);

		// for each node in the rootList
		for (Node curr : rootList) {

			while (true) {
				while (curr.getDegree() >= pairWiseCombine.size())
					pairWiseCombine.add(null);

				// add the node with particular degree into another list for
				// pairwise combine
				if (pairWiseCombine.get(curr.getDegree()) == null) {
					pairWiseCombine.set(curr.getDegree(), curr);
					break;
				}

				// if a tree of same degree already exists in the list get the
				// node to merge
				Node other = pairWiseCombine.get(curr.getDegree());

				// Clear the node from the list for next tree with same degree
				pairWiseCombine.set(curr.getDegree(), null);

				// get the minimum and max node of the two trees to be merged
				Node minimum;
				Node maximum;
				if(other.getValue() < curr.getValue()){
					minimum = other;
					maximum = curr;
				}else{
					maximum = other;
					minimum = curr;

				}


				/*
				 * Break maximum out of the root list, then merge it into minimum's
				 * child list.
				 */
				minimum.rightSib.leftSib = minimum.leftSib;
				minimum.leftSib.rightSib = minimum.rightSib;

				// Isolate the minimum node
				minimum.rightSib = minimum.leftSib = minimum;

				// merge the maximum node with the root list
				maximum.child = merge(maximum.child, minimum);

				// Make minimum node a child of maximum node
				minimum.parent = maximum;

				// Set minimum node's childcut to false
				minimum.childCut = false;

				// Increment the degree of the maximum node
				maximum.nodeDegree++;

				// make the maximum node the 'curr' node for next pass
				curr = maximum;
			}

			// Compare the curr node with maximum and update maximum if necessary
			if (curr.getValue() >= maxNode.getValue())
				maxNode = curr;
		}
		// return the element to be deleted - previous maximum node
		return maxElement;
	}

}

