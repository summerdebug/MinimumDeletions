# Requirements
- Maven
- Java 11

# Run
Import the project to IDE as a Maven project and run the test class `CharacterDeletionsTest`.

# Complexity
## General
Complexity is O(W * L), where
- W - number of characters in the requested word
- L - total number of characters in the dictionary words

## Complexity - details
O(W * L) is a complexity of the main business method
`CharacterDeletions.getMinimumDeletions(String word)`.
There is also O(L) complexity of class instantiation process, because in the constructor
I iterate through the entire dictionary to remove duplicates and perform null checks.
It logically doesn't make sense to allow null values in the dictionary, because a string
cannot become null via character deletions.
This initialization with O(L) complexity should be performed only once on class instantiation.

## 2 possible algorithms
There are 2 possible algorithms to solve this task:
1. Dictionary traversal, complexity is O(W * L)
2. Word traversal, complexity is O(Math.pow(2, W))

I implemented the algorithm of dictionary traversal,
as it's complexity linearly depends on the lengths of the requested word and the dictionary.
This algorithm will probably work better for most common cases.
In extreme cases - when the dictionary is too long, and the requested words are short,
the word traversal will work better.
In word traversal algorithm we have to create all Math.pow(2, W) combinations of word,
obtained by deleting some characters from it and check for each combination
if the dictionary contains it.

# Empty string
For the sake of generality, provided solution allows empty string in the dictionary.

# Concurrency
Thread safe collection `CopyOnWriteArrayList`, is used to store the dictionary,
so the solution allows access from multiple threads.

Thank you for your time!
