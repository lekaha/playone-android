package com.playone.mobile.presentation.browse

import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.anyVararg
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.playone.mobile.domain.interactor.browse.GetBufferoos
import com.playone.mobile.domain.model.Bufferoo
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.BufferooMapper
import com.playone.mobile.presentation.model.BufferooView
import com.playone.mobile.presentation.test.factory.BufferooFactory
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

        verify(mockGetBufferoos).execute(captor.capture(), eq(Unit))
        captor.firstValue.onSuccess(bufferoos)
        verify(mockBufferooMapper, times(2)).mapToView(anyVararg<Bufferoo>())
        verify(mockBrowseBufferoosView).onResponse(anyVararg<ViewResponse<List<BufferooView>>>())
    }

    @Test
    fun retrieveBufferoosHidesEmptyStateWhenErrorThrown() {
        browseBufferoosPresenter.retrieveBufferoos()

        verify(mockGetBufferoos).execute(captor.capture(), eq(Unit))
        captor.firstValue.onError(RuntimeException())
        verify(mockBufferooMapper, never()).mapToView(anyVararg<Bufferoo>())
        verify(mockBrowseBufferoosView).onResponse(anyVararg<ViewResponse<List<BufferooView>>>())
    }
    //</editor-fold>

}