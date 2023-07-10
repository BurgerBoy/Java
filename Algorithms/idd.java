//Create nodes from verticies and costs. Add relelvant edges to corresponding
//verticies. Display increasing States with total cost less than the budget.
//To check covered edges, compare list of edges in current state (no doubles),
//If # of edges in current state = # entered, and cost <= T, solution is found.
//Else, keep trying until solution is found. If none is found, return FAIL. 


import java.util.*;

class Node { 
    String label;
    int cost;
    ArrayList<String> edges = new ArrayList<String>();
    
    Node(String l, int c){
        this.label = l;
        this.cost = c;
    }

    void addEdge(String edge){
        edges.add(edge);
    }
}

class State {
    // FIX ERROR
    int edgeCount;
    int totalCost;
    //int error;

    ArrayList<Node> nodes;
    ArrayList<Node> uncoveredNodes;
    ArrayList<String> coveredE;
    ArrayList<String> uncoveredE;

    
    State(){
        edgeCount = 0;

        totalCost = 0;

        nodes = new ArrayList<Node>();
    }

    State(State previous){
        edgeCount = previous.edgeCount;

        totalCost = previous.totalCost;

        nodes = new ArrayList<Node>(previous.nodes);
        uncoveredNodes = new ArrayList<Node>(previous.uncoveredNodes);
        coveredE = new ArrayList<String>(previous.coveredE);
        uncoveredE = new ArrayList<String>(previous.uncoveredE);
    }

    void getUN(ArrayList<Node> nodess){
        uncoveredNodes = new ArrayList<Node>();
        for(int i = 0; i < nodess.size(); i++){
            if( !nodes.contains(nodess.get(i)) ){
                uncoveredNodes.add(nodess.get(i));
            }
        }

    }

    //used for creating/altering states
   
    void addNodes(Node n){
        nodes.add(n);    
        //Update Cost & Error
        getCost();
    }


    void getCost(){
        //iterate over nodes
        int temp =0;
        for (int i = 0; i < nodes.size(); i++) {
            temp += nodes.get(i).cost;
        }
        totalCost = temp;
    }


    void getUncovered(ArrayList<String> edges){
        
        coveredE = new ArrayList<String>();
        uncoveredE = new ArrayList<String>(edges);

        for(int i =0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).edges.size(); j++ ){
                if(!coveredE.contains(nodes.get(i).edges.get(j))){
                    coveredE.add(nodes.get(i).edges.get(j));
                }   
            }
        }

        uncoveredE.removeAll(coveredE);
    }

    

    boolean checkGoal(ArrayList<String> edges, int budget){
        boolean isGoal = false;
        
        if( (coveredE.containsAll(edges)) && (totalCost <= budget) ){
            isGoal = true;
        }

        return isGoal;
    }


}


public class idd {

    static void updateNodeEdges(String a, ArrayList<Node> n){
        String[] a1 = a.split(" ");

        for(int i = 0; i < n.size(); i++){
            
            if(a1[0].equals(n.get(i).label)){
                n.get(i).addEdge(a);
            }
            else if(a1[1].equals(n.get(i).label)){
                n.get(i).addEdge(a);
            }
            
        }
    }

    static String printState(State s){
        String string = "";
        
        for(int i =0; i < s.nodes.size(); i++){
            string += s.nodes.get(i).label + " ";
        }

        return string;
    }

    static void printSolution(State s){
        String string = "";
        
        for(int i =0; i < s.nodes.size(); i++){
            string += s.nodes.get(i).label + " ";
        }

        System.out.println("Found solution: "+string+ "Cost=" +s.totalCost);
    }

    static void printStuff(ArrayList<State> s){
        for(int i = 0; i < s.size(); i++){
            String p = "";
            for(int j =0; j < s.get(i).nodes.size(); j++){
                p = p + s.get(i).nodes.get(j).label;
                if((j+1) == s.get(i).nodes.size()){
                    p+= ". ";
                }
                else{
                    p+= " ";
                }
            }
            System.out.println(p +"Cost="+ s.get(i).totalCost +".");
        }
    }

    static ArrayList<State> ids(ArrayList<State> states, ArrayList<Node> allNodes, 
                                ArrayList<String> allEdges, int b){

        ArrayList<State> ns = new ArrayList<State>();

        for(int i = 0; i < states.size(); i++){
            State s1 = new State(states.get(i));
            ns.add(s1);
            
            for(int j = 0; j < s1.uncoveredNodes.size(); j++){
                //if applicable, add node. get uncovered edges, nodes.
                int last = s1.nodes.size() - 1;
                if( s1.nodes.get(last).label.compareTo(s1.uncoveredNodes.get(j).label )< 0){
                    State s2 = new State();
                    //s2.addNodes(allNodes.get(i));
                    for(int k = 0; k < s1.nodes.size(); k++){
                        s2.addNodes(s1.nodes.get(k));
                    }
                    
                    s2.addNodes(s1.uncoveredNodes.get(j));
                    s2.getUN(allNodes);
                    s2.getUncovered(allEdges);
                    ns.add(s2);
                    if(s2.checkGoal(allEdges, b) == true){
                        //end loop
                        solutionFound = true;
                        j = s1.uncoveredNodes.size();
                        i = states.size();
                        //printSolution(s2);
                        sol = new State(s2);
                    }
                }
            }
        }
        return ns;
    }

    public static boolean solutionFound;
    public static State sol;

    public static void main(String[] args) {

    	Scanner input = new Scanner(System.in);

        solutionFound = false;
        ArrayList<Node> allNodes = new ArrayList<Node>();
        ArrayList<State> states = new ArrayList<State>();
        
        String vLine = input.nextLine();

        String[] firstLine = vLine.split(" ");
        int budget = Integer.parseInt(firstLine[0]);
        String verCom = firstLine[1];

        System.out.println();
        

        while(!vLine.equals("") ){
        	vLine = input.nextLine();
        	
        	if(!vLine.equals("")){
        		String[] cPairs = vLine.split(" ");
        		Node node = new Node(cPairs[0], Integer.parseInt(cPairs[1]));
                allNodes.add(node);
        	}
        }

        ArrayList<String> allEdges = new ArrayList<String>();

        //Adds edges to nodes in Edge ArrayList, Node ArrayList

        String edge = input.nextLine();
        allEdges.add(edge);
        updateNodeEdges(edge, allNodes);

        String[] ePairs = edge.split(" ");

        while(!edge.equals("") ){
            edge = input.nextLine();
            
            if(!edge.equals("")){
                allEdges.add(edge);
                updateNodeEdges(edge, allNodes);
            }   
        }


        for(int i = 0; i < allNodes.size(); i++){
            State state = new State();
            state.addNodes(allNodes.get(i));
            state.getUN(allNodes);
            state.getUncovered(allEdges);
            states.add(state);

        }

        int currentDepth = 1;
        System.out.println("Depth = 1");
        for(int i = 0; i < allNodes.size(); i++){
            System.out.println(allNodes.get(i).label +" Cost="+ allNodes.get(i).cost);
            
        }
        currentDepth++;

        System.out.println();


        ArrayList<State> holder = new ArrayList<State>(states);
        ArrayList<State> newStates;

        while( (currentDepth <= allNodes.size()) && (solutionFound == false) ){
            System.out.println("Depth = " + currentDepth);
           
            newStates = new ArrayList<State>(ids(holder,allNodes,allEdges,budget));
            printStuff(newStates);
            System.out.println();
            holder = new ArrayList<State>(newStates);
            currentDepth++;
        }
        
        if(solutionFound == true){
            printSolution(sol);
        }
    }
}