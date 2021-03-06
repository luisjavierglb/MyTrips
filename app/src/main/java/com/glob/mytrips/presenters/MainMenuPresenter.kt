package com.glob.mytrips.presenters

import com.glob.mytrips.contracts.MainMenuContract
import com.glob.mytrips.domain.providers.UserInfoProvider
import com.glob.mytrips.domain.usecases.userinfo.GetUserInfoUseCase
import io.reactivex.disposables.CompositeDisposable

class MainMenuPresenter(
    private val userInfoProvider: UserInfoProvider,
    private val view: MainMenuContract.View
) : MainMenuContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getIdUser() {
        //TODO("Not yet implemented")
    }

    override fun getUserAccount(userId: Int) {
        val params = GetUserInfoUseCase.Params(userId)
        showLoader()
        disposable.add(
            userInfoProvider.getCountriesByUserUseCase().execute(params)
                .subscribe({success ->
                    view.onMainInfoLoaded(success)
                    hideLoader()
                }, { throwable ->
                    view.onMainInfoLoadedFail(throwable.message ?: "")
                    hideLoader()
                })
        )
    }

//    override fun getIdCountry(user: Int) {}
//    override fun getIdState(county: Int) {
//        //TODO("Not yet implemented")
//    }
//    override fun getIdPlace(state: Int) {
//        //TODO("Not yet implemented")
//    }

    private fun showLoader() {
        view.showLoader(true)
    }

    private fun hideLoader() {
        view.showLoader(false)
    }
    override fun onDestroy() {
        disposable.dispose()
    }
}