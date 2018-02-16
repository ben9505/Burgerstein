package theagency.vn.burgerstein;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator PC on 3/13/2015.
 */
public class Fragment_Uber extends BaseFragment {

    View view;

    public static Fragment_Uber newInstance() {
        Fragment_Uber f = new Fragment_Uber();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uber,container,false);
        return view;
    }

}
