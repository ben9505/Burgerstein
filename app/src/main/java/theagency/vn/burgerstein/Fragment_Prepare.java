package theagency.vn.burgerstein;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import theagency.vn.listenner.NotificationCenter;

/**
 * Created by Administrator PC on 3/13/2015.
 */
public class Fragment_Prepare extends BaseFragment {

    View view;

    public static Fragment_Prepare newInstance() {
        Fragment_Prepare f = new Fragment_Prepare();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prepare,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.btnSymptom,R.id.btnNahrstoff})
    public void onClick(View v) {
        if (v.getId()==R.id.btnSymptom)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.SYMTOM,"");
        else
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.NAHRSTOFF,"");
    }

}
