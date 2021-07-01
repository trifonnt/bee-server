#!/bin/bash

# curl -X POST "http://localhost:9000/api/measurement-records" -H  "accept: */*" -H  "Content-Type: application/json" -d '{ "deviceCode": "камера-01",  "deviceId": 0,  "inboundCount": 10,  "outboundCount": 15,  "recordedAt": "2021-06-01T11:50:14.272Z"}'

#for (( h=5; h<=17; h++ ));
for (( h=5; h<=11; h++ ));
do
  echo "index: $h";

  for (( m=0; m<=20; m+=10 ));
  do
    hour=$( printf '%02d' $h )
    minute=$( printf '%02d' $m )
#    echo "hour11: $h; minute11: $m";
#    echo "hour22: $hour; minute22: $minute";

    inbound=$(($h + 2))
    outbound=$(($h + 5))
    curl -X POST "http://localhost:9000/api/measurement-records" \
      -H  "accept: */*" -H  "Content-Type: application/json" \
      -d '{ "deviceCode": "камера-01", "inboundCount": '$inbound', "outboundCount": '$outbound', "recordedAt": "2021-06-01T'$hour':'$minute':00.000Z"}' \

  done
done
