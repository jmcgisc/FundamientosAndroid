package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.FragmentSignUpBinding
import io.keepcoding.eh_ho.extensions.isValidEmail
import io.keepcoding.eh_ho.extensions.isValidPassword
import io.keepcoding.eh_ho.extensions.isValidUsername

class SignUpFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater, container, false).apply {
        labelSignIn.setOnClickListener {
            vm.moveToSignIn()
        }
        vm.signUpData.observe(viewLifecycleOwner) {
            inputEmail.apply {
                setText(it.email)
                setSelection(it.email.length)
            }
            inputUsername.apply {
                setText(it.userName)
                setSelection(it.userName.length)
            }
            inputPassword.apply {
                setText(it.password)
                setSelection(it.password.length)
            }
            inputConfirmPassword.apply {
                setText(it.confirmPassword)
                setSelection(it.confirmPassword.length)
            }
        }
        vm.signUpEnabled.observe(viewLifecycleOwner) {
            buttonSignUp.isEnabled = it
        }
        inputEmail.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpEmail))
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpPassword))
        }
        inputConfirmPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpConfirmPassword))
        }
        buttonSignUp.setOnClickListener {

            if (inputConfirmPassword.text.toString().equals(inputPassword.text.toString())){
                if (checkSignUpPattern(inputUsername.text.toString(), inputEmail.text.toString(), inputPassword.text.toString())){
                    vm.signUp()
                }else{
                    inputConfirmPassword.setText(" ")
                    inputPassword.setText(" ")
                    inputEmail.setText(" ")
                    inputUsername.setText(" ")

                    inputEmail.error = "Verfiry marked fields"
                    inputUsername.error = "Verfiry marked fields"
                    inputPassword.error = "Verfiry marked fields"
                }
            }else{
                inputConfirmPassword.error = "Password confirmation does not match"
            }
        }
    }.root

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }

    //
    private fun checkSignUpPattern(username: String, email: String, password: String): Boolean{
        return email.isValidEmail() && password.isValidPassword()
    }
}