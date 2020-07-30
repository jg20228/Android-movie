package org.wc.movieapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class YtsAdapter extends RecyclerView.Adapter<YtsAdapter.MyViewHolder>{

    private static final String TAG = "YtsAdapter";
    //어댑터는 항상 컬렉션이 있어야한다.

    private List<YtsData.MyData.Movie> movies = new ArrayList<>();

    //컬렉션을 채워줄수 있는 함수도 만들어야한다.
    public void addItem(YtsData.MyData.Movie movie){
        movies.add(movie);
    }

    //컬렉션으로 한번에 받는 함수
    public void addItmes(List<YtsData.MyData.Movie> movies){
        this.movies = movies;
    }

    //뷰 홀더를 생성하고 뷰를 붙여주는 부분
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: cardItem 생성됨");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cardItemView = inflater.inflate(R.layout.car2_item, parent, false);
        //View 하나에 걸고 싶으면
        return new MyViewHolder(cardItemView);
    }

    //재활용 되는 뷰가 호출하여 실행되는 메소드, 뷰홀더를 전달하고 어댑터는
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //책꽂이(View들을 채워두면 됨) 재활용하기 위한, 책갈피 같은거 뷰홀더?
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //Card item에 있는 모든 View를 가져옴
        private ImageView ivPoster;
        private TextView tvTitle;
        private TextView tvRating;
        private RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            //이벤트를 하나하나에 걸고 싶으면 여기에다가
        }

        public void setItem(YtsData.MyData.Movie movie){
            //옛날버전은 get이 아니라 with였다.
            //movie.getMedium_cover_image()은 주소이다.
            Picasso.get().load(movie.getMedium_cover_image()).into(ivPoster);
            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getRating()+"");

            //점수가 0~10점이고 별점이 1~5점이라 2로 나눔
            ratingBar.setRating(movie.getRating()/2);
        }
    }
}
