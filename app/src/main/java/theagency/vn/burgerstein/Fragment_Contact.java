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
public class Fragment_Contact extends BaseFragment {

    View view;

    public static Fragment_Contact newInstance(boolean isMenuAcion) {
        Fragment_Contact f = new Fragment_Contact();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contactinfo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnPrepareSendEmail)
    public void onClick(View v) {
        if (v.getId() == R.id.btnPrepareSendEmail) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.SENDEMAIL, "");
        }
    }
}
