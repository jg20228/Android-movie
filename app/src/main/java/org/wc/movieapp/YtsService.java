package org.wc.movieapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Service와 Repository라고 해도된다.
//interface로 만들라고 문서에 적혀있음
public interface YtsService {

    //데이터를 많이 받아서 Call로 받음
    @GET("list_movies.json")
    Call<YtsData> 영화목록가져오기(
            @Query("sort_by") String sort_by,
            @Query("limit") int limit,//최대 몇개까지
            @Query("page") int page
    );

    //static의 이유 : onCreate가 실행도 되기도 전에 JVM이 실행될때 바로 연결시킬거라서
    //onCreate에서 객체를 안만들고 여기서 만들었다.
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://yts.mx/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())//컨버터(잭슨,Gson) 종류가 많음
            .build();


    //BODY
    //@HEADER
    //@POST는 영화목록가져오기()-<여기 안에 BODY를 넣고;

}




