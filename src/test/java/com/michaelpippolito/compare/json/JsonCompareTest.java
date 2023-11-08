package com.michaelpippolito.compare.json;

import com.michaelpippolito.compare.common.CompareResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonCompareTest {
    @Test
    public void testEquals() throws IOException {
        FileInputStream streamA = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("equals.json")).getFile());
        FileInputStream streamB = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("equals.json")).getFile());

        CompareResult compareResult = new JsonCompare(streamA, streamB).compare();

        Assert.assertTrue(compareResult.getMismatched().isEmpty());
        Assert.assertTrue(compareResult.getMatched().contains("testNull"));
        Assert.assertTrue(compareResult.getMatched().contains("testString"));
        Assert.assertTrue(compareResult.getMatched().contains("testInteger"));
        Assert.assertTrue(compareResult.getMatched().contains("testDouble"));
        Assert.assertTrue(compareResult.getMatched().contains("testBoolean"));
        Assert.assertTrue(compareResult.getMatched().contains("testArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[3]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[4]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedNull"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedString"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedInteger"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedDouble"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedBoolean"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedMixedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedMixedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedMixedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedMixedArray[3]"));
        Assert.assertTrue(compareResult.getMatched().contains("testMixedArray[5].testArrayNestedMixedArray[4]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectNull"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectString"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectInteger"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectDouble"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectBoolean"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectMixedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectMixedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectMixedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectMixedArray[3]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testObjectMixedArray[4]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedNull"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedString"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedInteger"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedDouble"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedBoolean"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedMixedArray[0]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedMixedArray[1]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedMixedArray[2]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedMixedArray[3]"));
        Assert.assertTrue(compareResult.getMatched().contains("testObject.testNestedObject.testNestedMixedArray[4]"));
    }

    @Test
    public void testMismatch() throws IOException {
        FileInputStream streamA = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("testA.json")).getFile());
        FileInputStream streamB = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("testB.json")).getFile());

        CompareResult compareResult = new JsonCompare(streamA, streamB).compare();

        // null tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testNullOnlyInAMismatch"));
        Assert.assertNull(compareResult.getMismatched().get("testNullOnlyInAMismatch").get("valueInA"));
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testNullOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testNullOnlyInBMismatch"));
        Assert.assertNull(compareResult.getMismatched().get("testNullOnlyInBMismatch").get("valueInB"));
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testNullOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testNullEqualityMismatch"));
        Assert.assertNull(compareResult.getMismatched().get("testNullEqualityMismatch").get("valueInA"));
        Assert.assertEquals("abc", compareResult.getMismatched().get("testNullEqualityMismatch").get("valueInB"));

        // string tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testStringOnlyInAMismatch"));
        Assert.assertEquals("testStringOnlyInAMismatch", compareResult.getMismatched().get("testStringOnlyInAMismatch").get("valueInA"));
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testStringOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testStringOnlyInBMismatch"));
        Assert.assertEquals("testStringOnlyInBMismatch", compareResult.getMismatched().get("testStringOnlyInBMismatch").get("valueInB"));
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testStringOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testStringEqualityMismatch"));
        Assert.assertEquals("stringA", compareResult.getMismatched().get("testStringEqualityMismatch").get("valueInA"));
        Assert.assertEquals("stringB", compareResult.getMismatched().get("testStringEqualityMismatch").get("valueInB"));

        // integer tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testIntegerOnlyInAMismatch"));
        Assert.assertEquals(1, compareResult.getMismatched().get("testIntegerOnlyInAMismatch").get("valueInA"));
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testIntegerOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testIntegerOnlyInBMismatch"));
        Assert.assertEquals(1, compareResult.getMismatched().get("testIntegerOnlyInBMismatch").get("valueInB"));
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testIntegerOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testIntegerEqualityMismatch"));
        Assert.assertEquals(1, compareResult.getMismatched().get("testIntegerEqualityMismatch").get("valueInA"));
        Assert.assertEquals(2, compareResult.getMismatched().get("testIntegerEqualityMismatch").get("valueInB"));

        // double tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testDoubleOnlyInAMismatch"));
        Assert.assertEquals(3.14, compareResult.getMismatched().get("testDoubleOnlyInAMismatch").get("valueInA"));
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testDoubleOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testDoubleOnlyInBMismatch"));
        Assert.assertEquals(3.14, compareResult.getMismatched().get("testDoubleOnlyInBMismatch").get("valueInB"));
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testDoubleOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testDoubleEqualityMismatch"));
        Assert.assertEquals(3.14, compareResult.getMismatched().get("testDoubleEqualityMismatch").get("valueInA"));
        Assert.assertEquals(2.78, compareResult.getMismatched().get("testDoubleEqualityMismatch").get("valueInB"));

        // boolean tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testBooleanOnlyInAMismatch"));
        Assert.assertTrue((boolean) compareResult.getMismatched().get("testBooleanOnlyInAMismatch").get("valueInA"));
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testBooleanOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testBooleanOnlyInBMismatch"));
        Assert.assertTrue((boolean) compareResult.getMismatched().get("testBooleanOnlyInBMismatch").get("valueInB"));
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testBooleanOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testBooleanEqualityMismatch"));
        Assert.assertTrue((boolean) compareResult.getMismatched().get("testBooleanEqualityMismatch").get("valueInA"));
        Assert.assertFalse((boolean) compareResult.getMismatched().get("testBooleanEqualityMismatch").get("valueInB"));

        // array tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testArrayOnlyInAMismatch"));
        Assert.assertTrue(compareResult.getMismatched().get("testArrayOnlyInAMismatch").get("valueInA") instanceof List);
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testArrayOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testArrayOnlyInBMismatch"));
        Assert.assertTrue(compareResult.getMismatched().get("testArrayOnlyInBMismatch").get("valueInB") instanceof List);
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testArrayOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testArraySizeMismatch[]"));
        Assert.assertEquals(3, ((List)compareResult.getMismatched().get("testArraySizeMismatch[]").get("valueInA")).size());
        Assert.assertEquals(2, ((List)compareResult.getMismatched().get("testArraySizeMismatch[]").get("valueInB")).size());
        Assert.assertTrue(compareResult.getMismatched().containsKey("testArrayEqualityMismatch[]"));
        Assert.assertTrue(compareResult.getMismatched().get("testArrayEqualityMismatch[]").get("valueInA") instanceof List);
        Assert.assertTrue(compareResult.getMismatched().get("testArrayEqualityMismatch[]").get("valueInB") instanceof List);
        Assert.assertTrue(compareResult.getMismatched().containsKey("testArrayObjectMismatch[0].testMismatchedArrayObject"));
        Assert.assertEquals("testMismatchedArrayObjectA", compareResult.getMismatched().get("testArrayObjectMismatch[0].testMismatchedArrayObject").get("valueInA"));
        Assert.assertEquals("testMismatchedArrayObjectB", compareResult.getMismatched().get("testArrayObjectMismatch[0].testMismatchedArrayObject").get("valueInB"));

        // object tests
        Assert.assertTrue(compareResult.getMismatched().containsKey("testObjectOnlyInAMismatch"));
        Assert.assertTrue(compareResult.getMismatched().get("testObjectOnlyInAMismatch").get("valueInA") instanceof Map);
        Assert.assertEquals("[MISSING IN B]", compareResult.getMismatched().get("testObjectOnlyInAMismatch").get("valueInB"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testObjectOnlyInBMismatch"));
        Assert.assertTrue(compareResult.getMismatched().get("testObjectOnlyInBMismatch").get("valueInB") instanceof Map);
        Assert.assertEquals("[MISSING IN A]", compareResult.getMismatched().get("testObjectOnlyInBMismatch").get("valueInA"));
        Assert.assertTrue(compareResult.getMismatched().containsKey("testObjectEqualityMismatch.testMismatchedObject"));
        Assert.assertEquals("testMismatchedObjectA", compareResult.getMismatched().get("testObjectEqualityMismatch.testMismatchedObject").get("valueInA"));
        Assert.assertEquals("testMismatchedObjectB", compareResult.getMismatched().get("testObjectEqualityMismatch.testMismatchedObject").get("valueInB"));

    }
}
