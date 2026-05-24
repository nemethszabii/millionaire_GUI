package com.demo.logic;

import com.demo.GUI.GuiDisplay;

import java.util.*;

public class Help {
    private GuiDisplay gui;
    private Scanner sc;

    public Help(GuiDisplay gui, Scanner sc) {
        this.gui = gui;
        this.sc = sc;
    }

    public Set<String> fiftyFifty(TreeMap<String, String> shuffledOptions, String solutionKey) {
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

        gui.displayMsg("50:50 result:");
        for (String key : keys) {
            gui.displayMsg(key + " - " + shuffledOptions.get(key));
        }
        return keys;
    }

    public void phone(TreeMap<String, String> shuffledOptions) {

    }

    public void audience(TreeMap<String, String> shuffledOptions) {

    }

    public String displayHelpOptions() {
        String msg = """
                1 - 50:50
                2 - Phone a friend
                3 - Ask the audience
        """;
        gui.displayMsg(msg);
        String answer = "";
        do {
            gui.displayInLineMsg("Choose an option: ");
            answer = sc.nextLine();
        } while (!Set.of("1", "2", "3").contains(answer));
        return answer;
    }
}
