package nowoscmexico.com.tradinggames.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import nowoscmexico.com.tradinggames.R;

/**
 * Created by vera_john on 11/04/17.
 */

public class GridAdapter extends BaseAdapter {

    public Integer[] mThumbIds;
    public Context context;

    public GridAdapter(Integer[] mT, Context c){
        mThumbIds = mT;
        context = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return mThumbIds[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.imageViewgrid);

            imageView.setImageResource(mThumbIds[i]);

        } else {
            gridView = (View) view;
        }

        return gridView;

    }
}
