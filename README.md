# Room + Singleton + Coroutine + ViewModel

<img src="app/src/ref/CoverImg.jpeg" height="400"/>

---
RecyclerView 퍼포먼스 향상시키기</br>

1. <a href = "#content1">아이템 갱신 최소화하기</a></br>
2. <a href = "#content2">DiffUtil 사용하기</a></br>
3. <a href = "#content3">setHasFixedSize(true) 호출하기</a></br>
4. <a href = "#content4">setItemViewCacheSize(int) 호출하기</a></br>
5. <a href = "#content5">setRecycledViewPool(RecyclerViewPool) 호출하기</a></br>
6. <a href = "#content6">setHasStableIds(true) 호출하기</a></br>

* <a href = "#ref">참고링크</a>
---

><a id = "content1">**1. 아이템 갱신 최소화하기**</a></br>

`Adapter.notifyDataSetChanged()`를 사용하여 전체를 갱신하는 대신 특정 아이템만 갱신하도록 한다.</br>

부분적으로 아이템을 갱신하는 메서드</br>
.notifyItemChanged</br>
.notifyItemInserted</br>
.notifyItemRemoved</br>
.notifyItemMoved</br>
.notifyItemRangeChanged</br>
.notifyItemRangeInserted</br>
.notifyItemRangeRemoved</br>

<br></br>



><a id = "content2">**2. DiffUtil 사용하기**</a></br>

`DiffUtil` : 두 리스트의 차이를 계산하고 변경된 부분만 갱신하도록 도와주는 유틸클래스</br>

반드시 구현해야하는 DiffUtil.Callback 메소드</br>
.getOldListSize: 이전 리스트의 사이즈를 반환</br>
.getNewListSIze: 새로운 리스트의 사이즈를 반환</br>
.areItemsTheSame: 두 아이템이 같은지 검사한다.</br>
.areContentsTheSame: 두 아이템의 내용이 같은지 검사한다.</br>

<br></br>



><a id = "content3">**3. setHasFixedSize(true) 호출하기**</a></br>

`RecyclerView.setHasFixedSize(true)`: Adapter 내용이 변경될 때 RecyclerView가 전체 레이아웃을 갱신하지 않도록 할 수 있다.

<img src="app/src/ref/ex1.png" height="200"/>

<br></br>



><a id = "content4">**4. setItemViewCacheSize(int) 호출하기**</a></br>

`RecyclerView.setItemViewCacheSize(int n)`: 아이템 뷰가 Pool 로 들어가기 전에 유지되는 캐시의 사이즈를 결정한다.

<img src="app/src/ref/ex2.png" height="400"/>

<br></br>



><a id = "content5">**5. setRecycledViewPool(RecyclerViewPool) 호출하기**</a></br>

`RecyclerView.setRecycledViewPool(RecyclerViewPool)`: 리사이클러뷰간 view pool을 공유하여 성능을 개선한다.

<img src="app/src/ref/ex3_1.png" height="400"/>
<img src="app/src/ref/ex3_2.png" height="200"/>

<br></br>



><a id = "content6">**6. setHasStableIds(true) 호출하기**</a></br>

`Adapter.setHasStableIds(true)`: 아이템에 대해 고유 식별자를 부여하여 동일한 아이템에 대해 onBindViewHolder 호출을 방지하여 성능을 개선한다.

<img src="app/src/ref/ex4.png" height="200"/>

<br></br>



---

><a id = "ref">**참고링크**</a></br>

-Room build version</br>
https://developer.android.com/jetpack/androidx/releases/room</br>

-Migration 클래스</br>
https://developer.android.com/training/data-storage/room/migrating-db-versions?hl=ko</br>

-Room Database 튜토리얼 + MVVM + Repository(with kotlin)</br>
https://thkim-study.tistory.com/15

-Create ViewModels with dependencies(viewModel Factory 참고 코드)</br>
https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories

-안드로이드 Expandable RecyclerView 만들기</br>
https://android-dev.tistory.com/59

-[Android] RecyclerView Item Swipe 구현하기 (with Kotlin)</br>
https://velog.io/@jeongminji4490/Android-RecyclerView-Item-Swipe-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-with-Kotlin

-RecyclerView 에서 item Swipe 하기 (feat. ItemTouchHelper, ItemTouchUIUtil)</br>
https://velog.io/@nimok97/RecyclerView-%EC%97%90%EC%84%9C-item-Swipe-%ED%95%98%EA%B8%B0-feat.-ItemTouchHelper-ItemTouchUIUtil

https://github.com/hanchang97/android_study/tree/master/selfStudy/Recyclerview/ItmTouchHelper2





