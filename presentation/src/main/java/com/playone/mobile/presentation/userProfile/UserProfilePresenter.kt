package com.playone.mobile.presentation.userProfile

import com.playone.mobile.domain.Credential
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.user.GetUser
import com.playone.mobile.domain.interactor.user.UpdateUserProfile
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.UserView
import io.reactivex.observers.DisposableSingleObserver

class UserProfilePresenter(
    val getUser: GetUser,
    val getCurrentUser: GetCurrentUser,
    val updateUserProfile: UpdateUserProfile,
    val viewMapper: Mapper<UserView, User>
) : UserProfileContract.Presenter {

    var userProfileView: UserProfileContract.View? = null

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        userProfileView = null
    }

    override fun setView(
        view: UserProfileContract.View
    ) {
        userProfileView = view
    }

    override fun getUserById(userId: String) = getUser.execute(GetUserSubscriber(), userId)

    override fun getCurrentUser() = getCurrentUser.execute(GetUserSubscriber())

    inner class GetUserSubscriber: DisposableSingleObserver<User>() {

        override fun onSuccess(t: User) {

            userProfileView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            userProfileView?.onResponse(ViewResponse.error(e))
        }

    }
}