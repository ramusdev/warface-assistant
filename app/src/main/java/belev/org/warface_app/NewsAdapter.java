package belev.org.warface_app;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    Context context;
    List<News> rowItem;

    NewsAdapter(Context context, List<News> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        News currentNews = rowItem.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_news, null);

            ViewHolder holder = new ViewHolder();
            holder.titleView = (TextView)convertView.findViewById(R.id.maps_name);
            holder.descriptionView = (TextView)convertView.findViewById(R.id.maps_descript);
            holder.pubdateView = (TextView)convertView.findViewById(R.id.maps_type);
            holder.imageView = (ImageView)convertView.findViewById(R.id.maps_logo);
            convertView.setTag(holder);
        }

        if(currentNews != null) {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.titleView.setText(currentNews.getTitle());
            holder.descriptionView.setText(currentNews.getPreviewText());
            holder.pubdateView.setText(News.formatFromDatabaseToView(currentNews.getDate()));
            //holder.iconView.setImageDrawable(currentMaps.getIconID());
            Glide.with(context)
                    .load(currentNews.getImage())
                    .dontTransform()
                    .transition(withCrossFade())
                    .transform(new MultiTransformation(new GranularRoundedCorners(20, 20, 0, 0)))
                    .into(holder.imageView);
        }

        return convertView;

    }

    static class ViewHolder {
        TextView titleView;
        TextView descriptionView;
        TextView pubdateView;
        ImageView imageView;
    }

}

