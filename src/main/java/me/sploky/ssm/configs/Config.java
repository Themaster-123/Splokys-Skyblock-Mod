package me.sploky.ssm.configs;

import net.minecraftforge.fml.common.Loader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    public JSONObject jsonObject;

    public final String name;

    protected final String path;

    public Config(String name) {
        this.name = name;
        jsonObject = new JSONObject();
        path = Loader.instance().getConfigDir().getPath() + "/SplokySkyblockMod/" + name + ".json";
        try {
            Files.createDirectories(Paths.get(path).getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Load();
    }



    public void save() {

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Load() {
        BufferedReader fileReader = null;
        try {
            JSONParser jsonParser = new JSONParser();
            if (Files.exists(Paths.get(path)))
                jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }


    }

}
