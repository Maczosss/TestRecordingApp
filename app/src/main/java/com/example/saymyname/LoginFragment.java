package com.example.saymyname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private NavController navController;

    private Button logInButton;
    private Button signUpButton;
    private Button goToRecordFragmentButton;
    private AuthUser currentUser;

    public LoginFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = Amplify.Auth.getCurrentUser();
    }

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

        //check if user is logged in
        if(currentUser != null){
            navController.navigate(R.id.action_loginFragment_to_recordFragment);
        }

        logInButton = view.findViewById(R.id.login_button);
        signUpButton = view.findViewById(R.id.sign_up_button);
        goToRecordFragmentButton = view.findViewById(R.id.test_purposes);

        logInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        goToRecordFragmentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.test_purposes:

                navController.navigate(R.id.action_loginFragment_to_recordFragment);
                break;
            case(R.id.login_button):
                logIn();
                break;
            case(R.id.sign_up_button):
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
                break;
        }
    }

    private void logIn() {
        EditText emailField = this.getView().findViewById(R.id.email);
        EditText passwordField = this.getView().findViewById(R.id.password);
        System.out.println(emailField.getText());
        System.out.println(passwordField.getText());

        Amplify.Auth.signIn(
                emailField.getText().toString(),
                passwordField.getText().toString(),
                this::onLoginSuccess,
                this::onLoginFail);
    }

    private void onLoginSuccess(AuthSignInResult authSignInResult) {
        // go to record activity
        navController.navigate(R.id.action_loginFragment_to_recordFragment);
    }

    private void onLoginFail(AuthException e) {
        //report error
        Toast.makeText(this.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
    }
}
