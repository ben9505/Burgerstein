package theagency.vn.burgerstein;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import theagency.vn.listenner.NotificationCenter;
import theagency.vn.support.Helper;
import theagency.vn.support.ItemArrayAdapter;
import theagency.vn.support.Items;
import theagency.vn.support.MyArrayAdapter;
import theagency.vn.support.Products;

/**
 * Created by Administrator PC on 3/17/2015.
 */
public class Fragment_Detail_Nahrstoff extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher, AbsListView.OnScrollListener {

    public final int HANDLER_CLOSEINFORMATION = 234;
    public final int HANDLER_SHOWINFOR_FALSE = 214;
    public final int HANDLER_SHOWINFOR_TRUE = 254;
    public final int HANDLER_SETACTIVE_FLASE = 1000;
    View v;
    TextView textViewTitle, textViewTitleDetail, textViewInfo, titleDetail03, titleDetail02, titleDetail01;
    boolean isShowInfo, isSearchTableShow;
    ImageButton btnArrowShowInfo, btnArrowSearch;
    Helper mHelper;
    Handler handler;
    ListView listView;
    ListView listViewDetail;
    String[] arrSymptom;
    String[] arrSymptomID;
    String[] arrNahrstoffID;
    String[] arrWirkung;
    String[] arrDoseing;
    String[] arrNahrstoff;

    Products mProducts;
    ArrayList<Items> mArrayItems;
    ItemArrayAdapter mAdapterTitle;
    ArrayList<Products> mArray;
    ArrayList<Products> mArrayProducts;
    MyArrayAdapter myAdapter;
    RelativeLayout detail_bottom, detailinformation, bg_active;
    FrameLayout searchBar;
    EditText searchText;
    Button btnInputSymptom, btnInputNahrstoff;
    View arrowCheck;
    int mPositionTitle;
    float animationYShowInfo;
    boolean isActive;
    SQLiteDatabase databaseSQLite;

    //zoom view
    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    private static final String TAG = "ZoomLayout";
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 4.0f;

    private Mode mode = Mode.NONE;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;

    // Where the finger first  touches the screen
    private float startX = 0f;
    private float startY = 0f;

    // How much to translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;
    boolean isZoom;
    GestureDetector gestureDetectorNahrstoff;


    public static Fragment_Detail_Nahrstoff newInstance() {
        Fragment_Detail_Nahrstoff f = new Fragment_Detail_Nahrstoff();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detail_symptom, container, false);
        try {
            animationYShowInfo = getActivity().getResources().getDimension(R.dimen.animationYShowInfo);
            textViewTitle = (TextView) v.findViewById(R.id.textViewSymptom);
            textViewInfo = (TextView) v.findViewById(R.id.textViewInfoSymptom);
            mHelper = Helper.shareIns(getActivity());
            textViewTitle.setTypeface(mHelper.FONT_BOLD);
            textViewInfo.setTypeface(mHelper.FONT_REGULAR);
            textViewTitle.setText(getActivity().getResources().getString(R.string.detail_title_nahrstoff));
            textViewInfo.setText(getActivity().getResources().getString(R.string.detail_nahrstoff));
            bg_active = (RelativeLayout) v.findViewById(R.id.detailinformation);
            bg_active.setBackgroundResource(R.mipmap.nahstoff_active);
            btnArrowShowInfo = (ImageButton) v.findViewById(R.id.btnArrowShowInfo);
            btnArrowShowInfo.setOnClickListener(this);
            btnArrowSearch = (ImageButton) v.findViewById(R.id.btnArrowSearch);
            btnArrowSearch.setOnClickListener(this);
            isShowInfo = false;
            isSearchTableShow = false;
            btnInputSymptom = (Button) v.findViewById(R.id.btnInputSymptom);
            btnInputSymptom.setEnabled(true);
            btnInputNahrstoff = (Button) v.findViewById(R.id.btnInputNahrstoff);
            btnInputNahrstoff.setEnabled(false);
            btnInputSymptom.setOnClickListener(this);
            searchText = (EditText) v.findViewById(R.id.editText);
            searchText.addTextChangedListener(this);
            searchText.setTypeface(mHelper.FONT_REGULAR);
            detailinformation = (RelativeLayout) v.findViewById(R.id.detailinformation);
            String langID = getActivity().getResources().getString(R.string.langID);
            if (langID.equalsIgnoreCase("fr")) {
                detailinformation.setBackgroundResource(R.mipmap.fr_nahstoff_active);
            }
            searchBar = (FrameLayout) v.findViewById(R.id.searchBar);
            detail_bottom = (RelativeLayout) v.findViewById(R.id.detail_bottom);
            titleDetail01 = (TextView) v.findViewById(R.id.titleDetail01);
            titleDetail02 = (TextView) v.findViewById(R.id.titleDetail02);
            titleDetail03 = (TextView) v.findViewById(R.id.titleDetail03);
            textViewTitleDetail = (TextView) v.findViewById(R.id.textViewTitleDetail);
            String textSymptom = getActivity().getResources().getString(R.string.detail_title_symptom);
            String textWurkung = getActivity().getResources().getString(R.string.detail_title_wirkung);
            String textDoseing = getActivity().getResources().getString(R.string.detail_title_doseing);
            titleDetail01.setText(textSymptom.toUpperCase());
            titleDetail02.setText(textWurkung.toUpperCase());
            titleDetail03.setText(textDoseing.toUpperCase());
            listView = (ListView) v.findViewById(R.id.listView);


            arrSymptom = mHelper.getArrSymptom();
            arrNahrstoff = mHelper.getArrNahrstoffItem();
            arrNahrstoffID = mHelper.getArrNahrstoffID();
            arrSymptomID = mHelper.getArrSymptomID();
            arrWirkung = mHelper.getArrWirkung();
            arrDoseing = mHelper.getArrDoseing();


            textViewTitleDetail.setText(arrNahrstoff[0].toUpperCase());
            textViewTitleDetail.setTypeface(mHelper.FONT_BOLD);
            isActive = false;
            mArrayItems = new ArrayList<>();
            mPositionTitle = 0;
            int indexItemID = 0;
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < arrNahrstoff.length; i++) {
                    if (i == 0) {
                        Items items = new Items(true, arrNahrstoff[0], "1");
                        mArrayItems.add(items);
                    } else {
                        indexItemID = i + 1;
                        String text = String.valueOf(arrNahrstoff[i]);
                        text = text.replaceAll("\n", " ");
                        Items items = new Items(false, text, String.valueOf(indexItemID));
                        mArrayItems.add(items);
                    }
                }
            }
            mAdapterTitle = new ItemArrayAdapter(mArrayItems, getActivity());
            listView.setAdapter(mAdapterTitle);
            listView.setOnItemClickListener(this);
            mArray = new ArrayList<>();
            for (int i = 0; i < 121; i++) {
                String txtWirkung = String.valueOf(arrWirkung[i]).replaceAll("\n", " ");
                mProducts = new Products(arrSymptomID[i], arrNahrstoffID[i], txtWirkung, arrDoseing[i]);
                mArray.add(mProducts);
            }
            mArrayProducts = new ArrayList<>();
            for (int i = 0; i < mArray.size(); i++) {
                if (String.valueOf(mArray.get(i).getmNahrstoffID()).equalsIgnoreCase("1")) {
                    mArrayProducts.add(mArray.get(i));
                }
            }
            myAdapter = new MyArrayAdapter(getActivity(), mArrayProducts, arrSymptom);
            listViewDetail = (ListView) v.findViewById(R.id.listViewDetail);
            listViewDetail.setAdapter(myAdapter);
            arrowCheck = v.findViewById(R.id.arrowCheck);
            listViewDetail.setOnScrollListener(this);
            listViewDetail.setOnItemClickListener(null);
            listViewDetail.setClickable(true);
            listViewDetail.setEnabled(true);
            if (mHelper.isFristTimeGoToNahrstoff) {
                mHelper.isFristTimeGoToNahrstoff = false;
                handlerMessenger.sendEmptyMessageDelayed(HANDLER_CLOSEINFORMATION, 500);
            }

            isZoom = false;
            GestureDoubleTapNahrstoff gestureDoubleTap = new GestureDoubleTapNahrstoff();
            gestureDetectorNahrstoff = new GestureDetector(getActivity(), gestureDoubleTap);
            listViewDetail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetectorNahrstoff.onTouchEvent(event);
                }
            });
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isActive) {
                        return false;
                    }
                    if (mHelper.isZoomView) {
                        //----Start-----------//
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_DOWN:

                                if (scale > MIN_ZOOM) {
                                    mode = Mode.DRAG;
                                    startX = event.getX() - prevDx;
                                    startY = event.getY() - prevDy;

                                }
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (mode == Mode.DRAG) {
                                    dx = event.getX() - startX;
                                    dy = event.getY() - startY;
                                }
                                break;
                            case MotionEvent.ACTION_POINTER_DOWN:
                                mode = Mode.ZOOM;


                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                mode = Mode.DRAG;

                                break;
                            case MotionEvent.ACTION_UP:


                                mode = Mode.NONE;
                                prevDx = dx;
                                prevDy = dy;
                                break;
                        }


                        if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            float maxDx = (v.getWidth() - (v.getWidth() / scale)) / 2 * scale;
                            float maxDy = (v.getHeight() - (v.getHeight() / scale)) / 2 * scale;
                            dx = Math.min(Math.max(dx, -maxDx), maxDx);
                            dy = Math.min(Math.max(dy, -maxDy), maxDy);

                            applyScaleAndTranslation();
                        }
                        //----End-----------//
                    }
                    return true;

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return v;
    }

    private void applyScaleAndTranslation() {
        v.setScaleX(scale);
        v.setScaleY(scale);
        v.setTranslationX(dx);
        v.setTranslationY(dy);

    }

    Handler handlerMessenger = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLER_SHOWINFOR_TRUE) {
                isShowInfo = true;
                textViewInfo.setVisibility(View.VISIBLE);
            } else if (msg.what == HANDLER_SHOWINFOR_FALSE) {
                isShowInfo = false;
                btnArrowSearch.setEnabled(true);
                searchText.setEnabled(true);
                detail_bottom.setVisibility(View.VISIBLE);
            } else if (msg.what == HANDLER_CLOSEINFORMATION) {
                showMoreInfomation();
            } else if (msg.what == HANDLER_SETACTIVE_FLASE) {
                isActive = false;
            } else {
                return;
            }
        }
    };

    @Override
    public void onClick(View v) {

        if (this.isActive) {
            return;
        }
        this.isActive = true;
        //--------------start-----------
        if (v == btnArrowShowInfo) {
            if (isShowInfo) {
                closeMoreInformation();
            } else {
                showMoreInfomation();
            }
            handlerMessenger.sendEmptyMessageDelayed(HANDLER_SETACTIVE_FLASE, 1000);
        }
        if (v == btnArrowSearch) {
            if (isSearchTableShow) {
                isSearchTableShow = false;
                listView.setVisibility(View.GONE);
                btnInputNahrstoff.setVisibility(View.VISIBLE);
                btnInputSymptom.setVisibility(View.VISIBLE);
            } else {
                isSearchTableShow = true;
                for (int j = 0; j < mArrayItems.size(); j++) {
                    mArrayItems.get(j).setSelect(false);
                }
                mArrayItems.get(mPositionTitle).setSelect(true);
                mArrayItems.get(mPositionTitle + 43).setSelect(true);
                mAdapterTitle.notifyDataSetChanged();
                listView.requestLayout();
                listView.setVisibility(View.VISIBLE);
                btnInputNahrstoff.setVisibility(View.GONE);
                btnInputSymptom.setVisibility(View.GONE);
            }
            handlerMessenger.sendEmptyMessageDelayed(HANDLER_SETACTIVE_FLASE, 500);

        }
        if (v == btnInputSymptom) {
            mHelper.isZoomView = false;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.SYMTOM, "");
            handlerMessenger.sendEmptyMessageDelayed(HANDLER_SETACTIVE_FLASE, 500);

        }


        //----------------end--------------
    }

    public void showMoreInfomation() {
        btnArrowSearch.setEnabled(false);
        searchText.setEnabled(false);
        animationYShowInfo = textViewInfo.getHeight() - btnArrowShowInfo.getHeight() + 20;
        detail_bottom.setVisibility(View.GONE);
        searchBar.animate().translationY(animationYShowInfo).setDuration(300).setStartDelay(0).start();
        detailinformation.animate().translationY(animationYShowInfo).setDuration(300).setStartDelay(0).start();
        btnArrowShowInfo.animate().translationY(animationYShowInfo).setDuration(300).setStartDelay(0).start();
        btnArrowShowInfo.animate().rotation(180).setDuration(300).setStartDelay(300).start();
        handlerMessenger.sendEmptyMessageDelayed(HANDLER_SHOWINFOR_TRUE, 600);
    }

    public void closeMoreInformation() {
        textViewInfo.setVisibility(View.INVISIBLE);
        handlerMessenger.sendEmptyMessageDelayed(HANDLER_SHOWINFOR_FALSE, 700);
        btnArrowShowInfo.animate().rotation(0).setDuration(300).setStartDelay(0).start();
        searchBar.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).start();
        detailinformation.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).start();
        btnArrowShowInfo.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).start();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            mArrayProducts.clear();
            if (position > 43) {
                position = position - 43;
            }
            String itemID = mArrayItems.get(position).getmID();
            String langID = getActivity().getResources().getString(R.string.langID);
            int myNum = 0;
            try {
                myNum = Integer.parseInt(itemID);
            } catch (NumberFormatException nfe) {
            }
            if (myNum > 20) {
                myNum++;
            }
            if (myNum > 24) {
                myNum = myNum + 3;
            }
            mPositionTitle = position;
            mArrayProducts.removeAll(mArrayProducts);
            for (int i = 0; i < mArray.size(); i++) {
                int myNum01 = 0;
                try {
                    myNum01 = Integer.parseInt(String.valueOf(mArray.get(i).getmNahrstoffID()));
                } catch (NumberFormatException nfe) {
                }

                if (myNum01 == myNum) {
                    if (mArray.get(i).getmSymptomID().toString().equalsIgnoreCase("34")) {
                        if (myNum01 != 4 && myNum01 != 23 && myNum01 != 42) {
                            if (langID.equalsIgnoreCase("fr")) {
                                Products product = new Products("30", mArray.get(i).getmNahrstoffID().toString(),
                                        " Co-facteur n�cessaire � la" +
                                                " formation endog�ne de" +
                                                " l'hormone du sommeil" +
                                                " m�latonine",
                                        "<b>10-25 mg</b> (seulement le matin)");
                                mArrayProducts.add(product);
                            }
                        }

                    }
                    mArrayProducts.add(mArray.get(i));
                }
            }
            if (myNum == 20) {
                mArrayProducts.add(mArray.get(29));
            }
            if (myNum == 10) {
                if (langID.equalsIgnoreCase("fr")) {
                    Products product = new Products("27", "27", "27", "27");
                    mArrayProducts.add(product);
                } else {
                    Products product = new Products("27", "10", "Ben&ouml;tigt f&uuml;r einen normalen " +
                            "Homocystein-Stoffwechsel; " +
                            "erh&ouml;hte Homocysteinspiegel " +
                            "k&ouml;nnen zu einem gr&ouml;sseren " +
                            "Knochenfrakturrisiko f&uuml;hren", "<b>0,4-0,8 mg</b> (aufteteilt morgens/abends)");
                    mArrayProducts.add(product);
                }
            }
            if (myNum == 27) {
                mArrayProducts.add(mArray.get(32));
                mArrayProducts.add(mArray.get(51));
                mArrayProducts.add(mArray.get(77));
                mArrayProducts.add(mArray.get(106));
            }
            isSearchTableShow = false;
            searchText.setHint(arrNahrstoff[position]);
            searchText.setText(null);
            listView.setVisibility(View.GONE);
            btnInputNahrstoff.setVisibility(View.VISIBLE);
            btnInputSymptom.setVisibility(View.VISIBLE);
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            textViewTitleDetail.setText(arrNahrstoff[position]);
            myAdapter.notifyDataSetChanged();
            listView.requestLayout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        try {
            isSearchTableShow = false;
            for (int j = 0; j < mArrayItems.size(); j++) {
                mArrayItems.get(j).setSelect(false);
            }
            mAdapterTitle.notifyDataSetChanged();
            listView.requestLayout();
            listView.setVisibility(View.VISIBLE);
            searchText.setAlpha(1f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            if (count != 0 && count < 4) {
                for (int i = 0; i < arrNahrstoff.length; i++) {
                    String text = arrNahrstoff[i];
                    String FirstChar = text.substring(0, count);
                    if (FirstChar.equalsIgnoreCase(s.toString())) {
                        listView.setSelection(i);
                        i = arrNahrstoff.length;
                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void afterTextChanged(Editable s) {


    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isShowInfo) {
            closeMoreInformation();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getLastVisiblePosition() == view.getAdapter().getCount() - 1 &&
                view.getChildAt(view.getChildCount() - 1).getBottom() <= view.getHeight()) {
            arrowCheck.setVisibility(View.GONE);
        } else {
            arrowCheck.setVisibility(View.VISIBLE);
        }
    }


    public class GestureDoubleTapNahrstoff extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isActive) {
                return false;
            }
            isActive = true;
            scale = 2;
            Log.w("ZoomView", "true");
            Toast.makeText(getActivity(), "Click \"Back\" button to return to normal mode.", Toast.LENGTH_LONG).show();
            applyScaleAndTranslation();

            mHelper.isZoomView = true;

            listViewDetail.setEnabled(false);
            listViewDetail.setClickable(false);
            isActive = false;
            return true;
        }
    }


}

