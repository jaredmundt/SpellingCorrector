# SpellingCorrector

A Java spelling corrector backed by a trie. Given a dictionary file and a word, it suggests the closest dictionary word using edit distance (deletion, transposition, alteration, insertion), searching up to an edit distance of two and preferring the most frequent candidate.

## Project layout

```
src/
  spell/
    Trie.java, Node.java       Trie implementation (lowercase a–z only)
    SpellCorrector.java        Edit-distance suggestion logic
    ITrie, INode, ISpellCorrector  Interfaces the implementation must satisfy
    Main.java                  Command-line entry point
  passoff/
    TrieTest.java, SpellTest.java  JUnit 5 tests
notsobig.txt                   Large sample dictionary (Project Gutenberg text)
word.txt, words.txt            Small dictionaries used by the tests
```

## Requirements

- JDK 11 or newer

## Build and run

From the repository root:

```bash
javac -d build src/spell/*.java
java -cp build spell.Main notsobig.txt spelng
```

```
Suggestion is: seeing
```

`Main` takes two arguments: the dictionary file and the word to correct. Dictionary files are whitespace-separated words; repeated words increase that word's frequency.

## Running the tests

The tests use JUnit 5 (Jupiter). Download the [JUnit Platform Console Standalone](https://central.sonatype.com/artifact/org.junit.platform/junit-platform-console-standalone) jar, then from the repository root:

```bash
javac -d build -cp junit-platform-console-standalone.jar src/spell/*.java src/passoff/*.java
java -jar junit-platform-console-standalone.jar execute -cp build --select-package passoff
```

Run from the repository root so the tests can find `word.txt`, `words.txt`, and `notsobig.txt`. In IntelliJ IDEA, open the project, add JUnit 5 to the module, and run the `passoff` package.
