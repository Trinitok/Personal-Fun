import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *   This class is to demo how to create encrypted files in java
 *   
 *   NOTE: This will not deal with making the file unwritable or hidden, it will show the file and allow it to be written to if the user is able to decrypt it
 */

/**
 * @author kellymr1
 *
 */
@SuppressWarnings("serial")
public class EncryptedFile extends File {

	public EncryptedFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
		PrintWriter writer;
		try {
			writer = new PrintWriter(pathname, "UTF-8");
			writer.println("Can you see me?");
			writer.println("I sure hope you can");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		EncryptedFile test = new EncryptedFile("test.txt");
		EncryptedFile enc = new EncryptedFile("Encryption Example.txt");

		try {
			// get the file to be read
			BufferedReader br = new BufferedReader(new FileReader(enc));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
			sb.append(line);
			sb.append(System.lineSeparator());
			String everything = sb.toString();
			
			br.close();
			
			System.out.println(everything);

			KeyGenerator keygenerator = KeyGenerator.getInstance("AES"); // sets
																			// encryption
																			// algorithm
																			// type
			SecretKey myDesKey = keygenerator.generateKey();

			Cipher desCipher;
			desCipher = Cipher.getInstance("AES");

			byte[] text = everything.getBytes("UTF8"); // make sure
																	// to
																	// specify
																	// utf8
																	// because
																	// it might
																	// differ
																	// from
																	// machine
																	// to
																	// machine

			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey); // tell to encrypt
															// using the
															// selected key
			byte[] textEncrypted = desCipher.doFinal(text);

			String s = new String(textEncrypted);
			System.out.println(s);

			//  reset encrypted file
			PrintWriter c = new PrintWriter(enc, "UTF-8");
			c.print(s);
			c.close();
			
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey); // tell to decrypt
															// using the
															// selected key
			byte[] textDecrypted = desCipher.doFinal(textEncrypted);

			s = new String(textDecrypted);
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
