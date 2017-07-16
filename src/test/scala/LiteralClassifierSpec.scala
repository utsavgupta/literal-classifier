import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import actors.LiteralClassifier._

/**
  * Created by utsav on 15/7/17.
  */
class LiteralClassifierSpec() extends TestKit(ActorSystem("my-spec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "LiteralClassifierSpec" must {
    "test for unknown state if no input is provided" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      actor ! GetState
      expectMsg(State(UnknownState))
    }

    "test for integer 123" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "123".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(IntegerState))
    }

    "test for float 123.45" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "123.45".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(FloatState))
    }

    "test for float .12" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      ".12".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(FloatState))
    }

    "test for dot ." in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      ".".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(MaybeFloatState))
    }

    "test for double dot .." in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "..".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(StringState))
    }

    "test for string 123ab" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "123ab".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(StringState))
    }

    "test for string 123.4ab" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "123.4ab".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(StringState))
    }

    "test for string ab123.5" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "ab123.5".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(StringState))
    }

    "test for string hello world" in {
      val actor = system.actorOf(actors.LiteralClassifier.props)
      "hello world".foreach(c => actor ! Input(c))
      actor ! GetState
      expectMsg(State(StringState))
    }
  }
}
