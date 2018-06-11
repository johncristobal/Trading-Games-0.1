package nowoscmexico.com.tradinggames.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nowoscmexico.com.tradinggames.DataBase.ArticuloDao;
import nowoscmexico.com.tradinggames.R;

/**
 * Created by vera_john on 26/12/16.
 */
public class MatchAdapter extends BaseAdapter {

    Context context;
    ArrayList<ArticuloDao> matchs;
    private static LayoutInflater inflater = null;

    public MatchAdapter(Context c, ArrayList<ArticuloDao> p){
        context = c;
        matchs = p;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return matchs.size();
    }

    @Override
    public Object getItem(int i) {
        return matchs.get(i);
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

        name.setText(matchs.get(i).getTitulo());
        cate.setText(matchs.get(i).getCategoria());
        /*PlatilloDao fond = (PlatilloDao)platillos.get(i);
        name.setText(fond.getNombre());
        cate.setText(fond.getPrecio()+"");
        */

        //get the imagesbutton to set methids
        ImageView delete = (ImageView)vi.findViewById(R.id.imageViewDeleteItem);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Deleting item: "+pos, Toast.LENGTH_LONG).show();
            }
        });

        /*ImageView update = (ImageView)vi.findViewById(R.id.imageViewEditData);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"updating item: "+pos, Toast.LENGTH_LONG).show();
            }
        });*/

        return vi;
    }
}
