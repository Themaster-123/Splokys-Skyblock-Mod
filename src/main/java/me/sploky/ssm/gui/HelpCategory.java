package me.sploky.ssm.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCategory {
    public HelpCategory(String name, String... decodeText) {
        this.name = name;
        this.decodeText = new ArrayList<>(Arrays.asList(decodeText));
    }

    public String name;

    public List<String> decodeText;
}
