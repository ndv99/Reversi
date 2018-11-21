public class Player {

    private String name;
    private int score;

    Player(String name){
        this.setName(name);
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
