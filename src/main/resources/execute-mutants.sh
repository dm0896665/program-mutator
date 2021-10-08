#!/bin/bash

filepath=$1
inputs=("$@")
for file in "$filepath"/*
do
	path="$file"
	prefix="$filepath"/
	file=${file//$prefix/}
	ending=.java
	fileName=${file//$ending/}
	cd $prefix
	javac $file
	inputString=""
	for input in "${inputs[@]:1}"
	do
		inputString="$inputString$input\n"
		>&2 echo "$input"
	done
	>&2 echo "$inputString"
	
	echo "Running New File"
	
	echo -ne "$inputString" |java $fileName
done