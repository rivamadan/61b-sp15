
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
		head = new DNode(val); 
	}

	public DNode getFront() {
		return head;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		DNode copy = head;
		while (copy.next != null) {
			copy = copy.next;
		}
		return copy;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		head = new DNode(null, d, head);
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode backNode = getBack();
		backNode.next = new DNode(backNode, d, null);
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		return null;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		/* your code here */		
		return null;
	}

	public static class DNode {
		public DNode prev;
		public DNode next;
		public double val;
		
		private DNode(double val) {
			this(null, val, null);
		}
		
		private DNode(DNode prev, double val, DNode next) {
			this.prev = prev;
			this.val = val;
			this.next =next;
		}
	}
	
}
