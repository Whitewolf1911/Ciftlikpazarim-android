package com.alibasoglu.ciftlikpazarimandroid.adverts.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alibasoglu.ciftlikpazarimandroid.adverts.data.AdvertsApi
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import java.io.IOException
import retrofit2.HttpException

class AdvertsPagingSource(
    private val advertsApi: AdvertsApi,
    private val category: String
) : PagingSource<Int, Advert>() {

    override fun getRefreshKey(state: PagingState<Int, Advert>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.prevKey?.plus(1) ?: closestPage.nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Advert> {
        var page = params.key ?: FIRST_PAGE_INDEX
        return try {
            val advertsListResponse = advertsApi.getCategoryAdverts(category = category, page = page).body()
            val adverts = advertsListResponse.orEmpty()
            val nextKey = if (adverts.isEmpty()) null else page.plus(1)
            val prevKey = if (page == FIRST_PAGE_INDEX) null else page.minus(1)
            LoadResult.Page(
                data = adverts,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Log.d("ioexception", e.toString())
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("http exception", e.toString())
            LoadResult.Error(e)
        }

    }


    companion object {
        const val ADVERTS_PAGE_SIZE = 5
        private const val FIRST_PAGE_INDEX = 0
    }
}
