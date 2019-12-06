package com.tonyz3t.android.dimensionaldash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private static final String TAG = "Login";

    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Button mSignOutButton;
    private FirebaseAuth mAuth;
    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INIT our intent here on creation
        mAuth = FirebaseAuth.getInstance();
        intent = new Intent(getActivity(), MainMenuActivity.class);
        if(mAuth.getCurrentUser() != null) startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.login_fragment, container, false);

        //INIT Firebase login
        //mAuth = FirebaseAuth.getInstance();

        //INIT our intent
        //intent = new Intent(getActivity(), MainMenuActivity.class);

        // INIT LoginField
        mUsernameField = (EditText) v.findViewById(R.id.username_field);

        // INIT PasswordField
        mPasswordField = (EditText) v.findViewById(R.id.password_field);
        mPasswordField.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign in and send to main menu
                signIn(mUsernameField.getText().toString(), mPasswordField.getText().toString());

            }
        });

        // INIT register button
        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mUsernameField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        // REMOVE THIS ONCE SIGNOUT BUTTON INSIDE APP WORKS
        mSignOutButton = (Button) v.findViewById(R.id.signout_button);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Log.d(TAG, "sign out successful");
                Toast.makeText(getActivity(), "Sign out successful", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    // Helper method to prevent user from going back to login page while still signed in
    @Override
    public void onResume() {
        super.onResume();
        if(mAuth.getCurrentUser() != null) startActivity(intent);
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    // Helper method to create an account for the user if once does not exist
    // User is logged in upon registration
    private void createAccount(String username, String password) {
        Log.d(TAG, "createAccount()");
        if(!validateForm()) return;

        // START create user with email
        Log.d(TAG, "Starting create user with email");
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Inside addOnCompleteListener");
                        if(task.isSuccessful()) {
                            //SIGN UP SUCCESS
                            Log.d(TAG, "Sign up success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "sign up success", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                            if(intent != null) startActivity(intent);
                        } else {
                            // SIGN UP FAILED
                            Log.w(TAG, "sign up failed", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }

    // Helper method to see if username and/or password field is empty
    private boolean validateForm() {
        Log.d(TAG, "validateForm()");
        boolean valid = true;

        // email from our username field
        String email = mUsernameField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUsernameField.setError("Required");
            valid = false;
        } else {
            mUsernameField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)){
            mPasswordField.setError("Required");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    // Helper method to sign the user in
    private void signIn(String username, String password) {
        Log.d(TAG, "signIn()");

        if(!validateForm()) return;

        // START sign in with email and password
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //sign in success
                            Log.d(TAG, "Sign in successfull");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "sign in success", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                            if(intent != null) startActivity(intent);
                        } else {
                            //Sign in fails, display message
                            Log.w(TAG, "sign in failed", task.getException());
                            Toast.makeText(getActivity(), "sign in failed", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        //check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //TODO
        mUsernameField.setText("");
        mPasswordField.setText("");
    }

    //  Helper method to convert password field characters into asterisks
    private class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; //store char sequence
            }

            @Override
            public int length() {
                return mSource.length(); //return default
            }

            @Override
            public char charAt(int i) {
                return '*'; //replaces character with asterisk
            }

            @Override
            public CharSequence subSequence(int i, int i1) {
                return mSource.subSequence(i, i1); //return default
            }
        }
    }
}

