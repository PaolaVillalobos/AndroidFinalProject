package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.exercise.androidfinalproject.LoginActivity.isEmailValid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.exercise.androidfinalproject.databinding.FragmentLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileFragment extends Fragment {

    private FragmentLayoutBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_layout, container, false);

        TextView lName = view.findViewById(R.id.etName);
        Button lLogOut = view.findViewById(R.id.btLogOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            lName.setText(name);

        }


        lLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", "");
                editor.putString("password", "");
                editor.apply();
                auth.getInstance().signOut();
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
            }
        });


        return view;
    }
}
