package ford;

//자바는 아직 능숙하지 않아서 오픈 소스를 받아 코드를 모듈별로 해석하고 참고하는데 이용했습니다.

import java.util.LinkedList;

public class TestGraphs {

	public static void main(String[] args) {
		Graph g = new Graph(10);
		//vertex : source , sink 포함 6개
		System.out.println("Graph:");
		// add Edges
		g.addEdge( 0,1,12);
		g.addEdge( 0,2,3 );
		g.addEdge( 0,3,20 );
		g.addEdge( 1,6,5 );
		g.addEdge( 1,4,6 );
		g.addEdge( 2,4,4 );
		g.addEdge( 2,5,4 );
		g.addEdge( 3,5,5);
		g.addEdge( 3,8,10 );
		g.addEdge( 4,6,3 );
		g.addEdge( 4,7,3 );
		g.addEdge( 5,7,5);
		g.addEdge( 5,8,3 );
		g.addEdge( 6,9,13 );
		g.addEdge( 7,9,10 );
		g.addEdge( 8,9,12 );

		// 그래프 출력
		g.printGraph();

		// 최대유량을 찾기위한 포드 풀커슨 알고리즘 호출
		System.out.print("Ford-Fulkerson Max Flow(DFS): ");
		System.out.println(FordFulkerson(g, 0, 9));
	}

	public static float FordFulkerson(Graph g, int source, int sink) {
		// 예외 처리
		if (source == sink) {
			return 0;
		}
		//그래프의 vertex개수 
		int V = g.getvCount();

		// 잔여 용량 그래프 생성
		
		Graph rg = new Graph(V);
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				rg.getAdj()[i][j] = g.getAdj()[i][j];
			}
		}

		//path를 저장하기 위해 bfs로 확인
		int parent[] = new int[V];
		
		float max_flow = 0; // max flow value

		// source에서 sink까지 path가 있어 루프를 반복할 수 있는 동안
		while (bfs(rg, source, sink, parent)) {
			// to store path flow
			float path_flow = Float.MAX_VALUE;

			//bfs 탐색을 통해서 각각의 path의 최솟값을 구하고 최대 유량을 찾는 과정
			for (int i = sink; i != source; i = parent[i]) {
				int j = parent[i];
				path_flow = Math.min(path_flow, rg.getAdj()[j][i]);
				
			}
			

			// 찾아낸 경로에 실제로 유량을 흘려주는 과정
			// 순방향 , 역방향 모두 고려
			for (int i = sink; i != source; i = parent[i]) {
				int j = parent[i];
				rg.getAdj()[j][i] -= path_flow;
				rg.getAdj()[i][j] += path_flow;
				
			}

			// 최대 유량에 추가
			max_flow += path_flow;
		}
		

		return max_flow;
	}
	//bfs : 너비 우선 탐색 
	public static boolean bfs(Graph rg, int source, int sink, int parent[]) {
		// 방분한 정점을 저장하는 배열 
		boolean[] seen = new boolean[rg.getvCount()];
		for (int i = 0; i < rg.getvCount(); i++)
			seen[i] = false;

		LinkedList<Integer> q = new LinkedList<Integer>(); // queue이용

		// path를 통해 흘렀는지 여부를 판단하기 위해 참/거짓으로 저장
		q.add(source);
		seen[source] = true;
		parent[source] = -1;

		// 모든 정점을 방문할 때까지 큐를 통해 반복
		while (!q.isEmpty()) {
			int i = q.poll();
			// i와 연결된 노드를 확인
			for (Integer j : rg.neighbours(i)) {
				// 경로로 사용할 수 있을지 판별하기 위해 방문하지 않은 정점 + 잔여용량이 남아있는지확인
				if ((seen[j] == false) && (rg.getAdj()[i][j] > 0)) {
					q.add(j);
					seen[j] = true;
					parent[j] = i;
				}
			}
		}

		// sink까지 방문했는지의 참 거짓을 판별하기 위한 boolean값 반환
		return seen[sink];
	}

}

