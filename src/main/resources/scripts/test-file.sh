#!/bin/bash

filePath=$1
fileName=$2
inputs=("$@")
ending=.java
cd $filePath
>&2 echo "$filePath"
>&2 echo "$fileName$ending"
find $filePath -type f -name "$fileName.class" -delete
javac $fileName$ending
>&2 echo $(ls)
inputString=""
for input in "${inputs[@]:2}"
do
	inputString="$inputString$input\n"
	>&2 echo "$input"
done
>&2 echo "$inputString"
echo "Running New File"

timeout 0.1 echo -ne "$inputString" |java $fileName