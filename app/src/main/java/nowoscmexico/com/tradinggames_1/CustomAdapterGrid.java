package nowoscmexico.com.tradinggames_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import nowoscmexico.com.tradinggames_1.game.MatchActivity;
import nowoscmexico.com.tradinggames_1.game.SimpleViewG;
import nowoscmexico.com.tradinggames_1.user.UserActivity;

/**
 * Created by vera_john on 12/12/16.
 */
public class CustomAdapterGrid extends BaseAdapter{

    public String [] result;
    public Context context;
    public String sesion;
    private static LayoutInflater layi;

    private boolean flag=false;

    public CustomAdapterGrid(TrendsGames trend, String [] data,String s){
        result = data;
        context = trend;
        sesion = s;
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
        TextView textGame;
        TextView textAutor;
        ImageView match;
        ImageView game;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int pos = i;

        final Holder holder = new Holder();

        View rowView;

        rowView = layi.inflate(R.layout.listitem,null);
        holder.textGame = (TextView)rowView.findViewById(R.id.gameLabel);
        holder.textAutor = (TextView)rowView.findViewById(R.id.autorLabel);
        holder.match = (ImageView)rowView.findViewById(R.id.imageViewUser);
        holder.game = (ImageView)rowView.findViewById(R.id.imageViewgame);

        holder.textGame.setText(result[i]);

        holder.game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Activity: "+result[pos],Toast.LENGTH_SHORT).show();
                launchSimpleView(pos);
            }
        });


        holder.textGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(context,"Clicked: "+result[pos],Toast.LENGTH_SHORT).show();

            /*View vistas;

            vistas = layi.inflate(R.layout.activity_trends_games,null);

            RelativeLayout gridRel = (RelativeLayout)vistas.findViewById(R.id.gridRelative);
            RelativeLayout simpleRel = (RelativeLayout)vistas.findViewById(R.id.simpleRelative);

            gridRel.setVisibility(View.INVISIBLE);
            simpleRel.setVisibility(View.VISIBLE);*/

            launchSimpleView(pos);
            }
        });

        holder.match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //When click in ,atch =>verify if sesion active
                //IF sesion => save in matches
                //else => start sesion
                if(sesion.equals("1")){
                    //Add and delete from the DB table match
                    //if not in list
                    if(!flag){
                        flag=true;
                        holder.match.setImageResource(android.R.drawable.btn_star_big_on);
                        Toast.makeText(context,"Juego agregado a lista de Match.\n"+result[pos],Toast.LENGTH_SHORT).show();
                    }else{
                        flag=false;
                        holder.match.setImageResource(android.R.drawable.btn_star);
                        //Toast.makeText(context,"Juego eliminado de lista de Match.\n"+result[pos],Toast.LENGTH_SHORT).show();
                    }
                    //else
                    //Toast.makeText(context,"Juego elimiando de lista de Match.\n"+result[pos],Toast.LENGTH_SHORT).show();

                }else {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("nombre",result[pos]);
                    intent.putExtra("activity","match");
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }

    public void launchSimpleView(int p){
        Intent intent = new Intent(context, SimpleViewG.class);
        intent.putExtra("nombre",result[p]);
        context.startActivity(intent);
    }
}
