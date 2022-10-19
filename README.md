# Room + Singleton + Coroutine

<img src="https://github.com/HYUNJUNEPARK/ImageRepository/blob/master/androidProgramming/SQLite.jpg" height="400"/>

---
1. <a href = "#content1">Room</a></br>
2. <a href = "#content2">Room 구성요소</a></br>
-Database</br>
-Dao</br>
-Entity</br>
3. <a href = "#content3">Migration</a></br>
* <a href = "#ref">참고링크</a>
---
><a id = "content1">**1. Room**</a></br>

```kotlin
private lateinit var db: MemoDatabase
//...
db = DBProvider.getInstance(this)!!
```

-ORM(Object Relational Mapping) 라이브러리 : 객체(class)와 RDB 를 매핑하고 변환하는 기술</br>
-SQLite 에서는 쿼리문을 알아야했던것과 달리 ORM 라이브러리인 Room 을 사용하면 쿼리를 몰라도 코드만으로 DB 컨트롤 가능</br>
-클래스 파일에 ORM 을 적용하면 자동으로 쿼리로 변환해 테이블을 생성</br>
-어노테이션 프로세싱(Annotation Processing API) :</br>
클래스명, 변수명 위에 '@명령어' 를 사용하는 것으로 명령어가 컴파일 시 코드로 생성되기 때문에 실행 시 발생할 수 있는 성능 문제가 많이 개선됨</br>

```
//build
plugin {
    id 'kotlin-kapt' //어노테이션 프로세싱을 코틀린에서 사용 가능하게 해줌
}

dependencies {
    //Room
    def roomVersion = "2.4.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    
     //Coroutine
     implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
     implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
}
```
<br></br>
<br></br>

><a id = "content2">**2. Room 구성요소**</a></br>


**(1)RoomHelper**</br>
-RoomDatabase() 를 상속받는 추상 클래스로 Dao 를 상속받는 추상 메서드가 내부에 있음</br>
`abstract fun myDao(): MyDao`</br>
-Entity, Dao 의 정보가 모여 있는 껍데기 클래스로 최종적으로 Room 에 만들어져 있는 코드를 사용할 수 있음</br>
-entities : Room 라이브러리가 사용할 테이블 클래스 목록</br>
`entities = arrayOf(Entity::class)` or `entities = [Entity::class]`</br>
-version : DB 버전</br>
-exportSchema : true -> 스키마 정보를 파일로 출력</br>

```kotlin
@Database(entities = [MyEntity::class], version = 1, exportSchema = false)
abstract class MyRoomHelper: RoomDatabase() {
    abstract fun myDao(): MyDao
}
```
<br></br>
**(2)Dao**</br>
-데이터베이스에 엑세스하는데 사용되는 메소드(select, inset, delete, join 등)를 갖고 있음</br>
-@RoomDao : DAO 라는 것을 명시(Data Access Object)</br>
-@Query : 쿼리를 직접 작성하고 실행</br>
-@Insert, @Delete</br>

```kotlin
@Dao
interface MyDao {
    @Query("SELECT * FROM room_table")
    fun getAll(): List<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: MyEntity)

    @Delete
    fun delete(table: MyEntity)
}
```
<br></br>
**(3)Entity**</br>
-DB 테이블의 정보를 담고 있는 클래스</br>
-@Entity(tableName = "") : 해당 어노테이션이 적용된 클래스를 찾아 테이블로 변환</br>
-@ColumnInfo(name = "") : 컬럼에 대한 정보를 담고 있는 변수위에 사용됨</br>
-@Ignore : 해당 변수가 테이블과 관계없는 변수라는 것을 나타냄</br>
-@PrimaryKey(autoGenerate = true)</br>

```kotlin
@Entity(tableName = "room_table")
class MyEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo(name = "date")
    var datetime: Long = 0

    constructor(content: String, datetime: Long) {
        this.content = content
        this.datetime = datetime
    }
}
```


<br></br>
<br></br>

><a id = "content3">**3. Migration**</a></br>

-DB 수정 클래스</br>
-앱 업데이트로 DB 테이블을 변경될 때 기기 내 이미 있는 DB 데이터는 유지하는 것이 중요함</br>
-Room 라이브러리는 Migration 클래스를 지원해 유저의 데이터를 유지함</br>

```kotlin
object MigrateDatabase {
    val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val alter = "ALTER table room_table add column new_title text"
            database.execSQL(alter)
        }
    }
}
```

<br></br>
<br></br>
---

><a id = "ref">**참고링크**</a></br>

Room build version</br>
https://developer.android.com/jetpack/androidx/releases/room</br>

Migration 클래스</br>
https://developer.android.com/training/data-storage/room/migrating-db-versions?hl=ko</br>
