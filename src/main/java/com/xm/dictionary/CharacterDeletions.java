package com.xm.dictionary;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CharacterDeletions {

    private final CopyOnWriteArrayList<String> dictionary;

    public CharacterDeletions(Collection<String> dictionary) {
        Objects.requireNonNull(dictionary, "dictionary should not be null");

        Set<String> uniqueWords = new HashSet<>();
        for (String word : dictionary) {
            Objects.requireNonNull(word, "Dictionary should not contain null values");
            uniqueWords.add(word);
        }

        this.dictionary = new CopyOnWriteArrayList<>(uniqueWords);
    }

    /**
     * Returns minimum number of character deletions in the word to make it a valid word from the dictionary.
     *
     * @return minimum number of character deletions or Optional.EMPTY if solution not found
     * @throws NullPointerException if the word is null
     */
    public Optional<Integer> getMinimumDeletions(String word) {
        Objects.requireNonNull(word, "word should not be null");

        int minDeletions = Integer.MAX_VALUE;
        for (String dictionaryWord : dictionary) {
            if (word.length() > dictionaryWord.length()) {
                if (contains(word, dictionaryWord)) {
                    int deletions = word.length() - dictionaryWord.length();
                    if (deletions < minDeletions) {
                        minDeletions = deletions;
                    }
                }
            } else if (word.length() == dictionaryWord.length()) {
                if (word.equals(dictionaryWord)) {
                    return Optional.of(0);
                }
            }
        }

        return minDeletions < Integer.MAX_VALUE ? Optional.of(minDeletions) : Optional.empty();
    }

    /**
     * Returns true if and only if a contains all characters of b in the same order.
     *
     * @param a containing string
     * @param b contained string
     * @return true if a contains all characters of b in same order, false otherwise
     */
    private boolean contains(String a, String b) {
        int aIndex = 0;
        int bIndex = 0;
        while (bIndex < b.length() && aIndex >= 0 && aIndex < a.length()) {
            aIndex = a.indexOf(b.charAt(bIndex++), aIndex);
            if (aIndex >= 0) {
                aIndex++;
            }
        }
        return bIndex == b.length() && aIndex >= 0;
    }

    /**
     * @return immutable copy of dictionary
     */
    public Set<String> getDictionary() {
        return Set.copyOf(dictionary);
    }

    /**
     * Adds word to dictionary if it does not contain that word.
     *
     * @return true if the word was added
     * @throws NullPointerException if the word is null
     */
    public boolean addDictionaryWord(String word) {
        Objects.requireNonNull(word, "dictionary does not allow nulls");
        return dictionary.addIfAbsent(word);
    }

    /**
     * Removed specified word from dictionary.
     *
     * @return true if the dictionary contained the specified word
     */
    public boolean removeDictionaryWord(String word) {
        return dictionary.remove(word);
    }
}
