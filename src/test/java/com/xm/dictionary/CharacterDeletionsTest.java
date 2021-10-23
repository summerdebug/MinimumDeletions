package com.xm.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDeletionsTest {

    private CharacterDeletions characterDeletions;

    @BeforeEach
    public void setup() {
        List<String> dictionary = List.of(
                "a",
                "ab",
                "abc",
                "abcd",
                "abcde",
                "abcdef",
                "abcdefg",
                "abcdefgh",
                "qwerty",
                " ");
        characterDeletions = new CharacterDeletions(dictionary);
    }

    @Test
    public void basicUseCase() {
        Optional<Integer> actual = characterDeletions.getMinimumDeletions("XabcX");

        assertTrue(actual.isPresent());
        assertEquals(2, actual.get());
    }

    @ParameterizedTest
    @MethodSource("whenSolutionExistsThenSuccessParams")
    public void whenSolutionExistsThenSuccess(String word, Integer expected) {
        Optional<Integer> actual = characterDeletions.getMinimumDeletions(word);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    public static Stream<Arguments> whenSolutionExistsThenSuccessParams() {
        return Stream.of(
                Arguments.of("1234qwerty5678", 8),
                Arguments.of("www www", 6),
                Arguments.of("-abcd-", 2),
                Arguments.of("xab", 1),
                Arguments.of("abx", 1),
                Arguments.of("abcdef", 0),
                Arguments.of("a", 0),
                Arguments.of("abc", 0),
                Arguments.of("abcdefgh", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("whenSolutionNotExistThenOptionalEmptyParams")
    public void whenSolutionNotExistThenOptionalEmpty(String word) {
        Optional<Integer> actual = characterDeletions.getMinimumDeletions(word);
        assertTrue(actual.isEmpty());
    }

    public static List<String> whenSolutionNotExistThenOptionalEmptyParams() {
        return List.of("z", "zzz", "zzzzzzzzzzzzzzzzzzzzzzzzzzzz", "1", "123456!@#$", "");
    }

    @Test
    public void whenWordNullThenException() {
        assertThrows(NullPointerException.class, () -> characterDeletions.getMinimumDeletions(null),
                "word should not be null");
    }

    @Test
    public void whenAddDictionaryWordThenSuccess() {
        String dictionaryWord = "new_word";
        String word = "XX" + dictionaryWord + "XX";
        boolean added = characterDeletions.addDictionaryWord(dictionaryWord);
        Optional<Integer> actual = characterDeletions.getMinimumDeletions(word);

        assertTrue(added);
        assertTrue(actual.isPresent());
        assertEquals(4, actual.get());
    }

    @Test
    public void whenRemoveDictionaryWordThenSuccess() {
        String word = "qwerty";
        boolean removed = characterDeletions.removeDictionaryWord(word);
        Optional<Integer> actual = characterDeletions.getMinimumDeletions(word);

        assertTrue(removed);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void whenAddDictionaryWordNullThenException() {
        assertThrows(NullPointerException.class, () -> characterDeletions.addDictionaryWord(null),
                "dictionary does not allow nulls");
    }

    @Test
    public void whenGetDictionaryThenUnmodifiableSet() {
        Set<String> dictionary = characterDeletions.getDictionary();
        assertThrows(UnsupportedOperationException.class,
                () -> dictionary.add("Dictionary should not allow modification"));
    }

    @Test
    public void whenEmptyStringThenSuccess() {
        String emptyStr = "";
        String longWord = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++";
        boolean added = characterDeletions.addDictionaryWord(emptyStr);
        Optional<Integer> emptyDeletionsCount = characterDeletions.getMinimumDeletions(emptyStr);
        Optional<Integer> longDeletionsCount = characterDeletions.getMinimumDeletions(longWord);

        assertTrue(added);
        assertTrue(emptyDeletionsCount.isPresent());
        assertEquals(0, emptyDeletionsCount.get());
        assertTrue(longDeletionsCount.isPresent());
        assertEquals(longWord.length(), longDeletionsCount.get());
    }
}