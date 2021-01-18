#!/bin/sh

for i in ./src/test/deca/codegen/valid/*
do
    for j in $i/*.deca
    do
        str=$j
        prefix=${str%.deca}
        assembly=$prefix.ass
        result=$prefix.res
        echo $str
        #cat "$j"
        echo "\n"
        decac "$j"
        ima $assembly > $result
        echo "fichier $assembly généré\n"
        echo "Résultat de l'exécution dans $result"
    echo "------------------------------------------------------------------------------------"
    done
done
