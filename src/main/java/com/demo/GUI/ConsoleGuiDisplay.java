package com.demo.GUI;

public class ConsoleGuiDisplay implements GuiDisplay {
    @Override
    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    @Override
    public void displayInLineMsg(String msg) {
        System.out.print(msg);
    }
}
