#!/bin/sh
echo "Tests lexicaux invalides"
for i in src/test/deca/syntax/invalid/*.deca
    do
        echo "$i"
        cat $i
        echo "***********Résultat***********"
        src/test/script/launchers/test_lex "$i"
    echo "---------------------------------------------------------------------------------------"
    done
