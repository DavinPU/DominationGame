public class Card {
    private int value;
    private String suit;
    private String name;

    public Card (int value, String suit) {
        // value should range from 2 to 14 with 2 being spy and 14 being ace

        this.value = value;
        this.suit = suit;

        String facename = null;

        if (value > 10) {
            if (value == 11) {
                facename = "Jack";
            } else if (value == 12) {
                facename = "Queen";
            } else if (value == 13) {
                facename = "King";
            } else if (value == 14) {
                facename = "Ace";
            } else {
                facename = "error, value above 14";
            }
        } else {
            facename = String.valueOf(value);
        }

        this.name = facename + " of " + suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public String getName() {
        return name;
    }

    public void printName() {
        System.out.println(name);
    }

    public int compareTo(Card other) {
        if (this.value == 2 && other.value > 10) {
            return 1;
            // two is special case here
        }

        if (other.value == 2 && this.value > 10) {
            return -1;
        }

        // if neither if statements are activated, then we just compare integers regularly
        return (Integer.compare(this.value, other.value));

    }

    public static void main(String[] args) {
        Card a = new Card(14, "Hearts");
        Card b = new Card(2, "Spades");

        a.printName();
        b.printName();

        System.out.println(a.compareTo(b));
    }
}
