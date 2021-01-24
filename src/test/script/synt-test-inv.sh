#!/bin/sh
echo "Tests syntaxiques invalides"
for i in src/test/deca/syntax/invalid/*.deca
    do
        echo "$i"
        cat $i
        echo "***********Résultat***********"
        src/test/script/launchers/test_synt "$i"
    echo "---------------------------------------------------------------------------------------"
    done
