package org.example;

public class VoteTypeCount {
    private String voteType;
    private int count;

    public VoteTypeCount(String voteType, int count) {
        this.voteType = voteType;
        this.count = count;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "VoteTypeCount{" +
                "voteType='" + voteType + '\'' +
                ", count=" + count +
                '}';
    }
}
