package svastek.marriage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tatson on 08-01-2016.
 */
public class ImageAdapter extends BaseAdapter {
    private final List<Item1> mItems = new ArrayList<Item1>();
    private final LayoutInflater mInflater;

Context context;
    public ImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
        mItems.add(new Item1( "Cake",R.drawable.cake));
        mItems.add(new Item1( "Photo",R.drawable.photography));
        mItems.add(new Item1("Flower",R.drawable.florists));
        mItems.add(new Item1("Anchors",R.drawable.anchors));
        mItems.add(new Item1("Beauty",R.drawable.beauty));
        mItems.add(new Item1("Cards",R.drawable.cards));
        mItems.add(new Item1("Catering",R.drawable.catering));
        mItems.add(new Item1("Decorators",R.drawable.decorators));
        mItems.add(new Item1("Hall",R.drawable.halls));
        mItems.add(new Item1("Vehicles",R.drawable.vehicles));
        mItems.add(new Item1("Music",R.drawable.music));
        mItems.add(new Item1("Cloth",R.drawable.clothing));


    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item1 getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;


        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));

        }

        picture = (ImageView) v.getTag(R.id.picture);

        final Item1 item = getItem(i);

        picture.setImageResource(item.drawableId);
      //  picture.setScaleType(ImageView.ScaleType.FIT_XY);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, String.valueOf(item.name), Toast.LENGTH_SHORT).show();

                Intent in= new Intent(context,Sooncome.class);
                in.putExtra("cat", item.name);

               Log.d("I",item.name);
                context.startActivity(in);
            }
        });
        return v;
    }

    public static class Item1 {

        public final int drawableId;
  public  final  String name;
        Item1(String name,int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
