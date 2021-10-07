#!/bin/bash

filepath=$1
for file in "$filepath"/*
do
	path="$file"
	prefix="$filepath"/
	file=${file//$prefix/}
	ending=.java
	fileName=${file//$ending/}
	cd $prefix
	javac $file
	echo -ne "4\n" |java $fileName
done