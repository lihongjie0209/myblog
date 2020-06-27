package cn.lihongjie.graph;

import java.util.*;

public class AdjacencyListGraph {
    private int vertexCount;
    private int edgeCount;
    private List<Set<Integer>> list;

    public AdjacencyListGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.list = new ArrayList<Set<Integer>>(vertexCount);

        for (int i = 0; i < vertexCount; i++) {

            list.add(new LinkedHashSet<Integer>());

        }


    }

    public List<Integer> dfs(int start) {

        boolean[] marker = new boolean[vertexCount];

        ArrayList<Integer> ans = new ArrayList<>();


        dfs(start, marker, ans);

        return ans;

    }

    private void dfs(int start, boolean[] marker, ArrayList<Integer> ans) {

        if (marker[start]) {
            return;
        }


        ans.add(start);
        marker[start] = true;


        Set<Integer> set = list.get(start);


        for (Integer integer : set) {

            dfs(integer, marker, ans);
        }


    }

    public void addEdge(int from, int to) {

        boolean x = list.get(from).add(to);
        boolean y = list.get(to).add(from);

        if (x && y) {
            edgeCount++;
        }


    }

    public boolean hasPathTo(int from, int to) {


        return !path(from, to).isEmpty();

    }


    private void dfs3(int from, boolean[] marker, Integer[] ans) {


        marker[from] = true;


        Set<Integer> integers = list.get(from);

        for (Integer integer : integers) {
            if (marker[integer]) {
                continue;
            }
            ans[integer] = from;
            dfs3(integer, marker, ans);
        }

    }

    public List<Integer> path(int from, int to) {
        Integer[] path = new Integer[vertexCount];
        dfs3(from, new boolean[vertexCount], path);
        Integer p = to;
        Stack<Integer> stack = new Stack<>();
        while (p != null ) {
            stack.push(p);
            p = path[p];
        }
        if (stack.size() <= 1) {
            return new ArrayList<>();
        }

        ArrayList<Integer> ans = new ArrayList<>();
        while (!stack.isEmpty()) {
            ans.add(stack.pop());
        }
        return ans;
    }
}
