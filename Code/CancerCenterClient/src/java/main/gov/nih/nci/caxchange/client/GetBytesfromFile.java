package gov.nih.nci.caxchange.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// Returns the contents of the file in a byte array.

public class GetBytesfromFile{
	
public static byte[] GetBytesFromFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);

    // Get the size of the file
    long length = file.length();

    // You cannot create an array using a long type.
    // It needs to be an int type.
    // Before converting to an int type, check
    // to ensure that file is not larger than Integer.MAX_VALUE.
    //if (length > Integer.MAX_VALUE) {
        // File is too large
    //}

    // Create the byte array to hold the data
    byte[] bytes = new byte[(int)length];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
        offset += numRead;
    }

    // Ensure all the bytes have been read in
    if (offset < bytes.length) {
        throw new IOException("Could not completely read file "+file.getName());
    }

    // Close the input stream and return bytes
    is.close();
    return bytes;
}


public static void main(String[] args) {
	
	String inProcessFolder  = "C:/HubClientFolders/InProcess";
	File inProcessDir    = new File(inProcessFolder);
	File[] fileList =  inProcessDir.listFiles();
	GetBytesfromFile gbf = new GetBytesfromFile();
	for (int i=0; i<fileList.length; i++)
    {
		try{
    	     System.out.println("Processing File : " + fileList[i].toString() );
    	     gbf.GetBytesFromFile(fileList[i]);		
    	     boolean success = fileList[i].renameTo(new File(inProcessDir, fileList[i].getName()));
		}
		catch (IOException e)
		{
			System.out.println("IO Exception");
		}
    }	
}

}