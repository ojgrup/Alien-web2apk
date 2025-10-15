package mak.webview.wallpaperalien.activity.More;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import mak.webview.wallpaperalien.R;


public class MoreAdapter extends RecyclerView.Adapter {


    public static List<MoreList> webLists;
    public Context context;

    public MoreAdapter(List<MoreList> webLists, Context context) {

        // generate constructors to initialise the List and Context objects

        this.webLists = webLists;
        this.context = context;


    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects

        public TextView html_url;
        public ImageView avatar_url;
        public RelativeLayout linearLayout;
        public Button favoriteImg;

        public ViewHolder(View itemView) {
            super(itemView);


            html_url = (TextView) itemView.findViewById(R.id.username);
            avatar_url = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (RelativeLayout) itemView.findViewById(R.id.linearLayout);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.more_list, parent, false);
                return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,  final int position) {

          if (holder instanceof ViewHolder) {
                    final MoreList webList = webLists.get(position);

                    ((ViewHolder)holder).html_url.setText(webList.getHtml_url());

                    Picasso.get()
                            .load(webList.getAvatar_url())
                            .into( ((ViewHolder)holder).avatar_url);

                    ((ViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                         String buka_more = webList.getPsl_url();
                            String str = buka_more;
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(str)));


                        }
                    });

        }

    }

    public int getItemCount() {
        return webLists.size();
    }

}
