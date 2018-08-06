/**
 * Created by kevin on 3/12/2018.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class MaxHeap {
    private int[] heap = new int[1];
    private int arrSize;
    private int heapSize;
    private int lastNode;
    private int max;
    private int removed;

    private String traversal;



    private String[] readFile(String inFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));

            String line;
            String[] val = null;
            while ((line = reader.readLine()) != null) {
                val = line.split(" ");
            }
            reader.close();
            return val;
        }
        catch(java.io.IOException e) {
            System.out.println("Either the file was not found, or something went wrong. Please try again.");
            System.exit(0);
        }
        return null;
    }


    //method to insert into the heap
    private void insert(int k) {
        arrSize = heap.length;

        int[] temp;
            //COPY ELEMENTS TO NEW ARRAY OF DOUBLE SIZE WHEN HEAP IS FULL
        if(heapSize == arrSize) {
            temp = new int[arrSize*2];
            for(int i = 0; i < heapSize; i++) {
                temp[i] = heap[i];
            }
            heap = temp;
        }
        lastNode = heapSize;
        heap[lastNode] = k;
        heapSize++;
        upheap(heap,lastNode);


    }
    private int getMax() {
        max = heap[0];
        return max;
    }

    private int delete() {
        removed = heap[0];
        lastNode = heapSize -1;
        heap[0] = heap[lastNode];
        lastNode = 0;
        heapSize--;
        downheap(heap, 0, heapSize);
     //   return the removed element
        return removed;
    }



    private boolean hasOneChild(int i) {
       return (((2 *i) + 1) < heapSize && (2* i) + 2 >= heapSize);

    }

    private boolean hasTwoChildren(int i) {
        return (((2 *i) + 1) < heapSize && (2 *i) + 2 < heapSize);
    }
private void upheap(int[] h, int i) {
        int parent = (i-1) /2;
        int temp;
        if(i < 0) {
            return;
        }
        temp = h[parent];
        if(h[i] > h[parent]) {
            h[parent] = h[i];
            h[i] = temp;
            upheap(h, parent);
        }
    }

    private void downheap(int[] h, int i, int size) {
        int leftChild = (2* i) + 1;
        int rightChild = (2* i) + 2;
        int temp;
        if(i >= size) {
            return;
        }
        temp = h[i];
        if (hasTwoChildren(i)) {
            if(h[i] < h[leftChild] || h[i] < h[rightChild]) {
                if(h[leftChild] > h[rightChild]) {
                    h[i] = h[leftChild];
                    h[leftChild] = temp;
                    downheap(h, leftChild, size);
                }
                else if(h[leftChild] < h[rightChild]) {
                    h[i] = h[rightChild];
                    h[rightChild] = temp;
                    downheap(h, rightChild, size);
                }
            }

        }
        //we don't need recursion because if a node has only 1 child in a complete binary tree, we know that child is the last leaf of the tree
        if(hasOneChild(i)) {
            if(h[leftChild] > h[i]) {
                h[i] = h[leftChild];
                h[leftChild] = temp;
            }
        }
    }

    private boolean search(int[] h, int key) {
        for(int i = 0; i < h.length; i++) {
            if(h[i] == key) {
                return true;
            }
        }
        return false;
    }



    //takes in a heap and traversal method for input and recursively prints
    private void print(int[] h, String t, int i, int size) {

        int leftChild = (2* i) + 1;
        int rightChild = (2* i) + 2;

        if(size == 0) {
            System.out.print("The heap is empty!");
            return;
        }

        if(i >= size) {
            return;
        }
        if (t.equals("pre")) {
            System.out.print(h[i] + " ");
            print(h, t, leftChild, size);
            print(h, t, rightChild, size);


        } else if (t.equals("post")) {
            print(h, t, leftChild, size);
            print(h, t, rightChild, size);
            System.out.print(h[i] + " ");

        } else if (t.equals("in")) {
            print(h, t, leftChild, size);
            System.out.print(h[i] + " ");
            print(h, t, rightChild, size);

        } else {
            System.out.println("UNEXPECTED ERROR: Please make sure you specified your method of traversal properly and try again.");
        }




    }

    private void execute(String[] target) {
        int key;
        MaxHeap maxHeap = new MaxHeap();
        traversal = target[0];
        if(!traversal.equals("pre") && !traversal.equals("post") && !traversal.equals("in")) {
            System.out.println("FATAL ERROR: The first command in the file must be a string that specifies the method of traversal for output" +
                    " (pre, post, in). The program will now exit.");
            System.exit(0);
        }
        System.out.print("Input read from file: ");
        for(int i = 0; i < target.length; i++) {
            System.out.print(target[i] + " ");
        }
        System.out.print("\n");

        for (int i = 1; i < target.length; i++) {
            try {
                if (target[i].endsWith(".in")) {
                    key = Integer.parseInt(target[i].replaceAll("[^\\d-]", ""));
                    System.out.println("\nCOMMAND: " + target[i]);
                    System.out.print("Heap before command, printed with " + target[0] + "-order traversal: ");
                    print(heap, traversal, 0, heapSize);
                    System.out.println(" ");
                    insert(key);
                    System.out.print("Heap after command, printed with " + target[0] + "-order traversal: ");
                    print(heap, traversal, 0, heapSize);
                    System.out.println("\n");
                /*for(int j = 0; j < heapSize; j++) {
                    System.out.print(heap[j] + " ");
                } */
                } else if (target[i].equals("del")) {
                    //delete max
                    System.out.println("\nCOMMAND: " + target[i]);
                    System.out.print("Heap before command, printed with " + target[0] + "-order traversal: ");
                    print(heap, traversal, 0, heapSize);
                    System.out.println(" ");

                    if (heapSize > 0) {
                        delete();
                        System.out.print("Successfully removed value " + removed + " from heap.");
                    }
                    else {
                        System.out.print("ERROR: You can't delete from an empty heap!");
                    }
                    System.out.println(" ");
                    System.out.print("Heap after command, printed with " + target[0] + "-order traversal: ");
                    print(heap, traversal, 0, heapSize);
                    System.out.println(" ");

                /*for(int j = 0; j < heapSize; j++) {
                    System.out.print(heap[j] + " ");
                } */
                } else if (target[i].endsWith(".sch")) {
                    //search something
                    key = Integer.parseInt(target[i].replaceAll("[^\\d-]", ""));
                    System.out.println("\nCOMMAND: " + target[i]);
                    if (search(heap, key)) {
                        System.out.println(key + " was found.");
                    } else {
                        System.out.println(key + " was not found.");
                    }
                } else {
                    System.out.print("Command '" + target[i] + "' rejected. REASON: Not a valid command.");
                }
            }
            catch(NumberFormatException e) {
                System.out.println("\nERROR: Command '" + target[i] + "' is not a valid command. Only integers are allowed in the heap. Command was not processed.");
            }

        }


    }
    public static void main(String[] args) {
        MaxHeap run = new MaxHeap();

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of a file.");
        String userIn = scan.nextLine();

        run.execute(run.readFile(userIn));
    }
}
