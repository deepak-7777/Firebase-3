//package com.example.firebase6;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
////public class MainActivity extends AppCompatActivity {
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        EdgeToEdge.enable(this);
////        setContentView(R.layout.activity_main);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
////    }
////}
//
//
//
//public class MainActivity extends AppCompatActivity {
//
//    EditText phoneEditText, otpEditText;
//    View sendOtpBtn;
//    View verifyOtpBtn;
//    String verificationId;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        phoneEditText = findViewById(R.id.phoneEditText);
//        otpEditText = findViewById(R.id.otpEditText);
//        sendOtpBtn = findViewById(R.id.sendOtpBtn);
//        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);
//
//        sendOtpBtn.setOnClickListener(v -> sendOTP());
//        verifyOtpBtn.setOnClickListener(v -> verifyOTP());
//    }
//
//    private void sendOTP() {
//        String phoneNumber = "+91" + phoneEditText.getText().toString();
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(callbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
//            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential credential) {
//                    signInWithCredential(credential);
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    Toast.makeText(MainActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                    super.onCodeSent(id, token);
//                    verificationId = id;
//                    Toast.makeText(MainActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
//                }
//            };
//
//    private void verifyOTP() {
//        String otp = otpEditText.getText().toString();
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
//        signInWithCredential(credential);
//    }
//
//    private void signInWithCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}




package com.example.firebase6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phoneEditText, otpEditText;
    View sendOtpBtn;
    View verifyOtpBtn;
    String verificationId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneEditText = findViewById(R.id.phoneEditText);
        otpEditText = findViewById(R.id.otpEditText);
        sendOtpBtn = findViewById(R.id.sendOtpBtn);
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);

        mAuth = FirebaseAuth.getInstance();

        sendOtpBtn.setOnClickListener(v -> sendOTP());
        verifyOtpBtn.setOnClickListener(v -> verifyOTP());
    }

    private void sendOTP() {
        String phoneNumber = phoneEditText.getText().toString().trim();

        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        String fullPhoneNumber = "+91" + phoneNumber;

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(fullPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(this, "Sending OTP...", Toast.LENGTH_SHORT).show();
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    signInWithCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(MainActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(id, token);
                    verificationId = id;
                    Toast.makeText(MainActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyOTP() {
        String otp = otpEditText.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (verificationId == null || verificationId.isEmpty()) {
            Toast.makeText(this, "Please request OTP first", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class)); // Replace with your dashboard/activity
                finish();
            } else {
                Toast.makeText(this, "Invalid OTP or Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

