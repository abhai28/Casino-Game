import java.util.*;
/*
 * Author: Abhai Gill
 * Date: 2017/01/21
 * Description: This is used to get the value of the players and dealers hand.
 * 				It also inherits all the methods and data types of Hand class.
 * Method List: public Hand ()
 * 				public void clear()
 * 				public void addCard(Card c)
 * 				public void removeCard(Card c)
 * 				public void removeCard(int position)
 * 				public int getCardCount() 
 * 				public Card getCard(int position)
 * 				public static void main(String[] args)
 */
public class Hand {

	private Vector hand;   // Declaring vector variable hand for cards in the hand.

	public Hand()
	{
		// Create a Hand object that is initially empty.
		hand = new Vector();
	}

	public void clear() 
	{
		// Discard all the cards from the hand.
		hand.removeAllElements();
	}

	public void addCard(Card c)
	{
		// Add the card c to the hand.  c should be non-null.  (If c is
		// null, nothing is added to the hand.)
		if (c != null)
		{
			hand.addElement(c);
		}
	}

	public void removeCard(Card c)
	{
		// If the specified card is in the hand, it is removed.
		hand.removeElement(c);
	}

	public void removeCard(int position)
	{
		// If the specified position is a valid position in the hand,
		// then the card in that position is removed.
		if (position >= 0 && position < hand.size())
		{
			hand.removeElementAt(position);
		}
	}

	public int getCardCount() 
	{
		// used to return the number of cards in the hand.
		return hand.size();
	}

	public Card getCard(int position)
	{
		/* 
		 * Gets the card from the hand in given position, where positions are numbered starting from 0.  
		 * If the specified position is not the position number of a card in the hand, then null is returned.
		 */
		if (position >= 0 && position < hand.size())
		{
			return (Card)hand.elementAt(position);
		}
		else
		{
			return null;
		}
	}

	// Self Testing Method
	public static void main (String args [])
	{
		Blackjack hand = new Blackjack();
		Deck deck = new Deck ();
		Card value = null;
		int val;
		// Testing getCard method and add card method
		deck.shuffle();
		hand.addCard(deck.dealCard());
		int i = 0;
		value = hand.getCard(i);  
		System.out.println(value);

		// Testing getCardCount method 
		val = hand.getCardCount();
		System.out.println("Amount of cards before remove card: " + val);

		// Testing removeCard method
		hand.removeCard(0);

		// Testing getCardCount method
		val = hand.getCardCount();
		System.out.println("Amount of cards after remove card: " + val);

	}

}