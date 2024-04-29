import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  //@TODO
  test("filterDecemberData") {
    val records = List(
      Some(CO2Record(2021, 11, 400.5)),
      Some(CO2Record(2021, 12, 410.2)),
      Some(CO2Record(2022, 1, 415.7)),
      Some(CO2Record(2022, 12, 420.3)),
      Some(CO2Record(2023, 2, 425.9))
    )

    val result = ClimateService.filterDecemberData(records)

    assert(result.forall(record => record.month != 12))
  }

  test("getMinMax") {
    val records = List(
      CO2Record(2021, 1, 400.5),
      CO2Record(2021, 2, 410.2),
      CO2Record(2021, 12, 415.7),
      CO2Record(2022, 1, 420.3),
      CO2Record(2022, 12, 425.9)
    )

    val (min, max) = ClimateService.getMinMax(records)
    assert(min == 400.5)
    assert(max == 425.9)
  }

  test("getMinMaxByYear") {
    val records = List(
      CO2Record(2021, 1, 400.5),
      CO2Record(2021, 2, 410.2),
      CO2Record(2021, 12, 415.7),
      CO2Record(2022, 1, 420.3),
      CO2Record(2022, 12, 425.9)
    )

    val (min, max) = ClimateService.getMinMaxByYear(records, 2021)
    assert(min == 400.5)
    assert(max == 415.7)
  }
}
