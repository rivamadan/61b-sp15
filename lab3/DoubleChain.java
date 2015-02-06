
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
		while (copy != null && copy.next != null) {
			copy = copy.next;
		}
		return copy;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		DNode newFront = new DNode(d);
		head.prev = newFront;
		newFront.next = head;
		head = newFront;
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode backNode = getBack();
		backNode.next = new DNode(backNode, d, null);
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		if (head.next == null) {
			return null;
		}
		DNode backNode = getBack();
		backNode = backNode.prev;
		backNode.next = null;
		return getBack();
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		DNode copy = head;
		StringBuilder dString = new StringBuilder("<[");
		while (copy.next != null) {
			dString.append(copy.val);
			dString.append(", ");
			copy = copy.next;
		}
		dString.append(copy.val);
		dString.append("]>");
		return dString.toString();
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
