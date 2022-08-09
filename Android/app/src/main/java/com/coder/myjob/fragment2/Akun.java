package com.coder.myjob.fragment2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coder.myjob.Login;
import com.coder.myjob.R;
import com.coder.myjob.utils.SessionHandle;

public class Akun extends Fragment {
    private Button mLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview =  inflater.inflate(R.layout.fragment_akun, container, false);
        dataInit(mview);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHandle.logout(requireContext());
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if(getActivity()!= null){
                    getActivity().finish();
                }
            }
        });
        return mview;
    }

    private void dataInit(View mview){
        mLogout = mview.findViewById(R.id.btn_logout);
    }
}