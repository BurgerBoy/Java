import java.util.*;
import java.lang.*;


class State{
	int state;
	//action -> map of states and probabilities
	HashMap<Integer, HashMap<Integer, Float>> actions;
	
	State(String a){
		setState(a);
		actions = new HashMap<Integer, HashMap<Integer, Float>>();
		fillActions(a);
	}

	void setState(String a){
		String[] a1 = a.split(" ");
		String[] s1 = a1[0].split(":");
		state = Integer.parseInt(s1[0]);
	}

	int getAction(String a){
		String[] a1 = a.split(" ");
		String[] ac = a1[0].split(":");
		return Integer.parseInt(ac[1]);
	}

	void fillActions(String a){
		String[] a1 = a.split(" ");
		HashMap<Integer, Float> probs = new HashMap<Integer,Float>();
		
		for(int i = 1; i < a1.length; i++){
			probs.put(Integer.parseInt(a1[i]), Float.parseFloat(a1[i+1]));
			i++;
		}
		actions.put(getAction(a),probs);
	}
}

public class blue{

	static int[] sToI(String a){
		
		String[] b = a.split(" ");
		int[] r = new int[b.length];

		for(int i =0; i < b.length; i++){
			r[i] = Integer.parseInt(b[i]);
		}

		return r;
	}

	static int chooseAction(State s, int[][] count, int[][] total, int m){
		int n = s.actions.size();
		int[] avg = new int[n];

		float[] savg = new float[n];
		float[] up = new float[n];
		float[] p = new float[n];

		int top = 0;
		int bottom = 1000;

		int c = 0;
		float norm = 0.0f;

		//Add another for loop for all states
		for(int i = 0; i < count[s.state].length; i++){
			//If any action in the state has count 0, return action.
			if(count[s.state][i] == 0){
				return i;
			}	
		}

		for(int i = 0; i < n-1; i++){
			avg[i] = total[s.state][i]/count[s.state][i];
			top = Math.max(top, total[s.state][i]);
			bottom = Math.min(bottom, total[s.state][i]);
		}

		for(int i = 0; i < n-1; i++){
			savg[i] = 0.25f + 0.75f*(avg[i]-bottom)/(top-bottom);
			c += count[s.state][i];
		}

		for(int i = 0; i < n-1; i++){
			up[i] = (float) Math.pow(savg[i],(c/m));
			norm += up[i];
		}

		for(int i = 0; i < n-1; i++){
			p[i]= (float) up[i]/norm;
		}

		int randomAction1 = randomAction(p);
		return randomAction1;
	}

	static int randomAction(float[] p){
		int k = p.length-1;
		float[] u = new float[p.length];
		u[0] = p[0];

		for(int i = 1; i < k; i++){
			u[i] = u[i-1] + p[i];
		}

		float x = (float) (Math.random());
		for(int i = 0; i < k-1; i++){
			if(x <u[i]){
				return i;
			}
		}
		return k;
	}

	static int nextState(HashMap<Integer, Float> a) {
		HashMap<Float,Integer> b = new HashMap<Float,Integer>();
		float[] a1 = new float[a.size()];
		int[] a2 = new int[a.size()];
		int i = 0;

		for (Map.Entry<Integer, Float> e : a.entrySet()) {
    		a1[i] = e.getValue();
    		a2[i] = e.getKey();
    		b.put(e.getValue(),e.getKey());
    		i++;
		}
		int ra = randomAction(a1);

		return a2[ra];
	}   

	static void getStates(HashMap<Integer, State> states, String thirdLine){
		String[] tl = thirdLine.split(" ");
        String[] tl0 = tl[0].split(":");

        if(states.containsKey(Integer.parseInt(tl0[0]))){
        	states.get(Integer.parseInt(tl0[0])).fillActions(thirdLine);
        }
        else{
        	State newState = new State(thirdLine);
        	states.put(Integer.parseInt(tl0[0]), newState);
        }
	}

	static void computeReward(int[][] total, int[][] rh, int reward){
		for(int i = 0; i < rh.length; i++){
			for(int j = 0; j < rh[i].length; j++){
				if(rh[i][j]==1){
					total[i][j] += reward;
				}
			}
		}
	}

	static void incrementCount(int[][] count, int[][] rh){
		for(int i = 0; i < rh.length; i++){
			for(int j = 0; j < rh[i].length; j++){

				if(rh[i][j]==1){
					count[i][j] += 1;
				}
			}
		}
	}

	static void tallyCount(int[][] count){
		String s = "Count:\n";
		for(int i = 0; i < count.length; i++){
			String s1 = "";
			for(int j =0; j< count[i].length; j++){
				s1 += "[" + i + ", " + j +"]="+count[i][j]+". ";
			}
			s1 +="\n";
			s += s1;
		}
		System.out.println(s);
	}

	static void tallyTotal(int[][] total){
		String s = "Total:\n";
		for(int i = 0; i < total.length; i++){
			String s1 = "";
			for(int j =0; j< total[i].length; j++){
				s1 += "[" + i + ", " + j +"]="+total[i][j]+". ";
			}
			s1 +="\n";
			s += s1;
		}
		System.out.println(s);
	}

	static void getBestActions(int[][] total, int[][] count){
		String s = "Best action: ";
		for(int i = 0; i < total.length; i++){
			float best = 0.0f;
			int iH = 0;
			int jH = 0;

			for(int j =0; j< total[i].length; j++){
				if(count[i][j] != 0){
					float current = (float) total[i][j]/count[i][j];
					if(current > best){
						best = current;
						iH = i;
						jH = j;
					}
				}
				
			}
			s += iH + ":" + jH +". ";
		}
		s += "\n";
		System.out.println(s);
	}

    public static void main(String[] args) {
        System.out.println("Hello, World!"); 
        Scanner input = new Scanner(System.in);
        String firstLine = input.nextLine();
        String secondLine = input.nextLine();
        
       //First Line: Base Information
        int[] fl = sToI(firstLine);

        int nts = fl[0];
        int ts = fl[1];
        int r = fl[2];
        int v = fl[3];
        int m = fl[4];

       //Second Line: State Rewards
        int[] sl = sToI(secondLine);
        LinkedHashMap<Integer, Integer> stateRewards = new LinkedHashMap<Integer,Integer>();

        for(int i= 0; i < sl.length; i++){
        	stateRewards.put(sl[i], sl[i+1]);
        	i++;
        }

        //Remaining lines, building states
        HashMap<Integer, State> states = new HashMap<Integer, State>();

        String thirdLine = input.nextLine();     
        int sMax = 0;
        int aMax = 0;
        HashMap<Integer, Integer> hh = new HashMap<Integer,Integer>();

        while(!thirdLine.isEmpty()){
        	String[] a = thirdLine.split(" ");
        	String[] a1= a[0].split(":");

        	if(!hh.containsKey(Integer.parseInt(a1[0]))){
        		hh.put(Integer.parseInt(a1[0]),Integer.parseInt(a1[1]));
        	}

        	else if(hh.containsKey(Integer.parseInt(a1[0]))){
        		hh.replace(Integer.parseInt(a1[0]),Integer.parseInt(a1[1]));
        	}
 	
        	sMax = Math.max(Integer.parseInt(a1[0]), sMax);
        	aMax = Math.max(Integer.parseInt(a1[1]), aMax);

			getStates(states,thirdLine);

			thirdLine = input.nextLine();		
        }

        int[][] count = new int[nts][aMax+1];
        int[][] total = new int[nts][aMax+1];
        
        for(int i =0; i < nts; i++){
        	count[i] = new int[hh.get(i)+1];
        	total[i] = new int[hh.get(i)+1];    	
        }
        for(int i =0; i < nts; i++){
        	System.out.println(count[i].length); 	
        }

        //Begin Rounds
 		for(int i = 0; i < r; i++){
 			int round = i+1;

 			int start = (int) ((Math.random() * (states.size() - 0)) + 0);
	 		State randState = states.get(start);
	 	
	 		int[][] rewardHolder = new int[nts][aMax+1];
	 		
	 		for(int ii =0; ii < nts; ii++){
	        	rewardHolder[ii] = new int[hh.get(ii)+1];	
	        }
	
	 		while(!stateRewards.containsKey(start)){
	 			//Choose Action
		 		int action = chooseAction(randState, count, total, m);
		 		
		 		while(!randState.actions.containsKey(action)){
		 			action = chooseAction(randState, count, total, m);
		 		}
		 		
		 		rewardHolder[randState.state][action] = 1;
		 		incrementCount(count, rewardHolder);

		 		start = nextState(randState.actions.get(action));
		 		randState = states.get(start);

	 		}
			
	 		computeReward(total, rewardHolder, stateRewards.get(start));
 			
 			if(round % v == 0){
 				System.out.println("After "+ round + " rounds");
 				tallyCount(count);
 				tallyTotal(total);
 				getBestActions(total,count);
 			}
 		}

    }
}