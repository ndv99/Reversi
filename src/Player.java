import java.io.Serializable;

/**
 * Class for object Player. Stores name and score.
 */
class Player implements Serializable {
// 'Serializable' implementation means that the game object can be saved to a file (need since players are objects in game)
    private String name;
    private int score;

    /**
     *
     * @param name Name of player (string)
     */
    Player(String name){
        this.setName(name);
    }

    /**
     * Accessor for player name.
     * @return name of player (string)
     */
    String getName() {
        return name;
    }

    /**
     * Mutator for player name
     * @param name Name of player (string)
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for score
     * @return player score (int)
     */
    int getScore() {
        return score;
    }

    /**
     * Mutator for score
     * @param score Player score (int)
     */
    void setScore(int score) {
        this.score = score;
    }
}
