package `in`.test.pro.beta.data.base

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <I> input type
 * @param <O> output type
 */
abstract class Mapper<in I, out O> {

    abstract fun map(input: I): O

    open fun mapList(inputList: List<I>): List<O>{
        return inputList.map {
            map(it)
        }
    }
}