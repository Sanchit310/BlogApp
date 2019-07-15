package example.android.myapplication;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class BloggerAPI {

    private static final String url = "https://www.googleapis.com/blogger/v3/blogs/7502371742020592000/posts/";
    private  static final String key = "AIzaSyB-GD2CFKIJCEBPPh40neaVClUR7G6DebE";


    public static PostService postService = null;
    public static PostService getService(){

        if(postService == null){

            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            postService = retrofit.create(PostService.class);

        }
        return  postService;

    }


    public interface PostService{

        @GET("?key=" + key)
        Call<PostList> getPostList();

    }
}
