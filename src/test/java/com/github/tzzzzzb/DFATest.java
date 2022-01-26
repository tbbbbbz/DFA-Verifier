package com.github.tzzzzzb;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DFATest {
    File DFAFileRepo;

    @BeforeEach
    void setup() {
        DFAFileRepo = new File("src/test/resources/testCases");
        assertTrue(DFAFileRepo.exists());
    }

    @Test
    void DFADescriptionFileReadingTest() throws IOException {
        DFA dfa = new DFA(new File(DFAFileRepo, "0.json").getAbsolutePath());
        JSONObject jsonObject = dfa.getJsonObject();
        assertEquals("ab*", jsonObject.get("regex"));
    }

    @Test
    void verifyDFAStructure0() throws IOException {
        DFA dfa = new DFA(new File(DFAFileRepo, "0.json").getAbsolutePath());
        TestResult result = dfa.verify(100);
        assertEquals(100, result.getTests().size());
        assertEquals(true, result.getPass());
    }


    @Test
    void verifyDFAStructure1() throws IOException {
        DFA dfa = new DFA(new File(DFAFileRepo, "1.json").getAbsolutePath());
        TestResult result = dfa.verify(100);
        assertEquals(false, result.getPass());
    }
}