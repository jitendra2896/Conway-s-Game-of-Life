
class Utility {
	public static void generateMatrixWithKeys(int a[][],int key,int width,int height){
		String s = Integer.toBinaryString(key);
		int num = 28-s.length();
		for(int i = 0;i<num;i++){
			s = "0"+s;
		}
		int xCoord = Integer.parseUnsignedInt(s.substring(0, 4), 2);
		int yCoord = Integer.parseUnsignedInt(s.substring(4,8),2);
		int index = 8;
		for(int i = xCoord;i<xCoord+width;i++){
			for(int j = yCoord;j<yCoord+height;j++){
				a[i][j] = Integer.parseInt(s.substring(index, index+1));
				index++;
			}
		}
	}
}
