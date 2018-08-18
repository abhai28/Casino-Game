/*
 * Author: Abhai Gill
 * Date: 2017/01/21
 * Description: This is used to get the value of the players and dealers hand.
 * 				It also inherits all the methods and data types of Hand class.
 * Method List: public Deck ()
 * 				public static void main(String[] args)
 * 				public void shuffle ()
 * 				public int cardsLeft() 
 * 				public Card dealCard()
 */
public class Deck {

	private Card[] deck;   // array of 52 Cards, representing the deck.
	private int cardsUsed; // Declaring Integer variable for amount of cards that have been dealt from the deck.

	public Deck() {
		// Creates an unshuffled deck of cards.
		deck = new Card[52];
		int cardCt = 0; // Integer Variable for how many cards that have been created
		for ( int suit = 0; suit <= 3; suit++ ) 
		{
			for ( int value = 1; value <= 13; value++ ) 
			{
				deck[cardCt] = new Card(value,suit);
				cardCt++;
			}
		}
		cardsUsed = 0;
	}

	public void shuffle() 
	{
		// puts all the cards back into the deck and shuffles it in a random order using Math.random
		for ( int i = 51; i > 0; i-- ) 
		{
			int rand = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}

	public int cardsLeft() 
	{
		/*
		 * As cards are dealt from the deck, the number of cards left decreases.  
		 * This function returns the number of cards that are still left in the deck.
		 */
		return 52 - cardsUsed;
	}

	public Card dealCard() 
	{
		// Shuffles the deck and deals one card from the deck and returns it.
		if (cardsUsed == 52)
		{
			shuffle();
		}
		cardsUsed++;
		return deck[cardsUsed - 1];
	}

	// Self Testing Method
	public static void main (String args[])
	{
		Deck deck = new Deck ();
		Card value;
		deck.shuffle();
		value = deck.dealCard();
		System.out.println(value);

		int value2;
		value2 = deck.cardsLeft();
		System.out.println("Cards left: " + value2);


	}
} // end class Deck