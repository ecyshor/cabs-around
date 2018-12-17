package com.github.ecyshor.taxitrips.importing

package object parsing {


  val YellowHeader = "vendor_name,Trip_Pickup_DateTime,Trip_Dropoff_DateTime,Passenger_Count,Trip_Distance," +
    "Start_Lon,Start_Lat,Rate_Code,store_and_forward,End_Lon,End_Lat,Payment_Type,Fare_Amt,surcharge,mta_tax," +
    "Tip_Amt,Tolls_Amt,Total_Amt"
  val YellowNewHeader = "VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount"
  //VTS,2009-12-17 07:35:00,2009-12-17 07:40:00,1,0.11,-73.987927999999997,40.737884999999999,,,-73.990335000000002,40.748449999999998,Credit,4.9000000000000004,0,0.5,1,0,6.4000000000000004
  val GreenHeader = "VendorID,lpep_pickup_datetime,Lpep_dropoff_datetime,Store_and_fwd_flag,RateCodeID," +
    "Pickup_longitude,Pickup_latitude,Dropoff_longitude,Dropoff_latitude,Passenger_count,Trip_distance," +
    "Fare_amount,Extra,MTA_tax,Tip_amount,Tolls_amount,Ehail_fee,Total_amount,Payment_type,Trip_type"
  //2,2013-12-01 00:00:00,2013-12-01 20:44:23,N,1,0,0,-73.957260131835937,40.742355346679687,1,4.00,13,0.5,0.5,0,0,,14,2,,,

}

