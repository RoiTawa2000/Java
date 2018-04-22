package Minesweeper;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class MineSweeper extends JFrame implements ActionListener {
	private JButton Button[][] = new JButton[8][8]; // Objekte
	private JLabel Text;
	private JButton reset;
	private JCheckBox flag;
	private JButton showmines;
	private JButton hidemines;
	private JButton Game_won;
	private JButton Game_lost;
	private int Feld[][] = new int[8][8];
	final int mine = 10;
	private int minesset;
	private JLabel flagcounter;
	private int flags;
	// ----------------------------------------------------Konstruktor-------------------------------------------------------------------------------------------------------------------
	public MineSweeper()
	{
		super("Minesweeper");
		JPanel Platte = new JPanel();
		Platte.setLayout(null);

		ButtonGroup Gruppe = new ButtonGroup();
		for (int i = 0; i < 8; i++) {
			for (int o = 0; o < 8; o++) {
				Button[i][o] = new JButton("");
				Button[i][o].setSize(50, 50);
				Button[i][o].setLocation(49 * i + 20, 49 * o + 100);
				Button[i][o].addActionListener(this);
				Button[i][o].setBackground(Color.LIGHT_GRAY);
				Gruppe.add(Button[i][o]);
				Platte.add(Button[i][o]);
			}
		}
		// Flaggenzähler oben rechts
		flagcounter = new JLabel("Bomben: 0/10");
		flagcounter.setSize(150, 45);
		flagcounter.setLocation(282, 60);
		flagcounter.setFont(new Font("Arial", Font.BOLD, 20));
		flagcounter.setBackground(Color.black);
		Platte.add(flagcounter);

		// reset Button
		reset = new JButton("Neu Starten");
		reset.setSize(125, 45);
		reset.setLocation(20, 525);
		reset.setBackground(Color.black);
		reset.setForeground(Color.white);
		reset.setFont(new Font("Arial", Font.BOLD, 15));
		reset.addActionListener(this);
		Platte.add(reset);

		// Markierbox
		flag = new JCheckBox("Markieren");
		flag.setSize(125, 45);
		flag.setLocation(155, 525);
		flag.setBackground(Color.yellow);
		flag.setForeground(Color.black);
		flag.setFont(new Font("Arial", Font.BOLD, 15));
		flag.setHorizontalAlignment(SwingConstants.CENTER);
		;
		flag.addActionListener(this);
		Platte.add(flag);

		// Methode um Minen das 1. mal zu generieren
		MinesCreator();

		// Minen zeigen Knopf
		showmines = new JButton("Minen zeigen");
		showmines.setSize(130, 45);
		showmines.setLocation(290, 525);
		showmines.setBackground(Color.black);
		showmines.setForeground(Color.white);
		showmines.addActionListener(this);
		showmines.setFont(new Font("Arial", Font.BOLD, 15));
		Platte.add(showmines);

		// Minen verbergen Knopf
		hidemines = new JButton("Minen verbergen");
		hidemines.setSize(130, 45);
		hidemines.setLocation(290, 525);
		hidemines.setBackground(Color.black);
		hidemines.setForeground(Color.white);
		hidemines.setFont(new Font("Arial", Font.BOLD, 15));
		hidemines.addActionListener(this);
		Platte.add(hidemines);

		// Game gewonnen Knopf
		Game_won = new JButton("Gewonnen!");
		Game_won.setSize(250, 55);
		Game_won.setLocation(100, 15);
		Game_won.setFont(new Font("Arial", Font.BOLD, 35));
		Game_won.setBackground(Color.WHITE);
		Game_won.setForeground(Color.GREEN);
		Game_won.addActionListener(this);
		Platte.add(Game_won);
		Game_won.setVisible(false);

		// Game verloren Knopf
		Game_lost = new JButton("Verloren!");
		Game_lost.setSize(250, 55);
		Game_lost.setLocation(100, 15);
		Game_lost.setFont(new Font("Arial", Font.BOLD, 35));
		Game_lost.setBackground(new Color(240, 240, 240)); //hellgrau
		Game_lost.setForeground(Color.RED);
		Game_lost.addActionListener(this);
		Platte.add(Game_lost);
		Game_lost.setVisible(false);

		// Titel
		Text = new JLabel("Minesweeper");
		Text.setSize(200, 50);
		Text.setLocation(140, 10);
		Text.setFont(new Font("Arial", Font.BOLD, 25));
		Text.setForeground(new Color(0, 0, 0)); //schwarzer
		Platte.add(Text);
		setContentPane(Platte);

		Platte.setBackground(new Color(225, 243, 244)); //hellblauer Hintergrund
	}
	// ------------------------------------------------------Methoden--------------------------------------------------------------------------------------------------------------------
	public void MinesCreator() {
		// exakt 10 Minen zufällig auf dem Feld verteilen
		while (minesset <= 9) {
			int x = (int) (Math.random() * 8);
			int y = (int) (Math.random() * 8);

			if (Feld[x][y] != mine) {
				Feld[x][y] = mine;
				minesset++;
			} else {
				System.out.println("Mine doppelt gesetzt"); // falls Mine auf das selbe Feld gesetzt wird
			}
		}
		// Nachbarfelder nach Minen absuchen
		for (int x = 0; x < Feld.length; x++) {
			for (int y = 0; y < Feld[0].length; y++) {
				if (Feld[x][y] != mine) {
					int Nachbarfeldminenzähler = 0;

					if (x > 0 && y > 0 && Feld[x - 1][y - 1] == mine) // oben links
						Nachbarfeldminenzähler++;
					if (y > 0 && Feld[x][y - 1] == mine) // oben
						Nachbarfeldminenzähler++;
					if (x < Feld.length - 1 && y > 0 && Feld[x + 1][y - 1] == mine) // oben rechts
						Nachbarfeldminenzähler++;
					if (x > 0 && Feld[x - 1][y] == mine) // mitte links
						Nachbarfeldminenzähler++;
					if (x < Feld.length - 1 && Feld[x + 1][y] == mine) // mitte rechts
						Nachbarfeldminenzähler++;
					if (x > 0 && y < Feld.length - 1 && Feld[x - 1][y + 1] == mine) // unten links
						Nachbarfeldminenzähler++;
					if (y < Feld.length - 1 && Feld[x][y + 1] == mine) // unten
						Nachbarfeldminenzähler++;
					if (x < Feld.length - 1 && y < Feld.length - 1 && Feld[x + 1][y + 1] == mine) // unten rechts
						Nachbarfeldminenzähler++;
					Feld[x][y] = Nachbarfeldminenzähler;
				}
			}
		}
	}
	
	// Spielfeld aufdecken
	public void lostGame() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (Button[x][y].isEnabled()) {
					if (Feld[x][y] == mine) {
						Button[x][y].setText("X");
						Button[x][y].setEnabled(false);
						Button[x][y].setBackground(Color.PINK);

					} else if (Feld[x][y] == 0) {
						Button[x][y].setText("");
						Button[x][y].setEnabled(false);
					} else {
						Button[x][y].setText(Feld[x][y] + "");
						Button[x][y].setEnabled(false);
					}
				}
			}
		}
		Game_lost.setVisible(true);
	}
	
	// Minen anzeigen
	public void showmines() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (Feld[x][y] == mine) {
					Button[x][y].setText("X");
					Button[x][y].setEnabled(false);
					Button[x][y].setBackground(Color.PINK);
				}
			}
		}
	}
	
	// Minen verbergen
	public void hidemines() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (Feld[x][y] == mine) {
					Button[x][y].setText("");
					Button[x][y].setEnabled(true);
					Button[x][y].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
	}
	
	// Schauen ob gewonnen wurde
	public void CheckWin() {
		Boolean win = true;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (Feld[x][y] != mine && Button[x][y].isEnabled() == true //schauen, ob alle Felder aufgedeckt sind, die keine Mine sind oder als Mine gekennzeichnet wurden
						&& Button[x][y].getBackground() != Color.yellow) {
					win = false;
				}
			}
		}
		if (win == true) { //Wenn alle Felder ausser die Minen aufgedeckt sind, dann zeige das Gewonnen Schild und mache die Minen grün
			Game_won.setVisible(true);
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (Feld[x][y] == mine) {
						Button[x][y].setBackground(Color.GREEN);
						Button[x][y].setText("X");
					}
					Button[x][y].setEnabled(false);
				}
			}
			flag.setEnabled(false);
			showmines.setEnabled(false);
			hidemines.setEnabled(false);
		}
	}
	// --------------------------------------------------------------------Ereignisse----------------------------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent Ereignis) {

		Object Quelle = Ereignis.getSource();

		if (flag.isSelected()) { // Wenn Markieren ausgewählt, wird das Feld Gelb, sonst grau
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (Button[x][y].getBackground() == Color.yellow) {
						Button[x][y].setEnabled(true);
					}
					if (Quelle == Button[x][y]) {
						if (Button[x][y].getBackground() == Color.yellow) {
							Button[x][y].setBackground(Color.LIGHT_GRAY);
							flags--;
							flagcounter.setText("Bomben: " + flags + "/10");
						} else if (Button[x][y].getBackground() == Color.LIGHT_GRAY) {
							Button[x][y].setBackground(Color.yellow);
							flags++;
							flagcounter.setText("Bomben: " + flags + "/10");
						}
						System.out.println(flags);
					}
				}
			}
		} else { //Wenn das Feld Gelb ist und Markieren nicht ausgewählt ist, dann wird das Feld gesperrt
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (Button[x][y].getBackground() == Color.yellow) {
						Button[x][y].setEnabled(false);
					}
					if (Quelle == Button[x][y]) { //Wenn es ein normales Feld ist und Markieren nicht ausgewählt ist, dann wird das Feld weiss
						Button[x][y].setBackground(Color.white);
					}
				}
			}
		}
		// Minen zeigen, "Minen zeigen" Schaltfläche verdecken, "Minen verstecken"
		// Schaltfläche zeigen
		if (Quelle == showmines) {
			showmines();
			showmines.setVisible(false);
			hidemines.setVisible(true);
		}
		// Minen verbergen, "Minen verbergen" Schaltfläche verbergen, "Minen zeigen"
		// Schaltfläche zeigen
		if (Quelle == hidemines) {
			hidemines();
			hidemines.setVisible(false);
			showmines.setVisible(true);
		}
		// Wenn Reset Button gedrückt wurde oder Game verloren/gewonnen Button gedrückt wurde, wird alles auf den anfangszustand gesetzt
		if (Quelle == reset || Quelle == Game_lost || Quelle == Game_won) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Button[x][y].setEnabled(true);
					Button[x][y].setText(" ");
					Feld[x][y] = 0;
					Button[x][y].setBackground(Color.LIGHT_GRAY);
				}
			}
			Game_won.setVisible(false);
			hidemines.setVisible(false);
			showmines.setVisible(true);
			showmines.setEnabled(true);
			hidemines.setEnabled(true);
			Game_lost.setVisible(false);
			flag.setEnabled(true);
			flag.setSelected(false);
			minesset = 0;
			MinesCreator();
			flags = 0;
			flagcounter.setText("Bomben: 0/10");
		} else if ((boolean) (Quelle = flag.isSelected())) { // Wenn markieren ausgewählt ist, werden keine Felder aufgedeckt
		} else { // Spiel spielen
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (Ereignis.getSource().equals(Button[x][y])) {
						if (Feld[x][y] == mine) { // Wenn Feld eine Mine ist, dann ist das Game verloren
							Button[x][y].setText("X");
							lostGame();
							Button[x][y].setBackground(Color.red);
							showmines.setEnabled(false);
							Game_lost.setVisible(true);
							flag.setEnabled(false);
						} else if (Feld[x][y] == 0) { // Die Textfelder mit 0 leeren und die 8 angrenzenden aufdecken und auf gewinnen prüfen
							Button[x][y].setEnabled(false);

							if (x > 0 && y > 0) {// oben links
								Button[x - 1][y - 1].setText(Feld[x - 1][y - 1] + "");
								Button[x - 1][y - 1].setEnabled(false);
								Button[x - 1][y - 1].setBackground(Color.white);
								if (Feld[x - 1][y - 1] == 0) {
									Button[x - 1][y - 1].setText(" ");
								}
							}
							if (y > 0) {// oben
								Button[x][y - 1].setText(Feld[x][y - 1] + "");
								Button[x][y - 1].setEnabled(false);
								Button[x][y - 1].setBackground(Color.white);
								if (Feld[x][y - 1] == 0) {
									Button[x][y - 1].setText(" ");
								}
							}
							if (x < 7 && y > 0) {// oben rechts
								Button[x + 1][y - 1].setText(Feld[x + 1][y - 1] + "");
								Button[x + 1][y - 1].setEnabled(false);
								Button[x + 1][y - 1].setBackground(Color.white);
								if (Feld[x + 1][y - 1] == 0) {
									Button[x + 1][y - 1].setText(" ");
								}
							}
							if (x > 0) {// mitte links
								Button[x - 1][y].setText(Feld[x - 1][y] + "");
								Button[x - 1][y].setEnabled(false);
								Button[x - 1][y].setBackground(Color.white);
								if (Feld[x - 1][y] == 0) {
									Button[x - 1][y].setText(" ");
								}
							}
							if (x < 7) {// mitte rechts
								Button[x + 1][y].setText(Feld[x + 1][y] + "");
								Button[x + 1][y].setEnabled(false);
								Button[x + 1][y].setBackground(Color.white);
								if (Feld[x + 1][y] == 0) {
									Button[x + 1][y].setText(" ");
								}
							}
							if (x > 0 && y < 7) {// unten links
								Button[x - 1][y + 1].setText(Feld[x - 1][y + 1] + "");
								Button[x - 1][y + 1].setEnabled(false);
								Button[x - 1][y + 1].setBackground(Color.white);
								if (Feld[x - 1][y + 1] == 0) {
									Button[x - 1][y + 1].setText(" ");
								}
							}
							if (y < 7) {// unten
								Button[x][y + 1].setText(Feld[x][y + 1] + "");
								Button[x][y + 1].setEnabled(false);
								Button[x][y + 1].setBackground(Color.white);
								if (Feld[x][y + 1] == 0) {
									Button[x][y + 1].setText(" ");
								}
							}
							if (x < 7 && y < 7) {// unten rechts
								Button[x + 1][y + 1].setText(Feld[x + 1][y + 1] + "");
								Button[x + 1][y + 1].setEnabled(false);
								Button[x + 1][y + 1].setBackground(Color.white);
								if (Feld[x + 1][y + 1] == 0) {
									Button[x + 1][y + 1].setText(" ");
								}
							}
							CheckWin();
						} else { // Nachbarfelderminenanzahl auf den Button hinzufügen und auf gewinnen prüfen
							Button[x][y].setText(Feld[x][y] + "");
							Button[x][y].setEnabled(false);
							CheckWin();
						}
					}
				}
			}
		}
	}
	// ------------------------------------------------------------------main Methode----------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		MineSweeper Fenster = new MineSweeper();
		Fenster.setSize(450, 650);
		Fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Fenster.setVisible(true);
	}
}