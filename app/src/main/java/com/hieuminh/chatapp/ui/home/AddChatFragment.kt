package com.hieuminh.chatapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.dialog.CustomAlertDialog
import com.hieuminh.chatapp.model.UserProfileModel
import com.hieuminh.chatapp.presenter.contract.AddChatContract
import com.hieuminh.chatapp.presenter.impl.AddChatPresenterImpl
import com.hieuminh.chatapp.ui.navigator.AddChatNavigator
import com.hieuminh.chatapp.ui.navigator.Impl.AddChatNavigatorImpl
import kotlinx.android.synthetic.main.fragment_add_chat.*
import kotlinx.android.synthetic.main.fragment_add_chat.view.*

class AddChatFragment : Fragment(), AddChatContract.View {

    private lateinit var mAddChatNavigator: AddChatNavigator
    private lateinit var mAddChatPresenter: AddChatContract.Presenter
    private var userProfile: UserProfileModel? = null
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_add_chat, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        toolBar.setNavigationOnClickListener(onReturnHomeListener())

        btSearchEmail.setOnClickListener(onHandleSearchEmailListener())

        btAddChat.setOnClickListener(onHandleAddChatListener())
    }

    override fun onDestroy() {
        mAddChatPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onSearchSuccess(userProfile: UserProfileModel?) {
        this.userProfile = userProfile
        mView.llSearchSuccess.visibility = VISIBLE
        mView.tvEmail.text = userProfile?.email
        mView.tvName.text = userProfile?.name
        Glide.with(mView).load(userProfile?.avatarUrl).into(mView.civAvatar)
    }

    override fun onSearchNotFound() {
        mView.llSearchNotFound.visibility = VISIBLE
    }

    override fun onSearchFailure(message: String) {
        CustomAlertDialog(requireContext()).startAlertDialog(message)
    }

    override fun onAddChatSuccess(chatId: String) {
        Toast.makeText(requireContext(), getString(R.string.connect_success), Toast.LENGTH_SHORT)
            .show()
        mAddChatNavigator.startChatDetailFragment(chatId)
    }

    override fun onAddChatFailure(message: String) {
        CustomAlertDialog(requireContext()).startAlertDialog(message)
    }

    override fun onAddChatFailureDueToMessageYourself() {
        CustomAlertDialog(requireContext()).startAlertDialog(resources.getString(R.string.texting_youself_is_not_available))
    }

    override fun onStartProcessBar() {
        mView.llSearchNotFound.visibility = GONE
        mView.llSearchSuccess.visibility = GONE
        mView.processBar.visibility = VISIBLE
    }

    override fun onStopProcessBar() {
        mView.processBar.visibility = GONE
    }

    override fun onAddChatFailureDueToUserExistedAndTransferToChatDetail(chatId: String) {
        Toast.makeText(
            context,
            getString(R.string.this_user_is_connected_before),
            Toast.LENGTH_LONG
        ).show()
        mAddChatNavigator.startChatDetailFragment(chatId)
    }

    private fun onReturnHomeListener() =
        View.OnClickListener { mAddChatNavigator.returnHomeFragment() }

    private fun onHandleSearchEmailListener() = View.OnClickListener {
        mAddChatPresenter.search(
            mView.etAddChat.text.toString().trim()
        )
    }

    private fun onHandleAddChatListener() =
        View.OnClickListener { mAddChatPresenter.addChat(userProfile) }

    private fun init(view: View) {
        mAddChatNavigator = AddChatNavigatorImpl(view)
        mAddChatPresenter = AddChatPresenterImpl(this)
    }
}
