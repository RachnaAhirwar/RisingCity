public class RBT {
	Node nodeRoot;
	
	RBT(){
		nodeRoot = null;
	}

	// Returns the node color
	public int getNodeColor(Node rbnode) {
		if(rbnode==null)
			return 1;
		else return rbnode.color;
	}

	// Sets the color of a node - red/ black
	public void setNodeColor(Node rbnode, int color) {
		if(rbnode==null)
			return;
		rbnode.color=color;
	}
	
	// Inserts a new node
	// If the same building number exists, it returns exception.
	// If building number is less than the root, calls - insertBST(nodeRoot.left,new_node);
	// If building number is more than the root, calls- insertBST(nodeRoot.right,new_node);
	public Node insertRBT(Node nodeRoot, Node new_node) throws MyException{
		if(nodeRoot==null)
			return new_node;
		if(new_node.b.buildingNum == nodeRoot.b.buildingNum) {
			throw new MyException("Same Building number exists:" + new_node.b.buildingNum);
		}
		if(new_node.b.buildingNum < nodeRoot.b.buildingNum) {
			nodeRoot.left=insertRBT(nodeRoot.left,new_node);
			nodeRoot.left.p=nodeRoot;
		}else{
			nodeRoot.right=insertRBT(nodeRoot.right,new_node);
			nodeRoot.right.p=nodeRoot;
		}
		return nodeRoot;
	}

    // Calls insertBST and fixInsertRBTree
    public void insertValue(Node rbnode) throws MyException {
    	nodeRoot= insertRBT(nodeRoot,rbnode);
    	fixInsertRBTree(rbnode);
    }

	// rotate node left
	public void rotateLeft(Node rbnode) {
		Node right_child=rbnode.right;
		rbnode.right=right_child.left;
		
		if(rbnode.right!=null) {
			rbnode.right.p=rbnode;
		}
		right_child.p=rbnode.p;
		if(rbnode.p==null)
			nodeRoot=right_child;
		else if(rbnode==rbnode.p.left)
			rbnode.p.left=right_child;
		else
			rbnode.p.right=right_child;
		right_child.left=rbnode;
		rbnode.p=right_child;
	}

	// Performs the left rotation on the input node	
	public void rotateRight(Node rbnode) {
		Node left_child=rbnode.left;
		rbnode.left=left_child.right;
		
		if(rbnode.left!=null) {
			rbnode.left.p=rbnode;
		}
		left_child.p=rbnode.p;
		if(rbnode.p==null)
			nodeRoot=left_child;
		else if(rbnode==rbnode.p.left)
			rbnode.p.left=left_child;
		else
			rbnode.p.right=left_child;
		left_child.right=rbnode;
		rbnode.p=left_child;
	}

	// Fix the red black tree on node insertion
	public void fixInsertRBTree(Node rbnode) {
		Node p = null;
	    Node gp = null;
	    p = rbnode.p;
	    if(p!=null)
	    	gp = p.p;
	    
	    while (gp!=null&&rbnode != nodeRoot && getNodeColor(rbnode) == 0 && getNodeColor(rbnode.p) == 0) {
	    	p = rbnode.p;
	        gp = p.p;
	        if(gp!=null) {
	        if (p == gp.left) {
	            Node uncle = gp.right;
	            if (getNodeColor(uncle) == 0) {
	                setNodeColor(uncle, 1);
	                setNodeColor(p, 1);
	                setNodeColor(gp, 0);
	                rbnode = gp;
	            } else {
	                if (rbnode == p.right) {
	                    rotateLeft(p);
	                    rbnode = p;
	                    p = rbnode.p;
	                }
	                rotateRight(gp);
	                {
	                	int temp = p.color;
	                	p.color=gp.color;
	                	gp.color=temp;
	                }
	                rbnode = p;
	            }
	        } else {
	            Node uncle = gp.left;
	            if (getNodeColor(uncle) == 0) {
	                setNodeColor(uncle, 1);
	                setNodeColor(p, 1);
	                setNodeColor(gp, 0);
	                rbnode = gp;
	            } else {
	                if (rbnode == p.left) {
	                    rotateRight(p);
	                    rbnode = p;
	                    p = rbnode.p;
	                }
	                rotateLeft(gp);
	                {
	                	int temp = p.color;
	                	p.color=gp.color;
	                	gp.color=temp;
	                }
	                rbnode = p;
	            }
	        }
	        }
	    }
	    setNodeColor(nodeRoot, 0);
	}

	// Fix the red black tree on node deletion
	public void fixDeleteRBTree(Node rbnode) {
	    if (rbnode == null)
	        return;

	    if (rbnode == nodeRoot) {
	        nodeRoot = null;
	        return;
	    }

	    if (getNodeColor(rbnode) == 0 || getNodeColor(rbnode.left) == 0 || getNodeColor(rbnode.right) == 0) {
	        Node child = rbnode.left != null ? rbnode.left : rbnode.right;

	        if (rbnode == rbnode.p.left) {
	            rbnode.p.left = child;
	            if (child != null)
	                child.p = rbnode.p;
	            setNodeColor(child, 1);
	            //delete (node);
	            rbnode=null;
	        } else {
	            rbnode.p.right = child;
	            if (child != null)
	                child.p = rbnode.p;
	            setNodeColor(child, 1);
	            rbnode=null;
	        }
	    } else {
	        Node sibling = null;
	        Node p = null;
	        Node ptr = rbnode;
	        setNodeColor(ptr, 2);
	        while (ptr != nodeRoot && getNodeColor(ptr) == 2) {
	            p = ptr.p;
	            if (ptr == p.left) {
	                sibling = p.right;
	                if( sibling == null )
	                	break ;
	                if (getNodeColor(sibling) == 0) {
	                    setNodeColor(sibling, 1);
	                    setNodeColor(p, 0);
	                    rotateLeft(p);
	                } else {
	                    if (getNodeColor(sibling.left) == 1 && getNodeColor(sibling.right) == 1) {
	                        setNodeColor(sibling, 0);
	                        if(getNodeColor(p) == 0)
	                            setNodeColor(p, 1);
	                        else
	                            setNodeColor(p, 2);
	                        ptr = p;
	                    } else {
	                        if (getNodeColor(sibling.right) == 1) {
	                            setNodeColor(sibling.left, 1);
	                            setNodeColor(sibling, 0);
	                            rotateRight(sibling);
	                            sibling = p.right;
	                        }
	                        setNodeColor(sibling, p.color);
	                        setNodeColor(p, 1);
	                        setNodeColor(sibling.right, 1);
	                        rotateLeft(p);
	                        break;
	                    }
	                }
	            } else {
	            	
	                sibling = p.left;
	                
	                if( sibling == null )
	                	break ;
	              //update sibling check null
	                if ( sibling != null && getNodeColor(sibling) == 0) {
	                    setNodeColor(sibling, 1);
	                    setNodeColor(p, 0);
	                    rotateRight(p);
	                } else {
	                	//update sibling check null
	                    if ( sibling!= null && getNodeColor(sibling.left) == 1 && getNodeColor(sibling.right) == 1) {
	                        setNodeColor(sibling, 0);
	                        if (getNodeColor(p) == 0)
	                            setNodeColor(p, 1);
	                        else
	                            setNodeColor(p, 2);
	                        ptr = p;
	                    } else {
	                    	//update sibling check null
	                        if (sibling != null && getNodeColor(sibling.left) == 1) {
	                            setNodeColor(sibling.right, 1);
	                            setNodeColor(sibling, 0);
	                            rotateLeft(sibling);
	                            sibling = p.left;
	                        }
	                        setNodeColor(sibling, p.color);
	                        setNodeColor(p, 1);
	                        setNodeColor(sibling.left, 1);
	                        rotateRight(p);
	                        break;
	                    }
	                }
	            }
	        }
	        if (rbnode == rbnode.p.left)
	            rbnode.p.left = null;
	        else
	            rbnode.p.right = null;
	        	rbnode=null;
	        	setNodeColor(nodeRoot, 1);
	    }
	}

	// Deletes a building number from the tree
	public Node deleteRBT(Node nodeRoot, int buildingNum){
		if(nodeRoot==null)
			return nodeRoot;
		if(buildingNum<nodeRoot.b.buildingNum)
			return deleteRBT(nodeRoot.left,buildingNum);
		if(buildingNum>nodeRoot.b.buildingNum)
			return deleteRBT(nodeRoot.right,buildingNum);
		if(nodeRoot.left==null||nodeRoot.right==null)
			return nodeRoot;
		Node temp=minValNode(nodeRoot.right);
		nodeRoot.b=temp.b;
		
		return deleteRBT(nodeRoot.right,temp.b.buildingNum);
	}
	
    // Calls deleteBST and fixDeleteRBTree
    public void deleteValue(int buildingNum) {
    	Node node=deleteRBT(nodeRoot,buildingNum);
    	fixDeleteRBTree(node);
    }
	
	// Returns the node with the minimum value
	public Node minValNode(Node rbnode) {
		Node ptr=rbnode;
		while (ptr.left != null)
	        ptr = ptr.left;

	    return ptr;
	}

	// Returns the node with the maximum value
	public Node maxValueNode(Node rbnode) {
		Node ptr=rbnode;
		while (ptr.right != null)
	        ptr = ptr.right;

	    return ptr;
	}

	// Returns the height of black nodes
	public int getBlackHeight(Node rbnode) {
		int blackheight = 0;
	    while (rbnode != null) {
	        if (getNodeColor(rbnode) == 1)
	            blackheight++;
	        rbnode = rbnode.left;
	    }
	    return blackheight;
	}
	
	// Finds the given input Node
	public Node find(int buildingNum,Node rbnode) {
		if(rbnode==null)
			return null;
		if(buildingNum<rbnode.b.buildingNum)
			return find(buildingNum,rbnode.left);
		else if(buildingNum>rbnode.b.buildingNum)
			return find(buildingNum,rbnode.right);
		else
			return rbnode;
	}
	
	// Prints the building information as follows for a given building number as an input: 
 	// (building number, executed time, total time for execution)
	// If a building number does not exist, it prints (0,0,0)
	public void printBuilding(int buildingNum) {
		Node ret = find(buildingNum,nodeRoot);
		if(ret==null)
			System.out.println("(0,0,0)");
		else
			System.out.println("("+ret.b.buildingNum+","+ret.b.executed_time+","+ret.b.total_time+")");
	}
	
	// Prints all the buildings having value between buildingNum1 and buildingNum2 inclusive
	public String printBuilding(Node rbnode,int buildingNum1, int buildingNum2) {
		if(rbnode==null)
		{		
			return "";
		}
			
		String s1="";
		if(buildingNum1 < rbnode.b.buildingNum)
			s1 = printBuilding(rbnode.left,buildingNum1,buildingNum2);
		if(buildingNum1<=rbnode.b.buildingNum&&buildingNum2>=rbnode.b.buildingNum)
			s1= s1 +"("+rbnode.b.buildingNum+","+rbnode.b.executed_time+","+rbnode.b.total_time+"),";
		if(buildingNum2>rbnode.b.buildingNum)
			s1=s1+printBuilding(rbnode.right,buildingNum1,buildingNum2);
		return s1;
	}
}

// Red black tree Node class
class Node{
	building b;
	Node left,right,p;
	int color;
	Node(building b){
		this.b=b;
		left=right=p=null;
		color= 0;
	}
}
@SuppressWarnings("serial")
class MyException extends Exception{
	public MyException(String s) {
		super(s);
	}
}

// Red black tree node Color class
class color{
	public static final Integer RED=0;
	public static final Integer BLACK=0;
	public static final Integer DBLACK=0;
}
    

    
