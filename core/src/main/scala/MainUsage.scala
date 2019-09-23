package demo

import Queries._

object Queries {
   implicit class QueryHelper(private val sc: StringContext) extends AnyVal {
      def query(args: Any*): String =
         macro Demo.queryImpl
   }
}


object Usage {
   object Schema {
      val name = "Foo.name"
   }

   def main(args: Array[String]): Unit = {
      println(query"""
        [:find ?a
         :in $$
         :where [?a ${Schema.name}]]
      """)
   }
}

