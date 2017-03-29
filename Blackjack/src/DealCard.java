
import java.util.*;
public class DealCard
{
    ArrayList<String> cards = new ArrayList<String>();//array list to hold completed deck
    String[] cardNumber = {"Ace","2","3","4","5","6","7","8","9","10","Jack", "Queen","King"};
    String[] cardSuit = {"Hearts","Clubs","Diamonds","Spades"};
    Random random = new Random();
    int playerScore =0;
    int aiScore = 0;
    int wins = 0;
    int losses = 0;
    int aces =0;
    int acesUsed=0;
    /**
     * Constructor for objects of class DealCard
     */
    public DealCard()
    {
        makeDeck();//creates a new deck on startup
    }
    private void makeDeck()
    {
        for (int i=0;i<cardSuit.length;i++)//for all card suits
        {
            for (int j=0;j<cardNumber.length;j++)//for all card numbers
            {
                String newCard = cardNumber[j]+ " of " + cardSuit[i];//create a card by combining suit and number strings
                cards.add(newCard);//add to an arraylist
                //count++;
            }
        }
        //System.out.println(cards);//used for testing array
    }
    public String dealCard()
    {
        int randIndex = (int)(Math.random()*cards.size());//use a random number for index up to deck size
        if (!cards.isEmpty())//for non-empty array deck
        {
            if(cards.get(randIndex)!= null)//if the returned card is non-null
            {   String card = cards.get(randIndex);//use the returned card
                cards.remove(card);//remove the card from the arraylist
                cards.trimToSize();//resize arraylist
                System.out.println(card);
                return card;//print out and return the selected card
            }
        }
        else
        {
            System.out.println("No more cards");
        }
        return "No more cards";
    }
    public void dealCards(int number)
    {
        for(int i=0;i<number;i++)//deal the specified number of cards
        {
            dealCard();
        }
        System.out.println("Dealt "+number+" cards.");
    }
    public void newDeck()//empty the cards arraylist and fill it with a complete set of cards.
    {
        cards.clear();
        makeDeck();
    }
    private int checkValue(String cardName)//Finds the value of the card
    {
        int nextSpace = cardName.indexOf(" ");//Find the location of the first space
        String number = cardName.substring(0,nextSpace);//Take the first word of the card name and call it "number"
        number = number.trim();//remove any extra spaces (may not be needed here)
    
        if (number.equals("Ace"))//if the card is an Ace sets the value to 1
        {
            int value = 1;
            return value;
        }
        else if (number.equals("Jack")|| number.equals("Queen")||number.equals("King"))//if the card is a picture card, it has value 10
        {
            int value = 10;
            return value;
        }
        else//if the card has a number, convert it into an integer
        {
            int value = Integer.parseInt(number);
            return value;
        }
    }
    private int dealAndValue()
    {
        String card1 = dealCard();
        int value1 = checkValue(card1);
        if(value1==1)
        {
            aces++;
        }
        return value1;
    }
    private int dealAndValue2()//deals and finds the value of 2 dealt cards
    {
        int score = dealAndValue();
        score = score + dealAndValue();
        return score;
    }
    private int acesCalc(int score, int numberAces)//score will be corrected for the number of aces in hand
    {
        for(int i=0; i<numberAces;i++)//for each ace in hand
        {
            if(score+10<22)//if the score would stay under the limit, the ace is treated as 11
            {
                score=score+10;
                acesUsed++;//track the number of aces used as 11
            }
            else if(acesUsed>0 && score>21)//if the score is above 21 and an ace is currently being used as 11
            {
                score = score-10;//treat the ace as value 1
                acesUsed--;//remove one ace usage
            }
            else
            {
                return score;//return the score unchanged if aces are not (un)used
            }
        }
        return score;
    }
    public void startGame()
    {
        Scanner s = new Scanner(System.in);
        boolean playing=true;
        while(playing==true)//while player continues to play
        {
            playerScore = dealAndValue();
            playerScore = playerScore + dealAndValue();//deals 2 cards and returns the combined value
            playerScore = acesCalc(playerScore, aces);//performs corrections to score for aces
            System.out.println("Your current total is: "+ playerScore);
            String input = null;
            boolean stick = false;
            while(stick==false)//continue to play until the player sticks or goes over 21
            {
                System.out.println("Stick or Twist? s/t");
                boolean badChoice = true;
                while(badChoice == true)
                {
                    input = s.nextLine();
                    if(input.equalsIgnoreCase("s"))
                    {
                        badChoice = false;
                        stick = true;
                        break;
                    }
                    else if(input.equals("t"))
                    {
                        badChoice=false;    
                        playerScore = playerScore + dealAndValue();
                        playerScore = acesCalc(playerScore, aces);
                        System.out.println("Your current total is: " + playerScore);
                        if(playerScore>=21)
                        {
                            stick=true;
                        }
                    }    
                
                    else
                    {
                        System.out.println("Try again. Type 's' for Stick or 't' for twist");
                    }
                }
            }
            aces=0;//reset the number aces used to 0
            acesUsed=0;
            System.out.println("The computer draws two cards");
            aiScore = dealAndValue();//deal and value 2 cards
            aiScore = aiScore + dealAndValue();
            aiScore = acesCalc(aiScore, aces);//correcting for number of aces in hand
            while(aiScore<13)//the computer will continue to twist if its current score is <13 (it can only go bust on 22)
            {
                System.out.println("The computer twists");
                aiScore=aiScore+dealAndValue();
                aiScore = acesCalc(aiScore, aces);
            }
            System.out.println("The computer scores " + aiScore);
            if(aiScore>21)
            {
                System.out.println("The computer went bust");
                System.out.println("You win");
                wins++;
            }
            else if(playerScore>21)
            {
                System.out.println("You went bust");
                System.out.println("You lose");
                losses++;
            }
            else if(playerScore>aiScore)
            {
                System.out.println("You win");
                wins++;
            }
            else if (playerScore<aiScore)
            {
                System.out.println("You lose");
                losses++;
            }
            else if (playerScore==aiScore)
            {
                System.out.println("It's a draw");
            }
            System.out.println("Play again? y/n");
            boolean badChoice = true;
            while(badChoice==true)
            {
                input = s.nextLine();
                if(input.equalsIgnoreCase("y"))
                {
                    badChoice=false;
                    System.out.print('\f');
                    System.out.println("Your current score is: " + wins+" - "+losses);
                    aces=0;
                    acesUsed=0;
                    newDeck();
                    break;
                }
                else if(input.equalsIgnoreCase("n"))
                {
                    badChoice=false;
                    playing=false;
                    System.out.println("The final score is: " + wins+" - "+losses);
                    break;
                }
                else
                {
                    System.out.println("Try again. Type 'y' or 'n'");
                }
            }
        }
    }
}
