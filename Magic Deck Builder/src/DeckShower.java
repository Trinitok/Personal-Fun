import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class DeckShower implements Runnable {

	public DeckShower(Dimension size, JFrame MagicFrame) {
		// TODO Auto-generated constructor stub
		Dimension deckPanelSize = new Dimension(MagicFrame.WIDTH/5, MagicFrame.HEIGHT*2/3);
		Dimension artPanelSize = new Dimension(MagicFrame.WIDTH*4/5, MagicFrame.HEIGHT*2/3);
		Dimension textPanelSize = new Dimension(MagicFrame.WIDTH, MagicFrame.HEIGHT*1/3);
		
//		MagicFrame.add(new CardPanel(deckPanelSize, this), BorderLayout.LEFT_ALIGNMENT);
//		MagicFrame.add(new ArtPanel(artPanelSize, this), BorderLayout.CENTER_ALIGNMENT);
//		MagicFrame.add(new TextPanel(textPanelSize, this), BorderLayout.BOTTOM_ALIGNMENT);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
