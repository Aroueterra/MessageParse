package com.finastra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    Pattern senderPattern;
    Pattern nestedSenderPattern;
    Pattern receiverPattern;
    Pattern nestedReceiverPattern;
    Pattern referencePattern;
    Pattern referencePatternC;
    public RegexUtil(){
        this.senderPattern = Pattern.compile("(?<=1:).*?(?=})");
        this.nestedSenderPattern = Pattern.compile("SPX.{9}");
        this.receiverPattern = Pattern.compile("(?<=2:).*?(?=})");
        this.nestedReceiverPattern = Pattern.compile("(SPX.*)([a-zA-Z])$");
        this.referencePattern = Pattern.compile("^((:20:)(.*))");
        this.referencePatternC = Pattern.compile("^((:20C::))");
    }

    /**
     * Retrieves sender code and sender priority
     * @param text = tank file line
     * @return = sender code as String || empty
     */
    public String checkSender(String text){
        Matcher matcher = this.senderPattern.matcher(text);
        while(matcher.find()){
            Matcher message = this.nestedSenderPattern.matcher(matcher.group());
            if(message.find()){
                return message.group();
            }
        }
        return "";
    }

    /**
     * Retrieves receiver code and receiver priority
     * @param text = tank file line
     * @return = "receiver" && "priority || null
     */
    public Map<String, String> checkReceiver(String text){
        Matcher matcher = this.receiverPattern.matcher(text);
        while(matcher.find()){
            Matcher message = this.nestedReceiverPattern.matcher(matcher.group());
            if(message.find()){
                var pair = new HashMap<String, String>();
                pair.put("receiver", message.group(1));
                pair.put("priority", message.group(2));
                return pair;
            }
        }
        return null;
    }

    /**
     * Retrieves reference number group :20: or :20C::
     * @param text = tank file line
     * @return = :20: reference number || :20C:: reference number, as String || empty
     */
    public String checkRefNo(String text){
        Matcher matcher = this.referencePattern.matcher(text);
        Matcher matcherC = this.referencePatternC.matcher(text);
        if(matcher.find()){
            return matcher.group(3);
        }else if(matcherC.find()){
            int index = text.lastIndexOf('/');
            return text.substring(index + 1);
        }else{
            return "";
        }
    }

    /**
     * Generic access to pattern matcher
     * overload for nested patterns
     * @param text
     * @param pattern
     * @hidden  pattern2
     * @param idx
     */
    public void parseText(String text, Pattern pattern, int idx){
        List<String> matches = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()){
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(idx));
        }
    }
    public String parseText(String text, Pattern pattern, Pattern pattern2, int idx){
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()){
            Matcher message = pattern2.matcher(matcher.group());
            if(message.find()){
                System.out.println(message.groupCount());
                System.out.println(message.group(idx));
                return message.group(idx);
            }
        }
        return null;
    }
}
