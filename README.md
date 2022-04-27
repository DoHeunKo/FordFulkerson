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

<figure class="half">
<img src="https://user-images.githubusercontent.com/98294597/165542981-31812cdb-4a08-4858-b3b4-91a4f536159c.PNG" width="300"/>
<img src="https://user-images.githubusercontent.com/98294597/165543018-8f1ce36a-421f-40ef-8d4d-cbe678803e1f.PNG" width="300"/>
figure>  



### 용어
* source : 유량이 시작하는 지점 
* sink : 유량이 도착하는 지점
* capacity : 한 정점에서 다른 정점으로 갈 수 있는 간선의 용량
* flow : 한 정점에서 다른 정점으로 실제로 흐르는 유량  
***주의  : 간선에 표시되어 있는 수는 가중치 값이 아닌 용량***  
<br>



### 특징

1. 용량의 제한      
- 각각의 간선에 흐르는 유량은 간선의 용량을 초과할 수 없다.  

2. 유량의 보존  
- 하나의 정점에 대해서 들어오는 유량의 총합과 나가는 유량의 총합이 같다. 

3. ***유량의 대칭 (포드 폴커슨 알고리즘의 핵심)***  
- 최대 유량을 찾기 위해 정점사이에 가상의 간선(capacity=0)을 만들어 증강 경로를 설정하도록 한다.  
- 실제로는 결과에 영향을 주지 않는다.
<br>

## Ford-Fulkerson  
* 소스(source) 노드에서 시작해서 싱크(sink)노드로 도착하는 유량  
  -유량 네트워크의 모든 간선의 유량을 0으로 초기화   
  -소스에서 싱크로 유량을 더 보낼 수 있는 경로를 찾아 유량 보내기를 반복  
<br>

* 잔여용량(residual capacity)  
> -각 간선에 이미 흐르고 있는 용량 이외에 추가로 보낼 수 있는 유량  
> -r(u,v) = c(u,v) – f(u,v)  
> -증강 경로로 보낼 수 있는 최대 유량 :포함된 간선의 **잔여용량 중에서 가장 작은 값**  
> -증강 경로 찾기 ? **DFS**
> -**더 이상 증강 경로가 존재하지 않을 때까지** 증강경로를 찾고, 보낼 수 있는 최대 유량을 해당 경로를 따라 보내는 작업을 **반복**  

![ford](https://user-images.githubusercontent.com/98294597/165564326-36de8ad1-f633-4375-a4ba-86e7f5a11286.PNG)  

#### 출력된 결과값  
'''
Graph:
0: 1 2 
1: 2 3 
2: 3 
3: 
s--v--t
s--w--t
s--v--w--t
Ford-Fulkerson Max Flow: 5.0
'''

