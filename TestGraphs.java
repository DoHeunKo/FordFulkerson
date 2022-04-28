package ford;

//�ڹٴ� ���� �ɼ����� �ʾƼ� ���� �ҽ��� �޾� �ڵ带 ��⺰�� �ؼ��ϰ� �����ϴµ� �̿��߽��ϴ�.

import java.util.LinkedList;

public class TestGraphs {

	public static void main(String[] args) {
		Graph g = new Graph(10);
		//vertex : source , sink ���� 6��
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

		// �׷��� ���
		g.printGraph();

		// �ִ������� ã������ ���� ǮĿ�� �˰��� ȣ��
		System.out.print("Ford-Fulkerson Max Flow(DFS): ");
		System.out.println(FordFulkerson(g, 0, 9));
	}

	public static float FordFulkerson(Graph g, int source, int sink) {
		// ���� ó��
		if (source == sink) {
			return 0;
		}
		//�׷����� vertex���� 
		int V = g.getvCount();

		// �ܿ� �뷮 �׷��� ����
		
		Graph rg = new Graph(V);
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				rg.getAdj()[i][j] = g.getAdj()[i][j];
			}
		}

		//path�� �����ϱ� ���� bfs�� Ȯ��
		int parent[] = new int[V];
		
		float max_flow = 0; // max flow value

		// source���� sink���� path�� �־� ������ �ݺ��� �� �ִ� ����
		while (bfs(rg, source, sink, parent)) {
			// to store path flow
			float path_flow = Float.MAX_VALUE;

			//bfs Ž���� ���ؼ� ������ path�� �ּڰ��� ���ϰ� �ִ� ������ ã�� ����
			for (int i = sink; i != source; i = parent[i]) {
				int j = parent[i];
				path_flow = Math.min(path_flow, rg.getAdj()[j][i]);
				
			}
			

			// ã�Ƴ� ��ο� ������ ������ ����ִ� ����
			// ������ , ������ ��� ���
			for (int i = sink; i != source; i = parent[i]) {
				int j = parent[i];
				rg.getAdj()[j][i] -= path_flow;
				rg.getAdj()[i][j] += path_flow;
				
			}

			// �ִ� ������ �߰�
			max_flow += path_flow;
		}
		

		return max_flow;
	}
	//bfs : �ʺ� �켱 Ž�� 
	public static boolean bfs(Graph rg, int source, int sink, int parent[]) {
		// ����� ������ �����ϴ� �迭 
		boolean[] seen = new boolean[rg.getvCount()];
		for (int i = 0; i < rg.getvCount(); i++)
			seen[i] = false;

		LinkedList<Integer> q = new LinkedList<Integer>(); // queue�̿�

		// path�� ���� �귶���� ���θ� �Ǵ��ϱ� ���� ��/�������� ����
		q.add(source);
		seen[source] = true;
		parent[source] = -1;

		// ��� ������ �湮�� ������ ť�� ���� �ݺ�
		while (!q.isEmpty()) {
			int i = q.poll();
			// i�� ����� ��带 Ȯ��
			for (Integer j : rg.neighbours(i)) {
				// ��η� ����� �� ������ �Ǻ��ϱ� ���� �湮���� ���� ���� + �ܿ��뷮�� �����ִ���Ȯ��
				if ((seen[j] == false) && (rg.getAdj()[i][j] > 0)) {
					q.add(j);
					seen[j] = true;
					parent[j] = i;
				}
			}
		}

		// sink���� �湮�ߴ����� �� ������ �Ǻ��ϱ� ���� boolean�� ��ȯ
		return seen[sink];
	}

}

