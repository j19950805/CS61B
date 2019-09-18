public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<Character>();
        for(int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return palindromeHelper(wordDeque);
    }

    private boolean palindromeHelper(Deque<Character> d) {
        if (d.size() <= 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()) {
            return palindromeHelper(d);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return palindromeHelper(wordDeque, cc);
    }

    private boolean palindromeHelper(Deque<Character> d, CharacterComparator cc) {
        if (d.size() <= 1) {
            return true;
        } else if (cc.equalChars(d.removeFirst(), d.removeLast())) {
            return palindromeHelper(d, cc);
        } else {
            return false;
        }
    }
}
