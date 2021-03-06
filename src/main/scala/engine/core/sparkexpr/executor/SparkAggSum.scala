package engine.core.sparkexpr.executor

import engine.core.sparkexpr.expr.ExprHelper
import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * Created by xiangnanren on 04/09/2017.
  */


/**
  * The Max aggregator bases on the type Double for computation,
  * i.e., the method converts BigDecimal, Double, Float, Int
  * into Double for aggregation.
  *
  * Note that the chosen initial value for Max aggregator is 0.0
  */
object SparkAggSum extends UserDefinedAggregateFunction {
  override def inputSchema: StructType =
    StructType(StructField("inputColumn", StringType) :: Nil)

  override def bufferSchema: StructType = {
    StructType(StructField("spark-agg-sum", DoubleType) :: Nil)
  }

  override def dataType: DataType = DoubleType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0.0
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    val arg = input.getString(0)
    if (arg != null) {
      ExprHelper.isNumValue(arg) match {
        case true =>
          val numArg = ExprHelper.getNumValueAsString(arg).toDouble
          buffer(0) = buffer.getDouble(0) + numArg
        case false =>
      }
    }
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getDouble(0) + buffer2.getDouble(0)
  }

  override def evaluate(buffer: Row): Double = buffer.getDouble(0)
}
