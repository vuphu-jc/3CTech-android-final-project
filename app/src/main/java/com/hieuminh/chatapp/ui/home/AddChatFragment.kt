package com.hieuminh.chatapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.dialog.CustomAlertDialog
import com.hieuminh.chatapp.model.UserProfileModel
import com.hieuminh.chatapp.presenter.contract.AddChatContract
import com.hieuminh.chatapp.presenter.impl.AddChatPresenterImpl
import com.hieuminh.chatapp.ui.navigator.AddChatNavigator
import com.hieuminh.chatapp.ui.navigator.Impl.AddChatNavigatorImpl
import kotlinx.android.synthetic.main.fragment_add_chat.*

class AddChatFragment : Fragment(), AddChatContract.View {

    private lateinit var mAddChatNavigator: AddChatNavigator
    private lateinit var mAddChatPresenter: AddChatContract.Presenter
    private var userProfile: UserProfileModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_chat, container, false)
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
        llSearchSuccess.visibility = VISIBLE
        tvEmail.text = userProfile?.email
        tvName.text = userProfile?.name
    }

    override fun onSearchNotFound() {
        llSearchNotFound.visibility = VISIBLE
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
        if (llSearchNotFound.visibility != GONE)
            llSearchNotFound.visibility = GONE
        if (llSearchSuccess.visibility != GONE)
            llSearchSuccess.visibility = GONE
        processBar.visibility = VISIBLE
    }

    override fun onStopProcessBar() {
        processBar.visibility = GONE
    }

    override fun onAddChatFailureDueToUserExistedAndTransferToChatDetail(chatId: String) {
        Toast.makeText(
            context,
            getString(R.string.this_user_is_connected_before),
            Toast.LENGTH_LONG
        ).show()
        mAddChatNavigator.startChatDetailFragment(chatId)
    }

    private fun onReturnHomeListener() = View.OnClickListener { mAddChatNavigator.returnHomeFragment() }

    private fun onHandleSearchEmailListener() = View.OnClickListener {
        mAddChatPresenter.search(
            etAddChat.text.toString().trim()
        )
    }

    private fun onHandleAddChatListener() =
        View.OnClickListener {
            mAddChatPresenter.addChat(userProfile)
        }

    private fun init(view: View) {
        mAddChatNavigator = AddChatNavigatorImpl(view)
        mAddChatPresenter = AddChatPresenterImpl(this)
    }
}
