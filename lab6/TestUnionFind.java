import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void TestUnionFind() {
        UnionFind uf = new UnionFind(10);
        assertFalse(uf.connected(1, 2));
        uf.union(2, 1);
        uf.union(3, 1);
        uf.union(5, 4);
        uf.union(3, 5);
        assertTrue(uf.connected(1, 4));
        assertEquals(5, uf.sizeOf(5));
        uf.union(7, 6);
        assertEquals(2, uf.sizeOf(6));
        uf.union(7, 5);
        assertEquals(1, uf.parent(6));
        assertFalse(uf.connected(8, 2));
    }
}
