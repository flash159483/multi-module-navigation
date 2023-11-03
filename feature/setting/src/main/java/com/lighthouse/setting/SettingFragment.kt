package com.lighthouse.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lighthouse.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {
    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig

    private lateinit var binding: FragmentSettingBinding
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            getResult.value = result.data

        }

    private val getResult = MutableLiveData<Intent?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.tvVersion.text = remoteConfig.getString("MIN_VER")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInListener()
        observeSignInResult()
    }

    private fun observeSignInResult() {
        getResult.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            val task = GoogleSignIn.getSignedInAccountFromIntent(it)
            try {
                val account = task.getResult(ApiException::class.java)

                val email = account.email ?: ""
                getResult.value = null

                Log.d("TESTING", "${account.familyName}, ${account.givenName}, ${account.photoUrl}")
            } catch (e: ApiException) {
                Log.d("TESTING", e.stackTraceToString())
            }
        }
    }

    private fun signInListener() {
        binding.btnSignIn.setOnClickListener {
            requestGoogleLogin()
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInClient = googleSignInClient.signInIntent
        resultLauncher.launch(signInClient)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Scope("https://www.googleapis.com/auth/userinfo.email"))
                .requestEmail()
                .requestId()
                .requestProfile()
                .requestIdToken(getString(com.lighthouse.common_ui.R.string.client_id))
                .build()

        return GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

}