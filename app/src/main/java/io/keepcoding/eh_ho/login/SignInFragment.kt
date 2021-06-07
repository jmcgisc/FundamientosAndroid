package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.FragmentSignInBinding
import io.keepcoding.eh_ho.extensions.isValidPassword
import io.keepcoding.eh_ho.extensions.isValidUsername
import java.util.regex.Pattern

class SignInFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSignInBinding.inflate(inflater, container, false).apply {
        labelCreateAccount.setOnClickListener {
            vm.moveToSignUp()
        }
        vm.signInData.observe(viewLifecycleOwner) {
            inputUsername.apply {
                setText(it.userName)
                setSelection(it.userName.length)

            }
            inputPassword.apply {
                setText(it.password)
                setSelection(it.password.length)
            }
        }
        vm.signInEnabled.observe(viewLifecycleOwner) {
            buttonLogin.isEnabled = it
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInPassword))
        }
        buttonLogin.setOnClickListener {
            if(checkLoginPattern(inputUsername.text.toString(), inputPassword.text.toString())){
                vm.signIn()
            }else{
                inputUsername.error = " Username or password incorrect"
                inputPassword.error = " Username or password incorrect"
            }
        }
    }.root

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }

    //
    private fun checkLoginPattern(username: String, password: String): Boolean{
        return username.isValidUsername() && password.isValidPassword()
    }
}

