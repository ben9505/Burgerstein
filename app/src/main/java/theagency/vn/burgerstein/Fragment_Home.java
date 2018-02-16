package theagency.vn.burgerstein;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import theagency.vn.listenner.NotificationCenter;

/**
 * Created by Administrator PC on 3/10/2015.
 */
public class Fragment_Home extends BaseFragment implements View.OnClickListener {

    View homeView;

    @BindView(R.id.cover_menu) RelativeLayout cover_menu;
    @BindView(R.id.cover_btn_top) ImageButton btnTop;
    @BindView(R.id.cover_btn_bottom) ImageButton btnBottom;
    @BindView(R.id.btnUns) Button btnUns;
    @BindView(R.id.btnEmgega) Button btnEmgega;
    @BindView(R.id.btnDetail) Button btnDetail;
    @BindView(R.id.btnContact) Button btnContact;
    @BindView(R.id.btnLink) Button btnLink;


    public static Fragment_Home newInstance() {
        Fragment_Home f = new Fragment_Home();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,homeView);
        return homeView;
    }

    long lastSecondClick = 0;

    @OnClick({R.id.cover_btn_top,R.id.cover_btn_bottom,R.id.btnUns,R.id.btnEmgega,R.id.btnDetail,R.id.btnContact,R.id.btnLink})
    public void onClick(View v) {
        if (lastSecondClick >= (System.currentTimeMillis() -1000))
            return;
        lastSecondClick = System.currentTimeMillis();

        if (v.getId() == R.id.btnUns)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.UBER);
        if (v.getId() == R.id.btnEmgega)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.EMGAGEMENT);
        if (v.getId() == R.id.btnDetail)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.SYMTOM_NARSTOFF);
        if (v.getId() == R.id.btnContact)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.CONTACTINFO);
        if (v.getId() == R.id.btnLink)
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.LINK);
        if (v.getId() == R.id.cover_btn_top || v.getId() == R.id.cover_btn_bottom)
            cover_menu.setVisibility(cover_menu.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);

    }
}