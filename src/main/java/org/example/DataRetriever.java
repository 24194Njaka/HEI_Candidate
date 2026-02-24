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

     public VoteSummary computeVoteSummary(){
        String sql = """
            select count(case when vote_type = 'VALID' then 1 END) as valid_count,
                   count(case when vote_type= 'BLANK' then 1 END) as blank_count,
                   count( case when vote_type= 'NULL' then 1 END) as null_count from vote
        """;

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long validCount = resultSet.getLong("valid_count");
                long blankCount = resultSet.getLong("blank_count");
                long nullCount = resultSet.getLong("null_count");

                return new VoteSummary(validCount, blankCount, nullCount);

            }

        }catch (SQLException e){
            throw new RuntimeException(e);
            }
        return null;

     }

     public  double computeTurnoutRate() throws SQLException {
         String sql = """
                
                 SELECT
                    COUNT(DISTINCT v.voter_id) AS vote_count,
                    COUNT(e.id) AS total_voters
                FROM voter e
                LEFT JOIN vote v ON e.id = v.voter_id
   

            """;

        try(Connection connection = DBConnection.
              getConnection();
        PreparedStatement statement = connection.
              prepareStatement(sql);
        ResultSet resultSet =

             statement.executeQuery()) {

            if (resultSet.next()) {
                long votedCount = resultSet.getLong("vote_count");
                long totalVoters = resultSet.getLong("total_voters");

                if(totalVoters == 0) return 0.0;

                return ((double) totalVoters / votedCount) * 100;

            }

        } catch (SQLException e){
            throw new RuntimeException(e);
     }
        return 0.0;
     }


}
