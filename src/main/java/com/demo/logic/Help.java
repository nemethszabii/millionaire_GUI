package com.demo.logic;

import com.demo.GUI.GuiDisplay;
import com.demo.models.Player;

import java.util.*;

public class Help {
    private GuiDisplay gui;
    private Scanner sc;
    private Player player;

    public Help(GuiDisplay gui, Scanner sc, Player player) {
        this.gui = gui;
        this.sc = sc;
        this.player = player;
    }

    public String fiftyFifty(TreeMap<String, String> shuffledOptions, String solutionKey) {
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

        String answer;
        do {
            gui.displayInLineMsg("Choose an option: ");
            answer = sc.nextLine();
        } while (!keys.contains(answer));
        return answer;
    }

    public void phone() {
        gui.displayMsg("Hey! I guess, 'a' is the correct answer!");
    }

    public void audience() {
        gui.displayMsg("The audience has voted!");
    }

    public String helpMenu(TreeMap<String, String> shuffledOptions, String solutionKey) {
        displayHelps();
        String input;
        int chosenIndex;
        String chosenHelp;
        do {
            gui.displayInLineMsg("Choose a help: ");
            input = sc.nextLine();
        } while (!Set.of("1", "2", "3").contains(input));

        chosenIndex = Integer.parseInt(input);
        chosenHelp = this.player.getAvailableHelps()[chosenIndex - 1];
        removeChosenHelp(chosenHelp);
        return mainHelpLogic(chosenHelp, shuffledOptions, solutionKey);
    }

    private String mainHelpLogic(String chosenHelp, TreeMap<String, String> shuffledOptions, String solutionKey) {
        String answer = "";
        switch (chosenHelp) {
            case "50:50":
                answer = fiftyFifty(shuffledOptions, solutionKey);
                break;
            case "Phone":
                phone();
                answer = getPlayerAnswer(Set.of("a", "b", "c", "d"));
                break;
            case "Audience":
                audience();
                answer = getPlayerAnswer(Set.of("a", "b", "c", "d"));
                break;
        }
        return answer;
    }

    private String getPlayerAnswer(Set<String> answerOptions) {
        String answer;
        do {
            gui.displayInLineMsg("Choose an answer [a, b, c, d] or get help [h] or to quit [q]: ");
            answer = sc.nextLine();
        } while (!answerOptions.contains(answer));
        return answer;
    }

    private void removeChosenHelp(String chosenHelp) {
        String[] helps = this.player.getAvailableHelps();
        String[] newArray = new String[helps.length - 1];
        int counter = 0;
        for (int i = 0; i < helps.length; i++) {
            if (!helps[i].equals(chosenHelp)) {
                newArray[counter] = helps[i];
                counter++;
            }
        }
        this.player.setAvailableHelps(newArray);
    }

    private void displayHelps() {
        int i = 1;
        for (String help : this.player.getAvailableHelps()) {
            gui.displayMsg(i + " - " + help);
            i++;
        }
    }
}
