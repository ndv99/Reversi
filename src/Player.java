class Player {

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

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}
