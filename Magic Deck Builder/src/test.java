import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
public class test {

//    JFrame frame = new JFrame("");
//    AutoCompleteDecorator decorator;
//    JComboBox combobox;
//
//    public test() {
//        combobox = new JComboBox(new Object[]{"","Ester", "Jordi",
//            "Jordina", "Jorge", "Sergi"});
//        AutoCompleteDecorator.decorate(combobox);
//        frame.setSize(400,400);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new FlowLayout());
//
//        frame.add(combobox);
//        frame.setVisible(true);
//    }

	public static void main(String[] args) throws Exception {
	    String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
	    String destinationFile = "C:/Users/kellymr1/Documents/Classes/CSSE371/test.jpg";

	    saveImage(imageUrl, destinationFile);
	    
	    EventQueue.invokeLater(new Runnable(){
	    	public void run(){
                ImageFrame frame = new ImageFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);


            }
	    });
	}

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
	}
	
	
	class ImageFrame extends JFrame{

	    public ImageFrame(){
	        setTitle("ImageTest");
	        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	        ImageComponent component = new ImageComponent();
	        add(component);

	    }

	    public static final int DEFAULT_WIDTH = 300;
	    public static final int DEFAULT_HEIGHT = 200;
	}
	
	class ImageComponent extends JComponent{
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;
	    private Image image;
	    public ImageComponent(){
	        try{
	            File image2 = new File("C:/Users/kellymr1/Documents/Classes/CSSE371/test.jpg");
	            image = ImageIO.read(image2);

	        }
	        catch (IOException e){
	            e.printStackTrace();
	        }
	    }
	    public void paintComponent (Graphics g){
	        if(image == null) return;
	        int imageWidth = image.getWidth(this);
	        int imageHeight = image.getHeight(this);

	        g.drawImage(image, 50, 50, this);

	        for (int i = 0; i*imageWidth <= getWidth(); i++)
	            for(int j = 0; j*imageHeight <= getHeight();j++)
	                if(i+j>0) g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);
	    }

	}
}
