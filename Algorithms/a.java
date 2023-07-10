import java.util.*;
import java.io.*;


public class a {

    static HashMap<Integer, String> returnJumps(int[][] a, int n){
        
        HashMap<Integer, String> axioms = new HashMap<Integer, String>();
        for(int i = 0; i < a.length;i++){
    
            for(int z = 1; z < n-1; z++){

                String s = "Jump(";

                for(int j = 0; j < a[0].length; j++){
                    s += a[i][j] + ",";

                }
    
                s += z +")";
                axioms.put((i*2) + z, s);
            }
        }
        return axioms;

    }

    static HashMap<Integer, String> returnAxioms(int[][] a, int n){

        HashMap<Integer,String> b = returnJumps(a,n);
        int m = b.size() + 1;
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n-1; j++){
                String s = "Peg(" + (i+1) + "," + (j+1) +")" ;
                b.put(m, s);
                m++;
            }      

        }
        return b;
    
    }

    static HashMap<String, Integer> returnJumpsFlip(int[][] a, int n){
        
        HashMap<String, Integer> axioms = new HashMap<String, Integer>();
        for(int i = 0; i < a.length;i++){
    
            for(int z = 1; z < n-1; z++){

                String s = "Jump(";

                for(int j = 0; j < a[0].length; j++){
                    s += a[i][j] + ",";

                }
    
                s += z +")";
                axioms.put(s, (i*2) + z);
            }
        }
        return axioms;

    }

    static HashMap<String, Integer> returnAxiomsFlip(int[][] a, int n){

        HashMap<String, Integer> b = returnJumpsFlip(a,n);
        int m = b.size() + 1;
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n-1; j++){
                String s = "Peg(" + (i+1) + "," + (j+1) +")" ;
                b.put(s, m);
                m++;
            }      

        }
        return b;
    
    }

    static int[][] return2D(ArrayList<String> a){
        
        int[][] triples = new int[a.size()*2][3];

        for(int i = 0; i < a.size(); i++){
            String[] s = a.get(i).split(" ", 3);


            for(int j = 0; j < 3; j++){
                triples[i*2][j] =  Integer.parseInt(s[j]);
                triples[(i*2)+1][2-j] =  Integer.parseInt(s[j]);
            }
        }

        return triples;
    }

    static String axioms11(int[] a, int n, HashMap<String, Integer> b){
        String sFinal = "";

        for(int z = 1; z < n-1; z++){
            String j = "Jump(";

            for(int h = 0; h < a.length; h++){
                j += a[h] + ",";

            }
        
            j += z +")";
        
            for(int i = 0; i < a.length; i++){
                String p = "Peg(" + a[i] + "," + z +")";
                if(i == 2){
                    String s1 = "-"+ b.get(j) + " -" + b.get(p);
                    sFinal += s1 +"\n";

                }
                else{
                    String s1 = "-"+ b.get(j) + " " + b.get(p);
                    sFinal += s1 +"\n";

                }   
            
            }
        } 
        return sFinal;   
    }

    static String axioms21(int[] a, int n, HashMap<String, Integer> b){
        String sFinal = "";

        for(int z = 1; z < n-1; z++){
            String j = "Jump(";

            for(int h = 0; h < a.length; h++){
                j += a[h] + ",";

            }
            j += z +")";

            for(int i = 0; i < a.length; i++){
                String p = "Peg(" + a[i] + "," + (z+1) +")";
                if(i != 2){
                    String s1 = "-"+ b.get(j) + " -" + b.get(p);
                    sFinal += s1 +"\n";
                }

                else{
                    String s1 = "-"+ b.get(j) + " " + b.get(p);
                    sFinal += s1 +"\n";
                }   
            
            }
        } 
        return sFinal;   
    }

    static String axioms31A(int[] a, int n, HashMap<String, Integer> b){
        String sFinal = "";
        int i = 0;
        for(int j = 1; j < n-1; j++){

            String sLine = "-";
            String p1 = "Peg(" + (a[0]) + "," + j + ")";
            String p2 = "Peg(" + (a[0]) + "," + (j+1) + ")" ;
            sLine += b.get(p1) + " " + b.get(p2);

            Iterator bI = b.entrySet().iterator();   
            while (bI.hasNext()) { 
                Map.Entry mapElement = (Map.Entry)bI.next(); 
                String b1 = (String) mapElement.getKey();

                if(b1.charAt(0) == 'J' && (b1.charAt(11) == ((char)(j+'0')))  &&
                    ((b1.charAt(5) == ((char)((a[0])+'0'))) || (b1.charAt(7) == ((char)((a[0])+'0')))) ){
                    sLine += " " + b.get(b1);

                }
            } 
        
            sFinal += sLine + "\n";
        }

        return sFinal;   
    }

    static String axioms31B(int[] a, int n, HashMap<String, Integer> b){
        String sFinal = "";
        int i = 0;
        for(int j = 1; j < n-1; j++){

            String sLine = "";
            String p1 = "Peg(" + (a[0]) + "," + j + ")";
            String p2 = "Peg(" + (a[0]) + "," + (j+1) + ")" ;
            sLine += b.get(p1) + " -" + b.get(p2);

            Iterator bI = b.entrySet().iterator(); 
        
            while (bI.hasNext()) { 
                Map.Entry mapElement = (Map.Entry)bI.next(); 
                String b1 = (String) mapElement.getKey();

                if(b1.charAt(0) == 'J' && (b1.charAt(11) == ((char)(j+'0')))  &&
                    (b1.charAt(9) == ((char)((a[0])+'0'))) ){
                    sLine += " " + b.get(b1);
                }
            } 
        
            sFinal += sLine + "\n";
        }

        return sFinal;   
    }

    static String axioms41A(int[] a, int n, HashMap<String, Integer> b){
        String sFinal = "";
        String[] jumps = new String[n-2];
        
        for(int z = 1; z < n-1; z++){
            String j = "Jump(";
            
            for(int i = 0; i < a.length; i++){
                j += a[i] + ",";
            }
            j += z +")";
            jumps[z-1] = j;
        }

        for(int i = 0; i < jumps.length; i++){

            Iterator bI = b.entrySet().iterator(); 
            while (bI.hasNext()) { 
                String sLine = "-" + b.get(jumps[i]);
                Map.Entry mapElement = (Map.Entry)bI.next(); 
                String b1 = (String) mapElement.getKey();

                if( (b1.charAt(0) == 'J') && (b1.charAt(11) == (jumps[i].charAt(11))) && 
                    !(jumps[i].equals(b1)) ){
                    sLine += " -" + b.get(b1);
                    sFinal +=sLine + "\n";

                    }
            }
        }

        return sFinal;   
    }

    static String axioms411A(int[][] a, int n, HashMap<String, Integer> b){
        String sFinal = ""; 

        String s = "";
        for(int i = 0; i < a.length; i++){
         s += axioms41A(a[i],n,b);
        }
        String[] s1 = s.split("\n");
        ArrayList<String> holder = new ArrayList<String>();
        for(int i = 0; i < s1.length; i++){
            String[] s2 = s1[i].split(" ");
            //get each atom from each 4A axiom
            String one = s2[0].replace("-", "").trim();
            String two = s2[1].replace("-", "").trim();
            String three = one + " " + two;
            String four = two + " " + one;
            if(   !(holder.contains(three) && holder.contains(four))  ){
                holder.add(three);
                holder.add(four);
                sFinal += s1[i] + "\n";

            }

        }
        return sFinal;

    }

    static String axioms51(int n, int h, HashMap<String, Integer> b){
        String sFinal = "";

        for(int i = 0; i < n; i++){
            String s = "Peg("+ (i+1) + ",1)";
            if((i+1) == h){
                sFinal+= "-"+ b.get(s) +"\n";
            }
            else{
                sFinal+= b.get(s) +"\n";
            }  
        }

        return sFinal;
    }



    static String axioms61A(int n, HashMap<String, Integer> b){
        String sFinal = "";

        for(int i = 0; i < n; i++){
            String s = "Peg("+ (i+1) + "," +(n-1) +")";
            sFinal+= b.get(s) +" ";  
        }

        sFinal = sFinal.trim() + "\n";
        return sFinal;
    }

    static String axioms61B(int n, HashMap<String, Integer> b){
        String sFinal = "";

        for(int i = 0; i < n; i++){
            String b1 = "Peg("+ (i+1) + "," +(n-1) +")";
            for(int j = i+1; j < n ; j++){
                String b2 =  "Peg("+ (j+1) + "," +(n-1) +")";
                String s = "-" + b.get(b1) + " -" + b.get(b2);
                sFinal += s + "\n";
            }
 
        }

        return sFinal;
    }

    static void allAxioms(int[][] a, int n, int h, HashMap<Integer, String> b ,HashMap<String, Integer> b2){
        String c = "";
        //Axioms 1
        for(int i = 0; i < a.length; i++){
            c += axioms11(a[i], n, b2);
        }
        //Axioms 2
        for(int i = 0; i < a.length; i++){
            c += axioms21(a[i], n, b2);
        }
        //Axioms 3
        for(int i = 0; i < a.length; i++){
            if(i % 2 == 0){
                c += axioms31A(a[i], n, b2);
            }
        }

        for(int i = 0; i < a.length; i++){
            if(i % 2 == 0){
                c += axioms31B(a[i], n, b2);
            }
        }
        //Axioms 4
        c += axioms411A(a,n,b2);
        //Axioms 5
        c += axioms51(n,h,b2);
        //Axioms 6
        c += axioms61A(n,b2);
        c += axioms61B(n,b2);
        c += "0\n";

        for(int i =1; i < b.size()+1; i++){
            if(i == b.size()){
                c += (i + " " + b.get(i));
            }
            else{
                c += (i + " " + b.get(i)) + "\n";
            }
            
        }


        System.out.println(c);
    }

    static String allAxioms1(int[][] a, int n, int h, HashMap<Integer, String> b ,HashMap<String, Integer> b2){
        String c = "";
        //Axioms 1
        for(int i = 0; i < a.length; i++){
            c += axioms11(a[i], n, b2);
        }
        //Axioms 2
        for(int i = 0; i < a.length; i++){
            c += axioms21(a[i], n, b2);
        }
        //Axioms 3
        for(int i = 0; i < a.length; i++){
            if(i % 2 == 0){
                c += axioms31A(a[i], n, b2);
            }
        }

        for(int i = 0; i < a.length; i++){
            if(i % 2 == 0){
                c += axioms31B(a[i], n, b2);
            }
        }
        //Axioms 4
        c += axioms411A(a,n,b2);

        //Axioms 5
        c += axioms51(n,h,b2);
        //Axioms 6
        c += axioms61A(n,b2);
        c += axioms61B(n,b2);
        c += "0\n";
        //Key
        for(int i =1; i < b.size()+1; i++){
            if(i == b.size()){
                c += (i + " " + b.get(i));
            }
            else{
                c += (i + " " + b.get(i)) + "\n";
            }
            
        }


        return c;
    }


    static boolean findValue(String s, HashMap<String, Boolean> b){

        String atom = s.replace("-","").trim();
        String sign = s.replace(atom,"").trim();
        Boolean bool = b.get(atom);
        Boolean bool2 = true;
        Boolean bool3 = true;
        if(bool == null){
            if(sign.equals("")){
                bool2 = true;
            }
            else if(sign.equals("-")){
                bool2 = false;
            }
        }
        else if( (bool!=null) && sign.equals("-")){
            bool2 = bool;
            bool2 = !bool2;
        }

        if(sign.equals("-")){
            bool3 = !bool3;
        }
        return bool3;
    }

    static boolean easyCase1(ArrayList<String> cs, HashMap<String, Boolean> b){
       boolean truther = false;
        for(int i = 0; i < cs.size(); i++){
            if(!cs.get(i).contains(" ")){
                String atom = cs.get(i).replace("-","").trim();

                Boolean v = findValue(cs.get(i), b);

                propagate(cs, b, atom, v);
                truther = true;
            }
        }
        return truther;
    }

    static boolean easyCase1Copy(ArrayList<String> cs, HashMap<String, Boolean> b){
       boolean truther = false;
        for(int i = 0; i < cs.size(); i++){
            if(!cs.get(i).contains(" ")){
                truther = true;
            }
        }
        return truther;
    }

    static boolean easyCase2(ArrayList<String> cs, HashMap<String, Boolean> b, String[] as){
        boolean truther = false;

        //for each atom in list of atoms
        for(int i = 0; i < as.length; i++){
            //check if A is in each C in CS with sign V
            //1\n
            String a1 = as[i];
            String sign = "-";
            String a2 = sign + a1;

            int n = 0;
            int m = 0;

            for(int j = 0; j < cs.size(); j++){
                String[] c = cs.get(j).split(" ");
                for(int k = 0; k < c.length ; k++ ){
                    
                    //if all are positive, assign positive
                    if(c[k].equals(a1)){
                        n++;
                    }
                    //if all are negative assign negative
                    if(c[k].equals(a2)){
                        m++;
                    }
                }
            }
            if((n > 0) && (m == 0)){
                propagate(cs, b, a1, true);
                truther = true;
            }
            else if((m > 0) && (n == 0)){
                propagate(cs, b, a1, false);
                truther = true;
            }
            
        }
        return truther;
    }

    static boolean easyCase2Copy(ArrayList<String> cs, HashMap<String, Boolean> b, String[] as){
        boolean truther = false;

        //for each atom in list of atoms
        for(int i = 0; i < as.length; i++){
            //check if A is in each C in CS with sign V
            //1\n
            String a1 = as[i];
            String sign = "-";
            String a2 = sign + a1;

            int n = 0;
            int m = 0;

            for(int j = 0; j < cs.size(); j++){
                String[] c = cs.get(j).split(" ");
                for(int k = 0; k < c.length ; k++ ){
                    
                    //if all are positive, assign positive
                    if(c[k].equals(a1)){
                        n++;
                    }
                    //if all are negative assign negative
                    if(c[k].equals(a2)){
                        m++;
                    }
                }
            }
            if((n > 0) && (m == 0)){
                truther = true;
            }
            else if((m > 0) && (n == 0)){
                truther = true;
            }
            
        }
        return truther;
    }



    static String returnValues(HashMap<String, Boolean> b){
        String a = "";
        for(int i = 0; i< b.size()-1; i++){
            String c = Integer.toString((i+1));
            String v = "";
            //b.get(c);
            if(b.get(c) != null){
                if(b.get(c) == true){
                    v = "T";
                }
                if(b.get(c) == false){
                    v = "F";
                }

            }


            a += c + " " + v + "\n";
        }
        return a;
    }

    static boolean emptyClause(ArrayList<String> cs){
        boolean hasEmptyClause = false;
        for(int i = 0; i < cs.size(); i++){
            if(cs.get(i).trim().equals("")){
                hasEmptyClause = true;
                i = cs.size(); //end loop
            }
        }
        return hasEmptyClause; 
    }

    static Boolean noClauses(ArrayList<String> cs, HashMap<String, Boolean> b){
        Boolean a = false; 
        if(cs.size() == 0){
           a = true;
        }
        return a;
    }


    static Boolean easyCaseInCopy(ArrayList<String> cs, HashMap<String, Boolean> b, String[] a){
        boolean truther = false;

        ArrayList<String> csCopy = new ArrayList<String>(cs);
        HashMap<String, Boolean> bCopy = new HashMap<String, Boolean>(b);

        boolean truther1 = easyCase1Copy(csCopy, bCopy);
        boolean truther2 = easyCase2Copy(csCopy, bCopy, a);
        if((truther1 == true) || (truther2 == true)){
            truther = true;
        }
        return truther;
    }

    static void easyCase(ArrayList<String> cs, HashMap<String, Boolean> b, String[] a){
        easyCase1(cs, b);
        easyCase2(cs, b, a);

    }


    static void propagate(ArrayList<String> cs, HashMap<String, Boolean> b, String a, boolean v){
        String sign = "";
        String signOp = "-";  
        if(v == false){
            sign += "-";
        }

        if(b.containsKey(a)){
            b.replace(a,v);
            System.out.println("Replacing "+ a + " as "+ v);
        }
        if(!b.containsKey(a)){
            b.put(a,v);
            System.out.println("Adding "+ a + " as "+ v);
        }

        if(sign.equals("-")){
            signOp = "";

        }
        //for each C in CS
        for(int i = 0; i < cs.size(); i++){
            //Each literal of each clause in clauses
            String[] c = cs.get(i).split(" ");
            //Copy current clause
            String cNew = cs.get(i);
            for(int j = 0; j < c.length; j++){
               
                //remove c from CS
                if(c[j].equals((sign + a))){
                    //Remove a plus v from c in clauses
                    System.out.println("Removing clause: " + cs.get(i));
                    cs.remove(i);
                    i = i -1;
                    j = c.length -1;
                }

                //Delete literal from c
                
                else if(c[j].equals(signOp + a)){
                    cNew = cNew.replace(c[j],"").trim();
                    cs.set(i, cNew.trim()); 
                    j = c.length -1;

                }
                
            }

        }

    }


/*
    static void propagate(ArrayList<String> cs, HashMap<String, Boolean> b, String a, boolean v){
        b.put(a,v);
        
        String sign = "";
        String signOp = "-";
        if(v != true){
            sign = "-";
            signOp = "";
        }

        String aSign = a+sign; 
        String aSignOp = a+signOp;



        for(int i = 0; i < cs.size(); i++){
            String[] c = cs.get(i).split(" ");
            for(int j = 0; j < c.length; j++){
                if(aSign.equals(c[j])){
                    //remove c from cs
                    cs.remove(i);
                    i = i-1;
                    j = c.length-1;
                }
                else if(aSignOp.equals(c[j])){
                    //delete literal from c
                    cs.set(i, cs.get(i).replace(aSignOp, "").trim());
                    j = c.length-1;
                }
            }
        }


    }

*/
    static String dpll(ArrayList<String> cs, HashMap<String, Boolean> b, String[] a){
        String answer = "";
        

        if(cs.size() == 0){
            return returnValues(b);
        }
        /*
        if(emptyClause(cs)){
            answer = "FAIL";
            System.out.println(answer);
            return answer;
        }
        */
        while(emptyClause(cs)){
            answer = "FAIL";
            System.out.println(answer);
            return answer;
        }

        
        if(easyCaseInCopy(cs, b, a)){
            easyCase(cs,b,a);
            //answer= "FAIL"
        }
        
        /*
        while(easyCaseInCopy(cs, b, a) && (!answer.equals("FAIL")) ){
            easyCase(cs,b,a);
            if(emptyClause(cs) == true){
                answer = "FAIL";
                return answer;
            }
            //answer= "FAIL"
        }


        if(!easyCaseInCopy(cs, b, a)){
            //answer= "FAIL";
            System.out.println("No Easy Cases, Continue");
            //return answer;
        }
        */

        while( (cs.size() != 0) && (!(emptyClause(cs))) && easyCaseInCopy(cs,b,a)){
            easyCase(cs,b,a);

        }

        ArrayList<String> csCopy = new ArrayList<String>(cs);
        HashMap<String, Boolean> bCopy = new HashMap<String, Boolean>(b);

        String p = "";
        for(int i = 0; i < a.length; i++){
            if(!b.containsKey(a[i])){

                p = a[i];
                i = a.length;
            }
        }

        System.out.println("!!!!!!!!!!!HERE!!!!!!!!!!!!!!!!!!!");
        propagate(csCopy, bCopy, p, true);
        
        //Choose unbound atom

        answer = dpll(csCopy,bCopy,a);
        System.out.println("@@@@@@@@@@@@HERE@@@@@@@@@@@@@@@");
        System.out.println(answer);
        System.out.println("@@@@@@@@@@@@HERE@@@@@@@@@@@@@@@");
        System.exit(0);
        if(!answer.equals("FAIL")){
            return answer;
        }


        propagate(cs, b, p, false);

        return dpll(cs, b, a);

    } 

    public static void main(String[] args) {

    //Front End

        Scanner input = new Scanner(System.in);
        String firstLine = input.nextLine();
        String[] first = firstLine.split(" ",2); 
        
        int n = Integer.parseInt(first[0]);
        int h = Integer.parseInt(first[1]);


        ArrayList<String> lines = new ArrayList<String>();
        String jumpLine = input.nextLine();
        
        while(!jumpLine.isEmpty()){
            lines.add(jumpLine);
            jumpLine = input.nextLine();
        }

        int[][] triples = return2D(lines);

        HashMap<Integer, String> b1 = returnAxioms(triples, n);
        HashMap<String, Integer> b2 = returnAxiomsFlip(triples, n);


        //allAxioms(triples, n, h, b, b2);
        String s = allAxioms1(triples, n, h, b1, b2);
    

    //Middle Part    

        String[] s1 = s.split("\n0\n", 2);


        //String clauses = s1[0];
        //String keys = s1[0];
        String[] clause = s1[0].split("\n");
        String[] atom = s1[1].split("\n");

        String[] atoms = new String[atom.length];
        for(int i =0; i < atoms.length; i++){
            int j = i+1;
            atoms[i] = Integer.toString(j);
        }

        ArrayList<String> clauses = new ArrayList<String>();
        for(int i = 0; i < clause.length; i++){
            clauses.add(clause[i]);
        }

        HashMap<String, Boolean> b = new HashMap<String, Boolean>();

        //propagate(clauses, b, "17", false);



    
        
        /*
       for(int i = 0; i < atoms.length; i++){
            propagate(clauses, b ,atoms[i], true);
       }
       for(int i = 0; i < b.size(); i++){
        System.out.println((Integer.toString(i+1))+ " "+b.get(Integer.toString(i+1)));
       }


      // propagate(clauses, b ,atoms[0], true);
       for(int i = 0; i < clauses.size(); i++){
        System.out.println(clauses.get(i));
       }
        */
        //axioms511(n, h, b2, b);

       String dpll = dpll(clauses, b, atoms);
       System.out.println(dpll);



       Iterator it = b.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }

    System.out.println(b.get("2"));
   








    

    //Back End
        //Read Input, if something comes up as true, remember the number.
        //As number comes back again (after 0 occurs),
        // if the number has a corresponding true value, Print the instruction.  


        /*
        Scanner backReader = new Scanner(new File("a.txt"));
        while (backReader.hasNextLine()) {
           String line = backReader.nextLine();
           String[] line1 = line.split(" ", 2);
           if(line1[1] == "T"){
                //line1[0]; //<-- Store
           }
        }

    */



    }
}