package com.diquemc.sign;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChatBlock {

    public static double msgLength(String str) {
        double length = 0.0D;
        str = cleanColors(str);


        for (int x = 0; x < str.length(); x++) {
            int len = charLength(str.charAt(x));
            if (len > 0) {
                length += len;
            } else {
                x++;
            }
        }
        return length;
    }

    public static String cleanColors(String str) {
        String patternStr = "ï¿½.";
        String replacementStr = "";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replacementStr);

    }

    public static int charLength(char x) {
        if ("i.:,;|!".indexOf(x) != -1) {
            return 2;
        }
        if ("l'".indexOf(x) != -1) {
            return 3;
        }
        if ("tI[]".indexOf(x) != -1) {
            return 4;
        }
        if ("fk{}<>\"*()".indexOf(x) != -1) {
            return 5;
        }
        if ("abcdeghjmnopqrsuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890\\/#?$%-=_+&^".indexOf(x) != -1) {
            return 6;
        }
        if ("@~".indexOf(x) != -1) {
            return 7;
        }
        if (x == ' ') {
            return 4;
        }


        return -1;
    }

    public static String[] wordWrap(String msg, int prefixLn) {
        ArrayList<String> split = new ArrayList<>();
        split.addAll(Arrays.asList(msg.split(" ")));


        ArrayList<String> out = new ArrayList<>();


        while (!split.isEmpty()) {
            int len = 0;


            ArrayList<String> words = new ArrayList<>();


            while ((!split.isEmpty()) && (split.get(0) != null) && (len <= 320 - prefixLn)) {
                double wordLength = msgLength(split.get(0)) + 4.0D;


                if (wordLength > 320 - prefixLn) {
                    String[] tempArray = wordCut(len, split.remove(0));
                    words.add(tempArray[0]);
                    split.add(tempArray[1]);
                }


                len = (int) (len + wordLength);
                if (len < 320 - prefixLn + 4) {
                    words.add(split.remove(0));
                }
            }

            String merged = combineSplit(0, words.toArray(new String[words.size()]), " ") + " ";
            out.add(merged.replaceAll("\\s+$", ""));
        }


        return out.toArray(new String[out.size()]);
    }

    public static String combineSplit(int startIndex, String[] string, String seperator) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < string.length; i++) {
            builder.append(string[i]);
            builder.append(seperator);
        }
        builder.deleteCharAt(builder.length() - seperator.length());

        return builder.toString();
    }

    public static String[] wordCut(int lengthBefore, String str) {
        int length = lengthBefore;


        String[] output = new String[2];
        int x = 0;
        while ((length < 320) && (x < str.length())) {
            int len = charLength(str.charAt(x));
            if (len > 0) {
                length += len;
            } else {
                x++;
            }
            x++;
        }
        if (x > str.length()) {
            x = str.length();
        }


        output[0] = str.substring(0, x);


        output[1] = str.substring(x);
        return output;
    }

    public static void sendMessage(Player receiver, String msg) {
        sendPrefixedMessage(receiver, null, msg);
    }

    public static void sendPrefixedMessage(Player receiver, String prefix, String msg) {
        if (receiver == null) {
            return;
        }

        int prefix_width = prefix == null ? 0 : (int) msgLength(prefix);

        String[] message = colorize(wordWrap(msg, prefix_width));

        for (String out : message) {
            receiver.sendMessage(prefix == null ? "" : (prefix + " " + out));
        }
    }


    public static String[] colorize(String[] message) {
        return colorizeBase(message, 167);
    }

    public static String[] colorizeBase(String[] message, int charcode) {
        if ((message != null) && (message[0] != null) && (!message[0].isEmpty())) {


            String prevColor = "";
            String lastColor = "";

            int counter = 0;
            for (String msg : message) {


                for (int x = 0; x < msg.length(); x++) {

                    if (msg.codePointAt(x) == charcode) {

                        x += 1;

                        lastColor = ChatColor.getByChar(msg.charAt(x)) + "";
                    }
                }


                message[counter] = (prevColor + msg);
                prevColor = lastColor;
                counter++;
            }
        }

        return message;
    }


}
