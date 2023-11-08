package com.michaelpippolito.compare.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Data
public class CompareResult {
    private final List<String> matched;
    private final Map<String, Map<String, Object>> mismatched;

    private final String MISMATCH_KEY = "key";
    private final String MISMATCH_VALUE_IN_KEY = "valueIn%s";

    public static final String MISSING_IN_VALUE = "[MISSING IN %s]";

    public void combine(CompareResult toCombine) {
        this.matched.addAll(toCombine.getMatched());
        this.mismatched.putAll(toCombine.getMismatched());
    }

    public void addMismatch(String key, String sourceAName, Object sourceAValue, String sourceBName, Object sourceBValue) {
        Map<String, Object> mismatch = new HashMap<>();
        mismatch.put(String.format(MISMATCH_VALUE_IN_KEY, sourceAName), sourceAValue);
        mismatch.put(String.format(MISMATCH_VALUE_IN_KEY, sourceBName), sourceBValue);
        this.mismatched.put(key, mismatch);
    }
}
