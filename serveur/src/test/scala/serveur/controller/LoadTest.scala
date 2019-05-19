package serveur.controller

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")

  object TryConnect {
    val get: ChainBuilder = exec(http("TryConnect")
      .get("/127.0.0.1/8081/Bob/Connect"))
  }

  val myScenario: ScenarioBuilder = scenario("TryConnect")
    .exec(TryConnect.get)

  val myScenario2: ScenarioBuilder = scenario("TryConnect2")
    .exec(TryConnect.get)

  setUp(myScenario.inject(
    incrementUsersPerSec(100)
      .times(5)
      .eachLevelLasting(5 seconds)
      .separatedByRampsLasting(5 seconds)
      .startingFrom(20)
  ),myScenario2.inject(
    nothingFor(58 seconds),
    constantUsersPerSec(200) during (10 seconds),
    constantUsersPerSec(300) during (5 seconds) randomized,
    atOnceUsers(500),nothingFor(1),
    constantUsersPerSec(1000) during (5 seconds),
    constantUsersPerSec(250) during (5 seconds)).protocols(httpProtocol)).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.between(80,100))

}