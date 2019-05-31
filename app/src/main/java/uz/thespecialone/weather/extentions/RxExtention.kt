package uz.thespecialone.weather.extentions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.subscribeEx(f: (T) -> Unit, e: (Throwable) -> Unit): Disposable {
    return main().subscribe({ f(it) }, { e(it) })
}

fun <T> Single<T>.main() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.main(): Observable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())