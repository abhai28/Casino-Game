/*
 * Author: Abhai Gill
 * Date: 2017/01/21
 * Description: This is used to get the value of the players and dealers hand.
 * 				It also inherits all the methods and data types of Hand class.
 * Method List: public Card(int theValue, int theSuit)
 * 				public int getSuit()
 * 				public int getValue() 
 * 				public String getSuitAsString() 
 * 				public String getValueAsString() 
 * 				public static void main(String[] args)
 */

public class Card
{
	// Declaring integer vriable which contain the values of the suits.
	public final static int SPADES = 0, HEARTS = 1, DIAMONDS = 2, CLUBS = 3;

	// Declaring integer variable which contain the values of the non-numeric cards.
	public final int ACE = 1,JACK = 11, QUEEN = 12, KING = 13;

	// Declaring integer variable for suits, SPADES, HEARTS, DIAMONDS, CLUBS.
	private final int suit;

	// Declaring integer variable for the value of card from 1 to 13
	private final int value;

	public Card(int theValue, int theSuit)
	{
		/* 
		 * Constructs a card with a specific value and suit.
		 * Value is between 1 and 13.  Suit must be between 0 and 3.  
		 * If they are outside of the ranges specified, the constructed card object will be invalid.
		 */
		value = theValue;
		suit = theSuit;
	}

	public int getSuit()
	{
		// Returns value of the card's suit.
		return suit;
	}

	public int getValue() 
	{
		// Returns the value of the card.
		return value;
	}

	public String getSuitAsString() 
	{
		// Returns a String representation of the card's suit, and if the suit is invalid it will return a ??.
		switch ( suit )
		{
		case SPADES:   return "Spades";
		case HEARTS:   return "Hearts";
		case DIAMONDS: return "Diamonds";
		case CLUBS:    return "Clubs";
		default:       return "??";
		}
	}

	public String getValueAsString() 
	{
		// Returns a String representation of the card's value, and if the value is invalid it will return a ??.
		switch ( value ) {
		case 1:  
		{
			return "Ace";
		}
		case 2:   
		{
			return "2";
		}
		case 3:
		{
			return "3";
		}
		case 4:  
		{
			return "4";
		}
		case 5:   
		{
			return "5";
		}
		case 6:  
		{
			return "6";
		}
		case 7:   
		{
			return "7";
		}
		case 8:  
		{
			return "8";
		}
		case 9:  
		{
			return "9";
		}
		case 10:  
		{
			return "10";
		}
		case 11:  
		{
			return "Jack";
		}
		case 12: 
		{
			return "Queen";
		}
		case 13: 
		{
			return "King";
		}
		default:  
		{
			return "??";
		}
		}
	}

	// Self Testing Method
	public static void main (String args [])
	{
		Card card = new Card(13, 2);

		// Testing getSuit
		System.out.println("Testing getSuit");
		System.out.println(card.getSuit());

		// Testing getValue
		System.out.println("Testing getValue");
		System.out.println(card.getValue());

		// Testing getValueAsString and getSuitAsString
		System.out.println("Testing getValueAsString and getSuitAsString");
		System.out.println(card.getValueAsString() + " of " + card.getSuitAsString());

	}
} // end class Card