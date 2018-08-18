import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.text.NumberFormat;
import javax.imageio.*;
import javax.swing.*;

/*
 * Author: Abhai Gill
 * Date: 2017/01/21
 * Description: This is the GUI for blackjack, it lets the user play blackjack, and bet money which they can double or lose.
 * 				Also has the regular blackjack commands which are hit and stand. 
 * 				Has buttons to go back home, hit, stand and start a new game.
 * Method List: BlackJackGUI()
 * 				public static void main(String[] args)
 * 				public void hit()
 * 				public void stand()
 * 				public void newGame()
 * 				public void actionPerformed(ActionEvent e) 
 * 				public void paint (Graphics g)		
 * 				public void drawCard(Card card, Graphics g, int x, int y)	
 */
public class BlackJackGUI extends JFrame implements ActionListener
{
	// Private data
	// Declaring Panel
	private JPanel contentPane;
	// Label for the GUI
	private JLabel msg, valueMSG, money, background, iconPanel, name;
	// Buttons for home, hit, stand, new game, bet, and all the fixed betting values
	private JButton btnHome, btnHit, btnStand, btnNewGame,betBtn, $1btn, $5btn, $10btn, $25btn, $50btn, $100btn;
	// Contains dealers cards
	private Blackjack dealerHand;
	// Contains players cards
	private Blackjack playerHand; 
	// Contains the deck of cards which will be used in the game
	private Deck deck;       
	// Set to true if the game is in progress and to false when there is no game being played
	private boolean gameInProgress;
	// Holder Variables For the Parameters
	// Double
	private double amountBet, accountBalHolder = 100;
	// String
	private String message, userName;
	// Used to get the input of amount of money user wants to bet
	private JTextField betInput;
	//Number Formatter
	private NumberFormat nf;
	//Player account 
	private PlayerAccount playerHolder, playerEdited;
	//player list object to change the file whenever money is lost or bet
	private PlayerList list;
	public static void main(String[] args) throws IOException 
	{
		PlayerAccount player = new PlayerAccount ("John","35th Street","9056661239","password",1000,"1.png");
		BlackJackGUI frame = new BlackJackGUI(player);
	}

	public BlackJackGUI(PlayerAccount player) throws IOException
	{
		//make the title be Slot Machine Game
		super ("Blackjack Game");
		// setting the size and layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 20, 1000, 700);
		setResizable (false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nf = NumberFormat.getCurrencyInstance();
		nf.setMaximumFractionDigits(2);

		//set list to the file 
		list = new PlayerList();
		list.readFromFile("Players.txt");
		
		//holder global variable so that other methods can access the account balance
		playerHolder = player;
		playerEdited = player;
		accountBalHolder = player.getPlayerBalance();
		userName = player.getPlayerName();

		/*
		 *  Create buttons, labels, and text boxes.
		 *  Also sets it coordinates and adds it to the frame
		 */
		btnNewGame = new JButton("New Game\r\n");
		btnNewGame.setFont(new Font("Charlemagne Std", Font.BOLD, 18));
		btnNewGame.setForeground(Color.BLACK);
		btnNewGame.setBackground(Color.WHITE);
		btnNewGame.setBounds(839, 180, 155, 29);
		contentPane.add(btnNewGame);

		btnStand = new JButton("Stand");
		btnStand.setBackground(Color.WHITE);
		btnStand.setFont(new Font("Charlemagne Std", Font.BOLD, 20));
		btnStand.setForeground(Color.BLACK);
		btnStand.setBounds(881, 417, 113, 29);
		contentPane.add(btnStand);

		btnHit = new JButton("Hit");
		btnHit.setFont(new Font("Charlemagne Std", Font.BOLD, 25));
		btnHit.setForeground(Color.BLACK);
		btnHit.setBackground(Color.WHITE);
		btnHit.setBounds(881, 305, 113, 29);
		contentPane.add(btnHit);

		$1btn = new JButton("$1\r\n");
		$1btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$1btn.setForeground(Color.BLACK);
		$1btn.setBackground(Color.WHITE);
		$1btn.setBounds(66, 625, 69, 35);
		contentPane.add($1btn);

		$5btn = new JButton("$5\r\n");
		$5btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$5btn.setForeground(Color.BLACK);
		$5btn.setBackground(Color.WHITE);
		$5btn.setBounds(145, 625, 75, 34);
		contentPane.add($5btn);

		$10btn = new JButton("$10\r\n");
		$10btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$10btn.setForeground(Color.BLACK);
		$10btn.setBackground(Color.WHITE);
		$10btn.setBounds(230, 625, 91, 34);
		contentPane.add($10btn);

		$25btn = new JButton("$25\r\n");
		$25btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$25btn.setForeground(Color.BLACK);
		$25btn.setBackground(Color.WHITE);
		$25btn.setBounds(331, 625, 91, 34);
		contentPane.add($25btn);

		$50btn = new JButton("$50");
		$50btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$50btn.setForeground(Color.BLACK);
		$50btn.setBackground(Color.WHITE);
		$50btn.setBounds(432, 625, 91, 35);
		contentPane.add($50btn);

		$100btn = new JButton("$100");
		$100btn.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 25));
		$100btn.setForeground(Color.BLACK);
		$100btn.setBackground(Color.WHITE);
		$100btn.setBounds(533, 625, 103, 35);
		contentPane.add($100btn);

		betBtn = new JButton("Bet");
		betBtn.setFont(new Font("Charlemagne Std", Font.BOLD, 25));
		betBtn.setForeground(Color.BLACK);
		betBtn.setBackground(Color.WHITE);
		betBtn.setBounds(871, 625, 91, 35);
		contentPane.add(betBtn);

		btnHome = new JButton("Home\r\n");
		btnHome.setFont(new Font("Charlemagne Std", Font.BOLD, 25));
		btnHome.setForeground(Color.BLACK);
		btnHome.setBackground(Color.WHITE);
		btnHome.setBounds(864, 512, 130, 40);
		contentPane.add(btnHome);

		betInput = new JTextField();
		betInput.setBounds(796, 633, 69, 20);
		contentPane.add(betInput);
		betInput.setColumns(10);

		name = new JLabel(userName);
		name.setBounds(10, 67, 125, 14);
		contentPane.add(name);

		iconPanel = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource(player.getIconNumber()))));
		iconPanel.setBounds(6,6,62,48);
		contentPane.add(iconPanel);

		money = new JLabel(nf.format(accountBalHolder));
		money.setBounds(10, 92, 125, 14);
		contentPane.add(money);

		valueMSG = new JLabel();
		valueMSG.setFont(new Font("Charlemagne Std", Font.BOLD, 20));
		valueMSG.setForeground(Color.BLACK);
		valueMSG.setBounds(88, 531, 46, 20);
		contentPane.add(valueMSG);

		msg = new JLabel();
		msg.setFont(new Font("Charlemagne Std", Font.BOLD, 20));
		msg.setForeground(Color.BLACK);
		msg.setBounds(172, 346, 658, 50);
		contentPane.add(msg);

		JLabel amtLabel = new JLabel("Custom Amount\r\n");
		amtLabel.setFont(new Font("Charlemagne Std", Font.PLAIN, 15));
		amtLabel.setForeground(Color.BLACK);
		amtLabel.setBounds(638, 627, 167, 30);
		contentPane.add(amtLabel);

		JLabel background = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("Blackjack.png"))));
		background.setBounds(0, 0, 1000, 700);
		contentPane.add(background);

		// makes the buttons listen to the clicks on the program
		btnHit.addActionListener(this);
		btnStand.addActionListener(this);
		btnNewGame.addActionListener (this);
		btnHome.addActionListener(this);
		betBtn.addActionListener (this);
		$1btn.addActionListener (this);
		$5btn.addActionListener (this);
		$10btn.addActionListener (this);
		$25btn.addActionListener (this);
		$50btn.addActionListener (this);
		$100btn.addActionListener (this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		// if the hit button is pressed it will call the hit() method
		if (e.getSource () == btnHit)
		{
			hit();
		}
		// if the stand button is pressed it will call the stand() method
		else if (e.getSource () == btnStand)
		{
			stand();
		}
		/*
		 *  if the new game button is pressed it will call the newGame() method
		 *  Also repaint the frame
		 */
		else if (e.getSource () == btnNewGame)
		{
			btnStand.setEnabled(false);
			btnHit.setEnabled(false);
			amountBet = 0;
			getContentPane().repaint();
			newGame();
		}
		// if the bet button is pressed it will get the text from the bet text field and set that as the value of amount bet
		else if (e.getSource() == betBtn)
		{
			double bet = Double.parseDouble(betInput.getText());
			if (((accountBalHolder - bet) < 0) == false)
			{
				amountBet = Integer.parseInt(betInput.getText());
				if (amountBet < 1)
				{
					message = "Please bet more than $1";
					amountBet = 0;
					repaint();
				}
				else
				{
					btnStand.setEnabled(true);
					btnHit.setEnabled(true);
					playerEdited.withdraw(amountBet);
					list.insertion();
					list.change(playerHolder, playerEdited);
					try {
						list.saveToFile("Players.txt");
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					accountBalHolder -= amountBet;
					money.setText(nf.format(accountBalHolder));
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account");
			}
		}
		// if the $1 button is pressed it will set the value of amount bet as 1
		else if (e.getSource() == $1btn)
		{
			if (((accountBalHolder -1) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 1;
				playerEdited.withdraw(1);
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 1;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account");
			}
		}
		// same as $1 button but value set will be 5
		else if (e.getSource() == $5btn)
		{
			if (((accountBalHolder -5) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 5;
				playerEdited.withdraw(5);
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 5;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account!");
			}
		}
		// same as $1 button but value set will be 10
		else if (e.getSource() == $10btn)
		{
			if (((accountBalHolder -10) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 10;
				playerEdited.withdraw(10);
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 10;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account!");
			}
		}
		// same as $1 button but value set will be 25
		else if (e.getSource() == $25btn)
		{
			if (((accountBalHolder -25) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 25;
				playerEdited.withdraw(25);
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 25;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account!");
			}
		}
		// same as $1 button but value set will be 50
		else if (e.getSource() == $50btn)
		{
			if (((accountBalHolder -50) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 50;
				playerEdited.withdraw(50);
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 50;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account!");
			}
		}
		// same as $1 button but value set will be 100
		else if (e.getSource() == $100btn)
		{
			if (((accountBalHolder -100) < 0) == false)
			{
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				accountBalHolder -= 100;
				playerEdited.withdraw(100);
				
				list.insertion();
				list.change(playerHolder, playerEdited);
				try {
					list.saveToFile("Players.txt");
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				money.setText(nf.format(accountBalHolder));
				amountBet += 100;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Not enough money in account");
			}
		}
		// if the home button is pressed, it will close blackjack graphical user interface and load up main menu graphical user interface 
		else if (e.getSource() == btnHome)
		{
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				MainMenu menu = new MainMenu(playerEdited);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setVisible (false);
		}
	}
	public void hit() 
	{
		/* This method is called when the user clicks the hit button.
		 * It begins by checking if the game is in progress.
		 * If the game is not in progress it lets the user know to 
		 * press button "New Game" and exits  
		 * Otherwise, it will deal the user its starting two cards.
		 * The game will end if the user gets a blackjack or
		 * if the user takes 5 cards without going over 21.
		 */
		if (gameInProgress == false)
		{
			message = ("Click \"New Game\" to start a new game.");
			return;
		}
		playerHand.addCard( deck.dealCard() );
		if ( playerHand.getBlackjackValue() > 21 ) 
		{
			message = ("You've busted!  Sorry, you lose.");
			gameInProgress = false;
		}
		else if (playerHand.getCardCount() == 5)
		{
			message = ("You win by taking 5 cards without going over 21.");	
			gameInProgress = false;
			playerEdited.deposit(amountBet*2); 
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			money.setText(nf.format(accountBalHolder));
		}
		else if (playerHand.getBlackjackValue() == 21)
		{
			message = ("You win by scoring blackjack");
			playerEdited.deposit(amountBet*2);
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			accountBalHolder += (amountBet*2);
			money.setText(nf.format(accountBalHolder));
			gameInProgress = false;
		}
		else 
		{
			message = ("You have " + playerHand.getBlackjackValue() + ".  Hit or Stand?");
		}
		repaint();
	}


	public void stand()
	{
		/* This method is called when the user clicks the "Stand!" button.
		 * Checks if there is a game in progress or not  
		 * If there is a game in progress it will end the game.
		 * The dealers takes it's cards until it has either more than 16 as the value of the cards or has 5.
		 * At the end winner of the game is determined, based on who has cards which total up to a greater value,
		 * or if any has 5 cards without going over 21, and if the dealer has busted.
		 * Also based on if the user wins or losses deducts or adds money to the users account.
		 */
		if (gameInProgress == false)
		{
			message = ("Click \"New Game\" to start a new game.");
			repaint();
			return;
		}
		gameInProgress = false;
		while (dealerHand.getBlackjackValue() <= 16 && dealerHand.getCardCount() < 5)
		{
			dealerHand.addCard( deck.dealCard() );
		}
		if (dealerHand.getBlackjackValue() > 21)
		{
			message = ("You win!  Dealer has busted with " + dealerHand.getBlackjackValue() + ".");
			playerEdited.deposit(amountBet*2);    
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			accountBalHolder += (amountBet*2);
			money.setText(nf.format(accountBalHolder));
		}
		else if (dealerHand.getCardCount() == 5)
		{
			message = ("Sorry, you lose.  Dealer took 5 cards without going over 21.");
		}
		else if (dealerHand.getBlackjackValue() > playerHand.getBlackjackValue())
		{
			message = ("Sorry, you lose, " + dealerHand.getBlackjackValue() + " to " + playerHand.getBlackjackValue() + ".");
		}
		else if (dealerHand.getBlackjackValue() == playerHand.getBlackjackValue())
		{
			message = ("Draw");
			playerEdited.deposit(amountBet);
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			accountBalHolder += amountBet;
			money.setText(nf.format(accountBalHolder));
		}
		else
		{
			message = ("You win, " + playerHand.getBlackjackValue() + " to " + dealerHand.getBlackjackValue() + "!");
			playerEdited.deposit(amountBet*2);
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			accountBalHolder += (amountBet*2);
			money.setText(nf.format(accountBalHolder));
		}
		repaint();
	}


	public void newGame()
	{
		/* Called by ActionPerformedif the use clicks the "New Game" button.  
		 * This starts a new game.
		 * Deals two cards to each player. 
		 * If one of the players has blackjack, the game ends. 
		 * Otherwise, gameInProgress is set to true and the game begins.
		 * Also based on if the user wins or losses deducts or adds money to the users account.
		 */
		if (gameInProgress)
		{
			message = ("Game is still in progress.");
			repaint();
			return;
		}
		JOptionPane.showMessageDialog(null, "Welcome To Blackjack");
		deck = new Deck();
		dealerHand = new Blackjack();
		playerHand = new Blackjack();
		deck.shuffle();
		dealerHand.addCard( deck.dealCard() ); 
		dealerHand.addCard( deck.dealCard() );
		playerHand.addCard( deck.dealCard() );
		playerHand.addCard( deck.dealCard() );
		if (dealerHand.getBlackjackValue() == 21) 
		{
			message = ("Sorry, you lose. Dealer has Blackjack.");
			gameInProgress = false;
		}
		else if (playerHand.getBlackjackValue() == 21) 
		{
			message = ("You win!  You have Blackjack.");
			accountBalHolder += (amountBet*2);
			money.setText(nf.format(accountBalHolder));
			playerEdited.deposit(amountBet*2);
			list.insertion();
			list.change(playerHolder, playerEdited);
			try {
				list.saveToFile("Players.txt");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			gameInProgress = false;
		}
		else
		{
			message = ("You have " + playerHand.getBlackjackValue() + ".  Hit or stand?");
			gameInProgress = true;
		}
		repaint();
	}

	// Draws the dealers and players cards
	public void paint(Graphics g) 
	{
		try 
		{
			if (gameInProgress)
			{
				drawCard(null, g, 195, 235);
			}
			else
			{
				drawCard(dealerHand.getCard(0), g, 180, 213);
			}
			for (int i = 1; i < dealerHand.getCardCount(); i++)
			{
				drawCard(dealerHand.getCard(i), g, 180 + i * 137, 213);
			}
			for (int i = 0; i < playerHand.getCardCount(); i++)
			{
				drawCard(playerHand.getCard(i), g, 180 + i * 137, 475);
			} 
			valueMSG.setText (String.valueOf(playerHand.getBlackjackValue()));
			msg.setText(message);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "File not found");
		}

	}  // end paint()

	/* 
	 * Returns a picture of the back side of a card for dealers first card and 
	 * returns the image of the card that the player or dealer got
	 */
	public void drawCard(Card card, Graphics g, int x, int y) throws IOException 
	{
		if (card == null) 
		{  
			g.setColor(Color.blue);
			g.fillRect(x,y,80,100);
			g.setColor(Color.white);
			g.drawRect(x+3,y+3,73,93);
			g.drawRect(x+4,y+4,71,91);
		}
		else 
		{
			BufferedImage img = null;
			if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "2")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("2OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "3")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("3OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "4")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("4OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "5")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("5OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "6")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("6OfDiamonds.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "7")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("7OfDiamonds.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "8")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("8OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "9")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("9OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "10")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("10OfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "Jack")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("JOfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "Queen")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("QOfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString() == "King")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("KOfDia.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "Ace")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("AceOfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "2")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("2OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "3")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("3OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "4")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("4OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "5")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("5OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "6")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("6OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "7")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("7OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "8")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("8OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "9")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("9OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "10")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("10OfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "Jack")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("JOfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "Queen")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("QOfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.HEARTS && card.getValueAsString() == "King")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("KOfHea.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "Ace")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("AceOfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "2")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("2OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "3")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("3OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "4")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("4OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "5")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("5OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "6")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("6OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "7")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("7OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "8")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("8OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "9")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("9OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "10")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("10OfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "Jack")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("JOfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "Queen")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("QOfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.SPADES && card.getValueAsString() == "King")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("KOfSpa.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "Ace")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("AOfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "2")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("2OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "3")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("3OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "4")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("4OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "5")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("5OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "6")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("6OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "7")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("7OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "8")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("8OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "9")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("9OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "10")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("10OfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "Jack")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("JOfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "Queen")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("QOfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}
			else if (card.getSuit() == Card.CLUBS && card.getValueAsString() == "King")
			{
				try
				{
					img = ImageIO.read(getClass().getResource("KOfClu.png"));
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
				g.drawImage(img, x, y, null);
			}			
		}
	}  // end drawCard()
}
