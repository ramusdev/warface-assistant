package rb.dev.warfaceapp;

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

public class Adapter extends BaseAdapter {

    Context context;
    List<Achivment> rowItem;
    public static int[] layout_arr = { R.layout.achivment_item2, R.layout.achivment_item, R.layout.achivment_item };

    Adapter(Context context, List<Achivment> rowItem) {
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

        Achivment currentAchivment = rowItem.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(layout_arr[currentAchivment.getAchivmentid()], null);

			ViewHolder holder = new ViewHolder();
			holder.nameView = (TextView)convertView.findViewById(R.id.badge_name);
			holder.aboutView = (TextView)convertView.findViewById(R.id.badge_about);
			holder.iconView = (ImageView)convertView.findViewById(R.id.badge_logo);
            convertView.setTag(holder);
        }

	    if(currentAchivment != null) {
	        ViewHolder holder = (ViewHolder)convertView.getTag();
	        holder.nameView.setText(currentAchivment.getName());
	        holder.aboutView.setText(Html.fromHtml(currentAchivment.getAbout()));
	        //holder.iconView.setImageDrawable(currentAchivment.getIconID());
            //Glide.with(context).load("").placeholder(currentAchivment.getIconID()).into(holder.iconView);
            //Glide.with(context).load("").asBitmap(currentAchivment.getIconID()).into(holder.iconView);
            //override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            //dontTransform()
            Glide.with(context).load(currentAchivment.getThumbnail()).dontTransform().into(holder.iconView);
	    }

        return convertView;

    }
    
	static class ViewHolder {
		TextView nameView;
		TextView aboutView;
		ImageView iconView;
	}

}

