package com.example.reversequestions;

public class Score implements Comparable{
    private String name;
    private int score;
    private int time;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }



    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        int score=((Score)o).getScore();
        /* For Ascending order*/
        return this.score-score;
    }
}
