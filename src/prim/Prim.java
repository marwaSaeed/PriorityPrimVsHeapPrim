package prim;

import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class Prim {

    public static void main(String[] args) {
        //declaration startTime, endTime, estimated Time, to Calculate time execution of algorithm
        long startTime;
        long endTime;
        long estimatedTime;
        Generate_Random_Graph graph = Make_graph(10);

        //******************Prim's using Priority queue***********************************
        System.out.println();
        System.out.println("************************Prim's using Priority queue************************");

        startTime = System.currentTimeMillis();

        graph.prim_Using_PriorityQueue();

        endTime = System.currentTimeMillis();
        estimatedTime = endTime - startTime;
        System.out.println("The time execution  of prim's algorithm using priority queue = " + estimatedTime);

        //*********************Prim's using Min Heap**************************************
        System.out.println();
        System.out.println("************************Prim's using Min Heap************************");

        startTime = System.currentTimeMillis();

        graph.prim_Using_MinHeap();

        endTime = System.currentTimeMillis();
        estimatedTime = endTime - startTime;
        System.out.println("The time execution  of prim's algorithm using Min Heap = " + estimatedTime);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Make_graph--> Generate Random graph for differant cases
    public static Generate_Random_Graph Make_graph(int cases) {

        int v = 0, e = 0;//decliration and Initialize two varible v(vertices) and e(edges)

        switch (cases) {
            case 1:     //Case1: generate v=1000 , e= 10000
                v = 1000;
                e = 10000;
                break;
            case 2:    //Case2: generate v=1000 , e= 15000
                v = 1000;
                e = 15000;

                break;
            case 3:    //Case3: generate v=1000 , e= 25000
                v = 1000;
                e = 25000;
                break;
            case 4:    //Case4: generate v= 5000 , e= 15000
                v = 5000;
                e = 15000;
                break;
            case 5:    //Case5: generate v=5000, e= 25000
                v = 5000;
                e = 25000;
                break;
            case 6:   //Case2: generate v=10000 , e= 15000
                v = 10000;
                e = 15000;
                break;
            case 7:  //Case7: generate v=10000 , e= 25000
                v = 10000;
                e = 25000;
                break;
            case 8:  //Case8: generate v=20000 , e=200000
                v = 20000;
                e = 200000;
                break;
            case 9:  //Case9: generate v=20000 , e= 300000
                v = 20000;
                e = 300000;
                break;
            case 10: //Case10: generate v=50000 , e= 1000000
                v = 50000;
                e = 1000000;
                break;
        }
        // Create a Generate_Random_Graph object
        return new Generate_Random_Graph(v, e);

    }

    //**************************************************************************
    //class of edge 
    static class Edge {

        //decliration source, destination, weight
        int source;
        int destination;
        int weight;

        // Creating the constructor
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public boolean equals(Object o) {
            //check if the 
            return (this.source == ((Edge) o).source && this.destination == ((Edge) o).destination);
            //return false  if => not equel 
            //return True  if  => equel
        }

    }

    //**************************************************************************
    // Class to define the Parent and weight for evry vertx 
    static class ResultSet {

        int parent;
        int weight;
    }
    //**************************************************************************
    // class to Generate Random Graph 

    public static class Generate_Random_Graph {

        //decliration vertices, edges
        public int vertices;
        public int edges;

        Random random = new Random();// generate random values

        // An adjacency list to represent a graph
        LinkedList<Edge>[] adjacencyList; //decliration adjacencyList

        // Creating the constructor
        public Generate_Random_Graph(int Vertices, int edge) {

            this.vertices = Vertices;//the number of vertices 

            this.edges = edge; //the number of edges 

            adjacencyList = new LinkedList[vertices];//Initializing the linked list by the number of vertices 

            // Creating an adjacency list for each vertices
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new LinkedList<>();
            }

            // for loop to generate random edges
            for (int i = 0; i < edges; i++) {
                // Randomly select two vertices (v,u) to create an edge between them
                int v = random.nextInt(vertices);
                int u = random.nextInt(vertices);

                Random ran = new Random();
                int wieght = ran.nextInt(10) + 1;
                //creating an edge between the two vertices v and u and assigning it a random weight 
                addEgde(v, u, wieght);
            }

        }

        //**************************************************************************
        // Method to add edges between source, destination
        public void addEgde(int source, int destination, int weight) {
            //creating two edges ot represent graph as undirected graph 
            //for example: 1-2 and 2-1 have the same weight
            Edge edge1 = new Edge(source, destination, weight);
            Edge edge = new Edge(destination, source, weight);
            //chick if there is loop or not 
            if (!(source == destination)) {
                //chick if the edge(source-----destination)and (destination-----source)in adjacencyList
                if (!(exists(source, destination)) && !(exists(destination, source))) {
                    adjacencyList[source].addFirst(edge1);//add edge in adjacencyList
                    adjacencyList[destination].addFirst(edge);//add edge in adjacencyList
                }
            }
        }
        //**************************************************************************    
        // Function to chick if edge contains in adjacencyList  

        public boolean exists(int v, int u) {
            Edge edge = new Edge(v, u, 0);
            return adjacencyList[v].contains(edge);
        }

        //**************************************************************************
        //function to find the minimum spannig tree by using Priority Queue
        public void prim_Using_PriorityQueue() {

            boolean[] mst = new boolean[vertices];//declaration and Initialization boolean array 
            ResultSet[] resultSet = new ResultSet[vertices];//declaration and Initialization ResultSet array to store the wight and parint for each vertix 
            int[] key = new int[vertices];  //keys used to store the key to know whether priority queue update is required

            //Initialize all the keys to infinity and initialize resultSet for all the vertices
            for (int i = 0; i < vertices; i++) {
                key[i] = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
            }

            //declaration and Initialization of priority queue
            //override the comparator to do the sorting based keys
            PriorityQueue<Pair<Integer, Integer>> Queue = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using key values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                }
            });

            //create the pair for for the first index-->  key(weight)=0, index(vertice)=0
            key[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);

            Queue.offer(p0);//add pair to pq

            resultSet[0] = new ResultSet();
            resultSet[0].parent = -1; // Assigning the vertex in index 0 a value of -1 since it has no parent 

            //while priority queue is not empty
            while (!Queue.isEmpty()) {
                //extract the pair from queue
                Pair<Integer, Integer> extractedPair = Queue.poll();

                //extracted vertex
                int extractedVertex = extractedPair.getValue();
                mst[extractedVertex] = true;

                //iterate through all the adjacent vertices and update the keys
                LinkedList<Edge> list = adjacencyList[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is not present in mst
                    if (mst[edge.destination] == false) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update 
                        if (key[destination] > newKey) {
                            //creating a pair with destination and newKey
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            Queue.offer(p); //add pair(p)to the priority queue

                            //update the resultSet for destination vertex
                            resultSet[destination].parent = extractedPair.getValue();
                            resultSet[destination].weight = newKey;
                            //update the key[]
                            key[destination] = newKey;
                        }
                    }
                }
            }

        }

        //***************************************************************************************************
        //###############################Prim's using Priority queue#########################################
        //function to find the minimum spannig tree by using min heap 
        public void prim_Using_MinHeap() {

            //declaration and Initialization boolean array 
            boolean[] inHeap = new boolean[vertices];

            //declaration and Initialization ResultSet array to store the wight and parint for each vertix 
            ResultSet[] resultSet = new ResultSet[vertices];

            //keys used to store the key to know whether min heap update is required
            int[] key = new int[vertices];

            //create heapNode for all the vertices
            HeapNode[] heapNodes = new HeapNode[vertices];
            for (int i = 0; i < vertices; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
                resultSet[i].parent = -1;
                inHeap[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            //decrease the key for the first index
            heapNodes[0].key = 0;

            //insert all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(vertices);

            //insert all the vertices to priority queue
            for (int i = 0; i < vertices; i++) {
                minHeap.insert(heapNodes[i]);
            }

            //enter in the while loop until min Heap is empty 
            while (!minHeap.isEmpty()) {
                //extract the root of the heap
                HeapNode extractedNode = minHeap.extractMin();

                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                inHeap[extractedVertex] = false;

                //insert all the adjacent vertices of (extractedVertex)
                LinkedList<Edge> list = adjacencyList[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is present in heap
                    if (inHeap[edge.destination]) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update it
                        if (key[destination] > newKey) {
                            decreaseKey(minHeap, newKey, destination);
                            //update the parent node for destination
                            resultSet[destination].parent = extractedVertex;
                            //update the weight node for destination
                            resultSet[destination].weight = newKey;
                            //change the key of destination by newKey
                            key[destination] = newKey;
                        }
                    }
                }
            }

        }

        //**************************************************************************
        public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {

            //get the index which key's needs a decrease;
            int index = minHeap.indexes[vertex];

            //get the node and update its value
            HeapNode node = minHeap.mH[index];
            node.key = newKey;
            minHeap.bubbleUp(index);
        }

        //**************************************************************************
        //class heapNode to define vertex and key fo evry node
        static class HeapNode {

            int vertex;
            int key;
        }

        //**************************************************************************
        static class MinHeap {

            int capacity;
            int currentSize;
            HeapNode[] mH;
            int[] indexes; //will be used to decrease the key

            public MinHeap(int capacity) {
                this.capacity = capacity;
                mH = new HeapNode[capacity + 1];
                indexes = new int[capacity];
                mH[0] = new HeapNode();
                mH[0].key = Integer.MIN_VALUE;
                mH[0].vertex = -1;
                currentSize = 0;
            }

            public void insert(HeapNode x) {
                currentSize++;
                int idx = currentSize;
                mH[idx] = x;
                indexes[x.vertex] = idx;
                bubbleUp(idx);
            }

            public void bubbleUp(int pos) {
                int parentIdx = pos / 2;
                int currentIdx = pos;
                while (currentIdx > 0 && mH[parentIdx].key > mH[currentIdx].key) {
                    HeapNode currentNode = mH[currentIdx];
                    HeapNode parentNode = mH[parentIdx];

                    //swap the positions
                    indexes[currentNode.vertex] = parentIdx;
                    indexes[parentNode.vertex] = currentIdx;
                    swap(currentIdx, parentIdx);
                    currentIdx = parentIdx;
                    parentIdx = parentIdx / 2;
                }
            }

            public HeapNode extractMin() {
                HeapNode min = mH[1];
                HeapNode lastNode = mH[currentSize];
                // update the indexes[] and move the last node to the top
                indexes[lastNode.vertex] = 1;
                mH[1] = lastNode;
                mH[currentSize] = null;
                sinkDown(1);
                currentSize--;
                return min;
            }

            public void sinkDown(int k) {
                int smallest = k;
                int leftChildIdx = 2 * k;
                int rightChildIdx = 2 * k + 1;
                if (leftChildIdx < heapSize() && mH[smallest].key > mH[leftChildIdx].key) {
                    smallest = leftChildIdx;
                }
                if (rightChildIdx < heapSize() && mH[smallest].key > mH[rightChildIdx].key) {
                    smallest = rightChildIdx;
                }
                if (smallest != k) {

                    HeapNode smallestNode = mH[smallest];
                    HeapNode kNode = mH[k];

                    //swap the positions
                    indexes[smallestNode.vertex] = k;
                    indexes[kNode.vertex] = smallest;
                    swap(k, smallest);
                    sinkDown(smallest);
                }
            }

            //function swap() swapping between two node 
            public void swap(int a, int b) {
                HeapNode temp = mH[a];
                mH[a] = mH[b];
                mH[b] = temp;
            }

            //function isEmpty() return true if currentSize=0 and false if currentSize!=0
            public boolean isEmpty() {
                return currentSize == 0;
            }

            //function heapSize() return currentSize
            public int heapSize() {
                return currentSize;
            }
        }
    }
}
