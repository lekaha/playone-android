package com.playone.android.presentation.browse

import com.playone.android.domain.interactor.browse.GetBufferoos
import com.playone.android.domain.model.Bufferoo
import com.playone.android.presentation.ViewResponse
import com.playone.android.presentation.mapper.BufferooMapper
import com.playone.android.presentation.model.BufferooView
import com.playone.android.presentation.test.factory.BufferooFactory
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BrowseBufferoosPresenterTest {

    private lateinit var mockBrowseBufferoosView: BrowseBufferoosContract.View
    private lateinit var mockBufferooMapper: BufferooMapper
    private lateinit var mockGetBufferoos: GetBufferoos

    private lateinit var browseBufferoosPresenter: BrowseBufferoosPresenter
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Bufferoo>>>

    @Before
    fun setup() {
        captor = argumentCaptor<DisposableSingleObserver<List<Bufferoo>>>()
        mockBrowseBufferoosView = mock()
        mockBufferooMapper = mock()
        mockGetBufferoos = mock()
        browseBufferoosPresenter = BrowseBufferoosPresenter(
                mockGetBufferoos, mockBufferooMapper)
        browseBufferoosPresenter.setView(mockBrowseBufferoosView)
    }

    @Test
    fun retrieveBufferoosShowsBufferoos() {
        val bufferoos = BufferooFactory.makeBufferooList(2)
        browseBufferoosPresenter.retrieveBufferoos()

        verify(mockGetBufferoos).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(bufferoos)
        verify(mockBufferooMapper, times(2)).mapToView(anyVararg<Bufferoo>())
        verify(mockBrowseBufferoosView).onResponse(anyVararg<ViewResponse<List<BufferooView>>>())
    }

    @Test
    fun retrieveBufferoosHidesEmptyStateWhenErrorThrown() {
        browseBufferoosPresenter.retrieveBufferoos()

        verify(mockGetBufferoos).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        verify(mockBufferooMapper, never()).mapToView(anyVararg<Bufferoo>())
        verify(mockBrowseBufferoosView).onResponse(anyVararg<ViewResponse<List<BufferooView>>>())
    }
    //</editor-fold>

}