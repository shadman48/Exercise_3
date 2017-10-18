import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ex3Client {
	public static void main(String[] args) throws Exception 
	{
		// Create socket
    	try (Socket socket = new Socket("18.221.102.182", 38002)) 
        {
    		InputStream is = socket.getInputStream();
    		DataInputStream dis = new DataInputStream(is);
    		OutputStream os = socket.getOutputStream();
    		DataOutputStream dos = new DataOutputStream(os);
    		
        }
	}
}
