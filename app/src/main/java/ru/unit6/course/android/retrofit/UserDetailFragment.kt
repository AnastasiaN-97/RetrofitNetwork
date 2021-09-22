package ru.unit6.course.android.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.unit6.course.android.retrofit.data.model.User
import ru.unit6.course.android.retrofit.databinding.UserDetailFragmentBinding
import ru.unit6.course.android.retrofit.ui.main.MainViewModel
import ru.unit6.course.android.retrofit.utils.Status


class UserDetailFragment : Fragment(R.layout.user_detail_fragment) {

    private lateinit var binding: UserDetailFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var progressBar: ProgressBar

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getUser(args.userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    resource.data?.let {//showInfo()}
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    fun showInfo(user: User){
        binding.textViewUserName.text = user.name
        binding.textViewUserEmail.text = user.email
        Glide.with(binding.imageViewAvatar.context)
            .load(user.avatar)
            .into(binding.imageViewAvatar)
    }
}