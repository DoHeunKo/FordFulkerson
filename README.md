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

> 특정한 지점에서 다른 지점까지 데이터가 얼마나 많이 흐르는지 측정하기위해 네트워크 플로우를 이용하고 유량 네트워크에서 주어진 두 정점 사이의 최대 유량을 찾기 위해 Ford Folkerson Algorithm(FFA)을 이용할 것이다.  
> 하지만 DFS를 이용한 포드풀커슨알고리즘은 특수한 경우 한계점이 있기 때문에 그것을 개선한 에드먼트-카프 알고리즘(BFS)에 대해 서도 알아보려고 한다.
> 코드의 성능측정은 java의 DFS와 BFS를 이용하고, 그래프의 노드 수에 따라 최대유량이 어떻게 달라지는지 확인해 볼 것이다.  

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

## Ford-Fulkerson(DFS)  
* 소스(source) 노드에서 시작해서 싱크(sink)노드로 도착하는 유량  
  -유량 네트워크의 모든 간선의 유량을 0으로 초기화   
  -소스에서 싱크로 유량을 더 보낼 수 있는 경로를 찾아 유량 보내기를 반복  
<br>

* 잔여용량(residual capacity)  
> -각 간선에 이미 흐르고 있는 용량 이외에 추가로 보낼 수 있는 유량  
> -r(u,v) = c(u,v) – f(u,v)  
> -증강 경로로 보낼 수 있는 최대 유량 :포함된 간선의 **잔여용량 중에서 가장 작은 값**  

---  

#### 알고리즘
  
```
1. 그래프, 소스, 싱크를 입력으로 받는다.

2. 입력 그래프와 동일한 잔여용량 그래프를 만들고 maxflow=0으로 초기화  

3. DFS가 소스에서 싱크까지 증가경로를 찾는다      
* 최대 path flow를 찾는다  
* 잔여 용량 그래프를 업데이트하고 경로에 따라 간선의 최솟값을 찾는다  
* maxflow에 pathflow를 추가 한다  
4. maxflow를 반환한다  
```

####  코드 알고리즘  
```
Ford-Fulkerson( G, s, t)  
1 for each edge(u,v) G.E에 포함  
2 	(u, v) .f =0  
3	while there exists a path p from s to t in the residual network Gf  
4		cf(p)= main{ cf(u,v) : (u,v) is in p}  
5		for each edge (u,v) in p  
6			if( u,v) 는 E에 포함  
7				(u,v).f = (u,v).f + cf(p)  
8			else (v,u).f = (v,u).f – cf(p)  

```

---

<img src="https://user-images.githubusercontent.com/98294597/165581049-e338f5b8-bb4e-493f-b5f3-535a075c321b.PNG">
  
<figure class="third">
<img src="https://user-images.githubusercontent.com/98294597/165584500-b80c3f4b-933e-4c94-adee-449ae77635d1.png">
<img src="https://user-images.githubusercontent.com/98294597/165584547-9defece8-7481-4665-a8ed-86083c9540fa.png">
<img src="https://user-images.githubusercontent.com/98294597/165584619-db741482-9b07-4f04-8922-3f260e2feb7a.png">
<img src="https://user-images.githubusercontent.com/98294597/165585315-b61a2d56-91c9-4c77-b8c6-78b685aa17b0.png">
<img src="https://user-images.githubusercontent.com/98294597/165585424-e69c0591-89d1-4283-bcf7-c983d63e13f8.png">

figure>

#### 출력된 결과값  
``` 
Graph:
0: 1 4 
1: 2 3 
2: 5 
3: 2 5 
4: 1 3 
5: 
Ford-Fulkerson Max Flow: 10.0
```  
> 포드 풀거슨 알고리즘을 생각하지않고 단순히 최대용량을 따라서 계산한 경우  
> min[s-A-B-t] =min[7,5,8] : 5  
> maxflow=5
> min[s-A-C-t] =min[7-5,3,5] : 2  
> maxflow=5+2  
  
<img src="https://user-images.githubusercontent.com/98294597/165587091-796f9981-350d-4547-b41c-286652194427.PNG">

#### DFS사용시 Ford-Folkerson의 worst case  
  
![image](https://user-images.githubusercontent.com/98294597/165586450-ed006ca8-11e6-4863-bf46-7f5190f37ee5.png)  
> 위와 같은 그래프의 경우 각각의 단계마다 1 유량 증가  
> 최대 유량을 찾기 위해서는 1,000,000 단계가 걸린다. 만약 DFS(Depth First Search)대신에 BFS(Breadth First Search)를 사용한다면 2단계만에 최대 유량을 얻을 수 있다.  

![DFS(1000000)](https://user-images.githubusercontent.com/98294597/165678101-d8bada2f-e516-4643-a100-5dded9243f82.PNG)  


## Edmonds-Karp(BFS)
그래프 알고리즘에서 Ford-Folkerson과 Edmonds-Karp는 가지고 있는 자료를 100%로 탐색한다는 점에서 모두 brute force 알고리즘에 속한다. 네트워크 유량 알고리즘을 DFS 혹은 BFS를 사용하는지 여부에 따라 결정된다. 탐색에 따라 두 경우의 시간복잡도는 다르다. 하지만 DFS(Ford-Folkerson)로 구현할 경우 찾는 해가 최적이 아닐 가능성이 있기 때문에 BFS를 대부분 사용하는 경우가 많다. 다음의 예시와 시간복잡도의 측면에서 어떤 방식을 사용하는 것이 더 최적의 최대유량을 얻을 수 있는 지 확인해보자.  
  
  
BFS를 사용했을 때 개선되는 점(예시)  

시간복잡도 비교  
  
시간복잡도에 따라 최적의 유량을 얻을 수 있는 그래프 특징(예시)  성능(결과)  
  
내 생각  

코드 분석 (java) 

이미지 파일 나란히 정렬
포드 폴거슨 이미지 설명 (일반적 vs FFA의 특징과 비교)  
다른 형태의 그래프와 비교(복잡) 
특수한 경우 설명  
특징 (수식 넣어서 보완)
알고리즘 플로우차트로 설명
