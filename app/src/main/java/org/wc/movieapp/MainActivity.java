package org.wc.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    // 항상 걸자
    private Context mContext = MainActivity.this;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private YtsAdapter ytsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        init();

        //다운로드
        initDownload();

        initListener();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        ytsAdapter = new YtsAdapter();
    }


    private void initDownload() {
        //Yts 받는 함수
        YtsService ytsService = YtsService.retrofit.create(YtsService.class);
        Call<YtsData> call = ytsService.영화목록가져오기("rating", 10, 1);
        //Call에는 많은 기능들이 있다

        //call은 null값이 들어가는게 아니라 상자를 준다(프로미스)

        //enqueue - 콜백을 받아줌
        call.enqueue(new Callback<YtsData>() {
            //이렇게 해두면 콜백받았을때 바로보여줄려고
            @Override
            public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                if (response.isSuccessful() == true) {
                    YtsData ytsData = response.body();
                    //여기서 리사이클러뷰에 연결
                    //콜백스택안이라서 return할수가 없어서.
                    ytsAdapter.addItmes(ytsData.getData().getMovies());
                    recyclerView.setAdapter(ytsAdapter);
                }
            }

            @Override
            public void onFailure(Call<YtsData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "다운로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener(){
        //리사클러뷰가 들고있음
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(mContext, "안녕", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        
    }
}