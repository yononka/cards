package com.lakshmi.cards;

//import android.app.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyContactsFragment() {
        // Required empty public constructor
    }


    public static MyContactsFragment newInstance(String param1, String param2) {
        MyContactsFragment fragment = new MyContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View mycontactsInflatedView = inflater.inflate(R.layout.fragment_my_contacts, container,false);

        TextView my_tel = (TextView) (TextView) mycontactsInflatedView.findViewById(R.id.my_tel);
        my_tel.setTypeface(null, Typeface.BOLD);
        TextView my_email = (TextView) (TextView) mycontactsInflatedView.findViewById(R.id.my_email);
        my_email.setTypeface(null, Typeface.BOLD);
        TextView my_skype = (TextView) (TextView) mycontactsInflatedView.findViewById(R.id.my_skype);
        my_skype.setTypeface(null, Typeface.BOLD);
        TextView my_name = (TextView) (TextView) mycontactsInflatedView.findViewById(R.id.my_name);
        my_name.setTypeface(null, Typeface.BOLD);

        return mycontactsInflatedView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            Toast.makeText(context, "MyContactsFragment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
