package me.sploky.ssm.elements;

import me.sploky.ssm.hypixeldata.Skill;
import me.sploky.ssm.hypixeldata.SkillData;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementTextDecoder {
    public static HashMap<String, Supplier<Float>> numberDecodeMap = new HashMap<>();

    public static String decodeText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);

        // decodes any part of the text that is surrounded with square brackets into a number
        Pattern numberPattern = Pattern.compile("(?<=\\[)[^\\[\\]]+(?=])");
        Matcher numberMatcher = numberPattern.matcher(text);

        int offset = 0;
        while (numberMatcher.find()) {
            if (numberDecodeMap.containsKey(numberMatcher.group().toLowerCase())) {
                float number = numberDecodeMap.get(numberMatcher.group().toLowerCase()).get();
                stringBuilder.replace(numberMatcher.start() - 1 - offset, numberMatcher.end() + 1 - offset, Float.toString(number));
                offset += (numberMatcher.end() + 1) - (numberMatcher.start() - 1) - Float.toString(number).length();
            }
        }

        while (true) {
            Pattern parenthesesPattern = Pattern.compile("(?<=\\()([^\\[\\]\\D]| |[\\+|\\-|\\*|\\/]|\\d\\.\\d)+(?=\\))+");

            Matcher parenthesesMatcher = parenthesesPattern.matcher(stringBuilder.toString());

            if (parenthesesMatcher.find()) {
                parenthesesMatcher.reset();

                offset = 0;
                while (parenthesesMatcher.find()) {
                    StringBuilder parenthesesBuilder = new StringBuilder(parenthesesMatcher.group());

                    runBasicMathDecoders(stringBuilder);

                    stringBuilder.replace(parenthesesMatcher.start() - 1 - offset, parenthesesMatcher.end() + 1 - offset,
                            parenthesesBuilder.toString());
                    offset += (parenthesesMatcher.end() + 1) - (parenthesesMatcher.start() - 1) - parenthesesBuilder.length();
                }

                parenthesesMatcher.reset(stringBuilder.toString());
            } else {
                break;
            }

        }


        runBasicMathDecoders(stringBuilder);


        return stringBuilder.toString();
    }

    public static void initDecoder() {
        initNumbers();
    }

    private static void initNumbers() {
        numberDecodeMap.put("mining skill",  () -> (float) SkillData.MINING_XP.getXp());
        numberDecodeMap.put("e", () -> 69f);
    }

    @SafeVarargs
    private static void doMaths(StringBuilder stringBuilder, String patternCharacters, String[] characters, BiFunction<Float, Float, Float>... functions) {
        Pattern mathPattern = Pattern.compile("((?<!\\d)\\-)?[\\d.]+|[" + patternCharacters + "]|[^\\s\\d]+");
        Matcher mathMatcher = mathPattern.matcher(stringBuilder.toString());

        int offset = 0;
        Float currentNumber = Float.NaN;
        int operation = -1;
        int start = 0;
        int offsetStart = 0;

        outer:
        while (mathMatcher.find()) {
            if (Float.isNaN(currentNumber)) {
                try {
                    currentNumber = Float.parseFloat(mathMatcher.group());
                    start = mathMatcher.start() - offset;
                    offsetStart = start;
                } catch (NumberFormatException ignored) {

                }
            } else {
                if (operation == -1) {
                    for (int i = 0; i < characters.length; i++) {
                        if (mathMatcher.group().equals(characters[i])) {
                            operation = i;
                            continue outer;
                        }
                    }

                    currentNumber = Float.NaN;
                    continue;
                }

                try {
                    float number = Float.parseFloat(mathMatcher.group());
                    currentNumber = functions[operation].apply(currentNumber, number);
                    stringBuilder.replace(start, mathMatcher.end() - offset, Float.toString(currentNumber));
                    offset = mathMatcher.end() - offsetStart - Float.toString(currentNumber).length();
                    //offsetStart = mathMatcher.start();

                    operation = -1;
                } catch (NumberFormatException ex) {
                    operation = -1;
                    currentNumber = Float.NaN;
                }
            }

        }
    }

    private static void runBasicMathDecoders(StringBuilder stringBuilder) {
        // Exponents
        doMaths(stringBuilder, "\\^", new String[] {"^"}, (x, y) -> (float)Math.pow(x, y));

        // multiply and divide
        doMaths(stringBuilder, "\\*\\/", new String[]  {"*", "/"}, (x, y) -> x * y, (x, y) -> x / y);


        // add and subtract
        doMaths(stringBuilder, "\\+\\-", new String[]  {"+", "-"}, Float::sum, (x, y) -> x - y);
    }
}