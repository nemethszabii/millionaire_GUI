package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;

import java.util.*;

public class Help {
    private GuiDisplay gui;
    private Player player;

    public Help(Player player) {
        this.player = player;
    }

    public void fiftyFifty(TreeMap<String, String> shuffledOptions, String solutionKey) {
        List<String> chars = new ArrayList<>();
        chars.add("a");
        chars.add("b");
        chars.add("c");
        chars.add("d");
        chars.remove(solutionKey);

        Set<String> keys = new TreeSet<>();
        keys.add(solutionKey);
        String chosenChar = GameContent.getRandomOption(chars);
        keys.add(chosenChar);
    }

    public String phone(String solutionKey) {
        return "Hey! I guess, '" + solutionKey + "' is the correct answer!";
    }

    public String audience() {
        List<Integer> votes = genAudienceVotes(4, 100);
        return "a: " + votes.get(0) + "% " + "b: " + votes.get(1) + "% " + "c: " + votes.get(2) + "% " + "d: " + votes.get(3) + "%";
    }

    private List<Integer> genAudienceVotes(int numberOfVotes, int sum) {
        Random r = new Random();
        TreeSet<Integer> set = new TreeSet<>();
        while (set.size() < numberOfVotes - 1) {
            set.add(r.nextInt(sum - 1) + 1);
        }

        List<Integer> cutList = new ArrayList<>(set);
        cutList.add(0, 0);
        cutList.add(sum);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < cutList.size() - 1; i++) {
            result.add(cutList.get(i + 1) - cutList.get(i));
        }
        return result;
    }
}
