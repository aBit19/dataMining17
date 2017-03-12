import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Header

import scala.io.StdIn

object Preprocessing  {
  def main(args: Array[String]): Unit = {
    val contents = readFile("", Nil)
    val res: List[List[String]] = contents.
      map(str => if (str == null) "" else str).
      map(x => x.split('"').toList).
      reverse
    val header :: data =  res
    val map = unroll(header, data)
    //val map1 = mapRow(header, firstRow)
    //val (second, t1) = getHeadTail(t)
    //val map2 = mapRow(header, second)
    //val rs = combineMap(map1, map2)
    for ((k, v) <- map)
      println(k + "->" + v)
  }

  def readFile(x: String, list: List[String]): List[String] = {
    if (x == null) x :: list
    else if (x isEmpty) readFile(StdIn.readLine(), list)
    else readFile(StdIn.readLine(), x :: list)
  }

  def unroll(header: List[String], dataObjs: List[List[String]]) : Map[String, List[String]] = {
    dataObjs match {
      case h :: t => combineMap(mapRow(header, h), unroll(header, t))
      case Nil => Map.empty[String, List[String]]
    }
  }
  def mapRow(header: List[String], row: List[String]): Map[String, List[String]]= {
    (header.zip(row).map(t => Map(t._1 -> List(t._2))) :\ Map.empty[String, List[String]])(_ ++ _)
  }

  def combineMap(m1: Map[String, List[String]], m2: Map[String, List[String]]): Map[String ,List[String]] = {
    val l = (for (e1 <- m1) yield Map(e1._1 -> (e1._2 ::: m2.getOrElse(e1._1, List.empty)))).toList
    (Map.empty[String, List[String]] /: l) (_ ++ _)
  }
}