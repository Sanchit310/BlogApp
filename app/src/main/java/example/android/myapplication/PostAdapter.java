package example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Item> items;

    public PostAdapter(Context context, List<Item> items) {

        this.context = context;
        this.items = items;
    }


    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item, parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int position) {
        final Item item = items.get(position);
        postViewHolder.PostHead.setText(item.getTitle());
        postViewHolder.PostCont.setText(item.getContent());

        Document document = Jsoup.parse(item.getContent());
        postViewHolder.PostCont.setText(((org.jsoup.nodes.Document) document).text());

        Elements elements = document.select("img");

        Glide.with(context).load(elements.get(0).attr("src")).into(postViewHolder.pimg);

        postViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("url",item.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView pimg;
        TextView PostHead;
        TextView PostCont;

        public PostViewHolder(View itemView) {
            super(itemView);

            pimg = itemView.findViewById(R.id.img);
            PostHead = itemView.findViewById(R.id.posttitle);
            PostCont = itemView.findViewById(R.id.postcontnt);
        }
    }
}
