package demo

import language.experimental.macros
import scala.reflect.macros.blackbox.Context

object Demo {

  def stringer(e: String): String = macro stringerImpl

  def stringerImpl(c: Context)(e: c.Expr[String]): c.Tree = {
    import c.universe._

    c.info(c.enclosingPosition, s"${showRaw(e.tree)}", false)

    e.tree match {
      case Literal(Constant(s: String)) =>
        c.info(c.enclosingPosition, "Matched String literal", false)
        Literal(Constant(s"Literal: $s"))

      case q"scala.StringContext.apply(..$parts).s(..$args)" =>
        c.info(c.enclosingPosition, s"Matched StringContext with $parts and $args", false)
        q""""StringContext: " + Seq(..$args).mkString(", ")"""

      case _ =>
        c.abort(c.enclosingPosition, "Expected a string literal")
    }
  }
}
