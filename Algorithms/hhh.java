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
    int error;

    ArrayList<Node> nodes;
    ArrayList<Node> uncoveredNodes;
    ArrayList<String> coveredE;

    
    State(){
        edgeCount = 0;

        totalCost = 0;

        error = 0;

        nodes = new ArrayList<Node>();

    }

    State(State previous){
        edgeCount = previous.edgeCount;

        totalCost = previous.totalCost;

        error = error;

        nodes = new ArrayList<Node>(previous.nodes);
        //uncoveredNodes = new ArrayList<Node>(previous.uncoveredNodes);
        coveredE = new ArrayList<String>(previous.coveredE);
    }

    ArrayList<Node> getUN(ArrayList<Node> nodess){
        uncoveredNodes = new ArrayList<Node>();
        for(int i = 0; i < nodess.size(); i++){
            if( !nodes.contains(nodess.get(i)) ){
                uncoveredNodes.add(nodess.get(i));
            }
        }
        return uncoveredNodes;

    }

    //used for creating/altering states
   
    void addNodes(Node n){
        nodes.add(n);
        
        //Update Cost & Error
        getCost();
    }

    void removeNode(Node n){
        nodes.remove(n);
        //Update Cost & Error
        getCost();
    }

    void removeNode(int i){
        nodes.remove(i);
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


    ArrayList<String> getUncovered(ArrayList<String> edges){
        
        coveredE = new ArrayList<String>();
        ArrayList<String> uncovered = new ArrayList<String>(edges);

        for(int i =0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).edges.size(); j++ ){
                if(!coveredE.contains(nodes.get(i).edges.get(j))){
                    coveredE.add(nodes.get(i).edges.get(j));
                    //System.out.println(nodes.get(i).edges.get(j));
                }   
            }
        }

        uncovered.removeAll(coveredE);
        return uncovered;
    }

    void getError(HashMap<String, Node> nodes, ArrayList<String> edges, int b){
        ArrayList<String> uncovered = new ArrayList<String>(getUncovered(edges));
        String current = "";
        int e = 0; 
        for(int i = 0; i < uncovered.size(); i++){
            current = uncovered.get(i);
            String[] tokens = current.split(" ");
            e += Math.min(nodes.get(tokens[0]).cost, nodes.get(tokens[1]).cost);
            
        }
        error = Math.max(0, (totalCost- b)) +e;
    }



    //checkGoal: Takes Statecheck if stateCost <= Cost && edgeCount == edgeNumber.
    //checkGoal: Takes Statecheck if stateCost <= Cost && edgeCount == edgeNumber.

    boolean checkGoal(ArrayList<String> edges, int budget){
        boolean isGoal = false;
        
        if( (coveredE.containsAll(edges)) && (totalCost <= budget) ){
            isGoal = true;
        }

        return isGoal;
    }


}





public class hhh {

    static State chooseBest(ArrayList<State> stateList){

        State best = new State(stateList.get(0));

        for(int i = 1; i < stateList.size(); i++){
            State current = new State(stateList.get(i));
            if((current.error <= best.error) && 
                (current.coveredE.size() > best.coveredE.size()) ){
                best = new State(current);
            }
        }
        return best;
    }
    

    static State randomState(HashMap<String, Node> h, ArrayList<Node> n, 
        ArrayList<String> e, int b){

        State start = new State();
        int x = (int) ((Math.random() * ((h.size() - 2) + 1)) + 1);;
        //
        for(int i = 0; i < x; i++){
            int y = (int) ((Math.random() * ((h.size() - 2) + 1)) + 1);
            
            if( !start.nodes.contains(h.get(n.get(y-1).label)) ){
                start.addNodes(h.get(n.get(y-1).label));
            }
            else{
                i--;
            }
        }
        start.getError(h, e, b); 
        return start;
    }

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

    static void printStuff(ArrayList<State> s){
        System.out.println("Neighbors");
        for(int i = 1; i < s.size(); i++){
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
            System.out.println(p +"Cost="+ s.get(i).totalCost +
                ". "+"Error="+ s.get(i).error);
        }
    }

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
    
       

        String vLine = input.nextLine();
        String[] firstLine = vLine.split(" ");
        
        int budget = Integer.parseInt(firstLine[0]);
        String verCom = firstLine[1];
        int randRestart = Integer.parseInt(firstLine[2]);

        System.out.println(); 
        
        ArrayList<Node> allNodes = new ArrayList<Node>();
        HashMap<String, Node> nodeMap = new HashMap<String,Node>();

        while(!vLine.equals("") ){
            vLine = input.nextLine();
            
            if(!vLine.equals("")){
                String[] cPairs = vLine.split(" ");
                Node node = new Node(cPairs[0], Integer.parseInt(cPairs[1]));
                
                allNodes.add(node);
                nodeMap.put(cPairs[0],node);

            }
        }

        ArrayList<String> allEdges = new ArrayList<String>();

        //Adds edges to nodes in edge ArrayList, Node ArrayList,
        //String Key Node Value HashMap. Yet to create random state.  
        String edge = input.nextLine();
        allEdges.add(edge);
        updateNodeEdges(edge, allNodes);

        String[] ePairs = edge.split(" ");
        nodeMap.get(ePairs[0]).addEdge(edge);
        nodeMap.get(ePairs[1]).addEdge(edge);

        
        while(!edge.equals("") ){
            edge = input.nextLine();
            
            if(!edge.equals("")){
                allEdges.add(edge);
                updateNodeEdges(edge, allNodes);

                ePairs = edge.split(" ");
                nodeMap.get(ePairs[0]).addEdge(edge);
                nodeMap.get(ePairs[1]).addEdge(edge);
            }   
        }

        int rrLoop = 0;
        int count = 0;
        boolean foundSolution = false;
        State solution;
        State bState = new State();

        //Loop until random restart runs out, or solution is found
        while( (rrLoop < randRestart) && (foundSolution == false)  ){
            if(count == 0){
                bState = new State(randomState(nodeMap, allNodes, allEdges, budget));
                bState.getError(nodeMap, allEdges, budget);
                
                String baseString = "Randomly chosen start state: ";
                String stateString = "";

                for(int i = 0; i < bState.nodes.size(); i++){
                    stateString += bState.nodes.get(i).label + " ";
                }
                System.out.println(baseString + stateString);
                System.out.println(stateString + " Cost="+ bState.totalCost+
                                    ". Error="+bState.error);

            }

            ArrayList<State> states = new ArrayList<State>();

            states.add(bState);

        //Add bState permeations to states

            if(bState.nodes.size() > 1){
                for(int x =0; x < bState.nodes.size(); x++){
                    State temp = new State(bState);
                    temp.removeNode(x);
                    temp.getError(nodeMap, allEdges, budget);
                    states.add(temp);
                } 
            }
  
            ArrayList<Node> unc = new ArrayList<Node>(bState.getUN(allNodes));
            for(int x =0 ; x < unc.size(); x++){
                State temp = new State(bState);
                temp.addNodes(unc.get(x));
                temp.getError(nodeMap, allEdges, budget);
                states.add(temp);
            }


        
        //Print state information
            printStuff(states);
            

            System.out.println();

            State bestState = new State(chooseBest(states));


        //Goal is not found, best = base
            if ( (!bestState.checkGoal(allEdges, budget)) &&  ( printState(bestState).equals(printState(bState)) ) ){
                //Do another random restart, set count to 0, increase rrLoop
                
                System.out.println("Search failed");
                System.out.println();
                count = 0;
                rrLoop++;
            }

        //Goal is found
            else if(bestState.checkGoal(allEdges, budget)){
                    foundSolution = true;
                    solution = new State(bestState);
                    System.out.println("Found solution " +printState(solution)+
                        " Cost="+ solution.totalCost +". Error="+ solution.error);
            }

        //Goal is not found, best is found.
            else if ( !bestState.checkGoal(allEdges, budget) && !( printState(bestState).equals(printState(bState)) )  ){
                bState = new State(bestState);
                bState.getError(nodeMap, allEdges, budget);
                
                String sString = "";

                for(int i = 0; i < bState.nodes.size(); i++){
                    sString+= bState.nodes.get(i).label + " ";
                }

                System.out.println("Move to " + sString +
                        " Cost="+ bState.totalCost +". Error="+ bState.error);
                count++;

            }

        }
        
            

        if(foundSolution == false){

            System.out.println("No solution found");
        }
            




        




    }
}