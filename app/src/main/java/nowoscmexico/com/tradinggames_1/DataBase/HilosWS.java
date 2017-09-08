package nowoscmexico.com.tradinggames_1.DataBase;

import android.os.AsyncTask;

import java.util.ArrayList;

import nowoscmexico.com.tradinggames_1.R;

/**
 * Created by vera_john on 20/03/17.
 */

public class HilosWS {

    public HilosWS(){}

    public static class WSTasktoget extends AsyncTask<String,Void,ArrayList<ArticuloDao>> {

        ArrayList<ArticuloDao> lista = new ArrayList<>();

        @Override
        protected ArrayList<ArticuloDao> doInBackground(String... strings) {

            //call ws to get games...the last games inserted into firebase...?
            //meanwhile...
            ArticuloDao dao1 = new ArticuloDao();
            dao1.setFoto(""+ R.drawable.fifa);
            dao1.setTitulo("fifa");
            lista.add(dao1);

            ArticuloDao dao2 = new ArticuloDao();
            dao2.setFoto(""+ R.drawable.mario);
            dao2.setTitulo("mario");
            lista.add(dao2);

            ArticuloDao dao3 = new ArticuloDao();
            dao3.setFoto(""+ R.drawable.papermario);
            dao3.setTitulo("papermario");
            lista.add(dao3);

            ArticuloDao dao4 = new ArticuloDao();
            dao4.setFoto(""+ R.drawable.starfox);
            dao4.setTitulo("starfix");
            lista.add(dao4);

            ArticuloDao dao5 = new ArticuloDao();
            dao5.setFoto(""+ R.drawable.tloz);
            dao5.setTitulo("tloz");
            lista.add(dao5);

            return lista;
        }
    }
}
