# FordFulkerson
<br>

# 네트워크 유량

<ol>
<li>Network Flow</li>
<li>Ford-Fulkerson Algorithm</li>
<li>Edmonds-Karp algorithm</li>
</ol>

---

## Network Flow

<br>

> 특정한 지점에서 다른 지점까지 데이터가 얼마나 많이 흐르는지 측정하기위해 네트워크 플로우를 이용하고 유량 네트워크에서 주어진 두 정점 사이의 최대 유량을 찾기 위해 Ford Folkerson Algorithm(FFA)을 이용할 것이다. 하지만 DFS를 이용한 포드풀커슨알고리즘은 특수한 경우 한계점이 있기 때문에 그것을 개선한 에드먼트-카프 알고리즘(BFS)에 대해 서도 알아보려고 한다.  
코드의 성능측정은 java의 DFS와 BFS를 이용하고, 그래프의 노드 수에 따라 최대유량이 어떻게 달라지는지 확인해 볼 것이다.

<br>

### 용어
* source : 유량이 시작하는 지점 
* sink : 유량이 도착하는 지점
* capacity : 한 정점에서 다른 정점으로 갈 수 있는 간선의 용량
* flow : 한 정점에서 다른 정점으로 실제로 흐르는 유량
***주의  : 간선에 표시되어 있는 수는 가중치 값이 아닌 용량***  
<br>

---

<br>
### 특징
1_ 용량의 제한  
- 각각의 간선에 흐르는 유량은 간선의 용량을 초과할 수 없다.  
$ \forall\ (u,v) \in E : f(u,v) \leq c(u,v) $  
