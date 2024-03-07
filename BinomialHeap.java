	/**
	 * BinomialHeap
	 *
	 * An implementation of binomial heap over non-negative integers.
	 * Based on exercise from previous semester.
	 */
public class BinomialHeap
	{
		public int size;
		public HeapNode last;
		public HeapNode min;
		public BinomialHeap() {   ///constructor. Neri 25/2- 17:30
			this.size = 0;
			this.last = null;
			this.min = null;
		}
		
public static int counter;

		/**
		 * 
		 * pre: key > 0
		 *
		 * Insert (key,info) into the heap and return the newly generated HeapItem.
		 *
		 */
		


		public HeapItem insert(int key, String info) ///Ehud 26.02 
		{
			
			 // Create a new node with the provided key and info
		    HeapNode node = new HeapNode();
		    node.item = new HeapItem();
		    node.item.key = key;
		    node.item.info = info;
		    node.next = node;
		    node.rank = 0;
		    node.item.node = node;

		    // Create a new heap containing only the new node
		    BinomialHeap newHeap = new BinomialHeap();
		    newHeap.min = node;
		    newHeap.last = node;
		    newHeap.size = 1;
		    
		    // Meld the new heap with the current heap
		    this.meld(newHeap);

		    // Return the inserted item
		    return node.item;
		}
		

		/**
		 * 
		 * Delete the minimal item
		 *
		 */
		public void deleteMin() // yoav 04/03
			{this.size -= (int) Math.pow(2,this.min.rank);
			if (this.min == null) { // Heap is empty, nothing to delete
		        return;
		    }
			HeapNode minimum_ob = this.min;
			HeapNode closer = this.min.next;
			if(minimum_ob == closer) {
				this.min = null;
				this.last = null;
				minimum_ob.next = null;
			}
			else {
				HeapNode prev_node = closer;
				while(closer.next != minimum_ob) {
					if(prev_node.item.key > closer.item.key) {prev_node = closer;}
					closer = closer.next;
				}
				closer.next = this.min.next;
				if(this.last == minimum_ob) {this.last = closer;}
				minimum_ob.next = null;
				this.min = prev_node;
			}
			if(minimum_ob.child != null) {
				BinomialHeap heap2 = new BinomialHeap();
				heap2.size = (int) Math.pow(2,minimum_ob.rank);
				HeapNode lst_child = minimum_ob.child;
				heap2.last = lst_child;
				lst_child.parent = null;
				HeapNode node = lst_child;
				node.parent = null;
				node = node.next;
				heap2.size = (int) Math.pow(2,minimum_ob.rank) - 1;
				while(node != lst_child) {
					node.parent = null;
					node = node.next;
				}
				heap2.min = heap2.findMinNew(node);
				this.meld(heap2);
			}
			return;
		}

		/**
		 * 
		 * Return the minimal HeapItem
		 *
		 */
		public HeapItem findMin() // Ehud 26.2
		{
			return this.min.item; // should be replaced by student code
		} 

		/**
		 * 
		 * pre: 0 < diff < item.key
		 * 
		 * Decrease the key of item by diff and fix the heap. 
		 * 
		 */
		public void decreaseKey(HeapItem item, int diff) 
			{item.key -= diff; // decrease item's key
			HeapNode node = item.node; 
			this.shiftUp(node); // shift item in place up until it's in right place
			return; // should be replaced by student code
			}

		/**
		 * 
		 * Delete the item from the heap.
		 *
		 */
		public void delete(HeapItem item) 
			{this.decreaseKey(item, item.key + 1); // decrease items key to make it the minimum given all keys are bigger than 0
			this.deleteMin();}

		/**
		 * 
		 * Meld the heap with heap2
		 *
		 */
		public void meld(BinomialHeap heap2){            ///Yoav 02/03
			this.size += heap2.size;
			if(this.last == null) {
				this.last = heap2.last;
				this.min = heap2.min;
				this.size = heap2.size;
				return;}
			else if (heap2.last == null){return;}
			HeapNode node1 = this.last.next;
			if(node1 == null) {node1 = this.last;}
			HeapNode node2 = heap2.last.next;
			if(node2 == null) {node2 = heap2.last;}
			this.last.next = null;
			heap2.last.next = null;
			HeapNode carry = null;
			HeapNode tmp = null;
			HeapNode prev1 = null;
			boolean last = true; //tells us if fst is final and updated correctly  
			HeapNode fst = null;
			//deciding which node from what tree is the first
			if(node1.rank > node2.rank) {
				tmp = node2.next;
				node2.next = node1;
				fst = node2;
				prev1 = node2;
				node2 = tmp;
				last = false;}
			else if(node1.rank < node2.rank) {
				tmp = node1.next;
				node1.next = node2;
				fst = node1;
				node1 = tmp;
				last = false;}
			else {
				tmp = node1.next;
				HeapNode tmp2 = node2.next;
				carry = node1.link(node2);
				node1 = tmp;
				node2 = tmp2;
				fst = carry;
			}
			while(node1 != null && node2 != null) {
				//case a rank of node1 is smaller 
				if (node1.rank < node2.rank) {
					if(carry != null && carry.rank == node1.rank){ //carry exists node1 is equal to it add node1 to carry and continue  
						tmp = node1.next;
						carry = carry.link(node1); 
						node1 = tmp;
						}
					// carry exists but is smaller than node 1 add to the tree and save it as previous
					else if (carry != null && carry.rank < node1.rank) { 
						carry.next = node1;
						if(last) { // need to update first tree node in order to connect it to last
							fst = carry;
							prev1 = node1;
							node1 = node1.next;
							carry = null;
							last = false;}
						else {
							if(prev1 != null) {prev1.next = carry;};
							prev1 = node1;
							node1 = node1.next;
							carry = null;
						}
					}
					else {
						tmp = node1.next;
						node1.next = node2;
						prev1 = node1;
						node1 = tmp;
					}
					}
				// case b rank of node2 is smaller use same algorithm but connect the results to node 1
				else if (node2.rank < node1.rank) {
					if(carry != null && carry.rank == node2.rank) {
						tmp = node2.next;
						carry = node2.link(carry);
						node2 = tmp;
					}
					else if(carry != null && carry.rank < node2.rank) {
						carry.next = node2;
						if(last) {
							fst = carry;
							prev1 = carry;
							node2 = node2.next;
							carry = null;
							last = false;}
						else {
							if(prev1 != null) {prev1.next = carry;};
							prev1 = node2;
							node2 = node2.next;
							carry = null;}
					}
					else {
						if(prev1 != null) {prev1.next = node2;}
						tmp = node2.next;
						prev1 = node2;
						node2.next = node1;
						node2 = tmp;
						}
					}
				// case c rank of node1 is equal to rank of node 2 add node2 to carry and continue
				else {
					if(carry != null) {
						tmp = node2.next;
						carry = carry.link(node2);
						if(prev1 == null) {
							last = false;
							fst = node1;}
						prev1 = node1;
						node1 = node1.next;
						node2 = tmp;
					}
					else {
						tmp = node2.next;
						HeapNode tmp1 = node1.next;
						if(prev1 != null) {prev1.next = tmp1;}
						carry = node1.link(node2);
						node1 = tmp1;
						node2 = tmp;
					}
				}
				}
			// heap1 longer than heap2 take care of carry
			if(node1 != null) {
				if(carry != null) {
					while(carry != null && node1 != null) {
						if(carry.rank == node1.rank) {
							tmp = node1.next;
							carry = carry.link(node1); //continue linking until end of tree or rank of node1 is bigger
							node1 = tmp;
							}
						else {
							if(prev1 != null) {prev1.next = carry;}
							carry.next = node1;
							//if carry is first then assign it 
							if(last) { 
								fst = carry;
								last = false;
							}
							carry = null;}
					}
				}
				}
			//heap2 longer than heap1 take care of carry and connect the remainder to heap1 
			else if (node2 != null) {
					while(node2 != null) {
						if(carry != null && carry.rank == node2.rank) {
							tmp = node2.next;
							carry = carry.link(node2);
							node2 = tmp;
						}
						else if (carry != null && carry.rank < node2.rank) {
							if(prev1 != null) {prev1.next = carry;}
							carry.next = node2;
							prev1 = node2;
							carry = null;
						}
						else {
							this.last = heap2.last;
							if(prev1 != null) {prev1.next = node2;}
							prev1 = node2;
							node2 = node2.next;
						}
					}
				}
			//both heaps finished but carry still exists connect it to the end 
			if(carry != null) {
				if(prev1 != null) {prev1.next = carry;}
				if(last) {fst= carry;} // carry is first in heap 
				this.last = carry;
				carry = null;}
			if(heap2.min.item.key < this.min.item.key) {this.min = heap2.min;} //update minimum
			this.last.next = fst; // close the circle
			
			}
			
		

			
		
		
		/**
		 * 
		 * Return the number of elements in the heap
		 *   
		 */
		public int size() //  Ehud 26.2
		{	return this.size; //return new size
		}

		/**
		 * 
		 * The method returns true if and only if the heap
		 * is empty.
		 *   
		 */
		public boolean empty() {
			return this.size == 0; // should be replaced by student code
		}

		/**
		 * 
		 * Return the number of trees in the heap.
		 * 
		 */
		public int numTrees()
		{if(this.empty()) {return 0;}
		HeapNode node = this.last.next;
		int cnt = 1;
		while(node != this.last) {
			cnt += 1;
			node = node.next;}
		return cnt; // should be replaced by student code
		}

		/**
		 * Class implementing a node in a Binomial Heap.
		 *  
		 */
		public class HeapNode{
			public HeapItem item;
			public HeapNode child;
			public HeapNode next;
			public HeapNode parent;
			public int rank;
			
			public HeapNode(){                             ///constructor    Neri 26/2-1030
				this.item = null;
				this.child = null;
				this.next = null;
				this.parent = null;
				this.rank = 0;
			}
			
			public HeapNode link(HeapNode node2) {       ///helper. yoav 02/03
				counter += 1;
				this.next = null;
				node2.next = null;
				// case 1 no children to connect
				if(this.rank == 0 || node2.rank == 0) {
					if(this.item.key > node2.item.key) {
						node2.child = this;
						this.parent = node2;
						this.next = this;
						node2.rank++;
						return node2;}
					else {
						this.child = node2;
						node2.parent = this;
						node2.next = node2;
						this.rank++;
						return this;
						}
					}
				//case 2 need to connect the children
				if(this.item.key > node2.item.key) { // key of node 2 is root
					HeapNode child2 = node2.child;
					HeapNode fst = node2.child.next;
					node2.child = this;
					this.parent = node2;
					child2.next = this;
					this.next = fst;
					node2.rank++;
					return node2;}
				else { // key of node 1 is root
					HeapNode child1 = this.child;
					HeapNode fst = this.child.next;
					this.child = node2;
					node2.parent = this;
					child1.next = node2;
					node2.next = fst;
					this.rank++;
					return this;}
				}
				}
		
		

		// helpers 

	public HeapNode findNextMin() { // Ehud 26.2 // 
		if (this.min == null) {
	        return null; // Heap is empty
	    }

	    HeapNode min = this.min;
	    HeapNode next_min = this.min.next;
	    HeapNode temp = this.min.next;

	    while (temp != min) {
	        if (temp.item.key < min.item.key) { //comparing all items in tree
	            next_min = temp;
	        }
	        temp = temp.next;
	    }

	    return next_min;
	}


	public HeapNode findMinNew(HeapNode node) {
	    if (node == null) {
	        return null; // Node is null, so there are no children
	    }

	    HeapNode min = node;
	    HeapNode temp = node.next;

	    while (temp != node) { 
	        if (temp.item.key < min.item.key) { //loop through the tree finding lowest key
	            min = temp;
	        }
	        temp = temp.next;
	    }

	    return min;
	}
	public void shiftUp(HeapNode node) {//yoav 04.03
		boolean lst = false;
		while (node.parent != null) { 
			if(node.item.key < node.parent.item.key) {
				 HeapItem parent_item = node.parent.item;
				 HeapNode parent_node = node.parent;
				 HeapItem node_item = node.item;
				 if(this.last == parent_node) {lst = true;} //a need to update last popped up
				 parent_node.item = node_item; //replacing item pointers between node and parent
				 node_item.node = parent_node;
				 node.item = parent_item;
				 parent_item.node = node;
				 if(true) {this.last = parent_node;}
				 node = parent_node;}
			break;
		}
		if (node.item.key < this.min.item.key) {this.min = node;}
		}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */

	public class HeapItem{          
		public HeapNode node;
		public int key;
		public String info;
		
		public HeapItem(){                         ///constructor          Neri 26/2- 1050
			this.node = null;
			this.key = -1;                        //-1 is not a valid key
			this.info = null;
		}
	}
	}
