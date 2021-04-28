package com.example.saymyname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private NavController navController;

    private Button logInButton;
    private Button signUpButton;
    private Button goToRecordFragmentButton;

    public LoginFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        logInButton = view.findViewById(R.id.login_button);
        signUpButton = view.findViewById(R.id.sign_up_button);
        goToRecordFragmentButton = view.findViewById(R.id.test_purposes);

        goToRecordFragmentButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.test_purposes:
                navController.navigate(R.id.action_loginFragment_to_recordFragment);
                break;
            case(R.id.login_button):
                System.out.println("login");
                break;
            case(R.id.sign_up_button):
                System.out.println("signup");
                break;
        }
    }
}
