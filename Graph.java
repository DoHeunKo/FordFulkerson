package ford;

import java.util.List;
import java.util.ArrayList;
//그래프의 개수 , 정점간의 용량을 포함하는 행렬 생성
public class Graph {
	private int vCount;
	private float[][] adj;
	
	public int getvCount() {
		return vCount;
	}

	public float[][] getAdj() {
		return adj;
	}

	public Graph(int vCount) {
		this.vCount = vCount;
		adj = new float[vCount][vCount];
		for (int i = 0; i < vCount; i++) {
			for (int j = 0; j < vCount; j++) {
				adj[i][j] = 0;
			}
		}
	}
	//연결할 간선 추가
	public void addEdge(int i, int j, float capa) {
		adj[i][j] = capa;
	}
	
	public void removeEdge(int i, int j) {
		adj[i][j] = 0;
	}
	
	public boolean hasEdge(int i, int j) {
		if (adj[i][j] != 0) {
			return true;
		}
		return false;
	}
	
	public List<Integer> neighbours(int vertex) {
		List<Integer> edges = new ArrayList<Integer>();
		for (int i = 0; i < vCount; i++)
			if (hasEdge(vertex, i))
				edges.add(i);
		return edges;
	}
	//그래프 출력
	public void printGraph() {
		for (int i = 0; i < vCount; i++) {
			List<Integer> edges = neighbours(i);
			System.out.print(i + ": ");
			for (int j = 0; j < edges.size(); j++) {
				System.out.print(edges.get(j) + " ");
			}
			System.out.println();
		}
	}
}