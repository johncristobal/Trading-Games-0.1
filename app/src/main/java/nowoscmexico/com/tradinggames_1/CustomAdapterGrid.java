package nowoscmexico.com.tradinggames_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import nowoscmexico.com.tradinggames_1.game.SimpleViewG;

/**
 * Created by vera_john on 12/12/16.
 */
public class CustomAdapterGrid extends BaseAdapter{

    public String [] result;
    public Context context;

    private static LayoutInflater layi;

    public CustomAdapterGrid(TrendsGames trend, String [] data){
        result = data;
        context = trend;

        layi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int i) {
        return result[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder{
        TextView text;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int pos = i;

        Holder holder = new Holder();

        View rowView;

        rowView = layi.inflate(R.layout.listitem,null);
        holder.text = (TextView)rowView.findViewById(R.id.gameLabel);

        holder.text.setText(result[i]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(context,"Clicked: "+result[pos],Toast.LENGTH_SHORT).show();

            /*View vistas;

            vistas = layi.inflate(R.layout.activity_trends_games,null);

            RelativeLayout gridRel = (RelativeLayout)vistas.findViewById(R.id.gridRelative);
            RelativeLayout simpleRel = (RelativeLayout)vistas.findViewById(R.id.simpleRelative);

            gridRel.setVisibility(View.INVISIBLE);
            simpleRel.setVisibility(View.VISIBLE);*/

            Intent intent = new Intent(context, SimpleViewG.class);
            intent.putExtra("nombre",result[pos]);
            context.startActivity(intent);
            }
        });

        return rowView;
    }
}
