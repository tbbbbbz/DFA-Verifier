package com.github.tzzzzzb;

import com.mifmif.common.regex.Generex;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

@Getter
public class DFA {
    JSONObject jsonObject;
    Map<String, Map<Character, String>> jumpingMap;
    Generex generex;
    String s0;
    Set<String> SAs;

    DFA (String filename) throws IOException {
        Reader reader = new FileReader(filename);
        jsonObject = new JSONObject(new JSONTokener(reader));
        reader.close();
        jumpingMap = new HashMap<>();
        String regex = jsonObject.getString("regex");
        generex = new Generex(regex);
        JSONArray function = jsonObject.getJSONArray("function");

        s0 = jsonObject.getString("s0");
        SAs = new HashSet<>();
        jsonObject.getJSONArray("SA").forEach(x -> SAs.add((String)x));
        for (int i = 0; i < function.length(); i++) {
            JSONArray jump = function.getJSONArray(i);
            String src = jump.getString(0);
            char edge = jump.getString(1).toCharArray()[0];
            String des = jump.getString(2);
            if (!jumpingMap.containsKey(src)) {
                jumpingMap.put(src, new HashMap<>());
            }
            if (jumpingMap.get(src).containsKey(edge)) {
                throw new DFAStructureException("two identical edges from one source");
            }
            jumpingMap.get(src).put(edge, des);
        }
    }

    TestResult verify(int numOfTests) {
        TestResult result = new TestResult();
        List<String> testStrings = generex.getMatchedStrings(numOfTests + 1);
        for (String testString : testStrings) {
            boolean pass = true;
            String cur = s0;
            for (char edge : testString.toCharArray()) {
                if (!jumpingMap.containsKey(cur) ||
                !jumpingMap.get(cur).containsKey(edge)) {
                    pass = false;
                    break;
                }
                cur = jumpingMap.get(cur).get(edge);
            }
            if (!SAs.contains(cur)) {
                pass = false;
            }
            result.getTests().put(testString, pass);
            if (!pass) {
                result.setPass(false);
            }
        }
        return result;
    }
}
