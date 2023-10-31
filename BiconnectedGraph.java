// A Java program to find if a graph is biconnected and print it's articulation points(if not biconnected)
import java.util.*;

class Graph {

	static int time=-1;
    static boolean flag= false;
    

	static void addEdge(ArrayList<ArrayList<Integer> > adj, int u, int v)
	{
		adj.get(u).add(v);//undirected graph
		adj.get(v).add(u);
	}

	static void APUtil(ArrayList<ArrayList<Integer> > adj, int u,boolean visited[], int disc[], int low[],int parent, boolean isAP[])
	{
		// Count of children in DFS Tree
		int children = 0;

		// Mark the current node as visited
		visited[u] = true;

		// Initialize discovery time and low value
		disc[u] = low[u] = ++time;

		// Go through all vertices adjacent to this
		for (Integer v : adj.get(u)) {
			// If v is not visited yet, then make it a child of u
			// in DFS tree and recur for it
			if (!visited[v]) {
				children++;
				APUtil(adj, v, visited, disc, low, u, isAP);

				// Check if the subtree rooted with v has
				// a connection to one of the ancestors of u
				low[u] = Math.min(low[u], low[v]);

				// If u is not root and low value of one of
				// its child is more than discovery value of u.
				if (parent != -1 && low[v] >= disc[u]){
					isAP[u] = true;
                    flag= true;
                }
			}

			// Update low value of u for parent function calls.
			else if (v != parent)
				low[u] = Math.min(low[u], disc[v]);
		}

		// If u is root of DFS tree and has two or more children.
		if (parent == -1 && children > 1){
			isAP[u] = true;
            flag=true;
        }
	}

	static void BiConCheck(ArrayList<ArrayList<Integer> > adj, int V)
	{
		boolean[] visited = new boolean[V];
		int[] disc = new int[V];
		int[] low = new int[V];
		boolean[] isAP = new boolean[V];
		int parent = -1;
        boolean connected=true;
        
        
        // To find articulation/ point in given graph. We do DFS traversal
        // starting from vertex 0
		APUtil(adj, 0, visited, disc, low, parent, isAP);

        for (int i = 0; i < V; i++){
            if (visited[i] == false)
                connected=false;
        }

        if(connected==false)
            System.out.println("Graph is not connected");
        else{
            if(flag){
                System.out.println("Graph is not biconnected and following are the articulation points-");
                for (int u = 0; u < V; u++){
			        if (isAP[u] == true)
				        System.out.print(u + " ");
                }
                System.out.println();
            }
            else
                System.out.println("Graph is biconnected");
        }          

        flag=false;
    }

	public static void main(String[] args)
	{
        //Sample graph with Articulation points 0,3 for code testing
        /*V = 5;
        for (int i = 0; i < V; i++)
			adj1.add(new ArrayList<Integer>());
		addEdge(adj1, 1, 0);
		addEdge(adj1, 0, 2);
		addEdge(adj1, 2, 1);
		addEdge(adj1, 0, 3);
		addEdge(adj1, 3, 4);
		BiConCheck(adj1, V);*/

        //Sample Biconnected graph for code testing
		/*V = 4;
		ArrayList<ArrayList<Integer> > adj2 = 
						new ArrayList<ArrayList<Integer> >(V);
		for (int i = 0; i < V; i++)
			adj2.add(new ArrayList<Integer>());

		addEdge(adj2, 0, 1);
		addEdge(adj2, 1, 2);
		addEdge(adj2, 2, 3);
        addEdge(adj2, 0, 3);
		BiConCheck(adj2, V);*/

        Random random = new Random();

		// Creating random graph with number of vertices V
        int[] n={10,100,200,300,400,500,600,700,800,900,1000};//Values for number of vertices
       for(int x=0;x<n.length;++x){
		    int V = n[x];
            int ecount=0;
		    ArrayList<ArrayList<Integer> > adj = new ArrayList<ArrayList<Integer> >(V);
            for (int i = 0; i < V; i++)
			    adj.add(new ArrayList<Integer>());

            for (int i = 0; i < V; i++) {
                for (int j = i+1; j < V; j++) {
                    // Generate a random value to decide whether to add an edge
                    
                        Double randomValue = random.nextDouble();

                        if (randomValue <0.3) { //0.3 is used as edge probability, can be adjusted according to number of vertices 
                            addEdge(adj,i, j);
                            ++ecount;
                            //System.out.println(i+ "<--->" + j);
                        }
                }
            }
            long start = System.nanoTime();//start time
            BiConCheck(adj, V); //Calling function to check for Biconnectivity
            long end = System.nanoTime();//end time
            System.out.println("V= "+ V + " E= "+ ecount);
            System.out.println("Elapsed Time in nano seconds: "+ (end-start));
            System.out.println();
        }
       
	}
}
