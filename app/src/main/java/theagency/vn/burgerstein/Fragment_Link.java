package theagency.vn.burgerstein;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import theagency.vn.support.Helper;

/**
 * Created by Administrator PC on 9/18/2015.
 */
public class Fragment_Link extends BaseFragment implements View.OnClickListener {

    View view;
    Helper mHelper;
    @BindView(R.id.link01) ImageView link01;
    @BindView(R.id.link02) ImageView link02;

    Timer timer;
    boolean isAnimation;
    MyTimerTask myTimerTask;

    public static Fragment_Link newInstance() {
        Fragment_Link f = new Fragment_Link();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_link, container, false);
        ButterKnife.bind(this,view);
        isAnimation=false;
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 900);
        return view;
    }
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAnimation){
                        isAnimation=false;
                        link01.animate().alpha(0.4f).setDuration(900).start();
                        link02.animate().alpha(0.4f).setDuration(900).start();
                    }else{
                        link01.animate().alpha(1f).setDuration(900).start();
                        link02.animate().alpha(1f).setDuration(900).start();
                        isAnimation=true;
                    }
                }
            });

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!timer.equals(null)) {
            timer.cancel();
            timer = null;
        }
    }

    @OnClick({R.id.btnLink01,R.id.btnLink02})
    public void onClick(View v) {
        if (v.getId()==R.id.btnLink01) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.burgerstein-foundation.ch/"));
            startActivity(browserIntent);
        }
        if (v.getId()==R.id.btnLink02) {
            Intent browserIntent01 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xn--mikronhrstoff-wissen-gzb.ch/"));
            startActivity(browserIntent01);
        }
    }

}
