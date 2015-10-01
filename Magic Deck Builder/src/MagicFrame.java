import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Max Kelly. Created Sept 19, 2015.
 */
@SuppressWarnings("javadoc")
public class MagicFrame extends JFrame {
	private Panel sp = new Panel();
	int TX = 60;
	int TY = 30;
	private Console cs = new Console();
	JTabbedPane tabs = new JTabbedPane();

	// constructor for the frame
	public MagicFrame() throws IOException {
		super("Magic the Gathering Deck Builder");
		this.setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(new ControlPanel(), BorderLayout.WEST);
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), "Display Panel");
		border.setTitleJustification(TitledBorder.LEFT);
		this.sp.setBorder(border);
		this.sp.setPreferredSize(new Dimension(1000,1000));
		
//		this.sp.addTab("test", temporaryLostComponent);
		JScrollPane scrollPane = new JScrollPane(sp);
		
		// troll icon img if someone hax this
		String imageUrl = "http://www.pinmobilesex.com/pics/t/990.jpg";
        String imageDest = "magicFrameIcon.jpg";
        
        // check to see if the icon has already been downloaded so you do not have to go through it all again
        File test = new File(imageDest);
        if(!test.exists() && !test.isDirectory()){
        	saveImage(imageUrl, imageDest);
        	String os = System.getProperty("os.name");
        	
        }
        
        //  set the icon for the image
        BufferedImage image = ImageIO.read(new File("magicFrameIcon.jpg"));
        ImageIcon icon = new ImageIcon(image);
        this.setIconImage(icon.getImage());
		
        //  set the frame to the center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		//  add the various panels and console
		content.add(scrollPane, BorderLayout.CENTER);
		content.add(this.cs, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	// save the icon img
	private void saveImage(String imageUrl, String destinationFile) throws IOException {
		imageUrl.replaceAll(" ", "+");
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

	//  generate everything
	public static void main(String[] args) throws IOException {
		
		
		// Change the look and feel of the frame to make it look prettier
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		// Construct the frame here
		
		MagicFrame frame = new MagicFrame();
		
		

	}
	
	//  where I will put the jinputfield and the button for adding
	class ControlPanel extends JPanel {
		InputField In = new InputField();
		AddButton add = new AddButton();

		@SuppressWarnings("deprecation")
		public ControlPanel() {
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Control Panel");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setLayout(new FlowLayout());
			this.setSize(500,500);
			// ///////////////slider
			JSlider TXControl = new JSlider(SwingConstants.HORIZONTAL, 50, 1200,
					60);
			TXControl.addChangeListener(new SliderListener1());
			TXControl.setMajorTickSpacing(50);
			TXControl.setMinorTickSpacing(50);
			TXControl.setPaintTicks(true);
			TXControl.setPaintLabels(true);
			// //////////////////slider end
			// ///////////////slider
			JSlider TYControl = new JSlider(SwingConstants.HORIZONTAL, 30, 180,
					30);
			TYControl.addChangeListener(new SliderListener2());
			TYControl.setMajorTickSpacing(50);
			TYControl.setMinorTickSpacing(50);
			TYControl.setPaintTicks(true);
			TYControl.setPaintLabels(true);
			// //////////////////slider end
			
			this.In.setText("Full Name Here");
			
			this.add(new JLabel("Put in the full card name:"));
			this.add(this.In);
			this.add(this.add);

		}
		
		

		class SliderListener1 implements ChangeListener {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					MagicFrame.this.TX = source.getValue();
					MagicFrame.this.sp.repaint();
				}
			}
		}
		class SliderListener2 implements ChangeListener {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					MagicFrame.this.TY = source.getValue();
					MagicFrame.this.sp.repaint();
				}
			}
		}

		//  addbutton for adding pictures to the frame
		class AddButton extends JButton {
			public AddButton() {
				super("Add");
				this.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						
						
						
						
						//  Search the library of cards
						try {
							
							// get the string and handle spaces for the google image search
							String a = ControlPanel.this.In
									.getText();
							a = a.replaceAll(" ", "+");
							
							
							URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+a+"+magiccards.info");
				            URLConnection connection = url.openConnection();
				            System.out.println("Success 1");

				            String line;
				            StringBuilder builder = new StringBuilder();
				            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				            while((line = reader.readLine()) != null) {
				                builder.append(line);
				            }
				            System.out.println("Success 2");

				            JSONObject json = new JSONObject(builder.toString());
				            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
				            String imageDest = "test3.jpg";
				            System.out.println("Success 3");
				            
				            saveImage(imageUrl, imageDest);
				            System.out.println("Success 4");
				            
				            
				            BufferedImage image = ImageIO.read(new File("test3.jpg"));
				            ImageIcon icon = new ImageIcon(image);
				          
				          JLabel lbl=new JLabel();
				          lbl.setIcon(icon);
				          MagicFrame.this.sp.add(lbl);
				          
				          
				          // Delete the file when closing
				          File file = new File(imageDest);
				          file.deleteOnExit();
							
							
							MagicFrame.this.sp.repaint();
							
							MagicFrame.this.cs
							.setText("Card Name:" 
									+ "\nCard Type:"
									+ "\nMana Cost:"
									+ "\nCard Text:");
							
							// search through gatherer
							URL oracle = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+["+a+"]");
					        URLConnection yc = oracle.openConnection();
					        BufferedReader in = new BufferedReader(new InputStreamReader(
					                                yc.getInputStream()));
					        String inputLine;
					        String cost = "";
					        String cardName = "";
					        String cardType = "";
					        String cardText = "";
					        while ((inputLine = in.readLine()) != null){
					        	
					        	if(inputLine.contains("Card Name")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardName = inputLine;
					        	}
					        	
					        	if(inputLine.contains("Types:")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardType = inputLine;
					        	}
					        	
					        	if(inputLine.contains("<div class=\"label\" style=\"font-size: .7em;\">")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cost = inputLine;
					        		
					        	}
					        	
					        	if(inputLine.contains("Card Text")){
					        		String inputLine2;
					        		while((inputLine2 = in.readLine()) != null && !inputLine2.contains("Flavor Text")){
					        			cardText.concat(inputLine2);
					        		}
					        	}
					        }
					            
					        in.close();
					        
					        MagicFrame.this.cs
							.setText("Card Name:   "+cardName
									+ "\nCard Type:   "+cardType
									+ "\nMana Cost:   "+cost
									+ "\nCard Text:   "+cardText
									);
							
						} catch (Exception nFE) {
							MagicFrame.this.cs
									.setText("Error:\n Please Put a valid card name into the search.  Spelling matters!"
											+ "\n\n\n"
											+ nFE.getMessage());
							
						}
					}

					public void saveImage(String imageUrl, String destinationFile) throws IOException {
						imageUrl.replaceAll(" ", "+");
						System.out.println(imageUrl);
						System.out.println(destinationFile);
					    URL url = new URL(imageUrl);
					    InputStream is = url.openStream();
					    OutputStream os = new FileOutputStream(destinationFile);
					    System.out.println("success3.1");

					    byte[] b = new byte[2048];
					    int length;

					    while ((length = is.read(b)) != -1) {
					        os.write(b, 0, length);
					    }
					    System.out.println("success3.2");

					    is.close();
					    System.out.println("success3.3");
					    os.close();
					    System.out.println("success3.4");
					}

					
				});
			}
			
			
		}

		
		// the jinputfield.  Will work for button or if enter is pressed
		class InputField extends JTextField {
			public InputField() {
				this.setColumns(10);
				this.selectAll();
				
				this.addActionListener(new ActionListener() {

				    @Override
				    public void actionPerformed(ActionEvent e) {
				       // do everything that the add button does
				    //  Search the library of cards
						try {
							
							// get the string and handle spaces for the google image search
							String a = ControlPanel.this.In
									.getText();
							a = a.replaceAll(" ", "+");
							
							
							URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+a+"+magiccards.info");
				            URLConnection connection = url.openConnection();
				            System.out.println("Success 1");

				            String line;
				            StringBuilder builder = new StringBuilder();
				            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				            while((line = reader.readLine()) != null) {
				                builder.append(line);
				            }
				            System.out.println("Success 2");

				            JSONObject json = new JSONObject(builder.toString());
				            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
				            String imageDest = "test3.jpg";
				            System.out.println("Success 3");
				            
				            saveImage(imageUrl, imageDest);
				            System.out.println("Success 4");
				            
				            
				            BufferedImage image = ImageIO.read(new File("test3.jpg"));
				            ImageIcon icon = new ImageIcon(image);
				          
				          JLabel lbl=new JLabel();
				          lbl.setIcon(icon);
				          MagicFrame.this.sp.add(lbl);
				          
				          
				          // Delete the file when closing
				          File file = new File(imageDest);
				          file.deleteOnExit();
							
							
							MagicFrame.this.sp.repaint();
							
							MagicFrame.this.cs
							.setText("Card Name:"
									+ "\nCard Type:"
									+ "\nMana Cost:"
									+ "\nCard Text:");
							
							// search through gatherer
							URL oracle = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+["+a+"]");
					        URLConnection yc = oracle.openConnection();
					        BufferedReader in = new BufferedReader(new InputStreamReader(
					                                yc.getInputStream()));
					        String inputLine;
					        String cost = "";
					        String cardName = "";
					        String cardType = "";
					        String cardText = "";
					        while ((inputLine = in.readLine()) != null){
					        	
					        	if(inputLine.contains("Card Name")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardName = inputLine;
					        	}
					        	
					        	if(inputLine.contains("Types:")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardType = inputLine;
					        	}
					        	
					        	if(inputLine.contains("<div class=\"label\" style=\"font-size: .7em;\">")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cost = inputLine;
					        		
					        	}
					        	
					        	if(inputLine.contains("Card Text")){
					        		String inputLine2;
					        		while((inputLine2 = in.readLine()) != null && !inputLine2.contains("Flavor Text")){
					        			cardText.concat(inputLine2);
					        		}
					        	}
					        }
					            
					        in.close();
					        
					        MagicFrame.this.cs
							.setText("Card Name:   "+cardName
									+ "\nCard Type:   "+cardType
									+ "\nMana Cost:   "+cost
									+ "\nCard Text:   "+cardText
									);
							
						}catch (Exception nFE) {
							MagicFrame.this.cs
							.setText("Error:\n Please Put a valid card name into the search.  Spelling matters!"
									+ "\n\n\n"
									+ nFE.getMessage());
					
				}
				    }
				});
			}
		}
	}

	// Panel where the card picture is going to be displayed
	@SuppressWarnings("rawtypes")
	class Panel extends JPanel {
		
		public Panel(){
			JTabbedPane tabs = new JTabbedPane();
			JComponent panel1 = makeTextPanel("Panel #1");
	        tabs.addTab("Tab 1", null,panel1,
	                "Does nothing");
			tabs.setMnemonicAt(0, KeyEvent.VK_1);
			
//			add(tabs);
			
		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
		}
		
		protected JComponent makeTextPanel(String text) {
	        JPanel panel = new JPanel(false);
	        JLabel filler = new JLabel(text);
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
	        return panel;
	    }

		
	}

	// creates the console at the bottom of the frame
	class Console extends JTextArea {
		public Console() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Card Text");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setPreferredSize(new Dimension(145, 100));
			this.append("Magic the Gathering Deck Constructor:"
					+ "\nPlease input a card then push the 'add' button"
					+ "\n                  @author Max Kelly");

		}
	}
	
//	class TabbedPane extends JPanel{
//		public TabbedPane() {
//	        super(new GridLayout(1, 1));
//	         
//	        JTabbedPane tabbedPane = new JTabbedPane();
//	         
//	        JComponent panel1 = makeTextPanel("Panel #1");
//	        tabbedPane.addTab("Tab 1", null,panel1,
//	                "Does nothing");
//	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
//	         
//	        JComponent panel2 = makeTextPanel("Panel #2");
//	        tabbedPane.addTab("Tab 2", null, panel2,
//	                "Does twice as much nothing");
//	        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//	         
//	        JComponent panel3 = makeTextPanel("Panel #3");
//	        tabbedPane.addTab("Tab 3", null, panel3,
//	                "Still does nothing");
//	        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
//	         
//	        JComponent panel4 = makeTextPanel(
//	                "Panel #4 (has a preferred size of 410 x 50).");
//	        panel4.setPreferredSize(new Dimension(410, 50));
//	        tabbedPane.addTab("Tab 4", null, panel4,
//	                "Does nothing at all");
//	        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
//	         
//	        //Add the tabbed pane to this panel.
//	        add(tabbedPane);
//	         
//	        //The following line enables to use scrolling tabs.
//	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//	    }
//	     
//	    protected JComponent makeTextPanel(String text) {
//	        JPanel panel = new JPanel(false);
//	        JLabel filler = new JLabel(text);
//	        filler.setHorizontalAlignment(JLabel.CENTER);
//	        panel.setLayout(new GridLayout(1, 1));
//	        panel.add(filler);
//	        return panel;
//	    }
//	}

}
