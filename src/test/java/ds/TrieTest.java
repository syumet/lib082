package ds;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieTest {

    @Test
    public void test() {
        Trie t = new Trie();
        List<String> strings = Arrays.asList("hello", "world", "helloword", "hell");

        strings.forEach(t::insert);
        strings.forEach(s -> assertTrue(t.search(s)));
        strings.forEach(s -> assertTrue(t.startsWith(s)));

        assertFalse(t.search("hellow"));
        assertTrue(t.startsWith("hellow"));
        assertFalse(t.search("we"));
    }
}