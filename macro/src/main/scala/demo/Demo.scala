package demo

import scala.reflect.macros.blackbox.Context

object Demo {

  def queryImpl(c: Context)(args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._

    c.info(c.enclosingPosition, s"queryImpl: ${showRaw(c.prefix.tree)}", false)

    c.prefix.tree match {
      case Literal(Constant(s: String)) =>
        c.info(c.enclosingPosition, s"Got literal string $s", false)
        c.Expr[String](Literal(Constant(s)))

      case Apply(_, List(a @ Apply(_, _))) =>
        c.info(c.enclosingPosition, s"Got StringContext ${show(a)} ${show(args)}", false)
        val q"scala.StringContext.apply(..$parts)" = a
        val partsWithPlaceholders = q"""Seq(..$parts).mkString(" ! ")"""
        val query = c.eval(c.Expr[String](c.untypecheck(partsWithPlaceholders.duplicate)))
        c.Expr[String](Literal(Constant(s"QueryHelper: $query")))

      case _ =>
        c.abort(c.enclosingPosition, s"Expected a string literal but got ${showRaw(c.prefix.tree)}")
    }
  }
}
