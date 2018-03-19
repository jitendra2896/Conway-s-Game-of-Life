import java.io.*;
import java.util.*;

class Game {
	public static void main(String args[]){

		ArrayList<Integer> keys = new ArrayList<>();
		int[][] a = new int[20][20];
		Input input = new Input(args[0]);
		int games, generations,width,height,percentage;
		
		width = Integer.parseInt(args[3]);
		height = Integer.parseInt(args[4]);
		games = Integer.parseInt(args[1]);
		generations = Integer.parseInt(args[2]);
		boolean exists = input.exists();
		if(exists){
			Pairs pair = getSubMatrix(input);
			width = pair.x;
			height = pair.y;
			generations = getGenerations(input);
			getKeys(keys,input);
			System.out.println("File already exists: Overriding input values with files");
		}
		
		//Header
		if(!exists){
			input.writeToFile(Integer.toString(20)+"\t"+Integer.toString(20));
			input.writeToFile(Integer.toString(width)+"\t"+Integer.toString(height));
			input.writeToFile(Integer.toString(generations));
		}
		
		Conway con = new Conway(input);
		int gameNumber = 1;
		while(gameNumber<=games){
			
			keys.clear();
			percentage = Game.randomWithRange(10, 90);
			int s = Game.generateInput(a, width, height, percentage);
			System.out.println("***GAME "+gameNumber+"***");
			
			if(!keys.contains(s)){
				keys.add(s);
				input.writeToFile(Integer.toString(s));
				input.writeToFile(Game.getString(a,20));
				System.out.println("Key: "+s);
				System.out.println("Initial Matrix\n");
				for(int i = 0;i<20;i++){
					for(int j = 0;j<20;j++){
						System.out.print(a[i][j]+" ");
					}
					System.out.println();
				}
				con.simulate(a, 20, generations,gameNumber);
				input.writeToFile(getString(a,20));
				System.out.println("After simulation\n");
		
				for(int i = 0;i<20;i++){
					for(int j = 0;j<20;j++){
						System.out.print(a[i][j]+" ");
					}
					System.out.println();
				}
				gameNumber++;
			}
		}
		/*int[][] temp = new int[20][20];

		int key = generateInput(temp, 5, 4, 50);
		for(int i = 0;i<20;i++){
			for(int j = 0;j<20;j++){
				temp[i][j] = a[i][j];
			}
		}
		System.out.println("Initial Matrix");
		for(int i = 0;i<20;i++){
			for(int j = 0;j<20;j++){
				System.out.print(a[i][j]+"  ");
			}
			System.out.println();
		}
		generateMatrixWithKeys(a,186010176,5,4);
		System.out.println("Generated Matrix");
		for(int i = 0;i<20;i++){
			for(int j = 0;j<20;j++){
				System.out.print(a[i][j]+"  ");
			}
			System.out.println();
		}
		
		if(equal(a,temp))
			System.out.println("Array Equal");
		else
			System.out.println("Array Not equal");*/
	}
	
	private static int getGenerations(Input input) {
		File file = new File(input.getFileName());
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			br.readLine();
			String data = br.readLine();
			return Integer.parseInt(data);
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	private static Pairs getSubMatrix(Input input) {
		File file = new File(input.getFileName());
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			String data = br.readLine();
			String[] val = data.split("\\t");
			return new Pairs(Integer.parseInt(val[0]),Integer.parseInt(val[1]));
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private static int generateInput(int[][] a,int width,int height,int percentage){
		for(int i = 0;i<20;i++){
			for(int j = 0;j<20;j++){
				a[i][j] = 0;
			}
		}
		
		Pairs coords = getRadarsInitialCoordinates(width,height,20);
		System.out.println("Radar: "+coords.x+", "+coords.y);
		ArrayList<Integer> list = getUniqueRandomValues(percentage,coords,new Pairs(coords.x+width-1,coords.y+height-1));

		System.out.println("Size of list "+list.size());
		
		for(int i = 0;i<list.size();i++){
			Pairs p = Game.getComponents(list.get(i));
			a[p.x][p.y] = 1;
		}
		
		return generateKey(a,coords,width,height);
	}
	
	private static int generateKey(int[][] a,Pairs initCoords,int width,int height){
		String key = new String();
		int oneInitCoord = 0;
		
		String x = Integer.toBinaryString(initCoords.x);
		String y = Integer.toBinaryString(initCoords.y);
		System.out.println("X Length: "+x.length());
		System.out.println("Y Length: "+y.length());
		int len = x.length();
		for(int i = 0;i<4-len;i++){
			x = "0"+x;
		}
		len = y.length();
		for(int i = 0;i<4-len;i++){
			y = "0"+y;
		}
		

		System.out.println("X: "+x);
		System.out.println("Y: "+y);
		
		/*if(initCoords.x == 0)
			oneInitCoord = initCoords.y;
		else{
			oneInitCoord = Integer.parseInt(Integer.toString(initCoords.x)+Integer.toString(initCoords.y));
		}*/
		
		key = x+y;
		System.out.println("Key Length: "+key.length());
		
		for(int i = initCoords.x;i<initCoords.x+width;i++){
			for(int j = initCoords.y;j<initCoords.y+height;j++){
				key = key+a[i][j];
			}
		}
		System.out.println("Key Length: "+key.length());
		System.out.println(Integer.toBinaryString(Integer.parseUnsignedInt(key, 2)).length()+" After integer conversion");
		return Integer.parseUnsignedInt(key, 2);
		
	}
	
	public static ArrayList<Integer> getUniqueRandomValues(int percentage,Pairs a,Pairs b) {
		ArrayList<Integer> list = new ArrayList<>();
		int row1 = a.x;
		int col1 = a.y;
		
		int row2 = b.x;
		int col2 = b.y;
		for(int i = row1;i<=row2;i++){
			for(int j = col1;j<=col2;j++){
				list.add(Game.getId(i, j));
			}
		}
		
		
		Collections.shuffle(list);
		int elementsNeeded = (list.size()*percentage)/100;
		System.out.println("Elements Needed: "+elementsNeeded);
		System.out.println("Elements "+list.size());
		ArrayList<Integer> randValues = new ArrayList<>();
		for(int i = 0;i<elementsNeeded;i++){
			randValues.add(list.get(i));
		}
		return randValues;
	}
	
	public static int getId(int x,int y){
		int a1 = (x + y);
		int a2 = a1*(a1+1);
		int a3 = a2  / 2;
		return (a3+y);
		
	}
	
	public static Pairs getComponents(int id){
		int x,y;
		int a1 = 8*id;
		int a2 = a1+1;
		double a3 = Math.sqrt(a2);
		double a4 = a3 - 1;
		double a5 = a4 / 2;
		int w = (int) Math.floor(a5);
		
		int c1 = w + 1;
		int c2 = w*c1;
		
		int t = c2/2;
		
		y = id - t;
		
		x = w - y;
		
		return new Pairs(x,y);
	}
	private static Pairs getRadarsInitialCoordinates(int width,int height,int matrixSize){
		int rowMin = 0;
		int rowMax = matrixSize - width;
		int colMin = 0;
		int colMax = matrixSize-height;
		
		return new Pairs(Game.randomWithRange(rowMin, rowMax),Game.randomWithRange(colMin, colMax));
	}
	
	private static int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
	private static String getString(int a[][],int n){
		String str = new String();
		for(int i = 0;i<n;i++){
			for(int j = 0;j<n;j++){
				str+=Integer.toString(a[i][j])+"  ";
			}
			str+="\r\n";
		}
		return str;
	}
	
	private static void getKeys(ArrayList<Integer> keys,Input input){
		File file = new File(input.getFileName());
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = new String();
			int linesRead = 0;
			while((line = br.readLine()) != null){
				linesRead++;
				if(linesRead<=3)
					continue;
				keys.add(Integer.parseInt(br.readLine()));
			}
			
			while((line = br.readLine()) != null){
				for(int i = 1;i<=42;i++)
					br.readLine();
				keys.add(Integer.parseInt(line));
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generateMatrixWithKeys(int a[][],int key,int width,int height){
		String s = Integer.toBinaryString(key);
		int num = 28-s.length();
		for(int i = 0;i<num;i++){
			System.out.println("Hello");
			s = "0"+s;
		}
		int xCoord = Integer.parseUnsignedInt(s.substring(0, 4), 2);
		int yCoord = Integer.parseUnsignedInt(s.substring(4,8),2);
		System.out.println("Parsed X Coord: "+xCoord);
		System.out.println("Parsed Y Coord: "+yCoord);
		System.out.println("Binary Key representation: "+s+" Length: "+s.length());
		int index = 8;
		for(int i = xCoord;i<xCoord+width;i++){
			for(int j = yCoord;j<yCoord+height;j++){
				a[i][j] = Integer.parseInt(s.substring(index, index+1));
				index++;
			}
		}
	}
	
	//Remove later
	private static boolean equal(int[][] a,int[][] b){
		for(int i = 0;i<20;i++){
			for(int j = 0;j<20;j++){
				if(a[i][j] != b[i][j]){
					return false;
				}
			}
		}
		return true;
	}
}
