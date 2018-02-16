package theagency.vn.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import theagency.vn.burgerstein.R;

/**
 * Created by Administrator PC on 3/18/2015.
 */
public class ItemArrayAdapter extends BaseAdapter {

    ArrayList<Items> mArray;
    Context mContext;
    private LayoutInflater mLayoutInflater = null;
    Helper mHelper;

    public ItemArrayAdapter(ArrayList<Items> mArray, Context mContext) {
        this.mArray = mArray;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHelper = Helper.shareIns(mContext);

    }

    @Override
    public int getCount() {
        return mArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.items, parent,false);
        }
        TextView textView = (TextView) v.findViewById(R.id.itemsDetail);
        textView.setTypeface(mHelper.FONT_REGULAR);
        textView.setText(String.valueOf(mArray.get(position).getmID()) + ". "+ mArray.get(position).getmName());
        if (mArray.get(position).isSelect()){
            textView.setAlpha(1.0f);
        }else{
            textView.setAlpha(0.5f);
        }
        return v;
    }
}
