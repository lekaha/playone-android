package com.playone.mobile.presentation.browse

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.anyVararg
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
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