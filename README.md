![travis](https://travis-ci.org/ecyshor/cabs-around.svg?branch=master)

Playground working with scala functional libraries and streaming analytics support.

#Idea

Load the 1billion plus taxi cab rides into clickhouse by streaming them and get the most out of it.
 - [ ] Stream the cab rides to clickhouse using alpakka
 - [ ] Linear prediction to get cost approximation of ride from A to B 
 - [ ] HLL in memory to calculate windowed values for cabs on the road

Libraries:

- Cats
- Shapeless
- Http4s
- Circe
- Akka streams

Testing:

- Scalatest
- Scalacheck

To be integrated:

- Algebird
- Akka typed actors (real time analytics)

Infra:

- Clickhouse
