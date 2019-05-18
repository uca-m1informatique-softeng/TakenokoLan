package serveur.controller

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")

  object HelloWorldResource {
    val get: ChainBuilder = exec(http("TryConnect")
      .get("/127.0.0.1/8081/Bob/Connect"))
  }

  val myScenario: ScenarioBuilder = scenario("TryConnect")
    .exec(HelloWorldResource.get)

  setUp(myScenario.inject(
    incrementUsersPerSec(40)
      .times(10)
      .eachLevelLasting(5 seconds)
      .separatedByRampsLasting(5 seconds)
      .startingFrom(20)
  )).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(80))
}