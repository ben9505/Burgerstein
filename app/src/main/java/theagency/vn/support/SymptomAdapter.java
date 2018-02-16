package theagency.vn.support;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import theagency.vn.burgerstein.R;

/**
 * Created by Administrator PC on 4/7/2015.
 */
public class SymptomAdapter extends BaseAdapter {

    Context mContext;
    private LayoutInflater mLayoutInflater = null;
    ArrayList<Products> mArrayProducts;
    String[] arrayTitle;
    String[] arrInforOnlyRow;
    Helper mHelper;

    public SymptomAdapter(Context mContext, ArrayList<Products> mArrayProducts, String[] pArrayTitle, String[]  arrInforOnlyRow) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArrayProducts = mArrayProducts;
        arrayTitle = pArrayTitle;
        this.arrInforOnlyRow = arrInforOnlyRow;
        mHelper = Helper.shareIns(mContext);
    }
    @Override
    public int getCount() {
        Log.e("Total",String.valueOf(mArrayProducts.size()));
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
        ViewHolder holder;
        if (convertView == null) {
            v = mLayoutInflater.inflate(R.layout.item_listview_detail, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }


        String langID = mContext.getResources().getString(R.string.langID);
        int index = Integer.parseInt(mArrayProducts.get(position).getmNahrstoffID());
        int indexSymptom = Integer.parseInt(mArrayProducts.get(position).getmSymptomID());
        holder.mBgYellow.setVisibility(View.GONE);


            if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("1")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(arrInforOnlyRow[0]);
            }else if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("2")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(arrInforOnlyRow[1]);
            }else if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("3")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(arrInforOnlyRow[2]);
            }else if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("4")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(arrInforOnlyRow[3]);
            }else if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("5")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(Html.fromHtml(arrInforOnlyRow[4]));
            }else if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("6")){
                holder.mBgYellow.setVisibility(View.VISIBLE);
                holder.mInfo01.setVisibility(View.GONE);
                holder.mInfo02.setVisibility(View.GONE);
                holder.mInfo03.setVisibility(View.GONE);
                holder.mInfo04.setText(arrInforOnlyRow[5]);
            }else{
                holder.mInfo01.setVisibility(View.VISIBLE);
                holder.mInfo02.setVisibility(View.VISIBLE);
                holder.mInfo03.setVisibility(View.VISIBLE);
                holder.mInfo01.setText(arrayTitle[index - 1]);
                holder.mInfo02.setText(mArrayProducts.get(position).getmWirkung());
                holder.mInfo03.setText(Html.fromHtml(mArrayProducts.get(position).getmDosierung()));

                holder.mInfo03.setText(Html.fromHtml(mArrayProducts.get(position).getmDosierung()));
                if (indexSymptom==25) {
                    if (index==33) {
                        if (langID.equalsIgnoreCase("de")) {
                            holder.mInfo03.setText(Html.fromHtml("Aminos&auml;uren mit<br />Vitaminen, Mineralstoffen,<br />Spurenelementen<br />(morgens)<br /><br />Evtl. vegetabiles Protein mit ausgewogenem Aminogramm."));
                        }else{
                            holder.mInfo03.setText(Html.fromHtml("Acides aminés avec des vitamines, mineraux et oligo-éléments (le matin)<br /><br />Évent. végétale avec<br />aminogramm équilibré."));
                        }

                    }
                }
            if (langID.equalsIgnoreCase("fr")) {
                if ( indexSymptom==1 ) {
                    if (position==0) {
                        holder.mInfo02.setText(Html.fromHtml("Calcium: stabilise les mastocytes<br />Carence en mangan&egrave;se: accro&icirc;t la tendance aux allergies<br />Zinc: inhibe la lib&eacute;ration d&rsquo;histamine"));
                    }
                }
                if (indexSymptom == 21) {
                    holder.mInfo03.setText(Html.fromHtml("<b>300-600 mg</b> magnésium élémentaire (répartir matin/soir)<br />Également sous forme de sels mineraux alcalins avec calcium et zinc."));
                }


                if (indexSymptom == 27) {
                    if (mArrayProducts.get(position).getmWirkung().equalsIgnoreCase("27")) {
                        holder.mInfo01.setText(Html.fromHtml("Acide folique"));
                        holder.mInfo02.setText(Html.fromHtml("Nécessaire au métabolisme<br />" +
                                "normal de l'homocystéine; des<br />" +
                                "taux élevés d'homocystéine<br />" +
                                "peuvent entraîner un risque<br />" +
                                "accru de fractures osseuses"));
                        holder.mInfo03.setText(Html.fromHtml("<b>0,4-0,8 mg</b> (répartir<br />matin/soir)"));
                    }
                }
                if (indexSymptom == 7) {
                    String txtWirkung = holder.mInfo02.getText().toString();
                    holder.mInfo02.setText(replaceText(txtWirkung));
                }
                if (indexSymptom == 2) {
                    String txtWirkung = holder.mInfo02.getText().toString();
                    holder.mInfo02.setText(replaceText(txtWirkung));
                }
                if (indexSymptom == 27) {
                    String txtWirkung = holder.mInfo02.getText().toString();
                    holder.mInfo02.setText(replaceText(txtWirkung));
                }
                if (indexSymptom == 31) {
                    if (position == 0) {
                        if (langID.equalsIgnoreCase("fr")) {
                            holder.mInfo03.setText(Html.fromHtml("Recommandation:<br /><b>600 μg</b> d’acide folique,<br /><b>20-30 mg</b> de fer,<br /><b>10 mg</b> de zinc,<br /><b>150 µg</b> de iode, vitamines, oligo-éléments, minéraux, etc.). Il est recommandé de prendre le produit avant même la conception."));
                        }
                    }

                }
                if (indexSymptom == 12) {
                    if (position == 1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>1ère semaine: 3x 400 UI</b>, aux repas (répartir matin/midi/ soir<br /><br /><b>2ème semaine: 2x 400 UI</b>, aux repas (répartir matin/soir)<br /><br /><b>Dose chronique/d’entretien:</b><br /><b>400 UI</b> (le matin)<br /><br />Prendre chaque fois avec 0,5 g de<br />vitamine C."));
                    }
                }
                if (indexSymptom == 24) {
                    if (position == 0) {
                        holder.mInfo03.setText(Html.fromHtml("Le matin:<br/><b>2,5 g</b> Glutamine,<br/><b>2 g</b> Arginine,<br/><b>700 mg</b> Lysine,<br/><b>1 g</b> Glycine,<br/><b>1 g</b> Taurine"));
                    }
                }
                if (indexSymptom == 31) {
                    if (position == 2) {
                        holder.mInfo03.setText(Html.fromHtml("Recommandation:<br />min. <b>200 mg</b> de DHA pur, s’ajoute à l‘apport journalier recommandé d’acides gras oméga-3 chez l‘adulte."));
                    }
                }
            }else{
                if ( indexSymptom==1 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>800 mg</b> Calcium,<br />" +
                                "<b>300 mg</b> Magnesium,<br />" +
                                "<b>10 mg</b> Zink,<br />"+
                                "<b>2 mg</b> Mangan<br />" +
                                "Pulver: nüchtern vor dem " +
                                "Morgenessen / Tabletten:<br />" +
                                "magensaftresistent"));
                    }
                }
                if ( indexSymptom==2 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>14-60 mg</b> (initial bei nachgewiesenem Mangel auch höher dosiert möglich, nach Laborwerten), (aufgeteilt morgens/abends)"));
                    }
                }
                if ( indexSymptom==3 ) {
                    if (position==1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>800 mg</b> Calcium,<br />" +
                                "<b>300 mg</b> Magnesium,<br />" +
                                "<b>10 mg</b> Zink,<br />"+
                                "<b>2 mg</b> Mangan<br />" +
                                "Pulver: nüchtern vor dem " +
                                "Morgenessen / Tabletten:<br />" +
                                "magensaftresistent"));
                    }
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>1,5-3 g</b> EPA (+DHA), zu den Mahlzeiten (aufgeteilt morgens/abends)"));
                    }
                }
                if ( indexSymptom==4 ) {
                    if (position==1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>1,5-3 g</b> EPA (+DHA), zu den Mahlzeiten (aufgeteilt morgens/abends)"));
                    }
                }
                if ( indexSymptom==5 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>3 g</b> EPA (+DHA), zu den Mahlzeiten (aufgeteilt morgens/abends)"));
                    }
                }
                if ( indexSymptom==6 ) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br />", " ");
                    text = text.replaceAll("<br/>", " ");
                    holder.mInfo03.setText(Html.fromHtml(text));
                }
                if ( indexSymptom==7 ) {
                    if (position==1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>300-600 mg</b> elementares Magnesium (aufgeteilt morgens/abends)"));
                    }
                    if (position==2) {
                        holder.mInfo03.setText(Html.fromHtml("<b>10-75 mg</b> kompletter<br/>B-Komplex inkl. Pantothensäure, Cholin, Folsäure, Vitamin B6 (aufgeteilt morgens/abends)"));
                    }
                    if (position==4) {
                        holder.mInfo03.setText(Html.fromHtml("<b>400 mg</b>, zu den Mahlzeiten (aufgeteilt morgens/mittags)"));
                    }
                }
                if ( indexSymptom==10 ) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br />", " ");
                    text = text.replaceAll("<br/>", " ");
                    holder.mInfo03.setText(Html.fromHtml(text));
                }
                if ( indexSymptom==12 ) {
                    if (position==1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>1. Woche: 3x 400 I.E.</b>, zu den Mahlzeiten (aufgeteilt morgens/mittags/abends)<br/><br/><b>2. Woche: 2x 400 I.E.</b>, zu den Mahlzeiten (aufgeteilt morgens/mittags/abends)<br/><br/><b>Chronische Erhaltungsdosis:</b><b>400 I.E.</b> (morgens)<br/><br/>Zusammen mit<br />2x 0,5 g Vitamin C einnehmen."));
                    }
                    if (position==2) {
                        holder.mInfo03.setText(Html.fromHtml("<b>800 mg</b> Calcium,<br />" +
                                "<b>300 mg</b> Magnesium,<br />" +
                                "<b>10 mg</b> Zink,<br />"+
                                "<b>2 mg</b> Mangan<br />" +
                                "Pulver: nüchtern vor dem " +
                                "Morgenessen / Tabletten:<br />" +
                                "magensaftresistent"));
                    }
                }
                if ( indexSymptom==15 ) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br />", " ");
                    text = text.replaceAll("<br/>", " ");
                    holder.mInfo03.setText(Html.fromHtml(text));
                }
                if (indexSymptom==17) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br />", " ");
                    text = text.replaceAll("<br/>", " ");
                    holder.mInfo03.setText(Html.fromHtml(text));
                }
                if ( indexSymptom==21 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>300-600 mg</b> elementares Magnesium (aufgeteilt morgens/abends)<br/><br/>Auch in Form basischer Mineralsalze, kombiniert mit Kalzium und Zink, möglich."));
                    }
                }
                if ( indexSymptom==24 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("Morgens:<br/><b>2,5 g</b> Glutamin,<br /><b>2 g</b> Arginin,<br/><b>700 mg</b> Lysin,<br /><b>1 g</b> Glycin,<br/><b>1 g</b> Taurin"));
                    }
                }
                if ( indexSymptom==28 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>60-90 mg</b>, zu den Mahlzeiten (morgens/mittags), Tablette im Mundraum einwirken lassen"));
                    }
                }
                if ( indexSymptom==29 ) {
                    if (position==2) {
                        if (mHelper.getDpWidth()>500) {
                            holder.mInfo02.setText("Mildert PMS-Symptome (Prostaglandinsynthese, PGE1)");
                        }else{
                            holder.mInfo02.setText("Mildert PMS-Symptome (Prostaglandinsynthe-\nse, PGE1)");
                        }

                    }
                }
                if ( indexSymptom==30 ) {
                    if (position==1) {
                        holder.mInfo03.setText(Html.fromHtml("<b>800-1’600 mg</b> (entspricht<br /><b>50-100 mg</b> elementarem Magnesium), abends, 1 h vor dem Zubettgehen"));
                    }
                }
                if ( indexSymptom==31 ) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br/>"," ");
                    text = text.replaceAll("<br />"," ");
                    holder.mInfo03.setText(Html.fromHtml(text));
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("Empfehlung:<br/><b>600 μg</b> Folsäure,<br/><b>20-30 mg</b> Eisen,<br /><b>10 mg</b> Zink,<br/><b>150 μg</b> Jod, Vitamine, Spurenelemente, Mineralstoffe, etc. Sollte bereits präkonzeptionell eingenommen werden."));
                    }
                    if (position==2) {
                        holder.mInfo03.setText(Html.fromHtml("Empfehlung: mind. <b>200 mg</b> reines DHA zusätzlich zu der<br />für Erwachsene<br />empfohlenen<br />Tagesdosierung an Omega-3 Fettsäuren"));
                    }
                }
                if ( indexSymptom==34 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>10-75 mg</b> kompletter<br/>B-Komplex inkl. Pantothensäure, Cholin, Folsäure, Vitamin B6 (aufgeteilt morgens/abends)"));
                    }
                }
                if ( indexSymptom==35 ) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("<b>800 mg</b> Calcium,<br />" +
                                "<b>300 mg</b> Magnesium,<br />" +
                                "<b>10 mg</b> Zink,<br />"+
                                "<b>2 mg</b> Mangan<br />" +
                                "Pulver: nüchtern vor dem " +
                                "Morgenessen / Tabletten:<br />" +
                                "magensaftresistent"));
                    }
                }
                if (indexSymptom==13) {
                    if (position==0) {
                        holder.mInfo03.setText(Html.fromHtml("Pr&auml;vention: <b>10-30 mg</b> (aufgeteilt morgens/abends)<br />Akuter Infekt:<br /><b>60-90 mg</b> (aufgeteilt morgens/ mittags/ abends)"));
                    }
                }
                if (indexSymptom==14) {
                    holder.mInfo03.setText(Html.fromHtml("Pr&auml;vention: <b>1-1,5 g</b><br />(aufgeteilt morgens/ mittags)<br/>Therapie: <b>3 g</b> (aufgeteil morgens/ mittags/ abends)"));
                }
                if (indexSymptom==19) {
                    String text = mArrayProducts.get(position).getmDosierung();
                    text = text.replaceAll("<br/>"," ");
                    text = text.replaceAll("<br />"," ");
                    holder.mInfo03.setText(Html.fromHtml(text));
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
        public TextView mInfo01,mInfo02,mInfo03,mInfo04;
        public LinearLayout mBgYellow;
        public ViewHolder(View base) {
            mInfo01 = (TextView) base.findViewById(R.id.textViewInfo01);
            mInfo02 = (TextView) base.findViewById(R.id.textViewInfo02);
            mInfo03 = (TextView) base.findViewById(R.id.textViewInfo03);
            mInfo04 = (TextView) base.findViewById(R.id.textViewInfo04);
            mInfo01.setTypeface(mHelper.FONT_REGULAR);
            mInfo02.setTypeface(mHelper.FONT_REGULAR);
            mInfo03.setTypeface(mHelper.FONT_REGULAR);
            mInfo04.setTypeface(mHelper.FONT_IT);
            mBgYellow = (LinearLayout) base.findViewById(R.id.backgroundYellow);
        }
    }
}
