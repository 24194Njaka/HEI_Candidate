package org.example;

import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        long totaVotes = dataRetriever.countAllVotes();
        System.out.println("TotalVotes: "+ totaVotes);




    }
}