package demo

object Usage {
   def main(args: Array[String]): Unit = {
      println(Demo.stringer("Hello, world"))

      val world = "world"
      val subject = "foo"
      println(Demo.stringer(s"Hello, $world from $subject"))
   }
}

