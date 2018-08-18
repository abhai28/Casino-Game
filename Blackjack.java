import java.io.IOException;

/*
 * Author: Abhai Gill
 * Date: 2017/01/21
 * Description: This is used to get the value of the players and dealers hand.
 * 				It also inherits all the methods and data types of Hand class.
 * Method List: getBlackjackValue()
 * 				public static void main(String[] args)
 */

public class Blackjack extends Hand
{
	public int getBlackjackValue() 
	{
		int hVal = 0;      // Declaring integer variable value for the hand.
		boolean ace = false;  // Declaring boolean variable ace with initial value false and is set to true if the hand contains an ace.
		int cards = getCardCount();;    // Declaring integer variable for number of cards in the hand.

		// adds the value of i to the value of card
		for ( int i = 0;  i < cards;  i++ )
		{			
			Card card;    // Declaring card variable
			int cardVal;  // Declaring integer variable for value of card
			card = getCard(i); // sets value of variable card by calling on the method getCard in hand class

			/*
			 * calls on method getValue in Hand class to get the value of variable card and
			 * sets value of variable cardVal to the value of variable card
			 */
			cardVal = card.getValue();   
			if (cardVal > 10) 
			{
				cardVal = 10;   // Sets the value of variable cardVal to 10 if the player or dealer has Jack, Queen, or King
			}
			if (cardVal == 1) 
			{
				ace = true; // Sets the state of boolean variable to true if the player or dealer has ace
			}
			hVal = hVal + cardVal;
		}

		/*
		 * if the dealer or player have an ace and if the value of ace is set to 10 
		 * and wont make the score go above 21, the new value of ace is set to 10
		 */

		if ( ace == true  &&  hVal + 10 <= 21 )
		{
			hVal = hVal + 10;
		}	
		return hVal;
	}  // end getBlackjackValue()


	// Self Testing method
	public static void main (String[] args)
	{
		Blackjack hand = new Blackjack();
		Deck deck = new Deck ();
		int value;
		deck.shuffle();
		hand.addCard(deck.dealCard());
		value = hand.getBlackjackValue();
		System.out.println(value);
	}

} // end class BlackjackHand
