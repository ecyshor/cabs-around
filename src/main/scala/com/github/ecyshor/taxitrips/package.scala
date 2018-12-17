package com.github.ecyshor

import cats.{Applicative, Apply}
import org.joda.time.DateTime
import cats.implicits._
import com.github.ecyshor.taxitrips.importing.parsing.csv.CsvParser

package object taxitrips {

  sealed trait Location

  case class Coordonates(latitude: Double, longitude: Double) extends Location

  case class LocationId(id: String) extends Location

  case class TripCourse(pickUp: Location, dropOff: Location)

  sealed trait Trip

  case class YellowTrip(vendor: String, pickupTime: DateTime, dropOffTime: DateTime, passengers: Int, distance: Double, rateCode: String,
                        course: TripCourse, storeAndForward: Boolean, paymentType: String, fareAmount: Double, extra: Option[Double], mtaTax: Double, tipAmount: Double, tollsAmount: Double,
                        improvementSurcharge: Option[Double], totalAmount: Double) extends Trip

  object YellowTrip {
    //    implicit val yellowTripCsvReader: CsvParser[YellowTrip] = (values: Map[String, String]) => Applicative[Either].map2(
    //
    //    )

  }


  /*
  ==> yellow_tripdata_2014-07.csv <==
  vendor_id,pickup_datetime,dropoff_datetime,passenger_count,trip_distance,pickup_longitude,pickup_latitude,rate_code,
  store_and_fwd_flag,dropoff_longitude,dropoff_latitude,
  payment_type,fare_amount,surcharge,mta_tax,tip_amount,tolls_amount,total_amount

  CMT,2014-07-10 10:07:55,2014-07-10 10:16:19,1,1.2,-73.981240999999997,40.781829000000002,1,N,-73.974051000000003,40.787320000000001,CSH,7.5,0,0.5,0,0,8

  ==> yellow_tripdata_2018-06.csv <==
  VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,
  PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount

  1,2018-06-01 00:15:40,2018-06-01 00:16:46,1,.00,1,N,145,145,2,3,0.5,0.5,0,0,0.3,4.3
  */
}
