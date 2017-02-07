//copied from https://github.com/almende/vis/issues/1929

package vis

import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.annotation._
import org.scalajs.dom.raw.Element
import scala.annotation.meta.field

@JSName("vis.Network")
@js.native
class Network(container: Element, data: Data, options: Options) extends js.Any

@JSName("vis.DataSet")
@js.native
class DataSet[T <: NodeOrEdge](data: js.Array[T]) extends js.Any{
  def add(datum: T): js.Any = js.native
  def update(datum: T): js.Any = js.native
  def getIds(): js.Array[String] = js.native
  def get(): js.Array[T] = js.native
}

object DataSet{
  implicit class Nodes(nodes: DataSet[Node]){
    def addOnce(node: Node) = {
      if(!nodes.get.exists(_.id == node.id))
        nodes.add(node)
    }
  }
  implicit class Edges(edges: DataSet[Edge]){
    def addOnce(edge: Edge) = {
      if(
        !edges.get.exists(
          e => e.from == edge.from && e.to == edge.to
        )
      )
        edges.add(edge)
    }
  }
}

@ScalaJSDefined
sealed trait NodeOrEdge extends js.Object

@ScalaJSDefined
final class Node(
                  val id: String,
                  val label: String,
                  var color: String = "gray",
                  val shape: String = "box",
                  val mass: Double = 3
                ) extends NodeOrEdge

@ScalaJSDefined
final class Edge( val from: String, val to: String, val toArrow: Boolean = false, val fromArrow: Boolean= false ) extends NodeOrEdge{
  val arrows = (Some("to").filter(_ => toArrow) ++ Some("from").filter(_ => fromArrow)).mkString(", ")
}

sealed abstract class DirectionInput(val string: String)

case object UD extends DirectionInput("UD")
case object DU extends DirectionInput("DU")
case object LR extends DirectionInput("LR")
case object RL extends DirectionInput("RL")

@ScalaJSDefined
final class Data( val nodes: DataSet[Node], val edges: DataSet[Edge] ) extends js.Object

@ScalaJSDefined
final class Hierarchical( _direction: DirectionInput ) extends js.Object{
  val direction = _direction.string
}

@ScalaJSDefined
final class Layout( val hierarchical: Hierarchical ) extends js.Object

@ScalaJSDefined
final class Repulsion( val nodeDistance: Int, val springLength: Int, val springConstant: Double ) extends js.Object

@ScalaJSDefined
final class Physics( val repulsion: Repulsion ) extends js.Object

@ScalaJSDefined
final class Options(
                     val layout: js.UndefOr[Layout] = js.undefined//,
                     //val physics: Physics = new Physics( new Repulsion( 1, 1, 0.001 ) )
                   ) extends js.Object