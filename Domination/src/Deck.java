import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> cards;
    private Random randomGen;

    public Deck() {
        cards = new ArrayList<Card>();

        // initialize randomGen here to just do it once?
        this.randomGen = new Random();

        for (int i = 2; i < 15; i++) {
            // iterate from values 2 to 14
            Card h = new Card(i, "Hearts");
            Card d = new Card(i, "Diamonds");
            Card s = new Card(i, "Spades");
            Card c = new Card(i, "Clubs");

            cards.add(h);
            cards.add(d);
            cards.add(s);
            cards.add(c);
        }

    }

    public int getSize() {
        return (cards.size());
    }

    public Card drawRandom() {
        int randomIndex = randomGen.nextInt(this.getSize());
        return (cards.remove(randomIndex));
    }

    public Card drawTop() {
        return (cards.remove(0));
    }

}
