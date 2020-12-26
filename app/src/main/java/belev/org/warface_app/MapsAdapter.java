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

import com.bumptech.glide.Glide;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MapsAdapter extends BaseAdapter {

    Context context;
    List<Maps> rowItem;
    //public static int[] layout_arr = { R.layout.achivment_item2, R.layout.achivment_item, R.layout.achivment_item };

    MapsAdapter(Context context, List<Maps> rowItem) {
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

        Maps currentMaps = rowItem.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_maps, null);

			ViewHolder holder = new ViewHolder();
			holder.nameView = (TextView)convertView.findViewById(R.id.maps_name);
			holder.aboutView = (TextView)convertView.findViewById(R.id.maps_descript);
            holder.typeView = (TextView)convertView.findViewById(R.id.maps_type);
			holder.iconView = (ImageView)convertView.findViewById(R.id.maps_logo);
			convertView.setTag(holder);
        }

	    if(currentMaps != null) {
	        ViewHolder holder = (ViewHolder)convertView.getTag();
	        holder.nameView.setText(currentMaps.getName());
	        holder.aboutView.setText(Html.fromHtml(currentMaps.getAbout()));
            holder.typeView.setText(currentMaps.getType());
	        //holder.iconView.setImageDrawable(currentMaps.getIconID());

            Glide.with(context)
                    .load(currentMaps.getThumbnail())
                    .dontTransform()
                    .transition(withCrossFade())
                    .into(holder.iconView);
	    }

        return convertView;

    }
    
	static class ViewHolder {
		TextView nameView;
		TextView aboutView;
        TextView typeView;
		ImageView iconView;
	}

}

