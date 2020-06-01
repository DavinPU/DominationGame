import java.util.ArrayList;

public class PlayerDeck {

    private ArrayList<Card> cards;

    private int win = 0;

    public PlayerDeck() {
        this.cards = new ArrayList<Card>();
    }

    public int getSize() {
        return cards.size();
    }

    public Card drawTop() {
        return (cards.remove(0));
    }

    public void insert(Card c) {
        cards.add(c);
    }

    public void setWin() {
        this.win = 1;
    }

    public int getWin() {
        return this.win;
    }

    public void resetWin() {
        // reset win identifier after every round
        this.win = 0;
    }

}
