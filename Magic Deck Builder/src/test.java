import javax.imageio.ImageIO;
import javax.lang.model.util.Elements;
import javax.swing.*;

import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.json.simple.*;
import org.*;

//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.io.InputStream;
public class test extends JPanel {
	
	
	
	
	/**
	    * Build a Drive service object.
	    *
	    * @param credentials OAuth 2.0 credentials.
	    * @return Drive service object.
	    */
	  static Drive buildService(GoogleCredential credentials) {
	    HttpTransport httpTransport = new NetHttpTransport();
	    JacksonFactory jsonFactory = new JacksonFactory();

	    return new Drive.Builder(httpTransport, jsonFactory, credentials)
	        .build();
	  }
	  
	  
	  /**
	   * Download a file's content using Google Drive API.
	   *
	   * @param service Drive API service instance.
	   * @param file Drive File instance.
	   * @return InputStream containing the file's content if successful,
	   *         {@code null} otherwise.
	   */
	  private static InputStream downloadFile(Drive service, File file) {
	    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	      try {
	        // uses alt=media query parameter to request content
	        return service.files().get(file.getId()).executeMediaAsInputStream();
	      } catch (IOException e) {
	        // An error occurred.
	        e.printStackTrace();
	        return null;
	      }
	    } else {
	      // The file doesn't have any content stored on Drive.
	      return null;
	    }
	  }

	
	// will change desktop background
//	 public static native int SystemParametersInfo(int uiAction,int uiParam,String pvParam,int fWinIni);
//
//	    static
//	    {
//	        System.loadLibrary("user32");
//	    }
//
//	    public int Change(String path)
//	    {
//	       return SystemParametersInfo(20, 0, path, 0);
//	    }
//
//	    public static void main(String args[])
//	    {
//	        String wallpaper_file = "c:\\wallpaper.jpg";
//	        test mychanger = new test();
//	        mychanger.Change(wallpaper_file);
//	    }
	
//	//  create tabbed panes
//	public test() {
//        super(new GridLayout(1, 1));
//         
//        JTabbedPane tabbedPane = new JTabbedPane();
//         
//        JComponent panel1 = makeTextPanel("Panel #1");
//        tabbedPane.addTab("Tab 1", null, panel1,
//                "Does nothing");
//        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
//         
//        JComponent panel2 = makeTextPanel("Panel #2");
//        tabbedPane.addTab("Tab 2", null, panel2,
//                "Does twice as much nothing");
//        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//         
//        JComponent panel3 = makeTextPanel("Panel #3");
//        tabbedPane.addTab("Tab 3", null, panel3,
//                "Still does nothing");
//        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
//         
//        JComponent panel4 = makeTextPanel(
//                "Panel #4 (has a preferred size of 410 x 50).");
//        panel4.setPreferredSize(new Dimension(410, 50));
//        tabbedPane.addTab("Tab 4", null, panel4,
//                "Does nothing at all");
//        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
//         
//        //Add the tabbed pane to this panel.
//        add(tabbedPane);
//         
//        //The following line enables to use scrolling tabs.
//        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//    }
//     
//    protected JComponent makeTextPanel(String text) {
//        JPanel panel = new JPanel(false);
//        JLabel filler = new JLabel(text);
//        filler.setHorizontalAlignment(JLabel.CENTER);
//        panel.setLayout(new GridLayout(1, 1));
//        panel.add(filler);
//        return panel;
//    }
//    
//    private static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("TabbedPaneDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         
//        //Add content to the window.
//        frame.add(new test(), BorderLayout.CENTER);
//         
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//    
//    public static void main(String[] args) {
//        //Schedule a job for the event dispatch thread:
//        //creating and showing this application's GUI.
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                //Turn off metal's use of bold fonts
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//        createAndShowGUI();
//            }
//        });
//    }
	
	
	// read specific lines from a website
//	public static void main(String[] args) throws Exception {
//		URL oracle = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+[mindstatic]");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                                yc.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null){
//        	
//        	if(inputLine.contains("Card Name")){
//        		inputLine = in.readLine();
//        		inputLine = in.readLine();
////        		inputLine = in.readLine();
//        		inputLine = inputLine.replaceAll(" ", "");
//        		inputLine = inputLine.replaceAll("</div>", "");
//        		System.out.println(inputLine);
//        		
////        		System.out.println("reached");
//        	}
//        }
//            
//        in.close();
//    }


	
	// This will find something on google images based on the search parameters and grab the first image
//	public static void main(String[] args) {
//        try{
//            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=sen+triplets");
//            URLConnection connection = url.openConnection();
//
//            String line;
//            StringBuilder builder = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            while((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//
//            JSONObject json = new JSONObject(builder.toString());
//            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
//            String imageDest = "test3.jpg";
//            
//            saveImage(imageUrl, imageDest);
//            
////            BufferedImage image = ImageIO.read(new URL(imageUrl));
//            
//            BufferedImage image = ImageIO.read(new File("test3.jpg"));
//            ImageIcon icon = new ImageIcon(image);
//            JFrame frame=new JFrame();
//          frame.setLayout(new FlowLayout());
//          frame.setSize(200,300);
//          JLabel lbl=new JLabel();
//          lbl.setIcon(icon);
//          frame.add(lbl);
//          frame.setVisible(true);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//          
//          // Delete the file when closing
//          File file = new File(imageDest);
//          file.deleteOnExit();
//            
//            
//            JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
//        } catch(Exception e){
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
//    }


	
	
	
	
	public static void main(String[] args) throws Exception {
//		
//		// grab the image from a url and save it
//	    String imageUrl = "http://i.tcgplayer.com/31799.jpg";
//	    String destinationFile = "test2.jpg";
//
//	    saveImage(imageUrl, destinationFile);
//	    
//	    
//	    // display the image in the frame
//	    BufferedImage img=ImageIO.read(new File("test2.jpg"));
//        ImageIcon icon=new ImageIcon(img);
//        JFrame frame=new JFrame();
//        frame.setLayout(new FlowLayout());
//        frame.setSize(200,300);
//        JLabel lbl=new JLabel();
//        lbl.setIcon(icon);
//        frame.add(lbl);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        // Delete the file when closing
//        File file = new File(destinationFile);
//        file.deleteOnExit();
	  
		GoogleCredential me = new GoogleCredential();
		
		downloadFile(buildService(me), new File());
	}
//
//	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
//	    URL url = new URL(imageUrl);
//	    InputStream is = url.openStream();
//	    OutputStream os = new FileOutputStream(destinationFile);
//
//	    byte[] b = new byte[2048];
//	    int length;
//
//	    while ((length = is.read(b)) != -1) {
//	        os.write(b, 0, length);
//	    }
//
//	    is.close();
//	    os.close();
//	}
//	
	
}
