package com.lakshmi.cards;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class CardsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CardsFragment() {
        // Required empty public constructor
    }

    public static CardsFragment newInstance(String param1, String param2) {
        CardsFragment fragment = new CardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public String final_post_num;
    TextView post_result;
    EditText post_num;
    Button post_btn;

    TextView comment_result;
    Button comment_btn;
    public String final_comment_num;
    EditText comment_num;

    public ImageView photo_result;
    Button photo_btn;
    public String final_photo_num;
    EditText photo_num;

    public String final_user_num;
    TextView user_result;
    EditText user_num;
    Button user_btn;
    public boolean user_first_load = true;

    TextView todo_result;
    Button todo_btn;
    public String final_todo_num;
    EditText todo_num;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View cardsInflatedView = inflater.inflate(R.layout.fragment_cards, container,false);

        post_result = (TextView) cardsInflatedView.findViewById(R.id.post_content_view);
        post_btn = (Button) cardsInflatedView.findViewById(R.id.post_btn_view);
        post_num = (EditText) cardsInflatedView.findViewById(R.id.post_num_view);

        comment_result = (TextView) cardsInflatedView.findViewById(R.id.comment_content_view);
        comment_btn = (Button) cardsInflatedView.findViewById(R.id.comment_btn_view);
        comment_num = (EditText) cardsInflatedView.findViewById(R.id.comment_num_view);


        photo_result = (ImageView) cardsInflatedView.findViewById(R.id.photo_content_view);
        photo_btn = (Button) cardsInflatedView.findViewById(R.id.photo_btn_view);
        photo_num = (EditText) cardsInflatedView.findViewById(R.id.photo_num_view);

        user_result = (TextView) cardsInflatedView.findViewById(R.id.user_content_view);
        user_btn = (Button) cardsInflatedView.findViewById(R.id.user_btn_view);
        user_num = (EditText) cardsInflatedView.findViewById(R.id.user_num_view);

        todo_result = (TextView) cardsInflatedView.findViewById(R.id.todo_content_view);
        todo_btn = (Button) cardsInflatedView.findViewById(R.id.todo_btn_view);
        todo_num = (EditText) cardsInflatedView.findViewById(R.id.todo_num_view);

        photo_num.setText("3");
        new GetPhotoItem().execute();

        new GetUserItem().execute();

        Random r = new Random();
        int random = r.nextInt(201 - 1) + 1;
        String random_todo = String.valueOf(random);
        todo_num.setText(random_todo);
        new GetTodoItem().execute();


        comment_num.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(comment_num.getText().toString().trim().isEmpty()){
                    comment_btn.setEnabled(false);
                }else{
                    comment_btn.setEnabled(true);
                }

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

        });

        user_num.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(user_num.getText().toString().trim().isEmpty()){
                    user_btn.setEnabled(false);
                }else{
                    user_btn.setEnabled(true);
                }

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

        });

        photo_num.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(photo_num.getText().toString().trim().isEmpty()){
                    photo_btn.setEnabled(false);
                }else{
                    photo_btn.setEnabled(true);
                }

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

        });

        todo_num.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(todo_num.getText().toString().trim().isEmpty()){
                    todo_btn.setEnabled(false);
                }else{
                    todo_btn.setEnabled(true);
                }

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {}

        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetPostItem().execute();
            }
        });

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetCommentItem().execute();
            }
        });

        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetPhotoItem().execute();

            }
        });


       user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_first_load = false;
                new GetUserItem().execute();

            }
        });

        todo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetTodoItem().execute();

            }
        });


        return cardsInflatedView;

    }

            /*---------- Handle show User ----------*/

    private class GetUserItem extends AsyncTask<Void, Void, ArrayList<String>> {
        HttpURLConnection urlConnection;

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList<String> users_result= new ArrayList<String>();

            if (user_first_load == true) {

               for (int i = 1; i < 6; i++) {
                      final_user_num = Integer.toString(i);
                      StringBuilder result = new StringBuilder();

                    try {

                        URL url = new URL("https://jsonplaceholder.typicode.com/users/" + final_user_num);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        users_result.add(result.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                }

            } else {
                StringBuilder result = new StringBuilder();
                final_user_num = user_num.getText().toString().trim();
                try {

                    URL url = new URL("https://jsonplaceholder.typicode.com/users/" + final_user_num);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    users_result.add(result.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            }

            return users_result;
        }


        @Override
        protected void onPostExecute(ArrayList<String> users_result) {
            StringBuilder multiusers = new StringBuilder();
            for ( int j=0; j<users_result.size(); j++ ) {
                JsonObject jobj = new Gson().fromJson(users_result.get(j), JsonObject.class);
                String user_name = jobj.get("name").toString().replaceAll("^\"|\"$", "");
                String user_phone = jobj.get("phone").toString().replaceAll("^\"|\"$", "");

                String user_entry = j+1 + ". " + user_name + ", tel: " + user_phone +"\n";

                multiusers.append(user_entry);

            }
            user_result.setText(multiusers);
        }

    }

        /*---------- Handle show Photo ----------*/

    private class GetPhotoItem extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();

            final_photo_num = photo_num.getText().toString().trim();
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/photos/" + final_photo_num);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                return result.toString();

           }

        @Override
        protected void onPostExecute(String result) {

           JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
           String photo_url = jobj.get("url").toString();
           Context c = getActivity().getApplicationContext();
           String photo_url_clean = photo_url.replaceAll("^\"|\"$", "");
           Picasso.with(c).load(photo_url_clean).into(photo_result);

        }
    }


        /*---------- Handle show Post ----------*/

    private class GetPostItem extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();
            final_post_num = post_num.getText().toString();

            if(Integer.parseInt(final_post_num) <= 100) {

                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/posts/" + final_post_num);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                return result.toString();

            }else {

                return null;

            }

        }

        @Override
        protected void onPostExecute(String result) {
            post_result.setText(result);
        }
   }



           /*---------- Handle show Comment ----------*/

    private class GetCommentItem extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();
            final_comment_num = comment_num.getText().toString();

            if(Integer.parseInt(final_comment_num) <= 500 && Integer.parseInt(final_comment_num) > 0 ) {

                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/comments/" + final_comment_num);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                return result.toString();

            }else {

                return null;

            }

        }

        @Override

        protected void onPostExecute(String result) {

            if (result != null) {
                JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
                String comment_id = jobj.get("id").toString().replaceAll("^\"|\"$", "");
                String comment_body = jobj.get("body").toString().replaceAll("^\"|\"$", "");

                String comment_entry = "id: " + comment_id + "\ncomment: " + comment_body;

                comment_result.setText(comment_entry);
            }
        }
    }

       /*---------- Handle show ToDos ----------*/

    private class GetTodoItem extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();

            final_todo_num = todo_num.getText().toString().trim();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/todos/" + final_todo_num);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return result.toString();

        }

        @Override
        protected void onPostExecute(String result) {

            JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
            String todo_id = jobj.get("id").toString().replaceAll("^\"|\"$", "");
            String todo_title = jobj.get("title").toString().replaceAll("^\"|\"$", "");
            String todo_entry = "id: " + todo_id + "\ntitle: " + todo_title;
            todo_result.setText(todo_entry);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
//        } else {
 //           Toast.makeText(context, "CardsFragment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
