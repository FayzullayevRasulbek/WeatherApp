package uz.thespecialone.weather.mvp.info

interface InfoPresenter {

    fun loadData(id: Long)

    fun clearAndDisposeDisposable()
}