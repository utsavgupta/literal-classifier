import actors.LiteralClassifier
import actors.LiteralClassifier._
import akka.pattern.ask
import akka.actor.ActorSystem
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

/**
  * Created by utsav on 14/7/17.
  */
object TestApp extends App {
  val system: ActorSystem = ActorSystem.create("my-system")
  val classifier = system.actorOf(LiteralClassifier.props, "literal-classifier")
  implicit val timeout: Timeout = 2.seconds

  print("Please enter a literal: ")

  StdIn.readLine().foreach(c => classifier ! Input(c))

  (classifier ? GetState).mapTo[State].map {
    case State(UnknownState) => println("Unknown literal")
    case State(IntegerState) => println("Integer literal")
    case State(FloatState)   => println("Float literal")
    case State(StringState)  => println("String literal")
    case State(MaybeFloatState)  => println("String literal")
  }
}
