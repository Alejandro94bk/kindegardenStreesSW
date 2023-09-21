package kindegarden

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.session._
import scala.concurrent.duration._

import java.util.concurrent.ThreadLocalRandom
import java.util.Base64


class HttpSimulation extends  Simulation {
        
        val httpProtocol = http
                .baseUrl("https://nodavlat-bogcha.istream.uz")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflatem br")
                .header("Connection", "keep-alive")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .header("Accept-Language", "uz")
                .header("version", "0.0.22")


        object kindeGardenSimulation{
                val generateToken = tryMax(1){
                        exec(
                                http("auth method")
                                .post("/login")
                                .body(StringBody(""" {"username": "****", "password": "****"} """)).asJson
                                .check(jsonPath("$.data.token").find.saveAs("response_token"))
                                .check(status.is(200))
                                ).exec(session => {
                                        val response_token = session("response_token")
                                        session.set("response_token", response_token)
                                        session})
                        }
                
                val genereatePhotoId = tryMax(2){
                        exec(
                                http("generate photo id")
                                .post("/kindergartens/28397/kid-photo")
                                .header("Authorization", "Bearer #{response_token}")
                                .header("Content-Type", "multipart/form-data'")

                                .bodyPart(RawFileBodyPart("file", "img.jpeg")
                                .fileName("img.jpeg")
                                .transferEncoding("binary")).asMultipartForm

                                .check(jsonPath("$.data.id").find.saveAs("response_id"))
                                .check(jsonPath("$.data.createdAt").find.saveAs("response_time"))
                                .check(status.is(200))
                        ).exec(session => { 
                                 session("response_id") session("response_time")
                                session})
                }

                val putPhotoId = tryMax(2){
                        exec(
                                http("put photo id")
                                .put("/kindergartens/kid-photo/pud-id")
                                .header("Authorization", "Bearer #{response_token}")
                                
                                .body(StringBody(""" {
                                                                        "created_at": "02.08.2023 14:42:31",
                                                                        "model": {
                                                                        "type": "json",
                                                                        "value": "{\"f\":-16776961,\"g\":{\"mHeight\":174,\"mNativePtr\":-5476376654842974000,\"mWidth\":176},\"c\":-1,\"d\":[[-0.007686172,0.0069923997,-0.0025471922,0.0028578164,-0.011370746,-0.11995613,0.06611303,-0.09159568,-0.22481592,0.17016557,0.015053984,0.0068973396,-0.0026049006,-0.0029180404,-0.0035600583,-0.1718168,-0.012901259,0.005699611,0.0024348341,-0.0016694136,0.14134811,0.01933019,-0.14585236,-5.5275654E-4,-0.0810436,0.014374541,-0.0034165632,-0.03729517,0.2157691,-0.26136518,0.0019278747,-0.006530275,-0.12422899,-0.007894547,-0.049856827,0.094086304,0.29658687,-0.0056763096,-2.4983276E-5,-0.25225267,-0.004862301,-0.0038690246,-0.005078905,0.00281734,0.008312616,-0.011739744,0.048937112,0.10379785,-9.4353844E-4,0.013103581,-0.07114294,-5.2884826E-4,-0.1517806,-0.0030796207,0.0022077896,0.009301994,0.038294204,0.0016988246,0.0045161587,-0.013650891,-0.0024970113,-0.069429584,-0.09955238,0.08587627,0.0072664544,0.0534925,0.0041415403,-0.0070090685,0.0052308473,-0.0024218832,-0.013588457,-0.13342454,-0.133062,-0.002131113,0.018509634,0.0022032657,-0.0012004697,-0.0030011998,-0.09171221,0.04378291,0.011592375,0.008383258,-0.0075403377,-0.06618876,0.16787937,0.0022681965,0.007942437,-0.021396412,0.050220326,0.020349974,-0.025914876,0.0021789544,-0.0039190124,0.008257228,-0.18780906,0.038544647,-0.20379275,-0.05372247,-0.0071983454,-1.7776973E-4,6.018481E-4,0.005691071,-1.723207E-4,0.004606523,-0.0048533278,8.210407E-4,-0.0024292644,-0.001732084,0.007827106,0.006818732,0.09969662,1.001012E-4,6.294497E-4,-0.14000395,-4.5512654E-5,-0.026000611,0.006717485,-0.013270604,0.069555275,-0.22032703,-0.20092806,0.045320313,0.1065812,-3.061261E-4,0.0030782444,0.0027807995,2.7888967E-4,-0.004342863,0.011948672,-0.12367618,0.0023804964,0.007392301,-9.710012E-4,0.078753926,-0.0037758462,0.009804465,-0.03401329,-0.041908395,-0.012311637,-0.0041076494,-0.0014050937,-0.0031023866,-0.00612785,-0.06571188,0.1390615,0.086249284,0.0114495335,0.0014865778,7.0587103E-4,0.0016824516,0.0078010233,-0.03472306,-0.015278221,-0.008932625,-3.7928537E-4,0.007266694,0.010495976,-0.0117970565,0.07359221,0.0013161617,3.8425358E-5,0.0012293155,-0.0031515073,-0.0019772886,-0.005598103,0.0019230713,0.00368091,0.051086485,-0.005143634,-2.8078748E-5,0.09608271,-0.15120862,0.0017381446,-0.057778317,-0.028936362,-0.0028229754,0.0038186682,-0.024011105,-0.00328194,-0.0065895156,-0.14008677,-0.051324524,-0.0039378437,-0.0011200734,-0.05505527,-0.08480629,-0.02958626,-0.0089175645,0.024623057,0.10738778,-0.007528606,-0.004661269]],\"a\":\"0\",\"e\":{\"bottom\":368,\"left\":234,\"right\":408,\"top\":192},\"b\":\"\"}"
                                                                        },
                                                                        "photo": "#{response_id}",
                                                                        "kid_id": 25194,
                                                                        "face_name": "2"
                                                                }
                                """))
                                .check(bodyString.saveAs("arrayBody"))

                                )
                }

                val getEducators = tryMax(1){
                        exec(
                                http("get educators")
                                .get("/kindergartens/28397/educators")
                                .header("Authorization", "Bearer #{response_token}")

                                .check(bodyString.saveAs("educatorsBody"))
                                .check(status.is(200))
                        )
                }


                val getKids = exec(
                        http("get kids")
                        .get("/kids")
                        .header("Authorization", "Bearer #{response_token}")

                        .check(bodyString.saveAs("kidsBody"))
                        .check(status.is(200))
                        )

                val getOrganization = exec(
                        http("get organization")
                        .get("/organization-data")
                        .header("Authorization", "Bearer #{response_token}")

                        .check(bodyString.saveAs("organiztionBody"))
                        .check(status.is(200))
                        )
        }
        
        val scenarioKindeGarden = scenario("put id photo")
                .exec(kindeGardenSimulation.generateToken)
                .repeat(2){
                        exec(
                                kindeGardenSimulation.genereatePhotoId,
                                kindeGardenSimulation.putPhotoId,
                                kindeGardenSimulation.getEducators,
                                kindeGardenSimulation.getKids,
                                kindeGardenSimulation.getOrganization
                        )
                }

        setUp(
                scenarioKindeGarden.inject(
                        rampUsers(1000).during(30.minutes)
                )
        ).protocols(httpProtocol)

}
