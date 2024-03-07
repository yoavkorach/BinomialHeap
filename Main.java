public class Main {
    public static void main(String[] args) {
        double totalRuntime = 0;
        BinomialHeap heap = new BinomialHeap();
            // Get the current time before running the code
            double startTime = System.nanoTime();

            for (int i = 1; i < (int) Math.pow(3, 6); i++) {
                heap.insert(i, null);
            }
            for (int j = 1; j<(int) Math.floor(Math.pow(3, 6)/2);j++) {
            	heap.deleteMin();
            }

            // Get the current time after running the code
            double endTime = System.nanoTime();

            // Calculate the runtime in milliseconds for this iteration
            totalRuntime =+ ((endTime - startTime) / 1000000);

            // Accumulate the runtime for all iterations

        // Calculate the average runtime in milliseconds
        double averageRuntime = totalRuntime/1000;
        System.out.println("num of trees"+heap.numTrees());

        System.out.println("Average runtime: " + averageRuntime + " milliseconds");
        }
    }
