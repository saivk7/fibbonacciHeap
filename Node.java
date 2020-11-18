
/* 
   Structure of a Node in Fibonnaci Heap
*/


public class Node{

	int frequency;							/* frequency of Hash-tag stored in the node */
	int nodeDegree = 0;						/* Degree associated with the node */

	Node rightSib;							/* Right sibling of the Node in a circular list*/
	Node leftSib;							/* Left sibling of the Node */
	Node child;								/* Child of the Node */
	Node parent;							/* Parent of the Node */


	boolean childCut = false;				/* Child-Cut value of the Node */
	/* Node Constructor */
	
	Node(int data){
		rightSib =  this;
		leftSib = this;
		frequency = data;
	}

	/*  Getter and Setter functions ethods for Node Class*/

	public int getValue() {					/* returns the Frequency of corresponding Node */
		return frequency;
	}

	public void setValue(Integer frequency){
		this.frequency = frequency;			/* Updates frequency of Node in Fibonacci Heap */
	}



	public void setDegree(int newDegree) {	/* updates the Frequency of Node */
		this.nodeDegree = newDegree;
	}

	public int getDegree() { 				/* returns the degree assosciated with Node */
		return nodeDegree;
	}

}