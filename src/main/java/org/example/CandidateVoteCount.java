package org.example;

public class CandidateVoteCount {
    private String candidateName;
    private long validVoteCount;

    public CandidateVoteCount(String candidateName, long validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public long getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(long validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public String toString() {
        return
                candidateName + "= " + validVoteCount;
    }
}
