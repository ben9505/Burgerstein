package theagency.vn.burgerstein;

import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.VideoView;

import theagency.vn.listenner.NotificationCenter;
import theagency.vn.listenner.NotificationCenter.NotificationCenterDelegate;
import theagency.vn.support.Helper;

public class MainActivity extends Activity implements OnClickListener, NotificationCenterDelegate {

    Button btnSkipIntro;
    boolean isActive;
    Helper mHelper;
    FrameLayout frameContent;
    VideoView viewVideo;
    //ImageButton btnZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mHelper = Helper.shareIns(getApplicationContext());
        setContentView(R.layout.activity_main);
        referenceView();
        initVideo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.HOME);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.SYMTOM);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.NAHRSTOFF);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.SENDEMAIL);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.CONTACTINFO);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UBER);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.EMGAGEMENT);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.SYMTOM_NARSTOFF);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.LINK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewVideo.start();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.HOME);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.SYMTOM);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.NAHRSTOFF);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.SENDEMAIL);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.CONTACTINFO);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UBER);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.EMGAGEMENT);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.SYMTOM_NARSTOFF);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.LINK);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.HOME)
            initFragment(Fragment_Home.newInstance());
        else if (id == NotificationCenter.SYMTOM)
            initFragment(Fragment_Detail_Symptom.newInstance());
        else if (id == NotificationCenter.NAHRSTOFF)
            initFragment(Fragment_Detail_Nahrstoff.newInstance());
        else if (id == NotificationCenter.SENDEMAIL)
            initFragment(Fragment_SendEmail.newInstance());
        else if (id == NotificationCenter.CONTACTINFO)
            initFragment(Fragment_Contact.newInstance(false));
        else if (id == NotificationCenter.UBER)
            initFragment(Fragment_Uber.newInstance());
        else if (id == NotificationCenter.EMGAGEMENT)
            initFragment(Fragment_Emgagement.newInstance());
        else if (id == NotificationCenter.SYMTOM_NARSTOFF)
            initFragment(Fragment_Prepare.newInstance());
        else if (id == NotificationCenter.LINK)
            initFragment(Fragment_Link.newInstance());
    }

    private void referenceView() {
        frameContent = (FrameLayout) findViewById(R.id.content);
        btnSkipIntro = (Button) findViewById(R.id.btnSkipIntro);
        btnSkipIntro.setOnClickListener(this);
        viewVideo = (VideoView) findViewById(R.id.myvideoview);
    }

    private void initVideo() {
        viewVideo.setMediaController(null);
        viewVideo.setVisibility(View.VISIBLE);

        int density = getResources().getDisplayMetrics().densityDpi;
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.intro_xxhdpi);
        viewVideo.setVideoURI(video);
        viewVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                actionAfterVideoCover();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnSkipIntro) {
            viewVideo.pause();
            viewVideo.stopPlayback();
            actionAfterVideoCover();
        }

    }


//---------------------Method Support------------------------------

    public void actionAfterVideoCover() {
        btnSkipIntro.setVisibility(View.GONE);
        frameContent.setVisibility(View.VISIBLE);
        initFragment(Fragment_Home.newInstance());
    }

    private void initFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = getFragmentManager().findFragmentById(R.id.content);

        if (fragment instanceof Fragment_Home) {
            super.onBackPressed();
        } else {
            initFragment(Fragment_Home.newInstance());
        }


    }


}
