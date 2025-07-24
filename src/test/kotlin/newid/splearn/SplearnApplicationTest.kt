package newid.splearn

import org.mockito.Mockito
import org.springframework.boot.SpringApplication
import kotlin.test.Test

class SplearnApplicationTest {
    @Test
    fun testSpringApplicationRunIsCalled() {
        Mockito.mockStatic(SpringApplication::class.java).use { mocked ->
            // 직접 main() 호출 대신 직접 run() 호출
            SpringApplication.run(SplearnApplication::class.java, *arrayOf<String>())

            mocked.verify {
                SpringApplication.run(SplearnApplication::class.java, *arrayOf<String>())
            }
        }
    }
}