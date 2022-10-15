package model;

public class Player {
    private String name, mark;
    private boolean hasTurn;
    private int points;

    public Player(String name, String mark) {
        this.name = name;
        this.mark = mark;
        this.hasTurn = false;
        this.points = 0;
    }

    public Player(String name, String mark, boolean starts) {
        this.name = name;
        this.mark = mark;
        this.hasTurn = starts;
        this.points = 0;
    }

    public boolean hasTurn() {
        return this.hasTurn;
    }

    public void setTurn(boolean turn) {
        this.hasTurn = turn;
    }

    public String getMark() {
        return this.mark;
    }

    public String getName() {
        return this.name;
    }
}
