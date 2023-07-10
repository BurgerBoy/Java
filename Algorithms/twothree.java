class Node {
	    String guide;
	    int value;
	}

	class InternalNode extends Node {
	    Node child0, child1, child2;
	}

	class LeafNode extends Node {
	}

	class TwoThreeTree {
	   Node root;
	   int height;

	   TwoThreeTree() {
	      root = null;
	      height = -1;
	   }
	}

	class WorkSpace {
	// this class is used to hold return values for the recursive doInsert
	// routine (see below)

	   Node newNode;
	   int offset;
	   boolean guideChanged;
	   Node[] scratch;
	}

public class twothree{
	

	static void insert(String key, int value, TwoThreeTree tree) {
	   // insert a key value pair into tree (overwrite existsing value
	   // if key is already present)

	  int h = tree.height;

	  if (h == -1) {
		LeafNode newLeaf = new LeafNode();
	    newLeaf.guide = key;
	    newLeaf.value = value;
	    tree.root = newLeaf; 
	    tree.height = 0;
      }
      
      else {
         WorkSpace ws = doInsert(key, value, tree.root, h);

         if (ws != null && ws.newNode != null) {
         // create a new root

            InternalNode newRoot = new InternalNode();
            if (ws.offset == 0) {
               newRoot.child0 = ws.newNode; 
               newRoot.child1 = tree.root;
            }
            else {
               newRoot.child0 = tree.root; 
               newRoot.child1 = ws.newNode;
            }
            resetGuide(newRoot);
            tree.root = newRoot;
            tree.height = h+1;
         }
      }
      

   }


   static WorkSpace doInsert(String key, int value, Node p, int h) {
   // auxiliary recursive routine for insert

      if (h == 0) {
         // we're at the leaf level, so compare and 
         // either update value or insert new leaf

         LeafNode leaf = (LeafNode) p; //downcast
         int cmp = key.compareTo(leaf.guide);

         if (cmp == 0) {
            leaf.value = value; 
            return null;
         }

         // create new leaf node and insert into tree
         LeafNode newLeaf = new LeafNode();
         newLeaf.guide = key; 
         newLeaf.value = value;

         int offset = (cmp < 0) ? 0 : 1;
         // offset == 0 => newLeaf inserted as left sibling
         // offset == 1 => newLeaf inserted as right sibling

         WorkSpace ws = new WorkSpace();
         ws.newNode = newLeaf;
         ws.offset = offset;
         ws.scratch = new Node[4];

         return ws;
      }
      else {
         InternalNode q = (InternalNode) p; // downcast
         int pos;
         WorkSpace ws;

         if (key.compareTo(q.child0.guide) <= 0) {
            pos = 0; 
            ws = doInsert(key, value, q.child0, h-1);
         }
         else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
            pos = 1;
            ws = doInsert(key, value, q.child1, h-1);
         }
         else {
            pos = 2; 
            ws = doInsert(key, value, q.child2, h-1);
         }

         if (ws != null) {
            if (ws.newNode != null) {
               // make ws.newNode child # pos + ws.offset of q

               int sz = copyOutChildren(q, ws.scratch);
               insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);
               if (sz == 2) {
                  ws.newNode = null;
                  ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
               }
               else {
                  ws.newNode = new InternalNode();
                  ws.offset = 1;
                  resetChildren(q, ws.scratch, 0, 2);
                  resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
               }
            }
            else if (ws.guideChanged) {
               ws.guideChanged = resetGuide(q);
            }
         }

         return ws;
      }
   }


	static int copyOutChildren(InternalNode q, Node[] x) {
   // copy children of q into x, and return # of children

      int sz = 2;
      x[0] = q.child0; x[1] = q.child1;
      if (q.child2 != null) {
         x[2] = q.child2; 
         sz = 3;
      }
      return sz;
	}


	static void insertNode(Node[] x, Node p, int sz, int pos) {
	   // insert p in x[0..sz) at position pos,
	   // moving existing extries to the right

	      for (int i = sz; i > pos; i--){
	      	x[i] = x[i-1];
	      }

	      x[pos] = p;
	}

	static boolean resetGuide(InternalNode q) {
   // reset q.guide, and return true if it changes.

      String oldGuide = q.guide;
      if (q.child2 != null)
         q.guide = q.child2.guide;
      else
         q.guide = q.child1.guide;

      return q.guide != oldGuide;
   }


   static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
   // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
   // also resets guide, and returns the result of that

      q.child0 = x[pos]; 
      q.child1 = x[pos+1];

      if (sz == 3) 
         q.child2 = x[pos+2];
      else
         q.child2 = null;

      return resetGuide(q);
   }


   static void printAll(Node p, int h){
   		if(h==0){
   			//System.out.println(p.guide + " " + String.valueOf(p.value));
   		}
   		else{
   			InternalNode newInternal = (InternalNode) p;
   			printAll(newInternal.child0, h-1);
   			printAll(newInternal.child1, h-1);
   			if(newInternal.child2 != null){
   				printAll(newInternal.child2, h-1);
   			}
   		}
   		
   }


   static void printLE(Node p, String x, int h){
   		if(h==0){
   			if(x.compareTo(p.guide)>= 0){
   				//System.out.println(p.guide + " " + String.valueOf(p.value));
   			}
   		}

   		else{
   			InternalNode newInternal = (InternalNode) p;
   			if(x.compareTo(newInternal.child0.guide) <= 0){
   				printLE(newInternal.child0, x, h-1);
   			}
   			else if((newInternal.child2 == null) || (x.compareTo(newInternal.child1.guide) <= 0)){
   				printAll(newInternal.child0, h-1);
   				printLE(newInternal.child1, x, h-1);
   			}
   			else{
   				printAll(newInternal.child0, h-1);
   				printAll(newInternal.child1, h-1);
   				printLE(newInternal.child2, x, h-1);
   			}
   		}

   }


   static void printGE(Node p, String x, int h){
	    if (h==0){
	    	if(x.compareTo(p.guide)<= 0){
   				//System.out.println(p.guide + " " + String.valueOf(p.value));
   			}
	    } 

	    else{
	    	InternalNode newInternal = (InternalNode) p;

		    if (x.compareTo(newInternal.child0.guide) <= 0){	
		    	printGE(newInternal.child0, x, h-1);
		    	printGE(newInternal.child1, x, h-1);
		    	if(newInternal.child2 != null){
					printGE(newInternal.child2, x, h-1);
		    	}
		    }

		    else if(x.compareTo(newInternal.child1.guide) <= 0){
		    	printGE(newInternal.child1, x, h-1);
		    	if( newInternal.child2 != null ) {
		    		printGE(newInternal.child2, x, h-1);
		    	}
		    }

		    else if( (newInternal.child2 != null) && (x.compareTo(newInternal.child2.guide) <= 0) ){
		    	printGE(newInternal.child2,x, h-1);
		    }
	    }
	
	}


	static String search(Node p, String x , int h){
		
		String s = "-1";
		if (h==0){
			if (x.compareTo(p.guide) == 0){
				s = Integer.toString(p.value);
				return s;
			}
		}

		else{
			
			InternalNode newInternal = (InternalNode) p;
			
			if(x.compareTo(newInternal.child0.guide) <= 0){

				if(x.compareTo(newInternal.child0.guide) == 0){
					s = Integer.toString(newInternal.child0.value);
					return s;

				}
				else{
					search(newInternal.child0, x, h-1);
				}

			}

			else if((newInternal.child2 == null) || (x.compareTo(newInternal.child1.guide) <= 0) ) {
				
				if(x.compareTo(newInternal.child1.guide) == 0){
					s = Integer.toString(newInternal.child1.value);
					return s;
				}
				else{
					search(newInternal.child1, x, h-1);
				}
				
			}

			else if(x.compareTo(newInternal.child2.guide) <= 0){
				
				if(x.compareTo(newInternal.child2.guide) == 0){
					s = Integer.toString(newInternal.child2.value);
					return s;
				}
				else{
					search(newInternal.child2, x, h-1);
				}

			}
		}
		return s;

	}


	static void addRange(Node p, String x, String y, int h, int d){
		if(h==0){
			if( (x.compareTo(p.guide) <= 0) && (y.compareTo(p.guide) >= 0) ){
				
				p.value += d;
				//System.out.println(p.guide + " " + p.value);
			}
		}
		else{
			InternalNode newInternal = (InternalNode) p;
			if(y.compareTo(newInternal.child0.guide) <= 0){
				addRange(newInternal.child0, x, y, h-1, d);
			}
			else if( (newInternal.child2 == null) || (y.compareTo(newInternal.child1.guide) <=0) ){
				if(x.compareTo(newInternal.child0.guide) <= 0){
					newInternal.child0.value += d;
					newInternal.child1.value += d;
					printGE(newInternal.child0,x,h-1);

					printLE(newInternal.child1, y, h-1);
				}
				else{
					addRange(newInternal.child1, x, y, h-1, d);
				}
			}
			else{
				if(x.compareTo(newInternal.child0.guide) <= 0){
					newInternal.child0.value += d;
					newInternal.child1.value += d;
					newInternal.child2.value += d;
					printGE(newInternal.child0, x, h-1);
					printAll(newInternal.child1, h-1);
					printLE(newInternal.child2, y, h-1);

				}
				else if(x.compareTo(newInternal.child1.guide) <= 0){
					newInternal.child1.value += d;
					newInternal.child2.value += d;
					printGE(newInternal.child1, x, h-1);
					printLE(newInternal.child2, y, h-1);
				}
				else{
					addRange(newInternal.child2, x, y, h-1, d);
				}
			}
		}

	}


	static String chooser(String[] base, TwoThreeTree tree){
		
		String t = "";
		int k;
		if(base.length != 2){
			switch(base[0]){
				case "1":
					// Insert a planet with name base[1] and entrance fee k into the database
					k = Integer.parseInt(base[2]);
					insert( base[1], k ,tree);
					break;

				case "2":
					// Increase the entrance fee for all planets between base[1], base[2] by base[3]
					k = Integer.parseInt(base[3]);
					addRange(tree.root, base[1], base[2], tree.height, k);
					break;

			}

		}
		
		else{
			t = search(tree.root, base[1], tree.height);

		}

		return t;
	
	}

}	