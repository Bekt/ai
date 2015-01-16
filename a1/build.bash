#!/usr/bin/env bash

# pip dependencies
echo 'Installing dependencies...'
pip3 install -r requirements.txt

# Takes about 1-2 seconds to run.
echo 'Running...'
python3 bfs.py terrain.png 100 100 400 400

