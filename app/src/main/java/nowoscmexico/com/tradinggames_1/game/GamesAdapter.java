package nowoscmexico.com.tradinggames_1.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nowoscmexico.com.tradinggames_1.R;

/**
 * Created by vera_john on 26/12/16.
 */
public class GamesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Object> games;
    private static LayoutInflater inflater = null;

    public GamesAdapter(Context c, ArrayList<Object> p){
        context = c;
        games = p;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;

        final int pos = i;

        if (vi == null)
            vi = inflater.inflate(R.layout.rowgame, null);

        TextView name = (TextView) vi.findViewById(R.id.textViewCustomName);
        TextView cate = (TextView) vi.findViewById(R.id.textViewCustomCategoria);

        name.setText(games.get(i).toString());
        /*PlatilloDao fond = (PlatilloDao)platillos.get(i);
        name.setText(fond.getNombre());
        cate.setText(fond.getPrecio()+"");
        */

        //get the imagesbutton to set methids
        ImageView delete = (ImageView)vi.findViewById(R.id.imageViewDeleteItem);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Deleting game: "+pos, Toast.LENGTH_LONG).show();
            }
        });

        ImageView update = (ImageView)vi.findViewById(R.id.imageViewEditData);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"updating game: "+pos, Toast.LENGTH_LONG).show();
            }
        });

        return vi;

    }
}
