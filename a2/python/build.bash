#!/usr/bin/env bash

# ~15 seconds.
time python3 terrain.py
# ~1 hour.
time python3 layton.py

rm -rf __pycache__/
