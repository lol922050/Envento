package svastek.marriage;

/**
 * Created by Tatson on 08-01-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader1,imageLoader2;
    private Context context;
    String name1,address1;
    //List of superHeroes
    List<Vendor_items> superHeroes;

    public CardAdapter(List<Vendor_items> superHeroes, Context context){
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Vendor_items superHero =  superHeroes.get(position);

        imageLoader1 = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader2 = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader1.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.work, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
imageLoader2.get(superHero.getwork(), ImageLoader.getImageListener(holder.logo, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.work.setImageUrl(superHero.getImageUrl(), imageLoader1);
        holder.logo.setImageUrl(superHero.getwork(), imageLoader2);

      holder.name.setText(superHero.getName());
        holder.address.setText(String.valueOf(superHero.getAdd()));



    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView work;
        public TextView name;
        public TextView address;
        CircularNetworkImageView logo;


        public ViewHolder(final View itemView) {
            super(itemView);
            work = (NetworkImageView) itemView.findViewById(R.id.imageViewwork);
          name = (TextView) itemView.findViewById(R.id.name_row);
            address= (TextView) itemView.findViewById(R.id.addrs_row);
            logo = (CircularNetworkImageView) itemView.findViewById(R.id.imageViewlogo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //
                    int postion= getAdapterPosition();
                    Vendor_items superHero =  superHeroes.get(postion);
                    name1=superHero.getName();
                    address1= superHero.getAdd();
               //     Toast.makeText(context, String.valueOf(postion), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemView.getContext(),Detail_vendor.class);
                    i.putExtra("name",name1);
                    i.putExtra("address",address1);

                    context.startActivity(i);
                }
            });

        }


    }
}
