package theagency.vn.support;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import theagency.vn.burgerstein.R;

/**
 * Created by Administrator PC on 3/16/2015.
 */
public class MyArrayAdapter extends BaseAdapter {
    Context mContext;
    private LayoutInflater mLayoutInflater = null;
    ArrayList<Products> mArrayProducts;
    String[] arrayTitle;
    Helper mHelper;

    public MyArrayAdapter(Context mContext, ArrayList<Products> mArrayProducts, String[] pArrayTitle) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArrayProducts = mArrayProducts;
        arrayTitle = pArrayTitle;
        mHelper = Helper.shareIns(mContext);
    }

    @Override
    public int getCount() {
        return mArrayProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            v = mLayoutInflater.inflate(R.layout.item_listview_detail, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        String langID = mContext.getResources().getString(R.string.langID);
        int index = Integer.parseInt(mArrayProducts.get(position).getmSymptomID());
        int indexNahrstoff = Integer.parseInt(mArrayProducts.get(position).getmNahrstoffID());
        viewHolder.mInfo01.setText(arrayTitle[index - 1]);
        viewHolder.mInfo02.setText(mArrayProducts.get(position).getmWirkung());
        viewHolder.mInfo03.setText(Html.fromHtml(mArrayProducts.get(position).getmDosierung()));
        if (index==25) {
            if (indexNahrstoff==33) {

                if (langID.equalsIgnoreCase("de")) {
                    viewHolder.mInfo03.setText(Html.fromHtml("Aminos&auml;uren mit Vitaminen, Mineralstoffen, Spurenelementen (morgens)<br /><br />Evtl. vegetabiles Protein mit ausgewogenem Aminogramm."));
                }

            }
        }
        if (langID.equalsIgnoreCase("fr")) {
            if (indexNahrstoff==1) {
                if (position==1){
                    viewHolder.mInfo03.setText(Html.fromHtml("Le matin:<br/><b>2,5 g</b> Glutamine,<br/><b>2 g</b> Arginine,<br/><b>700 mg</b> Lysine,<br/><b>1 g</b> Glycine,<br/><b>1 g</b> Taurine"));
                }
            }
            if (indexNahrstoff==2) {
                if (position==0) {
                    viewHolder.mInfo02.setText(Html.fromHtml("Calcium: stabilise les mastocytes<br />Carence en mangan&egrave;se: accro&icirc;t la tendance aux allergies<br />Zinc: inhibe la lib&eacute;ration d&rsquo;histamine"));
                }
            }
            if (indexNahrstoff==9){
                String text = mArrayProducts.get(position).getmWirkung();
                viewHolder.mInfo02.setText(replaceText(text));
            }
            if (index==25) {
                if (indexNahrstoff==33) {
                        viewHolder.mInfo03.setText(Html.fromHtml("Acides amin?s avec des vitamines, mineraux et oligo-?l?ments (le matin)<br /><br />?vent. v?g?tale avec<br />aminogramm ?quilibr?."));
                }
            }
            if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("27")){
                viewHolder.mInfo02.setText(Html.fromHtml("N&eacute;cessaire au m&eacute;tabolisme " +
                        "normal de l'homocyst&eacute;ine; des " +
                        "taux &eacute;lev&eacute;s d'homocyst&eacute;ine " +
                        "peuvent entra&icirc;ner un risque " +
                        "accru de fractures osseuses"));
                viewHolder.mInfo03.setText(Html.fromHtml("<b>0,4-0,8 mg</b> (r&eacute;partir<br />matin/soir)"));

            }
            if (viewHolder.mInfo01.getText().toString().equalsIgnoreCase("Burnout")) {
                String a = viewHolder.mInfo02.getText().toString().replaceAll("\n"," ");
                viewHolder.mInfo02.setText(a);
            }
            if (indexNahrstoff==23){
                if (position==5) {
                    String text = "<b>300-600 mg</b> magn&eacute;sium &eacute;l&eacute;mentaire (r&eacute;partir matin/soir)<br />" +
                            "&Eacute;galement sous forme de " +
                            "sels mineraux alcalins avec " +
                            "calcium et zinc.";
                     viewHolder.mInfo03.setText(Html.fromHtml(text));
                }
            }

            if (indexNahrstoff==24){
                if (position==0) {
                    String text = "<b>800-1&rsquo;600 mg</b> (correspond &agrave;<br />50-100 mg de magn&eacute;sium &eacute;l&eacute;mentaire), le soir<br/>1 h avant le coucher";
                    viewHolder.mInfo03.setText(Html.fromHtml(text));
                }
            }
            if (indexNahrstoff==26) {
                String a = "Recommandation:<br /><b>600 &micro;g</b> d&rsquo;acide folique,<br /><b>20-30 mg</b> de fer,<br /><b>10 mg</b> de zinc,<br /><b>150 &micro;g</b> de iode, vitamines, oligo-&eacute;l&eacute;ments, min&eacute;raux, etc.). Il est recommand&eacute; de prendre le produit avant m&ecirc;me la conception.";
                viewHolder.mInfo03.setText(Html.fromHtml(a));
            }
            if (indexNahrstoff==30) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==33) {
                String text = "Acides amin&eacute;s avec des vitamines, mineraux et oligo-&eacute;l&eacute;ments (le matin)<br /><br />&Eacute;vent. v&eacute;g&eacute;tale avec<br />aminogramm &eacute;quilibr&eacute;.";
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==41) {
                if (position==2) {
                    String text = " Co-facteur n&eacute;cessaire &agrave; la" +
                            " formation endog&egrave;ne de" +
                            " l'hormone du sommeil" +
                            " m&eacute;latonine";
                    viewHolder.mInfo02.setText(Html.fromHtml(text));
                }
            }
            if (indexNahrstoff==45) {
                if (position==3) {
                    String text = "<b>1&egrave;re semaine: 3x 400 UI</b>, aux repas (r&eacute;partir matin/ midi/ soir<br /><br /><b>2&egrave;me semaine: 2x 400 UI</b>, aux repas (r&eacute;partir matin/ soir)<br /><br /><b>Dose chronique/d&rsquo;entretien:</b><br /><b>400 UI</b> (le matin)<br /><br />Prendre chaque fois avec 0,5 g de vitamine C.";
                    viewHolder.mInfo03.setText(Html.fromHtml(text));
                }
            }
        }else {
            if (indexNahrstoff==1) {
                if ( position==0) {
                    viewHolder.mInfo01.setText(Html.fromHtml("Ged&auml;chtnisprobleme / Konzentrationsst&ouml;- rungen / Lernen"));
                }
                if ( position==1 ){
                    viewHolder.mInfo03.setText(Html.fromHtml("Morgens:<br/><b>2,5 g</b> Glutamin,<br /><b>2 g</b> Arginin,<br/><b>700 mg</b> Lysin,<br /><b>1 g</b> Glycin,<br/><b>1 g</b> Taurin"));
                }

            }
            if (indexNahrstoff==2) {
                if (position==0) {

                    if (langID.equalsIgnoreCase("de")) {
                        if (mHelper.getDpWidth()>500) {
                            viewHolder.mInfo02.setText(Html.fromHtml("Kalzium: stabilisiert die Mastzellen<br />Manganmangel: erh&ouml;ht die Allergieneigung<br />Zink: hemmt Histaminfreisetzung"));
                        }
                    }
                }
                viewHolder.mInfo03.setText(Html.fromHtml("<b>800 mg</b> Calcium,<br />" +
                        "<b>300 mg</b> Magnesium,<br />" +
                        "<b>10 mg</b> Zink,<br />"+
                        "<b>2 mg</b> Mangan<br />" +
                        "Pulver: n&uuml;chtern vor dem " +
                        "Morgenessen / Tabletten:<br />" +
                        "magensaftresistent"));
            }
            if (indexNahrstoff==3) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if ( indexNahrstoff==4 ) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if ( indexNahrstoff==9 ) {
                    viewHolder.mInfo03.setText(Html.fromHtml("<b>14-60 mg</b> (initial bei nachgewiesenem Mangel auch h&ouml;her dosiert m&ouml;glich, nach Laborwerten), (aufgeteilt morgens/abends)"));
            }
            if ( indexNahrstoff==10 ) {
                if (position==1) {
                    viewHolder.mInfo02.setText(Html.fromHtml(viewHolder.mInfo02.getText().toString()));
                }

            }
            if (indexNahrstoff==13) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==18) {
                viewHolder.mInfo03.setText(Html.fromHtml("Pr&auml;vention: <b>1-1,5 g</b> (aufgeteilt morgens/ mittags)<br/>Therapie: <b>3 g</b> (aufgeteilt morgens/ mittags/ abends)"));
            }
            if ( indexNahrstoff==23 ) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
                if (position==5) {
                    viewHolder.mInfo03.setText(Html.fromHtml("<b>300-600 mg</b> elementares Magnesium (aufgeteilt morgens/abends)<br/><br/>Auch in Form basischer Mineralsalze, kombiniert mit Kalzium und Zink, m&ouml;glich."));
                }

            }
            if ( indexNahrstoff==24 ) {
                viewHolder.mInfo03.setText(Html.fromHtml("<b>800-1&rsquo;600 mg</b> (entspricht<br /><b>50-100 mg</b> elementarem Magnesium), abends, 1 h vor dem Zubettgehen"));
            }
            if (indexNahrstoff==28) {
                if (position==4) {
                    if (mHelper.getDpWidth()>500) {
                        viewHolder.mInfo02.setText("Mildert PMS-Symptome (Prostaglandinsynthese, PGE1)");
                    }else{
                        viewHolder.mInfo02.setText("Mildert PMS-Symptome (Prostaglandinsynthe-\nse, PGE1)");
                    }
                }
            }
            if (indexNahrstoff==29) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==30) {
                if (position==1) {
                    viewHolder.mInfo01.setText(Html.fromHtml("Ged&auml;chtnisprobleme / Konzentrationsst&ouml;- rungen / Lernen"));
                }
            }
            if (indexNahrstoff==31) {
                if (position==0) {
                    viewHolder.mInfo01.setText(Html.fromHtml("Ged&auml;chtnisprobleme / Konzentrationsst&ouml;- rungen / Lernen"));
                }
            }
            if (indexNahrstoff==32) {
                if (position==1) {
                    viewHolder.mInfo01.setText(Html.fromHtml("Ged&auml;chtnisprobleme / Konzentrationsst&ouml;- rungen / Lernen"));
                }
            }
            if (indexNahrstoff==32) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==37) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==41) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
            }
            if (indexNahrstoff==45) {
                if (position==3) {
                    viewHolder.mInfo03.setText(Html.fromHtml("<b>1. Woche: 3x 400 I.E.</b>, zu den Mahlzeiten (aufgeteilt morgens/mittags/abends)<br/><br/><b>2. Woche: 2x 400 I.E.</b>, zu den Mahlzeiten (aufgeteilt morgens/mittags/abends)<br/><br/><b>Chronische Erhaltungsdosis:</b><b>400 I.E.</b> (morgens)<br/><br/>Zusammen mit<br />2x 0,5 g Vitamin C einnehmen."));
                }
            }
            if (indexNahrstoff==47) {
                String text = mArrayProducts.get(position).getmDosierung();
                text = text.replaceAll("<br/>"," ");
                text = text.replaceAll("<br />"," ");
                viewHolder.mInfo03.setText(Html.fromHtml(text));
                if (position==1) {
                    viewHolder.mInfo03.setText(Html.fromHtml("Pr&auml;vention: <b>10-30 mg</b> (aufgeteilt morgens/abends)<br />Akuter Infekt:<br /><b>60-90 mg</b> (aufgeteilt morgens/ mittags/ abends)"));
                }
            }
        }
        return v;
    }
    public  String replaceText(String text){
        String txtWirkung = text.replaceAll("\n"," ");
        return  txtWirkung;
    }
    class ViewHolder {
        public TextView mInfo01,mInfo02,mInfo03;
        public ViewHolder(View base) {
            mInfo01 = (TextView) base.findViewById(R.id.textViewInfo01);
            mInfo02 = (TextView) base.findViewById(R.id.textViewInfo02);
            mInfo03 = (TextView) base.findViewById(R.id.textViewInfo03);
            mInfo01.setTypeface(mHelper.FONT_REGULAR);
            mInfo02.setTypeface(mHelper.FONT_REGULAR);
            mInfo03.setTypeface(mHelper.FONT_REGULAR);
        }
    }
}
