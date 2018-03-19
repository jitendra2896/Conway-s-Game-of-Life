//Conway's Game of life brute force implementation in Java
//Name: Jitendra Tiwari

class Conway {
	Input input;
	public Conway(Input input){
		this.input = input;
	}
	public int[][] simulate(int[][] a,int n,int totalGen,int gameNo){
		int[][] temp = new int[n][n];
		int totalNeighbours = 0;
		
		for(int i = 0;i<totalGen;i++){
			for(int j = 0;j<n;j++){
				for(int k = 0;k<n;k++){
					
					totalNeighbours = 0;
					if(j-1 >=0 && k-1>= 0 && a[j-1][k-1] == 1)
						totalNeighbours++;
					if(j-1 >=0 && a[j-1][k] == 1)
						totalNeighbours++;
					if(j-1 >=0 && k+1 < n && a[j-1][k+1] == 1)
						totalNeighbours++;
					if(k-1 >= 0 &&a[j][k-1] == 1)
						totalNeighbours++;
					if(k+1 < n && a[j][k+1] == 1)
						totalNeighbours++;
					if(j+1 < n && k-1 >= 0 && a[j+1][k-1] == 1)
						totalNeighbours++;
					if(j+1 < n && a[j+1][k] == 1)
						totalNeighbours++;
					if(j+1 < n && k+1 < n && a[j+1][k+1] == 1)
						totalNeighbours++;
					
					
					if(a[j][k] == 1 && totalNeighbours <= 1)
						temp[j][k] = 0;
					if(a[j][k] == 1 && totalNeighbours > 3)
						temp[j][k] = 0;
					if(a[j][k] == 0 && totalNeighbours == 3)
						temp[j][k] = 1;
					if(a[j][k] == 1 && (totalNeighbours == 2 || totalNeighbours == 3))
						temp[j][k] = 1;
				}
			}
			
			for(int ii = 0;ii<n;ii++){
				for(int j = 0;j<n;j++){
					a[ii][j] = temp[ii][j];
				}
			}
		}
		
		return a;
	}
	
}
