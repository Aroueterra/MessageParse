package com.finastra;

import com.finastra.domain.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            var content = readFile("TankFile2019.txt");
            announce(content);
            System.out.println("Operation Complete [" + content.size() + "] messages processed");
        }catch (IOException ex){
            System.out.println("No file in path!");
        }
    }
    public static void announce(List<Transaction> transactionList){
        int counter = 1;
        for (var transaction: transactionList) {
            System.out.println("-----------------");
            System.out.println("Message " + counter++);
            System.out.println("-----------------");
            System.out.println(transaction.toString());
        }
    }
    public static List<Transaction> readFile(String path) throws IOException {
        // Read file by class
        RegexUtil util = new RegexUtil();
        InputStream uri = Main.class.getResourceAsStream(path);
        var reader = new BufferedReader(new InputStreamReader(uri));
        String lines = reader.readLine();
        List<Transaction> transactionList = new ArrayList<>();
        /*
         * Initial transaction, in test, initial message has no $ sign
         */
        Transaction transaction = new Transaction();
        do {
            if((lines) != null){
                if(lines.contains("$") && !transaction.getSender().isBlank()){
                    transactionList.add(transaction);
                    transaction = new Transaction();
                }
                var checkSender = util.checkSender(lines);
                /*
                 *  A sender and receiver always appear in the same line, we check it together
                 */
                if(!checkSender.isBlank()){
                    transaction.setSender(checkSender);
                    var checkReceiver = util.checkReceiver(lines);
                    try{
                        if (!checkReceiver.isEmpty()) {
                            transaction.setReceiver(checkReceiver.get("receiver"));
                            transaction.setPriority(checkReceiver.get("priority"));
                        }
                    } catch (NullPointerException ignored){}
                }
                /*
                 *  Multiple reference numbers can be associated to a single transaction
                 */
                else{
                    var checkRef = util.checkRefNo(lines);
                    if(!checkRef.isBlank()){
                        transaction.setRefNo(checkRef);
                    }
                }
            }
        } while((lines=reader.readLine()) != null);
        // Catch and append final transaction without $ sign delimiter
        if(!transaction.getSender().isBlank()){
            transactionList.add(transaction);
        }
        return transactionList;
    }
}
