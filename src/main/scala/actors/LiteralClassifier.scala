package actors

import akka.actor.{Actor, Props}

/**
  * Created by utsav on 14/7/17.
  */
object LiteralClassifier {
  sealed trait StateType
  case object UnknownState extends StateType
  case object IntegerState extends StateType
  case object MaybeFloatState extends StateType
  case object FloatState extends StateType
  case object StringState extends StateType

  case class Input(c: Char)
  case class State(state: StateType)
  case object GetState

  def props = Props(classOf[LiteralClassifier])
}

class LiteralClassifier extends Actor {
  import LiteralClassifier._

  val unknownState: Receive = {
    case Input(digit) if digit.toInt >= 48 && digit.toInt <= 57 => context.become(integerState)
    case Input(dot) if dot.toInt == 46 => context.become(maybeFloatState)
    case Input(other) => context.become(stringState)
    case GetState => sender ! State(UnknownState)
  }

  val integerState: Receive = {
    case Input(digit) if digit.toInt >= 48 && digit.toInt <= 57 => context.become(integerState)
    case Input(dot) if dot.toInt == 46 => context.become(floatState)
    case Input(other) => context.become(stringState)
    case GetState => sender ! State(IntegerState)
  }

  val maybeFloatState: Receive = {
    case Input(digit) if digit.toInt >= 48 && digit.toInt <= 57 => context.become(floatState)
    case Input(other) => context.become(stringState)
    case GetState => sender ! State(MaybeFloatState)
  }

  val floatState: Receive = {
    case Input(digit) if digit.toInt >= 48 && digit.toInt <= 57 => context.become(floatState)
    case Input(other) => context.become(stringState)
    case GetState => sender ! State(FloatState)
  }

  val stringState: Receive = {
    case Input(other) => context.become(stringState)
    case GetState => sender ! State(StringState)
  }

  val receive: Receive = unknownState
}
