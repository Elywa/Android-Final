package com.example.androidproject.authentication.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidproject.R;
import com.example.androidproject.authentication.signup.presenter.SignUpPresenter;
import com.example.androidproject.databinding.FragmentSignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {


    private FragmentSignUpBinding binding;
    private NavController controller ;
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;

    private SignUpPresenter presenter;

    private final ActivityResultLauncher<Intent> signUpLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    googleSignUpSuccess(account.getIdToken());

                } catch (ApiException e) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    enableInteraction();
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (savedInstanceState == null)
        {

            presenter = new SignUpPresenter();
        }else{
           presenter = savedInstanceState.getParcelable(getString(R.string.presenter));
           binding.emailInputLayout.getEditText().setText(presenter.getEmail());
           binding.usernameInputLayout.getEditText().setText(presenter.getName());
           binding.passwordInputLayout.getEditText().setText(presenter.getPassword());
        }

        binding.alreadyHaveAnAccount.setOnClickListener(view1 -> controller.navigate(R.id.action_signUpFragment_to_loginFragment));
        binding.backImageView.setOnClickListener(view1 ->     controller.popBackStack());
        //binding.googleButton.setOnClickListener(view1 -> signUpWithGoogle());

        binding.signUpButton.setOnClickListener(view1 -> signUp());
        emailTextWatcher();
        nameTextWatcher();
        passwordTextWatcher();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.presenter),presenter);
    }

    private void emailTextWatcher()
    {
        binding.emailInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence email, int i, int i1, int i2) {
                   presenter.setEmail(email.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void nameTextWatcher()
    {
        binding.usernameInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence name, int i, int i1, int i2) {
                presenter.setName(name.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    private void passwordTextWatcher()
    {
        binding.passwordInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence password, int i, int i1, int i2) {
                presenter.setPassword(password.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signUp()
    {

        disableInteraction();
        String email = binding.emailInputLayout.getEditText().getText().toString();
        String username = binding.usernameInputLayout.getEditText().getText().toString();
        String password = binding.passwordInputLayout.getEditText().getText().toString();

        if (!presenter.isValidEmail(email))
        {
            enableInteraction();
            Toast.makeText(requireContext(), getString(R.string.please_insert_valid_email), Toast.LENGTH_SHORT).show();
            return;
        }


        if (!presenter.isValidPassword(password))
        {
            enableInteraction();
            Toast.makeText(requireContext(), getString(R.string.your_password_should_have_number_capital_letter_small_letter_and_special_character), Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();
                            user.updateProfile(profileUpdates);
                            createNewCollectionForUser(user);
                        }

                    } else {
                        Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    enableInteraction();
                });
    }


    private void createNewCollectionForGoogleUser(FirebaseUser user) {
        Map<String, Object> data = new HashMap<>();
        data.put(getString(R.string.breakfast), new ArrayList<>());
        data.put(getString(R.string.launch), new ArrayList<>());
        data.put(getString(R.string.dinner), new ArrayList<>());
        data.put(getString(R.string.favourites), new ArrayList<>());


        firebaseFirestore.collection(getString(R.string.users)).document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> signUpSuccess(user.getDisplayName()))
                .addOnFailureListener(exception ->
                        firebaseFirestore.collection(getString(R.string.users))
                                .document(user.getUid()).set(data, SetOptions.merge())
                                .addOnSuccessListener(documentReference -> {
                                    signUpSuccess(user.getDisplayName());
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(requireContext(),"error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
    }


    private void createNewCollectionForUser(FirebaseUser user) {
        Map<String, Object> data = new HashMap<>();
        data.put(getString(R.string.breakfast), new ArrayList<>());
        data.put(getString(R.string.launch), new ArrayList<>());
        data.put(getString(R.string.dinner), new ArrayList<>());
        data.put(getString(R.string.favourites), new ArrayList<>());

        firebaseFirestore.collection(getString(R.string.users)).document(user.getUid()).set(data)
                .addOnSuccessListener(documentReference -> {
                    signUpSuccess(user.getDisplayName());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(),"error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void signUpWithGoogle()
    {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(requireContext(),gso);
        signUpLauncher.launch(client.getSignInIntent());
    }


    private void googleSignUpSuccess(String idToken)
    {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        createNewCollectionForGoogleUser(user);
                    }
                });
    }
    private void signUpSuccess(String username)
    {
        Toast.makeText(getContext(), getString(R.string.welcome)+" "+username, Toast.LENGTH_SHORT).show();
        controller.popBackStack();
    }


    private void enableInteraction()
    {
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }


    private void disableInteraction()
    {
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}