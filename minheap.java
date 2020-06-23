public class minheap {
	int capacity = 2000;
	int size = 0;
	building[] buildings;
	public minheap() {
		// make an array to represent heap nodes
        buildings = new building[capacity];
	}
    

    private int getParent(int indx) {
        return (indx - 1)/2;
    }
    private boolean hasLeftChild(int indx) {
    	return (2*indx + 1) < size;
    }
    private boolean hasRightChild(int indx) {
    	return (2*indx + 2) < size;
    }
    private int getLeftChild(int indx) {
        return 2*indx + 1;
    }
    private int getRightChild(int indx) {
        return 2*indx + 2;
    }

    // insert the new building data at the bottom of the heap and then heapify the tree upside till root
    void insert (building b) {
        size++;
        buildings[size-1] = b;
        UPheapify();
    }

    // Remove the root element, then move the last element of heap to root & then heapify the tree downside 
    building removetop() {
    	if(size == 0) throw new IllegalStateException();
    	building res = buildings[0];
        buildings[0] = buildings[size-1];
        size--;
        Downheapify();
        return res;
    }

    // Returns the top element of heap without removing it
    building seetop() {
        if(size == 0) throw new IllegalStateException();
        return buildings[0];
    }

    // Swaps two values
    void swap(int indx1, int indx2) {
	    building temp = buildings[indx1];
	    buildings[indx1] = buildings[indx2];
	    buildings[indx2] = temp;
    }

    // Heapifies from bottom to top
    void UPheapify() {
    	int indx = size - 1;
    	while(indx > 0) {
    		int parent_indx = getParent(indx);
    		if(buildings[indx].executed_time < buildings[parent_indx].executed_time) {
    			swap(indx, parent_indx);
    			indx = parent_indx;
    		}
    		else if(buildings[indx].executed_time == buildings[parent_indx].executed_time&&buildings[indx].buildingNum<buildings[parent_indx].buildingNum) {
    			swap(indx, parent_indx);
    			indx = parent_indx;
    		}else {
    			break;
    		}
    	}
    }

    // Heapifies from top to bottom
    void Downheapify() {
    	int indx = 0;
    	while(hasLeftChild(indx)) {
    		int shortChild = getLeftChild(indx);
    		if(hasRightChild(indx) && buildings[getRightChild(indx)].executed_time < buildings[shortChild].executed_time) {
    			shortChild = getRightChild(indx);
    		}
    		
    		else if(hasRightChild(indx) && buildings[getRightChild(indx)].executed_time == buildings[shortChild].executed_time&&buildings[getRightChild(indx)].buildingNum < buildings[shortChild].buildingNum) {
    			shortChild = getRightChild(indx);
    		}
    		if(buildings[indx].executed_time > buildings[shortChild].executed_time) {
    			swap(indx, shortChild);
    		}
    		else if(buildings[indx].executed_time == buildings[shortChild].executed_time&&buildings[indx].buildingNum > buildings[shortChild].buildingNum) {
    			swap(indx, shortChild);
    		}
    		else break;
    		indx = shortChild;
    	}
    }

    void execute(building b,Integer rem) {
    	b.executed_time=b.executed_time+rem;
    	Downheapify();
    	if(b.executed_time==b.total_time)
    		removetop();
    }

}

    
