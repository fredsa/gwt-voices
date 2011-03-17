#!/bin/bash
#
remoteapi=http://localhost:8888/_ah/remote_api
time appcfg.py upload_data \
  --application=gwt-voices \
  --url=$remoteapi \
  --filename=gwt-voices.dat \
  --batch_size=100 \
  --rps_limit=10000000000000000000 \
  --bandwidth_limit=250000000000 \
  --http_limit=5 \
  --num_threads=1 \
  $*

