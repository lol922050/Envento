package svastek.marriage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;
/**
 * Created by Tatson on 09-01-2016.
 */
public class Adapter_vendor_detail extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Vendor_deatil_items> movieItems;
    private ImageLoader imageLoader;
    public Adapter_vendor_detail(Activity activity, List<Vendor_deatil_items> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.detail_row_frag, null);
        imageLoader = CustomVolleyRequest.getInstance(activity).getImageLoader();

        TextView t1 = (TextView) convertView.findViewById(R.id.textView_t1);

        TextView t2 = (TextView) convertView.findViewById(R.id.textView_t2);
        TextView t3 = (TextView) convertView.findViewById(R.id.textView_t3);
        NetworkImageView i1 =(NetworkImageView)convertView.findViewById(R.id.user_image);
        Vendor_deatil_items  m = movieItems.get(position);

        t1.setText(m.getName1());

        imageLoader.get(m.getimageUrl(), ImageLoader.getImageListener(i1, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        t2.setText(m.getName2());
        t3.setText(m.getName3());
        i1.setImageUrl(m.getimageUrl(), imageLoader);

        return convertView;
    }

}