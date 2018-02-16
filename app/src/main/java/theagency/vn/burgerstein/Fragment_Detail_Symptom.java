package theagency.vn.burgerstein;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import theagency.vn.support.Products;
import theagency.vn.support.SymptomAdapter;

/**
 * Created by Administrator PC on 3/16/2015.
 */
public class Fragment_Detail_Symptom extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher, AbsListView.OnScrollListener {

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
    String[] arrayInfoOnlyRow;
    Products mProducts;
    ArrayList<Products> mArray;
    ArrayList<Products> mArrayProducts;
    ArrayList<Items> mArrayItems;
    ItemArrayAdapter mAdapterTitle;
    SymptomAdapter myAdapter;
    RelativeLayout detail_bottom, detailinformation;
    FrameLayout searchBar;
    EditText searchText;
    Button btnInputSymptom, btnInputNahrstoff;
    float animationYShowInfo;
    View arrowCheck;
    int mPositionTitle;
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
    GestureDetector gestureDetector;

    public static Fragment_Detail_Symptom newInstance() {
        Fragment_Detail_Symptom f = new Fragment_Detail_Symptom();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detail_symptom, container, false);
        try {
            animationYShowInfo = getActivity().getResources().getDimension(R.dimen.animationYShowInfo);
            this.isActive = false;
            textViewTitle = (TextView) v.findViewById(R.id.textViewSymptom);
            textViewInfo = (TextView) v.findViewById(R.id.textViewInfoSymptom);
            mHelper = Helper.shareIns(getActivity());
            textViewTitle.setTypeface(mHelper.FONT_BOLD);
            textViewInfo.setTypeface(mHelper.FONT_REGULAR);
            btnArrowShowInfo = (ImageButton) v.findViewById(R.id.btnArrowShowInfo);
            btnArrowShowInfo.setOnClickListener(this);
            btnArrowSearch = (ImageButton) v.findViewById(R.id.btnArrowSearch);
            btnArrowSearch.setOnClickListener(this);
            arrowCheck = v.findViewById(R.id.arrowCheck);
            isShowInfo = false;
            isSearchTableShow = false;
            btnInputSymptom = (Button) v.findViewById(R.id.btnInputSymptom);
            btnInputSymptom.setEnabled(false);
            btnInputNahrstoff = (Button) v.findViewById(R.id.btnInputNahrstoff);
            btnInputNahrstoff.setEnabled(true);
            btnInputNahrstoff.setOnClickListener(this);
            searchText = (EditText) v.findViewById(R.id.editText);
            searchText.addTextChangedListener(this);
            searchText.setTypeface(mHelper.FONT_REGULAR);
            detailinformation = (RelativeLayout) v.findViewById(R.id.detailinformation);
            String langID = getActivity().getResources().getString(R.string.langID);
            if (langID.equalsIgnoreCase("fr")) {
                detailinformation.setBackgroundResource(R.mipmap.fr_symptom_active);
            }


            searchBar = (FrameLayout) v.findViewById(R.id.searchBar);
            detail_bottom = (RelativeLayout) v.findViewById(R.id.detail_bottom);
            titleDetail01 = (TextView) v.findViewById(R.id.titleDetail01);
            titleDetail02 = (TextView) v.findViewById(R.id.titleDetail02);
            titleDetail03 = (TextView) v.findViewById(R.id.titleDetail03);
            textViewTitleDetail = (TextView) v.findViewById(R.id.textViewTitleDetail);
            String textNahrstoff = getActivity().getResources().getString(R.string.detail_title_nahrstoff);
            String textWurkung = getActivity().getResources().getString(R.string.detail_title_wirkung);
            String textDoseing = getActivity().getResources().getString(R.string.detail_title_doseing);
            titleDetail01.setText(textNahrstoff.toUpperCase());
            titleDetail02.setText(textWurkung.toUpperCase());
            titleDetail03.setText(textDoseing.toUpperCase());
            listView = (ListView) v.findViewById(R.id.listView);
            databaseSQLite = null;


            arrSymptom = mHelper.getArrSymptom();
            arrNahrstoff = mHelper.getArrNahrstoff();
            arrNahrstoffID = mHelper.getArrNahrstoffID();
            arrSymptomID = mHelper.getArrSymptomID();
            arrWirkung = mHelper.getArrWirkung();
            arrDoseing = mHelper.getArrDoseing();

            arrayInfoOnlyRow = getActivity().getResources().getStringArray(R.array.in_for_only_row);


            textViewTitleDetail.setText(arrSymptom[0].toUpperCase());
            textViewTitleDetail.setTypeface(mHelper.FONT_BOLD);
            mArrayItems = new ArrayList<>();
            mPositionTitle = 0;
            int indexItemID = 0;
            for (int i = 0; i < arrSymptom.length; i++) {
                if (i == 0) {
                    Items items = new Items(true, arrSymptom[mPositionTitle], "1");
                    mArrayItems.add(items);
                } else {
                    indexItemID = i + 1;
                    Items items = new Items(false, arrSymptom[i], String.valueOf(indexItemID));
                    mArrayItems.add(items);
                }
            }
            for (int i = 0; i < arrSymptom.length; i++) {
                if (i == 0) {
                    Items items = new Items(true, arrSymptom[mPositionTitle], "1");
                    mArrayItems.add(items);
                } else {
                    indexItemID = i + 1;
                    Items items = new Items(false, arrSymptom[i], String.valueOf(indexItemID));
                    mArrayItems.add(items);
                }
            }

            mAdapterTitle = new ItemArrayAdapter(mArrayItems, getActivity());
            listView.setAdapter(mAdapterTitle);

            listView.setOnItemClickListener(this);
            mArray = new ArrayList<>();
            for (int i = 0; i < 123; i++) {
                String txtWirkung = String.valueOf(arrWirkung[i]).replaceAll("\n", " ");
                mProducts = new Products(arrSymptomID[i], arrNahrstoffID[i], txtWirkung, arrDoseing[i]);
                mArray.add(mProducts);
            }
            mArrayProducts = new ArrayList<>();
            for (int i = 0; i < mArray.size(); i++) {
                String symptomName = String.valueOf(mArray.get(i).getmSymptomID());
                if (symptomName.equalsIgnoreCase("1")) {
                    mArrayProducts.add(mArray.get(i));
                }
            }

            myAdapter = new SymptomAdapter(getActivity(), mArrayProducts, arrNahrstoff, arrayInfoOnlyRow);
            listViewDetail = (ListView) v.findViewById(R.id.listViewDetail);
            listViewDetail.setAdapter(myAdapter);
            listViewDetail.setOnScrollListener(this);
            listViewDetail.setOnItemClickListener(null);

            if (mHelper.isFristTimeGoToSymptom) {
                mHelper.isFristTimeGoToSymptom = false;
                handlerMessenger.sendEmptyMessageDelayed(HANDLER_CLOSEINFORMATION, 500);
            }


            GestureDoubleTap gestureDoubleTap = new GestureDoubleTap();
            gestureDetector = new GestureDetector(getActivity(), gestureDoubleTap);
            listViewDetail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isActive) {
                        return false;
                    }
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
                detail_bottom.setVisibility(View.VISIBLE);
            } else if (msg.what == HANDLER_CLOSEINFORMATION) {
                showMoreInfomation();
            } else if (msg.what == HANDLER_SETACTIVE_FLASE) {
                isActive = false;
            } else if (msg.what == 1) {
                detail_bottom.setVisibility(View.VISIBLE);
            } else {
                return;
            }
        }
    };


    @Override
    public void onClick(View v) {
        if (isActive) {
            return;
        }
        isActive = true;


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
                listView.setVisibility(View.INVISIBLE);
                listView.setEnabled(true);
                listView.setClickable(true);
                btnInputNahrstoff.setVisibility(View.VISIBLE);
                btnInputSymptom.setVisibility(View.VISIBLE);
            } else {
                isSearchTableShow = true;
                listView.setEnabled(true);
                listView.setClickable(true);
                for (int j = 0; j < mArrayItems.size(); j++) {
                    mArrayItems.get(j).setSelect(false);
                }
                mArrayItems.get(mPositionTitle).setSelect(true);
                mArrayItems.get(mPositionTitle + 36).setSelect(true);
                mAdapterTitle.notifyDataSetChanged();
                listView.requestLayout();
                listView.setVisibility(View.VISIBLE);
                btnInputNahrstoff.setVisibility(View.GONE);
                btnInputSymptom.setVisibility(View.GONE);
            }
            handlerMessenger.sendEmptyMessageDelayed(HANDLER_SETACTIVE_FLASE, 500);

        }
        if (v == btnInputNahrstoff) {
            mHelper.isZoomView = false;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.NAHRSTOFF, "");
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
        btnArrowShowInfo.animate().rotation(180).setDuration(300).setStartDelay(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowInfo = true;
                textViewInfo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    public void closeMoreInformation() {

        textViewInfo.setVisibility(View.INVISIBLE);
        btnArrowShowInfo.animate().rotation(0).setDuration(300).setStartDelay(0).start();
        searchBar.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).start();
        detailinformation.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).start();
        btnArrowShowInfo.animate().translationY(mHelper.DpToPixel(0)).setDuration(300).setStartDelay(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowInfo = false;
                btnArrowSearch.setEnabled(true);
                searchText.setEnabled(true);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
        handlerMessenger.sendEmptyMessageDelayed(1, 700);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            int index = 0;
            if (position > 36) {
                position = position - 36;
            }
            mPositionTitle = position;
            mArrayProducts.removeAll(mArrayProducts);
            String langID = getActivity().getResources().getString(R.string.langID);
            for (int i = 0; i < mArray.size(); i++) {
                if (String.valueOf(mArray.get(i).getmSymptomID()).equalsIgnoreCase(String.valueOf(position + 1))) {
                    index = Integer.parseInt(String.valueOf(mArray.get(i).getmSymptomID()));
                    mArrayProducts.add(mArray.get(i));

                }
            }
            if (position == 29) {
                if (langID.equalsIgnoreCase("fr")) {
                    mArrayProducts.removeAll(mArrayProducts);
                    Products product = new Products("30", "41", "Co-facteur nécessaire à la" +
                            " formation endogène de" +
                            " l'hormone du sommeil" +
                            " mélatonine", "<b>10-25 mg</b> (seulement le matin)");
                    mArrayProducts.add(product);
                    Products product1 = new Products("30", "24", "Aide à s’endormir et peut améliorer la qualité du sommeil, tensiolytique",
                            "<b>800-1’600 mg</b> (correspond à<br />50-100 mg de magnésium élémentaire), le soir<br/>1 h avant le coucher");
                    mArrayProducts.add(product1);
                }

            }
            if (index == 2) {
                Products product = new Products("2", "2", "1", "1");
                mArrayProducts.add(product);
            }
            if (index == 18) {
                Products product = new Products("2", "2", "2", "2");
                mArrayProducts.add(product);
            }
            if (index == 19) {
                Products product = new Products("2", "2", "3", "3");
                mArrayProducts.add(product);
            }
            if (index == 27) {
                if (langID.equalsIgnoreCase("fr")) {
                    Products product = new Products("27", "27", "27", "27");
                    mArrayProducts.add(product);
                }
                Products product = new Products("2", "2", "4", "4");
                mArrayProducts.add(product);
            }
            if (index == 32) {
                Products product = new Products("2", "2", "5", "5");
                mArrayProducts.add(product);
            }
            if (index == 36) {
                Products product = new Products("2", "2", "6", "6");
                mArrayProducts.add(product);
            }
//        if (position==29){
//            mArrayProducts.add(mArray.get(5));
//        }
            isSearchTableShow = false;
            searchText.setHint(arrSymptom[position]);
            searchText.setText(null);
            listView.setVisibility(View.GONE);
            btnInputNahrstoff.setVisibility(View.VISIBLE);
            btnInputSymptom.setVisibility(View.VISIBLE);
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            textViewTitleDetail.setText(arrSymptom[position].toUpperCase());


            arrowCheck.setVisibility(View.VISIBLE);
            myAdapter.notifyDataSetChanged();
            listView.requestLayout();
            listView.setEnabled(true);
            listView.setClickable(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setScale(float scale) {
        this.scale = scale;
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
            if (count != 0 && count < 5) {
                for (int i = 0; i < arrSymptom.length; i++) {
                    String text = arrSymptom[i];
                    String FirstChar = text.substring(0, count);

                    if (FirstChar.equalsIgnoreCase(s.toString())) {
                        listView.setSelection(i);
                        i = arrSymptom.length;
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

    public class GestureDoubleTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isActive) {
                return true;
            }
            isActive = true;
            scale = 2;
            Toast.makeText(getActivity(), "Click \"Back\" button to return to normal mode.", Toast.LENGTH_LONG).show();
            applyScaleAndTranslation();
            listViewDetail.setEnabled(false);
            listViewDetail.setClickable(false);
            mHelper.isZoomView = true;
            mode = Mode.ZOOM;

            isActive = false;
            return true;
        }


    }


}
