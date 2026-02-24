package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    public  long countAllVotes() {
        String sql = """
                SELECT count(id) as total_votes FROM vote
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong("total_votes");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


     public List<VoteTypeCount> countVotesByType(){
        List<VoteTypeCount> voteTypeCounts = new ArrayList<>();
        String sql = """
                SELECT vote_type, COUNT(id) as count FROM vote
                 GROUP BY vote_type
        """;
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String voteType = resultSet.getString("vote_type");
                int count = resultSet.getInt("count");

                voteTypeCounts.add(new VoteTypeCount(voteType, count));


            }

        } catch (SQLException e){
            throw new  RuntimeException(e);
        }
        return voteTypeCounts;
     }

     public List<CandidateVoteCount> countValidVotesByCandidate(){
        List<CandidateVoteCount> candidateVoteCounts = new ArrayList<>();

        String sql = """
                SELECT c.name AS candidate_name,
                       COUNT(v.candidate_id) AS valid_vote FROM candidate c
                LEFT JOIN vote v ON v.candidate_id = c.id and v.vote_type = 'VALID'
                GROUP BY c.name, c.id ORDER BY c.id;
         
                """;
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String candidateName = resultSet.getString("candidate_name");
                long validVoteCount = resultSet.getInt("valid_vote");

                candidateVoteCounts.add(new CandidateVoteCount(candidateName, validVoteCount));
            }

        } catch (SQLException e){
            throw new  RuntimeException(e);
        }
        return candidateVoteCounts;
     }





}
