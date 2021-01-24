#!/bin/sh
echo "Tests syntaxiques valides"
for i in src/test/deca/syntax/valid/*.deca
    do
        echo "$i"
        cat $i
        echo "***********Résultat***********"
        src/test/script/launchers/test_synt "$i"
    echo "---------------------------------------------------------------------------------------"
    done
