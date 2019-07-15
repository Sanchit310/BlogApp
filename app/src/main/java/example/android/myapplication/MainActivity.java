package example.android.myapplication;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.postlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setuptoolbar();
        getData();
    }

    private void setuptoolbar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar =(Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void getData(){
        Call<PostList> postList = BloggerAPI.getService().getPostList();
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {

                PostList list = response.body();
                List<Item> myitemList = list.getItems();
                Log.d("____","check : " + myitemList.get(0).getPublished());

                PostAdapter adapter = new PostAdapter(MainActivity.this, myitemList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
