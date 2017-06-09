#!/bin/bash

param=$1

if [ -z "$param" ] 
then
  param="mydog"
fi

git add .
git commit -m "$param"
git push

