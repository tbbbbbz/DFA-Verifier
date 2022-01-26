package com.github.tzzzzzb;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TestResult {
    Boolean pass;
    Map<String, Boolean> tests;

    TestResult() {
        pass = true;
        tests = new HashMap<>();
    }
}
