package cn.lihongjie.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testDFS() {

        AdjacencyListGraph graph = new AdjacencyListGraph(3);


        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        List<Integer> dfs = graph.dfs(0);

        assertEquals(Arrays.asList(0, 1, 2), dfs);

        AdjacencyListGraph graph2 = new AdjacencyListGraph(3);


        graph2.addEdge(0, 1);
        graph2.addEdge(0, 2);
        graph2.addEdge(1, 2);

        List<Integer> dfs2 = graph2.dfs(0);

        assertEquals(Arrays.asList(0, 1, 2), dfs2);


        AdjacencyListGraph graph3 = new AdjacencyListGraph(6);


        graph3.addEdge(0, 5);
        graph3.addEdge(2, 4);
        graph3.addEdge(2, 3);
        graph3.addEdge(1, 2);
        graph3.addEdge(0, 1);
        graph3.addEdge(3, 4);
        graph3.addEdge(3, 5);
        graph3.addEdge(0, 2);

        List<Integer> dfs3 = graph3.dfs(0);

        assertEquals(Arrays.asList(0, 5, 3, 2, 4, 1), dfs3);
    }

    @Test
    void testHasPath() {

        AdjacencyListGraph graph = new AdjacencyListGraph(5);


        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(3, 4);


        assertTrue(graph.hasPathTo(0, 1));
        assertTrue(graph.hasPathTo(0, 2));
        assertTrue(graph.hasPathTo(1, 2));
        assertFalse(graph.hasPathTo(0, 3));
        assertFalse(graph.hasPathTo(1, 3));
        assertFalse(graph.hasPathTo(2, 3));
        assertFalse(graph.hasPathTo(0, 4));
        assertFalse(graph.hasPathTo(1, 4));
        assertFalse(graph.hasPathTo(2, 4));
    }

    @Test
    void testPath() {

        AdjacencyListGraph graph = new AdjacencyListGraph(5);


        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(3, 4);
        assertEquals(Arrays.asList(0, 1), graph.path(0, 1));
        assertEquals(Arrays.asList(0, 2), graph.path(0, 2));
        assertEquals(Arrays.asList(3, 4), graph.path(3, 4));
        assertEquals(Arrays.asList(1, 0, 2), graph.path(1, 2));
    }
}