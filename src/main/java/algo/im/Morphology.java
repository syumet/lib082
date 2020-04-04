package algo.im;

import ds.UnionFind;

public class Morphology<E> {
	
	public enum Connectivity {
		  FOUR,
		  EIGHT
	}
	
	private static final int[][] FOUR_NEIGHBORS = {{1,0},{-1,0},{0,1},{0,-1}};
	private static final int[][] EIGHT_NEIGHBORS = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};

	public int numOfComponents(double[][] I, Connectivity connectivity) throws IllegalArgumentException {
        int rows = I.length;  
        if (rows == 0) 
        	throw new IllegalArgumentException("Input is empty.");
        int cols = I[0].length; 
        
        int background = 0;
        for (int i = 0; i < rows; i++) {  
            for (int j = 0; j < cols; j++) {
            	if (I[i][j] == 0)
            		background++;
            }
        }
		
		UnionFind<E> uf = runUnionFind(I, connectivity);
		return uf.getCount() - background;
	}
	
	public int[][] label(double[][] I, Connectivity connectivity) {
        int rows = I.length;  
        if (rows == 0) 
        	throw new IllegalArgumentException("Input is empty.");
        int cols = I[0].length; 
        
        UnionFind<E> uf = runUnionFind(I, connectivity);
        int[] record = uf.getParent();
        
        int[][] L = new int[rows][cols];
        int groups = 0;
        int leader = -1;
        for (int i = 0; i < rows; i++) {  
            for (int j = 0; j < cols; j++) {
            	if (I[i][j] == 0)
            		L[i][i] = 0;
            	else {
            		int id = i * cols + j;
            		if (record[id] < 0) { // find a leader
            			groups++;
            			
            		}
            	}
            }       	
        }

        
        return L;
	}
	
	private UnionFind<E> runUnionFind(double[][] I, Connectivity connectivity) throws IllegalArgumentException {
		
        int rows = I.length;  
        int cols = I[0].length; 
        
        int[][] distance = (connectivity == Connectivity.FOUR) ? FOUR_NEIGHBORS : EIGHT_NEIGHBORS;
        
        UnionFind<E> uf = new UnionFind<E>(rows * cols);
        for (int i = 0; i < rows; i++) {  
            for (int j = 0; j < cols; j++) {
                if (I[i][j] > 0) {  
                    for (int[] d : distance) {
                        int x = i + d[0];
                        int y = j + d[1];
                        if (x >= 0 && x < rows && 
                            y >= 0 && y < cols && 
                            I[x][y] > 0) {  
                            int id1 = uf.find(i * cols + j);
                            int id2 = uf.find(x * cols + y);
                            if (id1 != id2)
                                uf.union(id1, id2);  
                        }  
                    }  
                }       
            }
        }
        return uf;
	}
}
