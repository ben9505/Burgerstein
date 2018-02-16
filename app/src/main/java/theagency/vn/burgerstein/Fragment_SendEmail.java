package theagency.vn.burgerstein;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import theagency.vn.listenner.NotificationCenter;
import theagency.vn.support.Helper;

/**
 * Created by Administrator PC on 3/13/2015.
 */
public class Fragment_SendEmail extends Fragment implements View.OnClickListener {

    View view;
    TextView textViewInfoBack;
    ImageButton btnBackPrePare;
    Helper mHelper;
    Handler handler;
    TextView mError;
    EditText edVorname,edCompany,edEmail,edContent,edPhone;
    ImageButton btnSend,btnClear;
    public static Fragment_SendEmail newInstance() {
        Fragment_SendEmail f = new Fragment_SendEmail();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sendemail,container,false);

        btnBackPrePare = (ImageButton) view.findViewById(R.id.btnBack);
        textViewInfoBack = (TextView) view.findViewById(R.id.textViewInfoBack);
        btnBackPrePare.setOnClickListener(this);
        mHelper = Helper.shareIns(getActivity());
        edVorname = (EditText) view.findViewById(R.id.editTextName);
        edCompany = (EditText) view.findViewById(R.id.editTextCompany);
        edPhone = (EditText) view.findViewById(R.id.editTextPhone);
        edEmail = (EditText) view.findViewById(R.id.editTextEmail);
        edContent = (EditText) view.findViewById(R.id.editTextContent);
        mError = (TextView) view.findViewById(R.id.error);

        edVorname.setTypeface(mHelper.FONT_REGULAR);
        edCompany.setTypeface(mHelper.FONT_REGULAR);
        edPhone.setTypeface(mHelper.FONT_REGULAR);
        edEmail.setTypeface(mHelper.FONT_REGULAR);
        edContent.setTypeface(mHelper.FONT_REGULAR);
        mError.setTypeface(mHelper.FONT_IT);


        textViewInfoBack.setTypeface(mHelper.FONT_BOLD);
        btnSend = (ImageButton) view.findViewById(R.id.btnSend);
        btnClear = (ImageButton) view.findViewById(R.id.btnClear);
        String langID = getActivity().getResources().getString(R.string.langID);
        if (langID.equalsIgnoreCase("fr")) {
            btnSend.setBackgroundResource(R.mipmap.fr_btn_send);
            btnClear.setBackgroundResource(R.mipmap.fr_btn_clear);
        }
        btnSend.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        mError.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btnBackPrePare){
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.CONTACTINFO,"");
            return;
        }
        if (v == btnSend){
            ConnectivityManager cn=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nf=cn.getActiveNetworkInfo();
            if(nf != null && nf.isConnected()==true ) {

                if (checkValidate()){
                    sendEmail();
                }
            }else{
                Toast.makeText(getActivity(),"Not Connection To Send Email",Toast.LENGTH_LONG).show();
            }

            return;
        }
        if (v == btnClear){
            edVorname.setText(null);
            edCompany.setText(null);
            edPhone.setText(null);
            edEmail.setText(null);
            edContent.setText(null);
            mError.setText(null);
            return;
        }
    }

    public boolean checkValidate(){
        if (edVorname.getText().toString().isEmpty()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Vorname is'nt null!");
            return false;
        }else if (edCompany.getText().toString().isEmpty()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Firma is'nt null!");
            return false;
        }else if (edPhone.getText().toString().isEmpty()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Phone is'nt null!");
            return false;
        }else if (edEmail.getText().toString().isEmpty()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Content is'nt null!");
            return false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edEmail.getText()).matches()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Email is'nt math!");
            return false;
        }else if (edPhone.getText().toString().isEmpty()){
            mError.setVisibility(View.VISIBLE);
            mError.setText("Phone is'nt null!");
            return false;
        }else if (edContent.getText().toString().isEmpty()) {
            mError.setVisibility(View.VISIBLE);
            mError.setText("Email is'nt null!");
            return false;
        }else{
            mError.setVisibility(View.GONE);
            mError.setText(null);
            return true;
        }
    }

    protected void sendEmail() {

        String[] TO = {"burgersteincontact@gmail.com"};
        String[] CC = {"burgersteincontact@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email From Burgerstein Application");
        String textEmail = "Vorname: " + edVorname.getText() + "\nFirma: " + edCompany.getText() + "\nPhone: " + edPhone.getText()
                + "\nEmail: " + edEmail.getText() + "\nContent: " + edContent.getText();
        emailIntent.putExtra(Intent.EXTRA_TEXT, textEmail);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Toast.makeText(getActivity(),"Send Email Successfull",Toast.LENGTH_LONG).show();
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
