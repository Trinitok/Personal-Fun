import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MagicFrame mtg = new MagicFrame();
		
//		new DeckShower(mtg.getSize(), mtg);
		
		Dimension deckPanelSize = new Dimension(MagicFrame.WIDTH/5, MagicFrame.HEIGHT*2/3);
		Dimension artPanelSize = new Dimension(MagicFrame.WIDTH*4/5, MagicFrame.HEIGHT*2/3);
		Dimension textPanelSize = new Dimension(MagicFrame.WIDTH, MagicFrame.HEIGHT*1/3);
		
		mtg.getContentPane().add(new CardPanel(deckPanelSize), BorderLayout.WEST);
		mtg.getContentPane().add(new ArtPanel(artPanelSize), BorderLayout.EAST);
		mtg.getContentPane().add(new TextPanel(textPanelSize), BorderLayout.SOUTH);
		
		
		mtg.setVisible(true);

	}

}
