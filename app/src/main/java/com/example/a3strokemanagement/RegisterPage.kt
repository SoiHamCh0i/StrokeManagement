package com.example.a3strokemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a3strokemanagement.databinding.ActivityRegisterPageBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Response

class RegisterPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtSignIn.setOnClickListener{
            val intent = Intent(this, SignInPage::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {

            val mailSignUp = binding.mailSignUp.text.toString()
            val passwordSignUp = binding.passwordSignUp.text.toString()
            val retypePassSignUp = binding.retypePassSignUp.text.toString()

            if (mailSignUp.isNotEmpty() && passwordSignUp.isNotEmpty() && retypePassSignUp.isNotEmpty()) {
                if (passwordSignUp.equals(retypePassSignUp)) {

                    firebaseAuth.createUserWithEmailAndPassword(mailSignUp, passwordSignUp)
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                val intent = Intent(this, SignInPage::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}