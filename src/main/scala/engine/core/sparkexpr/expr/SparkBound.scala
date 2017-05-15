package engine.core.sparkexpr.expr
import engine.core.sparkexpr.compiler.SparkExprVisitor
import org.apache.jena.sparql.expr.E_Bound

/**
  * Created by xiangnanren on 04/05/2017.
  */
class SparkBound(val expr: E_Bound,
                 subExpr: SparkExpr) extends SparkExpr1[SparkExpr](subExpr) {

  override def visit(sparkExprVisitor: SparkExprVisitor): Unit = {
    sparkExprVisitor.visit(this)
  }
}

object SparkBound {
  def apply(expr: E_Bound, subExpr: SparkExpr): SparkBound =
    new SparkBound(expr, subExpr)
}