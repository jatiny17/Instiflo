package com.rohg007.android.instiflo.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rohg007.android.instiflo.MainActivity;
import com.rohg007.android.instiflo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private String email;
    private String password;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        MaterialButton toSignUpButton= v.findViewById(R.id.to_sign_up_button);
        final TextInputLayout usernameInputLayout = v.findViewById(R.id.username_text_layout);
        final TextInputEditText usernameEditText = v.findViewById(R.id.username_edit_text);
        final TextInputLayout passwordInputLayout = v.findViewById(R.id.password_text_layout);
        final TextInputEditText passwordEditText = v.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = v.findViewById(R.id.next_button);
        MaterialButton cancelButton = v.findViewById(R.id.login_cancel_button);


        toSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.login_container,signUpFragment).addToBackStack(null).commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields(usernameEditText,passwordEditText);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();

                if(email.isEmpty())
                    usernameInputLayout.setError("Username can't be empty");

                if(password.isEmpty())
                    passwordInputLayout.setError("Password can't be empty");

                if(password.length()<8)
                    passwordInputLayout.setError("Enter atleast 8 characters");

                if(!password.isEmpty()&&!email.isEmpty()&&password.length()>=8) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Signed In", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        return v;
    }

    private void clearFields(TextInputEditText usernameEditText, TextInputEditText passwordEditText){
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

}
