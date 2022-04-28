# Ford-Fulkerson
<br>

<ol>
<li>Network Flow</li>
<li>Ford-Fulkerson Algorithm</li>
<li>Edmonds-Karp algorithm</li>
</ol>

---

## Network Flow

<br>

> 네트워크 유량은 굉장히 강력한 최적화 문제로 특정한 지점에서 다른 지점까지 데이터가 얼마나 많이 흐르는지 측정하기위해 네트워크 플로우를 이용하고 유량 네트워크에서 주어진 두 정점 사이의 최대 유량을 찾기 위해 Ford Folkerson Algorithm(FFA)을 이용할 것이다.  
> 하지만 DFS를 이용한 포드풀커슨알고리즘은 특수한 경우 한계점이 있기 때문에 그것을 개선한 에드먼트-카프 알고리즘(BFS)에 대해 서도 알아보려고 한다.
> 코드의 성능측정은 아래 세가지의 그래프로 Ford-Fulkerson의 DFS와 BFS를 적용하고, 그래프의 노드 수에 따라 최대유량이 어떻게 달라지는지 확인해 볼 것이다.  
    
![image](https://user-images.githubusercontent.com/98294597/165701076-36ba865a-4f1a-466b-adb8-bb541f6a4632.png)



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
     ![image](https://user-images.githubusercontent.com/98294597/165703814-1fdb25ee-1fbd-48a8-8779-62a72855d241.png)  
     

2. 유량의 보존  
    - 하나의 정점에 대해서 들어오는 유량의 총합과 나가는 유량의 총합이 같다. 
    ![image](https://user-images.githubusercontent.com/98294597/165703998-da599ee5-57bf-4d21-9c75-77442837b778.png)  
    

3. ***유량의 대칭 (포드 폴커슨 알고리즘의 핵심)***  
    - 최대 유량을 찾기 위해 정점사이에 가상의 간선(capacity=0)을 만들어 증강 경로를 설정하도록 한다.  
    - 실제로는 결과에 영향을 주지 않는다.  
    ![image](https://user-images.githubusercontent.com/98294597/165704097-22f916e0-2bf0-4fd5-91d1-15ae1a1729e1.png)  
    

<br>

---

## Ford-Fulkerson(DFS)  
* 소스(source) 노드에서 시작해서 싱크(sink)노드로 도착하는 유량  
> -유량 네트워크의 모든 간선의 유량을 0으로 초기화   
> -소스에서 싱크로 유량을 더 보낼 수 있는 경로를 찾아 유량 보내기를 반복  
<br>

* 잔여용량(residual capacity)  
> -각 간선에 이미 흐르고 있는 용량 이외에 추가로 보낼 수 있는 유량  
> -r(u,v) = c(u,v) – f(u,v)  
> -증강 경로로 보낼 수 있는 최대 유량 :포함된 간선의 **잔여용량 중에서 가장 작은 값**  
 
  
![netflow](https://user-images.githubusercontent.com/98294597/165693090-8f1131d5-9362-40c9-8f41-9c7d0ffccebb.PNG)
 
#### 포드 풀거슨 알고리즘을 사용하지 않고 최대용량을 따라서 계산한 경우  (① ② )
```
min[s,A,B,t]= min[7,5,8] : 5  
maxflow=5  
min[s,D,A,C,t]= min[4,3,3,5] : 3
maxflow=5+3  
min[s,D,A,C,B,t]= min[4-3,2,3,8-5] : 1
maxflow=5+3+1
```  
> 3번의 탐색을 통해서 maxflow가 9라는 결과를 얻었다. 용량이 다 차지않은 간선이 있지만 유량이 이동할 수 있는 증강경로를 찾기가 힘들다. 
> 이 때 포드-폴커슨 방법을 이용하면 A-D사이에 D-A와 반대로 유량을 흘려주면 유량의 대칭성의 성질로 3->2로 상쇄가 되고 s-A-D-C-t로 maxflow가 1이 추가되는 것을 알 수 있다.  

![net_ret](https://user-images.githubusercontent.com/98294597/165693545-f016d58f-62ba-4c25-bb22-3a2e49c7fbb0.PNG)

#### 출력된 결과값  
![image](https://user-images.githubusercontent.com/98294597/165695455-cb9fd882-1a4f-4782-9ce3-fd6a74d45845.png) 
  
#### 다른 형태의 그래프  
![image](https://user-images.githubusercontent.com/98294597/165701847-80d1cf78-ff4c-4d11-9936-f87540f7e884.png)

#### 출력된 결과값
![image](https://user-images.githubusercontent.com/98294597/165702229-5fdeefa9-c835-4ef0-8142-8e37229b7abf.png)



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



#### DFS사용시 Ford-Folkerson의 worst case 
  
![image](https://user-images.githubusercontent.com/98294597/165586450-ed006ca8-11e6-4863-bf46-7f5190f37ee5.png)  
> 위와 같은 그래프의 경우 각각의 단계마다 1 유량 증가  
> 최대 유량을 찾기 위해서는 1,000,000 단계가 걸린다. 만약 DFS(Depth First Search)대신에 BFS(Breadth First Search)를 사용한다면 2단계만에 최대 유량을 얻을 수 있다.  
***증가 경로의 수는 입력 크기에서 기하급수적으로 늘어날 수 있다!!***  

![DFS(1000000)](https://user-images.githubusercontent.com/98294597/165678101-d8bada2f-e516-4643-a100-5dded9243f82.PNG)  

#### ***증강 경로 선택시 주의할 점***  
* 효율적으로 증강경로 찾기  
* 적은 반복  
> 경로 선택에 따라  
>    - 몇몇의 결과는 지수 알고리즘을 나타낸다  
>    - 효율적인 선택은 다항식 알고리즘을 나타낸다  
>    - 용량이 정수가 아닌 무리수일 경우 종료를 보장하지 않는다  

sol) ***1. max bottleneck 용량  
     2. 충분히 큰 bottleneck 용량  
     3. 가장 적은 수의 간선***    
     



---

## Edmonds-Karp(BFS)
> 그래프 알고리즘에서 Ford-Folkerson과 Edmonds-Karp는 가지고 있는 자료를 100%로 탐색한다는 점에서 모두 brute force 알고리즘에 속한다. 네트워크 유량 알고리즘을 DFS 혹은 BFS를 사용하는지 여부에 따라 결정된다. 탐색에 따라 두 경우의 시간복잡도는 다르다. 하지만 DFS(Ford-Folkerson)로 구현할 경우 찾는 해가 최적이 아닐 가능성이 있기 때문에 BFS를 대부분 사용하는 경우가 많다.  
> 다음의 예시와 시간복잡도의 측면에서 어떤 방식을 사용하는 것이 더 최적의 최대유량을 얻을 수 있는 지 확인해보자.    
 

### 복잡도  

||time complexity|space complexity| 
|:---|:---:|:---:| 
|Ford-Folkerson|O(Ef)|O(E + V)| 
|Edmonds-Karp|O(VE^2)|O(E+V)|  
  
***(E:간선의 수, f:최대유량, V:정점의 수)***
  
### 시간복잡도에 따라 최적의 유량을 얻을 수 있는 특징   
> 위의 시간 복잡도에서 알 수 있듯이 Ford-Fulkerson은 flow에 영향을 받고 Edmonds-Karp는 flow보다는 edge에 더 많은 영향을 받게 된다. Ford-Fulkerson의 최악의 경우가 있다고 해서 무조건 BFS를 사용하는 Edmonds-karp가 성능이 좋다고 할 수 없다. 
> 그래프에서 flow값이 적고 edge가 더 많은 경우에는 Ford-Fulkerson이 더 나은 결과를 가져올 수 있다.  

---

### 전체 내용 정리(fordfulkerson) 
> * 소스에서 시작해 싱크까지 보낼 수 있는 데이터의 최대 용량을 구하는 알고리즘  
> * DFS에서 특수한 경우 증강경로의 수가 exponential의 형태를 띌 수 있다.  
> * 에드먼트 카프는 BFS를 사용하는 유량 네트워크 알고리즘의 종류이지만 무조건 포드 풀커슨보다 뛰어난 것은 아니다.  
 
  
 ### 참고자료 
 
 
 (https://gseok.gitbooks.io/algorithm/content/b124-d2b8-c6cc-d06c-d50c-b85c-c6b0/d3ec-b4dc-d480-cee4-c2a828-ford-fulkerson-c560-b4dc-baac-b4dc-ce74-d50428-edmonds-karp.html)  
 
 (http://cs.williams.edu/~shikha/teaching/spring20/cs256/lectures/Lecture19.pdf)  
 
 (https://m.blog.naver.com/jh20s/221298145631)  
 
 (https://www.topcoder.com/thrive/articles/edmonds-karp-and-dinics-algorithms-for-maximum-flow)  
 
 (https://stackoverflow.com/questions/16652902/having-trouble-understanding-and-implementing-the-ford-fulkerson-algorithm)  
 
 (https://courses.engr.illinois.edu/cs473/fa2012/notes/22-maxflowalgs.pdf)  
 
 (https://ocw.tudelft.nl/wp-content/uploads/Algoritmiek_Choosing_Good_Augmenting_Paths.pdf)  
 
 (https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm)  
 
 (https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/07NetworkFlowI.pdf)  
 
