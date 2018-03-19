import java.io.*;

class Input {
	String fileName;
	public Input(String fileName){
		this.fileName = fileName;
	}

	public void writeToFile(String data){
		try(FileWriter fw = new FileWriter(fileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
				out.println(data);
			} catch (IOException e) {
			}
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public boolean exists(){
		File file = new File(fileName);
		return file.exists();
	}
	
}
