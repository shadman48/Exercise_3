import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Ex3Client {
	public static void main(String[] args) throws Exception 
	{
		// Create socket
    	try (Socket socket = new Socket("18.221.102.182", 38103)) 
        {
    		System.out.println("Connected to server.");
    		InputStream is = socket.getInputStream();
    		OutputStream os = socket.getOutputStream();
    		DataInputStream dis = new DataInputStream(is);
    		DataOutputStream dos = new DataOutputStream(os);
    		
    		
    		//Read single byte of data. unsigned. This will be the number of bytes to follow.
    		int numbOfBytes;
    		numbOfBytes = dis.readUnsignedByte();
    		
    		System.out.println("Reading in " + numbOfBytes + " Bytes.");
    		System.out.println("Data recived: ");
    		//Store bytes into an array.
    		byte[] byteArray = new byte[numbOfBytes];
    		for(int i = 0; i < byteArray.length; i++)
    		{
    			byteArray[i] = (byte)(dis.readUnsignedByte());
    		}
    		System.out.println(bytesToHex(byteArray));
    		
    		//Call checkSum on the byte array.
    		 
    		short checksum = checkSum(byteArray);
    		ByteBuffer bbuffer = ByteBuffer.allocate(2);
    		bbuffer.putShort(checksum);
    		System.out.println(bytesToHex(byteArray));
    		
    		//Send the checksum back to server to check if right.
    			dos.write(bbuffer.array());
    		
    		//Response from server. 0 = bad, 1 = ok.
    		byte response = dis.readByte();
    		if(response == 1)
    			System.out.println("Check sum good.");
    		else
    			System.out.println("Check sum not good.");
    		
    		
    		
    		
    		
    		
        }
	}
	
	/**
	 * This method implements the Internet checksum algorithm.
	 * 
	 * @param ByteArray.
	 * 
	 * @return returns right most 16 bits of the sum.
	 */
	public static short checkSum(byte[] byteArray)
	{
		long sum = 0;
		int n;
		for(int i = 0; i < byteArray.length; i = i + 2)
		{
			n = byteArray[i];
			
			n = (n << 8) & 0xFF00;
			n = n + (byteArray[i+1] & 0xFF);
			
			sum += n;
			
			if((sum & 0xFFFF0000) > 0)
			{
				//carry occurred, so wrap around.
				sum &= 0xFFFF;
				sum++;
			}
		}
		return (short)(~(sum & 0xFFFF));
	}
	
	
	/**
	 * Converts bytes to Hex. 
	 * Returns string with Hex values.
	 */
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) 
	{
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	
	
	
}
