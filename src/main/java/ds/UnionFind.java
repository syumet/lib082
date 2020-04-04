package ds;

import java.util.Arrays;

public class UnionFind<E> {

	int[] parent;
	int count;
	
	public UnionFind(int n) {
		parent = new int[n];
		Arrays.fill(parent, -1);
		count = n;
	}
	
    public void union(int i, int j) {  
        if (-parent[i] < -parent[j]) {
        	int tmp = i;
        	i = j;
        	j = tmp;
        }
        parent[i] += parent[j];
        parent[j] = i;
        count--;
    }
    
	public int find (int i) {  
    	if (parent[i] < 0)
    		return i;
    	else {
    		parent[i] = find(parent[i]);
    		return parent[i]; 
    	} 
    }

    public int getCount() {
		return count;
	}

	public int[] getParent() {
		return parent;
	}
}
