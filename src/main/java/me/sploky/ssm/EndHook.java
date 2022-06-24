package me.sploky.ssm;

import me.sploky.ssm.elements.ElementData;

public class EndHook extends Thread{
    @Override
    public void run() {
        try {
            ElementData.saveData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
