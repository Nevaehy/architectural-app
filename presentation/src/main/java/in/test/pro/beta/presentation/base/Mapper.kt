package `in`.test.pro.beta.presentation.base

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <I> input type
 * @param <O> output type
 */
abstract class Mapper<in I, out O> {

    abstract fun mapToView(input: I): O

    open fun mapListToView(list: List<I>) = list.map { mapToView(it) }
}