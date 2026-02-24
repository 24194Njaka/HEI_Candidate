package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
//        Q1
//        long totaVotes = dataRetriever.countAllVotes();
//        System.out.println("TotalVotes: "+ totaVotes);


        List<VoteTypeCount> results = dataRetriever.countVotesByType();

        System.out.println(results);






    }
}