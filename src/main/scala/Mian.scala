import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Future

object Main {
  import scala.concurrent.ExecutionContext.Implicits._
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    val wsClient = AhcWSClient()

    call(wsClient)
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
  }

  def call(wsClient: WSClient) = {
    val postJson = """{"text": "This is a line of text in a channel.¥nAnd this is another line of text."}"""
    val contentType = ("Content-Type" -> "application/json")

    wsClient.url("https://hooks.slack.com/services/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")
      .withHeaders(contentType)
      .post(postJson)
  }
}