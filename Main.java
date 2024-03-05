public class Main {
	    public static void main(String[] args) {
	        // Instantiate two BinomialHeap objects
	        BinomialHeap binomialHeap1 = new BinomialHeap();
	        BinomialHeap binomialHeap2 = new BinomialHeap();

	        // Test insert method
	        System.out.println("Testing insert method:");
	        binomialHeap1.insert(5, "info1");
	        binomialHeap1.insert(3, "info2");
	        binomialHeap1.insert(8, "info3");
	        binomialHeap1.insert(10, "info4");
	        binomialHeap1.insert(2, "info5");
	        binomialHeap1.insert(15, "info6");

	        binomialHeap2.insert(7, "info7");
	        binomialHeap2.insert(12, "info8");
	        binomialHeap2.insert(6, "info9");
	        binomialHeap2.insert(20, "info10");
	        binomialHeap2.insert(4, "info11");
	        binomialHeap2.insert(11, "info12");

	        // Test findMin method
	        System.out.println("Testing findMin method:");
	        System.out.println("Min item in heap 1: " + binomialHeap1.findMin().key + " (Expected: 2)");
	        System.out.println("Min item in heap 2: " + binomialHeap2.findMin().key + " (Expected: 4)");

	        // Test meld method
	        System.out.println("Testing meld method:");
	        binomialHeap1.meld(binomialHeap2);

	        // Test findMin method after melding
	        System.out.println("Min item after melding: " + binomialHeap1.findMin().key + " (Expected: 2)");

	        // Test deleteMin method
	        System.out.println("Testing deleteMin method:");
	        binomialHeap1.deleteMin();
	        System.out.println("Min item after deleteMin: " + binomialHeap1.findMin().key + " (Expected: 3)");
	        System.out.println("size after delete " + binomialHeap1.size);

	        // Test decreaseKey method
	        System.out.println("Testing decreaseKey method:");
	        BinomialHeap.HeapItem item = binomialHeap1.insert(7, "info13");
	        binomialHeap1.decreaseKey(item, 7);
	        System.out.println("Min item after decreaseKey: " + binomialHeap1.findMin().key + " (Expected: 5)");

	        // Test delete method
	        System.out.println("Testing delete method:");
	        binomialHeap1.delete(item);
	        System.out.println("Min item after delete: " + binomialHeap1.findMin().key + " (Expected: 3)");
	        System.out.println("size after delete: " + binomialHeap1.size);

	    }
	}