package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.source.local.database.HeroDao
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class DBWrapperTest {

    private lateinit var dBWrapperImpl: DBWrapperImpl

    private val heroDaoMock: HeroDao = mock()

    private val dummyHeroData = listOf(
        LHero(
            id = 1,
            name = "A-Bomb (HAS)",
            availableComics = 4,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
        ),
        LHero(
            id = 2,
            name = "Absorbing Man",
            availableComics = 99,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg",
        )
    )


    @Before
    fun setUp() {
        dBWrapperImpl = DBWrapperImpl(
            heroDaoMock
        )
    }

    @Test
    fun `given no Heroes are stored when isHeroDataStored is invoked then verify countHeroes from DB returns false`() = runTest{
        // given
        val expectedCount = 0
        given(heroDaoMock.countHeroes()).willReturn(expectedCount)
        // when
        dBWrapperImpl.isHeroDataStored().collect { count ->

            // then
            verify(heroDaoMock).countHeroes()
            assertFalse(count)
        }
    }

    @Test
    fun `given Heroes are stored when isHeroDataStored is invoked then verify countHeroes from DB returns true`() = runTest{
        // given
        val expectedCount = 5
        given(heroDaoMock.countHeroes()).willReturn(expectedCount)

        // when
        dBWrapperImpl.isHeroDataStored().collect { count ->
            // then
            verify(heroDaoMock).countHeroes()
            assertTrue(count)
        }
    }


    @Test
    fun `when invoking storeHeroes verify results and interactions`() = runTest{
        // when
        dBWrapperImpl.storeHeroes(dummyHeroData)
        // then
        verify(heroDaoMock).insertHeroes(dummyHeroData)
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // given
        given(heroDaoMock.getHeroes()).willReturn(flowOf(dummyHeroData))

        // when
        dBWrapperImpl.getHeroes().collect { result ->
            // then
            verify(heroDaoMock).getHeroes()
            assertThat(result, equalTo(dummyHeroData))
        }
    }

    @Test
    fun `when invoking deleteAllHeroes verify results and interactions`() = runTest{
        // when
        dBWrapperImpl.deleteAllHeroes()
        // then
        verify(heroDaoMock).deleteAllHeroes()
    }

}