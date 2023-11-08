package com.michaelpippolito.compare.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelpippolito.compare.common.CompareResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class JsonCompare {
    private String sourceAName;
    private final FileInputStream jsonFileAStream;
    private String sourceBName;
    private final FileInputStream jsonFileBStream;

    private final ObjectMapper objectMapper = new ObjectMapper();



    public CompareResult compare() throws IOException {
        String sourceA = StringUtils.isEmpty(this.sourceAName) ?
                "A" : this.sourceAName;
        String sourceB = StringUtils.isEmpty(this.sourceBName) ?
                "B" : this.sourceBName;

        Map<String, Object> jsonA = objectMapper.readValue(jsonFileAStream, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> jsonB = objectMapper.readValue(jsonFileBStream, new TypeReference<Map<String, Object>>() {});

        return compare(sourceA, jsonA, sourceB, jsonB, "");
    }

    private CompareResult compare(String sourceA, Map<String, Object> jsonA, String sourceB, Map<String, Object> jsonB, String root) {
        CompareResult compareResult = new CompareResult(new ArrayList<>(), new HashMap<>());
        for (String key : Stream.concat(jsonA.keySet().stream(), jsonB.keySet().stream()).collect(Collectors.toSet())) {
            String prefix = root + "." + key;
            if (jsonA.containsKey(key)) {
                if (jsonB.containsKey(key)) {
                    // Both contain
                    Object objectA = jsonA.get(key);
                    Object objectB = jsonB.get(key);

                    compareObjects(prefix, compareResult, sourceA, objectA, sourceB, objectB);
                } else {
                    // Only A
                    compareResult.addMismatch(prefix.substring(1), sourceA, jsonA.get(key), sourceB, String.format(CompareResult.MISSING_IN_VALUE, sourceB.toUpperCase()));
                }
            } else if (jsonB.containsKey(key)) {
                // Only B
                compareResult.addMismatch(prefix.substring(1), sourceA, String.format(CompareResult.MISSING_IN_VALUE, sourceA.toUpperCase()), sourceB, jsonB.get(key));
            }
        }
        return compareResult;
    }

    private void compareObjects(String prefix, CompareResult compareResult, String sourceA, Object objectA, String sourceB, Object objectB) {
        if (objectA == null) {
            if (objectB == null) {
                // Both null
                compareResult.getMatched().add(prefix.substring(1));
            } else {
                // A null, B exists
                compareResult.addMismatch(prefix.substring(1), sourceA, null, sourceB, objectB);
            }
        } else if (objectB == null) {
            // B null, A exists
            compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, null);
        } else {
            // Both non-null
            if (objectA instanceof Map) {
                if (objectB instanceof Map) {
                    // Both are maps - recursively call the operation and combine the CompareResult
                    compareResult.combine(compare(sourceA, (Map<String, Object>) objectA, sourceB, (Map<String, Object>) objectB, prefix));
                } else {
                    // A is map, B is not
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            } else if (objectA instanceof List) {
                if (objectB instanceof List) {
                    // Both are lists
                    compareLists(prefix, compareResult, sourceA, (List<Object>) objectA, sourceB, (List<Object>) objectB);
                } else {
                    // A is list, B is not
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            } else if (objectA instanceof Integer) {
                if (objectB instanceof Integer) {
                    // Both are integers
                    if ((int) objectA == (int) objectB) {
                        compareResult.getMatched().add(prefix.substring(1));
                    } else {
                        compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                    }
                } else {
                    // A is integer, B is something else
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            } else if (objectA instanceof Double) {
                if (objectB instanceof Double) {
                    // Both are double
                    if ((double) objectA == (double) objectB) {
                        compareResult.getMatched().add(prefix.substring(1));
                    } else {
                        compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                    }
                } else {
                    // A is double, B is something else
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            } else if (objectA instanceof Boolean) {
                if (objectB instanceof Boolean) {
                    // Both are boolean
                    if ((boolean) objectA == (boolean) objectB) {
                        compareResult.getMatched().add(prefix.substring(1));
                    } else {
                        compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                    }
                } else {
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            } else if (objectA instanceof String) {
                if (objectB instanceof String) {
                    // Both are string
                    if (objectA.equals(objectB)) {
                        compareResult.getMatched().add(prefix.substring(1));
                    } else {
                        compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                    }
                } else {
                    // A is string, B is something else
                    compareResult.addMismatch(prefix.substring(1), sourceA, objectA, sourceB, objectB);
                }
            }
        }
    }

    private void compareLists(String prefix, CompareResult compareResult, String sourceA, List<Object> listA, String sourceB, List<Object> listB) {
        if (listA.size() != listB.size()) {
            // Lists are unequal
            compareResult.addMismatch(prefix.substring(1) + "[]", sourceA, listA, sourceB, listB);
        } else {
            for (int i = 0; i < listA.size(); i++) {
                compareObjects(prefix + "[" + i + "]", compareResult, sourceA, listA.get(i), sourceB, listB.get(i));
            }
        }
    }
}
